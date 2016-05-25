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

/** Generated Interface for UY_BudgetDeliveryLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_BudgetDeliveryLine 
{

    /** TableName=UY_BudgetDeliveryLine */
    public static final String Table_Name = "UY_BudgetDeliveryLine";

    /** AD_Table_ID=1000330 */
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

    /** Column name design */
    public static final String COLUMNNAME_design = "design";

	/** Set design	  */
	public void setdesign (String design);

	/** Get design	  */
	public String getdesign();

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

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

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

    /** Column name QtyToDeliver */
    public static final String COLUMNNAME_QtyToDeliver = "QtyToDeliver";

	/** Set Qty to deliver	  */
	public void setQtyToDeliver (BigDecimal QtyToDeliver);

	/** Get Qty to deliver	  */
	public BigDecimal getQtyToDeliver();

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

    /** Column name UY_BudgetDelivery_ID */
    public static final String COLUMNNAME_UY_BudgetDelivery_ID = "UY_BudgetDelivery_ID";

	/** Set UY_BudgetDelivery	  */
	public void setUY_BudgetDelivery_ID (int UY_BudgetDelivery_ID);

	/** Get UY_BudgetDelivery	  */
	public int getUY_BudgetDelivery_ID();

	public I_UY_BudgetDelivery getUY_BudgetDelivery() throws RuntimeException;

    /** Column name UY_BudgetDeliveryLine_ID */
    public static final String COLUMNNAME_UY_BudgetDeliveryLine_ID = "UY_BudgetDeliveryLine_ID";

	/** Set UY_BudgetDeliveryLine	  */
	public void setUY_BudgetDeliveryLine_ID (int UY_BudgetDeliveryLine_ID);

	/** Get UY_BudgetDeliveryLine	  */
	public int getUY_BudgetDeliveryLine_ID();

    /** Column name UY_BudgetLine_ID */
    public static final String COLUMNNAME_UY_BudgetLine_ID = "UY_BudgetLine_ID";

	/** Set UY_BudgetLine	  */
	public void setUY_BudgetLine_ID (int UY_BudgetLine_ID);

	/** Get UY_BudgetLine	  */
	public int getUY_BudgetLine_ID();

	public I_UY_BudgetLine getUY_BudgetLine() throws RuntimeException;

    /** Column name UY_ManufLine_ID */
    public static final String COLUMNNAME_UY_ManufLine_ID = "UY_ManufLine_ID";

	/** Set UY_ManufLine	  */
	public void setUY_ManufLine_ID (int UY_ManufLine_ID);

	/** Get UY_ManufLine	  */
	public int getUY_ManufLine_ID();

	public I_UY_ManufLine getUY_ManufLine() throws RuntimeException;
}
