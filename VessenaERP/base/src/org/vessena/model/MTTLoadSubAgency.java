/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 23, 2015
*/
package org.openup.model;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.*;

/**
 * org.openup.model - MTTLoadSubAgency
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 23, 2015
*/
public class MTTLoadSubAgency extends X_UY_TT_LoadSubAgency implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = -5375381710435775309L;
	
	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_LoadSubAgency_ID
	 * @param trxName
	*/
	public MTTLoadSubAgency(Properties ctx, int UY_TT_LoadSubAgency_ID, String trxName) {
		super(ctx, UY_TT_LoadSubAgency_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/
	public MTTLoadSubAgency(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 */
	@Override
	public boolean applyIt() {
		
		String message = null;
		
		try {
			
			// Elimino datos anteriores
			DB.executeUpdateEx(" delete from " + I_UY_TT_LoadSubAgencyLine.Table_Name + " where uy_tt_loadSubagency_id =" + this.get_ID(), get_TrxName());
			DB.executeUpdateEx(" delete from " + I_UY_TT_LoadSubAgencyIssue.Table_Name + " where uy_tt_loadSubagency_id =" + this.get_ID(), get_TrxName());

			// Tengo archivo
			if ((this.getFileName() == null) || (this.getFileName().equalsIgnoreCase(""))){
				throw new AdempiereException("Falta indicar archivo a procesar.");
			}
			
			// Obtengo archivo
			File mainFile = new File(this.getFileName()); 
			if (!mainFile.exists()){
				throw new AdempiereException("El archivo indicado no existe.");
			}

			// Proceso archivo y genero lineas de este documento segun tipo de archivo
			if (this.getLoadSubAgencyType().equalsIgnoreCase(LOADSUBAGENCYTYPE_RECEPCIONENSUBAGENCIA)){
				//message = this.procesoRecepcion(mainFile);	
				message = this.procesoRecepcionXLS(mainFile);
			}
			else {
				//message = this.procesoEntrega(mainFile);
				message = this.procesoEntregaXLS(mainFile);
			}
			
			if (message != null) throw new AdempiereException(message);
			
			this.setDocAction(DocAction.ACTION_Complete);
			this.setDocStatus(DocumentEngine.STATUS_Applied);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	/***
	 * Proceso archivo de recepcion y genero lineas en modelo.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 23, 2015
	 * @return
	 */
	@SuppressWarnings("unused")
	private String procesoRecepcion(File mainFile) {
		
		String message = null;

		Workbook wb = null;
		
		try {
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			// Instancio workbook
			wb = new XSSFWorkbook(new FileInputStream(mainFile));

			// Verifico que tenga al menos una pagina
			if (wb.getNumberOfSheets() <= 0){
				message = "La planilla seleccionada no tiene hojas.";
				return message;
			}

			String fechaRemito = "";
			String fechaRecepcion = "";
			String subAgenciaNo = "";
			
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {

    			boolean hayError = false;
            	MTTCard card = null;
    			
            	if (row.getRowNum() == 0){
            		continue;
            	}
            	
            	Cell cellEmpresa= row.getCell(0);
            	
            	// Si tengo datos en primer celda
            	if ((cellEmpresa != null) && (cellEmpresa.getNumericCellValue() >= 0)){
            		// Si empresa es valida
            		String empresaST = String.valueOf(new BigDecimal(cellEmpresa.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            		if (empresaST.equalsIgnoreCase(config.getEmpCodeRedPagos())){

            			// Fecha remito
            			Cell cellFecRemito = row.getCell(1);
            			if (cellFecRemito != null){
            				Date dateRemito = cellFecRemito.getDateCellValue();
            				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy"); 
                			fechaRemito = sdf.format(dateRemito);
            			}
            			else{
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell("1");
            				issue.setRowNo(row.getRowNum());
            				issue.setErrorMsg("No se indica valor para Fecha de Remito");
            				issue.saveEx();
            			}

            			// Numero de subagencia
            			Cell cellSubAgencia = row.getCell(2);
            			if ((cellSubAgencia != null) && (cellSubAgencia.getNumericCellValue() >= 0)){
                			subAgenciaNo = String.valueOf(new BigDecimal(cellSubAgencia.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            			}
            			else{
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell("2");
            				issue.setRowNo(row.getRowNum());
            				issue.setErrorMsg("No se indica valor para Subagencia");
            				issue.saveEx();
            			}
            			
            			// Fecha de recepcion
            			Cell cellFecRecepcion = row.getCell(3);
            			if (cellFecRecepcion != null){
            				Date dateRecep = cellFecRecepcion.getDateCellValue();
            				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
                			fechaRecepcion = sdf.format(dateRecep);
            			}
            			else{
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell("3");
            				issue.setRowNo(row.getRowNum());
            				issue.setErrorMsg("No se indica valor para Fecha de Recepcion");
            				issue.saveEx();
            			}
            		}
            		else{
        				hayError = true;
        				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
        				issue.setcell("0");
        				issue.setRowNo(row.getRowNum());
        				issue.setContentText(empresaST);
        				issue.setErrorMsg("Valor de Empresa incorrecto");
        				issue.saveEx();
            		}
            	}
            	else{
            		// No tengo datos en primer celda, busco en celda que tiene el datos de numero de incidencia
            		Cell cellIncidencia = row.getCell(4);
            		if ((cellIncidencia != null) && (cellIncidencia.getNumericCellValue() >= 0)){

            			String nroIncidencia = String.valueOf(new BigDecimal(cellIncidencia.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            			nroIncidencia = nroIncidencia.replace("0000000000", "");
            			nroIncidencia = nroIncidencia.replace("000000000", "");
            			nroIncidencia = nroIncidencia.replace("00000000", "");
            			nroIncidencia = nroIncidencia.replace("0000000", "");
            			nroIncidencia = nroIncidencia.replace("000000", "");
            			nroIncidencia = nroIncidencia.replace("00000", "");
            			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), nroIncidencia, null);
            			if (incidencia == null){
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell(String.valueOf(cellIncidencia.getColumnIndex()));
            				issue.setRowNo(row.getRowNum());
            				issue.setContentText(nroIncidencia);
            				issue.setErrorMsg("No existe incidencia con este valor.");
            				issue.saveEx();
            			}
            			else{
            				card = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), null);
            			}
            		}
            		else{
            			// No obtuve nada en celda de empresa y en celda de numero de incidencia
            			// No leo mas filas
            			break;
            		}
            	}
            	
            	// Si tengo cuenta y no hay error
            	if ((!hayError) && (card != null) & (!subAgenciaNo.equalsIgnoreCase(""))){
            		// Todo bien nueva linea
            		MTTLoadSubAgencyLine line = new MTTLoadSubAgencyLine(getCtx(), 0, get_TrxName());
            		line.setUY_TT_LoadSubAgency_ID(this.get_ID());
            		line.setUY_TT_Card_ID(card.get_ID());
            		line.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
            		line.setSubAgencyNo(subAgenciaNo);
            		line.setDateReceived(Timestamp.valueOf(fechaRecepcion));
            		line.saveEx();
            	}
            }
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			if (wb != null){
				try {
					wb.close();	
				} 
				catch (Exception e2) {
				}
			}
		}
		
		return message;
	}

	/***
	 * Proceso archivo de entrega y genero lineas en modelo.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 23, 2015
	 * @return
	 */
	@SuppressWarnings("unused")
	private String procesoEntrega(File mainFile) {
		
		String message = null;

		Workbook wb = null;
		
		try {
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			// Instancio workbook
			wb = new XSSFWorkbook(new FileInputStream(mainFile));

			// Verifico que tenga al menos una pagina
			if (wb.getNumberOfSheets() <= 0){
				message = "La planilla seleccionada no tiene hojas.";
				return message;
			}

			String fechaEntrega = "", subAgenciaNo = "", cedula = "", caja = "", movimiento = "", nombre = "";
			
            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {

    			boolean hayError = false;
            	MTTCard card = null;
    			
            	if (row.getRowNum() == 0){
            		continue;
            	}

    			// Fecha de entrega
    			Cell cellFecEntrega = row.getCell(0);
    			if (cellFecEntrega != null){
    				Date dateEnt = cellFecEntrega.getDateCellValue();
    				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        			fechaEntrega = sdf.format(dateEnt);
    			}
    			else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("0");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Fecha de Entrega");
    				issue.saveEx();
    			}
            	
        		// Numero de incidencia
        		Cell cellIncidencia = row.getCell(1);
        		if ((cellIncidencia != null) && (cellIncidencia.getNumericCellValue() >= 0)){

        			String nroIncidencia = String.valueOf(new BigDecimal(cellIncidencia.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
        			nroIncidencia = nroIncidencia.replace("0000000000", "");
        			nroIncidencia = nroIncidencia.replace("000000000", "");
        			nroIncidencia = nroIncidencia.replace("00000000", "");
        			nroIncidencia = nroIncidencia.replace("0000000", "");
        			nroIncidencia = nroIncidencia.replace("000000", "");
        			nroIncidencia = nroIncidencia.replace("00000", "");

        			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), nroIncidencia, null);
        			if (incidencia == null){
        				hayError = true;
        				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
        				issue.setcell(String.valueOf(cellIncidencia.getColumnIndex()));
        				issue.setRowNo(row.getRowNum());
        				issue.setContentText(nroIncidencia);
        				issue.setErrorMsg("No existe incidencia con este valor.");
        				issue.saveEx();
        			}
        			else{
        				card = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), null);
        			}
        		}
        		else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("1");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Numero de Incidencia");
    				issue.saveEx();
        		}
    			    			
        		// Cedula
            	Cell cellCedula = row.getCell(2);
            	if ((cellCedula != null) && (cellCedula.getNumericCellValue() >= 0)){
            		cedula = String.valueOf(new BigDecimal(cellCedula.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            	}
        		else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("2");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Cedula");
    				issue.saveEx();
        		}
            	
            	// Nombre
            	Cell cellNombre = row.getCell(3);
            	if ((cellNombre != null) && (!cellNombre.getStringCellValue().trim().equalsIgnoreCase(""))){
            		nombre = cellNombre.getStringCellValue().trim();
            	}
        		else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("3");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica Nombre");
    				issue.saveEx();
        		}
            	
    			// Numero de subagencia
    			Cell cellSubAgencia = row.getCell(4);
    			if ((cellSubAgencia != null) && (cellSubAgencia.getNumericCellValue() >= 0)){
        			subAgenciaNo = String.valueOf(new BigDecimal(cellSubAgencia.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
    			}
    			else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("4");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Subagencia");
    				issue.saveEx();
    			}

    			// Caja
    			Cell cellCaja = row.getCell(5);
    			if ((cellCaja != null) && (cellCaja.getNumericCellValue() >= 0)){
    				caja = String.valueOf(new BigDecimal(cellCaja.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
    			}
    			/*
    			else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("5");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Caja");
    				issue.saveEx();
    			}
    			*/

    			// Movimiento
    			Cell cellMov = row.getCell(6);
    			if ((cellMov != null) && (cellMov.getNumericCellValue() >= 0)){
    				movimiento = String.valueOf(new BigDecimal(cellMov.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
    			}
    			/*
    			else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("6");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Movimiento");
    				issue.saveEx();
    			}
    			*/
        		
            	// Si tengo cuenta y no hay error
            	if ((!hayError) && (card != null)){
            		// Todo bien nueva linea
            		MTTLoadSubAgencyLine line = new MTTLoadSubAgencyLine(getCtx(), 0, get_TrxName());
            		line.setUY_TT_LoadSubAgency_ID(this.get_ID());
            		line.setUY_TT_Card_ID(card.get_ID());
            		line.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
            		line.setSubAgencyNo(subAgenciaNo);
            		line.setDateReceived(Timestamp.valueOf(fechaEntrega));
            		line.setName(nombre);
            		line.setCedula(cedula);
            		line.saveEx();
            	}
            }
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			if (wb != null){
				try {
					wb.close();	
				} 
				catch (Exception e2) {
				}
			}
		}
		
		return message;

	}

	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
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

		
		// Obtengo issues
		List<MTTLoadSubAgencyIssue> issues = this.getIssues();
		
		// Si tengo issues no puedo completar
		if (issues.size() > 0){
			this.processMsg = "No es posible completar el documento ya que existen Inconsistencias de datos en el archivo indicado.";
			return DocAction.STATUS_Invalid;
		}
		
		// Estados a considerar
		MTTCardStatus stRecepcionada = MTTCardStatus.forValue(getCtx(), get_TrxName(), "recepcionada");
		MTTCardStatus stEntregada = MTTCardStatus.forValue(getCtx(), null, "entregada");
		
		// Obtiene cuentas y procesa segun tipo de archivo
		List<MTTLoadSubAgencyLine> lines = this.getLines();
		
		for (MTTLoadSubAgencyLine line: lines){
			
			MTTCard card = (MTTCard)line.getUY_TT_Card();
			
			// Segun tipo de archivo
			if (this.getLoadSubAgencyType().equalsIgnoreCase(LOADSUBAGENCYTYPE_RECEPCIONENSUBAGENCIA)){
				
				card.setUY_TT_CardStatus_ID(stRecepcionada.get_ID()); //cambio estado de la tarjeta
				card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				card.setUY_DeliveryPoint_ID_Actual(this.getUY_DeliveryPoint_ID()); //cambio el punto de distribucion actual de la tarjeta
				card.setSubAgencia(line.getSubAgencyNo());
				card.setDateAssign(new Timestamp(System.currentTimeMillis()));
				card.setDiasActual(0);
				card.saveEx();
				
				//seteo a null el ID de precinto
				DB.executeUpdateEx("update uy_tt_card set uy_tt_seal_id = null where uy_tt_card_id = " + card.get_ID(), get_TrxName()); 
				
				// Quito cuenta de precinto
				DB.executeUpdateEx(" delete from uy_tt_sealcard where uy_tt_card_id =" + card.get_ID(), get_TrxName());
				
				// Obtengo punto de distribucion de esta subagencia
				MDeliveryPoint delPointSubAge = MDeliveryPoint.forSubAgencyNo(getCtx(), card.getSubAgencia().trim(), null);
				String datosSubAge = "";
				if ((delPointSubAge != null) && (delPointSubAge.get_ID() > 0)){
					if (delPointSubAge.getAddress1()!=null){
						datosSubAge +=delPointSubAge.getAddress1();
					}
					if (delPointSubAge.getTelephone() != null){
						datosSubAge += " - " + delPointSubAge.getTelephone();
					}
					if (delPointSubAge.getDeliveryTime() != null){
						datosSubAge += " - L.a V.: " + delPointSubAge.getDeliveryTime(); 
					}
					if (delPointSubAge.getDeliveryTime2() != null){
						datosSubAge += " - Sab.: " + delPointSubAge.getDeliveryTime2(); 
					}
					if (delPointSubAge.getDeliveryTime3() != null){
						datosSubAge += " - Dom.: " + delPointSubAge.getDeliveryTime3(); 
					}
				}
				
				// Tracking cuenta 
				MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
				cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
				cardTrack.setAD_User_ID(this.getUpdatedBy());
				cardTrack.setDescription("Recepcionada en SubAgencia: " + card.getSubAgencia());
				cardTrack.setobservaciones(datosSubAge);
				cardTrack.setUY_TT_Card_ID(card.get_ID());
				cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
				cardTrack.setUY_DeliveryPoint_ID_Actual(card.getUY_DeliveryPoint_ID_Actual());
				cardTrack.setSubAgencia(card.getSubAgencia());
				cardTrack.saveEx();
			}
			else{

				// Cierro tracking de cuenta por Entregada
				card.closeTrackingDelivered(line.getDateReceived());
				
				// Obtengo punto de distribucion de esta subagencia
				MDeliveryPoint delPointSubAge = MDeliveryPoint.forSubAgencyNo(getCtx(), card.getSubAgencia().trim(), null);
				String datosSubAge = "";
				if ((delPointSubAge != null) && (delPointSubAge.get_ID() > 0)){
					if (delPointSubAge.getAddress1()!=null){
						datosSubAge +=delPointSubAge.getAddress1();
					}
					if (delPointSubAge.getTelephone() != null){
						datosSubAge += " - " + delPointSubAge.getTelephone();
					}
					if (delPointSubAge.getDeliveryTime() != null){
						datosSubAge += " - L.a V.: " + delPointSubAge.getDeliveryTime(); 
					}
					if (delPointSubAge.getDeliveryTime2() != null){
						datosSubAge += " - Sab.: " + delPointSubAge.getDeliveryTime2(); 
					}
					if (delPointSubAge.getDeliveryTime3() != null){
						datosSubAge += " - Dom.: " + delPointSubAge.getDeliveryTime3(); 
					}
				}
				
				// Tracking cuenta
				MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
				cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
				cardTrack.setAD_User_ID(this.getUpdatedBy());
				cardTrack.setDescription("Entregada a " + line.getName() + " - Cedula : " + line.getCedula());
				cardTrack.setobservaciones(datosSubAge);
				cardTrack.setUY_TT_Card_ID(card.get_ID());
				cardTrack.setUY_TT_CardStatus_ID(stEntregada.get_ID());
				cardTrack.setSubAgencia(card.getSubAgencia());				
				cardTrack.saveEx();
				
			}
			
			card.saveEx();
			
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Obtiene y retorna lineas de este documento.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 23, 2015
	 * @return
	 */
	public List<MTTLoadSubAgencyLine> getLines(){
		
		String whereClause = X_UY_TT_LoadSubAgencyLine.COLUMNNAME_UY_TT_LoadSubAgency_ID + "=" + this.get_ID();
		
		List<MTTLoadSubAgencyLine> lines = new Query(getCtx(), I_UY_TT_LoadSubAgencyLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}

	/***
	 * Obtiene y retorna lineas con errores de este documento.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 23, 2015
	 * @return
	 */
	public List<MTTLoadSubAgencyIssue> getIssues(){
		
		String whereClause = X_UY_TT_LoadSubAgencyIssue.COLUMNNAME_UY_TT_LoadSubAgency_ID + "=" + this.get_ID();
		
		List<MTTLoadSubAgencyIssue> lines = new Query(getCtx(), I_UY_TT_LoadSubAgencyIssue.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}
	
	
	private String procesoEntregaXLS(File mainFile) {
		
		String message = null;

		HSSFWorkbook wb = null;
		
		try {
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			// Instancio workbook
			wb = new HSSFWorkbook(new FileInputStream(mainFile));

			// Verifico que tenga al menos una pagina
			if (wb.getNumberOfSheets() <= 0){
				message = "La planilla seleccionada no tiene hojas.";
				return message;
			}

			MTTCardStatus stRecepcionada = MTTCardStatus.forValue(getCtx(), get_TrxName(), "recepcionada");
			
			String fechaEntrega = "", subAgenciaNo = "", cedula = "", caja = "", movimiento = "", nombre = "";
			Date dateEnt = null;
			
            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {

    			boolean hayError = false;
            	MTTCard card = null;
    			
            	if (row.getRowNum() == 0){
            		continue;
            	}

    			// Fecha de entrega
    			Cell cellFecEntrega = row.getCell(0);
    			if (cellFecEntrega != null){
    				dateEnt = cellFecEntrega.getDateCellValue();
    				if (dateEnt == null){
    					break;
    				}
    				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        			fechaEntrega = sdf.format(dateEnt);
    			}
    			else{
    				// Si no hay fecha de entrega corto el archivo
    				break;
    				/*
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("0");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Fecha de Entrega");
    				issue.saveEx();
    				*/
    			}
            	
        		// Numero de incidencia
        		Cell cellIncidencia = row.getCell(1);
        		if ((cellIncidencia != null) && (!cellIncidencia.getStringCellValue().trim().equalsIgnoreCase(""))){
        		//if ((cellIncidencia != null) && (cellIncidencia.getNumericCellValue() >= 0)){

        			//String nroIncidencia = String.valueOf(new BigDecimal(cellIncidencia.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
        			String nroIncidencia = cellIncidencia.getStringCellValue().trim();
        			nroIncidencia = nroIncidencia.replace("0000000000", "");
        			nroIncidencia = nroIncidencia.replace("000000000", "");
        			nroIncidencia = nroIncidencia.replace("00000000", "");
        			nroIncidencia = nroIncidencia.replace("0000000", "");
        			nroIncidencia = nroIncidencia.replace("000000", "");
        			nroIncidencia = nroIncidencia.replace("00000", "");

        			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), nroIncidencia, null);
        			if (incidencia == null){
        				hayError = true;
        				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
        				issue.setcell(String.valueOf(cellIncidencia.getColumnIndex()));
        				issue.setRowNo(row.getRowNum());
        				issue.setContentText(nroIncidencia);
        				issue.setErrorMsg("No existe incidencia con este valor.");
        				issue.saveEx();
        			}
        			else{
        				card = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), null);
        				// Valido status de cuenta, tiene que estar recepcionada en redpagos
        				if (card.getUY_TT_CardStatus_ID() != stRecepcionada.get_ID()){
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell(String.valueOf(cellIncidencia.getColumnIndex()));
            				issue.setRowNo(row.getRowNum());
            				issue.setContentText(nroIncidencia);
            				issue.setErrorMsg("Esta cuenta figura como no recepcionada y por lo tanto no puede ser entregada.");
            				issue.saveEx();
        				}
        				else{
        					if (card.getUY_DeliveryPoint_ID_Actual() != config.getUY_DeliveryPoint_ID()){
                				hayError = true;
                				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
                				issue.setsheet("1");
                				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
                				issue.setcell(String.valueOf(cellIncidencia.getColumnIndex()));
                				issue.setRowNo(row.getRowNum());
                				issue.setContentText(nroIncidencia);
                				issue.setErrorMsg("Esta cuenta no figura actualmente ubicada en subagencia de Red Pagos.");
                				issue.saveEx();
        					}
        				}
        			}
        		}
        		else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("1");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Numero de Incidencia");
    				issue.saveEx();
        		}
    			    			
        		// Cedula
            	Cell cellCedula = row.getCell(2);
            	if (cellCedula != null){
            		try {
						cedula = cellCedula.getStringCellValue().trim();
					} catch (Exception e) {
						cedula = "E";
					}
            		if (cedula.equalsIgnoreCase("E")){
            			cedula = String.valueOf(new BigDecimal(cellCedula.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();	
            		}
            	}
        		else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("2");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Cedula");
    				issue.saveEx();
        		}
            	
            	// Nombre
            	Cell cellNombre = row.getCell(3);
            	if ((cellNombre != null) && (!cellNombre.getStringCellValue().trim().equalsIgnoreCase(""))){
            		nombre = cellNombre.getStringCellValue().trim();
            	}
        		else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("3");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica Nombre");
    				issue.saveEx();
        		}
            	
    			// Numero de subagencia
    			Cell cellSubAgencia = row.getCell(4);
    			if (cellSubAgencia != null){
    				try {
						subAgenciaNo = cellSubAgencia.getStringCellValue().trim();
					} catch (Exception e) {
						subAgenciaNo = "E";
					}
    				if (subAgenciaNo.equalsIgnoreCase("E")){
    					subAgenciaNo = String.valueOf(new BigDecimal(cellSubAgencia.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();	
    				}
    			}
    			else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("4");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Subagencia");
    				issue.saveEx();
    			}

    			// Caja
    			Cell cellCaja = row.getCell(5);
    			if (cellCaja != null){
    				try {
    					caja = cellCaja.getStringCellValue().trim();
					} catch (Exception e) {
						caja = "E";
					}
    				if (caja.equalsIgnoreCase("E")){
    					caja = String.valueOf(new BigDecimal(cellCaja.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();	
    				}
    			}
    			/*
    			else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("5");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Caja");
    				issue.saveEx();
    			}
    			*/

    			// Movimiento
    			Cell cellMov = row.getCell(6);
    			if (cellMov != null){
    				try {
    					movimiento = cellMov.getStringCellValue().trim();
					} catch (Exception e) {
						movimiento = "E";
					}
    				if (movimiento.equalsIgnoreCase("E")){
    					movimiento = String.valueOf(new BigDecimal(cellMov.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();	
    				}    				
    			}
    			/*
    			else{
    				hayError = true;
    				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
    				issue.setsheet("1");
    				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
    				issue.setcell("6");
    				issue.setRowNo(row.getRowNum());
    				issue.setErrorMsg("No se indica valor para Movimiento");
    				issue.saveEx();
    			}
    			*/
        		
            	// Si tengo cuenta y no hay error
            	if ((!hayError) && (card != null)){
            		// Todo bien nueva linea
            		MTTLoadSubAgencyLine line = new MTTLoadSubAgencyLine(getCtx(), 0, get_TrxName());
            		line.setUY_TT_LoadSubAgency_ID(this.get_ID());
            		line.setUY_TT_Card_ID(card.get_ID());
            		line.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
            		line.setSubAgencyNo(subAgenciaNo);
            		line.setDateReceived(new Timestamp(dateEnt.getTime()));
            		line.setName(nombre);
            		line.setCedula(cedula);
            		line.saveEx();
            	}
            }
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			if (wb != null){
				try {
					wb.close();	
				} 
				catch (Exception e2) {
				}
			}
		}
		
		return message;

	}

	private String procesoRecepcionXLS(File mainFile) {
		
		String message = null;

		HSSFWorkbook wb = null;
		
		try {
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			// Instancio workbook
			wb = new HSSFWorkbook(new FileInputStream(mainFile));

			// Verifico que tenga al menos una pagina
			if (wb.getNumberOfSheets() <= 0){
				message = "La planilla seleccionada no tiene hojas.";
				return message;
			}

			// Estado en transito
			MTTCardStatus stTransito = MTTCardStatus.forValue(getCtx(), get_TrxName(), "enviada");
			
			String fechaRemito = "";
			String fechaRecepcion = "";
			String subAgenciaNo = "";
			
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {

    			boolean hayError = false;
            	MTTCard card = null;
            	String cedula = "";
    			
            	if (row.getRowNum() == 0){
            		continue;
            	}
            	
            	Cell cellEmpresa= row.getCell(0);
            	
            	// Si tengo datos en primer celda
            	if ((cellEmpresa != null) && (cellEmpresa.getNumericCellValue() >= 0)){
            		// Si empresa es valida
            		String empresaST = String.valueOf(new BigDecimal(cellEmpresa.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            		if (empresaST.equalsIgnoreCase(config.getEmpCodeRedPagos())){

            			// Fecha remito
            			Cell cellFecRemito = row.getCell(1);
            			if (cellFecRemito != null){
            				try {
            					fechaRemito = String.valueOf(new BigDecimal(cellFecRemito.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();	
							} catch (Exception e) {
								fechaRemito = "E";
							}
            				if (fechaRemito.equalsIgnoreCase("E")){
                				Date dateRemito = cellFecRemito.getDateCellValue();
                				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy"); 
                    			fechaRemito = sdf.format(dateRemito);
            				}
            			}
            			else{
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell("1");
            				issue.setRowNo(row.getRowNum());
            				issue.setErrorMsg("No se indica valor para Fecha de Remito");
            				issue.saveEx();
            			}

            			// Numero de subagencia
            			Cell cellSubAgencia = row.getCell(2);
            			if (cellSubAgencia != null){
            				try {
            					subAgenciaNo = cellSubAgencia.getStringCellValue().trim();	
							} catch (Exception e) {
								subAgenciaNo = "E";
							}
            				if (subAgenciaNo.equalsIgnoreCase("E")){
            					subAgenciaNo = String.valueOf(new BigDecimal(cellSubAgencia.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            				}
            			}
            			else{
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell("2");
            				issue.setRowNo(row.getRowNum());
            				issue.setErrorMsg("No se indica valor para Subagencia");
            				issue.saveEx();
            			}
            			
            			// Fecha de recepcion
            			Cell cellFecRecepcion = row.getCell(3);
            			if (cellFecRecepcion != null){
            				Date dateRecep = cellFecRecepcion.getDateCellValue();
            				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                			fechaRecepcion = sdf.format(dateRecep);
            			}
            			else{
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell("3");
            				issue.setRowNo(row.getRowNum());
            				issue.setErrorMsg("No se indica valor para Fecha de Recepcion");
            				issue.saveEx();
            			}
            		}
            		else{
        				hayError = true;
        				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
        				issue.setcell("0");
        				issue.setRowNo(row.getRowNum());
        				issue.setContentText(empresaST);
        				issue.setErrorMsg("Valor de Empresa incorrecto");
        				issue.saveEx();
            		}
            	}
            	else{
            		boolean sinIncidencia = false;

            		// No tengo datos en primer celda, busco en celda que tiene el datos de numero de incidencia
            		Cell cellIncidencia = row.getCell(5);
            		if (cellIncidencia != null){

            			//String nroIncidencia = String.valueOf(new BigDecimal(cellIncidencia.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            			String nroIncidencia = cellIncidencia.getStringCellValue().trim();
            			nroIncidencia = nroIncidencia.replace("0000000000", "");
            			nroIncidencia = nroIncidencia.replace("000000000", "");
            			nroIncidencia = nroIncidencia.replace("00000000", "");
            			nroIncidencia = nroIncidencia.replace("0000000", "");
            			nroIncidencia = nroIncidencia.replace("000000", "");
            			nroIncidencia = nroIncidencia.replace("00000", "");
            			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), nroIncidencia, null);
            			if (incidencia == null){
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell(String.valueOf(cellIncidencia.getColumnIndex()));
            				issue.setRowNo(row.getRowNum());
            				issue.setContentText(nroIncidencia);
            				issue.setErrorMsg("No existe incidencia con este valor.");
            				issue.saveEx();
            			}
            			else{
            				card = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), null);
            				// Tengo que validar estado y ubicacion de cuenta para poder aceptar la recepcion en subagencia
            				// Estado en transito sino aviso con error
            				if (card.getUY_TT_CardStatus_ID() != stTransito.get_ID()){
                				hayError = true;
                				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
                				issue.setsheet("1");
                				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
                				issue.setcell(String.valueOf(cellIncidencia.getColumnIndex()));
                				issue.setRowNo(row.getRowNum());
                				issue.setContentText(nroIncidencia);
                				issue.setErrorMsg("La cuenta asociada NO esta en Transito a esta subagencia.");
                				issue.saveEx();
            				}
            				else{
            					if (card.getSubAgencia() != null){
            						if (!card.getSubAgencia().equalsIgnoreCase(subAgenciaNo)){
                        				hayError = true;
                        				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
                        				issue.setsheet("1");
                        				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
                        				issue.setcell(String.valueOf(cellIncidencia.getColumnIndex()));
                        				issue.setRowNo(row.getRowNum());
                        				issue.setContentText(nroIncidencia);
                        				issue.setErrorMsg("La cuenta asociada NO esta en Transito a esta subagencia.");
                        				issue.saveEx();
            						}
            					}
            				}
            			}
            		}
            		else{
            			sinIncidencia = true;
            		}
            		
            		// Cedula
                	Cell cellCedula = row.getCell(4);
                	if (cellCedula != null){
                		try {
                			cedula = cellCedula.getStringCellValue().trim();	
						} catch (Exception e) {
							cedula = "E";
						}
                		if (cedula.equalsIgnoreCase("E")){
                			cedula = String.valueOf(new BigDecimal(cellCedula.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
                		}
                	}
            		else{
            			if (!sinIncidencia){
            				hayError = true;
            				MTTLoadSubAgencyIssue issue = new MTTLoadSubAgencyIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_TT_LoadSubAgency_ID(this.get_ID());
            				issue.setcell("2");
            				issue.setRowNo(row.getRowNum());
            				issue.setErrorMsg("No se indica valor para Cedula");
            				issue.saveEx();
            			}
            			else{
                			// No obtuve nada en celda de empresa, cedula y numero de incidencia
                			// No leo mas filas
                			break;
            			}
            		}

            	}
            	
            	// Si tengo cuenta y no hay error
            	if ((!hayError) && (card != null) & (!subAgenciaNo.equalsIgnoreCase(""))){
            		// Todo bien nueva linea
            		MTTLoadSubAgencyLine line = new MTTLoadSubAgencyLine(getCtx(), 0, get_TrxName());
            		line.setUY_TT_LoadSubAgency_ID(this.get_ID());
            		line.setUY_TT_Card_ID(card.get_ID());
            		line.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
            		line.setSubAgencyNo(subAgenciaNo);
            		line.setDateReceived(Timestamp.valueOf(fechaRecepcion));
            		line.saveEx();
            	}
            }
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			if (wb != null){
				try {
					wb.close();	
				} 
				catch (Exception e2) {
				}
			}
		}
		
		return message;
	}
	
}
