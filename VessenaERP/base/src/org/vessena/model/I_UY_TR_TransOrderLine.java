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

/** Generated Interface for UY_TR_TransOrderLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_TransOrderLine 
{

    /** TableName=UY_TR_TransOrderLine */
    public static final String Table_Name = "UY_TR_TransOrderLine";

    /** AD_Table_ID=1000756 */
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

    /** Column name DateDelivered */
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	/** Set Date Delivered.
	  * Date when the product was delivered
	  */
	public void setDateDelivered (Timestamp DateDelivered);

	/** Get Date Delivered.
	  * Date when the product was delivered
	  */
	public Timestamp getDateDelivered();

    /** Column name EndTrackStatus */
    public static final String COLUMNNAME_EndTrackStatus = "EndTrackStatus";

	/** Set EndTrackStatus	  */
	public void setEndTrackStatus (boolean EndTrackStatus);

	/** Get EndTrackStatus	  */
	public boolean isEndTrackStatus();

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

    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/** Set Delivered	  */
	public void setIsDelivered (boolean IsDelivered);

	/** Get Delivered	  */
	public boolean isDelivered();

    /** Column name IsReleased */
    public static final String COLUMNNAME_IsReleased = "IsReleased";

	/** Set IsReleased.
	  * IsReleased
	  */
	public void setIsReleased (boolean IsReleased);

	/** Get IsReleased.
	  * IsReleased
	  */
	public boolean isReleased();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name NationalPercentage */
    public static final String COLUMNNAME_NationalPercentage = "NationalPercentage";

	/** Set NationalPercentage	  */
	public void setNationalPercentage (BigDecimal NationalPercentage);

	/** Get NationalPercentage	  */
	public BigDecimal getNationalPercentage();

    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/** Set Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered);

	/** Get Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered();

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

    /** Column name UY_TR_Crt_ID */
    public static final String COLUMNNAME_UY_TR_Crt_ID = "UY_TR_Crt_ID";

	/** Set UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID);

	/** Get UY_TR_Crt	  */
	public int getUY_TR_Crt_ID();

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException;

    /** Column name UY_TR_Driver_ID */
    public static final String COLUMNNAME_UY_TR_Driver_ID = "UY_TR_Driver_ID";

	/** Set UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID);

	/** Get UY_TR_Driver	  */
	public int getUY_TR_Driver_ID();

	public I_UY_TR_Driver getUY_TR_Driver() throws RuntimeException;

    /** Column name UY_TR_LoadMonitorLine_ID */
    public static final String COLUMNNAME_UY_TR_LoadMonitorLine_ID = "UY_TR_LoadMonitorLine_ID";

	/** Set UY_TR_LoadMonitorLine	  */
	public void setUY_TR_LoadMonitorLine_ID (int UY_TR_LoadMonitorLine_ID);

	/** Get UY_TR_LoadMonitorLine	  */
	public int getUY_TR_LoadMonitorLine_ID();

	public I_UY_TR_LoadMonitorLine getUY_TR_LoadMonitorLine() throws RuntimeException;

    /** Column name UY_TR_PackageType_ID */
    public static final String COLUMNNAME_UY_TR_PackageType_ID = "UY_TR_PackageType_ID";

	/** Set UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID);

	/** Get UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID();

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException;

    /** Column name UY_TR_Remito_ID */
    public static final String COLUMNNAME_UY_TR_Remito_ID = "UY_TR_Remito_ID";

	/** Set UY_TR_Remito	  */
	public void setUY_TR_Remito_ID (int UY_TR_Remito_ID);

	/** Get UY_TR_Remito	  */
	public int getUY_TR_Remito_ID();

    /** Column name UY_TR_TransOrder_ID */
    public static final String COLUMNNAME_UY_TR_TransOrder_ID = "UY_TR_TransOrder_ID";

	/** Set UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID);

	/** Get UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID();

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException;

    /** Column name UY_TR_TransOrderLine_ID */
    public static final String COLUMNNAME_UY_TR_TransOrderLine_ID = "UY_TR_TransOrderLine_ID";

	/** Set UY_TR_TransOrderLine	  */
	public void setUY_TR_TransOrderLine_ID (int UY_TR_TransOrderLine_ID);

	/** Get UY_TR_TransOrderLine	  */
	public int getUY_TR_TransOrderLine_ID();

    /** Column name UY_TR_Trip_ID */
    public static final String COLUMNNAME_UY_TR_Trip_ID = "UY_TR_Trip_ID";

	/** Set UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID);

	/** Get UY_TR_Trip	  */
	public int getUY_TR_Trip_ID();

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException;

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException;

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
