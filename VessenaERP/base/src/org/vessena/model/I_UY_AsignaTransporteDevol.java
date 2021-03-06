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

/** Generated Interface for UY_AsignaTransporteDevol
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_AsignaTransporteDevol 
{

    /** TableName=UY_AsignaTransporteDevol */
    public static final String Table_Name = "UY_AsignaTransporteDevol";

    /** AD_Table_ID=1000195 */
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

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

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

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Shipment/Receipt.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Shipment/Receipt.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

	public I_M_InOut getM_InOut() throws RuntimeException;

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

    /** Column name UY_AsignaTransporteDevol_ID */
    public static final String COLUMNNAME_UY_AsignaTransporteDevol_ID = "UY_AsignaTransporteDevol_ID";

	/** Set UY_AsignaTransporteDevol_ID	  */
	public void setUY_AsignaTransporteDevol_ID (int UY_AsignaTransporteDevol_ID);

	/** Get UY_AsignaTransporteDevol_ID	  */
	public int getUY_AsignaTransporteDevol_ID();

    /** Column name UY_AsignaTransporteHdr_ID */
    public static final String COLUMNNAME_UY_AsignaTransporteHdr_ID = "UY_AsignaTransporteHdr_ID";

	/** Set UY_AsignaTransporteHdr_ID	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID);

	/** Get UY_AsignaTransporteHdr_ID	  */
	public int getUY_AsignaTransporteHdr_ID();

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException;

    /** Column name uy_cantbultos */
    public static final String COLUMNNAME_uy_cantbultos = "uy_cantbultos";

	/** Set uy_cantbultos	  */
	public void setuy_cantbultos (BigDecimal uy_cantbultos);

	/** Get uy_cantbultos	  */
	public BigDecimal getuy_cantbultos();

    /** Column name uy_cantbultos_manual */
    public static final String COLUMNNAME_uy_cantbultos_manual = "uy_cantbultos_manual";

	/** Set uy_cantbultos_manual	  */
	public void setuy_cantbultos_manual (BigDecimal uy_cantbultos_manual);

	/** Get uy_cantbultos_manual	  */
	public BigDecimal getuy_cantbultos_manual();

    /** Column name UY_ReservaPedidoHdr_ID */
    public static final String COLUMNNAME_UY_ReservaPedidoHdr_ID = "UY_ReservaPedidoHdr_ID";

	/** Set UY_ReservaPedidoHdr	  */
	public void setUY_ReservaPedidoHdr_ID (int UY_ReservaPedidoHdr_ID);

	/** Get UY_ReservaPedidoHdr	  */
	public int getUY_ReservaPedidoHdr_ID();

	public I_UY_ReservaPedidoHdr getUY_ReservaPedidoHdr() throws RuntimeException;
}
