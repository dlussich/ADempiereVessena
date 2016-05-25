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

/** Generated Interface for UY_BG_Offer
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_BG_Offer 
{

    /** TableName=UY_BG_Offer */
    public static final String Table_Name = "UY_BG_Offer";

    /** AD_Table_ID=1000952 */
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

    /** Column name BuySell */
    public static final String COLUMNNAME_BuySell = "BuySell";

	/** Set BuySell.
	  * List document details
	  */
	public void setBuySell (String BuySell);

	/** Get BuySell.
	  * List document details
	  */
	public String getBuySell();

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

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

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

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

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

    /** Column name IsAll */
    public static final String COLUMNNAME_IsAll = "IsAll";

	/** Set IsAll.
	  * Indica si la offerta forward aplica Todo o nada
	  */
	public void setIsAll (boolean IsAll);

	/** Get IsAll.
	  * Indica si la offerta forward aplica Todo o nada
	  */
	public boolean isAll();

    /** Column name isMatched */
    public static final String COLUMNNAME_isMatched = "isMatched";

	/** Set isMatched	  */
	public void setisMatched (boolean isMatched);

	/** Get isMatched	  */
	public boolean isMatched();

    /** Column name IsPanic */
    public static final String COLUMNNAME_IsPanic = "IsPanic";

	/** Set IsPanic.
	  * Se indica si hubo accion de panico
	  */
	public void setIsPanic (boolean IsPanic);

	/** Get IsPanic.
	  * Se indica si hubo accion de panico
	  */
	public boolean isPanic();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/** Set Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered);

	/** Get Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered();

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

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

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

    /** Column name UY_BG_Bursa_ID */
    public static final String COLUMNNAME_UY_BG_Bursa_ID = "UY_BG_Bursa_ID";

	/** Set UY_BG_Bursa	  */
	public void setUY_BG_Bursa_ID (int UY_BG_Bursa_ID);

	/** Get UY_BG_Bursa	  */
	public int getUY_BG_Bursa_ID();

	public I_UY_BG_Bursa getUY_BG_Bursa() throws RuntimeException;

    /** Column name UY_BG_Bursa_ID_2 */
    public static final String COLUMNNAME_UY_BG_Bursa_ID_2 = "UY_BG_Bursa_ID_2";

	/** Set UY_BG_Bursa_ID_2.
	  * Bolsa contraparte
	  */
	public void setUY_BG_Bursa_ID_2 (int UY_BG_Bursa_ID_2);

	/** Get UY_BG_Bursa_ID_2.
	  * Bolsa contraparte
	  */
	public int getUY_BG_Bursa_ID_2();

	public I_UY_BG_Bursa getUY_BG_Bursa_I() throws RuntimeException;

    /** Column name UY_BG_Instrument_ID */
    public static final String COLUMNNAME_UY_BG_Instrument_ID = "UY_BG_Instrument_ID";

	/** Set UY_BG_Instrument	  */
	public void setUY_BG_Instrument_ID (int UY_BG_Instrument_ID);

	/** Get UY_BG_Instrument	  */
	public int getUY_BG_Instrument_ID();

	public I_UY_BG_Instrument getUY_BG_Instrument() throws RuntimeException;

    /** Column name UY_BG_Offer_ID */
    public static final String COLUMNNAME_UY_BG_Offer_ID = "UY_BG_Offer_ID";

	/** Set UY_BG_Offer	  */
	public void setUY_BG_Offer_ID (int UY_BG_Offer_ID);

	/** Get UY_BG_Offer	  */
	public int getUY_BG_Offer_ID();

    /** Column name UY_BG_Offer_ID_2 */
    public static final String COLUMNNAME_UY_BG_Offer_ID_2 = "UY_BG_Offer_ID_2";

	/** Set UY_BG_Offer_ID_2	  */
	public void setUY_BG_Offer_ID_2 (int UY_BG_Offer_ID_2);

	/** Get UY_BG_Offer_ID_2	  */
	public int getUY_BG_Offer_ID_2();

    /** Column name UY_BG_TypeOffers_ID */
    public static final String COLUMNNAME_UY_BG_TypeOffers_ID = "UY_BG_TypeOffers_ID";

	/** Set UY_BG_TypeOffers	  */
	public void setUY_BG_TypeOffers_ID (int UY_BG_TypeOffers_ID);

	/** Get UY_BG_TypeOffers	  */
	public int getUY_BG_TypeOffers_ID();

	public I_UY_BG_TypeOffers getUY_BG_TypeOffers() throws RuntimeException;

    /** Column name UY_BG_TypeOperation_ID */
    public static final String COLUMNNAME_UY_BG_TypeOperation_ID = "UY_BG_TypeOperation_ID";

	/** Set UY_BG_TypeOperatio	  */
	public void setUY_BG_TypeOperation_ID (int UY_BG_TypeOperation_ID);

	/** Get UY_BG_TypeOperatio	  */
	public int getUY_BG_TypeOperation_ID();

	public I_UY_BG_TypeOperation getUY_BG_TypeOperation() throws RuntimeException;

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();

    /** Column name VolumeBase */
    public static final String COLUMNNAME_VolumeBase = "VolumeBase";

	/** Set VolumeBase.
	  * Volumen base 
	  */
	public void setVolumeBase (BigDecimal VolumeBase);

	/** Get VolumeBase.
	  * Volumen base 
	  */
	public BigDecimal getVolumeBase();
}
