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

/** Generated Interface for UY_ResguardoGenDet
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_ResguardoGenDet 
{

    /** TableName=UY_ResguardoGenDet */
    public static final String Table_Name = "UY_ResguardoGenDet";

    /** AD_Table_ID=1000997 */
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

    /** Column name AmtRetention */
    public static final String COLUMNNAME_AmtRetention = "AmtRetention";

	/** Set AmtRetention	  */
	public void setAmtRetention (BigDecimal AmtRetention);

	/** Get AmtRetention	  */
	public BigDecimal getAmtRetention();

    /** Column name amtretentionsource */
    public static final String COLUMNNAME_amtretentionsource = "amtretentionsource";

	/** Set amtretentionsource	  */
	public void setamtretentionsource (BigDecimal amtretentionsource);

	/** Get amtretentionsource	  */
	public BigDecimal getamtretentionsource();

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

    /** Column name UY_ResguardoGenDet_ID */
    public static final String COLUMNNAME_UY_ResguardoGenDet_ID = "UY_ResguardoGenDet_ID";

	/** Set UY_ResguardoGenDet	  */
	public void setUY_ResguardoGenDet_ID (int UY_ResguardoGenDet_ID);

	/** Get UY_ResguardoGenDet	  */
	public int getUY_ResguardoGenDet_ID();

    /** Column name UY_ResguardoGenDoc_ID */
    public static final String COLUMNNAME_UY_ResguardoGenDoc_ID = "UY_ResguardoGenDoc_ID";

	/** Set UY_ResguardoGenDoc	  */
	public void setUY_ResguardoGenDoc_ID (int UY_ResguardoGenDoc_ID);

	/** Get UY_ResguardoGenDoc	  */
	public int getUY_ResguardoGenDoc_ID();

	public I_UY_ResguardoGenDoc getUY_ResguardoGenDoc() throws RuntimeException;

    /** Column name UY_Retention_ID */
    public static final String COLUMNNAME_UY_Retention_ID = "UY_Retention_ID";

	/** Set UY_Retention	  */
	public void setUY_Retention_ID (int UY_Retention_ID);

	/** Get UY_Retention	  */
	public int getUY_Retention_ID();

	public I_UY_Retention getUY_Retention() throws RuntimeException;
}
