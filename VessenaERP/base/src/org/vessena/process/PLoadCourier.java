/**
 * 
 */
package org.openup.process;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.I_UY_TT_XlsCourierLine;
import org.openup.model.MDeliveryPoint;
import org.openup.model.MRReclamo;
import org.openup.model.MTTCard;
import org.openup.model.MTTCardStatus;
import org.openup.model.MTTCardTracking;
import org.openup.model.MTTDelPointRetReasons;
import org.openup.model.MTTDelivery;
import org.openup.model.MTTDeliveryPointStatus;
import org.openup.model.MTTWebCourier;
import org.openup.model.MTTWebCourierLine;
import org.openup.model.MTTXlsCourier;
import org.openup.model.MTTXlsCourierLine;
import org.openup.model.MXLSIssue;
import org.openup.model.X_UY_TT_XlsCourierLine;

/**
 * @author Nicolas
 *
 */
public class PLoadCourier extends SvrProcess {

	private MTTXlsCourier loadCourier = null;
	Sheet hoja=null;
	String fileName = null;

	Workbook workbook=null;
	Integer tope =0;
	int linesOK=0;
	int linesMal=0;
	AuxWorkCellXLS utiles;
	Properties ctx;
	Integer table_ID;
	Integer record_ID;	
	CLogger	log ;
	boolean errorRepetidos;
	MTTXlsCourier windows;
	
	/**
	 * 
	 */
	public PLoadCourier() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		loadCourier = new MTTXlsCourier(getCtx(), getRecord_ID(), null);
		fileName = this.loadCourier.getFileName();

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		//Borro posibles lineas procesadas anteriormente
		deleteOldData();
	
		//Borro errores anteriores
		deleteOldError();

		String s=validacionXLSInicial();
		//Validacion inicial
		if(!(s.equals(""))){
			return s;
		}

		s=this.readXLS();
		
		if(s.equals("Proceso Finalizado OK")){

			if(this.getLines().size() > 0) {

				this.generateDoc(); //genero documento de seguimiento

				loadCourier.setProcessed(true);
				loadCourier.saveEx();	
				
				return s;

			} else throw new AdempiereException ("No hay lineas procesadas para generar documento de seguimiento de courier");
						
		} else throw new AdempiereException("Proceso finalizado con errores");
	}
	
	/***
	 * Genera el documento de Carga Courier.
	 * OpenUp Ltda. Issue #1185
	 * @author Nicolas Sarlabos - 19/09/2013
	 * @see
	 * @return
	 */	
	private void generateDoc() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		MTTWebCourierLine line = null;
		MTTCard card = null;
		MTTCardTracking cardTrack = null;
		MDeliveryPoint courier = new MDeliveryPoint(getCtx(), loadCourier.getUY_DeliveryPoint_ID(), get_TrxName());			
		
		try {
			
			//Creo el modelo del cabezal de documento
			MTTWebCourier hdr = new MTTWebCourier(this.getCtx(), 0, this.get_TrxName());
			hdr.setUY_DeliveryPoint_ID(loadCourier.getUY_DeliveryPoint_ID());
			hdr.setDateTrx(new Timestamp(System.currentTimeMillis()));
			hdr.setC_DocType_ID(MDocType.forValue(this.getCtx(), "webcourier", null).get_ID());
			hdr.saveEx();	
			
			sql = "select * from uy_tt_xlscourierline where uy_tt_xlscourier_id = " + loadCourier.get_ID();
						
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
		    	String name2 = rs.getString("name2");
		    	Timestamp returndate = rs.getTimestamp("returndate");
		       	int returnReasonsID = rs.getInt("uy_tt_returnreasons_id");
		    	int delPointRReasonID = rs.getInt("uy_tt_delpointretreasons_id");
		    	String cedula = rs.getString("cedula");
		    	String phone = rs.getString("phone");    	
		    	String vinculo = rs.getString("vinculo");
		    			    	
		    	line = new MTTWebCourierLine(getCtx(), 0, get_TrxName());
		    	line.setUY_TT_WebCourier_ID(hdr.get_ID());
		    	
		    	line.setAccountNo(rs.getString("accountno"));
		    	line.setName(rs.getString("name"));
		    	line.setLevante(rs.getString("retirementnumber"));
		    	line.setDeliveryNo(rs.getString("deliverynumber"));
		    	line.setDateDelivery(rs.getTimestamp("datestart"));
		    	line.setPieza(rs.getString("pieza"));
		    	line.setUY_TT_CardStatus_ID(rs.getInt("uy_tt_cardstatus_id"));
		    	line.setUY_TT_DeliveryPointStatus_ID(rs.getInt("uy_tt_deliverypointstatus_id"));
		    	line.setAddress1(rs.getString("address1"));
		    	line.setlocalidad(rs.getString("localidad"));
		    	//OpenUp SBT 20/11/2015 Issue #5061
		    	//Se agrega el id del reclamo a la linea ya que no se estaba seteando y seutiliza para buscar la cuenta
		    	line.setUY_R_Reclamo_ID(rs.getInt("UY_R_Reclamo_ID"));  
		    	//FIN OpenUp SBT 20/11/2015
		    	
		    	
		    	if(returndate!=null) line.setDateTrx(returndate);
		    	if(name2!= null && !name2.equalsIgnoreCase("")) line.setName2(name2);
		    	if(returnReasonsID > 0) line.setUY_TT_ReturnReasons_ID(returnReasonsID);
		    	if(delPointRReasonID > 0) line.setUY_TT_DelPointRetReasons_ID(delPointRReasonID);
		    	if(cedula!=null && !cedula.equalsIgnoreCase("")) line.setCedula(cedula);
		    	if(phone!=null && !phone.equalsIgnoreCase("")) line.setPhone(phone);
		    	if(vinculo!=null && !vinculo.equalsIgnoreCase("")) line.setVinculo(vinculo);
		    	
		    	line.saveEx();   
		    	
		    	card = MTTCard.forIncidenciaLevante(getCtx(), line.getUY_R_Reclamo_ID(), line.getLevante(),hdr.getUY_DeliveryPoint_ID(), this.get_TrxName());
		    	
				if(card != null && card.get_ID() > 0){
					//Evaluo si tengo que modificar el estado de la tarjeta y guardar trazabilidad
					if(card.getUY_TT_CardStatus_ID() != line.getUY_TT_CardStatus_ID()){
					
						card.setUY_TT_CardStatus_ID(line.getUY_TT_CardStatus_ID());
						card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						card.saveEx();
						
						//Tracking 1a parte
						cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
						cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
						cardTrack.setAD_User_ID(this.getAD_User_ID());
						cardTrack.setDescription("Lectura " + courier.getName() + ": " + ((MTTCardStatus) card.getUY_TT_CardStatus()).getName());												
						cardTrack.setUY_TT_Card_ID(card.get_ID());
						cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
						cardTrack.setUY_DeliveryPoint_ID_Actual(courier.get_ID());
						//cardTrack.saveEx();
						
						//Tracking 2a parte
						if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "entregada").get_ID()){
							if(cardTrack != null){
								if(!line.getName2().equalsIgnoreCase("") && !line.getCedula().equalsIgnoreCase("")){
									cardTrack.setobservaciones("Entregado a " + line.getName2() + " CI: " + line.getCedula());										
								}else if(!line.getName2().equalsIgnoreCase("") && line.getCedula().equalsIgnoreCase("")){
									cardTrack.setobservaciones("Entregado a " + line.getName2());
								}else if(line.getName2().equalsIgnoreCase("") && !line.getCedula().equalsIgnoreCase("")){
									cardTrack.setobservaciones("Entregado a CI: " + line.getCedula());
								}else{
									cardTrack.setobservaciones("No hay datos de remitente");
								}
								//cardTrack.saveEx();
							}

							//Solo si existe una cuenta valida se cierra el tracking
							if(card != null) card.closeTrackingDelivered(null);

						}else if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "devuelta").get_ID()){
							if(cardTrack != null){
								if(line.getUY_TT_ReturnReasons_ID() > 0){
									cardTrack.setobservaciones("Devuelto por: " + ((MTTDelPointRetReasons) line.getUY_TT_DelPointRetReasons()).getName());
								}else{
									cardTrack.setobservaciones("No hay motivo de devolución");
								}
								//cardTrack.saveEx();
							}
						}							
						
						cardTrack.saveEx();
						
						//Ahora pregunto si el estado que reporta el courier es un estado que marca que ya no esta mas en manos de el
						if(!((MTTDeliveryPointStatus) line.getUY_TT_DeliveryPointStatus()).isInCourier()){

							this.modificarPendientes(line.getUY_R_Reclamo_ID(), line.getLevante());
						}		

					}					
					
				}	
		    	
		    } 		    
		    
		    loadCourier.setUY_TT_WebCourier_ID(hdr.get_ID()); //seteo el ID del documento generado en la ventana de la carga xls			
			
		} catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
	}

	private String validacionXLSInicial(){

		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName,
					"", "", "", "El nombre de la planilla excel esta vacio",
					null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName,
					"", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}


		try {

			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);

			// Se vacia la lista de errores
			// MXLSIssue.Delete(ctx, table_ID,record_ID,null);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}

			if (hoja.getRows() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}
		}catch (Exception e) {
			MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","Error al abrir planilla (TRY) "+e.toString(),null);
			return ("Error al abrir planilla (TRY)");
		}

		return"";
	}
	
	private void deleteOldError() throws Exception{

		String sql = "DELETE FROM uy_xlsissue WHERE record_id IN(SELECT uy_tt_xlscourier_id FROM uy_tt_xlscourier WHERE createdby="+Env.getAD_User_ID(Env.getCtx())+") " +
				"OR record_id="+getRecord_ID()+" AND createdby="+Env.getAD_User_ID(Env.getCtx());

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	private void deleteOldData() throws Exception{

		String sql = "DELETE FROM uy_tt_xlscourierline WHERE uy_tt_xlscourier_id = " + this.loadCourier.get_ID();

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	private String readXLS() throws Exception {
		
		String message = "";
		String msgFinal = "";
		Cell cell = null;
		tope = hoja.getRows();
		String trxAux=null;
		Trx trans = null;
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(getCtx(), getTable_ID(), getRecord_ID(),null, hoja,null);
		MTTXlsCourierLine line = null;
		boolean isInCourier = false;
		boolean cuentaValida = true;
		
		MRReclamo incidencia = null;
		
		for (int recorrido = 2; recorrido < tope; recorrido++) {
			
			message = "";
			
			try {

				//Se lee Nro Levante en columna H que es 7***************************
				cell = (hoja.getCell(7, recorrido));
				String retiro=utiles.getStringFromCell(cell);
				if(retiro==null || retiro.equalsIgnoreCase("")){
					MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. de retiro vacio",null);
					retiro=null;
					message="Nro. de retiro vacio";
				} else retiro=retiro.trim();
				//-----------------------------------------------------------------------------

				if(retiro!=null && !retiro.equalsIgnoreCase("")){

					//Se lee numero de incidencia en columna A que es 0***************************
					cell = (hoja.getCell(0, recorrido));
					
					String docNoIncidencia = utiles.getStringFromCell(cell);
					
					if (docNoIncidencia == null || docNoIncidencia.equalsIgnoreCase("")){
						MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. de Incidencia vacio",null);
						docNoIncidencia = null;
						message="Nro. de Incidencia vacio";
					} else {
						docNoIncidencia = docNoIncidencia.trim();
						
						incidencia = MRReclamo.forDocumentNo(getCtx(), docNoIncidencia, null);
						if (incidencia == null) incidencia = new MRReclamo(getCtx(), 0, null); 
						
						cuentaValida = MTTCard.existsIncidenciaAndDeliveryPoint(getCtx(), null, incidencia.get_ID(), loadCourier.getUY_DeliveryPoint_ID());
						if(!cuentaValida) {
							MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Incidencia invalida para el punto de distribucion",null);
							docNoIncidencia = null;
							message="Incidencia invalida para el punto de distribucion";
						} else{
							isInCourier = MTTWebCourierLine.getIsInCourierForIncidencia(getCtx(), incidencia.get_ID(), retiro, this.get_TrxName());
							
							// OpenUp. Gabriel Vila. 04/07/2014. Issue #2341
							if (!isInCourier){
								MTTCard cardAux = MTTCard.forIncidenciaAndDeliveryPoint(getCtx(), null, incidencia.get_ID(), loadCourier.getUY_DeliveryPoint_ID());
								if ((cardAux != null) && (cardAux.get_ID() > 0)){
									MTTCardStatus cst = (MTTCardStatus)cardAux.getUY_TT_CardStatus();
									if ((cst.getValue().equalsIgnoreCase("recepcionada") || (cst.getValue().equalsIgnoreCase("distribucion")))){
										isInCourier = true;
									}
									else{
										message="Incidencia no tiene estado correcto para ser procesada (recepcionada o en distribucion).";
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),message,null);
										docNoIncidencia = null;
									}
								}
							}
							// Fin OpenUp. Issue #2341.
						}
						//Openup. Sylvie Bouissa Openup 28/10/2014 Ini Issue # 3164
						if (docNoIncidencia != null && !(retiro.isEmpty())){
							MTTCard aux = MTTCard.forIncidenciaLevante(getCtx(), incidencia.get_ID(), retiro , loadCourier.getUY_DeliveryPoint_ID(), this.get_TrxName());
							if (aux == null){
								String delPointName=String.valueOf(loadCourier.getUY_DeliveryPoint_ID());
								//se crea obtención de pto de distribución por UY_DeliveryPonint_ID, 
								//para retornar nombre del mismo.
								MDeliveryPoint delPonit = MDeliveryPoint.forUY_DeliveryPonint_ID(getCtx(), loadCourier.getUY_DeliveryPoint_ID(), this.get_TrxName());
								if(delPonit!=null){
									delPointName =delPonit.getName();
								}
								message="No existe la incidencia: " + docNoIncidencia + " en el punto de distribución: "+ delPointName +", con levante: "+retiro+" ";
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(), fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),message,null);
								docNoIncidencia = null;
							}
						}//Fin Issue # 3164
						
					}
					//---------------------------------------------------------------

					if (docNoIncidencia != null && !docNoIncidencia.equalsIgnoreCase("")){
						
						if(isInCourier){  //si la tarjeta esta todavia en el courier

							//Se lee Fecha de ingreso en columna B que es 1***************************
							cell = (hoja.getCell(1, recorrido));
							Timestamp fechaIngreso = null;
							String fchIngreso=utiles.getStringFromCell(cell);
							if(fchIngreso!=null){
								try{
									fchIngreso=fchIngreso.trim();
									fechaIngreso=utiles.formatStringDate(fchIngreso);
									String f = fechaIngreso.toString();
									if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
										message="Fecha con formato incorrecto";

									}

								}catch (Exception e){
									MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
									message="Fecha con formato incorrecto";
									fechaIngreso=null;
								}
							} else {

								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha de ingreso vacia",null);
								message="Fecha de ingreso vacia";
							}
							//---------------------------------------------------------------

							//Se lee Estado Courier en columna C que es 2***************************
							int cardStatusID = 0;
							int delPointStatusID = 0;
							cell = (hoja.getCell(2, recorrido));
							String estado=utiles.getStringFromCell(cell);
							if(estado==null || estado.equalsIgnoreCase("")){
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Estado vacio",null);
								estado=null;
								message="Estado vacio";
							} else {

								estado = estado.trim();

								MTTDeliveryPointStatus point = MTTDeliveryPointStatus.getMTTDeliveryPointStatusForEstado(getCtx(), get_TrxName(), loadCourier.getUY_DeliveryPoint_ID(), estado); 

								if(point!=null) {

									cardStatusID = point.getUY_TT_CardStatus_ID();
									delPointStatusID = point.getUY_TT_DeliveryPointStatus_ID();

									//Evaluo si tengo que modificar el estado de la tarjeta
									/*if(cuentaValida){ //si es una cuenta valida para el punto de distribucion

										//card = MTTCard.forAccountOpen(getCtx(), cuenta, this.get_TrxName());
										if(card != null && card.get_ID() > 0){

											card.setUY_TT_CardStatus_ID(cardStatusID);
											card.saveEx();

										}

									}*/

								} else {
									MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Estado inexistente para el punto de distribucion",null);
									message="Estado de tarjeta inexistente";
									estado=null;
								}										
							}
							//-----------------------------------------------------------------------------

							//Se lee Nombre en columna D que es 3***************************
							cell = (hoja.getCell(3, recorrido));
							String nombre=utiles.getStringFromCell(cell);
							if(nombre==null || nombre.equalsIgnoreCase("")){
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nombre vacio",null);
								nombre=null;
								message="Nombre vacio";
							} else nombre = nombre.trim();
							//-----------------------------------------------------------------------------

							//Se lee Direccion en columna E que es 4***************************
							cell = (hoja.getCell(4, recorrido));
							String direccion=utiles.getStringFromCell(cell);
							if(direccion==null || direccion.equalsIgnoreCase("")){
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Direccion vacia",null);
								direccion=null;
								message="Direccion vacia";
							} else direccion = direccion.trim();
							//-----------------------------------------------------------------------------

							//Se lee Localidad en columna F que es 5***************************
							cell = (hoja.getCell(5, recorrido));
							String localidad=utiles.getStringFromCell(cell);
							if(localidad==null || localidad.equalsIgnoreCase("")){
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Localidad vacia",null);
								localidad=null;
								message="Localidad vacia";
							} else localidad = localidad.trim();
							//-----------------------------------------------------------------------------

							//Se lee Pieza en columna G que es 6***************************
							String piezaAux = "";
							cell = (hoja.getCell(6, recorrido));
							String pieza=utiles.getStringFromCell(cell);
							if(pieza==null || pieza.equalsIgnoreCase("")){
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Pieza vacia",null);
								pieza=null;
								message="Pieza vacia";
							}else {						

								try {

									pieza = pieza.trim();
									piezaAux = utiles.getStringFromCell(cell);
									piezaAux = piezaAux.toUpperCase();

									if(piezaAux.equalsIgnoreCase("TARJETA") || piezaAux.equalsIgnoreCase("RESUMEN")) {
										pieza = piezaAux;
										
									} else {	
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Pieza fuera de rango especificado",null);
										pieza=null;
										message="Pieza invalida";							
									}

								}catch (Exception e){

									MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Pieza no es un numero entero",null);
									pieza=null;
									message="Error al obtener pieza";
								}						
							}
							//-----------------------------------------------------------------------------

							//Se lee Nro envio en columna I que es 8***************************
							cell = (hoja.getCell(8, recorrido));
							String envio=utiles.getStringFromCell(cell);
							if(envio==null || envio.equalsIgnoreCase("")){
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. de envio vacio",null);
								retiro=null;
								message="Nro. de envio vacio";
							} else envio = envio.trim();
							//-----------------------------------------------------------------------------

							//Se lee Fecha Cambio Estado en columna J que es 9***************************
							cell = (hoja.getCell(9, recorrido));
							Timestamp fechaCambio = null;
							String fchCam=utiles.getStringFromCell(cell);
							if(fchCam!=null){
								try{

									fchCam = fchCam.trim();
									fechaCambio=utiles.formatStringDate(fchCam);
									String f = fechaCambio.toString();
									if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
										message="Fecha con formato incorrecto";

									}

								}catch (Exception e){
									MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
									message="Fecha con formato incorrecto";
									fechaCambio=null;
								}
							}
							//---------------------------------------------------------------

							//Se lee Motivo Devuelto en columna K que es 10***************************
							int returnReasonsID = 0;
							int delPointRetReasonsID = 0;
							cell = (hoja.getCell(10, recorrido));
							String motivo = utiles.getStringFromCell(cell);
							if(motivo!=null){

								motivo = motivo.trim();
								MTTDelPointRetReasons ret = MTTDelPointRetReasons.forCourierIDAndMotivo(getCtx(), get_TrxName(), loadCourier.getUY_DeliveryPoint_ID(), motivo); 

								if(ret!=null) {

									returnReasonsID = ret.getUY_TT_ReturnReasons_ID();
									delPointRetReasonsID = ret.getUY_TT_DelPointRetReasons_ID();

								} else {

									MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Motivo de devolucion inexistente para el punto de distribucion",null);
									message="Motivo de devolucion inexistente";
								}									

							} else {
								
								if(estado != null){
									
									String estadoAux = estado.toUpperCase();
									
									if(estadoAux.equalsIgnoreCase("DEVUELTO")) {
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Motivo de devolucion no puede ser vacio cuando el estado es DEVUELTO",null);
										message="Motivo de devolucion no puede ser vacio cuando el estado es DEVUELTO";
										motivo=null;
										cardStatusID = 0; //pongo el ID de estado en cero para evitar guardar la linea
									}									
									
								}							
								
							}
							//---------------------------------------------------------------

							//Se lee Nombre Receptor en columna L que es 11***************************
							cell = (hoja.getCell(11, recorrido));
							String nomReceptor=utiles.getStringFromCell(cell);
							if(nomReceptor!=null && !nomReceptor.equalsIgnoreCase("")) {
								nomReceptor = nomReceptor.trim();
							} else {

								if(estado != null){

									String estadoAux = estado.toUpperCase();

									if(estadoAux.equalsIgnoreCase("ENTREGADO")) {
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nombre receptor no puede ser vacio cuando el estado es ENTREGADO",null);
										message="Nombre receptor no puede ser vacio cuando el estado es ENTREGADO";
										nomReceptor=null;
										cardStatusID = 0; //pongo el ID de estado en cero para evitar guardar la linea
									//OpenUp Sylvie Bouissa 05/01/2015 Issue#3435 - En el caso de correo plaza el estado que reportan es el siguiente.
									}else if(estadoAux.equalsIgnoreCase("RENDIDO-ENTREGADO")){
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nombre receptor no puede ser vacio cuando el estado es ENTREGADO",null);
										message="Nombre receptor no puede ser vacio cuando el estado es RENDIDO-ENTREGADO";
										nomReceptor=null;
										cardStatusID = 0; //pongo el ID de estado en cero para evitar guardar la linea
									}
								}	

							}
							//-----------------------------------------------------------------------------

							//Se lee Documento Receptor en columna M que es 12***************************
							cell = (hoja.getCell(12, recorrido));
							String docReceptor=utiles.getStringFromCell(cell);
							if(docReceptor!=null && !docReceptor.equalsIgnoreCase("")){
								docReceptor = docReceptor.trim();
							} else {

								if(estado != null){

									String estadoAux = estado.toUpperCase();

									if(estadoAux.equalsIgnoreCase("ENTREGADO")) {
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Documento receptor no puede ser vacio cuando el estado es ENTREGADO",null);
										message="Documento receptor no puede ser vacio cuando el estado es ENTREGADO";
										docReceptor=null;
										cardStatusID = 0; //pongo el ID de estado en cero para evitar guardar la linea
									//OpenUp Sylvie Bouissa 05/01/2015 Issue#3435 - En el caso de correo plaza el estado es el siguiente.
									}else if(estadoAux.equalsIgnoreCase("RENDIDO-ENTREGADO")){
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Documento receptor no puede ser vacio cuando el estado es ENTREGADO",null);
										message="Documento receptor no puede ser vacio cuando el estado es RENDIDO-ENTREGADO";
										docReceptor=null;
										cardStatusID = 0;//pongo el ID de estado en cero para evitar guardar la linea
									}
								}	

							}							
							//-----------------------------------------------------------------------------

							//Se lee Telefono en columna N que es 13***************************
							cell = (hoja.getCell(13, recorrido));
							String telefono=utiles.getStringFromCell(cell);
							if(telefono!=null && !telefono.equalsIgnoreCase("")) {
								telefono = telefono.trim();
							} /*else {

								if(estado != null){

									String estadoAux = estado.toUpperCase();

									if(estadoAux.equalsIgnoreCase("ENTREGADO")) {
										MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Telefono no puede ser vacio cuando el estado es ENTREGADO",null);
										message="Telefono no puede ser vacio cuando el estado es ENTREGADO";
										telefono=null;
										cardStatusID = 0; //pongo el ID de estado en cero para evitar guardar la linea
									}
								}

							}*/				
							//-----------------------------------------------------------------------------
							
							//Se lee Vinculo en columna O que es 14***************************
							cell = (hoja.getCell(14, recorrido));
							String vinculo=utiles.getStringFromCell(cell);
							if(vinculo!=null && !vinculo.equalsIgnoreCase("")) vinculo = vinculo.trim();
							//-----------------------------------------------------------------------------

							if (docNoIncidencia != null && fechaIngreso!=null && cardStatusID > 0 && delPointStatusID > 0 && nombre!=null && direccion!=null && localidad!=null && pieza!=null && retiro!=null && envio!=null && message.equalsIgnoreCase("")){

								trxAux=Trx.createTrxName();
								trans = Trx.get(trxAux, true);

								line = new MTTXlsCourierLine(getCtx(), 0, trxAux);
								line.setUY_TT_XlsCourier_ID(loadCourier.get_ID());

								if (incidencia != null){
									line.setUY_R_Reclamo_ID(incidencia.get_ID());
									MTTCard cardAcct = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), get_TrxName()); 
									if (cardAcct != null){
										line.setAccountNo(cardAcct.getAccountNo());		
									}
								}
								
								line.setDateStart(fechaIngreso);
								line.setUY_TT_CardStatus_ID(cardStatusID);
								line.setUY_TT_DeliveryPointStatus_ID(delPointStatusID);
								line.setName(nombre);
								line.setAddress1(direccion);
								line.setlocalidad(localidad);
								line.setPieza(pieza);
								line.setRetirementNumber(retiro);
								line.setDeliveryNumber(envio);

								if(fechaCambio!=null) line.setReturnDate(fechaCambio);
								if(returnReasonsID > 0) line.setUY_TT_ReturnReasons_ID(returnReasonsID);
								if(delPointRetReasonsID > 0) line.setUY_TT_DelPointRetReasons_ID(delPointRetReasonsID);
								if(nomReceptor!=null) line.setName2(nomReceptor);
								if(docReceptor!=null) line.setCedula(docReceptor);
								if(telefono!=null) line.setPhone(telefono); 
								if(vinculo!=null) line.setVinculo(vinculo);

								line.saveEx(trxAux);

							}	
						}
					}					
				}

			} catch (Exception e) {
				//Errores no contemplados
				trans.rollback();
				trans.close();
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
			
			msgFinal += message;
			
			if(trans!=null){
				trans.close();
			}
			
		}			
		
		if (workbook!=null){
			workbook.close();
		}
		
		if(msgFinal.equalsIgnoreCase("")) msgFinal="Proceso Finalizado OK";
		
		return msgFinal;
	}
	
	/***
	 * Retorna lineas procesadas de esta carga.
	 * OpenUp Ltda. Issue #1185
	 * @author Nicolas Sarlabos - 11/10/2013
	 * @see
	 * @return
	 */
	public List<MTTXlsCourierLine> getLines(){
		
		String whereClause = X_UY_TT_XlsCourierLine.COLUMNNAME_UY_TT_XlsCourier_ID + "=" + this.loadCourier.get_ID();
		
		List<MTTXlsCourierLine> lines = new Query(getCtx(), I_UY_TT_XlsCourierLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}
	
	/**
	 *OpenUp. Guillermo Brust. 10/09/2013. ISSUE #1300
	 *Método que desasocia el envio de la cuenta si el estado del envio retornado por el courier es uno de los parametrizados como   
	 * Se pone dentro de la misma transaccion, dado que esto se hacer en cada linea guardada, y si se cae el proceso, no deberia realizarse para ninguna.
	 **/
	private void modificarPendientes(int uyRReclamoID, String levante){
		
		//Obtengo el estado de tarjeta EN DISTRIBUCION
		int statusDistribucionID = MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "distribucion").get_ID();
		
		//Obtengo el envio a partir del punto de distribucion y el numero de levante
		MTTDelivery delivery = MTTDelivery.forDelvieryPointAndLevante(getCtx(), loadCourier.getUY_DeliveryPoint_ID(), levante, get_TrxName());
		
		if(delivery != null){
			
			//Obtengo la tarjeta a partir de la cuenta y del envio
			MTTCard card = MTTCard.forIncidenciaAndDeliveryAndStatus(getCtx(), uyRReclamoID, delivery.get_ID(), this.get_TrxName());
							
			if(card != null){			
				if(card.getUY_TT_Delivery_ID() > 0){
					if(card.getUY_TT_CardStatus_ID() == statusDistribucionID){
						DB.executeUpdateEx("UPDATE UY_TT_Card SET UY_TT_Delivery_ID = NULL WHERE UY_TT_Card_ID = " + card.get_ID(), this.get_TrxName());
					}	
				}					
			}		
		} 
		
	}
}
