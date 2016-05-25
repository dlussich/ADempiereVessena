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

/** Generated Model for UY_SimpleForecastLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_SimpleForecastLine extends PO implements I_UY_SimpleForecastLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_SimpleForecastLine (Properties ctx, int UY_SimpleForecastLine_ID, String trxName)
    {
      super (ctx, UY_SimpleForecastLine_ID, trxName);
      /** if (UY_SimpleForecastLine_ID == 0)
        {
			setDateTo (new Timestamp( System.currentTimeMillis() ));
			setM_Product_ID (0);
			setQty (Env.ZERO);
			setUY_SimpleForecast_ID (0);
			setUY_SimpleForecastLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_SimpleForecastLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_SimpleForecastLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_UY_SimpleForecast getUY_SimpleForecast() throws RuntimeException
    {
		return (I_UY_SimpleForecast)MTable.get(getCtx(), I_UY_SimpleForecast.Table_Name)
			.getPO(getUY_SimpleForecast_ID(), get_TrxName());	}

	/** Set Simple Forecast.
		@param UY_SimpleForecast_ID Simple Forecast	  */
	public void setUY_SimpleForecast_ID (int UY_SimpleForecast_ID)
	{
		if (UY_SimpleForecast_ID < 1) 
			set_Value (COLUMNNAME_UY_SimpleForecast_ID, null);
		else 
			set_Value (COLUMNNAME_UY_SimpleForecast_ID, Integer.valueOf(UY_SimpleForecast_ID));
	}

	/** Get Simple Forecast.
		@return Simple Forecast	  */
	public int getUY_SimpleForecast_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SimpleForecast_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Simple Forecast Line.
		@param UY_SimpleForecastLine_ID Simple Forecast Line	  */
	public void setUY_SimpleForecastLine_ID (int UY_SimpleForecastLine_ID)
	{
		if (UY_SimpleForecastLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_SimpleForecastLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_SimpleForecastLine_ID, Integer.valueOf(UY_SimpleForecastLine_ID));
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