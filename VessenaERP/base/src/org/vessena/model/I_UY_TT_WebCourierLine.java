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

/** Generated Interface for UY_TT_WebCourierLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_WebCourierLine 
{

    /** TableName=UY_TT_WebCourierLine */
    public static final String Table_Name = "UY_TT_WebCourierLine";

    /** AD_Table_ID=1000591 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

	/** Set Account No.
	  * Account Number
	  */
	public void setAccountNo (String AccountNo);

	/** Get Account No.
	  * Account Number
	  */
	public String getAccountNo();

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

    /** Column name DateDelivery */
    public static final String COLUMNNAME_DateDelivery = "DateDelivery";

	/** Set DateDelivery.
	  * DateDelivery
	  */
	public void setDateDelivery (Timestamp DateDelivery);

	/** Get DateDelivery.
	  * DateDelivery
	  */
	public Timestamp getDateDelivery();

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

    /** Column name DeliveryNo */
    public static final String COLUMNNAME_DeliveryNo = "DeliveryNo";

	/** Set DeliveryNo.
	  * DeliveryNo
	  */
	public void setDeliveryNo (String DeliveryNo);

	/** Get DeliveryNo.
	  * DeliveryNo
	  */
	public String getDeliveryNo();

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

    /** Column name Levante */
    public static final String COLUMNNAME_Levante = "Levante";

	/** Set Levante.
	  * Levante
	  */
	public void setLevante (String Levante);

	/** Get Levante.
	  * Levante
	  */
	public String getLevante();

    /** Column name localidad */
    public static final String COLUMNNAME_localidad = "localidad";

	/** Set localidad	  */
	public void setlocalidad (String localidad);

	/** Get localidad	  */
	public String getlocalidad();

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

    /** Column name Pieza */
    public static final String COLUMNNAME_Pieza = "Pieza";

	/** Set Pieza	  */
	public void setPieza (String Pieza);

	/** Get Pieza	  */
	public String getPieza();

    /** Column name RetreatNo */
    public static final String COLUMNNAME_RetreatNo = "RetreatNo";

	/** Set RetreatNo.
	  * RetreatNo
	  */
	public void setRetreatNo (String RetreatNo);

	/** Get RetreatNo.
	  * RetreatNo
	  */
	public String getRetreatNo();

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

    /** Column name UY_R_Reclamo_ID */
    public static final String COLUMNNAME_UY_R_Reclamo_ID = "UY_R_Reclamo_ID";

	/** Set UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID);

	/** Get UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID();

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException;

    /** Column name UY_TT_CardStatus_ID */
    public static final String COLUMNNAME_UY_TT_CardStatus_ID = "UY_TT_CardStatus_ID";

	/** Set UY_TT_CardStatus	  */
	public void setUY_TT_CardStatus_ID (int UY_TT_CardStatus_ID);

	/** Get UY_TT_CardStatus	  */
	public int getUY_TT_CardStatus_ID();

	public I_UY_TT_CardStatus getUY_TT_CardStatus() throws RuntimeException;

    /** Column name UY_TT_DeliveryPointStatus_ID */
    public static final String COLUMNNAME_UY_TT_DeliveryPointStatus_ID = "UY_TT_DeliveryPointStatus_ID";

	/** Set UY_TT_DeliveryPointStatus	  */
	public void setUY_TT_DeliveryPointStatus_ID (int UY_TT_DeliveryPointStatus_ID);

	/** Get UY_TT_DeliveryPointStatus	  */
	public int getUY_TT_DeliveryPointStatus_ID();

	public I_UY_TT_DeliveryPointStatus getUY_TT_DeliveryPointStatus() throws RuntimeException;

    /** Column name UY_TT_DelPointRetReasons_ID */
    public static final String COLUMNNAME_UY_TT_DelPointRetReasons_ID = "UY_TT_DelPointRetReasons_ID";

	/** Set UY_TT_DelPointRetReasons	  */
	public void setUY_TT_DelPointRetReasons_ID (int UY_TT_DelPointRetReasons_ID);

	/** Get UY_TT_DelPointRetReasons	  */
	public int getUY_TT_DelPointRetReasons_ID();

	public I_UY_TT_DelPointRetReasons getUY_TT_DelPointRetReasons() throws RuntimeException;

    /** Column name UY_TT_ReturnReasons_ID */
    public static final String COLUMNNAME_UY_TT_ReturnReasons_ID = "UY_TT_ReturnReasons_ID";

	/** Set UY_TT_ReturnReasons	  */
	public void setUY_TT_ReturnReasons_ID (int UY_TT_ReturnReasons_ID);

	/** Get UY_TT_ReturnReasons	  */
	public int getUY_TT_ReturnReasons_ID();

	public I_UY_TT_ReturnReasons getUY_TT_ReturnReasons() throws RuntimeException;

    /** Column name UY_TT_WebCourier_ID */
    public static final String COLUMNNAME_UY_TT_WebCourier_ID = "UY_TT_WebCourier_ID";

	/** Set UY_TT_WebCourier	  */
	public void setUY_TT_WebCourier_ID (int UY_TT_WebCourier_ID);

	/** Get UY_TT_WebCourier	  */
	public int getUY_TT_WebCourier_ID();

	public I_UY_TT_WebCourier getUY_TT_WebCourier() throws RuntimeException;

    /** Column name UY_TT_WebCourierLine_ID */
    public static final String COLUMNNAME_UY_TT_WebCourierLine_ID = "UY_TT_WebCourierLine_ID";

	/** Set UY_TT_WebCourierLine	  */
	public void setUY_TT_WebCourierLine_ID (int UY_TT_WebCourierLine_ID);

	/** Get UY_TT_WebCourierLine	  */
	public int getUY_TT_WebCourierLine_ID();

    /** Column name Vinculo */
    public static final String COLUMNNAME_Vinculo = "Vinculo";

	/** Set Vinculo	  */
	public void setVinculo (String Vinculo);

	/** Get Vinculo	  */
	public String getVinculo();
}
