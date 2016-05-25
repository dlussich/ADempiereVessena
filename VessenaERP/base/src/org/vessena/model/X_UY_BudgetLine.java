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

/** Generated Model for UY_BudgetLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_BudgetLine extends PO implements I_UY_BudgetLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121209L;

    /** Standard Constructor */
    public X_UY_BudgetLine (Properties ctx, int UY_BudgetLine_ID, String trxName)
    {
      super (ctx, UY_BudgetLine_ID, trxName);
      /** if (UY_BudgetLine_ID == 0)
        {
			setamt1 (Env.ZERO);
			setamt2 (Env.ZERO);
			setamt3 (Env.ZERO);
			setprice1 (Env.ZERO);
			setprice2 (Env.ZERO);
			setprice3 (Env.ZERO);
			setqty1 (Env.ZERO);
			setqty2 (Env.ZERO);
			setqty3 (Env.ZERO);
			setUY_Budget_ID (0);
			setUY_BudgetLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BudgetLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BudgetLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amt1.
		@param amt1 amt1	  */
	public void setamt1 (BigDecimal amt1)
	{
		set_Value (COLUMNNAME_amt1, amt1);
	}

	/** Get amt1.
		@return amt1	  */
	public BigDecimal getamt1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt2.
		@param amt2 amt2	  */
	public void setamt2 (BigDecimal amt2)
	{
		set_Value (COLUMNNAME_amt2, amt2);
	}

	/** Get amt2.
		@return amt2	  */
	public BigDecimal getamt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt3.
		@param amt3 amt3	  */
	public void setamt3 (BigDecimal amt3)
	{
		set_Value (COLUMNNAME_amt3, amt3);
	}

	/** Get amt3.
		@return amt3	  */
	public BigDecimal getamt3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set concept.
		@param concept concept	  */
	public void setconcept (String concept)
	{
		set_Value (COLUMNNAME_concept, concept);
	}

	/** Get concept.
		@return concept	  */
	public String getconcept () 
	{
		return (String)get_Value(COLUMNNAME_concept);
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

	/** Set Detail Information.
		@param DetailInfo 
		Additional Detail Information
	  */
	public void setDetailInfo (String DetailInfo)
	{
		set_Value (COLUMNNAME_DetailInfo, DetailInfo);
	}

	/** Get Detail Information.
		@return Additional Detail Information
	  */
	public String getDetailInfo () 
	{
		return (String)get_Value(COLUMNNAME_DetailInfo);
	}

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IsApprovedQty1.
		@param IsApprovedQty1 IsApprovedQty1	  */
	public void setIsApprovedQty1 (boolean IsApprovedQty1)
	{
		set_Value (COLUMNNAME_IsApprovedQty1, Boolean.valueOf(IsApprovedQty1));
	}

	/** Get IsApprovedQty1.
		@return IsApprovedQty1	  */
	public boolean isApprovedQty1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsApprovedQty1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsApprovedQty2.
		@param IsApprovedQty2 IsApprovedQty2	  */
	public void setIsApprovedQty2 (boolean IsApprovedQty2)
	{
		set_Value (COLUMNNAME_IsApprovedQty2, Boolean.valueOf(IsApprovedQty2));
	}

	/** Get IsApprovedQty2.
		@return IsApprovedQty2	  */
	public boolean isApprovedQty2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsApprovedQty2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsApprovedQty3.
		@param IsApprovedQty3 IsApprovedQty3	  */
	public void setIsApprovedQty3 (boolean IsApprovedQty3)
	{
		set_Value (COLUMNNAME_IsApprovedQty3, Boolean.valueOf(IsApprovedQty3));
	}

	/** Get IsApprovedQty3.
		@return IsApprovedQty3	  */
	public boolean isApprovedQty3 () 
	{
		Object oo = get_Value(COLUMNNAME_IsApprovedQty3);
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

	/** Set price1.
		@param price1 price1	  */
	public void setprice1 (BigDecimal price1)
	{
		set_Value (COLUMNNAME_price1, price1);
	}

	/** Get price1.
		@return price1	  */
	public BigDecimal getprice1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_price1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set price2.
		@param price2 price2	  */
	public void setprice2 (BigDecimal price2)
	{
		set_Value (COLUMNNAME_price2, price2);
	}

	/** Get price2.
		@return price2	  */
	public BigDecimal getprice2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_price2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set price3.
		@param price3 price3	  */
	public void setprice3 (BigDecimal price3)
	{
		set_Value (COLUMNNAME_price3, price3);
	}

	/** Get price3.
		@return price3	  */
	public BigDecimal getprice3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_price3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty1.
		@param qty1 qty1	  */
	public void setqty1 (BigDecimal qty1)
	{
		set_Value (COLUMNNAME_qty1, qty1);
	}

	/** Get qty1.
		@return qty1	  */
	public BigDecimal getqty1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty2.
		@param qty2 qty2	  */
	public void setqty2 (BigDecimal qty2)
	{
		set_Value (COLUMNNAME_qty2, qty2);
	}

	/** Get qty2.
		@return qty2	  */
	public BigDecimal getqty2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty3.
		@param qty3 qty3	  */
	public void setqty3 (BigDecimal qty3)
	{
		set_Value (COLUMNNAME_qty3, qty3);
	}

	/** Get qty3.
		@return qty3	  */
	public BigDecimal getqty3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_Budget getUY_Budget() throws RuntimeException
    {
		return (I_UY_Budget)MTable.get(getCtx(), I_UY_Budget.Table_Name)
			.getPO(getUY_Budget_ID(), get_TrxName());	}

	/** Set UY_Budget.
		@param UY_Budget_ID UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID)
	{
		if (UY_Budget_ID < 1) 
			set_Value (COLUMNNAME_UY_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Budget_ID, Integer.valueOf(UY_Budget_ID));
	}

	/** Get UY_Budget.
		@return UY_Budget	  */
	public int getUY_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BudgetLine.
		@param UY_BudgetLine_ID UY_BudgetLine	  */
	public void setUY_BudgetLine_ID (int UY_BudgetLine_ID)
	{
		if (UY_BudgetLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BudgetLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BudgetLine_ID, Integer.valueOf(UY_BudgetLine_ID));
	}

	/** Get UY_BudgetLine.
		@return UY_BudgetLine	  */
	public int getUY_BudgetLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BudgetLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}