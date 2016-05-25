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

/** Generated Interface for UY_BG_Customer
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_BG_Customer 
{

    /** TableName=UY_BG_Customer */
    public static final String Table_Name = "UY_BG_Customer";

    /** AD_Table_ID=1000956 */
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

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Address 1.
	  * Address line 1 for this location
	  */
	public void setAddress1 (String Address1);

	/** Get Address 1.
	  * Address line 1 for this location
	  */
	public String getAddress1();

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

    /** Column name C_City_ID */
    public static final String COLUMNNAME_C_City_ID = "C_City_ID";

	/** Set City.
	  * City
	  */
	public void setC_City_ID (int C_City_ID);

	/** Get City.
	  * City
	  */
	public int getC_City_ID();

	public org.compiere.model.I_C_City getC_City() throws RuntimeException;

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

    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Region.
	  * Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifies a geographical Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

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

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

    /** Column name Phone_2 */
    public static final String COLUMNNAME_Phone_2 = "Phone_2";

	/** Set Phone_2	  */
	public void setPhone_2 (String Phone_2);

	/** Get Phone_2	  */
	public String getPhone_2();

    /** Column name RUC */
    public static final String COLUMNNAME_RUC = "RUC";

	/** Set RUC	  */
	public void setRUC (String RUC);

	/** Get RUC	  */
	public String getRUC();

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

    /** Column name UY_BG_Customer_ID */
    public static final String COLUMNNAME_UY_BG_Customer_ID = "UY_BG_Customer_ID";

	/** Set UY_BG_Customer	  */
	public void setUY_BG_Customer_ID (int UY_BG_Customer_ID);

	/** Get UY_BG_Customer	  */
	public int getUY_BG_Customer_ID();

    /** Column name UY_BG_UserActivity_ID */
    public static final String COLUMNNAME_UY_BG_UserActivity_ID = "UY_BG_UserActivity_ID";

	/** Set UY_BG_UserActivity	  */
	public void setUY_BG_UserActivity_ID (int UY_BG_UserActivity_ID);

	/** Get UY_BG_UserActivity	  */
	public int getUY_BG_UserActivity_ID();

	public I_UY_BG_UserActivity getUY_BG_UserActivity() throws RuntimeException;

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
