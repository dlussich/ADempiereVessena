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

/** Generated Model for M_ProductAttribute
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_M_ProductAttribute extends PO implements I_M_ProductAttribute, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150617L;

    /** Standard Constructor */
    public X_M_ProductAttribute (Properties ctx, int M_ProductAttribute_ID, String trxName)
    {
      super (ctx, M_ProductAttribute_ID, trxName);
      /** if (M_ProductAttribute_ID == 0)
        {
			setIsSelected (false);
			setM_ProductAttribute_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_ProductAttribute (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_ProductAttribute[")
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

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set M_ProductAttribute_ID.
		@param M_ProductAttribute_ID M_ProductAttribute_ID	  */
	public void setM_ProductAttribute_ID (int M_ProductAttribute_ID)
	{
		if (M_ProductAttribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductAttribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductAttribute_ID, Integer.valueOf(M_ProductAttribute_ID));
	}

	/** Get M_ProductAttribute_ID.
		@return M_ProductAttribute_ID	  */
	public int getM_ProductAttribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductAttribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ProdAttribute getUY_ProdAttribute() throws RuntimeException
    {
		return (I_UY_ProdAttribute)MTable.get(getCtx(), I_UY_ProdAttribute.Table_Name)
			.getPO(getUY_ProdAttribute_ID(), get_TrxName());	}

	/** Set UY_ProdAttribute.
		@param UY_ProdAttribute_ID UY_ProdAttribute	  */
	public void setUY_ProdAttribute_ID (int UY_ProdAttribute_ID)
	{
		if (UY_ProdAttribute_ID < 1) 
			set_Value (COLUMNNAME_UY_ProdAttribute_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProdAttribute_ID, Integer.valueOf(UY_ProdAttribute_ID));
	}

	/** Get UY_ProdAttribute.
		@return UY_ProdAttribute	  */
	public int getUY_ProdAttribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProdAttribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}