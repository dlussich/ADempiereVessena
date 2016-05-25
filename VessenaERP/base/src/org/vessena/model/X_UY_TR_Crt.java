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

/** Generated Model for UY_TR_Crt
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Crt extends PO implements I_UY_TR_Crt, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150514L;

    /** Standard Constructor */
    public X_UY_TR_Crt (Properties ctx, int UY_TR_Crt_ID, String trxName)
    {
      super (ctx, UY_TR_Crt_ID, trxName);
      /** if (UY_TR_Crt_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency2_ID (0);
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// AY
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsOldSequence (false);
// N
			setProcessed (false);
			setUY_TR_Crt_ID (0);
			setUY_TR_Trip_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Crt (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Crt[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amt1.
		@param amt1 amt1	  */
	public void setamt1 (BigDecimal amt1)
	{
		set_Value (COLUMNNAME_amt1, amt1);
	}

	/** Get amt1.
		@return amt1	  */
	public BigDecimal getamt1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt10.
		@param amt10 amt10	  */
	public void setamt10 (BigDecimal amt10)
	{
		set_Value (COLUMNNAME_amt10, amt10);
	}

	/** Get amt10.
		@return amt10	  */
	public BigDecimal getamt10 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt10);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt2.
		@param amt2 amt2	  */
	public void setamt2 (BigDecimal amt2)
	{
		set_Value (COLUMNNAME_amt2, amt2);
	}

	/** Get amt2.
		@return amt2	  */
	public BigDecimal getamt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt3.
		@param amt3 amt3	  */
	public void setamt3 (BigDecimal amt3)
	{
		set_Value (COLUMNNAME_amt3, amt3);
	}

	/** Get amt3.
		@return amt3	  */
	public BigDecimal getamt3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt4.
		@param amt4 amt4	  */
	public void setamt4 (BigDecimal amt4)
	{
		set_Value (COLUMNNAME_amt4, amt4);
	}

	/** Get amt4.
		@return amt4	  */
	public BigDecimal getamt4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt5.
		@param amt5 amt5	  */
	public void setamt5 (BigDecimal amt5)
	{
		set_Value (COLUMNNAME_amt5, amt5);
	}

	/** Get amt5.
		@return amt5	  */
	public BigDecimal getamt5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt6.
		@param amt6 amt6	  */
	public void setamt6 (BigDecimal amt6)
	{
		set_Value (COLUMNNAME_amt6, amt6);
	}

	/** Get amt6.
		@return amt6	  */
	public BigDecimal getamt6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt7.
		@param amt7 amt7	  */
	public void setamt7 (BigDecimal amt7)
	{
		set_Value (COLUMNNAME_amt7, amt7);
	}

	/** Get amt7.
		@return amt7	  */
	public BigDecimal getamt7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt8.
		@param amt8 amt8	  */
	public void setamt8 (BigDecimal amt8)
	{
		set_Value (COLUMNNAME_amt8, amt8);
	}

	/** Get amt8.
		@return amt8	  */
	public BigDecimal getamt8 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt8);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt9.
		@param amt9 amt9	  */
	public void setamt9 (BigDecimal amt9)
	{
		set_Value (COLUMNNAME_amt9, amt9);
	}

	/** Get amt9.
		@return amt9	  */
	public BigDecimal getamt9 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt9);
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

	public org.compiere.model.I_C_Currency getC_Currency2() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency2_ID(), get_TrxName());	}

	/** Set C_Currency2_ID.
		@param C_Currency2_ID C_Currency2_ID	  */
	public void setC_Currency2_ID (int C_Currency2_ID)
	{
		if (C_Currency2_ID < 1) 
			set_Value (COLUMNNAME_C_Currency2_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency2_ID, Integer.valueOf(C_Currency2_ID));
	}

	/** Get C_Currency2_ID.
		@return C_Currency2_ID	  */
	public int getC_Currency2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency2_ID);
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

	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_Location)MTable.get(getCtx(), org.compiere.model.I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigo.
		@param codigo codigo	  */
	public void setcodigo (String codigo)
	{
		set_Value (COLUMNNAME_codigo, codigo);
	}

	/** Get codigo.
		@return codigo	  */
	public String getcodigo () 
	{
		return (String)get_Value(COLUMNNAME_codigo);
	}

	/** Set Consignatario.
		@param Consignatario Consignatario	  */
	public void setConsignatario (String Consignatario)
	{
		set_Value (COLUMNNAME_Consignatario, Consignatario);
	}

	/** Get Consignatario.
		@return Consignatario	  */
	public String getConsignatario () 
	{
		return (String)get_Value(COLUMNNAME_Consignatario);
	}

	/** Set DateLast.
		@param DateLast DateLast	  */
	public void setDateLast (Timestamp DateLast)
	{
		set_Value (COLUMNNAME_DateLast, DateLast);
	}

	/** Get DateLast.
		@return DateLast	  */
	public Timestamp getDateLast () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLast);
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

	/** Set Delivery Rule.
		@param DeliveryRule 
		Defines the timing of Delivery
	  */
	public void setDeliveryRule (String DeliveryRule)
	{
		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	/** Get Delivery Rule.
		@return Defines the timing of Delivery
	  */
	public String getDeliveryRule () 
	{
		return (String)get_Value(COLUMNNAME_DeliveryRule);
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

	/** Set Destinatario.
		@param Destinatario Destinatario	  */
	public void setDestinatario (String Destinatario)
	{
		set_Value (COLUMNNAME_Destinatario, Destinatario);
	}

	/** Get Destinatario.
		@return Destinatario	  */
	public String getDestinatario () 
	{
		return (String)get_Value(COLUMNNAME_Destinatario);
	}

	/** Set Destinatario2.
		@param Destinatario2 Destinatario2	  */
	public void setDestinatario2 (String Destinatario2)
	{
		set_Value (COLUMNNAME_Destinatario2, Destinatario2);
	}

	/** Get Destinatario2.
		@return Destinatario2	  */
	public String getDestinatario2 () 
	{
		return (String)get_Value(COLUMNNAME_Destinatario2);
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

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set ExecuteAction2.
		@param ExecuteAction2 ExecuteAction2	  */
	public void setExecuteAction2 (String ExecuteAction2)
	{
		set_Value (COLUMNNAME_ExecuteAction2, ExecuteAction2);
	}

	/** Get ExecuteAction2.
		@return ExecuteAction2	  */
	public String getExecuteAction2 () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction2);
	}

	/** Set ExecuteAction3.
		@param ExecuteAction3 ExecuteAction3	  */
	public void setExecuteAction3 (String ExecuteAction3)
	{
		set_Value (COLUMNNAME_ExecuteAction3, ExecuteAction3);
	}

	/** Get ExecuteAction3.
		@return ExecuteAction3	  */
	public String getExecuteAction3 () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction3);
	}

	/** Set FleteLiteral.
		@param FleteLiteral FleteLiteral	  */
	public void setFleteLiteral (String FleteLiteral)
	{
		set_Value (COLUMNNAME_FleteLiteral, FleteLiteral);
	}

	/** Get FleteLiteral.
		@return FleteLiteral	  */
	public String getFleteLiteral () 
	{
		return (String)get_Value(COLUMNNAME_FleteLiteral);
	}

	/** Set Importe.
		@param Importe Importe	  */
	public void setImporte (String Importe)
	{
		set_Value (COLUMNNAME_Importe, Importe);
	}

	/** Get Importe.
		@return Importe	  */
	public String getImporte () 
	{
		return (String)get_Value(COLUMNNAME_Importe);
	}

	/** Set Info.
		@param Info 
		Information
	  */
	public void setInfo (String Info)
	{
		throw new IllegalArgumentException ("Info is virtual column");	}

	/** Get Info.
		@return Information
	  */
	public String getInfo () 
	{
		return (String)get_Value(COLUMNNAME_Info);
	}

	/** Set IsOldSequence.
		@param IsOldSequence IsOldSequence	  */
	public void setIsOldSequence (boolean IsOldSequence)
	{
		set_Value (COLUMNNAME_IsOldSequence, Boolean.valueOf(IsOldSequence));
	}

	/** Get IsOldSequence.
		@return IsOldSequence	  */
	public boolean isOldSequence () 
	{
		Object oo = get_Value(COLUMNNAME_IsOldSequence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LiteralNumber.
		@param LiteralNumber LiteralNumber	  */
	public void setLiteralNumber (String LiteralNumber)
	{
		set_Value (COLUMNNAME_LiteralNumber, LiteralNumber);
	}

	/** Get LiteralNumber.
		@return LiteralNumber	  */
	public String getLiteralNumber () 
	{
		return (String)get_Value(COLUMNNAME_LiteralNumber);
	}

	/** Set Location comment.
		@param LocationComment 
		Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment)
	{
		set_Value (COLUMNNAME_LocationComment, LocationComment);
	}

	/** Get Location comment.
		@return Additional comments or remarks concerning the location
	  */
	public String getLocationComment () 
	{
		return (String)get_Value(COLUMNNAME_LocationComment);
	}

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (String LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, LocatorValue);
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public String getLocatorValue () 
	{
		return (String)get_Value(COLUMNNAME_LocatorValue);
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

	/** Set Notificar.
		@param Notificar Notificar	  */
	public void setNotificar (String Notificar)
	{
		set_Value (COLUMNNAME_Notificar, Notificar);
	}

	/** Get Notificar.
		@return Notificar	  */
	public String getNotificar () 
	{
		return (String)get_Value(COLUMNNAME_Notificar);
	}

	/** Set Numero.
		@param Numero Numero	  */
	public void setNumero (String Numero)
	{
		set_Value (COLUMNNAME_Numero, Numero);
	}

	/** Get Numero.
		@return Numero	  */
	public String getNumero () 
	{
		return (String)get_Value(COLUMNNAME_Numero);
	}

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	/** Set Observaciones2.
		@param Observaciones2 Observaciones2	  */
	public void setObservaciones2 (String Observaciones2)
	{
		set_Value (COLUMNNAME_Observaciones2, Observaciones2);
	}

	/** Get Observaciones2.
		@return Observaciones2	  */
	public String getObservaciones2 () 
	{
		return (String)get_Value(COLUMNNAME_Observaciones2);
	}

	/** Set Observaciones3.
		@param Observaciones3 Observaciones3	  */
	public void setObservaciones3 (String Observaciones3)
	{
		set_Value (COLUMNNAME_Observaciones3, Observaciones3);
	}

	/** Get Observaciones3.
		@return Observaciones3	  */
	public String getObservaciones3 () 
	{
		return (String)get_Value(COLUMNNAME_Observaciones3);
	}

	/** Set otros.
		@param otros otros	  */
	public void setotros (String otros)
	{
		set_Value (COLUMNNAME_otros, otros);
	}

	/** Get otros.
		@return otros	  */
	public String getotros () 
	{
		return (String)get_Value(COLUMNNAME_otros);
	}

	/** Set otros2.
		@param otros2 otros2	  */
	public void setotros2 (String otros2)
	{
		set_Value (COLUMNNAME_otros2, otros2);
	}

	/** Get otros2.
		@return otros2	  */
	public String getotros2 () 
	{
		return (String)get_Value(COLUMNNAME_otros2);
	}

	/** Set otros3.
		@param otros3 otros3	  */
	public void setotros3 (String otros3)
	{
		set_Value (COLUMNNAME_otros3, otros3);
	}

	/** Get otros3.
		@return otros3	  */
	public String getotros3 () 
	{
		return (String)get_Value(COLUMNNAME_otros3);
	}

	/** Set Porteador.
		@param Porteador Porteador	  */
	public void setPorteador (String Porteador)
	{
		set_Value (COLUMNNAME_Porteador, Porteador);
	}

	/** Get Porteador.
		@return Porteador	  */
	public String getPorteador () 
	{
		return (String)get_Value(COLUMNNAME_Porteador);
	}

	/** Set Porteador2.
		@param Porteador2 Porteador2	  */
	public void setPorteador2 (String Porteador2)
	{
		set_Value (COLUMNNAME_Porteador2, Porteador2);
	}

	/** Get Porteador2.
		@return Porteador2	  */
	public String getPorteador2 () 
	{
		return (String)get_Value(COLUMNNAME_Porteador2);
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

	/** Set ProductAmt.
		@param ProductAmt ProductAmt	  */
	public void setProductAmt (String ProductAmt)
	{
		set_Value (COLUMNNAME_ProductAmt, ProductAmt);
	}

	/** Get ProductAmt.
		@return ProductAmt	  */
	public String getProductAmt () 
	{
		return (String)get_Value(COLUMNNAME_ProductAmt);
	}

	/** Set QtyPackage.
		@param QtyPackage QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage)
	{
		set_Value (COLUMNNAME_QtyPackage, QtyPackage);
	}

	/** Get QtyPackage.
		@return QtyPackage	  */
	public BigDecimal getQtyPackage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPackage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reembolso.
		@param Reembolso Reembolso	  */
	public void setReembolso (String Reembolso)
	{
		set_Value (COLUMNNAME_Reembolso, Reembolso);
	}

	/** Get Reembolso.
		@return Reembolso	  */
	public String getReembolso () 
	{
		return (String)get_Value(COLUMNNAME_Reembolso);
	}

	/** Set Remitente.
		@param Remitente Remitente	  */
	public void setRemitente (String Remitente)
	{
		set_Value (COLUMNNAME_Remitente, Remitente);
	}

	/** Get Remitente.
		@return Remitente	  */
	public String getRemitente () 
	{
		return (String)get_Value(COLUMNNAME_Remitente);
	}

	/** Set Remitente2.
		@param Remitente2 Remitente2	  */
	public void setRemitente2 (String Remitente2)
	{
		set_Value (COLUMNNAME_Remitente2, Remitente2);
	}

	/** Get Remitente2.
		@return Remitente2	  */
	public String getRemitente2 () 
	{
		return (String)get_Value(COLUMNNAME_Remitente2);
	}

	/** Set Response Text.
		@param ResponseText 
		Request Response Text
	  */
	public void setResponseText (String ResponseText)
	{
		set_Value (COLUMNNAME_ResponseText, ResponseText);
	}

	/** Get Response Text.
		@return Request Response Text
	  */
	public String getResponseText () 
	{
		return (String)get_Value(COLUMNNAME_ResponseText);
	}

	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (String Result)
	{
		set_Value (COLUMNNAME_Result, Result);
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public String getResult () 
	{
		return (String)get_Value(COLUMNNAME_Result);
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

	/** Set transporte.
		@param transporte transporte	  */
	public void settransporte (String transporte)
	{
		set_Value (COLUMNNAME_transporte, transporte);
	}

	/** Get transporte.
		@return transporte	  */
	public String gettransporte () 
	{
		return (String)get_Value(COLUMNNAME_transporte);
	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException
    {
		return (I_UY_TR_Trip)MTable.get(getCtx(), I_UY_TR_Trip.Table_Name)
			.getPO(getUY_TR_Trip_ID(), get_TrxName());	}

	/** Set UY_TR_Trip.
		@param UY_TR_Trip_ID UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID)
	{
		if (UY_TR_Trip_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, Integer.valueOf(UY_TR_Trip_ID));
	}

	/** Get UY_TR_Trip.
		@return UY_TR_Trip	  */
	public int getUY_TR_Trip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Trip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ValorFleteExt.
		@param ValorFleteExt ValorFleteExt	  */
	public void setValorFleteExt (String ValorFleteExt)
	{
		set_Value (COLUMNNAME_ValorFleteExt, ValorFleteExt);
	}

	/** Get ValorFleteExt.
		@return ValorFleteExt	  */
	public String getValorFleteExt () 
	{
		return (String)get_Value(COLUMNNAME_ValorFleteExt);
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight2.
		@param Weight2 Weight2	  */
	public void setWeight2 (BigDecimal Weight2)
	{
		set_Value (COLUMNNAME_Weight2, Weight2);
	}

	/** Get Weight2.
		@return Weight2	  */
	public BigDecimal getWeight2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}