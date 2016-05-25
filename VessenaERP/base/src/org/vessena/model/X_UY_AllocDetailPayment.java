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

/** Generated Model for UY_AllocDetailPayment
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_AllocDetailPayment extends PO implements I_UY_AllocDetailPayment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_AllocDetailPayment (Properties ctx, int UY_AllocDetailPayment_ID, String trxName)
    {
      super (ctx, UY_AllocDetailPayment_ID, trxName);
      /** if (UY_AllocDetailPayment_ID == 0)
        {
			setAD_User_ID (0);
			setamtallocated (Env.ZERO);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setC_Payment_ID (0);
			setdateallocated (new Timestamp( System.currentTimeMillis() ));
			setdatemodified (new Timestamp( System.currentTimeMillis() ));
			setdocstatusallocation (null);
			setDocumentNo (null);
			setIsManual (false);
			setUY_AllocDetailPayment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_AllocDetailPayment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_AllocDetailPayment[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_User getAD_User() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
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

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
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

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
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

	public I_C_Payment getC_Payment() throws RuntimeException
    {
		return (I_C_Payment)MTable.get(getCtx(), I_C_Payment.Table_Name)
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

	/** Set dateallocated.
		@param dateallocated dateallocated	  */
	public void setdateallocated (Timestamp dateallocated)
	{
		set_Value (COLUMNNAME_dateallocated, dateallocated);
	}

	/** Get dateallocated.
		@return dateallocated	  */
	public Timestamp getdateallocated () 
	{
		return (Timestamp)get_Value(COLUMNNAME_dateallocated);
	}

	/** Set datemodified.
		@param datemodified datemodified	  */
	public void setdatemodified (Timestamp datemodified)
	{
		set_Value (COLUMNNAME_datemodified, datemodified);
	}

	/** Get datemodified.
		@return datemodified	  */
	public Timestamp getdatemodified () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datemodified);
	}

	/** docstatusallocation AD_Reference_ID=131 */
	public static final int DOCSTATUSALLOCATION_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUSALLOCATION_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUSALLOCATION_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUSALLOCATION_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUSALLOCATION_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUSALLOCATION_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUSALLOCATION_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUSALLOCATION_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUSALLOCATION_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUSALLOCATION_Unknown = "??";
	/** En Proceso = IP */
	public static final String DOCSTATUSALLOCATION_EnProceso = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUSALLOCATION_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUSALLOCATION_WaitingConfirmation = "WC";
	/** Asigned = AS */
	public static final String DOCSTATUSALLOCATION_Asigned = "AS";
	/** Requested = RQ */
	public static final String DOCSTATUSALLOCATION_Requested = "RQ";
	/** Recived = RV */
	public static final String DOCSTATUSALLOCATION_Recived = "RV";
	/** Picking = PK */
	public static final String DOCSTATUSALLOCATION_Picking = "PK";
	/** Set docstatusallocation.
		@param docstatusallocation docstatusallocation	  */
	public void setdocstatusallocation (String docstatusallocation)
	{

		set_Value (COLUMNNAME_docstatusallocation, docstatusallocation);
	}

	/** Get docstatusallocation.
		@return docstatusallocation	  */
	public String getdocstatusallocation () 
	{
		return (String)get_Value(COLUMNNAME_docstatusallocation);
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

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_Allocation getUY_Allocation() throws RuntimeException
    {
		return (I_UY_Allocation)MTable.get(getCtx(), I_UY_Allocation.Table_Name)
			.getPO(getUY_Allocation_ID(), get_TrxName());	}

	/** Set UY_Allocation_ID.
		@param UY_Allocation_ID UY_Allocation_ID	  */
	public void setUY_Allocation_ID (int UY_Allocation_ID)
	{
		if (UY_Allocation_ID < 1) 
			set_Value (COLUMNNAME_UY_Allocation_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Allocation_ID, Integer.valueOf(UY_Allocation_ID));
	}

	/** Get UY_Allocation_ID.
		@return UY_Allocation_ID	  */
	public int getUY_Allocation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Allocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_AllocDetailPayment.
		@param UY_AllocDetailPayment_ID UY_AllocDetailPayment	  */
	public void setUY_AllocDetailPayment_ID (int UY_AllocDetailPayment_ID)
	{
		if (UY_AllocDetailPayment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_AllocDetailPayment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_AllocDetailPayment_ID, Integer.valueOf(UY_AllocDetailPayment_ID));
	}

	/** Get UY_AllocDetailPayment.
		@return UY_AllocDetailPayment	  */
	public int getUY_AllocDetailPayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AllocDetailPayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}