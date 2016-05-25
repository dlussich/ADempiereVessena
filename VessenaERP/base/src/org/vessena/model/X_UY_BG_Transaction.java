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

/** Generated Model for UY_BG_Transaction
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_Transaction extends PO implements I_UY_BG_Transaction, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150624L;

    /** Standard Constructor */
    public X_UY_BG_Transaction (Properties ctx, int UY_BG_Transaction_ID, String trxName)
    {
      super (ctx, UY_BG_Transaction_ID, trxName);
      /** if (UY_BG_Transaction_ID == 0)
        {
			setHaveSign1 (false);
// N
			setUY_BG_Bursa_ID (0);
// @SQL=(select uy_bg_bursa_id from uy_bg_bursa where ad_user_id =@#AD_User_ID@)
			setUY_BG_Transaction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_Transaction (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_Transaction[")
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

	/** Set AmtRetention.
		@param AmtRetention AmtRetention	  */
	public void setAmtRetention (BigDecimal AmtRetention)
	{
		set_Value (COLUMNNAME_AmtRetention, AmtRetention);
	}

	/** Get AmtRetention.
		@return AmtRetention	  */
	public BigDecimal getAmtRetention () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtRetention);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtRetention2.
		@param AmtRetention2 AmtRetention2	  */
	public void setAmtRetention2 (BigDecimal AmtRetention2)
	{
		set_Value (COLUMNNAME_AmtRetention2, AmtRetention2);
	}

	/** Get AmtRetention2.
		@return AmtRetention2	  */
	public BigDecimal getAmtRetention2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtRetention2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Bodega.
		@param Bodega Bodega	  */
	public void setBodega (String Bodega)
	{
		set_Value (COLUMNNAME_Bodega, Bodega);
	}

	/** Get Bodega.
		@return Bodega	  */
	public String getBodega () 
	{
		return (String)get_Value(COLUMNNAME_Bodega);
	}

	/** Set bp_name.
		@param bp_name bp_name	  */
	public void setbp_name (String bp_name)
	{
		set_Value (COLUMNNAME_bp_name, bp_name);
	}

	/** Get bp_name.
		@return bp_name	  */
	public String getbp_name () 
	{
		return (String)get_Value(COLUMNNAME_bp_name);
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

	/** Set C_City_ID_2.
		@param C_City_ID_2 C_City_ID_2	  */
	public void setC_City_ID_2 (int C_City_ID_2)
	{
		set_Value (COLUMNNAME_C_City_ID_2, Integer.valueOf(C_City_ID_2));
	}

	/** Get C_City_ID_2.
		@return C_City_ID_2	  */
	public int getC_City_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID_2);
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

	/** Set C_Region_ID_2.
		@param C_Region_ID_2 C_Region_ID_2	  */
	public void setC_Region_ID_2 (int C_Region_ID_2)
	{
		set_Value (COLUMNNAME_C_Region_ID_2, Integer.valueOf(C_Region_ID_2));
	}

	/** Get C_Region_ID_2.
		@return C_Region_ID_2	  */
	public int getC_Region_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateApproved.
		@param DateApproved DateApproved	  */
	public void setDateApproved (Timestamp DateApproved)
	{
		set_ValueNoCheck (COLUMNNAME_DateApproved, DateApproved);
	}

	/** Get DateApproved.
		@return DateApproved	  */
	public Timestamp getDateApproved () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateApproved);
	}

	/** Set Date Delivered.
		@param DateDelivered 
		Date when the product was delivered
	  */
	public void setDateDelivered (Timestamp DateDelivered)
	{
		set_Value (COLUMNNAME_DateDelivered, DateDelivered);
	}

	/** Get Date Delivered.
		@return Date when the product was delivered
	  */
	public Timestamp getDateDelivered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDelivered);
	}

	/** Set Date printed.
		@param DatePrinted 
		Date the document was printed.
	  */
	public void setDatePrinted (Timestamp DatePrinted)
	{
		set_Value (COLUMNNAME_DatePrinted, DatePrinted);
	}

	/** Get Date printed.
		@return Date the document was printed.
	  */
	public Timestamp getDatePrinted () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePrinted);
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

	/** Set HaveSign1.
		@param HaveSign1 HaveSign1	  */
	public void setHaveSign1 (boolean HaveSign1)
	{
		set_Value (COLUMNNAME_HaveSign1, Boolean.valueOf(HaveSign1));
	}

	/** Get HaveSign1.
		@return HaveSign1	  */
	public boolean isHaveSign1 () 
	{
		Object oo = get_Value(COLUMNNAME_HaveSign1);
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

	/** LocationFrom AD_Reference_ID=1000508 */
	public static final int LOCATIONFROM_AD_Reference_ID=1000508;
	/** Nacional = 1-Nacional */
	public static final String LOCATIONFROM_Nacional = "1-Nacional";
	/** Extranjero = 2-Extranjero */
	public static final String LOCATIONFROM_Extranjero = "2-Extranjero";
	/** Set LocationFrom.
		@param LocationFrom LocationFrom	  */
	public void setLocationFrom (String LocationFrom)
	{

		set_Value (COLUMNNAME_LocationFrom, LocationFrom);
	}

	/** Get LocationFrom.
		@return LocationFrom	  */
	public String getLocationFrom () 
	{
		return (String)get_Value(COLUMNNAME_LocationFrom);
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

	/** paymentruletype AD_Reference_ID=1000194 */
	public static final int PAYMENTRULETYPE_AD_Reference_ID=1000194;
	/** Contado = CO */
	public static final String PAYMENTRULETYPE_Contado = "CO";
	/** Credito = CR */
	public static final String PAYMENTRULETYPE_Credito = "CR";
	/** Set paymentruletype.
		@param paymentruletype paymentruletype	  */
	public void setpaymentruletype (String paymentruletype)
	{

		set_Value (COLUMNNAME_paymentruletype, paymentruletype);
	}

	/** Get paymentruletype.
		@return paymentruletype	  */
	public String getpaymentruletype () 
	{
		return (String)get_Value(COLUMNNAME_paymentruletype);
	}

	/** Set Price.
		@param PriceEntered 
		Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Price.
		@return Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** ProjectType AD_Reference_ID=1000517 */
	public static final int PROJECTTYPE_AD_Reference_ID=1000517;
	/** Compra = 1- Compra */
	public static final String PROJECTTYPE_Compra = "1- Compra";
	/** Venta = 2- Venta */
	public static final String PROJECTTYPE_Venta = "2- Venta";
	/** Set ProjectType.
		@param ProjectType 
		Type of Attribute Value
	  */
	public void setProjectType (String ProjectType)
	{

		set_Value (COLUMNNAME_ProjectType, ProjectType);
	}

	/** Get ProjectType.
		@return Type of Attribute Value
	  */
	public String getProjectType () 
	{
		return (String)get_Value(COLUMNNAME_ProjectType);
	}

	/** Set RequestedUser_ID.
		@param RequestedUser_ID RequestedUser_ID	  */
	public void setRequestedUser_ID (int RequestedUser_ID)
	{
		if (RequestedUser_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_RequestedUser_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_RequestedUser_ID, Integer.valueOf(RequestedUser_ID));
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

	public I_UY_BG_Bursa getUY_BG_Bursa() throws RuntimeException
    {
		return (I_UY_BG_Bursa)MTable.get(getCtx(), I_UY_BG_Bursa.Table_Name)
			.getPO(getUY_BG_Bursa_ID(), get_TrxName());	}

	/** Set UY_BG_Bursa.
		@param UY_BG_Bursa_ID UY_BG_Bursa	  */
	public void setUY_BG_Bursa_ID (int UY_BG_Bursa_ID)
	{
		if (UY_BG_Bursa_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Bursa_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Bursa_ID, Integer.valueOf(UY_BG_Bursa_ID));
	}

	/** Get UY_BG_Bursa.
		@return UY_BG_Bursa	  */
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

	public I_UY_BG_PackingMode getUY_BG_PackingMode() throws RuntimeException
    {
		return (I_UY_BG_PackingMode)MTable.get(getCtx(), I_UY_BG_PackingMode.Table_Name)
			.getPO(getUY_BG_PackingMode_ID(), get_TrxName());	}

	/** Set UY_BG_PackingMode.
		@param UY_BG_PackingMode_ID UY_BG_PackingMode	  */
	public void setUY_BG_PackingMode_ID (int UY_BG_PackingMode_ID)
	{
		if (UY_BG_PackingMode_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_PackingMode_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_PackingMode_ID, Integer.valueOf(UY_BG_PackingMode_ID));
	}

	/** Get UY_BG_PackingMode.
		@return UY_BG_PackingMode	  */
	public int getUY_BG_PackingMode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_PackingMode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_Renta getUY_BG_Renta() throws RuntimeException
    {
		return (I_UY_BG_Renta)MTable.get(getCtx(), I_UY_BG_Renta.Table_Name)
			.getPO(getUY_BG_Renta_ID(), get_TrxName());	}

	/** Set UY_BG_Renta.
		@param UY_BG_Renta_ID UY_BG_Renta	  */
	public void setUY_BG_Renta_ID (int UY_BG_Renta_ID)
	{
		if (UY_BG_Renta_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Renta_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Renta_ID, Integer.valueOf(UY_BG_Renta_ID));
	}

	/** Get UY_BG_Renta.
		@return UY_BG_Renta	  */
	public int getUY_BG_Renta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Renta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BG_Transaction.
		@param UY_BG_Transaction_ID UY_BG_Transaction	  */
	public void setUY_BG_Transaction_ID (int UY_BG_Transaction_ID)
	{
		if (UY_BG_Transaction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_Transaction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_Transaction_ID, Integer.valueOf(UY_BG_Transaction_ID));
	}

	/** Get UY_BG_Transaction.
		@return UY_BG_Transaction	  */
	public int getUY_BG_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Transaction_ID);
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
}