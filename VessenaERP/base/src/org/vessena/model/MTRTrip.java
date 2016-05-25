/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocation;
import org.compiere.model.MOrgInfo;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 * @author gbrust
 *
 */
public class MTRTrip extends X_UY_TR_Trip implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	private static final long serialVersionUID = -7651001120985079632L;

	/**
	 * @param ctx
	 * @param UY_TR_Trip_ID
	 * @param trxName
	 */
	public MTRTrip(Properties ctx, int UY_TR_Trip_ID, String trxName) {
		super(ctx, UY_TR_Trip_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTrip(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if(param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		if(param.isControlTripValue()){ //se controlan valores ingresados segun parametros de transporte
			
			if(this.getWeight().compareTo(Env.ZERO)<=0) throw new AdempiereException("El peso bruto debe ser mayor a cero");
			if(this.getWeight2().compareTo(Env.ZERO)<=0) throw new AdempiereException("El peso neto debe ser mayor a cero");
			if(this.getQtyPackage().compareTo(Env.ZERO)<=0) throw new AdempiereException("Cantidad de bultos debe ser mayor a cero");
			if(this.getProductAmt().compareTo(Env.ZERO)<=0) throw new AdempiereException("Valor de la mercaderia debe ser mayor a cero");
			if(this.getWeight2().compareTo(this.getWeight())>0) throw new AdempiereException("El peso neto no puede ser mayor al peso bruto");			
		}	
		
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MTRWay way = (MTRWay)this.getUY_TR_Way();
		MBPartner partner = null;
		String sql = "";
		int locID = 0;
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		MTRConfigRound round = MTRConfigRound.forConfig(getCtx(), param.get_ID(), get_TrxName());
		
		if (round==null) throw new AdempiereException("No se obtuvieron parametros de precision decimal para la empresa actual");
		
		if(is_ValueChanged("Weight")){
			
			this.setWeight(this.getWeight().setScale(round.getWeight(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("Weight2")){
			
			this.setWeight2(this.getWeight2().setScale(round.getWeight2(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("Volume")){
			
			this.setVolume(this.getVolume().setScale(round.getVolume(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("QtyPackage")){
			
			this.setQtyPackage(this.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("ProductAmt")){
			
			this.setProductAmt(this.getProductAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP));
			
		}		
		
		//seteo el tipo de expediente segun el trayecto elegido y la organizacion actual
		if(is_ValueChanged("uy_tr_way_id")){

			way = (MTRWay)this.getUY_TR_Way();

			MOrgInfo orgInfo = MOrgInfo.get(this.getCtx(), this.getAD_Org_ID(), this.get_TrxName()); //obtengo informacion de la organizacion

			if(orgInfo!=null && orgInfo.get_ID()>0){

				MLocation loc = (MLocation)orgInfo.getC_Location(); //obtengo localizacion de la organizacion

				if(loc!= null && loc.get_ID()>0){

					if(way.getC_Country_ID()==loc.getC_Country_ID() && way.getC_Country_ID_1()!=loc.getC_Country_ID()){

						this.setTripType("EXPORTACION");

					} else if(way.getC_Country_ID_1()==loc.getC_Country_ID() && way.getC_Country_ID()!=loc.getC_Country_ID()){

						this.setTripType("IMPORTACION");

					} else if(way.getC_Country_ID()==loc.getC_Country_ID() && way.getC_Country_ID_1()==loc.getC_Country_ID()) this.setTripType("NACIONAL"); 

				} else throw new AdempiereException("No se pudo obtener localizacion para la organizacion actual");	

			} else throw new AdempiereException("No se pudo obtener informacion de la organizacion actual");	
			
		}
		
		//se cargan direcciones fiscales de cliente origen y destino
		if(is_ValueChanged("c_bpartner_id_from")){

			partner = new MBPartner(getCtx(),this.getC_BPartner_ID_From(),get_TrxName());
			
			sql = "select c_bpartner_location_id" +
			      " from c_bpartner_location" +
			      " where c_bpartner_id = " + partner.get_ID() +
			      " and isbillto = 'Y' and isactive = 'Y'";
			locID = DB.getSQLValueEx(get_TrxName(), sql);
			
			if(locID > 0){
				
				this.setC_BPartner_Location_ID(locID);				
				
			} else {
				
				DB.executeUpdate("update uy_tr_trip set c_bpartner_location_id = null where uy_tr_trip_id = " + this.getUY_TR_Trip_ID(), get_TrxName());
				
				MBPartnerLocation bploc = new MBPartnerLocation(getCtx(), 0, get_TrxName());
				bploc.setC_BPartner_ID(partner.get_ID());
				bploc.setName("NO DEFINIDO");
				//bploc.setC_Location_ID(location.get_ID());
				bploc.setC_Country_ID(336);
				bploc.setAddress1("NO DEFINIDO");
				bploc.setIsBillTo(true);
				bploc.setIsShipTo(true);
				bploc.setIsRemitTo(true);
				bploc.setIsPayFrom(true);
				bploc.saveEx();
				
				this.setC_BPartner_Location_ID(bploc.get_ID());
			}
			
		}
		
		if(is_ValueChanged("c_bpartner_id_to")){
			
			partner = new MBPartner(getCtx(),this.getC_BPartner_ID_To(),get_TrxName());
			
			sql = "select c_bpartner_location_id" +
			      " from c_bpartner_location" +
			      " where c_bpartner_id = " + partner.get_ID() +
			      " and isbillto = 'Y' and isactive = 'Y'";
			locID = DB.getSQLValueEx(get_TrxName(), sql);
			
			if(locID > 0){
				
				this.setC_BPartner_Location_ID_2(locID);				
				
			} else {
				
				DB.executeUpdate("update uy_tr_trip set c_bpartner_location_id_2 = null where uy_tr_trip_id = " + this.getUY_TR_Trip_ID(), get_TrxName());
												
				MBPartnerLocation bploc = new MBPartnerLocation(getCtx(), 0, get_TrxName());
				bploc.setC_BPartner_ID(partner.get_ID());
				bploc.setName("NO DEFINIDO");
				//bploc.setC_Location_ID(location.get_ID());
				bploc.setC_Country_ID(336);
				bploc.setAddress1("NO DEFINIDO");
				bploc.setIsBillTo(true);
				bploc.setIsShipTo(true);
				bploc.setIsRemitTo(true);
				bploc.setIsPayFrom(true);
				bploc.saveEx();
				
				this.setC_BPartner_Location_ID_2(bploc.get_ID());
			}
			
		}	
		
		if(way.isPrintExpo()){

			if(!newRecord && is_ValueChanged("ReferenceNo")){

				this.updateMic(true);
			
			}
		}
		
		if(way.isPrintDeclaration()){

			if(!newRecord && (is_ValueChanged("DeclarationType") || is_ValueChanged("Numero") || is_ValueChanged("UY_TR_Despachante_ID_3"))){

				this.updateMic(false);
							
			}
		}

		/*if(!newRecord){
			//si hay solo 1 linea de OT para este expediente, lo actualizo con los nuevos valores
			if(this.getQtyLines().size()==1){

				DB.executeUpdateEx("update uy_tr_tripqty set weight = " + this.getWeight() + ", weight2 = " + this.getWeight2() + ", qtypackage = " +
						this.getQtyPackage() + ", volume = " + this.getVolume() + ", c_currency_id = " + this.getC_Currency_ID() + ", productamt = " + 
						this.getProductAmt() + " where uy_tr_trip_id = " + this.get_ID(), get_TrxName());

			}
		}*/
		
		return true;
	}

	public void refreshFromTrip() {
		
		String sql = "";
		
		try{
			
			if(this.getQtyLines().size()>0) this.verifyValues();//verifico que las sumas de valores en lineas de distribucion sea iguales a los totales del expediente      
			
			MTRCrt crt = MTRCrt.forTrip(getCtx(), this.get_ID(), get_TrxName()); //obtengo el CRT
			
			MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
			
			if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
			
			MTRConfigRound round = MTRConfigRound.forConfig(getCtx(), param.get_ID(), get_TrxName());
			
			if (round==null) throw new AdempiereException("No se obtuvieron parametros de precision decimal para la empresa actual");
			
			//si existe, actualizo proforma asociada al expediente
			sql = "select i.c_invoice_id" +
					" from c_invoice i" +
					" inner join c_doctype d on i.c_doctypetarget_id = d.c_doctype_id" +
					" where uy_tr_trip_id = " + this.get_ID() +
					" and d.value = 'fileprofinvoice'" +
					" and i.docstatus <> 'VO'";
			int invID = DB.getSQLValueEx(get_TrxName(), sql);

			if(invID > 0){

				MInvoice hdr = new MInvoice(getCtx(),invID,get_TrxName());
				hdr.setuy_cantbultos(this.getQtyPackage());
				hdr.set_ValueOfColumn("Weight", this.getWeight());
				hdr.set_ValueOfColumn("Weight2", this.getWeight2());
				hdr.setAmtOriginal(this.getProductAmt());
				hdr.set_ValueOfColumn("UY_TR_PackageType_ID", this.getUY_TR_PackageType_ID());
				hdr.set_ValueOfColumn("ReferenceNo", this.getReferenceNo());
				hdr.set_ValueOfColumn("C_Currency2_ID", this.getC_Currency_ID());
				hdr.setincoterms(this.getIncotermType());
				hdr.saveEx();			
			}

			if(crt!=null && crt.get_ID()>0){
								
				crt.setQtyPackage(this.getQtyPackage());
				crt.setWeight(this.getWeight());
				crt.setWeight2(this.getWeight2());
				crt.setVolume(this.getVolume());
				crt.setC_Currency_ID(this.getC_Currency_ID());
				crt.setTotalAmt(this.getProductAmt());
				
				//seteo importador y exportador
				crt.loadPartners(this);		
				
				//seteo cantidad y clases de bultos
				if(this.getProductType()!=null && !this.getProductType().equalsIgnoreCase("")){
					
					if(this.getDescription()!=null && !this.getDescription().equalsIgnoreCase("")){
						
						crt.setDescription(this.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP) + " " + this.getDescription() + "\n" + this.getProductType());			
						
					} else crt.setDescription(this.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP) + "\n" + this.getProductType());			
					
				} else {
					
					if(this.getDescription()!=null && !this.getDescription().equalsIgnoreCase("")){
						
						crt.setDescription(this.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP) + " " + this.getDescription());
						
					} else crt.setDescription(this.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP).toString());			
						
				}
				
				// Importe a texto con formato . para miles y , para decimales
				BigDecimal impaux = this.getProductAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP);
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(round.getProductAmt());
				df.setMinimumFractionDigits(round.getProductAmt());
				df.setGroupingUsed(true);
				String result = df.format(impaux);
				
				if (result != null){
					result = result.replace(".", ";");
					result = result.replace(",", ".");
					result = result.replace(";", ",");
				}
				
				crt.setImporte(result);				
				
				crt.saveEx();
				
				// Actualizo OT y movimientos
				if(this.getQtyLines().size()>0) this.refreshMovements();
				
				// Actualizo toda orden que haya tenido este CRT
				//crt.refreshTRStock();
				
			}

		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}		
	
	}
		
	/***
	 * Actualiza movimientos desde el expediente.
	 * OpenUp #
	 * @author Nicolas Sarlabos - 13/04/2015
	 * @see
	 * @return
	 */
	private void refreshMovements() {
		
		MTRTransOrderLine otLine = null;
		MTRLoadMonitorLine mLine = null;
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		MTRConfigRound round = MTRConfigRound.forConfig(getCtx(), param.get_ID(), get_TrxName());
		
		if (round==null) throw new AdempiereException("No se obtuvieron parametros de precision decimal para la empresa actual");
		
		List<MTRTripQty> qtyLines = this.getQtyLines(); //obtengo lineas de distribucion
		
		for(MTRTripQty line : qtyLines){

			//actualizo movimientos y stock
			if(line.getUY_TR_Stock_ID() > 0){
			
				//actualizo movimiento
				mLine = new MTRLoadMonitorLine(getCtx(), line.getUY_TR_LoadMonitorLine_ID(), get_TrxName());
				if(mLine!=null && mLine.get_ID()>0){
					mLine.setQtyPackage(line.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
					mLine.setWeight(line.getWeight().setScale(round.getWeight(), RoundingMode.HALF_UP));
					mLine.setWeight2(line.getWeight2().setScale(round.getWeight2(), RoundingMode.HALF_UP));
					mLine.setVolume(line.getVolume().setScale(round.getVolume(), RoundingMode.HALF_UP));
					mLine.setProductAmt(line.getProductAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP));
					mLine.setUY_TR_PackageType_ID(this.getUY_TR_PackageType_ID());
					mLine.saveEx();
				}

				//actualizo stock para este CRT					
				DB.executeUpdateEx("delete from uy_tr_stock where uy_tr_crt_id = " + line.getUY_TR_Crt_ID() + " and uy_tr_trip_id = " + line.getUY_TR_Trip_ID() +
						" and m_warehouse_id = " + line.getM_Warehouse_ID() + " and uy_tr_stock_id <> " + line.getUY_TR_Stock_ID(), get_TrxName());

				MTRStock stock = new MTRStock(getCtx(), line.getUY_TR_Stock_ID(), get_TrxName()); //instancio linea de stock
				if(stock!=null && stock.get_ID()>0){
					stock.setProductAmt(line.getProductAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP));
					stock.setQtyPackage(line.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
					stock.setVolume(line.getVolume().setScale(round.getVolume(), RoundingMode.HALF_UP));
					stock.setWeight(line.getWeight().setScale(round.getWeight(), RoundingMode.HALF_UP));
					stock.setWeight2(line.getWeight2().setScale(round.getWeight2(), RoundingMode.HALF_UP));
					stock.setC_Currency_ID(line.getC_Currency_ID());
					stock.saveEx();	
				}

				
			} else { //actualizo ordenes y movimientos
				
				//actualizo OT
				otLine = new MTRTransOrderLine(getCtx(), line.getUY_TR_TransOrderLine_ID(), get_TrxName());
				if(otLine!=null && otLine.get_ID()>0){
					otLine.setQtyPackage(line.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
					otLine.setWeight(line.getWeight().setScale(round.getWeight(), RoundingMode.HALF_UP));
					otLine.setWeight2(line.getWeight2().setScale(round.getWeight2(), RoundingMode.HALF_UP));
					otLine.setVolume(line.getVolume().setScale(round.getVolume(), RoundingMode.HALF_UP));
					otLine.setProductAmt(line.getProductAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP));
					otLine.setUY_TR_PackageType_ID(this.getUY_TR_PackageType_ID());
					otLine.isFromTrip = true;
					otLine.saveEx();
				}

				//actualizo movimiento
				mLine = new MTRLoadMonitorLine(getCtx(), otLine.getUY_TR_LoadMonitorLine_ID(), get_TrxName());
				if(mLine!=null && mLine.get_ID()>0){
					mLine.setQtyPackage(line.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
					mLine.setWeight(line.getWeight().setScale(round.getWeight(), RoundingMode.HALF_UP));
					mLine.setWeight2(line.getWeight2().setScale(round.getWeight2(), RoundingMode.HALF_UP));
					mLine.setVolume(line.getVolume().setScale(round.getVolume(), RoundingMode.HALF_UP));
					mLine.setProductAmt(line.getProductAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP));
					mLine.setUY_TR_PackageType_ID(this.getUY_TR_PackageType_ID());
					mLine.saveEx();
				}
				
			}
	
			//actualizo MIC
			this.refreshMicHdr(line.getUY_TR_Crt_ID(), line);
			this.refreshMicCont(line.getUY_TR_Crt_ID(), true, line);
			this.refreshMicCont(line.getUY_TR_Crt_ID(), false, line);			
			
		}	
		
	}

	private void verifyValues() {
		
		String sql = "";
		BigDecimal qty = Env.ZERO;

		sql = "select coalesce(sum(qtypackage),0)" +
				" from uy_tr_tripqty" + 
				" where uy_tr_trip_id = " + this.get_ID();
		qty = DB.getSQLValueBDEx(get_TrxName(), sql);

		if(this.getQtyPackage().compareTo(qty) < 0) throw new AdempiereException("Cantidad de bultos del expediente no puede ser menor al total de bultos distribuidos");

		sql = "select coalesce(sum(weight),0)" +
				" from uy_tr_tripqty" + 
				" where uy_tr_trip_id = " + this.get_ID();
		qty = DB.getSQLValueBDEx(get_TrxName(), sql);

		if(this.getWeight().compareTo(qty) < 0 ) throw new AdempiereException("Peso bruto del expediente no puede ser menor al total de pesos brutos distribuidos");

		sql = "select coalesce(sum(weight2),0)" +
				" from uy_tr_tripqty" + 
				" where uy_tr_trip_id = " + this.get_ID();
		qty = DB.getSQLValueBDEx(get_TrxName(), sql);

		if(this.getWeight2().compareTo(qty) < 0) throw new AdempiereException("Peso neto del expediente no puede ser menor al total de pesos netos distribuidos");

		sql = "select coalesce(sum(volume),0)" +
				" from uy_tr_tripqty" + 
				" where uy_tr_trip_id = " + this.get_ID();
		qty = DB.getSQLValueBDEx(get_TrxName(), sql);

		if(this.getVolume().compareTo(qty) < 0) throw new AdempiereException("Volumen del expediente no puede ser menor al total de volumenes distribuidos");

		sql = "select coalesce(sum(productamt),0)" +
				" from uy_tr_tripqty" + 
				" where uy_tr_trip_id = " + this.get_ID();
		qty = DB.getSQLValueBDEx(get_TrxName(), sql);

		if(this.getProductAmt().compareTo(qty) < 0) throw new AdempiereException("Valor de mercaderia del expediente no puede ser menor al total de valores de mercaderia distribuidos");

	}

	private void refreshMicHdr(int crtID, MTRTripQty line) {
		
		String sql = "";
		MTRMic mic = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			sql = "select uy_tr_mic_id" + 
                  " from uy_tr_mic" +
                  " where uy_tr_crt_id = " + crtID +
                  " and uy_tr_transorder_id = " + line.getUY_TR_TransOrder_ID();
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while(rs.next()){
				
				mic = new MTRMic(getCtx(),rs.getInt("uy_tr_mic_id"),get_TrxName());
				
				mic.setC_Currency_ID(this.getC_Currency_ID());
				mic.setImporte(line.getProductAmt());
				mic.setQtyPackage(line.getQtyPackage());
				mic.setUY_TR_PackageType_ID(this.getUY_TR_PackageType_ID());
				mic.setpesoBruto(line.getWeight());
				mic.setpesoNeto(line.getWeight2());
				mic.setDescription(this.getProductType());
				mic.saveEx();
			
				MTRMic.updateTotals(mic);				
				
			}		
			
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
	
	}
	
	private void refreshMicCont(int crtID, boolean isCRT1, MTRTripQty line) {
		
		String sql = "";
		MTRMic mic = null;
		MTRMicCont cont = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			if(isCRT1){ //si es el CRT1 de la continuacion
				
				sql = "select c.uy_tr_miccont_id" + 
		              " from uy_tr_miccont c" +
					  " inner join uy_tr_mic m on c.uy_tr_mic_id = m.uy_tr_mic_id" +	
		              " where c.uy_tr_crt_id = " + crtID +
		              " and m.uy_tr_transorder_id = " + line.getUY_TR_TransOrder_ID();
								
			} else { //si es el CRT2 de la continuacion
				
				sql = "select c.uy_tr_miccont_id" + 
			          " from uy_tr_miccont c" +
					  " inner join uy_tr_mic m on c.uy_tr_mic_id = m.uy_tr_mic_id" +	
			          " where c.uy_tr_crt_id_1 = " + crtID +
			          " and m.uy_tr_transorder_id = " + line.getUY_TR_TransOrder_ID();		
				
			}
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while(rs.next()){
				
				cont = new MTRMicCont(getCtx(),rs.getInt("uy_tr_miccont_id"),get_TrxName());
				mic = new MTRMic(getCtx(),cont.getUY_TR_Mic_ID(),get_TrxName());
				
				if(isCRT1){

					cont.setC_Currency_ID(this.getC_Currency_ID());
					cont.setImporte(line.getProductAmt());
					cont.setQtyPackage(line.getQtyPackage());
					cont.setUY_TR_PackageType_ID(this.getUY_TR_PackageType_ID());
					cont.setpesoBruto(line.getWeight());
					cont.setpesoNeto(line.getWeight2());
					cont.setDescription(this.getProductType());
					cont.saveEx();

				} else {

					cont.setC_Currency2_ID(this.getC_Currency_ID());
					cont.setImporte2(line.getProductAmt());
					cont.setQtyPackage2(line.getQtyPackage());
					cont.setUY_TR_PackageType_ID_1(this.getUY_TR_PackageType_ID());
					cont.setpesoBruto2(line.getWeight());
					cont.setpesoNeto2(line.getWeight2());
					cont.setdescripcion(this.getProductType());
					cont.saveEx();
					
				}	
				
				MTRMic.updateTotals(mic);				
				
			}		
			
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
	
	}

	private void updateMic(boolean isPrintExpo) {

		String sql = "", value = "";

		if(isPrintExpo){

			MBPartner partner = new MBPartner(getCtx(),this.getC_BPartner_ID_To(),get_TrxName()); //instancio el exportador

			value = "EXPORTADOR: " + partner.getName() + "\n";

			if(this.getReferenceNo()!=null && !this.getReferenceNo().equalsIgnoreCase("")) value += "FACTURA N°: " + this.getReferenceNo();

		} else {

			if(this.getUY_TR_Despachante_ID_3()>0){

				MTRDespachante desp = new MTRDespachante(getCtx(),this.getUY_TR_Despachante_ID_3(),get_TrxName()); //obtengo representante en frontera del exportador

				value = "DESP: " + desp.getName() + "\n";

			}	

			if(this.getDeclarationType()!=null && !this.getDeclarationType().equalsIgnoreCase("") && (this.getNumero()!=null && !this.getNumero().equalsIgnoreCase(""))){

				if(value.equalsIgnoreCase("")){

					value = this.getDeclarationType() + ": " + this.getNumero();					

				} else value += this.getDeclarationType() + ": " + this.getNumero();			

			}

		}
		
		//actualizo cabezales de MIC
		sql = "update uy_tr_mic set observaciones2 = truckdata || '\n' || '" + value +
				"' where uy_tr_mic_id in (select m.uy_tr_mic_id" +
				" from uy_tr_mic m" +
				" inner join uy_tr_crt c on m.uy_tr_crt_id = c.uy_tr_crt_id" +
				" where c.uy_tr_trip_id = " + this.get_ID() + ")";

		DB.executeUpdateEx(sql, get_TrxName());
		
		//actualizo continuaciones de MIC - CRT1
		sql = "update uy_tr_miccont cont set observaciones2 = m.truckdata || '\n' || '" + value +
				"' from uy_tr_mic m" +
				" where cont.uy_tr_mic_id = m.uy_tr_mic_id and uy_tr_miccont_id in (select cont.uy_tr_miccont_id" +
				" from uy_tr_miccont cont" +
				" inner join uy_tr_mic hdr on cont.uy_tr_mic_id = hdr.uy_tr_mic_id" +
				" inner join uy_tr_crt crt on cont.uy_tr_crt_id = crt.uy_tr_crt_id" +
				" where crt.uy_tr_trip_id = " + this.get_ID() + ")";

		DB.executeUpdateEx(sql, get_TrxName());
		
		//actualizo continuaciones de MIC - CRT2
		sql = "update uy_tr_miccont cont set observaciones4 = m.truckdata || '\n' || '" + value +
				"' from uy_tr_mic m" +
				" where cont.uy_tr_mic_id = m.uy_tr_mic_id and uy_tr_miccont_id in (select cont.uy_tr_miccont_id" +
				" from uy_tr_miccont cont" +
				" inner join uy_tr_mic hdr on cont.uy_tr_mic_id = hdr.uy_tr_mic_id" +
				" inner join uy_tr_crt crt on cont.uy_tr_crt_id_1 = crt.uy_tr_crt_id" +
				" where crt.uy_tr_trip_id = " + this.get_ID() + ")";

		DB.executeUpdateEx(sql, get_TrxName());		
		
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {

		//	Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		/*
		BigDecimal saldo = this.getSaldoAmount().setScale(2, RoundingMode.HALF_UP);
		
		if(saldo.compareTo(Env.ZERO) <= 0) {
						
			ADialog.info(0,null,"Expediente completo con saldo final = " + saldo);
			
		} else throw new AdempiereException("Imposible completar, el expediente tiene saldo = " + saldo + ". Verifique que se han realizado y completado todas las facturas de flete");
		
		*/
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
		
	}

	/***
	 * Verifica que no queden saldos en el expediente antes de completar.
	 * OpenUp #2222
	 * @author Nicolas Sarlabos - 27/05/2014
	 * @see
	 * @return
	 */
	@SuppressWarnings("unused")
	private BigDecimal getSaldoAmount() {
		
		BigDecimal saldo = Env.ZERO;
		String sql = "";
		BigDecimal amtProforma = Env.ZERO;
		BigDecimal amtFreight = Env.ZERO;
		
		MDocType docProforma = MDocType.forValue(getCtx(), "fileprofinvoice", get_TrxName()); //obtengo tipo documento Proforma
		
		if(docProforma==null || docProforma.get_ID()<=0) throw new AdempiereException("Error al obtener tipo de documento Proforma Expediente"); 

		MDocType docFreightInv = MDocType.forValue(getCtx(), "freightinvoice", get_TrxName()); //obtengo tipo documento Factura Flete

		if(docFreightInv==null || docFreightInv.get_ID()<=0) throw new AdempiereException("Error al obtener tipo de documento Factura Flete");

		//obtengo suma de importes totales de las proformas para este expediente
		sql = "select coalesce(sum(grandtotal),0)" +
				" from c_invoice" +
				" where c_doctypetarget_id = " + docProforma.get_ID() +
				" and docstatus = 'CO' and uy_tr_trip_id = " + this.get_ID();
		amtProforma  = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		//obtengo suma de importes totales de las facturas de flete para este expediente
		sql = "select coalesce(sum(grandtotal),0)" +
				" from c_invoice" +
				" where c_doctypetarget_id = " + docFreightInv.get_ID() +
				" and docstatus = 'CO' and uy_tr_trip_id = " + this.get_ID();
		amtFreight  = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		saldo = amtProforma.subtract(amtFreight);
		
		return saldo;
	}
	
	/***
	 * Devuelve el saldo de cantidad de bultos pendiente para carga de campos en MIC/DTA.
	 * OpenUp #2222
	 * @author Nicolas Sarlabos - 02/06/2014
	 * @see
	 * @return
	 */
	public BigDecimal getSaldoMic(MTRCrt crt){
		
		BigDecimal value = Env.ZERO;
		String sql = "";
		
		//obtengo cantidad de bultos en cabezales de MIC para el CRT seleccionado
		sql = "select coalesce(sum (m.qtypackage),0)" +
              " from uy_tr_mic m" +
              " inner join uy_tr_crt c on m.uy_tr_crt_id = c.uy_tr_crt_id" +
              " inner join uy_tr_trip t on c.uy_tr_trip_id = t.uy_tr_trip_id" +
              " where t.uy_tr_trip_id = " + this.get_ID() +
              " and c.uy_tr_crt_id = " + crt.get_ID();
		BigDecimal qtyHdr = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		//obtengo cantidad de bultos en continuaciones de MIC para el CRT seleccionado
		sql = "select coalesce(sum (mc.qtypackage),0)" +
              " from uy_tr_miccont mc" +
              " inner join uy_tr_crt c on mc.uy_tr_crt_id = c.uy_tr_crt_id" +
              " inner join uy_tr_trip t on c.uy_tr_trip_id = t.uy_tr_trip_id" +
              " where t.uy_tr_trip_id = " + this.get_ID() +
              " and c.uy_tr_crt_id = " + crt.get_ID();
		BigDecimal qtyContCrt1 = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		//obtengo cantidad de bultos en continuaciones de MIC para el CRT seleccionado
		sql = "select coalesce(sum (mc.qtypackage),0)" +
              " from uy_tr_miccont mc" +
              " inner join uy_tr_crt c on mc.uy_tr_crt_id_1 = c.uy_tr_crt_id" +
              " inner join uy_tr_trip t on c.uy_tr_trip_id = t.uy_tr_trip_id" +
              " where t.uy_tr_trip_id = " + this.get_ID() +
              " and c.uy_tr_crt_id = " + crt.get_ID();
		BigDecimal qtyContCrt2 = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		//obtengo cantidad de bultos en Proformas para el CRT seleccionado
		sql = "select coalesce(sum (i.uy_cantbultos),0)" +
              " from c_invoice i" +
			  " inner join uy_tr_crt c on i.c_invoice_id = c.c_invoice_id" +	
              " where c.uy_tr_crt_id = " + crt.getUY_TR_Crt_ID();
		BigDecimal qtyProform = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		BigDecimal totalCont = qtyContCrt1.add(qtyContCrt2);
		
		value = (qtyProform.subtract(qtyHdr.add(totalCont))).setScale(2, RoundingMode.HALF_UP);		
		
		return value;
	}

	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		this.validateVoid();		
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
		
	}

	/***
	 * Verifica que no exista una proforma completa para este expediente.
	 * OpenUp #5526
	 * @author Nicolas Sarlabos - 09/03/2016
	 * @see
	 * @return
	 */
	private void validateVoid() {
		
		MDocType doc = MDocType.forValue(getCtx(), "fileprofinvoice", get_TrxName());
		
		String sql = "select c_invoice_id" +
				" from c_invoice" +
				" where c_doctypetarget_id = " + doc.get_ID() +
				" and uy_tr_trip_id = " + this.get_ID() +
				" and docstatus <> 'VO'";
		
		int invoiceID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(invoiceID > 0){
			
			MInvoice inv = new MInvoice(getCtx(), invoiceID, get_TrxName());
			
			throw new AdempiereException("Imposible anular. Existe la proforma Nº " + inv.getDocumentNo() + " en estado distinto a ANULADO.");		
			
		}	
		
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
	
		// Before reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (processMsg != null)
			return false;

		// After reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

		return true;
	}
	
	/***
	 * Obtiene y retorna un expediente para la cotizacion recibida
	 * OpenUp Ltda. Issue #3399
	 * @author Nicolas Sarlabos - 06/02/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRTrip forBudget(Properties ctx, int budgetID, String trxName){
		
		String whereClause = X_UY_TR_Trip.COLUMNNAME_UY_TR_Budget_ID + "=" + budgetID;
		
		MTRTrip trip = new Query(ctx, I_UY_TR_Trip.Table_Name, whereClause, trxName)
		.first();
				
		return trip;
	}
	
	/***
	 * Obtiene y retorna todas las lineas de OTs para este presupuesto.
	 * OpenUp Ltda. Issue # 
	 * @author Nicolas Sarlabos - 13/04/2015
	 * @see
	 * @return
	 */
	public List<MTRTripQty> getQtyLines(){

		String whereClause = X_UY_TR_TripQty.COLUMNNAME_UY_TR_Trip_ID + "=" + this.get_ID();

		List<MTRTripQty> lines = new Query(getCtx(), I_UY_TR_TripQty.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna cantidad de lineas de OTs, sin repetir n° de OT, para este expediente.
	 * OpenUp Ltda. Issue # 
	 * @author Nicolas Sarlabos - 13/04/2015
	 * @see
	 * @return
	 */
	/*public int getQtyLinesOT(){
		
		int qty = 0;
		
		String sql = "select count (distinct (uy_tr_transorder_id))" +
		             " from uy_tr_tripqty" +
				     " where uy_tr_trip_id = " + this.get_ID();
		
		qty = DB.getSQLValueEx(get_TrxName(), sql);

		return qty;
	}*/

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
