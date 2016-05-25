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

/** Generated Interface for UY_TR_ConfigCrt
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_ConfigCrt 
{

    /** TableName=UY_TR_ConfigCrt */
    public static final String Table_Name = "UY_TR_ConfigCrt";

    /** AD_Table_ID=1000824 */
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

    /** Column name IsPermit */
    public static final String COLUMNNAME_IsPermit = "IsPermit";

	/** Set IsPermit	  */
	public void setIsPermit (boolean IsPermit);

	/** Get IsPermit	  */
	public boolean isPermit();

    /** Column name IsPesoNeto */
    public static final String COLUMNNAME_IsPesoNeto = "IsPesoNeto";

	/** Set IsPesoNeto	  */
	public void setIsPesoNeto (boolean IsPesoNeto);

	/** Get IsPesoNeto	  */
	public boolean isPesoNeto();

    /** Column name IsValueIncluded */
    public static final String COLUMNNAME_IsValueIncluded = "IsValueIncluded";

	/** Set IsValueIncluded	  */
	public void setIsValueIncluded (boolean IsValueIncluded);

	/** Get IsValueIncluded	  */
	public boolean isValueIncluded();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

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

    /** Column name UY_TR_ConfigCrt_ID */
    public static final String COLUMNNAME_UY_TR_ConfigCrt_ID = "UY_TR_ConfigCrt_ID";

	/** Set UY_TR_ConfigCrt	  */
	public void setUY_TR_ConfigCrt_ID (int UY_TR_ConfigCrt_ID);

	/** Get UY_TR_ConfigCrt	  */
	public int getUY_TR_ConfigCrt_ID();

    /** Column name UY_TR_Config_ID */
    public static final String COLUMNNAME_UY_TR_Config_ID = "UY_TR_Config_ID";

	/** Set UY_TR_Config	  */
	public void setUY_TR_Config_ID (int UY_TR_Config_ID);

	/** Get UY_TR_Config	  */
	public int getUY_TR_Config_ID();

	public I_UY_TR_Config getUY_TR_Config() throws RuntimeException;
}
