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

/** Generated Interface for UY_RT_InternalInvoiceLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_InternalInvoiceLine 
{

    /** TableName=UY_RT_InternalInvoiceLine */
    public static final String Table_Name = "UY_RT_InternalInvoiceLine";

    /** AD_Table_ID=1001006 */
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

    /** Column name AD_Org_ID_To */
    public static final String COLUMNNAME_AD_Org_ID_To = "AD_Org_ID_To";

	/** Set AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To);

	/** Get AD_Org_ID_To	  */
	public int getAD_Org_ID_To();

    /** Column name amtallocated */
    public static final String COLUMNNAME_amtallocated = "amtallocated";

	/** Set amtallocated	  */
	public void setamtallocated (BigDecimal amtallocated);

	/** Get amtallocated	  */
	public BigDecimal getamtallocated();

    /** Column name amtdocument */
    public static final String COLUMNNAME_amtdocument = "amtdocument";

	/** Set amtdocument	  */
	public void setamtdocument (BigDecimal amtdocument);

	/** Get amtdocument	  */
	public BigDecimal getamtdocument();

    /** Column name amtopen */
    public static final String COLUMNNAME_amtopen = "amtopen";

	/** Set amtopen	  */
	public void setamtopen (BigDecimal amtopen);

	/** Get amtopen	  */
	public BigDecimal getamtopen();

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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

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

    /** Column name UY_RT_InternalDelivery_ID */
    public static final String COLUMNNAME_UY_RT_InternalDelivery_ID = "UY_RT_InternalDelivery_ID";

	/** Set UY_RT_InternalDelivery	  */
	public void setUY_RT_InternalDelivery_ID (int UY_RT_InternalDelivery_ID);

	/** Get UY_RT_InternalDelivery	  */
	public int getUY_RT_InternalDelivery_ID();

	public I_UY_RT_InternalDelivery getUY_RT_InternalDelivery() throws RuntimeException;

    /** Column name UY_RT_InternalInvoice_ID */
    public static final String COLUMNNAME_UY_RT_InternalInvoice_ID = "UY_RT_InternalInvoice_ID";

	/** Set UY_RT_InternalInvoice	  */
	public void setUY_RT_InternalInvoice_ID (int UY_RT_InternalInvoice_ID);

	/** Get UY_RT_InternalInvoice	  */
	public int getUY_RT_InternalInvoice_ID();

	public I_UY_RT_InternalInvoice getUY_RT_InternalInvoice() throws RuntimeException;

    /** Column name UY_RT_InternalInvoiceLine_ID */
    public static final String COLUMNNAME_UY_RT_InternalInvoiceLine_ID = "UY_RT_InternalInvoiceLine_ID";

	/** Set UY_RT_InternalInvoiceLine	  */
	public void setUY_RT_InternalInvoiceLine_ID (int UY_RT_InternalInvoiceLine_ID);

	/** Get UY_RT_InternalInvoiceLine	  */
	public int getUY_RT_InternalInvoiceLine_ID();
}
