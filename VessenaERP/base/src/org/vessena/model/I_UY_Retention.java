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

/** Generated Interface for UY_Retention
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Retention 
{

    /** TableName=UY_Retention */
    public static final String Table_Name = "UY_Retention";

    /** AD_Table_ID=1000383 */
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

    /** Column name ConceptPorc */
    public static final String COLUMNNAME_ConceptPorc = "ConceptPorc";

	/** Set ConceptPorc	  */
	public void setConceptPorc (BigDecimal ConceptPorc);

	/** Get ConceptPorc	  */
	public BigDecimal getConceptPorc();

    /** Column name ConceptValue */
    public static final String COLUMNNAME_ConceptValue = "ConceptValue";

	/** Set Concept Value.
	  * Value of the Concept
	  */
	public void setConceptValue (String ConceptValue);

	/** Get Concept Value.
	  * Value of the Concept
	  */
	public String getConceptValue();

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

    /** Column name IsUnidadIndexada */
    public static final String COLUMNNAME_IsUnidadIndexada = "IsUnidadIndexada";

	/** Set IsUnidadIndexada	  */
	public void setIsUnidadIndexada (boolean IsUnidadIndexada);

	/** Get IsUnidadIndexada	  */
	public boolean isUnidadIndexada();

    /** Column name MaxAmt */
    public static final String COLUMNNAME_MaxAmt = "MaxAmt";

	/** Set Max Amount.
	  * Maximum Amount in invoice currency
	  */
	public void setMaxAmt (BigDecimal MaxAmt);

	/** Get Max Amount.
	  * Maximum Amount in invoice currency
	  */
	public BigDecimal getMaxAmt();

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

    /** Column name Rate */
    public static final String COLUMNNAME_Rate = "Rate";

	/** Set Rate.
	  * Rate or Tax or Exchange
	  */
	public void setRate (BigDecimal Rate);

	/** Get Rate.
	  * Rate or Tax or Exchange
	  */
	public BigDecimal getRate();

    /** Column name R_Retention_Acct */
    public static final String COLUMNNAME_R_Retention_Acct = "R_Retention_Acct";

	/** Set R_Retention_Acct	  */
	public void setR_Retention_Acct (int R_Retention_Acct);

	/** Get R_Retention_Acct	  */
	public int getR_Retention_Acct();

	public I_C_ValidCombination getR_Retention_A() throws RuntimeException;

    /** Column name R_Transit_Acct */
    public static final String COLUMNNAME_R_Transit_Acct = "R_Transit_Acct";

	/** Set R_Transit_Acct	  */
	public void setR_Transit_Acct (int R_Transit_Acct);

	/** Get R_Transit_Acct	  */
	public int getR_Transit_Acct();

	public I_C_ValidCombination getR_Transit_A() throws RuntimeException;

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

    /** Column name UY_ProductGroup_ID */
    public static final String COLUMNNAME_UY_ProductGroup_ID = "UY_ProductGroup_ID";

	/** Set UY_ProductGroup	  */
	public void setUY_ProductGroup_ID (int UY_ProductGroup_ID);

	/** Get UY_ProductGroup	  */
	public int getUY_ProductGroup_ID();

	public I_UY_ProductGroup getUY_ProductGroup() throws RuntimeException;

    /** Column name UY_Retention_ID */
    public static final String COLUMNNAME_UY_Retention_ID = "UY_Retention_ID";

	/** Set UY_Retention	  */
	public void setUY_Retention_ID (int UY_Retention_ID);

	/** Get UY_Retention	  */
	public int getUY_Retention_ID();

    /** Column name UY_SubFamilia_ID */
    public static final String COLUMNNAME_UY_SubFamilia_ID = "UY_SubFamilia_ID";

	/** Set UY_SubFamilia	  */
	public void setUY_SubFamilia_ID (int UY_SubFamilia_ID);

	/** Get UY_SubFamilia	  */
	public int getUY_SubFamilia_ID();

	public I_UY_SubFamilia getUY_SubFamilia() throws RuntimeException;

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
