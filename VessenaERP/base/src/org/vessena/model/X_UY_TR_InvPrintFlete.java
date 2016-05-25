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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_TR_InvPrintFlete
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_InvPrintFlete extends PO implements I_UY_TR_InvPrintFlete, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150112L;

    /** Standard Constructor */
    public X_UY_TR_InvPrintFlete (Properties ctx, int UY_TR_InvPrintFlete_ID, String trxName)
    {
      super (ctx, UY_TR_InvPrintFlete_ID, trxName);
      /** if (UY_TR_InvPrintFlete_ID == 0)
        {
			setC_Invoice_ID (0);
			setIsPrinted (true);
// Y
			setUY_TR_InvPrintFlete_ID (0);
			setUY_TR_PrintFlete_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_InvPrintFlete (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_InvPrintFlete[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_TR_InvPrintFlete.
		@param UY_TR_InvPrintFlete_ID UY_TR_InvPrintFlete	  */
	public void setUY_TR_InvPrintFlete_ID (int UY_TR_InvPrintFlete_ID)
	{
		if (UY_TR_InvPrintFlete_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_InvPrintFlete_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_InvPrintFlete_ID, Integer.valueOf(UY_TR_InvPrintFlete_ID));
	}

	/** Get UY_TR_InvPrintFlete.
		@return UY_TR_InvPrintFlete	  */
	public int getUY_TR_InvPrintFlete_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_InvPrintFlete_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_PrintFlete getUY_TR_PrintFlete() throws RuntimeException
    {
		return (I_UY_TR_PrintFlete)MTable.get(getCtx(), I_UY_TR_PrintFlete.Table_Name)
			.getPO(getUY_TR_PrintFlete_ID(), get_TrxName());	}

	/** Set UY_TR_PrintFlete.
		@param UY_TR_PrintFlete_ID UY_TR_PrintFlete	  */
	public void setUY_TR_PrintFlete_ID (int UY_TR_PrintFlete_ID)
	{
		if (UY_TR_PrintFlete_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_PrintFlete_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_PrintFlete_ID, Integer.valueOf(UY_TR_PrintFlete_ID));
	}

	/** Get UY_TR_PrintFlete.
		@return UY_TR_PrintFlete	  */
	public int getUY_TR_PrintFlete_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_PrintFlete_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}