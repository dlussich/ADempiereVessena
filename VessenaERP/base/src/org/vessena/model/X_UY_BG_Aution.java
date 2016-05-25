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

/** Generated Model for UY_BG_Aution
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_Aution extends PO implements I_UY_BG_Aution, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150817L;

    /** Standard Constructor */
    public X_UY_BG_Aution (Properties ctx, int UY_BG_Aution_ID, String trxName)
    {
      super (ctx, UY_BG_Aution_ID, trxName);
      /** if (UY_BG_Aution_ID == 0)
        {
			setDocumentNo (null);
			setUY_BG_Aution_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_Aution (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_Aution[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set amt3.
		@param amt3 amt3	  */
	public void setamt3 (BigDecimal amt3)
	{
		set_Value (COLUMNNAME_amt3, amt3);
	}

	/** Get amt3.
		@return amt3	  */
	public BigDecimal getamt3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt3);
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

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_UY_BG_AutionBid getUY_BG_AutionBid() throws RuntimeException
    {
		return (I_UY_BG_AutionBid)MTable.get(getCtx(), I_UY_BG_AutionBid.Table_Name)
			.getPO(getUY_BG_AutionBid_ID(), get_TrxName());	}

	/** Set UY_BG_AutionBid.
		@param UY_BG_AutionBid_ID UY_BG_AutionBid	  */
	public void setUY_BG_AutionBid_ID (int UY_BG_AutionBid_ID)
	{
		if (UY_BG_AutionBid_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_AutionBid_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_AutionBid_ID, Integer.valueOf(UY_BG_AutionBid_ID));
	}

	/** Get UY_BG_AutionBid.
		@return UY_BG_AutionBid	  */
	public int getUY_BG_AutionBid_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_AutionBid_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BG_Aution.
		@param UY_BG_Aution_ID UY_BG_Aution	  */
	public void setUY_BG_Aution_ID (int UY_BG_Aution_ID)
	{
		if (UY_BG_Aution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_Aution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_Aution_ID, Integer.valueOf(UY_BG_Aution_ID));
	}

	/** Get UY_BG_Aution.
		@return UY_BG_Aution	  */
	public int getUY_BG_Aution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Aution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_AutionReq getUY_BG_AutionReq() throws RuntimeException
    {
		return (I_UY_BG_AutionReq)MTable.get(getCtx(), I_UY_BG_AutionReq.Table_Name)
			.getPO(getUY_BG_AutionReq_ID(), get_TrxName());	}

	/** Set UY_BG_AutionReq.
		@param UY_BG_AutionReq_ID UY_BG_AutionReq	  */
	public void setUY_BG_AutionReq_ID (int UY_BG_AutionReq_ID)
	{
		if (UY_BG_AutionReq_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_AutionReq_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_AutionReq_ID, Integer.valueOf(UY_BG_AutionReq_ID));
	}

	/** Get UY_BG_AutionReq.
		@return UY_BG_AutionReq	  */
	public int getUY_BG_AutionReq_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_AutionReq_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}