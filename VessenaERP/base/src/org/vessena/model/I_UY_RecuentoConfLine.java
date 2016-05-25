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

import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_RecuentoConfLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_RecuentoConfLine 
{

    /** TableName=UY_RecuentoConfLine */
    public static final String Table_Name = "UY_RecuentoConfLine";

    /** AD_Table_ID=1000256 */
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

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name qty_approved */
    public static final String COLUMNNAME_qty_approved = "qty_approved";

	/** Set qty_approved	  */
	public void setqty_approved (BigDecimal qty_approved);

	/** Get qty_approved	  */
	public BigDecimal getqty_approved();

    /** Column name Qty_ApprovedBook */
    public static final String COLUMNNAME_Qty_ApprovedBook = "Qty_ApprovedBook";

	/** Set Qty_ApprovedBook	  */
	public void setQty_ApprovedBook (BigDecimal Qty_ApprovedBook);

	/** Get Qty_ApprovedBook	  */
	public BigDecimal getQty_ApprovedBook();

    /** Column name qty_approvedr1 */
    public static final String COLUMNNAME_qty_approvedr1 = "qty_approvedr1";

	/** Set qty_approvedr1	  */
	public void setqty_approvedr1 (BigDecimal qty_approvedr1);

	/** Get qty_approvedr1	  */
	public BigDecimal getqty_approvedr1();

    /** Column name qty_approvedr2 */
    public static final String COLUMNNAME_qty_approvedr2 = "qty_approvedr2";

	/** Set qty_approvedr2	  */
	public void setqty_approvedr2 (BigDecimal qty_approvedr2);

	/** Get qty_approvedr2	  */
	public BigDecimal getqty_approvedr2();

    /** Column name qty_approvedr3 */
    public static final String COLUMNNAME_qty_approvedr3 = "qty_approvedr3";

	/** Set qty_approvedr3	  */
	public void setqty_approvedr3 (BigDecimal qty_approvedr3);

	/** Get qty_approvedr3	  */
	public BigDecimal getqty_approvedr3();

    /** Column name qty_blocked */
    public static final String COLUMNNAME_qty_blocked = "qty_blocked";

	/** Set qty_blocked	  */
	public void setqty_blocked (BigDecimal qty_blocked);

	/** Get qty_blocked	  */
	public BigDecimal getqty_blocked();

    /** Column name Qty_BlockedBook */
    public static final String COLUMNNAME_Qty_BlockedBook = "Qty_BlockedBook";

	/** Set Qty_BlockedBook	  */
	public void setQty_BlockedBook (BigDecimal Qty_BlockedBook);

	/** Get Qty_BlockedBook	  */
	public BigDecimal getQty_BlockedBook();

    /** Column name qty_blockedr1 */
    public static final String COLUMNNAME_qty_blockedr1 = "qty_blockedr1";

	/** Set qty_blockedr1	  */
	public void setqty_blockedr1 (BigDecimal qty_blockedr1);

	/** Get qty_blockedr1	  */
	public BigDecimal getqty_blockedr1();

    /** Column name qty_blockedr2 */
    public static final String COLUMNNAME_qty_blockedr2 = "qty_blockedr2";

	/** Set qty_blockedr2	  */
	public void setqty_blockedr2 (BigDecimal qty_blockedr2);

	/** Get qty_blockedr2	  */
	public BigDecimal getqty_blockedr2();

    /** Column name qty_blockedr3 */
    public static final String COLUMNNAME_qty_blockedr3 = "qty_blockedr3";

	/** Set qty_blockedr3	  */
	public void setqty_blockedr3 (BigDecimal qty_blockedr3);

	/** Get qty_blockedr3	  */
	public BigDecimal getqty_blockedr3();

    /** Column name qty_quarantine */
    public static final String COLUMNNAME_qty_quarantine = "qty_quarantine";

	/** Set qty_quarantine	  */
	public void setqty_quarantine (BigDecimal qty_quarantine);

	/** Get qty_quarantine	  */
	public BigDecimal getqty_quarantine();

    /** Column name Qty_QuarantineBook */
    public static final String COLUMNNAME_Qty_QuarantineBook = "Qty_QuarantineBook";

	/** Set Qty_QuarantineBook	  */
	public void setQty_QuarantineBook (BigDecimal Qty_QuarantineBook);

	/** Get Qty_QuarantineBook	  */
	public BigDecimal getQty_QuarantineBook();

    /** Column name qty_quarantiner1 */
    public static final String COLUMNNAME_qty_quarantiner1 = "qty_quarantiner1";

	/** Set qty_quarantiner1	  */
	public void setqty_quarantiner1 (BigDecimal qty_quarantiner1);

	/** Get qty_quarantiner1	  */
	public BigDecimal getqty_quarantiner1();

    /** Column name qty_quarantiner2 */
    public static final String COLUMNNAME_qty_quarantiner2 = "qty_quarantiner2";

	/** Set qty_quarantiner2	  */
	public void setqty_quarantiner2 (BigDecimal qty_quarantiner2);

	/** Get qty_quarantiner2	  */
	public BigDecimal getqty_quarantiner2();

    /** Column name qty_quarantiner3 */
    public static final String COLUMNNAME_qty_quarantiner3 = "qty_quarantiner3";

	/** Set qty_quarantiner3	  */
	public void setqty_quarantiner3 (BigDecimal qty_quarantiner3);

	/** Get qty_quarantiner3	  */
	public BigDecimal getqty_quarantiner3();

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

    /** Column name UY_RecuentoConf_ID */
    public static final String COLUMNNAME_UY_RecuentoConf_ID = "UY_RecuentoConf_ID";

	/** Set UY_RecuentoConf	  */
	public void setUY_RecuentoConf_ID (int UY_RecuentoConf_ID);

	/** Get UY_RecuentoConf	  */
	public int getUY_RecuentoConf_ID();

	public I_UY_RecuentoConf getUY_RecuentoConf() throws RuntimeException;

    /** Column name UY_RecuentoConfLine_ID */
    public static final String COLUMNNAME_UY_RecuentoConfLine_ID = "UY_RecuentoConfLine_ID";

	/** Set UY_RecuentoConfLine	  */
	public void setUY_RecuentoConfLine_ID (int UY_RecuentoConfLine_ID);

	/** Get UY_RecuentoConfLine	  */
	public int getUY_RecuentoConfLine_ID();

    /** Column name uy_recuentohdr_id1 */
    public static final String COLUMNNAME_uy_recuentohdr_id1 = "uy_recuentohdr_id1";

	/** Set uy_recuentohdr_id1	  */
	public void setuy_recuentohdr_id1 (int uy_recuentohdr_id1);

	/** Get uy_recuentohdr_id1	  */
	public int getuy_recuentohdr_id1();

	public I_UY_RecuentoHdr getuy_recuentohdr_1() throws RuntimeException;

    /** Column name uy_recuentohdr_id2 */
    public static final String COLUMNNAME_uy_recuentohdr_id2 = "uy_recuentohdr_id2";

	/** Set uy_recuentohdr_id2	  */
	public void setuy_recuentohdr_id2 (int uy_recuentohdr_id2);

	/** Get uy_recuentohdr_id2	  */
	public int getuy_recuentohdr_id2();

	public I_UY_RecuentoHdr getuy_recuentohdr_2() throws RuntimeException;

    /** Column name uy_recuentohdr_id3 */
    public static final String COLUMNNAME_uy_recuentohdr_id3 = "uy_recuentohdr_id3";

	/** Set uy_recuentohdr_id3	  */
	public void setuy_recuentohdr_id3 (int uy_recuentohdr_id3);

	/** Get uy_recuentohdr_id3	  */
	public int getuy_recuentohdr_id3();

	public I_UY_RecuentoHdr getuy_recuentohdr_3() throws RuntimeException;

    /** Column name Value2 */
    public static final String COLUMNNAME_Value2 = "Value2";

	/** Set Value To.
	  * Value To
	  */
	public void setValue2 (String Value2);

	/** Get Value To.
	  * Value To
	  */
	public String getValue2();
}
