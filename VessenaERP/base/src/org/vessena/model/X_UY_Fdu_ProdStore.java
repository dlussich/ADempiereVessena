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

/** Generated Model for UY_Fdu_ProdStore
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_ProdStore extends PO implements I_UY_Fdu_ProdStore, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130327L;

    /** Standard Constructor */
    public X_UY_Fdu_ProdStore (Properties ctx, int UY_Fdu_ProdStore_ID, String trxName)
    {
      super (ctx, UY_Fdu_ProdStore_ID, trxName);
      /** if (UY_Fdu_ProdStore_ID == 0)
        {
			setUY_Fdu_InvoiceType_ID (0);
			setUY_Fdu_ProdStore_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_ProdStore (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_ProdStore[")
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

	public I_UY_Fdu_InvoiceType getUY_Fdu_InvoiceType() throws RuntimeException
    {
		return (I_UY_Fdu_InvoiceType)MTable.get(getCtx(), I_UY_Fdu_InvoiceType.Table_Name)
			.getPO(getUY_Fdu_InvoiceType_ID(), get_TrxName());	}

	/** Set UY_Fdu_InvoiceType.
		@param UY_Fdu_InvoiceType_ID UY_Fdu_InvoiceType	  */
	public void setUY_Fdu_InvoiceType_ID (int UY_Fdu_InvoiceType_ID)
	{
		if (UY_Fdu_InvoiceType_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_InvoiceType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_InvoiceType_ID, Integer.valueOf(UY_Fdu_InvoiceType_ID));
	}

	/** Get UY_Fdu_InvoiceType.
		@return UY_Fdu_InvoiceType	  */
	public int getUY_Fdu_InvoiceType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_InvoiceType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_ProdStore.
		@param UY_Fdu_ProdStore_ID UY_Fdu_ProdStore	  */
	public void setUY_Fdu_ProdStore_ID (int UY_Fdu_ProdStore_ID)
	{
		if (UY_Fdu_ProdStore_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_ProdStore_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_ProdStore_ID, Integer.valueOf(UY_Fdu_ProdStore_ID));
	}

	/** Get UY_Fdu_ProdStore.
		@return UY_Fdu_ProdStore	  */
	public int getUY_Fdu_ProdStore_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_ProdStore_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Store getUY_Fdu_Store() throws RuntimeException
    {
		return (I_UY_Fdu_Store)MTable.get(getCtx(), I_UY_Fdu_Store.Table_Name)
			.getPO(getUY_Fdu_Store_ID(), get_TrxName());	}

	/** Set UY_Fdu_Store.
		@param UY_Fdu_Store_ID UY_Fdu_Store	  */
	public void setUY_Fdu_Store_ID (int UY_Fdu_Store_ID)
	{
		if (UY_Fdu_Store_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Store_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Store_ID, Integer.valueOf(UY_Fdu_Store_ID));
	}

	/** Get UY_Fdu_Store.
		@return UY_Fdu_Store	  */
	public int getUY_Fdu_Store_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Store_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}