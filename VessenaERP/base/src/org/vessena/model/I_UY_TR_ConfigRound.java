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

/** Generated Interface for UY_TR_ConfigRound
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_ConfigRound 
{

    /** TableName=UY_TR_ConfigRound */
    public static final String Table_Name = "UY_TR_ConfigRound";

    /** AD_Table_ID=1000872 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (int Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public int getAmount();

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

    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/** Set Payment amount.
	  * Amount being paid
	  */
	public void setPayAmt (int PayAmt);

	/** Get Payment amount.
	  * Amount being paid
	  */
	public int getPayAmt();

    /** Column name ProductAmt */
    public static final String COLUMNNAME_ProductAmt = "ProductAmt";

	/** Set ProductAmt	  */
	public void setProductAmt (int ProductAmt);

	/** Get ProductAmt	  */
	public int getProductAmt();

    /** Column name QtyPackage */
    public static final String COLUMNNAME_QtyPackage = "QtyPackage";

	/** Set QtyPackage	  */
	public void setQtyPackage (int QtyPackage);

	/** Get QtyPackage	  */
	public int getQtyPackage();

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

    /** Column name UY_TR_ConfigRound_ID */
    public static final String COLUMNNAME_UY_TR_ConfigRound_ID = "UY_TR_ConfigRound_ID";

	/** Set UY_TR_ConfigRound	  */
	public void setUY_TR_ConfigRound_ID (int UY_TR_ConfigRound_ID);

	/** Get UY_TR_ConfigRound	  */
	public int getUY_TR_ConfigRound_ID();

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (int Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public int getVolume();

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (int Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public int getWeight();

    /** Column name Weight2 */
    public static final String COLUMNNAME_Weight2 = "Weight2";

	/** Set Weight2	  */
	public void setWeight2 (int Weight2);

	/** Get Weight2	  */
	public int getWeight2();
}
