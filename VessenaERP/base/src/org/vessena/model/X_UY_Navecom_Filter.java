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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_Navecom_Filter
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_Navecom_Filter extends PO implements I_UY_Navecom_Filter, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_Navecom_Filter (Properties ctx, int UY_Navecom_Filter_ID, String trxName)
    {
      super (ctx, UY_Navecom_Filter_ID, trxName);
      /** if (UY_Navecom_Filter_ID == 0)
        {
			setUY_Navecom_Filter_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Navecom_Filter (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Navecom_Filter[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	public I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (I_M_Product_Category)MTable.get(getCtx(), I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
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

	/** Set Margin Amount.
		@param MarginAmt 
		Difference between actual and limit price multiplied by the quantity
	  */
	public void setMarginAmt (BigDecimal MarginAmt)
	{
		set_Value (COLUMNNAME_MarginAmt, MarginAmt);
	}

	/** Get Margin Amount.
		@return Difference between actual and limit price multiplied by the quantity
	  */
	public BigDecimal getMarginAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MarginAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set saldo.
		@param saldo saldo	  */
	public void setsaldo (BigDecimal saldo)
	{
		set_Value (COLUMNNAME_saldo, saldo);
	}

	/** Get saldo.
		@return saldo	  */
	public BigDecimal getsaldo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_saldo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_AD_User getSalesRep() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Familia.
		@param UY_Familia_ID UY_Familia	  */
	public void setUY_Familia_ID (int UY_Familia_ID)
	{
		if (UY_Familia_ID < 1) 
			set_Value (COLUMNNAME_UY_Familia_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Familia_ID, Integer.valueOf(UY_Familia_ID));
	}

	/** Get UY_Familia.
		@return UY_Familia	  */
	public int getUY_Familia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Familia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Navecom_Filter.
		@param UY_Navecom_Filter_ID UY_Navecom_Filter	  */
	public void setUY_Navecom_Filter_ID (int UY_Navecom_Filter_ID)
	{
		if (UY_Navecom_Filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Navecom_Filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Navecom_Filter_ID, Integer.valueOf(UY_Navecom_Filter_ID));
	}

	/** Get UY_Navecom_Filter.
		@return UY_Navecom_Filter	  */
	public int getUY_Navecom_Filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Navecom_Filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_SubFamilia.
		@param UY_SubFamilia_ID UY_SubFamilia	  */
	public void setUY_SubFamilia_ID (int UY_SubFamilia_ID)
	{
		if (UY_SubFamilia_ID < 1) 
			set_Value (COLUMNNAME_UY_SubFamilia_ID, null);
		else 
			set_Value (COLUMNNAME_UY_SubFamilia_ID, Integer.valueOf(UY_SubFamilia_ID));
	}

	/** Get UY_SubFamilia.
		@return UY_SubFamilia	  */
	public int getUY_SubFamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SubFamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TipoProducto_ID.
		@param UY_TipoProducto_ID UY_TipoProducto_ID	  */
	public void setUY_TipoProducto_ID (int UY_TipoProducto_ID)
	{
		if (UY_TipoProducto_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TipoProducto_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TipoProducto_ID, Integer.valueOf(UY_TipoProducto_ID));
	}

	/** Get UY_TipoProducto_ID.
		@return UY_TipoProducto_ID	  */
	public int getUY_TipoProducto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TipoProducto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}