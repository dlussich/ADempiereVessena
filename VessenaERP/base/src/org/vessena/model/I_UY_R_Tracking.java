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

/** Generated Interface for UY_R_Tracking
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_Tracking 
{

    /** TableName=UY_R_Tracking */
    public static final String Table_Name = "UY_R_Tracking";

    /** AD_Table_ID=1000498 */
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

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

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

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

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

    /** Column name UY_R_ActionResponse_ID */
    public static final String COLUMNNAME_UY_R_ActionResponse_ID = "UY_R_ActionResponse_ID";

	/** Set UY_R_ActionResponse	  */
	public void setUY_R_ActionResponse_ID (int UY_R_ActionResponse_ID);

	/** Get UY_R_ActionResponse	  */
	public int getUY_R_ActionResponse_ID();

	public I_UY_R_ActionResponse getUY_R_ActionResponse() throws RuntimeException;

    /** Column name UY_R_ReclamoAccion_ID */
    public static final String COLUMNNAME_UY_R_ReclamoAccion_ID = "UY_R_ReclamoAccion_ID";

	/** Set UY_R_ReclamoAccion	  */
	public void setUY_R_ReclamoAccion_ID (int UY_R_ReclamoAccion_ID);

	/** Get UY_R_ReclamoAccion	  */
	public int getUY_R_ReclamoAccion_ID();

	public I_UY_R_ReclamoAccion getUY_R_ReclamoAccion() throws RuntimeException;

    /** Column name UY_R_Reclamo_ID */
    public static final String COLUMNNAME_UY_R_Reclamo_ID = "UY_R_Reclamo_ID";

	/** Set UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID);

	/** Get UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID();

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException;

    /** Column name UY_R_Tarea_ID */
    public static final String COLUMNNAME_UY_R_Tarea_ID = "UY_R_Tarea_ID";

	/** Set UY_R_Tarea	  */
	public void setUY_R_Tarea_ID (int UY_R_Tarea_ID);

	/** Get UY_R_Tarea	  */
	public int getUY_R_Tarea_ID();

	public I_UY_R_Tarea getUY_R_Tarea() throws RuntimeException;

    /** Column name UY_R_Tracking_ID */
    public static final String COLUMNNAME_UY_R_Tracking_ID = "UY_R_Tracking_ID";

	/** Set UY_R_Tracking	  */
	public void setUY_R_Tracking_ID (int UY_R_Tracking_ID);

	/** Get UY_R_Tracking	  */
	public int getUY_R_Tracking_ID();
}
