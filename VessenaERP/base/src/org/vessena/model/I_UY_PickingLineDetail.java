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

/** Generated Interface for UY_PickingLineDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_PickingLineDetail 
{

    /** TableName=UY_PickingLineDetail */
    public static final String Table_Name = "UY_PickingLineDetail";

    /** AD_Table_ID=1000143 */
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

    /** Column name UY_EsBonificCruzada */
    public static final String COLUMNNAME_UY_EsBonificCruzada = "UY_EsBonificCruzada";

	/** Set UY_EsBonificCruzada	  */
	public void setUY_EsBonificCruzada (boolean UY_EsBonificCruzada);

	/** Get UY_EsBonificCruzada	  */
	public boolean isUY_EsBonificCruzada();

    /** Column name UY_PickingLineDetail_ID */
    public static final String COLUMNNAME_UY_PickingLineDetail_ID = "UY_PickingLineDetail_ID";

	/** Set UY_PickingLineDetail	  */
	public void setUY_PickingLineDetail_ID (int UY_PickingLineDetail_ID);

	/** Get UY_PickingLineDetail	  */
	public int getUY_PickingLineDetail_ID();

    /** Column name UY_PickingLine_ID */
    public static final String COLUMNNAME_UY_PickingLine_ID = "UY_PickingLine_ID";

	/** Set UY_PickingLine	  */
	public void setUY_PickingLine_ID (BigDecimal UY_PickingLine_ID);

	/** Get UY_PickingLine	  */
	public BigDecimal getUY_PickingLine_ID();

    /** Column name uy_qtyentered_original */
    public static final String COLUMNNAME_uy_qtyentered_original = "uy_qtyentered_original";

	/** Set uy_qtyentered_original	  */
	public void setuy_qtyentered_original (BigDecimal uy_qtyentered_original);

	/** Get uy_qtyentered_original	  */
	public BigDecimal getuy_qtyentered_original();

    /** Column name uy_qtyreserved_original */
    public static final String COLUMNNAME_uy_qtyreserved_original = "uy_qtyreserved_original";

	/** Set uy_qtyreserved_original	  */
	public void setuy_qtyreserved_original (BigDecimal uy_qtyreserved_original);

	/** Get uy_qtyreserved_original	  */
	public BigDecimal getuy_qtyreserved_original();

    /** Column name UY_ReservaPedidoHdr_ID */
    public static final String COLUMNNAME_UY_ReservaPedidoHdr_ID = "UY_ReservaPedidoHdr_ID";

	/** Set UY_ReservaPedidoHdr	  */
	public void setUY_ReservaPedidoHdr_ID (int UY_ReservaPedidoHdr_ID);

	/** Get UY_ReservaPedidoHdr	  */
	public int getUY_ReservaPedidoHdr_ID();

	public I_UY_ReservaPedidoHdr getUY_ReservaPedidoHdr() throws RuntimeException;

    /** Column name UY_ReservaPedidoLine_ID */
    public static final String COLUMNNAME_UY_ReservaPedidoLine_ID = "UY_ReservaPedidoLine_ID";

	/** Set UY_ReservaPedidoLine	  */
	public void setUY_ReservaPedidoLine_ID (int UY_ReservaPedidoLine_ID);

	/** Get UY_ReservaPedidoLine	  */
	public int getUY_ReservaPedidoLine_ID();

	public I_UY_ReservaPedidoLine getUY_ReservaPedidoLine() throws RuntimeException;

    /** Column name UY_TieneBonificCruzada */
    public static final String COLUMNNAME_UY_TieneBonificCruzada = "UY_TieneBonificCruzada";

	/** Set UY_TieneBonificCruzada	  */
	public void setUY_TieneBonificCruzada (boolean UY_TieneBonificCruzada);

	/** Get UY_TieneBonificCruzada	  */
	public boolean isUY_TieneBonificCruzada();
}
