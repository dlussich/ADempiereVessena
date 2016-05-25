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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_BPartner_Product
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_C_BPartner_Product 
{

    /** TableName=C_BPartner_Product */
    public static final String Table_Name = "C_BPartner_Product";

    /** AD_Table_ID=632 */
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

    /** Column name IsManufacturer */
    public static final String COLUMNNAME_IsManufacturer = "IsManufacturer";

	/** Set Is Manufacturer.
	  * Indicate role of this Business partner as Manufacturer
	  */
	public void setIsManufacturer (boolean IsManufacturer);

	/** Get Is Manufacturer.
	  * Indicate role of this Business partner as Manufacturer
	  */
	public boolean isManufacturer();

    /** Column name Manufacturer */
    public static final String COLUMNNAME_Manufacturer = "Manufacturer";

	/** Set Manufacturer.
	  * Manufacturer of the Product
	  */
	public void setManufacturer (String Manufacturer);

	/** Get Manufacturer.
	  * Manufacturer of the Product
	  */
	public String getManufacturer();

    /** Column name Margin */
    public static final String COLUMNNAME_Margin = "Margin";

	/** Set Margin %.
	  * Margin for a product as a percentage
	  */
	public void setMargin (BigDecimal Margin);

	/** Get Margin %.
	  * Margin for a product as a percentage
	  */
	public BigDecimal getMargin();

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

    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/** Set Price.
	  * Price
	  */
	public void setPrice (BigDecimal Price);

	/** Get Price.
	  * Price
	  */
	public BigDecimal getPrice();

    /** Column name PriceCostFinal */
    public static final String COLUMNNAME_PriceCostFinal = "PriceCostFinal";

	/** Set PriceCostFinal	  */
	public void setPriceCostFinal (BigDecimal PriceCostFinal);

	/** Get PriceCostFinal	  */
	public BigDecimal getPriceCostFinal();

    /** Column name PriceInvoiced */
    public static final String COLUMNNAME_PriceInvoiced = "PriceInvoiced";

	/** Set Price Invoiced.
	  * The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public void setPriceInvoiced (BigDecimal PriceInvoiced);

	/** Get Price Invoiced.
	  * The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public BigDecimal getPriceInvoiced();

    /** Column name PricePO */
    public static final String COLUMNNAME_PricePO = "PricePO";

	/** Set PO Price.
	  * Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO);

	/** Get PO Price.
	  * Price based on a purchase order
	  */
	public BigDecimal getPricePO();

    /** Column name PriceSOList */
    public static final String COLUMNNAME_PriceSOList = "PriceSOList";

	/** Set PriceSOList	  */
	public void setPriceSOList (BigDecimal PriceSOList);

	/** Get PriceSOList	  */
	public BigDecimal getPriceSOList();

    /** Column name QualityRating */
    public static final String COLUMNNAME_QualityRating = "QualityRating";

	/** Set Quality Rating.
	  * Method for rating vendors
	  */
	public void setQualityRating (BigDecimal QualityRating);

	/** Get Quality Rating.
	  * Method for rating vendors
	  */
	public BigDecimal getQualityRating();

    /** Column name ShelfLifeMinDays */
    public static final String COLUMNNAME_ShelfLifeMinDays = "ShelfLifeMinDays";

	/** Set Min Shelf Life Days.
	  * Minimum Shelf Life in days based on Product Instance Guarantee Date
	  */
	public void setShelfLifeMinDays (int ShelfLifeMinDays);

	/** Get Min Shelf Life Days.
	  * Minimum Shelf Life in days based on Product Instance Guarantee Date
	  */
	public int getShelfLifeMinDays();

    /** Column name ShelfLifeMinPct */
    public static final String COLUMNNAME_ShelfLifeMinPct = "ShelfLifeMinPct";

	/** Set Min Shelf Life %.
	  * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	public void setShelfLifeMinPct (int ShelfLifeMinPct);

	/** Get Min Shelf Life %.
	  * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	public int getShelfLifeMinPct();

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

    /** Column name UY_DtoFianancAlPago */
    public static final String COLUMNNAME_UY_DtoFianancAlPago = "UY_DtoFianancAlPago";

	/** Set UY_DtoFianancAlPago.
	  * Descuento al Pago
	  */
	public void setUY_DtoFianancAlPago (BigDecimal UY_DtoFianancAlPago);

	/** Get UY_DtoFianancAlPago.
	  * Descuento al Pago
	  */
	public BigDecimal getUY_DtoFianancAlPago();

    /** Column name UY_DtoFinancFact */
    public static final String COLUMNNAME_UY_DtoFinancFact = "UY_DtoFinancFact";

	/** Set UY_DtoFinancFact.
	  * Descuento Financiero en Factura
	  */
	public void setUY_DtoFinancFact (BigDecimal UY_DtoFinancFact);

	/** Get UY_DtoFinancFact.
	  * Descuento Financiero en Factura
	  */
	public BigDecimal getUY_DtoFinancFact();

    /** Column name UY_DtoFinancFueraFact */
    public static final String COLUMNNAME_UY_DtoFinancFueraFact = "UY_DtoFinancFueraFact";

	/** Set UY_DtoFinancFueraFact.
	  * Descuento Financiero Fuera de Factura
	  */
	public void setUY_DtoFinancFueraFact (BigDecimal UY_DtoFinancFueraFact);

	/** Get UY_DtoFinancFueraFact.
	  * Descuento Financiero Fuera de Factura
	  */
	public BigDecimal getUY_DtoFinancFueraFact();

    /** Column name UY_DtoOperativo */
    public static final String COLUMNNAME_UY_DtoOperativo = "UY_DtoOperativo";

	/** Set UY_DtoOperativo.
	  * Descuento Operativo
	  */
	public void setUY_DtoOperativo (BigDecimal UY_DtoOperativo);

	/** Get UY_DtoOperativo.
	  * Descuento Operativo
	  */
	public BigDecimal getUY_DtoOperativo();

    /** Column name UY_IsBonifC */
    public static final String COLUMNNAME_UY_IsBonifC = "UY_IsBonifC";

	/** Set UY_IsBonifC.
	  * Es Bonificación Cruzada
	  */
	public void setUY_IsBonifC (boolean UY_IsBonifC);

	/** Get UY_IsBonifC.
	  * Es Bonificación Cruzada
	  */
	public boolean isUY_IsBonifC();

    /** Column name UY_IsBonifS */
    public static final String COLUMNNAME_UY_IsBonifS = "UY_IsBonifS";

	/** Set UY_IsBonifS.
	  * Es Bonificación Simple
	  */
	public void setUY_IsBonifS (boolean UY_IsBonifS);

	/** Get UY_IsBonifS.
	  * Es Bonificación Simple
	  */
	public boolean isUY_IsBonifS();

    /** Column name UY_IsRetorno */
    public static final String COLUMNNAME_UY_IsRetorno = "UY_IsRetorno";

	/** Set UY_IsRetorno	  */
	public void setUY_IsRetorno (boolean UY_IsRetorno);

	/** Get UY_IsRetorno	  */
	public boolean isUY_IsRetorno();

    /** Column name UY_PorcentajeRetorno */
    public static final String COLUMNNAME_UY_PorcentajeRetorno = "UY_PorcentajeRetorno";

	/** Set UY_PorcentajeRetorno.
	  * Porcentaje de Retorno
	  */
	public void setUY_PorcentajeRetorno (BigDecimal UY_PorcentajeRetorno);

	/** Get UY_PorcentajeRetorno.
	  * Porcentaje de Retorno
	  */
	public BigDecimal getUY_PorcentajeRetorno();

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

    /** Column name ValidFrom2 */
    public static final String COLUMNNAME_ValidFrom2 = "ValidFrom2";

	/** Set ValidFrom2	  */
	public void setValidFrom2 (Timestamp ValidFrom2);

	/** Get ValidFrom2	  */
	public Timestamp getValidFrom2();

    /** Column name VendorCategory */
    public static final String COLUMNNAME_VendorCategory = "VendorCategory";

	/** Set Partner Category.
	  * Product Category of the Business Partner
	  */
	public void setVendorCategory (String VendorCategory);

	/** Get Partner Category.
	  * Product Category of the Business Partner
	  */
	public String getVendorCategory();

    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";

	/** Set Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public void setVendorProductNo (String VendorProductNo);

	/** Get Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public String getVendorProductNo();
}
