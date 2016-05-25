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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;
import org.openup.model.I_UY_CanalVentas;
import org.openup.model.I_UY_HRSeguroSalud;
import org.openup.model.I_UY_ZonaReparto;

/** Generated Interface for C_BPartner
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_C_BPartner 
{

    /** TableName=C_BPartner */
    public static final String Table_Name = "C_BPartner";

    /** AD_Table_ID=291 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AcqusitionCost */
    public static final String COLUMNNAME_AcqusitionCost = "AcqusitionCost";

	/** Set Acquisition Cost.
	  * The cost of gaining the prospect as a customer
	  */
	public void setAcqusitionCost (BigDecimal AcqusitionCost);

	/** Get Acquisition Cost.
	  * The cost of gaining the prospect as a customer
	  */
	public BigDecimal getAcqusitionCost();

    /** Column name ActualLifeTimeValue */
    public static final String COLUMNNAME_ActualLifeTimeValue = "ActualLifeTimeValue";

	/** Set Actual Life Time Value.
	  * Actual Life Time Revenue
	  */
	public void setActualLifeTimeValue (BigDecimal ActualLifeTimeValue);

	/** Get Actual Life Time Value.
	  * Actual Life Time Revenue
	  */
	public BigDecimal getActualLifeTimeValue();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/** Set Language.
	  * Language for this entity
	  */
	public void setAD_Language (String AD_Language);

	/** Get Language.
	  * Language for this entity
	  */
	public String getAD_Language();

    /** Column name AD_OrgBP_ID */
    public static final String COLUMNNAME_AD_OrgBP_ID = "AD_OrgBP_ID";

	/** Set Linked Organization.
	  * The Business Partner is another Organization for explicit Inter-Org transactions
	  */
	public void setAD_OrgBP_ID (String AD_OrgBP_ID);

	/** Get Linked Organization.
	  * The Business Partner is another Organization for explicit Inter-Org transactions
	  */
	public String getAD_OrgBP_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name birthdate */
    public static final String COLUMNNAME_birthdate = "birthdate";

	/** Set birthdate	  */
	public void setbirthdate (Timestamp birthdate);

	/** Get birthdate	  */
	public Timestamp getbirthdate();

    /** Column name BPartner_Parent_ID */
    public static final String COLUMNNAME_BPartner_Parent_ID = "BPartner_Parent_ID";

	/** Set Partner Parent.
	  * Business Partner Parent
	  */
	public void setBPartner_Parent_ID (int BPartner_Parent_ID);

	/** Get Partner Parent.
	  * Business Partner Parent
	  */
	public int getBPartner_Parent_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/** Set Business Partner Group.
	  * Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/** Get Business Partner Group.
	  * Business Partner Group
	  */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException;

    /** Column name C_Dunning_ID */
    public static final String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

	/** Set Dunning.
	  * Dunning Rules for overdue invoices
	  */
	public void setC_Dunning_ID (int C_Dunning_ID);

	/** Get Dunning.
	  * Dunning Rules for overdue invoices
	  */
	public int getC_Dunning_ID();

	public org.compiere.model.I_C_Dunning getC_Dunning() throws RuntimeException;

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

    /** Column name C_Greeting_ID */
    public static final String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/** Set Greeting.
	  * Greeting to print on correspondence
	  */
	public void setC_Greeting_ID (int C_Greeting_ID);

	/** Get Greeting.
	  * Greeting to print on correspondence
	  */
	public int getC_Greeting_ID();

	public org.compiere.model.I_C_Greeting getC_Greeting() throws RuntimeException;

    /** Column name C_InvoiceSchedule_ID */
    public static final String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

	/** Set Invoice Schedule.
	  * Schedule for generating Invoices
	  */
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/** Get Invoice Schedule.
	  * Schedule for generating Invoices
	  */
	public int getC_InvoiceSchedule_ID();

	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule() throws RuntimeException;

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name C_TaxGroup_ID */
    public static final String COLUMNNAME_C_TaxGroup_ID = "C_TaxGroup_ID";

	/** Set Tax Group	  */
	public void setC_TaxGroup_ID (int C_TaxGroup_ID);

	/** Get Tax Group	  */
	public int getC_TaxGroup_ID();

	public org.eevolution.model.I_C_TaxGroup getC_TaxGroup() throws RuntimeException;

    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/** Set Delivery Rule.
	  * Defines the timing of Delivery
	  */
	public void setDeliveryRule (String DeliveryRule);

	/** Get Delivery Rule.
	  * Defines the timing of Delivery
	  */
	public String getDeliveryRule();

    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/** Set Delivery Via.
	  * How the order will be delivered
	  */
	public void setDeliveryViaRule (String DeliveryViaRule);

	/** Get Delivery Via.
	  * How the order will be delivered
	  */
	public String getDeliveryViaRule();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Discount %.
	  * Discount in percent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Discount %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount();

    /** Column name DocumentCopies */
    public static final String COLUMNNAME_DocumentCopies = "DocumentCopies";

	/** Set Document Copies.
	  * Number of copies to be printed
	  */
	public void setDocumentCopies (int DocumentCopies);

	/** Get Document Copies.
	  * Number of copies to be printed
	  */
	public int getDocumentCopies();

    /** Column name DocumentType */
    public static final String COLUMNNAME_DocumentType = "DocumentType";

	/** Set Document Type.
	  * Document Type
	  */
	public void setDocumentType (String DocumentType);

	/** Get Document Type.
	  * Document Type
	  */
	public String getDocumentType();

    /** Column name DunningGrace */
    public static final String COLUMNNAME_DunningGrace = "DunningGrace";

	/** Set Dunning Grace	  */
	public void setDunningGrace (Timestamp DunningGrace);

	/** Get Dunning Grace	  */
	public Timestamp getDunningGrace();

    /** Column name DUNS */
    public static final String COLUMNNAME_DUNS = "DUNS";

	/** Set D-U-N-S.
	  * Dun & Bradstreet Number
	  */
	public void setDUNS (String DUNS);

	/** Get D-U-N-S.
	  * Dun & Bradstreet Number
	  */
	public String getDUNS();

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name FirstName */
    public static final String COLUMNNAME_FirstName = "FirstName";

	/** Set FirstName	  */
	public void setFirstName (String FirstName);

	/** Get FirstName	  */
	public String getFirstName();

    /** Column name FirstSale */
    public static final String COLUMNNAME_FirstSale = "FirstSale";

	/** Set First Sale.
	  * Date of First Sale
	  */
	public void setFirstSale (Timestamp FirstSale);

	/** Get First Sale.
	  * Date of First Sale
	  */
	public Timestamp getFirstSale();

    /** Column name FirstSurname */
    public static final String COLUMNNAME_FirstSurname = "FirstSurname";

	/** Set FirstSurname	  */
	public void setFirstSurname (String FirstSurname);

	/** Get FirstSurname	  */
	public String getFirstSurname();

    /** Column name FlatDiscount */
    public static final String COLUMNNAME_FlatDiscount = "FlatDiscount";

	/** Set Flat Discount %.
	  * Flat discount percentage 
	  */
	public void setFlatDiscount (BigDecimal FlatDiscount);

	/** Get Flat Discount %.
	  * Flat discount percentage 
	  */
	public BigDecimal getFlatDiscount();

    /** Column name FreightCostRule */
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/** Set Freight Cost Rule.
	  * Method for charging Freight
	  */
	public void setFreightCostRule (String FreightCostRule);

	/** Get Freight Cost Rule.
	  * Method for charging Freight
	  */
	public String getFreightCostRule();

    /** Column name Invoice_PrintFormat_ID */
    public static final String COLUMNNAME_Invoice_PrintFormat_ID = "Invoice_PrintFormat_ID";

	/** Set Invoice Print Format.
	  * Print Format for printing Invoices
	  */
	public void setInvoice_PrintFormat_ID (int Invoice_PrintFormat_ID);

	/** Get Invoice Print Format.
	  * Print Format for printing Invoices
	  */
	public int getInvoice_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getInvoice_PrintFormat() throws RuntimeException;

    /** Column name InvoiceRule */
    public static final String COLUMNNAME_InvoiceRule = "InvoiceRule";

	/** Set Invoice Rule.
	  * Frequency and method of invoicing 
	  */
	public void setInvoiceRule (String InvoiceRule);

	/** Get Invoice Rule.
	  * Frequency and method of invoicing 
	  */
	public String getInvoiceRule();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsCustomer */
    public static final String COLUMNNAME_IsCustomer = "IsCustomer";

	/** Set Customer.
	  * Indicates if this Business Partner is a Customer
	  */
	public void setIsCustomer (boolean IsCustomer);

	/** Get Customer.
	  * Indicates if this Business Partner is a Customer
	  */
	public boolean isCustomer();

    /** Column name IsDiscountPrinted */
    public static final String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/** Set Discount Printed.
	  * Print Discount on Invoice and Order
	  */
	public void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/** Get Discount Printed.
	  * Print Discount on Invoice and Order
	  */
	public boolean isDiscountPrinted();

    /** Column name IsEmployee */
    public static final String COLUMNNAME_IsEmployee = "IsEmployee";

	/** Set Employee.
	  * Indicates if  this Business Partner is an employee
	  */
	public void setIsEmployee (boolean IsEmployee);

	/** Get Employee.
	  * Indicates if  this Business Partner is an employee
	  */
	public boolean isEmployee();

    /** Column name IsHr */
    public static final String COLUMNNAME_IsHr = "IsHr";

	/** Set IsHr	  */
	public void setIsHr (boolean IsHr);

	/** Get IsHr	  */
	public boolean isHr();

    /** Column name IsManufacturer */
    public static final String COLUMNNAME_IsManufacturer = "IsManufacturer";

	/** Set Is Manufacturer.
	  * Indicate role of this Business partner as Manufacturer
	  */
	public void setIsManufacturer (boolean IsManufacturer);

	/** Get Is Manufacturer.
	  * Indicate role of this Business partner as Manufacturer
	  */
	public boolean isManufacturer();

    /** Column name IsOneTime */
    public static final String COLUMNNAME_IsOneTime = "IsOneTime";

	/** Set One time transaction	  */
	public void setIsOneTime (boolean IsOneTime);

	/** Get One time transaction	  */
	public boolean isOneTime();

    /** Column name IsPOTaxExempt */
    public static final String COLUMNNAME_IsPOTaxExempt = "IsPOTaxExempt";

	/** Set PO Tax exempt.
	  * Business partner is exempt from tax on purchases
	  */
	public void setIsPOTaxExempt (boolean IsPOTaxExempt);

	/** Get PO Tax exempt.
	  * Business partner is exempt from tax on purchases
	  */
	public boolean isPOTaxExempt();

    /** Column name isproduction */
    public static final String COLUMNNAME_isproduction = "isproduction";

	/** Set isproduction	  */
	public void setisproduction (boolean isproduction);

	/** Get isproduction	  */
	public boolean isproduction();

    /** Column name IsProspect */
    public static final String COLUMNNAME_IsProspect = "IsProspect";

	/** Set Prospect.
	  * Indicates this is a Prospect
	  */
	public void setIsProspect (boolean IsProspect);

	/** Get Prospect.
	  * Indicates this is a Prospect
	  */
	public boolean isProspect();

    /** Column name IsRecurrent */
    public static final String COLUMNNAME_IsRecurrent = "IsRecurrent";

	/** Set IsRecurrent	  */
	public void setIsRecurrent (boolean IsRecurrent);

	/** Get IsRecurrent	  */
	public boolean isRecurrent();

    /** Column name IsSalesRep */
    public static final String COLUMNNAME_IsSalesRep = "IsSalesRep";

	/** Set Sales Representative.
	  * Indicates if  the business partner is a sales representative or company agent
	  */
	public void setIsSalesRep (boolean IsSalesRep);

	/** Get Sales Representative.
	  * Indicates if  the business partner is a sales representative or company agent
	  */
	public boolean isSalesRep();

    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set Summary Level.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Summary Level.
	  * This is a summary entity
	  */
	public boolean isSummary();

    /** Column name IsTaxExempt */
    public static final String COLUMNNAME_IsTaxExempt = "IsTaxExempt";

	/** Set SO Tax exempt.
	  * Business partner is exempt from tax on sales
	  */
	public void setIsTaxExempt (boolean IsTaxExempt);

	/** Get SO Tax exempt.
	  * Business partner is exempt from tax on sales
	  */
	public boolean isTaxExempt();

    /** Column name IsVendor */
    public static final String COLUMNNAME_IsVendor = "IsVendor";

	/** Set Vendor.
	  * Indicates if this Business Partner is a Vendor
	  */
	public void setIsVendor (boolean IsVendor);

	/** Get Vendor.
	  * Indicates if this Business Partner is a Vendor
	  */
	public boolean isVendor();

    /** Column name leavingreason */
    public static final String COLUMNNAME_leavingreason = "leavingreason";

	/** Set leavingreason	  */
	public void setleavingreason (String leavingreason);

	/** Get leavingreason	  */
	public String getleavingreason();

    /** Column name Logo_ID */
    public static final String COLUMNNAME_Logo_ID = "Logo_ID";

	/** Set Logo	  */
	public void setLogo_ID (int Logo_ID);

	/** Get Logo	  */
	public int getLogo_ID();

    /** Column name maritalstatus */
    public static final String COLUMNNAME_maritalstatus = "maritalstatus";

	/** Set maritalstatus	  */
	public void setmaritalstatus (String maritalstatus);

	/** Get maritalstatus	  */
	public String getmaritalstatus();

    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/** Set Discount Schema.
	  * Schema to calculate the trade discount percentage
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/** Get Discount Schema.
	  * Schema to calculate the trade discount percentage
	  */
	public int getM_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException;

    /** Column name modificanombre */
    public static final String COLUMNNAME_modificanombre = "modificanombre";

	/** Set modificanombre	  */
	public void setmodificanombre (String modificanombre);

	/** Get modificanombre	  */
	public String getmodificanombre();

    /** Column name modificarazon */
    public static final String COLUMNNAME_modificarazon = "modificarazon";

	/** Set modificarazon	  */
	public void setmodificarazon (String modificarazon);

	/** Get modificarazon	  */
	public String getmodificarazon();

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

    /** Column name NAICS */
    public static final String COLUMNNAME_NAICS = "NAICS";

	/** Set NAICS/SIC.
	  * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	public void setNAICS (String NAICS);

	/** Get NAICS/SIC.
	  * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	public String getNAICS();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Additional Name
	  */
	public void setName2 (String Name2);

	/** Get Name 2.
	  * Additional Name
	  */
	public String getName2();

    /** Column name natcodetype */
    public static final String COLUMNNAME_natcodetype = "natcodetype";

	/** Set natcodetype	  */
	public void setnatcodetype (String natcodetype);

	/** Get natcodetype	  */
	public String getnatcodetype();

    /** Column name NationalCode */
    public static final String COLUMNNAME_NationalCode = "NationalCode";

	/** Set National Code	  */
	public void setNationalCode (String NationalCode);

	/** Get National Code	  */
	public String getNationalCode();

    /** Column name nationality */
    public static final String COLUMNNAME_nationality = "nationality";

	/** Set nationality	  */
	public void setnationality (String nationality);

	/** Get nationality	  */
	public String getnationality();

    /** Column name nrolegajo */
    public static final String COLUMNNAME_nrolegajo = "nrolegajo";

	/** Set nrolegajo	  */
	public void setnrolegajo (String nrolegajo);

	/** Get nrolegajo	  */
	public String getnrolegajo();

    /** Column name nrotarjeta */
    public static final String COLUMNNAME_nrotarjeta = "nrotarjeta";

	/** Set nrotarjeta	  */
	public void setnrotarjeta (String nrotarjeta);

	/** Get nrotarjeta	  */
	public String getnrotarjeta();

    /** Column name NumberEmployees */
    public static final String COLUMNNAME_NumberEmployees = "NumberEmployees";

	/** Set Employees.
	  * Number of employees
	  */
	public void setNumberEmployees (int NumberEmployees);

	/** Get Employees.
	  * Number of employees
	  */
	public int getNumberEmployees();

    /** Column name passportexpdate */
    public static final String COLUMNNAME_passportexpdate = "passportexpdate";

	/** Set passportexpdate	  */
	public void setpassportexpdate (Timestamp passportexpdate);

	/** Get passportexpdate	  */
	public Timestamp getpassportexpdate();

    /** Column name passportorigin */
    public static final String COLUMNNAME_passportorigin = "passportorigin";

	/** Set passportorigin	  */
	public void setpassportorigin (String passportorigin);

	/** Get passportorigin	  */
	public String getpassportorigin();

    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/** Set Payment Rule.
	  * How you pay the invoice
	  */
	public void setPaymentRule (String PaymentRule);

	/** Get Payment Rule.
	  * How you pay the invoice
	  */
	public String getPaymentRule();

    /** Column name PaymentRulePO */
    public static final String COLUMNNAME_PaymentRulePO = "PaymentRulePO";

	/** Set Payment Rule.
	  * Purchase payment option
	  */
	public void setPaymentRulePO (String PaymentRulePO);

	/** Get Payment Rule.
	  * Purchase payment option
	  */
	public String getPaymentRulePO();

    /** Column name PO_DiscountSchema_ID */
    public static final String COLUMNNAME_PO_DiscountSchema_ID = "PO_DiscountSchema_ID";

	/** Set PO Discount Schema.
	  * Schema to calculate the purchase trade discount percentage
	  */
	public void setPO_DiscountSchema_ID (int PO_DiscountSchema_ID);

	/** Get PO Discount Schema.
	  * Schema to calculate the purchase trade discount percentage
	  */
	public int getPO_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema() throws RuntimeException;

    /** Column name PO_PaymentTerm_ID */
    public static final String COLUMNNAME_PO_PaymentTerm_ID = "PO_PaymentTerm_ID";

	/** Set PO Payment Term.
	  * Payment rules for a purchase order
	  */
	public void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID);

	/** Get PO Payment Term.
	  * Payment rules for a purchase order
	  */
	public int getPO_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getPO_PaymentTerm() throws RuntimeException;

    /** Column name PO_PriceList_ID */
    public static final String COLUMNNAME_PO_PriceList_ID = "PO_PriceList_ID";

	/** Set Purchase Pricelist.
	  * Price List used by this Business Partner
	  */
	public void setPO_PriceList_ID (int PO_PriceList_ID);

	/** Get Purchase Pricelist.
	  * Price List used by this Business Partner
	  */
	public int getPO_PriceList_ID();

	public org.compiere.model.I_M_PriceList getPO_PriceList() throws RuntimeException;

    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/** Set Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference);

	/** Get Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference();

    /** Column name PotentialLifeTimeValue */
    public static final String COLUMNNAME_PotentialLifeTimeValue = "PotentialLifeTimeValue";

	/** Set Potential Life Time Value.
	  * Total Revenue expected
	  */
	public void setPotentialLifeTimeValue (BigDecimal PotentialLifeTimeValue);

	/** Get Potential Life Time Value.
	  * Total Revenue expected
	  */
	public BigDecimal getPotentialLifeTimeValue();

    /** Column name Rating */
    public static final String COLUMNNAME_Rating = "Rating";

	/** Set Rating.
	  * Classification or Importance
	  */
	public void setRating (String Rating);

	/** Get Rating.
	  * Classification or Importance
	  */
	public String getRating();

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException;

    /** Column name SalesVolume */
    public static final String COLUMNNAME_SalesVolume = "SalesVolume";

	/** Set Sales Volume in 1.000.
	  * Total Volume of Sales in Thousands of Currency
	  */
	public void setSalesVolume (int SalesVolume);

	/** Get Sales Volume in 1.000.
	  * Total Volume of Sales in Thousands of Currency
	  */
	public int getSalesVolume();

    /** Column name SecondName */
    public static final String COLUMNNAME_SecondName = "SecondName";

	/** Set SecondName	  */
	public void setSecondName (String SecondName);

	/** Get SecondName	  */
	public String getSecondName();

    /** Column name SecondSurname */
    public static final String COLUMNNAME_SecondSurname = "SecondSurname";

	/** Set SecondSurname	  */
	public void setSecondSurname (String SecondSurname);

	/** Get SecondSurname	  */
	public String getSecondSurname();

    /** Column name SendEMail */
    public static final String COLUMNNAME_SendEMail = "SendEMail";

	/** Set Send EMail.
	  * Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail);

	/** Get Send EMail.
	  * Enable sending Document EMail
	  */
	public boolean isSendEMail();

    /** Column name sex */
    public static final String COLUMNNAME_sex = "sex";

	/** Set sex	  */
	public void setsex (String sex);

	/** Get sex	  */
	public String getsex();

    /** Column name ShareOfCustomer */
    public static final String COLUMNNAME_ShareOfCustomer = "ShareOfCustomer";

	/** Set Share.
	  * Share of Customer's business as a percentage
	  */
	public void setShareOfCustomer (int ShareOfCustomer);

	/** Get Share.
	  * Share of Customer's business as a percentage
	  */
	public int getShareOfCustomer();

    /** Column name ShelfLifeMinPct */
    public static final String COLUMNNAME_ShelfLifeMinPct = "ShelfLifeMinPct";

	/** Set Min Shelf Life %.
	  * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	public void setShelfLifeMinPct (int ShelfLifeMinPct);

	/** Get Min Shelf Life %.
	  * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	public int getShelfLifeMinPct();

    /** Column name SO_CreditLimit */
    public static final String COLUMNNAME_SO_CreditLimit = "SO_CreditLimit";

	/** Set Credit Limit.
	  * Total outstanding invoice amounts allowed
	  */
	public void setSO_CreditLimit (BigDecimal SO_CreditLimit);

	/** Get Credit Limit.
	  * Total outstanding invoice amounts allowed
	  */
	public BigDecimal getSO_CreditLimit();

    /** Column name SOCreditStatus */
    public static final String COLUMNNAME_SOCreditStatus = "SOCreditStatus";

	/** Set Credit Status.
	  * Business Partner Credit Status
	  */
	public void setSOCreditStatus (String SOCreditStatus);

	/** Get Credit Status.
	  * Business Partner Credit Status
	  */
	public String getSOCreditStatus();

    /** Column name SO_CreditUsed */
    public static final String COLUMNNAME_SO_CreditUsed = "SO_CreditUsed";

	/** Set Credit Used.
	  * Current open balance
	  */
	public void setSO_CreditUsed (BigDecimal SO_CreditUsed);

	/** Get Credit Used.
	  * Current open balance
	  */
	public BigDecimal getSO_CreditUsed();

    /** Column name SO_Description */
    public static final String COLUMNNAME_SO_Description = "SO_Description";

	/** Set Order Description.
	  * Description to be used on orders
	  */
	public void setSO_Description (String SO_Description);

	/** Get Order Description.
	  * Description to be used on orders
	  */
	public String getSO_Description();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name TaxID */
    public static final String COLUMNNAME_TaxID = "TaxID";

	/** Set Tax ID.
	  * Tax Identification
	  */
	public void setTaxID (String TaxID);

	/** Get Tax ID.
	  * Tax Identification
	  */
	public String getTaxID();

    /** Column name TotalOpenBalance */
    public static final String COLUMNNAME_TotalOpenBalance = "TotalOpenBalance";

	/** Set Open Balance.
	  * Total Open Balance Amount in primary Accounting Currency
	  */
	public void setTotalOpenBalance (BigDecimal TotalOpenBalance);

	/** Get Open Balance.
	  * Total Open Balance Amount in primary Accounting Currency
	  */
	public BigDecimal getTotalOpenBalance();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/** Set URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL);

	/** Get URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL();

    /** Column name UY_AsignacionFamiliar */
    public static final String COLUMNNAME_UY_AsignacionFamiliar = "UY_AsignacionFamiliar";

	/** Set UY_AsignacionFamiliar	  */
	public void setUY_AsignacionFamiliar (boolean UY_AsignacionFamiliar);

	/** Get UY_AsignacionFamiliar	  */
	public boolean isUY_AsignacionFamiliar();

    /** Column name UY_CanalVentas_ID */
    public static final String COLUMNNAME_UY_CanalVentas_ID = "UY_CanalVentas_ID";

	/** Set UY_CanalVentas	  */
	public void setUY_CanalVentas_ID (int UY_CanalVentas_ID);

	/** Get UY_CanalVentas	  */
	public int getUY_CanalVentas_ID();

	public I_UY_CanalVentas getUY_CanalVentas() throws RuntimeException;

    /** Column name UY_Collector_ID */
    public static final String COLUMNNAME_UY_Collector_ID = "UY_Collector_ID";

	/** Set UY_Collector	  */
	public void setUY_Collector_ID (int UY_Collector_ID);

	/** Get UY_Collector	  */
	public int getUY_Collector_ID();

    /** Column name uy_credit_action */
    public static final String COLUMNNAME_uy_credit_action = "uy_credit_action";

	/** Set uy_credit_action	  */
	public void setuy_credit_action (String uy_credit_action);

	/** Get uy_credit_action	  */
	public String getuy_credit_action();

    /** Column name uy_creditlimit_doc */
    public static final String COLUMNNAME_uy_creditlimit_doc = "uy_creditlimit_doc";

	/** Set uy_creditlimit_doc	  */
	public void setuy_creditlimit_doc (BigDecimal uy_creditlimit_doc);

	/** Get uy_creditlimit_doc	  */
	public BigDecimal getuy_creditlimit_doc();

    /** Column name uy_credit_openamt */
    public static final String COLUMNNAME_uy_credit_openamt = "uy_credit_openamt";

	/** Set uy_credit_openamt	  */
	public void setuy_credit_openamt (BigDecimal uy_credit_openamt);

	/** Get uy_credit_openamt	  */
	public BigDecimal getuy_credit_openamt();

    /** Column name uy_credit_openamt_doc */
    public static final String COLUMNNAME_uy_credit_openamt_doc = "uy_credit_openamt_doc";

	/** Set uy_credit_openamt_doc	  */
	public void setuy_credit_openamt_doc (BigDecimal uy_credit_openamt_doc);

	/** Get uy_credit_openamt_doc	  */
	public BigDecimal getuy_credit_openamt_doc();

    /** Column name UY_HRAcumulacionLaboral_ID */
    public static final String COLUMNNAME_UY_HRAcumulacionLaboral_ID = "UY_HRAcumulacionLaboral_ID";

	/** Set UY_HRAcumulacionLaboral	  */
	public void setUY_HRAcumulacionLaboral_ID (int UY_HRAcumulacionLaboral_ID);

	/** Get UY_HRAcumulacionLaboral	  */
	public int getUY_HRAcumulacionLaboral_ID();

    /** Column name UY_HRCodigoExoneracion_ID */
    public static final String COLUMNNAME_UY_HRCodigoExoneracion_ID = "UY_HRCodigoExoneracion_ID";

	/** Set UY_HRCodigoExoneracion	  */
	public void setUY_HRCodigoExoneracion_ID (int UY_HRCodigoExoneracion_ID);

	/** Get UY_HRCodigoExoneracion	  */
	public int getUY_HRCodigoExoneracion_ID();

    /** Column name UY_HRComputoEspecial_ID */
    public static final String COLUMNNAME_UY_HRComputoEspecial_ID = "UY_HRComputoEspecial_ID";

	/** Set UY_HRComputoEspecial	  */
	public void setUY_HRComputoEspecial_ID (int UY_HRComputoEspecial_ID);

	/** Get UY_HRComputoEspecial	  */
	public int getUY_HRComputoEspecial_ID();

    /** Column name UY_HRMotivosEgreso_ID */
    public static final String COLUMNNAME_UY_HRMotivosEgreso_ID = "UY_HRMotivosEgreso_ID";

	/** Set UY_HRMotivosEgreso	  */
	public void setUY_HRMotivosEgreso_ID (int UY_HRMotivosEgreso_ID);

	/** Get UY_HRMotivosEgreso	  */
	public int getUY_HRMotivosEgreso_ID();

    /** Column name UY_HRSeguroSalud_ID */
    public static final String COLUMNNAME_UY_HRSeguroSalud_ID = "UY_HRSeguroSalud_ID";

	/** Set UY_HRSeguroSalud	  */
	public void setUY_HRSeguroSalud_ID (int UY_HRSeguroSalud_ID);

	/** Get UY_HRSeguroSalud	  */
	public int getUY_HRSeguroSalud_ID();

	public I_UY_HRSeguroSalud getUY_HRSeguroSalud() throws RuntimeException;

    /** Column name UY_HRVinculoFuncional_ID */
    public static final String COLUMNNAME_UY_HRVinculoFuncional_ID = "UY_HRVinculoFuncional_ID";

	/** Set UY_HRVinculoFuncional	  */
	public void setUY_HRVinculoFuncional_ID (int UY_HRVinculoFuncional_ID);

	/** Get UY_HRVinculoFuncional	  */
	public int getUY_HRVinculoFuncional_ID();
	
    /** Column name uy_prioridadgenent */
    public static final String COLUMNNAME_uy_prioridadgenent = "uy_prioridadgenent";

	/** Set uy_prioridadgenent	  */
	public void setuy_prioridadgenent (int uy_prioridadgenent);

	/** Get uy_prioridadgenent	  */
	public int getuy_prioridadgenent();

    /** Column name UY_ZonaReparto_ID */
    public static final String COLUMNNAME_UY_ZonaReparto_ID = "UY_ZonaReparto_ID";

	/** Set UY_ZonaReparto	  */
	public void setUY_ZonaReparto_ID (int UY_ZonaReparto_ID);

	/** Get UY_ZonaReparto	  */
	public int getUY_ZonaReparto_ID();

	public I_UY_ZonaReparto getUY_ZonaReparto() throws RuntimeException;

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name weddingdate */
    public static final String COLUMNNAME_weddingdate = "weddingdate";

	/** Set weddingdate	  */
	public void setweddingdate (Timestamp weddingdate);

	/** Get weddingdate	  */
	public Timestamp getweddingdate();
}
