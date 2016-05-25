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

/** Generated Model for UY_TR_TransOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_TransOrder extends PO implements I_UY_TR_TransOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141223L;

    /** Standard Constructor */
    public X_UY_TR_TransOrder (Properties ctx, int UY_TR_TransOrder_ID, String trxName)
    {
      super (ctx, UY_TR_TransOrder_ID, trxName);
      /** if (UY_TR_TransOrder_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// AY
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsAssociated (true);
// Y
			setIsLastre (false);
// N
			setIsManual (false);
// N
			setIsOwn (true);
// Y
			setProcessed (false);
// N
			setUY_TR_TransOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_TransOrder (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_TransOrder[")
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

	/** Set C_Currency_ID_2.
		@param C_Currency_ID_2 C_Currency_ID_2	  */
	public void setC_Currency_ID_2 (int C_Currency_ID_2)
	{
		set_Value (COLUMNNAME_C_Currency_ID_2, Integer.valueOf(C_Currency_ID_2));
	}

	/** Get C_Currency_ID_2.
		@return C_Currency_ID_2	  */
	public int getC_Currency_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_2);
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

	/** Set IsAssociated.
		@param IsAssociated IsAssociated	  */
	public void setIsAssociated (boolean IsAssociated)
	{
		set_Value (COLUMNNAME_IsAssociated, Boolean.valueOf(IsAssociated));
	}

	/** Get IsAssociated.
		@return IsAssociated	  */
	public boolean isAssociated () 
	{
		Object oo = get_Value(COLUMNNAME_IsAssociated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsLastre.
		@param IsLastre IsLastre	  */
	public void setIsLastre (boolean IsLastre)
	{
		set_Value (COLUMNNAME_IsLastre, Boolean.valueOf(IsLastre));
	}

	/** Get IsLastre.
		@return IsLastre	  */
	public boolean isLastre () 
	{
		Object oo = get_Value(COLUMNNAME_IsLastre);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set MicDnaNo.
		@param MicDnaNo MicDnaNo	  */
	public void setMicDnaNo (String MicDnaNo)
	{
		set_Value (COLUMNNAME_MicDnaNo, MicDnaNo);
	}

	/** Get MicDnaNo.
		@return MicDnaNo	  */
	public String getMicDnaNo () 
	{
		return (String)get_Value(COLUMNNAME_MicDnaNo);
	}

	/** Set MicNo.
		@param MicNo MicNo	  */
	public void setMicNo (String MicNo)
	{
		set_Value (COLUMNNAME_MicNo, MicNo);
	}

	/** Get MicNo.
		@return MicNo	  */
	public String getMicNo () 
	{
		return (String)get_Value(COLUMNNAME_MicNo);
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

	/** Set Remolque_ID.
		@param Remolque_ID 
		Remolque_ID
	  */
	public void setRemolque_ID (int Remolque_ID)
	{
		if (Remolque_ID < 1) 
			set_Value (COLUMNNAME_Remolque_ID, null);
		else 
			set_Value (COLUMNNAME_Remolque_ID, Integer.valueOf(Remolque_ID));
	}

	/** Get Remolque_ID.
		@return Remolque_ID
	  */
	public int getRemolque_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Remolque_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalWeight.
		@param TotalWeight TotalWeight	  */
	public void setTotalWeight (BigDecimal TotalWeight)
	{
		set_Value (COLUMNNAME_TotalWeight, TotalWeight);
	}

	/** Get TotalWeight.
		@return TotalWeight	  */
	public BigDecimal getTotalWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWeight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tractor_ID.
		@param Tractor_ID Tractor_ID	  */
	public void setTractor_ID (int Tractor_ID)
	{
		if (Tractor_ID < 1) 
			set_Value (COLUMNNAME_Tractor_ID, null);
		else 
			set_Value (COLUMNNAME_Tractor_ID, Integer.valueOf(Tractor_ID));
	}

	/** Get Tractor_ID.
		@return Tractor_ID	  */
	public int getTractor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Tractor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException
    {
		return (I_UY_Ciudad)MTable.get(getCtx(), I_UY_Ciudad.Table_Name)
			.getPO(getUY_Ciudad_ID(), get_TrxName());	}

	/** Set UY_Ciudad.
		@param UY_Ciudad_ID UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID)
	{
		if (UY_Ciudad_ID < 1) 
			set_Value (COLUMNNAME_UY_Ciudad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Ciudad_ID, Integer.valueOf(UY_Ciudad_ID));
	}

	/** Get UY_Ciudad.
		@return UY_Ciudad	  */
	public int getUY_Ciudad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_1.
		@param UY_Ciudad_ID_1 UY_Ciudad_ID_1	  */
	public void setUY_Ciudad_ID_1 (int UY_Ciudad_ID_1)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_1, Integer.valueOf(UY_Ciudad_ID_1));
	}

	/** Get UY_Ciudad_ID_1.
		@return UY_Ciudad_ID_1	  */
	public int getUY_Ciudad_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Driver getUY_TR_Driver() throws RuntimeException
    {
		return (I_UY_TR_Driver)MTable.get(getCtx(), I_UY_TR_Driver.Table_Name)
			.getPO(getUY_TR_Driver_ID(), get_TrxName());	}

	/** Set UY_TR_Driver.
		@param UY_TR_Driver_ID UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID)
	{
		if (UY_TR_Driver_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Driver_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Driver_ID, Integer.valueOf(UY_TR_Driver_ID));
	}

	/** Get UY_TR_Driver.
		@return UY_TR_Driver	  */
	public int getUY_TR_Driver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Driver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException
    {
		return (I_UY_TR_Mic)MTable.get(getCtx(), I_UY_TR_Mic.Table_Name)
			.getPO(getUY_TR_Mic_ID(), get_TrxName());	}

	/** Set UY_TR_Mic.
		@param UY_TR_Mic_ID UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID)
	{
		if (UY_TR_Mic_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, Integer.valueOf(UY_TR_Mic_ID));
	}

	/** Get UY_TR_Mic.
		@return UY_TR_Mic	  */
	public int getUY_TR_Mic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Mic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TransOrder.
		@param UY_TR_TransOrder_ID UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID)
	{
		if (UY_TR_TransOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TransOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TransOrder_ID, Integer.valueOf(UY_TR_TransOrder_ID));
	}

	/** Get UY_TR_TransOrder.
		@return UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrder_ID);
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

	/** Set UY_TR_Truck_ID_Aux.
		@param UY_TR_Truck_ID_Aux UY_TR_Truck_ID_Aux	  */
	public void setUY_TR_Truck_ID_Aux (int UY_TR_Truck_ID_Aux)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_Aux, Integer.valueOf(UY_TR_Truck_ID_Aux));
	}

	/** Get UY_TR_Truck_ID_Aux.
		@return UY_TR_Truck_ID_Aux	  */
	public int getUY_TR_Truck_ID_Aux () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_Aux);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TruckType getUY_TR_TruckType() throws RuntimeException
    {
		return (I_UY_TR_TruckType)MTable.get(getCtx(), I_UY_TR_TruckType.Table_Name)
			.getPO(getUY_TR_TruckType_ID(), get_TrxName());	}

	/** Set UY_TR_TruckType.
		@param UY_TR_TruckType_ID UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID)
	{
		if (UY_TR_TruckType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TruckType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TruckType_ID, Integer.valueOf(UY_TR_TruckType_ID));
	}

	/** Get UY_TR_TruckType.
		@return UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TruckType_ID_2.
		@param UY_TR_TruckType_ID_2 UY_TR_TruckType_ID_2	  */
	public void setUY_TR_TruckType_ID_2 (int UY_TR_TruckType_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_TruckType_ID_2, Integer.valueOf(UY_TR_TruckType_ID_2));
	}

	/** Get UY_TR_TruckType_ID_2.
		@return UY_TR_TruckType_ID_2	  */
	public int getUY_TR_TruckType_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}