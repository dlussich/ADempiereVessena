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

/** Generated Interface for UY_Credit_Filter
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_Credit_Filter 
{

    /** TableName=UY_Credit_Filter */
    public static final String Table_Name = "UY_Credit_Filter";

    /** AD_Table_ID=1000125 */
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

	public I_AD_User getAD_User() throws RuntimeException;

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

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public I_C_Order getC_Order() throws RuntimeException;

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

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

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

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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

    /** Column name UY_CanalVentas_ID */
    public static final String COLUMNNAME_UY_CanalVentas_ID = "UY_CanalVentas_ID";

	/** Set UY_CanalVentas	  */
	public void setUY_CanalVentas_ID (int UY_CanalVentas_ID);

	/** Get UY_CanalVentas	  */
	public int getUY_CanalVentas_ID();

    /** Column name UY_Credit_Filter_ID */
    public static final String COLUMNNAME_UY_Credit_Filter_ID = "UY_Credit_Filter_ID";

	/** Set UY_Credit_Filter	  */
	public void setUY_Credit_Filter_ID (int UY_Credit_Filter_ID);

	/** Get UY_Credit_Filter	  */
	public int getUY_Credit_Filter_ID();

    /** Column name uy_credit_status */
    public static final String COLUMNNAME_uy_credit_status = "uy_credit_status";

	/** Set uy_credit_status	  */
	public void setuy_credit_status (String uy_credit_status);

	/** Get uy_credit_status	  */
	public String getuy_credit_status();

    /** Column name uy_dateordered_from */
    public static final String COLUMNNAME_uy_dateordered_from = "uy_dateordered_from";

	/** Set uy_dateordered_from	  */
	public void setuy_dateordered_from (Timestamp uy_dateordered_from);

	/** Get uy_dateordered_from	  */
	public Timestamp getuy_dateordered_from();

    /** Column name uy_dateordered_to */
    public static final String COLUMNNAME_uy_dateordered_to = "uy_dateordered_to";

	/** Set uy_dateordered_to	  */
	public void setuy_dateordered_to (Timestamp uy_dateordered_to);

	/** Get uy_dateordered_to	  */
	public Timestamp getuy_dateordered_to();

    /** Column name uy_datepromised_from */
    public static final String COLUMNNAME_uy_datepromised_from = "uy_datepromised_from";

	/** Set uy_datepromised_from	  */
	public void setuy_datepromised_from (Timestamp uy_datepromised_from);

	/** Get uy_datepromised_from	  */
	public Timestamp getuy_datepromised_from();

    /** Column name uy_datepromised_to */
    public static final String COLUMNNAME_uy_datepromised_to = "uy_datepromised_to";

	/** Set uy_datepromised_to	  */
	public void setuy_datepromised_to (Timestamp uy_datepromised_to);

	/** Get uy_datepromised_to	  */
	public Timestamp getuy_datepromised_to();

    /** Column name uy_nrotrx */
    public static final String COLUMNNAME_uy_nrotrx = "uy_nrotrx";

	/** Set uy_nrotrx	  */
	public void setuy_nrotrx (int uy_nrotrx);

	/** Get uy_nrotrx	  */
	public int getuy_nrotrx();
}
