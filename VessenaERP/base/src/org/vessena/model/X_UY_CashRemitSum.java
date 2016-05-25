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

/** Generated Model for UY_CashRemitSum
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashRemitSum extends PO implements I_UY_CashRemitSum, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150724L;

    /** Standard Constructor */
    public X_UY_CashRemitSum (Properties ctx, int UY_CashRemitSum_ID, String trxName)
    {
      super (ctx, UY_CashRemitSum_ID, trxName);
      /** if (UY_CashRemitSum_ID == 0)
        {
			setAmount (Env.ZERO);
			setAmount2 (Env.ZERO);
			setC_Currency_ID (0);
			setQtyCount (0);
			setQtyCount2 (0);
			setUY_CashRemitSum_ID (0);
			setUY_CashRemittance_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashRemitSum (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashRemitSum[")
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

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (int QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, Integer.valueOf(QtyCount));
	}

	/** Get Quantity count.
		@return Counted Quantity
	  */
	public int getQtyCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyCount2.
		@param QtyCount2 QtyCount2	  */
	public void setQtyCount2 (int QtyCount2)
	{
		set_Value (COLUMNNAME_QtyCount2, Integer.valueOf(QtyCount2));
	}

	/** Get QtyCount2.
		@return QtyCount2	  */
	public int getQtyCount2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyCount2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CashRemitSum.
		@param UY_CashRemitSum_ID UY_CashRemitSum	  */
	public void setUY_CashRemitSum_ID (int UY_CashRemitSum_ID)
	{
		if (UY_CashRemitSum_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashRemitSum_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashRemitSum_ID, Integer.valueOf(UY_CashRemitSum_ID));
	}

	/** Get UY_CashRemitSum.
		@return UY_CashRemitSum	  */
	public int getUY_CashRemitSum_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashRemitSum_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CashRemittance getUY_CashRemittance() throws RuntimeException
    {
		return (I_UY_CashRemittance)MTable.get(getCtx(), I_UY_CashRemittance.Table_Name)
			.getPO(getUY_CashRemittance_ID(), get_TrxName());	}

	/** Set UY_CashRemittance.
		@param UY_CashRemittance_ID UY_CashRemittance	  */
	public void setUY_CashRemittance_ID (int UY_CashRemittance_ID)
	{
		if (UY_CashRemittance_ID < 1) 
			set_Value (COLUMNNAME_UY_CashRemittance_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashRemittance_ID, Integer.valueOf(UY_CashRemittance_ID));
	}

	/** Get UY_CashRemittance.
		@return UY_CashRemittance	  */
	public int getUY_CashRemittance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashRemittance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}