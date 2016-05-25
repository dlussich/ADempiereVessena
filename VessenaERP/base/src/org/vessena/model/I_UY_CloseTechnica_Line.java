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

/** Generated Interface for UY_CloseTechnica_Line
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_CloseTechnica_Line 
{

    /** TableName=UY_CloseTechnica_Line */
    public static final String Table_Name = "UY_CloseTechnica_Line";

    /** AD_Table_ID=1000149 */
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

    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/** Set Date Ordered.
	  * Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered);

	/** Get Date Ordered.
	  * Date of Order
	  */
	public Timestamp getDateOrdered();

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

    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/** Set Manufacturing Order	  */
	public void setPP_Order_ID (int PP_Order_ID);

	/** Get Manufacturing Order	  */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException;

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

    /** Column name UY_CloseTechnica_Filter_ID */
    public static final String COLUMNNAME_UY_CloseTechnica_Filter_ID = "UY_CloseTechnica_Filter_ID";

	/** Set UY_CloseTechnica_Filter_ID	  */
	public void setUY_CloseTechnica_Filter_ID (int UY_CloseTechnica_Filter_ID);

	/** Get UY_CloseTechnica_Filter_ID	  */
	public int getUY_CloseTechnica_Filter_ID();

    /** Column name UY_CloseTechnica_Line_ID */
    public static final String COLUMNNAME_UY_CloseTechnica_Line_ID = "UY_CloseTechnica_Line_ID";

	/** Set UY_CloseTechnica_Line_ID	  */
	public void setUY_CloseTechnica_Line_ID (int UY_CloseTechnica_Line_ID);

	/** Get UY_CloseTechnica_Line_ID	  */
	public int getUY_CloseTechnica_Line_ID();

    /** Column name UY_PacksLiteral */
    public static final String COLUMNNAME_UY_PacksLiteral = "UY_PacksLiteral";

	/** Set UY_PacksLiteral	  */
	public void setUY_PacksLiteral (String UY_PacksLiteral);

	/** Get UY_PacksLiteral	  */
	public String getUY_PacksLiteral();

    /** Column name UY_Procesar */
    public static final String COLUMNNAME_UY_Procesar = "UY_Procesar";

	/** Set UY_Procesar	  */
	public void setUY_Procesar (boolean UY_Procesar);

	/** Get UY_Procesar	  */
	public boolean isUY_Procesar();
}
