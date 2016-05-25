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

/** Generated Interface for UY_PayOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PayOrder 
{

    /** TableName=UY_PayOrder */
    public static final String Table_Name = "UY_PayOrder";

    /** AD_Table_ID=1000476 */
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

    /** Column name AlaOrden */
    public static final String COLUMNNAME_AlaOrden = "AlaOrden";

	/** Set AlaOrden	  */
	public void setAlaOrden (boolean AlaOrden);

	/** Get AlaOrden	  */
	public boolean isAlaOrden();

    /** Column name AmtResguardo */
    public static final String COLUMNNAME_AmtResguardo = "AmtResguardo";

	/** Set AmtResguardo	  */
	public void setAmtResguardo (BigDecimal AmtResguardo);

	/** Get AmtResguardo	  */
	public BigDecimal getAmtResguardo();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Bank Account.
	  * Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Bank Account.
	  * Account at the Bank
	  */
	public int getC_BankAccount_ID();

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException;

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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

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

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException;

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

    /** Column name Discount2 */
    public static final String COLUMNNAME_Discount2 = "Discount2";

	/** Set Discount 2 %.
	  * Discount in percent
	  */
	public void setDiscount2 (BigDecimal Discount2);

	/** Get Discount 2 %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount2();

    /** Column name Discount3 */
    public static final String COLUMNNAME_Discount3 = "Discount3";

	/** Set Discount 3 %.
	  * Discount in percent
	  */
	public void setDiscount3 (BigDecimal Discount3);

	/** Get Discount 3 %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount3();

    /** Column name Discount4 */
    public static final String COLUMNNAME_Discount4 = "Discount4";

	/** Set Discount4	  */
	public void setDiscount4 (BigDecimal Discount4);

	/** Get Discount4	  */
	public BigDecimal getDiscount4();

    /** Column name DiscountAmt */
    public static final String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/** Set Discount Amount.
	  * Calculated amount of discount
	  */
	public void setDiscountAmt (BigDecimal DiscountAmt);

	/** Get Discount Amount.
	  * Calculated amount of discount
	  */
	public BigDecimal getDiscountAmt();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

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

    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/** Set Payment amount.
	  * Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt);

	/** Get Payment amount.
	  * Amount being paid
	  */
	public BigDecimal getPayAmt();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Subtotal */
    public static final String COLUMNNAME_Subtotal = "Subtotal";

	/** Set Subtotal	  */
	public void setSubtotal (BigDecimal Subtotal);

	/** Get Subtotal	  */
	public BigDecimal getSubtotal();

    /** Column name TieneResguardo */
    public static final String COLUMNNAME_TieneResguardo = "TieneResguardo";

	/** Set TieneResguardo	  */
	public void setTieneResguardo (boolean TieneResguardo);

	/** Get TieneResguardo	  */
	public boolean isTieneResguardo();

    /** Column name TotalDiscounts */
    public static final String COLUMNNAME_TotalDiscounts = "TotalDiscounts";

	/** Set TotalDiscounts	  */
	public void setTotalDiscounts (BigDecimal TotalDiscounts);

	/** Get TotalDiscounts	  */
	public BigDecimal getTotalDiscounts();

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

    /** Column name UY_PayEmit_ID */
    public static final String COLUMNNAME_UY_PayEmit_ID = "UY_PayEmit_ID";

	/** Set UY_PayEmit	  */
	public void setUY_PayEmit_ID (int UY_PayEmit_ID);

	/** Get UY_PayEmit	  */
	public int getUY_PayEmit_ID();

	public I_UY_PayEmit getUY_PayEmit() throws RuntimeException;

    /** Column name UY_PaymentRule_ID */
    public static final String COLUMNNAME_UY_PaymentRule_ID = "UY_PaymentRule_ID";

	/** Set UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID);

	/** Get UY_PaymentRule	  */
	public int getUY_PaymentRule_ID();

	public I_UY_PaymentRule getUY_PaymentRule() throws RuntimeException;

    /** Column name UY_PayOrderGen_ID */
    public static final String COLUMNNAME_UY_PayOrderGen_ID = "UY_PayOrderGen_ID";

	/** Set UY_PayOrderGen	  */
	public void setUY_PayOrderGen_ID (int UY_PayOrderGen_ID);

	/** Get UY_PayOrderGen	  */
	public int getUY_PayOrderGen_ID();

	public I_UY_PayOrderGen getUY_PayOrderGen() throws RuntimeException;

    /** Column name UY_PayOrder_ID */
    public static final String COLUMNNAME_UY_PayOrder_ID = "UY_PayOrder_ID";

	/** Set UY_PayOrder	  */
	public void setUY_PayOrder_ID (int UY_PayOrder_ID);

	/** Get UY_PayOrder	  */
	public int getUY_PayOrder_ID();

    /** Column name UY_Resguardo_ID */
    public static final String COLUMNNAME_UY_Resguardo_ID = "UY_Resguardo_ID";

	/** Set UY_Resguardo	  */
	public void setUY_Resguardo_ID (int UY_Resguardo_ID);

	/** Get UY_Resguardo	  */
	public int getUY_Resguardo_ID();

	public I_UY_Resguardo getUY_Resguardo() throws RuntimeException;
}
