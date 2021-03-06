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

/** Generated Interface for UY_PR_SchTaskMonth
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PR_SchTaskMonth 
{

    /** TableName=UY_PR_SchTaskMonth */
    public static final String Table_Name = "UY_PR_SchTaskMonth";

    /** AD_Table_ID=1000735 */
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

    /** Column name DayNo */
    public static final String COLUMNNAME_DayNo = "DayNo";

	/** Set DayNo.
	  * DayNo
	  */
	public void setDayNo (int DayNo);

	/** Get DayNo.
	  * DayNo
	  */
	public int getDayNo();

    /** Column name DayType */
    public static final String COLUMNNAME_DayType = "DayType";

	/** Set DayType	  */
	public void setDayType (String DayType);

	/** Get DayType	  */
	public String getDayType();

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

    /** Column name TaskHour */
    public static final String COLUMNNAME_TaskHour = "TaskHour";

	/** Set TaskHour.
	  * TaskHour
	  */
	public void setTaskHour (Timestamp TaskHour);

	/** Get TaskHour.
	  * TaskHour
	  */
	public Timestamp getTaskHour();

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

    /** Column name UY_PR_SchTask_ID */
    public static final String COLUMNNAME_UY_PR_SchTask_ID = "UY_PR_SchTask_ID";

	/** Set UY_PR_SchTask	  */
	public void setUY_PR_SchTask_ID (int UY_PR_SchTask_ID);

	/** Get UY_PR_SchTask	  */
	public int getUY_PR_SchTask_ID();

	public I_UY_PR_SchTask getUY_PR_SchTask() throws RuntimeException;

    /** Column name UY_PR_SchTaskMonth_ID */
    public static final String COLUMNNAME_UY_PR_SchTaskMonth_ID = "UY_PR_SchTaskMonth_ID";

	/** Set UY_PR_SchTaskMonth	  */
	public void setUY_PR_SchTaskMonth_ID (int UY_PR_SchTaskMonth_ID);

	/** Get UY_PR_SchTaskMonth	  */
	public int getUY_PR_SchTaskMonth_ID();
}
