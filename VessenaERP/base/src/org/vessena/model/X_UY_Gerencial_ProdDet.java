/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_Gerencial_ProdDet
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Gerencial_ProdDet extends PO implements I_UY_Gerencial_ProdDet, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130302L;

    /** Standard Constructor */
    public X_UY_Gerencial_ProdDet (Properties ctx, int UY_Gerencial_ProdDet_ID, String trxName)
    {
      super (ctx, UY_Gerencial_ProdDet_ID, trxName);
      /** if (UY_Gerencial_ProdDet_ID == 0)
        {
			setC_BPartner_ID (0);
			setM_Product_ID (0);
			setUY_Gerencial_Account_ID (0);
			setUY_Gerencial_ID (0);
			setUY_Gerencial_Main_ID (0);
			setUY_Gerencial_ProdDet_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Gerencial_ProdDet (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UY_Gerencial_ProdDet[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account Type.
		@param AccountType 
		Indicates the type of account
	  */
	public void setAccountType (String AccountType)
	{
		set_Value (COLUMNNAME_AccountType, AccountType);
	}

	/** Get Account Type.
		@return Indicates the type of account
	  */
	public String getAccountType () 
	{
		return (String)get_Value(COLUMNNAME_AccountType);
	}

	/** Set acctname.
		@param acctname acctname	  */
	public void setacctname (String acctname)
	{
		set_Value (COLUMNNAME_acctname, acctname);
	}

	/** Get acctname.
		@return acctname	  */
	public String getacctname () 
	{
		return (String)get_Value(COLUMNNAME_acctname);
	}

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set DateAudit.
		@param DateAudit DateAudit	  */
	public void setDateAudit (Timestamp DateAudit)
	{
		set_Value (COLUMNNAME_DateAudit, DateAudit);
	}

	/** Get DateAudit.
		@return DateAudit	  */
	public Timestamp getDateAudit () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAudit);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Parent.
		@param Parent_ID 
		Parent of Entity
	  */
	public void setParent_ID (int Parent_ID)
	{
		if (Parent_ID < 1) 
			set_Value (COLUMNNAME_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_ID, Integer.valueOf(Parent_ID));
	}

	/** Get Parent.
		@return Parent of Entity
	  */
	public int getParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set parentname.
		@param parentname parentname	  */
	public void setparentname (String parentname)
	{
		set_Value (COLUMNNAME_parentname, parentname);
	}

	/** Get parentname.
		@return parentname	  */
	public String getparentname () 
	{
		return (String)get_Value(COLUMNNAME_parentname);
	}

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set seqnoparent.
		@param seqnoparent seqnoparent	  */
	public void setseqnoparent (int seqnoparent)
	{
		set_Value (COLUMNNAME_seqnoparent, Integer.valueOf(seqnoparent));
	}

	/** Get seqnoparent.
		@return seqnoparent	  */
	public int getseqnoparent () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_seqnoparent);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UserAudit.
		@param UserAudit UserAudit	  */
	public void setUserAudit (int UserAudit)
	{
		set_Value (COLUMNNAME_UserAudit, Integer.valueOf(UserAudit));
	}

	/** Get UserAudit.
		@return UserAudit	  */
	public int getUserAudit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserAudit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Gerencial_Account getUY_Gerencial_Account() throws RuntimeException
    {
		return (I_UY_Gerencial_Account)MTable.get(getCtx(), I_UY_Gerencial_Account.Table_Name)
			.getPO(getUY_Gerencial_Account_ID(), get_TrxName());	}

	/** Set UY_Gerencial_Account.
		@param UY_Gerencial_Account_ID UY_Gerencial_Account	  */
	public void setUY_Gerencial_Account_ID (int UY_Gerencial_Account_ID)
	{
		if (UY_Gerencial_Account_ID < 1) 
			set_Value (COLUMNNAME_UY_Gerencial_Account_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Gerencial_Account_ID, Integer.valueOf(UY_Gerencial_Account_ID));
	}

	/** Get UY_Gerencial_Account.
		@return UY_Gerencial_Account	  */
	public int getUY_Gerencial_Account_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Gerencial getUY_Gerencial() throws RuntimeException
    {
		return (I_UY_Gerencial)MTable.get(getCtx(), I_UY_Gerencial.Table_Name)
			.getPO(getUY_Gerencial_ID(), get_TrxName());	}

	/** Set UY_Gerencial.
		@param UY_Gerencial_ID UY_Gerencial	  */
	public void setUY_Gerencial_ID (int UY_Gerencial_ID)
	{
		if (UY_Gerencial_ID < 1) 
			set_Value (COLUMNNAME_UY_Gerencial_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Gerencial_ID, Integer.valueOf(UY_Gerencial_ID));
	}

	/** Get UY_Gerencial.
		@return UY_Gerencial	  */
	public int getUY_Gerencial_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Gerencial_Main getUY_Gerencial_Main() throws RuntimeException
    {
		return (I_UY_Gerencial_Main)MTable.get(getCtx(), I_UY_Gerencial_Main.Table_Name)
			.getPO(getUY_Gerencial_Main_ID(), get_TrxName());	}

	/** Set UY_Gerencial_Main.
		@param UY_Gerencial_Main_ID UY_Gerencial_Main	  */
	public void setUY_Gerencial_Main_ID (int UY_Gerencial_Main_ID)
	{
		if (UY_Gerencial_Main_ID < 1) 
			set_Value (COLUMNNAME_UY_Gerencial_Main_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Gerencial_Main_ID, Integer.valueOf(UY_Gerencial_Main_ID));
	}

	/** Get UY_Gerencial_Main.
		@return UY_Gerencial_Main	  */
	public int getUY_Gerencial_Main_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_Main_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Gerencial_ProdDet.
		@param UY_Gerencial_ProdDet_ID UY_Gerencial_ProdDet	  */
	public void setUY_Gerencial_ProdDet_ID (int UY_Gerencial_ProdDet_ID)
	{
		if (UY_Gerencial_ProdDet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Gerencial_ProdDet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Gerencial_ProdDet_ID, Integer.valueOf(UY_Gerencial_ProdDet_ID));
	}

	/** Get UY_Gerencial_ProdDet.
		@return UY_Gerencial_ProdDet	  */
	public int getUY_Gerencial_ProdDet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_ProdDet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Gerencial_Prod getUY_Gerencial_Prod() throws RuntimeException
    {
		return (I_UY_Gerencial_Prod)MTable.get(getCtx(), I_UY_Gerencial_Prod.Table_Name)
			.getPO(getUY_Gerencial_Prod_ID(), get_TrxName());	}

	/** Set UY_Gerencial_Prod.
		@param UY_Gerencial_Prod_ID UY_Gerencial_Prod	  */
	public void setUY_Gerencial_Prod_ID (int UY_Gerencial_Prod_ID)
	{
		if (UY_Gerencial_Prod_ID < 1) 
			set_Value (COLUMNNAME_UY_Gerencial_Prod_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Gerencial_Prod_ID, Integer.valueOf(UY_Gerencial_Prod_ID));
	}

	/** Get UY_Gerencial_Prod.
		@return UY_Gerencial_Prod	  */
	public int getUY_Gerencial_Prod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_Prod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}