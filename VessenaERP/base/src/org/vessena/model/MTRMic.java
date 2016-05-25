/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCountry;
import org.compiere.model.MDocType;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRMic extends X_UY_TR_Mic implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;
	private static boolean enviandoAduana = false;
	
	public static boolean isEnviandoAduana() {
		return MTRMic.enviandoAduana;
	}

	public static void setEnviandoAduana(boolean enviandoAduana) {
		MTRMic.enviandoAduana = enviandoAduana;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383381912716235435L;

	/**
	 * @param ctx
	 * @param UY_TR_Mic_ID
	 * @param trxName
	 */
	public MTRMic(Properties ctx, int UY_TR_Mic_ID, String trxName) {
		super(ctx, UY_TR_Mic_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMic(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
	public boolean applyIt() {
		this.loadData();
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.isRemolque() && this.isSemiRemolque()) throw new AdempiereException("No se puede indicar que un vehiculo es remolque y semirremolque a la vez");
		if(this.isRemolque2() && this.isSemiRemolque2()) throw new AdempiereException("No se puede indicar que un vehiculo es remolque y semirremolque a la vez");	
		
		/*if(is_ValueChanged("DateTrx") || is_ValueChanged("DateFinish")){

			if(this.getDateTrx()!=null && this.getDateFinish()!=null){
				
				int days = OpenUpUtils.diffDays(this.getDateFinish(), this.getDateTrx());
		
				if(days >=0) this.setDiference(days);		

			}
		}*/
		

		if(!this.isManual()){

			if(MSysConfig.getBooleanValue("UY_TR_CONTROL_MIC_NUMBER", false, this.getAD_Client_ID())) {

				if(this.getNumero()!=null && !this.getNumero().equalsIgnoreCase("")){

					if(this.getNumero().length()<11 || this.getNumero().length()>11) throw new AdempiereException("El numero de MIC/DTA debe tener 11 caracteres");

				}					
			}				
		}		
		
		if (!MTRMic.enviandoAduana) {
			
			if(!newRecord){
				
				if(this.getMicStatus().equalsIgnoreCase("ENVIADO") || this.getMicStatus().equalsIgnoreCase("MODIFICAR")){
					
					this.setMicStatus("MODIFICAR");
					this.setCrtStatus1("ENMODIFICACION");
				}
				
			}
			
		}
		
		// Alimento el campo Mic/DNA de la orden de transporte asociada
		if ((!newRecord) && is_ValueChanged(COLUMNNAME_NroMic)){
			if (this.getNroMic() != null){
				if (this.getUY_TR_TransOrder_ID() > 0){
					DB.executeUpdateEx(" update uy_tr_transorder set micdnano ='" + this.getNroMic() + "' " +
									   " where uy_tr_transorder_id =" + this.getUY_TR_TransOrder_ID(), get_TrxName());
				}
			}
		}

		//cargo datos para envio a aduana
		if(newRecord || is_ValueChanged("UY_TR_TransOrder_ID") || is_ValueChanged("UY_TR_TransOrder_ID_1") || is_ValueChanged("UY_TR_Border_ID_1") || is_ValueChanged("UY_TR_Border_ID_2")){

			MTRTransOrder order = null;
			MCiudad cityFrom = null;
			MCiudad cityTo = null;
			String sql = "";
			int aduanaID = 0;
			int stateID = 0;

			MOrgInfo info = MOrgInfo.get(getCtx(), this.getAD_Org_ID(), get_TrxName()); //instancio info de la organizacion
			MLocation loc = (MLocation)info.getC_Location();

			if(!this.isLastre()){

				order = (MTRTransOrder)this.getUY_TR_TransOrder();

			} else order = new MTRTransOrder(getCtx(),this.getUY_TR_TransOrder_ID_1(),get_TrxName());
			
			if(order!=null && order.get_ID()>0 && order.getUY_TR_Driver_ID()>0) this.setUY_TR_Driver_ID(order.getUY_TR_Driver_ID());//seteo chofer	

			cityFrom = new MCiudad(getCtx(),order.getUY_Ciudad_ID(),get_TrxName());
			cityTo = new MCiudad(getCtx(),order.getUY_Ciudad_ID_1(),get_TrxName());

			if(loc.getC_Country_ID()>0){

				if(loc.getC_Country_ID()==cityFrom.getC_Country_ID() && loc.getC_Country_ID()!=cityTo.getC_Country_ID()){

					this.setType("1");					

				} else if(loc.getC_Country_ID()!=cityFrom.getC_Country_ID() && loc.getC_Country_ID()==cityTo.getC_Country_ID()){

					this.setType("0");					

				} else if(loc.getC_Country_ID()==cityFrom.getC_Country_ID() && loc.getC_Country_ID()==cityTo.getC_Country_ID()) this.setType("2");			

			}

			//seteo paises de origen y destino
			this.setC_Country_ID(cityFrom.getC_Country_ID());
			this.setC_Country_ID_1(cityTo.getC_Country_ID());

			//seteo lugar de partida y estado
			this.setUY_Ciudad_ID_2(cityFrom.get_ID());

			sql = "select coalesce(d.uy_departamentos_id,0)" +
					" from uy_departamentos d" +
					" inner join uy_localidades l on d.uy_departamentos_id = l.uy_departamentos_id" +
					" where lower (l.name) like '" + cityFrom.getName().toLowerCase() + "'";

			stateID = DB.getSQLValueEx(null, sql);

			if(stateID > 0) this.setUY_Departamentos_ID(stateID);

			//seteo lugar de destino y estado
			this.setUY_Ciudad_ID_3(cityTo.get_ID());

			sql = "select coalesce(d.uy_departamentos_id,0)" +
					" from uy_departamentos d" +
					" inner join uy_localidades l on d.uy_departamentos_id = l.uy_departamentos_id" +
					" where lower (l.name) like '" + cityTo.getName().toLowerCase() + "'";

			stateID = DB.getSQLValueEx(null, sql);

			if(stateID > 0) this.setUY_Departamentos_ID_1(stateID);		

			//seteo aduana de ingreso o salida
			if(this.getType()!=null && !this.getType().equalsIgnoreCase("")){

				if(this.getType().equalsIgnoreCase("0")){ //si es INGRESO

					if(this.getUY_TR_Border_ID_2() > 0){

						MTRBorder border = new MTRBorder(getCtx(),this.getUY_TR_Border_ID_2(),get_TrxName());

						sql = "select uy_tr_aduana_id"+
								" from uy_tr_aduana" +
								" where lower (name) like '" + border.getName().toLowerCase() + "'";
						aduanaID = DB.getSQLValueEx(get_TrxName(), sql);

						if(aduanaID > 0) this.setUY_TR_Aduana_ID(aduanaID);					
					}				

				} else if(this.getType().equalsIgnoreCase("1")){ //si es EGRESO

					if(this.getUY_TR_Border_ID_1() > 0){

						MTRBorder border = new MTRBorder(getCtx(),this.getUY_TR_Border_ID_1(),get_TrxName());

						sql = "select uy_tr_aduana_id"+
								" from uy_tr_aduana" +
								" where lower (name) like '" + border.getName().toLowerCase() + "'";
						aduanaID = DB.getSQLValueEx(get_TrxName(), sql);

						if(aduanaID > 0) this.setUY_TR_Aduana_ID_1(aduanaID);					
					}

				} 
			}				
		}
		
		//si el estado del MIC es distinto a borrador y se modifica tractor o remolque, se deben actualizar los datos
		if(!this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Drafted)){
			
			if(is_ValueChanged("Tractor_ID") || is_ValueChanged("UY_TR_Driver_ID")){ //refresco datos de tractor y conductor
				
				if(this.getTractor_ID()>0) this.loadTruckData(true,false);				
			}
			
			if(is_ValueChanged("Remolque_ID")){ //refresco datos de remolque
				
				if(this.getRemolque_ID()>0) this.loadTruckData(false,true);				
				
			}		
			
		}

		return true;
	}

	@Override
	protected boolean beforeDelete() {

		// No permito eliminar un registro en estado aplicado.
		if (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Applied) || this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed) 
				|| this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_InProgress)){
			log.saveError(null, "No es posible borrar un MIC en estado diferente a Borrador");
			return false;
		}

		if(this.getUY_TR_Crt_ID() > 0){

			if(this.getCrtStatus1()!=null){

				if(this.getCrtStatus1().equalsIgnoreCase("ENBAJA") || this.getCrtStatus1().equalsIgnoreCase("ENMODIFICACION")
						|| this.getCrtStatus1().equalsIgnoreCase("VINCULADO")) throw new AdempiereException ("Imposible borrar el documento, primero debe dar de baja los conocimientos");
						
			}		
			
		}		
		
		return true;
	}

	/***
	 * OpenUp. Nicolas Sarlabos. 31/03/2014. ISSUE#1629
	 * Metodo que carga la grilla de expedientes y CRT segun los filtros elegidos.
	 * 
	 * */
	private void loadData() {

		String sql = "", porteador = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		MTRTransOrder order = null;
		MTRTransOrderLine oLine = null;
		boolean isHdr = true;
		int orgID = 0;
		int orgActualID = 0;
		MOrg org = null;
		MOrgInfo info = null;
		MTRTrip tripAux = null;

		try{

			DB.executeUpdateEx("delete from uy_tr_micline where uy_tr_mic_id = " + this.get_ID(), get_TrxName()); //borro lineas anteriores		
			
			MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
			
			if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");

			if(!this.isLastre()){

				order = (MTRTransOrder) this.getUY_TR_TransOrder();
				
				//obtengo CRTs desde la orden de transporte seleccionada
				sql = "select trip.uy_tr_trip_id, crt.uy_tr_crt_id, trip.referenceno, ol.uy_tr_transorderline_id" +
                      " from uy_tr_transorderline ol" +
                      " inner join uy_tr_crt crt on ol.uy_tr_crt_id = crt.uy_tr_crt_id" +
                      " inner join uy_tr_trip trip on ol.uy_tr_trip_id = trip.uy_tr_trip_id" +
                      " where crt.docstatus = 'CO' and ol.uy_tr_transorder_id = " + order.get_ID() +
                      " order by ol.uy_tr_transorderline_id"; 
                      
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery();

				while(rs.next()){
					
					oLine = new MTRTransOrderLine(getCtx(), rs.getInt("uy_tr_transorderline_id"),get_TrxName()); 
					MTRCrt crt = new MTRCrt(getCtx(),rs.getInt("uy_tr_crt_id"),get_TrxName());
					
					//control antes de cargar CRT
					if(oLine.getQtyPackage().compareTo(crt.getQtyPackage())>0 || oLine.getWeight().compareTo(crt.getWeight())>0 ||
							oLine.getWeight2().compareTo(crt.getWeight2())>0 || oLine.getVolume().compareTo(crt.getVolume())>0 ||
							oLine.getProductAmt().compareTo(crt.getTotalAmt())>0 || oLine.getAmount().compareTo(crt.getamt1().add(crt.getamt2()))>0){
						
						throw new AdempiereException("No es posible cargar el CRT N° " + crt.getNumero() + "\n" + "Los valores de la Orden de Transporte superan los del CRT");
						
					}
					
					orgActualID = crt.getAD_Org_ID();
					
					//si al menos 1 CRT es de la empresa definida como principal para campo 1 de MIC, entonces se toman
					//los datos de la organizacion correspondiente a la misma
					if(crt.getAD_Client_ID()==param.getAD_Client_ID_Aux()) orgID = crt.getAD_Org_ID();
			
					MTRMicLine line = new MTRMicLine(getCtx(),0,get_TrxName());
					line.setUY_TR_Mic_ID(this.get_ID());
					line.setUY_TR_Trip_ID(rs.getInt("uy_tr_trip_id"));
					line.setReferenceNo(rs.getString("referenceno"));
					line.setUY_TR_Crt_ID(rs.getInt("uy_tr_crt_id"));
					line.setUY_TR_TransOrderLine_ID(rs.getInt("uy_tr_transorderline_id"));
					
					//seteo cliente exportador/importador
					//el expediente siempre se abre por el cliente uruguayo
					MTRTrip trip = new MTRTrip(getCtx(),rs.getInt("uy_tr_trip_id"),get_TrxName());

					if(trip.getTripType().equalsIgnoreCase("IMPORTACION")){ //se abre por cliente DESTINO

						line.setC_BPartner_ID(trip.getC_BPartner_ID_From());

					} else if(trip.getTripType().equalsIgnoreCase("EXPORTACION")){ //se abre por cliente ORIGEN

						line.setC_BPartner_ID(trip.getC_BPartner_ID_To());							
					}

					line.saveEx();    	
					
					if(isHdr){ //si es el primer CRT, se carga en el cabezal del MIC
																		
						tripAux = trip;
						
						this.loadTruckData(true,true);
						
						this.loadCrtHdr(order,oLine);
						
						isHdr = false;					
						
					} else { //si no es el primer CRT, se carga en una continuacion
						
						this.loadCrtCont(order,oLine);						
					}

				}	   
				
				if(orgID > 0){ //hay al menos 1 CRT de la empresa principal
					
					org = MOrg.get(getCtx(), orgID);
					
				} else org = MOrg.get(getCtx(), orgActualID);
				
				//seteo numero de documento
				if(!this.isManual()) this.setNumber(order,tripAux,org.getAD_Client_ID(),org.get_ID());
					
				if(org!=null){
					
					porteador = org.getName() + "\n";
					
					info = MOrgInfo.get(getCtx(), org.get_ID(), get_TrxName());
					
					if(info!=null){
						
						if(info.getDUNS()!= null && !info.getDUNS().equalsIgnoreCase("")) porteador += info.getDUNS() + "\n";				
						
					} else throw new AdempiereException("No se obtuvo informacion de la organizacion '" + org.getName() + "'");
					
					if(this.getPermiso()!=null) porteador += "Insc.  " + this.getPermiso();					
					
					this.setPorteador(porteador);
									
				} 				

			} else {
				
				MCiudad ciudad = null;
				MCountry pais = null;
				String paisOrigen = "", paisDestino = "", ciudadOrigen = "", ciudadDestino = "", value = "";
				
				order = new MTRTransOrder (getCtx(),this.getUY_TR_TransOrder_ID_1(),get_TrxName());
								
				//this.setNumber(order,null,this.getAD_Client_ID(),this.getAD_Org_ID()); //seteo numero de documento
						
				this.setDescription("EN LASTRE - EN LASTRE - EN LASTRE" + "\n" + "EN LASTRE - EN LASTRE - EN LASTRE" + "\n" + 
				"EN LASTRE - EN LASTRE - EN LASTRE" + "\n" + "EN LASTRE - EN LASTRE - EN LASTRE" + "\n" + "EN LASTRE - EN LASTRE - EN LASTRE");
				
				this.setLocationComment("EN LASTRE");
				this.setRemitente("EN LASTRE" + "\n" + "EN LASTRE");
				this.setDestinatario("EN LASTRE" + "\n" + "EN LASTRE");
				this.setConsignatario("EN LASTRE" + "\n" + "EN LASTRE");
				this.setObservaciones3("EN LASTRE - EN LASTRE - EN LASTRE" + "\n" + "EN LASTRE - EN LASTRE - EN LASTRE");				
				
				//seteo aduana destino
				if(this.getUY_TR_Border_ID_2()>0) this.setUY_TR_Border_ID(this.getUY_TR_Border_ID_2());
				
				//obtengo ciudad y pais de origen
				if(order.getUY_Ciudad_ID() > 0) {
											
					ciudad = new MCiudad(getCtx(),order.getUY_Ciudad_ID(),get_TrxName());
					ciudadOrigen = ciudad.getName().toUpperCase();
					
					pais = new MCountry(getCtx(),ciudad.getC_Country_ID(),get_TrxName());
					paisOrigen = pais.getName().toUpperCase();
				}
				
				//obtengo ciudad y pais de destino
				if(order.getUY_Ciudad_ID_1() > 0) {
											
					ciudad = new MCiudad(getCtx(),order.getUY_Ciudad_ID_1(),get_TrxName());
					ciudadDestino = ciudad.getName().toUpperCase();
					
					pais = new MCountry(getCtx(),ciudad.getC_Country_ID(),get_TrxName());
					paisDestino = pais.getName().toUpperCase();
				}
				
				//seteo aduana, ciudad y pais de origen
				if(this.getUY_TR_Border_ID_1()>0){
					
					MTRBorder border = new MTRBorder(getCtx(),this.getUY_TR_Border_ID_1(),get_TrxName());
					MCountry country = (MCountry)border.getC_Country();
					
					value = border.getName() + "/" + country.getName() + "\n";
					
					if(order.getUY_Ciudad_ID()>0){
										
						MCiudad origen = (MCiudad)order.getUY_Ciudad(); //obtengo ciudad origen desde la OT
						MCountry cty = (MCountry)origen.getC_Country(); //obtengo pais de origen
						
						value += origen.getName() + "/" + cty.getName();		
						
					}
			
					this.setLocatorValue(value);		
					
				} else 	this.setLocatorValue(ciudadOrigen + "/" + paisOrigen);	
				
				this.setDeliveryRule(ciudadDestino + "/" + paisDestino); //seteo ciudad y pais de destino
				
				this.loadTruckData(true,true); //cargo datos del vehiculo
						
				if(!this.isManual()) this.setNumber(order,null,this.getAD_Client_ID(),this.getAD_Org_ID()); //seteo numero de documento
				
			}

		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	

	}
	
	/***
	 * Metodo que carga los datos del vehiculo.
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 07/04/2014
	 * @see
	 * @return
	 */
	private void loadTruckData(boolean loadTractor, boolean loadRemolque) {
		
		String sql = "", value = "", truckData = "";
		MTRTruck truck = null;
		MTRTransOrder order = null; 
				
		try{			
			
			if(this.isLastre()){
				
				order = new MTRTransOrder(getCtx(),this.getUY_TR_TransOrder_ID_1(),get_TrxName());
				
			} else order = new MTRTransOrder(getCtx(),this.getUY_TR_TransOrder_ID(),get_TrxName());

			if(loadTractor){

				truck = new MTRTruck(getCtx(),this.getTractor_ID(),get_TrxName());
				MTRConfig param = MTRConfig.forClient(getCtx(),get_TrxName());

				this.setUY_TR_Truck_ID(truck.get_ID()); //seteo camion original	

				//seteo datos del propietario del camion original
				if(truck.isOwn()){ //si el vehiculo es PROPIO

					value = "";

					MOrg org = MOrg.get(getCtx(), this.getAD_Org_ID()); //instancio organizacion
					MOrgInfo info = MOrgInfo.get(getCtx(), this.getAD_Org_ID(), get_TrxName()); //instancio info de la organizacion

					if(org.getDescription()!=null && !org.getDescription().equalsIgnoreCase("")){

						if(param.isVehiculo()) truckData += "Vehículo Aut. " + org.getDescription().toUpperCase() + "\n";

						value += org.getDescription().toUpperCase() + "\n";

					}

					if(truck.getMTOPNo()!=null && !truck.getMTOPNo().equalsIgnoreCase("")) truckData += "M.T.O.P. " + truck.getMTOPNo() + "\n";

					if(info!=null && info.get_ID()>0){

						MLocation loc = new MLocation(getCtx(),info.getC_Location_ID(),get_TrxName());

						if(loc!=null && loc.get_ID()>0){

							if(loc.getAddress1()!= null && !loc.getAddress1().equalsIgnoreCase("")){

								value += loc.getAddress1().toUpperCase() + "\n";
							}

							MCountry pais = new MCountry(getCtx(),loc.getC_Country_ID(),get_TrxName());
							MDepartamentos depto = new MDepartamentos(getCtx(),loc.getUY_Departamentos_ID(),get_TrxName());

							if((depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){

								value += depto.getName() + " " + pais.getName();							

							}						

						}					
					}

					if(!value.equalsIgnoreCase("")) this.setOriginalTruck(value);

					if(info.getDUNS()!=null) this.setRut1(info.getDUNS());							

				} else { //si el vehiculo NO es PROPIO

					value = "";
					MBPartner partner = null;

					//si NO es lastre, debo cargar los datos del socio de negocio PERMISIONARIO indicado en el vehiculo
					if(!this.isLastre()){

						if(truck.getC_BPartner_ID()>0){

							partner = new MBPartner(getCtx(),truck.getC_BPartner_ID(),get_TrxName());

						} else {

							DB.executeUpdateEx("delete from uy_tr_micline where uy_tr_mic_id = " + this.get_ID(), get_TrxName());						

							throw new AdempiereException("No se pudo obtener permisionario del vehiculo " + truck.getValue());	
						}


					} else { //si ES lastre, debo cargar los datos del PROPIETARIO del vehiculo

						if(truck.getC_BPartner_ID_P()>0){

							partner = new MBPartner(getCtx(),truck.getC_BPartner_ID_P(),get_TrxName());

						} else {

							DB.executeUpdateEx("delete from uy_tr_micline where uy_tr_mic_id = " + this.get_ID(), get_TrxName());						

							throw new AdempiereException("No se pudo obtener propietario del vehiculo " + truck.getValue());						
						}

					}			

					if(partner.getName2() != null && !partner.getName2().equalsIgnoreCase("")){

						if(param.isVehiculo()) truckData += "Vehículo Aut. " + partner.getName2().toUpperCase() + "\n";

						value += partner.getName2().toUpperCase() + "\n";

					} else value += partner.getName().toUpperCase() + "\n";

					if(truck.getMTOPNo()!=null && !truck.getMTOPNo().equalsIgnoreCase("")) truckData += "M.T.O.P. " + truck.getMTOPNo() + "\n";

					sql = "select c_bpartner_location_id" +
							" from c_bpartner_location " +
							" where isbillto = 'Y' and c_bpartner_id = " + partner.get_ID() +
							" and isactive = 'Y'";
					int locID = DB.getSQLValueEx(get_TrxName(), sql);

					if(locID > 0){

						MBPartnerLocation loc = new MBPartnerLocation(getCtx(),locID,get_TrxName());

						if(loc.getAddress1()!= null && !loc.getAddress1().equalsIgnoreCase("")){

							value += loc.getAddress1().toUpperCase() + "\n";
						}

						MCountry pais = new MCountry(getCtx(),loc.getC_Country_ID(),get_TrxName());
						MDepartamentos depto = new MDepartamentos(getCtx(),loc.getUY_Departamentos_ID(),get_TrxName());
						MLocalidades localidad = new MLocalidades(getCtx(),loc.getUY_Localidades_ID(),get_TrxName());

						//si es Uruguay se muestra "departamento - pais", si no se muestra "localidad - departamento - pais"
						if(pais.get_ID()==336){

							if((depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){

								value += depto.getName() + " " + pais.getName();								
							}							

						} else {

							if((localidad!=null && localidad.get_ID()>0) && (depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){

								value += localidad.getName() + " " + depto.getName() + " " + pais.getName();								
							}							

						}					

					} else throw new AdempiereException("No se pudo obtener datos de localizacion para el permisionario del vehiculo " + truck.getValue());

					if(!value.equalsIgnoreCase("")) this.setOriginalTruck(value);

					if(partner.getDUNS()!=null) this.setRut1(partner.getDUNS());	

				}			

				this.setUY_TR_Mark_ID(truck.getUY_TR_Mark_ID()); //seteo marca del camion orignal
				this.setWeight(truck.getArrastre()); //seteo capacidad de arrastre
				this.setYearFrom(truck.getanio()); //seteo anio del vehiculo
				
				//cargo datos del chofer
				if(order.getUY_TR_Driver_ID() > 0){

					MTRDriver driver = new MTRDriver(getCtx(),order.getUY_TR_Driver_ID(),get_TrxName());

					String driverName = driver.getFirstName() + " " + driver.getFirstSurname();
					String driverTipoDoc = driver.gettipodoc();
					String driverNumDoc = driver.getNationalCode();

					truckData += "Chofer: " + driverName + " " + driverTipoDoc + " " + driverNumDoc;				
				}
				
				if(this.isLastre()){
					
					if(truckData!=null && !truckData.equalsIgnoreCase("")){
						
						this.setObservaciones2(truckData + "\n" + "EN LASTRE");
													
					} else this.setObservaciones2("EN LASTRE");				
					
				} else this.setObservaciones2(truckData);
		
				this.setTruckData(truckData);			

			}
			
			if(loadRemolque){
			
				//cargo datos del remolque
				if(this.getRemolque_ID() > 0){

					//MTRTruck truck2 = new MTRTruck(getCtx(),this.getRemolque_ID(),get_TrxName());

					if(this.getTractor_ID() == this.getRemolque_ID()){

						this.setIsSemiRemolque(false);
						this.setIsRemolque(false);

					} else {

						this.setUY_TR_Truck_ID_2(this.getRemolque_ID()); //seteo remolque o semi original
						this.setIsSemiRemolque(true);					
					}	

				}	

			}	
			
		} catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}

	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 31/03/2014. ISSUE#1629
	 * Metodo que carga los campos de la ventana cabezal del formulario.
	 * 
	 * */
	public void loadCrtHdr(MTRTransOrder order, MTRTransOrderLine oLine) {
		
		String ciudadOrigen = "", ciudadDestino = "", paisOrigen = "", paisDestino = "", value = "";
		MCiudad ciudad = null;
		MCountry pais = null;
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
	
		//SE PROCEDE CON LA CARGA DE CAMPOS------------------------------------------------------------------
		MTRCrt crt = new MTRCrt(getCtx(),oLine.getUY_TR_Crt_ID(),get_TrxName()); //obtengo CRT
		MTRTrip trip = new MTRTrip(getCtx(),crt.getUY_TR_Trip_ID(),get_TrxName()); //obtengo el expediente del CRT
		MTRWay way = new MTRWay(getCtx(),trip.getUY_TR_Way_ID(),get_TrxName()); //obtengo el trayecto
				
		this.setUY_TR_Crt_ID(crt.getUY_TR_Crt_ID()); //seteo CRT seleccionado
		this.setCrtImgNum1(crt.getcodigo()); //seteo el codigo de imagen
		this.setC_Currency_ID(crt.getC_Currency_ID()); //seteo la moneda
		this.setRemitente(crt.getRemitente()); //seteo el remitente
		this.setDestinatario(crt.getDestinatario()); //seteo el destinatario
		this.setConsignatario(crt.getConsignatario()); //seteo el consignatario
		this.setUY_TR_PackageType_ID(oLine.getUY_TR_PackageType_ID()); //seteo tipo de bulto
		
		if(this.getObservaciones2()==null || this.getObservaciones2().equalsIgnoreCase("")){
			
			//cargo datos del chofer
			if(order.getUY_TR_Driver_ID() > 0){

				MTRDriver driver = new MTRDriver(getCtx(),order.getUY_TR_Driver_ID(),get_TrxName());

				String driverName = driver.getFirstName() + " " + driver.getFirstSurname();
				String driverTipoDoc = driver.gettipodoc();
				String driverNumDoc = driver.getNationalCode();

				String driverData = "Chofer: " + driverName + " " + driverTipoDoc + " " + driverNumDoc;
				
				this.setObservaciones2(driverData);
			}		
			
		}
		
		//seteo aduana destino
		if(this.getUY_TR_Border_ID_2()>0){
			
			this.setUY_TR_Border_ID(this.getUY_TR_Border_ID_2());
						
		} else this.setUY_TR_Border_ID(trip.getUY_TR_Border_ID_1()); 
		
		 //seteo marcas y numeros de los bultos
		if(trip.getProductType()!=null){
			this.setDescription(trip.getProductType());
		}
				
		this.setpesoBruto(oLine.getWeight()); //seteo peso bruto
		this.setpesoNeto(oLine.getWeight2()); //seteo peso neto
		this.setQtyPackage(oLine.getQtyPackage()); //seteo cantidad de bultos
		this.setImporte(oLine.getProductAmt()); //seteo valor de mercaderia
		
		//seteo valor del flete segun parametros de transporte
		//verifico si debo hacer la conversion a U$S
		if(param.isConvertedAmt()){
			
			if(crt.getC_Currency2_ID()!=100){

				BigDecimal dividerate = Env.ZERO;

				BigDecimal amt = oLine.getAmount();
				int curFromID = crt.getC_Currency2_ID();
				int curToID = 100;

				if (curFromID == 142){
					dividerate = MConversionRate.getDivideRate(curFromID, curToID, this.getDateTrx(), 0, this.getAD_Client_ID(), this.getAD_Org_ID());	
				}
				else{
					BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, this.getDateTrx(), 0, this.getAD_Client_ID(), 0);

					if ((rateFrom != null) && (rateTo != null)){
						if (rateTo.compareTo(Env.ZERO) > 0) {
							dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
						}
					}
				}

				if (dividerate == null || dividerate.compareTo(Env.ZERO)==0){

					ADialog.warn(0,null,"No se obtuvo tasa de cambio para fecha de documento");

				} else {

					amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);

					this.setAmount(amt);
				}

			} else this.setAmount(oLine.getAmount());
			
		} else this.setAmount(oLine.getAmount());

		//obtengo ciudad y pais de origen
		if(order.getUY_Ciudad_ID() > 0) {
									
			ciudad = new MCiudad(getCtx(),trip.getUY_Ciudad_ID(),get_TrxName());
			ciudadOrigen = ciudad.getName().toUpperCase();
			
			pais = new MCountry(getCtx(),ciudad.getC_Country_ID(),get_TrxName());
			paisOrigen = pais.getName().toUpperCase();
		}
		
		//obtengo ciudad y pais de destino
		if(order.getUY_Ciudad_ID_1() > 0) {
									
			ciudad = new MCiudad(getCtx(),trip.getUY_Ciudad_ID_1(),get_TrxName());
			ciudadDestino = ciudad.getName().toUpperCase();
			
			pais = new MCountry(getCtx(),ciudad.getC_Country_ID(),get_TrxName());
			paisDestino = pais.getName().toUpperCase();
		}
		
		//seteo aduana, ciudad y pais de origen
		if(this.getUY_TR_Border_ID_1()>0){
			
			MTRBorder border = new MTRBorder(getCtx(),this.getUY_TR_Border_ID_1(),get_TrxName());
			MCountry country = (MCountry)border.getC_Country();
			
			value = border.getName() + "/" + country.getName() + "\n";
			
			if(order.getUY_Ciudad_ID()>0){
								
				MCiudad origen = (MCiudad)order.getUY_Ciudad(); //obtengo ciudad origen desde la OT
				MCountry cty = (MCountry)origen.getC_Country(); //obtengo pais de origen
				
				value += origen.getName() + "/" + cty.getName();		
				
			}
	
			this.setLocatorValue(value);		
			
		} else 	this.setLocatorValue(ciudadOrigen + "/" + paisOrigen);	
		
		this.setDeliveryRule(ciudadDestino + "/" + paisDestino); //seteo ciudad y pais de destino
		
		//seteo origen de las mercaderias
		if(trip.getProdSource()!=null && !trip.getProdSource().equalsIgnoreCase("")){
			
			this.setLocationComment(trip.getProdSource());
				
		} else this.setLocationComment(paisOrigen);		

		if(way.isPrintExpo()){

			value = "";			
			
			MBPartner partner = new MBPartner(getCtx(),trip.getC_BPartner_ID_To(),get_TrxName()); //instancio el exportador

			value = "EXPORTADOR: " + partner.getName() + "\n";

			if(trip.getReferenceNo()!=null && !trip.getReferenceNo().equalsIgnoreCase("")) value += "FACTURA N°: " + trip.getReferenceNo();

			this.setObservaciones2(this.getObservaciones2() + "\n" + value);


		} else if (way.isPrintDeclaration()){
			
			value = "";
			
			if(trip.getUY_TR_Despachante_ID_3()>0){
				
				MTRDespachante desp = new MTRDespachante(getCtx(),trip.getUY_TR_Despachante_ID_3(),get_TrxName()); //obtengo representante en frontera del exportador
				
				value = "DESP: " + desp.getName() + "\n";
				
			}	
				
			if(trip.getDeclarationType()!=null && (trip.getNumero()!=null && !trip.getNumero().equalsIgnoreCase(""))){
				
				if(value.equalsIgnoreCase("")){
					
					value = trip.getDeclarationType() + ": " + trip.getNumero();					
					
				} else value += trip.getDeclarationType() + ": " + trip.getNumero();			
				
			}
				
			this.setObservaciones2(this.getObservaciones2() + "\n" + value);

		}				
		
		this.setCrtStatus1("ENALTA");
		this.saveEx();
		
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 25/11/2014. ISSUE#3093
	 * Metodo que carga los campos de los CRT en las continuaciones del MIC.
	 * 
	 * */
	public void loadCrtCont(MTRTransOrder order, MTRTransOrderLine oLine) {
				
		//obtengo ultima continuacion del MIC, si la hay
		MTRMicCont cont = this.getMicCont();
		
		//si no hay ninguna continuacion, creo una nueva y cargo CRT 1
		if(cont == null){
			
			cont = new MTRMicCont(getCtx(),0,get_TrxName());
			cont.setUY_TR_Mic_ID(this.get_ID());
			cont.loadCRT1(oLine);
			cont.saveEx();
			
		} else if (cont.getUY_TR_Crt_ID() <= 0){ //si esta vacio el CRT1 lo cargo ahi
			
			cont.loadCRT1(oLine);
			cont.saveEx();
			
		} else if (cont.getUY_TR_Crt_ID_1() <= 0){ //si esta ocupado el CRT1 y libre el CRT2, lo cargo en el 2
			
			cont.loadCRT2(oLine);
			cont.saveEx();
			
		} else { //si la continuacion esta completa, creo una nueva y cargo en CRT1
			
			cont = new MTRMicCont(getCtx(),0,get_TrxName());
			cont.setUY_TR_Mic_ID(this.get_ID());
			cont.loadCRT1(oLine);
			cont.saveEx();		
			
		}
		
	}
	
	/***
	 * Obtiene y retorna la ultima continuacion del MIC.
	 * OpenUp Ltda. Issue #3093
	 * @author Nicolas Sarlabos - 25/11/2014
	 * @see
	 * @return
	 */
	public MTRMicCont getMicCont(){

		MTRMicCont cont = null;
		
		String sql = "select uy_tr_miccont_id" +
		             " from uy_tr_miccont" +
				     " where uy_tr_mic_id = " + this.get_ID() +
				     " order by uy_tr_miccont_id desc";
		int contID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(contID > 0) cont = new MTRMicCont(getCtx(),contID,get_TrxName());

		return cont;
	}
	
	/***
	 * Obtiene y retorna las continuaciones del MIC.
	 * OpenUp Ltda. Issue #3093
	 * @author Nicolas Sarlabos - 28/11/2014
	 * @see
	 * @return
	 */
	public List<MTRMicCont> getCont(){

		String whereClause = X_UY_TR_MicCont.COLUMNNAME_UY_TR_Mic_ID + "=" + this.get_ID();

		List<MTRMicCont> lines = new Query(getCtx(), I_UY_TR_MicCont.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna un cabezal de MIC para el ID de OT recibido.
	 * OpenUp Ltda. Issue #3093
	 * @author Nicolas Sarlabos - 02/12/2014
	 * @see
	 * @return
	 */
	public static MTRMic forOT(Properties ctx, int orderID, String trxName){

		String whereClause = X_UY_TR_Mic.COLUMNNAME_UY_TR_TransOrder_ID + "=" + orderID + " OR " + X_UY_TR_Mic.COLUMNNAME_UY_TR_TransOrder_ID_1 + "::integer=" + orderID;

		MTRMic mic = new Query(ctx, I_UY_TR_Mic.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();

		return mic;
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 07/04/2014. ISSUE#1629
	 * Metodo que crea y setea el numero de documento.
	 * @param order 
	 * @param trip 
	 * 
	 * */
	private void setNumber(MTRTransOrder order, MTRTrip trip, int clientID, int orgID) {
		
		String sql = "", number = "", secNumber = "", suitNumber = "", porteador = "", year = "", countryCode = "";
		//String moveType = "";
		MCiudad ciudad = null;
		MCountry paisOrigen = null;
		MCountry paisDestino = null;
		MTRWay way = null;
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");

		//obtengo ultimos 2 digitos del año de emision, segun parametrizacion de transporte
		if(param.isYearIncluded()){
			if(!this.isManual()){ //si no es numeracion manual

				Calendar cal = Calendar.getInstance(); 
				cal.setTime(this.getDateTrx()); 
				int anio = cal.get(Calendar.YEAR); 

				year = String.valueOf(anio);

				if(year!=null && !year.equalsIgnoreCase("")) year = year.substring(2);

			}
		}

		if(order!=null){

			if(!order.isLastre()){

				//obtengo pais de origen
				ciudad = new MCiudad(getCtx(),order.getUY_Ciudad_ID(),get_TrxName());
				paisOrigen = (MCountry)ciudad.getC_Country();

				//obtengo pais de destino
				ciudad = new MCiudad(getCtx(),order.getUY_Ciudad_ID_1(),get_TrxName());
				paisDestino = (MCountry)ciudad.getC_Country();	
				
				if(trip!=null) way = (MTRWay)trip.getUY_TR_Way();	
				
				//obtengo codigo de idoneidad para los dos paises obtenidos
				sql = "select coalesce(code,null)" +
						" from uy_tr_suitability" +
						" where ((c_country_id = " + paisOrigen.get_ID() + " and c_country_id_1 = " + 
						paisDestino.get_ID() + ") or (c_country_id = " + paisDestino.get_ID() + " and c_country_id_1 = " + paisOrigen.get_ID() + "))" +
						" and ad_client_id = " + clientID + " and ad_org_id = " + orgID;
				suitNumber = DB.getSQLValueStringEx(get_TrxName(), sql);
				
				if(suitNumber == null) throw new AdempiereException("No se pudo obtener codigo de idoneidad para los paises de origen y destino");	
				
				
			} else{

				//obtengo pais de origen
				if(this.getUY_Ciudad_ID() > 0) {

					ciudad = new MCiudad(getCtx(),this.getUY_Ciudad_ID(),get_TrxName());
					paisOrigen = new MCountry(getCtx(),ciudad.getC_Country_ID(),get_TrxName());

				}

				//obtengo pais de destino
				if(this.getUY_Ciudad_ID_1() > 0) {

					ciudad = new MCiudad(getCtx(),this.getUY_Ciudad_ID_1(),get_TrxName());
					paisDestino = new MCountry(getCtx(),ciudad.getC_Country_ID(),get_TrxName());
				}	
				
				MTRTruck truck = new MTRTruck(getCtx(),this.getTractor_ID(),get_TrxName()); //obtengo tractor
				
				if(truck.isOwn()){
					
					MOrg org = MOrg.get(getCtx(), this.getAD_Org_ID()); //instancio organizacion
					MOrgInfo info = MOrgInfo.get(getCtx(), this.getAD_Org_ID(), get_TrxName()); //instancio info de la organizacion
					
					//obtengo codigo de idoneidad para los dos paises obtenidos
					sql = "select coalesce(code,null)" +
							" from uy_tr_suitability" +
							" where ((c_country_id = " + paisOrigen.get_ID() + " and c_country_id_1 = " + 
							paisDestino.get_ID() + ") or (c_country_id = " + paisDestino.get_ID() + " and c_country_id_1 = " + paisOrigen.get_ID() + "))" +
							" and ad_client_id = " + this.getAD_Client_ID() + " and ad_org_id = " + this.getAD_Org_ID();
					suitNumber = DB.getSQLValueStringEx(get_TrxName(), sql);
					
					if(suitNumber == null) throw new AdempiereException("No se pudo obtener codigo de idoneidad para los paises de origen y destino");	
					
					porteador = org.getName() + "\n";
					
					info = MOrgInfo.get(getCtx(), org.get_ID(), get_TrxName());
					
					if(info!=null){
						
						if(info.getDUNS()!= null && !info.getDUNS().equalsIgnoreCase("")) porteador += info.getDUNS() + "\n";				
						
					} else throw new AdempiereException("No se obtuvo informacion de la organizacion '" + org.getName() + "'");
					
					porteador += "Insc.  " + suitNumber;					
					
					this.setPorteador(porteador);					
					
				} else {
					
					if(truck.getC_BPartner_ID() > 0){
						
						MBPartner partner = new MBPartner(getCtx(),truck.getC_BPartner_ID(),get_TrxName());
						
						sql = "select coalesce(code,null)" +
								" from c_bp_permission" +
								" where ((c_country_id = " + paisOrigen.get_ID() + " and c_country_id_1 = " + 
								paisDestino.get_ID() + ") or (c_country_id = " + paisDestino.get_ID() + " and c_country_id_1 = " + paisOrigen.get_ID() + "))" +
								" and c_bpartner_id = " + partner.get_ID();
						suitNumber = DB.getSQLValueStringEx(get_TrxName(), sql);
						
						if(suitNumber == null) throw new AdempiereException("No se pudo obtener codigo de idoneidad para los paises de origen y destino del cliente '" + partner.getName() + "'");
						
						//seteo datos del porteador
						String value = partner.getName() + "\n";
						
						if(partner.getDUNS()!=null && !partner.getDUNS().equalsIgnoreCase("")) value += partner.getDUNS() + "\n";
						
						value += "Insc.  " + suitNumber;
						
						this.setPorteador(value);
						
					} else throw new AdempiereException("No se obtuvo premisionario para el vehiculo " + truck.getValue());	
					
				}			
				
				//obtengo trayecto para los paises origen y destino
				way = MTRWay.forCountry(getCtx(), paisOrigen.get_ID(), paisDestino.get_ID(), get_TrxName());
				
				if(way==null) throw new AdempiereException("No se pudo obtener trayecto para los paises de origen y destino en la orden N° " + order.getDocumentNo());			
			}

			countryCode = paisOrigen.getCountryCode(); //obtengo codigo de pais de origen	
			this.setPermiso(suitNumber);
				
			MSequence seq = new MSequence(getCtx(), way.getAD_Sequence_ID_1(), get_TrxName());
			
			if(seq.getPrefix()!=null && !seq.getPrefix().equalsIgnoreCase("")){
				
				secNumber = seq.getPrefix() + seq.getCurrentNext();
				
			} else {
				
				sql = "select currentnext from ad_sequence where ad_sequence_id = " + seq.get_ID();
				secNumber = DB.getSQLValueStringEx(get_TrxName(), sql);						
				
			}
			
			DB.executeUpdateEx("update ad_sequence set currentnext = currentnext + 1 where ad_sequence_id = " + seq.get_ID(), get_TrxName());
		
			//obtengo largo total entre permiso, año(si tengo) y secuencia
			int length = 0;
			
			if(year.equalsIgnoreCase("")){
				
				length = (suitNumber + secNumber).length(); 				
				
			} else length = (suitNumber + year + secNumber).length();
						
			if(length < 9){
				
				int diff = 9 - length;
				
				if(diff == 1){
					
					suitNumber = "0" + suitNumber;
					
				} else if (diff == 2){
					
					suitNumber = "00" + suitNumber;
					
				} else if (diff == 3){
					
					suitNumber = "000" + suitNumber;
					
				} 			
				
			}
			
			if(year.equalsIgnoreCase("")){
				
				number = countryCode + suitNumber + secNumber;
				
			} else number = countryCode + suitNumber + year + secNumber;		
			
			if(number == null || number.equalsIgnoreCase("")) throw new AdempiereException("Error al obtener numero de MIC/DTA");
			
			this.setNumero(number);				
			
		}
		
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 20/01/2015.
	 * Método que actualiza los totalizadores en las continuaciones de MIC.
	 *   
	 * **/
	public static void updateTotals(MTRMic mic) {
		
		List<MTRMicCont> lines = mic.getCont(); //obtengo lista de continuaciones de MIC
		
		//recorro continuaciones y voy actualizando los totales de pesos y bultos
		for (MTRMicCont cont: lines){
					
			cont.setTotals();
			cont.saveEx();
		}		
		
	}

	public List<MTRMicLine> getLines(){

		String whereClause = X_UY_TR_MicLine.COLUMNNAME_UY_TR_Mic_ID + "=" + this.get_ID();

		List<MTRMicLine> lines = new Query(getCtx(), I_UY_TR_MicLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	public String validateMic(){
		
		String value = null;
		MTRCrt crt = null;
		MTRMicCont cont = null;
		
		List<MTRMicLine> lines = this.getLines();
		
		for (MTRMicLine mLine: lines){
			
			crt = new MTRCrt(getCtx(),mLine.getUY_TR_Crt_ID(),get_TrxName()); //instancio el CRT
			
			//si es el CRT del cabezal
			if(crt.get_ID()==this.getUY_TR_Crt_ID()){
				
				if(this.getQtyPackage().compareTo(crt.getQtyPackage())>0 || this.getpesoBruto().compareTo(crt.getWeight())>0 ||
						this.getpesoNeto().compareTo(crt.getWeight2())>0 || this.getImporte().compareTo(crt.getTotalAmt())>0 || 
						this.getAmount().compareTo(crt.getamt1().add(crt.getamt2()))>0){
					
					value = "No es posible enviar datos del CRT N° " + crt.getNumero() + "\n" + "Los valores del MIC superan los del CRT";
					
					return value;					
				}				
				
			} else { //es un CRT en continuacion
				
				cont = MTRMicCont.forMicLine(mLine);
				
				if(cont!=null && cont.get_ID()>0){
					
					if(crt.get_ID()==cont.getUY_TR_Crt_ID()){ //si es el CRT1 de la continuacion
						
						if(cont.getQtyPackage().compareTo(crt.getQtyPackage())>0 || cont.getpesoBruto().compareTo(crt.getWeight())>0 ||
								cont.getpesoNeto().compareTo(crt.getWeight2())>0 || cont.getImporte().compareTo(crt.getTotalAmt())>0 || 
								cont.getAmount().compareTo(crt.getamt1().add(crt.getamt2()))>0){
							
							value = "No es posible enviar datos del CRT N° " + crt.getNumero() + "\n" + "Los valores del MIC superan los del CRT";
							
							return value;					
						}					
						
					} else if(crt.get_ID()==cont.getUY_TR_Crt_ID_1()){ //si es el CRT2 de la continuacion
				
						if(cont.getQtyPackage2().compareTo(crt.getQtyPackage())>0 || cont.getpesoBruto2().compareTo(crt.getWeight())>0 ||
								cont.getpesoNeto2().compareTo(crt.getWeight2())>0 || cont.getImporte2().compareTo(crt.getTotalAmt())>0 || 
								cont.getAmount2().compareTo(crt.getamt1().add(crt.getamt2()))>0){
							
							value = "No es posible enviar datos del CRT N° " + crt.getNumero() + "\n" + "Los valores del MIC superan los del CRT";
							
							return value;					
						}					
						
					}				
					
				}	
				
			}	
			
		}
		
		return value;	
		
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
		
		if(this.getNumero() == null || this.getNumero().equalsIgnoreCase("")) throw new AdempiereException("Error: documento sin numero de MIC/DTA");
		
		String msg = this.validateMic();
		
		if(msg!=null) throw new AdempiereException(msg);
		
		if (this.getUY_TR_TransOrder_ID() > 0 && !this.isLastre()){
			DB.executeUpdateEx(" update uy_tr_transorder set uy_tr_mic_id = " + this.get_ID() + ", micno ='" + this.getNumero() + "' " +
							   " where uy_tr_transorder_id =" + this.getUY_TR_TransOrder_ID(), get_TrxName());
		}
		
		//si es un MIC en LASTRE
		if(this.getUY_TR_TransOrder_ID_1() > 0 && this.isLastre()){
			DB.executeUpdateEx(" update uy_tr_transorder set uy_tr_mic_id = " + this.get_ID() + ", micno ='" + this.getNumero() + "' " +
					   " where uy_tr_transorder_id =" + this.getUY_TR_TransOrder_ID_1(), get_TrxName());			
		}
		
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

	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
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
	 * Verifica si este Mic fue totalmente enviado a la aduana o no.
	 * OpenUp Ltda. Issue #3996 
	 * @author Gabriel Vila - 20/04/2015
	 * @see
	 * @return
	 */
	public boolean isSentOK() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean value = true;
		
		try{
			sql = " select uy_tr_mic_id, crtstatus1, crtstatus2 " +
				  " from vtr_mic_sent " +
				  " where uy_tr_mic_id =" + this.get_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				if (rs.getInt("crtstatus1") == 0){
					value = false;
				}
				if (rs.getInt("crtstatus2") == 0){
					value = false;
				}

			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return value;
	
	}

}
