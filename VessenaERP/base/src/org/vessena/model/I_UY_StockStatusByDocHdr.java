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

/** Generated Interface for UY_StockStatusByDocHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_StockStatusByDocHdr 
{

    /** TableName=UY_StockStatusByDocHdr */
    public static final String Table_Name = "UY_StockStatusByDocHdr";

    /** AD_Table_ID=1000183 */
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

    /** Column name c_doctype_affected_id */
    public static final String COLUMNNAME_c_doctype_affected_id = "c_doctype_affected_id";

	/** Set c_doctype_affected_id	  */
	public void setc_doctype_affected_id (BigDecimal c_doctype_affected_id);

	/** Get c_doctype_affected_id	  */
	public BigDecimal getc_doctype_affected_id();

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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name ConsumeTypeAdd */
    public static final String COLUMNNAME_ConsumeTypeAdd = "ConsumeTypeAdd";

	/** Set ConsumeTypeAdd.
	  * ConsumeTypeAdd
	  */
	public void setConsumeTypeAdd (String ConsumeTypeAdd);

	/** Get ConsumeTypeAdd.
	  * ConsumeTypeAdd
	  */
	public String getConsumeTypeAdd();

    /** Column name ConsumeTypeSub */
    public static final String COLUMNNAME_ConsumeTypeSub = "ConsumeTypeSub";

	/** Set ConsumeTypeSub	  */
	public void setConsumeTypeSub (String ConsumeTypeSub);

	/** Get ConsumeTypeSub	  */
	public String getConsumeTypeSub();

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

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

    /** Column name MovementType */
    public static final String COLUMNNAME_MovementType = "MovementType";

	/** Set Movement Type.
	  * Method of moving the inventory
	  */
	public void setMovementType (String MovementType);

	/** Get Movement Type.
	  * Method of moving the inventory
	  */
	public String getMovementType();

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

    /** Column name UY_StockStatusByDocHdr_ID */
    public static final String COLUMNNAME_UY_StockStatusByDocHdr_ID = "UY_StockStatusByDocHdr_ID";

	/** Set UY_StockStatusByDocHdr	  */
	public void setUY_StockStatusByDocHdr_ID (int UY_StockStatusByDocHdr_ID);

	/** Get UY_StockStatusByDocHdr	  */
	public int getUY_StockStatusByDocHdr_ID();

    /** Column name UY_StockStatusListHdr_ID */
    public static final String COLUMNNAME_UY_StockStatusListHdr_ID = "UY_StockStatusListHdr_ID";

	/** Set UY_StockStatusListHdr	  */
	public void setUY_StockStatusListHdr_ID (int UY_StockStatusListHdr_ID);

	/** Get UY_StockStatusListHdr	  */
	public int getUY_StockStatusListHdr_ID();

	public I_UY_StockStatusListHdr getUY_StockStatusListHdr() throws RuntimeException;
}
