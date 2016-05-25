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

/** Generated Interface for UY_TR_BudgetLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_BudgetLine 
{

    /** TableName=UY_TR_BudgetLine */
    public static final String Table_Name = "UY_TR_BudgetLine";

    /** AD_Table_ID=1000812 */
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
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name Amount2 */
    public static final String COLUMNNAME_Amount2 = "Amount2";

	/** Set Amount2	  */
	public void setAmount2 (BigDecimal Amount2);

	/** Get Amount2	  */
	public BigDecimal getAmount2();

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

    /** Column name CityType */
    public static final String COLUMNNAME_CityType = "CityType";

	/** Set CityType	  */
	public void setCityType (String CityType);

	/** Get CityType	  */
	public String getCityType();

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

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsConsolidated */
    public static final String COLUMNNAME_IsConsolidated = "IsConsolidated";

	/** Set IsConsolidated	  */
	public void setIsConsolidated (boolean IsConsolidated);

	/** Get IsConsolidated	  */
	public boolean isConsolidated();

    /** Column name IsDangerous */
    public static final String COLUMNNAME_IsDangerous = "IsDangerous";

	/** Set IsDangerous	  */
	public void setIsDangerous (boolean IsDangerous);

	/** Get IsDangerous	  */
	public boolean isDangerous();

    /** Column name IsRepresentation */
    public static final String COLUMNNAME_IsRepresentation = "IsRepresentation";

	/** Set IsRepresentation	  */
	public void setIsRepresentation (boolean IsRepresentation);

	/** Get IsRepresentation	  */
	public boolean isRepresentation();

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

    /** Column name OnuNO */
    public static final String COLUMNNAME_OnuNO = "OnuNO";

	/** Set OnuNO	  */
	public void setOnuNO (String OnuNO);

	/** Get OnuNO	  */
	public String getOnuNO();

    /** Column name ProductAmt */
    public static final String COLUMNNAME_ProductAmt = "ProductAmt";

	/** Set ProductAmt	  */
	public void setProductAmt (BigDecimal ProductAmt);

	/** Get ProductAmt	  */
	public BigDecimal getProductAmt();

    /** Column name QtyPackage */
    public static final String COLUMNNAME_QtyPackage = "QtyPackage";

	/** Set QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage);

	/** Get QtyPackage	  */
	public BigDecimal getQtyPackage();

    /** Column name Representado_ID */
    public static final String COLUMNNAME_Representado_ID = "Representado_ID";

	/** Set Representado_ID	  */
	public void setRepresentado_ID (int Representado_ID);

	/** Get Representado_ID	  */
	public int getRepresentado_ID();

	public org.compiere.model.I_C_BPartner getRepresentado() throws RuntimeException;

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

    /** Column name UY_Ciudad_ID */
    public static final String COLUMNNAME_UY_Ciudad_ID = "UY_Ciudad_ID";

	/** Set UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID);

	/** Get UY_Ciudad	  */
	public int getUY_Ciudad_ID();

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException;

    /** Column name UY_TR_Budget_ID */
    public static final String COLUMNNAME_UY_TR_Budget_ID = "UY_TR_Budget_ID";

	/** Set UY_TR_Budget	  */
	public void setUY_TR_Budget_ID (int UY_TR_Budget_ID);

	/** Get UY_TR_Budget	  */
	public int getUY_TR_Budget_ID();

	public I_UY_TR_Budget getUY_TR_Budget() throws RuntimeException;

    /** Column name UY_TR_BudgetLine_ID */
    public static final String COLUMNNAME_UY_TR_BudgetLine_ID = "UY_TR_BudgetLine_ID";

	/** Set UY_TR_BudgetLine	  */
	public void setUY_TR_BudgetLine_ID (int UY_TR_BudgetLine_ID);

	/** Get UY_TR_BudgetLine	  */
	public int getUY_TR_BudgetLine_ID();

    /** Column name UY_TR_PackageType_ID */
    public static final String COLUMNNAME_UY_TR_PackageType_ID = "UY_TR_PackageType_ID";

	/** Set UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID);

	/** Get UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID();

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException;

    /** Column name UY_TR_Trip_ID */
    public static final String COLUMNNAME_UY_TR_Trip_ID = "UY_TR_Trip_ID";

	/** Set UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID);

	/** Get UY_TR_Trip	  */
	public int getUY_TR_Trip_ID();

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException;

    /** Column name UY_TR_TruckType_ID */
    public static final String COLUMNNAME_UY_TR_TruckType_ID = "UY_TR_TruckType_ID";

	/** Set UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID);

	/** Get UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID();

	public I_UY_TR_TruckType getUY_TR_TruckType() throws RuntimeException;

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();

    /** Column name Weight2 */
    public static final String COLUMNNAME_Weight2 = "Weight2";

	/** Set Weight2	  */
	public void setWeight2 (BigDecimal Weight2);

	/** Get Weight2	  */
	public BigDecimal getWeight2();
}
