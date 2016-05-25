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

/** Generated Model for UY_TT_LoadSubAgencyIssue
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_LoadSubAgencyIssue extends PO implements I_UY_TT_LoadSubAgencyIssue, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150823L;

    /** Standard Constructor */
    public X_UY_TT_LoadSubAgencyIssue (Properties ctx, int UY_TT_LoadSubAgencyIssue_ID, String trxName)
    {
      super (ctx, UY_TT_LoadSubAgencyIssue_ID, trxName);
      /** if (UY_TT_LoadSubAgencyIssue_ID == 0)
        {
			setErrorMsg (null);
			setUY_TT_LoadSubAgency_ID (0);
			setUY_TT_LoadSubAgencyIssue_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_LoadSubAgencyIssue (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_LoadSubAgencyIssue[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set cell.
		@param cell cell	  */
	public void setcell (String cell)
	{
		set_Value (COLUMNNAME_cell, cell);
	}

	/** Get cell.
		@return cell	  */
	public String getcell () 
	{
		return (String)get_Value(COLUMNNAME_cell);
	}

	/** Set Content.
		@param ContentText Content	  */
	public void setContentText (String ContentText)
	{
		set_Value (COLUMNNAME_ContentText, ContentText);
	}

	/** Get Content.
		@return Content	  */
	public String getContentText () 
	{
		return (String)get_Value(COLUMNNAME_ContentText);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set RowNo.
		@param RowNo RowNo	  */
	public void setRowNo (int RowNo)
	{
		set_Value (COLUMNNAME_RowNo, Integer.valueOf(RowNo));
	}

	/** Get RowNo.
		@return RowNo	  */
	public int getRowNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RowNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set sheet.
		@param sheet sheet	  */
	public void setsheet (String sheet)
	{
		set_Value (COLUMNNAME_sheet, sheet);
	}

	/** Get sheet.
		@return sheet	  */
	public String getsheet () 
	{
		return (String)get_Value(COLUMNNAME_sheet);
	}

	public I_UY_TT_LoadSubAgency getUY_TT_LoadSubAgency() throws RuntimeException
    {
		return (I_UY_TT_LoadSubAgency)MTable.get(getCtx(), I_UY_TT_LoadSubAgency.Table_Name)
			.getPO(getUY_TT_LoadSubAgency_ID(), get_TrxName());	}

	/** Set UY_TT_LoadSubAgency.
		@param UY_TT_LoadSubAgency_ID UY_TT_LoadSubAgency	  */
	public void setUY_TT_LoadSubAgency_ID (int UY_TT_LoadSubAgency_ID)
	{
		if (UY_TT_LoadSubAgency_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_LoadSubAgency_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_LoadSubAgency_ID, Integer.valueOf(UY_TT_LoadSubAgency_ID));
	}

	/** Get UY_TT_LoadSubAgency.
		@return UY_TT_LoadSubAgency	  */
	public int getUY_TT_LoadSubAgency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_LoadSubAgency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_LoadSubAgencyIssue.
		@param UY_TT_LoadSubAgencyIssue_ID UY_TT_LoadSubAgencyIssue	  */
	public void setUY_TT_LoadSubAgencyIssue_ID (int UY_TT_LoadSubAgencyIssue_ID)
	{
		if (UY_TT_LoadSubAgencyIssue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_LoadSubAgencyIssue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_LoadSubAgencyIssue_ID, Integer.valueOf(UY_TT_LoadSubAgencyIssue_ID));
	}

	/** Get UY_TT_LoadSubAgencyIssue.
		@return UY_TT_LoadSubAgencyIssue	  */
	public int getUY_TT_LoadSubAgencyIssue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_LoadSubAgencyIssue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}