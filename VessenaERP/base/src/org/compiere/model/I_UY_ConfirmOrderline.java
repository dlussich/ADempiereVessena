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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;
import org.openup.model.I_UY_StockStatus;

/** Generated Interface for UY_ConfirmOrderline
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_ConfirmOrderline 
{

    /** TableName=UY_ConfirmOrderline */
    public static final String Table_Name = "UY_ConfirmOrderline";

    /** AD_Table_ID=1000063 */
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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name IsReversal */
    public static final String COLUMNNAME_IsReversal = "IsReversal";

	/** Set Reversal.
	  * This is a reversing transaction
	  */
	public void setIsReversal (boolean IsReversal);

	/** Get Reversal.
	  * This is a reversing transaction
	  */
	public boolean isReversal();

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

    /** Column name manual */
    public static final String COLUMNNAME_manual = "manual";

	/** Set manual	  */
	public void setmanual (boolean manual);

	/** Get manual	  */
	public boolean ismanual();

    /** Column name PP_Order_BOMLine_ID */
    public static final String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/** Set Manufacturing Order BOM Line	  */
	public void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/** Get Manufacturing Order BOM Line	  */
	public int getPP_Order_BOMLine_ID();

	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine() throws RuntimeException;

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

    /** Column name QtyBOM */
    public static final String COLUMNNAME_QtyBOM = "QtyBOM";

	/** Set Quantity.
	  * Indicate the Quantity  use in this BOM
	  */
	public void setQtyBOM (BigDecimal QtyBOM);

	/** Get Quantity.
	  * Indicate the Quantity  use in this BOM
	  */
	public BigDecimal getQtyBOM();

    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/** Set Delivered Quantity.
	  * Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered);

	/** Get Delivered Quantity.
	  * Delivered Quantity
	  */
	public BigDecimal getQtyDelivered();

    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/** Set Quantity.
	  * The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered);

	/** Get Quantity.
	  * The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered();

    /** Column name qtymanual */
    public static final String COLUMNNAME_qtymanual = "qtymanual";

	/** Set qtymanual	  */
	public void setqtymanual (BigDecimal qtymanual);

	/** Get qtymanual	  */
	public BigDecimal getqtymanual();

    /** Column name QtyReject */
    public static final String COLUMNNAME_QtyReject = "QtyReject";

	/** Set Qty Reject	  */
	public void setQtyReject (BigDecimal QtyReject);

	/** Get Qty Reject	  */
	public BigDecimal getQtyReject();

    /** Column name QtyRequired */
    public static final String COLUMNNAME_QtyRequired = "QtyRequired";

	/** Set Qty Required	  */
	public void setQtyRequired (BigDecimal QtyRequired);

	/** Get Qty Required	  */
	public BigDecimal getQtyRequired();

    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/** Set Reserved Quantity.
	  * Reserved Quantity
	  */
	public void setQtyReserved (BigDecimal QtyReserved);

	/** Get Reserved Quantity.
	  * Reserved Quantity
	  */
	public BigDecimal getQtyReserved();

    /** Column name QtyScrap */
    public static final String COLUMNNAME_QtyScrap = "QtyScrap";

	/** Set Scrap %.
	  * Scrap % Quantity for this componet
	  */
	public void setQtyScrap (BigDecimal QtyScrap);

	/** Get Scrap %.
	  * Scrap % Quantity for this componet
	  */
	public BigDecimal getQtyScrap();

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

    /** Column name uy_almacenorigen */
    public static final String COLUMNNAME_uy_almacenorigen = "uy_almacenorigen";

	/** Set uy_almacenorigen	  */
	public void setuy_almacenorigen (int uy_almacenorigen);

	/** Get uy_almacenorigen	  */
	public int getuy_almacenorigen();

	public org.compiere.model.I_M_Warehouse getuy_almacenori() throws RuntimeException;

    /** Column name UY_Confirmorderhdr_ID */
    public static final String COLUMNNAME_UY_Confirmorderhdr_ID = "UY_Confirmorderhdr_ID";

	/** Set UY_Confirmorderhdr	  */
	public void setUY_Confirmorderhdr_ID (int UY_Confirmorderhdr_ID);

	/** Get UY_Confirmorderhdr	  */
	public int getUY_Confirmorderhdr_ID();

	public I_UY_Confirmorderhdr getUY_Confirmorderhdr() throws RuntimeException;

    /** Column name UY_ConfirmOrderline_ID */
    public static final String COLUMNNAME_UY_ConfirmOrderline_ID = "UY_ConfirmOrderline_ID";

	/** Set UY_ConfirmOrderline	  */
	public void setUY_ConfirmOrderline_ID (int UY_ConfirmOrderline_ID);

	/** Get UY_ConfirmOrderline	  */
	public int getUY_ConfirmOrderline_ID();

    /** Column name UY_ConsumoTotal */
    public static final String COLUMNNAME_UY_ConsumoTotal = "UY_ConsumoTotal";

	/** Set UY_ConsumoTotal	  */
	public void setUY_ConsumoTotal (BigDecimal UY_ConsumoTotal);

	/** Get UY_ConsumoTotal	  */
	public BigDecimal getUY_ConsumoTotal();

    /** Column name UY_StockStatus_ID */
    public static final String COLUMNNAME_UY_StockStatus_ID = "UY_StockStatus_ID";

	/** Set UY_StockStatus	  */
	public void setUY_StockStatus_ID (int UY_StockStatus_ID);

	/** Get UY_StockStatus	  */
	public int getUY_StockStatus_ID();

	public I_UY_StockStatus getUY_StockStatus() throws RuntimeException;
}
