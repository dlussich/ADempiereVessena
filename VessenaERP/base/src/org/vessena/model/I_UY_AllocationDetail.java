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
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_AllocationDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_AllocationDetail 
{

    /** TableName=UY_AllocationDetail */
    public static final String Table_Name = "UY_AllocationDetail";

    /** AD_Table_ID=1000262 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

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

    /** Column name amtallocated */
    public static final String COLUMNNAME_amtallocated = "amtallocated";

	/** Set amtallocated	  */
	public void setamtallocated (BigDecimal amtallocated);

	/** Get amtallocated	  */
	public BigDecimal getamtallocated();

    /** Column name amtinvnativeallocated */
    public static final String COLUMNNAME_amtinvnativeallocated = "amtinvnativeallocated";

	/** Set amtinvnativeallocated	  */
	public void setamtinvnativeallocated (BigDecimal amtinvnativeallocated);

	/** Get amtinvnativeallocated	  */
	public BigDecimal getamtinvnativeallocated();

    /** Column name amtpaynativeallocated */
    public static final String COLUMNNAME_amtpaynativeallocated = "amtpaynativeallocated";

	/** Set amtpaynativeallocated	  */
	public void setamtpaynativeallocated (BigDecimal amtpaynativeallocated);

	/** Get amtpaynativeallocated	  */
	public BigDecimal getamtpaynativeallocated();

    /** Column name c_currency_allocation_id */
    public static final String COLUMNNAME_c_currency_allocation_id = "c_currency_allocation_id";

	/** Set c_currency_allocation_id	  */
	public void setc_currency_allocation_id (int c_currency_allocation_id);

	/** Get c_currency_allocation_id	  */
	public int getc_currency_allocation_id();

    /** Column name c_currency_invoice_id */
    public static final String COLUMNNAME_c_currency_invoice_id = "c_currency_invoice_id";

	/** Set c_currency_invoice_id	  */
	public void setc_currency_invoice_id (int c_currency_invoice_id);

	/** Get c_currency_invoice_id	  */
	public int getc_currency_invoice_id();

    /** Column name c_currency_payment_id */
    public static final String COLUMNNAME_c_currency_payment_id = "c_currency_payment_id";

	/** Set c_currency_payment_id	  */
	public void setc_currency_payment_id (int c_currency_payment_id);

	/** Get c_currency_payment_id	  */
	public int getc_currency_payment_id();

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Payment.
	  * Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Payment.
	  * Payment identifier
	  */
	public int getC_Payment_ID();

	public I_C_Payment getC_Payment() throws RuntimeException;

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

    /** Column name creditnote_id */
    public static final String COLUMNNAME_creditnote_id = "creditnote_id";

	/** Set creditnote_id	  */
	public void setcreditnote_id (int creditnote_id);

	/** Get creditnote_id	  */
	public int getcreditnote_id();

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

    /** Column name datepayment */
    public static final String COLUMNNAME_datepayment = "datepayment";

	/** Set datepayment	  */
	public void setdatepayment (Timestamp datepayment);

	/** Get datepayment	  */
	public Timestamp getdatepayment();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name DivideRate */
    public static final String COLUMNNAME_DivideRate = "DivideRate";

	/** Set Divide Rate.
	  * To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate);

	/** Get Divide Rate.
	  * To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate();

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

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

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

    /** Column name UY_AllocationDetail_ID */
    public static final String COLUMNNAME_UY_AllocationDetail_ID = "UY_AllocationDetail_ID";

	/** Set UY_AllocationDetail	  */
	public void setUY_AllocationDetail_ID (int UY_AllocationDetail_ID);

	/** Get UY_AllocationDetail	  */
	public int getUY_AllocationDetail_ID();

    /** Column name UY_Allocation_ID */
    public static final String COLUMNNAME_UY_Allocation_ID = "UY_Allocation_ID";

	/** Set UY_Allocation_ID	  */
	public void setUY_Allocation_ID (int UY_Allocation_ID);

	/** Get UY_Allocation_ID	  */
	public int getUY_Allocation_ID();

	public I_UY_Allocation getUY_Allocation() throws RuntimeException;

    /** Column name UY_AllocationInvoice_ID */
    public static final String COLUMNNAME_UY_AllocationInvoice_ID = "UY_AllocationInvoice_ID";

	/** Set UY_AllocationInvoice	  */
	public void setUY_AllocationInvoice_ID (int UY_AllocationInvoice_ID);

	/** Get UY_AllocationInvoice	  */
	public int getUY_AllocationInvoice_ID();

	public I_UY_AllocationInvoice getUY_AllocationInvoice() throws RuntimeException;

    /** Column name UY_AllocationPayment_ID */
    public static final String COLUMNNAME_UY_AllocationPayment_ID = "UY_AllocationPayment_ID";

	/** Set UY_AllocationPayment	  */
	public void setUY_AllocationPayment_ID (int UY_AllocationPayment_ID);

	/** Get UY_AllocationPayment	  */
	public int getUY_AllocationPayment_ID();

	public I_UY_AllocationPayment getUY_AllocationPayment() throws RuntimeException;
}
