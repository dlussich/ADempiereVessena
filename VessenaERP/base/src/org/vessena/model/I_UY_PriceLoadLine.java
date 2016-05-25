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

/** Generated Interface for UY_PriceLoadLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PriceLoadLine 
{

    /** TableName=UY_PriceLoadLine */
    public static final String Table_Name = "UY_PriceLoadLine";

    /** AD_Table_ID=1000925 */
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

    /** Column name Code */
    public static final String COLUMNNAME_Code = "Code";

	/** Set Validation code.
	  * Validation Code
	  */
	public void setCode (String Code);

	/** Get Validation code.
	  * Validation Code
	  */
	public String getCode();

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

    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/** Set Tax Category.
	  * Tax Category
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/** Get Tax Category.
	  * Tax Category
	  */
	public int getC_TaxCategory_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException;

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

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name DateAction */
    public static final String COLUMNNAME_DateAction = "DateAction";

	/** Set DateAction	  */
	public void setDateAction (Timestamp DateAction);

	/** Get DateAction	  */
	public Timestamp getDateAction();

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

    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/** Set Difference.
	  * Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt);

	/** Get Difference.
	  * Difference Amount
	  */
	public BigDecimal getDifferenceAmt();

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

    /** Column name NewPrice */
    public static final String COLUMNNAME_NewPrice = "NewPrice";

	/** Set NewPrice	  */
	public void setNewPrice (BigDecimal NewPrice);

	/** Get NewPrice	  */
	public BigDecimal getNewPrice();

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

    /** Column name PriceSOList */
    public static final String COLUMNNAME_PriceSOList = "PriceSOList";

	/** Set PriceSOList	  */
	public void setPriceSOList (BigDecimal PriceSOList);

	/** Get PriceSOList	  */
	public BigDecimal getPriceSOList();

    /** Column name ProdCode */
    public static final String COLUMNNAME_ProdCode = "ProdCode";

	/** Set Product code.
	  * Product code
	  */
	public void setProdCode (String ProdCode);

	/** Get Product code.
	  * Product code
	  */
	public String getProdCode();

    /** Column name Success */
    public static final String COLUMNNAME_Success = "Success";

	/** Set Success	  */
	public void setSuccess (boolean Success);

	/** Get Success	  */
	public boolean isSuccess();

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

    /** Column name UY_PriceLoad_ID */
    public static final String COLUMNNAME_UY_PriceLoad_ID = "UY_PriceLoad_ID";

	/** Set UY_PriceLoad	  */
	public void setUY_PriceLoad_ID (int UY_PriceLoad_ID);

	/** Get UY_PriceLoad	  */
	public int getUY_PriceLoad_ID();

	public I_UY_PriceLoad getUY_PriceLoad() throws RuntimeException;

    /** Column name UY_PriceLoadLine_ID */
    public static final String COLUMNNAME_UY_PriceLoadLine_ID = "UY_PriceLoadLine_ID";

	/** Set UY_PriceLoadLine	  */
	public void setUY_PriceLoadLine_ID (int UY_PriceLoadLine_ID);

	/** Get UY_PriceLoadLine	  */
	public int getUY_PriceLoadLine_ID();

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
}
