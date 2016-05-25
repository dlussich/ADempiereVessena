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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Charge Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MCharge.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 *  
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>FR [ 2214883 ] Remove SQL code and Replace for Query
 */
public class MCharge extends X_C_Charge
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 630271473830196435L;


	/**
	 *  Get Charge Account
	 *  @param C_Charge_ID charge
	 *  @param as account schema
	 *  @param amount amount for expense(+)/revenue(-)
	 *  @return Charge Account or null
	 */
	public static MAccount getAccount (int C_Charge_ID, MAcctSchema as, BigDecimal amount)
	{
		if (C_Charge_ID == 0 || as == null)
			return null;

		String acctName = X_C_Charge_Acct.COLUMNNAME_Ch_Expense_Acct;		//  Expense (positive amt)
		if (amount != null && amount.signum() < 0)
			acctName = X_C_Charge_Acct.COLUMNNAME_Ch_Revenue_Acct;			//  Revenue (negative amt)
		String sql = "SELECT "+acctName+" FROM C_Charge_Acct WHERE C_Charge_ID=? AND C_AcctSchema_ID=?";
		int Account_ID = DB.getSQLValueEx(null, sql, C_Charge_ID, as.get_ID());
		//	No account
		if (Account_ID <= 0)
		{
			s_log.severe ("NO account for C_Charge_ID=" + C_Charge_ID);
			return null;
		}

		//	Return Account
		MAccount acct = MAccount.get (as.getCtx(), Account_ID);
		return acct;
	}   //  getAccount

	/**
	 * 	Get MCharge from Cache
	 *	@param ctx context
	 *	@param C_Charge_ID id
	 *	@return MCharge
	 */
	public static MCharge get (Properties ctx, int C_Charge_ID)
	{
		Integer key = new Integer (C_Charge_ID);
		MCharge retValue = (MCharge)s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MCharge (ctx, C_Charge_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**	Cache						*/
	private static CCache<Integer, MCharge> s_cache 
		= new CCache<Integer, MCharge> ("C_Charge", 10);
	
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MCharge.class);
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_Charge_ID id
	 *	@param trxName transaction
	 */
	public MCharge (Properties ctx, int C_Charge_ID, String trxName)
	{
		super (ctx, C_Charge_ID, trxName);
		if (C_Charge_ID == 0)
		{
			setChargeAmt (Env.ZERO);
			setIsSameCurrency (false);
			setIsSameTax (false);
			setIsTaxIncluded (false);	// N
		//	setName (null);
		//	setC_TaxCategory_ID (0);
		}
	}	//	MCharge

	/**
	 * 	Load Constructor
	 *	@param ctx ctx
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MCharge (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MCharge

	
	
	/***
	 * OpenUp. Gabriel Vila. 05/09/2012. Issue #18.
	 * Valido que no puedan actualizar informacion de un cargo
	 * creado de manera automatica al crearse una cuenta contable.
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {

		/*
		if (newRecord) return true;
		
		if (!this.isManual()){
			if (!is_ValueChanged(COLUMNNAME_IsManual)){
				throw new AdempiereException("No es posible modificar datos de un Cargo creado automaticamente a partir de una Cuenta Contable.\n" +
						" Modifique la Cuenta Contable asociada y el Cargo se actualizará automaticamente.");
			}
		}
		*/
		
		return true;
	}

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord && success)
			insert_Accounting("C_Charge_Acct", "C_AcctSchema_Default", null);

		return success;
	}	//	afterSave

	/**
	 * 	Before Delete
	 *	@return true
	 */
	protected boolean beforeDelete ()
	{
		// OpenUp. Gabriel Vila. 05/09/2012. Issue #18.
		// No permito eliminar cargos que fueron creados automaticamente a 
		// partir de la creacion de una cuenta contable.
		if (!this.isManual()){
			throw new AdempiereException("No es posible eliminar un Cargo creado automaticamente a partir de una Cuenta Contable.\n" +
					" Elimine la Cuenta Contable asociada y el Cargo se eliminará automaticamente.");
		}
		// Fin OpenUp.
		
		return delete_Accounting("C_Charge_Acct"); 
	}	//	beforeDelete

	
	/***
	 * Obtiene y retorna cargo asociado a una determinada combinacion de cuenta contable
	 * OpenUp Ltda. Issue #18 
	 * @author Gabriel Vila - 03/09/2012
	 * @see
	 * @param ctx
	 * @param cValidCombinationID
	 * @return
	 */
	public static MCharge forCombination (Properties ctx, int cValidCombinationID, String trxName)
	{
		String whereClause = X_C_Charge.COLUMNNAME_C_ValidCombination_ID + " =" + cValidCombinationID;
		
		MCharge value = new Query(ctx, I_C_Charge.Table_Name, whereClause, trxName)
		.setClient_ID()
		.firstOnly();

		return value;
		
	}

	
	/***
	 * Obtiene y retorna cargo segun value recibido.
	 * OpenUp Ltda. Issue #18 
	 * @author Gabriel Vila - 05/09/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @return
	 */
	public static MCharge forValue (Properties ctx, String value, String trxName)
	{
		String whereClause = X_C_Charge.COLUMNNAME_Value + " ='" + value + "' ";
		
		MCharge charge = new Query(ctx, I_C_Charge.Table_Name, whereClause, trxName)
		.setClient_ID()
		.firstOnly();

		return charge;
		
	}

	
	/***
	 * Obtiene y retorna cargos automaticos (creados de manera automatica al crearse una cuenta contable).
	 * OpenUp Ltda. Issue #19 
	 * @author Gabriel Vila - 07/09/2012
	 * @see
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static List<MCharge> getAutomaticCharges (Properties ctx, String trxName){
		
		String whereClause = X_C_Charge.COLUMNNAME_IsManual + "='N' ";
		
		List<MCharge> charges = new Query(ctx, I_C_Charge.Table_Name, whereClause, trxName)
		.list();
		
		return charges;
	}
	
}	//	MCharge
