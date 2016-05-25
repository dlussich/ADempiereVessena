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

/** Generated Model for UY_CashRemitBankLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashRemitBankLine extends PO implements I_UY_CashRemitBankLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150723L;

    /** Standard Constructor */
    public X_UY_CashRemitBankLine (Properties ctx, int UY_CashRemitBankLine_ID, String trxName)
    {
      super (ctx, UY_CashRemitBankLine_ID, trxName);
      /** if (UY_CashRemitBankLine_ID == 0)
        {
			setAmount (Env.ZERO);
			setNroSobre (null);
			setQtyCount (0);
			setUY_CashRemitBank_ID (0);
			setUY_CashRemitBankLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashRemitBankLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashRemitBankLine[")
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

	/** Set NroSobre.
		@param NroSobre NroSobre	  */
	public void setNroSobre (String NroSobre)
	{
		set_Value (COLUMNNAME_NroSobre, NroSobre);
	}

	/** Get NroSobre.
		@return NroSobre	  */
	public String getNroSobre () 
	{
		return (String)get_Value(COLUMNNAME_NroSobre);
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

	public I_UY_CashRemitBank getUY_CashRemitBank() throws RuntimeException
    {
		return (I_UY_CashRemitBank)MTable.get(getCtx(), I_UY_CashRemitBank.Table_Name)
			.getPO(getUY_CashRemitBank_ID(), get_TrxName());	}

	/** Set UY_CashRemitBank.
		@param UY_CashRemitBank_ID UY_CashRemitBank	  */
	public void setUY_CashRemitBank_ID (int UY_CashRemitBank_ID)
	{
		if (UY_CashRemitBank_ID < 1) 
			set_Value (COLUMNNAME_UY_CashRemitBank_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashRemitBank_ID, Integer.valueOf(UY_CashRemitBank_ID));
	}

	/** Get UY_CashRemitBank.
		@return UY_CashRemitBank	  */
	public int getUY_CashRemitBank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashRemitBank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CashRemitBankLine.
		@param UY_CashRemitBankLine_ID UY_CashRemitBankLine	  */
	public void setUY_CashRemitBankLine_ID (int UY_CashRemitBankLine_ID)
	{
		if (UY_CashRemitBankLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashRemitBankLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashRemitBankLine_ID, Integer.valueOf(UY_CashRemitBankLine_ID));
	}

	/** Get UY_CashRemitBankLine.
		@return UY_CashRemitBankLine	  */
	public int getUY_CashRemitBankLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashRemitBankLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}