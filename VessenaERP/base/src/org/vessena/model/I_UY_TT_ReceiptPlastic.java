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

/** Generated Interface for UY_TT_ReceiptPlastic
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_ReceiptPlastic 
{

    /** TableName=UY_TT_ReceiptPlastic */
    public static final String Table_Name = "UY_TT_ReceiptPlastic";

    /** AD_Table_ID=1000607 */
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

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

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

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (String DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public String getDueDate();

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

    /** Column name IsTitular */
    public static final String COLUMNNAME_IsTitular = "IsTitular";

	/** Set IsTitular	  */
	public void setIsTitular (boolean IsTitular);

	/** Get IsTitular	  */
	public boolean isTitular();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name TipoSocio */
    public static final String COLUMNNAME_TipoSocio = "TipoSocio";

	/** Set TipoSocio	  */
	public void setTipoSocio (int TipoSocio);

	/** Get TipoSocio	  */
	public int getTipoSocio();

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

    /** Column name UY_TT_Card_ID */
    public static final String COLUMNNAME_UY_TT_Card_ID = "UY_TT_Card_ID";

	/** Set UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID);

	/** Get UY_TT_Card	  */
	public int getUY_TT_Card_ID();

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException;

    /** Column name UY_TT_CardPlastic_ID */
    public static final String COLUMNNAME_UY_TT_CardPlastic_ID = "UY_TT_CardPlastic_ID";

	/** Set UY_TT_CardPlastic	  */
	public void setUY_TT_CardPlastic_ID (int UY_TT_CardPlastic_ID);

	/** Get UY_TT_CardPlastic	  */
	public int getUY_TT_CardPlastic_ID();

	public I_UY_TT_CardPlastic getUY_TT_CardPlastic() throws RuntimeException;

    /** Column name UY_TT_Receipt_ID */
    public static final String COLUMNNAME_UY_TT_Receipt_ID = "UY_TT_Receipt_ID";

	/** Set UY_TT_Receipt	  */
	public void setUY_TT_Receipt_ID (int UY_TT_Receipt_ID);

	/** Get UY_TT_Receipt	  */
	public int getUY_TT_Receipt_ID();

	public I_UY_TT_Receipt getUY_TT_Receipt() throws RuntimeException;

    /** Column name UY_TT_ReceiptPlastic_ID */
    public static final String COLUMNNAME_UY_TT_ReceiptPlastic_ID = "UY_TT_ReceiptPlastic_ID";

	/** Set UY_TT_ReceiptPlastic	  */
	public void setUY_TT_ReceiptPlastic_ID (int UY_TT_ReceiptPlastic_ID);

	/** Get UY_TT_ReceiptPlastic	  */
	public int getUY_TT_ReceiptPlastic_ID();

    /** Column name UY_TT_ReceiptScan_ID */
    public static final String COLUMNNAME_UY_TT_ReceiptScan_ID = "UY_TT_ReceiptScan_ID";

	/** Set UY_TT_ReceiptScan	  */
	public void setUY_TT_ReceiptScan_ID (int UY_TT_ReceiptScan_ID);

	/** Get UY_TT_ReceiptScan	  */
	public int getUY_TT_ReceiptScan_ID();

	public I_UY_TT_ReceiptScan getUY_TT_ReceiptScan() throws RuntimeException;

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
