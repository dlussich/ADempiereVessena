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

/** Generated Model for UY_TR_ClearingFuel
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ClearingFuel extends PO implements I_UY_TR_ClearingFuel, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150610L;

    /** Standard Constructor */
    public X_UY_TR_ClearingFuel (Properties ctx, int UY_TR_ClearingFuel_ID, String trxName)
    {
      super (ctx, UY_TR_ClearingFuel_ID, trxName);
      /** if (UY_TR_ClearingFuel_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_Invoice_ID (0);
			setDateOperation (new Timestamp( System.currentTimeMillis() ));
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setGrandTotal (Env.ZERO);
			setIsVerified (false);
// N
			setUY_TR_ClearingFuel_ID (0);
			setUY_TR_Fuel_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ClearingFuel (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ClearingFuel[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateOperation.
		@param DateOperation DateOperation	  */
	public void setDateOperation (Timestamp DateOperation)
	{
		set_Value (COLUMNNAME_DateOperation, DateOperation);
	}

	/** Get DateOperation.
		@return DateOperation	  */
	public Timestamp getDateOperation () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOperation);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set factura.
		@param factura factura	  */
	public void setfactura (String factura)
	{
		set_Value (COLUMNNAME_factura, factura);
	}

	/** Get factura.
		@return factura	  */
	public String getfactura () 
	{
		return (String)get_Value(COLUMNNAME_factura);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Verified.
		@param IsVerified 
		The BOM configuration has been verified
	  */
	public void setIsVerified (boolean IsVerified)
	{
		set_Value (COLUMNNAME_IsVerified, Boolean.valueOf(IsVerified));
	}

	/** Get Verified.
		@return The BOM configuration has been verified
	  */
	public boolean isVerified () 
	{
		Object oo = get_Value(COLUMNNAME_IsVerified);
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
	/** Contado = CO */
	public static final String PAYMENTRULETYPE_Contado = "CO";
	/** Credito = CR */
	public static final String PAYMENTRULETYPE_Credito = "CR";
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

	/** Set UY_TR_ClearingFuel.
		@param UY_TR_ClearingFuel_ID UY_TR_ClearingFuel	  */
	public void setUY_TR_ClearingFuel_ID (int UY_TR_ClearingFuel_ID)
	{
		if (UY_TR_ClearingFuel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ClearingFuel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ClearingFuel_ID, Integer.valueOf(UY_TR_ClearingFuel_ID));
	}

	/** Get UY_TR_ClearingFuel.
		@return UY_TR_ClearingFuel	  */
	public int getUY_TR_ClearingFuel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ClearingFuel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_ClearingTruck getUY_TR_ClearingTruck() throws RuntimeException
    {
		return (I_UY_TR_ClearingTruck)MTable.get(getCtx(), I_UY_TR_ClearingTruck.Table_Name)
			.getPO(getUY_TR_ClearingTruck_ID(), get_TrxName());	}

	/** Set UY_TR_ClearingTruck.
		@param UY_TR_ClearingTruck_ID UY_TR_ClearingTruck	  */
	public void setUY_TR_ClearingTruck_ID (int UY_TR_ClearingTruck_ID)
	{
		if (UY_TR_ClearingTruck_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_ClearingTruck_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_ClearingTruck_ID, Integer.valueOf(UY_TR_ClearingTruck_ID));
	}

	/** Get UY_TR_ClearingTruck.
		@return UY_TR_ClearingTruck	  */
	public int getUY_TR_ClearingTruck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ClearingTruck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Fuel getUY_TR_Fuel() throws RuntimeException
    {
		return (I_UY_TR_Fuel)MTable.get(getCtx(), I_UY_TR_Fuel.Table_Name)
			.getPO(getUY_TR_Fuel_ID(), get_TrxName());	}

	/** Set UY_TR_Fuel.
		@param UY_TR_Fuel_ID UY_TR_Fuel	  */
	public void setUY_TR_Fuel_ID (int UY_TR_Fuel_ID)
	{
		if (UY_TR_Fuel_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Fuel_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Fuel_ID, Integer.valueOf(UY_TR_Fuel_ID));
	}

	/** Get UY_TR_Fuel.
		@return UY_TR_Fuel	  */
	public int getUY_TR_Fuel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Fuel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}