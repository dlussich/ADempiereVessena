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

/** Generated Model for UY_TR_Budget
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Budget extends PO implements I_UY_TR_Budget, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150205L;

    /** Standard Constructor */
    public X_UY_TR_Budget (Properties ctx, int UY_TR_Budget_ID, String trxName)
    {
      super (ctx, UY_TR_Budget_ID, trxName);
      /** if (UY_TR_Budget_ID == 0)
        {
			setAD_User_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setC_PaymentTerm_ID (0);
			setCityType (null);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setInTransit (false);
// N
			setPartnerType (null);
			setProcessed (false);
			setUY_Ciudad_ID (0);
			setUY_TR_Budget_ID (0);
			setUY_TR_Way_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Budget (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Budget[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
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

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentTerm)MTable.get(getCtx(), org.compiere.model.I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CityType AD_Reference_ID=1000455 */
	public static final int CITYTYPE_AD_Reference_ID=1000455;
	/** ORIGEN = O */
	public static final String CITYTYPE_ORIGEN = "O";
	/** DESTINO = D */
	public static final String CITYTYPE_DESTINO = "D";
	/** Set CityType.
		@param CityType CityType	  */
	public void setCityType (String CityType)
	{

		set_Value (COLUMNNAME_CityType, CityType);
	}

	/** Get CityType.
		@return CityType	  */
	public String getCityType () 
	{
		return (String)get_Value(COLUMNNAME_CityType);
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

	/** Set Expense.
		@param Expense Expense	  */
	public void setExpense (String Expense)
	{
		set_Value (COLUMNNAME_Expense, Expense);
	}

	/** Get Expense.
		@return Expense	  */
	public String getExpense () 
	{
		return (String)get_Value(COLUMNNAME_Expense);
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

	/** Set Note.
		@param Note 
		Optional additional user defined information
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Note.
		@return Optional additional user defined information
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
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

	/** PartnerType AD_Reference_ID=1000454 */
	public static final int PARTNERTYPE_AD_Reference_ID=1000454;
	/** IMPORTADOR = IMP */
	public static final String PARTNERTYPE_IMPORTADOR = "IMP";
	/** EXPORTADOR = EXP */
	public static final String PARTNERTYPE_EXPORTADOR = "EXP";
	/** Set PartnerType.
		@param PartnerType PartnerType	  */
	public void setPartnerType (String PartnerType)
	{

		set_Value (COLUMNNAME_PartnerType, PartnerType);
	}

	/** Get PartnerType.
		@return PartnerType	  */
	public String getPartnerType () 
	{
		return (String)get_Value(COLUMNNAME_PartnerType);
	}

	/** Set Payment Term Note.
		@param PaymentTermNote 
		Note of a Payment Term
	  */
	public void setPaymentTermNote (String PaymentTermNote)
	{
		set_Value (COLUMNNAME_PaymentTermNote, PaymentTermNote);
	}

	/** Get Payment Term Note.
		@return Note of a Payment Term
	  */
	public String getPaymentTermNote () 
	{
		return (String)get_Value(COLUMNNAME_PaymentTermNote);
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

	/** Set User ID.
		@param UserID 
		User ID or account number
	  */
	public void setUserID (int UserID)
	{
		set_Value (COLUMNNAME_UserID, Integer.valueOf(UserID));
	}

	/** Get User ID.
		@return User ID or account number
	  */
	public int getUserID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserID);
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

	/** Set UY_TR_Budget.
		@param UY_TR_Budget_ID UY_TR_Budget	  */
	public void setUY_TR_Budget_ID (int UY_TR_Budget_ID)
	{
		if (UY_TR_Budget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Budget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Budget_ID, Integer.valueOf(UY_TR_Budget_ID));
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

	public I_UY_TR_BudgetReason getUY_TR_BudgetReason() throws RuntimeException
    {
		return (I_UY_TR_BudgetReason)MTable.get(getCtx(), I_UY_TR_BudgetReason.Table_Name)
			.getPO(getUY_TR_BudgetReason_ID(), get_TrxName());	}

	/** Set Budget Reason.
		@param UY_TR_BudgetReason_ID Budget Reason	  */
	public void setUY_TR_BudgetReason_ID (int UY_TR_BudgetReason_ID)
	{
		if (UY_TR_BudgetReason_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_BudgetReason_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_BudgetReason_ID, Integer.valueOf(UY_TR_BudgetReason_ID));
	}

	/** Get Budget Reason.
		@return Budget Reason	  */
	public int getUY_TR_BudgetReason_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_BudgetReason_ID);
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

	/** Set ValoremPercentage.
		@param ValoremPercentage 
		ValoremPercentage
	  */
	public void setValoremPercentage (BigDecimal ValoremPercentage)
	{
		set_Value (COLUMNNAME_ValoremPercentage, ValoremPercentage);
	}

	/** Get ValoremPercentage.
		@return ValoremPercentage
	  */
	public BigDecimal getValoremPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValoremPercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}