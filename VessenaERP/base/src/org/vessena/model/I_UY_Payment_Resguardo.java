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

/** Generated Interface for UY_Payment_Resguardo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Payment_Resguardo 
{

    /** TableName=UY_Payment_Resguardo */
    public static final String Table_Name = "UY_Payment_Resguardo";

    /** AD_Table_ID=1000402 */
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

    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/** Set Amount.
	  * Amount
	  */
	public void setAmt (BigDecimal Amt);

	/** Get Amount.
	  * Amount
	  */
	public BigDecimal getAmt();

    /** Column name AmtSource */
    public static final String COLUMNNAME_AmtSource = "AmtSource";

	/** Set Source Amount.
	  * Amount Balance in Source Currency
	  */
	public void setAmtSource (BigDecimal AmtSource);

	/** Get Source Amount.
	  * Amount Balance in Source Currency
	  */
	public BigDecimal getAmtSource();

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

    /** Column name C_PaymentPayOrder_ID */
    public static final String COLUMNNAME_C_PaymentPayOrder_ID = "C_PaymentPayOrder_ID";

	/** Set C_PaymentPayOrder	  */
	public void setC_PaymentPayOrder_ID (int C_PaymentPayOrder_ID);

	/** Get C_PaymentPayOrder	  */
	public int getC_PaymentPayOrder_ID();

	public I_C_PaymentPayOrder getC_PaymentPayOrder() throws RuntimeException;

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

    /** Column name UY_Payment_Resguardo_ID */
    public static final String COLUMNNAME_UY_Payment_Resguardo_ID = "UY_Payment_Resguardo_ID";

	/** Set UY_Payment_Resguardo	  */
	public void setUY_Payment_Resguardo_ID (int UY_Payment_Resguardo_ID);

	/** Get UY_Payment_Resguardo	  */
	public int getUY_Payment_Resguardo_ID();

    /** Column name UY_PayOrder_ID */
    public static final String COLUMNNAME_UY_PayOrder_ID = "UY_PayOrder_ID";

	/** Set UY_PayOrder	  */
	public void setUY_PayOrder_ID (int UY_PayOrder_ID);

	/** Get UY_PayOrder	  */
	public int getUY_PayOrder_ID();

	public I_UY_PayOrder getUY_PayOrder() throws RuntimeException;

    /** Column name UY_Resguardo_ID */
    public static final String COLUMNNAME_UY_Resguardo_ID = "UY_Resguardo_ID";

	/** Set UY_Resguardo	  */
	public void setUY_Resguardo_ID (int UY_Resguardo_ID);

	/** Get UY_Resguardo	  */
	public int getUY_Resguardo_ID();

	public I_UY_Resguardo getUY_Resguardo() throws RuntimeException;

    /** Column name UY_ResguardoLine_ID */
    public static final String COLUMNNAME_UY_ResguardoLine_ID = "UY_ResguardoLine_ID";

	/** Set UY_ResguardoLine	  */
	public void setUY_ResguardoLine_ID (int UY_ResguardoLine_ID);

	/** Get UY_ResguardoLine	  */
	public int getUY_ResguardoLine_ID();

	public I_UY_ResguardoLine getUY_ResguardoLine() throws RuntimeException;

    /** Column name UY_Retention_ID */
    public static final String COLUMNNAME_UY_Retention_ID = "UY_Retention_ID";

	/** Set UY_Retention	  */
	public void setUY_Retention_ID (int UY_Retention_ID);

	/** Get UY_Retention	  */
	public int getUY_Retention_ID();

	public I_UY_Retention getUY_Retention() throws RuntimeException;
}
