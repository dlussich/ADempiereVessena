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

/** Generated Interface for UY_Reserve_Product
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_Reserve_Product 
{

    /** TableName=UY_Reserve_Product */
    public static final String Table_Name = "UY_Reserve_Product";

    /** AD_Table_ID=1000087 */
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

	public I_M_Warehouse getM_Warehouse() throws RuntimeException;

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

    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/** Set Ordered Quantity.
	  * Ordered Quantity
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered);

	/** Get Ordered Quantity.
	  * Ordered Quantity
	  */
	public BigDecimal getQtyOrdered();

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

    /** Column name uy_factor */
    public static final String COLUMNNAME_uy_factor = "uy_factor";

	/** Set uy_factor	  */
	public void setuy_factor (BigDecimal uy_factor);

	/** Get uy_factor	  */
	public BigDecimal getuy_factor();

    /** Column name uy_nrotrx */
    public static final String COLUMNNAME_uy_nrotrx = "uy_nrotrx";

	/** Set uy_nrotrx	  */
	public void setuy_nrotrx (int uy_nrotrx);

	/** Get uy_nrotrx	  */
	public int getuy_nrotrx();

    /** Column name uy_qtyonhand_after */
    public static final String COLUMNNAME_uy_qtyonhand_after = "uy_qtyonhand_after";

	/** Set uy_qtyonhand_after	  */
	public void setuy_qtyonhand_after (BigDecimal uy_qtyonhand_after);

	/** Get uy_qtyonhand_after	  */
	public BigDecimal getuy_qtyonhand_after();

    /** Column name UY_QtyOnHand_AfterUM */
    public static final String COLUMNNAME_UY_QtyOnHand_AfterUM = "UY_QtyOnHand_AfterUM";

	/** Set UY_QtyOnHand_AfterUM	  */
	public void setUY_QtyOnHand_AfterUM (BigDecimal UY_QtyOnHand_AfterUM);

	/** Get UY_QtyOnHand_AfterUM	  */
	public BigDecimal getUY_QtyOnHand_AfterUM();

    /** Column name uy_qtyonhand_before */
    public static final String COLUMNNAME_uy_qtyonhand_before = "uy_qtyonhand_before";

	/** Set uy_qtyonhand_before	  */
	public void setuy_qtyonhand_before (BigDecimal uy_qtyonhand_before);

	/** Get uy_qtyonhand_before	  */
	public BigDecimal getuy_qtyonhand_before();

    /** Column name uy_qtypending */
    public static final String COLUMNNAME_uy_qtypending = "uy_qtypending";

	/** Set uy_qtypending	  */
	public void setuy_qtypending (BigDecimal uy_qtypending);

	/** Get uy_qtypending	  */
	public BigDecimal getuy_qtypending();

    /** Column name UY_Reserve_Filter_ID */
    public static final String COLUMNNAME_UY_Reserve_Filter_ID = "UY_Reserve_Filter_ID";

	/** Set UY_Reserve_Filter	  */
	public void setUY_Reserve_Filter_ID (int UY_Reserve_Filter_ID);

	/** Get UY_Reserve_Filter	  */
	public int getUY_Reserve_Filter_ID();

	public I_UY_Reserve_Filter getUY_Reserve_Filter() throws RuntimeException;

    /** Column name UY_Reserve_Product_ID */
    public static final String COLUMNNAME_UY_Reserve_Product_ID = "UY_Reserve_Product_ID";

	/** Set UY_Reserve_Product	  */
	public void setUY_Reserve_Product_ID (int UY_Reserve_Product_ID);

	/** Get UY_Reserve_Product	  */
	public int getUY_Reserve_Product_ID();

    /** Column name uy_reserve_status */
    public static final String COLUMNNAME_uy_reserve_status = "uy_reserve_status";

	/** Set uy_reserve_status	  */
	public void setuy_reserve_status (String uy_reserve_status);

	/** Get uy_reserve_status	  */
	public String getuy_reserve_status();
}
