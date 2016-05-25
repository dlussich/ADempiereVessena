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

/** Generated Interface for UY_TR_Maintain
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Maintain 
{

    /** TableName=UY_TR_Maintain */
    public static final String Table_Name = "UY_TR_Maintain";

    /** AD_Table_ID=1000741 */
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

    /** Column name Consume_Acct_ID */
    public static final String COLUMNNAME_Consume_Acct_ID = "Consume_Acct_ID";

	/** Set Consume_Acct_ID.
	  * Consume_Acct_ID
	  */
	public void setConsume_Acct_ID (int Consume_Acct_ID);

	/** Get Consume_Acct_ID.
	  * Consume_Acct_ID
	  */
	public int getConsume_Acct_ID();

	public I_C_ValidCombination getConsume_Acct() throws RuntimeException;

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

    /** Column name Kilometros */
    public static final String COLUMNNAME_Kilometros = "Kilometros";

	/** Set Kilometros	  */
	public void setKilometros (int Kilometros);

	/** Get Kilometros	  */
	public int getKilometros();

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

    /** Column name MaintainProg */
    public static final String COLUMNNAME_MaintainProg = "MaintainProg";

	/** Set MaintainProg.
	  * Tipo de programacion de una tarea de mantenimiento de transporte
	  */
	public void setMaintainProg (String MaintainProg);

	/** Get MaintainProg.
	  * Tipo de programacion de una tarea de mantenimiento de transporte
	  */
	public String getMaintainProg();

    /** Column name MaintainType */
    public static final String COLUMNNAME_MaintainType = "MaintainType";

	/** Set MaintainType	  */
	public void setMaintainType (String MaintainType);

	/** Get MaintainType	  */
	public String getMaintainType();

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

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name PO_Acct_ID */
    public static final String COLUMNNAME_PO_Acct_ID = "PO_Acct_ID";

	/** Set PO_Acct_ID.
	  * PO_Acct_ID
	  */
	public void setPO_Acct_ID (int PO_Acct_ID);

	/** Get PO_Acct_ID.
	  * PO_Acct_ID
	  */
	public int getPO_Acct_ID();

	public I_C_ValidCombination getPO_Acct() throws RuntimeException;

    /** Column name porcentaje */
    public static final String COLUMNNAME_porcentaje = "porcentaje";

	/** Set porcentaje	  */
	public void setporcentaje (BigDecimal porcentaje);

	/** Get porcentaje	  */
	public BigDecimal getporcentaje();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (int Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public int getQty();

    /** Column name TimeFrequencyType */
    public static final String COLUMNNAME_TimeFrequencyType = "TimeFrequencyType";

	/** Set TimeFrequencyType	  */
	public void setTimeFrequencyType (String TimeFrequencyType);

	/** Get TimeFrequencyType	  */
	public String getTimeFrequencyType();

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

    /** Column name UY_TR_Failure_ID */
    public static final String COLUMNNAME_UY_TR_Failure_ID = "UY_TR_Failure_ID";

	/** Set UY_TR_Failure	  */
	public void setUY_TR_Failure_ID (int UY_TR_Failure_ID);

	/** Get UY_TR_Failure	  */
	public int getUY_TR_Failure_ID();

	public I_UY_TR_Failure getUY_TR_Failure() throws RuntimeException;

    /** Column name UY_TR_Maintain_ID */
    public static final String COLUMNNAME_UY_TR_Maintain_ID = "UY_TR_Maintain_ID";

	/** Set UY_TR_Maintain	  */
	public void setUY_TR_Maintain_ID (int UY_TR_Maintain_ID);

	/** Get UY_TR_Maintain	  */
	public int getUY_TR_Maintain_ID();

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
