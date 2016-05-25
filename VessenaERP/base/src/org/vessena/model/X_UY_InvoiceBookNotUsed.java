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

/** Generated Model for UY_InvoiceBookNotUsed
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_InvoiceBookNotUsed extends PO implements I_UY_InvoiceBookNotUsed, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_InvoiceBookNotUsed (Properties ctx, int UY_InvoiceBookNotUsed_ID, String trxName)
    {
      super (ctx, UY_InvoiceBookNotUsed_ID, trxName);
      /** if (UY_InvoiceBookNotUsed_ID == 0)
        {
			setUY_InvoiceBookControl_ID (0);
			setUY_InvoiceBookNotUsed_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvoiceBookNotUsed (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvoiceBookNotUsed[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set DocumentNoNotUsed.
		@param DocumentNoNotUsed DocumentNoNotUsed	  */
	public void setDocumentNoNotUsed (int DocumentNoNotUsed)
	{
		set_Value (COLUMNNAME_DocumentNoNotUsed, Integer.valueOf(DocumentNoNotUsed));
	}

	/** Get DocumentNoNotUsed.
		@return DocumentNoNotUsed	  */
	public int getDocumentNoNotUsed () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentNoNotUsed);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_InvoiceBookControl getUY_InvoiceBookControl() throws RuntimeException
    {
		return (I_UY_InvoiceBookControl)MTable.get(getCtx(), I_UY_InvoiceBookControl.Table_Name)
			.getPO(getUY_InvoiceBookControl_ID(), get_TrxName());	}

	/** Set UY_InvoiceBookControl.
		@param UY_InvoiceBookControl_ID UY_InvoiceBookControl	  */
	public void setUY_InvoiceBookControl_ID (int UY_InvoiceBookControl_ID)
	{
		if (UY_InvoiceBookControl_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceBookControl_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceBookControl_ID, Integer.valueOf(UY_InvoiceBookControl_ID));
	}

	/** Get UY_InvoiceBookControl.
		@return UY_InvoiceBookControl	  */
	public int getUY_InvoiceBookControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceBookControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_InvoiceBookNotUsed.
		@param UY_InvoiceBookNotUsed_ID UY_InvoiceBookNotUsed	  */
	public void setUY_InvoiceBookNotUsed_ID (int UY_InvoiceBookNotUsed_ID)
	{
		if (UY_InvoiceBookNotUsed_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceBookNotUsed_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceBookNotUsed_ID, Integer.valueOf(UY_InvoiceBookNotUsed_ID));
	}

	/** Get UY_InvoiceBookNotUsed.
		@return UY_InvoiceBookNotUsed	  */
	public int getUY_InvoiceBookNotUsed_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceBookNotUsed_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}