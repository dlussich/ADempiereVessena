package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
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
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.util.ItalcredSystem;

/**
 * @author Sylvie Bouissa
 *
 */

public class MTTPrintVale extends X_UY_TT_PrintVale implements DocAction {

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
	
	private String processMsg = null;
	private boolean justPrepared = false;
	
	
	public MTTPrintVale(Properties ctx, int UY_TT_PrintVale_ID, String trxName) {
		super(ctx, UY_TT_PrintVale_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MTTPrintVale(Properties ctx, ResultSet rs, String trxName) {
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
		try {
			
			this.deleteOldData();
			this.deleteOldError();
			this.loadData();	

			if(!this.hayErrores()){
				this.setDocAction(DocAction.ACTION_Complete);
				this.setDocStatus(DocumentEngine.STATUS_Applied);
			} else {

				this.deleteOldData();
				throw new AdempiereException ("Carga finalizada con errores, verifique las inconsistencias");
			}

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		return true;
	}

	private boolean hayErrores() {
		// TODO Auto-generated method stub
		String sql = "select uy_xlsissue_id from uy_xlsissue where ad_table_id = " + get_Table_ID() + " and record_id = " + this.get_ID();
		int errors = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(errors > 0) return true;
		
		return false;	
	}

	private void loadData() throws Exception{
		// TODO Auto-generated method stub
		fileName = this.getFileName(); //cargo nombre de archivo xls
		
		//Borro errores anteriores
		deleteOldError();
		
		String s=validacionXLSInicial();
		//Validacion inicial
		if(!(s.equals(""))){
			throw new AdempiereException (s);
		}
		
		this.readXLS();
	}

	private String readXLS() throws Exception {
		// TODO Auto-generated method stub
		String message = "";
		Cell cell = null;
		tope = hoja.getRows();
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(getCtx(), get_Table_ID(), get_ID(),null, hoja,null);
		MTTPrintValeLine line = null;
		int cantVacias = 0;
		

		for (int recorrido = 1; recorrido < tope; recorrido++) {
			
			message = "";
			String ctaVacio = null;
			String msgCuentaVacio = "";
			String cedulaVacio = null;
			String msgCedulaVacio = "";
			String fechaVacio = null;
			String msgFechaVacio = "";
			String nombreVacio = null;
			String msgNombreVacio = "";	
			String valeFrimadoVacio = null;
			String msgValeFirmadoVacio = "";

			try {

				//Se lee Nro de Cuenta en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				String cuenta=utiles.getStringFromCell(cell);		
				if(cuenta==null || cuenta.equalsIgnoreCase("")){
					cuenta=null;
					message="Nro. de cuenta vacio";
					ctaVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgCuentaVacio = message;
					
				} else {
					
					//debo validar que la cuenta exista en Financial
					ItalcredSystem itsys = new ItalcredSystem();
					if(!itsys.accountExists(getCtx(), cuenta, null)){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. de cuenta '" + cuenta + "' no existe",null);
						message="Nro. de cuenta '" + cuenta + "' no existe";
						cuenta=null;
					} else{
						//Debo validar que la cuenta tenga pendiente la firma de vale y va a domicilio
						MTTCard card = MTTCard.forAccountOpen(getCtx(), cuenta.trim(), get_TrxName());
						if (card != null && !card.isValeSigned() && card.getCardDestination().equalsIgnoreCase("DOMICILIO")){
							cuenta=cuenta.trim();
						}else{
							MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. de cuenta '" + cuenta + "' no existe",null);
							message="Nro. de cuenta '" + cuenta + "' no va a domicilio o ya tiene la firma de vale";
							cuenta=null;
						}
						
					}
				}
				//---------------------------------------------------------------
				
				//Se lee Cedula en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String cedula=utiles.getStringFromCell(cell);
				if(cedula==null || cedula.equalsIgnoreCase("")){
					cedula=null;
					message="Nro. de cedula vacio";
					cedulaVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgCedulaVacio = message;
				} else {
										
					//debo validar que la cedula corresponda a la cuenta y que sea un titular
					if(cuenta!=null){
						ItalcredSystem itsys = new ItalcredSystem();
						String error = itsys.existsCIForAccount(getCtx(), cuenta, cedula, null);
						if(error!=null){
							MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),error,null);
							message= error;
							cedula=null;
						} else cedula = cedula.trim(); 
					}						
				}
				//-----------------------------------------------------------------------------
				
				//Se lee Fecha de Validez en columna C que es 2***************************
//				cell = (hoja.getCell(2, recorrido));
//				Timestamp fechaVal = null;
//				String fchValidez=utiles.getStringFromCell(cell);
//				if(fchValidez!=null){
//					try{
//						fchValidez=fchValidez.trim();
//						fechaVal=utiles.formatStringDate(fchValidez);
//						String f = fechaVal.toString();
//						if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
//							MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
//							message="Fecha con formato incorrecto";
//
//						}
//
//					}catch (Exception e){
//						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
//						message="Fecha con formato incorrecto";
//						fechaVal=null;
//					}
//				} else {
//					fechaVal=null;
//					message="Fecha de validez vacia";
//					fechaVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
//					msgFechaVacio = message;
//				}
				//---------------------------------------------------------------

				//Se lee Nombre en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				String nombre=utiles.getStringFromCell(cell);
				if(nombre==null || nombre.equalsIgnoreCase("")){
					nombre=null;
					message="Nombre vacio";
					nombreVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgNombreVacio = message;
				} else nombre = nombre.trim();

				//-----------------------------------------------------------------------------
				
				//Se lee Nombre en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				String valeFrimado=utiles.getStringFromCell(cell);
				if(cuenta!=null){
					
					if(valeFrimado==null || valeFrimado.equalsIgnoreCase("")){
						valeFrimado=null;
						message="Campo vale firmado vacío";
						valeFrimadoVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
						msgValeFirmadoVacio = message;
					} else {
						if(valeFrimado.trim().toLowerCase().equals("si")){
							//valeFrimado=null;
							message="La cuenta: "+cuenta+" ya tiene firma de vale";
							valeFrimadoVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgValeFirmadoVacio = message;
						}else	valeFrimado = valeFrimado.trim();
					}
				}
				//-----------------------------------------------------------------------------

				if(cuenta!=null && nombre!=null && cedula!=null && message.equalsIgnoreCase("")){	
					line = new MTTPrintValeLine (getCtx(), 0, null);
					line.setUY_TT_PrintVale_ID(this.get_ID());
					line.setAccountNo(cuenta);
					//line.setDueDate(fechaVal);
					line.setCedula(cedula);
					line.setName(nombre);
					line.setSuccess(true);
					
					line.saveEx(null);

				} 
				
				if(cuenta!=null || nombre!=null || cedula!=null ){	
					//si tengo al menos un campo No vacio entonces inserto todos los errores encontrados
					if(ctaVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), ctaVacio, "", msgCuentaVacio,null);
					if(cedulaVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), cedulaVacio, "", msgCedulaVacio,null);
					//if(fechaVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), fechaVacio, "", msgFechaVacio,null);
					if(nombreVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), nombreVacio, "", msgNombreVacio,null);
					if(valeFrimadoVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), valeFrimadoVacio, valeFrimado, msgValeFirmadoVacio,null);
				}
				
				if(cuenta==null && nombre==null && cedula==null) cantVacias ++; //aumento contador de filas vacias			
				if(cantVacias == 10) recorrido = tope; //permito un maximo de 10 lineas vacias leidas, superado ese tope salgo del FOR
				
			} catch (Exception e) {
				//Errores no contemplados
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
		
		}				

		if (workbook!=null){
			workbook.close();
		}

		if(message.equalsIgnoreCase("")) message="Proceso Finalizado OK";

		return message;
	}

	private String validacionXLSInicial() {
		// TODO Auto-generated method stub
		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(getCtx(), get_Table_ID(), this.get_ID(), fileName,
					"", "", "", "El nombre de la planilla excel esta vacio",
					null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(getCtx(), get_Table_ID(), this.get_ID(), fileName,
					"", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}

		try {

			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(getCtx(),get_Table_ID(),this.get_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
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

	private void deleteOldError() throws Exception {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM uy_xlsissue WHERE record_id IN(SELECT UY_TT_PrintVale_ID FROM UY_TT_PrintVale WHERE createdby="+Env.getAD_User_ID(Env.getCtx())+") " +
				"OR record_id=" + this.get_ID() +" AND createdby="+Env.getAD_User_ID(Env.getCtx());

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}

	private void deleteOldData() throws Exception {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM UY_TT_PrintValeLine WHERE UY_TT_PrintVale_ID = " + this.get_ID();

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
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
		
		//No permito completar el documento si existen errores
		if(this.hayErrores()) throw new AdempiereException("Imposible completar documento, verifique inconsistencias");		

		// Recorro lineas procesadas y voy seteando cambios en cuenta
		List<MTTPrintValeLine> lines = this.getSelectedLines();
		for (MTTPrintValeLine line: lines){
			if (line.isSuccess()){
				MTTCard card = MTTCard.forAccountOpen(getCtx(), line.getAccountNo(), get_TrxName());
				//MTTCard card = MTTCard.forAccountOpenAndUnreceipted(getCtx(), line.getAccountNo(), get_TrxName());
				if (card != null && !card.isValeSigned() && card.getCardDestination().equalsIgnoreCase("DOMICILIO")){
					card.set_ValueOfColumn("UY_TT_PrintValeLine_ID", line.get_ID());
					card.saveEx();
					line.setDescription("Se asocia la cuenta ");
					
				}
			}
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
		// TODO Auto-generated method stub
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
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/***
	 * Retorna lineas seleccionadas.
	 * OpenUp Ltda.  
	 * @author Sylvie Bouissa - 12/12/2014
	 * @see
	 * @return
	 */
	public List<MTTPrintValeLine> getSelectedLines(){
		
		String whereClause = X_UY_TT_PrintValeLine.COLUMNNAME_UY_TT_PrintVale_ID + "=" + this.get_ID() +
						" AND " + X_UY_TT_PrintValeLine.COLUMNNAME_Success + "='Y' ";
		
		List<MTTPrintValeLine> lines = new Query(getCtx(), I_UY_TT_PrintValeLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}

}
