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
import org.openup.model.I_UY_HRSeguroSalud;
import org.openup.model.I_UY_ZonaReparto;

/** Generated Model for C_BPartner
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_BPartner extends PO implements I_C_BPartner, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141006L;

    /** Standard Constructor */
    public X_C_BPartner (Properties ctx, int C_BPartner_ID, String trxName)
    {
      super (ctx, C_BPartner_ID, trxName);
      /** if (C_BPartner_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BP_Group_ID (0);
			setDocumentType (null);
// RUT
			setIsCustomer (false);
			setIsEmployee (false);
			setIsOneTime (false);
			setIsPOTaxExempt (false);
// N
			setIsProspect (false);
			setIsRecurrent (false);
// N
			setIsSalesRep (false);
			setIsSummary (false);
			setIsVendor (false);
			setName (null);
			setSendEMail (false);
			setSO_CreditLimit (Env.ZERO);
			setSO_CreditUsed (Env.ZERO);
			setuy_credit_action (null);
// VS
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_C_BPartner (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_BPartner[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Acquisition Cost.
		@param AcqusitionCost 
		The cost of gaining the prospect as a customer
	  */
	public void setAcqusitionCost (BigDecimal AcqusitionCost)
	{
		set_Value (COLUMNNAME_AcqusitionCost, AcqusitionCost);
	}

	/** Get Acquisition Cost.
		@return The cost of gaining the prospect as a customer
	  */
	public BigDecimal getAcqusitionCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AcqusitionCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Life Time Value.
		@param ActualLifeTimeValue 
		Actual Life Time Revenue
	  */
	public void setActualLifeTimeValue (BigDecimal ActualLifeTimeValue)
	{
		set_Value (COLUMNNAME_ActualLifeTimeValue, ActualLifeTimeValue);
	}

	/** Get Actual Life Time Value.
		@return Actual Life Time Revenue
	  */
	public BigDecimal getActualLifeTimeValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualLifeTimeValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** AD_Language AD_Reference_ID=327 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	/** Set Language.
		@param AD_Language 
		Language for this entity
	  */
	public void setAD_Language (String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Language.
		@return Language for this entity
	  */
	public String getAD_Language () 
	{
		return (String)get_Value(COLUMNNAME_AD_Language);
	}

	/** Set Linked Organization.
		@param AD_OrgBP_ID 
		The Business Partner is another Organization for explicit Inter-Org transactions
	  */
	public void setAD_OrgBP_ID (String AD_OrgBP_ID)
	{
		set_Value (COLUMNNAME_AD_OrgBP_ID, AD_OrgBP_ID);
	}

	/** Get Linked Organization.
		@return The Business Partner is another Organization for explicit Inter-Org transactions
	  */
	public String getAD_OrgBP_ID () 
	{
		return (String)get_Value(COLUMNNAME_AD_OrgBP_ID);
	}

	/** Set birthdate.
		@param birthdate birthdate	  */
	public void setbirthdate (Timestamp birthdate)
	{
		set_Value (COLUMNNAME_birthdate, birthdate);
	}

	/** Get birthdate.
		@return birthdate	  */
	public Timestamp getbirthdate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_birthdate);
	}

	/** Set Partner Parent.
		@param BPartner_Parent_ID 
		Business Partner Parent
	  */
	public void setBPartner_Parent_ID (int BPartner_Parent_ID)
	{
		if (BPartner_Parent_ID < 1) 
			set_Value (COLUMNNAME_BPartner_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_BPartner_Parent_ID, Integer.valueOf(BPartner_Parent_ID));
	}

	/** Get Partner Parent.
		@return Business Partner Parent
	  */
	public int getBPartner_Parent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BPartner_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (org.compiere.model.I_C_BP_Group)MTable.get(getCtx(), org.compiere.model.I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Dunning getC_Dunning() throws RuntimeException
    {
		return (org.compiere.model.I_C_Dunning)MTable.get(getCtx(), org.compiere.model.I_C_Dunning.Table_Name)
			.getPO(getC_Dunning_ID(), get_TrxName());	}

	/** Set Dunning.
		@param C_Dunning_ID 
		Dunning Rules for overdue invoices
	  */
	public void setC_Dunning_ID (int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_ID, Integer.valueOf(C_Dunning_ID));
	}

	/** Get Dunning.
		@return Dunning Rules for overdue invoices
	  */
	public int getC_Dunning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_ID);
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

	public org.compiere.model.I_C_Greeting getC_Greeting() throws RuntimeException
    {
		return (org.compiere.model.I_C_Greeting)MTable.get(getCtx(), org.compiere.model.I_C_Greeting.Table_Name)
			.getPO(getC_Greeting_ID(), get_TrxName());	}

	/** Set Greeting.
		@param C_Greeting_ID 
		Greeting to print on correspondence
	  */
	public void setC_Greeting_ID (int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_Value (COLUMNNAME_C_Greeting_ID, Integer.valueOf(C_Greeting_ID));
	}

	/** Get Greeting.
		@return Greeting to print on correspondence
	  */
	public int getC_Greeting_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Greeting_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceSchedule)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceSchedule.Table_Name)
			.getPO(getC_InvoiceSchedule_ID(), get_TrxName());	}

	/** Set Invoice Schedule.
		@param C_InvoiceSchedule_ID 
		Schedule for generating Invoices
	  */
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, Integer.valueOf(C_InvoiceSchedule_ID));
	}

	/** Get Invoice Schedule.
		@return Schedule for generating Invoices
	  */
	public int getC_InvoiceSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceSchedule_ID);
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

	public org.eevolution.model.I_C_TaxGroup getC_TaxGroup() throws RuntimeException
    {
		return (org.eevolution.model.I_C_TaxGroup)MTable.get(getCtx(), org.eevolution.model.I_C_TaxGroup.Table_Name)
			.getPO(getC_TaxGroup_ID(), get_TrxName());	}

	/** Set Tax Group.
		@param C_TaxGroup_ID Tax Group	  */
	public void setC_TaxGroup_ID (int C_TaxGroup_ID)
	{
		if (C_TaxGroup_ID < 1) 
			set_Value (COLUMNNAME_C_TaxGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxGroup_ID, Integer.valueOf(C_TaxGroup_ID));
	}

	/** Get Tax Group.
		@return Tax Group	  */
	public int getC_TaxGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Document Copies.
		@param DocumentCopies 
		Number of copies to be printed
	  */
	public void setDocumentCopies (int DocumentCopies)
	{
		set_Value (COLUMNNAME_DocumentCopies, Integer.valueOf(DocumentCopies));
	}

	/** Get Document Copies.
		@return Number of copies to be printed
	  */
	public int getDocumentCopies () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentCopies);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** DocumentType AD_Reference_ID=1000377 */
	public static final int DOCUMENTTYPE_AD_Reference_ID=1000377;
	/** RUT = RUT */
	public static final String DOCUMENTTYPE_RUT = "RUT";
	/** CNPJ = CNPJ */
	public static final String DOCUMENTTYPE_CNPJ = "CNPJ";
	/** Set Document Type.
		@param DocumentType 
		Document Type
	  */
	public void setDocumentType (String DocumentType)
	{

		set_Value (COLUMNNAME_DocumentType, DocumentType);
	}

	/** Get Document Type.
		@return Document Type
	  */
	public String getDocumentType () 
	{
		return (String)get_Value(COLUMNNAME_DocumentType);
	}

	/** Set Dunning Grace.
		@param DunningGrace Dunning Grace	  */
	public void setDunningGrace (Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	/** Get Dunning Grace.
		@return Dunning Grace	  */
	public Timestamp getDunningGrace () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DunningGrace);
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

	/** Set First Sale.
		@param FirstSale 
		Date of First Sale
	  */
	public void setFirstSale (Timestamp FirstSale)
	{
		set_Value (COLUMNNAME_FirstSale, FirstSale);
	}

	/** Get First Sale.
		@return Date of First Sale
	  */
	public Timestamp getFirstSale () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FirstSale);
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

	/** Set Flat Discount %.
		@param FlatDiscount 
		Flat discount percentage 
	  */
	public void setFlatDiscount (BigDecimal FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

	/** Get Flat Discount %.
		@return Flat discount percentage 
	  */
	public BigDecimal getFlatDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatDiscount);
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

	public org.compiere.model.I_AD_PrintFormat getInvoice_PrintFormat() throws RuntimeException
    {
		return (org.compiere.model.I_AD_PrintFormat)MTable.get(getCtx(), org.compiere.model.I_AD_PrintFormat.Table_Name)
			.getPO(getInvoice_PrintFormat_ID(), get_TrxName());	}

	/** Set Invoice Print Format.
		@param Invoice_PrintFormat_ID 
		Print Format for printing Invoices
	  */
	public void setInvoice_PrintFormat_ID (int Invoice_PrintFormat_ID)
	{
		if (Invoice_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_Invoice_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_Invoice_PrintFormat_ID, Integer.valueOf(Invoice_PrintFormat_ID));
	}

	/** Get Invoice Print Format.
		@return Print Format for printing Invoices
	  */
	public int getInvoice_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Invoice_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Customer.
		@param IsCustomer 
		Indicates if this Business Partner is a Customer
	  */
	public void setIsCustomer (boolean IsCustomer)
	{
		set_Value (COLUMNNAME_IsCustomer, Boolean.valueOf(IsCustomer));
	}

	/** Get Customer.
		@return Indicates if this Business Partner is a Customer
	  */
	public boolean isCustomer () 
	{
		Object oo = get_Value(COLUMNNAME_IsCustomer);
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

	/** Set Employee.
		@param IsEmployee 
		Indicates if  this Business Partner is an employee
	  */
	public void setIsEmployee (boolean IsEmployee)
	{
		set_Value (COLUMNNAME_IsEmployee, Boolean.valueOf(IsEmployee));
	}

	/** Get Employee.
		@return Indicates if  this Business Partner is an employee
	  */
	public boolean isEmployee () 
	{
		Object oo = get_Value(COLUMNNAME_IsEmployee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsHr.
		@param IsHr IsHr	  */
	public void setIsHr (boolean IsHr)
	{
		set_Value (COLUMNNAME_IsHr, Boolean.valueOf(IsHr));
	}

	/** Get IsHr.
		@return IsHr	  */
	public boolean isHr () 
	{
		Object oo = get_Value(COLUMNNAME_IsHr);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Manufacturer.
		@param IsManufacturer 
		Indicate role of this Business partner as Manufacturer
	  */
	public void setIsManufacturer (boolean IsManufacturer)
	{
		set_Value (COLUMNNAME_IsManufacturer, Boolean.valueOf(IsManufacturer));
	}

	/** Get Is Manufacturer.
		@return Indicate role of this Business partner as Manufacturer
	  */
	public boolean isManufacturer () 
	{
		Object oo = get_Value(COLUMNNAME_IsManufacturer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set One time transaction.
		@param IsOneTime One time transaction	  */
	public void setIsOneTime (boolean IsOneTime)
	{
		set_Value (COLUMNNAME_IsOneTime, Boolean.valueOf(IsOneTime));
	}

	/** Get One time transaction.
		@return One time transaction	  */
	public boolean isOneTime () 
	{
		Object oo = get_Value(COLUMNNAME_IsOneTime);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PO Tax exempt.
		@param IsPOTaxExempt 
		Business partner is exempt from tax on purchases
	  */
	public void setIsPOTaxExempt (boolean IsPOTaxExempt)
	{
		set_Value (COLUMNNAME_IsPOTaxExempt, Boolean.valueOf(IsPOTaxExempt));
	}

	/** Get PO Tax exempt.
		@return Business partner is exempt from tax on purchases
	  */
	public boolean isPOTaxExempt () 
	{
		Object oo = get_Value(COLUMNNAME_IsPOTaxExempt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isproduction.
		@param isproduction isproduction	  */
	public void setisproduction (boolean isproduction)
	{
		set_Value (COLUMNNAME_isproduction, Boolean.valueOf(isproduction));
	}

	/** Get isproduction.
		@return isproduction	  */
	public boolean isproduction () 
	{
		Object oo = get_Value(COLUMNNAME_isproduction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Prospect.
		@param IsProspect 
		Indicates this is a Prospect
	  */
	public void setIsProspect (boolean IsProspect)
	{
		set_Value (COLUMNNAME_IsProspect, Boolean.valueOf(IsProspect));
	}

	/** Get Prospect.
		@return Indicates this is a Prospect
	  */
	public boolean isProspect () 
	{
		Object oo = get_Value(COLUMNNAME_IsProspect);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRecurrent.
		@param IsRecurrent IsRecurrent	  */
	public void setIsRecurrent (boolean IsRecurrent)
	{
		set_Value (COLUMNNAME_IsRecurrent, Boolean.valueOf(IsRecurrent));
	}

	/** Get IsRecurrent.
		@return IsRecurrent	  */
	public boolean isRecurrent () 
	{
		Object oo = get_Value(COLUMNNAME_IsRecurrent);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Representative.
		@param IsSalesRep 
		Indicates if  the business partner is a sales representative or company agent
	  */
	public void setIsSalesRep (boolean IsSalesRep)
	{
		set_Value (COLUMNNAME_IsSalesRep, Boolean.valueOf(IsSalesRep));
	}

	/** Get Sales Representative.
		@return Indicates if  the business partner is a sales representative or company agent
	  */
	public boolean isSalesRep () 
	{
		Object oo = get_Value(COLUMNNAME_IsSalesRep);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Summary Level.
		@param IsSummary 
		This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SO Tax exempt.
		@param IsTaxExempt 
		Business partner is exempt from tax on sales
	  */
	public void setIsTaxExempt (boolean IsTaxExempt)
	{
		set_Value (COLUMNNAME_IsTaxExempt, Boolean.valueOf(IsTaxExempt));
	}

	/** Get SO Tax exempt.
		@return Business partner is exempt from tax on sales
	  */
	public boolean isTaxExempt () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxExempt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Vendor.
		@param IsVendor 
		Indicates if this Business Partner is a Vendor
	  */
	public void setIsVendor (boolean IsVendor)
	{
		set_Value (COLUMNNAME_IsVendor, Boolean.valueOf(IsVendor));
	}

	/** Get Vendor.
		@return Indicates if this Business Partner is a Vendor
	  */
	public boolean isVendor () 
	{
		Object oo = get_Value(COLUMNNAME_IsVendor);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** leavingreason AD_Reference_ID=1000141 */
	public static final int LEAVINGREASON_AD_Reference_ID=1000141;
	/** Fin Contrato = FC */
	public static final String LEAVINGREASON_FinContrato = "FC";
	/** Despido = DES */
	public static final String LEAVINGREASON_Despido = "DES";
	/** Cambio Empresa = CE */
	public static final String LEAVINGREASON_CambioEmpresa = "CE";
	/** Voluntario = VOL */
	public static final String LEAVINGREASON_Voluntario = "VOL";
	/** Fallecimiento = FA */
	public static final String LEAVINGREASON_Fallecimiento = "FA";
	/** Jubilaci贸n = JU */
	public static final String LEAVINGREASON_Jubilaci髇 = "JU";
	/** Baja Por Error = BE */
	public static final String LEAVINGREASON_BajaPorError = "BE";
	/** Alta Por Error = AE */
	public static final String LEAVINGREASON_AltaPorError = "AE";
	/** 3-Fallecimiento = 3 */
	public static final String LEAVINGREASON_3_Fallecimiento = "3";
	/** 4-T茅rmino de Contrato = 4 */
	public static final String LEAVINGREASON_4_T閞minoDeContrato = "4";
	/** 1-Voluntario = 1 */
	public static final String LEAVINGREASON_1_Voluntario = "1";
	/** 2-Despido = 2 */
	public static final String LEAVINGREASON_2_Despido = "2";
	/** 10-T茅rmino de mandato = 10 */
	public static final String LEAVINGREASON_10_T閞minoDeMandato = "10";
	/** 11-Cese por motivos reglamentarios = 11 */
	public static final String LEAVINGREASON_11_CesePorMotivosReglamentarios = "11";
	/** 5-Jubilaci贸n = 5 */
	public static final String LEAVINGREASON_5_Jubilaci髇 = "5";
	/** 8-Cambio de titular,fusi贸n,escisi贸n de empresas... = 8 */
	public static final String LEAVINGREASON_8_CambioDeTitularFusi髇Escisi髇DeEmpresas = "8";
	/** 22-Cesante.Inicio sub. por incapacidad f铆sica = 22 */
	public static final String LEAVINGREASON_22_CesanteInicioSubPorIncapacidadF韘ica = "22";
	/** 23-Cesante - Inicia cobro incentivo = 23 */
	public static final String LEAVINGREASON_23_Cesante_IniciaCobroIncentivo = "23";
	/** 12-Cese por edad = 12 */
	public static final String LEAVINGREASON_12_CesePorEdad = "12";
	/** 13-Cese por mala conducta = 13 */
	public static final String LEAVINGREASON_13_CesePorMalaConducta = "13";
	/** 33-Cese de comisi贸n = 33 */
	public static final String LEAVINGREASON_33_CeseDeComisi髇 = "33";
	/** 50-Otros motivos = 50 */
	public static final String LEAVINGREASON_50_OtrosMotivos = "50";
	/** 32-Cese de incentivo = 32 */
	public static final String LEAVINGREASON_32_CeseDeIncentivo = "32";
	/** Set leavingreason.
		@param leavingreason leavingreason	  */
	public void setleavingreason (String leavingreason)
	{

		set_Value (COLUMNNAME_leavingreason, leavingreason);
	}

	/** Get leavingreason.
		@return leavingreason	  */
	public String getleavingreason () 
	{
		return (String)get_Value(COLUMNNAME_leavingreason);
	}

	/** Set Logo.
		@param Logo_ID Logo	  */
	public void setLogo_ID (int Logo_ID)
	{
		if (Logo_ID < 1) 
			set_Value (COLUMNNAME_Logo_ID, null);
		else 
			set_Value (COLUMNNAME_Logo_ID, Integer.valueOf(Logo_ID));
	}

	/** Get Logo.
		@return Logo	  */
	public int getLogo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Logo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** maritalstatus AD_Reference_ID=1000130 */
	public static final int MARITALSTATUS_AD_Reference_ID=1000130;
	/** Casado = CAS */
	public static final String MARITALSTATUS_Casado = "CAS";
	/** Soltero = SOL */
	public static final String MARITALSTATUS_Soltero = "SOL";
	/** Uni贸n Libre = UL */
	public static final String MARITALSTATUS_Uni髇Libre = "UL";
	/** Divorciado = DIV */
	public static final String MARITALSTATUS_Divorciado = "DIV";
	/** Separado = SEP */
	public static final String MARITALSTATUS_Separado = "SEP";
	/** Viudo = VIU */
	public static final String MARITALSTATUS_Viudo = "VIU";
	/** Set maritalstatus.
		@param maritalstatus maritalstatus	  */
	public void setmaritalstatus (String maritalstatus)
	{

		set_Value (COLUMNNAME_maritalstatus, maritalstatus);
	}

	/** Get maritalstatus.
		@return maritalstatus	  */
	public String getmaritalstatus () 
	{
		return (String)get_Value(COLUMNNAME_maritalstatus);
	}

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException
    {
		return (org.compiere.model.I_M_DiscountSchema)MTable.get(getCtx(), org.compiere.model.I_M_DiscountSchema.Table_Name)
			.getPO(getM_DiscountSchema_ID(), get_TrxName());	}

	/** Set Discount Schema.
		@param M_DiscountSchema_ID 
		Schema to calculate the trade discount percentage
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Discount Schema.
		@return Schema to calculate the trade discount percentage
	  */
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set modificanombre.
		@param modificanombre modificanombre	  */
	public void setmodificanombre (String modificanombre)
	{
		set_Value (COLUMNNAME_modificanombre, modificanombre);
	}

	/** Get modificanombre.
		@return modificanombre	  */
	public String getmodificanombre () 
	{
		return (String)get_Value(COLUMNNAME_modificanombre);
	}

	/** Set modificarazon.
		@param modificarazon modificarazon	  */
	public void setmodificarazon (String modificarazon)
	{
		set_Value (COLUMNNAME_modificarazon, modificarazon);
	}

	/** Get modificarazon.
		@return modificarazon	  */
	public String getmodificarazon () 
	{
		return (String)get_Value(COLUMNNAME_modificarazon);
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

	/** Set NAICS/SIC.
		@param NAICS 
		Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	public void setNAICS (String NAICS)
	{
		set_Value (COLUMNNAME_NAICS, NAICS);
	}

	/** Get NAICS/SIC.
		@return Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	public String getNAICS () 
	{
		return (String)get_Value(COLUMNNAME_NAICS);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
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

	/** natcodetype AD_Reference_ID=1000132 */
	public static final int NATCODETYPE_AD_Reference_ID=1000132;
	/** CI = CI */
	public static final String NATCODETYPE_CI = "CI";
	/** Pasaporte = PP */
	public static final String NATCODETYPE_Pasaporte = "PP";
	/** Set natcodetype.
		@param natcodetype natcodetype	  */
	public void setnatcodetype (String natcodetype)
	{

		set_Value (COLUMNNAME_natcodetype, natcodetype);
	}

	/** Get natcodetype.
		@return natcodetype	  */
	public String getnatcodetype () 
	{
		return (String)get_Value(COLUMNNAME_natcodetype);
	}

	/** Set National Code.
		@param NationalCode National Code	  */
	public void setNationalCode (String NationalCode)
	{
		set_Value (COLUMNNAME_NationalCode, NationalCode);
	}

	/** Get National Code.
		@return National Code	  */
	public String getNationalCode () 
	{
		return (String)get_Value(COLUMNNAME_NationalCode);
	}

	/** nationality AD_Reference_ID=1000134 */
	public static final int NATIONALITY_AD_Reference_ID=1000134;
	/** Extranjero = EX */
	public static final String NATIONALITY_Extranjero = "EX";
	/** Ciudadano Legal = CL */
	public static final String NATIONALITY_CiudadanoLegal = "CL";
	/** Oriental = OR */
	public static final String NATIONALITY_Oriental = "OR";
	/** Set nationality.
		@param nationality nationality	  */
	public void setnationality (String nationality)
	{

		set_Value (COLUMNNAME_nationality, nationality);
	}

	/** Get nationality.
		@return nationality	  */
	public String getnationality () 
	{
		return (String)get_Value(COLUMNNAME_nationality);
	}

	/** Set nrolegajo.
		@param nrolegajo nrolegajo	  */
	public void setnrolegajo (String nrolegajo)
	{
		set_Value (COLUMNNAME_nrolegajo, nrolegajo);
	}

	/** Get nrolegajo.
		@return nrolegajo	  */
	public String getnrolegajo () 
	{
		return (String)get_Value(COLUMNNAME_nrolegajo);
	}

	/** Set nrotarjeta.
		@param nrotarjeta nrotarjeta	  */
	public void setnrotarjeta (String nrotarjeta)
	{
		set_Value (COLUMNNAME_nrotarjeta, nrotarjeta);
	}

	/** Get nrotarjeta.
		@return nrotarjeta	  */
	public String getnrotarjeta () 
	{
		return (String)get_Value(COLUMNNAME_nrotarjeta);
	}

	/** Set Employees.
		@param NumberEmployees 
		Number of employees
	  */
	public void setNumberEmployees (int NumberEmployees)
	{
		set_Value (COLUMNNAME_NumberEmployees, Integer.valueOf(NumberEmployees));
	}

	/** Get Employees.
		@return Number of employees
	  */
	public int getNumberEmployees () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumberEmployees);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set passportexpdate.
		@param passportexpdate passportexpdate	  */
	public void setpassportexpdate (Timestamp passportexpdate)
	{
		set_Value (COLUMNNAME_passportexpdate, passportexpdate);
	}

	/** Get passportexpdate.
		@return passportexpdate	  */
	public Timestamp getpassportexpdate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_passportexpdate);
	}

	/** Set passportorigin.
		@param passportorigin passportorigin	  */
	public void setpassportorigin (String passportorigin)
	{
		set_Value (COLUMNNAME_passportorigin, passportorigin);
	}

	/** Get passportorigin.
		@return passportorigin	  */
	public String getpassportorigin () 
	{
		return (String)get_Value(COLUMNNAME_passportorigin);
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

	/** PaymentRulePO AD_Reference_ID=195 */
	public static final int PAYMENTRULEPO_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULEPO_Cash = "B";
	/** Credit Card = K */
	public static final String PAYMENTRULEPO_CreditCard = "K";
	/** Direct Deposit = T */
	public static final String PAYMENTRULEPO_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULEPO_Check = "S";
	/** On Credit = P */
	public static final String PAYMENTRULEPO_OnCredit = "P";
	/** Direct Debit = D */
	public static final String PAYMENTRULEPO_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULEPO_Mixed = "M";
	/** Set Payment Rule.
		@param PaymentRulePO 
		Purchase payment option
	  */
	public void setPaymentRulePO (String PaymentRulePO)
	{

		set_Value (COLUMNNAME_PaymentRulePO, PaymentRulePO);
	}

	/** Get Payment Rule.
		@return Purchase payment option
	  */
	public String getPaymentRulePO () 
	{
		return (String)get_Value(COLUMNNAME_PaymentRulePO);
	}

	public org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema() throws RuntimeException
    {
		return (org.compiere.model.I_M_DiscountSchema)MTable.get(getCtx(), org.compiere.model.I_M_DiscountSchema.Table_Name)
			.getPO(getPO_DiscountSchema_ID(), get_TrxName());	}

	/** Set PO Discount Schema.
		@param PO_DiscountSchema_ID 
		Schema to calculate the purchase trade discount percentage
	  */
	public void setPO_DiscountSchema_ID (int PO_DiscountSchema_ID)
	{
		if (PO_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, Integer.valueOf(PO_DiscountSchema_ID));
	}

	/** Get PO Discount Schema.
		@return Schema to calculate the purchase trade discount percentage
	  */
	public int getPO_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_PaymentTerm getPO_PaymentTerm() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentTerm)MTable.get(getCtx(), org.compiere.model.I_C_PaymentTerm.Table_Name)
			.getPO(getPO_PaymentTerm_ID(), get_TrxName());	}

	/** Set PO Payment Term.
		@param PO_PaymentTerm_ID 
		Payment rules for a purchase order
	  */
	public void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID)
	{
		if (PO_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, Integer.valueOf(PO_PaymentTerm_ID));
	}

	/** Get PO Payment Term.
		@return Payment rules for a purchase order
	  */
	public int getPO_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_PriceList getPO_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getPO_PriceList_ID(), get_TrxName());	}

	/** Set Purchase Pricelist.
		@param PO_PriceList_ID 
		Price List used by this Business Partner
	  */
	public void setPO_PriceList_ID (int PO_PriceList_ID)
	{
		if (PO_PriceList_ID < 1) 
			set_Value (COLUMNNAME_PO_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PriceList_ID, Integer.valueOf(PO_PriceList_ID));
	}

	/** Get Purchase Pricelist.
		@return Price List used by this Business Partner
	  */
	public int getPO_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Potential Life Time Value.
		@param PotentialLifeTimeValue 
		Total Revenue expected
	  */
	public void setPotentialLifeTimeValue (BigDecimal PotentialLifeTimeValue)
	{
		set_Value (COLUMNNAME_PotentialLifeTimeValue, PotentialLifeTimeValue);
	}

	/** Get Potential Life Time Value.
		@return Total Revenue expected
	  */
	public BigDecimal getPotentialLifeTimeValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PotentialLifeTimeValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Rating.
		@param Rating 
		Classification or Importance
	  */
	public void setRating (String Rating)
	{
		set_Value (COLUMNNAME_Rating, Rating);
	}

	/** Get Rating.
		@return Classification or Importance
	  */
	public String getRating () 
	{
		return (String)get_Value(COLUMNNAME_Rating);
	}

	/** Set Reference No.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
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

	/** Set Sales Volume in 1.000.
		@param SalesVolume 
		Total Volume of Sales in Thousands of Currency
	  */
	public void setSalesVolume (int SalesVolume)
	{
		set_Value (COLUMNNAME_SalesVolume, Integer.valueOf(SalesVolume));
	}

	/** Get Sales Volume in 1.000.
		@return Total Volume of Sales in Thousands of Currency
	  */
	public int getSalesVolume () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesVolume);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** sex AD_Reference_ID=1000131 */
	public static final int SEX_AD_Reference_ID=1000131;
	/** Masculino = M */
	public static final String SEX_Masculino = "M";
	/** Femenino = F */
	public static final String SEX_Femenino = "F";
	/** Set sex.
		@param sex sex	  */
	public void setsex (String sex)
	{

		set_Value (COLUMNNAME_sex, sex);
	}

	/** Get sex.
		@return sex	  */
	public String getsex () 
	{
		return (String)get_Value(COLUMNNAME_sex);
	}

	/** Set Share.
		@param ShareOfCustomer 
		Share of Customer's business as a percentage
	  */
	public void setShareOfCustomer (int ShareOfCustomer)
	{
		set_Value (COLUMNNAME_ShareOfCustomer, Integer.valueOf(ShareOfCustomer));
	}

	/** Get Share.
		@return Share of Customer's business as a percentage
	  */
	public int getShareOfCustomer () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShareOfCustomer);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Min Shelf Life %.
		@param ShelfLifeMinPct 
		Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	public void setShelfLifeMinPct (int ShelfLifeMinPct)
	{
		set_Value (COLUMNNAME_ShelfLifeMinPct, Integer.valueOf(ShelfLifeMinPct));
	}

	/** Get Min Shelf Life %.
		@return Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	public int getShelfLifeMinPct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfLifeMinPct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit Limit.
		@param SO_CreditLimit 
		Total outstanding invoice amounts allowed
	  */
	public void setSO_CreditLimit (BigDecimal SO_CreditLimit)
	{
		set_Value (COLUMNNAME_SO_CreditLimit, SO_CreditLimit);
	}

	/** Get Credit Limit.
		@return Total outstanding invoice amounts allowed
	  */
	public BigDecimal getSO_CreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SO_CreditLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** SOCreditStatus AD_Reference_ID=289 */
	public static final int SOCREDITSTATUS_AD_Reference_ID=289;
	/** Credit Stop = S */
	public static final String SOCREDITSTATUS_CreditStop = "S";
	/** Credit Hold = H */
	public static final String SOCREDITSTATUS_CreditHold = "H";
	/** Credit Watch = W */
	public static final String SOCREDITSTATUS_CreditWatch = "W";
	/** No Credit Check = X */
	public static final String SOCREDITSTATUS_NoCreditCheck = "X";
	/** Credit OK = O */
	public static final String SOCREDITSTATUS_CreditOK = "O";
	/** Set Credit Status.
		@param SOCreditStatus 
		Business Partner Credit Status
	  */
	public void setSOCreditStatus (String SOCreditStatus)
	{

		set_Value (COLUMNNAME_SOCreditStatus, SOCreditStatus);
	}

	/** Get Credit Status.
		@return Business Partner Credit Status
	  */
	public String getSOCreditStatus () 
	{
		return (String)get_Value(COLUMNNAME_SOCreditStatus);
	}

	/** Set Credit Used.
		@param SO_CreditUsed 
		Current open balance
	  */
	public void setSO_CreditUsed (BigDecimal SO_CreditUsed)
	{
		set_ValueNoCheck (COLUMNNAME_SO_CreditUsed, SO_CreditUsed);
	}

	/** Get Credit Used.
		@return Current open balance
	  */
	public BigDecimal getSO_CreditUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SO_CreditUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Order Description.
		@param SO_Description 
		Description to be used on orders
	  */
	public void setSO_Description (String SO_Description)
	{
		set_Value (COLUMNNAME_SO_Description, SO_Description);
	}

	/** Get Order Description.
		@return Description to be used on orders
	  */
	public String getSO_Description () 
	{
		return (String)get_Value(COLUMNNAME_SO_Description);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Tax ID.
		@param TaxID 
		Tax Identification
	  */
	public void setTaxID (String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Tax ID.
		@return Tax Identification
	  */
	public String getTaxID () 
	{
		return (String)get_Value(COLUMNNAME_TaxID);
	}

	/** Set Open Balance.
		@param TotalOpenBalance 
		Total Open Balance Amount in primary Accounting Currency
	  */
	public void setTotalOpenBalance (BigDecimal TotalOpenBalance)
	{
		set_Value (COLUMNNAME_TotalOpenBalance, TotalOpenBalance);
	}

	/** Get Open Balance.
		@return Total Open Balance Amount in primary Accounting Currency
	  */
	public BigDecimal getTotalOpenBalance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOpenBalance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set URL.
		@param URL 
		Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL () 
	{
		return (String)get_Value(COLUMNNAME_URL);
	}

	/** Set UY_AsignacionFamiliar.
		@param UY_AsignacionFamiliar UY_AsignacionFamiliar	  */
	public void setUY_AsignacionFamiliar (boolean UY_AsignacionFamiliar)
	{
		set_Value (COLUMNNAME_UY_AsignacionFamiliar, Boolean.valueOf(UY_AsignacionFamiliar));
	}

	/** Get UY_AsignacionFamiliar.
		@return UY_AsignacionFamiliar	  */
	public boolean isUY_AsignacionFamiliar () 
	{
		Object oo = get_Value(COLUMNNAME_UY_AsignacionFamiliar);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set UY_Collector.
		@param UY_Collector_ID UY_Collector	  */
	public void setUY_Collector_ID (int UY_Collector_ID)
	{
		if (UY_Collector_ID < 1) 
			set_Value (COLUMNNAME_UY_Collector_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Collector_ID, Integer.valueOf(UY_Collector_ID));
	}

	/** Get UY_Collector.
		@return UY_Collector	  */
	public int getUY_Collector_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Collector_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** uy_credit_action AD_Reference_ID=1000063 */
	public static final int UY_CREDIT_ACTION_AD_Reference_ID=1000063;
	/** Verificar = VS */
	public static final String UY_CREDIT_ACTION_Verificar = "VS";
	/** No Verificar = VN */
	public static final String UY_CREDIT_ACTION_NoVerificar = "VN";
	/** Suspendido = SS */
	public static final String UY_CREDIT_ACTION_Suspendido = "SS";
	/** Set uy_credit_action.
		@param uy_credit_action uy_credit_action	  */
	public void setuy_credit_action (String uy_credit_action)
	{

		set_Value (COLUMNNAME_uy_credit_action, uy_credit_action);
	}

	/** Get uy_credit_action.
		@return uy_credit_action	  */
	public String getuy_credit_action () 
	{
		return (String)get_Value(COLUMNNAME_uy_credit_action);
	}

	/** Set uy_creditlimit_doc.
		@param uy_creditlimit_doc uy_creditlimit_doc	  */
	public void setuy_creditlimit_doc (BigDecimal uy_creditlimit_doc)
	{
		set_Value (COLUMNNAME_uy_creditlimit_doc, uy_creditlimit_doc);
	}

	/** Get uy_creditlimit_doc.
		@return uy_creditlimit_doc	  */
	public BigDecimal getuy_creditlimit_doc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_creditlimit_doc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_credit_openamt.
		@param uy_credit_openamt uy_credit_openamt	  */
	public void setuy_credit_openamt (BigDecimal uy_credit_openamt)
	{
		throw new IllegalArgumentException ("uy_credit_openamt is virtual column");	}

	/** Get uy_credit_openamt.
		@return uy_credit_openamt	  */
	public BigDecimal getuy_credit_openamt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_credit_openamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_credit_openamt_doc.
		@param uy_credit_openamt_doc uy_credit_openamt_doc	  */
	public void setuy_credit_openamt_doc (BigDecimal uy_credit_openamt_doc)
	{
		throw new IllegalArgumentException ("uy_credit_openamt_doc is virtual column");	}

	/** Get uy_credit_openamt_doc.
		@return uy_credit_openamt_doc	  */
	public BigDecimal getuy_credit_openamt_doc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_credit_openamt_doc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_HRAcumulacionLaboral.
		@param UY_HRAcumulacionLaboral_ID UY_HRAcumulacionLaboral	  */
	public void setUY_HRAcumulacionLaboral_ID (int UY_HRAcumulacionLaboral_ID)
	{
		if (UY_HRAcumulacionLaboral_ID < 1) 
			set_Value (COLUMNNAME_UY_HRAcumulacionLaboral_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRAcumulacionLaboral_ID, Integer.valueOf(UY_HRAcumulacionLaboral_ID));
	}

	/** Get UY_HRAcumulacionLaboral.
		@return UY_HRAcumulacionLaboral	  */
	public int getUY_HRAcumulacionLaboral_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRAcumulacionLaboral_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRCodigoExoneracion.
		@param UY_HRCodigoExoneracion_ID UY_HRCodigoExoneracion	  */
	public void setUY_HRCodigoExoneracion_ID (int UY_HRCodigoExoneracion_ID)
	{
		if (UY_HRCodigoExoneracion_ID < 1) 
			set_Value (COLUMNNAME_UY_HRCodigoExoneracion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRCodigoExoneracion_ID, Integer.valueOf(UY_HRCodigoExoneracion_ID));
	}

	/** Get UY_HRCodigoExoneracion.
		@return UY_HRCodigoExoneracion	  */
	public int getUY_HRCodigoExoneracion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRCodigoExoneracion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRComputoEspecial.
		@param UY_HRComputoEspecial_ID UY_HRComputoEspecial	  */
	public void setUY_HRComputoEspecial_ID (int UY_HRComputoEspecial_ID)
	{
		if (UY_HRComputoEspecial_ID < 1) 
			set_Value (COLUMNNAME_UY_HRComputoEspecial_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRComputoEspecial_ID, Integer.valueOf(UY_HRComputoEspecial_ID));
	}

	/** Get UY_HRComputoEspecial.
		@return UY_HRComputoEspecial	  */
	public int getUY_HRComputoEspecial_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRComputoEspecial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRMotivosEgreso.
		@param UY_HRMotivosEgreso_ID UY_HRMotivosEgreso	  */
	public void setUY_HRMotivosEgreso_ID (int UY_HRMotivosEgreso_ID)
	{
		if (UY_HRMotivosEgreso_ID < 1) 
			set_Value (COLUMNNAME_UY_HRMotivosEgreso_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRMotivosEgreso_ID, Integer.valueOf(UY_HRMotivosEgreso_ID));
	}

	/** Get UY_HRMotivosEgreso.
		@return UY_HRMotivosEgreso	  */
	public int getUY_HRMotivosEgreso_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRMotivosEgreso_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_HRSeguroSalud getUY_HRSeguroSalud() throws RuntimeException
    {
		return (I_UY_HRSeguroSalud)MTable.get(getCtx(), I_UY_HRSeguroSalud.Table_Name)
			.getPO(getUY_HRSeguroSalud_ID(), get_TrxName());	}

	/** Set UY_HRSeguroSalud.
		@param UY_HRSeguroSalud_ID UY_HRSeguroSalud	  */
	public void setUY_HRSeguroSalud_ID (int UY_HRSeguroSalud_ID)
	{
		if (UY_HRSeguroSalud_ID < 1) 
			set_Value (COLUMNNAME_UY_HRSeguroSalud_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRSeguroSalud_ID, Integer.valueOf(UY_HRSeguroSalud_ID));
	}

	/** Get UY_HRSeguroSalud.
		@return UY_HRSeguroSalud	  */
	public int getUY_HRSeguroSalud_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRSeguroSalud_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRVinculoFuncional.
		@param UY_HRVinculoFuncional_ID UY_HRVinculoFuncional	  */
	public void setUY_HRVinculoFuncional_ID (int UY_HRVinculoFuncional_ID)
	{
		if (UY_HRVinculoFuncional_ID < 1) 
			set_Value (COLUMNNAME_UY_HRVinculoFuncional_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRVinculoFuncional_ID, Integer.valueOf(UY_HRVinculoFuncional_ID));
	}

	/** Get UY_HRVinculoFuncional.
		@return UY_HRVinculoFuncional	  */
	public int getUY_HRVinculoFuncional_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRVinculoFuncional_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_prioridadgenent.
		@param uy_prioridadgenent uy_prioridadgenent	  */
	public void setuy_prioridadgenent (int uy_prioridadgenent)
	{
		set_Value (COLUMNNAME_uy_prioridadgenent, Integer.valueOf(uy_prioridadgenent));
	}

	/** Get uy_prioridadgenent.
		@return uy_prioridadgenent	  */
	public int getuy_prioridadgenent () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_prioridadgenent);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ZonaReparto getUY_ZonaReparto() throws RuntimeException
    {
		return (I_UY_ZonaReparto)MTable.get(getCtx(), I_UY_ZonaReparto.Table_Name)
			.getPO(getUY_ZonaReparto_ID(), get_TrxName());	}

	/** Set UY_ZonaReparto.
		@param UY_ZonaReparto_ID UY_ZonaReparto	  */
	public void setUY_ZonaReparto_ID (int UY_ZonaReparto_ID)
	{
		if (UY_ZonaReparto_ID < 1) 
			set_Value (COLUMNNAME_UY_ZonaReparto_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ZonaReparto_ID, Integer.valueOf(UY_ZonaReparto_ID));
	}

	/** Get UY_ZonaReparto.
		@return UY_ZonaReparto	  */
	public int getUY_ZonaReparto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ZonaReparto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set weddingdate.
		@param weddingdate weddingdate	  */
	public void setweddingdate (Timestamp weddingdate)
	{
		set_Value (COLUMNNAME_weddingdate, weddingdate);
	}

	/** Get weddingdate.
		@return weddingdate	  */
	public Timestamp getweddingdate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_weddingdate);
	}
}