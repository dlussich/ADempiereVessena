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

/** Generated Interface for UY_TR_LoadMsgLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_LoadMsgLine 
{

    /** TableName=UY_TR_LoadMsgLine */
    public static final String Table_Name = "UY_TR_LoadMsgLine";

    /** AD_Table_ID=1000863 */
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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name IsSent */
    public static final String COLUMNNAME_IsSent = "IsSent";

	/** Set IsSent	  */
	public void setIsSent (boolean IsSent);

	/** Get IsSent	  */
	public boolean isSent();

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

    /** Column name UY_TR_Crt_ID */
    public static final String COLUMNNAME_UY_TR_Crt_ID = "UY_TR_Crt_ID";

	/** Set UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID);

	/** Get UY_TR_Crt	  */
	public int getUY_TR_Crt_ID();

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException;

    /** Column name UY_TR_LoadMsg_ID */
    public static final String COLUMNNAME_UY_TR_LoadMsg_ID = "UY_TR_LoadMsg_ID";

	/** Set UY_TR_LoadMsg	  */
	public void setUY_TR_LoadMsg_ID (int UY_TR_LoadMsg_ID);

	/** Get UY_TR_LoadMsg	  */
	public int getUY_TR_LoadMsg_ID();

	public I_UY_TR_LoadMsg getUY_TR_LoadMsg() throws RuntimeException;

    /** Column name UY_TR_LoadMsgLine_ID */
    public static final String COLUMNNAME_UY_TR_LoadMsgLine_ID = "UY_TR_LoadMsgLine_ID";

	/** Set UY_TR_LoadMsgLine	  */
	public void setUY_TR_LoadMsgLine_ID (int UY_TR_LoadMsgLine_ID);

	/** Get UY_TR_LoadMsgLine	  */
	public int getUY_TR_LoadMsgLine_ID();

    /** Column name UY_TR_TransOrder_ID */
    public static final String COLUMNNAME_UY_TR_TransOrder_ID = "UY_TR_TransOrder_ID";

	/** Set UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID);

	/** Get UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID();

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException;

    /** Column name UY_TR_TransOrderLine_ID */
    public static final String COLUMNNAME_UY_TR_TransOrderLine_ID = "UY_TR_TransOrderLine_ID";

	/** Set UY_TR_TransOrderLine	  */
	public void setUY_TR_TransOrderLine_ID (int UY_TR_TransOrderLine_ID);

	/** Get UY_TR_TransOrderLine	  */
	public int getUY_TR_TransOrderLine_ID();

	public I_UY_TR_TransOrderLine getUY_TR_TransOrderLine() throws RuntimeException;

    /** Column name UY_TR_Trip_ID */
    public static final String COLUMNNAME_UY_TR_Trip_ID = "UY_TR_Trip_ID";

	/** Set UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID);

	/** Get UY_TR_Trip	  */
	public int getUY_TR_Trip_ID();

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException;
}
