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

/** Generated Interface for UY_PriceUpdateLineOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_PriceUpdateLineOrg 
{

    /** TableName=UY_PriceUpdateLineOrg */
    public static final String Table_Name = "UY_PriceUpdateLineOrg";

    /** AD_Table_ID=1001002 */
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

    /** Column name AD_Org_ID_To */
    public static final String COLUMNNAME_AD_Org_ID_To = "AD_Org_ID_To";

	/** Set AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To);

	/** Get AD_Org_ID_To	  */
	public int getAD_Org_ID_To();

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

    /** Column name UY_PriceUpdateLine_ID */
    public static final String COLUMNNAME_UY_PriceUpdateLine_ID = "UY_PriceUpdateLine_ID";

	/** Set UY_PriceUpdateLine	  */
	public void setUY_PriceUpdateLine_ID (int UY_PriceUpdateLine_ID);

	/** Get UY_PriceUpdateLine	  */
	public int getUY_PriceUpdateLine_ID();

	public I_UY_PriceUpdateLine getUY_PriceUpdateLine() throws RuntimeException;

    /** Column name UY_PriceUpdateLineOrg_ID */
    public static final String COLUMNNAME_UY_PriceUpdateLineOrg_ID = "UY_PriceUpdateLineOrg_ID";

	/** Set UY_PriceUpdateLineOrg	  */
	public void setUY_PriceUpdateLineOrg_ID (int UY_PriceUpdateLineOrg_ID);

	/** Get UY_PriceUpdateLineOrg	  */
	public int getUY_PriceUpdateLineOrg_ID();
}
