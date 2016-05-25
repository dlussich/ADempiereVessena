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

/** Generated Model for UY_POSectionProduct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_POSectionProduct extends PO implements I_UY_POSectionProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121103L;

    /** Standard Constructor */
    public X_UY_POSectionProduct (Properties ctx, int UY_POSectionProduct_ID, String trxName)
    {
      super (ctx, UY_POSectionProduct_ID, trxName);
      /** if (UY_POSectionProduct_ID == 0)
        {
			setM_Product_ID (0);
			setUY_POSection_ID (0);
			setUY_POSectionCategory_ID (0);
			setUY_POSectionProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_POSectionProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_POSectionProduct[")
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

	public I_UY_POSection getUY_POSection() throws RuntimeException
    {
		return (I_UY_POSection)MTable.get(getCtx(), I_UY_POSection.Table_Name)
			.getPO(getUY_POSection_ID(), get_TrxName());	}

	/** Set UY_POSection.
		@param UY_POSection_ID UY_POSection	  */
	public void setUY_POSection_ID (int UY_POSection_ID)
	{
		if (UY_POSection_ID < 1) 
			set_Value (COLUMNNAME_UY_POSection_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POSection_ID, Integer.valueOf(UY_POSection_ID));
	}

	/** Get UY_POSection.
		@return UY_POSection	  */
	public int getUY_POSection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POSection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_POSectionCategory getUY_POSectionCategory() throws RuntimeException
    {
		return (I_UY_POSectionCategory)MTable.get(getCtx(), I_UY_POSectionCategory.Table_Name)
			.getPO(getUY_POSectionCategory_ID(), get_TrxName());	}

	/** Set UY_POSectionCategory.
		@param UY_POSectionCategory_ID UY_POSectionCategory	  */
	public void setUY_POSectionCategory_ID (int UY_POSectionCategory_ID)
	{
		if (UY_POSectionCategory_ID < 1) 
			set_Value (COLUMNNAME_UY_POSectionCategory_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POSectionCategory_ID, Integer.valueOf(UY_POSectionCategory_ID));
	}

	/** Get UY_POSectionCategory.
		@return UY_POSectionCategory	  */
	public int getUY_POSectionCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POSectionCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_POSectionProduct.
		@param UY_POSectionProduct_ID UY_POSectionProduct	  */
	public void setUY_POSectionProduct_ID (int UY_POSectionProduct_ID)
	{
		if (UY_POSectionProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_POSectionProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_POSectionProduct_ID, Integer.valueOf(UY_POSectionProduct_ID));
	}

	/** Get UY_POSectionProduct.
		@return UY_POSectionProduct	  */
	public int getUY_POSectionProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POSectionProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}