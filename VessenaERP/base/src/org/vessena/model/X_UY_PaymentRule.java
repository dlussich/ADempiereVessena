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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_PaymentRule
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PaymentRule extends PO implements I_UY_PaymentRule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151110L;

    /** Standard Constructor */
    public X_UY_PaymentRule (Properties ctx, int UY_PaymentRule_ID, String trxName)
    {
      super (ctx, UY_PaymentRule_ID, trxName);
      /** if (UY_PaymentRule_ID == 0)
        {
			setIsApplied (false);
// N
			setIsBankHandler (false);
// N
			setIsPayEmit (false);
// N
			setIsStocked (false);
// N
			setName (null);
			setNeedNumber (false);
// N
			setpaymentruletype (null);
			setUY_PaymentRule_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_PaymentRule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PaymentRule[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_DocType_ID_PayEmit.
		@param C_DocType_ID_PayEmit C_DocType_ID_PayEmit	  */
	public void setC_DocType_ID_PayEmit (int C_DocType_ID_PayEmit)
	{
		set_Value (COLUMNNAME_C_DocType_ID_PayEmit, Integer.valueOf(C_DocType_ID_PayEmit));
	}

	/** Get C_DocType_ID_PayEmit.
		@return C_DocType_ID_PayEmit	  */
	public int getC_DocType_ID_PayEmit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID_PayEmit);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set IsApplied.
		@param IsApplied IsApplied	  */
	public void setIsApplied (boolean IsApplied)
	{
		set_Value (COLUMNNAME_IsApplied, Boolean.valueOf(IsApplied));
	}

	/** Get IsApplied.
		@return IsApplied	  */
	public boolean isApplied () 
	{
		Object oo = get_Value(COLUMNNAME_IsApplied);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsBankHandler.
		@param IsBankHandler IsBankHandler	  */
	public void setIsBankHandler (boolean IsBankHandler)
	{
		set_Value (COLUMNNAME_IsBankHandler, Boolean.valueOf(IsBankHandler));
	}

	/** Get IsBankHandler.
		@return IsBankHandler	  */
	public boolean isBankHandler () 
	{
		Object oo = get_Value(COLUMNNAME_IsBankHandler);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPayEmit.
		@param IsPayEmit IsPayEmit	  */
	public void setIsPayEmit (boolean IsPayEmit)
	{
		set_Value (COLUMNNAME_IsPayEmit, Boolean.valueOf(IsPayEmit));
	}

	/** Get IsPayEmit.
		@return IsPayEmit	  */
	public boolean isPayEmit () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayEmit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Receipt.
		@param IsReceipt 
		This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	/** Get Receipt.
		@return This is a sales transaction (receipt)
	  */
	public boolean isReceipt () 
	{
		Object oo = get_Value(COLUMNNAME_IsReceipt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Stocked.
		@param IsStocked 
		Organization stocks this product
	  */
	public void setIsStocked (boolean IsStocked)
	{
		set_Value (COLUMNNAME_IsStocked, Boolean.valueOf(IsStocked));
	}

	/** Get Stocked.
		@return Organization stocks this product
	  */
	public boolean isStocked () 
	{
		Object oo = get_Value(COLUMNNAME_IsStocked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set NeedNumber.
		@param NeedNumber NeedNumber	  */
	public void setNeedNumber (boolean NeedNumber)
	{
		set_Value (COLUMNNAME_NeedNumber, Boolean.valueOf(NeedNumber));
	}

	/** Get NeedNumber.
		@return NeedNumber	  */
	public boolean isNeedNumber () 
	{
		Object oo = get_Value(COLUMNNAME_NeedNumber);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** paymentruletype AD_Reference_ID=1000194 */
	public static final int PAYMENTRULETYPE_AD_Reference_ID=1000194;
	/** CONTADO = CO */
	public static final String PAYMENTRULETYPE_CONTADO = "CO";
	/** CREDITO = CR */
	public static final String PAYMENTRULETYPE_CREDITO = "CR";
	/** Set paymentruletype.
		@param paymentruletype paymentruletype	  */
	public void setpaymentruletype (String paymentruletype)
	{

		set_Value (COLUMNNAME_paymentruletype, paymentruletype);
	}

	/** Get paymentruletype.
		@return paymentruletype	  */
	public String getpaymentruletype () 
	{
		return (String)get_Value(COLUMNNAME_paymentruletype);
	}

	/** Set UseSelfBankAccount.
		@param UseSelfBankAccount UseSelfBankAccount	  */
	public void setUseSelfBankAccount (boolean UseSelfBankAccount)
	{
		set_Value (COLUMNNAME_UseSelfBankAccount, Boolean.valueOf(UseSelfBankAccount));
	}

	/** Get UseSelfBankAccount.
		@return UseSelfBankAccount	  */
	public boolean isUseSelfBankAccount () 
	{
		Object oo = get_Value(COLUMNNAME_UseSelfBankAccount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_PaymentRule.
		@param UY_PaymentRule_ID UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID)
	{
		if (UY_PaymentRule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PaymentRule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PaymentRule_ID, Integer.valueOf(UY_PaymentRule_ID));
	}

	/** Get UY_PaymentRule.
		@return UY_PaymentRule	  */
	public int getUY_PaymentRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PaymentRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}