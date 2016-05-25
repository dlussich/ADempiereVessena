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

/** Generated Interface for UY_R_Inbox
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_Inbox 
{

    /** TableName=UY_R_Inbox */
    public static final String Table_Name = "UY_R_Inbox";

    /** AD_Table_ID=1000469 */
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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name AssignTo_ID */
    public static final String COLUMNNAME_AssignTo_ID = "AssignTo_ID";

	/** Set AssignTo_ID	  */
	public void setAssignTo_ID (int AssignTo_ID);

	/** Get AssignTo_ID	  */
	public int getAssignTo_ID();

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

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

    /** Column name CriticalImage_ID */
    public static final String COLUMNNAME_CriticalImage_ID = "CriticalImage_ID";

	/** Set CriticalImage_ID	  */
	public void setCriticalImage_ID (int CriticalImage_ID);

	/** Get CriticalImage_ID	  */
	public int getCriticalImage_ID();

    /** Column name CriticalSeqNo */
    public static final String COLUMNNAME_CriticalSeqNo = "CriticalSeqNo";

	/** Set CriticalSeqNo	  */
	public void setCriticalSeqNo (int CriticalSeqNo);

	/** Get CriticalSeqNo	  */
	public int getCriticalSeqNo();

    /** Column name DateAssign */
    public static final String COLUMNNAME_DateAssign = "DateAssign";

	/** Set DateAssign.
	  * DateAssign
	  */
	public void setDateAssign (Timestamp DateAssign);

	/** Get DateAssign.
	  * DateAssign
	  */
	public Timestamp getDateAssign();

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

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

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

    /** Column name MediumDueDate */
    public static final String COLUMNNAME_MediumDueDate = "MediumDueDate";

	/** Set MediumDueDate	  */
	public void setMediumDueDate (Timestamp MediumDueDate);

	/** Get MediumDueDate	  */
	public Timestamp getMediumDueDate();

    /** Column name MediumTerm */
    public static final String COLUMNNAME_MediumTerm = "MediumTerm";

	/** Set MediumTerm	  */
	public void setMediumTerm (int MediumTerm);

	/** Get MediumTerm	  */
	public int getMediumTerm();

    /** Column name ModoVista */
    public static final String COLUMNNAME_ModoVista = "ModoVista";

	/** Set ModoVista	  */
	public void setModoVista (String ModoVista);

	/** Get ModoVista	  */
	public String getModoVista();

    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/** Set Priority.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (String Priority);

	/** Get Priority.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public String getPriority();

    /** Column name PriorityManual */
    public static final String COLUMNNAME_PriorityManual = "PriorityManual";

	/** Set PriorityManual	  */
	public void setPriorityManual (String PriorityManual);

	/** Get PriorityManual	  */
	public String getPriorityManual();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name StatusReclamo */
    public static final String COLUMNNAME_StatusReclamo = "StatusReclamo";

	/** Set StatusReclamo	  */
	public void setStatusReclamo (String StatusReclamo);

	/** Get StatusReclamo	  */
	public String getStatusReclamo();

    /** Column name StatusTarea */
    public static final String COLUMNNAME_StatusTarea = "StatusTarea";

	/** Set StatusTarea	  */
	public void setStatusTarea (String StatusTarea);

	/** Get StatusTarea	  */
	public String getStatusTarea();

    /** Column name TrackImage_ID */
    public static final String COLUMNNAME_TrackImage_ID = "TrackImage_ID";

	/** Set TrackImage_ID	  */
	public void setTrackImage_ID (int TrackImage_ID);

	/** Get TrackImage_ID	  */
	public int getTrackImage_ID();

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

    /** Column name UY_R_Canal_ID */
    public static final String COLUMNNAME_UY_R_Canal_ID = "UY_R_Canal_ID";

	/** Set UY_R_Canal	  */
	public void setUY_R_Canal_ID (int UY_R_Canal_ID);

	/** Get UY_R_Canal	  */
	public int getUY_R_Canal_ID();

	public I_UY_R_Canal getUY_R_Canal() throws RuntimeException;

    /** Column name UY_R_Cause_ID */
    public static final String COLUMNNAME_UY_R_Cause_ID = "UY_R_Cause_ID";

	/** Set UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID);

	/** Get UY_R_Cause	  */
	public int getUY_R_Cause_ID();

	public I_UY_R_Cause getUY_R_Cause() throws RuntimeException;

    /** Column name UY_R_Inbox_ID */
    public static final String COLUMNNAME_UY_R_Inbox_ID = "UY_R_Inbox_ID";

	/** Set UY_R_Inbox	  */
	public void setUY_R_Inbox_ID (int UY_R_Inbox_ID);

	/** Get UY_R_Inbox	  */
	public int getUY_R_Inbox_ID();

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

    /** Column name uy_r_subcause_id_1 */
    public static final String COLUMNNAME_uy_r_subcause_id_1 = "uy_r_subcause_id_1";

	/** Set uy_r_subcause_id_1	  */
	public void setuy_r_subcause_id_1 (int uy_r_subcause_id_1);

	/** Get uy_r_subcause_id_1	  */
	public int getuy_r_subcause_id_1();

    /** Column name uy_r_subcause_id_2 */
    public static final String COLUMNNAME_uy_r_subcause_id_2 = "uy_r_subcause_id_2";

	/** Set uy_r_subcause_id_2	  */
	public void setuy_r_subcause_id_2 (int uy_r_subcause_id_2);

	/** Get uy_r_subcause_id_2	  */
	public int getuy_r_subcause_id_2();

    /** Column name uy_r_subcause_id_3 */
    public static final String COLUMNNAME_uy_r_subcause_id_3 = "uy_r_subcause_id_3";

	/** Set uy_r_subcause_id_3	  */
	public void setuy_r_subcause_id_3 (int uy_r_subcause_id_3);

	/** Get uy_r_subcause_id_3	  */
	public int getuy_r_subcause_id_3();

    /** Column name uy_r_subcause_id_4 */
    public static final String COLUMNNAME_uy_r_subcause_id_4 = "uy_r_subcause_id_4";

	/** Set uy_r_subcause_id_4	  */
	public void setuy_r_subcause_id_4 (int uy_r_subcause_id_4);

	/** Get uy_r_subcause_id_4	  */
	public int getuy_r_subcause_id_4();

    /** Column name UY_R_Tarea_ID */
    public static final String COLUMNNAME_UY_R_Tarea_ID = "UY_R_Tarea_ID";

	/** Set UY_R_Tarea	  */
	public void setUY_R_Tarea_ID (int UY_R_Tarea_ID);

	/** Get UY_R_Tarea	  */
	public int getUY_R_Tarea_ID();

	public I_UY_R_Tarea getUY_R_Tarea() throws RuntimeException;

    /** Column name UY_R_Type_ID */
    public static final String COLUMNNAME_UY_R_Type_ID = "UY_R_Type_ID";

	/** Set UY_R_Type	  */
	public void setUY_R_Type_ID (int UY_R_Type_ID);

	/** Get UY_R_Type	  */
	public int getUY_R_Type_ID();

	public I_UY_R_Type getUY_R_Type() throws RuntimeException;
}
