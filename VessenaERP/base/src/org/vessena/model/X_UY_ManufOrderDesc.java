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

/** Generated Model for UY_ManufOrderDesc
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ManufOrderDesc extends PO implements I_UY_ManufOrderDesc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131217L;

    /** Standard Constructor */
    public X_UY_ManufOrderDesc (Properties ctx, int UY_ManufOrderDesc_ID, String trxName)
    {
      super (ctx, UY_ManufOrderDesc_ID, trxName);
      /** if (UY_ManufOrderDesc_ID == 0)
        {
			setDescription (null);
			setUY_Budget_ID (0);
			setUY_ManufOrder_ID (0);
			setUY_ManufOrderDesc_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ManufOrderDesc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ManufOrderDesc[")
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

	public I_UY_Budget getUY_Budget() throws RuntimeException
    {
		return (I_UY_Budget)MTable.get(getCtx(), I_UY_Budget.Table_Name)
			.getPO(getUY_Budget_ID(), get_TrxName());	}

	/** Set UY_Budget.
		@param UY_Budget_ID UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID)
	{
		if (UY_Budget_ID < 1) 
			set_Value (COLUMNNAME_UY_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Budget_ID, Integer.valueOf(UY_Budget_ID));
	}

	/** Get UY_Budget.
		@return UY_Budget	  */
	public int getUY_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ManufOrder getUY_ManufOrder() throws RuntimeException
    {
		return (I_UY_ManufOrder)MTable.get(getCtx(), I_UY_ManufOrder.Table_Name)
			.getPO(getUY_ManufOrder_ID(), get_TrxName());	}

	/** Set UY_ManufOrder.
		@param UY_ManufOrder_ID UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID)
	{
		if (UY_ManufOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, Integer.valueOf(UY_ManufOrder_ID));
	}

	/** Get UY_ManufOrder.
		@return UY_ManufOrder	  */
	public int getUY_ManufOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ManufOrderDesc.
		@param UY_ManufOrderDesc_ID UY_ManufOrderDesc	  */
	public void setUY_ManufOrderDesc_ID (int UY_ManufOrderDesc_ID)
	{
		if (UY_ManufOrderDesc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ManufOrderDesc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ManufOrderDesc_ID, Integer.valueOf(UY_ManufOrderDesc_ID));
	}

	/** Get UY_ManufOrderDesc.
		@return UY_ManufOrderDesc	  */
	public int getUY_ManufOrderDesc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufOrderDesc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}