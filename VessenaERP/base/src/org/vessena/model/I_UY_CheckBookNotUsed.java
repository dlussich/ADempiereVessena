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

/** Generated Interface for UY_CheckBookNotUsed
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_CheckBookNotUsed 
{

    /** TableName=UY_CheckBookNotUsed */
    public static final String Table_Name = "UY_CheckBookNotUsed";

    /** AD_Table_ID=1000219 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name DocumentNoNotUsed */
    public static final String COLUMNNAME_DocumentNoNotUsed = "DocumentNoNotUsed";

	/** Set DocumentNoNotUsed	  */
	public void setDocumentNoNotUsed (int DocumentNoNotUsed);

	/** Get DocumentNoNotUsed	  */
	public int getDocumentNoNotUsed();

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

    /** Column name UY_CheckBookNotUsed_ID */
    public static final String COLUMNNAME_UY_CheckBookNotUsed_ID = "UY_CheckBookNotUsed_ID";

	/** Set UY_CheckBookNotUsed	  */
	public void setUY_CheckBookNotUsed_ID (int UY_CheckBookNotUsed_ID);

	/** Get UY_CheckBookNotUsed	  */
	public int getUY_CheckBookNotUsed_ID();

    /** Column name UY_MediosPago_ID */
    public static final String COLUMNNAME_UY_MediosPago_ID = "UY_MediosPago_ID";

	/** Set Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID);

	/** Get Medios de Pago	  */
	public int getUY_MediosPago_ID();

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException;
}
