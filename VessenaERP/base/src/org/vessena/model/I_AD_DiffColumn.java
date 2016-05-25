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

/** Generated Interface for AD_DiffColumn
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_DiffColumn 
{

    /** TableName=AD_DiffColumn */
    public static final String Table_Name = "AD_DiffColumn";

    /** AD_Table_ID=1000181 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Diff_ID */
    public static final String COLUMNNAME_AD_Diff_ID = "AD_Diff_ID";

	/** Set Active Dictionary Difference	  */
	public void setAD_Diff_ID (int AD_Diff_ID);

	/** Get Active Dictionary Difference	  */
	public int getAD_Diff_ID();

	public I_AD_Diff getAD_Diff() throws RuntimeException;

    /** Column name AD_DiffColumn_ID */
    public static final String COLUMNNAME_AD_DiffColumn_ID = "AD_DiffColumn_ID";

	/** Set Application Dictionary Difference Column	  */
	public void setAD_DiffColumn_ID (int AD_DiffColumn_ID);

	/** Get Application Dictionary Difference Column	  */
	public int getAD_DiffColumn_ID();

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

    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

	/** Set DB Column Name.
	  * Name of the column in the database
	  */
	public void setColumnName (String ColumnName);

	/** Get DB Column Name.
	  * Name of the column in the database
	  */
	public String getColumnName();

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

    /** Column name Source */
    public static final String COLUMNNAME_Source = "Source";

	/** Set Source	  */
	public void setSource (String Source);

	/** Get Source	  */
	public String getSource();

    /** Column name Target */
    public static final String COLUMNNAME_Target = "Target";

	/** Set Target	  */
	public void setTarget (String Target);

	/** Get Target	  */
	public String getTarget();

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
}
