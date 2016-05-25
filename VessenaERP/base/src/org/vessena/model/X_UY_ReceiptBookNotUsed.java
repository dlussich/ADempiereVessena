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

/** Generated Model for UY_ReceiptBookNotUsed
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_ReceiptBookNotUsed extends PO implements I_UY_ReceiptBookNotUsed, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_ReceiptBookNotUsed (Properties ctx, int UY_ReceiptBookNotUsed_ID, String trxName)
    {
      super (ctx, UY_ReceiptBookNotUsed_ID, trxName);
      /** if (UY_ReceiptBookNotUsed_ID == 0)
        {
			setUY_ReceiptBookControl_ID (0);
			setUY_ReceiptBookNotUsed_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ReceiptBookNotUsed (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ReceiptBookNotUsed[")
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

	public I_UY_ReceiptBookControl getUY_ReceiptBookControl() throws RuntimeException
    {
		return (I_UY_ReceiptBookControl)MTable.get(getCtx(), I_UY_ReceiptBookControl.Table_Name)
			.getPO(getUY_ReceiptBookControl_ID(), get_TrxName());	}

	/** Set UY_ReceiptBookControl.
		@param UY_ReceiptBookControl_ID UY_ReceiptBookControl	  */
	public void setUY_ReceiptBookControl_ID (int UY_ReceiptBookControl_ID)
	{
		if (UY_ReceiptBookControl_ID < 1) 
			set_Value (COLUMNNAME_UY_ReceiptBookControl_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ReceiptBookControl_ID, Integer.valueOf(UY_ReceiptBookControl_ID));
	}

	/** Get UY_ReceiptBookControl.
		@return UY_ReceiptBookControl	  */
	public int getUY_ReceiptBookControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReceiptBookControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ReceiptBookNotUsed.
		@param UY_ReceiptBookNotUsed_ID UY_ReceiptBookNotUsed	  */
	public void setUY_ReceiptBookNotUsed_ID (int UY_ReceiptBookNotUsed_ID)
	{
		if (UY_ReceiptBookNotUsed_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ReceiptBookNotUsed_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ReceiptBookNotUsed_ID, Integer.valueOf(UY_ReceiptBookNotUsed_ID));
	}

	/** Get UY_ReceiptBookNotUsed.
		@return UY_ReceiptBookNotUsed	  */
	public int getUY_ReceiptBookNotUsed_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReceiptBookNotUsed_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}