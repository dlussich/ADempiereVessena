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

/** Generated Interface for UY_R_Cause
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_Cause 
{

    /** TableName=UY_R_Cause */
    public static final String Table_Name = "UY_R_Cause";

    /** AD_Table_ID=1000453 */
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

    /** Column name ConLegajo */
    public static final String COLUMNNAME_ConLegajo = "ConLegajo";

	/** Set ConLegajo	  */
	public void setConLegajo (boolean ConLegajo);

	/** Get ConLegajo	  */
	public boolean isConLegajo();

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

    /** Column name DeadLine */
    public static final String COLUMNNAME_DeadLine = "DeadLine";

	/** Set DeadLine	  */
	public void setDeadLine (int DeadLine);

	/** Get DeadLine	  */
	public int getDeadLine();

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

    /** Column name DiasSeguimiento */
    public static final String COLUMNNAME_DiasSeguimiento = "DiasSeguimiento";

	/** Set DiasSeguimiento	  */
	public void setDiasSeguimiento (int DiasSeguimiento);

	/** Get DiasSeguimiento	  */
	public int getDiasSeguimiento();

    /** Column name GenerateInbox */
    public static final String COLUMNNAME_GenerateInbox = "GenerateInbox";

	/** Set GenerateInbox	  */
	public void setGenerateInbox (boolean GenerateInbox);

	/** Get GenerateInbox	  */
	public boolean isGenerateInbox();

    /** Column name InformaBCU */
    public static final String COLUMNNAME_InformaBCU = "InformaBCU";

	/** Set InformaBCU	  */
	public void setInformaBCU (boolean InformaBCU);

	/** Get InformaBCU	  */
	public boolean isInformaBCU();

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

    /** Column name IsInternalIssue */
    public static final String COLUMNNAME_IsInternalIssue = "IsInternalIssue";

	/** Set IsInternalIssue.
	  * Incidencia Interna
	  */
	public void setIsInternalIssue (boolean IsInternalIssue);

	/** Get IsInternalIssue.
	  * Incidencia Interna
	  */
	public boolean isInternalIssue();

    /** Column name MediumTerm */
    public static final String COLUMNNAME_MediumTerm = "MediumTerm";

	/** Set MediumTerm	  */
	public void setMediumTerm (int MediumTerm);

	/** Get MediumTerm	  */
	public int getMediumTerm();

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

    /** Column name Notificable */
    public static final String COLUMNNAME_Notificable = "Notificable";

	/** Set Notificable	  */
	public void setNotificable (boolean Notificable);

	/** Get Notificable	  */
	public boolean isNotificable();

    /** Column name NotificaEmail */
    public static final String COLUMNNAME_NotificaEmail = "NotificaEmail";

	/** Set NotificaEmail	  */
	public void setNotificaEmail (boolean NotificaEmail);

	/** Get NotificaEmail	  */
	public boolean isNotificaEmail();

    /** Column name PriorityBase */
    public static final String COLUMNNAME_PriorityBase = "PriorityBase";

	/** Set Priority Base.
	  * Base of Priority
	  */
	public void setPriorityBase (String PriorityBase);

	/** Get Priority Base.
	  * Base of Priority
	  */
	public String getPriorityBase();

    /** Column name RequiereSeguimiento */
    public static final String COLUMNNAME_RequiereSeguimiento = "RequiereSeguimiento";

	/** Set RequiereSeguimiento	  */
	public void setRequiereSeguimiento (boolean RequiereSeguimiento);

	/** Get RequiereSeguimiento	  */
	public boolean isRequiereSeguimiento();

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

    /** Column name UY_R_Area_ID */
    public static final String COLUMNNAME_UY_R_Area_ID = "UY_R_Area_ID";

	/** Set UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID);

	/** Get UY_R_Area	  */
	public int getUY_R_Area_ID();

	public I_UY_R_Area getUY_R_Area() throws RuntimeException;

    /** Column name UY_R_Cause_ID */
    public static final String COLUMNNAME_UY_R_Cause_ID = "UY_R_Cause_ID";

	/** Set UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID);

	/** Get UY_R_Cause	  */
	public int getUY_R_Cause_ID();

    /** Column name UY_R_PtoResolucion_ID */
    public static final String COLUMNNAME_UY_R_PtoResolucion_ID = "UY_R_PtoResolucion_ID";

	/** Set UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID);

	/** Get UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID();

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException;

    /** Column name UY_R_Type_ID */
    public static final String COLUMNNAME_UY_R_Type_ID = "UY_R_Type_ID";

	/** Set UY_R_Type	  */
	public void setUY_R_Type_ID (int UY_R_Type_ID);

	/** Get UY_R_Type	  */
	public int getUY_R_Type_ID();

	public I_UY_R_Type getUY_R_Type() throws RuntimeException;

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
