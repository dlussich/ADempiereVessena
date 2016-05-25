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
import org.openup.model.I_UY_AsignaTransporteHdr;
import org.openup.model.I_UY_Budget;
import org.openup.model.I_UY_Departamentos;
import org.openup.model.I_UY_FF_Branch;
import org.openup.model.I_UY_FF_CashOut;
import org.openup.model.I_UY_ManufOrder;
import org.openup.model.I_UY_PaymentRule;
import org.openup.model.I_UY_Resguardo;

/** Generated Model for C_Invoice
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_C_Invoice extends PO implements I_C_Invoice, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140319L;

    /** Standard Constructor */
    public X_C_Invoice (Properties ctx, int C_Invoice_ID, String trxName)
    {
      super (ctx, C_Invoice_ID, trxName);
      /** if (C_Invoice_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Currency_ID (0);
// @C_Currency_ID@
			setC_DocType_ID (0);
			setC_DocTypeTarget_ID (0);
			setC_Invoice_ID (0);
			setContribuyenteFinal (false);
			setC_PaymentTerm_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDateInvoiced (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setGrandTotal (Env.ZERO);
			setIsApproved (false);
// @IsApproved@
			setIsDiscountPrinted (false);
			setIsInDispute (false);
// N
			setIsPaid (false);
			setIsPayScheduleValid (false);
			setIsPrinted (false);
			setIsSelfService (false);
			setIsSOTrx (false);
// @IsSOTrx@
			setIsTaxIncluded (false);
			setIsTransferred (false);
			setM_PriceList_ID (0);
			setPaymentRule (null);
// P
			setPosted (false);
// N
			setProcessed (false);
			setSendEMail (false);
			setTotalLines (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_Invoice (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Invoice[")
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

	/** Set AmtOriginal.
		@param AmtOriginal AmtOriginal	  */
	public void setAmtOriginal (BigDecimal AmtOriginal)
	{
		set_Value (COLUMNNAME_AmtOriginal, AmtOriginal);
	}

	/** Get AmtOriginal.
		@return AmtOriginal	  */
	public BigDecimal getAmtOriginal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtOriginal);
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

	public org.compiere.model.I_C_CashBook getC_CashBook() throws RuntimeException
    {
		return (org.compiere.model.I_C_CashBook)MTable.get(getCtx(), org.compiere.model.I_C_CashBook.Table_Name)
			.getPO(getC_CashBook_ID(), get_TrxName());	}

	/** Set Cash Book.
		@param C_CashBook_ID 
		Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID (int C_CashBook_ID)
	{
		if (C_CashBook_ID < 1) 
			set_Value (COLUMNNAME_C_CashBook_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
	}

	/** Get Cash Book.
		@return Cash Book for recording petty cash transactions
	  */
	public int getC_CashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashBook_ID);
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

	public org.compiere.model.I_C_BPartner getC_Customer() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_Customer_ID(), get_TrxName());	}

	/** Set C_Customer_ID.
		@param C_Customer_ID C_Customer_ID	  */
	public void setC_Customer_ID (int C_Customer_ID)
	{
		if (C_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_Customer_ID, Integer.valueOf(C_Customer_ID));
	}

	/** Get C_Customer_ID.
		@return C_Customer_ID	  */
	public int getC_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Customer_ID);
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

	public org.compiere.model.I_C_DunningLevel getC_DunningLevel() throws RuntimeException
    {
		return (org.compiere.model.I_C_DunningLevel)MTable.get(getCtx(), org.compiere.model.I_C_DunningLevel.Table_Name)
			.getPO(getC_DunningLevel_ID(), get_TrxName());	}

	/** Set Dunning Level.
		@param C_DunningLevel_ID Dunning Level	  */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1) 
			set_Value (COLUMNNAME_C_DunningLevel_ID, null);
		else 
			set_Value (COLUMNNAME_C_DunningLevel_ID, Integer.valueOf(C_DunningLevel_ID));
	}

	/** Get Dunning Level.
		@return Dunning Level	  */
	public int getC_DunningLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningLevel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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

	/** Set ContribuyenteFinal.
		@param ContribuyenteFinal ContribuyenteFinal	  */
	public void setContribuyenteFinal (boolean ContribuyenteFinal)
	{
		set_ValueNoCheck (COLUMNNAME_ContribuyenteFinal, Boolean.valueOf(ContribuyenteFinal));
	}

	/** Get ContribuyenteFinal.
		@return ContribuyenteFinal	  */
	public boolean isContribuyenteFinal () 
	{
		Object oo = get_Value(COLUMNNAME_ContribuyenteFinal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

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

	/** Set C_Period_ID_From.
		@param C_Period_ID_From C_Period_ID_From	  */
	public void setC_Period_ID_From (int C_Period_ID_From)
	{
		set_Value (COLUMNNAME_C_Period_ID_From, Integer.valueOf(C_Period_ID_From));
	}

	/** Get C_Period_ID_From.
		@return C_Period_ID_From	  */
	public int getC_Period_ID_From () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID_From);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Period_ID_To.
		@param C_Period_ID_To C_Period_ID_To	  */
	public void setC_Period_ID_To (int C_Period_ID_To)
	{
		set_Value (COLUMNNAME_C_Period_ID_To, Integer.valueOf(C_Period_ID_To));
	}

	/** Get C_Period_ID_To.
		@return C_Period_ID_To	  */
	public int getC_Period_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID_To);
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

	/** Set CreateFrom2.
		@param CreateFrom2 CreateFrom2	  */
	public void setCreateFrom2 (String CreateFrom2)
	{
		set_Value (COLUMNNAME_CreateFrom2, CreateFrom2);
	}

	/** Get CreateFrom2.
		@return CreateFrom2	  */
	public String getCreateFrom2 () 
	{
		return (String)get_Value(COLUMNNAME_CreateFrom2);
	}

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Custodio AD_Reference_ID=1000004 */
	public static final int CUSTODIO_AD_Reference_ID=1000004;
	/** Factoing = F */
	public static final String CUSTODIO_Factoing = "F";
	/** Extinto = E */
	public static final String CUSTODIO_Extinto = "E";
	/** a tercero = T */
	public static final String CUSTODIO_ATercero = "T";
	/** Disponible = O */
	public static final String CUSTODIO_Disponible = "O";
	/** Set Custodio.
		@param Custodio Custodio	  */
	public void setCustodio (String Custodio)
	{

		set_Value (COLUMNNAME_Custodio, Custodio);
	}

	/** Get Custodio.
		@return Custodio	  */
	public String getCustodio () 
	{
		return (String)get_Value(COLUMNNAME_Custodio);
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

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set Date Ordered.
		@param DateOrdered 
		Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
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

	/** Set datetimeinvoiced.
		@param datetimeinvoiced datetimeinvoiced	  */
	public void setdatetimeinvoiced (Timestamp datetimeinvoiced)
	{
		set_Value (COLUMNNAME_datetimeinvoiced, datetimeinvoiced);
	}

	/** Get datetimeinvoiced.
		@return datetimeinvoiced	  */
	public Timestamp getdatetimeinvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datetimeinvoiced);
	}

	/** Set DateVendor.
		@param DateVendor DateVendor	  */
	public void setDateVendor (Timestamp DateVendor)
	{
		set_Value (COLUMNNAME_DateVendor, DateVendor);
	}

	/** Get DateVendor.
		@return DateVendor	  */
	public Timestamp getDateVendor () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateVendor);
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

	/** DocStatusReason AD_Reference_ID=1000088 */
	public static final int DOCSTATUSREASON_AD_Reference_ID=1000088;
	/** Cliente sin dinero = Cliente sin dinero */
	public static final String DOCSTATUSREASON_ClienteSinDinero = "Cliente sin dinero";
	/** Cliente sin espacio = Cliente sin espacio */
	public static final String DOCSTATUSREASON_ClienteSinEspacio = "Cliente sin espacio";
	/** Diferencia de precio = Diferencia de precio */
	public static final String DOCSTATUSREASON_DiferenciaDePrecio = "Diferencia de precio";
	/** Direccion mal = Direccion mal */
	public static final String DOCSTATUSREASON_DireccionMal = "Direccion mal";
	/** Fuera de hora = Fuera de hora */
	public static final String DOCSTATUSREASON_FueraDeHora = "Fuera de hora";
	/** Local cerrado = Local cerrado */
	public static final String DOCSTATUSREASON_LocalCerrado = "Local cerrado";
	/** Mal impreso = Mal impreso */
	public static final String DOCSTATUSREASON_MalImpreso = "Mal impreso";
	/** Mercaderia no solicitada = Mercaderia no solicitada */
	public static final String DOCSTATUSREASON_MercaderiaNoSolicitada = "Mercaderia no solicitada";
	/** No cargado en camion = No cargado en camion */
	public static final String DOCSTATUSREASON_NoCargadoEnCamion = "No cargado en camion";
	/** No visitado = No visitado */
	public static final String DOCSTATUSREASON_NoVisitado = "No visitado";
	/** Orden repetida = Orden repetida */
	public static final String DOCSTATUSREASON_OrdenRepetida = "Orden repetida";
	/** Orden vencida = Orden vencida */
	public static final String DOCSTATUSREASON_OrdenVencida = "Orden vencida";
	/** Rotura en camion = Rotura en camion */
	public static final String DOCSTATUSREASON_RoturaEnCamion = "Rotura en camion";
	/** Se genera nuevo documento = Se genera nuevo documento */
	public static final String DOCSTATUSREASON_SeGeneraNuevoDocumento = "Se genera nuevo documento";
	/** Fecha Erronea = Fecha Erronea */
	public static final String DOCSTATUSREASON_FechaErronea = "Fecha Erronea";
	/** Set DocStatusReason.
		@param DocStatusReason 
		DocStatusReason
	  */
	public void setDocStatusReason (String DocStatusReason)
	{

		set_Value (COLUMNNAME_DocStatusReason, DocStatusReason);
	}

	/** Get DocStatusReason.
		@return DocStatusReason
	  */
	public String getDocStatusReason () 
	{
		return (String)get_Value(COLUMNNAME_DocStatusReason);
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

	/** Set DocumentNoAux.
		@param DocumentNoAux 
		DocumentNoAux
	  */
	public void setDocumentNoAux (String DocumentNoAux)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNoAux, DocumentNoAux);
	}

	/** Get DocumentNoAux.
		@return DocumentNoAux
	  */
	public String getDocumentNoAux () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNoAux);
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

	/** Set Dunning Grace Date.
		@param DunningGrace Dunning Grace Date	  */
	public void setDunningGrace (Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	/** Get Dunning Grace Date.
		@return Dunning Grace Date	  */
	public Timestamp getDunningGrace () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DunningGrace);
	}

	/** Set embalaje.
		@param embalaje embalaje	  */
	public void setembalaje (String embalaje)
	{
		set_Value (COLUMNNAME_embalaje, embalaje);
	}

	/** Get embalaje.
		@return embalaje	  */
	public String getembalaje () 
	{
		return (String)get_Value(COLUMNNAME_embalaje);
	}

	/** Set GenerateInvoice.
		@param GenerateInvoice GenerateInvoice	  */
	public void setGenerateInvoice (String GenerateInvoice)
	{
		set_Value (COLUMNNAME_GenerateInvoice, GenerateInvoice);
	}

	/** Get GenerateInvoice.
		@return GenerateInvoice	  */
	public String getGenerateInvoice () 
	{
		return (String)get_Value(COLUMNNAME_GenerateInvoice);
	}

	/** Set Generate To.
		@param GenerateTo 
		Generate To
	  */
	public void setGenerateTo (String GenerateTo)
	{
		set_Value (COLUMNNAME_GenerateTo, GenerateTo);
	}

	/** Get Generate To.
		@return Generate To
	  */
	public String getGenerateTo () 
	{
		return (String)get_Value(COLUMNNAME_GenerateTo);
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

	/** Set incoterms.
		@param incoterms incoterms	  */
	public void setincoterms (String incoterms)
	{
		set_Value (COLUMNNAME_incoterms, incoterms);
	}

	/** Get incoterms.
		@return incoterms	  */
	public String getincoterms () 
	{
		return (String)get_Value(COLUMNNAME_incoterms);
	}

	/** InvoiceCollectionType AD_Reference_ID=394 */
	public static final int INVOICECOLLECTIONTYPE_AD_Reference_ID=394;
	/** Dunning = D */
	public static final String INVOICECOLLECTIONTYPE_Dunning = "D";
	/** Collection Agency = C */
	public static final String INVOICECOLLECTIONTYPE_CollectionAgency = "C";
	/** Legal Procedure = L */
	public static final String INVOICECOLLECTIONTYPE_LegalProcedure = "L";
	/** Uncollectable = U */
	public static final String INVOICECOLLECTIONTYPE_Uncollectable = "U";
	/** Set Collection Status.
		@param InvoiceCollectionType 
		Invoice Collection Status
	  */
	public void setInvoiceCollectionType (String InvoiceCollectionType)
	{

		set_Value (COLUMNNAME_InvoiceCollectionType, InvoiceCollectionType);
	}

	/** Get Collection Status.
		@return Invoice Collection Status
	  */
	public String getInvoiceCollectionType () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceCollectionType);
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

	/** Set IsDevengable.
		@param IsDevengable IsDevengable	  */
	public void setIsDevengable (boolean IsDevengable)
	{
		set_Value (COLUMNNAME_IsDevengable, Boolean.valueOf(IsDevengable));
	}

	/** Get IsDevengable.
		@return IsDevengable	  */
	public boolean isDevengable () 
	{
		Object oo = get_Value(COLUMNNAME_IsDevengable);
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

	/** Set In Dispute.
		@param IsInDispute 
		Document is in dispute
	  */
	public void setIsInDispute (boolean IsInDispute)
	{
		set_Value (COLUMNNAME_IsInDispute, Boolean.valueOf(IsInDispute));
	}

	/** Get In Dispute.
		@return Document is in dispute
	  */
	public boolean isInDispute () 
	{
		Object oo = get_Value(COLUMNNAME_IsInDispute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Paid.
		@param IsPaid 
		The document is paid
	  */
	public void setIsPaid (boolean IsPaid)
	{
		set_Value (COLUMNNAME_IsPaid, Boolean.valueOf(IsPaid));
	}

	/** Get Paid.
		@return The document is paid
	  */
	public boolean isPaid () 
	{
		Object oo = get_Value(COLUMNNAME_IsPaid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pay Schedule valid.
		@param IsPayScheduleValid 
		Is the Payment Schedule is valid
	  */
	public void setIsPayScheduleValid (boolean IsPayScheduleValid)
	{
		set_ValueNoCheck (COLUMNNAME_IsPayScheduleValid, Boolean.valueOf(IsPayScheduleValid));
	}

	/** Get Pay Schedule valid.
		@return Is the Payment Schedule is valid
	  */
	public boolean isPayScheduleValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayScheduleValid);
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
		set_ValueNoCheck (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
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

	/** Set LiteralNumber.
		@param LiteralNumber LiteralNumber	  */
	public void setLiteralNumber (String LiteralNumber)
	{
		set_Value (COLUMNNAME_LiteralNumber, LiteralNumber);
	}

	/** Get LiteralNumber.
		@return LiteralNumber	  */
	public String getLiteralNumber () 
	{
		return (String)get_Value(COLUMNNAME_LiteralNumber);
	}

	/** Set LiteralNumber2.
		@param LiteralNumber2 LiteralNumber2	  */
	public void setLiteralNumber2 (String LiteralNumber2)
	{
		set_Value (COLUMNNAME_LiteralNumber2, LiteralNumber2);
	}

	/** Get LiteralNumber2.
		@return LiteralNumber2	  */
	public String getLiteralNumber2 () 
	{
		return (String)get_Value(COLUMNNAME_LiteralNumber2);
	}

	/** Set marcas.
		@param marcas marcas	  */
	public void setmarcas (String marcas)
	{
		set_Value (COLUMNNAME_marcas, marcas);
	}

	/** Get marcas.
		@return marcas	  */
	public String getmarcas () 
	{
		return (String)get_Value(COLUMNNAME_marcas);
	}

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_M_RMA getM_RMA() throws RuntimeException
    {
		return (org.compiere.model.I_M_RMA)MTable.get(getCtx(), org.compiere.model.I_M_RMA.Table_Name)
			.getPO(getM_RMA_ID(), get_TrxName());	}

	/** Set RMA.
		@param M_RMA_ID 
		Return Material Authorization
	  */
	public void setM_RMA_ID (int M_RMA_ID)
	{
		if (M_RMA_ID < 1) 
			set_Value (COLUMNNAME_M_RMA_ID, null);
		else 
			set_Value (COLUMNNAME_M_RMA_ID, Integer.valueOf(M_RMA_ID));
	}

	/** Get RMA.
		@return Return Material Authorization
	  */
	public int getM_RMA_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMA_ID);
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

	/** Set numeracion.
		@param numeracion numeracion	  */
	public void setnumeracion (String numeracion)
	{
		set_Value (COLUMNNAME_numeracion, numeracion);
	}

	/** Get numeracion.
		@return numeracion	  */
	public String getnumeracion () 
	{
		return (String)get_Value(COLUMNNAME_numeracion);
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

	/** Set pesoBruto.
		@param pesoBruto pesoBruto	  */
	public void setpesoBruto (String pesoBruto)
	{
		set_Value (COLUMNNAME_pesoBruto, pesoBruto);
	}

	/** Get pesoBruto.
		@return pesoBruto	  */
	public String getpesoBruto () 
	{
		return (String)get_Value(COLUMNNAME_pesoBruto);
	}

	/** Set pesoNeto.
		@param pesoNeto pesoNeto	  */
	public void setpesoNeto (String pesoNeto)
	{
		set_Value (COLUMNNAME_pesoNeto, pesoNeto);
	}

	/** Get pesoNeto.
		@return pesoNeto	  */
	public String getpesoNeto () 
	{
		return (String)get_Value(COLUMNNAME_pesoNeto);
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

	/** Set proforminvoice_text.
		@param proforminvoice_text proforminvoice_text	  */
	public void setproforminvoice_text (String proforminvoice_text)
	{
		set_Value (COLUMNNAME_proforminvoice_text, proforminvoice_text);
	}

	/** Get proforminvoice_text.
		@return proforminvoice_text	  */
	public String getproforminvoice_text () 
	{
		return (String)get_Value(COLUMNNAME_proforminvoice_text);
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

	/** Set Referenced Invoice.
		@param Ref_Invoice_ID Referenced Invoice	  */
	public void setRef_Invoice_ID (int Ref_Invoice_ID)
	{
		if (Ref_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Ref_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Invoice_ID, Integer.valueOf(Ref_Invoice_ID));
	}

	/** Get Referenced Invoice.
		@return Referenced Invoice	  */
	public int getRef_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Invoice getReversal() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getReversal_ID(), get_TrxName());	}

	/** Set Reversal ID.
		@param Reversal_ID 
		ID of document reversal
	  */
	public void setReversal_ID (int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_Value (COLUMNNAME_Reversal_ID, null);
		else 
			set_Value (COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
	}

	/** Get Reversal ID.
		@return ID of document reversal
	  */
	public int getReversal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reversal_ID);
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

	/** Set transporte.
		@param transporte transporte	  */
	public void settransporte (String transporte)
	{
		set_Value (COLUMNNAME_transporte, transporte);
	}

	/** Get transporte.
		@return transporte	  */
	public String gettransporte () 
	{
		return (String)get_Value(COLUMNNAME_transporte);
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

	public I_UY_AsignaTransporteHdr getUY_AsignaTransHdrACTUAL() throws RuntimeException
    {
		return (I_UY_AsignaTransporteHdr)MTable.get(getCtx(), I_UY_AsignaTransporteHdr.Table_Name)
			.getPO(getUY_AsignaTransHdrACTUAL_ID(), get_TrxName());	}

	/** Set UY_AsignaTransHdrACTUAL_ID.
		@param UY_AsignaTransHdrACTUAL_ID UY_AsignaTransHdrACTUAL_ID	  */
	public void setUY_AsignaTransHdrACTUAL_ID (int UY_AsignaTransHdrACTUAL_ID)
	{
		throw new IllegalArgumentException ("UY_AsignaTransHdrACTUAL_ID is virtual column");	}

	/** Get UY_AsignaTransHdrACTUAL_ID.
		@return UY_AsignaTransHdrACTUAL_ID	  */
	public int getUY_AsignaTransHdrACTUAL_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AsignaTransHdrACTUAL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException
    {
		return (I_UY_AsignaTransporteHdr)MTable.get(getCtx(), I_UY_AsignaTransporteHdr.Table_Name)
			.getPO(getUY_AsignaTransporteHdr_ID(), get_TrxName());	}

	/** Set UY_AsignaTransporteHdr_ID.
		@param UY_AsignaTransporteHdr_ID UY_AsignaTransporteHdr_ID	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID)
	{
		if (UY_AsignaTransporteHdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_AsignaTransporteHdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_AsignaTransporteHdr_ID, Integer.valueOf(UY_AsignaTransporteHdr_ID));
	}

	/** Get UY_AsignaTransporteHdr_ID.
		@return UY_AsignaTransporteHdr_ID	  */
	public int getUY_AsignaTransporteHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AsignaTransporteHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Budget getUY_Budget() throws RuntimeException
    {
		return (I_UY_Budget)MTable.get(getCtx(), I_UY_Budget.Table_Name)
			.getPO(getUY_Budget_ID(), get_TrxName());	}

	/** Set UY_Budget.
		@param UY_Budget_ID UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID)
	{
		if (UY_Budget_ID < 1) 
			set_Value (COLUMNNAME_UY_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Budget_ID, Integer.valueOf(UY_Budget_ID));
	}

	/** Get UY_Budget.
		@return UY_Budget	  */
	public int getUY_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_cantbultos.
		@param uy_cantbultos uy_cantbultos	  */
	public void setuy_cantbultos (BigDecimal uy_cantbultos)
	{
		set_Value (COLUMNNAME_uy_cantbultos, uy_cantbultos);
	}

	/** Get uy_cantbultos.
		@return uy_cantbultos	  */
	public BigDecimal getuy_cantbultos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_cantbultos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_cantbultos_manual.
		@param uy_cantbultos_manual uy_cantbultos_manual	  */
	public void setuy_cantbultos_manual (BigDecimal uy_cantbultos_manual)
	{
		set_Value (COLUMNNAME_uy_cantbultos_manual, uy_cantbultos_manual);
	}

	/** Get uy_cantbultos_manual.
		@return uy_cantbultos_manual	  */
	public BigDecimal getuy_cantbultos_manual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_cantbultos_manual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_CloneInvoice.
		@param UY_CloneInvoice UY_CloneInvoice	  */
	public void setUY_CloneInvoice (String UY_CloneInvoice)
	{
		set_Value (COLUMNNAME_UY_CloneInvoice, UY_CloneInvoice);
	}

	/** Get UY_CloneInvoice.
		@return UY_CloneInvoice	  */
	public String getUY_CloneInvoice () 
	{
		return (String)get_Value(COLUMNNAME_UY_CloneInvoice);
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

	public I_UY_FF_Branch getUY_FF_Branch() throws RuntimeException
    {
		return (I_UY_FF_Branch)MTable.get(getCtx(), I_UY_FF_Branch.Table_Name)
			.getPO(getUY_FF_Branch_ID(), get_TrxName());	}

	/** Set UY_FF_Branch.
		@param UY_FF_Branch_ID UY_FF_Branch	  */
	public void setUY_FF_Branch_ID (int UY_FF_Branch_ID)
	{
		if (UY_FF_Branch_ID < 1) 
			set_Value (COLUMNNAME_UY_FF_Branch_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FF_Branch_ID, Integer.valueOf(UY_FF_Branch_ID));
	}

	/** Get UY_FF_Branch.
		@return UY_FF_Branch	  */
	public int getUY_FF_Branch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FF_Branch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FF_CashOut getUY_FF_CashOut() throws RuntimeException
    {
		return (I_UY_FF_CashOut)MTable.get(getCtx(), I_UY_FF_CashOut.Table_Name)
			.getPO(getUY_FF_CashOut_ID(), get_TrxName());	}

	/** Set UY_FF_CashOut.
		@param UY_FF_CashOut_ID UY_FF_CashOut	  */
	public void setUY_FF_CashOut_ID (int UY_FF_CashOut_ID)
	{
		if (UY_FF_CashOut_ID < 1) 
			set_Value (COLUMNNAME_UY_FF_CashOut_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FF_CashOut_ID, Integer.valueOf(UY_FF_CashOut_ID));
	}

	/** Get UY_FF_CashOut.
		@return UY_FF_CashOut	  */
	public int getUY_FF_CashOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FF_CashOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Invoice.
		@param UY_Invoice UY_Invoice	  */
	public void setUY_Invoice (String UY_Invoice)
	{
		set_Value (COLUMNNAME_UY_Invoice, UY_Invoice);
	}

	/** Get UY_Invoice.
		@return UY_Invoice	  */
	public String getUY_Invoice () 
	{
		return (String)get_Value(COLUMNNAME_UY_Invoice);
	}

	/** Set UY_Invoice_ID.
		@param UY_Invoice_ID UY_Invoice_ID	  */
	public void setUY_Invoice_ID (int UY_Invoice_ID)
	{
		if (UY_Invoice_ID < 1) 
			set_Value (COLUMNNAME_UY_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Invoice_ID, Integer.valueOf(UY_Invoice_ID));
	}

	/** Get UY_Invoice_ID.
		@return UY_Invoice_ID	  */
	public int getUY_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ManufOrder getUY_ManufOrder() throws RuntimeException
    {
		return (I_UY_ManufOrder)MTable.get(getCtx(), I_UY_ManufOrder.Table_Name)
			.getPO(getUY_ManufOrder_ID(), get_TrxName());	}

	/** Set UY_ManufOrder.
		@param UY_ManufOrder_ID UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID)
	{
		if (UY_ManufOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, Integer.valueOf(UY_ManufOrder_ID));
	}

	/** Get UY_ManufOrder.
		@return UY_ManufOrder	  */
	public int getUY_ManufOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PaymentRule getUY_PaymentRule() throws RuntimeException
    {
		return (I_UY_PaymentRule)MTable.get(getCtx(), I_UY_PaymentRule.Table_Name)
			.getPO(getUY_PaymentRule_ID(), get_TrxName());	}

	/** Set UY_PaymentRule.
		@param UY_PaymentRule_ID UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID)
	{
		if (UY_PaymentRule_ID < 1) 
			set_Value (COLUMNNAME_UY_PaymentRule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PaymentRule_ID, Integer.valueOf(UY_PaymentRule_ID));
	}

	/** Get UY_PaymentRule.
		@return UY_PaymentRule	  */
	public int getUY_PaymentRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PaymentRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ProcesoFactmasHdr.
		@param UY_ProcesoFactmasHdr_ID UY_ProcesoFactmasHdr	  */
	public void setUY_ProcesoFactmasHdr_ID (BigDecimal UY_ProcesoFactmasHdr_ID)
	{
		set_Value (COLUMNNAME_UY_ProcesoFactmasHdr_ID, UY_ProcesoFactmasHdr_ID);
	}

	/** Get UY_ProcesoFactmasHdr.
		@return UY_ProcesoFactmasHdr	  */
	public BigDecimal getUY_ProcesoFactmasHdr_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_ProcesoFactmasHdr_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_Remito_ID.
		@param UY_Remito_ID UY_Remito_ID	  */
	public void setUY_Remito_ID (BigDecimal UY_Remito_ID)
	{
		set_Value (COLUMNNAME_UY_Remito_ID, UY_Remito_ID);
	}

	/** Get UY_Remito_ID.
		@return UY_Remito_ID	  */
	public BigDecimal getUY_Remito_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_Remito_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_ReservaPedidoHdr.
		@param UY_ReservaPedidoHdr_ID UY_ReservaPedidoHdr	  */
	public void setUY_ReservaPedidoHdr_ID (BigDecimal UY_ReservaPedidoHdr_ID)
	{
		set_Value (COLUMNNAME_UY_ReservaPedidoHdr_ID, UY_ReservaPedidoHdr_ID);
	}

	/** Get UY_ReservaPedidoHdr.
		@return UY_ReservaPedidoHdr	  */
	public BigDecimal getUY_ReservaPedidoHdr_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_ReservaPedidoHdr_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_Resguardo getUY_Resguardo() throws RuntimeException
    {
		return (I_UY_Resguardo)MTable.get(getCtx(), I_UY_Resguardo.Table_Name)
			.getPO(getUY_Resguardo_ID(), get_TrxName());	}

	/** Set UY_Resguardo.
		@param UY_Resguardo_ID UY_Resguardo	  */
	public void setUY_Resguardo_ID (int UY_Resguardo_ID)
	{
		if (UY_Resguardo_ID < 1) 
			set_Value (COLUMNNAME_UY_Resguardo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Resguardo_ID, Integer.valueOf(UY_Resguardo_ID));
	}

	/** Get UY_Resguardo.
		@return UY_Resguardo	  */
	public int getUY_Resguardo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Resguardo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** uy_tipogeneracion AD_Reference_ID=1000064 */
	public static final int UY_TIPOGENERACION_AD_Reference_ID=1000064;
	/** Factura = FAC */
	public static final String UY_TIPOGENERACION_Factura = "FAC";
	/** Remito = REM */
	public static final String UY_TIPOGENERACION_Remito = "REM";
	/** Set uy_tipogeneracion.
		@param uy_tipogeneracion uy_tipogeneracion	  */
	public void setuy_tipogeneracion (String uy_tipogeneracion)
	{

		set_Value (COLUMNNAME_uy_tipogeneracion, uy_tipogeneracion);
	}

	/** Get uy_tipogeneracion.
		@return uy_tipogeneracion	  */
	public String getuy_tipogeneracion () 
	{
		return (String)get_Value(COLUMNNAME_uy_tipogeneracion);
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (String Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public String getVolume () 
	{
		return (String)get_Value(COLUMNNAME_Volume);
	}
}