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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 * 	Natural Account
 *
 *  @author Jorg Janke
 *  @version $Id: MElementValue.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 *  
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			BF [ 1883533 ] Change to summary - valid combination issue
 * 			BF [ 2320411 ] Translate "Already posted to" message
 */
public class MElementValue extends X_C_ElementValue
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4765839867934329276L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_ElementValue_ID ID or 0 for new
	 *	@param trxName transaction
	 */
	public MElementValue(Properties ctx, int C_ElementValue_ID, String trxName)
	{
		super(ctx, C_ElementValue_ID, trxName);
		if (C_ElementValue_ID == 0)
		{
		//	setC_Element_ID (0);	//	Parent
		//	setName (null);
		//	setValue (null);
			setIsSummary (false);
			setAccountSign (ACCOUNTSIGN_Natural);
			setAccountType (ACCOUNTTYPE_Expense);
			setIsDocControlled(false);
			setIsForeignCurrency(false);
			setIsBankAccount(false);
			//
			setPostActual (true);
			setPostBudget (true);
			setPostEncumbrance (true);
			setPostStatistical (true);
		}
	}	//	MElementValue

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MElementValue(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MElementValue

	/**
	 * 	Full Constructor
	 *	@param ctx context
	 *	@param Value value
	 *	@param Name name
	 *	@param Description description
	 *	@param AccountType account type
	 *	@param AccountSign account sign
	 *	@param IsDocControlled doc controlled
	 *	@param IsSummary summary
	 *	@param trxName transaction
	 */
	public MElementValue (Properties ctx, String Value, String Name, String Description,
		String AccountType, String AccountSign,
		boolean IsDocControlled, boolean IsSummary, String trxName)
	{
		this (ctx, 0, trxName);
		setValue(Value);
		setName(Name);
		setDescription(Description);
		setAccountType(AccountType);
		setAccountSign(AccountSign);
		setIsDocControlled(IsDocControlled);
		setIsSummary(IsSummary);
	}	//	MElementValue
	
	/**
	 * 	Import Constructor
	 *	@param imp import
	 */
	public MElementValue (X_I_ElementValue imp)
	{
		this (imp.getCtx(), 0, imp.get_TrxName());
		setClientOrg(imp);
		set(imp);
	}	//	MElementValue

	/**
	 * 	Set/Update Settings from import
	 *	@param imp import
	 */
	public void set (X_I_ElementValue imp)
	{
		setValue(imp.getValue());
		setName(imp.getName());
		setDescription(imp.getDescription());
		setAccountType(imp.getAccountType());
		setAccountSign(imp.getAccountSign());
		setIsSummary(imp.isSummary());
		setIsDocControlled(imp.isDocControlled());
		setC_Element_ID(imp.getC_Element_ID());
		//
		setPostActual(imp.isPostActual());
		setPostBudget(imp.isPostBudget());
		setPostEncumbrance(imp.isPostEncumbrance());
		setPostStatistical(imp.isPostStatistical());
		//
	//	setC_BankAccount_ID(imp.getC_BankAccount_ID());
	//	setIsForeignCurrency(imp.isForeignCurrency());
	//	setC_Currency_ID(imp.getC_Currency_ID());
	//	setIsBankAccount(imp.isIsBankAccount());
	//	setValidFrom(null);
	//	setValidTo(null);
	}	//	set
	
	
	
	/**
	 * Is this a Balance Sheet Account
	 * @return boolean
	 */
	public boolean isBalanceSheet()
	{
		String accountType = getAccountType();
		return (ACCOUNTTYPE_Asset.equals(accountType)
			|| ACCOUNTTYPE_Liability.equals(accountType)
			|| ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	}	//	isBalanceSheet

	/**
	 * Is this an Activa Account
	 * @return boolean
	 */
	public boolean isActiva()
	{
		return ACCOUNTTYPE_Asset.equals(getAccountType());
	}	//	isActive

	/**
	 * Is this a Passiva Account
	 * @return boolean
	 */
	public boolean isPassiva()
	{
		String accountType = getAccountType();
		return (ACCOUNTTYPE_Liability.equals(accountType)
			|| ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	}	//	isPassiva

	/**
	 * 	User String Representation
	 *	@return info value - name
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ();
		sb.append(getValue()).append(" - ").append(getName());
		return sb.toString ();
	}	//	toString

	/**
	 * 	Extended String Representation
	 *	@return info
	 */
	public String toStringX ()
	{
		StringBuffer sb = new StringBuffer ("MElementValue[");
		sb.append(get_ID()).append(",").append(getValue()).append(" - ").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toStringX
	
	
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);
		//
		// Transform to summary level account
		if (!newRecord && isSummary() && is_ValueChanged(COLUMNNAME_IsSummary))
		{
			//
			// Check if we have accounting facts
			boolean match = new Query(getCtx(), I_Fact_Acct.Table_Name, I_Fact_Acct.COLUMNNAME_Account_ID+"=?", get_TrxName())
								.setParameters(getC_ElementValue_ID())
								.match();
			if (match)
			{
				throw new AdempiereException("@AlreadyPostedTo@");
			}
			//
			// Check Valid Combinations - teo_sarca FR [ 1883533 ]
			String whereClause = MAccount.COLUMNNAME_Account_ID+"=?";
			POResultSet<MAccount> rs = new Query(getCtx(), I_C_ValidCombination.Table_Name, whereClause, get_TrxName())
					.setParameters(get_ID())
					.scroll();
			try {
				while(rs.hasNext()) {
					rs.next().deleteEx(true);
				}
			}
			finally {
				rs.close();
			}
		}

		// OpenUp. Gabriel Vila. 16/12/2014. Issue  #1722.
		// Puedo duplicar codigos de cuenta siempre y cuando ambas cuentas sean capitulos
		MElementValue evAux = MElementValue.forValue(getCtx(), this.getValue(), get_TrxName());
		if (evAux != null){
			if ((!evAux.isSummary() || !this.isSummary()) && evAux.get_ID() != this.get_ID()){ //OpenUp. Nicolas Sarlabos 23/01/2014. #1831.
				throw new AdempiereException("Ya existe una Cuenta con ese Código.");
			}
		}
		// Fin OpenUp. Issue #1722.
		
		// OpenUp. Gabriel Vila. 05/09/2012. Issue  #18.
		// Me aseguro que la cuenta tenga un alias.
		if ((this.getAlias() == null) || (this.getAlias().equalsIgnoreCase(""))){
			this.setAlias(this.getValue());
		}
		// Fin OpenUp.
		
		return true;
	}	//	beforeSave
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord)
		{
			// afalcone [Bugs #1837219]
			int ad_Tree_ID= (new MElement(getCtx(), getC_Element_ID(), get_TrxName())).getAD_Tree_ID();
			String treeType= (new MTree(getCtx(),ad_Tree_ID,get_TrxName())).getTreeType();
			insert_Tree(treeType, getC_Element_ID());
			//	insert_Tree(MTree_Base.TREETYPE_ElementValue, getC_Element_ID()); Old
		}
		
		// OpenUp. Gabriel Vila. 03/09/2012. Issue #18. 
		// Gestiono informacion asociada a esta cuenta : combinacion, cargo, capitulo contable.
		this.setAccountInfo();
		// Fin OpenUp.

		return success;
	}	//	afterSave
	
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (success){
			delete_Tree(MTree_Base.TREETYPE_ElementValue);
			
			// OpenUp. Gabriel Vila. 17/07/2012.
			// Si elimino una cuenta, me aseguro de eliminar los cargos asociados
			MCharge charge = MCharge.forValue(getCtx(), this.getAlias(), get_TrxName());
			if (charge != null){
				if (charge.get_ID() > 0){
					charge.setIsManual(true);
					charge.deleteEx(true);
				}
			}
			// Fin OpenUp.
			
			
			// OpenUp. Gabriel Vila. 08/06/2012.
			// Si elimino una cuenta, me aseguro de eliminar la combinacion asociada
			String action = "DELETE FROM c_validcombination WHERE account_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			// Fin OpenUp
		}
			
		return success;
	}	//	afterDelete

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna modelo de cuenta contable segun un value recibido.
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 13/04/2011
	 */
	public static MElementValue getByValue(Properties ctx, String value, String trxName) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		MElementValue model = null;
		
		try{
			sql ="SELECT c_elementvalue_id " + 
 		  	" FROM c_elementvalue " + 
		  	" WHERE value =?"; 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, value.trim());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				model = new MElementValue(ctx, rs.getInt(1), trxName);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return model;
	}


	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna id del validcombination para esta cuenta.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 13/04/2011
	 */
	public int getValidCombinationID() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		int value = 0;
		
		try{
			sql ="SELECT c_validcombination_id " + 
 		  	" FROM c_validcombination " + 
		  	" WHERE ad_client_id =? " +
		  	" AND account_id =? " +
		  	" AND IsFullyQualified = 'Y'";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setInt(2, this.getC_ElementValue_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			throw new AdempiereException(e); 
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
	}

	/***
	 * Setea combinacion, cargo y capitulo contable de esta cuenta.
	 * OpenUp Ltda. Issue #18 
	 * @author Gabriel Vila - 03/09/2012
	 * @see
	 */
	public void setAccountInfo(){
	
		try{
			if (!this.isSummary()){
				
				MClient client = new MClient(getCtx(), this.getAD_Client_ID(), get_TrxName());
				
				if (client.getAcctSchema() != null){

					// Obtengo la combinacion de la cuenta y si no existe la creo ahora
					MAccount combination = MAccount.get(getCtx(),
							this.getAD_Client_ID(), this.getAD_Org_ID(), client.getAcctSchema().getC_AcctSchema_ID(), 
							this.get_ID(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, get_TrxName());

					if (combination != null && combination.get_ID() == 0){
						combination.saveEx();
					}

					// Me aseguro que la descripcion de la combinacion quede actualizada con respecto a esta cuenta
					MAccount.updateValueDescription(getCtx(), "Account_ID=" + getC_ElementValue_ID(),get_TrxName());
					
					// Obtiene cargo asociado a la combinacion y si no existe lo creo ahora
					MCharge charge = MCharge.forCombination(getCtx(), combination.get_ID(), get_TrxName()); 
					if (charge == null){
						charge = new MCharge(getCtx(), 0, get_TrxName());	
					}
					else{
						if (charge.get_ID() == 0){
							charge = new MCharge(getCtx(), 0, get_TrxName());
						}
					}
					
					// Seteo cargo
					charge.setAD_Client_ID(this.getAD_Client_ID());
					charge.setAD_Org_ID(this.getAD_Org_ID());
					charge.setValue(this.getValue());
					charge.setName(this.getName());
					charge.setDescription(this.getDescription());
					charge.setChargeAmt(Env.ZERO);
					charge.setIsSameTax(false);
					charge.setIsSameCurrency(false);
					charge.setIsTaxIncluded(false);
					charge.setC_TaxCategory_ID(MTaxCategory.getDefault().get_ID());
					charge.setIsManual(false);
					charge.setC_ValidCombination_ID(combination.get_ID());
					charge.saveEx();

					// Al cargo asociado le seteo esta combinacion en su parametrizacion de cuentas
					String action = " UPDATE C_Charge_Acct SET ch_expense_acct =" + combination.get_ID() + "," +
									" ch_revenue_acct =" + combination.get_ID() + 
									" WHERE c_charge_id =" + charge.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
			}
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new AdempiereException(e); 
		}

	}
	
	/***
	 * Obtiene y retorna lista de cuentas contables para un determindo Elemento contable.
	 * OpenUp Ltda. Issue #19 
	 * @author Gabriel Vila - 07/09/2012
	 * @see
	 * @param cElementID
	 * @param onlyNotSummary
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static List<MElementValue> forElement(int cElementID, boolean onlyNotSummary, Properties ctx, String trxName){
		
		String whereClause = X_C_ElementValue.COLUMNNAME_C_Element_ID + "=" + cElementID +							 
							((onlyNotSummary) ? (" AND " + X_C_ElementValue.COLUMNNAME_IsSummary + "='N'") : (""));
		
		List<MElementValue> cuentas = new Query(ctx, I_C_ElementValue.Table_Name, whereClause, trxName)
		.list();
		
		return cuentas;
		
	}
	
	/***
	 * Obtiene y retorna cuentas segun codigo recibido.
	 * OpenUp Ltda. Issue #29 
	 * @author Gabriel Vila - 13/09/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MElementValue forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_C_ElementValue.COLUMNNAME_Value + "='" + value + "' ";
		MElementValue cuenta = new Query(ctx, I_C_ElementValue.Table_Name, whereClause, trxName)
		.setClient_ID()
		.setOrderBy(COLUMNNAME_C_ElementValue_ID)
		.first();
		
		return cuenta;
		
	}

	
	/***
	 * Obtiene y retorna ultima cuenta creada segun codigo recibido.
	 * OpenUp Ltda. Issue #1722 
	 * @author Gabriel Vila - 13/09/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MElementValue forLastValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_C_ElementValue.COLUMNNAME_Value + "='" + value + "' ";
		MElementValue cuenta = new Query(ctx, I_C_ElementValue.Table_Name, whereClause, trxName)
		.setClient_ID()
		.setOrderBy(COLUMNNAME_C_ElementValue_ID + " Desc ")
		.first();
		
		return cuenta;
		
	}	
}	//	MElementValue
