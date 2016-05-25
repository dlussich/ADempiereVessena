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

/** Generated Model for UY_TR_Trip
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Trip extends PO implements I_UY_TR_Trip, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150526L;

    /** Standard Constructor */
    public X_UY_TR_Trip (Properties ctx, int UY_TR_Trip_ID, String trxName)
    {
      super (ctx, UY_TR_Trip_ID, trxName);
      /** if (UY_TR_Trip_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// AY
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setFrame1 (false);
// N
			setFrame2 (false);
// N
			setFrame3 (false);
// N
			setFrame4 (false);
// N
			setFrame5 (false);
// N
			setFrame6 (false);
// N
			setInTransit (false);
// N
			setIsConsolidated (false);
// N
			setIsDangerous (false);
// N
			setIsLoad (false);
// N
			setIsRepresentation (false);
// N
			setProcessed (false);
			setSatisfied (false);
			setTripType (null);
// IMPORTACION
			setUY_TR_Trip_ID (0);
			setUY_TR_Way_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Trip (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Trip[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set C_BPartner_ID_Aux.
		@param C_BPartner_ID_Aux C_BPartner_ID_Aux	  */
	public void setC_BPartner_ID_Aux (int C_BPartner_ID_Aux)
	{
		set_Value (COLUMNNAME_C_BPartner_ID_Aux, Integer.valueOf(C_BPartner_ID_Aux));
	}

	/** Get C_BPartner_ID_Aux.
		@return C_BPartner_ID_Aux	  */
	public int getC_BPartner_ID_Aux () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID_Aux);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BPartner_ID_From.
		@param C_BPartner_ID_From C_BPartner_ID_From	  */
	public void setC_BPartner_ID_From (int C_BPartner_ID_From)
	{
		set_Value (COLUMNNAME_C_BPartner_ID_From, Integer.valueOf(C_BPartner_ID_From));
	}

	/** Get C_BPartner_ID_From.
		@return C_BPartner_ID_From	  */
	public int getC_BPartner_ID_From () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID_From);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BPartner_ID_To.
		@param C_BPartner_ID_To C_BPartner_ID_To	  */
	public void setC_BPartner_ID_To (int C_BPartner_ID_To)
	{
		set_Value (COLUMNNAME_C_BPartner_ID_To, Integer.valueOf(C_BPartner_ID_To));
	}

	/** Get C_BPartner_ID_To.
		@return C_BPartner_ID_To	  */
	public int getC_BPartner_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BPartner_Location_ID_1.
		@param C_BPartner_Location_ID_1 C_BPartner_Location_ID_1	  */
	public void setC_BPartner_Location_ID_1 (int C_BPartner_Location_ID_1)
	{
		set_Value (COLUMNNAME_C_BPartner_Location_ID_1, Integer.valueOf(C_BPartner_Location_ID_1));
	}

	/** Get C_BPartner_Location_ID_1.
		@return C_BPartner_Location_ID_1	  */
	public int getC_BPartner_Location_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BPartner_Location_ID_2.
		@param C_BPartner_Location_ID_2 C_BPartner_Location_ID_2	  */
	public void setC_BPartner_Location_ID_2 (int C_BPartner_Location_ID_2)
	{
		set_Value (COLUMNNAME_C_BPartner_Location_ID_2, Integer.valueOf(C_BPartner_Location_ID_2));
	}

	/** Get C_BPartner_Location_ID_2.
		@return C_BPartner_Location_ID_2	  */
	public int getC_BPartner_Location_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BPartner_Location_ID_3.
		@param C_BPartner_Location_ID_3 C_BPartner_Location_ID_3	  */
	public void setC_BPartner_Location_ID_3 (int C_BPartner_Location_ID_3)
	{
		set_Value (COLUMNNAME_C_BPartner_Location_ID_3, Integer.valueOf(C_BPartner_Location_ID_3));
	}

	/** Get C_BPartner_Location_ID_3.
		@return C_BPartner_Location_ID_3	  */
	public int getC_BPartner_Location_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID_3);
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

	/** Set DateDUA.
		@param DateDUA DateDUA	  */
	public void setDateDUA (Timestamp DateDUA)
	{
		set_Value (COLUMNNAME_DateDUA, DateDUA);
	}

	/** Get DateDUA.
		@return DateDUA	  */
	public Timestamp getDateDUA () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDUA);
	}

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set DateShipment.
		@param DateShipment DateShipment	  */
	public void setDateShipment (Timestamp DateShipment)
	{
		set_Value (COLUMNNAME_DateShipment, DateShipment);
	}

	/** Get DateShipment.
		@return DateShipment	  */
	public Timestamp getDateShipment () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateShipment);
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

	/** Set DateUnload.
		@param DateUnload DateUnload	  */
	public void setDateUnload (Timestamp DateUnload)
	{
		set_Value (COLUMNNAME_DateUnload, DateUnload);
	}

	/** Get DateUnload.
		@return DateUnload	  */
	public Timestamp getDateUnload () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateUnload);
	}

	/** DeclarationType AD_Reference_ID=1000460 */
	public static final int DECLARATIONTYPE_AD_Reference_ID=1000460;
	/** DDE = DDE */
	public static final String DECLARATIONTYPE_DDE = "DDE";
	/** DSE = DSE */
	public static final String DECLARATIONTYPE_DSE = "DSE";
	/** Set DeclarationType.
		@param DeclarationType DeclarationType	  */
	public void setDeclarationType (String DeclarationType)
	{

		set_Value (COLUMNNAME_DeclarationType, DeclarationType);
	}

	/** Get DeclarationType.
		@return DeclarationType	  */
	public String getDeclarationType () 
	{
		return (String)get_Value(COLUMNNAME_DeclarationType);
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

	/** Set Despachante.
		@param Despachante Despachante	  */
	public void setDespachante (String Despachante)
	{
		set_Value (COLUMNNAME_Despachante, Despachante);
	}

	/** Get Despachante.
		@return Despachante	  */
	public String getDespachante () 
	{
		return (String)get_Value(COLUMNNAME_Despachante);
	}

	/** Set Despachante2.
		@param Despachante2 Despachante2	  */
	public void setDespachante2 (String Despachante2)
	{
		set_Value (COLUMNNAME_Despachante2, Despachante2);
	}

	/** Get Despachante2.
		@return Despachante2	  */
	public String getDespachante2 () 
	{
		return (String)get_Value(COLUMNNAME_Despachante2);
	}

	/** Set Destino.
		@param Destino Destino	  */
	public void setDestino (String Destino)
	{
		set_Value (COLUMNNAME_Destino, Destino);
	}

	/** Get Destino.
		@return Destino	  */
	public String getDestino () 
	{
		return (String)get_Value(COLUMNNAME_Destino);
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

	/** Set DuaNo.
		@param DuaNo DuaNo	  */
	public void setDuaNo (String DuaNo)
	{
		set_Value (COLUMNNAME_DuaNo, DuaNo);
	}

	/** Get DuaNo.
		@return DuaNo	  */
	public String getDuaNo () 
	{
		return (String)get_Value(COLUMNNAME_DuaNo);
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

	/** Set Frame1.
		@param Frame1 Frame1	  */
	public void setFrame1 (boolean Frame1)
	{
		set_Value (COLUMNNAME_Frame1, Boolean.valueOf(Frame1));
	}

	/** Get Frame1.
		@return Frame1	  */
	public boolean isFrame1 () 
	{
		Object oo = get_Value(COLUMNNAME_Frame1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Frame2.
		@param Frame2 Frame2	  */
	public void setFrame2 (boolean Frame2)
	{
		set_Value (COLUMNNAME_Frame2, Boolean.valueOf(Frame2));
	}

	/** Get Frame2.
		@return Frame2	  */
	public boolean isFrame2 () 
	{
		Object oo = get_Value(COLUMNNAME_Frame2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Frame3.
		@param Frame3 Frame3	  */
	public void setFrame3 (boolean Frame3)
	{
		set_Value (COLUMNNAME_Frame3, Boolean.valueOf(Frame3));
	}

	/** Get Frame3.
		@return Frame3	  */
	public boolean isFrame3 () 
	{
		Object oo = get_Value(COLUMNNAME_Frame3);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Frame4.
		@param Frame4 Frame4	  */
	public void setFrame4 (boolean Frame4)
	{
		set_Value (COLUMNNAME_Frame4, Boolean.valueOf(Frame4));
	}

	/** Get Frame4.
		@return Frame4	  */
	public boolean isFrame4 () 
	{
		Object oo = get_Value(COLUMNNAME_Frame4);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Frame5.
		@param Frame5 Frame5	  */
	public void setFrame5 (boolean Frame5)
	{
		set_Value (COLUMNNAME_Frame5, Boolean.valueOf(Frame5));
	}

	/** Get Frame5.
		@return Frame5	  */
	public boolean isFrame5 () 
	{
		Object oo = get_Value(COLUMNNAME_Frame5);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Frame6.
		@param Frame6 Frame6	  */
	public void setFrame6 (boolean Frame6)
	{
		set_Value (COLUMNNAME_Frame6, Boolean.valueOf(Frame6));
	}

	/** Get Frame6.
		@return Frame6	  */
	public boolean isFrame6 () 
	{
		Object oo = get_Value(COLUMNNAME_Frame6);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Identificator.
		@param Identificator 
		Identificator
	  */
	public void setIdentificator (String Identificator)
	{
		throw new IllegalArgumentException ("Identificator is virtual column");	}

	/** Get Identificator.
		@return Identificator
	  */
	public String getIdentificator () 
	{
		return (String)get_Value(COLUMNNAME_Identificator);
	}

	/** IncotermType AD_Reference_ID=1000402 */
	public static final int INCOTERMTYPE_AD_Reference_ID=1000402;
	/** FOB = FOB */
	public static final String INCOTERMTYPE_FOB = "FOB";
	/** EXW = EXW */
	public static final String INCOTERMTYPE_EXW = "EXW";
	/** CIF = CIF */
	public static final String INCOTERMTYPE_CIF = "CIF";
	/** FAS = FAS */
	public static final String INCOTERMTYPE_FAS = "FAS";
	/** FCA = FCA */
	public static final String INCOTERMTYPE_FCA = "FCA";
	/** CFR = CFR */
	public static final String INCOTERMTYPE_CFR = "CFR";
	/** CPT = CPT */
	public static final String INCOTERMTYPE_CPT = "CPT";
	/** CIP = CIP */
	public static final String INCOTERMTYPE_CIP = "CIP";
	/** DAT = DAT */
	public static final String INCOTERMTYPE_DAT = "DAT";
	/** DAP = DAP */
	public static final String INCOTERMTYPE_DAP = "DAP";
	/** DDP = DDP */
	public static final String INCOTERMTYPE_DDP = "DDP";
	/** Set IncotermType.
		@param IncotermType IncotermType	  */
	public void setIncotermType (String IncotermType)
	{

		set_Value (COLUMNNAME_IncotermType, IncotermType);
	}

	/** Get IncotermType.
		@return IncotermType	  */
	public String getIncotermType () 
	{
		return (String)get_Value(COLUMNNAME_IncotermType);
	}

	/** Set InTransit.
		@param InTransit InTransit	  */
	public void setInTransit (boolean InTransit)
	{
		set_Value (COLUMNNAME_InTransit, Boolean.valueOf(InTransit));
	}

	/** Get InTransit.
		@return InTransit	  */
	public boolean isInTransit () 
	{
		Object oo = get_Value(COLUMNNAME_InTransit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsConsolidated.
		@param IsConsolidated IsConsolidated	  */
	public void setIsConsolidated (boolean IsConsolidated)
	{
		set_Value (COLUMNNAME_IsConsolidated, Boolean.valueOf(IsConsolidated));
	}

	/** Get IsConsolidated.
		@return IsConsolidated	  */
	public boolean isConsolidated () 
	{
		Object oo = get_Value(COLUMNNAME_IsConsolidated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDangerous.
		@param IsDangerous IsDangerous	  */
	public void setIsDangerous (boolean IsDangerous)
	{
		set_Value (COLUMNNAME_IsDangerous, Boolean.valueOf(IsDangerous));
	}

	/** Get IsDangerous.
		@return IsDangerous	  */
	public boolean isDangerous () 
	{
		Object oo = get_Value(COLUMNNAME_IsDangerous);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsLoad.
		@param IsLoad IsLoad	  */
	public void setIsLoad (boolean IsLoad)
	{
		set_Value (COLUMNNAME_IsLoad, Boolean.valueOf(IsLoad));
	}

	/** Get IsLoad.
		@return IsLoad	  */
	public boolean isLoad () 
	{
		Object oo = get_Value(COLUMNNAME_IsLoad);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRepresentation.
		@param IsRepresentation IsRepresentation	  */
	public void setIsRepresentation (boolean IsRepresentation)
	{
		set_Value (COLUMNNAME_IsRepresentation, Boolean.valueOf(IsRepresentation));
	}

	/** Get IsRepresentation.
		@return IsRepresentation	  */
	public boolean isRepresentation () 
	{
		Object oo = get_Value(COLUMNNAME_IsRepresentation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Observaciones4.
		@param Observaciones4 Observaciones4	  */
	public void setObservaciones4 (String Observaciones4)
	{
		set_Value (COLUMNNAME_Observaciones4, Observaciones4);
	}

	/** Get Observaciones4.
		@return Observaciones4	  */
	public String getObservaciones4 () 
	{
		return (String)get_Value(COLUMNNAME_Observaciones4);
	}

	/** Set OnuNO.
		@param OnuNO OnuNO	  */
	public void setOnuNO (String OnuNO)
	{
		set_Value (COLUMNNAME_OnuNO, OnuNO);
	}

	/** Get OnuNO.
		@return OnuNO	  */
	public String getOnuNO () 
	{
		return (String)get_Value(COLUMNNAME_OnuNO);
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

	/** Set ProdSource.
		@param ProdSource ProdSource	  */
	public void setProdSource (String ProdSource)
	{
		set_Value (COLUMNNAME_ProdSource, ProdSource);
	}

	/** Get ProdSource.
		@return ProdSource	  */
	public String getProdSource () 
	{
		return (String)get_Value(COLUMNNAME_ProdSource);
	}

	/** Set ProductAmt.
		@param ProductAmt ProductAmt	  */
	public void setProductAmt (BigDecimal ProductAmt)
	{
		set_Value (COLUMNNAME_ProductAmt, ProductAmt);
	}

	/** Get ProductAmt.
		@return ProductAmt	  */
	public BigDecimal getProductAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Product Description.
		@param ProductDescription 
		Product Description
	  */
	public void setProductDescription (String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	/** Get Product Description.
		@return Product Description
	  */
	public String getProductDescription () 
	{
		return (String)get_Value(COLUMNNAME_ProductDescription);
	}

	/** Set Product Type.
		@param ProductType 
		Type of product
	  */
	public void setProductType (String ProductType)
	{
		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	/** Get Product Type.
		@return Type of product
	  */
	public String getProductType () 
	{
		return (String)get_Value(COLUMNNAME_ProductType);
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

	/** ReceiptMode AD_Reference_ID=1000401 */
	public static final int RECEIPTMODE_AD_Reference_ID=1000401;
	/** ORIGEN = ORIGEN */
	public static final String RECEIPTMODE_ORIGEN = "ORIGEN";
	/** DESTINO = DESTINO */
	public static final String RECEIPTMODE_DESTINO = "DESTINO";
	/** AMBOS = AMBOS */
	public static final String RECEIPTMODE_AMBOS = "AMBOS";
	/** Set ReceiptMode.
		@param ReceiptMode ReceiptMode	  */
	public void setReceiptMode (String ReceiptMode)
	{

		set_Value (COLUMNNAME_ReceiptMode, ReceiptMode);
	}

	/** Get ReceiptMode.
		@return ReceiptMode	  */
	public String getReceiptMode () 
	{
		return (String)get_Value(COLUMNNAME_ReceiptMode);
	}

	/** Set Reference No.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
	}

	/** Set Representado_ID.
		@param Representado_ID Representado_ID	  */
	public void setRepresentado_ID (int Representado_ID)
	{
		if (Representado_ID < 1) 
			set_Value (COLUMNNAME_Representado_ID, null);
		else 
			set_Value (COLUMNNAME_Representado_ID, Integer.valueOf(Representado_ID));
	}

	/** Get Representado_ID.
		@return Representado_ID	  */
	public int getRepresentado_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Representado_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Representante.
		@param Representante Representante	  */
	public void setRepresentante (String Representante)
	{
		set_Value (COLUMNNAME_Representante, Representante);
	}

	/** Get Representante.
		@return Representante	  */
	public String getRepresentante () 
	{
		return (String)get_Value(COLUMNNAME_Representante);
	}

	/** Set Satisfied.
		@param Satisfied Satisfied	  */
	public void setSatisfied (boolean Satisfied)
	{
		set_Value (COLUMNNAME_Satisfied, Boolean.valueOf(Satisfied));
	}

	/** Get Satisfied.
		@return Satisfied	  */
	public boolean isSatisfied () 
	{
		Object oo = get_Value(COLUMNNAME_Satisfied);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** TransitType AD_Reference_ID=1000399 */
	public static final int TRANSITTYPE_AD_Reference_ID=1000399;
	/** TOTAL = TOTAL */
	public static final String TRANSITTYPE_TOTAL = "TOTAL";
	/** PARCIAL = PARCIAL */
	public static final String TRANSITTYPE_PARCIAL = "PARCIAL";
	/** Set TransitType.
		@param TransitType TransitType	  */
	public void setTransitType (String TransitType)
	{

		set_Value (COLUMNNAME_TransitType, TransitType);
	}

	/** Get TransitType.
		@return TransitType	  */
	public String getTransitType () 
	{
		return (String)get_Value(COLUMNNAME_TransitType);
	}

	/** TripType AD_Reference_ID=1000398 */
	public static final int TRIPTYPE_AD_Reference_ID=1000398;
	/** EXPORTACION = EXPORTACION */
	public static final String TRIPTYPE_EXPORTACION = "EXPORTACION";
	/** IMPORTACION = IMPORTACION */
	public static final String TRIPTYPE_IMPORTACION = "IMPORTACION";
	/** NACIONAL = NACIONAL */
	public static final String TRIPTYPE_NACIONAL = "NACIONAL";
	/** Set TripType.
		@param TripType TripType	  */
	public void setTripType (String TripType)
	{

		set_Value (COLUMNNAME_TripType, TripType);
	}

	/** Get TripType.
		@return TripType	  */
	public String getTripType () 
	{
		return (String)get_Value(COLUMNNAME_TripType);
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

	public I_UY_TR_Border getUY_TR_Border() throws RuntimeException
    {
		return (I_UY_TR_Border)MTable.get(getCtx(), I_UY_TR_Border.Table_Name)
			.getPO(getUY_TR_Border_ID(), get_TrxName());	}

	/** Set UY_TR_Border.
		@param UY_TR_Border_ID UY_TR_Border	  */
	public void setUY_TR_Border_ID (int UY_TR_Border_ID)
	{
		if (UY_TR_Border_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Border_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Border_ID, Integer.valueOf(UY_TR_Border_ID));
	}

	/** Get UY_TR_Border.
		@return UY_TR_Border	  */
	public int getUY_TR_Border_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Border_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Border getUY_TR_Border_I() throws RuntimeException
    {
		return (I_UY_TR_Border)MTable.get(getCtx(), I_UY_TR_Border.Table_Name)
			.getPO(getUY_TR_Border_ID_1(), get_TrxName());	}

	/** Set UY_TR_Border_ID_1.
		@param UY_TR_Border_ID_1 
		UY_TR_Border_ID_1
	  */
	public void setUY_TR_Border_ID_1 (int UY_TR_Border_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Border_ID_1, Integer.valueOf(UY_TR_Border_ID_1));
	}

	/** Get UY_TR_Border_ID_1.
		@return UY_TR_Border_ID_1
	  */
	public int getUY_TR_Border_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Border_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Budget getUY_TR_Budget() throws RuntimeException
    {
		return (I_UY_TR_Budget)MTable.get(getCtx(), I_UY_TR_Budget.Table_Name)
			.getPO(getUY_TR_Budget_ID(), get_TrxName());	}

	/** Set UY_TR_Budget.
		@param UY_TR_Budget_ID UY_TR_Budget	  */
	public void setUY_TR_Budget_ID (int UY_TR_Budget_ID)
	{
		if (UY_TR_Budget_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Budget_ID, Integer.valueOf(UY_TR_Budget_ID));
	}

	/** Get UY_TR_Budget.
		@return UY_TR_Budget	  */
	public int getUY_TR_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_BudgetLine.
		@param UY_TR_BudgetLine_ID UY_TR_BudgetLine	  */
	public void setUY_TR_BudgetLine_ID (int UY_TR_BudgetLine_ID)
	{
		if (UY_TR_BudgetLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_BudgetLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_BudgetLine_ID, Integer.valueOf(UY_TR_BudgetLine_ID));
	}

	/** Get UY_TR_BudgetLine.
		@return UY_TR_BudgetLine	  */
	public int getUY_TR_BudgetLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_BudgetLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Despachante getUY_TR_Despachante() throws RuntimeException
    {
		return (I_UY_TR_Despachante)MTable.get(getCtx(), I_UY_TR_Despachante.Table_Name)
			.getPO(getUY_TR_Despachante_ID(), get_TrxName());	}

	/** Set UY_TR_Despachante.
		@param UY_TR_Despachante_ID UY_TR_Despachante	  */
	public void setUY_TR_Despachante_ID (int UY_TR_Despachante_ID)
	{
		if (UY_TR_Despachante_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Despachante_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Despachante_ID, Integer.valueOf(UY_TR_Despachante_ID));
	}

	/** Get UY_TR_Despachante.
		@return UY_TR_Despachante	  */
	public int getUY_TR_Despachante_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Despachante_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Despachante_ID_1.
		@param UY_TR_Despachante_ID_1 UY_TR_Despachante_ID_1	  */
	public void setUY_TR_Despachante_ID_1 (int UY_TR_Despachante_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Despachante_ID_1, Integer.valueOf(UY_TR_Despachante_ID_1));
	}

	/** Get UY_TR_Despachante_ID_1.
		@return UY_TR_Despachante_ID_1	  */
	public int getUY_TR_Despachante_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Despachante_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Despachante getUY_TR_Despachante_I() throws RuntimeException
    {
		return (I_UY_TR_Despachante)MTable.get(getCtx(), I_UY_TR_Despachante.Table_Name)
			.getPO(getUY_TR_Despachante_ID_2(), get_TrxName());	}

	/** Set UY_TR_Despachante_ID_2.
		@param UY_TR_Despachante_ID_2 UY_TR_Despachante_ID_2	  */
	public void setUY_TR_Despachante_ID_2 (int UY_TR_Despachante_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_Despachante_ID_2, Integer.valueOf(UY_TR_Despachante_ID_2));
	}

	/** Get UY_TR_Despachante_ID_2.
		@return UY_TR_Despachante_ID_2	  */
	public int getUY_TR_Despachante_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Despachante_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Despachante_ID_3.
		@param UY_TR_Despachante_ID_3 UY_TR_Despachante_ID_3	  */
	public void setUY_TR_Despachante_ID_3 (int UY_TR_Despachante_ID_3)
	{
		set_Value (COLUMNNAME_UY_TR_Despachante_ID_3, Integer.valueOf(UY_TR_Despachante_ID_3));
	}

	/** Get UY_TR_Despachante_ID_3.
		@return UY_TR_Despachante_ID_3	  */
	public int getUY_TR_Despachante_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Despachante_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException
    {
		return (I_UY_TR_PackageType)MTable.get(getCtx(), I_UY_TR_PackageType.Table_Name)
			.getPO(getUY_TR_PackageType_ID(), get_TrxName());	}

	/** Set UY_TR_PackageType.
		@param UY_TR_PackageType_ID UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID)
	{
		if (UY_TR_PackageType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_PackageType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_PackageType_ID, Integer.valueOf(UY_TR_PackageType_ID));
	}

	/** Get UY_TR_PackageType.
		@return UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_PackageType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Representante.
		@param UY_TR_Representante_ID UY_TR_Representante	  */
	public void setUY_TR_Representante_ID (int UY_TR_Representante_ID)
	{
		if (UY_TR_Representante_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Representante_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Representante_ID, Integer.valueOf(UY_TR_Representante_ID));
	}

	/** Get UY_TR_Representante.
		@return UY_TR_Representante	  */
	public int getUY_TR_Representante_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Representante_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Representante_ID_1.
		@param UY_TR_Representante_ID_1 UY_TR_Representante_ID_1	  */
	public void setUY_TR_Representante_ID_1 (int UY_TR_Representante_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Representante_ID_1, Integer.valueOf(UY_TR_Representante_ID_1));
	}

	/** Get UY_TR_Representante_ID_1.
		@return UY_TR_Representante_ID_1	  */
	public int getUY_TR_Representante_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Representante_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Trip.
		@param UY_TR_Trip_ID UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID)
	{
		if (UY_TR_Trip_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Trip_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Trip_ID, Integer.valueOf(UY_TR_Trip_ID));
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

	public I_UY_TR_Way getUY_TR_Way() throws RuntimeException
    {
		return (I_UY_TR_Way)MTable.get(getCtx(), I_UY_TR_Way.Table_Name)
			.getPO(getUY_TR_Way_ID(), get_TrxName());	}

	/** Set UY_TR_Way.
		@param UY_TR_Way_ID UY_TR_Way	  */
	public void setUY_TR_Way_ID (int UY_TR_Way_ID)
	{
		if (UY_TR_Way_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Way_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Way_ID, Integer.valueOf(UY_TR_Way_ID));
	}

	/** Get UY_TR_Way.
		@return UY_TR_Way	  */
	public int getUY_TR_Way_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Way_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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