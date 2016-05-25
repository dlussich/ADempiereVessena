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

/** Generated Interface for UY_Reserve_Detail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_Reserve_Detail 
{

    /** TableName=UY_Reserve_Detail */
    public static final String Table_Name = "UY_Reserve_Detail";

    /** AD_Table_ID=1000086 */
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

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public I_C_Order getC_Order() throws RuntimeException;

    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/** Set Sales Order Line.
	  * Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/** Get Sales Order Line.
	  * Sales Order Line
	  */
	public int getC_OrderLine_ID();

	public I_C_OrderLine getC_OrderLine() throws RuntimeException;

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

	public I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name uy_bonificaregla */
    public static final String COLUMNNAME_uy_bonificaregla = "uy_bonificaregla";

	/** Set uy_bonificaregla	  */
	public void setuy_bonificaregla (BigDecimal uy_bonificaregla);

	/** Get uy_bonificaregla	  */
	public BigDecimal getuy_bonificaregla();

    /** Column name UY_BonificaReglaUM */
    public static final String COLUMNNAME_UY_BonificaReglaUM = "UY_BonificaReglaUM";

	/** Set UY_BonificaReglaUM	  */
	public void setUY_BonificaReglaUM (BigDecimal UY_BonificaReglaUM);

	/** Get UY_BonificaReglaUM	  */
	public BigDecimal getUY_BonificaReglaUM();

    /** Column name UY_EsBonificCruzada */
    public static final String COLUMNNAME_UY_EsBonificCruzada = "UY_EsBonificCruzada";

	/** Set UY_EsBonificCruzada	  */
	public void setUY_EsBonificCruzada (boolean UY_EsBonificCruzada);

	/** Get UY_EsBonificCruzada	  */
	public boolean isUY_EsBonificCruzada();

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

    /** Column name UY_Reserve_Detail_ID */
    public static final String COLUMNNAME_UY_Reserve_Detail_ID = "UY_Reserve_Detail_ID";

	/** Set UY_Reserve_Detail	  */
	public void setUY_Reserve_Detail_ID (int UY_Reserve_Detail_ID);

	/** Get UY_Reserve_Detail	  */
	public int getUY_Reserve_Detail_ID();

    /** Column name UY_Reserve_DetailPadre_ID */
    public static final String COLUMNNAME_UY_Reserve_DetailPadre_ID = "UY_Reserve_DetailPadre_ID";

	/** Set UY_Reserve_DetailPadre	  */
	public void setUY_Reserve_DetailPadre_ID (int UY_Reserve_DetailPadre_ID);

	/** Get UY_Reserve_DetailPadre	  */
	public int getUY_Reserve_DetailPadre_ID();

    /** Column name UY_Reserve_Product_ID */
    public static final String COLUMNNAME_UY_Reserve_Product_ID = "UY_Reserve_Product_ID";

	/** Set UY_Reserve_Product	  */
	public void setUY_Reserve_Product_ID (int UY_Reserve_Product_ID);

	/** Get UY_Reserve_Product	  */
	public int getUY_Reserve_Product_ID();

	public I_UY_Reserve_Product getUY_Reserve_Product() throws RuntimeException;

    /** Column name UY_TieneBonificCruzada */
    public static final String COLUMNNAME_UY_TieneBonificCruzada = "UY_TieneBonificCruzada";

	/** Set UY_TieneBonificCruzada	  */
	public void setUY_TieneBonificCruzada (boolean UY_TieneBonificCruzada);

	/** Get UY_TieneBonificCruzada	  */
	public boolean isUY_TieneBonificCruzada();
}
