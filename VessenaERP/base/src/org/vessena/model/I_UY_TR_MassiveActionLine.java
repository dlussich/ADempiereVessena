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

/** Generated Interface for UY_TR_MassiveActionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_MassiveActionLine 
{

    /** TableName=UY_TR_MassiveActionLine */
    public static final String Table_Name = "UY_TR_MassiveActionLine";

    /** AD_Table_ID=1000882 */
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

    /** Column name C_BPartner_ID_From */
    public static final String COLUMNNAME_C_BPartner_ID_From = "C_BPartner_ID_From";

	/** Set C_BPartner_ID_From	  */
	public void setC_BPartner_ID_From (int C_BPartner_ID_From);

	/** Get C_BPartner_ID_From	  */
	public int getC_BPartner_ID_From();

    /** Column name C_BPartner_ID_To */
    public static final String COLUMNNAME_C_BPartner_ID_To = "C_BPartner_ID_To";

	/** Set C_BPartner_ID_To	  */
	public void setC_BPartner_ID_To (int C_BPartner_ID_To);

	/** Get C_BPartner_ID_To	  */
	public int getC_BPartner_ID_To();

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

    /** Column name ImageNo */
    public static final String COLUMNNAME_ImageNo = "ImageNo";

	/** Set ImageNo	  */
	public void setImageNo (String ImageNo);

	/** Get ImageNo	  */
	public String getImageNo();

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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name NroDNA */
    public static final String COLUMNNAME_NroDNA = "NroDNA";

	/** Set NroDNA	  */
	public void setNroDNA (String NroDNA);

	/** Get NroDNA	  */
	public String getNroDNA();

    /** Column name Tractor_ID */
    public static final String COLUMNNAME_Tractor_ID = "Tractor_ID";

	/** Set Tractor_ID	  */
	public void setTractor_ID (int Tractor_ID);

	/** Get Tractor_ID	  */
	public int getTractor_ID();

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

    /** Column name UY_Ciudad_ID */
    public static final String COLUMNNAME_UY_Ciudad_ID = "UY_Ciudad_ID";

	/** Set UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID);

	/** Get UY_Ciudad	  */
	public int getUY_Ciudad_ID();

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException;

    /** Column name UY_Ciudad_ID_1 */
    public static final String COLUMNNAME_UY_Ciudad_ID_1 = "UY_Ciudad_ID_1";

	/** Set UY_Ciudad_ID_1	  */
	public void setUY_Ciudad_ID_1 (int UY_Ciudad_ID_1);

	/** Get UY_Ciudad_ID_1	  */
	public int getUY_Ciudad_ID_1();

    /** Column name UY_TR_Crt_ID */
    public static final String COLUMNNAME_UY_TR_Crt_ID = "UY_TR_Crt_ID";

	/** Set UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID);

	/** Get UY_TR_Crt	  */
	public int getUY_TR_Crt_ID();

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException;

    /** Column name UY_TR_MassiveAction_ID */
    public static final String COLUMNNAME_UY_TR_MassiveAction_ID = "UY_TR_MassiveAction_ID";

	/** Set UY_TR_MassiveAction	  */
	public void setUY_TR_MassiveAction_ID (int UY_TR_MassiveAction_ID);

	/** Get UY_TR_MassiveAction	  */
	public int getUY_TR_MassiveAction_ID();

	public I_UY_TR_MassiveAction getUY_TR_MassiveAction() throws RuntimeException;

    /** Column name UY_TR_MassiveActionLine_ID */
    public static final String COLUMNNAME_UY_TR_MassiveActionLine_ID = "UY_TR_MassiveActionLine_ID";

	/** Set UY_TR_MassiveActionLine	  */
	public void setUY_TR_MassiveActionLine_ID (int UY_TR_MassiveActionLine_ID);

	/** Get UY_TR_MassiveActionLine	  */
	public int getUY_TR_MassiveActionLine_ID();

    /** Column name UY_TR_Mic_ID */
    public static final String COLUMNNAME_UY_TR_Mic_ID = "UY_TR_Mic_ID";

	/** Set UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID);

	/** Get UY_TR_Mic	  */
	public int getUY_TR_Mic_ID();

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException;

    /** Column name UY_TR_TransOrder_ID */
    public static final String COLUMNNAME_UY_TR_TransOrder_ID = "UY_TR_TransOrder_ID";

	/** Set UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID);

	/** Get UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID();

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException;
}
