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

/** Generated Model for UY_Gerencial_CCDet
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Gerencial_CCDet extends PO implements I_UY_Gerencial_CCDet, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130304L;

    /** Standard Constructor */
    public X_UY_Gerencial_CCDet (Properties ctx, int UY_Gerencial_CCDet_ID, String trxName)
    {
      super (ctx, UY_Gerencial_CCDet_ID, trxName);
      /** if (UY_Gerencial_CCDet_ID == 0)
        {
			setC_Activity_ID_1 (0);
			setGL_Journal_ID (0);
			setUY_Gerencial_Account_ID (0);
			setUY_Gerencial_CCDet_ID (0);
			setUY_Gerencial_CC_ID (0);
			setUY_Gerencial_ID (0);
			setUY_Gerencial_Main_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Gerencial_CCDet (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Gerencial_CCDet[")
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

	/** Set C_Activity_ID_1.
		@param C_Activity_ID_1 C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1)
	{
		set_Value (COLUMNNAME_C_Activity_ID_1, Integer.valueOf(C_Activity_ID_1));
	}

	/** Get C_Activity_ID_1.
		@return C_Activity_ID_1	  */
	public int getC_Activity_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_1);
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
	public void setExecuteAction (boolean ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, Boolean.valueOf(ExecuteAction));
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public boolean isExecuteAction () 
	{
		Object oo = get_Value(COLUMNNAME_ExecuteAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_GL_Journal getGL_Journal() throws RuntimeException
    {
		return (org.compiere.model.I_GL_Journal)MTable.get(getCtx(), org.compiere.model.I_GL_Journal.Table_Name)
			.getPO(getGL_Journal_ID(), get_TrxName());	}

	/** Set Journal.
		@param GL_Journal_ID 
		General Ledger Journal
	  */
	public void setGL_Journal_ID (int GL_Journal_ID)
	{
		if (GL_Journal_ID < 1) 
			set_Value (COLUMNNAME_GL_Journal_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Journal_ID, Integer.valueOf(GL_Journal_ID));
	}

	/** Get Journal.
		@return General Ledger Journal
	  */
	public int getGL_Journal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Journal_ID);
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

	/** Set UY_Gerencial_CCDet.
		@param UY_Gerencial_CCDet_ID UY_Gerencial_CCDet	  */
	public void setUY_Gerencial_CCDet_ID (int UY_Gerencial_CCDet_ID)
	{
		if (UY_Gerencial_CCDet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Gerencial_CCDet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Gerencial_CCDet_ID, Integer.valueOf(UY_Gerencial_CCDet_ID));
	}

	/** Get UY_Gerencial_CCDet.
		@return UY_Gerencial_CCDet	  */
	public int getUY_Gerencial_CCDet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_CCDet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Gerencial_CC getUY_Gerencial_CC() throws RuntimeException
    {
		return (I_UY_Gerencial_CC)MTable.get(getCtx(), I_UY_Gerencial_CC.Table_Name)
			.getPO(getUY_Gerencial_CC_ID(), get_TrxName());	}

	/** Set UY_Gerencial_CC.
		@param UY_Gerencial_CC_ID UY_Gerencial_CC	  */
	public void setUY_Gerencial_CC_ID (int UY_Gerencial_CC_ID)
	{
		if (UY_Gerencial_CC_ID < 1) 
			set_Value (COLUMNNAME_UY_Gerencial_CC_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Gerencial_CC_ID, Integer.valueOf(UY_Gerencial_CC_ID));
	}

	/** Get UY_Gerencial_CC.
		@return UY_Gerencial_CC	  */
	public int getUY_Gerencial_CC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_CC_ID);
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
}