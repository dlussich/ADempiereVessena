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

/** Generated Interface for UY_StoreLOPay
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_StoreLOPay 
{

    /** TableName=UY_StoreLOPay */
    public static final String Table_Name = "UY_StoreLOPay";

    /** AD_Table_ID=1001004 */
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

    /** Column name amtmt */
    public static final String COLUMNNAME_amtmt = "amtmt";

	/** Set amtmt	  */
	public void setamtmt (BigDecimal amtmt);

	/** Get amtmt	  */
	public BigDecimal getamtmt();

    /** Column name amtusd */
    public static final String COLUMNNAME_amtusd = "amtusd";

	/** Set amtusd	  */
	public void setamtusd (BigDecimal amtusd);

	/** Get amtusd	  */
	public BigDecimal getamtusd();

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

    /** Column name creditcard */
    public static final String COLUMNNAME_creditcard = "creditcard";

	/** Set creditcard	  */
	public void setcreditcard (String creditcard);

	/** Get creditcard	  */
	public String getcreditcard();

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

    /** Column name paytype */
    public static final String COLUMNNAME_paytype = "paytype";

	/** Set paytype	  */
	public void setpaytype (String paytype);

	/** Get paytype	  */
	public String getpaytype();

    /** Column name transno */
    public static final String COLUMNNAME_transno = "transno";

	/** Set transno	  */
	public void settransno (String transno);

	/** Get transno	  */
	public String gettransno();

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

    /** Column name UY_StoreLoadOrder_ID */
    public static final String COLUMNNAME_UY_StoreLoadOrder_ID = "UY_StoreLoadOrder_ID";

	/** Set UY_StoreLoadOrder	  */
	public void setUY_StoreLoadOrder_ID (int UY_StoreLoadOrder_ID);

	/** Get UY_StoreLoadOrder	  */
	public int getUY_StoreLoadOrder_ID();

	public I_UY_StoreLoadOrder getUY_StoreLoadOrder() throws RuntimeException;

    /** Column name UY_StoreLOPay_ID */
    public static final String COLUMNNAME_UY_StoreLOPay_ID = "UY_StoreLOPay_ID";

	/** Set UY_StoreLOPay	  */
	public void setUY_StoreLOPay_ID (int UY_StoreLOPay_ID);

	/** Get UY_StoreLOPay	  */
	public int getUY_StoreLOPay_ID();

    /** Column name UY_StoreLOSale_ID */
    public static final String COLUMNNAME_UY_StoreLOSale_ID = "UY_StoreLOSale_ID";

	/** Set UY_StoreLOSale	  */
	public void setUY_StoreLOSale_ID (int UY_StoreLOSale_ID);

	/** Get UY_StoreLOSale	  */
	public int getUY_StoreLOSale_ID();

	public I_UY_StoreLOSale getUY_StoreLOSale() throws RuntimeException;
}
