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

/** Generated Model for UY_BG_CustSubCust
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_CustSubCust extends PO implements I_UY_BG_CustSubCust, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150903L;

    /** Standard Constructor */
    public X_UY_BG_CustSubCust (Properties ctx, int UY_BG_CustSubCust_ID, String trxName)
    {
      super (ctx, UY_BG_CustSubCust_ID, trxName);
      /** if (UY_BG_CustSubCust_ID == 0)
        {
			setUY_BG_CustSubCust_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_CustSubCust (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_CustSubCust[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_UY_BG_Customer getUY_BG_Customer() throws RuntimeException
    {
		return (I_UY_BG_Customer)MTable.get(getCtx(), I_UY_BG_Customer.Table_Name)
			.getPO(getUY_BG_Customer_ID(), get_TrxName());	}

	/** Set UY_BG_Customer.
		@param UY_BG_Customer_ID UY_BG_Customer	  */
	public void setUY_BG_Customer_ID (int UY_BG_Customer_ID)
	{
		if (UY_BG_Customer_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Customer_ID, Integer.valueOf(UY_BG_Customer_ID));
	}

	/** Get UY_BG_Customer.
		@return UY_BG_Customer	  */
	public int getUY_BG_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BG_CustSubCust.
		@param UY_BG_CustSubCust_ID UY_BG_CustSubCust	  */
	public void setUY_BG_CustSubCust_ID (int UY_BG_CustSubCust_ID)
	{
		if (UY_BG_CustSubCust_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_CustSubCust_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_CustSubCust_ID, Integer.valueOf(UY_BG_CustSubCust_ID));
	}

	/** Get UY_BG_CustSubCust.
		@return UY_BG_CustSubCust	  */
	public int getUY_BG_CustSubCust_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_CustSubCust_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_SubCustomer getUY_BG_SubCustomer() throws RuntimeException
    {
		return (I_UY_BG_SubCustomer)MTable.get(getCtx(), I_UY_BG_SubCustomer.Table_Name)
			.getPO(getUY_BG_SubCustomer_ID(), get_TrxName());	}

	/** Set UY_BG_SubCustomer.
		@param UY_BG_SubCustomer_ID UY_BG_SubCustomer	  */
	public void setUY_BG_SubCustomer_ID (int UY_BG_SubCustomer_ID)
	{
		if (UY_BG_SubCustomer_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_SubCustomer_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_SubCustomer_ID, Integer.valueOf(UY_BG_SubCustomer_ID));
	}

	/** Get UY_BG_SubCustomer.
		@return UY_BG_SubCustomer	  */
	public int getUY_BG_SubCustomer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_SubCustomer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}