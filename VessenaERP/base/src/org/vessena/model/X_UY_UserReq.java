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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_UserReq
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_UserReq extends PO implements I_UY_UserReq, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150618L;

    /** Standard Constructor */
    public X_UY_UserReq (Properties ctx, int UY_UserReq_ID, String trxName)
    {
      super (ctx, UY_UserReq_ID, trxName);
      /** if (UY_UserReq_ID == 0)
        {
			setC_DocType_ID (0);
			setCode (null);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setHaveAttach1 (false);
// N
			setHaveAttach2 (false);
// N
			setHaveAttach3 (false);
// N
			setHaveAttach4 (false);
// N
			setHaveAttach5 (false);
// N
			setHaveAttach6 (false);
// N
			setHaveAttach7 (false);
// N
			setHaveAttach8 (false);
// N
			setHaveAttach9 (false);
// N
			setIsApproved (false);
// N
			setIsSelected (false);
// N
			setName (null);
			setPhone_Ident (false);
// N
			setPhone_Ident_2 (false);
// N
			setProcessed (false);
			setUY_BG_Customer_ID (0);
			setUY_UserReq_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_UserReq (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_UserReq[")
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

	/** Set Address 2.
		@param Address2 
		Address line 2 for this location
	  */
	public void setAddress2 (String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Address 2.
		@return Address line 2 for this location
	  */
	public String getAddress2 () 
	{
		return (String)get_Value(COLUMNNAME_Address2);
	}

	/** ApprovalStatus AD_Reference_ID=1000231 */
	public static final int APPROVALSTATUS_AD_Reference_ID=1000231;
	/** Pendiente Aprobacion Gerente de Area = PENDING1 */
	public static final String APPROVALSTATUS_PendienteAprobacionGerenteDeArea = "PENDING1";
	/** Pendiente Aprobacion Gerente General = PENDING2 */
	public static final String APPROVALSTATUS_PendienteAprobacionGerenteGeneral = "PENDING2";
	/** Aprobado por Gerente de Area = APPROVED1 */
	public static final String APPROVALSTATUS_AprobadoPorGerenteDeArea = "APPROVED1";
	/** Aprobado por Gerente General = APPROVED2 */
	public static final String APPROVALSTATUS_AprobadoPorGerenteGeneral = "APPROVED2";
	/** Rechazado por Gerente de Area = REJECT1 */
	public static final String APPROVALSTATUS_RechazadoPorGerenteDeArea = "REJECT1";
	/** Rechazado por Gerente General = REJECT2 */
	public static final String APPROVALSTATUS_RechazadoPorGerenteGeneral = "REJECT2";
	/** Aprobado por Gerente de Compras = APPROVEDPO */
	public static final String APPROVALSTATUS_AprobadoPorGerenteDeCompras = "APPROVEDPO";
	/** Pendiente Aprobacion Gerente de Compras = PENDINGPO */
	public static final String APPROVALSTATUS_PendienteAprobacionGerenteDeCompras = "PENDINGPO";
	/** Rechazado por Gerente de Compras = REJECTPO */
	public static final String APPROVALSTATUS_RechazadoPorGerenteDeCompras = "REJECTPO";
	/** Pendiente Aprobacion BAGSA = PENDING3 */
	public static final String APPROVALSTATUS_PendienteAprobacionBAGSA = "PENDING3";
	/** Aprobado por BAGSA = APPROVED3 */
	public static final String APPROVALSTATUS_AprobadoPorBAGSA = "APPROVED3";
	/** Rechazado por BAGSA = REJECT3 */
	public static final String APPROVALSTATUS_RechazadoPorBAGSA = "REJECT3";
	/** Set ApprovalStatus.
		@param ApprovalStatus ApprovalStatus	  */
	public void setApprovalStatus (String ApprovalStatus)
	{

		set_Value (COLUMNNAME_ApprovalStatus, ApprovalStatus);
	}

	/** Get ApprovalStatus.
		@return ApprovalStatus	  */
	public String getApprovalStatus () 
	{
		return (String)get_Value(COLUMNNAME_ApprovalStatus);
	}

	/** Set ApprovalText.
		@param ApprovalText ApprovalText	  */
	public void setApprovalText (String ApprovalText)
	{
		set_Value (COLUMNNAME_ApprovalText, ApprovalText);
	}

	/** Get ApprovalText.
		@return ApprovalText	  */
	public String getApprovalText () 
	{
		return (String)get_Value(COLUMNNAME_ApprovalText);
	}

	/** Set ApprovalUser_ID.
		@param ApprovalUser_ID ApprovalUser_ID	  */
	public void setApprovalUser_ID (int ApprovalUser_ID)
	{
		if (ApprovalUser_ID < 1) 
			set_Value (COLUMNNAME_ApprovalUser_ID, null);
		else 
			set_Value (COLUMNNAME_ApprovalUser_ID, Integer.valueOf(ApprovalUser_ID));
	}

	/** Get ApprovalUser_ID.
		@return ApprovalUser_ID	  */
	public int getApprovalUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ApprovalUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ApprovedType AD_Reference_ID=1000393 */
	public static final int APPROVEDTYPE_AD_Reference_ID=1000393;
	/** AUTORIZADO = AUTORIZADO */
	public static final String APPROVEDTYPE_AUTORIZADO = "AUTORIZADO";
	/** RECHAZADO = RECHAZADO */
	public static final String APPROVEDTYPE_RECHAZADO = "RECHAZADO";
	/** APROBADO = APROBADO */
	public static final String APPROVEDTYPE_APROBADO = "APROBADO";
	/** Set ApprovedType.
		@param ApprovedType ApprovedType	  */
	public void setApprovedType (String ApprovedType)
	{

		set_Value (COLUMNNAME_ApprovedType, ApprovedType);
	}

	/** Get ApprovedType.
		@return ApprovedType	  */
	public String getApprovedType () 
	{
		return (String)get_Value(COLUMNNAME_ApprovedType);
	}

	/** Set Aproved_ebagsa.
		@param Aproved_ebagsa 
		Aproved_ebagsa
	  */
	public void setAproved_ebagsa (boolean Aproved_ebagsa)
	{
		set_Value (COLUMNNAME_Aproved_ebagsa, Boolean.valueOf(Aproved_ebagsa));
	}

	/** Get Aproved_ebagsa.
		@return Aproved_ebagsa
	  */
	public boolean isAproved_ebagsa () 
	{
		Object oo = get_Value(COLUMNNAME_Aproved_ebagsa);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public org.compiere.model.I_C_City getC_City() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getC_City_ID(), get_TrxName());	}

	/** Set City.
		@param C_City_ID 
		City
	  */
	public void setC_City_ID (int C_City_ID)
	{
		if (C_City_ID < 1) 
			set_Value (COLUMNNAME_C_City_ID, null);
		else 
			set_Value (COLUMNNAME_C_City_ID, Integer.valueOf(C_City_ID));
	}

	/** Get City.
		@return City
	  */
	public int getC_City_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID);
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

	/** Set Validation code.
		@param Code 
		Validation Code
	  */
	public void setCode (String Code)
	{
		set_Value (COLUMNNAME_Code, Code);
	}

	/** Get Validation code.
		@return Validation Code
	  */
	public String getCode () 
	{
		return (String)get_Value(COLUMNNAME_Code);
	}

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
    {
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getC_Region_ID(), get_TrxName());	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateApproved.
		@param DateApproved DateApproved	  */
	public void setDateApproved (Timestamp DateApproved)
	{
		set_Value (COLUMNNAME_DateApproved, DateApproved);
	}

	/** Get DateApproved.
		@return DateApproved	  */
	public Timestamp getDateApproved () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateApproved);
	}

	/** Set DateRequested.
		@param DateRequested DateRequested	  */
	public void setDateRequested (Timestamp DateRequested)
	{
		set_Value (COLUMNNAME_DateRequested, DateRequested);
	}

	/** Get DateRequested.
		@return DateRequested	  */
	public Timestamp getDateRequested () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRequested);
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

	/** Set Email2.
		@param Email2 Email2	  */
	public void setEmail2 (String Email2)
	{
		set_Value (COLUMNNAME_Email2, Email2);
	}

	/** Get Email2.
		@return Email2	  */
	public String getEmail2 () 
	{
		return (String)get_Value(COLUMNNAME_Email2);
	}

	/** Set FirstName.
		@param FirstName FirstName	  */
	public void setFirstName (String FirstName)
	{
		set_Value (COLUMNNAME_FirstName, FirstName);
	}

	/** Get FirstName.
		@return FirstName	  */
	public String getFirstName () 
	{
		return (String)get_Value(COLUMNNAME_FirstName);
	}

	/** Set FirstSurname.
		@param FirstSurname FirstSurname	  */
	public void setFirstSurname (String FirstSurname)
	{
		set_Value (COLUMNNAME_FirstSurname, FirstSurname);
	}

	/** Get FirstSurname.
		@return FirstSurname	  */
	public String getFirstSurname () 
	{
		return (String)get_Value(COLUMNNAME_FirstSurname);
	}

	/** GrantType AD_Reference_ID=1000503 */
	public static final int GRANTTYPE_AD_Reference_ID=1000503;
	/** POR SOCIEDAD = SOCIEDAD */
	public static final String GRANTTYPE_PORSOCIEDAD = "SOCIEDAD";
	/** POR CONCESION = CONCESION */
	public static final String GRANTTYPE_PORCONCESION = "CONCESION";
	/** Set GrantType.
		@param GrantType GrantType	  */
	public void setGrantType (String GrantType)
	{

		set_Value (COLUMNNAME_GrantType, GrantType);
	}

	/** Get GrantType.
		@return GrantType	  */
	public String getGrantType () 
	{
		return (String)get_Value(COLUMNNAME_GrantType);
	}

	/** Set HaveAttach1.
		@param HaveAttach1 HaveAttach1	  */
	public void setHaveAttach1 (boolean HaveAttach1)
	{
		set_Value (COLUMNNAME_HaveAttach1, Boolean.valueOf(HaveAttach1));
	}

	/** Get HaveAttach1.
		@return HaveAttach1	  */
	public boolean isHaveAttach1 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HaveAttach2.
		@param HaveAttach2 HaveAttach2	  */
	public void setHaveAttach2 (boolean HaveAttach2)
	{
		set_Value (COLUMNNAME_HaveAttach2, Boolean.valueOf(HaveAttach2));
	}

	/** Get HaveAttach2.
		@return HaveAttach2	  */
	public boolean isHaveAttach2 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HaveAttach3.
		@param HaveAttach3 HaveAttach3	  */
	public void setHaveAttach3 (boolean HaveAttach3)
	{
		set_Value (COLUMNNAME_HaveAttach3, Boolean.valueOf(HaveAttach3));
	}

	/** Get HaveAttach3.
		@return HaveAttach3	  */
	public boolean isHaveAttach3 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach3);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HaveAttach4.
		@param HaveAttach4 HaveAttach4	  */
	public void setHaveAttach4 (boolean HaveAttach4)
	{
		set_Value (COLUMNNAME_HaveAttach4, Boolean.valueOf(HaveAttach4));
	}

	/** Get HaveAttach4.
		@return HaveAttach4	  */
	public boolean isHaveAttach4 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach4);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HaveAttach5.
		@param HaveAttach5 HaveAttach5	  */
	public void setHaveAttach5 (boolean HaveAttach5)
	{
		set_Value (COLUMNNAME_HaveAttach5, Boolean.valueOf(HaveAttach5));
	}

	/** Get HaveAttach5.
		@return HaveAttach5	  */
	public boolean isHaveAttach5 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach5);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HaveAttach6.
		@param HaveAttach6 HaveAttach6	  */
	public void setHaveAttach6 (boolean HaveAttach6)
	{
		set_Value (COLUMNNAME_HaveAttach6, Boolean.valueOf(HaveAttach6));
	}

	/** Get HaveAttach6.
		@return HaveAttach6	  */
	public boolean isHaveAttach6 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach6);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HaveAttach7.
		@param HaveAttach7 HaveAttach7	  */
	public void setHaveAttach7 (boolean HaveAttach7)
	{
		set_Value (COLUMNNAME_HaveAttach7, Boolean.valueOf(HaveAttach7));
	}

	/** Get HaveAttach7.
		@return HaveAttach7	  */
	public boolean isHaveAttach7 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach7);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HaveAttach8.
		@param HaveAttach8 HaveAttach8	  */
	public void setHaveAttach8 (boolean HaveAttach8)
	{
		set_Value (COLUMNNAME_HaveAttach8, Boolean.valueOf(HaveAttach8));
	}

	/** Get HaveAttach8.
		@return HaveAttach8	  */
	public boolean isHaveAttach8 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach8);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HaveAttach9.
		@param HaveAttach9 HaveAttach9	  */
	public void setHaveAttach9 (boolean HaveAttach9)
	{
		set_Value (COLUMNNAME_HaveAttach9, Boolean.valueOf(HaveAttach9));
	}

	/** Get HaveAttach9.
		@return HaveAttach9	  */
	public boolean isHaveAttach9 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveAttach9);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** PersonGrantType AD_Reference_ID=1000504 */
	public static final int PERSONGRANTTYPE_AD_Reference_ID=1000504;
	/** NATURAL = NATURAL */
	public static final String PERSONGRANTTYPE_NATURAL = "NATURAL";
	/** JURIDICA = JURIDICA */
	public static final String PERSONGRANTTYPE_JURIDICA = "JURIDICA";
	/** OTRAS = OTRAS */
	public static final String PERSONGRANTTYPE_OTRAS = "OTRAS";
	/** Set PersonGrantType.
		@param PersonGrantType PersonGrantType	  */
	public void setPersonGrantType (String PersonGrantType)
	{

		set_Value (COLUMNNAME_PersonGrantType, PersonGrantType);
	}

	/** Get PersonGrantType.
		@return PersonGrantType	  */
	public String getPersonGrantType () 
	{
		return (String)get_Value(COLUMNNAME_PersonGrantType);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set Phone_2.
		@param Phone_2 Phone_2	  */
	public void setPhone_2 (String Phone_2)
	{
		set_Value (COLUMNNAME_Phone_2, Phone_2);
	}

	/** Get Phone_2.
		@return Phone_2	  */
	public String getPhone_2 () 
	{
		return (String)get_Value(COLUMNNAME_Phone_2);
	}

	/** Set Phone_Ident.
		@param Phone_Ident Phone_Ident	  */
	public void setPhone_Ident (boolean Phone_Ident)
	{
		set_Value (COLUMNNAME_Phone_Ident, Boolean.valueOf(Phone_Ident));
	}

	/** Get Phone_Ident.
		@return Phone_Ident	  */
	public boolean isPhone_Ident () 
	{
		Object oo = get_Value(COLUMNNAME_Phone_Ident);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Phone_Ident_2.
		@param Phone_Ident_2 Phone_Ident_2	  */
	public void setPhone_Ident_2 (boolean Phone_Ident_2)
	{
		set_Value (COLUMNNAME_Phone_Ident_2, Boolean.valueOf(Phone_Ident_2));
	}

	/** Get Phone_Ident_2.
		@return Phone_Ident_2	  */
	public boolean isPhone_Ident_2 () 
	{
		Object oo = get_Value(COLUMNNAME_Phone_Ident_2);
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

	/** Set RequestedUser_ID.
		@param RequestedUser_ID RequestedUser_ID	  */
	public void setRequestedUser_ID (int RequestedUser_ID)
	{
		if (RequestedUser_ID < 1) 
			set_Value (COLUMNNAME_RequestedUser_ID, null);
		else 
			set_Value (COLUMNNAME_RequestedUser_ID, Integer.valueOf(RequestedUser_ID));
	}

	/** Get RequestedUser_ID.
		@return RequestedUser_ID	  */
	public int getRequestedUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RequestedUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RUC.
		@param RUC RUC	  */
	public void setRUC (String RUC)
	{
		set_Value (COLUMNNAME_RUC, RUC);
	}

	/** Get RUC.
		@return RUC	  */
	public String getRUC () 
	{
		return (String)get_Value(COLUMNNAME_RUC);
	}

	/** Set SecondName.
		@param SecondName SecondName	  */
	public void setSecondName (String SecondName)
	{
		set_Value (COLUMNNAME_SecondName, SecondName);
	}

	/** Get SecondName.
		@return SecondName	  */
	public String getSecondName () 
	{
		return (String)get_Value(COLUMNNAME_SecondName);
	}

	/** Set SecondSurname.
		@param SecondSurname SecondSurname	  */
	public void setSecondSurname (String SecondSurname)
	{
		set_Value (COLUMNNAME_SecondSurname, SecondSurname);
	}

	/** Get SecondSurname.
		@return SecondSurname	  */
	public String getSecondSurname () 
	{
		return (String)get_Value(COLUMNNAME_SecondSurname);
	}

	public I_UY_BG_Bursa getUY_BG_Bursa() throws RuntimeException
    {
		return (I_UY_BG_Bursa)MTable.get(getCtx(), I_UY_BG_Bursa.Table_Name)
			.getPO(getUY_BG_Bursa_ID(), get_TrxName());	}

	/** Set UY_BG_Bursa_ID.
		@param UY_BG_Bursa_ID UY_BG_Bursa_ID	  */
	public void setUY_BG_Bursa_ID (int UY_BG_Bursa_ID)
	{
		if (UY_BG_Bursa_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Bursa_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Bursa_ID, Integer.valueOf(UY_BG_Bursa_ID));
	}

	/** Get UY_BG_Bursa_ID.
		@return UY_BG_Bursa_ID	  */
	public int getUY_BG_Bursa_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Bursa_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_Customer getUY_BG_Customer() throws RuntimeException
    {
		return (I_UY_BG_Customer)MTable.get(getCtx(), I_UY_BG_Customer.Table_Name)
			.getPO(getUY_BG_Customer_ID(), get_TrxName());	}

	/** Set UY_BG_Customer.
		@param UY_BG_Customer_ID UY_BG_Customer	  */
	public void setUY_BG_Customer_ID (int UY_BG_Customer_ID)
	{
		if (UY_BG_Customer_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Customer_ID, Integer.valueOf(UY_BG_Customer_ID));
	}

	/** Get UY_BG_Customer.
		@return UY_BG_Customer	  */
	public int getUY_BG_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_UserActivity getUY_BG_UserActivity() throws RuntimeException
    {
		return (I_UY_BG_UserActivity)MTable.get(getCtx(), I_UY_BG_UserActivity.Table_Name)
			.getPO(getUY_BG_UserActivity_ID(), get_TrxName());	}

	/** Set UY_BG_UserActivity.
		@param UY_BG_UserActivity_ID UY_BG_UserActivity	  */
	public void setUY_BG_UserActivity_ID (int UY_BG_UserActivity_ID)
	{
		if (UY_BG_UserActivity_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_UserActivity_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_UserActivity_ID, Integer.valueOf(UY_BG_UserActivity_ID));
	}

	/** Get UY_BG_UserActivity.
		@return UY_BG_UserActivity	  */
	public int getUY_BG_UserActivity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_UserActivity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_UserReq.
		@param UY_UserReq_ID UY_UserReq	  */
	public void setUY_UserReq_ID (int UY_UserReq_ID)
	{
		if (UY_UserReq_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_UserReq_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_UserReq_ID, Integer.valueOf(UY_UserReq_ID));
	}

	/** Get UY_UserReq.
		@return UY_UserReq	  */
	public int getUY_UserReq_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_UserReq_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}