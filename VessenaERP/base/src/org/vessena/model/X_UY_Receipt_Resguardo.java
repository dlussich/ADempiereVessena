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

/** Generated Model for UY_Receipt_Resguardo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Receipt_Resguardo extends PO implements I_UY_Receipt_Resguardo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130313L;

    /** Standard Constructor */
    public X_UY_Receipt_Resguardo (Properties ctx, int UY_Receipt_Resguardo_ID, String trxName)
    {
      super (ctx, UY_Receipt_Resguardo_ID, trxName);
      /** if (UY_Receipt_Resguardo_ID == 0)
        {
			setC_Currency_ID (0);
			setC_Payment_ID (0);
			setUY_Receipt_Resguardo_ID (0);
			setUY_Retention_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Receipt_Resguardo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Receipt_Resguardo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
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

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
    {
		return (org.compiere.model.I_C_Payment)MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Receipt_Resguardo.
		@param UY_Receipt_Resguardo_ID UY_Receipt_Resguardo	  */
	public void setUY_Receipt_Resguardo_ID (int UY_Receipt_Resguardo_ID)
	{
		if (UY_Receipt_Resguardo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Receipt_Resguardo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Receipt_Resguardo_ID, Integer.valueOf(UY_Receipt_Resguardo_ID));
	}

	/** Get UY_Receipt_Resguardo.
		@return UY_Receipt_Resguardo	  */
	public int getUY_Receipt_Resguardo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Receipt_Resguardo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Retention getUY_Retention() throws RuntimeException
    {
		return (I_UY_Retention)MTable.get(getCtx(), I_UY_Retention.Table_Name)
			.getPO(getUY_Retention_ID(), get_TrxName());	}

	/** Set UY_Retention_ID.
		@param UY_Retention_ID UY_Retention_ID	  */
	public void setUY_Retention_ID (int UY_Retention_ID)
	{
		if (UY_Retention_ID < 1) 
			set_Value (COLUMNNAME_UY_Retention_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Retention_ID, Integer.valueOf(UY_Retention_ID));
	}

	/** Get UY_Retention_ID.
		@return UY_Retention_ID	  */
	public int getUY_Retention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Retention_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}