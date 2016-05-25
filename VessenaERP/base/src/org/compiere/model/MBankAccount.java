/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 *  Bank Account Model
 *
 *  @author Jorg Janke
 *  @version $Id: MBankAccount.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MBankAccount extends X_C_BankAccount
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8742307130542608791L;

	/**
	 * 	Get BankAccount from Cache
	 *	@param ctx context
	 *	@param C_BankAccount_ID id
	 *	@return MBankAccount
	 */
	public static MBankAccount get (Properties ctx, int C_BankAccount_ID)
	{
		Integer key = new Integer (C_BankAccount_ID);
		MBankAccount retValue = (MBankAccount) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MBankAccount (ctx, C_BankAccount_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	} //	get

	/**	Cache						*/
	private static CCache<Integer,MBankAccount>	s_cache
		= new CCache<Integer,MBankAccount>("C_BankAccount", 5);
	
	/**
	 * 	Bank Account Model
	 *	@param ctx context
	 *	@param C_BankAccount_ID bank account
	 *	@param trxName transaction
	 */
	public MBankAccount (Properties ctx, int C_BankAccount_ID, String trxName)
	{
		super (ctx, C_BankAccount_ID, trxName);
		if (C_BankAccount_ID == 0)
		{
			setIsDefault (false);
			setBankAccountType (BANKACCOUNTTYPE_Checking);
			setCurrentBalance (Env.ZERO);
		//	setC_Currency_ID (0);
			setCreditLimit (Env.ZERO);
		//	setC_BankAccount_ID (0);
		}
	}	//	MBankAccount

	/**
	 * 	Bank Account Model
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MBankAccount (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MBankAccount

	/**
	 * 	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MBankAccount[")
			.append (get_ID())
			.append("-").append(getAccountNo())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Get Bank
	 *	@return bank parent
	 */
	public MBank getBank()
	{
		return MBank.get(getCtx(), getC_Bank_ID());
	}	//	getBank
	
	/**OpenUp. Nicolas Sarlabos. 15/11/2012
	 * Metodo que recibe un Id de cuenta bancaria y devuelve el banco correspondiente
	 * @param account_ID
	 * @return
	 */
	
	public MBank getBankFromAccount(int account_ID){
		
		return MBank.getFromAccount(getCtx(),account_ID);
		
	}
	
	/**
	 * 	Get Bank Name and Account No
	 *	@return Bank/Account
	 */
	public String getName()
	{
		return getBank().getName() + " " + getAccountNo();
	}	//	getName
	
	/**
	 * 	After Save
	 *	@param newRecord new record
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord && success)
			return insert_Accounting("C_BankAccount_Acct", "C_AcctSchema_Default", null);
		return success;
	}	//	afterSave
	
	/***
	 * OpenUp. Gabriel Vila. 18/10/2012. Issue #76
	 * Segun el tipo que tiene la forma de pago seleccionada, debo 
	 * actualizar valor de TenderType para mantener funcionamiento de
	 * bancos.
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Setea tendertype segun el banco se considere como banco o no.
		MBank bank = (MBank)this.getC_Bank();
		if (bank.isBankHandler()){
			this.setTenderType(TENDERTYPE_Check);
		}
		else{
			this.setTenderType(TENDERTYPE_Cash);
		}
		
		return true;
	}

	/**
	 * 	Before Delete
	 *	@return true
	 */
	protected boolean beforeDelete ()
	{
		return delete_Accounting("C_BankAccount_Acct");
	}	//	beforeDelete
	
	/**
	 * Verifica que la caja seleccionada para el cobro se encuentre abierta.
	 * OpenUp Ltda. Issue #1218
	 * @author Nicolas Sarlabos - 14/08/2013
	 * @see
	 * @return
	 */
	public void validateCashOpen(Timestamp dateTrx) {

		String sql = "";
		int ID = 0;

		if(this.get_ValueAsBoolean("IsCashOpen")){
			
			//verifico existencia de apertura de caja en estado completo
			sql = "select uy_cashopen_id from uy_cashopen where c_bankaccount_id =" + this.get_ID() + 
					" and datetrx = '" + dateTrx + "' and docstatus='CO'";
			ID = DB.getSQLValueEx(null, sql);

			if(ID <= 0) throw new AdempiereException ("No existe apertura de caja completa para la caja y fecha actuales");		
			
		}

		//verifico existencia de cierre de caja en estado completo
		sql = "select uy_cashclose_id from uy_cashclose where c_bankaccount_id =" + this.get_ID() + 
				" and datetrx = '" + dateTrx + "' and docstatus='CO'";
		ID = DB.getSQLValueEx(null, sql);

		if(ID > 0) throw new AdempiereException ("Existe cierre de caja completo para la caja y fecha actuales");

	}
	
	/**
	 * Devuelve true si la cuenta de caja recibida tiene movimientos.
	 * OpenUp Ltda. Issue #1304
	 * @author Nicolas Sarlabos - 20/09/2013
	 * @see
	 * @return
	 */
	public static boolean hasMovements(int accountID) {

		String sql = "select uy_sum_accountstatus_id from uy_sum_accountstatus where c_bankaccount_id =" + accountID;
		int ID = DB.getSQLValueEx(null, sql);

		if(ID <= 0) return false;
		else return true;

	}


}	//	MBankAccount
