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

/** Generated Model for UY_CFE_InboxLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_InboxLine extends PO implements I_UY_CFE_InboxLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160110L;

    /** Standard Constructor */
    public X_UY_CFE_InboxLine (Properties ctx, int UY_CFE_InboxLine_ID, String trxName)
    {
      super (ctx, UY_CFE_InboxLine_ID, trxName);
      /** if (UY_CFE_InboxLine_ID == 0)
        {
			setDGIStatus (null);
// PENDIENTE
			setIsSelected (true);
// Y
			setPrintDoc (false);
// N
			setSourceType (null);
			setUY_CFE_Inbox_ID (0);
			setUY_CFE_InboxLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_InboxLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_InboxLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address 1.
		@param Address1 
		Address line 1 for this location
	  */
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Address 1.
		@return Address line 1 for this location
	  */
	public String getAddress1 () 
	{
		return (String)get_Value(COLUMNNAME_Address1);
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

	/** Set CfeType.
		@param CfeType CfeType	  */
	public void setCfeType (String CfeType)
	{
		set_Value (COLUMNNAME_CfeType, CfeType);
	}

	/** Get CfeType.
		@return CfeType	  */
	public String getCfeType () 
	{
		return (String)get_Value(COLUMNNAME_CfeType);
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

	/** Set DateCFE.
		@param DateCFE DateCFE	  */
	public void setDateCFE (Timestamp DateCFE)
	{
		set_Value (COLUMNNAME_DateCFE, DateCFE);
	}

	/** Get DateCFE.
		@return DateCFE	  */
	public Timestamp getDateCFE () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateCFE);
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

	/** DGIStatus AD_Reference_ID=1010393 */
	public static final int DGISTATUS_AD_Reference_ID=1010393;
	/** ENVIADO DGI = ENVIADO */
	public static final String DGISTATUS_ENVIADODGI = "ENVIADO";
	/** PROCESADO DGI = PROCESADO */
	public static final String DGISTATUS_PROCESADODGI = "PROCESADO";
	/** PENDIENTE ENVIO = PENDIENTE */
	public static final String DGISTATUS_PENDIENTEENVIO = "PENDIENTE";
	/** Set DGIStatus.
		@param DGIStatus DGIStatus	  */
	public void setDGIStatus (String DGIStatus)
	{

		set_Value (COLUMNNAME_DGIStatus, DGIStatus);
	}

	/** Get DGIStatus.
		@return DGIStatus	  */
	public String getDGIStatus () 
	{
		return (String)get_Value(COLUMNNAME_DGIStatus);
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

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set NroCAE.
		@param NroCAE NroCAE	  */
	public void setNroCAE (String NroCAE)
	{
		set_Value (COLUMNNAME_NroCAE, NroCAE);
	}

	/** Get NroCAE.
		@return NroCAE	  */
	public String getNroCAE () 
	{
		return (String)get_Value(COLUMNNAME_NroCAE);
	}

	/** Set NroCFE.
		@param NroCFE NroCFE	  */
	public void setNroCFE (String NroCFE)
	{
		set_Value (COLUMNNAME_NroCFE, NroCFE);
	}

	/** Get NroCFE.
		@return NroCFE	  */
	public String getNroCFE () 
	{
		return (String)get_Value(COLUMNNAME_NroCFE);
	}

	/** Set nrodoc.
		@param nrodoc nrodoc	  */
	public void setnrodoc (String nrodoc)
	{
		set_Value (COLUMNNAME_nrodoc, nrodoc);
	}

	/** Get nrodoc.
		@return nrodoc	  */
	public String getnrodoc () 
	{
		return (String)get_Value(COLUMNNAME_nrodoc);
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

	/** Set PrintDoc.
		@param PrintDoc PrintDoc	  */
	public void setPrintDoc (boolean PrintDoc)
	{
		set_Value (COLUMNNAME_PrintDoc, Boolean.valueOf(PrintDoc));
	}

	/** Get PrintDoc.
		@return PrintDoc	  */
	public boolean isPrintDoc () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** SourceType AD_Reference_ID=1010394 */
	public static final int SOURCETYPE_AD_Reference_ID=1010394;
	/** EXCEL = EXCEL */
	public static final String SOURCETYPE_EXCEL = "EXCEL";
	/** VISTA = VISTA */
	public static final String SOURCETYPE_VISTA = "VISTA";
	/** MANUAL = MANUAL */
	public static final String SOURCETYPE_MANUAL = "MANUAL";
	/** Set SourceType.
		@param SourceType SourceType	  */
	public void setSourceType (String SourceType)
	{

		set_Value (COLUMNNAME_SourceType, SourceType);
	}

	/** Get SourceType.
		@return SourceType	  */
	public String getSourceType () 
	{
		return (String)get_Value(COLUMNNAME_SourceType);
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_CFE_Inbox getUY_CFE_Inbox() throws RuntimeException
    {
		return (I_UY_CFE_Inbox)MTable.get(getCtx(), I_UY_CFE_Inbox.Table_Name)
			.getPO(getUY_CFE_Inbox_ID(), get_TrxName());	}

	/** Set UY_CFE_Inbox.
		@param UY_CFE_Inbox_ID UY_CFE_Inbox	  */
	public void setUY_CFE_Inbox_ID (int UY_CFE_Inbox_ID)
	{
		if (UY_CFE_Inbox_ID < 1) 
			set_Value (COLUMNNAME_UY_CFE_Inbox_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CFE_Inbox_ID, Integer.valueOf(UY_CFE_Inbox_ID));
	}

	/** Get UY_CFE_Inbox.
		@return UY_CFE_Inbox	  */
	public int getUY_CFE_Inbox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_Inbox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CFE_InboxLine.
		@param UY_CFE_InboxLine_ID UY_CFE_InboxLine	  */
	public void setUY_CFE_InboxLine_ID (int UY_CFE_InboxLine_ID)
	{
		if (UY_CFE_InboxLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InboxLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InboxLine_ID, Integer.valueOf(UY_CFE_InboxLine_ID));
	}

	/** Get UY_CFE_InboxLine.
		@return UY_CFE_InboxLine	  */
	public int getUY_CFE_InboxLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InboxLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CFE_InboxLoad getUY_CFE_InboxLoad() throws RuntimeException
    {
		return (I_UY_CFE_InboxLoad)MTable.get(getCtx(), I_UY_CFE_InboxLoad.Table_Name)
			.getPO(getUY_CFE_InboxLoad_ID(), get_TrxName());	}

	/** Set UY_CFE_InboxLoad.
		@param UY_CFE_InboxLoad_ID UY_CFE_InboxLoad	  */
	public void setUY_CFE_InboxLoad_ID (int UY_CFE_InboxLoad_ID)
	{
		if (UY_CFE_InboxLoad_ID < 1) 
			set_Value (COLUMNNAME_UY_CFE_InboxLoad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CFE_InboxLoad_ID, Integer.valueOf(UY_CFE_InboxLoad_ID));
	}

	/** Get UY_CFE_InboxLoad.
		@return UY_CFE_InboxLoad	  */
	public int getUY_CFE_InboxLoad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InboxLoad_ID);
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

	public I_UY_Localidades getUY_Localidades() throws RuntimeException
    {
		return (I_UY_Localidades)MTable.get(getCtx(), I_UY_Localidades.Table_Name)
			.getPO(getUY_Localidades_ID(), get_TrxName());	}

	/** Set Localidades por Departamentos.
		@param UY_Localidades_ID Localidades por Departamentos	  */
	public void setUY_Localidades_ID (int UY_Localidades_ID)
	{
		if (UY_Localidades_ID < 1) 
			set_Value (COLUMNNAME_UY_Localidades_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Localidades_ID, Integer.valueOf(UY_Localidades_ID));
	}

	/** Get Localidades por Departamentos.
		@return Localidades por Departamentos	  */
	public int getUY_Localidades_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Localidades_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}