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

/** Generated Model for UY_RecuentoHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_RecuentoHdr extends PO implements I_UY_RecuentoHdr, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_RecuentoHdr (Properties ctx, int UY_RecuentoHdr_ID, String trxName)
    {
      super (ctx, UY_RecuentoHdr_ID, trxName);
      /** if (UY_RecuentoHdr_ID == 0)
        {
			setC_DocType_ID (0);
			setDocumentNo (null);
			setUY_RecuentoDef_ID (0);
			setUY_RecuentoHdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RecuentoHdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RecuentoHdr[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
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

	/** Set fecdoc.
		@param fecdoc fecdoc	  */
	public void setfecdoc (Timestamp fecdoc)
	{
		set_ValueNoCheck (COLUMNNAME_fecdoc, fecdoc);
	}

	/** Get fecdoc.
		@return fecdoc	  */
	public Timestamp getfecdoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecdoc);
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

	public I_AD_User getUser1() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getUser1_ID(), get_TrxName());	}

	/** Set User List 1.
		@param User1_ID 
		User defined list element #1
	  */
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_User1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get User List 1.
		@return User defined list element #1
	  */
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RecuentoDef getUY_RecuentoDef() throws RuntimeException
    {
		return (I_UY_RecuentoDef)MTable.get(getCtx(), I_UY_RecuentoDef.Table_Name)
			.getPO(getUY_RecuentoDef_ID(), get_TrxName());	}

	/** Set UY_RecuentoDef.
		@param UY_RecuentoDef_ID UY_RecuentoDef	  */
	public void setUY_RecuentoDef_ID (int UY_RecuentoDef_ID)
	{
		if (UY_RecuentoDef_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RecuentoDef_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RecuentoDef_ID, Integer.valueOf(UY_RecuentoDef_ID));
	}

	/** Get UY_RecuentoDef.
		@return UY_RecuentoDef	  */
	public int getUY_RecuentoDef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RecuentoDef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RecuentoHdr.
		@param UY_RecuentoHdr_ID UY_RecuentoHdr	  */
	public void setUY_RecuentoHdr_ID (int UY_RecuentoHdr_ID)
	{
		if (UY_RecuentoHdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RecuentoHdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RecuentoHdr_ID, Integer.valueOf(UY_RecuentoHdr_ID));
	}

	/** Get UY_RecuentoHdr.
		@return UY_RecuentoHdr	  */
	public int getUY_RecuentoHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RecuentoHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}