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

/** Generated Interface for UY_CheckBookType
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_CheckBookType 
{

    /** TableName=UY_CheckBookType */
    public static final String Table_Name = "UY_CheckBookType";

    /** AD_Table_ID=1000220 */
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

	public I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name Counter */
    public static final String COLUMNNAME_Counter = "Counter";

	/** Set Counter.
	  * Count Value
	  */
	public void setCounter (int Counter);

	/** Get Counter.
	  * Count Value
	  */
	public int getCounter();

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

    /** Column name DocumentNoEnd */
    public static final String COLUMNNAME_DocumentNoEnd = "DocumentNoEnd";

	/** Set End document number.
	  * End document number
	  */
	public void setDocumentNoEnd (int DocumentNoEnd);

	/** Get End document number.
	  * End document number
	  */
	public int getDocumentNoEnd();

    /** Column name DocumentNoStart */
    public static final String COLUMNNAME_DocumentNoStart = "DocumentNoStart";

	/** Set Start document number.
	  * Start document number
	  */
	public void setDocumentNoStart (int DocumentNoStart);

	/** Get Start document number.
	  * Start document number
	  */
	public int getDocumentNoStart();

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

    /** Column name UY_CheckBookControl_ID */
    public static final String COLUMNNAME_UY_CheckBookControl_ID = "UY_CheckBookControl_ID";

	/** Set UY_CheckBookControl	  */
	public void setUY_CheckBookControl_ID (int UY_CheckBookControl_ID);

	/** Get UY_CheckBookControl	  */
	public int getUY_CheckBookControl_ID();

	public I_UY_CheckBookControl getUY_CheckBookControl() throws RuntimeException;

    /** Column name UY_CheckBookType_ID */
    public static final String COLUMNNAME_UY_CheckBookType_ID = "UY_CheckBookType_ID";

	/** Set UY_CheckBookType	  */
	public void setUY_CheckBookType_ID (int UY_CheckBookType_ID);

	/** Get UY_CheckBookType	  */
	public int getUY_CheckBookType_ID();
}
