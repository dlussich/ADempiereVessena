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

/** Generated Interface for UY_Route
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Route 
{

    /** TableName=UY_Route */
    public static final String Table_Name = "UY_Route";

    /** AD_Table_ID=1000592 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

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

    /** Column name OnFriday */
    public static final String COLUMNNAME_OnFriday = "OnFriday";

	/** Set Friday.
	  * Available on Fridays
	  */
	public void setOnFriday (boolean OnFriday);

	/** Get Friday.
	  * Available on Fridays
	  */
	public boolean isOnFriday();

    /** Column name OnMonday */
    public static final String COLUMNNAME_OnMonday = "OnMonday";

	/** Set Monday.
	  * Available on Mondays
	  */
	public void setOnMonday (boolean OnMonday);

	/** Get Monday.
	  * Available on Mondays
	  */
	public boolean isOnMonday();

    /** Column name OnSaturday */
    public static final String COLUMNNAME_OnSaturday = "OnSaturday";

	/** Set Saturday.
	  * Available on Saturday
	  */
	public void setOnSaturday (boolean OnSaturday);

	/** Get Saturday.
	  * Available on Saturday
	  */
	public boolean isOnSaturday();

    /** Column name OnSunday */
    public static final String COLUMNNAME_OnSunday = "OnSunday";

	/** Set Sunday.
	  * Available on Sundays
	  */
	public void setOnSunday (boolean OnSunday);

	/** Get Sunday.
	  * Available on Sundays
	  */
	public boolean isOnSunday();

    /** Column name OnThursday */
    public static final String COLUMNNAME_OnThursday = "OnThursday";

	/** Set Thursday.
	  * Available on Thursdays
	  */
	public void setOnThursday (boolean OnThursday);

	/** Get Thursday.
	  * Available on Thursdays
	  */
	public boolean isOnThursday();

    /** Column name OnTuesday */
    public static final String COLUMNNAME_OnTuesday = "OnTuesday";

	/** Set Tuesday.
	  * Available on Tuesdays
	  */
	public void setOnTuesday (boolean OnTuesday);

	/** Get Tuesday.
	  * Available on Tuesdays
	  */
	public boolean isOnTuesday();

    /** Column name OnWednesday */
    public static final String COLUMNNAME_OnWednesday = "OnWednesday";

	/** Set Wednesday.
	  * Available on Wednesdays
	  */
	public void setOnWednesday (boolean OnWednesday);

	/** Get Wednesday.
	  * Available on Wednesdays
	  */
	public boolean isOnWednesday();

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

    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/** Set URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL);

	/** Get URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL();

    /** Column name UY_Route_ID */
    public static final String COLUMNNAME_UY_Route_ID = "UY_Route_ID";

	/** Set UY_Route	  */
	public void setUY_Route_ID (int UY_Route_ID);

	/** Get UY_Route	  */
	public int getUY_Route_ID();

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
