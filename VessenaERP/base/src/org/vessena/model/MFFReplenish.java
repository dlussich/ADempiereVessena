/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_C_Invoice;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MFFReplenish extends X_UY_FF_Replenish implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4129383711639793097L;

	/**
	 * @param ctx
	 * @param UY_FF_Replenish_ID
	 * @param trxName
	 */
	public MFFReplenish(Properties ctx, int UY_FF_Replenish_ID, String trxName) {
		super(ctx, UY_FF_Replenish_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFReplenish(Properties ctx, ResultSet rs, String trxName) {
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
	protected boolean beforeSave(boolean newRecord) {

		//controlo que solo pueda crearse 1 cabezal en forma manual para la misma sucursal y moneda
		if(newRecord || (!newRecord && (is_ValueChanged("UY_FF_Branch_ID") || is_ValueChanged("C_Currency_ID")))){
			if(this.isManual()){
				String sql = "select uy_ff_replenish_id" +
						" from uy_ff_replenish" +
						" where uy_ff_branch_id = " + this.getUY_FF_Branch_ID() +
						" and c_currency_id = " + this.getC_Currency_ID() +
						" and ismanual = 'Y' and uy_ff_replenish_id <> " + this.get_ID();

				int ID = DB.getSQLValueEx(get_TrxName(), sql);

				if(ID > 0) throw new AdempiereException ("Ya existe un documento creado manualmente con la misma sucursal y moneda ");
			}

		}

		if(this.getAmtOriginal().compareTo(Env.ZERO) <= 0) throw new AdempiereException("El saldo inicial debe ser mayor a cero");		

		//me aseguro de setear siempre los importes correctos
		this.setamtacumulate(this.getTotalAmtAcumulate()); //seteo importe acumulado
		this.setActualAmt(this.getAmtOriginal().subtract(this.getamtacumulate())); //seteo importe de saldo actual

		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		
		List<MFFReplenishLine> lines = this.getLines();
		
		if(lines.size() > 0) throw new AdempiereException("Imposible borrar, el documento tiene lineas");
		
		return true;
	}

	/***
	 * Obtiene y retorna importe total de lineas de este documento.
	 * OpenUp Ltda. Issue #1418
	 * @author Nicolas Sarlabos - 17/10/2013
	 * @see
	 * @return
	 */
	public BigDecimal getTotalAmtAcumulate(){
		
		String sql = "select coalesce(sum(amount),0) from uy_ff_replenishline where uy_ff_replenish_id = " + this.get_ID();		
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;
	}
	
	/***
	 * Obtiene y retorna lineas de este documento.
	 * OpenUp Ltda. Issue #1426
	 * @author Nicolas Sarlabos - 30/12/2013
	 * @see
	 * @return
	 */
	public List<MFFReplenishLine> getLines(){

		String whereClause = X_UY_FF_ReplenishLine.COLUMNNAME_UY_FF_Replenish_ID + "=" + this.get_ID();

		List<MFFReplenishLine> lines = new Query(getCtx(), I_UY_FF_ReplenishLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
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
	
	/**
	 * OpenUp. Nicolas Sarlabos. 27/12/2013
	 * Metodo que devuelve un documento de reposicion de FF en estado diferente a completo para una 
	 * determinada sucursal y moneda
	 * 
	 * */
	public static MFFReplenish forBranchCurrency(Properties ctx, int branchID, int currencyID, String trxName){

		String whereClause = X_UY_FF_Replenish.COLUMNNAME_UY_FF_Branch_ID + "=" + branchID + " AND " + X_UY_FF_Replenish.COLUMNNAME_C_Currency_ID + "=" + currencyID +
				" AND " + X_UY_FF_Replenish.COLUMNNAME_DocStatus + " <> 'CO'";

		MFFReplenish hdr = new Query(ctx, I_UY_FF_Replenish.Table_Name, whereClause, trxName)
		.setClient_ID()
		.setOrderBy(X_UY_FF_Replenish.COLUMNNAME_DateFrom + " desc").first();

		return hdr;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
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
		
		List<MFFReplenishLine> lines = this.getLines();
		
		if(lines.size() <= 0) throw new AdempiereException("Imposible completar, el documento no tiene lineas");
		
		this.validate(); //se validan las lineas antes de completar	
		this.setDateTo(new Timestamp(System.currentTimeMillis())); //seteo fecha HASTA
		this.generateDocs(); //se genera nuevo documento de reposicion y otro de confirmacion		
		
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
	 * Metodo que verifica que no existan lineas pertenecientes a documentos de rendicion
	 * y que no existan documentos que fueron reactivados y no se volvieron a completar o facturas generadas sin completar.
	 * OpenUp Ltda. Issue #1426
	 * @author Nicolas Sarlabos - 03/01/2014
	 * @see
	 * @return
	 */	
	private void validate() {
		
		String sql = "";
		int value = 0;
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try{

			//obtengo documentos de salida y rendicion de FF
			MDocType docCashOut = MDocType.forValue(getCtx(), "cashout", get_TrxName());
			MDocType docCashRepay = MDocType.forValue(getCtx(), "cashrepay", get_TrxName());

			if(docCashOut==null) throw new AdempiereException("Error al obtener documento de salida de fondo fijo");
			if(docCashRepay==null) throw new AdempiereException("Error al obtener documento de rendicion de fondo fijo");

			//verifico documentos de salida y rendicion
			sql = "select uy_ff_replenishline_id" +
					" from uy_ff_replenishline" +
					" where uy_ff_replenish_id = " + this.get_ID() +
					" and c_doctype_id = " + docCashRepay.get_ID();
			value = DB.getSQLValueEx(get_TrxName(), sql);

			if(value > 0) throw new AdempiereException ("Se han encontrado lineas de rendicion de fondo fijo en el documento actual. " +
					"Se deben generar las facturas correspondientes antes de completar.");

			//verifico documentos sin completar
			MDocType facturaFF = MDocType.forValue(getCtx(), "factfondofijo", get_TrxName());
			MDocType reciboFF = MDocType.forValue(getCtx(), "pagofondofijo", get_TrxName());
			MDocType ingresoFF = MDocType.forValue(getCtx(), "ingresofondofijo", get_TrxName());

			sql = "select uy_ff_replenishline_id" +
					" from uy_ff_replenishline" +
					" where uy_ff_replenish_id = " + this.get_ID() +
					" and c_doctype_id in (" + facturaFF.get_ID() + "," + reciboFF.get_ID() + "," + ingresoFF.get_ID() + ")";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			boolean valid = true; //defino variable de error
			
			while(rs.next()){

				MFFReplenishLine line = new MFFReplenishLine(getCtx(),rs.getInt("uy_ff_replenishline_id"),get_TrxName());

				if(facturaFF.get_ID()==line.getC_DocType_ID() || ingresoFF.get_ID()==line.getC_DocType_ID()){ //si es factura o ingreso
					
					MInvoice inv = new MInvoice(getCtx(),line.getRecord_ID(),get_TrxName());
					
					if(inv.getDocStatus().equalsIgnoreCase("IP")) valid = false;
					
				}else if(reciboFF.get_ID()==line.getC_DocType_ID()){ //si es recibo

					MPayment pay = new MPayment(getCtx(),line.getRecord_ID(),get_TrxName());
					
					if(pay.getDocStatus().equalsIgnoreCase("IP")) valid = false;

				} 				
			}
			
			if(!valid) throw new AdempiereException("Se han encontrado facturas, recibos o ingresos de fondo fijo en proceso. Debe completar los documentos antes de completar esta reposicion");

			//verifico que no existan facturas que fueron generadas pero nunca se completaron
			sql = "select record_id" +
					" from uy_ff_replenishline" +
					" where uy_ff_replenish_id = " + this.get_ID() +
					" and c_doctype_id = " + facturaFF.get_ID() + 
					" and ad_table_id = " + X_C_Invoice.Table_ID;
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				MInvoice inv = new MInvoice(getCtx(),rs.getInt("record_id"),get_TrxName()); //instancio la factura FF
				
				MFFCashOut cash = new MFFCashOut(getCtx(),inv.getUY_FF_CashOut_ID(),get_TrxName()); //obtengo la rendicion
				
				List<MInvoice> invoices = cash.getFFInvoiceForCashRepay(); //obtengo lista de facturas para la rendicion
				
				for (MInvoice invoice: invoices){
					
					if(invoice.getDocStatus().equalsIgnoreCase("DR")) 
						throw new AdempiereException("Debe completar la factura N° " + invoice.getDocumentNo() + " perteneciente a la rendicion N° " + cash.getDocumentNo());					
									
				}			
			}			
			
		} catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
	}

	/***
	 * Metodo que genera un documento de reposicion y otro de confirmacion. 
	 * OpenUp Ltda. Issue #1427
	 * @author Nicolas Sarlabos - 02/01/2014
	 * @see
	 * @return
	 */	
	private void generateDocs() {
		
		try{
			//genero nuevo documento de reposicion------------------------------------------------
			MFFReplenish rep = new MFFReplenish(getCtx(),0,get_TrxName());
			rep.setC_DocType_ID(this.getC_DocType_ID());
			rep.setDateFrom(new Timestamp(System.currentTimeMillis()));
			rep.setUY_FF_Branch_ID(this.getUY_FF_Branch_ID());
			rep.setC_Currency_ID(this.getC_Currency_ID());
			rep.setAmtOriginal(this.getActualAmt());
			rep.setamtacumulate(Env.ZERO);
			rep.setActualAmt(this.getActualAmt());
			rep.setIsManual(false);
			rep.setDocStatus(DocumentEngine.STATUS_Drafted);
			rep.setDocAction(DocumentEngine.ACTION_Complete);
			rep.saveEx();

			//genero nuevo documento de confirmacion de reposicion---------------------------------
			MFFReplenishConf conf = new MFFReplenishConf(getCtx(),0,get_TrxName());
			MDocType doc = MDocType.forValue(getCtx(), "confrepfondofijo", get_TrxName());

			if(doc==null) throw new AdempiereException("Error al obtener documento de confirmacion de reposicion de fondo fijo");

			conf.setC_DocType_ID(doc.get_ID());
			conf.setUY_FF_Replenish_ID(rep.get_ID());
			conf.setAmount(this.getamtacumulate());
			conf.setDocStatus(DocumentEngine.STATUS_Drafted);
			conf.setDocAction(DocumentEngine.ACTION_Complete);
			conf.saveEx();

			//se crea y despliega mensaje
			String value = "Se generaron los siguientes documentos: \n" +
					"Reposicion Fondo Fijo N° " + rep.getDocumentNo() + "\n" +
					"Confirmacion de Reposicion N° " + conf.getDocumentNo();
			
			ADialog.info(0,null,value);

		} catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
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
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		
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

}
