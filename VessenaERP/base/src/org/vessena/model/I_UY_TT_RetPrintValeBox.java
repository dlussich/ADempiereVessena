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

/** Generated Interface for UY_TT_RetPrintValeBox
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_RetPrintValeBox 
{

    /** TableName=UY_TT_RetPrintValeBox */
    public static final String Table_Name = "UY_TT_RetPrintValeBox";

    /** AD_Table_ID=1010756 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

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

    /** Column name ImpresionVale */
    public static final String COLUMNNAME_ImpresionVale = "ImpresionVale";

	/** Set ImpresionVale	  */
	public void setImpresionVale (boolean ImpresionVale);

	/** Get ImpresionVale	  */
	public boolean isImpresionVale();

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

    /** Column name IsRetained */
    public static final String COLUMNNAME_IsRetained = "IsRetained";

	/** Set IsRetained	  */
	public void setIsRetained (boolean IsRetained);

	/** Get IsRetained	  */
	public boolean isRetained();

    /** Column name RetainedStatus */
    public static final String COLUMNNAME_RetainedStatus = "RetainedStatus";

	/** Set RetainedStatus	  */
	public void setRetainedStatus (String RetainedStatus);

	/** Get RetainedStatus	  */
	public String getRetainedStatus();

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

    /** Column name UY_DeliveryPoint_ID */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID = "UY_DeliveryPoint_ID";

	/** Set UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID);

	/** Get UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID();

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException;

    /** Column name UY_TT_Box_ID */
    public static final String COLUMNNAME_UY_TT_Box_ID = "UY_TT_Box_ID";

	/** Set UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID);

	/** Get UY_TT_Box	  */
	public int getUY_TT_Box_ID();

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException;

    /** Column name UY_TT_RetPrintValeBox_ID */
    public static final String COLUMNNAME_UY_TT_RetPrintValeBox_ID = "UY_TT_RetPrintValeBox_ID";

	/** Set UY_TT_RetPrintValeBox	  */
	public void setUY_TT_RetPrintValeBox_ID (int UY_TT_RetPrintValeBox_ID);

	/** Get UY_TT_RetPrintValeBox	  */
	public int getUY_TT_RetPrintValeBox_ID();

    /** Column name UY_TT_RetPrintVale_ID */
    public static final String COLUMNNAME_UY_TT_RetPrintVale_ID = "UY_TT_RetPrintVale_ID";

	/** Set UY_TT_RetPrintVale	  */
	public void setUY_TT_RetPrintVale_ID (int UY_TT_RetPrintVale_ID);

	/** Get UY_TT_RetPrintVale	  */
	public int getUY_TT_RetPrintVale_ID();

	public I_UY_TT_RetPrintVale getUY_TT_RetPrintVale() throws RuntimeException;
}
