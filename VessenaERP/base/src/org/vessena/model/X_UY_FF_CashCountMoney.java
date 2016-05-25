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

/** Generated Model for UY_FF_CashCountMoney
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_FF_CashCountMoney extends PO implements I_UY_FF_CashCountMoney, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140110L;

    /** Standard Constructor */
    public X_UY_FF_CashCountMoney (Properties ctx, int UY_FF_CashCountMoney_ID, String trxName)
    {
      super (ctx, UY_FF_CashCountMoney_ID, trxName);
      /** if (UY_FF_CashCountMoney_ID == 0)
        {
			setAmount (Env.ZERO);
			setLineTotalAmt (Env.ZERO);
			setQtyEntered (Env.ZERO);
			setUY_FF_CashCount_ID (0);
			setUY_FF_CashCountMoney_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FF_CashCountMoney (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FF_CashCountMoney[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Line Total.
		@param LineTotalAmt 
		Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt)
	{
		set_Value (COLUMNNAME_LineTotalAmt, LineTotalAmt);
	}

	/** Get Line Total.
		@return Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineTotalAmt);
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

	public I_UY_FF_CashCount getUY_FF_CashCount() throws RuntimeException
    {
		return (I_UY_FF_CashCount)MTable.get(getCtx(), I_UY_FF_CashCount.Table_Name)
			.getPO(getUY_FF_CashCount_ID(), get_TrxName());	}

	/** Set UY_FF_CashCount.
		@param UY_FF_CashCount_ID UY_FF_CashCount	  */
	public void setUY_FF_CashCount_ID (int UY_FF_CashCount_ID)
	{
		if (UY_FF_CashCount_ID < 1) 
			set_Value (COLUMNNAME_UY_FF_CashCount_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FF_CashCount_ID, Integer.valueOf(UY_FF_CashCount_ID));
	}

	/** Get UY_FF_CashCount.
		@return UY_FF_CashCount	  */
	public int getUY_FF_CashCount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FF_CashCount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FF_CashCountMoney.
		@param UY_FF_CashCountMoney_ID UY_FF_CashCountMoney	  */
	public void setUY_FF_CashCountMoney_ID (int UY_FF_CashCountMoney_ID)
	{
		if (UY_FF_CashCountMoney_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FF_CashCountMoney_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FF_CashCountMoney_ID, Integer.valueOf(UY_FF_CashCountMoney_ID));
	}

	/** Get UY_FF_CashCountMoney.
		@return UY_FF_CashCountMoney	  */
	public int getUY_FF_CashCountMoney_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FF_CashCountMoney_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}