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

/** Generated Model for UY_PriceUpdateLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PriceUpdateLine extends PO implements I_UY_PriceUpdateLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160129L;

    /** Standard Constructor */
    public X_UY_PriceUpdateLine (Properties ctx, int UY_PriceUpdateLine_ID, String trxName)
    {
      super (ctx, UY_PriceUpdateLine_ID, trxName);
      /** if (UY_PriceUpdateLine_ID == 0)
        {
			setDateAction (new Timestamp( System.currentTimeMillis() ));
			setDifferenceAmt (Env.ZERO);
			setIsSelected (false);
// N
			setIsSelected2 (false);
// N
			setPriceActual (Env.ZERO);
			setPriceList (Env.ZERO);
			setUY_PriceUpdate_ID (0);
			setUY_PriceUpdateLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PriceUpdateLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PriceUpdateLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Selected2.
		@param IsSelected2 Selected2	  */
	public void setIsSelected2 (boolean IsSelected2)
	{
		set_Value (COLUMNNAME_IsSelected2, Boolean.valueOf(IsSelected2));
	}

	/** Get Selected2.
		@return Selected2	  */
	public boolean isSelected2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Unit Price.
		@param PriceActual 
		Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price 
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
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

	public I_UY_PriceUpdate getUY_PriceUpdate() throws RuntimeException
    {
		return (I_UY_PriceUpdate)MTable.get(getCtx(), I_UY_PriceUpdate.Table_Name)
			.getPO(getUY_PriceUpdate_ID(), get_TrxName());	}

	/** Set UY_PriceUpdate.
		@param UY_PriceUpdate_ID UY_PriceUpdate	  */
	public void setUY_PriceUpdate_ID (int UY_PriceUpdate_ID)
	{
		if (UY_PriceUpdate_ID < 1) 
			set_Value (COLUMNNAME_UY_PriceUpdate_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PriceUpdate_ID, Integer.valueOf(UY_PriceUpdate_ID));
	}

	/** Get UY_PriceUpdate.
		@return UY_PriceUpdate	  */
	public int getUY_PriceUpdate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PriceUpdate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PriceUpdateLine.
		@param UY_PriceUpdateLine_ID UY_PriceUpdateLine	  */
	public void setUY_PriceUpdateLine_ID (int UY_PriceUpdateLine_ID)
	{
		if (UY_PriceUpdateLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PriceUpdateLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PriceUpdateLine_ID, Integer.valueOf(UY_PriceUpdateLine_ID));
	}

	/** Get UY_PriceUpdateLine.
		@return UY_PriceUpdateLine	  */
	public int getUY_PriceUpdateLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PriceUpdateLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}