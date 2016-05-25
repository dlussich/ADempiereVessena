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

/** Generated Model for UY_ClockInterface_Filter
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ClockInterface_Filter extends PO implements I_UY_ClockInterface_Filter, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120508L;

    /** Standard Constructor */
    public X_UY_ClockInterface_Filter (Properties ctx, int UY_ClockInterface_Filter_ID, String trxName)
    {
      super (ctx, UY_ClockInterface_Filter_ID, trxName);
      /** if (UY_ClockInterface_Filter_ID == 0)
        {
			setProcessed (false);
			setuy_clockinterface_filter_ID (0);
			setuy_docaction_update (null);
			setUY_HRProcess_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ClockInterface_Filter (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ClockInterface_Filter[")
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

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
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
	/** Recived = RV */
	public static final String DOCSTATUS_Recived = "RV";
	/** Requested = RQ */
	public static final String DOCSTATUS_Requested = "RQ";
	/** Asigned = AS */
	public static final String DOCSTATUS_Asigned = "AS";
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

	/** Set original_line.
		@param original_line original_line	  */
	public void setoriginal_line (String original_line)
	{
		set_Value (COLUMNNAME_original_line, original_line);
	}

	/** Get original_line.
		@return original_line	  */
	public String getoriginal_line () 
	{
		return (String)get_Value(COLUMNNAME_original_line);
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

	/** Set UY_ClockInterface_Filter.
		@param uy_clockinterface_filter_ID UY_ClockInterface_Filter	  */
	public void setuy_clockinterface_filter_ID (int uy_clockinterface_filter_ID)
	{
		if (uy_clockinterface_filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_uy_clockinterface_filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_uy_clockinterface_filter_ID, Integer.valueOf(uy_clockinterface_filter_ID));
	}

	/** Get UY_ClockInterface_Filter.
		@return UY_ClockInterface_Filter	  */
	public int getuy_clockinterface_filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_clockinterface_filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_docaction_update.
		@param uy_docaction_update uy_docaction_update	  */
	public void setuy_docaction_update (String uy_docaction_update)
	{
		set_Value (COLUMNNAME_uy_docaction_update, uy_docaction_update);
	}

	/** Get uy_docaction_update.
		@return uy_docaction_update	  */
	public String getuy_docaction_update () 
	{
		return (String)get_Value(COLUMNNAME_uy_docaction_update);
	}

	public I_UY_HRProcess getUY_HRProcess() throws RuntimeException
    {
		return (I_UY_HRProcess)MTable.get(getCtx(), I_UY_HRProcess.Table_Name)
			.getPO(getUY_HRProcess_ID(), get_TrxName());	}

	/** Set UY_HRProcess.
		@param UY_HRProcess_ID UY_HRProcess	  */
	public void setUY_HRProcess_ID (int UY_HRProcess_ID)
	{
		if (UY_HRProcess_ID < 1) 
			set_Value (COLUMNNAME_UY_HRProcess_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRProcess_ID, Integer.valueOf(UY_HRProcess_ID));
	}

	/** Get UY_HRProcess.
		@return UY_HRProcess	  */
	public int getUY_HRProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/*public I_UY_TipoMarcaReloj getuy_tipomarcareloj() throws RuntimeException
    {
		return (I_UY_TipoMarcaReloj)MTable.get(getCtx(), I_UY_TipoMarcaReloj.Table_Name)
			.getPO(getuy_tipomarcareloj_ID(), get_TrxName());	}*/

	/** Set uy_tipomarcareloj.
		@param uy_tipomarcareloj_ID uy_tipomarcareloj	  */
	public void setuy_tipomarcareloj_ID (int uy_tipomarcareloj_ID)
	{
		if (uy_tipomarcareloj_ID < 1) 
			set_Value (COLUMNNAME_uy_tipomarcareloj_ID, null);
		else 
			set_Value (COLUMNNAME_uy_tipomarcareloj_ID, Integer.valueOf(uy_tipomarcareloj_ID));
	}

	/** Get uy_tipomarcareloj.
		@return uy_tipomarcareloj	  */
	public int getuy_tipomarcareloj_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_tipomarcareloj_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}