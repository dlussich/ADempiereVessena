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

/** Generated Model for UY_MovBancariosHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_MovBancariosHdr extends PO implements I_UY_MovBancariosHdr, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_MovBancariosHdr (Properties ctx, int UY_MovBancariosHdr_ID, String trxName)
    {
      super (ctx, UY_MovBancariosHdr_ID, trxName);
      /** if (UY_MovBancariosHdr_ID == 0)
        {
			setC_BankAccount_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
// @#DateTrx@
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDivideRate (Env.ZERO);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsInitialLoad (false);
// N
			setIsManual (false);
			setMultiplyRate (Env.ZERO);
			setPosted (false);
// N
			setProcessed (false);
			setuy_intereses (Env.ZERO);
			setUY_MovBancariosHdr_ID (0);
			setUY_SubTotal (Env.ZERO);
			setuy_totalme (Env.ZERO);
			setuy_totalmn (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UY_MovBancariosHdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MovBancariosHdr[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
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

	/** Set Create lines from.
		@param CreateFrom 
		Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Create lines from.
		@return Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom () 
	{
		return (String)get_Value(COLUMNNAME_CreateFrom);
	}

	/** Set Date.
		@param Date1 
		Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date.
		@return Date when business is not conducted
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

	/** Set isamortizable.
		@param isamortizable isamortizable	  */
	public void setisamortizable (boolean isamortizable)
	{
		set_Value (COLUMNNAME_isamortizable, Boolean.valueOf(isamortizable));
	}

	/** Get isamortizable.
		@return isamortizable	  */
	public boolean isamortizable () 
	{
		Object oo = get_Value(COLUMNNAME_isamortizable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsGanado.
		@param IsGanado IsGanado	  */
	public void setIsGanado (boolean IsGanado)
	{
		set_Value (COLUMNNAME_IsGanado, Boolean.valueOf(IsGanado));
	}

	/** Get IsGanado.
		@return IsGanado	  */
	public boolean isGanado () 
	{
		Object oo = get_Value(COLUMNNAME_IsGanado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Multiply Rate.
		@param MultiplyRate 
		Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate)
	{
		set_Value (COLUMNNAME_MultiplyRate, MultiplyRate);
	}

	/** Get Multiply Rate.
		@return Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MultiplyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set totcobrado.
		@param totcobrado totcobrado	  */
	public void settotcobrado (BigDecimal totcobrado)
	{
		set_ValueNoCheck (COLUMNNAME_totcobrado, totcobrado);
	}

	/** Get totcobrado.
		@return totcobrado	  */
	public BigDecimal gettotcobrado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totcobrado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_C_BankAccount_From_ID.
		@param UY_C_BankAccount_From_ID UY_C_BankAccount_From_ID	  */
	public void setUY_C_BankAccount_From_ID (int UY_C_BankAccount_From_ID)
	{
		if (UY_C_BankAccount_From_ID < 1) 
			set_Value (COLUMNNAME_UY_C_BankAccount_From_ID, null);
		else 
			set_Value (COLUMNNAME_UY_C_BankAccount_From_ID, Integer.valueOf(UY_C_BankAccount_From_ID));
	}

	/** Get UY_C_BankAccount_From_ID.
		@return UY_C_BankAccount_From_ID	  */
	public int getUY_C_BankAccount_From_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_C_BankAccount_From_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getUY_C_Currency_From() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getUY_C_Currency_From_ID(), get_TrxName());	}

	/** Set UY_C_Currency_From_ID.
		@param UY_C_Currency_From_ID UY_C_Currency_From_ID	  */
	public void setUY_C_Currency_From_ID (int UY_C_Currency_From_ID)
	{
		if (UY_C_Currency_From_ID < 1) 
			set_Value (COLUMNNAME_UY_C_Currency_From_ID, null);
		else 
			set_Value (COLUMNNAME_UY_C_Currency_From_ID, Integer.valueOf(UY_C_Currency_From_ID));
	}

	/** Get UY_C_Currency_From_ID.
		@return UY_C_Currency_From_ID	  */
	public int getUY_C_Currency_From_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_C_Currency_From_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_cuotas.
		@param uy_cuotas uy_cuotas	  */
	public void setuy_cuotas (int uy_cuotas)
	{
		set_Value (COLUMNNAME_uy_cuotas, Integer.valueOf(uy_cuotas));
	}

	/** Get uy_cuotas.
		@return uy_cuotas	  */
	public int getuy_cuotas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_cuotas);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_fechafirma.
		@param uy_fechafirma uy_fechafirma	  */
	public void setuy_fechafirma (Timestamp uy_fechafirma)
	{
		set_Value (COLUMNNAME_uy_fechafirma, uy_fechafirma);
	}

	/** Get uy_fechafirma.
		@return uy_fechafirma	  */
	public Timestamp getuy_fechafirma () 
	{
		return (Timestamp)get_Value(COLUMNNAME_uy_fechafirma);
	}

	/** Set uy_intereses.
		@param uy_intereses uy_intereses	  */
	public void setuy_intereses (BigDecimal uy_intereses)
	{
		set_Value (COLUMNNAME_uy_intereses, uy_intereses);
	}

	/** Get uy_intereses.
		@return uy_intereses	  */
	public BigDecimal getuy_intereses () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_intereses);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException
    {
		return (I_UY_MediosPago)MTable.get(getCtx(), I_UY_MediosPago.Table_Name)
			.getPO(getUY_MediosPago_ID(), get_TrxName());	}

	/** Set Medios de Pago.
		@param UY_MediosPago_ID Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID)
	{
		if (UY_MediosPago_ID < 1) 
			set_Value (COLUMNNAME_UY_MediosPago_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MediosPago_ID, Integer.valueOf(UY_MediosPago_ID));
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

	/** Set UY_MovBancariosHdr.
		@param UY_MovBancariosHdr_ID UY_MovBancariosHdr	  */
	public void setUY_MovBancariosHdr_ID (int UY_MovBancariosHdr_ID)
	{
		if (UY_MovBancariosHdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MovBancariosHdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MovBancariosHdr_ID, Integer.valueOf(UY_MovBancariosHdr_ID));
	}

	/** Get UY_MovBancariosHdr.
		@return UY_MovBancariosHdr	  */
	public int getUY_MovBancariosHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MovBancariosHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MovBancariosHdr getUY_NumV() throws RuntimeException
    {
		return (I_UY_MovBancariosHdr)MTable.get(getCtx(), I_UY_MovBancariosHdr.Table_Name)
			.getPO(getUY_NumVale(), get_TrxName());	}

	/** Set Numero de Vale.
		@param UY_NumVale 
		Numero de Vale
	  */
	public void setUY_NumVale (int UY_NumVale)
	{
		set_Value (COLUMNNAME_UY_NumVale, Integer.valueOf(UY_NumVale));
	}

	/** Get Numero de Vale.
		@return Numero de Vale
	  */
	public int getUY_NumVale () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_NumVale);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_SubTotal.
		@param UY_SubTotal UY_SubTotal	  */
	public void setUY_SubTotal (BigDecimal UY_SubTotal)
	{
		set_Value (COLUMNNAME_UY_SubTotal, UY_SubTotal);
	}

	/** Get UY_SubTotal.
		@return UY_SubTotal	  */
	public BigDecimal getUY_SubTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_SubTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_tasa.
		@param uy_tasa uy_tasa	  */
	public void setuy_tasa (int uy_tasa)
	{
		set_Value (COLUMNNAME_uy_tasa, Integer.valueOf(uy_tasa));
	}

	/** Get uy_tasa.
		@return uy_tasa	  */
	public int getuy_tasa () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_tasa);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_total_manual.
		@param uy_total_manual uy_total_manual	  */
	public void setuy_total_manual (BigDecimal uy_total_manual)
	{
		set_Value (COLUMNNAME_uy_total_manual, uy_total_manual);
	}

	/** Get uy_total_manual.
		@return uy_total_manual	  */
	public BigDecimal getuy_total_manual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_total_manual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_totalme.
		@param uy_totalme uy_totalme	  */
	public void setuy_totalme (BigDecimal uy_totalme)
	{
		set_Value (COLUMNNAME_uy_totalme, uy_totalme);
	}

	/** Get uy_totalme.
		@return uy_totalme	  */
	public BigDecimal getuy_totalme () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_totalme);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_totalmn.
		@param uy_totalmn uy_totalmn	  */
	public void setuy_totalmn (BigDecimal uy_totalmn)
	{
		set_Value (COLUMNNAME_uy_totalmn, uy_totalmn);
	}

	/** Get uy_totalmn.
		@return uy_totalmn	  */
	public BigDecimal getuy_totalmn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_totalmn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}