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

/** Generated Model for C_PaymentPayOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_PaymentPayOrder extends PO implements I_C_PaymentPayOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151117L;

    /** Standard Constructor */
    public X_C_PaymentPayOrder (Properties ctx, int C_PaymentPayOrder_ID, String trxName)
    {
      super (ctx, C_PaymentPayOrder_ID, trxName);
      /** if (C_PaymentPayOrder_ID == 0)
        {
			setAmtResguardo (Env.ZERO);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setC_Payment_ID (0);
			setC_PaymentPayOrder_ID (0);
			setdatedocument (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setPayAmt (Env.ZERO);
			setSubtotal (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_PaymentPayOrder (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_PaymentPayOrder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtResguardo.
		@param AmtResguardo AmtResguardo	  */
	public void setAmtResguardo (BigDecimal AmtResguardo)
	{
		set_Value (COLUMNNAME_AmtResguardo, AmtResguardo);
	}

	/** Get AmtResguardo.
		@return AmtResguardo	  */
	public BigDecimal getAmtResguardo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtResguardo);
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

	/** Set C_PaymentPayOrder.
		@param C_PaymentPayOrder_ID C_PaymentPayOrder	  */
	public void setC_PaymentPayOrder_ID (int C_PaymentPayOrder_ID)
	{
		if (C_PaymentPayOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentPayOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentPayOrder_ID, Integer.valueOf(C_PaymentPayOrder_ID));
	}

	/** Get C_PaymentPayOrder.
		@return C_PaymentPayOrder	  */
	public int getC_PaymentPayOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentPayOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set datedocument.
		@param datedocument datedocument	  */
	public void setdatedocument (Timestamp datedocument)
	{
		set_Value (COLUMNNAME_datedocument, datedocument);
	}

	/** Get datedocument.
		@return datedocument	  */
	public Timestamp getdatedocument () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datedocument);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Subtotal.
		@param Subtotal Subtotal	  */
	public void setSubtotal (BigDecimal Subtotal)
	{
		set_Value (COLUMNNAME_Subtotal, Subtotal);
	}

	/** Get Subtotal.
		@return Subtotal	  */
	public BigDecimal getSubtotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Subtotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_PayOrder getUY_PayOrder() throws RuntimeException
    {
		return (I_UY_PayOrder)MTable.get(getCtx(), I_UY_PayOrder.Table_Name)
			.getPO(getUY_PayOrder_ID(), get_TrxName());	}

	/** Set UY_PayOrder.
		@param UY_PayOrder_ID UY_PayOrder	  */
	public void setUY_PayOrder_ID (int UY_PayOrder_ID)
	{
		if (UY_PayOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_PayOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PayOrder_ID, Integer.valueOf(UY_PayOrder_ID));
	}

	/** Get UY_PayOrder.
		@return UY_PayOrder	  */
	public int getUY_PayOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}