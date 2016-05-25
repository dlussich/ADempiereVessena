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

/** Generated Interface for UY_ResguardoGenDoc
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_ResguardoGenDoc 
{

    /** TableName=UY_ResguardoGenDoc */
    public static final String Table_Name = "UY_ResguardoGenDoc";

    /** AD_Table_ID=1000983 */
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

    /** Column name AmtIVA */
    public static final String COLUMNNAME_AmtIVA = "AmtIVA";

	/** Set AmtIVA	  */
	public void setAmtIVA (BigDecimal AmtIVA);

	/** Get AmtIVA	  */
	public BigDecimal getAmtIVA();

    /** Column name AmtIVAMT */
    public static final String COLUMNNAME_AmtIVAMT = "AmtIVAMT";

	/** Set AmtIVAMT	  */
	public void setAmtIVAMT (BigDecimal AmtIVAMT);

	/** Get AmtIVAMT	  */
	public BigDecimal getAmtIVAMT();

    /** Column name amtopen */
    public static final String COLUMNNAME_amtopen = "amtopen";

	/** Set amtopen	  */
	public void setamtopen (BigDecimal amtopen);

	/** Get amtopen	  */
	public BigDecimal getamtopen();

    /** Column name AmtRetention */
    public static final String COLUMNNAME_AmtRetention = "AmtRetention";

	/** Set AmtRetention	  */
	public void setAmtRetention (BigDecimal AmtRetention);

	/** Get AmtRetention	  */
	public BigDecimal getAmtRetention();

    /** Column name amtretentionsource */
    public static final String COLUMNNAME_amtretentionsource = "amtretentionsource";

	/** Set amtretentionsource	  */
	public void setamtretentionsource (BigDecimal amtretentionsource);

	/** Get amtretentionsource	  */
	public BigDecimal getamtretentionsource();

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

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

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

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name DivideRate */
    public static final String COLUMNNAME_DivideRate = "DivideRate";

	/** Set Divide Rate.
	  * To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate);

	/** Get Divide Rate.
	  * To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

    /** Column name GrandTotalSource */
    public static final String COLUMNNAME_GrandTotalSource = "GrandTotalSource";

	/** Set GrandTotalSource	  */
	public void setGrandTotalSource (BigDecimal GrandTotalSource);

	/** Get GrandTotalSource	  */
	public BigDecimal getGrandTotalSource();

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

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Total Lines.
	  * Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines);

	/** Get Total Lines.
	  * Total of all document lines
	  */
	public BigDecimal getTotalLines();

    /** Column name TotalLinesSource */
    public static final String COLUMNNAME_TotalLinesSource = "TotalLinesSource";

	/** Set TotalLinesSource	  */
	public void setTotalLinesSource (BigDecimal TotalLinesSource);

	/** Get TotalLinesSource	  */
	public BigDecimal getTotalLinesSource();

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

    /** Column name UY_ResguardoGenDoc_ID */
    public static final String COLUMNNAME_UY_ResguardoGenDoc_ID = "UY_ResguardoGenDoc_ID";

	/** Set UY_ResguardoGenDoc	  */
	public void setUY_ResguardoGenDoc_ID (int UY_ResguardoGenDoc_ID);

	/** Get UY_ResguardoGenDoc	  */
	public int getUY_ResguardoGenDoc_ID();

    /** Column name UY_ResguardoGen_ID */
    public static final String COLUMNNAME_UY_ResguardoGen_ID = "UY_ResguardoGen_ID";

	/** Set UY_ResguardoGen	  */
	public void setUY_ResguardoGen_ID (int UY_ResguardoGen_ID);

	/** Get UY_ResguardoGen	  */
	public int getUY_ResguardoGen_ID();

	public I_UY_ResguardoGen getUY_ResguardoGen() throws RuntimeException;
}
