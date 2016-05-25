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

/** Generated Interface for UY_PR_Task
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PR_Task 
{

    /** TableName=UY_PR_Task */
    public static final String Table_Name = "UY_PR_Task";

    /** AD_Table_ID=1000733 */
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

    /** Column name DeadLine */
    public static final String COLUMNNAME_DeadLine = "DeadLine";

	/** Set DeadLine	  */
	public void setDeadLine (BigDecimal DeadLine);

	/** Get DeadLine	  */
	public BigDecimal getDeadLine();

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

    /** Column name MediumTerm */
    public static final String COLUMNNAME_MediumTerm = "MediumTerm";

	/** Set MediumTerm	  */
	public void setMediumTerm (BigDecimal MediumTerm);

	/** Get MediumTerm	  */
	public BigDecimal getMediumTerm();

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

    /** Column name UY_PR_Task_ID */
    public static final String COLUMNNAME_UY_PR_Task_ID = "UY_PR_Task_ID";

	/** Set UY_PR_Task	  */
	public void setUY_PR_Task_ID (int UY_PR_Task_ID);

	/** Get UY_PR_Task	  */
	public int getUY_PR_Task_ID();

    /** Column name UY_R_Cause_ID */
    public static final String COLUMNNAME_UY_R_Cause_ID = "UY_R_Cause_ID";

	/** Set UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID);

	/** Get UY_R_Cause	  */
	public int getUY_R_Cause_ID();

	public I_UY_R_Cause getUY_R_Cause() throws RuntimeException;

    /** Column name UY_R_SubCause_ID */
    public static final String COLUMNNAME_UY_R_SubCause_ID = "UY_R_SubCause_ID";

	/** Set UY_R_SubCause	  */
	public void setUY_R_SubCause_ID (int UY_R_SubCause_ID);

	/** Get UY_R_SubCause	  */
	public int getUY_R_SubCause_ID();

	public I_UY_R_SubCause getUY_R_SubCause() throws RuntimeException;

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
