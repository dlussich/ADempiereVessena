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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_DocApprovalLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DocApprovalLine extends PO implements I_UY_DocApprovalLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150729L;

    /** Standard Constructor */
    public X_UY_DocApprovalLine (Properties ctx, int UY_DocApprovalLine_ID, String trxName)
    {
      super (ctx, UY_DocApprovalLine_ID, trxName);
      /** if (UY_DocApprovalLine_ID == 0)
        {
			setIsSelected (false);
// N
			setUY_DocApproval_ID (0);
			setUY_DocApprovalLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DocApprovalLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DocApprovalLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
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

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
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

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_DocApproval getUY_DocApproval() throws RuntimeException
    {
		return (I_UY_DocApproval)MTable.get(getCtx(), I_UY_DocApproval.Table_Name)
			.getPO(getUY_DocApproval_ID(), get_TrxName());	}

	/** Set UY_DocApproval.
		@param UY_DocApproval_ID UY_DocApproval	  */
	public void setUY_DocApproval_ID (int UY_DocApproval_ID)
	{
		if (UY_DocApproval_ID < 1) 
			set_Value (COLUMNNAME_UY_DocApproval_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DocApproval_ID, Integer.valueOf(UY_DocApproval_ID));
	}

	/** Get UY_DocApproval.
		@return UY_DocApproval	  */
	public int getUY_DocApproval_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DocApproval_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DocApprovalLine.
		@param UY_DocApprovalLine_ID UY_DocApprovalLine	  */
	public void setUY_DocApprovalLine_ID (int UY_DocApprovalLine_ID)
	{
		if (UY_DocApprovalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DocApprovalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DocApprovalLine_ID, Integer.valueOf(UY_DocApprovalLine_ID));
	}

	/** Get UY_DocApprovalLine.
		@return UY_DocApprovalLine	  */
	public int getUY_DocApprovalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DocApprovalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_DocApprovalReason getUY_DocApprovalReason() throws RuntimeException
    {
		return (I_UY_DocApprovalReason)MTable.get(getCtx(), I_UY_DocApprovalReason.Table_Name)
			.getPO(getUY_DocApprovalReason_ID(), get_TrxName());	}

	/** Set UY_DocApprovalReason.
		@param UY_DocApprovalReason_ID UY_DocApprovalReason	  */
	public void setUY_DocApprovalReason_ID (int UY_DocApprovalReason_ID)
	{
		if (UY_DocApprovalReason_ID < 1) 
			set_Value (COLUMNNAME_UY_DocApprovalReason_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DocApprovalReason_ID, Integer.valueOf(UY_DocApprovalReason_ID));
	}

	/** Get UY_DocApprovalReason.
		@return UY_DocApprovalReason	  */
	public int getUY_DocApprovalReason_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DocApprovalReason_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}