/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 13/05/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;


/**
 * org.openup.model - MSUMAccountStatus
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 13/05/2013
 * @see
 */
public class MSUMAccountStatus extends X_UY_SUM_AccountStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4880157798594736511L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_SUM_AccountStatus_ID
	 * @param trxName
	 */
	public MSUMAccountStatus(Properties ctx, int UY_SUM_AccountStatus_ID,
			String trxName) {
		super(ctx, UY_SUM_AccountStatus_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MSUMAccountStatus(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 20/09/2013. #1304
		MBankAccount account = new MBankAccount(getCtx(), this.getC_BankAccount_ID(), get_TrxName());
		MBank bank = MBank.getFromAccount(getCtx(), account.get_ID());
		
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		
		if(!bank.isBankHandler() && account.getBankAccountType().equalsIgnoreCase("X")){
			
			if(this.getDateTrx().equals(this.getDueDate())){
				
				if (MSysConfig.getBooleanValue("UY_CHECK_DATE_SUMACCOUNT", true, this.getAD_Client_ID())) {
					
					if(!this.getDateTrx().equals(today)) throw new AdempiereException("Imposible afectar la caja en fecha diferente a la actual");
					
				}						
				
			}			
		}
				
		return true;
		//Fin OpenUp.
	}

	/***
	 * Actualiza informacion de documento para este modelo.
	 * OpenUp Ltda. Issue #1341 
	 * @author Gabriel Vila - 26/09/2013
	 * @see
	 * @param model
	 * @param newDocumentNo
	 */
	public static void updateDocumentNo(PO model, String newDocumentNo, String trxName){
		
		try{
			String action = " UPDATE " + Table_Name +
					" SET DocumentNo ='" + newDocumentNo + "' " +
					" WHERE ad_table_id =" + model.get_Table_ID() +
					" AND record_id =" + model.get_ID();
			DB.executeUpdateEx(action, trxName);
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Actualiza informacion de documento para este modelo.
	 * OpenUp Ltda. Issue #5457 
	 * @author Santiago Evans - 15/02/2016
	 * @see
	 * @param model
	 * @param newDocumentNo
	 */
	public static void deleteDocumentNo(int cinvid, String documentNo, String trxName){
		
		try{
			String action = " DELETE FROM " + Table_Name +					
					" WHERE documentno = '" + documentNo + "'" +
					" AND record_id = " + cinvid;
			DB.executeUpdateEx(action, trxName);
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}	

}
