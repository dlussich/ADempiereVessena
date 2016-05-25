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

/** Generated Model for UY_CargoRegisterLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_CargoRegisterLine extends PO implements I_UY_CargoRegisterLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130510L;

    /** Standard Constructor */
    public X_UY_CargoRegisterLine (Properties ctx, int UY_CargoRegisterLine_ID, String trxName)
    {
      super (ctx, UY_CargoRegisterLine_ID, trxName);
      /** if (UY_CargoRegisterLine_ID == 0)
        {
			setAmount (Env.ZERO);
			setIsDebit (false);
// N
			setUY_Cargo_ID (0);
			setUY_CargoRegister_ID (0);
			setUY_CargoRegisterLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CargoRegisterLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CargoRegisterLine[")
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

	/** Set IsDebit.
		@param IsDebit IsDebit	  */
	public void setIsDebit (boolean IsDebit)
	{
		set_Value (COLUMNNAME_IsDebit, Boolean.valueOf(IsDebit));
	}

	/** Get IsDebit.
		@return IsDebit	  */
	public boolean isDebit () 
	{
		Object oo = get_Value(COLUMNNAME_IsDebit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_Cargo getUY_Cargo() throws RuntimeException
    {
		return (I_UY_Cargo)MTable.get(getCtx(), I_UY_Cargo.Table_Name)
			.getPO(getUY_Cargo_ID(), get_TrxName());	}

	/** Set UY_Cargo.
		@param UY_Cargo_ID UY_Cargo	  */
	public void setUY_Cargo_ID (int UY_Cargo_ID)
	{
		if (UY_Cargo_ID < 1) 
			set_Value (COLUMNNAME_UY_Cargo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Cargo_ID, Integer.valueOf(UY_Cargo_ID));
	}

	/** Get UY_Cargo.
		@return UY_Cargo	  */
	public int getUY_Cargo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Cargo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CargoRegister getUY_CargoRegister() throws RuntimeException
    {
		return (I_UY_CargoRegister)MTable.get(getCtx(), I_UY_CargoRegister.Table_Name)
			.getPO(getUY_CargoRegister_ID(), get_TrxName());	}

	/** Set UY_CargoRegister.
		@param UY_CargoRegister_ID UY_CargoRegister	  */
	public void setUY_CargoRegister_ID (int UY_CargoRegister_ID)
	{
		if (UY_CargoRegister_ID < 1) 
			set_Value (COLUMNNAME_UY_CargoRegister_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CargoRegister_ID, Integer.valueOf(UY_CargoRegister_ID));
	}

	/** Get UY_CargoRegister.
		@return UY_CargoRegister	  */
	public int getUY_CargoRegister_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CargoRegister_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CargoRegisterLine.
		@param UY_CargoRegisterLine_ID UY_CargoRegisterLine	  */
	public void setUY_CargoRegisterLine_ID (int UY_CargoRegisterLine_ID)
	{
		if (UY_CargoRegisterLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CargoRegisterLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CargoRegisterLine_ID, Integer.valueOf(UY_CargoRegisterLine_ID));
	}

	/** Get UY_CargoRegisterLine.
		@return UY_CargoRegisterLine	  */
	public int getUY_CargoRegisterLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CargoRegisterLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}