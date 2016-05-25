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

/** Generated Interface for UY_PaymentRule
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PaymentRule 
{

    /** TableName=UY_PaymentRule */
    public static final String Table_Name = "UY_PaymentRule";

    /** AD_Table_ID=1000337 */
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

    /** Column name C_DocType_ID_PayEmit */
    public static final String COLUMNNAME_C_DocType_ID_PayEmit = "C_DocType_ID_PayEmit";

	/** Set C_DocType_ID_PayEmit	  */
	public void setC_DocType_ID_PayEmit (int C_DocType_ID_PayEmit);

	/** Get C_DocType_ID_PayEmit	  */
	public int getC_DocType_ID_PayEmit();

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

    /** Column name IsApplied */
    public static final String COLUMNNAME_IsApplied = "IsApplied";

	/** Set IsApplied	  */
	public void setIsApplied (boolean IsApplied);

	/** Get IsApplied	  */
	public boolean isApplied();

    /** Column name IsBankHandler */
    public static final String COLUMNNAME_IsBankHandler = "IsBankHandler";

	/** Set IsBankHandler	  */
	public void setIsBankHandler (boolean IsBankHandler);

	/** Get IsBankHandler	  */
	public boolean isBankHandler();

    /** Column name IsPayEmit */
    public static final String COLUMNNAME_IsPayEmit = "IsPayEmit";

	/** Set IsPayEmit	  */
	public void setIsPayEmit (boolean IsPayEmit);

	/** Get IsPayEmit	  */
	public boolean isPayEmit();

    /** Column name IsReceipt */
    public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/** Set Receipt.
	  * This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt);

	/** Get Receipt.
	  * This is a sales transaction (receipt)
	  */
	public boolean isReceipt();

    /** Column name IsStocked */
    public static final String COLUMNNAME_IsStocked = "IsStocked";

	/** Set Stocked.
	  * Organization stocks this product
	  */
	public void setIsStocked (boolean IsStocked);

	/** Get Stocked.
	  * Organization stocks this product
	  */
	public boolean isStocked();

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

    /** Column name NeedNumber */
    public static final String COLUMNNAME_NeedNumber = "NeedNumber";

	/** Set NeedNumber	  */
	public void setNeedNumber (boolean NeedNumber);

	/** Get NeedNumber	  */
	public boolean isNeedNumber();

    /** Column name paymentruletype */
    public static final String COLUMNNAME_paymentruletype = "paymentruletype";

	/** Set paymentruletype	  */
	public void setpaymentruletype (String paymentruletype);

	/** Get paymentruletype	  */
	public String getpaymentruletype();

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

    /** Column name UseSelfBankAccount */
    public static final String COLUMNNAME_UseSelfBankAccount = "UseSelfBankAccount";

	/** Set UseSelfBankAccount	  */
	public void setUseSelfBankAccount (boolean UseSelfBankAccount);

	/** Get UseSelfBankAccount	  */
	public boolean isUseSelfBankAccount();

    /** Column name UY_PaymentRule_ID */
    public static final String COLUMNNAME_UY_PaymentRule_ID = "UY_PaymentRule_ID";

	/** Set UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID);

	/** Get UY_PaymentRule	  */
	public int getUY_PaymentRule_ID();

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
}
