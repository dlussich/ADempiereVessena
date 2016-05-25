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

/** Generated Interface for UY_TR_ServiceOrderProd
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_ServiceOrderProd 
{

    /** TableName=UY_TR_ServiceOrderProd */
    public static final String Table_Name = "UY_TR_ServiceOrderProd";

    /** AD_Table_ID=1000783 */
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

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

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

    /** Column name QtyAvailable */
    public static final String COLUMNNAME_QtyAvailable = "QtyAvailable";

	/** Set Available Quantity.
	  * Available Quantity (On Hand - Reserved)
	  */
	public void setQtyAvailable (BigDecimal QtyAvailable);

	/** Get Available Quantity.
	  * Available Quantity (On Hand - Reserved)
	  */
	public BigDecimal getQtyAvailable();

    /** Column name QtyRequired */
    public static final String COLUMNNAME_QtyRequired = "QtyRequired";

	/** Set Qty Required	  */
	public void setQtyRequired (BigDecimal QtyRequired);

	/** Get Qty Required	  */
	public BigDecimal getQtyRequired();

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

    /** Column name UY_StockStatus_ID */
    public static final String COLUMNNAME_UY_StockStatus_ID = "UY_StockStatus_ID";

	/** Set UY_StockStatus	  */
	public void setUY_StockStatus_ID (int UY_StockStatus_ID);

	/** Get UY_StockStatus	  */
	public int getUY_StockStatus_ID();

	public I_UY_StockStatus getUY_StockStatus() throws RuntimeException;

    /** Column name UY_TR_Failure_ID */
    public static final String COLUMNNAME_UY_TR_Failure_ID = "UY_TR_Failure_ID";

	/** Set UY_TR_Failure	  */
	public void setUY_TR_Failure_ID (int UY_TR_Failure_ID);

	/** Get UY_TR_Failure	  */
	public int getUY_TR_Failure_ID();

	public I_UY_TR_Failure getUY_TR_Failure() throws RuntimeException;

    /** Column name UY_TR_ServiceOrderFailure_ID */
    public static final String COLUMNNAME_UY_TR_ServiceOrderFailure_ID = "UY_TR_ServiceOrderFailure_ID";

	/** Set UY_TR_ServiceOrderFailure	  */
	public void setUY_TR_ServiceOrderFailure_ID (int UY_TR_ServiceOrderFailure_ID);

	/** Get UY_TR_ServiceOrderFailure	  */
	public int getUY_TR_ServiceOrderFailure_ID();

	public I_UY_TR_ServiceOrderFailure getUY_TR_ServiceOrderFailure() throws RuntimeException;

    /** Column name UY_TR_ServiceOrder_ID */
    public static final String COLUMNNAME_UY_TR_ServiceOrder_ID = "UY_TR_ServiceOrder_ID";

	/** Set UY_TR_ServiceOrder	  */
	public void setUY_TR_ServiceOrder_ID (int UY_TR_ServiceOrder_ID);

	/** Get UY_TR_ServiceOrder	  */
	public int getUY_TR_ServiceOrder_ID();

	public I_UY_TR_ServiceOrder getUY_TR_ServiceOrder() throws RuntimeException;

    /** Column name UY_TR_ServiceOrderMaintain_ID */
    public static final String COLUMNNAME_UY_TR_ServiceOrderMaintain_ID = "UY_TR_ServiceOrderMaintain_ID";

	/** Set UY_TR_ServiceOrderMaintain	  */
	public void setUY_TR_ServiceOrderMaintain_ID (int UY_TR_ServiceOrderMaintain_ID);

	/** Get UY_TR_ServiceOrderMaintain	  */
	public int getUY_TR_ServiceOrderMaintain_ID();

	public I_UY_TR_ServiceOrderMaintain getUY_TR_ServiceOrderMaintain() throws RuntimeException;

    /** Column name UY_TR_ServiceOrderProd_ID */
    public static final String COLUMNNAME_UY_TR_ServiceOrderProd_ID = "UY_TR_ServiceOrderProd_ID";

	/** Set UY_TR_ServiceOrderProd	  */
	public void setUY_TR_ServiceOrderProd_ID (int UY_TR_ServiceOrderProd_ID);

	/** Get UY_TR_ServiceOrderProd	  */
	public int getUY_TR_ServiceOrderProd_ID();
}
