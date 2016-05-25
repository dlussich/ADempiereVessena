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

/** Generated Model for UY_FF_CashCountLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_FF_CashCountLine extends PO implements I_UY_FF_CashCountLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140110L;

    /** Standard Constructor */
    public X_UY_FF_CashCountLine (Properties ctx, int UY_FF_CashCountLine_ID, String trxName)
    {
      super (ctx, UY_FF_CashCountLine_ID, trxName);
      /** if (UY_FF_CashCountLine_ID == 0)
        {
			setAD_Table_ID (0);
			setAD_User_ID (0);
			setAmount (Env.ZERO);
			setApprovedBy (null);
			setC_DocType_ID (0);
			setChargeName (null);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDescription (null);
			setDocumentNo (null);
			setestado (false);
			setLine (0);
			setRecord_ID (0);
			setUY_FF_CashCount_ID (0);
			setUY_FF_CashCountLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FF_CashCountLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FF_CashCountLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ApprovedBy.
		@param ApprovedBy ApprovedBy	  */
	public void setApprovedBy (String ApprovedBy)
	{
		set_Value (COLUMNNAME_ApprovedBy, ApprovedBy);
	}

	/** Get ApprovedBy.
		@return ApprovedBy	  */
	public String getApprovedBy () 
	{
		return (String)get_Value(COLUMNNAME_ApprovedBy);
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

	/** Set Charge Name.
		@param ChargeName 
		Name of the Charge
	  */
	public void setChargeName (String ChargeName)
	{
		set_Value (COLUMNNAME_ChargeName, ChargeName);
	}

	/** Get Charge Name.
		@return Name of the Charge
	  */
	public String getChargeName () 
	{
		return (String)get_Value(COLUMNNAME_ChargeName);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set estado.
		@param estado estado	  */
	public void setestado (boolean estado)
	{
		set_Value (COLUMNNAME_estado, Boolean.valueOf(estado));
	}

	/** Get estado.
		@return estado	  */
	public boolean isestado () 
	{
		Object oo = get_Value(COLUMNNAME_estado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UY_FF_CashCount getUY_FF_CashCount() throws RuntimeException
    {
		return (I_UY_FF_CashCount)MTable.get(getCtx(), I_UY_FF_CashCount.Table_Name)
			.getPO(getUY_FF_CashCount_ID(), get_TrxName());	}

	/** Set UY_FF_CashCount.
		@param UY_FF_CashCount_ID UY_FF_CashCount	  */
	public void setUY_FF_CashCount_ID (int UY_FF_CashCount_ID)
	{
		if (UY_FF_CashCount_ID < 1) 
			set_Value (COLUMNNAME_UY_FF_CashCount_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FF_CashCount_ID, Integer.valueOf(UY_FF_CashCount_ID));
	}

	/** Get UY_FF_CashCount.
		@return UY_FF_CashCount	  */
	public int getUY_FF_CashCount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FF_CashCount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FF_CashCountLine.
		@param UY_FF_CashCountLine_ID UY_FF_CashCountLine	  */
	public void setUY_FF_CashCountLine_ID (int UY_FF_CashCountLine_ID)
	{
		if (UY_FF_CashCountLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FF_CashCountLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FF_CashCountLine_ID, Integer.valueOf(UY_FF_CashCountLine_ID));
	}

	/** Get UY_FF_CashCountLine.
		@return UY_FF_CashCountLine	  */
	public int getUY_FF_CashCountLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FF_CashCountLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}