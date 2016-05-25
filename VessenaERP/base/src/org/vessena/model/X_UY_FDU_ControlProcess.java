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

/** Generated Model for UY_FDU_ControlProcess
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_FDU_ControlProcess extends PO implements I_UY_FDU_ControlProcess, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130116L;

    /** Standard Constructor */
    public X_UY_FDU_ControlProcess (Properties ctx, int UY_FDU_ControlProcess_ID, String trxName)
    {
      super (ctx, UY_FDU_ControlProcess_ID, trxName);
      /** if (UY_FDU_ControlProcess_ID == 0)
        {
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setUY_FDU_ControlProcess_ID (0);
			setUY_FDU_ControlType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FDU_ControlProcess (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FDU_ControlProcess[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Difference.
		@param DifferenceAmt 
		Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt)
	{
		set_Value (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

	/** Get Difference.
		@return Difference Amount
	  */
	public BigDecimal getDifferenceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Request = RQ */
	public static final String DOCACTION_Request = "RQ";
	/** Asign = AS */
	public static final String DOCACTION_Asign = "AS";
	/** Pick = PK */
	public static final String DOCACTION_Pick = "PK";
	/** Recive = RV */
	public static final String DOCACTION_Recive = "RV";
	/** Apply = AY */
	public static final String DOCACTION_Apply = "AY";
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
	/** Applied = AY */
	public static final String DOCSTATUS_Applied = "AY";
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set reprocessing.
		@param reprocessing reprocessing	  */
	public void setreprocessing (String reprocessing)
	{
		set_Value (COLUMNNAME_reprocessing, reprocessing);
	}

	/** Get reprocessing.
		@return reprocessing	  */
	public String getreprocessing () 
	{
		return (String)get_Value(COLUMNNAME_reprocessing);
	}

	/** Set Send EMail.
		@param SendEMail 
		Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail)
	{
		set_Value (COLUMNNAME_SendEMail, Boolean.valueOf(SendEMail));
	}

	/** Get Send EMail.
		@return Enable sending Document EMail
	  */
	public boolean isSendEMail () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set UY_FDU_ControlProcess.
		@param UY_FDU_ControlProcess_ID UY_FDU_ControlProcess	  */
	public void setUY_FDU_ControlProcess_ID (int UY_FDU_ControlProcess_ID)
	{
		if (UY_FDU_ControlProcess_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FDU_ControlProcess_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FDU_ControlProcess_ID, Integer.valueOf(UY_FDU_ControlProcess_ID));
	}

	/** Get UY_FDU_ControlProcess.
		@return UY_FDU_ControlProcess	  */
	public int getUY_FDU_ControlProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_ControlProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FDU_ControlType getUY_FDU_ControlType() throws RuntimeException
    {
		return (I_UY_FDU_ControlType)MTable.get(getCtx(), I_UY_FDU_ControlType.Table_Name)
			.getPO(getUY_FDU_ControlType_ID(), get_TrxName());	}

	/** Set UY_FDU_ControlType.
		@param UY_FDU_ControlType_ID UY_FDU_ControlType	  */
	public void setUY_FDU_ControlType_ID (int UY_FDU_ControlType_ID)
	{
		if (UY_FDU_ControlType_ID < 1) 
			set_Value (COLUMNNAME_UY_FDU_ControlType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FDU_ControlType_ID, Integer.valueOf(UY_FDU_ControlType_ID));
	}

	/** Get UY_FDU_ControlType.
		@return UY_FDU_ControlType	  */
	public int getUY_FDU_ControlType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_ControlType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}