/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 14, 2015
*/
package org.openup.model;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.compiere.model.MActivity;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocation;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MCFEInbox
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Dec 14, 2015
*/
public class MCFEInbox extends X_UY_CFE_Inbox implements DocAction {

	private static final long serialVersionUID = 2162602420938291822L;

	private String processMsg = null;
	private boolean justPrepared = false;
	
	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_CFE_Inbox_ID
	 * @param trxName
	*/

	public MCFEInbox(Properties ctx, int UY_CFE_Inbox_ID, String trxName) {
		super(ctx, UY_CFE_Inbox_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MCFEInbox(Properties ctx, ResultSet rs, String trxName) {
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
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
	 * Lee archivo excel y carga información en tablas finales.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Dec 14, 2015
	 * @return
	 */
	public String processExcelFile(MCFEInboxLoad cfeLoad){
		
		String message = null;

		Workbook wb = null;
		
		try {
			
			// Verifico que tenga archivo seleccionado
			if ((cfeLoad.getFileName() == null) || (cfeLoad.getFileName().trim().equalsIgnoreCase(""))){
				message = "Debe indicar archivo excel a procesar con extensión : xlsx";
				return message;
			}
			
			// Instancio workbook
			wb = new XSSFWorkbook(new FileInputStream(cfeLoad.getFileName()));

			// Verifico que tenga al menos una pagina
			if (wb.getNumberOfSheets() <= 0){
				message = "La planilla seleccionada no tiene hojas.";
				return message;
			}
			
			// Elimino viejas inconsistencias que pudieran estar asociadas a esta carga
			DB.executeUpdateEx(" delete from UY_CFE_InboxLoadIssue where UY_CFE_InboxLoad_ID =" + cfeLoad.get_ID(), get_TrxName());
			
            Sheet sheet = wb.getSheetAt(0);

            boolean hayInconsistencias = false;
            
            MCFEInboxLine inboxLine = null;
            
            // Atributos de cabezal
            String tipoDoc = "", nroDoc = "", fecTrans = "", descripcion = "", textoImpresion = "";
            String nombreCliente = "", cedulaCliente = "";
            String departamento = "", direccion = "", ciudad = "", codPostal = "";
            String moneda = "", terminoPago = "";
            BigDecimal subTotalHdr = Env.ZERO, totalHdr = Env.ONE;

            // Atributos de las lineas de comprobantes
            String codProducto = "", ccDepto = "", ccProd = "", ccAfin = "", tipoImpu = "";
            BigDecimal cantidad = Env.ZERO, precio = Env.ZERO, amtTax = Env.ZERO, netoLinea = Env.ZERO, totalLinea = Env.ZERO;
            		
            for (Row row : sheet) {
            	
            	// Si tengo tipo de documento en primer columna, estoy leyendo una row cabezal 
            	tipoDoc = "";
            	Cell cellTipoDoc = row.getCell(0);
            	if (cellTipoDoc != null){
    				try {
    					tipoDoc = cellTipoDoc.getStringCellValue().trim();	
					} catch (Exception e) {
					}
    				if (!tipoDoc.equalsIgnoreCase("")){
    					// Tengo valor de tipo de documento, ahora valido que sea un tipo de documento valido
    					MDocType doc = MDocType.forValue(getCtx(), tipoDoc, null);
    					if (doc == null){
    						// INCONSISTENCIA
    						hayInconsistencias = true;

    						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
    	    				issue.setsheet("1");
    	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
    	    				issue.setcell("1");
    	    				issue.setRowNo(row.getRowNum());
    	    				issue.setErrorMsg("No se indica valor para Tipo de Documento");
    	    				issue.saveEx();
    						
    						continue;
    					}
    					else{

    						// Estoy en linea de cabezal continua leyendo columnas del mismo
    						// Columna 2: Numero interno de comprobante
    						nroDoc = "";
    						Cell cellNroDoc = row.getCell(1);
                			if (cellNroDoc != null){
                				try {
                					nroDoc = cellNroDoc.getStringCellValue().trim();	
    							} catch (Exception e) {
    								nroDoc = "E";
    							}
                				if (nroDoc.equalsIgnoreCase("E")){
                					nroDoc = String.valueOf(new BigDecimal(cellNroDoc.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
                				}
                			}

    						// Columna 3: Fecha de Transaccion
                			fecTrans = "";
                			Cell cellFecTrans = row.getCell(2);
                			if (cellFecTrans != null){
                				try {
                					//fecTrans = String.valueOf(new BigDecimal(cellFecTrans.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
                					Date dateTrans = cellFecTrans.getDateCellValue();
                    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00"); 
                        			fecTrans = sdf.format(dateTrans);

                				} catch (Exception e) {
    								fecTrans = "E";
    							}
                				if (fecTrans.equalsIgnoreCase("E")){
                    				Date dateTrans = cellFecTrans.getDateCellValue();
                    				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy"); 
                        			fecTrans = sdf.format(dateTrans);
                				}
                			}
                			
                			// Columna 4: Descripcion con motivo del comprobante
                			descripcion = "";
                			Cell cellDesc = row.getCell(3);
                			if (cellDesc != null){
                				descripcion = cellDesc.getStringCellValue().trim();
                			}

                			// Columna 5: Texto de impresión
                			textoImpresion = "";
                			Cell cellTextoImp = row.getCell(4);
                			if (cellTextoImp != null){
                				textoImpresion = cellTextoImp.getStringCellValue().trim();
                			}

                			// Columna 6: Nombre cliente
                			nombreCliente = "";
                			Cell cellNombre = row.getCell(5);
                			if (cellNombre != null){
                				nombreCliente = cellNombre.getStringCellValue().trim();
                			}
                			
                			// Columna 7: Cedula o RUT del cliente
                			cedulaCliente = "";
                			Cell cellCedula = row.getCell(6);
                			if (cellCedula != null){
                				try {
                					cedulaCliente = cellCedula.getStringCellValue().trim();	
    							} catch (Exception e) {
    								cedulaCliente = "E";
    							}
                				if (cedulaCliente.equalsIgnoreCase("E")){
                					cedulaCliente = String.valueOf(new BigDecimal(cellCedula.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
                				}
                			}

                			// Columna 8: Direccion
                			direccion = "";
                			Cell cellDir = row.getCell(7);
                			if (cellDir != null){
                				direccion = cellDir.getStringCellValue().trim();
                			}

                			// Columna 9: Departamento
                			departamento = "";
                			Cell cellDpto = row.getCell(8);
                			if (cellDpto != null){
                				departamento = cellDpto.getStringCellValue().trim();
                			}
            				MDepartamentos mdep = MDepartamentos.forNameAndCountry(getCtx(), departamento, 336, null);
            				if ((mdep == null) || (mdep.get_ID() <= 0)){

            					// INCONSISTENCIA
            					hayInconsistencias = true;

        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setsheet("1");
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setcell("9");
        	    				issue.setRowNo(row.getRowNum());
        	    				issue.setErrorMsg("No se indica valor para Departamento");
        	    				issue.saveEx();

            					continue;
            				}

                			// Columna 10: Ciudad
                			ciudad = "";
                			Cell cellCiudad = row.getCell(9);
                			if (cellCiudad != null){
                				ciudad = cellCiudad.getStringCellValue().trim();
                			}
                			MLocalidades mlocalidades = MLocalidades.forNameAndDpto(getCtx(), ciudad, mdep.get_ID(), null);
                			if ((mlocalidades == null) || (mlocalidades.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;
            				
        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setsheet("1");
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setcell("10");
        	    				issue.setRowNo(row.getRowNum());
        	    				issue.setErrorMsg("No se indica valor para Localidad");
        	    				issue.saveEx();
            					
            					continue;
                			}

                			// Columna 11: Codigo postal
                			codPostal = "";
                			Cell cellCodPostal = row.getCell(10);
                			if (cellCodPostal != null){
                				try {
                					codPostal = cellCodPostal.getStringCellValue().trim();	
    							} catch (Exception e) {
    								codPostal = "E";
    							}
                				if (codPostal.equalsIgnoreCase("E")){
                					codPostal = String.valueOf(new BigDecimal(cellCodPostal.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
                				}
                			}
                			
                			// Columna 12: Moneda
                			moneda = "";
                			MCurrency cur = null;
                			Cell cellMoneda = row.getCell(11);
                			if (cellMoneda != null){
                				try {
                					moneda = cellMoneda.getStringCellValue().trim();	
    							} catch (Exception e) {
    								moneda = "E";
    							}
                				if (moneda.equalsIgnoreCase("E")){
                					moneda = String.valueOf(new BigDecimal(cellMoneda.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
                				}

                				cur = new MCurrency(getCtx(), Integer.parseInt(moneda), null);
                    			if ((cur == null) || (cur.get_ID() <= 0)){
                					// INCONSISTENCIA
                					hayInconsistencias = true;

            						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
            	    				issue.setsheet("1");
            	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
            	    				issue.setcell("12");
            	    				issue.setRowNo(row.getRowNum());
            	    				issue.setErrorMsg("No se indica valor correcto para Moneda");
            	    				issue.saveEx();
                					
                					continue;
                    			}

                			}
                			
                			// Columna 13: Termino de Pago
                			terminoPago = "";
                			MPaymentTerm payTerm = null;
                			Cell cellTerminoPago = row.getCell(12);
                			if (cellTerminoPago != null){
                				try {
                					terminoPago = cellTerminoPago.getStringCellValue().trim();	
    							} catch (Exception e) {
    								terminoPago = "E";
    							}
                				if (terminoPago.equalsIgnoreCase("E")){
                					terminoPago = String.valueOf(new BigDecimal(cellTerminoPago.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
                				}
                				
                    			payTerm = MPaymentTerm.forValue(getCtx(), terminoPago, null);
                    			
                    			if ((payTerm == null) || (payTerm.get_ID() <= 0)){
                    				payTerm = MPaymentTerm.forValue(getCtx(), "credito", null);
                    			}
                    			
                    			if ((payTerm == null) || (payTerm.get_ID() <= 0)){
                					// INCONSISTENCIA
                					hayInconsistencias = true;
                					
            						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
            	    				issue.setsheet("1");
            	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
            	    				issue.setcell("13");
            	    				issue.setRowNo(row.getRowNum());
            	    				issue.setErrorMsg("No se indica valor correcto para Termino de Pago");
            	    				issue.saveEx();
                					
                					continue;
                    			}
                			}
                			
                			// Columna 14: Subtotal cabezal
                			subTotalHdr = Env.ZERO;
                			Cell cellSubTotalHdr = row.getCell(13);
                			if (cellSubTotalHdr != null){
                				subTotalHdr = new BigDecimal(cellSubTotalHdr.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                			}

                			// Columna 15: Total cabezal
                			totalHdr = Env.ZERO;
                			Cell cellTotalHdr = row.getCell(14);
                			if (cellTotalHdr != null){
                				totalHdr = new BigDecimal(cellTotalHdr.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                			}
                			
                			// Si no hay inconsistencias
                			if (!hayInconsistencias){
                    			// Guardo linea de bandeja con cabezal de comprobante
                    			inboxLine = new MCFEInboxLine(getCtx(), 0, get_TrxName());
                    			inboxLine.setUY_CFE_Inbox_ID(this.get_ID());
                    			inboxLine.setC_DocType_ID(doc.get_ID());
                    			if ((nroDoc != null) && (!nroDoc.equalsIgnoreCase(""))){
                    				inboxLine.setnrodoc(nroDoc);	
                    			}                			
                    			if ((fecTrans != null) && (!fecTrans.equalsIgnoreCase(""))){
                    				inboxLine.setDateTrx(Timestamp.valueOf(fecTrans));	
                    			}                			
                    			inboxLine.setDescription(descripcion);
                    			inboxLine.setobservaciones(textoImpresion);
                    			boolean isNewBP = false;
                    			MBPartner bp = MBPartner.forName2(getCtx(), nombreCliente, null);
                    			if (bp == null){
                    				isNewBP = true;
                    				bp = new MBPartner(getCtx(), 0, get_TrxName());
                    				bp.setName(nombreCliente);
                    				bp.setName2(nombreCliente);
                    				bp.setIsCustomer(true);
                    				bp.setIsVendor(false);
                    				bp.setIsSalesRep(false);
                    				bp.saveEx();
                    			}
                    			inboxLine.setC_BPartner_ID(bp.get_ID());
                    			inboxLine.setName(bp.getName2());
                    			if ((cedulaCliente != null) && (!cedulaCliente.equalsIgnoreCase(""))){                				
                    				if ((bp.getDUNS() == null) || (bp.getDUNS().equalsIgnoreCase(cedulaCliente))){
                    					bp.setDUNS(cedulaCliente);
                    					bp.saveEx();
                    				}                				
                    			}
                    			inboxLine.setDUNS(bp.getDUNS());
                    			if (isNewBP){
                    				MLocation location = new MLocation(getCtx(), 0, get_TrxName());
                    				location.setC_Country_ID(336);
                    				location.setUY_Departamentos_ID(mdep.get_ID());
                    				location.setUY_Localidades_ID(mlocalidades.get_ID());
                    				location.setAddress1(direccion);
                    				location.setPostal(codPostal);
                    				location.saveEx();
                    				
                    				MBPartnerLocation bpLoc = new MBPartnerLocation(bp);
                    				bpLoc.setC_Location_ID(location.get_ID());
                    				bpLoc.setC_Country_ID(location.getC_Country_ID());
                    				bpLoc.setUY_Departamentos_ID(location.getUY_Departamentos_ID());
                    				bpLoc.setUY_Localidades_ID(location.getUY_Localidades_ID());
                    				bpLoc.setAddress1(location.getAddress1());
                    				bpLoc.setPostal(bpLoc.getPostal());
                    				bpLoc.saveEx();
                    			}
                    			inboxLine.setC_Country_ID(336);
                    			inboxLine.setUY_Departamentos_ID(mdep.get_ID());                			
                    			inboxLine.setUY_Localidades_ID(mlocalidades.get_ID());
                    			inboxLine.setAddress1(direccion);
                    			inboxLine.setPostal(codPostal);
                    			if ((cur != null) && (cur.get_ID() > 0)){
                    				inboxLine.setC_Currency_ID(cur.get_ID());	
                    			}
                    			if ((payTerm != null) && (payTerm.get_ID() > 0)){
                    				inboxLine.setC_PaymentTerm_ID(payTerm.get_ID());	
                    			}
                    			
                    			inboxLine.setTotalLines(subTotalHdr);
                    			inboxLine.setGrandTotal(totalHdr);
                    			inboxLine.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
                    			inboxLine.setSourceType(X_UY_CFE_InboxLine.SOURCETYPE_EXCEL);
                    			inboxLine.saveEx();
                			}
    					}
    				}
    				else{
						// INCONSISTENCIA
						hayInconsistencias = true;
						inboxLine = null;

						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
	    				issue.setsheet("1");
	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
	    				issue.setcell("1");
	    				issue.setRowNo(row.getRowNum());
	    				issue.setErrorMsg("No se indica valor para Tipo de Documento");
	    				issue.saveEx();
						
						continue;
    				}
             	}
            	else{
            		// No estoy en linea de cabezal, busco producto si es que tengo cabezal, sino no leo nada de esta linea
            		if ((inboxLine != null) && (inboxLine.get_ID() > 0)){
            			
            			// Columna 16: Producto
            			codProducto = "";
            			Cell cellProducto = row.getCell(15);
            			if (cellProducto != null){
            				try {
            					codProducto = cellProducto.getStringCellValue().trim();	
							} catch (Exception e) {
								codProducto = "E";
							}
            				if (codProducto.equalsIgnoreCase("E")){
            					codProducto = String.valueOf(new BigDecimal(cellProducto.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            				}
            			}
            			MProduct mProd = MProduct.forValue(getCtx(), codProducto, null);
            			if ((mProd == null) || (mProd.get_ID() <= 0)){
        					// INCONSISTENCIA
        					hayInconsistencias = true;

    						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
    	    				issue.setsheet("1");
    	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
    	    				issue.setcell("16");
    	    				issue.setRowNo(row.getRowNum());
    	    				issue.setErrorMsg("No se indica valor correcto de Producto");
    	    				issue.saveEx();

        					continue;
            			}
            			
            			// Columna 17: Cantidad
            			cantidad = Env.ZERO;
            			Cell cellCantidad = row.getCell(16);
            			if (cellCantidad != null){
            				cantidad = new BigDecimal(cellCantidad.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
            			}

            			// Columna 18: CC Departamento
            			ccDepto = "";
            			MActivity mCCDepto = null;
            			Cell cellCCDepto = row.getCell(17);
            			if (cellCCDepto != null){
            				try {
            					ccDepto = cellCCDepto.getStringCellValue().trim();	
							} catch (Exception e) {
								ccDepto = "E";
							}
            				if (ccDepto.equalsIgnoreCase("E")){
            					ccDepto = String.valueOf(new BigDecimal(cellCCDepto.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            				}
            				mCCDepto = MActivity.forValue(getCtx(), ccDepto, null);
            				if ((mCCDepto == null) || (mCCDepto.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;
            					
        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setsheet("1");
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setcell("18");
        	    				issue.setRowNo(row.getRowNum());
        	    				issue.setErrorMsg("No se indica valor correcto de CC Departamento");
        	    				issue.saveEx();
            					
            					continue;            					
            				}            				
            			}

            			// Columna 19: CC Producto
            			ccProd = "";
            			MActivity mCCProd = null;
            			Cell cellCCProducto = row.getCell(18);
            			if (cellCCProducto != null){
            				try {
            					ccProd = cellCCProducto.getStringCellValue().trim();	
							} catch (Exception e) {
								ccProd = "E";
							}
            				if (ccProd.equalsIgnoreCase("E")){
            					ccProd = String.valueOf(new BigDecimal(cellCCProducto.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            				}
            				mCCProd = MActivity.forValue(getCtx(), ccProd, null);
            				if ((mCCProd == null) || (mCCProd.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;
            					
        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setsheet("1");
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setcell("19");
        	    				issue.setRowNo(row.getRowNum());
        	    				issue.setErrorMsg("No se indica valor para correcto de CC Producto");
        	    				issue.saveEx();
            					
            					continue;            					
            				}            				
            			}

            			// Columna 20: CC Afinidad
            			ccAfin = "";
            			MActivity mCCAfin = null;
            			Cell cellCCAfin = row.getCell(19);
            			if (cellCCAfin != null){
            				try {
            					ccAfin = cellCCAfin.getStringCellValue().trim();	
							} catch (Exception e) {
								ccAfin = "E";
							}
            				if (ccAfin.equalsIgnoreCase("E")){
            					ccAfin = String.valueOf(new BigDecimal(cellCCAfin.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            				}
            				mCCAfin = MActivity.forValue(getCtx(), ccAfin, null);
            				if ((mCCAfin == null) || (mCCAfin.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;

        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setsheet("1");
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setcell("20");
        	    				issue.setRowNo(row.getRowNum());
        	    				issue.setErrorMsg("No se indica valor correcto de CC Afinidad");
        	    				issue.saveEx();

            					continue;            					
            				}            				
            			}

            			// Columna 21: Precio
            			precio = Env.ZERO;
            			Cell cellPrecio = row.getCell(20);
            			if (cellPrecio != null){
            				precio = new BigDecimal(cellPrecio.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
            			}

            			// Columna 22: Tipo impuesto
            			tipoImpu = "";
            			MTax tx = null;
            			Cell cellTax = row.getCell(21);
            			if (cellTax != null){
            				try {
            					tipoImpu = cellTax.getStringCellValue().trim();	
							} catch (Exception e) {
								tipoImpu = "E";
							}
            				if (tipoImpu.equalsIgnoreCase("E")){
            					tipoImpu = String.valueOf(new BigDecimal(cellTax.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP)).trim();
            				}
            				tx = this.getTax(tipoImpu);
            			}

        				if ((tx == null) || (tx.get_ID() <= 0)){
        					// INCONSISTENCIA
        					hayInconsistencias = true;

    						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
    	    				issue.setsheet("1");
    	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
    	    				issue.setcell("22");
    	    				issue.setRowNo(row.getRowNum());
    	    				issue.setErrorMsg("No se indica valor correcto de Tipo de Impuesto");
    	    				issue.saveEx();

        					continue;            					
        				}            				
            			
            			// Columna 23: Importe impuesto
            			amtTax = Env.ZERO;
            			Cell cellAmtTax = row.getCell(22);
            			if (cellAmtTax != null){
            				amtTax = new BigDecimal(cellAmtTax.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
            			}

            			// Columna 24: Neto Linea
            			netoLinea = Env.ZERO;
            			Cell cellNetoLinea = row.getCell(23);
            			if (cellNetoLinea != null){
            				netoLinea = new BigDecimal(cellNetoLinea.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
            			}

            			// Columna 25: Total Linea
            			totalLinea = Env.ZERO;
            			Cell cellTotalLinea = row.getCell(24);
            			if (cellTotalLinea != null){
            				totalLinea = new BigDecimal(cellTotalLinea.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
            			}

            			if (!hayInconsistencias){
                			// Instancio y persisto modelo de linea de comprobante
                			MCFEInboxProd inboxProd = new MCFEInboxProd(getCtx(), 0, get_TrxName());
                			inboxProd.setUY_CFE_InboxLine_ID(inboxLine.get_ID());
                			inboxProd.setM_Product_ID(mProd.get_ID());
                			inboxProd.setQtyEntered(cantidad);
                			inboxProd.setPriceEntered(precio);
                			
                			if ((mCCDepto != null) && (mCCDepto.get_ID() > 0)){
                				inboxProd.setC_Activity_ID_1(mCCDepto.get_ID());	
                			}
                			
                			if ((mCCProd != null) && (mCCProd.get_ID() > 0)){
                				inboxProd.setC_Activity_ID_2(mCCProd.get_ID());	
                			}
                			
                			if ((mCCAfin != null) && (mCCAfin.get_ID() > 0)){
                				inboxProd.setC_Activity_ID_3(mCCAfin.get_ID());	
                			}
                			
                			if ((tx != null) && (tx.get_ID() > 0)){
                				inboxProd.setC_Tax_ID(tx.get_ID());
                			}
                			
                			inboxProd.setTaxAmt(amtTax);
                			inboxProd.setLineNetAmt(netoLinea);
                			inboxProd.setLineTotalAmt(totalLinea);
                			inboxProd.saveEx();
            			}
            		}
            	}
            }
            
            // Si hubo inconsistencias me aseguro de eliminar todas los documentos originados por esta carga
            if (hayInconsistencias){
            	String action = " delete from uy_cfe_inboxline cascade where uy_cfe_inboxload_id =" + cfeLoad.get_ID();
            	DB.executeUpdateEx(action, get_TrxName());
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
	 * Obtiene y retorna modelo de impuesto para un determinado tipo de impuesto CFE recibido.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Jan 10, 2016
	 * @param tipoImpu
	 * @return
	 */
	private MTax getTax(String tipoImpu) {

		MTax model = null;

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			
			sql = " select max(c_tax_id) as c_tax_id "
					+ " from c_tax "
					+ " where cfetaxcode =? ";
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, tipoImpu);

			rs = pstmt.executeQuery();
			
			if (rs.next()){
				model =  new MTax(getCtx(), rs.getInt(1), null);
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return model;
	}

	/***
	 * Lee datos desde la vista y carga tablas finales.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Dec 14, 2015
	 * @return
	 */
	public String processView(MCFEInboxLoad cfeLoad){
		
		String message = null;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			// Elimino viejas inconsistencias que pudieran estar asociadas a esta carga
			DB.executeUpdateEx(" delete from UY_CFE_InboxLoadIssue where UY_CFE_InboxLoad_ID =" + cfeLoad.get_ID(), get_TrxName());
			
			int nroComp = 0;
			MPeriod period = (MPeriod)this.getC_Period();
			
            boolean hayInconsistencias = false;
            MCFEInboxLine inboxLine = null;
			 
			Date deliveryDate = (Date)cfeLoad.getDateAction();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String whereFecPro = sdf.format(deliveryDate);
			
			MCFEInboxFileType fileType = (MCFEInboxFileType)cfeLoad.getUY_CFE_InboxFileType();
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			
			con = this.getFDUConnection(fduData);
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);	

			sql = " select * "
				+ " from q_adempiere_cfe "
				+ " where FETipoFact='" + fileType.getValue().trim() + "' "
				+ " and FEFecPro='" + whereFecPro + "' "
				+ " order by FETipoFact, FEFecPro, FENumComp, FENumLin";
				
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
            	
				// Tomo numero de comprobante para mostrarlo en inconsistencias
				nroComp = rs.getInt("FENumComp");
				
            	// Si es un registro cabezal
				String tipoLinea = rs.getString("FETipoLin");
				if (tipoLinea == null){
					tipoLinea = "";
				}
				else {
					tipoLinea = tipoLinea.trim();
				}
            	if (tipoLinea.equalsIgnoreCase("CABEZAL")){
    				
            		if (rs.getString("FEDocCFE") != null){

            			// Tengo valor de tipo de documento, ahora valido que sea un tipo de documento valido
    					MDocType doc = MDocType.forValue(getCtx(), rs.getString("FEDocCFE").trim(), null);
    					if (doc == null){
    						// INCONSISTENCIA
    						hayInconsistencias = true;
    						
    						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
    	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
    	    				issue.setRowNo(nroComp);
    	    				issue.setErrorMsg("No se indica valor para Tipo de Documento");
    	    				issue.saveEx();
    						
    						continue;
    					}
    					else{

    						// Valido Departamento
    						String departamento = rs.getString("FECliDpto");
    						if (departamento == null){
    							departamento = "MONTEVIDEO";
    						}
    						else{
    							departamento = departamento.trim();
    							if (departamento.equalsIgnoreCase("")){
    								departamento = "MONTEVIDEO";
    							}
    						}    						
            				MDepartamentos mdep = MDepartamentos.forNameAndCountry(getCtx(), departamento, 336, null);
            				if ((mdep == null) || (mdep.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;

            					MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setRowNo(nroComp);
        	    				issue.setErrorMsg("No se indica valor para Departamento");
        	    				issue.saveEx();

            					continue;
            				}

                			// Valido localidad
                			String ciudad = rs.getString("FECliLoc");
                			if (ciudad == null){
                				ciudad = "MONTEVIDEO";
                			}
                			else{
                				ciudad = ciudad.trim();
                				if (ciudad.equalsIgnoreCase("")){
                					ciudad = "MONTEVIDEO"; 
                				}
                			}
                			
                			MLocalidades mlocalidades = MLocalidades.forNameAndDpto(getCtx(), ciudad, mdep.get_ID(), null);
                			if ((mlocalidades == null) || (mlocalidades.get_ID() <= 0)){
                				// Creo localidad en adempiere para evitar la inconsistencia
                				mlocalidades = new MLocalidades(getCtx(), 0, get_TrxName());
                				mlocalidades.setC_Country_ID(336);
                				mlocalidades.setUY_Departamentos_ID(mdep.get_ID());
                				mlocalidades.setName(ciudad.trim());
                				mlocalidades.saveEx();
                			}

                			if ((mlocalidades == null) || (mlocalidades.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;
            					
        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setRowNo(nroComp);
        	    				issue.setErrorMsg("No se indica valor para Localidad");
        	    				issue.saveEx();
            					
            					continue;
                			}

                			
                			// Valido Moneda
            				MCurrency cur = new MCurrency(getCtx(), rs.getInt("FEMoneda"), null);
                			if ((cur == null) || (cur.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;

            					MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setRowNo(nroComp);
        	    				issue.setErrorMsg("No se indica valor correcto de Moneda");
        	    				issue.saveEx();
            					
            					continue;
                			}

                			// Valido Termino de Pago
                			String terminoPago = rs.getString("FETermPgo");
                			if (terminoPago == null){
                				terminoPago = "credito";
                			}
                			else{
                				terminoPago = terminoPago.trim();
                				if (terminoPago.equalsIgnoreCase("")){
                					terminoPago = "credito";
                				}
                			}
                			MPaymentTerm payTerm = MPaymentTerm.forValue(getCtx(), terminoPago, null);
                			if ((payTerm == null) || (payTerm.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;
            					
        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setRowNo(nroComp);
        	    				issue.setErrorMsg("No se indica valor correcto de Termino de Pago");
        	    				issue.saveEx();
            					
            					continue;
                			}
                			
                			// Si no hay inconsistencias
                			if (!hayInconsistencias){
                    			// Guardo linea de bandeja con cabezal de comprobante
                    			inboxLine = new MCFEInboxLine(getCtx(), 0, get_TrxName());
                    			inboxLine.setUY_CFE_Inbox_ID(this.get_ID());
                    			inboxLine.setC_DocType_ID(doc.get_ID());
                    			inboxLine.setnrodoc(String.valueOf(rs.getInt("FENumComp")));
                    			
                    			Timestamp fecTrans = rs.getTimestamp("FEFecPro");
                    			if (fecTrans != null){
                    				if (fecTrans.after(period.getStartDate())){
                    					inboxLine.setDateTrx(fecTrans);
                    				}
                    			}
                    			
                    			String feMotivo = rs.getString("FEMotivo");
                    			if (feMotivo != null) feMotivo = feMotivo.trim();
                    			String feTexto = rs.getString("FETexto");
                    			if (feTexto != null) feTexto = feTexto.trim();
                    			
                    			inboxLine.setDescription(feMotivo);
                    			inboxLine.setobservaciones(feTexto);
                    			
                    			boolean isNewBP = false;
                    			String nombreCliente = rs.getString("FECliNom");
                    			if (nombreCliente != null){
                    				nombreCliente = nombreCliente.trim();
                    				if (!nombreCliente.equalsIgnoreCase("")){
                            			MBPartner bp = MBPartner.forName2(getCtx(), nombreCliente, null);
                            			if (bp == null){
                            				isNewBP = true;
                            				bp = new MBPartner(getCtx(), 0, get_TrxName());
                            				bp.setName(nombreCliente);
                            				bp.setName2(nombreCliente);
                            				bp.setIsCustomer(true);
                            				bp.setIsVendor(false);
                            				bp.setIsSalesRep(false);
                            				bp.saveEx();
                            			}
                            			
                            			inboxLine.setC_BPartner_ID(bp.get_ID());
                            			inboxLine.setName(bp.getName2());
                            			
                            			String cedulaCliente = rs.getString("FECliDoc");
                            			if (cedulaCliente == null){
                            				cedulaCliente = "";
                            			}
                            			else{
                            				cedulaCliente = cedulaCliente.trim();
                            			}
                            			if ((cedulaCliente != null) && (!cedulaCliente.equalsIgnoreCase(""))){                				
                            				if ((bp.getCedula() == null) || (bp.getCedula().equalsIgnoreCase(cedulaCliente))){
                            					bp.setCedula(cedulaCliente);
                            					bp.saveEx();
                            				}                				
                            			}
                            			
                            			if (bp.getDUNS() != null){
                            				if (!bp.getDUNS().trim().equalsIgnoreCase("")){
                            					inboxLine.setDUNS(bp.getDUNS());		
                            				}
                            			}
                            			
                            			if (bp.getCedula() != null){
                            				if (!bp.getCedula().trim().equalsIgnoreCase("")){
                            					inboxLine.setDUNS(bp.getCedula().trim());		
                            				}
                            			}
                            			
                            			if (isNewBP){
                            				MLocation location = new MLocation(getCtx(), 0, get_TrxName());
                            				location.setC_Country_ID(336);
                            				location.setUY_Departamentos_ID(mdep.get_ID());
                            				location.setUY_Localidades_ID(mlocalidades.get_ID());
                            				location.setAddress1(rs.getString("FECliDir"));
                            				location.setPostal(rs.getString("FECliCP"));
                            				location.saveEx();
                            				
                            				MBPartnerLocation bpLoc = new MBPartnerLocation(bp);
                            				bpLoc.setC_Location_ID(location.get_ID());
                            				bpLoc.setC_Country_ID(location.getC_Country_ID());
                            				bpLoc.setUY_Departamentos_ID(location.getUY_Departamentos_ID());
                            				bpLoc.setUY_Localidades_ID(location.getUY_Localidades_ID());
                            				bpLoc.setAddress1(location.getAddress1());
                            				bpLoc.setPostal(bpLoc.getPostal());
                            				bpLoc.saveEx();
                            			}
                        			}
                    			}

                    			inboxLine.setC_Country_ID(336);
                    			inboxLine.setUY_Departamentos_ID(mdep.get_ID());                			
                    			inboxLine.setUY_Localidades_ID(mlocalidades.get_ID());
                    			inboxLine.setAddress1(rs.getString("FECliDir"));
                    			inboxLine.setPostal(rs.getString("FECliCP"));
                    			if ((cur != null) && (cur.get_ID() > 0)){
                    				inboxLine.setC_Currency_ID(cur.get_ID());	
                    			}
                    			if ((payTerm != null) && (payTerm.get_ID() > 0)){
                    				inboxLine.setC_PaymentTerm_ID(payTerm.get_ID());	
                    			}
                    			
                    			inboxLine.setTotalLines(rs.getBigDecimal("FESubTot"));
                    			inboxLine.setGrandTotal(rs.getBigDecimal("FETotal"));
                    			inboxLine.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
                    			inboxLine.setSourceType(X_UY_CFE_InboxLine.SOURCETYPE_VISTA);
                    			inboxLine.saveEx();
                			}
    					}
    				}
             	}
            	else{
            		// No estoy en linea de cabezal, busco producto si es que tengo cabezal, sino no leo nada de esta linea
            		if ((inboxLine != null) && (inboxLine.get_ID() > 0)){
            			
            			String codProducto = rs.getString("FEProd");
            			MProduct mProd = null;
            			if (codProducto != null){
            				codProducto = codProducto.trim();
                			mProd = MProduct.forValue(getCtx(), codProducto, null);
                			if ((mProd == null) || (mProd.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;
            					
        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setRowNo(nroComp);
        	    				issue.setErrorMsg("No se indica valor correcto de Producto");
        	    				issue.saveEx();
            					
            					continue;
                			}
            			}
            			else{
        					// INCONSISTENCIA
        					hayInconsistencias = true;
        					
    						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
    	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
    	    				issue.setRowNo(nroComp);
    	    				issue.setErrorMsg("No se indica valor de Producto");
    	    				issue.saveEx();
        					
        					continue;
            			}

            			String ccDepto = rs.getString("FECCDpto");
            			if (ccDepto == null){
            				ccDepto = "";
            			}
            			ccDepto = ccDepto.trim();
            			MActivity mCCDepto = null;
            			if (!ccDepto.equalsIgnoreCase("")){
                			mCCDepto = MActivity.forValue(getCtx(), ccDepto.trim(), null);
            				if ((mCCDepto == null) || (mCCDepto.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;
            					
        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setRowNo(nroComp);
        	    				issue.setErrorMsg("No se indica valor correcto de CC Departamento");
        	    				issue.saveEx();
            					
            					continue;            					
            				}            				
            			}
            				 
            			
        				String ccProd = rs.getString("FECCProd");
            			if (ccProd == null){
            				ccProd = "";
            			}
            			ccProd = ccProd.trim();
            			MActivity mCCProd = null;
            			if (!ccProd.equalsIgnoreCase("")){
                			mCCProd = MActivity.forValue(getCtx(), ccProd.trim(), null);
            				if ((mCCProd == null) || (mCCProd.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;
            					
        						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setRowNo(nroComp);
        	    				issue.setErrorMsg("No se indica valor correcto de CC Producto");
        	    				issue.saveEx();
            					
            					continue;            					
            				}
            			}
        				
            			String ccAfin = rs.getString("FECCAfi");
            			if (ccAfin == null){
            				ccAfin = "";
            			}
            			ccAfin = ccAfin.trim();
            			MActivity mCCAfin = null;
            			if (!ccAfin.equalsIgnoreCase("")){
                			mCCAfin = MActivity.forValue(getCtx(), ccAfin, null);
            				if ((mCCAfin == null) || (mCCAfin.get_ID() <= 0)){
            					// INCONSISTENCIA
            					hayInconsistencias = true;

            					MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
        	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
        	    				issue.setRowNo(nroComp);
        	    				issue.setErrorMsg("No se indica valor correcto de CC Afinidad");
        	    				issue.saveEx();
            					
            					continue;            					
            				}
            			}

            			int tipoImpu = rs.getInt("FETipoImp");
            			MTax tx = this.getTax(String.valueOf(tipoImpu));
        				if ((tx == null) || (tx.get_ID() <= 0)){
        					// INCONSISTENCIA
        					hayInconsistencias = true;
        					
    						MCFEInboxLoadIssue issue = new MCFEInboxLoadIssue(getCtx(), 0, get_TrxName());
    	    				issue.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
    	    				issue.setRowNo(nroComp);
    	    				issue.setErrorMsg("No se indica valor correcto de Tipo de Impuesto");
    	    				issue.saveEx();
        					
        					continue;            					
        				}            				
            			
            			if (!hayInconsistencias){
                			// Instancio y persisto modelo de linea de comprobante
                			MCFEInboxProd inboxProd = new MCFEInboxProd(getCtx(), 0, get_TrxName());
                			inboxProd.setUY_CFE_InboxLine_ID(inboxLine.get_ID());
                			inboxProd.setM_Product_ID(mProd.get_ID());
                			inboxProd.setQtyEntered(rs.getBigDecimal("FECant"));
                			inboxProd.setPriceEntered(rs.getBigDecimal("FEPrecio"));
                			
                			if ((mCCDepto != null) && (mCCDepto.get_ID() > 0)){
                				inboxProd.setC_Activity_ID_1(mCCDepto.get_ID());	
                			}
                			
                			if ((mCCProd != null) && (mCCProd.get_ID() > 0)){
                				inboxProd.setC_Activity_ID_2(mCCProd.get_ID());	
                			}
                			
                			if ((mCCAfin != null) && (mCCAfin.get_ID() > 0)){
                				inboxProd.setC_Activity_ID_3(mCCAfin.get_ID());	
                			}
                			
                			if ((tx != null) && (tx.get_ID() > 0)){
                				inboxProd.setC_Tax_ID(tx.get_ID());
                			}
                			
                			inboxProd.setTaxAmt(rs.getBigDecimal("FEImpIVA"));
                			inboxProd.setLineNetAmt(rs.getBigDecimal("FENetoLin"));
                			inboxProd.setLineTotalAmt(rs.getBigDecimal("FETotLin"));
                			inboxProd.saveEx();
            			}
            		}
            	}
            }
            
            // Si hubo inconsistencias me aseguro de eliminar todas los documentos originados por esta carga
            if (hayInconsistencias){
            	String action = " delete from uy_cfe_inboxline cascade where uy_cfe_inboxload_id =" + cfeLoad.get_ID();
            	DB.executeUpdateEx(action, get_TrxName());
            }

			rs.close();
			con.close();			

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
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
		
		return message;
	}
	
	/***
	 * Lee datos de comprobantes manuales y los carga en tablas finales.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Dec 14, 2015
	 * @return
	 */
	public String processManualDocs(MCFEInboxLoad cfeLoad){
		
		String message = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			// Elimino registros anteriores no enviados aun a DGI
			String action = " delete from uy_cfe_inboxline cascade where uy_cfe_inbox_id =" + this.get_ID() + 
							" and sourcetype='" + X_UY_CFE_InboxLine.SOURCETYPE_MANUAL + "' " +
							" and dgistatus ='" + X_UY_CFE_InboxLine.DGISTATUS_PENDIENTEENVIO + "'";
			DB.executeUpdateEx(action, get_TrxName());
			
			MPeriod period = (MPeriod)this.getC_Period();
			
			sql = " select h.c_invoice_id, h.documentno, h.dateinvoiced, h.description, h.c_currency_id, "
					+ " h.c_doctypetarget_id, h.c_bpartner_id, h.c_paymentterm_id, h.c_bpartner_location_id,"
					+ " h.totallines, h.grandtotal, h.docstatus, "
					+ " l.m_product_id, l.qtyinvoiced, round(l.priceactual,2) as priceactual, l.linenetamt, "
					+ " l.c_tax_id, l.taxamt,l.linetotalamt, "
					+ " l.c_activity_id_1, l.c_activity_id_2, l.c_activity_id_3 "
					+ " from c_invoice h "
					+ " inner join c_invoiceline l on h.c_invoice_id = l.c_invoice_id "
					+ " inner join c_doctype doc on h.c_doctypetarget_id = doc.c_doctype_id "
					+ " where h.docstatus IN('DR', 'IN') "
					+ " and h.issotrx='Y' "
					+ " and h.dateinvoiced between ? and ? "
					+ " and doc.iscfe ='Y' "
					+ " order by c_invoice_id";
			
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setTimestamp(1, period.getStartDate());
			pstmt.setTimestamp(2, period.getEndDate());
			
			rs = pstmt.executeQuery();
			
			int cInvoiceIDAux = 0;
			MCFEInboxLine line = null;
			
			while(rs.next()){
			
				// Si cambia el id del comprobante
				if (rs.getInt("c_invoice_id") != cInvoiceIDAux){
					
					// Nuevo cabezal de comprobante
					cInvoiceIDAux = rs.getInt("c_invoice_id");
					
					MDocType doc = new MDocType(getCtx(), rs.getInt("c_doctypetarget_id"), null);
					MBPartner bp = new MBPartner(getCtx(), rs.getInt("c_bpartner_id"), null);
					MBPartnerLocation bpLoc = new MBPartnerLocation(getCtx(), rs.getInt("c_bpartner_location_id"), null);
					MLocation location = (MLocation)bpLoc.getC_Location();
					
					line = new MCFEInboxLine(getCtx(), 0, get_TrxName());
					line.setUY_CFE_Inbox_ID(this.get_ID());
					line.setUY_CFE_InboxLoad_ID(cfeLoad.get_ID());
					line.setC_Invoice_ID(cInvoiceIDAux);
					line.setCfeType(doc.get_ValueAsString("CfeType"));
					line.setC_DocType_ID(doc.get_ID());
					line.setnrodoc(rs.getString("documentno"));
					line.setDateTrx(rs.getTimestamp("dateinvoiced"));
					line.setDescription(rs.getString("description"));
					line.setC_BPartner_ID(bp.get_ID());
					line.setName(bp.getName2());
					if ((bp.getCedula() != null) && (!bp.getCedula().trim().equalsIgnoreCase(""))){
						line.setDUNS(bp.getCedula().trim());
					}
					else{
						if (bp.getDUNS() != null){
							line.setDUNS(bp.getDUNS().trim());	
						}
					}
					line.setC_Currency_ID(rs.getInt("c_currency_id"));
					line.setC_PaymentTerm_ID(rs.getInt("c_paymentterm_id"));
					line.setTotalLines(rs.getBigDecimal("totallines"));
					line.setGrandTotal(rs.getBigDecimal("grandtotal"));
					line.setDocStatus(rs.getString("docstatus"));
					line.setC_Country_ID(336);  // Fijo Uruguay por ahora
					line.setUY_Departamentos_ID(location.getUY_Departamentos_ID());
					line.setUY_Localidades_ID(location.getUY_Localidades_ID());
					line.setAddress1(location.getAddress1());
					line.setPostal(location.getPostal());
					line.setIsSelected(true);
					line.setPrintDoc(false);
					line.setSourceType(X_UY_CFE_InboxLine.SOURCETYPE_MANUAL);
					line.saveEx();
				}
				
				// Guardo producto de este comprobante
				MCFEInboxProd inboxProd = new MCFEInboxProd(getCtx(), 0, get_TrxName());
				inboxProd.setUY_CFE_InboxLine_ID(line.get_ID());
				inboxProd.setM_Product_ID(rs.getInt("m_product_id"));
				inboxProd.setQtyEntered(rs.getBigDecimal("qtyinvoiced"));
				inboxProd.setPriceEntered(rs.getBigDecimal("priceactual"));
				
				if (rs.getInt("c_activity_id_1") > 0){
					inboxProd.setC_Activity_ID_1(rs.getInt("c_activity_id_1"));	
				}
				
				if (rs.getInt("c_activity_id_2") > 0){
					inboxProd.setC_Activity_ID_2(rs.getInt("c_activity_id_2"));	
				}
				
				if (rs.getInt("c_activity_id_3") > 0){
					inboxProd.setC_Activity_ID_3(rs.getInt("c_activity_id_3"));	
				}
				
				inboxProd.setC_Tax_ID(rs.getInt("c_tax_id"));
				inboxProd.setTaxAmt(rs.getBigDecimal("taxamt"));
				inboxProd.setLineNetAmt(rs.getBigDecimal("linenetamt"));
				inboxProd.setLineTotalAmt(rs.getBigDecimal("linetotalamt"));
				
				inboxProd.saveEx();
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return message;
	}
	

	/***
	 * Realiza y retorna una nueva conexion a base de datos origen de la información.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 3, 2015
	 * @param fduData
	 * @return
	 * @throws Exception
	 */
	private Connection getFDUConnection(MFduConnectionData fduData){
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			
			if (fduData != null){
				
				connectString = "jdbc:sqlserver://" + fduData.getserver_ip() + "\\" + fduData.getServer() + 
								";databaseName=" + fduData.getdatabase_name() + ";user=" + fduData.getuser_db() + 
								";password=" + fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, fduData.getuser_db(), fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return retorno;
	}

	/***
	 * Envía información de comprobantes electrónicos a DGI.
	 * Previamente genera comprobantes en tablas finales, los completa y los contabiliza.
	 * Cada comprobante se procesa en una transacción dedicada.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Jan 13, 2016
	 * @return
	 */
	public String sendData(){

		String message = null;
		
		try {
			// Obtengo comprobantes a procesar segun condiciones
			List<MCFEInboxLine> lines = this.getDocsToSend();
			for (MCFEInboxLine line: lines){
				
				// Obtengo modelo invoice asociado a este comprobante
				// Si aun no tengo modelo asociado (carga por vista o excel) este el momento de crearlo
				if (line.getC_Invoice_ID() <= 0){
					line.generateInvoice();
				}
				MInvoice invoice = (MInvoice)line.getC_Invoice();
				
				// Completo invoice. En este momento se envía a DGI y se contabiliza automáticamente
				if (invoice.getDocStatus().equalsIgnoreCase(DOCSTATUS_Drafted)){
					if (!invoice.processIt(ACTION_Complete)){
						throw new AdempiereException(invoice.getProcessMsg());
					}
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return message;
	}

	/***
	 * Obtiene y retorna documentos aptos para ser enviados a DGI segun determinadas condiciones.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Jan 13, 2016
	 * @return
	 */
	private List<MCFEInboxLine> getDocsToSend(){
		
		String whereClause = X_UY_CFE_InboxLine.COLUMNNAME_UY_CFE_Inbox_ID + "=" + this.get_ID();
		
		List<MCFEInboxLine> lines = new Query(getCtx(), I_UY_CFE_InboxLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
	}
}
