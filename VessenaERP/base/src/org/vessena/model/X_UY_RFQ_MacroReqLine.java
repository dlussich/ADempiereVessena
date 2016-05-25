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

/** Generated Model for UY_RFQ_MacroReqLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RFQ_MacroReqLine extends PO implements I_UY_RFQ_MacroReqLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121217L;

    /** Standard Constructor */
    public X_UY_RFQ_MacroReqLine (Properties ctx, int UY_RFQ_MacroReqLine_ID, String trxName)
    {
      super (ctx, UY_RFQ_MacroReqLine_ID, trxName);
      /** if (UY_RFQ_MacroReqLine_ID == 0)
        {
			setUY_RFQ_MacroReq_ID (0);
			setUY_RFQ_MacroReqLine_ID (0);
			setUY_RFQ_Requisition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RFQ_MacroReqLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RFQ_MacroReqLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AutoSelected.
		@param AutoSelected AutoSelected	  */
	public void setAutoSelected (boolean AutoSelected)
	{
		set_Value (COLUMNNAME_AutoSelected, Boolean.valueOf(AutoSelected));
	}

	/** Get AutoSelected.
		@return AutoSelected	  */
	public boolean isAutoSelected () 
	{
		Object oo = get_Value(COLUMNNAME_AutoSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
		throw new IllegalArgumentException ("C_BPartner_ID is virtual column");	}

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

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		throw new IllegalArgumentException ("DatePromised is virtual column");	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
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

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ManualSelected.
		@param ManualSelected ManualSelected	  */
	public void setManualSelected (boolean ManualSelected)
	{
		set_Value (COLUMNNAME_ManualSelected, Boolean.valueOf(ManualSelected));
	}

	/** Get ManualSelected.
		@return ManualSelected	  */
	public boolean isManualSelected () 
	{
		Object oo = get_Value(COLUMNNAME_ManualSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rating.
		@param Rating 
		Classification or Importance
	  */
	public void setRating (String Rating)
	{
		throw new IllegalArgumentException ("Rating is virtual column");	}

	/** Get Rating.
		@return Classification or Importance
	  */
	public String getRating () 
	{
		return (String)get_Value(COLUMNNAME_Rating);
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		throw new IllegalArgumentException ("TotalAmt is virtual column");	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalPoints.
		@param TotalPoints TotalPoints	  */
	public void setTotalPoints (int TotalPoints)
	{
		set_ValueNoCheck (COLUMNNAME_TotalPoints, Integer.valueOf(TotalPoints));
	}

	/** Get TotalPoints.
		@return TotalPoints	  */
	public int getTotalPoints () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalPoints);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_QuoteVendor getUY_QuoteVendor() throws RuntimeException
    {
		return (I_UY_QuoteVendor)MTable.get(getCtx(), I_UY_QuoteVendor.Table_Name)
			.getPO(getUY_QuoteVendor_ID(), get_TrxName());	}

	/** Set UY_QuoteVendor.
		@param UY_QuoteVendor_ID UY_QuoteVendor	  */
	public void setUY_QuoteVendor_ID (int UY_QuoteVendor_ID)
	{
		if (UY_QuoteVendor_ID < 1) 
			set_Value (COLUMNNAME_UY_QuoteVendor_ID, null);
		else 
			set_Value (COLUMNNAME_UY_QuoteVendor_ID, Integer.valueOf(UY_QuoteVendor_ID));
	}

	/** Get UY_QuoteVendor.
		@return UY_QuoteVendor	  */
	public int getUY_QuoteVendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_QuoteVendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RFQ_MacroReq getUY_RFQ_MacroReq() throws RuntimeException
    {
		return (I_UY_RFQ_MacroReq)MTable.get(getCtx(), I_UY_RFQ_MacroReq.Table_Name)
			.getPO(getUY_RFQ_MacroReq_ID(), get_TrxName());	}

	/** Set UY_RFQ_MacroReq.
		@param UY_RFQ_MacroReq_ID UY_RFQ_MacroReq	  */
	public void setUY_RFQ_MacroReq_ID (int UY_RFQ_MacroReq_ID)
	{
		if (UY_RFQ_MacroReq_ID < 1) 
			set_Value (COLUMNNAME_UY_RFQ_MacroReq_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RFQ_MacroReq_ID, Integer.valueOf(UY_RFQ_MacroReq_ID));
	}

	/** Get UY_RFQ_MacroReq.
		@return UY_RFQ_MacroReq	  */
	public int getUY_RFQ_MacroReq_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_MacroReq_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RFQ_MacroReqLine.
		@param UY_RFQ_MacroReqLine_ID UY_RFQ_MacroReqLine	  */
	public void setUY_RFQ_MacroReqLine_ID (int UY_RFQ_MacroReqLine_ID)
	{
		if (UY_RFQ_MacroReqLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RFQ_MacroReqLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RFQ_MacroReqLine_ID, Integer.valueOf(UY_RFQ_MacroReqLine_ID));
	}

	/** Get UY_RFQ_MacroReqLine.
		@return UY_RFQ_MacroReqLine	  */
	public int getUY_RFQ_MacroReqLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_MacroReqLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RFQ_Requisition getUY_RFQ_Requisition() throws RuntimeException
    {
		return (I_UY_RFQ_Requisition)MTable.get(getCtx(), I_UY_RFQ_Requisition.Table_Name)
			.getPO(getUY_RFQ_Requisition_ID(), get_TrxName());	}

	/** Set UY_RFQ_Requisition.
		@param UY_RFQ_Requisition_ID UY_RFQ_Requisition	  */
	public void setUY_RFQ_Requisition_ID (int UY_RFQ_Requisition_ID)
	{
		if (UY_RFQ_Requisition_ID < 1) 
			set_Value (COLUMNNAME_UY_RFQ_Requisition_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RFQ_Requisition_ID, Integer.valueOf(UY_RFQ_Requisition_ID));
	}

	/** Get UY_RFQ_Requisition.
		@return UY_RFQ_Requisition	  */
	public int getUY_RFQ_Requisition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_Requisition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}