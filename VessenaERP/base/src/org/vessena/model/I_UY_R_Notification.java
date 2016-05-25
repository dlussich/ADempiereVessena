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

/** Generated Interface for UY_R_Notification
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_Notification 
{

    /** TableName=UY_R_Notification */
    public static final String Table_Name = "UY_R_Notification";

    /** AD_Table_ID=1000512 */
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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

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

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

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

    /** Column name IsNotified */
    public static final String COLUMNNAME_IsNotified = "IsNotified";

	/** Set IsNotified.
	  * IsNotified
	  */
	public void setIsNotified (boolean IsNotified);

	/** Get IsNotified.
	  * IsNotified
	  */
	public boolean isNotified();

    /** Column name IsOver */
    public static final String COLUMNNAME_IsOver = "IsOver";

	/** Set IsOver.
	  * IsOver
	  */
	public void setIsOver (boolean IsOver);

	/** Get IsOver.
	  * IsOver
	  */
	public boolean isOver();

    /** Column name NotificationActionType */
    public static final String COLUMNNAME_NotificationActionType = "NotificationActionType";

	/** Set NotificationActionType	  */
	public void setNotificationActionType (String NotificationActionType);

	/** Get NotificationActionType	  */
	public String getNotificationActionType();

    /** Column name PreActionType */
    public static final String COLUMNNAME_PreActionType = "PreActionType";

	/** Set PreActionType.
	  * PreActionType
	  */
	public void setPreActionType (String PreActionType);

	/** Get PreActionType.
	  * PreActionType
	  */
	public String getPreActionType();

    /** Column name Receptor_ID */
    public static final String COLUMNNAME_Receptor_ID = "Receptor_ID";

	/** Set Receptor_ID	  */
	public void setReceptor_ID (int Receptor_ID);

	/** Get Receptor_ID	  */
	public int getReceptor_ID();

    /** Column name Responsable_ID */
    public static final String COLUMNNAME_Responsable_ID = "Responsable_ID";

	/** Set Responsable_ID	  */
	public void setResponsable_ID (int Responsable_ID);

	/** Get Responsable_ID	  */
	public int getResponsable_ID();

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

    /** Column name UY_R_Action_ID */
    public static final String COLUMNNAME_UY_R_Action_ID = "UY_R_Action_ID";

	/** Set UY_R_Action	  */
	public void setUY_R_Action_ID (int UY_R_Action_ID);

	/** Get UY_R_Action	  */
	public int getUY_R_Action_ID();

	public I_UY_R_Action getUY_R_Action() throws RuntimeException;

    /** Column name UY_R_Area_ID */
    public static final String COLUMNNAME_UY_R_Area_ID = "UY_R_Area_ID";

	/** Set UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID);

	/** Get UY_R_Area	  */
	public int getUY_R_Area_ID();

	public I_UY_R_Area getUY_R_Area() throws RuntimeException;

    /** Column name UY_R_Notification_ID */
    public static final String COLUMNNAME_UY_R_Notification_ID = "UY_R_Notification_ID";

	/** Set UY_R_Notification	  */
	public void setUY_R_Notification_ID (int UY_R_Notification_ID);

	/** Get UY_R_Notification	  */
	public int getUY_R_Notification_ID();

    /** Column name UY_R_PtoResolucion_ID */
    public static final String COLUMNNAME_UY_R_PtoResolucion_ID = "UY_R_PtoResolucion_ID";

	/** Set UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID);

	/** Get UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID();

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException;

    /** Column name UY_R_Reclamo_ID */
    public static final String COLUMNNAME_UY_R_Reclamo_ID = "UY_R_Reclamo_ID";

	/** Set UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID);

	/** Get UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID();

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException;
}
