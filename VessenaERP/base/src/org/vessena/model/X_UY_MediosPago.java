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

/** Generated Model for UY_MediosPago
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MediosPago extends PO implements I_UY_MediosPago, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151106L;

    /** Standard Constructor */
    public X_UY_MediosPago (Properties ctx, int UY_MediosPago_ID, String trxName)
    {
      super (ctx, UY_MediosPago_ID, trxName);
      /** if (UY_MediosPago_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
// 1000044
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsInitialLoad (false);
// N
			setIsSOTrx (false);
// N
			setPayAmt (Env.ZERO);
			setProcessed (false);
			settipomp (null);
// PRO
			setUY_MediosPago_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_MediosPago (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MediosPago[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Account Name.
		@param A_Name 
		Name on Credit Card or Account holder
	  */
	public void setA_Name (String A_Name)
	{
		set_Value (COLUMNNAME_A_Name, A_Name);
	}

	/** Get Account Name.
		@return Name on Credit Card or Account holder
	  */
	public String getA_Name () 
	{
		return (String)get_Value(COLUMNNAME_A_Name);
	}

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set CheckLetter.
		@param CheckLetter CheckLetter	  */
	public void setCheckLetter (String CheckLetter)
	{
		set_Value (COLUMNNAME_CheckLetter, CheckLetter);
	}

	/** Get CheckLetter.
		@return CheckLetter	  */
	public String getCheckLetter () 
	{
		return (String)get_Value(COLUMNNAME_CheckLetter);
	}

	/** Set CheckLetter2.
		@param CheckLetter2 CheckLetter2	  */
	public void setCheckLetter2 (String CheckLetter2)
	{
		set_Value (COLUMNNAME_CheckLetter2, CheckLetter2);
	}

	/** Get CheckLetter2.
		@return CheckLetter2	  */
	public String getCheckLetter2 () 
	{
		return (String)get_Value(COLUMNNAME_CheckLetter2);
	}

	/** Set Check No.
		@param CheckNo 
		Check Number
	  */
	public void setCheckNo (String CheckNo)
	{
		set_Value (COLUMNNAME_CheckNo, CheckNo);
	}

	/** Get Check No.
		@return Check Number
	  */
	public String getCheckNo () 
	{
		return (String)get_Value(COLUMNNAME_CheckNo);
	}

	/** Custodio AD_Reference_ID=1000004 */
	public static final int CUSTODIO_AD_Reference_ID=1000004;
	/** Factoing = F */
	public static final String CUSTODIO_Factoing = "F";
	/** Extinto = E */
	public static final String CUSTODIO_Extinto = "E";
	/** a tercero = T */
	public static final String CUSTODIO_ATercero = "T";
	/** Disponible = O */
	public static final String CUSTODIO_Disponible = "O";
	/** Set Custodio.
		@param Custodio Custodio	  */
	public void setCustodio (String Custodio)
	{

		set_Value (COLUMNNAME_Custodio, Custodio);
	}

	/** Get Custodio.
		@return Custodio	  */
	public String getCustodio () 
	{
		return (String)get_Value(COLUMNNAME_Custodio);
	}

	/** Set Date.
		@param Date1 
		Fecha Original del Documento 
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date.
		@return Fecha Original del Documento 
	  */
	public Timestamp getDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date1);
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
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

	/** Set Divide Rate.
		@param DivideRate 
		To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divide Rate.
		@return To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
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

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** estado AD_Reference_ID=1000011 */
	public static final int ESTADO_AD_Reference_ID=1000011;
	/** Emitido = EMI */
	public static final String ESTADO_Emitido = "EMI";
	/** Entregado = ENT */
	public static final String ESTADO_Entregado = "ENT";
	/** Rechazado = REC */
	public static final String ESTADO_Rechazado = "REC";
	/** Conciliado = CON */
	public static final String ESTADO_Conciliado = "CON";
	/** Cartera = CAR */
	public static final String ESTADO_Cartera = "CAR";
	/** Depositado = DEP */
	public static final String ESTADO_Depositado = "DEP";
	/** Transferido = TRA */
	public static final String ESTADO_Transferido = "TRA";
	/** Descontado = DES */
	public static final String ESTADO_Descontado = "DES";
	/** Pago = PAG */
	public static final String ESTADO_Pago = "PAG";
	/** Cambiado = CAM */
	public static final String ESTADO_Cambiado = "CAM";
	/** Cobrado = COB */
	public static final String ESTADO_Cobrado = "COB";
	/** Set estado.
		@param estado estado	  */
	public void setestado (String estado)
	{

		set_Value (COLUMNNAME_estado, estado);
	}

	/** Get estado.
		@return estado	  */
	public String getestado () 
	{
		return (String)get_Value(COLUMNNAME_estado);
	}

	/** Set IsInitialLoad.
		@param IsInitialLoad IsInitialLoad	  */
	public void setIsInitialLoad (boolean IsInitialLoad)
	{
		set_Value (COLUMNNAME_IsInitialLoad, Boolean.valueOf(IsInitialLoad));
	}

	/** Get IsInitialLoad.
		@return IsInitialLoad	  */
	public boolean isInitialLoad () 
	{
		Object oo = get_Value(COLUMNNAME_IsInitialLoad);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isrechazado.
		@param isrechazado isrechazado	  */
	public void setisrechazado (boolean isrechazado)
	{
		set_Value (COLUMNNAME_isrechazado, Boolean.valueOf(isrechazado));
	}

	/** Get isrechazado.
		@return isrechazado	  */
	public boolean isrechazado () 
	{
		Object oo = get_Value(COLUMNNAME_isrechazado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Micr.
		@param Micr 
		Combination of routing no, account and check no
	  */
	public void setMicr (String Micr)
	{
		set_Value (COLUMNNAME_Micr, Micr);
	}

	/** Get Micr.
		@return Combination of routing no, account and check no
	  */
	public String getMicr () 
	{
		return (String)get_Value(COLUMNNAME_Micr);
	}

	/** Set nobanco.
		@param nobanco nobanco	  */
	public void setnobanco (String nobanco)
	{
		set_Value (COLUMNNAME_nobanco, nobanco);
	}

	/** Get nobanco.
		@return nobanco	  */
	public String getnobanco () 
	{
		return (String)get_Value(COLUMNNAME_nobanco);
	}

	/** Set oldstatus.
		@param oldstatus oldstatus	  */
	public void setoldstatus (String oldstatus)
	{
		set_Value (COLUMNNAME_oldstatus, oldstatus);
	}

	/** Get oldstatus.
		@return oldstatus	  */
	public String getoldstatus () 
	{
		return (String)get_Value(COLUMNNAME_oldstatus);
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

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Routing No.
		@param RoutingNo 
		Bank Routing Number
	  */
	public void setRoutingNo (String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	/** Get Routing No.
		@return Bank Routing Number
	  */
	public String getRoutingNo () 
	{
		return (String)get_Value(COLUMNNAME_RoutingNo);
	}

	/** Set serie.
		@param serie serie	  */
	public void setserie (String serie)
	{
		set_Value (COLUMNNAME_serie, serie);
	}

	/** Get serie.
		@return serie	  */
	public String getserie () 
	{
		return (String)get_Value(COLUMNNAME_serie);
	}

	/** TenderType AD_Reference_ID=214 */
	public static final int TENDERTYPE_AD_Reference_ID=214;
	/** Credit Card = C */
	public static final String TENDERTYPE_CreditCard = "C";
	/** Check = K */
	public static final String TENDERTYPE_Check = "K";
	/** Direct Deposit = A */
	public static final String TENDERTYPE_DirectDeposit = "A";
	/** Direct Debit = D */
	public static final String TENDERTYPE_DirectDebit = "D";
	/** Account = T */
	public static final String TENDERTYPE_Account = "T";
	/** Cash = X */
	public static final String TENDERTYPE_Cash = "X";
	/** Retencion OTT = O */
	public static final String TENDERTYPE_RetencionOTT = "O";
	/** Canje = J */
	public static final String TENDERTYPE_Canje = "J";
	/** Conforme = F */
	public static final String TENDERTYPE_Conforme = "F";
	/** Tickets Alim. = L */
	public static final String TENDERTYPE_TicketsAlim = "L";
	/** Fideicomiso = I */
	public static final String TENDERTYPE_Fideicomiso = "I";
	/** Set Tender type.
		@param TenderType 
		Method of Payment
	  */
	public void setTenderType (String TenderType)
	{

		set_Value (COLUMNNAME_TenderType, TenderType);
	}

	/** Get Tender type.
		@return Method of Payment
	  */
	public String getTenderType () 
	{
		return (String)get_Value(COLUMNNAME_TenderType);
	}

	/** tipomp AD_Reference_ID=1000012 */
	public static final int TIPOMP_AD_Reference_ID=1000012;
	/** Propio = PRO */
	public static final String TIPOMP_Propio = "PRO";
	/** Terceros = TER */
	public static final String TIPOMP_Terceros = "TER";
	/** Set tipomp.
		@param tipomp tipomp	  */
	public void settipomp (String tipomp)
	{

		set_Value (COLUMNNAME_tipomp, tipomp);
	}

	/** Get tipomp.
		@return tipomp	  */
	public String gettipomp () 
	{
		return (String)get_Value(COLUMNNAME_tipomp);
	}

	public I_UY_CheckReam getUY_CheckReam() throws RuntimeException
    {
		return (I_UY_CheckReam)MTable.get(getCtx(), I_UY_CheckReam.Table_Name)
			.getPO(getUY_CheckReam_ID(), get_TrxName());	}

	/** Set UY_CheckReam.
		@param UY_CheckReam_ID UY_CheckReam	  */
	public void setUY_CheckReam_ID (int UY_CheckReam_ID)
	{
		if (UY_CheckReam_ID < 1) 
			set_Value (COLUMNNAME_UY_CheckReam_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CheckReam_ID, Integer.valueOf(UY_CheckReam_ID));
	}

	/** Get UY_CheckReam.
		@return UY_CheckReam	  */
	public int getUY_CheckReam_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckReam_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CheckReamLine getUY_CheckReamLine() throws RuntimeException
    {
		return (I_UY_CheckReamLine)MTable.get(getCtx(), I_UY_CheckReamLine.Table_Name)
			.getPO(getUY_CheckReamLine_ID(), get_TrxName());	}

	/** Set UY_CheckReamLine.
		@param UY_CheckReamLine_ID UY_CheckReamLine	  */
	public void setUY_CheckReamLine_ID (int UY_CheckReamLine_ID)
	{
		if (UY_CheckReamLine_ID < 1) 
			set_Value (COLUMNNAME_UY_CheckReamLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CheckReamLine_ID, Integer.valueOf(UY_CheckReamLine_ID));
	}

	/** Get UY_CheckReamLine.
		@return UY_CheckReamLine	  */
	public int getUY_CheckReamLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckReamLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DevolucionChq.
		@param UY_DevolucionChq_ID UY_DevolucionChq	  */
	public void setUY_DevolucionChq_ID (int UY_DevolucionChq_ID)
	{
		if (UY_DevolucionChq_ID < 1) 
			set_Value (COLUMNNAME_UY_DevolucionChq_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DevolucionChq_ID, Integer.valueOf(UY_DevolucionChq_ID));
	}

	/** Get UY_DevolucionChq.
		@return UY_DevolucionChq	  */
	public int getUY_DevolucionChq_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DevolucionChq_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_InvoiceCashPayment getUY_InvoiceCashPayment() throws RuntimeException
    {
		return (I_UY_InvoiceCashPayment)MTable.get(getCtx(), I_UY_InvoiceCashPayment.Table_Name)
			.getPO(getUY_InvoiceCashPayment_ID(), get_TrxName());	}

	/** Set UY_InvoiceCashPayment.
		@param UY_InvoiceCashPayment_ID UY_InvoiceCashPayment	  */
	public void setUY_InvoiceCashPayment_ID (int UY_InvoiceCashPayment_ID)
	{
		if (UY_InvoiceCashPayment_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceCashPayment_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceCashPayment_ID, Integer.valueOf(UY_InvoiceCashPayment_ID));
	}

	/** Get UY_InvoiceCashPayment.
		@return UY_InvoiceCashPayment	  */
	public int getUY_InvoiceCashPayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceCashPayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_isreemplazo.
		@param uy_isreemplazo uy_isreemplazo	  */
	public void setuy_isreemplazo (boolean uy_isreemplazo)
	{
		set_Value (COLUMNNAME_uy_isreemplazo, Boolean.valueOf(uy_isreemplazo));
	}

	/** Get uy_isreemplazo.
		@return uy_isreemplazo	  */
	public boolean isuy_isreemplazo () 
	{
		Object oo = get_Value(COLUMNNAME_uy_isreemplazo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_LinePayment getUY_LinePayment() throws RuntimeException
    {
		return (I_UY_LinePayment)MTable.get(getCtx(), I_UY_LinePayment.Table_Name)
			.getPO(getUY_LinePayment_ID(), get_TrxName());	}

	/** Set UY_LinePayment.
		@param UY_LinePayment_ID UY_LinePayment	  */
	public void setUY_LinePayment_ID (int UY_LinePayment_ID)
	{
		if (UY_LinePayment_ID < 1) 
			set_Value (COLUMNNAME_UY_LinePayment_ID, null);
		else 
			set_Value (COLUMNNAME_UY_LinePayment_ID, Integer.valueOf(UY_LinePayment_ID));
	}

	/** Get UY_LinePayment.
		@return UY_LinePayment	  */
	public int getUY_LinePayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LinePayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Medios de Pago.
		@param UY_MediosPago_ID Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID)
	{
		if (UY_MediosPago_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MediosPago_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MediosPago_ID, Integer.valueOf(UY_MediosPago_ID));
	}

	/** Get Medios de Pago.
		@return Medios de Pago	  */
	public int getUY_MediosPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MediosPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MovBancariosHdr getUY_MovBancariosHdr() throws RuntimeException
    {
		return (I_UY_MovBancariosHdr)MTable.get(getCtx(), I_UY_MovBancariosHdr.Table_Name)
			.getPO(getUY_MovBancariosHdr_ID(), get_TrxName());	}

	/** Set UY_MovBancariosHdr_ID.
		@param UY_MovBancariosHdr_ID UY_MovBancariosHdr_ID	  */
	public void setUY_MovBancariosHdr_ID (int UY_MovBancariosHdr_ID)
	{
		if (UY_MovBancariosHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_MovBancariosHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MovBancariosHdr_ID, Integer.valueOf(UY_MovBancariosHdr_ID));
	}

	/** Get UY_MovBancariosHdr_ID.
		@return UY_MovBancariosHdr_ID	  */
	public int getUY_MovBancariosHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MovBancariosHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MovBancariosLine getUY_MovBancariosLine() throws RuntimeException
    {
		return (I_UY_MovBancariosLine)MTable.get(getCtx(), I_UY_MovBancariosLine.Table_Name)
			.getPO(getUY_MovBancariosLine_ID(), get_TrxName());	}

	/** Set UY_MovBancariosLine_ID.
		@param UY_MovBancariosLine_ID UY_MovBancariosLine_ID	  */
	public void setUY_MovBancariosLine_ID (int UY_MovBancariosLine_ID)
	{
		if (UY_MovBancariosLine_ID < 1) 
			set_Value (COLUMNNAME_UY_MovBancariosLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MovBancariosLine_ID, Integer.valueOf(UY_MovBancariosLine_ID));
	}

	/** Get UY_MovBancariosLine_ID.
		@return UY_MovBancariosLine_ID	  */
	public int getUY_MovBancariosLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MovBancariosLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PayEmit getUY_PayEmit() throws RuntimeException
    {
		return (I_UY_PayEmit)MTable.get(getCtx(), I_UY_PayEmit.Table_Name)
			.getPO(getUY_PayEmit_ID(), get_TrxName());	}

	/** Set UY_PayEmit.
		@param UY_PayEmit_ID UY_PayEmit	  */
	public void setUY_PayEmit_ID (int UY_PayEmit_ID)
	{
		if (UY_PayEmit_ID < 1) 
			set_Value (COLUMNNAME_UY_PayEmit_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PayEmit_ID, Integer.valueOf(UY_PayEmit_ID));
	}

	/** Get UY_PayEmit.
		@return UY_PayEmit	  */
	public int getUY_PayEmit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayEmit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PaymentRule getUY_PaymentRule() throws RuntimeException
    {
		return (I_UY_PaymentRule)MTable.get(getCtx(), I_UY_PaymentRule.Table_Name)
			.getPO(getUY_PaymentRule_ID(), get_TrxName());	}

	/** Set UY_PaymentRule.
		@param UY_PaymentRule_ID UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID)
	{
		if (UY_PaymentRule_ID < 1) 
			set_Value (COLUMNNAME_UY_PaymentRule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PaymentRule_ID, Integer.valueOf(UY_PaymentRule_ID));
	}

	/** Get UY_PaymentRule.
		@return UY_PaymentRule	  */
	public int getUY_PaymentRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PaymentRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UY_Reemplaza_ID.
		@param UY_Reemplaza_ID UY_Reemplaza_ID	  */
	public void setUY_Reemplaza_ID (int UY_Reemplaza_ID)
	{
		if (UY_Reemplaza_ID < 1) 
			set_Value (COLUMNNAME_UY_Reemplaza_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Reemplaza_ID, Integer.valueOf(UY_Reemplaza_ID));
	}

	/** Get UY_Reemplaza_ID.
		@return UY_Reemplaza_ID	  */
	public int getUY_Reemplaza_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Reemplaza_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}