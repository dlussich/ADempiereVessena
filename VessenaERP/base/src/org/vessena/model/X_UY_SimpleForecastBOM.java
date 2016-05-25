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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_SimpleForecastBOM
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_SimpleForecastBOM extends PO implements I_UY_SimpleForecastBOM, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_SimpleForecastBOM (Properties ctx, int UY_SimpleForecastBOM_ID, String trxName)
    {
      super (ctx, UY_SimpleForecastBOM_ID, trxName);
      /** if (UY_SimpleForecastBOM_ID == 0)
        {
			setM_Product_ID (0);
			setQty (Env.ZERO);
			setUY_SimpleForecastBOM_ID (0);
			setUY_SimpleForecastLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_SimpleForecastBOM (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_SimpleForecastBOM[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
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

	/** Set Generate Order.
		@param GenerateOrder 
		Generate Order
	  */
	public void setGenerateOrder (String GenerateOrder)
	{
		set_Value (COLUMNNAME_GenerateOrder, GenerateOrder);
	}

	/** Get Generate Order.
		@return Generate Order
	  */
	public String getGenerateOrder () 
	{
		return (String)get_Value(COLUMNNAME_GenerateOrder);
	}

	/** Set Bill of Materials.
		@param IsBOM 
		Bill of Materials
	  */
	public void setIsBOM (boolean IsBOM)
	{
		set_Value (COLUMNNAME_IsBOM, Boolean.valueOf(IsBOM));
	}

	/** Get Bill of Materials.
		@return Bill of Materials
	  */
	public boolean isBOM () 
	{
		Object oo = get_Value(COLUMNNAME_IsBOM);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Level no.
		@param LevelNo Level no	  */
	public void setLevelNo (int LevelNo)
	{
		set_Value (COLUMNNAME_LevelNo, Integer.valueOf(LevelNo));
	}

	/** Get Level no.
		@return Level no	  */
	public int getLevelNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LevelNo);
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Simple Forecast Bill Of Matireal.
		@param UY_SimpleForecastBOM_ID Simple Forecast Bill Of Matireal	  */
	public void setUY_SimpleForecastBOM_ID (int UY_SimpleForecastBOM_ID)
	{
		if (UY_SimpleForecastBOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_SimpleForecastBOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_SimpleForecastBOM_ID, Integer.valueOf(UY_SimpleForecastBOM_ID));
	}

	/** Get Simple Forecast Bill Of Matireal.
		@return Simple Forecast Bill Of Matireal	  */
	public int getUY_SimpleForecastBOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SimpleForecastBOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_SimpleForecastBOMBOM_ID.
		@param UY_SimpleForecastBOMBOM_ID UY_SimpleForecastBOMBOM_ID	  */
	public void setUY_SimpleForecastBOMBOM_ID (int UY_SimpleForecastBOMBOM_ID)
	{
		if (UY_SimpleForecastBOMBOM_ID < 1) 
			set_Value (COLUMNNAME_UY_SimpleForecastBOMBOM_ID, null);
		else 
			set_Value (COLUMNNAME_UY_SimpleForecastBOMBOM_ID, Integer.valueOf(UY_SimpleForecastBOMBOM_ID));
	}

	/** Get UY_SimpleForecastBOMBOM_ID.
		@return UY_SimpleForecastBOMBOM_ID	  */
	public int getUY_SimpleForecastBOMBOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SimpleForecastBOMBOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_SimpleForecastLine getUY_SimpleForecastLine() throws RuntimeException
    {
		return (I_UY_SimpleForecastLine)MTable.get(getCtx(), I_UY_SimpleForecastLine.Table_Name)
			.getPO(getUY_SimpleForecastLine_ID(), get_TrxName());	}

	/** Set Simple Forecast Line.
		@param UY_SimpleForecastLine_ID Simple Forecast Line	  */
	public void setUY_SimpleForecastLine_ID (int UY_SimpleForecastLine_ID)
	{
		if (UY_SimpleForecastLine_ID < 1) 
			set_Value (COLUMNNAME_UY_SimpleForecastLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_SimpleForecastLine_ID, Integer.valueOf(UY_SimpleForecastLine_ID));
	}

	/** Get Simple Forecast Line.
		@return Simple Forecast Line	  */
	public int getUY_SimpleForecastLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SimpleForecastLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}