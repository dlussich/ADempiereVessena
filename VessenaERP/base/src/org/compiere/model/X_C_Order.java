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
import org.openup.model.I_UY_CanalVentas;
import org.openup.model.I_UY_Distribuidor;
import org.openup.model.I_UY_Fletero;
import org.openup.model.I_UY_POPolicy;
import org.openup.model.I_UY_POPolicyRange;
import org.openup.model.I_UY_QuoteVendor;
import org.openup.model.I_UY_RFQ_MacroReq;

/** Generated Model for C_Order
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_Order extends PO implements I_C_Order, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140216L;

    /** Standard Constructor */
    public X_C_Order (Properties ctx, int C_Order_ID, String trxName)
    {
      super (ctx, C_Order_ID, trxName);
      /** if (C_Order_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Currency_ID (0);
// @C_Currency_ID@
			setC_DocType_ID (0);
// 0
			setC_DocTypeTarget_ID (0);
			setC_Order_ID (0);
			setC_PaymentTerm_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDateOrdered (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDeliveryRule (null);
// M
			setDeliveryViaRule (null);
// P
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setFreightCostRule (null);
// I
			setGrandTotal (Env.ZERO);
			setInvoiceRule (null);
// I
			setIsApproved (false);
// N
			setIsCreditApproved (false);
			setIsDelivered (false);
// N
			setIsDiscountPrinted (false);
// N
			setIsDropShip (false);
// N
			setIsInvoiced (false);
// N
			setIsPrinted (false);
// N
			setIsSelected (false);
// N
			setIsSelfService (false);
// N
			setIsSOTrx (false);
// @IsSOTrx@
			setIsTaxIncluded (false);
// N
			setIsTransferred (false);
// N
			setPaymentRule (null);
// B
			setPosted (false);
// N
			setPriorityRule (null);
// 5
			setProcessed (false);
			setSendEMail (false);
// N
			setTotalLines (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_Order (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Order[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set AmountRefunded.
		@param AmountRefunded AmountRefunded	  */
	public void setAmountRefunded (BigDecimal AmountRefunded)
	{
		set_Value (COLUMNNAME_AmountRefunded, AmountRefunded);
	}

	/** Get AmountRefunded.
		@return AmountRefunded	  */
	public BigDecimal getAmountRefunded () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountRefunded);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmountTendered.
		@param AmountTendered AmountTendered	  */
	public void setAmountTendered (BigDecimal AmountTendered)
	{
		set_Value (COLUMNNAME_AmountTendered, AmountTendered);
	}

	/** Get AmountTendered.
		@return AmountTendered	  */
	public BigDecimal getAmountTendered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountTendered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
		set_Value (COLUMNNAME_ApprovalDescription, ApprovalDescription);
	}

	/** Get ApprovalDescription.
		@return ApprovalDescription	  */
	public String getApprovalDescription () 
	{
		return (String)get_Value(COLUMNNAME_ApprovalDescription);
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

	/** Set ApprovedBy.
		@param ApprovedBy ApprovedBy	  */
	public void setApprovedBy (int ApprovedBy)
	{
		set_Value (COLUMNNAME_ApprovedBy, Integer.valueOf(ApprovedBy));
	}

	/** Get ApprovedBy.
		@return ApprovedBy	  */
	public int getApprovedBy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ApprovedBy);
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

	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getBill_BPartner_ID(), get_TrxName());	}

	/** Set Invoice Partner.
		@param Bill_BPartner_ID 
		Business Partner to be invoiced
	  */
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Invoice Partner.
		@return Business Partner to be invoiced
	  */
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getBill_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getBill_Location_ID(), get_TrxName());	}

	/** Set Invoice Location.
		@param Bill_Location_ID 
		Business Partner Location for invoicing
	  */
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	/** Get Invoice Location.
		@return Business Partner Location for invoicing
	  */
	public int getBill_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getBill_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getBill_User_ID(), get_TrxName());	}

	/** Set Invoice Contact.
		@param Bill_User_ID 
		Business Partner Contact for invoicing
	  */
	public void setBill_User_ID (int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Integer.valueOf(Bill_User_ID));
	}

	/** Get Invoice Contact.
		@return Business Partner Contact for invoicing
	  */
	public int getBill_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CardNumber.
		@param CardNumber CardNumber	  */
	public void setCardNumber (String CardNumber)
	{
		set_Value (COLUMNNAME_CardNumber, CardNumber);
	}

	/** Get CardNumber.
		@return CardNumber	  */
	public String getCardNumber () 
	{
		return (String)get_Value(COLUMNNAME_CardNumber);
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
    {
		return (org.compiere.model.I_C_Campaign)MTable.get(getCtx(), org.compiere.model.I_C_Campaign.Table_Name)
			.getPO(getC_Campaign_ID(), get_TrxName());	}

	/** Set Campaign.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Campaign.
		@return Marketing Campaign
	  */
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_CashLine getC_CashLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_CashLine)MTable.get(getCtx(), org.compiere.model.I_C_CashLine.Table_Name)
			.getPO(getC_CashLine_ID(), get_TrxName());	}

	/** Set Cash Journal Line.
		@param C_CashLine_ID 
		Cash Journal Line
	  */
	public void setC_CashLine_ID (int C_CashLine_ID)
	{
		if (C_CashLine_ID < 1) 
			set_Value (COLUMNNAME_C_CashLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashLine_ID, Integer.valueOf(C_CashLine_ID));
	}

	/** Get Cash Journal Line.
		@return Cash Journal Line
	  */
	public int getC_CashLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException
    {
		return (org.compiere.model.I_C_ConversionType)MTable.get(getCtx(), org.compiere.model.I_C_ConversionType.Table_Name)
			.getPO(getC_ConversionType_ID(), get_TrxName());	}

	/** Set Currency Type.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Currency Type.
		@return Currency Conversion Rate Type
	  */
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocTypeTarget_ID(), get_TrxName());	}

	/** Set Target Document Type.
		@param C_DocTypeTarget_ID 
		Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, Integer.valueOf(C_DocTypeTarget_ID));
	}

	/** Get Target Document Type.
		@return Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeTarget_ID);
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

	/** Set Charge amount.
		@param ChargeAmt 
		Charge Amount
	  */
	public void setChargeAmt (BigDecimal ChargeAmt)
	{
		set_Value (COLUMNNAME_ChargeAmt, ChargeAmt);
	}

	/** Get Charge amount.
		@return Charge Amount
	  */
	public BigDecimal getChargeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ChargeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Copy From.
		@param CopyFrom 
		Copy From Record
	  */
	public void setCopyFrom (String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	/** Get Copy From.
		@return Copy From Record
	  */
	public String getCopyFrom () 
	{
		return (String)get_Value(COLUMNNAME_CopyFrom);
	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_OrderSource getC_OrderSource() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderSource)MTable.get(getCtx(), org.compiere.model.I_C_OrderSource.Table_Name)
			.getPO(getC_OrderSource_ID(), get_TrxName());	}

	/** Set Order Source.
		@param C_OrderSource_ID Order Source	  */
	public void setC_OrderSource_ID (int C_OrderSource_ID)
	{
		if (C_OrderSource_ID < 1) 
			set_Value (COLUMNNAME_C_OrderSource_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderSource_ID, Integer.valueOf(C_OrderSource_ID));
	}

	/** Get Order Source.
		@return Order Source	  */
	public int getC_OrderSource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderSource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
    {
		return (org.compiere.model.I_C_Payment)MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
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

	public org.compiere.model.I_C_POS getC_POS() throws RuntimeException
    {
		return (org.compiere.model.I_C_POS)MTable.get(getCtx(), org.compiere.model.I_C_POS.Table_Name)
			.getPO(getC_POS_ID(), get_TrxName());	}

	/** Set POS Terminal.
		@param C_POS_ID 
		Point of Sales Terminal
	  */
	public void setC_POS_ID (int C_POS_ID)
	{
		if (C_POS_ID < 1) 
			set_Value (COLUMNNAME_C_POS_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_ID, Integer.valueOf(C_POS_ID));
	}

	/** Get POS Terminal.
		@return Point of Sales Terminal
	  */
	public int getC_POS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
    {
		return (org.compiere.model.I_C_Project)MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Date Ordered.
		@param DateOrdered 
		Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Date Ordered.
		@return Date of Order
	  */
	public Timestamp getDateOrdered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOrdered);
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

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
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

	/** DeliveryRule AD_Reference_ID=151 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** After Receipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** Complete Line = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** Complete Order = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
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

	/** DeliveryViaRule AD_Reference_ID=152 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Set Delivery Via.
		@param DeliveryViaRule 
		How the order will be delivered
	  */
	public void setDeliveryViaRule (String DeliveryViaRule)
	{

		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Delivery Via.
		@return How the order will be delivered
	  */
	public String getDeliveryViaRule () 
	{
		return (String)get_Value(COLUMNNAME_DeliveryViaRule);
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

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
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

	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getDropShip_BPartner_ID(), get_TrxName());	}

	/** Set Drop Shipment Partner.
		@param DropShip_BPartner_ID 
		Business Partner to ship to
	  */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, Integer.valueOf(DropShip_BPartner_ID));
	}

	/** Get Drop Shipment Partner.
		@return Business Partner to ship to
	  */
	public int getDropShip_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getDropShip_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getDropShip_Location_ID(), get_TrxName());	}

	/** Set Drop Shipment Location.
		@param DropShip_Location_ID 
		Business Partner Location for shipping to
	  */
	public void setDropShip_Location_ID (int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, Integer.valueOf(DropShip_Location_ID));
	}

	/** Get Drop Shipment Location.
		@return Business Partner Location for shipping to
	  */
	public int getDropShip_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getDropShip_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getDropShip_User_ID(), get_TrxName());	}

	/** Set Drop Shipment Contact.
		@param DropShip_User_ID 
		Business Partner Contact for drop shipment
	  */
	public void setDropShip_User_ID (int DropShip_User_ID)
	{
		if (DropShip_User_ID < 1) 
			set_Value (COLUMNNAME_DropShip_User_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_User_ID, Integer.valueOf(DropShip_User_ID));
	}

	/** Get Drop Shipment Contact.
		@return Business Partner Contact for drop shipment
	  */
	public int getDropShip_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Flat Discount %.
		@param FlatDiscount 
		Flat discount percentage 
	  */
	public void setFlatDiscount (int FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, Integer.valueOf(FlatDiscount));
	}

	/** Get Flat Discount %.
		@return Flat discount percentage 
	  */
	public int getFlatDiscount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FlatDiscount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Freight Amount.
		@param FreightAmt 
		Freight Amount 
	  */
	public void setFreightAmt (BigDecimal FreightAmt)
	{
		set_Value (COLUMNNAME_FreightAmt, FreightAmt);
	}

	/** Get Freight Amount.
		@return Freight Amount 
	  */
	public BigDecimal getFreightAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FreightAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** FreightCostRule AD_Reference_ID=153 */
	public static final int FREIGHTCOSTRULE_AD_Reference_ID=153;
	/** Freight included = I */
	public static final String FREIGHTCOSTRULE_FreightIncluded = "I";
	/** Fix price = F */
	public static final String FREIGHTCOSTRULE_FixPrice = "F";
	/** Calculated = C */
	public static final String FREIGHTCOSTRULE_Calculated = "C";
	/** Line = L */
	public static final String FREIGHTCOSTRULE_Line = "L";
	/** Set Freight Cost Rule.
		@param FreightCostRule 
		Method for charging Freight
	  */
	public void setFreightCostRule (String FreightCostRule)
	{

		set_Value (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	/** Get Freight Cost Rule.
		@return Method for charging Freight
	  */
	public String getFreightCostRule () 
	{
		return (String)get_Value(COLUMNNAME_FreightCostRule);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
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

	/** InvoiceRule AD_Reference_ID=150 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** After Order delivered = O */
	public static final String INVOICERULE_AfterOrderDelivered = "O";
	/** After Delivery = D */
	public static final String INVOICERULE_AfterDelivery = "D";
	/** Customer Schedule after Delivery = S */
	public static final String INVOICERULE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_Immediate = "I";
	/** Set Invoice Rule.
		@param InvoiceRule 
		Frequency and method of invoicing 
	  */
	public void setInvoiceRule (String InvoiceRule)
	{

		set_Value (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	/** Get Invoice Rule.
		@return Frequency and method of invoicing 
	  */
	public String getInvoiceRule () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceRule);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
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

	/** Set Credit Approved.
		@param IsCreditApproved 
		Credit  has been approved
	  */
	public void setIsCreditApproved (boolean IsCreditApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsCreditApproved, Boolean.valueOf(IsCreditApproved));
	}

	/** Get Credit Approved.
		@return Credit  has been approved
	  */
	public boolean isCreditApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreditApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Delivered.
		@param IsDelivered Delivered	  */
	public void setIsDelivered (boolean IsDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_IsDelivered, Boolean.valueOf(IsDelivered));
	}

	/** Get Delivered.
		@return Delivered	  */
	public boolean isDelivered () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Discount Printed.
		@param IsDiscountPrinted 
		Print Discount on Invoice and Order
	  */
	public void setIsDiscountPrinted (boolean IsDiscountPrinted)
	{
		set_Value (COLUMNNAME_IsDiscountPrinted, Boolean.valueOf(IsDiscountPrinted));
	}

	/** Get Discount Printed.
		@return Print Discount on Invoice and Order
	  */
	public boolean isDiscountPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsDiscountPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Drop Shipment.
		@param IsDropShip 
		Drop Shipments are sent from the Vendor directly to the Customer
	  */
	public void setIsDropShip (boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, Boolean.valueOf(IsDropShip));
	}

	/** Get Drop Shipment.
		@return Drop Shipments are sent from the Vendor directly to the Customer
	  */
	public boolean isDropShip () 
	{
		Object oo = get_Value(COLUMNNAME_IsDropShip);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoiced.
		@param IsInvoiced 
		Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_IsInvoiced, Boolean.valueOf(IsInvoiced));
	}

	/** Get Invoiced.
		@return Is this invoiced?
	  */
	public boolean isInvoiced () 
	{
		Object oo = get_Value(COLUMNNAME_IsInvoiced);
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
		set_ValueNoCheck (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
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

	/** Set Self-Service.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Self-Service.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
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

	/** Set Price includes Tax.
		@param IsTaxIncluded 
		Tax is included in the price 
	  */
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Price includes Tax.
		@return Tax is included in the price 
	  */
	public boolean isTaxIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Transferred.
		@param IsTransferred 
		Transferred to General Ledger (i.e. accounted)
	  */
	public void setIsTransferred (boolean IsTransferred)
	{
		set_ValueNoCheck (COLUMNNAME_IsTransferred, Boolean.valueOf(IsTransferred));
	}

	/** Get Transferred.
		@return Transferred to General Ledger (i.e. accounted)
	  */
	public boolean isTransferred () 
	{
		Object oo = get_Value(COLUMNNAME_IsTransferred);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_C_Order getLink_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getLink_Order_ID(), get_TrxName());	}

	/** Set Linked Order.
		@param Link_Order_ID 
		This field links a sales order to the purchase order that is generated from it.
	  */
	public void setLink_Order_ID (int Link_Order_ID)
	{
		if (Link_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Link_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Link_Order_ID, Integer.valueOf(Link_Order_ID));
	}

	/** Get Linked Order.
		@return This field links a sales order to the purchase order that is generated from it.
	  */
	public int getLink_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Link_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_FreightCategory getM_FreightCategory() throws RuntimeException
    {
		return (org.compiere.model.I_M_FreightCategory)MTable.get(getCtx(), org.compiere.model.I_M_FreightCategory.Table_Name)
			.getPO(getM_FreightCategory_ID(), get_TrxName());	}

	/** Set Freight Category.
		@param M_FreightCategory_ID 
		Category of the Freight
	  */
	public void setM_FreightCategory_ID (int M_FreightCategory_ID)
	{
		if (M_FreightCategory_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCategory_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCategory_ID, Integer.valueOf(M_FreightCategory_ID));
	}

	/** Get Freight Category.
		@return Category of the Freight
	  */
	public int getM_FreightCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
    {
		return (org.compiere.model.I_M_Shipper)MTable.get(getCtx(), org.compiere.model.I_M_Shipper.Table_Name)
			.getPO(getM_Shipper_ID(), get_TrxName());	}

	/** Set Shipper.
		@param M_Shipper_ID 
		Method or manner of product delivery
	  */
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Shipper.
		@return Method or manner of product delivery
	  */
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
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

	/** Set Order Type.
		@param OrderType 
		Type of Order: MRP records grouped by source (Sales Order, Purchase Order, Distribution Order, Requisition)
	  */
	public void setOrderType (String OrderType)
	{
		set_Value (COLUMNNAME_OrderType, OrderType);
	}

	/** Get Order Type.
		@return Type of Order: MRP records grouped by source (Sales Order, Purchase Order, Distribution Order, Requisition)
	  */
	public String getOrderType () 
	{
		return (String)get_Value(COLUMNNAME_OrderType);
	}

	/** Set Payment BPartner.
		@param Pay_BPartner_ID 
		Business Partner responsible for the payment
	  */
	public void setPay_BPartner_ID (int Pay_BPartner_ID)
	{
		if (Pay_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Pay_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Pay_BPartner_ID, Integer.valueOf(Pay_BPartner_ID));
	}

	/** Get Payment BPartner.
		@return Business Partner responsible for the payment
	  */
	public int getPay_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pay_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment Location.
		@param Pay_Location_ID 
		Location of the Business Partner responsible for the payment
	  */
	public void setPay_Location_ID (int Pay_Location_ID)
	{
		if (Pay_Location_ID < 1) 
			set_Value (COLUMNNAME_Pay_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Pay_Location_ID, Integer.valueOf(Pay_Location_ID));
	}

	/** Get Payment Location.
		@return Location of the Business Partner responsible for the payment
	  */
	public int getPay_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pay_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PaymentRule AD_Reference_ID=195 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** Credit Card = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** Direct Deposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** On Credit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** Direct Debit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** Set Payment Rule.
		@param PaymentRule 
		How you pay the invoice
	  */
	public void setPaymentRule (String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Payment Rule.
		@return How you pay the invoice
	  */
	public String getPaymentRule () 
	{
		return (String)get_Value(COLUMNNAME_PaymentRule);
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

	/** Set Order Reference.
		@param POReference 
		Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Order Reference.
		@return Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference () 
	{
		return (String)get_Value(COLUMNNAME_POReference);
	}

	/** Set POSCode.
		@param POSCode POSCode	  */
	public void setPOSCode (String POSCode)
	{
		set_Value (COLUMNNAME_POSCode, POSCode);
	}

	/** Get POSCode.
		@return POSCode	  */
	public String getPOSCode () 
	{
		return (String)get_Value(COLUMNNAME_POSCode);
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
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	/** Set Promotion Code.
		@param PromotionCode 
		User entered promotion code at sales time
	  */
	public void setPromotionCode (String PromotionCode)
	{
		set_Value (COLUMNNAME_PromotionCode, PromotionCode);
	}

	/** Get Promotion Code.
		@return User entered promotion code at sales time
	  */
	public String getPromotionCode () 
	{
		return (String)get_Value(COLUMNNAME_PromotionCode);
	}

	public org.compiere.model.I_C_Order getRef_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getRef_Order_ID(), get_TrxName());	}

	/** Set Referenced Order.
		@param Ref_Order_ID 
		Reference to corresponding Sales/Purchase Order
	  */
	public void setRef_Order_ID (int Ref_Order_ID)
	{
		if (Ref_Order_ID < 1) 
			set_Value (COLUMNNAME_Ref_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Order_ID, Integer.valueOf(Ref_Order_ID));
	}

	/** Get Referenced Order.
		@return Reference to corresponding Sales/Purchase Order
	  */
	public int getRef_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Send EMail.
		@param SendEMail 
		Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail)
	{
		set_Value (COLUMNNAME_SendEMail, Boolean.valueOf(SendEMail));
	}

	/** Get Send EMail.
		@return Enable sending Document EMail
	  */
	public boolean isSendEMail () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_ValueNoCheck (COLUMNNAME_TotalLines, TotalLines);
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

	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getUser1_ID(), get_TrxName());	}

	/** Set User List 1.
		@param User1_ID 
		User defined list element #1
	  */
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get User List 1.
		@return User defined list element #1
	  */
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getUser2_ID(), get_TrxName());	}

	/** Set User List 2.
		@param User2_ID 
		User defined list element #2
	  */
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get User List 2.
		@return User defined list element #2
	  */
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CanalVentas getUY_CanalVentas() throws RuntimeException
    {
		return (I_UY_CanalVentas)MTable.get(getCtx(), I_UY_CanalVentas.Table_Name)
			.getPO(getUY_CanalVentas_ID(), get_TrxName());	}

	/** Set UY_CanalVentas.
		@param UY_CanalVentas_ID UY_CanalVentas	  */
	public void setUY_CanalVentas_ID (int UY_CanalVentas_ID)
	{
		if (UY_CanalVentas_ID < 1) 
			set_Value (COLUMNNAME_UY_CanalVentas_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CanalVentas_ID, Integer.valueOf(UY_CanalVentas_ID));
	}

	/** Get UY_CanalVentas.
		@return UY_CanalVentas	  */
	public int getUY_CanalVentas_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CanalVentas_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_cancelado.
		@param uy_cancelado uy_cancelado	  */
	public void setuy_cancelado (boolean uy_cancelado)
	{
		set_Value (COLUMNNAME_uy_cancelado, Boolean.valueOf(uy_cancelado));
	}

	/** Get uy_cancelado.
		@return uy_cancelado	  */
	public boolean isuy_cancelado () 
	{
		Object oo = get_Value(COLUMNNAME_uy_cancelado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set uy_credit_approved.
		@param uy_credit_approved uy_credit_approved	  */
	public void setuy_credit_approved (boolean uy_credit_approved)
	{
		set_Value (COLUMNNAME_uy_credit_approved, Boolean.valueOf(uy_credit_approved));
	}

	/** Get uy_credit_approved.
		@return uy_credit_approved	  */
	public boolean isuy_credit_approved () 
	{
		Object oo = get_Value(COLUMNNAME_uy_credit_approved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set uy_credit_message.
		@param uy_credit_message uy_credit_message	  */
	public void setuy_credit_message (String uy_credit_message)
	{
		set_Value (COLUMNNAME_uy_credit_message, uy_credit_message);
	}

	/** Get uy_credit_message.
		@return uy_credit_message	  */
	public String getuy_credit_message () 
	{
		return (String)get_Value(COLUMNNAME_uy_credit_message);
	}

	public I_UY_Distribuidor getUY_Distribuidor() throws RuntimeException
    {
		return (I_UY_Distribuidor)MTable.get(getCtx(), I_UY_Distribuidor.Table_Name)
			.getPO(getUY_Distribuidor_ID(), get_TrxName());	}

	/** Set UY_Distribuidor.
		@param UY_Distribuidor_ID UY_Distribuidor	  */
	public void setUY_Distribuidor_ID (int UY_Distribuidor_ID)
	{
		if (UY_Distribuidor_ID < 1) 
			set_Value (COLUMNNAME_UY_Distribuidor_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Distribuidor_ID, Integer.valueOf(UY_Distribuidor_ID));
	}

	/** Get UY_Distribuidor.
		@return UY_Distribuidor	  */
	public int getUY_Distribuidor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Distribuidor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fletero getUY_Fletero() throws RuntimeException
    {
		return (I_UY_Fletero)MTable.get(getCtx(), I_UY_Fletero.Table_Name)
			.getPO(getUY_Fletero_ID(), get_TrxName());	}

	/** Set UY_Fletero.
		@param UY_Fletero_ID UY_Fletero	  */
	public void setUY_Fletero_ID (int UY_Fletero_ID)
	{
		if (UY_Fletero_ID < 1) 
			set_Value (COLUMNNAME_UY_Fletero_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fletero_ID, Integer.valueOf(UY_Fletero_ID));
	}

	/** Get UY_Fletero.
		@return UY_Fletero	  */
	public int getUY_Fletero_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fletero_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_POPolicy getUY_POPolicy() throws RuntimeException
    {
		return (I_UY_POPolicy)MTable.get(getCtx(), I_UY_POPolicy.Table_Name)
			.getPO(getUY_POPolicy_ID(), get_TrxName());	}

	/** Set UY_POPolicy.
		@param UY_POPolicy_ID UY_POPolicy	  */
	public void setUY_POPolicy_ID (int UY_POPolicy_ID)
	{
		if (UY_POPolicy_ID < 1) 
			set_Value (COLUMNNAME_UY_POPolicy_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POPolicy_ID, Integer.valueOf(UY_POPolicy_ID));
	}

	/** Get UY_POPolicy.
		@return UY_POPolicy	  */
	public int getUY_POPolicy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POPolicy_ID);
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

	public I_UY_QuoteVendor getUY_QuoteVendor() throws RuntimeException
    {
		return (I_UY_QuoteVendor)MTable.get(getCtx(), I_UY_QuoteVendor.Table_Name)
			.getPO(getUY_QuoteVendor_ID(), get_TrxName());	}

	/** Set UY_QuoteVendor.
		@param UY_QuoteVendor_ID UY_QuoteVendor	  */
	public void setUY_QuoteVendor_ID (int UY_QuoteVendor_ID)
	{
		if (UY_QuoteVendor_ID < 1) 
			set_Value (COLUMNNAME_UY_QuoteVendor_ID, null);
		else 
			set_Value (COLUMNNAME_UY_QuoteVendor_ID, Integer.valueOf(UY_QuoteVendor_ID));
	}

	/** Get UY_QuoteVendor.
		@return UY_QuoteVendor	  */
	public int getUY_QuoteVendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_QuoteVendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ReservaStock.
		@param UY_ReservaStock UY_ReservaStock	  */
	public void setUY_ReservaStock (boolean UY_ReservaStock)
	{
		set_Value (COLUMNNAME_UY_ReservaStock, Boolean.valueOf(UY_ReservaStock));
	}

	/** Get UY_ReservaStock.
		@return UY_ReservaStock	  */
	public boolean isUY_ReservaStock () 
	{
		Object oo = get_Value(COLUMNNAME_UY_ReservaStock);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_RFQ_MacroReq getUY_RFQ_MacroReq() throws RuntimeException
    {
		return (I_UY_RFQ_MacroReq)MTable.get(getCtx(), I_UY_RFQ_MacroReq.Table_Name)
			.getPO(getUY_RFQ_MacroReq_ID(), get_TrxName());	}

	/** Set UY_RFQ_MacroReq.
		@param UY_RFQ_MacroReq_ID UY_RFQ_MacroReq	  */
	public void setUY_RFQ_MacroReq_ID (int UY_RFQ_MacroReq_ID)
	{
		if (UY_RFQ_MacroReq_ID < 1) 
			set_Value (COLUMNNAME_UY_RFQ_MacroReq_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RFQ_MacroReq_ID, Integer.valueOf(UY_RFQ_MacroReq_ID));
	}

	/** Get UY_RFQ_MacroReq.
		@return UY_RFQ_MacroReq	  */
	public int getUY_RFQ_MacroReq_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_MacroReq_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TotalPendiente.
		@param UY_TotalPendiente UY_TotalPendiente	  */
	public void setUY_TotalPendiente (BigDecimal UY_TotalPendiente)
	{
		throw new IllegalArgumentException ("UY_TotalPendiente is virtual column");	}

	/** Get UY_TotalPendiente.
		@return UY_TotalPendiente	  */
	public BigDecimal getUY_TotalPendiente () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_TotalPendiente);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
}