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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_R_Reclamo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_Reclamo extends PO implements I_UY_R_Reclamo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151011L;

    /** Standard Constructor */
    public X_UY_R_Reclamo (Properties ctx, int UY_R_Reclamo_ID, String trxName)
    {
      super (ctx, UY_R_Reclamo_ID, trxName);
      /** if (UY_R_Reclamo_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// AY
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setHideInfo (false);
// N
			setisinmediate (false);
			setProcessed (false);
			setUY_R_Canal_ID (0);
			setUY_R_Reclamo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_Reclamo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_Reclamo[")
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

	/** Set Adjunto_ID_Obligatorio.
		@param Adjunto_ID_Obligatorio Adjunto_ID_Obligatorio	  */
	public void setAdjunto_ID_Obligatorio (int Adjunto_ID_Obligatorio)
	{
		set_Value (COLUMNNAME_Adjunto_ID_Obligatorio, Integer.valueOf(Adjunto_ID_Obligatorio));
	}

	/** Get Adjunto_ID_Obligatorio.
		@return Adjunto_ID_Obligatorio	  */
	public int getAdjunto_ID_Obligatorio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Adjunto_ID_Obligatorio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Adjunto_ID_Opcional.
		@param Adjunto_ID_Opcional Adjunto_ID_Opcional	  */
	public void setAdjunto_ID_Opcional (int Adjunto_ID_Opcional)
	{
		set_Value (COLUMNNAME_Adjunto_ID_Opcional, Integer.valueOf(Adjunto_ID_Opcional));
	}

	/** Get Adjunto_ID_Opcional.
		@return Adjunto_ID_Opcional	  */
	public int getAdjunto_ID_Opcional () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Adjunto_ID_Opcional);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtRequested.
		@param AmtRequested AmtRequested	  */
	public void setAmtRequested (BigDecimal AmtRequested)
	{
		set_Value (COLUMNNAME_AmtRequested, AmtRequested);
	}

	/** Get AmtRequested.
		@return AmtRequested	  */
	public BigDecimal getAmtRequested () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtRequested);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Assign From.
		@param AssignDateFrom 
		Assign resource from
	  */
	public void setAssignDateFrom (Timestamp AssignDateFrom)
	{
		set_Value (COLUMNNAME_AssignDateFrom, AssignDateFrom);
	}

	/** Get Assign From.
		@return Assign resource from
	  */
	public Timestamp getAssignDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AssignDateFrom);
	}

	/** Set Assign To.
		@param AssignDateTo 
		Assign resource until
	  */
	public void setAssignDateTo (Timestamp AssignDateTo)
	{
		set_Value (COLUMNNAME_AssignDateTo, AssignDateTo);
	}

	/** Get Assign To.
		@return Assign resource until
	  */
	public Timestamp getAssignDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AssignDateTo);
	}

	/** Set AssignTo_ID.
		@param AssignTo_ID AssignTo_ID	  */
	public void setAssignTo_ID (int AssignTo_ID)
	{
		if (AssignTo_ID < 1) 
			set_Value (COLUMNNAME_AssignTo_ID, null);
		else 
			set_Value (COLUMNNAME_AssignTo_ID, Integer.valueOf(AssignTo_ID));
	}

	/** Get AssignTo_ID.
		@return AssignTo_ID	  */
	public int getAssignTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AssignTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Boletinada.
		@param Boletinada Boletinada	  */
	public void setBoletinada (boolean Boletinada)
	{
		set_Value (COLUMNNAME_Boletinada, Boolean.valueOf(Boletinada));
	}

	/** Get Boletinada.
		@return Boletinada	  */
	public boolean isBoletinada () 
	{
		Object oo = get_Value(COLUMNNAME_Boletinada);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Cargos.
		@param Cargos Cargos	  */
	public void setCargos (int Cargos)
	{
		set_Value (COLUMNNAME_Cargos, Integer.valueOf(Cargos));
	}

	/** Get Cargos.
		@return Cargos	  */
	public int getCargos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cargos);
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

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
	}

	/** Set CoefAdelanto.
		@param CoefAdelanto CoefAdelanto	  */
	public void setCoefAdelanto (BigDecimal CoefAdelanto)
	{
		set_Value (COLUMNNAME_CoefAdelanto, CoefAdelanto);
	}

	/** Get CoefAdelanto.
		@return CoefAdelanto	  */
	public BigDecimal getCoefAdelanto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CoefAdelanto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Coeficiente.
		@param Coeficiente Coeficiente	  */
	public void setCoeficiente (BigDecimal Coeficiente)
	{
		set_Value (COLUMNNAME_Coeficiente, Coeficiente);
	}

	/** Get Coeficiente.
		@return Coeficiente	  */
	public BigDecimal getCoeficiente () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Coeficiente);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cupon.
		@param Cupon Cupon	  */
	public void setCupon (String Cupon)
	{
		set_Value (COLUMNNAME_Cupon, Cupon);
	}

	/** Get Cupon.
		@return Cupon	  */
	public String getCupon () 
	{
		return (String)get_Value(COLUMNNAME_Cupon);
	}

	/** Set CustomerName.
		@param CustomerName CustomerName	  */
	public void setCustomerName (String CustomerName)
	{
		set_Value (COLUMNNAME_CustomerName, CustomerName);
	}

	/** Get CustomerName.
		@return CustomerName	  */
	public String getCustomerName () 
	{
		return (String)get_Value(COLUMNNAME_CustomerName);
	}

	/** Set DateAlta.
		@param DateAlta DateAlta	  */
	public void setDateAlta (Timestamp DateAlta)
	{
		set_Value (COLUMNNAME_DateAlta, DateAlta);
	}

	/** Get DateAlta.
		@return DateAlta	  */
	public Timestamp getDateAlta () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAlta);
	}

	/** Set DateOperation.
		@param DateOperation DateOperation	  */
	public void setDateOperation (Timestamp DateOperation)
	{
		set_Value (COLUMNNAME_DateOperation, DateOperation);
	}

	/** Get DateOperation.
		@return DateOperation	  */
	public Timestamp getDateOperation () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOperation);
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

	/** Set DiasCierre.
		@param DiasCierre DiasCierre	  */
	public void setDiasCierre (int DiasCierre)
	{
		set_Value (COLUMNNAME_DiasCierre, Integer.valueOf(DiasCierre));
	}

	/** Get DiasCierre.
		@return DiasCierre	  */
	public int getDiasCierre () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasCierre);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DiasGestion.
		@param DiasGestion DiasGestion	  */
	public void setDiasGestion (int DiasGestion)
	{
		set_Value (COLUMNNAME_DiasGestion, Integer.valueOf(DiasGestion));
	}

	/** Get DiasGestion.
		@return DiasGestion	  */
	public int getDiasGestion () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasGestion);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DiasNotificacion.
		@param DiasNotificacion DiasNotificacion	  */
	public void setDiasNotificacion (int DiasNotificacion)
	{
		set_Value (COLUMNNAME_DiasNotificacion, Integer.valueOf(DiasNotificacion));
	}

	/** Get DiasNotificacion.
		@return DiasNotificacion	  */
	public int getDiasNotificacion () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasNotificacion);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set diasresolucion.
		@param diasresolucion diasresolucion	  */
	public void setdiasresolucion (int diasresolucion)
	{
		set_Value (COLUMNNAME_diasresolucion, Integer.valueOf(diasresolucion));
	}

	/** Get diasresolucion.
		@return diasresolucion	  */
	public int getdiasresolucion () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_diasresolucion);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DiasVencido.
		@param DiasVencido DiasVencido	  */
	public void setDiasVencido (int DiasVencido)
	{
		set_Value (COLUMNNAME_DiasVencido, Integer.valueOf(DiasVencido));
	}

	/** Get DiasVencido.
		@return DiasVencido	  */
	public int getDiasVencido () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasVencido);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DigitoVerificador.
		@param DigitoVerificador DigitoVerificador	  */
	public void setDigitoVerificador (int DigitoVerificador)
	{
		set_Value (COLUMNNAME_DigitoVerificador, Integer.valueOf(DigitoVerificador));
	}

	/** Get DigitoVerificador.
		@return DigitoVerificador	  */
	public int getDigitoVerificador () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DigitoVerificador);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Direction.
		@param Direction Direction	  */
	public void setDirection (String Direction)
	{
		set_Value (COLUMNNAME_Direction, Direction);
	}

	/** Get Direction.
		@return Direction	  */
	public String getDirection () 
	{
		return (String)get_Value(COLUMNNAME_Direction);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
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

	/** Set DueDateTitular.
		@param DueDateTitular DueDateTitular	  */
	public void setDueDateTitular (String DueDateTitular)
	{
		set_Value (COLUMNNAME_DueDateTitular, DueDateTitular);
	}

	/** Get DueDateTitular.
		@return DueDateTitular	  */
	public String getDueDateTitular () 
	{
		return (String)get_Value(COLUMNNAME_DueDateTitular);
	}

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
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

	/** Set EnvioExt.
		@param EnvioExt EnvioExt	  */
	public void setEnvioExt (boolean EnvioExt)
	{
		set_Value (COLUMNNAME_EnvioExt, Boolean.valueOf(EnvioExt));
	}

	/** Get EnvioExt.
		@return EnvioExt	  */
	public boolean isEnvioExt () 
	{
		Object oo = get_Value(COLUMNNAME_EnvioExt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set EnvioResumen.
		@param EnvioResumen EnvioResumen	  */
	public void setEnvioResumen (boolean EnvioResumen)
	{
		set_Value (COLUMNNAME_EnvioResumen, Boolean.valueOf(EnvioResumen));
	}

	/** Get EnvioResumen.
		@return EnvioResumen	  */
	public boolean isEnvioResumen () 
	{
		Object oo = get_Value(COLUMNNAME_EnvioResumen);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set ExecuteAction4.
		@param ExecuteAction4 ExecuteAction4	  */
	public void setExecuteAction4 (String ExecuteAction4)
	{
		set_Value (COLUMNNAME_ExecuteAction4, ExecuteAction4);
	}

	/** Get ExecuteAction4.
		@return ExecuteAction4	  */
	public String getExecuteAction4 () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction4);
	}

	/** Set GAFCOD.
		@param GAFCOD GAFCOD	  */
	public void setGAFCOD (int GAFCOD)
	{
		set_Value (COLUMNNAME_GAFCOD, Integer.valueOf(GAFCOD));
	}

	/** Get GAFCOD.
		@return GAFCOD	  */
	public int getGAFCOD () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GAFCOD);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GAFNOM.
		@param GAFNOM GAFNOM	  */
	public void setGAFNOM (String GAFNOM)
	{
		set_Value (COLUMNNAME_GAFNOM, GAFNOM);
	}

	/** Get GAFNOM.
		@return GAFNOM	  */
	public String getGAFNOM () 
	{
		return (String)get_Value(COLUMNNAME_GAFNOM);
	}

	/** Set Gestor_ID.
		@param Gestor_ID Gestor_ID	  */
	public void setGestor_ID (int Gestor_ID)
	{
		if (Gestor_ID < 1) 
			set_Value (COLUMNNAME_Gestor_ID, null);
		else 
			set_Value (COLUMNNAME_Gestor_ID, Integer.valueOf(Gestor_ID));
	}

	/** Get Gestor_ID.
		@return Gestor_ID	  */
	public int getGestor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Gestor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GrpCtaCte.
		@param GrpCtaCte GrpCtaCte	  */
	public void setGrpCtaCte (int GrpCtaCte)
	{
		set_Value (COLUMNNAME_GrpCtaCte, Integer.valueOf(GrpCtaCte));
	}

	/** Get GrpCtaCte.
		@return GrpCtaCte	  */
	public int getGrpCtaCte () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GrpCtaCte);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HideInfo.
		@param HideInfo HideInfo	  */
	public void setHideInfo (boolean HideInfo)
	{
		set_Value (COLUMNNAME_HideInfo, Boolean.valueOf(HideInfo));
	}

	/** Get HideInfo.
		@return HideInfo	  */
	public boolean isHideInfo () 
	{
		Object oo = get_Value(COLUMNNAME_HideInfo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDenounced.
		@param IsDenounced IsDenounced	  */
	public void setIsDenounced (boolean IsDenounced)
	{
		set_Value (COLUMNNAME_IsDenounced, Boolean.valueOf(IsDenounced));
	}

	/** Get IsDenounced.
		@return IsDenounced	  */
	public boolean isDenounced () 
	{
		Object oo = get_Value(COLUMNNAME_IsDenounced);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isinmediate.
		@param isinmediate isinmediate	  */
	public void setisinmediate (boolean isinmediate)
	{
		set_Value (COLUMNNAME_isinmediate, Boolean.valueOf(isinmediate));
	}

	/** Get isinmediate.
		@return isinmediate	  */
	public boolean isinmediate () 
	{
		Object oo = get_Value(COLUMNNAME_isinmediate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsInternalIssue.
		@param IsInternalIssue 
		Incidencia Interna
	  */
	public void setIsInternalIssue (boolean IsInternalIssue)
	{
		set_Value (COLUMNNAME_IsInternalIssue, Boolean.valueOf(IsInternalIssue));
	}

	/** Get IsInternalIssue.
		@return Incidencia Interna
	  */
	public boolean isInternalIssue () 
	{
		Object oo = get_Value(COLUMNNAME_IsInternalIssue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsObserver.
		@param IsObserver 
		IsObserver
	  */
	public void setIsObserver (boolean IsObserver)
	{
		set_Value (COLUMNNAME_IsObserver, Boolean.valueOf(IsObserver));
	}

	/** Get IsObserver.
		@return IsObserver
	  */
	public boolean isObserver () 
	{
		Object oo = get_Value(COLUMNNAME_IsObserver);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPreNotificacion.
		@param IsPreNotificacion IsPreNotificacion	  */
	public void setIsPreNotificacion (boolean IsPreNotificacion)
	{
		set_Value (COLUMNNAME_IsPreNotificacion, Boolean.valueOf(IsPreNotificacion));
	}

	/** Get IsPreNotificacion.
		@return IsPreNotificacion	  */
	public boolean isPreNotificacion () 
	{
		Object oo = get_Value(COLUMNNAME_IsPreNotificacion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Justification.
		@param Justification Justification	  */
	public void setJustification (String Justification)
	{
		set_Value (COLUMNNAME_Justification, Justification);
	}

	/** Get Justification.
		@return Justification	  */
	public String getJustification () 
	{
		return (String)get_Value(COLUMNNAME_Justification);
	}

	/** Set LetraCompra.
		@param LetraCompra LetraCompra	  */
	public void setLetraCompra (String LetraCompra)
	{
		set_Value (COLUMNNAME_LetraCompra, LetraCompra);
	}

	/** Get LetraCompra.
		@return LetraCompra	  */
	public String getLetraCompra () 
	{
		return (String)get_Value(COLUMNNAME_LetraCompra);
	}

	/** Set LimCreditoTitular.
		@param LimCreditoTitular LimCreditoTitular	  */
	public void setLimCreditoTitular (BigDecimal LimCreditoTitular)
	{
		set_Value (COLUMNNAME_LimCreditoTitular, LimCreditoTitular);
	}

	/** Get LimCreditoTitular.
		@return LimCreditoTitular	  */
	public BigDecimal getLimCreditoTitular () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LimCreditoTitular);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set MLCod.
		@param MLCod MLCod	  */
	public void setMLCod (String MLCod)
	{
		set_Value (COLUMNNAME_MLCod, MLCod);
	}

	/** Get MLCod.
		@return MLCod	  */
	public String getMLCod () 
	{
		return (String)get_Value(COLUMNNAME_MLCod);
	}

	/** Set Mobile.
		@param Mobile Mobile	  */
	public void setMobile (String Mobile)
	{
		set_Value (COLUMNNAME_Mobile, Mobile);
	}

	/** Get Mobile.
		@return Mobile	  */
	public String getMobile () 
	{
		return (String)get_Value(COLUMNNAME_Mobile);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name 2.
		@param Name2 
		Additional Name
	  */
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** Set NombreTitular.
		@param NombreTitular NombreTitular	  */
	public void setNombreTitular (String NombreTitular)
	{
		set_Value (COLUMNNAME_NombreTitular, NombreTitular);
	}

	/** Get NombreTitular.
		@return NombreTitular	  */
	public String getNombreTitular () 
	{
		return (String)get_Value(COLUMNNAME_NombreTitular);
	}

	/** Set Notificable.
		@param Notificable Notificable	  */
	public void setNotificable (boolean Notificable)
	{
		throw new IllegalArgumentException ("Notificable is virtual column");	}

	/** Get Notificable.
		@return Notificable	  */
	public boolean isNotificable () 
	{
		Object oo = get_Value(COLUMNNAME_Notificable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NotificationAssignTo_ID.
		@param NotificationAssignTo_ID NotificationAssignTo_ID	  */
	public void setNotificationAssignTo_ID (int NotificationAssignTo_ID)
	{
		if (NotificationAssignTo_ID < 1) 
			set_Value (COLUMNNAME_NotificationAssignTo_ID, null);
		else 
			set_Value (COLUMNNAME_NotificationAssignTo_ID, Integer.valueOf(NotificationAssignTo_ID));
	}

	/** Get NotificationAssignTo_ID.
		@return NotificationAssignTo_ID	  */
	public int getNotificationAssignTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NotificationAssignTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NotificationDateFrom.
		@param NotificationDateFrom NotificationDateFrom	  */
	public void setNotificationDateFrom (Timestamp NotificationDateFrom)
	{
		set_Value (COLUMNNAME_NotificationDateFrom, NotificationDateFrom);
	}

	/** Get NotificationDateFrom.
		@return NotificationDateFrom	  */
	public Timestamp getNotificationDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NotificationDateFrom);
	}

	/** Set NotificationDateTo.
		@param NotificationDateTo NotificationDateTo	  */
	public void setNotificationDateTo (Timestamp NotificationDateTo)
	{
		set_Value (COLUMNNAME_NotificationDateTo, NotificationDateTo);
	}

	/** Get NotificationDateTo.
		@return NotificationDateTo	  */
	public Timestamp getNotificationDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NotificationDateTo);
	}

	/** NotificationVia AD_Reference_ID=1000288 */
	public static final int NOTIFICATIONVIA_AD_Reference_ID=1000288;
	/** Telefono Fijo = TEL */
	public static final String NOTIFICATIONVIA_TelefonoFijo = "TEL";
	/** Celular = CEL */
	public static final String NOTIFICATIONVIA_Celular = "CEL";
	/** Email = EMA */
	public static final String NOTIFICATIONVIA_Email = "EMA";
	/** Set NotificationVia.
		@param NotificationVia 
		Via Notificacion
	  */
	public void setNotificationVia (String NotificationVia)
	{

		set_Value (COLUMNNAME_NotificationVia, NotificationVia);
	}

	/** Get NotificationVia.
		@return Via Notificacion
	  */
	public String getNotificationVia () 
	{
		return (String)get_Value(COLUMNNAME_NotificationVia);
	}

	/** Set Notificator_ID.
		@param Notificator_ID Notificator_ID	  */
	public void setNotificator_ID (int Notificator_ID)
	{
		if (Notificator_ID < 1) 
			set_Value (COLUMNNAME_Notificator_ID, null);
		else 
			set_Value (COLUMNNAME_Notificator_ID, Integer.valueOf(Notificator_ID));
	}

	/** Get Notificator_ID.
		@return Notificator_ID	  */
	public int getNotificator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Notificator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NroCuentaTitular.
		@param NroCuentaTitular NroCuentaTitular	  */
	public void setNroCuentaTitular (String NroCuentaTitular)
	{
		set_Value (COLUMNNAME_NroCuentaTitular, NroCuentaTitular);
	}

	/** Get NroCuentaTitular.
		@return NroCuentaTitular	  */
	public String getNroCuentaTitular () 
	{
		return (String)get_Value(COLUMNNAME_NroCuentaTitular);
	}

	/** Set NroTarjetaTitular.
		@param NroTarjetaTitular NroTarjetaTitular	  */
	public void setNroTarjetaTitular (String NroTarjetaTitular)
	{
		set_Value (COLUMNNAME_NroTarjetaTitular, NroTarjetaTitular);
	}

	/** Get NroTarjetaTitular.
		@return NroTarjetaTitular	  */
	public String getNroTarjetaTitular () 
	{
		return (String)get_Value(COLUMNNAME_NroTarjetaTitular);
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

	/** Set Parametros.
		@param Parametros Parametros	  */
	public void setParametros (int Parametros)
	{
		set_Value (COLUMNNAME_Parametros, Integer.valueOf(Parametros));
	}

	/** Get Parametros.
		@return Parametros	  */
	public int getParametros () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parametros);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set PorcBonif.
		@param PorcBonif PorcBonif	  */
	public void setPorcBonif (BigDecimal PorcBonif)
	{
		set_Value (COLUMNNAME_PorcBonif, PorcBonif);
	}

	/** Get PorcBonif.
		@return PorcBonif	  */
	public BigDecimal getPorcBonif () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PorcBonif);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ZIP.
		@param Postal 
		Postal code
	  */
	public void setPostal (String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get ZIP.
		@return Postal code
	  */
	public String getPostal () 
	{
		return (String)get_Value(COLUMNNAME_Postal);
	}

	/** PriorityBase AD_Reference_ID=154 */
	public static final int PRIORITYBASE_AD_Reference_ID=154;
	/** Medium = 3 */
	public static final String PRIORITYBASE_Medium = "3";
	/** Urgent = 1 */
	public static final String PRIORITYBASE_Urgent = "1";
	/** Minor = 5 */
	public static final String PRIORITYBASE_Minor = "5";
	/** Low = 4 */
	public static final String PRIORITYBASE_Low = "4";
	/** High = 2 */
	public static final String PRIORITYBASE_High = "2";
	/** Inmediate = 0 */
	public static final String PRIORITYBASE_Inmediate = "0";
	/** Set Priority Base.
		@param PriorityBase 
		Base of Priority
	  */
	public void setPriorityBase (String PriorityBase)
	{

		set_Value (COLUMNNAME_PriorityBase, PriorityBase);
	}

	/** Get Priority Base.
		@return Base of Priority
	  */
	public String getPriorityBase () 
	{
		return (String)get_Value(COLUMNNAME_PriorityBase);
	}

	/** PriorityManual AD_Reference_ID=154 */
	public static final int PRIORITYMANUAL_AD_Reference_ID=154;
	/** Medium = 3 */
	public static final String PRIORITYMANUAL_Medium = "3";
	/** Urgent = 1 */
	public static final String PRIORITYMANUAL_Urgent = "1";
	/** Minor = 5 */
	public static final String PRIORITYMANUAL_Minor = "5";
	/** Low = 4 */
	public static final String PRIORITYMANUAL_Low = "4";
	/** High = 2 */
	public static final String PRIORITYMANUAL_High = "2";
	/** Inmediate = 0 */
	public static final String PRIORITYMANUAL_Inmediate = "0";
	/** Set PriorityManual.
		@param PriorityManual PriorityManual	  */
	public void setPriorityManual (String PriorityManual)
	{

		set_Value (COLUMNNAME_PriorityManual, PriorityManual);
	}

	/** Get PriorityManual.
		@return PriorityManual	  */
	public String getPriorityManual () 
	{
		return (String)get_Value(COLUMNNAME_PriorityManual);
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

	/** Set ProductoAux.
		@param ProductoAux ProductoAux	  */
	public void setProductoAux (String ProductoAux)
	{
		set_Value (COLUMNNAME_ProductoAux, ProductoAux);
	}

	/** Get ProductoAux.
		@return ProductoAux	  */
	public String getProductoAux () 
	{
		return (String)get_Value(COLUMNNAME_ProductoAux);
	}

	/** Set QtyDerivados.
		@param QtyDerivados QtyDerivados	  */
	public void setQtyDerivados (int QtyDerivados)
	{
		set_Value (COLUMNNAME_QtyDerivados, Integer.valueOf(QtyDerivados));
	}

	/** Get QtyDerivados.
		@return QtyDerivados	  */
	public int getQtyDerivados () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyDerivados);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyQuote.
		@param QtyQuote QtyQuote	  */
	public void setQtyQuote (int QtyQuote)
	{
		set_Value (COLUMNNAME_QtyQuote, Integer.valueOf(QtyQuote));
	}

	/** Get QtyQuote.
		@return QtyQuote	  */
	public int getQtyQuote () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyQuote);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Receptor_ID.
		@param Receptor_ID Receptor_ID	  */
	public void setReceptor_ID (int Receptor_ID)
	{
		if (Receptor_ID < 1) 
			set_Value (COLUMNNAME_Receptor_ID, null);
		else 
			set_Value (COLUMNNAME_Receptor_ID, Integer.valueOf(Receptor_ID));
	}

	/** Get Receptor_ID.
		@return Receptor_ID	  */
	public int getReceptor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Receptor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ReclamoNotificado.
		@param ReclamoNotificado ReclamoNotificado	  */
	public void setReclamoNotificado (boolean ReclamoNotificado)
	{
		set_Value (COLUMNNAME_ReclamoNotificado, Boolean.valueOf(ReclamoNotificado));
	}

	/** Get ReclamoNotificado.
		@return ReclamoNotificado	  */
	public boolean isReclamoNotificado () 
	{
		Object oo = get_Value(COLUMNNAME_ReclamoNotificado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ReclamoResuelto.
		@param ReclamoResuelto ReclamoResuelto	  */
	public void setReclamoResuelto (boolean ReclamoResuelto)
	{
		set_Value (COLUMNNAME_ReclamoResuelto, Boolean.valueOf(ReclamoResuelto));
	}

	/** Get ReclamoResuelto.
		@return ReclamoResuelto	  */
	public boolean isReclamoResuelto () 
	{
		Object oo = get_Value(COLUMNNAME_ReclamoResuelto);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Renovacion.
		@param Renovacion Renovacion	  */
	public void setRenovacion (boolean Renovacion)
	{
		set_Value (COLUMNNAME_Renovacion, Boolean.valueOf(Renovacion));
	}

	/** Get Renovacion.
		@return Renovacion	  */
	public boolean isRenovacion () 
	{
		Object oo = get_Value(COLUMNNAME_Renovacion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ResueltoEmpresa.
		@param ResueltoEmpresa ResueltoEmpresa	  */
	public void setResueltoEmpresa (boolean ResueltoEmpresa)
	{
		set_Value (COLUMNNAME_ResueltoEmpresa, Boolean.valueOf(ResueltoEmpresa));
	}

	/** Get ResueltoEmpresa.
		@return ResueltoEmpresa	  */
	public boolean isResueltoEmpresa () 
	{
		Object oo = get_Value(COLUMNNAME_ResueltoEmpresa);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** StatusReclamo AD_Reference_ID=1000260 */
	public static final int STATUSRECLAMO_AD_Reference_ID=1000260;
	/** Pendiente = PENDIENTE */
	public static final String STATUSRECLAMO_Pendiente = "PENDIENTE";
	/** Vencido = VENCIDO */
	public static final String STATUSRECLAMO_Vencido = "VENCIDO";
	/** Cerrado = CERRADO */
	public static final String STATUSRECLAMO_Cerrado = "CERRADO";
	/** En Curso = CURSO */
	public static final String STATUSRECLAMO_EnCurso = "CURSO";
	/** Resuelto = RESU */
	public static final String STATUSRECLAMO_Resuelto = "RESU";
	/** Nueva Incidencia = NUEVO */
	public static final String STATUSRECLAMO_NuevaIncidencia = "NUEVO";
	/** Pendiente de Gestion = GESTPEND */
	public static final String STATUSRECLAMO_PendienteDeGestion = "GESTPEND";
	/** En Gestion = GESTION */
	public static final String STATUSRECLAMO_EnGestion = "GESTION";
	/** Pendiente de Notificacion = NOTIFPEND */
	public static final String STATUSRECLAMO_PendienteDeNotificacion = "NOTIFPEND";
	/** En Notificacion = NOTIFICACION */
	public static final String STATUSRECLAMO_EnNotificacion = "NOTIFICACION";
	/** Pendiente de Canal Escrito = ESCPEND */
	public static final String STATUSRECLAMO_PendienteDeCanalEscrito = "ESCPEND";
	/** Pendiente Aprobacion = PENDAPROBACION */
	public static final String STATUSRECLAMO_PendienteAprobacion = "PENDAPROBACION";
	/** Pendiente Aprobacion Nivel 1 = PENDAPROBACION_1 */
	public static final String STATUSRECLAMO_PendienteAprobacionNivel1 = "PENDAPROBACION_1";
	/** Pendiente Aprobacion Nivel 2 = PENDAPROBACION_2 */
	public static final String STATUSRECLAMO_PendienteAprobacionNivel2 = "PENDAPROBACION_2";
	/** Aprobado = APROBADO */
	public static final String STATUSRECLAMO_Aprobado = "APROBADO";
	/** Aprobado Nivel 1 = APROBADO_1 */
	public static final String STATUSRECLAMO_AprobadoNivel1 = "APROBADO_1";
	/** Aprobado Nivel 2 = APROBADO_2 */
	public static final String STATUSRECLAMO_AprobadoNivel2 = "APROBADO_2";
	/** No Aprobado = NOAPROBADO */
	public static final String STATUSRECLAMO_NoAprobado = "NOAPROBADO";
	/** No Aprobado Nivel 1 = NOAPROBADO_1 */
	public static final String STATUSRECLAMO_NoAprobadoNivel1 = "NOAPROBADO_1";
	/** No Aprobado Nivel 2 = NOAPROBADO_2 */
	public static final String STATUSRECLAMO_NoAprobadoNivel2 = "NOAPROBADO_2";
	/** Pendiente Aprobacion Category = PENDAPROBACIONCAT */
	public static final String STATUSRECLAMO_PendienteAprobacionCategory = "PENDAPROBACIONCAT";
	/** Aprobado Categoria = APROBADO_CAT */
	public static final String STATUSRECLAMO_AprobadoCategoria = "APROBADO_CAT";
	/** No Aprobado Categoria = NOAPROBADO_CAT */
	public static final String STATUSRECLAMO_NoAprobadoCategoria = "NOAPROBADO_CAT";
	/** Pendiente Aprobacion Stock = PENDAPROBACION_STK */
	public static final String STATUSRECLAMO_PendienteAprobacionStock = "PENDAPROBACION_STK";
	/** No Aprobado por Stock = NOAPROBADO_STK */
	public static final String STATUSRECLAMO_NoAprobadoPorStock = "NOAPROBADO_STK";
	/** Set StatusReclamo.
		@param StatusReclamo StatusReclamo	  */
	public void setStatusReclamo (String StatusReclamo)
	{

		set_Value (COLUMNNAME_StatusReclamo, StatusReclamo);
	}

	/** Get StatusReclamo.
		@return StatusReclamo	  */
	public String getStatusReclamo () 
	{
		return (String)get_Value(COLUMNNAME_StatusReclamo);
	}

	/** Set Tasas.
		@param Tasas Tasas	  */
	public void setTasas (int Tasas)
	{
		set_Value (COLUMNNAME_Tasas, Integer.valueOf(Tasas));
	}

	/** Get Tasas.
		@return Tasas	  */
	public int getTasas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Tasas);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Telephone.
		@param Telephone Telephone	  */
	public void setTelephone (String Telephone)
	{
		set_Value (COLUMNNAME_Telephone, Telephone);
	}

	/** Get Telephone.
		@return Telephone	  */
	public String getTelephone () 
	{
		return (String)get_Value(COLUMNNAME_Telephone);
	}

	public I_UY_PR_Schedule getUY_PR_Schedule() throws RuntimeException
    {
		return (I_UY_PR_Schedule)MTable.get(getCtx(), I_UY_PR_Schedule.Table_Name)
			.getPO(getUY_PR_Schedule_ID(), get_TrxName());	}

	/** Set UY_PR_Schedule.
		@param UY_PR_Schedule_ID UY_PR_Schedule	  */
	public void setUY_PR_Schedule_ID (int UY_PR_Schedule_ID)
	{
		if (UY_PR_Schedule_ID < 1) 
			set_Value (COLUMNNAME_UY_PR_Schedule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PR_Schedule_ID, Integer.valueOf(UY_PR_Schedule_ID));
	}

	/** Get UY_PR_Schedule.
		@return UY_PR_Schedule	  */
	public int getUY_PR_Schedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_Schedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PR_SchTask getUY_PR_SchTask() throws RuntimeException
    {
		return (I_UY_PR_SchTask)MTable.get(getCtx(), I_UY_PR_SchTask.Table_Name)
			.getPO(getUY_PR_SchTask_ID(), get_TrxName());	}

	/** Set UY_PR_SchTask.
		@param UY_PR_SchTask_ID UY_PR_SchTask	  */
	public void setUY_PR_SchTask_ID (int UY_PR_SchTask_ID)
	{
		if (UY_PR_SchTask_ID < 1) 
			set_Value (COLUMNNAME_UY_PR_SchTask_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PR_SchTask_ID, Integer.valueOf(UY_PR_SchTask_ID));
	}

	/** Get UY_PR_SchTask.
		@return UY_PR_SchTask	  */
	public int getUY_PR_SchTask_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_SchTask_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PR_Task getUY_PR_Task() throws RuntimeException
    {
		return (I_UY_PR_Task)MTable.get(getCtx(), I_UY_PR_Task.Table_Name)
			.getPO(getUY_PR_Task_ID(), get_TrxName());	}

	/** Set UY_PR_Task.
		@param UY_PR_Task_ID UY_PR_Task	  */
	public void setUY_PR_Task_ID (int UY_PR_Task_ID)
	{
		if (UY_PR_Task_ID < 1) 
			set_Value (COLUMNNAME_UY_PR_Task_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PR_Task_ID, Integer.valueOf(UY_PR_Task_ID));
	}

	/** Get UY_PR_Task.
		@return UY_PR_Task	  */
	public int getUY_PR_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_ActionType getUY_R_ActionType() throws RuntimeException
    {
		return (I_UY_R_ActionType)MTable.get(getCtx(), I_UY_R_ActionType.Table_Name)
			.getPO(getUY_R_ActionType_ID(), get_TrxName());	}

	/** Set UY_R_ActionType.
		@param UY_R_ActionType_ID UY_R_ActionType	  */
	public void setUY_R_ActionType_ID (int UY_R_ActionType_ID)
	{
		if (UY_R_ActionType_ID < 1) 
			set_Value (COLUMNNAME_UY_R_ActionType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_ActionType_ID, Integer.valueOf(UY_R_ActionType_ID));
	}

	/** Get UY_R_ActionType.
		@return UY_R_ActionType	  */
	public int getUY_R_ActionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ActionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_ActionType_ID_Notif.
		@param UY_R_ActionType_ID_Notif UY_R_ActionType_ID_Notif	  */
	public void setUY_R_ActionType_ID_Notif (int UY_R_ActionType_ID_Notif)
	{
		set_Value (COLUMNNAME_UY_R_ActionType_ID_Notif, Integer.valueOf(UY_R_ActionType_ID_Notif));
	}

	/** Get UY_R_ActionType_ID_Notif.
		@return UY_R_ActionType_ID_Notif	  */
	public int getUY_R_ActionType_ID_Notif () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ActionType_ID_Notif);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Area getUY_R_Area() throws RuntimeException
    {
		return (I_UY_R_Area)MTable.get(getCtx(), I_UY_R_Area.Table_Name)
			.getPO(getUY_R_Area_ID(), get_TrxName());	}

	/** Set UY_R_Area.
		@param UY_R_Area_ID UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID)
	{
		if (UY_R_Area_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Area_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Area_ID, Integer.valueOf(UY_R_Area_ID));
	}

	/** Get UY_R_Area.
		@return UY_R_Area	  */
	public int getUY_R_Area_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Area_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Canal getUY_R_Canal() throws RuntimeException
    {
		return (I_UY_R_Canal)MTable.get(getCtx(), I_UY_R_Canal.Table_Name)
			.getPO(getUY_R_Canal_ID(), get_TrxName());	}

	/** Set UY_R_Canal.
		@param UY_R_Canal_ID UY_R_Canal	  */
	public void setUY_R_Canal_ID (int UY_R_Canal_ID)
	{
		if (UY_R_Canal_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Canal_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Canal_ID, Integer.valueOf(UY_R_Canal_ID));
	}

	/** Get UY_R_Canal.
		@return UY_R_Canal	  */
	public int getUY_R_Canal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Canal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_CanalNotifica_ID.
		@param UY_R_CanalNotifica_ID UY_R_CanalNotifica_ID	  */
	public void setUY_R_CanalNotifica_ID (int UY_R_CanalNotifica_ID)
	{
		if (UY_R_CanalNotifica_ID < 1) 
			set_Value (COLUMNNAME_UY_R_CanalNotifica_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_CanalNotifica_ID, Integer.valueOf(UY_R_CanalNotifica_ID));
	}

	/** Get UY_R_CanalNotifica_ID.
		@return UY_R_CanalNotifica_ID	  */
	public int getUY_R_CanalNotifica_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_CanalNotifica_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Cause getUY_R_Cause() throws RuntimeException
    {
		return (I_UY_R_Cause)MTable.get(getCtx(), I_UY_R_Cause.Table_Name)
			.getPO(getUY_R_Cause_ID(), get_TrxName());	}

	/** Set UY_R_Cause.
		@param UY_R_Cause_ID UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID)
	{
		if (UY_R_Cause_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Cause_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Cause_ID, Integer.valueOf(UY_R_Cause_ID));
	}

	/** Get UY_R_Cause.
		@return UY_R_Cause	  */
	public int getUY_R_Cause_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Cause_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_CedulaCuenta getUY_R_CedulaCuenta() throws RuntimeException
    {
		return (I_UY_R_CedulaCuenta)MTable.get(getCtx(), I_UY_R_CedulaCuenta.Table_Name)
			.getPO(getUY_R_CedulaCuenta_ID(), get_TrxName());	}

	/** Set UY_R_CedulaCuenta.
		@param UY_R_CedulaCuenta_ID UY_R_CedulaCuenta	  */
	public void setUY_R_CedulaCuenta_ID (int UY_R_CedulaCuenta_ID)
	{
		if (UY_R_CedulaCuenta_ID < 1) 
			set_Value (COLUMNNAME_UY_R_CedulaCuenta_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_CedulaCuenta_ID, Integer.valueOf(UY_R_CedulaCuenta_ID));
	}

	/** Get UY_R_CedulaCuenta.
		@return UY_R_CedulaCuenta	  */
	public int getUY_R_CedulaCuenta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_CedulaCuenta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Comercio getUY_R_Comercio() throws RuntimeException
    {
		return (I_UY_R_Comercio)MTable.get(getCtx(), I_UY_R_Comercio.Table_Name)
			.getPO(getUY_R_Comercio_ID(), get_TrxName());	}

	/** Set UY_R_Comercio.
		@param UY_R_Comercio_ID UY_R_Comercio	  */
	public void setUY_R_Comercio_ID (int UY_R_Comercio_ID)
	{
		if (UY_R_Comercio_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Comercio_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Comercio_ID, Integer.valueOf(UY_R_Comercio_ID));
	}

	/** Get UY_R_Comercio.
		@return UY_R_Comercio	  */
	public int getUY_R_Comercio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Comercio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException
    {
		return (I_UY_R_PtoResolucion)MTable.get(getCtx(), I_UY_R_PtoResolucion.Table_Name)
			.getPO(getUY_R_PtoResolucion_ID(), get_TrxName());	}

	/** Set UY_R_PtoResolucion.
		@param UY_R_PtoResolucion_ID UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID)
	{
		if (UY_R_PtoResolucion_ID < 1) 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, Integer.valueOf(UY_R_PtoResolucion_ID));
	}

	/** Get UY_R_PtoResolucion.
		@return UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_PtoResolucion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Reclamo.
		@param UY_R_Reclamo_ID UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID)
	{
		if (UY_R_Reclamo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_Reclamo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_Reclamo_ID, Integer.valueOf(UY_R_Reclamo_ID));
	}

	/** Get UY_R_Reclamo.
		@return UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Reclamo_ID_2.
		@param UY_R_Reclamo_ID_2 UY_R_Reclamo_ID_2	  */
	public void setUY_R_Reclamo_ID_2 (int UY_R_Reclamo_ID_2)
	{
		set_Value (COLUMNNAME_UY_R_Reclamo_ID_2, Integer.valueOf(UY_R_Reclamo_ID_2));
	}

	/** Get UY_R_Reclamo_ID_2.
		@return UY_R_Reclamo_ID_2	  */
	public int getUY_R_Reclamo_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_r_subcause_id_1.
		@param uy_r_subcause_id_1 uy_r_subcause_id_1	  */
	public void setuy_r_subcause_id_1 (int uy_r_subcause_id_1)
	{
		set_Value (COLUMNNAME_uy_r_subcause_id_1, Integer.valueOf(uy_r_subcause_id_1));
	}

	/** Get uy_r_subcause_id_1.
		@return uy_r_subcause_id_1	  */
	public int getuy_r_subcause_id_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_r_subcause_id_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_r_subcause_id_2.
		@param uy_r_subcause_id_2 uy_r_subcause_id_2	  */
	public void setuy_r_subcause_id_2 (int uy_r_subcause_id_2)
	{
		set_Value (COLUMNNAME_uy_r_subcause_id_2, Integer.valueOf(uy_r_subcause_id_2));
	}

	/** Get uy_r_subcause_id_2.
		@return uy_r_subcause_id_2	  */
	public int getuy_r_subcause_id_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_r_subcause_id_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_r_subcause_id_3.
		@param uy_r_subcause_id_3 uy_r_subcause_id_3	  */
	public void setuy_r_subcause_id_3 (int uy_r_subcause_id_3)
	{
		set_Value (COLUMNNAME_uy_r_subcause_id_3, Integer.valueOf(uy_r_subcause_id_3));
	}

	/** Get uy_r_subcause_id_3.
		@return uy_r_subcause_id_3	  */
	public int getuy_r_subcause_id_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_r_subcause_id_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_r_subcause_id_4.
		@param uy_r_subcause_id_4 uy_r_subcause_id_4	  */
	public void setuy_r_subcause_id_4 (int uy_r_subcause_id_4)
	{
		set_Value (COLUMNNAME_uy_r_subcause_id_4, Integer.valueOf(uy_r_subcause_id_4));
	}

	/** Get uy_r_subcause_id_4.
		@return uy_r_subcause_id_4	  */
	public int getuy_r_subcause_id_4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_r_subcause_id_4);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Type getUY_R_Type() throws RuntimeException
    {
		return (I_UY_R_Type)MTable.get(getCtx(), I_UY_R_Type.Table_Name)
			.getPO(getUY_R_Type_ID(), get_TrxName());	}

	/** Set UY_R_Type.
		@param UY_R_Type_ID UY_R_Type	  */
	public void setUY_R_Type_ID (int UY_R_Type_ID)
	{
		if (UY_R_Type_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Type_ID, Integer.valueOf(UY_R_Type_ID));
	}

	/** Get UY_R_Type.
		@return UY_R_Type	  */
	public int getUY_R_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Validez.
		@param Validez Validez	  */
	public void setValidez (String Validez)
	{
		set_Value (COLUMNNAME_Validez, Validez);
	}

	/** Get Validez.
		@return Validez	  */
	public String getValidez () 
	{
		return (String)get_Value(COLUMNNAME_Validez);
	}

	/** Set Vencido.
		@param Vencido Vencido	  */
	public void setVencido (boolean Vencido)
	{
		set_Value (COLUMNNAME_Vencido, Boolean.valueOf(Vencido));
	}

	/** Get Vencido.
		@return Vencido	  */
	public boolean isVencido () 
	{
		Object oo = get_Value(COLUMNNAME_Vencido);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}