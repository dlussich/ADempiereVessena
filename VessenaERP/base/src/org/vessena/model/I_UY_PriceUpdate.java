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

/** Generated Interface for UY_PriceUpdate
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PriceUpdate 
{

    /** TableName=UY_PriceUpdate */
    public static final String Table_Name = "UY_PriceUpdate";

    /** AD_Table_ID=1000911 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

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

    /** Column name C_Currency_ID_2 */
    public static final String COLUMNNAME_C_Currency_ID_2 = "C_Currency_ID_2";

	/** Set C_Currency_ID_2	  */
	public void setC_Currency_ID_2 (int C_Currency_ID_2);

	/** Get C_Currency_ID_2	  */
	public int getC_Currency_ID_2();

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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

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

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

    /** Column name M_PriceList_ID_2 */
    public static final String COLUMNNAME_M_PriceList_ID_2 = "M_PriceList_ID_2";

	/** Set M_PriceList_ID_2	  */
	public void setM_PriceList_ID_2 (int M_PriceList_ID_2);

	/** Get M_PriceList_ID_2	  */
	public int getM_PriceList_ID_2();

    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/** Set Price List Version.
	  * Identifies a unique instance of a Price List
	  */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/** Get Price List Version.
	  * Identifies a unique instance of a Price List
	  */
	public int getM_PriceList_Version_ID();

	public org.compiere.model.I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException;

    /** Column name M_PriceList_Version_ID_2 */
    public static final String COLUMNNAME_M_PriceList_Version_ID_2 = "M_PriceList_Version_ID_2";

	/** Set M_PriceList_Version_ID_2	  */
	public void setM_PriceList_Version_ID_2 (int M_PriceList_Version_ID_2);

	/** Get M_PriceList_Version_ID_2	  */
	public int getM_PriceList_Version_ID_2();

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

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name Percentage */
    public static final String COLUMNNAME_Percentage = "Percentage";

	/** Set Percentage.
	  * Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage);

	/** Get Percentage.
	  * Percent of the entire amount
	  */
	public BigDecimal getPercentage();

    /** Column name PricePrecision */
    public static final String COLUMNNAME_PricePrecision = "PricePrecision";

	/** Set Price Precision.
	  * Precision (number of decimals) for the Price
	  */
	public void setPricePrecision (int PricePrecision);

	/** Get Price Precision.
	  * Precision (number of decimals) for the Price
	  */
	public int getPricePrecision();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/** Set UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC);

	/** Get UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC();

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

    /** Column name UY_Familia_ID */
    public static final String COLUMNNAME_UY_Familia_ID = "UY_Familia_ID";

	/** Set UY_Familia	  */
	public void setUY_Familia_ID (int UY_Familia_ID);

	/** Get UY_Familia	  */
	public int getUY_Familia_ID();

	public I_UY_Familia getUY_Familia() throws RuntimeException;

    /** Column name UY_Linea_Negocio_ID */
    public static final String COLUMNNAME_UY_Linea_Negocio_ID = "UY_Linea_Negocio_ID";

	/** Set UY_Linea_Negocio_ID	  */
	public void setUY_Linea_Negocio_ID (int UY_Linea_Negocio_ID);

	/** Get UY_Linea_Negocio_ID	  */
	public int getUY_Linea_Negocio_ID();

	public I_UY_Linea_Negocio getUY_Linea_Negocio() throws RuntimeException;

    /** Column name UY_PriceUpdate_ID */
    public static final String COLUMNNAME_UY_PriceUpdate_ID = "UY_PriceUpdate_ID";

	/** Set UY_PriceUpdate	  */
	public void setUY_PriceUpdate_ID (int UY_PriceUpdate_ID);

	/** Get UY_PriceUpdate	  */
	public int getUY_PriceUpdate_ID();

    /** Column name UY_ProductGroup_ID */
    public static final String COLUMNNAME_UY_ProductGroup_ID = "UY_ProductGroup_ID";

	/** Set UY_ProductGroup	  */
	public void setUY_ProductGroup_ID (int UY_ProductGroup_ID);

	/** Get UY_ProductGroup	  */
	public int getUY_ProductGroup_ID();

	public I_UY_ProductGroup getUY_ProductGroup() throws RuntimeException;

    /** Column name UY_SubFamilia_ID */
    public static final String COLUMNNAME_UY_SubFamilia_ID = "UY_SubFamilia_ID";

	/** Set UY_SubFamilia	  */
	public void setUY_SubFamilia_ID (int UY_SubFamilia_ID);

	/** Get UY_SubFamilia	  */
	public int getUY_SubFamilia_ID();

	public I_UY_SubFamilia getUY_SubFamilia() throws RuntimeException;

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();
}
