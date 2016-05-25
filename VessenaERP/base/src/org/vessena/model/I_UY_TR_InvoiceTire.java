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

/** Generated Interface for UY_TR_InvoiceTire
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_InvoiceTire 
{

    /** TableName=UY_TR_InvoiceTire */
    public static final String Table_Name = "UY_TR_InvoiceTire";

    /** AD_Table_ID=1000796 */
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

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

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

    /** Column name QtyKmNew */
    public static final String COLUMNNAME_QtyKmNew = "QtyKmNew";

	/** Set QtyKmNew	  */
	public void setQtyKmNew (int QtyKmNew);

	/** Get QtyKmNew	  */
	public int getQtyKmNew();

    /** Column name QtyKmRecauchu */
    public static final String COLUMNNAME_QtyKmRecauchu = "QtyKmRecauchu";

	/** Set QtyKmRecauchu	  */
	public void setQtyKmRecauchu (int QtyKmRecauchu);

	/** Get QtyKmRecauchu	  */
	public int getQtyKmRecauchu();

    /** Column name TireModel */
    public static final String COLUMNNAME_TireModel = "TireModel";

	/** Set TireModel.
	  * TireModel
	  */
	public void setTireModel (String TireModel);

	/** Get TireModel.
	  * TireModel
	  */
	public String getTireModel();

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

    /** Column name UY_TR_InvoiceTire_ID */
    public static final String COLUMNNAME_UY_TR_InvoiceTire_ID = "UY_TR_InvoiceTire_ID";

	/** Set UY_TR_InvoiceTire	  */
	public void setUY_TR_InvoiceTire_ID (int UY_TR_InvoiceTire_ID);

	/** Get UY_TR_InvoiceTire	  */
	public int getUY_TR_InvoiceTire_ID();

    /** Column name UY_TR_TireMark_ID */
    public static final String COLUMNNAME_UY_TR_TireMark_ID = "UY_TR_TireMark_ID";

	/** Set UY_TR_TireMark	  */
	public void setUY_TR_TireMark_ID (int UY_TR_TireMark_ID);

	/** Get UY_TR_TireMark	  */
	public int getUY_TR_TireMark_ID();

	public I_UY_TR_TireMark getUY_TR_TireMark() throws RuntimeException;

    /** Column name UY_TR_TireMeasure_ID */
    public static final String COLUMNNAME_UY_TR_TireMeasure_ID = "UY_TR_TireMeasure_ID";

	/** Set UY_TR_TireMeasure	  */
	public void setUY_TR_TireMeasure_ID (int UY_TR_TireMeasure_ID);

	/** Get UY_TR_TireMeasure	  */
	public int getUY_TR_TireMeasure_ID();

	public I_UY_TR_TireMeasure getUY_TR_TireMeasure() throws RuntimeException;

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
