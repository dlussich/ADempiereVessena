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

/** Generated Model for UY_ExtProvisionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ExtProvisionLine extends PO implements I_UY_ExtProvisionLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140429L;

    /** Standard Constructor */
    public X_UY_ExtProvisionLine (Properties ctx, int UY_ExtProvisionLine_ID, String trxName)
    {
      super (ctx, UY_ExtProvisionLine_ID, trxName);
      /** if (UY_ExtProvisionLine_ID == 0)
        {
			setamtallocated (Env.ZERO);
			setamtdocument (Env.ZERO);
			setamtopen (Env.ZERO);
			setC_BPartner_ID (0);
			setC_Period_ID (0);
			setIsSelected (true);
// Y
			setUY_ExtProvision_ID (0);
			setUY_ExtProvisionLine_ID (0);
			setUY_Provision_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ExtProvisionLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ExtProvisionLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amtallocated.
		@param amtallocated amtallocated	  */
	public void setamtallocated (BigDecimal amtallocated)
	{
		set_Value (COLUMNNAME_amtallocated, amtallocated);
	}

	/** Get amtallocated.
		@return amtallocated	  */
	public BigDecimal getamtallocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtallocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtdocument.
		@param amtdocument amtdocument	  */
	public void setamtdocument (BigDecimal amtdocument)
	{
		set_Value (COLUMNNAME_amtdocument, amtdocument);
	}

	/** Get amtdocument.
		@return amtdocument	  */
	public BigDecimal getamtdocument () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtdocument);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtopen.
		@param amtopen amtopen	  */
	public void setamtopen (BigDecimal amtopen)
	{
		set_Value (COLUMNNAME_amtopen, amtopen);
	}

	/** Get amtopen.
		@return amtopen	  */
	public BigDecimal getamtopen () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtopen);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set C_Activity_ID_1.
		@param C_Activity_ID_1 C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1)
	{
		set_Value (COLUMNNAME_C_Activity_ID_1, Integer.valueOf(C_Activity_ID_1));
	}

	/** Get C_Activity_ID_1.
		@return C_Activity_ID_1	  */
	public int getC_Activity_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
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

	public I_UY_ExtProvision getUY_ExtProvision() throws RuntimeException
    {
		return (I_UY_ExtProvision)MTable.get(getCtx(), I_UY_ExtProvision.Table_Name)
			.getPO(getUY_ExtProvision_ID(), get_TrxName());	}

	/** Set UY_ExtProvision.
		@param UY_ExtProvision_ID UY_ExtProvision	  */
	public void setUY_ExtProvision_ID (int UY_ExtProvision_ID)
	{
		if (UY_ExtProvision_ID < 1) 
			set_Value (COLUMNNAME_UY_ExtProvision_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ExtProvision_ID, Integer.valueOf(UY_ExtProvision_ID));
	}

	/** Get UY_ExtProvision.
		@return UY_ExtProvision	  */
	public int getUY_ExtProvision_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ExtProvision_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ExtProvisionLine.
		@param UY_ExtProvisionLine_ID UY_ExtProvisionLine	  */
	public void setUY_ExtProvisionLine_ID (int UY_ExtProvisionLine_ID)
	{
		if (UY_ExtProvisionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ExtProvisionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ExtProvisionLine_ID, Integer.valueOf(UY_ExtProvisionLine_ID));
	}

	/** Get UY_ExtProvisionLine.
		@return UY_ExtProvisionLine	  */
	public int getUY_ExtProvisionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ExtProvisionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Provision getUY_Provision() throws RuntimeException
    {
		return (I_UY_Provision)MTable.get(getCtx(), I_UY_Provision.Table_Name)
			.getPO(getUY_Provision_ID(), get_TrxName());	}

	/** Set UY_Provision.
		@param UY_Provision_ID UY_Provision	  */
	public void setUY_Provision_ID (int UY_Provision_ID)
	{
		if (UY_Provision_ID < 1) 
			set_Value (COLUMNNAME_UY_Provision_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Provision_ID, Integer.valueOf(UY_Provision_ID));
	}

	/** Get UY_Provision.
		@return UY_Provision	  */
	public int getUY_Provision_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Provision_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ProvisionLine getUY_ProvisionLine() throws RuntimeException
    {
		return (I_UY_ProvisionLine)MTable.get(getCtx(), I_UY_ProvisionLine.Table_Name)
			.getPO(getUY_ProvisionLine_ID(), get_TrxName());	}

	/** Set UY_ProvisionLine.
		@param UY_ProvisionLine_ID UY_ProvisionLine	  */
	public void setUY_ProvisionLine_ID (int UY_ProvisionLine_ID)
	{
		if (UY_ProvisionLine_ID < 1) 
			set_Value (COLUMNNAME_UY_ProvisionLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProvisionLine_ID, Integer.valueOf(UY_ProvisionLine_ID));
	}

	/** Get UY_ProvisionLine.
		@return UY_ProvisionLine	  */
	public int getUY_ProvisionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProvisionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}