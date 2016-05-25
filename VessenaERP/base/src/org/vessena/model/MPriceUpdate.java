/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MOrg;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.retail.MRTRetailInterface;

/**
 * @author Nicolas
 *
 */
public class MPriceUpdate extends X_UY_PriceUpdate implements DocAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1337179015166269780L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
	private boolean isAmount = false;
	private BigDecimal percentage = Env.ZERO;
	private int seqID = 0;

	/**
	 * @param ctx
	 * @param UY_PriceUpdate_ID
	 * @param trxName
	 */
	public MPriceUpdate(Properties ctx, int UY_PriceUpdate_ID, String trxName) {
		super(ctx, UY_PriceUpdate_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPriceUpdate(Properties ctx, ResultSet rs, String trxName) {
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
	protected boolean beforeSave(boolean newRecord) {	

		MDocType doc = (MDocType)this.getC_DocType();
		
		if (doc.getValue() != null){
			if (doc.getValue().equalsIgnoreCase("priceupdate")){
				if (this.getC_Currency_ID() != this.getC_Currency_ID_2()) {
					throw new AdempiereException("La moneda de la Lista Origen debe ser igual a la moneda de la Lista Destino");	
				}
			}
		}

		return true;
	}

	@Override
	public boolean applyIt() {

		MDocType doc = (MDocType)this.getC_DocType();
		
		if (doc.getValue() != null){
			if (doc.getValue().equalsIgnoreCase("priceupdate")){
				this.loadData();
			}
		}
		
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);

		return true;
	}
	
	/***
	 * Carga las lineas del documento segun los filtros seleccionados.
	 * OpenUp Ltda. Issue #4420
	 * @author Nicolas Sarlabos - 17/06/2015
	 * @see
	 * @return
	 */
	private void loadData() {
		
		String sql = "", where = "";
		
		/*
		if(this.getAmount().compareTo(Env.ZERO)!=0 && this.getPercentage().compareTo(Env.ZERO)!=0) throw new AdempiereException("Debe indicar importe o porcentaje, pero no ambos");
		if(this.getAmount().compareTo(Env.ZERO)==0 && this.getPercentage().compareTo(Env.ZERO)==0) throw new AdempiereException("Debe indicar importe o porcentaje");
		*/

		if(this.getPricePrecision()<0) throw new AdempiereException("Precision decimal no puede ser menor a cero");
				
		MPriceList listFrom = (MPriceList)this.getM_PriceList();//instancio lista origen		
		
		//obtengo ultima version activa para la lista de origen seleccionada
		MPriceListVersion versionFrom = MPriceListVersion.forPriceList(getCtx(), listFrom.getM_PriceList_ID(), get_TrxName());
		
		if(versionFrom!=null && versionFrom.get_ID()>0){
			
			this.setM_PriceList_Version_ID(versionFrom.get_ID());
			
		} else throw new AdempiereException("Error al obtener ultima version activa para la lista ORIGEN seleccionada");		
		
		MPriceList listTo = new MPriceList(getCtx(),this.getM_PriceList_ID_2(),get_TrxName());//instancio lista destino
				
		//obtengo ultima version activa para la lista de destino seleccionada
		MPriceListVersion versionTo = MPriceListVersion.forPriceList(getCtx(), listTo.getM_PriceList_ID(), get_TrxName());
		
		if(versionTo!=null && versionTo.get_ID()>0){
			
			this.setM_PriceList_Version_ID_2(versionTo.get_ID());
			
		} else throw new AdempiereException("Error al obtener ultima version activa para la lista DESTINO seleccionada");
		
		//obtengo secuencia para realizar inserts en tabla de lineas del documento actual
		sql = "select ad_sequence_id from ad_sequence where lower(name) like 'uy_priceupdateline'";
		this.seqID = DB.getSQLValueEx(get_TrxName(), sql);

		if(this.getAmount().compareTo(Env.ZERO)!=0){ //si se ingreso un importe

			this.isAmount = true;

		} else if(this.getAmount().compareTo(Env.ZERO)==0 && this.getPercentage().compareTo(Env.ZERO)!=0) { //si se ingreso porcentaje

			this.isAmount = false;

			this.percentage = (this.getPercentage().divide(Env.ONEHUNDRED, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
		}

		//armo WHERE de filtros para productos a considerar (cumplen con los filtros indicados)
		if(this.getUY_Linea_Negocio_ID()>0){
			where += " and prod.uy_linea_negocio_id = " + this.getUY_Linea_Negocio_ID();
		}
		if(this.getUY_ProductGroup_ID()>0){
			where += " and prod.uy_productgroup_id = " + this.getUY_ProductGroup_ID();
		}
		if(this.getUY_Familia_ID()>0){
			where += " and prod.uy_familia_id = " + this.getUY_Familia_ID();
		}
		if(this.getUY_SubFamilia_ID()>0){
			where += " and prod.uy_subfamilia_id = " + this.getUY_SubFamilia_ID();
		}
		if(this.getM_Product_ID()>0){
			where += " and prod.m_product_id = " + this.getM_Product_ID();
		}
		if (this.getUPC() != null){
			if (!this.getUPC().trim().equalsIgnoreCase("")){
				where += " and prod.m_product_id in (select max(m_product_id) from uy_productupc where upc='" + this.getUPC() + "') ";
			}
		}

		this.loadLines(versionFrom,versionTo,where,this.getPricePrecision(),"Y");//cargo lineas a considerar

		/*
		//armo WHERE de filtros para productos a NO considerar (NO cumplen con los filtros indicados)
		if(hayFiltros){

			where = " and prod.m_product_id not in (select m_product_id from uy_priceupdateline where uy_priceupdate_id = " + this.get_ID() + ")";

			this.loadLines(versionFrom,where,this.getPricePrecision(),"N");//cargo lineas a NO considerar
		}
		*/
		
	}
	
	/***
	 * Carga las lineas del documento para productos a procesar o no, segun WHERE de filtros recibido.
	 * OpenUp Ltda. Issue #4420
	 * @author Nicolas Sarlabos - 17/06/2015
	 * @see
	 * @return
	 */
	private void loadLines(MPriceListVersion versionFrom, MPriceListVersion versionTo, String where, int round, String selected) {
		
		String sql = "", insert = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal priceActual = Env.ZERO, newPrice = Env.ZERO, diffAmt = Env.ZERO;

		try{
			
			sql = "select ppo.m_product_id,coalesce(ppd.pricelist,0) as pricelist,coalesce(ppd.dateaction,'" + this.getValidFrom() + "') as dateaction,ppo.isactive" +
                  " from m_productprice ppo" +
                  " inner join m_product prod on ppo.m_product_id = prod.m_product_id" +
                  " left outer join m_productprice ppd on (ppo.m_product_id = ppd.m_product_id and ppd.m_pricelist_version_id = " + versionTo.get_ID() + ")" +
                  " where ppo.m_pricelist_version_id = " + versionFrom.get_ID() + where;
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){		
				
				if(selected.equalsIgnoreCase("Y")){ //si se modifica precio

					priceActual = rs.getBigDecimal("PriceList").setScale(round, RoundingMode.HALF_UP);

					if(this.isAmount){

						newPrice = priceActual.add(this.getAmount());					

					} else {

						newPrice = priceActual.add(priceActual.multiply(this.percentage));					

					}

					diffAmt = newPrice.subtract(priceActual);

					insert = "INSERT INTO uy_priceupdateline (uy_priceupdateline_id,ad_client_id,ad_org_id,isactive,createdby,created,updated," +
							"updatedby,uy_priceupdate_id,m_product_id,priceactual,pricelist,differenceamt,dateaction,isselected)";

					sql = "select nextID(" + this.seqID + ",'N')," + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'" + rs.getString("IsActive") + 
							"'," + Env.getAD_User_ID(getCtx()) + ",now(),now()," + Env.getAD_User_ID(getCtx()) + "," + 
							this.get_ID() + "," + rs.getInt("M_Product_ID") + "," + priceActual.setScale(round, RoundingMode.HALF_UP) + "," + 
							newPrice.setScale(round, RoundingMode.HALF_UP) + "," + diffAmt.setScale(round, RoundingMode.HALF_UP) + ",'" + 
							rs.getTimestamp("DateAction") + "','" + selected + "'";

					DB.executeUpdateEx(insert + sql, get_TrxName());	

				} else { //si NO se modifica precio
					
					insert = "INSERT INTO uy_priceupdateline (uy_priceupdateline_id,ad_client_id,ad_org_id,isactive,createdby,created,updated," +
							"updatedby,uy_priceupdate_id,m_product_id,priceactual,pricelist,differenceamt,dateaction,isselected)";

					sql = "select nextID(" + this.seqID + ",'N')," + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'" + rs.getString("IsActive") + 
							"'," + Env.getAD_User_ID(getCtx()) + ",now(),now()," + Env.getAD_User_ID(getCtx()) + "," + 
							this.get_ID() + "," + rs.getInt("M_Product_ID") + "," + rs.getBigDecimal("PriceList").setScale(round, RoundingMode.HALF_UP) + "," + 
							rs.getBigDecimal("PriceList").setScale(round, RoundingMode.HALF_UP) + ",0,'" + rs.getTimestamp("DateAction") + "','" + selected + "'";

					DB.executeUpdateEx(insert + sql, get_TrxName());

				}
				
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

	/***
	 * Obtiene y retorna lineas del documento.
	 * OpenUp Ltda. Issue #4420
	 * @author Nicolas Sarlabos - 17/06/2015
	 * @see
	 * @return
	 */
	public List<MPriceUpdateLine> getLines(){

		String whereClause = X_UY_PriceUpdateLine.COLUMNNAME_UY_PriceUpdate_ID + "=" + this.get_ID();

		List<MPriceUpdateLine> lines = new Query(getCtx(), I_UY_PriceUpdateLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas del documento con precios modificados.
	 * OpenUp Ltda. Issue #4458
	 * @author Nicolas Sarlabos - 24/06/2015
	 * @see
	 * @return
	 */
	public List<MPriceUpdateLine> getLinesDifference(){

		String whereClause = X_UY_PriceUpdateLine.COLUMNNAME_UY_PriceUpdate_ID + "=" + this.get_ID() + 
				" and " + X_UY_PriceUpdateLine.COLUMNNAME_DifferenceAmt + " <> 0";

		List<MPriceUpdateLine> lines = new Query(getCtx(), I_UY_PriceUpdateLine.Table_Name, whereClause, get_TrxName())
		.setOrderBy(X_UY_PriceUpdateLine.COLUMNNAME_UY_PriceUpdateLine_ID)
		.list();

		return lines;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {

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

		MDocType doc = (MDocType)this.getC_DocType();
		if (doc.getValue() == null){
			this.processMsg = "No es pudo determinar el tipo de documento para esta transacción.";
			return DocAction.STATUS_Invalid;
		}
		
		/*
		// Valido que las versiones de las listas utilzadas en este proceso no hayan cambiado.
		boolean vigenciaOK = true;
		if (this.getM_PriceList_ID() > 0){
			if (MPriceListVersion.forPriceList(getCtx(), this.getM_PriceList_ID(), null).get_ID() != this.getM_PriceList_Version_ID()){
				vigenciaOK = false;
			}
		}
		if (this.getM_PriceList_ID_2() > 0){
			if (MPriceListVersion.forPriceList(getCtx(), this.getM_PriceList_ID_2(), null).get_ID() != this.getM_PriceList_Version_ID_2()){
				vigenciaOK = false;				
			}
		}
		if (!vigenciaOK){
			this.processMsg = "No es posible completar el documento ya que hay cambios en la vigencia de la Lista de Precios.";
			return DocAction.STATUS_Invalid;
		}
		*/
		
		// Obtengo lineas con actualizacion de precio
		List<MPriceUpdateLine> linesDiff = this.getLinesDifference();
		
		if ((linesDiff == null) || (linesDiff.size() <= 0)){
			this.processMsg = "No hay productos con precio actualizado para procesar.";
			return DocAction.STATUS_Invalid;
		}

		if (!(doc.getValue().equalsIgnoreCase("salespriceupdate")) && !(doc.getValue().equalsIgnoreCase("priceupdate"))){
			this.processMsg = "No se pudo determinar el tipo de documento para esta transacción.";
			return DocAction.STATUS_Invalid;
		}
		
		
		this.processSOPriceLists(doc,linesDiff);
		
//		
//		
//		// Lista destino y vigencia actual según documento 
//		MPriceList priceListDestino = null;
//		MPriceListVersion actualVersion = null;
//		if (doc.getValue().equalsIgnoreCase("salespriceupdate")){
//			priceListDestino = new MPriceList(getCtx(), this.getM_PriceList_ID(), null);
//			//actualVersion = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID(), get_TrxName());
//			int pLVId = MPriceListVersion.forPriceList(getCtx(), this.getM_PriceList_ID(), null).get_ID();
//			actualVersion = new MPriceListVersion(getCtx(), pLVId, get_TrxName());
//		}
//		else if (doc.getValue().equalsIgnoreCase("priceupdate")){
//			priceListDestino = new MPriceList(getCtx(), this.getM_PriceList_ID_2(), null);
//			int pLVId = MPriceListVersion.forPriceList(getCtx(), this.getM_PriceList_ID_2(), null).get_ID();
//			//actualVersion = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_2(), get_TrxName());
//			actualVersion = new MPriceListVersion(getCtx(), pLVId, get_TrxName());
//		}
//		else{
//			this.processMsg = "No se pudo determinar el tipo de documento para esta transacción.";
//			return DocAction.STATUS_Invalid;
//		}
//
//		// Proceso actualizacion de lista de precios de venta
//		MClient cte = new MClient(getCtx(), this.getAD_Client_ID(), null);
//		//SBT 28/01/2016 Issue @5386 Se debe contemplar este tipo de cliente porque opera con mas de una lista de venta
//		if(cte.getName().equals("Planeta")){
//			
//			// Obtemgo lines con diferencia de precio que están seleccionadas con precio diferencial
//			List<MPriceLoadLine> linesCheck = this.getLinesDifferenceChecked();
//			// Si no tengo lineas que hayan sufrido cambios no haga nada
//			if (linesDiff.size() <= 0 && linesCheck.size() <= 0) return;
//			
//			int[] sucursales = null;
//			//si hay diferencia entonces tengo linesa sin chequear corresponde actualizar en 
//			//cada lista de precio de venta de cada sucursal activa
//			if(linesDiff.size()>linesCheck.size()){
//				sucursales = MPriceLoadLineOrg.getOrgs(this.get_ID(),get_TrxName());
//			}else{
//			//si es la misma cantidad entonces tengo que obtener las org que participan 
//			//para no crear versiones de lista que no corresponden	
//				sucursales = MPriceLoadLineOrg.getOrgsLoad(this.get_ID(),get_TrxName());
//			}
//			
//			//Recorro las sucursales y proceso
//			if(null!=sucursales){
//				for(int i = 0;i<sucursales.length;i++){
//				//	procesarPorSucursal(sucursales[i],lines,multiOrg);
//					
//					priceListDestino = MPriceList.getPricListForOrg(getCtx(),sucursales[i],this.getC_Currency_ID(),null);
//					 MOrg org = new MOrg(getCtx(), sucursales[i], null);
//						if(0>=priceListDestino.get_ID()) 
//							throw new AdempiereException("No existe lista de venta para la organización "+ org.getName());
//					
//					
//					//priceListDestino = new MPriceList(getCtx(), this.getM_PriceList_ID_2(), null);
//					int pLVId = MPriceListVersion.forPriceList(getCtx(), priceListDestino.get_ID(), null).get_ID();
//					//actualVersion = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_2(), get_TrxName());
//					actualVersion = new MPriceListVersion(getCtx(), pLVId, get_TrxName());
//					
//					
//					
//					this.procesarPorSucursal(linesDiff,priceListDestino,actualVersion,true);
//				}
//			}
//			
//			
//		}else{
//			this.procesarPorSucursal(linesDiff,priceListDestino,actualVersion,false);//falta testear 28/01/2016 - SBT
//			//this.processSOPriceList();
//		}
//		
		
//		// Creo nueva version de lista destino
//		Date deliveryDate = (Date)this.getDateTrx();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String newVersionName = priceListDestino.getName() + "_" + sdf.format(deliveryDate);
//		
//		MPriceListVersion newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName());
//		newVersion.setM_PriceList_ID(priceListDestino.get_ID());
//		newVersion.setName(newVersionName);
//		newVersion.setM_DiscountSchema_ID(actualVersion.getM_DiscountSchema_ID());
//		newVersion.setValidFrom(this.getValidFrom());
//		newVersion.setIsActive(true);
//		newVersion.saveEx();
//
//		// Clono la version actual en nueva version
//		String action = " INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
//				        " updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";
//
//		String sql = " select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + 
//				     ",pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction" +
//				     " from m_productprice" +
//				     " where m_pricelist_version_id = " + actualVersion.get_ID();
//
//		DB.executeUpdateEx(action + sql, get_TrxName());			
//		
//		// Recorro lineas de productos con actualizacion de precio
//		for(MPriceUpdateLine line : linesDiff){
//			
//			String active = "Y";
//			Timestamp dateAction = line.getDateAction(); 
//			
//			if(!line.isActive()) active = "N";
//				
//			dateAction = this.getValidFrom();					
//				
//			MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), newVersion.get_ID(), line.getM_Product_ID(), get_TrxName());
//			
//			// Si este producto ya existe en la nueva version
//			if (pprice != null){  
//				
//				action = "update m_productprice set isactive = '" + active + "',pricelist = " + line.getPriceList() + ",pricestd = " + line.getPriceList() +
//						",pricelimit = " + line.getPriceList() + ",dateaction = '" + dateAction + "'" +
//						" where m_pricelist_version_id = " + newVersion.get_ID() + " and m_product_id = " + line.getM_Product_ID();
//			} 
//			else { 
//				
//				// Este producto no existe, creo nuevo registro
//				action = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
//						"updatedby,pricelist,pricestd,pricelimit,dateaction) ";
//
//				action += " select " + newVersion.get_ID() + "," + line.getM_Product_ID() + "," + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'" + 
//				       active + "',now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + "," + line.getPriceList() + 
//				       "," + line.getPriceList() + "," + line.getPriceList() + ",'" + dateAction + "'";					
//				
//			}					
//				
//			DB.executeUpdateEx(action, get_TrxName());
//			
//		}			
//		
//		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
//		Timestamp validFrom = TimeUtil.trunc(this.getValidFrom(), TimeUtil.TRUNC_DAY);
//		
//		// Si la fecha de vigencia es HOY entonces desactivo las versiones anteriores
//		if(validFrom.compareTo(today)==0) 
//			DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id <> " + newVersion.get_ID() +
//					" and m_pricelist_id = " + newVersion.getM_PriceList_ID()  + " and validfrom <= '" + today + "'", get_TrxName());
//
//		// Impacto en tabla de interfase cuando actuliza lista de venta
//		if (priceListDestino.isSOPriceList()){
//
//			MRTInterfaceProd.insertInterfaceFromPriceList(linesDiff, priceListDestino.getC_Currency_ID(), getCtx(), get_TrxName());
//			
//			// Marco precio cambiado en producto para impresion de etiquetas de precio
//			for (MPriceUpdateLine line: linesDiff){
//
//				MProductPrintLabel plabel = MProductPrintLabel.forProduct(getCtx(), line.getM_Product_ID(), get_TrxName());
//				
//				if ((plabel == null) || (plabel.get_ID() <= 0)){
//					plabel = new MProductPrintLabel(getCtx(), 0, get_TrxName());
//					plabel.setM_Product_ID(line.getM_Product_ID());
//					plabel.saveEx();
//				}
//				//OpenUp SBT 21/12/2015 Issue #5153 Interfaceo producto
//				//COMENTADO TEMPORALMENTE HASTA PONER EN PRODUCCION
//				//MRTRetailInterface.actualizaPrecioArticulo(getCtx(),line.getAD_Client_ID() , line.getM_Product_ID(), get_TrxName());
//			}
//		}

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

	/**
	 * 
	 * @author OpenUp SBT Issue#  29/1/2016 16:29:44
	 * @param doc
	 * @param linesDiff
	 */
	private void processSOPriceLists(MDocType doc,List<MPriceUpdateLine> linesDiff) {
		boolean listaDestinoVenta = true;
		// Lista destino y vigencia actual según documento 
		MPriceList priceListDestino = null;
		MPriceListVersion actualVersion = null;
		if (doc.getValue().equalsIgnoreCase("salespriceupdate")){
			priceListDestino = new MPriceList(getCtx(), this.getM_PriceList_ID(), null);
			int pLVId = MPriceListVersion.forPriceList(getCtx(), this.getM_PriceList_ID(), null).get_ID();
			actualVersion = new MPriceListVersion(getCtx(), pLVId, get_TrxName());
		}
		else if (doc.getValue().equalsIgnoreCase("priceupdate")){
			priceListDestino = new MPriceList(getCtx(), this.getM_PriceList_ID_2(), null);
			int pLVId = MPriceListVersion.forPriceList(getCtx(), this.getM_PriceList_ID_2(), null).get_ID();
			actualVersion = new MPriceListVersion(getCtx(), pLVId, get_TrxName());
			if (!priceListDestino.isSOPriceList()){ //Si la lista de destino no es de venta no se deben actualizar las listas de ventas del resto ni enviar datos a 
				listaDestinoVenta = false;
			}
		}
		
		MClient cte = new MClient(getCtx(), this.getAD_Client_ID(), null);
		
		if(cte.getName().equals("Planeta")){
			
			if(listaDestinoVenta){//Si la lista de destino es de venta
				// Obtemgo lines con diferencia de precio que están seleccionadas con precio diferencial
				List<MPriceUpdateLine> linesCheck = this.getLinesDifferenceChecked();
				// Si no tengo lineas que hayan sufrido cambios no haga nada
				if (linesDiff.size() <= 0 && linesCheck.size() <= 0) return;
				
				int[] sucursales = null;
				//si hay diferencia entonces tengo linesa sin chequear corresponde actualizar en 
				//cada lista de precio de venta de cada sucursal activa
				if(linesDiff.size()>linesCheck.size()){
					sucursales = MPriceUpdateLineOrg.getOrgs(get_TrxName());
				}else{
				//si es la misma cantidad entonces tengo que obtener las org que participan 
				//para no crear versiones de lista que no corresponden	
					sucursales = MPriceUpdateLineOrg.getOrgsUpdate(this.get_ID(),get_TrxName());
				}
				
				//Recorro las sucursales y proceso
				if(null!=sucursales){
					for(int i = 0;i<sucursales.length;i++){
					//	procesarPorSucursal(sucursales[i],lines,multiOrg);
						priceListDestino = MPriceList.getPricListForOrg(getCtx(),sucursales[i],this.getC_Currency_ID(),null);
						MOrg org = new MOrg(getCtx(), sucursales[i], null);
						if(0>=priceListDestino.get_ID()) {
							throw new AdempiereException("No existe lista de venta para la organización "+ org.getName());
						}
						int pLVId = MPriceListVersion.forPriceList(getCtx(), priceListDestino.get_ID(), null).get_ID();
						if(!(pLVId>0)){
							throw new AdempiereException("No existe version vigente para la lista "+ priceListDestino.getName());
						}
						actualVersion = new MPriceListVersion(getCtx(), pLVId, get_TrxName());	
						
						// Proceso actualizacion de lista de precios de venta
						this.procesarPorSucursal(linesDiff,priceListDestino,actualVersion,sucursales[i],true,listaDestinoVenta);
					}
				}
			}else{//Si la lista de destino no es de venta, solo corresponde clonar y nada mas # 5386 SBT 02/02/2016
				this.procesarPorSucursal(linesDiff,priceListDestino,actualVersion,this.getAD_Org_ID(),false,listaDestinoVenta);//falta testear 02/02/2016 - SBT
			}
				
		}else{
			// Proceso actualizacion de lista de precios de venta
			this.procesarPorSucursal(linesDiff,priceListDestino,actualVersion,this.getAD_Org_ID(),false,listaDestinoVenta);
		}
		
	}

	/**Obtiene y retorna lineas del documento con precios modificados y seleccionados para precio diferencial.
	 * 
	 * @author OpenUp SBT Issue #5392  29/1/2016 16:35:22
	 * @return
	 */
	private List<MPriceUpdateLine> getLinesDifferenceChecked() {
		String whereClause = X_UY_PriceUpdateLine.COLUMNNAME_UY_PriceUpdate_ID + "=" + this.get_ID() + 
				" AND " + X_UY_PriceUpdateLine.COLUMNNAME_DifferenceAmt + " <> 0 " +
				" AND "	+ X_UY_PriceUpdateLine.COLUMNNAME_IsSelected2 +" = 'Y' ";

		List<MPriceUpdateLine> lines = new Query(getCtx(), I_UY_PriceUpdateLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	private void procesarPorSucursal(List<MPriceUpdateLine> linesDiff,
			MPriceList priceListDestino,MPriceListVersion actualVersion,int adOrgID,boolean multiOrg, boolean listaDestinoVenta) {
		
		// Creo nueva version de lista destino
		Date deliveryDate = (Date)this.getDateTrx();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newVersionName = priceListDestino.getName() + "_" + sdf.format(deliveryDate);
		
		MPriceListVersion newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName());
		newVersion.setM_PriceList_ID(priceListDestino.get_ID());
		newVersion.setName(newVersionName);
		newVersion.setM_DiscountSchema_ID(actualVersion.getM_DiscountSchema_ID());
		newVersion.setValidFrom(this.getValidFrom());
		newVersion.setIsActive(true);
		newVersion.saveEx();

		// Clono la version actual en nueva version
		String action = " INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
				        " updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";

		String sql = " select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + 
				     ",pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction" +
				     " from m_productprice" +
				     " where m_pricelist_version_id = " + actualVersion.get_ID();

		DB.executeUpdateEx(action + sql, get_TrxName());			
		
		// Recorro lineas de productos con actualizacion de precio
		for(MPriceUpdateLine line : linesDiff){
			
			//Controlo para los casos en que es multi organización si corresponde agregar o no
			if(multiOrg && line.isSelected2() && listaDestinoVenta){
				MPriceUpdateLineOrg lineOrg = MPriceUpdateLineOrg.forUpdateLineAndOrg(getCtx(),line.get_ID()
						,adOrgID,get_TrxName());
				if (null==lineOrg) continue;
			}
			
			
			String active = "Y";
			Timestamp dateAction = line.getDateAction(); 
			
			if(!line.isActive()) active = "N";
				
			dateAction = this.getValidFrom();					
				
			MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), newVersion.get_ID(), line.getM_Product_ID(), get_TrxName());
			
			// Si este producto ya existe en la nueva version
			if (pprice != null){  
				
				action = "update m_productprice set isactive = '" + active + "',pricelist = " + line.getPriceList() + ",pricestd = " + line.getPriceList() +
						",pricelimit = " + line.getPriceList() + ",dateaction = '" + dateAction + "'" +
						" where m_pricelist_version_id = " + newVersion.get_ID() + " and m_product_id = " + line.getM_Product_ID();
			} 
			else { 
				
				// Este producto no existe, creo nuevo registro
				action = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
						"updatedby,pricelist,pricestd,pricelimit,dateaction) ";

				action += " select " + newVersion.get_ID() + "," + line.getM_Product_ID() + "," + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'" + 
				       active + "',now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + "," + line.getPriceList() + 
				       "," + line.getPriceList() + "," + line.getPriceList() + ",'" + dateAction + "'";				
			}					
				
			DB.executeUpdateEx(action, get_TrxName());
			
			if(priceListDestino.isSOPriceList()){//Si la lista de precio de destino es de venta corresponde impactar en el sistema de cajas
				MProduct prod = (MProduct)line.getM_Product();
				if(!prod.isSold()) continue;
				if(!line.getPriceList().equals(Env.ZERO)){

				// Impacto en tabla de interfase
				MRTRetailInterface.actualizaPrecioArticulo(getCtx(),line.getAD_Client_ID() , line.getM_Product_ID(),
						priceListDestino.getC_Currency_ID(),line.getPriceList(),adOrgID,get_TrxName());

				// Marco precio cambiado en producto para impresion de etiquetas de precio
				MProductPrintLabel plabel = MProductPrintLabel.forProduct(getCtx(), prod.get_ID(), get_TrxName());
				if ((plabel == null) || (plabel.get_ID() <= 0)){
					plabel = new MProductPrintLabel(getCtx(), 0, get_TrxName());
					plabel.setM_Product_ID(prod.get_ID());
					plabel.set_ValueOfColumn("C_Currency_ID", priceListDestino.getC_Currency_ID());//SBT 08/04/2016 Issue #5733
					plabel.saveEx();
				}
				//Impacto si corresponde en tabla de interfaceo para Balanzas - SBT 18-02-2016 Issue #5351
				if(prod.get_Value("UsaBalanza")!=null){
					if(prod.get_ValueAsBoolean("UsaBalanza")){
						//OpenUp SBT 04/04/2016 se evia el codigo interno ya que es el unico que corresponde enviar a las balanzas
						//MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), "0", prod.get_ID(), get_TrxName());
						MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), prod.getValue(), prod.get_ID(), get_TrxName());
					}	
				}
			}
					
		}			
		}
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		Timestamp validFrom = TimeUtil.trunc(this.getValidFrom(), TimeUtil.TRUNC_DAY);
		
		// Si la fecha de vigencia es HOY entonces desactivo las versiones anteriores 
		if(!validFrom.after(today) && validFrom.compareTo(actualVersion.getValidFrom())>=0){ //CORRESPONDE EN LOS CASOS QUE LA FECHA ES ANTERIOR O IGUAL VERDAD?????
			
			DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id <> " + newVersion.get_ID() +
					" and m_pricelist_id = " + newVersion.getM_PriceList_ID()  + " and isactive = 'Y'", get_TrxName());
			
			if(newVersion.getM_PriceList().isSOPriceList()){
				String qry = "update C_BPartner_Product b"+
									" set priceSOList = a.pricelist,"+
									" validFrom2 = a.dateaction, "+
									" m_pricelist_version_id_2 = a.m_pricelist_version_id,"+
									" margin = case when (b.priceCostFinal = 0 or b.priceCostFinal is null) " +
									 " then 0 else round((a.pricelist*100/b.priceCostFinal)-100, 2) end"+
								" from m_productprice a"+
								" where b.m_product_id = a.m_product_id "+
								" and a.m_pricelist_version_id = " + newVersion.get_ID();
				DB.executeUpdateEx(qry, get_TrxName());
			}
		}
			
	}

	/***
	 * Crea nueva version de la lista de precios.
	 * OpenUp Ltda. Issue #4420
	 * @author Nicolas Sarlabos - 17/06/2015
	 * @see
	 * @return
	 */
	@SuppressWarnings("unused")
	private void createVersion() {
		
		String sql = "", insert = "";
		boolean isDistinct = false; //indica si las listas origen y destino son distintas o no
		MPriceListVersion oldVersion = null, newVersion = null;
		
		try{
			
			// Obtengo lineas con diferencia de precio en lista destino
			List<MPriceUpdateLine> lines = this.getLinesDifference();
			
			// Si no tengo lineas que hayan sufrido cambios no haga nada
			if (lines.size() <= 0) return;
			
			MPriceList lista = new MPriceList(getCtx(), this.getM_PriceList_ID_2(), null);
			
			Date deliveryDate = (Date)this.getDateTrx();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
			String nombreVersion = lista.getName() + "_" + sdf.format(deliveryDate);
			
			if(this.getM_PriceList_ID()==this.getM_PriceList_ID_2()){ //si la lista origen y destino es la misma
				
				oldVersion = new MPriceListVersion(getCtx(),this.getM_PriceList_Version_ID(),get_TrxName());//instancio version origen anterior guardada 
				
				newVersion = new MPriceListVersion(getCtx(),0,get_TrxName()); //creo nueva version de lista
				newVersion.setM_PriceList_ID(this.getM_PriceList_ID());
				newVersion.setName(nombreVersion);
				newVersion.setM_DiscountSchema_ID(oldVersion.getM_DiscountSchema_ID());
				newVersion.setValidFrom(this.getValidFrom());
				newVersion.setIsActive(true);
				newVersion.saveEx();				
				
			} else { //si las listas origen y destino son distintas
				
				isDistinct = true;
				
				oldVersion = new MPriceListVersion(getCtx(),this.getM_PriceList_Version_ID_2(),get_TrxName());//instancio version destino anterior guardada
				
				newVersion = new MPriceListVersion(getCtx(),0,get_TrxName()); //creo nueva version de lista
				newVersion.setM_PriceList_ID(this.getM_PriceList_ID_2());
				newVersion.setName(nombreVersion);
				newVersion.setM_DiscountSchema_ID(oldVersion.getM_DiscountSchema_ID());
				newVersion.setValidFrom(this.getValidFrom());
				newVersion.setIsActive(true);
				newVersion.saveEx();				

			}

			//clono la version
			insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
					"updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";

			sql = "select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + 
					",pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction" +
					" from m_productprice" +
					" where m_pricelist_version_id = " + oldVersion.get_ID();

			DB.executeUpdateEx(insert + sql, get_TrxName());			
			
			//recorro lineas de documento
			for(MPriceUpdateLine line : lines){
				
				String active = "Y";
				Timestamp dateAction = line.getDateAction(); 
				
				if(!line.isActive()) active = "N";
				
				//si se modifico el precio entonces seteo fecha de vigencia indicada en el cabezal para este producto
				if(line.getDifferenceAmt().compareTo(Env.ZERO)!=0) {
					
					dateAction = this.getValidFrom();					
					
				}
				
				if(isDistinct){ //si las listas origen y destino son distintas					
					
					//verifico si existe en producto en la lista
					MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), newVersion.get_ID(), line.getM_Product_ID(), get_TrxName());
					
					if(pprice!=null){ //si el producto existe lo actualizo 
						
						sql = "update m_productprice set isactive = '" + active + "',pricelist = " + line.getPriceList() + ",pricestd = " + line.getPriceList() +
								",pricelimit = " + line.getPriceList() + ",dateaction = '" + dateAction + "'" +
								" where m_pricelist_version_id = " + newVersion.get_ID() + " and m_product_id = " + line.getM_Product_ID();
						
						DB.executeUpdateEx(sql, get_TrxName());							
						
					} else { //si el producto no existe, creo nuevo registro
						
						insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
								"updatedby,pricelist,pricestd,pricelimit,dateaction)";

						sql = "select " + newVersion.get_ID() + "," + line.getM_Product_ID() + "," + line.getAD_Client_ID() + "," + line.getAD_Org_ID() + ",'" + 
						       active + "',now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + "," + line.getPriceList() + 
						       "," + line.getPriceList() + "," + line.getPriceList() + ",'" + dateAction + "'";					

						DB.executeUpdateEx(insert + sql, get_TrxName());							
						
					}					
					
				} else { //si las listas origen y destino son iguales
					
					sql = "update m_productprice set isactive = '" + active + "',pricelist = " + line.getPriceList() + ",pricestd = " + line.getPriceList() +
							",pricelimit = " + line.getPriceList() + ",dateaction = '" + dateAction + "'" +
							" where m_pricelist_version_id = " + newVersion.get_ID() + " and m_product_id = " + line.getM_Product_ID();
					
					DB.executeUpdateEx(sql, get_TrxName());							
				}				
				
			}			
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			Timestamp validFrom = TimeUtil.trunc(this.getValidFrom(), TimeUtil.TRUNC_DAY);
			
			//si la fecha de vigencia es HOY entonces desactivo las versiones anteriores
			if(!validFrom.after(today) && validFrom.compareTo(oldVersion.getValidFrom())>=0){
				
				DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id <> " + newVersion.get_ID() +
						" and m_pricelist_id = " + newVersion.getM_PriceList_ID()  + " and isactive = 'Y'", get_TrxName());
				
				if(newVersion.getM_PriceList().isSOPriceList()){
					String qry = "update C_BPartner_Product b"+
										" set priceSOList = a.pricelist,"+
										" validFrom2 = a.dateaction, "+
										" m_pricelist_version_id_2 = a.m_pricelist_version_id,"+
										" margin = case when (b.priceCostFinal = 0 or b.priceCostFinal is null) " +
										 " then 0 else round((a.pricelist*100/b.priceCostFinal)-100, 2) end"+
									" from m_productprice a"+
									" where b.m_product_id = a.m_product_id "+
									" and a.m_pricelist_version_id = " + newVersion.get_ID();
					DB.executeUpdateEx(qry, get_TrxName());
				}
			}
				
		
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}

	}
	
	

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}

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
		return this.processMsg;
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
	
	/***
	 * Crea nueva version de la lista de precios en base a la version vigente.
	 * OpenUp Ltda. Issue #5130
	 * @author Emiliano Bentancor - 30/11/2015
	 * @see
	 * @return
	 */
	@SuppressWarnings("unused")
	private void createPLVersion() {
		
		String sql = "", insert = "";
//		boolean isDistinct = false; //indica si las listas origen y destino son distintas o no
		MPriceListVersion oldVersion = null, newVersion = null;
		
		try{
			
			// Obtengo lineas con diferencia de precio en lista destino
			List<MPriceUpdateLine> lines = this.getLinesDifference();
			// Si no tengo lineas que hayan sufrido cambios no haga nada
			if (lines.size() <= 0) return;
			MPriceList lista = null;
			if(this.getC_DocType_ID() == MDocType.forValue(getCtx(), "salespriceupdate", null).get_ID()){
				lista = new MPriceList(getCtx(), this.getM_PriceList_ID(), null);				
			}else{
				lista = new MPriceList(getCtx(), this.getM_PriceList_ID_2(), null);
			}
			int vVigente = 0;
			Date deliveryDate = (Date)this.getDateTrx();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
			String nombreVersion = lista.getName() + "_" + sdf.format(deliveryDate);
			
			if(null != MPriceListVersion.forPriceList(getCtx(), lista.get_ID(), null)){
				vVigente = MPriceListVersion.forPriceList(getCtx(), lista.get_ID(), null).get_ID();
			}else{
				throw new AdempiereException("No se encuentra version vigente para la lista "+lista.get_ID());
			}
			
			oldVersion = new MPriceListVersion(getCtx(),vVigente,get_TrxName());//instancio version origen anterior guardada 
			
			newVersion = new MPriceListVersion(getCtx(),0,get_TrxName()); //creo nueva version de lista
			newVersion.setM_PriceList_ID(this.getM_PriceList_ID());
			newVersion.setName(nombreVersion);
			newVersion.setM_DiscountSchema_ID(oldVersion.getM_DiscountSchema_ID());
			newVersion.setValidFrom(this.getValidFrom());
			newVersion.setIsActive(true);
			newVersion.saveEx();

			//clono la version
			insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
					"updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";

			sql = " select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + 
					",pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction" +
					" from m_productprice" +
					" where m_pricelist_version_id = " + oldVersion.get_ID();

			DB.executeUpdateEx(insert + sql, get_TrxName());			
			
			//recorro lineas de documento
			for(MPriceUpdateLine line : lines){
				
				String active = "Y";
				Timestamp dateAction = line.getDateAction(); 
				
				if(!line.isActive()) active = "N";
					
				dateAction = this.getValidFrom();					
					
				MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), newVersion.get_ID(), line.getM_Product_ID(), get_TrxName());
				
				if(pprice!=null){ //si el producto existe lo actualizo 
					
					sql = "update m_productprice set isactive = '" + active + "',pricelist = " + line.getPriceList() + ",pricestd = " + line.getPriceList() +
							",pricelimit = " + line.getPriceList() + ",dateaction = '" + dateAction + "'" +
							" where m_pricelist_version_id = " + newVersion.get_ID() + " and m_product_id = " + line.getM_Product_ID();
					
					DB.executeUpdateEx(sql, get_TrxName());							
					
				} else { //si el producto no existe, creo nuevo registro
					
					insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
							"updatedby,pricelist,pricestd,pricelimit,dateaction)";

					sql = "select " + newVersion.get_ID() + "," + line.getM_Product_ID() + "," + line.getAD_Client_ID() + "," + line.getAD_Org_ID() + ",'" + 
					       active + "',now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + "," + line.getPriceList() + 
					       "," + line.getPriceList() + "," + line.getPriceList() + ",'" + dateAction + "'";					

					DB.executeUpdateEx(insert + sql, get_TrxName());							
					
				}					
									
				
			}			
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			Timestamp validFrom = TimeUtil.trunc(this.getValidFrom(), TimeUtil.TRUNC_DAY);
			
			//si la fecha de vigencia es HOY entonces desactivo las versiones anteriores
			if(!validFrom.after(today) && validFrom.compareTo(oldVersion.getValidFrom())>=0){
				DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id <> " + newVersion.get_ID() +
						" and m_pricelist_id = " + newVersion.getM_PriceList_ID()  + " and isactive = 'Y'", get_TrxName());
				
				if(newVersion.getM_PriceList().isSOPriceList()){
					String qry = "update C_BPartner_Product b"+
										" set priceSOList = a.pricelist,"+
										" validFrom2 = a.dateaction, "+
										" m_pricelist_version_id_2 = a.m_pricelist_version_id,"+
										" margin = case when (b.priceCostFinal = 0 or b.priceCostFinal is null) " +
										 " then 0 else round((a.pricelist*100/b.priceCostFinal)-100, 2) end"+
									" from m_productprice a"+
									" where b.m_product_id = a.m_product_id "+
									" and a.m_pricelist_version_id = " + newVersion.get_ID();
					DB.executeUpdateEx(qry, get_TrxName());
				}
			}
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}

	}

}
