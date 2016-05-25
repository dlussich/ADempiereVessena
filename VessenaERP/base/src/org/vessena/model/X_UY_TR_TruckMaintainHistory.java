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

/** Generated Model for UY_TR_TruckMaintainHistory
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_TruckMaintainHistory extends PO implements I_UY_TR_TruckMaintainHistory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150817L;

    /** Standard Constructor */
    public X_UY_TR_TruckMaintainHistory (Properties ctx, int UY_TR_TruckMaintainHistory_ID, String trxName)
    {
      super (ctx, UY_TR_TruckMaintainHistory_ID, trxName);
      /** if (UY_TR_TruckMaintainHistory_ID == 0)
        {
			setIsOwn (false);
			setUY_TR_Maintain_ID (0);
			setUY_TR_ServiceOrder_ID (0);
			setUY_TR_Truck_ID (0);
			setUY_TR_TruckMaintainHistory_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_TruckMaintainHistory (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_TruckMaintainHistory[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Date Ordered.
		@param DateOrdered 
		Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered)
	{
		throw new IllegalArgumentException ("DateOrdered is virtual column");	}

	/** Get Date Ordered.
		@return Date of Order
	  */
	public Timestamp getDateOrdered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOrdered);
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

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Asigned = AS */
	public static final String DOCSTATUS_Asigned = "AS";
	/** Requested = RQ */
	public static final String DOCSTATUS_Requested = "RQ";
	/** Recived = RV */
	public static final String DOCSTATUS_Recived = "RV";
	/** Picking = PK */
	public static final String DOCSTATUS_Picking = "PK";
	/** Applied = AY */
	public static final String DOCSTATUS_Applied = "AY";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		throw new IllegalArgumentException ("DocStatus is virtual column");	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set IsOwn.
		@param IsOwn IsOwn	  */
	public void setIsOwn (boolean IsOwn)
	{
		set_Value (COLUMNNAME_IsOwn, Boolean.valueOf(IsOwn));
	}

	/** Get IsOwn.
		@return IsOwn	  */
	public boolean isOwn () 
	{
		Object oo = get_Value(COLUMNNAME_IsOwn);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kilometros.
		@param Kilometros Kilometros	  */
	public void setKilometros (int Kilometros)
	{
		throw new IllegalArgumentException ("Kilometros is virtual column");	}

	/** Get Kilometros.
		@return Kilometros	  */
	public int getKilometros () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Kilometros);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		throw new IllegalArgumentException ("TotalLines is virtual column");	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_TR_Failure getUY_TR_Failure() throws RuntimeException
    {
		return (I_UY_TR_Failure)MTable.get(getCtx(), I_UY_TR_Failure.Table_Name)
			.getPO(getUY_TR_Failure_ID(), get_TrxName());	}

	/** Set UY_TR_Failure.
		@param UY_TR_Failure_ID UY_TR_Failure	  */
	public void setUY_TR_Failure_ID (int UY_TR_Failure_ID)
	{
		if (UY_TR_Failure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Failure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Failure_ID, Integer.valueOf(UY_TR_Failure_ID));
	}

	/** Get UY_TR_Failure.
		@return UY_TR_Failure	  */
	public int getUY_TR_Failure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Failure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Maintain getUY_TR_Maintain() throws RuntimeException
    {
		return (I_UY_TR_Maintain)MTable.get(getCtx(), I_UY_TR_Maintain.Table_Name)
			.getPO(getUY_TR_Maintain_ID(), get_TrxName());	}

	/** Set UY_TR_Maintain.
		@param UY_TR_Maintain_ID UY_TR_Maintain	  */
	public void setUY_TR_Maintain_ID (int UY_TR_Maintain_ID)
	{
		if (UY_TR_Maintain_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Maintain_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Maintain_ID, Integer.valueOf(UY_TR_Maintain_ID));
	}

	/** Get UY_TR_Maintain.
		@return UY_TR_Maintain	  */
	public int getUY_TR_Maintain_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Maintain_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_ServiceOrder getUY_TR_ServiceOrder() throws RuntimeException
    {
		return (I_UY_TR_ServiceOrder)MTable.get(getCtx(), I_UY_TR_ServiceOrder.Table_Name)
			.getPO(getUY_TR_ServiceOrder_ID(), get_TrxName());	}

	/** Set UY_TR_ServiceOrder.
		@param UY_TR_ServiceOrder_ID UY_TR_ServiceOrder	  */
	public void setUY_TR_ServiceOrder_ID (int UY_TR_ServiceOrder_ID)
	{
		if (UY_TR_ServiceOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_ServiceOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_ServiceOrder_ID, Integer.valueOf(UY_TR_ServiceOrder_ID));
	}

	/** Get UY_TR_ServiceOrder.
		@return UY_TR_ServiceOrder	  */
	public int getUY_TR_ServiceOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ServiceOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException
    {
		return (I_UY_TR_Truck)MTable.get(getCtx(), I_UY_TR_Truck.Table_Name)
			.getPO(getUY_TR_Truck_ID(), get_TrxName());	}

	/** Set UY_TR_Truck.
		@param UY_TR_Truck_ID UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID)
	{
		if (UY_TR_Truck_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, Integer.valueOf(UY_TR_Truck_ID));
	}

	/** Get UY_TR_Truck.
		@return UY_TR_Truck	  */
	public int getUY_TR_Truck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TruckMaintainHistory.
		@param UY_TR_TruckMaintainHistory_ID UY_TR_TruckMaintainHistory	  */
	public void setUY_TR_TruckMaintainHistory_ID (int UY_TR_TruckMaintainHistory_ID)
	{
		if (UY_TR_TruckMaintainHistory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TruckMaintainHistory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TruckMaintainHistory_ID, Integer.valueOf(UY_TR_TruckMaintainHistory_ID));
	}

	/** Get UY_TR_TruckMaintainHistory.
		@return UY_TR_TruckMaintainHistory	  */
	public int getUY_TR_TruckMaintainHistory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckMaintainHistory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}