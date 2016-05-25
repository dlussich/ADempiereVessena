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

/** Generated Model for UY_RFQGen_Filter_Prod
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RFQGen_Filter_Prod extends PO implements I_UY_RFQGen_Filter_Prod, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121212L;

    /** Standard Constructor */
    public X_UY_RFQGen_Filter_Prod (Properties ctx, int UY_RFQGen_Filter_Prod_ID, String trxName)
    {
      super (ctx, UY_RFQGen_Filter_Prod_ID, trxName);
      /** if (UY_RFQGen_Filter_Prod_ID == 0)
        {
			setUY_RFQGen_Filter_ID (0);
			setUY_RFQGen_Filter_Prod_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RFQGen_Filter_Prod (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RFQGen_Filter_Prod[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
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

	public I_UY_RFQGen_Filter getUY_RFQGen_Filter() throws RuntimeException
    {
		return (I_UY_RFQGen_Filter)MTable.get(getCtx(), I_UY_RFQGen_Filter.Table_Name)
			.getPO(getUY_RFQGen_Filter_ID(), get_TrxName());	}

	/** Set UY_RFQGen_Filter.
		@param UY_RFQGen_Filter_ID UY_RFQGen_Filter	  */
	public void setUY_RFQGen_Filter_ID (int UY_RFQGen_Filter_ID)
	{
		if (UY_RFQGen_Filter_ID < 1) 
			set_Value (COLUMNNAME_UY_RFQGen_Filter_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RFQGen_Filter_ID, Integer.valueOf(UY_RFQGen_Filter_ID));
	}

	/** Get UY_RFQGen_Filter.
		@return UY_RFQGen_Filter	  */
	public int getUY_RFQGen_Filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQGen_Filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RFQGen_Filter_Prod.
		@param UY_RFQGen_Filter_Prod_ID UY_RFQGen_Filter_Prod	  */
	public void setUY_RFQGen_Filter_Prod_ID (int UY_RFQGen_Filter_Prod_ID)
	{
		if (UY_RFQGen_Filter_Prod_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RFQGen_Filter_Prod_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RFQGen_Filter_Prod_ID, Integer.valueOf(UY_RFQGen_Filter_Prod_ID));
	}

	/** Get UY_RFQGen_Filter_Prod.
		@return UY_RFQGen_Filter_Prod	  */
	public int getUY_RFQGen_Filter_Prod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQGen_Filter_Prod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}