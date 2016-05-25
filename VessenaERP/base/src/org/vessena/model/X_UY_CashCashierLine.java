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

/** Generated Model for UY_CashCashierLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashCashierLine extends PO implements I_UY_CashCashierLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160331L;

    /** Standard Constructor */
    public X_UY_CashCashierLine (Properties ctx, int UY_CashCashierLine_ID, String trxName)
    {
      super (ctx, UY_CashCashierLine_ID, trxName);
      /** if (UY_CashCashierLine_ID == 0)
        {
			setAmount (Env.ZERO);
			setUY_CashCashier_ID (0);
			setUY_CashCashierLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashCashierLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashCashierLine[")
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CashCashier getUY_CashCashier() throws RuntimeException
    {
		return (I_UY_CashCashier)MTable.get(getCtx(), I_UY_CashCashier.Table_Name)
			.getPO(getUY_CashCashier_ID(), get_TrxName());	}

	/** Set UY_CashCashier.
		@param UY_CashCashier_ID UY_CashCashier	  */
	public void setUY_CashCashier_ID (int UY_CashCashier_ID)
	{
		if (UY_CashCashier_ID < 1) 
			set_Value (COLUMNNAME_UY_CashCashier_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashCashier_ID, Integer.valueOf(UY_CashCashier_ID));
	}

	/** Get UY_CashCashier.
		@return UY_CashCashier	  */
	public int getUY_CashCashier_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCashier_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CashCashierLine.
		@param UY_CashCashierLine_ID UY_CashCashierLine	  */
	public void setUY_CashCashierLine_ID (int UY_CashCashierLine_ID)
	{
		if (UY_CashCashierLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashCashierLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashCashierLine_ID, Integer.valueOf(UY_CashCashierLine_ID));
	}

	/** Get UY_CashCashierLine.
		@return UY_CashCashierLine	  */
	public int getUY_CashCashierLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCashierLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}