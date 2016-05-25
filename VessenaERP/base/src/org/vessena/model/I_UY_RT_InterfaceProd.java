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

/** Generated Interface for UY_RT_InterfaceProd
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_InterfaceProd 
{

    /** TableName=UY_RT_InterfaceProd */
    public static final String Table_Name = "UY_RT_InterfaceProd";

    /** AD_Table_ID=1000908 */
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

    /** Column name attr_1 */
    public static final String COLUMNNAME_attr_1 = "attr_1";

	/** Set attr_1	  */
	public void setattr_1 (int attr_1);

	/** Get attr_1	  */
	public int getattr_1();

    /** Column name attr_11 */
    public static final String COLUMNNAME_attr_11 = "attr_11";

	/** Set attr_11	  */
	public void setattr_11 (int attr_11);

	/** Get attr_11	  */
	public int getattr_11();

    /** Column name attr_12 */
    public static final String COLUMNNAME_attr_12 = "attr_12";

	/** Set attr_12	  */
	public void setattr_12 (int attr_12);

	/** Get attr_12	  */
	public int getattr_12();

    /** Column name attr_13 */
    public static final String COLUMNNAME_attr_13 = "attr_13";

	/** Set attr_13	  */
	public void setattr_13 (int attr_13);

	/** Get attr_13	  */
	public int getattr_13();

    /** Column name attr_14 */
    public static final String COLUMNNAME_attr_14 = "attr_14";

	/** Set attr_14	  */
	public void setattr_14 (int attr_14);

	/** Get attr_14	  */
	public int getattr_14();

    /** Column name attr_15 */
    public static final String COLUMNNAME_attr_15 = "attr_15";

	/** Set attr_15	  */
	public void setattr_15 (int attr_15);

	/** Get attr_15	  */
	public int getattr_15();

    /** Column name attr_16 */
    public static final String COLUMNNAME_attr_16 = "attr_16";

	/** Set attr_16	  */
	public void setattr_16 (int attr_16);

	/** Get attr_16	  */
	public int getattr_16();

    /** Column name attr_17 */
    public static final String COLUMNNAME_attr_17 = "attr_17";

	/** Set attr_17	  */
	public void setattr_17 (int attr_17);

	/** Get attr_17	  */
	public int getattr_17();

    /** Column name attr_18 */
    public static final String COLUMNNAME_attr_18 = "attr_18";

	/** Set attr_18	  */
	public void setattr_18 (int attr_18);

	/** Get attr_18	  */
	public int getattr_18();

    /** Column name attr_19 */
    public static final String COLUMNNAME_attr_19 = "attr_19";

	/** Set attr_19	  */
	public void setattr_19 (int attr_19);

	/** Get attr_19	  */
	public int getattr_19();

    /** Column name attr_2 */
    public static final String COLUMNNAME_attr_2 = "attr_2";

	/** Set attr_2	  */
	public void setattr_2 (int attr_2);

	/** Get attr_2	  */
	public int getattr_2();

    /** Column name attr_20 */
    public static final String COLUMNNAME_attr_20 = "attr_20";

	/** Set attr_20	  */
	public void setattr_20 (int attr_20);

	/** Get attr_20	  */
	public int getattr_20();

    /** Column name attr_21 */
    public static final String COLUMNNAME_attr_21 = "attr_21";

	/** Set attr_21	  */
	public void setattr_21 (int attr_21);

	/** Get attr_21	  */
	public int getattr_21();

    /** Column name attr_22 */
    public static final String COLUMNNAME_attr_22 = "attr_22";

	/** Set attr_22	  */
	public void setattr_22 (int attr_22);

	/** Get attr_22	  */
	public int getattr_22();

    /** Column name attr_24 */
    public static final String COLUMNNAME_attr_24 = "attr_24";

	/** Set attr_24	  */
	public void setattr_24 (int attr_24);

	/** Get attr_24	  */
	public int getattr_24();

    /** Column name attr_25 */
    public static final String COLUMNNAME_attr_25 = "attr_25";

	/** Set attr_25	  */
	public void setattr_25 (int attr_25);

	/** Get attr_25	  */
	public int getattr_25();

    /** Column name attr_27 */
    public static final String COLUMNNAME_attr_27 = "attr_27";

	/** Set attr_27	  */
	public void setattr_27 (int attr_27);

	/** Get attr_27	  */
	public int getattr_27();

    /** Column name attr_29 */
    public static final String COLUMNNAME_attr_29 = "attr_29";

	/** Set attr_29	  */
	public void setattr_29 (int attr_29);

	/** Get attr_29	  */
	public int getattr_29();

    /** Column name attr_3 */
    public static final String COLUMNNAME_attr_3 = "attr_3";

	/** Set attr_3	  */
	public void setattr_3 (int attr_3);

	/** Get attr_3	  */
	public int getattr_3();

    /** Column name attr_30 */
    public static final String COLUMNNAME_attr_30 = "attr_30";

	/** Set attr_30	  */
	public void setattr_30 (int attr_30);

	/** Get attr_30	  */
	public int getattr_30();

    /** Column name attr_31 */
    public static final String COLUMNNAME_attr_31 = "attr_31";

	/** Set attr_31	  */
	public void setattr_31 (int attr_31);

	/** Get attr_31	  */
	public int getattr_31();

    /** Column name attr_32 */
    public static final String COLUMNNAME_attr_32 = "attr_32";

	/** Set attr_32	  */
	public void setattr_32 (int attr_32);

	/** Get attr_32	  */
	public int getattr_32();

    /** Column name attr_36 */
    public static final String COLUMNNAME_attr_36 = "attr_36";

	/** Set attr_36	  */
	public void setattr_36 (int attr_36);

	/** Get attr_36	  */
	public int getattr_36();

    /** Column name attr_38 */
    public static final String COLUMNNAME_attr_38 = "attr_38";

	/** Set attr_38	  */
	public void setattr_38 (int attr_38);

	/** Get attr_38	  */
	public int getattr_38();

    /** Column name attr_39 */
    public static final String COLUMNNAME_attr_39 = "attr_39";

	/** Set attr_39	  */
	public void setattr_39 (int attr_39);

	/** Get attr_39	  */
	public int getattr_39();

    /** Column name attr_4 */
    public static final String COLUMNNAME_attr_4 = "attr_4";

	/** Set attr_4	  */
	public void setattr_4 (int attr_4);

	/** Get attr_4	  */
	public int getattr_4();

    /** Column name attr_40 */
    public static final String COLUMNNAME_attr_40 = "attr_40";

	/** Set attr_40	  */
	public void setattr_40 (int attr_40);

	/** Get attr_40	  */
	public int getattr_40();

    /** Column name attr_41 */
    public static final String COLUMNNAME_attr_41 = "attr_41";

	/** Set attr_41	  */
	public void setattr_41 (int attr_41);

	/** Get attr_41	  */
	public int getattr_41();

    /** Column name attr_45 */
    public static final String COLUMNNAME_attr_45 = "attr_45";

	/** Set attr_45	  */
	public void setattr_45 (int attr_45);

	/** Get attr_45	  */
	public int getattr_45();

    /** Column name attr_46 */
    public static final String COLUMNNAME_attr_46 = "attr_46";

	/** Set attr_46	  */
	public void setattr_46 (int attr_46);

	/** Get attr_46	  */
	public int getattr_46();

    /** Column name attr_47 */
    public static final String COLUMNNAME_attr_47 = "attr_47";

	/** Set attr_47	  */
	public void setattr_47 (int attr_47);

	/** Get attr_47	  */
	public int getattr_47();

    /** Column name attr_48 */
    public static final String COLUMNNAME_attr_48 = "attr_48";

	/** Set attr_48	  */
	public void setattr_48 (int attr_48);

	/** Get attr_48	  */
	public int getattr_48();

    /** Column name attr_5 */
    public static final String COLUMNNAME_attr_5 = "attr_5";

	/** Set attr_5	  */
	public void setattr_5 (int attr_5);

	/** Get attr_5	  */
	public int getattr_5();

    /** Column name attr_6 */
    public static final String COLUMNNAME_attr_6 = "attr_6";

	/** Set attr_6	  */
	public void setattr_6 (int attr_6);

	/** Get attr_6	  */
	public int getattr_6();

    /** Column name attr_7 */
    public static final String COLUMNNAME_attr_7 = "attr_7";

	/** Set attr_7	  */
	public void setattr_7 (int attr_7);

	/** Get attr_7	  */
	public int getattr_7();

    /** Column name attr_9 */
    public static final String COLUMNNAME_attr_9 = "attr_9";

	/** Set attr_9	  */
	public void setattr_9 (int attr_9);

	/** Get attr_9	  */
	public int getattr_9();

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

    /** Column name EnvCode */
    public static final String COLUMNNAME_EnvCode = "EnvCode";

	/** Set EnvCode	  */
	public void setEnvCode (int EnvCode);

	/** Get EnvCode	  */
	public int getEnvCode();

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

    /** Column name MeasureCode */
    public static final String COLUMNNAME_MeasureCode = "MeasureCode";

	/** Set MeasureCode	  */
	public void setMeasureCode (String MeasureCode);

	/** Get MeasureCode	  */
	public String getMeasureCode();

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

    /** Column name M_Product_Tandem_ID */
    public static final String COLUMNNAME_M_Product_Tandem_ID = "M_Product_Tandem_ID";

	/** Set M_Product_Tandem_ID	  */
	public void setM_Product_Tandem_ID (int M_Product_Tandem_ID);

	/** Get M_Product_Tandem_ID	  */
	public int getM_Product_Tandem_ID();

	public org.compiere.model.I_M_Product getM_Product_Tandem() throws RuntimeException;

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

    /** Column name ProcessingDate */
    public static final String COLUMNNAME_ProcessingDate = "ProcessingDate";

	/** Set Processing date	  */
	public void setProcessingDate (Timestamp ProcessingDate);

	/** Get Processing date	  */
	public Timestamp getProcessingDate();

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

    /** Column name ReadingDate */
    public static final String COLUMNNAME_ReadingDate = "ReadingDate";

	/** Set ReadingDate	  */
	public void setReadingDate (Timestamp ReadingDate);

	/** Get ReadingDate	  */
	public Timestamp getReadingDate();

    /** Column name TablaOrigen */
    public static final String COLUMNNAME_TablaOrigen = "TablaOrigen";

	/** Set TablaOrigen	  */
	public void setTablaOrigen (String TablaOrigen);

	/** Get TablaOrigen	  */
	public String getTablaOrigen();

    /** Column name UnitsPerPack */
    public static final String COLUMNNAME_UnitsPerPack = "UnitsPerPack";

	/** Set UnitsPerPack.
	  * The Units Per Pack indicates the no of units of a product packed together.
	  */
	public void setUnitsPerPack (int UnitsPerPack);

	/** Get UnitsPerPack.
	  * The Units Per Pack indicates the no of units of a product packed together.
	  */
	public int getUnitsPerPack();

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

    /** Column name UY_RT_Action_ID */
    public static final String COLUMNNAME_UY_RT_Action_ID = "UY_RT_Action_ID";

	/** Set UY_RT_Action	  */
	public void setUY_RT_Action_ID (int UY_RT_Action_ID);

	/** Get UY_RT_Action	  */
	public int getUY_RT_Action_ID();

	public I_UY_RT_Action getUY_RT_Action() throws RuntimeException;

    /** Column name UY_RT_InterfaceProd_ID */
    public static final String COLUMNNAME_UY_RT_InterfaceProd_ID = "UY_RT_InterfaceProd_ID";

	/** Set UY_RT_InterfaceProd	  */
	public void setUY_RT_InterfaceProd_ID (int UY_RT_InterfaceProd_ID);

	/** Get UY_RT_InterfaceProd	  */
	public int getUY_RT_InterfaceProd_ID();

    /** Column name UY_SubFamilia_ID */
    public static final String COLUMNNAME_UY_SubFamilia_ID = "UY_SubFamilia_ID";

	/** Set UY_SubFamilia	  */
	public void setUY_SubFamilia_ID (int UY_SubFamilia_ID);

	/** Get UY_SubFamilia	  */
	public int getUY_SubFamilia_ID();

	public I_UY_SubFamilia getUY_SubFamilia() throws RuntimeException;
}
