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

/** Generated Interface for UY_MB_StoreLORet
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_MB_StoreLORet 
{

    /** TableName=UY_MB_StoreLORet */
    public static final String Table_Name = "UY_MB_StoreLORet";

    /** AD_Table_ID=1001016 */
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

    /** Column name amtusd */
    public static final String COLUMNNAME_amtusd = "amtusd";

	/** Set amtusd	  */
	public void setamtusd (BigDecimal amtusd);

	/** Get amtusd	  */
	public BigDecimal getamtusd();

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name dev_WayID */
    public static final String COLUMNNAME_dev_WayID = "dev_WayID";

	/** Set dev_WayID1	  */
	public void setdev_WayID (int dev_WayID);

	/** Get dev_WayID1	  */
	public int getdev_WayID();

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

    /** Column name UY_MB_StoreLoadOrder_ID */
    public static final String COLUMNNAME_UY_MB_StoreLoadOrder_ID = "UY_MB_StoreLoadOrder_ID";

	/** Set UY_MB_StoreLoadOrder	  */
	public void setUY_MB_StoreLoadOrder_ID (int UY_MB_StoreLoadOrder_ID);

	/** Get UY_MB_StoreLoadOrder	  */
	public int getUY_MB_StoreLoadOrder_ID();

	public I_UY_MB_StoreLoadOrder getUY_MB_StoreLoadOrder() throws RuntimeException;

    /** Column name UY_MB_StoreLoadOrderWay_ID */
    public static final String COLUMNNAME_UY_MB_StoreLoadOrderWay_ID = "UY_MB_StoreLoadOrderWay_ID";

	/** Set UY_MB_StoreLoadOrderWay	  */
	public void setUY_MB_StoreLoadOrderWay_ID (int UY_MB_StoreLoadOrderWay_ID);

	/** Get UY_MB_StoreLoadOrderWay	  */
	public int getUY_MB_StoreLoadOrderWay_ID();

	public I_UY_MB_StoreLoadOrderWay getUY_MB_StoreLoadOrderWay() throws RuntimeException;

    /** Column name UY_MB_StoreLORet_ID */
    public static final String COLUMNNAME_UY_MB_StoreLORet_ID = "UY_MB_StoreLORet_ID";

	/** Set UY_MB_StoreLORet	  */
	public void setUY_MB_StoreLORet_ID (int UY_MB_StoreLORet_ID);

	/** Get UY_MB_StoreLORet	  */
	public int getUY_MB_StoreLORet_ID();
}
