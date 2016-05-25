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

/** Generated Interface for UY_TR_ConfigVFlete
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_ConfigVFlete 
{

    /** TableName=UY_TR_ConfigVFlete */
    public static final String Table_Name = "UY_TR_ConfigVFlete";

    /** AD_Table_ID=1000846 */
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

    /** Column name AmtManualVF */
    public static final String COLUMNNAME_AmtManualVF = "AmtManualVF";

	/** Set AmtManualVF	  */
	public void setAmtManualVF (boolean AmtManualVF);

	/** Get AmtManualVF	  */
	public boolean isAmtManualVF();

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

    /** Column name HandleCRT */
    public static final String COLUMNNAME_HandleCRT = "HandleCRT";

	/** Set HandleCRT	  */
	public void setHandleCRT (boolean HandleCRT);

	/** Get HandleCRT	  */
	public boolean isHandleCRT();

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

    /** Column name IsSeparated */
    public static final String COLUMNNAME_IsSeparated = "IsSeparated";

	/** Set IsSeparated	  */
	public void setIsSeparated (boolean IsSeparated);

	/** Get IsSeparated	  */
	public boolean isSeparated();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name M_Product_ID_2 */
    public static final String COLUMNNAME_M_Product_ID_2 = "M_Product_ID_2";

	/** Set M_Product_ID_2	  */
	public void setM_Product_ID_2 (int M_Product_ID_2);

	/** Get M_Product_ID_2	  */
	public int getM_Product_ID_2();

    /** Column name M_Product_ID_3 */
    public static final String COLUMNNAME_M_Product_ID_3 = "M_Product_ID_3";

	/** Set M_Product_ID_3	  */
	public void setM_Product_ID_3 (int M_Product_ID_3);

	/** Get M_Product_ID_3	  */
	public int getM_Product_ID_3();

    /** Column name P_Asset_Acct */
    public static final String COLUMNNAME_P_Asset_Acct = "P_Asset_Acct";

	/** Set Product Asset.
	  * Account for Product Asset (Inventory)
	  */
	public void setP_Asset_Acct (int P_Asset_Acct);

	/** Get Product Asset.
	  * Account for Product Asset (Inventory)
	  */
	public int getP_Asset_Acct();

	public I_C_ValidCombination getP_Asset_A() throws RuntimeException;

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

    /** Column name UY_TR_Config_ID */
    public static final String COLUMNNAME_UY_TR_Config_ID = "UY_TR_Config_ID";

	/** Set UY_TR_Config	  */
	public void setUY_TR_Config_ID (int UY_TR_Config_ID);

	/** Get UY_TR_Config	  */
	public int getUY_TR_Config_ID();

	public I_UY_TR_Config getUY_TR_Config() throws RuntimeException;

    /** Column name UY_TR_ConfigVFlete_ID */
    public static final String COLUMNNAME_UY_TR_ConfigVFlete_ID = "UY_TR_ConfigVFlete_ID";

	/** Set UY_TR_ConfigVFlete	  */
	public void setUY_TR_ConfigVFlete_ID (int UY_TR_ConfigVFlete_ID);

	/** Get UY_TR_ConfigVFlete	  */
	public int getUY_TR_ConfigVFlete_ID();
}
