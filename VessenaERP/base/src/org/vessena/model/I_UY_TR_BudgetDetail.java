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

/** Generated Interface for UY_TR_BudgetDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_BudgetDetail 
{

    /** TableName=UY_TR_BudgetDetail */
    public static final String Table_Name = "UY_TR_BudgetDetail";

    /** AD_Table_ID=1000814 */
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

    /** Column name AplicaValorem */
    public static final String COLUMNNAME_AplicaValorem = "AplicaValorem";

	/** Set AplicaValorem.
	  * AplicaValorem
	  */
	public void setAplicaValorem (boolean AplicaValorem);

	/** Get AplicaValorem.
	  * AplicaValorem
	  */
	public boolean isAplicaValorem();

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

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException;

    /** Column name InternationalAmt */
    public static final String COLUMNNAME_InternationalAmt = "InternationalAmt";

	/** Set InternationalAmt.
	  * InternationalAmt
	  */
	public void setInternationalAmt (BigDecimal InternationalAmt);

	/** Get InternationalAmt.
	  * InternationalAmt
	  */
	public BigDecimal getInternationalAmt();

    /** Column name InterPercentage */
    public static final String COLUMNNAME_InterPercentage = "InterPercentage";

	/** Set InterPercentage	  */
	public void setInterPercentage (BigDecimal InterPercentage);

	/** Get InterPercentage	  */
	public BigDecimal getInterPercentage();

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

    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/** Set Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt);

	/** Get Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt();

    /** Column name LineTotalAmt */
    public static final String COLUMNNAME_LineTotalAmt = "LineTotalAmt";

	/** Set Line Total.
	  * Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt);

	/** Get Line Total.
	  * Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt();

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

    /** Column name NationalAmt */
    public static final String COLUMNNAME_NationalAmt = "NationalAmt";

	/** Set NationalAmt.
	  * NationalAmt
	  */
	public void setNationalAmt (BigDecimal NationalAmt);

	/** Get NationalAmt.
	  * NationalAmt
	  */
	public BigDecimal getNationalAmt();

    /** Column name NationalPercentage */
    public static final String COLUMNNAME_NationalPercentage = "NationalPercentage";

	/** Set NationalPercentage	  */
	public void setNationalPercentage (BigDecimal NationalPercentage);

	/** Get NationalPercentage	  */
	public BigDecimal getNationalPercentage();

    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/** Set Unit Price.
	  * Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual);

	/** Get Unit Price.
	  * Actual Price 
	  */
	public BigDecimal getPriceActual();

    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/** Set Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered);

	/** Get Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered();

    /** Column name PriceLimit */
    public static final String COLUMNNAME_PriceLimit = "PriceLimit";

	/** Set Limit Price.
	  * Lowest price for a product
	  */
	public void setPriceLimit (BigDecimal PriceLimit);

	/** Get Limit Price.
	  * Lowest price for a product
	  */
	public BigDecimal getPriceLimit();

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set List Price.
	  * List Price
	  */
	public void setPriceList (BigDecimal PriceList);

	/** Get List Price.
	  * List Price
	  */
	public BigDecimal getPriceList();

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

    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/** Set Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced);

	/** Get Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced();

    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/** Set Tax Amount.
	  * Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt);

	/** Get Tax Amount.
	  * Tax Amount for a document
	  */
	public BigDecimal getTaxAmt();

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

    /** Column name UY_TR_BudgetDetail_ID */
    public static final String COLUMNNAME_UY_TR_BudgetDetail_ID = "UY_TR_BudgetDetail_ID";

	/** Set UY_TR_BudgetDetail	  */
	public void setUY_TR_BudgetDetail_ID (int UY_TR_BudgetDetail_ID);

	/** Get UY_TR_BudgetDetail	  */
	public int getUY_TR_BudgetDetail_ID();

    /** Column name UY_TR_BudgetLine_ID */
    public static final String COLUMNNAME_UY_TR_BudgetLine_ID = "UY_TR_BudgetLine_ID";

	/** Set UY_TR_BudgetLine	  */
	public void setUY_TR_BudgetLine_ID (int UY_TR_BudgetLine_ID);

	/** Get UY_TR_BudgetLine	  */
	public int getUY_TR_BudgetLine_ID();

	public I_UY_TR_BudgetLine getUY_TR_BudgetLine() throws RuntimeException;

    /** Column name ValoremPercentage */
    public static final String COLUMNNAME_ValoremPercentage = "ValoremPercentage";

	/** Set ValoremPercentage.
	  * ValoremPercentage
	  */
	public void setValoremPercentage (BigDecimal ValoremPercentage);

	/** Get ValoremPercentage.
	  * ValoremPercentage
	  */
	public BigDecimal getValoremPercentage();
}
