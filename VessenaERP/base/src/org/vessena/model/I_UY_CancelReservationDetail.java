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

/** Generated Interface for UY_CancelReservationDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_CancelReservationDetail 
{

    /** TableName=UY_CancelReservationDetail */
    public static final String Table_Name = "UY_CancelReservationDetail";

    /** AD_Table_ID=1000275 */
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

    /** Column name datereserved */
    public static final String COLUMNNAME_datereserved = "datereserved";

	/** Set datereserved	  */
	public void setdatereserved (Timestamp datereserved);

	/** Get datereserved	  */
	public Timestamp getdatereserved();

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

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public I_AD_User getSalesRep() throws RuntimeException;

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

    /** Column name UY_CancelReservationDetail_ID */
    public static final String COLUMNNAME_UY_CancelReservationDetail_ID = "UY_CancelReservationDetail_ID";

	/** Set UY_CancelReservationDetail	  */
	public void setUY_CancelReservationDetail_ID (int UY_CancelReservationDetail_ID);

	/** Get UY_CancelReservationDetail	  */
	public int getUY_CancelReservationDetail_ID();

    /** Column name UY_CancelReservationFilter_ID */
    public static final String COLUMNNAME_UY_CancelReservationFilter_ID = "UY_CancelReservationFilter_ID";

	/** Set UY_CancelReservationFilter	  */
	public void setUY_CancelReservationFilter_ID (int UY_CancelReservationFilter_ID);

	/** Get UY_CancelReservationFilter	  */
	public int getUY_CancelReservationFilter_ID();

	public I_UY_CancelReservationFilter getUY_CancelReservationFilter() throws RuntimeException;

    /** Column name uy_procesar */
    public static final String COLUMNNAME_uy_procesar = "uy_procesar";

	/** Set uy_procesar	  */
	public void setuy_procesar (boolean uy_procesar);

	/** Get uy_procesar	  */
	public boolean isuy_procesar();

    /** Column name UY_ReservaPedidoHdr_ID */
    public static final String COLUMNNAME_UY_ReservaPedidoHdr_ID = "UY_ReservaPedidoHdr_ID";

	/** Set UY_ReservaPedidoHdr	  */
	public void setUY_ReservaPedidoHdr_ID (int UY_ReservaPedidoHdr_ID);

	/** Get UY_ReservaPedidoHdr	  */
	public int getUY_ReservaPedidoHdr_ID();

	public I_UY_ReservaPedidoHdr getUY_ReservaPedidoHdr() throws RuntimeException;

    /** Column name UY_ZonaReparto_ID */
    public static final String COLUMNNAME_UY_ZonaReparto_ID = "UY_ZonaReparto_ID";

	/** Set UY_ZonaReparto	  */
	public void setUY_ZonaReparto_ID (int UY_ZonaReparto_ID);

	/** Get UY_ZonaReparto	  */
	public int getUY_ZonaReparto_ID();

	public I_UY_ZonaReparto getUY_ZonaReparto() throws RuntimeException;
}
