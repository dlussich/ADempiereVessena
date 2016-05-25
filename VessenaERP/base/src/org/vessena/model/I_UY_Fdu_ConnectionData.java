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

/** Generated Interface for UY_Fdu_ConnectionData
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Fdu_ConnectionData 
{

    /** TableName=UY_Fdu_ConnectionData */
    public static final String Table_Name = "UY_Fdu_ConnectionData";

    /** AD_Table_ID=1000385 */
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

    /** Column name database_name */
    public static final String COLUMNNAME_database_name = "database_name";

	/** Set database_name	  */
	public void setdatabase_name (String database_name);

	/** Get database_name	  */
	public String getdatabase_name();

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

    /** Column name password_db */
    public static final String COLUMNNAME_password_db = "password_db";

	/** Set password_db	  */
	public void setpassword_db (String password_db);

	/** Get password_db	  */
	public String getpassword_db();

    /** Column name Schema */
    public static final String COLUMNNAME_Schema = "Schema";

	/** Set Schema.
	  * Schema
	  */
	public void setSchema (String Schema);

	/** Get Schema.
	  * Schema
	  */
	public String getSchema();

    /** Column name Server */
    public static final String COLUMNNAME_Server = "Server";

	/** Set Server.
	  * Server
	  */
	public void setServer (String Server);

	/** Get Server.
	  * Server
	  */
	public String getServer();

    /** Column name server_ip */
    public static final String COLUMNNAME_server_ip = "server_ip";

	/** Set server_ip	  */
	public void setserver_ip (String server_ip);

	/** Get server_ip	  */
	public String getserver_ip();

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

    /** Column name user_db */
    public static final String COLUMNNAME_user_db = "user_db";

	/** Set user_db	  */
	public void setuser_db (String user_db);

	/** Get user_db	  */
	public String getuser_db();

    /** Column name UY_Fdu_ConnectionData_ID */
    public static final String COLUMNNAME_UY_Fdu_ConnectionData_ID = "UY_Fdu_ConnectionData_ID";

	/** Set UY_Fdu_ConnectionData	  */
	public void setUY_Fdu_ConnectionData_ID (int UY_Fdu_ConnectionData_ID);

	/** Get UY_Fdu_ConnectionData	  */
	public int getUY_Fdu_ConnectionData_ID();

    /** Column name UY_FduFile_ID */
    public static final String COLUMNNAME_UY_FduFile_ID = "UY_FduFile_ID";

	/** Set UY_FduFile_ID	  */
	public void setUY_FduFile_ID (int UY_FduFile_ID);

	/** Get UY_FduFile_ID	  */
	public int getUY_FduFile_ID();

	public I_UY_FduFile getUY_FduFile() throws RuntimeException;
}
