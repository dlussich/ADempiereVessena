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

/** Generated Interface for UY_TR_Driver
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Driver 
{

    /** TableName=UY_TR_Driver */
    public static final String Table_Name = "UY_TR_Driver";

    /** AD_Table_ID=1000727 */
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name DateAction */
    public static final String COLUMNNAME_DateAction = "DateAction";

	/** Set DateAction	  */
	public void setDateAction (Timestamp DateAction);

	/** Get DateAction	  */
	public Timestamp getDateAction();

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Timestamp getDateFrom();

    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/** Set Date To.
	  * End date of a date range
	  */
	public void setDateTo (Timestamp DateTo);

	/** Get Date To.
	  * End date of a date range
	  */
	public Timestamp getDateTo();

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

    /** Column name DocNo2 */
    public static final String COLUMNNAME_DocNo2 = "DocNo2";

	/** Set DocNo2	  */
	public void setDocNo2 (String DocNo2);

	/** Get DocNo2	  */
	public String getDocNo2();

    /** Column name DocType2 */
    public static final String COLUMNNAME_DocType2 = "DocType2";

	/** Set DocType2	  */
	public void setDocType2 (String DocType2);

	/** Get DocType2	  */
	public String getDocType2();

    /** Column name DriverCharge */
    public static final String COLUMNNAME_DriverCharge = "DriverCharge";

	/** Set DriverCharge	  */
	public void setDriverCharge (String DriverCharge);

	/** Get DriverCharge	  */
	public String getDriverCharge();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

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

    /** Column name IsEmployee */
    public static final String COLUMNNAME_IsEmployee = "IsEmployee";

	/** Set Employee.
	  * Indicates if  this Business Partner is an employee
	  */
	public void setIsEmployee (boolean IsEmployee);

	/** Get Employee.
	  * Indicates if  this Business Partner is an employee
	  */
	public boolean isEmployee();

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

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Additional Name
	  */
	public void setName2 (String Name2);

	/** Get Name 2.
	  * Additional Name
	  */
	public String getName2();

    /** Column name NationalCode */
    public static final String COLUMNNAME_NationalCode = "NationalCode";

	/** Set National Code	  */
	public void setNationalCode (String NationalCode);

	/** Get National Code	  */
	public String getNationalCode();

    /** Column name nationality */
    public static final String COLUMNNAME_nationality = "nationality";

	/** Set nationality	  */
	public void setnationality (String nationality);

	/** Get nationality	  */
	public String getnationality();

    /** Column name NroCarne */
    public static final String COLUMNNAME_NroCarne = "NroCarne";

	/** Set NroCarne	  */
	public void setNroCarne (String NroCarne);

	/** Get NroCarne	  */
	public String getNroCarne();

    /** Column name Numero */
    public static final String COLUMNNAME_Numero = "Numero";

	/** Set Numero	  */
	public void setNumero (String Numero);

	/** Get Numero	  */
	public String getNumero();

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

    /** Column name Phone2 */
    public static final String COLUMNNAME_Phone2 = "Phone2";

	/** Set 2nd Phone.
	  * Identifies an alternate telephone number.
	  */
	public void setPhone2 (String Phone2);

	/** Get 2nd Phone.
	  * Identifies an alternate telephone number.
	  */
	public String getPhone2();

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

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name tipo */
    public static final String COLUMNNAME_tipo = "tipo";

	/** Set tipo	  */
	public void settipo (String tipo);

	/** Get tipo	  */
	public String gettipo();

    /** Column name tipodoc */
    public static final String COLUMNNAME_tipodoc = "tipodoc";

	/** Set tipodoc	  */
	public void settipodoc (String tipodoc);

	/** Get tipodoc	  */
	public String gettipodoc();

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

    /** Column name UY_TR_Driver_ID */
    public static final String COLUMNNAME_UY_TR_Driver_ID = "UY_TR_Driver_ID";

	/** Set UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID);

	/** Get UY_TR_Driver	  */
	public int getUY_TR_Driver_ID();

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
