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

/** Generated Interface for UY_TR_TruckType
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_TruckType 
{

    /** TableName=UY_TR_TruckType */
    public static final String Table_Name = "UY_TR_TruckType";

    /** AD_Table_ID=1000716 */
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

    /** Column name AxisDistance */
    public static final String COLUMNNAME_AxisDistance = "AxisDistance";

	/** Set AxisDistance	  */
	public void setAxisDistance (String AxisDistance);

	/** Get AxisDistance	  */
	public String getAxisDistance();

    /** Column name AxisType */
    public static final String COLUMNNAME_AxisType = "AxisType";

	/** Set AxisType.
	  * AxisType
	  */
	public void setAxisType (String AxisType);

	/** Get AxisType.
	  * AxisType
	  */
	public String getAxisType();

    /** Column name AxisWeight */
    public static final String COLUMNNAME_AxisWeight = "AxisWeight";

	/** Set AxisWeight	  */
	public void setAxisWeight (BigDecimal AxisWeight);

	/** Get AxisWeight	  */
	public BigDecimal getAxisWeight();

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

    /** Column name IsAssociated */
    public static final String COLUMNNAME_IsAssociated = "IsAssociated";

	/** Set IsAssociated	  */
	public void setIsAssociated (boolean IsAssociated);

	/** Get IsAssociated	  */
	public boolean isAssociated();

    /** Column name IsContainer */
    public static final String COLUMNNAME_IsContainer = "IsContainer";

	/** Set IsContainer	  */
	public void setIsContainer (boolean IsContainer);

	/** Get IsContainer	  */
	public boolean isContainer();

    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/** Set Locator Key.
	  * Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue);

	/** Get Locator Key.
	  * Key of the Warehouse Locator
	  */
	public int getLocatorValue();

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

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name QtyAux */
    public static final String COLUMNNAME_QtyAux = "QtyAux";

	/** Set QtyAux.
	  * QtyAux
	  */
	public void setQtyAux (int QtyAux);

	/** Get QtyAux.
	  * QtyAux
	  */
	public int getQtyAux();

    /** Column name QtyAxis */
    public static final String COLUMNNAME_QtyAxis = "QtyAxis";

	/** Set QtyAxis.
	  * QtyAxis
	  */
	public void setQtyAxis (int QtyAxis);

	/** Get QtyAxis.
	  * QtyAxis
	  */
	public int getQtyAxis();

    /** Column name QtyLong */
    public static final String COLUMNNAME_QtyLong = "QtyLong";

	/** Set QtyLong	  */
	public void setQtyLong (BigDecimal QtyLong);

	/** Get QtyLong	  */
	public BigDecimal getQtyLong();

    /** Column name Traction */
    public static final String COLUMNNAME_Traction = "Traction";

	/** Set Traction	  */
	public void setTraction (String Traction);

	/** Get Traction	  */
	public String getTraction();

    /** Column name TruckGroup */
    public static final String COLUMNNAME_TruckGroup = "TruckGroup";

	/** Set TruckGroup	  */
	public void setTruckGroup (String TruckGroup);

	/** Get TruckGroup	  */
	public String getTruckGroup();

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

    /** Column name UY_TR_TruckType_ID */
    public static final String COLUMNNAME_UY_TR_TruckType_ID = "UY_TR_TruckType_ID";

	/** Set UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID);

	/** Get UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID();

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

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();
}
