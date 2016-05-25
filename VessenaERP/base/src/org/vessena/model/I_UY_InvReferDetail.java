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

/** Generated Interface for UY_InvReferDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_InvReferDetail 
{

    /** TableName=UY_InvReferDetail */
    public static final String Table_Name = "UY_InvReferDetail";

    /** AD_Table_ID=1000271 */
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

    /** Column name bultos */
    public static final String COLUMNNAME_bultos = "bultos";

	/** Set bultos	  */
	public void setbultos (int bultos);

	/** Get bultos	  */
	public int getbultos();

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

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

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

    /** Column name UY_AsignaTransporteHdr_ID */
    public static final String COLUMNNAME_UY_AsignaTransporteHdr_ID = "UY_AsignaTransporteHdr_ID";

	/** Set UY_AsignaTransporteHdr_ID	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID);

	/** Get UY_AsignaTransporteHdr_ID	  */
	public int getUY_AsignaTransporteHdr_ID();

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException;

    /** Column name UY_InvReferDetail_ID */
    public static final String COLUMNNAME_UY_InvReferDetail_ID = "UY_InvReferDetail_ID";

	/** Set UY_InvReferDetail	  */
	public void setUY_InvReferDetail_ID (int UY_InvReferDetail_ID);

	/** Get UY_InvReferDetail	  */
	public int getUY_InvReferDetail_ID();

    /** Column name UY_InvReferFilter_ID */
    public static final String COLUMNNAME_UY_InvReferFilter_ID = "UY_InvReferFilter_ID";

	/** Set UY_InvReferFilter	  */
	public void setUY_InvReferFilter_ID (int UY_InvReferFilter_ID);

	/** Get UY_InvReferFilter	  */
	public int getUY_InvReferFilter_ID();

	public I_UY_InvReferFilter getUY_InvReferFilter() throws RuntimeException;

    /** Column name uy_precio */
    public static final String COLUMNNAME_uy_precio = "uy_precio";

	/** Set uy_precio	  */
	public void setuy_precio (String uy_precio);

	/** Get uy_precio	  */
	public String getuy_precio();

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
}
