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

/** Generated Interface for UY_TR_LoadStatus
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_LoadStatus 
{

    /** TableName=UY_TR_LoadStatus */
    public static final String Table_Name = "UY_TR_LoadStatus";

    /** AD_Table_ID=1000759 */
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

    /** Column name ChangeDriver */
    public static final String COLUMNNAME_ChangeDriver = "ChangeDriver";

	/** Set ChangeDriver	  */
	public void setChangeDriver (boolean ChangeDriver);

	/** Get ChangeDriver	  */
	public boolean isChangeDriver();

    /** Column name ChangeTruck */
    public static final String COLUMNNAME_ChangeTruck = "ChangeTruck";

	/** Set ChangeTruck	  */
	public void setChangeTruck (boolean ChangeTruck);

	/** Get ChangeTruck	  */
	public boolean isChangeTruck();

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

    /** Column name EndTrackStatus */
    public static final String COLUMNNAME_EndTrackStatus = "EndTrackStatus";

	/** Set EndTrackStatus	  */
	public void setEndTrackStatus (boolean EndTrackStatus);

	/** Get EndTrackStatus	  */
	public boolean isEndTrackStatus();

    /** Column name HandleCRT */
    public static final String COLUMNNAME_HandleCRT = "HandleCRT";

	/** Set HandleCRT	  */
	public void setHandleCRT (boolean HandleCRT);

	/** Get HandleCRT	  */
	public boolean isHandleCRT();

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

    /** Column name IsBeforeLoad */
    public static final String COLUMNNAME_IsBeforeLoad = "IsBeforeLoad";

	/** Set IsBeforeLoad	  */
	public void setIsBeforeLoad (boolean IsBeforeLoad);

	/** Get IsBeforeLoad	  */
	public boolean isBeforeLoad();

    /** Column name IsConfirmation */
    public static final String COLUMNNAME_IsConfirmation = "IsConfirmation";

	/** Set IsConfirmation	  */
	public void setIsConfirmation (boolean IsConfirmation);

	/** Get IsConfirmation	  */
	public boolean isConfirmation();

    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/** Set Delivered	  */
	public void setIsDelivered (boolean IsDelivered);

	/** Get Delivered	  */
	public boolean isDelivered();

    /** Column name IsLastre */
    public static final String COLUMNNAME_IsLastre = "IsLastre";

	/** Set IsLastre	  */
	public void setIsLastre (boolean IsLastre);

	/** Get IsLastre	  */
	public boolean isLastre();

    /** Column name IsPlanification */
    public static final String COLUMNNAME_IsPlanification = "IsPlanification";

	/** Set IsPlanification	  */
	public void setIsPlanification (boolean IsPlanification);

	/** Get IsPlanification	  */
	public boolean isPlanification();

    /** Column name IsTrasbordo */
    public static final String COLUMNNAME_IsTrasbordo = "IsTrasbordo";

	/** Set IsTrasbordo	  */
	public void setIsTrasbordo (boolean IsTrasbordo);

	/** Get IsTrasbordo	  */
	public boolean isTrasbordo();

    /** Column name IsWarehouseRequired */
    public static final String COLUMNNAME_IsWarehouseRequired = "IsWarehouseRequired";

	/** Set IsWarehouseRequired.
	  * IsWarehouseRequired
	  */
	public void setIsWarehouseRequired (boolean IsWarehouseRequired);

	/** Get IsWarehouseRequired.
	  * IsWarehouseRequired
	  */
	public boolean isWarehouseRequired();

    /** Column name LoadProduct */
    public static final String COLUMNNAME_LoadProduct = "LoadProduct";

	/** Set LoadProduct	  */
	public void setLoadProduct (boolean LoadProduct);

	/** Get LoadProduct	  */
	public boolean isLoadProduct();

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

    /** Column name OnMove */
    public static final String COLUMNNAME_OnMove = "OnMove";

	/** Set OnMove.
	  * OnMove
	  */
	public void setOnMove (boolean OnMove);

	/** Get OnMove.
	  * OnMove
	  */
	public boolean isOnMove();

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

    /** Column name UY_TR_LoadStatus_ID */
    public static final String COLUMNNAME_UY_TR_LoadStatus_ID = "UY_TR_LoadStatus_ID";

	/** Set UY_TR_LoadStatus	  */
	public void setUY_TR_LoadStatus_ID (int UY_TR_LoadStatus_ID);

	/** Get UY_TR_LoadStatus	  */
	public int getUY_TR_LoadStatus_ID();

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
