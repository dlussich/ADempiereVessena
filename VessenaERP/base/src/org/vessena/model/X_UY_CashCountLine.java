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

/** Generated Model for UY_CashCountLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashCountLine extends PO implements I_UY_CashCountLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160226L;

    /** Standard Constructor */
    public X_UY_CashCountLine (Properties ctx, int UY_CashCountLine_ID, String trxName)
    {
      super (ctx, UY_CashCountLine_ID, trxName);
      /** if (UY_CashCountLine_ID == 0)
        {
			setUY_CashCountLine_ID (0);
			setUY_CashCountPayRule_ID (0);
			setUY_RT_CashBox_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashCountLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashCountLine[")
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

	/** Set Amount2.
		@param Amount2 Amount2	  */
	public void setAmount2 (BigDecimal Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Amount2);
	}

	/** Get Amount2.
		@return Amount2	  */
	public BigDecimal getAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Quebranto.
		@param Quebranto Quebranto	  */
	public void setQuebranto (BigDecimal Quebranto)
	{
		set_Value (COLUMNNAME_Quebranto, Quebranto);
	}

	/** Get Quebranto.
		@return Quebranto	  */
	public BigDecimal getQuebranto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Quebranto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_CashCountLine.
		@param UY_CashCountLine_ID UY_CashCountLine	  */
	public void setUY_CashCountLine_ID (int UY_CashCountLine_ID)
	{
		if (UY_CashCountLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashCountLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashCountLine_ID, Integer.valueOf(UY_CashCountLine_ID));
	}

	/** Get UY_CashCountLine.
		@return UY_CashCountLine	  */
	public int getUY_CashCountLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCountLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CashCountPayRule getUY_CashCountPayRule() throws RuntimeException
    {
		return (I_UY_CashCountPayRule)MTable.get(getCtx(), I_UY_CashCountPayRule.Table_Name)
			.getPO(getUY_CashCountPayRule_ID(), get_TrxName());	}

	/** Set UY_CashCountPayRule.
		@param UY_CashCountPayRule_ID UY_CashCountPayRule	  */
	public void setUY_CashCountPayRule_ID (int UY_CashCountPayRule_ID)
	{
		if (UY_CashCountPayRule_ID < 1) 
			set_Value (COLUMNNAME_UY_CashCountPayRule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashCountPayRule_ID, Integer.valueOf(UY_CashCountPayRule_ID));
	}

	/** Get UY_CashCountPayRule.
		@return UY_CashCountPayRule	  */
	public int getUY_CashCountPayRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCountPayRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_CashBox getUY_RT_CashBox() throws RuntimeException
    {
		return (I_UY_RT_CashBox)MTable.get(getCtx(), I_UY_RT_CashBox.Table_Name)
			.getPO(getUY_RT_CashBox_ID(), get_TrxName());	}

	/** Set UY_RT_CashBox.
		@param UY_RT_CashBox_ID UY_RT_CashBox	  */
	public void setUY_RT_CashBox_ID (int UY_RT_CashBox_ID)
	{
		if (UY_RT_CashBox_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_CashBox_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_CashBox_ID, Integer.valueOf(UY_RT_CashBox_ID));
	}

	/** Get UY_RT_CashBox.
		@return UY_RT_CashBox	  */
	public int getUY_RT_CashBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_CashBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}