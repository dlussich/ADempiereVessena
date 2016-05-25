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

/** Generated Model for UY_LoadPOrderDBFilter
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_LoadPOrderDBFilter extends PO implements I_UY_LoadPOrderDBFilter, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_LoadPOrderDBFilter (Properties ctx, int UY_LoadPOrderDBFilter_ID, String trxName)
    {
      super (ctx, UY_LoadPOrderDBFilter_ID, trxName);
      /** if (UY_LoadPOrderDBFilter_ID == 0)
        {
			setTotalLines (Env.ZERO);
			setUY_LoadPOrderDBFilter_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_LoadPOrderDBFilter (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_LoadPOrderDBFilter[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Request = RQ */
	public static final String DOCACTION_Request = "RQ";
	/** Asign = AS */
	public static final String DOCACTION_Asign = "AS";
	/** Pick = PK */
	public static final String DOCACTION_Pick = "PK";
	/** Recive = RV */
	public static final String DOCACTION_Recive = "RV";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=135 */
	public static final int DOCSTATUS_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCSTATUS_Complete = "CO";
	/** Approve = AP */
	public static final String DOCSTATUS_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCSTATUS_Reject = "RJ";
	/** Post = PO */
	public static final String DOCSTATUS_Post = "PO";
	/** Void = VO */
	public static final String DOCSTATUS_Void = "VO";
	/** Close = CL */
	public static final String DOCSTATUS_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCSTATUS_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCSTATUS_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCSTATUS_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCSTATUS_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCSTATUS_None = "--";
	/** Prepare = PR */
	public static final String DOCSTATUS_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCSTATUS_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCSTATUS_WaitComplete = "WC";
	/** Request = RQ */
	public static final String DOCSTATUS_Request = "RQ";
	/** Asign = AS */
	public static final String DOCSTATUS_Asign = "AS";
	/** Pick = PK */
	public static final String DOCSTATUS_Pick = "PK";
	/** Recive = RV */
	public static final String DOCSTATUS_Recive = "RV";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set DocumentNoInvoice.
		@param DocumentNoInvoice DocumentNoInvoice	  */
	public void setDocumentNoInvoice (String DocumentNoInvoice)
	{
		set_Value (COLUMNNAME_DocumentNoInvoice, DocumentNoInvoice);
	}

	/** Get DocumentNoInvoice.
		@return DocumentNoInvoice	  */
	public String getDocumentNoInvoice () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNoInvoice);
	}

	/** Set Memo.
		@param Memo 
		Memo Text
	  */
	public void setMemo (String Memo)
	{
		set_ValueNoCheck (COLUMNNAME_Memo, Memo);
	}

	/** Get Memo.
		@return Memo Text
	  */
	public String getMemo () 
	{
		return (String)get_Value(COLUMNNAME_Memo);
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_LoadPOrderDBFilter.
		@param UY_LoadPOrderDBFilter_ID UY_LoadPOrderDBFilter	  */
	public void setUY_LoadPOrderDBFilter_ID (int UY_LoadPOrderDBFilter_ID)
	{
		if (UY_LoadPOrderDBFilter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_LoadPOrderDBFilter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_LoadPOrderDBFilter_ID, Integer.valueOf(UY_LoadPOrderDBFilter_ID));
	}

	/** Get UY_LoadPOrderDBFilter.
		@return UY_LoadPOrderDBFilter	  */
	public int getUY_LoadPOrderDBFilter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LoadPOrderDBFilter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_procesar.
		@param uy_procesar uy_procesar	  */
	public void setuy_procesar (boolean uy_procesar)
	{
		set_Value (COLUMNNAME_uy_procesar, Boolean.valueOf(uy_procesar));
	}

	/** Get uy_procesar.
		@return uy_procesar	  */
	public boolean isuy_procesar () 
	{
		Object oo = get_Value(COLUMNNAME_uy_procesar);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}