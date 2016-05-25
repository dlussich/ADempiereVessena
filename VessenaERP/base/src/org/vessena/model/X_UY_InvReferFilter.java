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

/** Generated Model for UY_InvReferFilter
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_InvReferFilter extends PO implements I_UY_InvReferFilter, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_InvReferFilter (Properties ctx, int UY_InvReferFilter_ID, String trxName)
    {
      super (ctx, UY_InvReferFilter_ID, trxName);
      /** if (UY_InvReferFilter_ID == 0)
        {
			setProcessed (false);
			setUY_InvReferFilter_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvReferFilter (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvReferFilter[")
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

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
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

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
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

	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
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
	/** En Proceso = IP */
	public static final String DOCSTATUS_EnProceso = "IP";
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
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_CanalVentas getUY_CanalVentas() throws RuntimeException
    {
		return (I_UY_CanalVentas)MTable.get(getCtx(), I_UY_CanalVentas.Table_Name)
			.getPO(getUY_CanalVentas_ID(), get_TrxName());	}

	/** Set UY_CanalVentas.
		@param UY_CanalVentas_ID UY_CanalVentas	  */
	public void setUY_CanalVentas_ID (int UY_CanalVentas_ID)
	{
		if (UY_CanalVentas_ID < 1) 
			set_Value (COLUMNNAME_UY_CanalVentas_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CanalVentas_ID, Integer.valueOf(UY_CanalVentas_ID));
	}

	/** Get UY_CanalVentas.
		@return UY_CanalVentas	  */
	public int getUY_CanalVentas_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CanalVentas_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_dateinvoiced_from.
		@param uy_dateinvoiced_from uy_dateinvoiced_from	  */
	public void setuy_dateinvoiced_from (Timestamp uy_dateinvoiced_from)
	{
		set_Value (COLUMNNAME_uy_dateinvoiced_from, uy_dateinvoiced_from);
	}

	/** Get uy_dateinvoiced_from.
		@return uy_dateinvoiced_from	  */
	public Timestamp getuy_dateinvoiced_from () 
	{
		return (Timestamp)get_Value(COLUMNNAME_uy_dateinvoiced_from);
	}

	/** Set uy_dateinvoiced_to.
		@param uy_dateinvoiced_to uy_dateinvoiced_to	  */
	public void setuy_dateinvoiced_to (Timestamp uy_dateinvoiced_to)
	{
		set_Value (COLUMNNAME_uy_dateinvoiced_to, uy_dateinvoiced_to);
	}

	/** Get uy_dateinvoiced_to.
		@return uy_dateinvoiced_to	  */
	public Timestamp getuy_dateinvoiced_to () 
	{
		return (Timestamp)get_Value(COLUMNNAME_uy_dateinvoiced_to);
	}

	/** Set UY_InvReferFilter.
		@param UY_InvReferFilter_ID UY_InvReferFilter	  */
	public void setUY_InvReferFilter_ID (int UY_InvReferFilter_ID)
	{
		if (UY_InvReferFilter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvReferFilter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvReferFilter_ID, Integer.valueOf(UY_InvReferFilter_ID));
	}

	/** Get UY_InvReferFilter.
		@return UY_InvReferFilter	  */
	public int getUY_InvReferFilter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvReferFilter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}