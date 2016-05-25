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

/** Generated Interface for UY_PayOrderRule
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PayOrderRule 
{

    /** TableName=UY_PayOrderRule */
    public static final String Table_Name = "UY_PayOrderRule";

    /** AD_Table_ID=1000877 */
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

    /** Column name CheckNo */
    public static final String COLUMNNAME_CheckNo = "CheckNo";

	/** Set Check No.
	  * Check Number
	  */
	public void setCheckNo (String CheckNo);

	/** Get Check No.
	  * Check Number
	  */
	public String getCheckNo();

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

    /** Column name InternalCode */
    public static final String COLUMNNAME_InternalCode = "InternalCode";

	/** Set InternalCode	  */
	public void setInternalCode (String InternalCode);

	/** Get InternalCode	  */
	public String getInternalCode();

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

    /** Column name serie */
    public static final String COLUMNNAME_serie = "serie";

	/** Set serie	  */
	public void setserie (String serie);

	/** Get serie	  */
	public String getserie();

    /** Column name TenderType */
    public static final String COLUMNNAME_TenderType = "TenderType";

	/** Set Tender type.
	  * Method of Payment
	  */
	public void setTenderType (String TenderType);

	/** Get Tender type.
	  * Method of Payment
	  */
	public String getTenderType();

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

    /** Column name UY_MediosPago_ID */
    public static final String COLUMNNAME_UY_MediosPago_ID = "UY_MediosPago_ID";

	/** Set Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID);

	/** Get Medios de Pago	  */
	public int getUY_MediosPago_ID();

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException;

    /** Column name UY_PaymentRule_ID */
    public static final String COLUMNNAME_UY_PaymentRule_ID = "UY_PaymentRule_ID";

	/** Set UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID);

	/** Get UY_PaymentRule	  */
	public int getUY_PaymentRule_ID();

	public I_UY_PaymentRule getUY_PaymentRule() throws RuntimeException;

    /** Column name UY_PayOrder_ID */
    public static final String COLUMNNAME_UY_PayOrder_ID = "UY_PayOrder_ID";

	/** Set UY_PayOrder	  */
	public void setUY_PayOrder_ID (int UY_PayOrder_ID);

	/** Get UY_PayOrder	  */
	public int getUY_PayOrder_ID();

	public I_UY_PayOrder getUY_PayOrder() throws RuntimeException;

    /** Column name UY_PayOrderRule_ID */
    public static final String COLUMNNAME_UY_PayOrderRule_ID = "UY_PayOrderRule_ID";

	/** Set UY_PayOrderRule	  */
	public void setUY_PayOrderRule_ID (int UY_PayOrderRule_ID);

	/** Get UY_PayOrderRule	  */
	public int getUY_PayOrderRule_ID();
}
