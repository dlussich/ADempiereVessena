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

/** Generated Model for UY_TypeFactLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TypeFactLine extends PO implements I_UY_TypeFactLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141124L;

    /** Standard Constructor */
    public X_UY_TypeFactLine (Properties ctx, int UY_TypeFactLine_ID, String trxName)
    {
      super (ctx, UY_TypeFactLine_ID, trxName);
      /** if (UY_TypeFactLine_ID == 0)
        {
			setC_ElementValue_ID (0);
			setIsCalculated (false);
// N
			setIsDebit (false);
// N
			setManageBPartner (false);
// N
			setManageDateTrx (false);
// N
			setManageDocument (false);
// N
			setManageDueDate (false);
// N
			setUY_DocTypeFact_ID (0);
			setUY_TypeFact_ID (0);
			setUY_TypeFactLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TypeFactLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TypeFactLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Calculate.
		@param Calculate Calculate	  */
	public void setCalculate (String Calculate)
	{
		set_Value (COLUMNNAME_Calculate, Calculate);
	}

	/** Get Calculate.
		@return Calculate	  */
	public String getCalculate () 
	{
		return (String)get_Value(COLUMNNAME_Calculate);
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

	/** Set DocumentNoAux.
		@param DocumentNoAux 
		DocumentNoAux
	  */
	public void setDocumentNoAux (String DocumentNoAux)
	{
		set_Value (COLUMNNAME_DocumentNoAux, DocumentNoAux);
	}

	/** Get DocumentNoAux.
		@return DocumentNoAux
	  */
	public String getDocumentNoAux () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNoAux);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Calculated.
		@param IsCalculated 
		The value is calculated by the system
	  */
	public void setIsCalculated (boolean IsCalculated)
	{
		set_Value (COLUMNNAME_IsCalculated, Boolean.valueOf(IsCalculated));
	}

	/** Get Calculated.
		@return The value is calculated by the system
	  */
	public boolean isCalculated () 
	{
		Object oo = get_Value(COLUMNNAME_IsCalculated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDebit.
		@param IsDebit IsDebit	  */
	public void setIsDebit (boolean IsDebit)
	{
		set_Value (COLUMNNAME_IsDebit, Boolean.valueOf(IsDebit));
	}

	/** Get IsDebit.
		@return IsDebit	  */
	public boolean isDebit () 
	{
		Object oo = get_Value(COLUMNNAME_IsDebit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ManageBPartner.
		@param ManageBPartner ManageBPartner	  */
	public void setManageBPartner (boolean ManageBPartner)
	{
		set_Value (COLUMNNAME_ManageBPartner, Boolean.valueOf(ManageBPartner));
	}

	/** Get ManageBPartner.
		@return ManageBPartner	  */
	public boolean isManageBPartner () 
	{
		Object oo = get_Value(COLUMNNAME_ManageBPartner);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ManageDateTrx.
		@param ManageDateTrx 
		ManageDateTrx
	  */
	public void setManageDateTrx (boolean ManageDateTrx)
	{
		set_Value (COLUMNNAME_ManageDateTrx, Boolean.valueOf(ManageDateTrx));
	}

	/** Get ManageDateTrx.
		@return ManageDateTrx
	  */
	public boolean isManageDateTrx () 
	{
		Object oo = get_Value(COLUMNNAME_ManageDateTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ManageDocument.
		@param ManageDocument ManageDocument	  */
	public void setManageDocument (boolean ManageDocument)
	{
		set_Value (COLUMNNAME_ManageDocument, Boolean.valueOf(ManageDocument));
	}

	/** Get ManageDocument.
		@return ManageDocument	  */
	public boolean isManageDocument () 
	{
		Object oo = get_Value(COLUMNNAME_ManageDocument);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ManageDueDate.
		@param ManageDueDate 
		ManageDueDate
	  */
	public void setManageDueDate (boolean ManageDueDate)
	{
		set_Value (COLUMNNAME_ManageDueDate, Boolean.valueOf(ManageDueDate));
	}

	/** Get ManageDueDate.
		@return ManageDueDate
	  */
	public boolean isManageDueDate () 
	{
		Object oo = get_Value(COLUMNNAME_ManageDueDate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_DocTypeFact getUY_DocTypeFact() throws RuntimeException
    {
		return (I_UY_DocTypeFact)MTable.get(getCtx(), I_UY_DocTypeFact.Table_Name)
			.getPO(getUY_DocTypeFact_ID(), get_TrxName());	}

	/** Set UY_DocTypeFact.
		@param UY_DocTypeFact_ID UY_DocTypeFact	  */
	public void setUY_DocTypeFact_ID (int UY_DocTypeFact_ID)
	{
		if (UY_DocTypeFact_ID < 1) 
			set_Value (COLUMNNAME_UY_DocTypeFact_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DocTypeFact_ID, Integer.valueOf(UY_DocTypeFact_ID));
	}

	/** Get UY_DocTypeFact.
		@return UY_DocTypeFact	  */
	public int getUY_DocTypeFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DocTypeFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TypeFact getUY_TypeFact() throws RuntimeException
    {
		return (I_UY_TypeFact)MTable.get(getCtx(), I_UY_TypeFact.Table_Name)
			.getPO(getUY_TypeFact_ID(), get_TrxName());	}

	/** Set UY_TypeFact.
		@param UY_TypeFact_ID UY_TypeFact	  */
	public void setUY_TypeFact_ID (int UY_TypeFact_ID)
	{
		if (UY_TypeFact_ID < 1) 
			set_Value (COLUMNNAME_UY_TypeFact_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TypeFact_ID, Integer.valueOf(UY_TypeFact_ID));
	}

	/** Get UY_TypeFact.
		@return UY_TypeFact	  */
	public int getUY_TypeFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TypeFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TypeFactLine.
		@param UY_TypeFactLine_ID UY_TypeFactLine	  */
	public void setUY_TypeFactLine_ID (int UY_TypeFactLine_ID)
	{
		if (UY_TypeFactLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TypeFactLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TypeFactLine_ID, Integer.valueOf(UY_TypeFactLine_ID));
	}

	/** Get UY_TypeFactLine.
		@return UY_TypeFactLine	  */
	public int getUY_TypeFactLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TypeFactLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}