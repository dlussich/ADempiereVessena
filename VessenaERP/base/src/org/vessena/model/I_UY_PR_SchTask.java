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

/** Generated Interface for UY_PR_SchTask
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PR_SchTask 
{

    /** TableName=UY_PR_SchTask */
    public static final String Table_Name = "UY_PR_SchTask";

    /** AD_Table_ID=1000734 */
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

    /** Column name Condition */
    public static final String COLUMNNAME_Condition = "Condition";

	/** Set Condition.
	  * Condition
	  */
	public void setCondition (String Condition);

	/** Get Condition.
	  * Condition
	  */
	public String getCondition();

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

    /** Column name FrequencySubType */
    public static final String COLUMNNAME_FrequencySubType = "FrequencySubType";

	/** Set FrequencySubType	  */
	public void setFrequencySubType (String FrequencySubType);

	/** Get FrequencySubType	  */
	public String getFrequencySubType();

    /** Column name FrequencyType */
    public static final String COLUMNNAME_FrequencyType = "FrequencyType";

	/** Set FrequencyType.
	  * Frequency of event
	  */
	public void setFrequencyType (String FrequencyType);

	/** Get FrequencyType.
	  * Frequency of event
	  */
	public String getFrequencyType();

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

    /** Column name StartMode */
    public static final String COLUMNNAME_StartMode = "StartMode";

	/** Set Start Mode.
	  * Workflow Activity Start Mode 
	  */
	public void setStartMode (String StartMode);

	/** Get Start Mode.
	  * Workflow Activity Start Mode 
	  */
	public String getStartMode();

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

    /** Column name UY_PR_ParentTask_ID */
    public static final String COLUMNNAME_UY_PR_ParentTask_ID = "UY_PR_ParentTask_ID";

	/** Set UY_PR_ParentTask_ID	  */
	public void setUY_PR_ParentTask_ID (int UY_PR_ParentTask_ID);

	/** Get UY_PR_ParentTask_ID	  */
	public int getUY_PR_ParentTask_ID();

    /** Column name UY_PR_Schedule_ID */
    public static final String COLUMNNAME_UY_PR_Schedule_ID = "UY_PR_Schedule_ID";

	/** Set UY_PR_Schedule	  */
	public void setUY_PR_Schedule_ID (int UY_PR_Schedule_ID);

	/** Get UY_PR_Schedule	  */
	public int getUY_PR_Schedule_ID();

	public I_UY_PR_Schedule getUY_PR_Schedule() throws RuntimeException;

    /** Column name UY_PR_SchTask_ID */
    public static final String COLUMNNAME_UY_PR_SchTask_ID = "UY_PR_SchTask_ID";

	/** Set UY_PR_SchTask	  */
	public void setUY_PR_SchTask_ID (int UY_PR_SchTask_ID);

	/** Get UY_PR_SchTask	  */
	public int getUY_PR_SchTask_ID();

    /** Column name UY_PR_Task_ID */
    public static final String COLUMNNAME_UY_PR_Task_ID = "UY_PR_Task_ID";

	/** Set UY_PR_Task	  */
	public void setUY_PR_Task_ID (int UY_PR_Task_ID);

	/** Get UY_PR_Task	  */
	public int getUY_PR_Task_ID();

	public I_UY_PR_Task getUY_PR_Task() throws RuntimeException;
}
