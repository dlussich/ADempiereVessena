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

/** Generated Model for UY_TR_Mic
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Mic extends PO implements I_UY_TR_Mic, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150516L;

    /** Standard Constructor */
    public X_UY_TR_Mic (Properties ctx, int UY_TR_Mic_ID, String trxName)
    {
      super (ctx, UY_TR_Mic_ID, trxName);
      /** if (UY_TR_Mic_ID == 0)
        {
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
			setDocStatus (null);
			setDocumentNo (null);
			setIsDrop (false);
// N
			setIsLastre (false);
// N
			setIsManual (false);
// N
			setIsValid (false);
			setMicStatus (null);
// 'SINENVIAR'
			setProcessed (false);
			setTractor_ID (0);
			setUY_TR_Mic_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Mic (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Mic[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AduanaDestino.
		@param AduanaDestino AduanaDestino	  */
	public void setAduanaDestino (String AduanaDestino)
	{
		set_Value (COLUMNNAME_AduanaDestino, AduanaDestino);
	}

	/** Get AduanaDestino.
		@return AduanaDestino	  */
	public String getAduanaDestino () 
	{
		return (String)get_Value(COLUMNNAME_AduanaDestino);
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

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
    {
		return (org.compiere.model.I_C_Country)MTable.get(getCtx(), org.compiere.model.I_C_Country.Table_Name)
			.getPO(getC_Country_ID(), get_TrxName());	}

	/** Set Country.
		@param C_Country_ID 
		Country 
	  */
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Country.
		@return Country 
	  */
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Country_ID_1.
		@param C_Country_ID_1 C_Country_ID_1	  */
	public void setC_Country_ID_1 (int C_Country_ID_1)
	{
		set_Value (COLUMNNAME_C_Country_ID_1, Integer.valueOf(C_Country_ID_1));
	}

	/** Get C_Country_ID_1.
		@return C_Country_ID_1	  */
	public int getC_Country_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID_1);
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

	/** Set ContadorPais.
		@param ContadorPais ContadorPais	  */
	public void setContadorPais (int ContadorPais)
	{
		set_Value (COLUMNNAME_ContadorPais, Integer.valueOf(ContadorPais));
	}

	/** Get ContadorPais.
		@return ContadorPais	  */
	public int getContadorPais () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ContadorPais);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Copies.
		@param Copies Copies	  */
	public void setCopies (BigDecimal Copies)
	{
		set_Value (COLUMNNAME_Copies, Copies);
	}

	/** Get Copies.
		@return Copies	  */
	public BigDecimal getCopies () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Copies);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CrtImgNum1.
		@param CrtImgNum1 CrtImgNum1	  */
	public void setCrtImgNum1 (String CrtImgNum1)
	{
		set_Value (COLUMNNAME_CrtImgNum1, CrtImgNum1);
	}

	/** Get CrtImgNum1.
		@return CrtImgNum1	  */
	public String getCrtImgNum1 () 
	{
		return (String)get_Value(COLUMNNAME_CrtImgNum1);
	}

	/** CrtImgStatus1 AD_Reference_ID=1000445 */
	public static final int CRTIMGSTATUS1_AD_Reference_ID=1000445;
	/** SINCARGAR = SINCARGAR */
	public static final String CRTIMGSTATUS1_SINCARGAR = "SINCARGAR";
	/** CARGADO = CARGADO */
	public static final String CRTIMGSTATUS1_CARGADO = "CARGADO";
	/** Set CrtImgStatus1.
		@param CrtImgStatus1 CrtImgStatus1	  */
	public void setCrtImgStatus1 (String CrtImgStatus1)
	{

		set_Value (COLUMNNAME_CrtImgStatus1, CrtImgStatus1);
	}

	/** Get CrtImgStatus1.
		@return CrtImgStatus1	  */
	public String getCrtImgStatus1 () 
	{
		return (String)get_Value(COLUMNNAME_CrtImgStatus1);
	}

	/** CrtLineStatus1 AD_Reference_ID=1000446 */
	public static final int CRTLINESTATUS1_AD_Reference_ID=1000446;
	/** SINCARGAR = SINCARGAR */
	public static final String CRTLINESTATUS1_SINCARGAR = "SINCARGAR";
	/** CARGADO = CARGADO */
	public static final String CRTLINESTATUS1_CARGADO = "CARGADO";
	/** Set CrtLineStatus1.
		@param CrtLineStatus1 CrtLineStatus1	  */
	public void setCrtLineStatus1 (String CrtLineStatus1)
	{

		set_Value (COLUMNNAME_CrtLineStatus1, CrtLineStatus1);
	}

	/** Get CrtLineStatus1.
		@return CrtLineStatus1	  */
	public String getCrtLineStatus1 () 
	{
		return (String)get_Value(COLUMNNAME_CrtLineStatus1);
	}

	/** CrtStatus1 AD_Reference_ID=1000444 */
	public static final int CRTSTATUS1_AD_Reference_ID=1000444;
	/** VINCULADO = VINCULADO */
	public static final String CRTSTATUS1_VINCULADO = "VINCULADO";
	/** ENALTA = ENALTA */
	public static final String CRTSTATUS1_ENALTA = "ENALTA";
	/** ENMODIFICACION = ENMODIFICACION */
	public static final String CRTSTATUS1_ENMODIFICACION = "ENMODIFICACION";
	/** ENBAJA = ENBAJA */
	public static final String CRTSTATUS1_ENBAJA = "ENBAJA";
	/** DESVINCULADO = DESVINCULADO */
	public static final String CRTSTATUS1_DESVINCULADO = "DESVINCULADO";
	/** Set CrtStatus1.
		@param CrtStatus1 CrtStatus1	  */
	public void setCrtStatus1 (String CrtStatus1)
	{

		set_Value (COLUMNNAME_CrtStatus1, CrtStatus1);
	}

	/** Get CrtStatus1.
		@return CrtStatus1	  */
	public String getCrtStatus1 () 
	{
		return (String)get_Value(COLUMNNAME_CrtStatus1);
	}

	/** Set DateAction.
		@param DateAction DateAction	  */
	public void setDateAction (Timestamp DateAction)
	{
		set_Value (COLUMNNAME_DateAction, DateAction);
	}

	/** Get DateAction.
		@return DateAction	  */
	public Timestamp getDateAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAction);
	}

	/** Set Finish Date.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	/** Get Finish Date.
		@return Finish or (planned) completion date
	  */
	public Timestamp getDateFinish () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFinish);
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

	/** Set Diference.
		@param Diference Diference	  */
	public void setDiference (int Diference)
	{
		set_Value (COLUMNNAME_Diference, Integer.valueOf(Diference));
	}

	/** Get Diference.
		@return Diference	  */
	public int getDiference () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Diference);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set D-U-N-S.
		@param DUNS 
		Dun & Bradstreet Number
	  */
	public void setDUNS (String DUNS)
	{
		set_Value (COLUMNNAME_DUNS, DUNS);
	}

	/** Get D-U-N-S.
		@return Dun & Bradstreet Number
	  */
	public String getDUNS () 
	{
		return (String)get_Value(COLUMNNAME_DUNS);
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

	/** Set Importe.
		@param Importe Importe	  */
	public void setImporte (BigDecimal Importe)
	{
		set_Value (COLUMNNAME_Importe, Importe);
	}

	/** Get Importe.
		@return Importe	  */
	public BigDecimal getImporte () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Importe);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set InProcess.
		@param InProcess InProcess	  */
	public void setInProcess (boolean InProcess)
	{
		set_Value (COLUMNNAME_InProcess, Boolean.valueOf(InProcess));
	}

	/** Get InProcess.
		@return InProcess	  */
	public boolean isInProcess () 
	{
		Object oo = get_Value(COLUMNNAME_InProcess);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set IsDrop.
		@param IsDrop IsDrop	  */
	public void setIsDrop (boolean IsDrop)
	{
		set_Value (COLUMNNAME_IsDrop, Boolean.valueOf(IsDrop));
	}

	/** Get IsDrop.
		@return IsDrop	  */
	public boolean isDrop () 
	{
		Object oo = get_Value(COLUMNNAME_IsDrop);
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

	/** Set IsRemolque.
		@param IsRemolque IsRemolque	  */
	public void setIsRemolque (boolean IsRemolque)
	{
		set_Value (COLUMNNAME_IsRemolque, Boolean.valueOf(IsRemolque));
	}

	/** Get IsRemolque.
		@return IsRemolque	  */
	public boolean isRemolque () 
	{
		Object oo = get_Value(COLUMNNAME_IsRemolque);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRemolque2.
		@param IsRemolque2 IsRemolque2	  */
	public void setIsRemolque2 (boolean IsRemolque2)
	{
		set_Value (COLUMNNAME_IsRemolque2, Boolean.valueOf(IsRemolque2));
	}

	/** Get IsRemolque2.
		@return IsRemolque2	  */
	public boolean isRemolque2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsRemolque2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSemiRemolque.
		@param IsSemiRemolque IsSemiRemolque	  */
	public void setIsSemiRemolque (boolean IsSemiRemolque)
	{
		set_Value (COLUMNNAME_IsSemiRemolque, Boolean.valueOf(IsSemiRemolque));
	}

	/** Get IsSemiRemolque.
		@return IsSemiRemolque	  */
	public boolean isSemiRemolque () 
	{
		Object oo = get_Value(COLUMNNAME_IsSemiRemolque);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSemiRemolque2.
		@param IsSemiRemolque2 IsSemiRemolque2	  */
	public void setIsSemiRemolque2 (boolean IsSemiRemolque2)
	{
		set_Value (COLUMNNAME_IsSemiRemolque2, Boolean.valueOf(IsSemiRemolque2));
	}

	/** Get IsSemiRemolque2.
		@return IsSemiRemolque2	  */
	public boolean isSemiRemolque2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsSemiRemolque2);
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

	/** MicStatus AD_Reference_ID=1000443 */
	public static final int MICSTATUS_AD_Reference_ID=1000443;
	/** SINENVIAR = SINENVIAR */
	public static final String MICSTATUS_SINENVIAR = "SINENVIAR";
	/** ENVIADO = ENVIADO */
	public static final String MICSTATUS_ENVIADO = "ENVIADO";
	/** MODIFICAR = MODIFICAR */
	public static final String MICSTATUS_MODIFICAR = "MODIFICAR";
	/** ELIMINAR = ELIMINAR */
	public static final String MICSTATUS_ELIMINAR = "ELIMINAR";
	/** Set MicStatus.
		@param MicStatus MicStatus	  */
	public void setMicStatus (String MicStatus)
	{

		set_Value (COLUMNNAME_MicStatus, MicStatus);
	}

	/** Get MicStatus.
		@return MicStatus	  */
	public String getMicStatus () 
	{
		return (String)get_Value(COLUMNNAME_MicStatus);
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

	/** Set NroDNA.
		@param NroDNA NroDNA	  */
	public void setNroDNA (String NroDNA)
	{
		set_Value (COLUMNNAME_NroDNA, NroDNA);
	}

	/** Get NroDNA.
		@return NroDNA	  */
	public String getNroDNA () 
	{
		return (String)get_Value(COLUMNNAME_NroDNA);
	}

	/** Set NroMic.
		@param NroMic NroMic	  */
	public void setNroMic (String NroMic)
	{
		set_Value (COLUMNNAME_NroMic, NroMic);
	}

	/** Get NroMic.
		@return NroMic	  */
	public String getNroMic () 
	{
		return (String)get_Value(COLUMNNAME_NroMic);
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

	/** Set OriginalTruck.
		@param OriginalTruck OriginalTruck	  */
	public void setOriginalTruck (String OriginalTruck)
	{
		set_Value (COLUMNNAME_OriginalTruck, OriginalTruck);
	}

	/** Get OriginalTruck.
		@return OriginalTruck	  */
	public String getOriginalTruck () 
	{
		return (String)get_Value(COLUMNNAME_OriginalTruck);
	}

	/** Set Permiso.
		@param Permiso Permiso	  */
	public void setPermiso (String Permiso)
	{
		set_Value (COLUMNNAME_Permiso, Permiso);
	}

	/** Get Permiso.
		@return Permiso	  */
	public String getPermiso () 
	{
		return (String)get_Value(COLUMNNAME_Permiso);
	}

	/** Set pesoBruto.
		@param pesoBruto pesoBruto	  */
	public void setpesoBruto (BigDecimal pesoBruto)
	{
		set_Value (COLUMNNAME_pesoBruto, pesoBruto);
	}

	/** Get pesoBruto.
		@return pesoBruto	  */
	public BigDecimal getpesoBruto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoBruto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set pesoNeto.
		@param pesoNeto pesoNeto	  */
	public void setpesoNeto (BigDecimal pesoNeto)
	{
		set_Value (COLUMNNAME_pesoNeto, pesoNeto);
	}

	/** Get pesoNeto.
		@return pesoNeto	  */
	public BigDecimal getpesoNeto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoNeto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set precinto.
		@param precinto precinto	  */
	public void setprecinto (String precinto)
	{
		set_Value (COLUMNNAME_precinto, precinto);
	}

	/** Get precinto.
		@return precinto	  */
	public String getprecinto () 
	{
		return (String)get_Value(COLUMNNAME_precinto);
	}

	/** Set Precinto2.
		@param Precinto2 Precinto2	  */
	public void setPrecinto2 (String Precinto2)
	{
		set_Value (COLUMNNAME_Precinto2, Precinto2);
	}

	/** Get Precinto2.
		@return Precinto2	  */
	public String getPrecinto2 () 
	{
		return (String)get_Value(COLUMNNAME_Precinto2);
	}

	/** Set PrintDoc.
		@param PrintDoc PrintDoc	  */
	public void setPrintDoc (String PrintDoc)
	{
		set_Value (COLUMNNAME_PrintDoc, PrintDoc);
	}

	/** Get PrintDoc.
		@return PrintDoc	  */
	public String getPrintDoc () 
	{
		return (String)get_Value(COLUMNNAME_PrintDoc);
	}

	/** Set Format Type.
		@param PrintFormatType 
		Print Format Type
	  */
	public void setPrintFormatType (String PrintFormatType)
	{
		set_Value (COLUMNNAME_PrintFormatType, PrintFormatType);
	}

	/** Get Format Type.
		@return Print Format Type
	  */
	public String getPrintFormatType () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType);
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

	/** Set Recinto.
		@param Recinto Recinto	  */
	public void setRecinto (String Recinto)
	{
		set_Value (COLUMNNAME_Recinto, Recinto);
	}

	/** Get Recinto.
		@return Recinto	  */
	public String getRecinto () 
	{
		return (String)get_Value(COLUMNNAME_Recinto);
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

	public I_UY_TR_Truck getRemolque() throws RuntimeException
    {
		return (I_UY_TR_Truck)MTable.get(getCtx(), I_UY_TR_Truck.Table_Name)
			.getPO(getRemolque_ID(), get_TrxName());	}

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

	/** Set Rut1.
		@param Rut1 Rut1	  */
	public void setRut1 (String Rut1)
	{
		set_Value (COLUMNNAME_Rut1, Rut1);
	}

	/** Get Rut1.
		@return Rut1	  */
	public String getRut1 () 
	{
		return (String)get_Value(COLUMNNAME_Rut1);
	}

	/** Set Rut2.
		@param Rut2 Rut2	  */
	public void setRut2 (String Rut2)
	{
		set_Value (COLUMNNAME_Rut2, Rut2);
	}

	/** Get Rut2.
		@return Rut2	  */
	public String getRut2 () 
	{
		return (String)get_Value(COLUMNNAME_Rut2);
	}

	/** Set SecNoCrt.
		@param SecNoCrt SecNoCrt	  */
	public void setSecNoCrt (String SecNoCrt)
	{
		set_Value (COLUMNNAME_SecNoCrt, SecNoCrt);
	}

	/** Get SecNoCrt.
		@return SecNoCrt	  */
	public String getSecNoCrt () 
	{
		return (String)get_Value(COLUMNNAME_SecNoCrt);
	}

	/** Set SecNoImg1.
		@param SecNoImg1 SecNoImg1	  */
	public void setSecNoImg1 (String SecNoImg1)
	{
		set_Value (COLUMNNAME_SecNoImg1, SecNoImg1);
	}

	/** Get SecNoImg1.
		@return SecNoImg1	  */
	public String getSecNoImg1 () 
	{
		return (String)get_Value(COLUMNNAME_SecNoImg1);
	}

	/** Set SecNoLine1.
		@param SecNoLine1 SecNoLine1	  */
	public void setSecNoLine1 (String SecNoLine1)
	{
		set_Value (COLUMNNAME_SecNoLine1, SecNoLine1);
	}

	/** Get SecNoLine1.
		@return SecNoLine1	  */
	public String getSecNoLine1 () 
	{
		return (String)get_Value(COLUMNNAME_SecNoLine1);
	}

	/** Set Seguro.
		@param Seguro Seguro	  */
	public void setSeguro (BigDecimal Seguro)
	{
		set_Value (COLUMNNAME_Seguro, Seguro);
	}

	/** Get Seguro.
		@return Seguro	  */
	public BigDecimal getSeguro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Seguro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set sheet.
		@param sheet sheet	  */
	public void setsheet (int sheet)
	{
		set_Value (COLUMNNAME_sheet, Integer.valueOf(sheet));
	}

	/** Get sheet.
		@return sheet	  */
	public int getsheet () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_sheet);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SubstituteTruck.
		@param SubstituteTruck SubstituteTruck	  */
	public void setSubstituteTruck (String SubstituteTruck)
	{
		set_Value (COLUMNNAME_SubstituteTruck, SubstituteTruck);
	}

	/** Get SubstituteTruck.
		@return SubstituteTruck	  */
	public String getSubstituteTruck () 
	{
		return (String)get_Value(COLUMNNAME_SubstituteTruck);
	}

	public I_UY_TR_Truck getTractor() throws RuntimeException
    {
		return (I_UY_TR_Truck)MTable.get(getCtx(), I_UY_TR_Truck.Table_Name)
			.getPO(getTractor_ID(), get_TrxName());	}

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

	/** Set Transaction Code.
		@param TransactionCode 
		The transaction code represents the search definition
	  */
	public void setTransactionCode (String TransactionCode)
	{
		set_Value (COLUMNNAME_TransactionCode, TransactionCode);
	}

	/** Get Transaction Code.
		@return The transaction code represents the search definition
	  */
	public String getTransactionCode () 
	{
		return (String)get_Value(COLUMNNAME_TransactionCode);
	}

	/** Set TruckData.
		@param TruckData TruckData	  */
	public void setTruckData (String TruckData)
	{
		set_Value (COLUMNNAME_TruckData, TruckData);
	}

	/** Get TruckData.
		@return TruckData	  */
	public String getTruckData () 
	{
		return (String)get_Value(COLUMNNAME_TruckData);
	}

	/** Type AD_Reference_ID=1000438 */
	public static final int TYPE_AD_Reference_ID=1000438;
	/** INGRESO = 0 */
	public static final String TYPE_INGRESO = "0";
	/** EGRESO = 1 */
	public static final String TYPE_EGRESO = "1";
	/** DE PASO = 2 */
	public static final String TYPE_DEPASO = "2";
	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
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

	/** Set UY_Ciudad_ID_2.
		@param UY_Ciudad_ID_2 UY_Ciudad_ID_2	  */
	public void setUY_Ciudad_ID_2 (int UY_Ciudad_ID_2)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_2, Integer.valueOf(UY_Ciudad_ID_2));
	}

	/** Get UY_Ciudad_ID_2.
		@return UY_Ciudad_ID_2	  */
	public int getUY_Ciudad_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_3.
		@param UY_Ciudad_ID_3 UY_Ciudad_ID_3	  */
	public void setUY_Ciudad_ID_3 (int UY_Ciudad_ID_3)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_3, Integer.valueOf(UY_Ciudad_ID_3));
	}

	/** Get UY_Ciudad_ID_3.
		@return UY_Ciudad_ID_3	  */
	public int getUY_Ciudad_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_4.
		@param UY_Ciudad_ID_4 UY_Ciudad_ID_4	  */
	public void setUY_Ciudad_ID_4 (int UY_Ciudad_ID_4)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_4, Integer.valueOf(UY_Ciudad_ID_4));
	}

	/** Get UY_Ciudad_ID_4.
		@return UY_Ciudad_ID_4	  */
	public int getUY_Ciudad_ID_4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_4);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_5.
		@param UY_Ciudad_ID_5 UY_Ciudad_ID_5	  */
	public void setUY_Ciudad_ID_5 (int UY_Ciudad_ID_5)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_5, Integer.valueOf(UY_Ciudad_ID_5));
	}

	/** Get UY_Ciudad_ID_5.
		@return UY_Ciudad_ID_5	  */
	public int getUY_Ciudad_ID_5 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_5);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Departamentos getUY_Departamentos() throws RuntimeException
    {
		return (I_UY_Departamentos)MTable.get(getCtx(), I_UY_Departamentos.Table_Name)
			.getPO(getUY_Departamentos_ID(), get_TrxName());	}

	/** Set Departamentos o regiones por Pais.
		@param UY_Departamentos_ID Departamentos o regiones por Pais	  */
	public void setUY_Departamentos_ID (int UY_Departamentos_ID)
	{
		if (UY_Departamentos_ID < 1) 
			set_Value (COLUMNNAME_UY_Departamentos_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Departamentos_ID, Integer.valueOf(UY_Departamentos_ID));
	}

	/** Get Departamentos o regiones por Pais.
		@return Departamentos o regiones por Pais	  */
	public int getUY_Departamentos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Departamentos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Departamentos_ID_1.
		@param UY_Departamentos_ID_1 UY_Departamentos_ID_1	  */
	public void setUY_Departamentos_ID_1 (int UY_Departamentos_ID_1)
	{
		set_Value (COLUMNNAME_UY_Departamentos_ID_1, Integer.valueOf(UY_Departamentos_ID_1));
	}

	/** Get UY_Departamentos_ID_1.
		@return UY_Departamentos_ID_1	  */
	public int getUY_Departamentos_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Departamentos_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Aduana getUY_TR_Aduana() throws RuntimeException
    {
		return (I_UY_TR_Aduana)MTable.get(getCtx(), I_UY_TR_Aduana.Table_Name)
			.getPO(getUY_TR_Aduana_ID(), get_TrxName());	}

	/** Set UY_TR_Aduana.
		@param UY_TR_Aduana_ID UY_TR_Aduana	  */
	public void setUY_TR_Aduana_ID (int UY_TR_Aduana_ID)
	{
		if (UY_TR_Aduana_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Aduana_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Aduana_ID, Integer.valueOf(UY_TR_Aduana_ID));
	}

	/** Get UY_TR_Aduana.
		@return UY_TR_Aduana	  */
	public int getUY_TR_Aduana_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Aduana_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Aduana_ID_1.
		@param UY_TR_Aduana_ID_1 UY_TR_Aduana_ID_1	  */
	public void setUY_TR_Aduana_ID_1 (int UY_TR_Aduana_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Aduana_ID_1, Integer.valueOf(UY_TR_Aduana_ID_1));
	}

	/** Get UY_TR_Aduana_ID_1.
		@return UY_TR_Aduana_ID_1	  */
	public int getUY_TR_Aduana_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Aduana_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Aduana_ID_2.
		@param UY_TR_Aduana_ID_2 UY_TR_Aduana_ID_2	  */
	public void setUY_TR_Aduana_ID_2 (int UY_TR_Aduana_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_Aduana_ID_2, Integer.valueOf(UY_TR_Aduana_ID_2));
	}

	/** Get UY_TR_Aduana_ID_2.
		@return UY_TR_Aduana_ID_2	  */
	public int getUY_TR_Aduana_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Aduana_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Aduana_ID_3.
		@param UY_TR_Aduana_ID_3 UY_TR_Aduana_ID_3	  */
	public void setUY_TR_Aduana_ID_3 (int UY_TR_Aduana_ID_3)
	{
		set_Value (COLUMNNAME_UY_TR_Aduana_ID_3, Integer.valueOf(UY_TR_Aduana_ID_3));
	}

	/** Get UY_TR_Aduana_ID_3.
		@return UY_TR_Aduana_ID_3	  */
	public int getUY_TR_Aduana_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Aduana_ID_3);
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

	/** Set UY_TR_Border_ID_2.
		@param UY_TR_Border_ID_2 UY_TR_Border_ID_2	  */
	public void setUY_TR_Border_ID_2 (int UY_TR_Border_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_Border_ID_2, Integer.valueOf(UY_TR_Border_ID_2));
	}

	/** Get UY_TR_Border_ID_2.
		@return UY_TR_Border_ID_2	  */
	public int getUY_TR_Border_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Border_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
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

	public I_UY_TR_Mark getUY_TR_Mark() throws RuntimeException
    {
		return (I_UY_TR_Mark)MTable.get(getCtx(), I_UY_TR_Mark.Table_Name)
			.getPO(getUY_TR_Mark_ID(), get_TrxName());	}

	/** Set UY_TR_Mark.
		@param UY_TR_Mark_ID UY_TR_Mark	  */
	public void setUY_TR_Mark_ID (int UY_TR_Mark_ID)
	{
		if (UY_TR_Mark_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Mark_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Mark_ID, Integer.valueOf(UY_TR_Mark_ID));
	}

	/** Get UY_TR_Mark.
		@return UY_TR_Mark	  */
	public int getUY_TR_Mark_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Mark_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Mark_ID_1.
		@param UY_TR_Mark_ID_1 UY_TR_Mark_ID_1	  */
	public void setUY_TR_Mark_ID_1 (int UY_TR_Mark_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Mark_ID_1, Integer.valueOf(UY_TR_Mark_ID_1));
	}

	/** Get UY_TR_Mark_ID_1.
		@return UY_TR_Mark_ID_1	  */
	public int getUY_TR_Mark_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Mark_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Mic.
		@param UY_TR_Mic_ID UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID)
	{
		if (UY_TR_Mic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Mic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Mic_ID, Integer.valueOf(UY_TR_Mic_ID));
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

	public I_UY_TR_OperativeSite getUY_TR_OperativeSite() throws RuntimeException
    {
		return (I_UY_TR_OperativeSite)MTable.get(getCtx(), I_UY_TR_OperativeSite.Table_Name)
			.getPO(getUY_TR_OperativeSite_ID(), get_TrxName());	}

	/** Set UY_TR_OperativeSite.
		@param UY_TR_OperativeSite_ID UY_TR_OperativeSite	  */
	public void setUY_TR_OperativeSite_ID (int UY_TR_OperativeSite_ID)
	{
		if (UY_TR_OperativeSite_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_OperativeSite_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_OperativeSite_ID, Integer.valueOf(UY_TR_OperativeSite_ID));
	}

	/** Get UY_TR_OperativeSite.
		@return UY_TR_OperativeSite	  */
	public int getUY_TR_OperativeSite_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_OperativeSite_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_OperativeSite_ID_1.
		@param UY_TR_OperativeSite_ID_1 UY_TR_OperativeSite_ID_1	  */
	public void setUY_TR_OperativeSite_ID_1 (int UY_TR_OperativeSite_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_OperativeSite_ID_1, Integer.valueOf(UY_TR_OperativeSite_ID_1));
	}

	/** Get UY_TR_OperativeSite_ID_1.
		@return UY_TR_OperativeSite_ID_1	  */
	public int getUY_TR_OperativeSite_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_OperativeSite_ID_1);
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

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException
    {
		return (I_UY_TR_TransOrder)MTable.get(getCtx(), I_UY_TR_TransOrder.Table_Name)
			.getPO(getUY_TR_TransOrder_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrder.
		@param UY_TR_TransOrder_ID UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID)
	{
		if (UY_TR_TransOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, Integer.valueOf(UY_TR_TransOrder_ID));
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

	public I_UY_TR_TransOrder getUY_TR_TransOrder_I() throws RuntimeException
    {
		return (I_UY_TR_TransOrder)MTable.get(getCtx(), I_UY_TR_TransOrder.Table_Name)
			.getPO(getUY_TR_TransOrder_ID_1(), get_TrxName());	}

	/** Set UY_TR_TransOrder_ID_1.
		@param UY_TR_TransOrder_ID_1 UY_TR_TransOrder_ID_1	  */
	public void setUY_TR_TransOrder_ID_1 (int UY_TR_TransOrder_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_TransOrder_ID_1, Integer.valueOf(UY_TR_TransOrder_ID_1));
	}

	/** Get UY_TR_TransOrder_ID_1.
		@return UY_TR_TransOrder_ID_1	  */
	public int getUY_TR_TransOrder_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrder_ID_1);
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

	/** Set UY_TR_Truck_ID_1.
		@param UY_TR_Truck_ID_1 UY_TR_Truck_ID_1	  */
	public void setUY_TR_Truck_ID_1 (int UY_TR_Truck_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_1, Integer.valueOf(UY_TR_Truck_ID_1));
	}

	/** Get UY_TR_Truck_ID_1.
		@return UY_TR_Truck_ID_1	  */
	public int getUY_TR_Truck_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck_ID_2.
		@param UY_TR_Truck_ID_2 UY_TR_Truck_ID_2	  */
	public void setUY_TR_Truck_ID_2 (int UY_TR_Truck_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_2, Integer.valueOf(UY_TR_Truck_ID_2));
	}

	/** Get UY_TR_Truck_ID_2.
		@return UY_TR_Truck_ID_2	  */
	public int getUY_TR_Truck_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck_ID_3.
		@param UY_TR_Truck_ID_3 UY_TR_Truck_ID_3	  */
	public void setUY_TR_Truck_ID_3 (int UY_TR_Truck_ID_3)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_3, Integer.valueOf(UY_TR_Truck_ID_3));
	}

	/** Get UY_TR_Truck_ID_3.
		@return UY_TR_Truck_ID_3	  */
	public int getUY_TR_Truck_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set YearFrom.
		@param YearFrom YearFrom	  */
	public void setYearFrom (String YearFrom)
	{
		set_Value (COLUMNNAME_YearFrom, YearFrom);
	}

	/** Get YearFrom.
		@return YearFrom	  */
	public String getYearFrom () 
	{
		return (String)get_Value(COLUMNNAME_YearFrom);
	}

	/** Set YearTo.
		@param YearTo YearTo	  */
	public void setYearTo (String YearTo)
	{
		set_Value (COLUMNNAME_YearTo, YearTo);
	}

	/** Get YearTo.
		@return YearTo	  */
	public String getYearTo () 
	{
		return (String)get_Value(COLUMNNAME_YearTo);
	}
}