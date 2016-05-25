package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.beans.AuxWorkCellXLS;

//import java.text.SimpleDateFormat;
public class MLoadExtract extends X_UY_LoadExtract implements DocAction {
	
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
	
	//SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * 
	 */
	private static final long serialVersionUID = 797859331234807872L;

	public MLoadExtract(Properties ctx, int UY_LoadExtract_ID, String trxName) {
		super(ctx, UY_LoadExtract_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLoadExtract(Properties ctx, ResultSet rs, String trxName) {
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
	public boolean applyIt() {
		try {
			this.loadData();
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
//		Re-Check
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

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getAmount().compareTo(Env.ZERO)==0) ADialog.warn(0, null, "ATENCION: El saldo de la cta. bancaria en esta carga es cero");
		
		return true;
	}

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		//OpenUp. Nicolas Sarlabos. 01/11/2013. #1485. Valido antes de anular
		validBeforeVoid();
		//Fin OpenUp.
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	/**OpenUp. Nicolas Sarlabos. 01/11/2013
	 * Metodo que valida antes de anular el documento que ninguna linea
	 * se encuentre en alguna conciliacion en cualquier estado.
	 * @return
	 */
	private void validBeforeVoid() {
	
		String sql = "";
		
		//verifico cantidad de lineas de extracto que fueron utilizadas en alguna conciliacion
		sql = "select coalesce(count(be.uy_bankextract_id),0)" +
              " from uy_bankextract be" +
              " where be.uy_loadextract_id = " + this.get_ID() +  
              " and be.uy_bankextract_id in (select uy_bankextract_id from uy_conciliabank)";
		
		int count = DB.getSQLValueEx(get_TrxName(), sql); //obtengo cantidad de lienas
		
		if(count > 0) throw new AdempiereException ("Imposible anular documento por haber sido utilizado en una o mas conciliaciones");	
		
	}
	
	@Override
	public boolean closeIt() {
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (this.processMsg != null)
			return false;
				
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Closed);
		setDocAction(DocAction.ACTION_None);
		
		return true;
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
	public String getDocumentNo() {
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
	 * Carga el extracto bancario.
	 * OpenUp Ltda. Issue #693
	 * @author Nicolas Sarlabos - 17/04/2013
	 * @throws Exception 
	 * @see
	 */
	private void loadData() throws Exception{
		
		fileName = this.getFileName(); //cargo nomre de archivo xls
		
		//Borro errores anteriores
		deleteOldError();
		
		String s=validacionXLSInicial();
		//Validacion inicial
		if(s!=null){
			throw new AdempiereException (s);
		}
		
		s=this.readXLS();
		if(s.equals("Proceso Terminado OK")){
			this.saveEx();
		} else throw new AdempiereException (s);
						
	}
	
	private void deleteOldError()throws Exception{

		String sql = "DELETE FROM uy_xlsissue WHERE record_id IN(SELECT uy_loadextract_id FROM uy_loadextract WHERE createdby="+Env.getAD_User_ID(Env.getCtx())+") " +
				"OR record_id=" + this.get_ID() +" AND createdby="+Env.getAD_User_ID(Env.getCtx());

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	private String validacionXLSInicial(){


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

			// Se vacia la lista de errores
			// MXLSIssue.Delete(ctx, table_ID,record_ID,null);

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

		return null;
	}
	
	
	private String readXLS() throws Exception {

		//Cell cell = null;
		tope = hoja.getRows();
		//String trxAux=null;
		//Trx trans = null;
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(getCtx(), get_Table_ID(), this.get_ID(),null, hoja,null);
		//MBankExtract ext = null;
		MBank bank = null;
		//MBank bank = getBank(loadExtract.getC_BankAccount_ID());
		MBankAccount account = new MBankAccount (getCtx(),this.getC_BankAccount_ID(),get_TrxName());
		if (account!=null) bank = account.getBankFromAccount(account.get_ID());
		int file = bank.getFirstRowExtract(); //obtengo el numero de fila inicial del xls
		//int docID = 0;
		//boolean success = true;
		//String warnings = "";
	
		if(file <= 0) throw new AdempiereException("El numero de primer fila de archivo para el banco " + bank.getName() + " es menor o igual a cero");
		
		if(bank.getValue()==null || bank.getValue().equalsIgnoreCase("")) throw new AdempiereException("El banco " + bank.getName() + " no tiene clave de busqueda asociada");
				
		if(bank.getValue().equalsIgnoreCase("BROU")){
			switch(account.getC_Currency_ID()){
			case 100: // Extracto para dolares
				readBrouSheetDolares(file, bank, account);
				break;
			case 142: //Extracto para pesos
				readBrouSheetPesos(file, bank, account);
				break;
			}
			
		} else if (bank.getValue().equalsIgnoreCase("SCOTIABANK")){
			readScotiabankSheet(file, bank, account);
		} else if (bank.getValue().equalsIgnoreCase("BANDES")){
			readBandesSheet(file, bank, account);
		} else if (bank.getValue().equalsIgnoreCase("BRADESCO")){
			readBradescoSheet(file, bank, account);
		} else if (bank.getValue().equalsIgnoreCase("BBVA")){
			readBBVASheet(file, bank, account);
		}else if (bank.getValue().equalsIgnoreCase("SANTANDER")){
			readSantanderSheet(file, bank, account);
		} else if (bank.getValue().equalsIgnoreCase("ITAU")){
			readItauSheet(file, bank, account);
		}
			

		if (workbook!=null){
			workbook.close();
		}
		return "Proceso Terminado OK";
	}
	

	private String readItauSheet (int file, MBank bank, MBankAccount account){
		
		Cell cell = null;
		String trxAux = null;
		Trx trans = null;
		MBankExtract ext = null;
		int docID = 0;
		boolean success = true;
		String warnings = "";
	
		
		for (int recorrido = (file - 1); recorrido < tope-1; recorrido++) {

			success = true;
			warnings = "";
			
			try{
				
				//Se lee Fecha en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				Timestamp fecha = null;
				String fch=utiles.getStringFromCell(cell);
				if(fch!=null){
					try{
							fecha=utiles.formatStringDate(fch);
							String f = fecha.toString();
							if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el a\F1o no comience con "0", debido al formato incorrecto de la celda
								MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
																
							}
					}catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						fecha=null;
					}
				} else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha vacia",null);
				//---------------------------------------------------------------

				//Se lee Tipo Movimiento en columna C que es 2 (Concepto)***************************
				cell = (hoja.getCell(2, recorrido));
				String tipoMovimiento = utiles.getStringFromCell(cell);
				//-----------------------------------------------------------------------------
				
				//Se lee Debito en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				BigDecimal debito = Env.ZERO;
				String d = utiles.getStringFromCell(cell);
				if(d!=null && !d.equalsIgnoreCase("")){
					d=d.replace("-","");
					d=replaceChar(d);
					debito = new BigDecimal(d);
				}
				
				//Se lee Credito en columna F que es 5***************************
				cell = (hoja.getCell(5, recorrido));
				BigDecimal credito = Env.ZERO;
				String c=utiles.getStringFromCell(cell);
				if(c!=null && !c.equalsIgnoreCase("")){
					c=replaceChar(c);
					credito = new BigDecimal(c);
				}
				
				if (fecha==null){
					warnings += "Fecha vacia";
					success = false;
				} else if (fecha.toString().substring(0,1).equalsIgnoreCase("0")){
					warnings += "Fecha con formato incorrecto";
					success = false;
				}
				
		
				if (tipoMovimiento==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Tipo Movimiento vacio";	
					}else warnings += "-Tipo Movimiento vacio";
					
					success = false;
				}
	
				if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito iguales a 0";
					} else warnings += "-Debito y Credito iguales a 0";
					
					success = false;
				}
				
				if(fecha!=null && tipoMovimiento!=null){
					
					trxAux=Trx.createTrxName();
					trans = Trx.get(trxAux, true);
					
					ext=new MBankExtract(getCtx(),0, trxAux);
					ext.setDateTrx(fecha);
					ext.setDescription(tipoMovimiento);
					//ext.setdocumentNo(referencia);
					ext.setAmtSourceCr(credito);
					ext.setAmtSourceDr(debito);
					ext.setC_Bank_ID(bank.get_ID());
					ext.setC_BankAccount_ID(this.getC_BankAccount_ID());
					ext.setC_Currency_ID(account.getC_Currency_ID());
					ext.setUY_LoadExtract_ID(this.get_ID());
					ext.setobservaciones(warnings);
					ext.setSuccess(success);
					
					docID = getDocType(bank,tipoMovimiento);
					if(docID>0) ext.setC_DocType_ID(docID);
					
					ext.saveEx(trxAux);
			}	
			
			
		} catch (Exception e) {
			//Errores no contemplados
			trans.rollback();
			trans.close();
			MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

		}
		if(trans!=null){
			trans.close();
		}
	}
	
				return null;
	}
	
	private String readSantanderSheet (int file, MBank bank, MBankAccount account){

		Cell cell = null;
		String trxAux = null;
		Trx trans = null;
		MBankExtract ext = null;
		int docID = 0;
		boolean success = true;
		String warnings = "";
		
		for (int recorrido = (file - 1); recorrido < tope - 1; recorrido++) {

			success = true;
			warnings = "";
			
			try{
				
				//Se lee Fecha en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				Timestamp fecha = null;
				String fch=utiles.getStringFromCell(cell);
				if(fch!=null){
					try{
							fecha=utiles.formatStringDate(fch);
							String f = fecha.toString();
							if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el a\F1o no comience con "0", debido al formato incorrecto de la celda
								MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
																
							}
					}catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						fecha=null;
					}
				} else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha vacia",null);
				//---------------------------------------------------------------

				//Se lee Referencia en columna b que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String referencia = utiles.getStringFromCell(cell);
				//-----------------------------------------------------------------------------

				//Se lee Tipo Movimiento en columna c que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				String tipoMovimiento = utiles.getStringFromCell(cell);
				//-----------------------------------------------------------------------------

				//Se lee Tipo Descripcion en columna d que es 3***************************/ 
				//cell = (hoja.getCell(3, recorrido)); Se le asigna a la descripcion el tipo de movimiendo
				//String descripcion = utiles.getStringFromCell(cell); que en este caso es mas descriptivo..
				//-----------------------------------------------------------------------------   

				//Se lee Debito en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				BigDecimal debito = Env.ZERO;
				String d = utiles.getStringFromCell(cell);
				if(d!=null && !d.equalsIgnoreCase("")){
					d=d.replace("-","");
					d=replaceChar(d);
					debito = new BigDecimal(d);
				}
				
				//Se lee Credito en columna F que es 5***************************
				cell = (hoja.getCell(5, recorrido));
				BigDecimal credito = Env.ZERO;
				String c=utiles.getStringFromCell(cell);
				if(c!=null && !c.equalsIgnoreCase("")){
					c=replaceChar(c);
					credito = new BigDecimal(c);
				}
				//

				if (fecha==null){
					warnings += "Fecha vacia";
					success = false;
				} else if (fecha.toString().substring(0,1).equalsIgnoreCase("0")){
					warnings += "Fecha con formato incorrecto";
					success = false;
				}
				
				if (referencia==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Descripcion vacia";	
					}else warnings += "-Descripcion vacia";
					
					success = false;
				}
				
				if (tipoMovimiento==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Tipo Movimiento vacio";	
					}else warnings += "-Tipo Movimiento vacio";
					
					success = false;
				}
				/*
				if (descripcion==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Descripción vacio";	
					}else warnings += "-Descripción vacio";
					
					success = false;
				}
					*/
				if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito iguales a 0";
					} else warnings += "-Debito y Credito iguales a 0";
					
					success = false;
				}		
				
				
				if(fecha!=null && referencia!=null){
				
						trxAux=Trx.createTrxName();
						trans = Trx.get(trxAux, true);
						
						ext=new MBankExtract(getCtx(),0, trxAux);
						ext.setDateTrx(fecha);
						ext.setDescription(tipoMovimiento);
						ext.setDocumentNo(referencia);
						ext.setAmtSourceCr(credito);
						ext.setAmtSourceDr(debito);
						ext.setC_Bank_ID(bank.get_ID());
						ext.setC_BankAccount_ID(this.getC_BankAccount_ID());
						ext.setC_Currency_ID(account.getC_Currency_ID());
						ext.setUY_LoadExtract_ID(this.get_ID());
						ext.setobservaciones(warnings);
						ext.setSuccess(success);
						
						docID = getDocType(bank,tipoMovimiento);
						if(docID>0) ext.setC_DocType_ID(docID);
						
						ext.saveEx(trxAux);
				}	
				
				
			} catch (Exception e) {
				//Errores no contemplados
				trans.rollback();
				trans.close();
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
			if(trans!=null){
				trans.close();
			}
		}
			
		return null;
	}
	
	
	private String readBBVASheet (int file, MBank bank, MBankAccount account){
		

		Cell cell = null;
		String trxAux = null;
		Trx trans = null;
		MBankExtract ext = null;
		int docID = 0;
		boolean success = true;
		String warnings = "";
		int blankSpaces = 0;
		
		for (int recorrido = (file-1); recorrido < tope; recorrido++) {

			success = true;
			warnings = "";
			
			try {
				
				//Se lee Fecha en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				Timestamp fecha = null;
				String fch=utiles.getStringFromCell(cell);
				if(fch!=null){
					try{
							fecha=utiles.formatStringDate(fch);
							String f = fecha.toString();
							if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
								MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
																
							}
					}catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						fecha=null;
					}
				} else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha vacia",null);
				//---------------------------------------------------------------

				//Se lee Descripcion en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String description=utiles.getStringFromCell(cell);
				//-----------------------------------------------------------------------------

				//Se lee Numero de Documento en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				String documento=utiles.getStringFromCell(cell);
				if(documento==null){
					documento="0";
					
				}//-----------------------------------------------------------------------------

				//Se lee Debito en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				BigDecimal debito = Env.ZERO;
				String d=utiles.getStringFromCell(cell);
				if(d!=null && !d.trim().equalsIgnoreCase("")){
					d=d.replace("-","");
					d=replaceChar(d);
					debito = new BigDecimal(d);
				}
				
				//Se lee Credito en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				BigDecimal credito = Env.ZERO;
				String c=utiles.getStringFromCell(cell);
				if(c!=null && !c.trim().equalsIgnoreCase("")){
					c=replaceChar(c);
					credito = new BigDecimal(c);
				}
				//-----------------------------------------------------------------------------

				if (fecha == null && description == null && debito.compareTo(Env.ZERO) == 0 && credito.compareTo(Env.ZERO) == 0)
				{
					blankSpaces ++;
					if (blankSpaces == 15){
						recorrido = tope;
					}
				}
				
				if (fecha==null){
					warnings += "Fecha vacia";
					success = false;
				} else if (fecha.toString().substring(0,1).equalsIgnoreCase("0")){
					warnings += "Fecha con formato incorrecto";
					success = false;
				}
				
				
				if (description==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Descripcion vacia";	
					}else warnings += "-Descripcion vacia";
					
					success = false;
				}
					
				if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito iguales a 0";
					} else warnings += "-Debito y Credito iguales a 0";
					
					success = false;
				}		
				
				if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito mayores a 0";
					} else warnings += "-Debito y Credito mayores a 0";
					
					success = false;
				}
					
				if(fecha!=null && description!=null){
				
						trxAux=Trx.createTrxName();
						trans = Trx.get(trxAux, true);
						
						ext=new MBankExtract(getCtx(),0, trxAux);
						ext.setDateTrx(fecha);
						ext.setDescription(description);
						ext.setDocumentNo(documento);
						ext.setAmtSourceCr(credito);
						ext.setAmtSourceDr(debito);
						ext.setC_Bank_ID(bank.get_ID());
						ext.setC_BankAccount_ID(this.getC_BankAccount_ID());
						ext.setC_Currency_ID(account.getC_Currency_ID());
						ext.setUY_LoadExtract_ID(this.get_ID());
						ext.setobservaciones(warnings);
						ext.setSuccess(success);
						
						docID = getDocType(bank,description);
						if(docID>0) ext.setC_DocType_ID(docID);
						
						ext.saveEx(trxAux);
				}	
				
				
			} catch (Exception e) {
				//Errores no contemplados
				trans.rollback();
				trans.close();
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
			if(trans!=null){
				trans.close();
			}
		}//Fin for
		return null;
	}
	
	private String readBradescoSheet (int file, MBank bank, MBankAccount account){
		
		Cell cell = null;
		String trxAux = null;
		Trx trans = null;
		MBankExtract ext = null;
		int docID = 0;
		boolean success = true;
		String warnings = "";
		
		for (int recorrido = (file-1); recorrido < tope ; recorrido++) {

			success = true;
			warnings = "";
			
			try {
				
				//Se lee Fecha en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				Timestamp fecha = null;
				String fch=utiles.getStringFromCell(cell);
				if(fch.equalsIgnoreCase("Total")){
					recorrido=tope;
				} else {
					if(fch!=null){
						try{
								fecha=utiles.formatStringDate(fch);
								String f = fecha.toString();
								if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
									MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
																	
								}
						}catch (Exception e){
							MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
							fecha=null;
						}
					} //else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha vacia",null);
					//---------------------------------------------------------------
	
					//Se lee Descripcion en columna B que es 1***************************
					cell = (hoja.getCell(1, recorrido));
					String description=utiles.getStringFromCell(cell);
					//-----------------------------------------------------------------------------
	
					//Se lee Numero de Documento en columna C que es 2***************************
					cell = (hoja.getCell(2, recorrido));
					String documento=utiles.getStringFromCell(cell);
					if(documento==null){
						documento="0";
						
					}//-----------------------------------------------------------------------------
	
					//Se lee Debito en columna E que es 4***************************
					cell = (hoja.getCell(4, recorrido));
					BigDecimal debito = Env.ZERO;
					String d=utiles.getStringFromCell(cell);
					if(d!=null && !d.equalsIgnoreCase("")){
						d=d.replace("-","");
						d=replaceChar(d);
						debito = new BigDecimal(d);
					}
					
					//Se lee Credito en columna D que es 3***************************
					cell = (hoja.getCell(3, recorrido));
					BigDecimal credito = Env.ZERO;
					String c=utiles.getStringFromCell(cell);
					if(c!=null && !c.equalsIgnoreCase("")){
						c=replaceChar(c);
						credito = new BigDecimal(c);
					}
					//-----------------------------------------------------------------------------
					
					if (fecha==null){
						warnings += "Fecha vacia";
						success = false;
					} else if (fecha.toString().substring(0,1).equalsIgnoreCase("0")){
						warnings += "Fecha con formato incorrecto";
						success = false;
					}
					
					
					if (description==null){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Descripcion vacia";	
						}else warnings += "-Descripcion vacia";
						
						success = false;
					}
						
					if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Debito y Credito iguales a 0";
						} else warnings += "-Debito y Credito iguales a 0";
						
						success = false;
					}		
					
					if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Debito y Credito mayores a 0";
						} else warnings += "-Debito y Credito mayores a 0";
						
						success = false;
					}
						
					if(fecha!=null && description!=null){
					
							trxAux=Trx.createTrxName();
							trans = Trx.get(trxAux, true);
							
							ext=new MBankExtract(getCtx(),0, trxAux);
							ext.setDateTrx(fecha);
							ext.setDescription(description);
							ext.setDocumentNo(documento);
							ext.setAmtSourceCr(credito);
							ext.setAmtSourceDr(debito);
							ext.setC_Bank_ID(bank.get_ID());
							ext.setC_BankAccount_ID(this.getC_BankAccount_ID());
							ext.setC_Currency_ID(account.getC_Currency_ID());
							ext.setUY_LoadExtract_ID(this.get_ID());
							ext.setobservaciones(warnings);
							ext.setSuccess(success);
							
							docID = getDocType(bank,description);
							if(docID>0) ext.setC_DocType_ID(docID);
							
							ext.saveEx(trxAux);
					}	
				}
				
			} catch (Exception e) {
				//Errores no contemplados
				trans.rollback();
				trans.close();
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
			if(trans!=null){
				trans.close();
			}
		}//Fin for
		
		return null;
	}
	
	private String readBrouSheetDolares (int file, MBank bank, MBankAccount account) {
		
		Cell cell = null;
		String trxAux = null;
		Trx trans = null;
		MBankExtract ext = null;
		int docID = 0;
		String warnings = "";
		boolean success = true;
		for (int recorrido = (file-1); recorrido < tope - 3; recorrido++) {
		
			success = true;
			warnings = "";
		
			try {
				//Se lee Fecha en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				Timestamp fecha = null;
				fecha = utiles.formatDateFromDateCell(cell);
				
				if(fecha!=null){
					try{
						
						String f = convertTimeStampToString(fecha);
						
						
					}catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						fecha=null;
					}
				} else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha vacia",null);
				//---------------------------------------------------------------

				//Se lee Descripcion en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				String description=utiles.getStringFromCell(cell);
				//-----------------------------------------------------------------------------
				//Se lee Numero de Documento en columna E que es 4***************************
				
				cell = (hoja.getCell(4, recorrido));
				String documento=utiles.getStringFromCell(cell);
				if(documento==null){
					documento="0";
					
				}//-----------------------------------------------------------------------------
				//Se lee Numero de Sucursal en columna F que es 5***************************
				cell = (hoja.getCell(5, recorrido));
				String sucursal=utiles.getStringFromCell(cell);
				if(sucursal!=null){
					
					if(sucursal.equalsIgnoreCase("")) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. sucursal vacio",null);
					
				}else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. sucursal vacio",null);
				//----------------------------------------------------------------------------
				
				//Se lee Debito en columna I que es 8***************************
				cell = (hoja.getCell(8, recorrido));
				BigDecimal debito = Env.ZERO;
				String d=utiles.getStringFromCell(cell);
				if(d!=null && !d.equalsIgnoreCase("")){
					d=d.replace("-","");
					d=replaceChar(d);
					debito = new BigDecimal(d);
				}
				
				//Se lee Credito en columna J que es 9***************************
				cell = (hoja.getCell(9, recorrido));
				BigDecimal credito = Env.ZERO;
				String c=utiles.getStringFromCell(cell);
				if(c!=null && !c.equalsIgnoreCase("")){
					c=replaceChar(c);
					credito = new BigDecimal(c);
				}
				//-----------------------------------------------------------------------------
				
				if (fecha==null){
					warnings += "Fecha vacia";
					success = false;
				} 
				
				
				if (description==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Descripcion vacia";	
					}else warnings += "-Descripcion vacia";
					
					success = false;
				}
					
				if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito iguales a 0";
					} else warnings += "-Debito y Credito iguales a 0";
					
					success = false;
				}		
				
				if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito mayores a 0";
					} else warnings += "-Debito y Credito mayores a 0";
					
					success = false;
				}
					
				if(fecha!=null && description!=null && sucursal!=null){
				
						trxAux=Trx.createTrxName();
						trans = Trx.get(trxAux, true);
						
						ext=new MBankExtract(getCtx(),0, trxAux);
						ext.setDateTrx(fecha);
						ext.setDescription(description);
						ext.setDocumentNo(documento);
						ext.setAmtSourceCr(credito);
						ext.setAmtSourceDr(debito);
						ext.setC_Bank_ID(bank.get_ID());
						ext.setC_BankAccount_ID(this.getC_BankAccount_ID());
						ext.setC_Currency_ID(account.getC_Currency_ID());
						ext.setUY_LoadExtract_ID(this.get_ID());
						ext.setsucursal(sucursal);
						ext.setobservaciones(warnings);
						ext.setSuccess(success);
						
						docID = getDocType(bank,description);
						if(docID>0) ext.setC_DocType_ID(docID);
						
						ext.saveEx(trxAux);
				}	
				
			} catch (Exception e) {
				//Errores no contemplados
				trans.rollback();
				trans.close();
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
			if(trans!=null){
				trans.close();
			}
		}//Fin for			
		return null;
		
	}
	private String readBrouSheetPesos (int file, MBank bank, MBankAccount account) {

		Cell cell = null;
		String trxAux = null;
		Trx trans = null;
		MBankExtract ext = null;
		int docID = 0;
		String warnings = "";
		boolean success = true;
		for (int recorrido = (file-1); recorrido < tope - 3; recorrido++) {

			success = true;
			warnings = "";
			
			try {

				//Se lee Fecha en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				Timestamp fecha = null;
				fecha = utiles.formatDateFromDateCell(cell);
				
//				fch = parseo[2];
//				if(parseo[2].length()==2){
//					fch = parseo[0] + "/" + parseo[1] + "/" + "20" + parseo[2];
//					}
				
					
				if(fecha!=null){
					try{
						
						String f = convertTimeStampToString(fecha);
							
					} catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						fecha=null;
					}
				} else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha vacia",null);
				
				//---------------------------------------------------------------

				//Se lee Descripcion en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				String description=utiles.getStringFromCell(cell);
				//-----------------------------------------------------------------------------

				//Se lee Numero de Documento en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				String documento=utiles.getStringFromCell(cell);
				if(documento==null){
					documento="0";
					
				}//-----------------------------------------------------------------------------
				
				//Se lee Numero de Sucursal en columna F que es 5***************************
				cell = (hoja.getCell(5, recorrido));
				String sucursal=utiles.getStringFromCell(cell);
				if(sucursal!=null){
					
					if(sucursal.equalsIgnoreCase("")) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. sucursal vacio",null);
					
				}else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. sucursal vacio",null);
				//-----------------------------------------------------------------------------
				
				//Se lee Debito en columna I que es 8***************************
				cell = (hoja.getCell(8, recorrido));
				BigDecimal debito = Env.ZERO;
				String d=utiles.getStringFromCell(cell);
				if(d!=null && !d.equalsIgnoreCase("")){
					d=d.replace("-","");
					d=replaceChar(d);
					debito = new BigDecimal(d);
				}
				
				//Se lee Credito en columna J que es 9***************************
				cell = (hoja.getCell(9, recorrido));
				BigDecimal credito = Env.ZERO;
				String c=utiles.getStringFromCell(cell);
				if(c!=null && !c.equalsIgnoreCase("")){
					c=replaceChar(c);
					credito = new BigDecimal(c);
				}
				//-----------------------------------------------------------------------------
				
				if (fecha==null){
					warnings += "Fecha vacia";
					success = false;
				}
				
				
				if (description==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Descripcion vacia";	
					}else warnings += "-Descripcion vacia";
					
					success = false;
				}
					
				if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito iguales a 0";
					} else warnings += "-Debito y Credito iguales a 0";
					
					success = false;
				}		
				
				if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito mayores a 0";
					} else warnings += "-Debito y Credito mayores a 0";
					
					success = false;
				}
					
				if(fecha!=null && description!=null && sucursal!=null){
				
						trxAux=Trx.createTrxName();
						trans = Trx.get(trxAux, true);
						
						ext=new MBankExtract(getCtx(),0, trxAux);
						ext.setDateTrx(fecha);
						ext.setDescription(description);
						ext.setDocumentNo(documento);
						ext.setAmtSourceCr(credito);
						ext.setAmtSourceDr(debito);
						ext.setC_Bank_ID(bank.get_ID());
						ext.setC_BankAccount_ID(this.getC_BankAccount_ID());
						ext.setC_Currency_ID(account.getC_Currency_ID());
						ext.setUY_LoadExtract_ID(this.get_ID());
						ext.setsucursal(sucursal);
						ext.setobservaciones(warnings);
						ext.setSuccess(success);
						
						docID = getDocType(bank,description);
						if(docID>0) ext.setC_DocType_ID(docID);
						
						ext.saveEx(trxAux);
				}	
				
			} catch (Exception e) {
				//Errores no contemplados
				trans.rollback();
				trans.close();
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
			if(trans!=null){
				trans.close();
			}
		}//Fin for			
		return null;
		
	}
	
	private String readScotiabankSheet(int file, MBank bank, MBankAccount account){

		Cell cell = null;
		String trxAux = null;
		Trx trans = null;
		MBankExtract ext = null;
		int docID = 0;
		boolean success = true;
		String warnings = "";
		
		for (int recorrido = file; recorrido < tope; recorrido++) {
			
			success = true;
			warnings = "";
			
			try {

		/*		//Se lee Numero de Sucursal en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				String sucursal=utiles.getStringFromCell(cell);
				if(sucursal==null || sucursal.equalsIgnoreCase("")){
					MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. sucursal vacio",null);
					
				}//----------------------------------------------------------------------------- */
				
				System.out.println("Fila :" + recorrido);
				
				//Se lee Fecha en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				Timestamp fecha = null;
				String fch=utiles.getStringFromCell(cell);
				if(fch!=null){
					try{
							fecha=utiles.formatStringDate(fch);
							String f = fecha.toString();
							if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
								MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
																
							}
					}catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						fecha=null;
					}
				} else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha vacia",null);
				//---------------------------------------------------------------
			/*	
				//Se lee Fecha Valor en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				Timestamp fechaV = null;
				String fchV=utiles.getStringFromCell(cell);
				if(fchV!=null){
					try{
							fechaV=utiles.formatStringDate(fchV);
					}catch (Exception e){
						fechaV=null;
					}
				} else { 
					
					MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha Valor vacia",null);
					
				} */
				//---------------------------------------------------------------

				//Se lee Descripcion en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String description=utiles.getStringFromCell(cell);
				//-----------------------------------------------------------------------------

				//Se lee Numero de Documento (Comprobante) en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				String documento=utiles.getStringFromCell(cell);
				if(documento==null){
					documento="0";
				}//-----------------------------------------------------------------------------

				//Se lee Debito en columna D que es 3***************************
				 //Str.substring(0, 4); 
				//Str = Str.substring(0, 4);
				//String d=utiles.getStringFromCell(cell);
				cell = (hoja.getCell(3, recorrido));
				
				//String Str = utiles.getStringFromCell(cell);
				String d = null;
				String[] parseo = null;
				BigDecimal debito = Env.ZERO;
				
				if ((cell.getContents() != null) && (!cell.getContents().trim().equalsIgnoreCase(""))){

					d=utiles.getStringFromCell(cell);
					parseo = d.split(" ");
					d = parseo[1];
					if(d!=null && !d.equalsIgnoreCase("")){
						d=d.replace("-","");
						d=replaceChar(d);
						debito = new BigDecimal(d);
					}
				}
				
				//Se lee Credito en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				// Str = utiles.getStringFromCell(cell);
				 //Str.substring(0, 4);

				BigDecimal credito = Env.ZERO;
				if ((cell.getContents() != null) && (!cell.getContents().trim().equalsIgnoreCase(""))){
					d=utiles.getStringFromCell(cell);
					parseo = d.split(" ");
					 d = parseo[1];
					//String c=utiles.getStringFromCell(cell);
					if(d!=null && !d.equalsIgnoreCase("")){
						d=replaceChar(d);
						credito = new BigDecimal(d);
					}
				}
				
				//-----------------------------------------------------------------------------
				// Se lee importe final f que es 5
				cell = (hoja.getCell(5, recorrido));
				
				BigDecimal impfinal = Env.ZERO;
				if ((cell.getContents() != null) && (!cell.getContents().trim().equalsIgnoreCase(""))){
					 d=utiles.getStringFromCell(cell);
					 parseo = d.split(" ");
					 d = parseo[1];
					//String x=utiles.getStringFromCell(cell);
					if(d!=null && !d.equalsIgnoreCase("")){
						d=replaceChar(d);
						impfinal = new BigDecimal(d);
					}
				}

				 //Str = utiles.getStringFromCell(cell);
				 //Str.substring(0 ,4);
				
				//--------------------------------------------------------------------------------
				if (fecha==null){
					warnings += "Fecha vacia";
					success = false;
				} else if (fecha.toString().substring(0,1).equalsIgnoreCase("0")){
					warnings += "Fecha con formato incorrecto";
					success = false;
				}
				
				if (description==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Descripcion vacia";	
					}else warnings += "-Descripcion vacia";
					
					success = false;
				}
					
				if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito iguales a 0";
					} else warnings += "-Debito y Credito iguales a 0";
					
					success = false;
				}		
				
				if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito mayores a 0";
					} else warnings += "-Debito y Credito mayores a 0";
					
					success = false;
				}				
				

				if(fecha!=null && description!=null){   // && sucursal!=null
									
						trxAux=Trx.createTrxName();
						trans = Trx.get(trxAux, true);

						ext=new MBankExtract(getCtx(),0, trxAux);
						//ext.setsucursal(sucursal);
						ext.setDateTrx(fecha);
						//ext.setDateValue(fechaV);
						ext.setDescription(description);
						ext.setDocumentNo(documento);
						ext.setAmtSourceCr(credito);
						ext.setAmtSourceDr(debito);
						ext.setTotalAmt(impfinal);
						ext.setC_Bank_ID(bank.get_ID());
						ext.setC_BankAccount_ID(this.getC_BankAccount_ID());
						ext.setC_Currency_ID(account.getC_Currency_ID());
						ext.setUY_LoadExtract_ID(this.get_ID());
						ext.setobservaciones(warnings);
						ext.setSuccess(success);
						
						docID = getDocType(bank,description);
						if(docID>0) ext.setC_DocType_ID(docID);
						
						ext.saveEx(trxAux);

				}
				
			} catch (Exception e) {
				//Errores no contemplados
				trans.rollback();
				trans.close();
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
			if(trans!=null){
				trans.close();
			}
		}//Fin for
	
		return null;
	}
	private String readBandesSheet(int file, MBank bank, MBankAccount account){

		Cell cell = null;
		String trxAux = null;
		Trx trans = null;
		MBankExtract ext = null;
		int docID = 0;
		boolean success = true;
		String warnings = "";
		
		for (int recorrido = (file-1); recorrido < tope; recorrido++) {
			
			success = true;
			warnings = "";
			
			try {

				//Se lee Fecha en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				Timestamp fecha = null;
				String fch=utiles.getStringFromCell(cell);
				if(fch!=null){
					try{
							fecha=utiles.formatStringDate(fch);
							String f = fecha.toString();
							if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
								MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
																
							}
					}catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						fecha=null;
					}
				} else MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha vacia",null);
				//---------------------------------------------------------------
				
				//Se lee Descripcion en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				String description=utiles.getStringFromCell(cell);
				//-----------------------------------------------------------------------------

				//Se lee Numero de Documento en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				String documento=utiles.getStringFromCell(cell);
				if(documento==null){
					documento="0";
					
				}//-----------------------------------------------------------------------------
									
				//Se lee Debito en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				BigDecimal debito = Env.ZERO;
				String d=utiles.getStringFromCell(cell);
				if(d!=null && !d.equalsIgnoreCase("")){
					d=d.replace("-","");
					d=replaceChar(d);
					debito = new BigDecimal(d);
				}
				
				//Se lee Credito en columna F que es 5***************************
				cell = (hoja.getCell(5, recorrido));
				BigDecimal credito = Env.ZERO;
				String c=utiles.getStringFromCell(cell);
				if(c!=null && !c.equalsIgnoreCase("")){
					c=replaceChar(c);
					credito = new BigDecimal(c);
				}
				//-----------------------------------------------------------------------------
				
				//Se lee Saldo en columna G que es 6***************************
				/*cell = (hoja.getCell(6, recorrido));
				BigDecimal saldo = Env.ZERO;
				String s=utiles.getStringFromCell(cell);
				if(s!=null && !s.equalsIgnoreCase("")){
					s=s.replace("-","");
					s=replaceChar(s);
					saldo = new BigDecimal(s);
				}*/
				//--------------------------------------------------------------------------------------								

				if (fecha==null){
					warnings += "Fecha vacia";
					success = false;
				} else if (fecha.toString().substring(0,1).equalsIgnoreCase("0")){
					warnings += "Fecha con formato incorrecto";
					success = false;
				}
				
				if (description==null){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Descripcion vacia";	
					}else warnings += "_Descripcion vacia";
					
					success = false;
				}
					
				if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito iguales a 0";
					} else warnings += "-Debito y Credito iguales a 0";
					
					success = false;
				}		
				
				if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
					if(warnings.equalsIgnoreCase("")){
						warnings += "Debito y Credito mayores a 0";
					} else warnings += "-Debito y Credito mayores a 0";
					
					success = false;
				}				

				if(fecha!=null && description!=null){
				
						trxAux=Trx.createTrxName();
						trans = Trx.get(trxAux, true);

						ext=new MBankExtract(getCtx(),0, trxAux);
						ext.setDateTrx(fecha);
						ext.setDescription(description);
						ext.setDocumentNo(documento);
						ext.setAmtSourceCr(credito);
						ext.setAmtSourceDr(debito);
						//ext.setTotalAmt(saldo);
						ext.setC_Bank_ID(bank.get_ID());
						ext.setC_BankAccount_ID(this.getC_BankAccount_ID());
						ext.setC_Currency_ID(account.getC_Currency_ID());
						ext.setUY_LoadExtract_ID(this.get_ID());
						ext.setobservaciones(warnings);
						ext.setSuccess(success);
						
						docID = getDocType(bank,description);
						if(docID>0) ext.setC_DocType_ID(docID);
						
						ext.saveEx(trxAux);						
						
				}	
				
			} catch (Exception e) {
				//Errores no contemplados
				trans.rollback();
				trans.close();
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
			if(trans!=null){
				trans.close();
			}
		}//Fin for
		
		return null;
	}	
	
	
	
	/**OpenUp. Nicolas Sarlabos. 14/12/2012
	 * Recorre un string y modifica puntos y comas para evitar problemas al convertir dicho string a BigDecimal
	 * @param cadena
	 * @return
	 */
	private String replaceChar(String cadena) {
		
		if(cadena!=null && !cadena.equalsIgnoreCase("")){
			
			char punto = '.';
			char coma = ',';

			for(int i=cadena.length()-1;i>=0;i--){

				if(cadena.charAt(i)==punto) {
					cadena=cadena.replace(",", "");
					i=-1;

				} else if(cadena.charAt(i)==coma){
					cadena=cadena.replace(",", "+");
					cadena=cadena.replace(".", "");
					cadena=cadena.replace("+", ".");
					i=-1;
				}

			}
		}
			
		return cadena;
	}

	/**OpenUp. Nicolas Sarlabos. 23/11/2012
	 * Metodo que recibe un banco y una descripcion de movimiento del mismo
	 * y devuelve el ID del documento correspondiente segun la parametrizacion 
	 * @param bank
	 * @param description
	 * @return
	 * @throws SQLException 
	 */
	
	private int getDocType(MBank bank, String description) throws SQLException {

		int value = 0;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		if(bank!=null && description!="" && description!=null){

			try {
				sql = "SELECT description,c_doctype_Id" +
						" FROM uy_extractdocument" +
						" WHERE c_bank_id=" + bank.get_ID();

				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery ();

				while(rs.next()){

					if(description.toUpperCase().trim().contains(rs.getString("description").toUpperCase().trim())){

						value = rs.getInt("c_doctype_id");
						return value;
					}
				}

			} catch(Exception e) {
				throw new AdempiereException(e);

			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

		}

		return value;
	}
	
	/***
	 * Obtiene y retorna lineas de este documento.
	 * OpenUp Ltda. Issue #1485
	 * @author Nicolas Sarlabos - 01/11/2013
	 * @see
	 * @return
	 */
	public List<MBankExtract> getLines(){

		String whereClause = X_UY_BankExtract.COLUMNNAME_UY_LoadExtract_ID + "=" + this.get_ID();

		List<MBankExtract> lines = new Query(getCtx(), I_UY_BankExtract.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	/***
	 * Retorna el String de convertir un timpestamp siguiendo el siguiente patro dd/MM/yyyy.
	 * OpenUp Ltda. Issue #3538
	 * @author Raul Capecce - 06/02/2015
	 * @see
	 * @return Fecha convertida a String
	 */
	public String convertTimeStampToString(Timestamp data){
		String ret = null;
		
		ret = new SimpleDateFormat("dd/MM/yyyy").format(data);
		
		return ret;
	}
	
}
