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

/** Generated Interface for UY_TT_Seal
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_Seal 
{

    /** TableName=UY_TT_Seal */
    public static final String Table_Name = "UY_TT_Seal";

    /** AD_Table_ID=1000572 */
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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

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

    /** Column name IsComplete */
    public static final String COLUMNNAME_IsComplete = "IsComplete";

	/** Set Complete.
	  * It is complete
	  */
	public void setIsComplete (boolean IsComplete);

	/** Get Complete.
	  * It is complete
	  */
	public boolean isComplete();

    /** Column name IsConfirmed */
    public static final String COLUMNNAME_IsConfirmed = "IsConfirmed";

	/** Set Confirmed.
	  * Assignment is confirmed
	  */
	public void setIsConfirmed (boolean IsConfirmed);

	/** Get Confirmed.
	  * Assignment is confirmed
	  */
	public boolean isConfirmed();

    /** Column name IsOwn */
    public static final String COLUMNNAME_IsOwn = "IsOwn";

	/** Set IsOwn	  */
	public void setIsOwn (boolean IsOwn);

	/** Get IsOwn	  */
	public boolean isOwn();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name QtyBook */
    public static final String COLUMNNAME_QtyBook = "QtyBook";

	/** Set Quantity book.
	  * Book Quantity
	  */
	public void setQtyBook (int QtyBook);

	/** Get Quantity book.
	  * Book Quantity
	  */
	public int getQtyBook();

    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/** Set Quantity count.
	  * Counted Quantity
	  */
	public void setQtyCount (int QtyCount);

	/** Get Quantity count.
	  * Counted Quantity
	  */
	public int getQtyCount();

    /** Column name SealCountType */
    public static final String COLUMNNAME_SealCountType = "SealCountType";

	/** Set SealCountType	  */
	public void setSealCountType (String SealCountType);

	/** Get SealCountType	  */
	public String getSealCountType();

    /** Column name SealStatus */
    public static final String COLUMNNAME_SealStatus = "SealStatus";

	/** Set SealStatus	  */
	public void setSealStatus (String SealStatus);

	/** Get SealStatus	  */
	public String getSealStatus();

    /** Column name SealType */
    public static final String COLUMNNAME_SealType = "SealType";

	/** Set SealType	  */
	public void setSealType (String SealType);

	/** Get SealType	  */
	public String getSealType();

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

    /** Column name UY_DeliveryPoint_ID_To */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID_To = "UY_DeliveryPoint_ID_To";

	/** Set UY_DeliveryPoint_ID_To	  */
	public void setUY_DeliveryPoint_ID_To (int UY_DeliveryPoint_ID_To);

	/** Get UY_DeliveryPoint_ID_To	  */
	public int getUY_DeliveryPoint_ID_To();

    /** Column name UY_TT_Delivery_ID */
    public static final String COLUMNNAME_UY_TT_Delivery_ID = "UY_TT_Delivery_ID";

	/** Set UY_TT_Delivery	  */
	public void setUY_TT_Delivery_ID (int UY_TT_Delivery_ID);

	/** Get UY_TT_Delivery	  */
	public int getUY_TT_Delivery_ID();

	public I_UY_TT_Delivery getUY_TT_Delivery() throws RuntimeException;

    /** Column name UY_TT_Seal_ID */
    public static final String COLUMNNAME_UY_TT_Seal_ID = "UY_TT_Seal_ID";

	/** Set UY_TT_Seal	  */
	public void setUY_TT_Seal_ID (int UY_TT_Seal_ID);

	/** Get UY_TT_Seal	  */
	public int getUY_TT_Seal_ID();

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
