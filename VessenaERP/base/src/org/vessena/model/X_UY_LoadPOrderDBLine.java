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

/** Generated Model for UY_LoadPOrderDBLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_LoadPOrderDBLine extends PO implements I_UY_LoadPOrderDBLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_LoadPOrderDBLine (Properties ctx, int UY_LoadPOrderDBLine_ID, String trxName)
    {
      super (ctx, UY_LoadPOrderDBLine_ID, trxName);
      /** if (UY_LoadPOrderDBLine_ID == 0)
        {
			setUY_LoadPOrderDBFilter_ID (0);
			setUY_LoadPOrderDBLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_LoadPOrderDBLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_LoadPOrderDBLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
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

	/** Set idinvoice.
		@param idinvoice idinvoice	  */
	public void setidinvoice (int idinvoice)
	{
		set_ValueNoCheck (COLUMNNAME_idinvoice, Integer.valueOf(idinvoice));
	}

	/** Get idinvoice.
		@return idinvoice	  */
	public int getidinvoice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_idinvoice);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_ValueNoCheck (COLUMNNAME_TotalLines, TotalLines);
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

	public I_UY_LoadPOrderDBFilter getUY_LoadPOrderDBFilter() throws RuntimeException
    {
		return (I_UY_LoadPOrderDBFilter)MTable.get(getCtx(), I_UY_LoadPOrderDBFilter.Table_Name)
			.getPO(getUY_LoadPOrderDBFilter_ID(), get_TrxName());	}

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

	/** Set UY_LoadPOrderDBLine.
		@param UY_LoadPOrderDBLine_ID UY_LoadPOrderDBLine	  */
	public void setUY_LoadPOrderDBLine_ID (int UY_LoadPOrderDBLine_ID)
	{
		if (UY_LoadPOrderDBLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_LoadPOrderDBLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_LoadPOrderDBLine_ID, Integer.valueOf(UY_LoadPOrderDBLine_ID));
	}

	/** Get UY_LoadPOrderDBLine.
		@return UY_LoadPOrderDBLine	  */
	public int getUY_LoadPOrderDBLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LoadPOrderDBLine_ID);
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