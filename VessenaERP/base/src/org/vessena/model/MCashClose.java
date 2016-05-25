package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

public class MCashClose extends X_UY_CashClose implements DocAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3930369665171711652L;
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CashClose_ID
	 * @param trxName
	 */
	public MCashClose(Properties ctx, int UY_CashClose_ID, String trxName) {
		super(ctx, UY_CashClose_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashClose(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if(newRecord || is_ValueChanged("c_bankaccount_id") || is_ValueChanged("datetrx")){

			String sql = "";
			BigDecimal saldoinicial = Env.ZERO;
			BigDecimal amtMovimientos = Env.ZERO;
			int count = 0;

			// Me aseguro fecha sin hora
			Timestamp dateAux = this.getDateTrx();
			this.setDateTrx(TimeUtil.trunc(dateAux, TimeUtil.TRUNC_DAY));
			
			//obtengo apertura de caja del dia
			MCashOpen cash = MCashOpen.getForAccountDate(getCtx(), this.getC_BankAccount_ID(), this.getDateTrx(), get_TrxName());

			if(cash==null) throw new AdempiereException ("No se encontro documento de apertura completo para la fecha actual");

			//controlo no repetir un documento de cierre para misma cuenta y fecha
			sql = "select count(uy_cashclose_id)" +
					" from uy_cashclose" +
					" where c_bankaccount_id = " + this.getC_BankAccount_ID() + 
					" and datetrx = '" + this.getDateTrx() + "' and uy_cashclose_id <> " + this.get_ID();
			count = DB.getSQLValueEx(get_TrxName(), sql);

			if(count > 0) throw new AdempiereException ("Ya existe un documento de cierre para igual cuenta y fecha");		

			if(is_ValueChanged("c_bankaccount_id")) this.deleteFromLines(); //limpio gilla de movimientos

			//seteo saldo inicial de apertura
			saldoinicial = cash.getAmount();
			if(saldoinicial==null) saldoinicial = Env.ZERO;
			this.setsaldoinicial(saldoinicial);	//seteo importe de saldo de apertura de cuenta seleccionada
			
			//obtengo importe de movimientos del dia
			if (MSysConfig.getBooleanValue("UY_CASHCLOSE_CR-DR", false, getAD_Client_ID())) {
				
				sql = "select coalesce(sum(amtsourcecr)-sum(amtsourcedr),0)" +
						" from uy_sum_accountstatus" +
						" where c_bankaccount_id = " + this.getC_BankAccount_ID() + " and datetrx = '" + this.getDateTrx() + "'";
				amtMovimientos = DB.getSQLValueBDEx(get_TrxName(), sql);				
				
			} else {
				
				sql = "select coalesce(sum(amtsourcedr)-sum(amtsourcecr),0)" +
						" from uy_sum_accountstatus" +
						" where c_bankaccount_id = " + this.getC_BankAccount_ID() + " and datetrx = '" + this.getDateTrx() + "'";
				amtMovimientos = DB.getSQLValueBDEx(get_TrxName(), sql);				
			}	

			if(amtMovimientos==null) amtMovimientos = Env.ZERO;
			this.setsaldo(saldoinicial.add(amtMovimientos));	//seteo importe de saldo de apertura de cuenta seleccionada

		}

		return true;
	}

	private void deleteFromLines() {
		
		String sql = "";
		
		try{
			
			sql = "DELETE FROM uy_cashcloseline" + 
				  " WHERE uy_cashclose_id =" + this.get_ID();
			
			DB.executeUpdate(sql,null);
	
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		//cargo grilla con movimientos del dia
		if(newRecord || is_ValueChanged("c_bankaccount_id") || is_ValueChanged("datetrx")){
			if(this.getsaldoinicial().compareTo(this.getsaldo())!=0) this.loadMovements();	
		}
		return true;
	}

	private void loadMovements() {
		
		String sql = "";
		MCashCloseLine line = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			
			//obtengo movimientos del dia
			sql = "select c_doctype_id, documentno, checkno, amtsourcedr, amtsourcecr" +
					" from uy_sum_accountstatus" +
					" where c_bankaccount_id = " + this.getC_BankAccount_ID() + " and datetrx = '" + this.getDateTrx() + "'" +
					" order by updated desc";
			
			pstmt = DB.prepareStatement (sql,get_TrxName());
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				
				line = new MCashCloseLine (getCtx(),0,get_TrxName());
				line.setUY_CashClose_ID(this.get_ID());
				line.setC_DocType_ID(rs.getInt("c_doctype_id"));
				line.setDocumentNo(rs.getString("documentno"));
				line.setAmtSourceDr(rs.getBigDecimal("amtsourcedr"));
				line.setAmtSourceCr(rs.getBigDecimal("amtsourcecr"));
				if(rs.getString("checkno")!=null) line.setCheckNo(rs.getString("checkno"));
				line.saveEx();
				
			}			
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 20/09/2013. #1304
	 * Metodo que devuelve un cierre de caja completo segun cuenta y fecha recibidas.
	 * 
	 * */
	public static MCashClose getForAccountDate(Properties ctx, int accountID, Timestamp date, String trxName){

		String whereClause = X_UY_CashClose.COLUMNNAME_C_BankAccount_ID + "=" + accountID + 
				" AND " + X_UY_CashClose.COLUMNNAME_DateTrx + "='" + date + "' AND docStatus = 'CO'";

		MCashClose cash = new Query(ctx, I_UY_CashClose.Table_Name, whereClause, trxName).first();

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
