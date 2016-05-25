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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_PriceLoadLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PriceLoadLine extends PO implements I_UY_PriceLoadLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160128L;

    /** Standard Constructor */
    public X_UY_PriceLoadLine (Properties ctx, int UY_PriceLoadLine_ID, String trxName)
    {
      super (ctx, UY_PriceLoadLine_ID, trxName);
      /** if (UY_PriceLoadLine_ID == 0)
        {
			setIsSelected (false);
// N
			setPriceList (Env.ZERO);
			setSuccess (false);
			setUPC (null);
			setUY_PriceLoad_ID (0);
			setUY_PriceLoadLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PriceLoadLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PriceLoadLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Validation code.
		@param Code 
		Validation Code
	  */
	public void setCode (String Code)
	{
		set_Value (COLUMNNAME_Code, Code);
	}

	/** Get Validation code.
		@return Validation Code
	  */
	public String getCode () 
	{
		return (String)get_Value(COLUMNNAME_Code);
	}

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
    {
		return (org.compiere.model.I_C_TaxCategory)MTable.get(getCtx(), org.compiere.model.I_C_TaxCategory.Table_Name)
			.getPO(getC_TaxCategory_ID(), get_TrxName());	}

	/** Set Tax Category.
		@param C_TaxCategory_ID 
		Tax Category
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Tax Category.
		@return Tax Category
	  */
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateAction.
		@param DateAction DateAction	  */
	public void setDateAction (Timestamp DateAction)
	{
		set_Value (COLUMNNAME_DateAction, DateAction);
	}

	/** Get DateAction.
		@return DateAction	  */
	public Timestamp getDateAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAction);
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

	/** Set Difference.
		@param DifferenceAmt 
		Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt)
	{
		set_Value (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

	/** Get Difference.
		@return Difference Amount
	  */
	public BigDecimal getDifferenceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Margin %.
		@param Margin 
		Margin for a product as a percentage
	  */
	public void setMargin (BigDecimal Margin)
	{
		set_Value (COLUMNNAME_Margin, Margin);
	}

	/** Get Margin %.
		@return Margin for a product as a percentage
	  */
	public BigDecimal getMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set NewPrice.
		@param NewPrice NewPrice	  */
	public void setNewPrice (BigDecimal NewPrice)
	{
		set_Value (COLUMNNAME_NewPrice, NewPrice);
	}

	/** Get NewPrice.
		@return NewPrice	  */
	public BigDecimal getNewPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceSOList.
		@param PriceSOList PriceSOList	  */
	public void setPriceSOList (BigDecimal PriceSOList)
	{
		set_Value (COLUMNNAME_PriceSOList, PriceSOList);
	}

	/** Get PriceSOList.
		@return PriceSOList	  */
	public BigDecimal getPriceSOList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceSOList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Product code.
		@param ProdCode 
		Product code
	  */
	public void setProdCode (String ProdCode)
	{
		set_Value (COLUMNNAME_ProdCode, ProdCode);
	}

	/** Get Product code.
		@return Product code
	  */
	public String getProdCode () 
	{
		return (String)get_Value(COLUMNNAME_ProdCode);
	}

	/** Set Success.
		@param Success Success	  */
	public void setSuccess (boolean Success)
	{
		set_Value (COLUMNNAME_Success, Boolean.valueOf(Success));
	}

	/** Get Success.
		@return Success	  */
	public boolean isSuccess () 
	{
		Object oo = get_Value(COLUMNNAME_Success);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
	}

	public I_UY_Familia getUY_Familia() throws RuntimeException
    {
		return (I_UY_Familia)MTable.get(getCtx(), I_UY_Familia.Table_Name)
			.getPO(getUY_Familia_ID(), get_TrxName());	}

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

	public I_UY_Linea_Negocio getUY_Linea_Negocio() throws RuntimeException
    {
		return (I_UY_Linea_Negocio)MTable.get(getCtx(), I_UY_Linea_Negocio.Table_Name)
			.getPO(getUY_Linea_Negocio_ID(), get_TrxName());	}

	/** Set UY_Linea_Negocio_ID.
		@param UY_Linea_Negocio_ID UY_Linea_Negocio_ID	  */
	public void setUY_Linea_Negocio_ID (int UY_Linea_Negocio_ID)
	{
		if (UY_Linea_Negocio_ID < 1) 
			set_Value (COLUMNNAME_UY_Linea_Negocio_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Linea_Negocio_ID, Integer.valueOf(UY_Linea_Negocio_ID));
	}

	/** Get UY_Linea_Negocio_ID.
		@return UY_Linea_Negocio_ID	  */
	public int getUY_Linea_Negocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Linea_Negocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PriceLoad getUY_PriceLoad() throws RuntimeException
    {
		return (I_UY_PriceLoad)MTable.get(getCtx(), I_UY_PriceLoad.Table_Name)
			.getPO(getUY_PriceLoad_ID(), get_TrxName());	}

	/** Set UY_PriceLoad.
		@param UY_PriceLoad_ID UY_PriceLoad	  */
	public void setUY_PriceLoad_ID (int UY_PriceLoad_ID)
	{
		if (UY_PriceLoad_ID < 1) 
			set_Value (COLUMNNAME_UY_PriceLoad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PriceLoad_ID, Integer.valueOf(UY_PriceLoad_ID));
	}

	/** Get UY_PriceLoad.
		@return UY_PriceLoad	  */
	public int getUY_PriceLoad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PriceLoad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PriceLoadLine.
		@param UY_PriceLoadLine_ID UY_PriceLoadLine	  */
	public void setUY_PriceLoadLine_ID (int UY_PriceLoadLine_ID)
	{
		if (UY_PriceLoadLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PriceLoadLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PriceLoadLine_ID, Integer.valueOf(UY_PriceLoadLine_ID));
	}

	/** Get UY_PriceLoadLine.
		@return UY_PriceLoadLine	  */
	public int getUY_PriceLoadLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PriceLoadLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ProductGroup getUY_ProductGroup() throws RuntimeException
    {
		return (I_UY_ProductGroup)MTable.get(getCtx(), I_UY_ProductGroup.Table_Name)
			.getPO(getUY_ProductGroup_ID(), get_TrxName());	}

	/** Set UY_ProductGroup.
		@param UY_ProductGroup_ID UY_ProductGroup	  */
	public void setUY_ProductGroup_ID (int UY_ProductGroup_ID)
	{
		if (UY_ProductGroup_ID < 1) 
			set_Value (COLUMNNAME_UY_ProductGroup_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProductGroup_ID, Integer.valueOf(UY_ProductGroup_ID));
	}

	/** Get UY_ProductGroup.
		@return UY_ProductGroup	  */
	public int getUY_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProductGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_SubFamilia getUY_SubFamilia() throws RuntimeException
    {
		return (I_UY_SubFamilia)MTable.get(getCtx(), I_UY_SubFamilia.Table_Name)
			.getPO(getUY_SubFamilia_ID(), get_TrxName());	}

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
}