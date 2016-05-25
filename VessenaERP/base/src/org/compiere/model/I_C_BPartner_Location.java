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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;
import org.openup.model.I_UY_Departamentos;
import org.openup.model.I_UY_Localidades;
import org.openup.model.I_UY_ZonaReparto;

/** Generated Interface for C_BPartner_Location
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_C_BPartner_Location 
{

    /** TableName=C_BPartner_Location */
    public static final String Table_Name = "C_BPartner_Location";

    /** AD_Table_ID=293 */
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

    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/** Set Address 2.
	  * Address line 2 for this location
	  */
	public void setAddress2 (String Address2);

	/** Get Address 2.
	  * Address line 2 for this location
	  */
	public String getAddress2();

    /** Column name Address3 */
    public static final String COLUMNNAME_Address3 = "Address3";

	/** Set Address 3.
	  * Address Line 3 for the location
	  */
	public void setAddress3 (String Address3);

	/** Get Address 3.
	  * Address Line 3 for the location
	  */
	public String getAddress3();

    /** Column name Address4 */
    public static final String COLUMNNAME_Address4 = "Address4";

	/** Set Address 4.
	  * Address Line 4 for the location
	  */
	public void setAddress4 (String Address4);

	/** Get Address 4.
	  * Address Line 4 for the location
	  */
	public String getAddress4();

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

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

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

    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Country.
	  * Country 
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Country.
	  * Country 
	  */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException;

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public I_C_Location getC_Location() throws RuntimeException;

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

    /** Column name C_SalesRegion_ID */
    public static final String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/** Set Sales Region.
	  * Sales coverage region
	  */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/** Get Sales Region.
	  * Sales coverage region
	  */
	public int getC_SalesRegion_ID();

	public org.compiere.model.I_C_SalesRegion getC_SalesRegion() throws RuntimeException;

    /** Column name department */
    public static final String COLUMNNAME_department = "department";

	/** Set department	  */
	public void setdepartment (String department);

	/** Get department	  */
	public String getdepartment();

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Discount %.
	  * Discount in percent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Discount %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount();

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

    /** Column name Fax */
    public static final String COLUMNNAME_Fax = "Fax";

	/** Set Fax.
	  * Facsimile number
	  */
	public void setFax (String Fax);

	/** Get Fax.
	  * Facsimile number
	  */
	public String getFax();

    /** Column name HoraEntrega1 */
    public static final String COLUMNNAME_HoraEntrega1 = "HoraEntrega1";

	/** Set HoraEntrega1	  */
	public void setHoraEntrega1 (Timestamp HoraEntrega1);

	/** Get HoraEntrega1	  */
	public Timestamp getHoraEntrega1();

    /** Column name HoraEntrega2 */
    public static final String COLUMNNAME_HoraEntrega2 = "HoraEntrega2";

	/** Set HoraEntrega2	  */
	public void setHoraEntrega2 (Timestamp HoraEntrega2);

	/** Get HoraEntrega2	  */
	public Timestamp getHoraEntrega2();

    /** Column name HoraEntrega3 */
    public static final String COLUMNNAME_HoraEntrega3 = "HoraEntrega3";

	/** Set HoraEntrega3	  */
	public void setHoraEntrega3 (Timestamp HoraEntrega3);

	/** Get HoraEntrega3	  */
	public Timestamp getHoraEntrega3();

    /** Column name HoraEntrega4 */
    public static final String COLUMNNAME_HoraEntrega4 = "HoraEntrega4";

	/** Set HoraEntrega4	  */
	public void setHoraEntrega4 (Timestamp HoraEntrega4);

	/** Get HoraEntrega4	  */
	public Timestamp getHoraEntrega4();

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

    /** Column name IsBillTo */
    public static final String COLUMNNAME_IsBillTo = "IsBillTo";

	/** Set Invoice Address.
	  * Business Partner Invoice/Bill Address
	  */
	public void setIsBillTo (boolean IsBillTo);

	/** Get Invoice Address.
	  * Business Partner Invoice/Bill Address
	  */
	public boolean isBillTo();

    /** Column name ISDN */
    public static final String COLUMNNAME_ISDN = "ISDN";

	/** Set ISDN.
	  * ISDN or modem line
	  */
	public void setISDN (String ISDN);

	/** Get ISDN.
	  * ISDN or modem line
	  */
	public String getISDN();

    /** Column name IsPayFrom */
    public static final String COLUMNNAME_IsPayFrom = "IsPayFrom";

	/** Set Pay-From Address.
	  * Business Partner pays from that address and we'll send dunning letters there
	  */
	public void setIsPayFrom (boolean IsPayFrom);

	/** Get Pay-From Address.
	  * Business Partner pays from that address and we'll send dunning letters there
	  */
	public boolean isPayFrom();

    /** Column name IsRemitTo */
    public static final String COLUMNNAME_IsRemitTo = "IsRemitTo";

	/** Set Remit-To Address.
	  * Business Partner payment address
	  */
	public void setIsRemitTo (boolean IsRemitTo);

	/** Get Remit-To Address.
	  * Business Partner payment address
	  */
	public boolean isRemitTo();

    /** Column name IsShipTo */
    public static final String COLUMNNAME_IsShipTo = "IsShipTo";

	/** Set Ship Address.
	  * Business Partner Shipment Address
	  */
	public void setIsShipTo (boolean IsShipTo);

	/** Get Ship Address.
	  * Business Partner Shipment Address
	  */
	public boolean isShipTo();

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

    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/** Set ZIP.
	  * Postal code
	  */
	public void setPostal (String Postal);

	/** Get ZIP.
	  * Postal code
	  */
	public String getPostal();

    /** Column name RegionName */
    public static final String COLUMNNAME_RegionName = "RegionName";

	/** Set Region.
	  * Name of the Region
	  */
	public void setRegionName (String RegionName);

	/** Get Region.
	  * Name of the Region
	  */
	public String getRegionName();

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

    /** Column name TEntrega */
    public static final String COLUMNNAME_TEntrega = "TEntrega";

	/** Set Tiempo de Entrega	  */
	public void setTEntrega (Timestamp TEntrega);

	/** Get Tiempo de Entrega	  */
	public Timestamp getTEntrega();

    /** Column name TEspera */
    public static final String COLUMNNAME_TEspera = "TEspera";

	/** Set Tiempo de Espera	  */
	public void setTEspera (Timestamp TEspera);

	/** Get Tiempo de Espera	  */
	public Timestamp getTEspera();

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

    /** Column name UY_Departamentos_ID */
    public static final String COLUMNNAME_UY_Departamentos_ID = "UY_Departamentos_ID";

	/** Set Departamentos o regiones por Pais	  */
	public void setUY_Departamentos_ID (int UY_Departamentos_ID);

	/** Get Departamentos o regiones por Pais	  */
	public int getUY_Departamentos_ID();

	public I_UY_Departamentos getUY_Departamentos() throws RuntimeException;

    /** Column name UY_GLN */
    public static final String COLUMNNAME_UY_GLN = "UY_GLN";

	/** Set GLN	  */
	public void setUY_GLN (String UY_GLN);

	/** Get GLN	  */
	public String getUY_GLN();

    /** Column name UY_Localidades_ID */
    public static final String COLUMNNAME_UY_Localidades_ID = "UY_Localidades_ID";

	/** Set Localidades por Departamentos	  */
	public void setUY_Localidades_ID (int UY_Localidades_ID);

	/** Get Localidades por Departamentos	  */
	public int getUY_Localidades_ID();

	public I_UY_Localidades getUY_Localidades() throws RuntimeException;

    /** Column name UY_ZonaReparto_ID */
    public static final String COLUMNNAME_UY_ZonaReparto_ID = "UY_ZonaReparto_ID";

	/** Set UY_ZonaReparto	  */
	public void setUY_ZonaReparto_ID (int UY_ZonaReparto_ID);

	/** Get UY_ZonaReparto	  */
	public int getUY_ZonaReparto_ID();

	public I_UY_ZonaReparto getUY_ZonaReparto() throws RuntimeException;
}
