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

/** Generated Interface for UY_BG_Broker
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_BG_Broker 
{

    /** TableName=UY_BG_Broker */
    public static final String Table_Name = "UY_BG_Broker";

    /** AD_Table_ID=1000944 */
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

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name FirstName */
    public static final String COLUMNNAME_FirstName = "FirstName";

	/** Set FirstName	  */
	public void setFirstName (String FirstName);

	/** Get FirstName	  */
	public String getFirstName();

    /** Column name FirstSurname */
    public static final String COLUMNNAME_FirstSurname = "FirstSurname";

	/** Set FirstSurname	  */
	public void setFirstSurname (String FirstSurname);

	/** Get FirstSurname	  */
	public String getFirstSurname();

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

    /** Column name IsSmartPhone1 */
    public static final String COLUMNNAME_IsSmartPhone1 = "IsSmartPhone1";

	/** Set IsSmartPhone1	  */
	public void setIsSmartPhone1 (boolean IsSmartPhone1);

	/** Get IsSmartPhone1	  */
	public boolean isSmartPhone1();

    /** Column name IsSmartPhone2 */
    public static final String COLUMNNAME_IsSmartPhone2 = "IsSmartPhone2";

	/** Set IsSmartPhone2	  */
	public void setIsSmartPhone2 (boolean IsSmartPhone2);

	/** Get IsSmartPhone2	  */
	public boolean isSmartPhone2();

    /** Column name Mobile1 */
    public static final String COLUMNNAME_Mobile1 = "Mobile1";

	/** Set Mobile1	  */
	public void setMobile1 (String Mobile1);

	/** Get Mobile1	  */
	public String getMobile1();

    /** Column name Mobile2 */
    public static final String COLUMNNAME_Mobile2 = "Mobile2";

	/** Set Mobile2	  */
	public void setMobile2 (String Mobile2);

	/** Get Mobile2	  */
	public String getMobile2();

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

    /** Column name SecondName */
    public static final String COLUMNNAME_SecondName = "SecondName";

	/** Set SecondName	  */
	public void setSecondName (String SecondName);

	/** Get SecondName	  */
	public String getSecondName();

    /** Column name SecondSurname */
    public static final String COLUMNNAME_SecondSurname = "SecondSurname";

	/** Set SecondSurname	  */
	public void setSecondSurname (String SecondSurname);

	/** Get SecondSurname	  */
	public String getSecondSurname();

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

    /** Column name UY_BG_Broker_ID */
    public static final String COLUMNNAME_UY_BG_Broker_ID = "UY_BG_Broker_ID";

	/** Set UY_BG_Broker	  */
	public void setUY_BG_Broker_ID (int UY_BG_Broker_ID);

	/** Get UY_BG_Broker	  */
	public int getUY_BG_Broker_ID();

    /** Column name UY_BG_Bursa_ID */
    public static final String COLUMNNAME_UY_BG_Bursa_ID = "UY_BG_Bursa_ID";

	/** Set UY_BG_Bursa	  */
	public void setUY_BG_Bursa_ID (int UY_BG_Bursa_ID);

	/** Get UY_BG_Bursa	  */
	public int getUY_BG_Bursa_ID();

	public I_UY_BG_Bursa getUY_BG_Bursa() throws RuntimeException;

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
