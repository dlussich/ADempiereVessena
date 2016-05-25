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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.openup.model.I_UY_POPolicyCategory;
import org.openup.model.I_UY_POPolicyRange;
import org.openup.model.I_UY_POSection;

/** Generated Model for M_Requisition
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_M_Requisition extends PO implements I_M_Requisition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140308L;

    /** Standard Constructor */
    public X_M_Requisition (Properties ctx, int M_Requisition_ID, String trxName)
    {
      super (ctx, M_Requisition_ID, trxName);
      /** if (M_Requisition_ID == 0)
        {
			setAD_User_ID (0);
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDateRequired (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// RQ
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsApproved (false);
			setM_Requisition_ID (0);
			setPosted (false);
			setPriorityRule (null);
// 5
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_M_Requisition (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_M_Requisition[")
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

	/** Set ApprovalDate.
		@param ApprovalDate ApprovalDate	  */
	public void setApprovalDate (Timestamp ApprovalDate)
	{
		set_ValueNoCheck (COLUMNNAME_ApprovalDate, ApprovalDate);
	}

	/** Get ApprovalDate.
		@return ApprovalDate	  */
	public Timestamp getApprovalDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ApprovalDate);
	}

	/** Set ApprovalDescription.
		@param ApprovalDescription ApprovalDescription	  */
	public void setApprovalDescription (String ApprovalDescription)
	{
		set_ValueNoCheck (COLUMNNAME_ApprovalDescription, ApprovalDescription);
	}

	/** Get ApprovalDescription.
		@return ApprovalDescription	  */
	public String getApprovalDescription () 
	{
		return (String)get_Value(COLUMNNAME_ApprovalDescription);
	}

	/** Set ApprovalSeqNo.
		@param ApprovalSeqNo ApprovalSeqNo	  */
	public void setApprovalSeqNo (int ApprovalSeqNo)
	{
		set_Value (COLUMNNAME_ApprovalSeqNo, Integer.valueOf(ApprovalSeqNo));
	}

	/** Get ApprovalSeqNo.
		@return ApprovalSeqNo	  */
	public int getApprovalSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ApprovalSeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ApprovalStatus AD_Reference_ID=1000231 */
	public static final int APPROVALSTATUS_AD_Reference_ID=1000231;
	/** Pendiente Aprobacion Nivel 1 = PENDING1 */
	public static final String APPROVALSTATUS_PendienteAprobacionNivel1 = "PENDING1";
	/** Pendiente Aprobacion Nivel 2 = PENDING2 */
	public static final String APPROVALSTATUS_PendienteAprobacionNivel2 = "PENDING2";
	/** Aprobado Nivel 1 = APPROVED1 */
	public static final String APPROVALSTATUS_AprobadoNivel1 = "APPROVED1";
	/** Aprobado Nivel 2 = APPROVED2 */
	public static final String APPROVALSTATUS_AprobadoNivel2 = "APPROVED2";
	/** Rechazado Nivel 1 = REJECT1 */
	public static final String APPROVALSTATUS_RechazadoNivel1 = "REJECT1";
	/** Rechazado Nivel 2 = REJECT2 */
	public static final String APPROVALSTATUS_RechazadoNivel2 = "REJECT2";
	/** Aprobado por Compras = APPROVEDPO */
	public static final String APPROVALSTATUS_AprobadoPorCompras = "APPROVEDPO";
	/** Pendiente Aprobacion Compras = PENDINGPO */
	public static final String APPROVALSTATUS_PendienteAprobacionCompras = "PENDINGPO";
	/** Rechazado por Compras = REJECTPO */
	public static final String APPROVALSTATUS_RechazadoPorCompras = "REJECTPO";
	/** Nueva = NUEVA */
	public static final String APPROVALSTATUS_Nueva = "NUEVA";
	/** Pendiente Aprobacion Categoria = PENDINGCAT */
	public static final String APPROVALSTATUS_PendienteAprobacionCategoria = "PENDINGCAT";
	/** Aprobado Categoria = APPROVEDCAT */
	public static final String APPROVALSTATUS_AprobadoCategoria = "APPROVEDCAT";
	/** Rechazado Categoria = REJECTCAT */
	public static final String APPROVALSTATUS_RechazadoCategoria = "REJECTCAT";
	/** Set ApprovalStatus.
		@param ApprovalStatus ApprovalStatus	  */
	public void setApprovalStatus (String ApprovalStatus)
	{

		set_ValueNoCheck (COLUMNNAME_ApprovalStatus, ApprovalStatus);
	}

	/** Get ApprovalStatus.
		@return ApprovalStatus	  */
	public String getApprovalStatus () 
	{
		return (String)get_Value(COLUMNNAME_ApprovalStatus);
	}

	/** Set ApprovalUser_ID.
		@param ApprovalUser_ID ApprovalUser_ID	  */
	public void setApprovalUser_ID (int ApprovalUser_ID)
	{
		if (ApprovalUser_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ApprovalUser_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ApprovalUser_ID, Integer.valueOf(ApprovalUser_ID));
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

	/** ApprovedType AD_Reference_ID=1000354 */
	public static final int APPROVEDTYPE_AD_Reference_ID=1000354;
	/** AUTORIZADO = AUTORIZADO */
	public static final String APPROVEDTYPE_AUTORIZADO = "AUTORIZADO";
	/** RECHAZADO = RECHAZADO */
	public static final String APPROVEDTYPE_RECHAZADO = "RECHAZADO";
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

	/** Set CategoryUser_ID.
		@param CategoryUser_ID CategoryUser_ID	  */
	public void setCategoryUser_ID (int CategoryUser_ID)
	{
		if (CategoryUser_ID < 1) 
			set_Value (COLUMNNAME_CategoryUser_ID, null);
		else 
			set_Value (COLUMNNAME_CategoryUser_ID, Integer.valueOf(CategoryUser_ID));
	}

	/** Get CategoryUser_ID.
		@return CategoryUser_ID	  */
	public int getCategoryUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CategoryUser_ID);
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

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
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

	/** Set Date Required.
		@param DateRequired 
		Date when required
	  */
	public void setDateRequired (Timestamp DateRequired)
	{
		set_Value (COLUMNNAME_DateRequired, DateRequired);
	}

	/** Get Date Required.
		@return Date when required
	  */
	public Timestamp getDateRequired () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRequired);
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
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
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

	/** Set IsApproved1.
		@param IsApproved1 IsApproved1	  */
	public void setIsApproved1 (boolean IsApproved1)
	{
		set_Value (COLUMNNAME_IsApproved1, Boolean.valueOf(IsApproved1));
	}

	/** Get IsApproved1.
		@return IsApproved1	  */
	public boolean isApproved1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsApproved2.
		@param IsApproved2 IsApproved2	  */
	public void setIsApproved2 (boolean IsApproved2)
	{
		set_Value (COLUMNNAME_IsApproved2, Boolean.valueOf(IsApproved2));
	}

	/** Get IsApproved2.
		@return IsApproved2	  */
	public boolean isApproved2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsApprovedCategory.
		@param IsApprovedCategory IsApprovedCategory	  */
	public void setIsApprovedCategory (boolean IsApprovedCategory)
	{
		set_Value (COLUMNNAME_IsApprovedCategory, Boolean.valueOf(IsApprovedCategory));
	}

	/** Get IsApprovedCategory.
		@return IsApprovedCategory	  */
	public boolean isApprovedCategory () 
	{
		Object oo = get_Value(COLUMNNAME_IsApprovedCategory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Requisition.
		@param M_Requisition_ID 
		Material Requisition
	  */
	public void setM_Requisition_ID (int M_Requisition_ID)
	{
		if (M_Requisition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Requisition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Requisition_ID, Integer.valueOf(M_Requisition_ID));
	}

	/** Get Requisition.
		@return Material Requisition
	  */
	public int getM_Requisition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Requisition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NeedApprove1.
		@param NeedApprove1 NeedApprove1	  */
	public void setNeedApprove1 (boolean NeedApprove1)
	{
		set_Value (COLUMNNAME_NeedApprove1, Boolean.valueOf(NeedApprove1));
	}

	/** Get NeedApprove1.
		@return NeedApprove1	  */
	public boolean isNeedApprove1 () 
	{
		Object oo = get_Value(COLUMNNAME_NeedApprove1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NeedApprove2.
		@param NeedApprove2 NeedApprove2	  */
	public void setNeedApprove2 (boolean NeedApprove2)
	{
		set_Value (COLUMNNAME_NeedApprove2, Boolean.valueOf(NeedApprove2));
	}

	/** Get NeedApprove2.
		@return NeedApprove2	  */
	public boolean isNeedApprove2 () 
	{
		Object oo = get_Value(COLUMNNAME_NeedApprove2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set nivel.
		@param nivel nivel	  */
	public void setnivel (int nivel)
	{
		set_Value (COLUMNNAME_nivel, Integer.valueOf(nivel));
	}

	/** Get nivel.
		@return nivel	  */
	public int getnivel () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_nivel);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set POUser_ID.
		@param POUser_ID POUser_ID	  */
	public void setPOUser_ID (int POUser_ID)
	{
		if (POUser_ID < 1) 
			set_Value (COLUMNNAME_POUser_ID, null);
		else 
			set_Value (COLUMNNAME_POUser_ID, Integer.valueOf(POUser_ID));
	}

	/** Get POUser_ID.
		@return POUser_ID	  */
	public int getPOUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_POUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PriorityRule AD_Reference_ID=154 */
	public static final int PRIORITYRULE_AD_Reference_ID=154;
	/** Medium = 3 */
	public static final String PRIORITYRULE_Medium = "3";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_Urgent = "1";
	/** Minor = 5 */
	public static final String PRIORITYRULE_Minor = "5";
	/** Low = 4 */
	public static final String PRIORITYRULE_Low = "4";
	/** High = 2 */
	public static final String PRIORITYRULE_High = "2";
	/** Inmediate = 0 */
	public static final String PRIORITYRULE_Inmediate = "0";
	/** Set Priority.
		@param PriorityRule 
		Priority of a document
	  */
	public void setPriorityRule (String PriorityRule)
	{

		set_Value (COLUMNNAME_PriorityRule, PriorityRule);
	}

	/** Get Priority.
		@return Priority of a document
	  */
	public String getPriorityRule () 
	{
		return (String)get_Value(COLUMNNAME_PriorityRule);
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

	public I_UY_POPolicyCategory getUY_POPolicyCategory() throws RuntimeException
    {
		return (I_UY_POPolicyCategory)MTable.get(getCtx(), I_UY_POPolicyCategory.Table_Name)
			.getPO(getUY_POPolicyCategory_ID(), get_TrxName());	}

	/** Set UY_POPolicyCategory.
		@param UY_POPolicyCategory_ID UY_POPolicyCategory	  */
	public void setUY_POPolicyCategory_ID (int UY_POPolicyCategory_ID)
	{
		if (UY_POPolicyCategory_ID < 1) 
			set_Value (COLUMNNAME_UY_POPolicyCategory_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POPolicyCategory_ID, Integer.valueOf(UY_POPolicyCategory_ID));
	}

	/** Get UY_POPolicyCategory.
		@return UY_POPolicyCategory	  */
	public int getUY_POPolicyCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POPolicyCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_POPolicyRange getUY_POPolicyRange() throws RuntimeException
    {
		return (I_UY_POPolicyRange)MTable.get(getCtx(), I_UY_POPolicyRange.Table_Name)
			.getPO(getUY_POPolicyRange_ID(), get_TrxName());	}

	/** Set UY_POPolicyRange.
		@param UY_POPolicyRange_ID UY_POPolicyRange	  */
	public void setUY_POPolicyRange_ID (int UY_POPolicyRange_ID)
	{
		if (UY_POPolicyRange_ID < 1) 
			set_Value (COLUMNNAME_UY_POPolicyRange_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POPolicyRange_ID, Integer.valueOf(UY_POPolicyRange_ID));
	}

	/** Get UY_POPolicyRange.
		@return UY_POPolicyRange	  */
	public int getUY_POPolicyRange_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POPolicyRange_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_POSection getUY_POSection() throws RuntimeException
    {
		return (I_UY_POSection)MTable.get(getCtx(), I_UY_POSection.Table_Name)
			.getPO(getUY_POSection_ID(), get_TrxName());	}

	/** Set UY_POSection.
		@param UY_POSection_ID UY_POSection	  */
	public void setUY_POSection_ID (int UY_POSection_ID)
	{
		if (UY_POSection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_POSection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_POSection_ID, Integer.valueOf(UY_POSection_ID));
	}

	/** Get UY_POSection.
		@return UY_POSection	  */
	public int getUY_POSection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POSection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}