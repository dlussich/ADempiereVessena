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

/** Generated Interface for UY_TT_ActionCardSMS
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_TT_ActionCardSMS 
{

    /** TableName=UY_TT_ActionCardSMS */
    public static final String Table_Name = "UY_TT_ActionCardSMS";

    /** AD_Table_ID=1000646 */
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

    /** Column name Mobile */
    public static final String COLUMNNAME_Mobile = "Mobile";

	/** Set Mobile	  */
	public void setMobile (String Mobile);

	/** Get Mobile	  */
	public String getMobile();

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

    /** Column name UY_TT_ActionCard_ID */
    public static final String COLUMNNAME_UY_TT_ActionCard_ID = "UY_TT_ActionCard_ID";

	/** Set UY_TT_ActionCard	  */
	public void setUY_TT_ActionCard_ID (int UY_TT_ActionCard_ID);

	/** Get UY_TT_ActionCard	  */
	public int getUY_TT_ActionCard_ID();

	public I_UY_TT_ActionCard getUY_TT_ActionCard() throws RuntimeException;

    /** Column name UY_TT_ActionCardSMS_ID */
    public static final String COLUMNNAME_UY_TT_ActionCardSMS_ID = "UY_TT_ActionCardSMS_ID";

	/** Set UY_TT_ActionCardSMS	  */
	public void setUY_TT_ActionCardSMS_ID (int UY_TT_ActionCardSMS_ID);

	/** Get UY_TT_ActionCardSMS	  */
	public int getUY_TT_ActionCardSMS_ID();

    /** Column name UY_TT_Card_ID */
    public static final String COLUMNNAME_UY_TT_Card_ID = "UY_TT_Card_ID";

	/** Set UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID);

	/** Get UY_TT_Card	  */
	public int getUY_TT_Card_ID();

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException;

    /** Column name UY_TT_SMS_ID */
    public static final String COLUMNNAME_UY_TT_SMS_ID = "UY_TT_SMS_ID";

	/** Set UY_TT_SMS	  */
	public void setUY_TT_SMS_ID (int UY_TT_SMS_ID);

	/** Get UY_TT_SMS	  */
	public int getUY_TT_SMS_ID();

	public I_UY_TT_SMS getUY_TT_SMS() throws RuntimeException;
}
