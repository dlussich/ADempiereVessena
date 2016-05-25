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

/** Generated Model for UY_RT_InternalInvoiceLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_InternalInvoiceLine extends PO implements I_UY_RT_InternalInvoiceLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160201L;

    /** Standard Constructor */
    public X_UY_RT_InternalInvoiceLine (Properties ctx, int UY_RT_InternalInvoiceLine_ID, String trxName)
    {
      super (ctx, UY_RT_InternalInvoiceLine_ID, trxName);
      /** if (UY_RT_InternalInvoiceLine_ID == 0)
        {
			setAD_Org_ID_To (0);
			setC_BPartner_ID (0);
			setM_Warehouse_ID (0);
			setUY_RT_InternalDelivery_ID (0);
			setUY_RT_InternalInvoice_ID (0);
			setUY_RT_InternalInvoiceLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_InternalInvoiceLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_InternalInvoiceLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_Org_ID_To.
		@param AD_Org_ID_To AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To)
	{
		set_Value (COLUMNNAME_AD_Org_ID_To, Integer.valueOf(AD_Org_ID_To));
	}

	/** Get AD_Org_ID_To.
		@return AD_Org_ID_To	  */
	public int getAD_Org_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Org_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_InternalDelivery getUY_RT_InternalDelivery() throws RuntimeException
    {
		return (I_UY_RT_InternalDelivery)MTable.get(getCtx(), I_UY_RT_InternalDelivery.Table_Name)
			.getPO(getUY_RT_InternalDelivery_ID(), get_TrxName());	}

	/** Set UY_RT_InternalDelivery.
		@param UY_RT_InternalDelivery_ID UY_RT_InternalDelivery	  */
	public void setUY_RT_InternalDelivery_ID (int UY_RT_InternalDelivery_ID)
	{
		if (UY_RT_InternalDelivery_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_InternalDelivery_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_InternalDelivery_ID, Integer.valueOf(UY_RT_InternalDelivery_ID));
	}

	/** Get UY_RT_InternalDelivery.
		@return UY_RT_InternalDelivery	  */
	public int getUY_RT_InternalDelivery_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_InternalDelivery_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_InternalInvoice getUY_RT_InternalInvoice() throws RuntimeException
    {
		return (I_UY_RT_InternalInvoice)MTable.get(getCtx(), I_UY_RT_InternalInvoice.Table_Name)
			.getPO(getUY_RT_InternalInvoice_ID(), get_TrxName());	}

	/** Set UY_RT_InternalInvoice.
		@param UY_RT_InternalInvoice_ID UY_RT_InternalInvoice	  */
	public void setUY_RT_InternalInvoice_ID (int UY_RT_InternalInvoice_ID)
	{
		if (UY_RT_InternalInvoice_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_InternalInvoice_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_InternalInvoice_ID, Integer.valueOf(UY_RT_InternalInvoice_ID));
	}

	/** Get UY_RT_InternalInvoice.
		@return UY_RT_InternalInvoice	  */
	public int getUY_RT_InternalInvoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_InternalInvoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RT_InternalInvoiceLine.
		@param UY_RT_InternalInvoiceLine_ID UY_RT_InternalInvoiceLine	  */
	public void setUY_RT_InternalInvoiceLine_ID (int UY_RT_InternalInvoiceLine_ID)
	{
		if (UY_RT_InternalInvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InternalInvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InternalInvoiceLine_ID, Integer.valueOf(UY_RT_InternalInvoiceLine_ID));
	}

	/** Get UY_RT_InternalInvoiceLine.
		@return UY_RT_InternalInvoiceLine	  */
	public int getUY_RT_InternalInvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_InternalInvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}