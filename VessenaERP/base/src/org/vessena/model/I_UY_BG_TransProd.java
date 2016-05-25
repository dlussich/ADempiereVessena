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

/** Generated Interface for UY_BG_TransProd
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_BG_TransProd 
{

    /** TableName=UY_BG_TransProd */
    public static final String Table_Name = "UY_BG_TransProd";

    /** AD_Table_ID=1000905 */
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

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Address 1.
	  * Address line 1 for this location
	  */
	public void setAddress1 (String Address1);

	/** Get Address 1.
	  * Address line 1 for this location
	  */
	public String getAddress1();

    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/** Set Address 2.
	  * Address line 2 for this location
	  */
	public void setAddress2 (String Address2);

	/** Get Address 2.
	  * Address line 2 for this location
	  */
	public String getAddress2();

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

    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/** Set Amount.
	  * Amount
	  */
	public void setAmt (BigDecimal Amt);

	/** Get Amount.
	  * Amount
	  */
	public BigDecimal getAmt();

    /** Column name amt3 */
    public static final String COLUMNNAME_amt3 = "amt3";

	/** Set amt3	  */
	public void setamt3 (int amt3);

	/** Get amt3	  */
	public int getamt3();

    /** Column name AttributeValueType */
    public static final String COLUMNNAME_AttributeValueType = "AttributeValueType";

	/** Set Attribute Value Type.
	  * Type of Attribute Value
	  */
	public void setAttributeValueType (String AttributeValueType);

	/** Get Attribute Value Type.
	  * Type of Attribute Value
	  */
	public String getAttributeValueType();

    /** Column name Cantidad */
    public static final String COLUMNNAME_Cantidad = "Cantidad";

	/** Set Cantidad	  */
	public void setCantidad (BigDecimal Cantidad);

	/** Get Cantidad	  */
	public BigDecimal getCantidad();

    /** Column name C_City_ID */
    public static final String COLUMNNAME_C_City_ID = "C_City_ID";

	/** Set City.
	  * City
	  */
	public void setC_City_ID (int C_City_ID);

	/** Get City.
	  * City
	  */
	public int getC_City_ID();

	public org.compiere.model.I_C_City getC_City() throws RuntimeException;

    /** Column name C_City_ID_2 */
    public static final String COLUMNNAME_C_City_ID_2 = "C_City_ID_2";

	/** Set C_City_ID_2	  */
	public void setC_City_ID_2 (int C_City_ID_2);

	/** Get C_City_ID_2	  */
	public int getC_City_ID_2();

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

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

    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Region.
	  * Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifies a geographical Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

    /** Column name C_Region_ID_2 */
    public static final String COLUMNNAME_C_Region_ID_2 = "C_Region_ID_2";

	/** Set C_Region_ID_2	  */
	public void setC_Region_ID_2 (int C_Region_ID_2);

	/** Get C_Region_ID_2	  */
	public int getC_Region_ID_2();

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

    /** Column name No_Constancia */
    public static final String COLUMNNAME_No_Constancia = "No_Constancia";

	/** Set No_Constancia	  */
	public void setNo_Constancia (int No_Constancia);

	/** Get No_Constancia	  */
	public int getNo_Constancia();

    /** Column name No_Factura */
    public static final String COLUMNNAME_No_Factura = "No_Factura";

	/** Set No_Factura	  */
	public void setNo_Factura (int No_Factura);

	/** Get No_Factura	  */
	public int getNo_Factura();

    /** Column name No_Recibo */
    public static final String COLUMNNAME_No_Recibo = "No_Recibo";

	/** Set No_Recibo	  */
	public void setNo_Recibo (int No_Recibo);

	/** Get No_Recibo	  */
	public int getNo_Recibo();

    /** Column name Price2 */
    public static final String COLUMNNAME_Price2 = "Price2";

	/** Set Price2	  */
	public void setPrice2 (BigDecimal Price2);

	/** Get Price2	  */
	public BigDecimal getPrice2();

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

    /** Column name total2 */
    public static final String COLUMNNAME_total2 = "total2";

	/** Set total2	  */
	public void settotal2 (BigDecimal total2);

	/** Get total2	  */
	public BigDecimal gettotal2();

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

    /** Column name UY_BG_PackingMode_ID */
    public static final String COLUMNNAME_UY_BG_PackingMode_ID = "UY_BG_PackingMode_ID";

	/** Set UY_BG_PackingMode	  */
	public void setUY_BG_PackingMode_ID (int UY_BG_PackingMode_ID);

	/** Get UY_BG_PackingMode	  */
	public int getUY_BG_PackingMode_ID();

	public I_UY_BG_PackingMode getUY_BG_PackingMode() throws RuntimeException;

    /** Column name UY_BG_Renta_ID */
    public static final String COLUMNNAME_UY_BG_Renta_ID = "UY_BG_Renta_ID";

	/** Set UY_BG_Renta	  */
	public void setUY_BG_Renta_ID (int UY_BG_Renta_ID);

	/** Get UY_BG_Renta	  */
	public int getUY_BG_Renta_ID();

	public I_UY_BG_Renta getUY_BG_Renta() throws RuntimeException;

    /** Column name UY_BG_SubCustomer_ID */
    public static final String COLUMNNAME_UY_BG_SubCustomer_ID = "UY_BG_SubCustomer_ID";

	/** Set UY_BG_SubCustomer	  */
	public void setUY_BG_SubCustomer_ID (int UY_BG_SubCustomer_ID);

	/** Get UY_BG_SubCustomer	  */
	public int getUY_BG_SubCustomer_ID();

	public I_UY_BG_SubCustomer getUY_BG_SubCustomer() throws RuntimeException;

    /** Column name UY_BG_Transaction_ID */
    public static final String COLUMNNAME_UY_BG_Transaction_ID = "UY_BG_Transaction_ID";

	/** Set UY_BG_Transaction	  */
	public void setUY_BG_Transaction_ID (int UY_BG_Transaction_ID);

	/** Get UY_BG_Transaction	  */
	public int getUY_BG_Transaction_ID();

	public I_UY_BG_Transaction getUY_BG_Transaction() throws RuntimeException;

    /** Column name UY_BG_TransProd_ID */
    public static final String COLUMNNAME_UY_BG_TransProd_ID = "UY_BG_TransProd_ID";

	/** Set UY_BG_TransProd	  */
	public void setUY_BG_TransProd_ID (int UY_BG_TransProd_ID);

	/** Get UY_BG_TransProd	  */
	public int getUY_BG_TransProd_ID();
}
