package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MCashOpen extends X_UY_CashOpen implements DocAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6698964511487639240L;
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CashOpen_ID
	 * @param trxName
	 */
	public MCashOpen(Properties ctx, int UY_CashOpen_ID, String trxName) {
		super(ctx, UY_CashOpen_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashOpen(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if(newRecord || is_ValueChanged("c_bankaccount_id")){

			String sql = "";
			int count = 0;
			BigDecimal saldo = Env.ZERO;
			MCashClose close = null;

			//controlo no repetir un documento de apertura para misma cuenta y fecha
			sql = "select count(uy_cashopen_id)" +
					" from uy_cashopen" +
					" where c_bankaccount_id = " + this.getC_BankAccount_ID() + 
					" and datetrx = '" + this.getDateTrx() + "' and uy_cashopen_id <> " + this.get_ID();
			count = DB.getSQLValueEx(get_TrxName(), sql);

			if(count > 0) throw new AdempiereException ("Ya existe un documento de apertura para igual cuenta y fecha");

			//si la cuenta tiene movimientos obtengo ultimo cierre de caja para la fecha y cuenta bancaria 
			//if(MBankAccount.hasMovements(this.getC_BankAccount_ID())) {
			close = this.validateCashOpen();
			//}

			if(close!=null){

				saldo = close.getsaldo();
				if(saldo==null) saldo = Env.ZERO;
				this.setAmount(saldo); //seteo saldo inicial

			} else this.setAmount(saldo); //seteo saldo inicial = cero

		}

		return true;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 20/09/2013. #1304
	 * Metodo que realiza validaciones y si el resultado es positivo devuelve un cierre de caja
	 * 
	 * */
	
	public MCashClose validateCashOpen(){
		
		MCashOpen open = null;
		MCashClose close = null;
		String sql = "";
		int ID = 0;
		
		//obtengo ultima apertura de caja para la cuenta actual
		sql = "select max(uy_cashopen_id) from uy_cashopen where c_bankaccount_id = " + this.getC_BankAccount_ID();
		ID = DB.getSQLValueEx(null, sql);
		
		if(ID > 0){
		
			open = new MCashOpen(getCtx(),ID,null);	
			
		} else throw new AdempiereException ("No se encontro apertura de caja anterior"); 		
		
		//obtengo cierre para la cuenta y fecha
		sql = "select uy_cashclose_id" +
              " from uy_cashclose" +
              " where c_bankaccount_id = " + this.getC_BankAccount_ID() + " and datetrx='" + open.getDateTrx() + "'";
		ID = DB.getSQLValueEx(null, sql);
		
		if(ID < 0) {
			
			throw new AdempiereException ("No existe cierre para la cuenta actual y fecha '" + open.getDateTrx() + "'");			
			
		} else {
			
			close = new MCashClose(getCtx(),ID,null);
				
			if(!close.getDocStatus().equalsIgnoreCase("CO")) throw new AdempiereException ("El cierre N° " + close.getDocumentNo() + " para la cuenta actual y fecha '" + close.getDateTrx() + "' no esta completo");
		}
		
		return close;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 20/09/2013. #1304
	 * Metodo que devuelve una apertura de caja completa segun cuenta y fecha recibidas.
	 * 
	 * */
	public static MCashOpen getForAccountDate(Properties ctx, int accountID, Timestamp date, String trxName){

		String whereClause = X_UY_CashOpen.COLUMNNAME_C_BankAccount_ID + "=" + accountID + 
				" AND " + X_UY_CashOpen.COLUMNNAME_DateTrx + "='" + date + "' AND docStatus = 'CO'";

		MCashOpen cash = new Query(ctx, I_UY_CashOpen.Table_Name, whereClause, trxName).first();

		return cash;
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
		return null;
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
