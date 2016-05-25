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

/** Generated Interface for UY_StockAdjustmentLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_StockAdjustmentLine 
{

    /** TableName=UY_StockAdjustmentLine */
    public static final String Table_Name = "UY_StockAdjustmentLine";

    /** AD_Table_ID=1000220 */
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

    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/** Get Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID();

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

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

	public I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/** Set Movement Quantity.
	  * Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty);

	/** Get Movement Quantity.
	  * Quantity of a product moved.
	  */
	public BigDecimal getMovementQty();

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

	public I_M_Product getM_Product() throws RuntimeException;

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

    /** Column name QtyBook */
    public static final String COLUMNNAME_QtyBook = "QtyBook";

	/** Set Quantity book.
	  * Book Quantity
	  */
	public void setQtyBook (BigDecimal QtyBook);

	/** Get Quantity book.
	  * Book Quantity
	  */
	public BigDecimal getQtyBook();

    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/** Set Quantity count.
	  * Counted Quantity
	  */
	public void setQtyCount (BigDecimal QtyCount);

	/** Get Quantity count.
	  * Counted Quantity
	  */
	public BigDecimal getQtyCount();

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

    /** Column name UY_StockAdjustment_ID */
    public static final String COLUMNNAME_UY_StockAdjustment_ID = "UY_StockAdjustment_ID";

	/** Set UY_StockAdjustment	  */
	public void setUY_StockAdjustment_ID (int UY_StockAdjustment_ID);

	/** Get UY_StockAdjustment	  */
	public int getUY_StockAdjustment_ID();

	public I_UY_StockAdjustment getUY_StockAdjustment() throws RuntimeException;

    /** Column name UY_StockAdjustmentLine_ID */
    public static final String COLUMNNAME_UY_StockAdjustmentLine_ID = "UY_StockAdjustmentLine_ID";

	/** Set UY_StockAdjustmentLine	  */
	public void setUY_StockAdjustmentLine_ID (int UY_StockAdjustmentLine_ID);

	/** Get UY_StockAdjustmentLine	  */
	public int getUY_StockAdjustmentLine_ID();

    /** Column name UY_StockStatus_ID */
    public static final String COLUMNNAME_UY_StockStatus_ID = "UY_StockStatus_ID";

	/** Set UY_StockStatus	  */
	public void setUY_StockStatus_ID (int UY_StockStatus_ID);

	/** Get UY_StockStatus	  */
	public int getUY_StockStatus_ID();

	public I_UY_StockStatus getUY_StockStatus() throws RuntimeException;
}
