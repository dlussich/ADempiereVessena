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

/** Generated Interface for UY_TT_ActionCard
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_ActionCard 
{

    /** TableName=UY_TT_ActionCard */
    public static final String Table_Name = "UY_TT_ActionCard";

    /** AD_Table_ID=1000645 */
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

    /** Column name IsExecuted */
    public static final String COLUMNNAME_IsExecuted = "IsExecuted";

	/** Set IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted);

	/** Get IsExecuted	  */
	public boolean isExecuted();

    /** Column name Link_ID */
    public static final String COLUMNNAME_Link_ID = "Link_ID";

	/** Set Link_ID	  */
	public void setLink_ID (int Link_ID);

	/** Get Link_ID	  */
	public int getLink_ID();

    /** Column name TTActionType */
    public static final String COLUMNNAME_TTActionType = "TTActionType";

	/** Set TTActionType	  */
	public void setTTActionType (String TTActionType);

	/** Get TTActionType	  */
	public String getTTActionType();

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

    /** Column name UY_DeliveryPoint_ID_To */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID_To = "UY_DeliveryPoint_ID_To";

	/** Set UY_DeliveryPoint_ID_To	  */
	public void setUY_DeliveryPoint_ID_To (int UY_DeliveryPoint_ID_To);

	/** Get UY_DeliveryPoint_ID_To	  */
	public int getUY_DeliveryPoint_ID_To();

    /** Column name UY_TT_ActionCard_ID */
    public static final String COLUMNNAME_UY_TT_ActionCard_ID = "UY_TT_ActionCard_ID";

	/** Set UY_TT_ActionCard	  */
	public void setUY_TT_ActionCard_ID (int UY_TT_ActionCard_ID);

	/** Get UY_TT_ActionCard	  */
	public int getUY_TT_ActionCard_ID();

    /** Column name UY_TT_Action_ID */
    public static final String COLUMNNAME_UY_TT_Action_ID = "UY_TT_Action_ID";

	/** Set UY_TT_Action	  */
	public void setUY_TT_Action_ID (int UY_TT_Action_ID);

	/** Get UY_TT_Action	  */
	public int getUY_TT_Action_ID();

	public I_UY_TT_Action getUY_TT_Action() throws RuntimeException;

    /** Column name UY_TT_Mensaje_ID */
    public static final String COLUMNNAME_UY_TT_Mensaje_ID = "UY_TT_Mensaje_ID";

	/** Set UY_TT_Mensaje	  */
	public void setUY_TT_Mensaje_ID (int UY_TT_Mensaje_ID);

	/** Get UY_TT_Mensaje	  */
	public int getUY_TT_Mensaje_ID();

	public I_UY_TT_Mensaje getUY_TT_Mensaje() throws RuntimeException;

    /** Column name UY_TT_SMS_ID */
    public static final String COLUMNNAME_UY_TT_SMS_ID = "UY_TT_SMS_ID";

	/** Set UY_TT_SMS	  */
	public void setUY_TT_SMS_ID (int UY_TT_SMS_ID);

	/** Get UY_TT_SMS	  */
	public int getUY_TT_SMS_ID();

	public I_UY_TT_SMS getUY_TT_SMS() throws RuntimeException;
}
