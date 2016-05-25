/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Sep 30, 2015
*/
package org.openup.model;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;


/**
 * org.openup.model - MRLoadXls
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Sep 30, 2015
*/
public class MRLoadXls extends X_UY_R_LoadXls implements DocAction {

	private static final long serialVersionUID = -7846982765601687406L;

	private String processMsg = null;
	private boolean justPrepared = false;
	
	
	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_R_LoadXls_ID
	 * @param trxName
	*/

	public MRLoadXls(Properties ctx, int UY_R_LoadXls_ID, String trxName) {
		super(ctx, UY_R_LoadXls_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MRLoadXls(Properties ctx, ResultSet rs, String trxName) {
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
			DB.executeUpdateEx(" delete from " + I_UY_R_LoadXlsLine.Table_Name + " where uy_r_loadxls_id =" + this.get_ID(), get_TrxName());
			DB.executeUpdateEx(" delete from " + I_UY_R_LoadXlsIssue.Table_Name + " where uy_r_loadxls_id =" + this.get_ID(), get_TrxName());

			// Tengo archivo
			if ((this.getFileName() == null) || (this.getFileName().equalsIgnoreCase(""))){
				throw new AdempiereException("Falta indicar archivo a procesar.");
			}
			
			// Obtengo archivo
			File mainFile = new File(this.getFileName()); 
			if (!mainFile.exists()){
				throw new AdempiereException("El archivo indicado no existe.");
			}

			// Proceso archivo y genero lineas
			message = this.readIncidencias(mainFile);
			
			if (message != null) throw new AdempiereException(message);
			
			this.setDocAction(DocAction.ACTION_Complete);
			this.setDocStatus(DocumentEngine.STATUS_Applied);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
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
		List<MRLoadXlsIssue> issues = this.getIssues();
		
		// Si tengo issues no puedo completar
		if (issues.size() > 0){
			this.processMsg = "No es posible completar el documento ya que existen Inconsistencias de datos en el archivo indicado.";
			return DocAction.STATUS_Invalid;
		}
		
		// Obtiene lineas y las procesa
		List<MRLoadXlsLine> lines = this.getLines();
		
		for (MRLoadXlsLine line: lines){
			
			// Genero y aplico incidentica
			this.generateIncidencia(line);
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
	public List<MRLoadXlsLine> getLines(){
		
		String whereClause = X_UY_R_LoadXlsLine.COLUMNNAME_UY_R_LoadXls_ID + "=" + this.get_ID();
		
		List<MRLoadXlsLine> lines = new Query(getCtx(), I_UY_R_LoadXlsLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}

	/***
	 * Obtiene y retorna lineas con errores de este documento.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 23, 2015
	 * @return
	 */
	public List<MRLoadXlsIssue> getIssues(){
		
		String whereClause = X_UY_R_LoadXlsIssue.COLUMNNAME_UY_R_LoadXls_ID + "=" + this.get_ID();
		
		List<MRLoadXlsIssue> lines = new Query(getCtx(), I_UY_R_LoadXlsIssue.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}

	/***
	 * Proceso archivo y genero lineas en modelo.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 23, 2015
	 * @return
	 */
	private String readIncidencias(File mainFile) {
		
		String message = null;

		Workbook wb = null;
		
		try {

			// Instancio workbook
			wb = new XSSFWorkbook(new FileInputStream(mainFile));

			// Verifico que tenga al menos una pagina
			if (wb.getNumberOfSheets() <= 0){
				message = "La planilla seleccionada no tiene hojas.";
				return message;
			}

			int countBlankRow = 0;
			
            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {

    			boolean hayError = false, blankRow = false;
    			boolean cedulaNula = false, cuentaNula = false, viaNula = false, descripNula = false;
    			boolean canalNula = false, tipoNula = false, temaNula = false;
    			
    			String cedula = "", cuenta = "", via = "", canal = "", tipo = "", tema = "", subTema = "", descrip = "";    			
    			MRCanal canalModel = null;
    			MRType typeModel = null;
    			MRCause causeModel = null;
    			MRSubCause subCauseModel = null;
    			
            	if (row.getRowNum() == 0){
            		continue;
            	}
            	
    			// Cedula
    			Cell cellCedula = row.getCell(0);
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
    			
    			if ((cedula == null) || (cedula.equalsIgnoreCase(""))){
    				cedulaNula = true;
    			}

    			// Cuenta
    			Cell cellCuenta = row.getCell(1);
    			if (cellCuenta != null){
    				try {
    					cuenta = cellCuenta.getStringCellValue().trim();	
					} catch (Exception e) {
						cuenta = "E";
					}
    				if (cuenta.equalsIgnoreCase("E")){
    					cuenta = String.valueOf(new BigDecimal(cellCuenta.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
    				}
    			}
    			
    			if ((cuenta == null) || (cuenta.equalsIgnoreCase(""))){
    				cuentaNula = true;
    			}

    			// Via Notificacion
    			Cell cellVia = row.getCell(2);
    			if (cellVia != null){
    				via = cellVia.getStringCellValue().trim();
    			}
    			
    			if ((via == null) || (via.equalsIgnoreCase(""))){
    				viaNula = true;
    			}
    			else{
    				if ((!via.equalsIgnoreCase(X_UY_R_LoadXlsLine.NOTIFICATIONVIA_Email)) &&
    					(!via.equalsIgnoreCase(X_UY_R_LoadXlsLine.NOTIFICATIONVIA_Celular)) &&
    					(!via.equalsIgnoreCase(X_UY_R_LoadXlsLine.NOTIFICATIONVIA_TelefonoFijo))){
    			
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("2");
        				issue.setRowNo(row.getRowNum());
        				issue.setContentText(via);
        				issue.setErrorMsg("Valor Incorrecto para Via de Notificacion");
        				issue.saveEx();

    				}
    			}

    			// Canal Recepcion
    			Cell cellCanal = row.getCell(3);
    			if (cellCanal != null){
    				canal = cellCanal.getStringCellValue().trim();
    			}
    			if ((canal == null) || (canal.equalsIgnoreCase(""))){
    				canalNula = true;
    			}
    			else{
    				// Busco canal por nombre
    				canalModel = MRCanal.forName(getCtx(), canal, null);
    				if ((canalModel == null) || (canalModel.get_ID() <= 0)){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("3");
        				issue.setRowNo(row.getRowNum());
        				issue.setContentText(canal);
        				issue.setErrorMsg("Valor Incorrecto para Canal");
        				issue.saveEx();
    				}
    			}

    			// Tipo
    			Cell cellTipo = row.getCell(4);
    			if (cellTipo != null){
    				tipo = cellTipo.getStringCellValue().trim();
    			}
    			if ((tipo == null) || (tipo.equalsIgnoreCase(""))){
    				tipoNula = true;
    			}
    			else{
    				// Busco tipo por nombre
    				typeModel = MRType.forName(getCtx(), tipo, null);
    				if ((typeModel == null) || (typeModel.get_ID() <= 0)){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("4");
        				issue.setRowNo(row.getRowNum());
        				issue.setContentText(tipo);
        				issue.setErrorMsg("Valor Incorrecto para Tipo");
        				issue.saveEx();
    				}
    			}

    			if ((!tipoNula) && (!hayError)){

    				// Tema
        			Cell cellTema = row.getCell(5);
        			if (cellTema != null){
        				tema = cellTema.getStringCellValue().trim();
        			}
        			if ((tema == null) || (tema.equalsIgnoreCase(""))){
        				temaNula = true;
        			}
        			else{
        				// Busco tema por nombre
        				causeModel = MRCause.forTypeAndName(getCtx(), typeModel.get_ID(), tema, null);
        				if ((causeModel == null) || (causeModel.get_ID() <= 0)){
        					hayError = true;
            				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
            				issue.setsheet("1");
            				issue.setUY_R_LoadXls_ID(this.get_ID());
            				issue.setcell("5");
            				issue.setRowNo(row.getRowNum());
            				issue.setContentText(tema);
            				issue.setErrorMsg("Valor Incorrecto para Tema");
            				issue.saveEx();
        				}
        			}
    				
    			}
    			
    			if ((!temaNula) && (!hayError)){

    				// SubTema
        			Cell cellSubTema = row.getCell(6);
        			if (cellSubTema != null){
        				subTema = cellSubTema.getStringCellValue().trim();
        			}
        			if ((subTema != null) && (!subTema.equalsIgnoreCase(""))){
	    				// Busco subtema por nombre
	    				subCauseModel = MRSubCause.forCauseAndName(getCtx(), causeModel.get_ID(), subTema, null);
	    				if ((subCauseModel == null) || (subCauseModel.get_ID() <= 0)){
	    					hayError = true;
	        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
	        				issue.setsheet("1");
	        				issue.setUY_R_LoadXls_ID(this.get_ID());
	        				issue.setcell("6");
	        				issue.setRowNo(row.getRowNum());
	        				issue.setContentText(subTema);
	        				issue.setErrorMsg("Valor Incorrecto para SubTema");
	        				issue.saveEx();
	    				}
        			}
    				
    			}
    			
    			Cell cellDescrip = row.getCell(7);
    			if (cellDescrip != null){
    				descrip = cellDescrip.getStringCellValue().trim();
    			}

    			if ((descrip == null) || (descrip.equalsIgnoreCase(""))){
    				descripNula = true;
    			}
    			
    			if (cedulaNula && cuentaNula && viaNula && canalNula && tipoNula && temaNula && descripNula){
    				blankRow = true;
    				countBlankRow++;
    			}
    			
    			if (!blankRow){
    				if (cedulaNula){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("0");
        				issue.setRowNo(row.getRowNum());
        				issue.setErrorMsg("Debe indicar Cedula");
        				issue.saveEx();
    				}
    				if (cuentaNula){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("1");
        				issue.setRowNo(row.getRowNum());
        				issue.setErrorMsg("Debe indicar Cuenta");
        				issue.saveEx();
    				}
    				if (viaNula){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("2");
        				issue.setRowNo(row.getRowNum());
        				issue.setErrorMsg("Debe indicar Via de Notificacion");
        				issue.saveEx();
    				}
    				if (canalNula){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("3");
        				issue.setRowNo(row.getRowNum());
        				issue.setErrorMsg("Debe indicar Canal");
        				issue.saveEx();
    				}
    				if (tipoNula){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("4");
        				issue.setRowNo(row.getRowNum());
        				issue.setErrorMsg("Debe indicar Tipo");
        				issue.saveEx();
    				}
    				if (temaNula){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("5");
        				issue.setRowNo(row.getRowNum());
        				issue.setErrorMsg("Debe indicar Tema");
        				issue.saveEx();
    				}
    				if (descripNula){
    					hayError = true;
        				MRLoadXlsIssue issue = new MRLoadXlsIssue(getCtx(), 0, get_TrxName());
        				issue.setsheet("1");
        				issue.setUY_R_LoadXls_ID(this.get_ID());
        				issue.setcell("7");
        				issue.setRowNo(row.getRowNum());
        				issue.setErrorMsg("Debe indicar Detalle");
        				issue.saveEx();
    				}
    			}
    			else{
    				if (countBlankRow >= 5){
    					break;
    				}
    			}
    			
            	// Si no tuve error en la row leída, genero linea
            	if ((!blankRow) && (!hayError)){

            		MRLoadXlsLine line = new MRLoadXlsLine(getCtx(), 0, get_TrxName());
            		line.setUY_R_LoadXls_ID(this.get_ID());
            		line.setCedula(cedula);
            		line.setAccountNo(cuenta);
            		line.setNotificationVia(via);
            		line.setUY_R_Canal_ID(canalModel.get_ID());
            		line.setUY_R_Type_ID(typeModel.get_ID());
            		line.setUY_R_Cause_ID(causeModel.get_ID());
            		if (subCauseModel != null){
            			line.setUY_R_SubCause_ID(subCauseModel.get_ID());
            		}
            		line.setDescription(descrip);

            		MRCedulaCuenta cedcta = MRCedulaCuenta.forCedulaCuenta(getCtx(), cedula, cuenta, null);
					if ((cedcta == null) || (cedcta.get_ID() <= 0)){
						cedcta = new MRCedulaCuenta(getCtx(), 0, null);
						cedcta.setValue(cedula);
						cedcta.setAccountNo(cuenta);
						cedcta.saveEx();
					}

					line.setUY_R_CedulaCuenta_ID(cedcta.get_ID());
            		line.saveEx();
            	}
            	
            	if (hayError){
            		message = "Se detectaron Inconsistencias en los datos del Archivo";
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

	
	private void generateIncidencia(MRLoadXlsLine line){

		Connection con = null;
		ResultSet rs = null;

		try{
			
			// Instancio y seteo modelo de Incidencia
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY); 
			
			MRReclamo incidencia = new MRReclamo(getCtx(), 0, get_TrxName());
			incidencia.setCedula(line.getCedula());
			incidencia.setDefaultDocType();
			incidencia.setDateTrx(new Timestamp (System.currentTimeMillis()));
			incidencia.setUY_R_Type_ID(line.getUY_R_Type_ID());
			
			MRCause cause = (MRCause)line.getUY_R_Cause();

			incidencia.setUY_R_Cause_ID(cause.get_ID());
			incidencia.setPriorityBase(cause.getPriorityBase());
			incidencia.setPriorityManual(cause.getPriorityBase());
			
			if (line.getUY_R_SubCause_ID() > 0){
				incidencia.setuy_r_subcause_id_1(line.getUY_R_SubCause_ID());	
			}
			
			incidencia.setisinmediate(false);
			incidencia.setDescription(line.getDescription());
			incidencia.setUY_R_Canal_ID(line.getUY_R_Canal_ID());
			incidencia.setReceptor_ID(MUser.forName(getCtx(), "adempiere", null).get_ID());
			incidencia.setUY_R_Area_ID(MRArea.forValue(getCtx(), "operaciones", null).get_ID());
			incidencia.setUY_R_PtoResolucion_ID(MRPtoResolucion.forValue(getCtx(), "logistica", null).get_ID());
			incidencia.setIsInternalIssue(false);
			incidencia.setNotificationVia(line.getNotificationVia());
			incidencia.setAccountNo(line.getAccountNo());
			incidencia.setUY_R_CanalNotifica_ID(MRCanal.forValue(getCtx(), "operaciones", null).get_ID());
			incidencia.setIsObserver(false);
			incidencia.setReclamoResuelto(false);
			incidencia.setReclamoNotificado(false);
			incidencia.setIsPreNotificacion(false);
			incidencia.setUY_R_ActionType_ID(MRActionType.IDforValue(getCtx(), "clinotifica", null));
			incidencia.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(), today, 0));
			incidencia.setDocStatus(X_UY_R_Reclamo.DOCSTATUS_Drafted);
			incidencia.setDocAction(X_UY_R_Reclamo.DOCACTION_Apply);
			incidencia.setUY_R_CedulaCuenta_ID(line.getUY_R_CedulaCuenta_ID());
			

			// 
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select stapenom,stcallenro,isnull(stdepto,'') as stdepto, " +
						 " isnull(clitel,'NO TIENE') as clitel, isnull(clicelular,'NO TIENE') as clicelular, " +
						 " isnull(stcodpos,0) as stcodpos, isnull(stnrocta,0) as stnrocta, isnull(climail,'NO TIENE - SOLICITAR !!') as climail, " +
						 " stnrotarj, stvenctarj, isnull(stlimcred,0) as stlimcred,  isnull(lccod,'') as producto, " +
						 " isnull(stderinro,0) as sttiposoc, isnull(nombre,'') as nombreapellido, " +
						 " GrpCtaCte, GAFCOD, MLCod, Tasas, Cargos, Parametros " +
					     " from q_clientes_adempiere " +
					     " where clicod=" + line.getCedula() +
					     " order by sttiposoc ";
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){

				int codPostal = rs.getInt("stcodpos");
				
				String strCodPostal = (codPostal > 0) ? String.valueOf(codPostal) : "NO TIENE";
				String apto = "";
				if (rs.getString("stdepto") != null){
					if (!rs.getString("stdepto").trim().equalsIgnoreCase("")){
						apto = " - Apto.: " + rs.getString("stdepto");
					}
				}
				
				String email = rs.getString("climail");
				if ((email == null) || (email.trim().equalsIgnoreCase(""))){
					email = "NO TIENE - SOLICITAR !!" ;
				}

				String celular = rs.getString("clicelular");
				if ((celular == null) || (celular.trim().equalsIgnoreCase(""))){
					celular = "NO TIENE" ;
				}

				String telefono = rs.getString("clitel");
				if ((telefono == null) || (telefono.trim().equalsIgnoreCase(""))){
					telefono = "NO TIENE" ;
				}

				incidencia.setCustomerName(rs.getString("stapenom"));
				incidencia.setDirection(rs.getString("stcallenro") + apto);
				incidencia.setTelephone(telefono);
				incidencia.setMobile(celular);
				incidencia.setPostal(strCodPostal);
				incidencia.setEMail(email);
				incidencia.setName(rs.getString("nombreapellido"));

			}
			
			rs.close();
			con.close();

			incidencia.saveEx();

			MRCedulaCuenta cedcta = (MRCedulaCuenta)line.getUY_R_CedulaCuenta();
 			cedcta.setUY_R_Reclamo_ID(incidencia.get_ID());
			cedcta.setInternalCode(incidencia.getDocumentNo());
			cedcta.saveEx();

			// Aplico inciencia
			if (!incidencia.processIt(DocAction.ACTION_Apply)){
				String message = incidencia.getProcessMsg();
				if (message == null) message = "";
				throw new AdempiereException("No fue posible crear una nueva Incidencia asociada a cedula: " + line.getCedula() + "\n" + incidencia.getProcessMsg());
			}
			
			incidencia.saveEx();
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}

	}
	
	private Connection getFDUConnection(MFduConnectionData fduData) throws Exception {
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			if(fduData != null){
				
				connectString = "jdbc:sqlserver://" + fduData.getserver_ip() + "\\" + fduData.getServer() + 
								";databaseName=" + fduData.getdatabase_name() + ";user=" + fduData.getuser_db() + 
								";password=" + fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, fduData.getuser_db(), fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}

}
