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
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.openup.model.I_UY_Departamentos;
import org.openup.model.I_UY_Localidades;
import org.openup.model.I_UY_ZonaReparto;

/** Generated Model for C_BPartner_Location
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_BPartner_Location extends PO implements I_C_BPartner_Location, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140809L;

    /** Standard Constructor */
    public X_C_BPartner_Location (Properties ctx, int C_BPartner_Location_ID, String trxName)
    {
      super (ctx, C_BPartner_Location_ID, trxName);
      /** if (C_BPartner_Location_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Location_ID (0);
			setIsBillTo (true);
// Y
			setIsPayFrom (true);
// Y
			setIsRemitTo (true);
// Y
			setIsShipTo (true);
// Y
			setName (null);
// .
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Location (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_BPartner_Location[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address 1.
		@param Address1 
		Address line 1 for this location
	  */
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Address 1.
		@return Address line 1 for this location
	  */
	public String getAddress1 () 
	{
		return (String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Address 2.
		@param Address2 
		Address line 2 for this location
	  */
	public void setAddress2 (String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Address 2.
		@return Address line 2 for this location
	  */
	public String getAddress2 () 
	{
		return (String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Address 3.
		@param Address3 
		Address Line 3 for the location
	  */
	public void setAddress3 (String Address3)
	{
		set_Value (COLUMNNAME_Address3, Address3);
	}

	/** Get Address 3.
		@return Address Line 3 for the location
	  */
	public String getAddress3 () 
	{
		return (String)get_Value(COLUMNNAME_Address3);
	}

	/** Set Address 4.
		@param Address4 
		Address Line 4 for the location
	  */
	public void setAddress4 (String Address4)
	{
		set_Value (COLUMNNAME_Address4, Address4);
	}

	/** Get Address 4.
		@return Address Line 4 for the location
	  */
	public String getAddress4 () 
	{
		return (String)get_Value(COLUMNNAME_Address4);
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_City getC_City() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getC_City_ID(), get_TrxName());	}

	/** Set City.
		@param C_City_ID 
		City
	  */
	public void setC_City_ID (int C_City_ID)
	{
		if (C_City_ID < 1) 
			set_Value (COLUMNNAME_C_City_ID, null);
		else 
			set_Value (COLUMNNAME_C_City_ID, Integer.valueOf(C_City_ID));
	}

	/** Get City.
		@return City
	  */
	public int getC_City_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
    {
		return (org.compiere.model.I_C_Country)MTable.get(getCtx(), org.compiere.model.I_C_Country.Table_Name)
			.getPO(getC_Country_ID(), get_TrxName());	}

	/** Set Country.
		@param C_Country_ID 
		Country 
	  */
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Country.
		@return Country 
	  */
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
    {
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getC_Region_ID(), get_TrxName());	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_SalesRegion getC_SalesRegion() throws RuntimeException
    {
		return (org.compiere.model.I_C_SalesRegion)MTable.get(getCtx(), org.compiere.model.I_C_SalesRegion.Table_Name)
			.getPO(getC_SalesRegion_ID(), get_TrxName());	}

	/** Set Sales Region.
		@param C_SalesRegion_ID 
		Sales coverage region
	  */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
	}

	/** Get Sales Region.
		@return Sales coverage region
	  */
	public int getC_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SalesRegion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** department AD_Reference_ID=1000129 */
	public static final int DEPARTMENT_AD_Reference_ID=1000129;
	/** Treinta y Tres = TT */
	public static final String DEPARTMENT_TreintaYTres = "TT";
	/** Lavalleja = LA */
	public static final String DEPARTMENT_Lavalleja = "LA";
	/** Tacuarembó = TA */
	public static final String DEPARTMENT_Tacuaremb� = "TA";
	/** Cerro Largo = CL */
	public static final String DEPARTMENT_CerroLargo = "CL";
	/** Canelones = CA */
	public static final String DEPARTMENT_Canelones = "CA";
	/** Montevideo = MVD */
	public static final String DEPARTMENT_Montevideo = "MVD";
	/** Rocha = RO */
	public static final String DEPARTMENT_Rocha = "RO";
	/** Maldonado = MA */
	public static final String DEPARTMENT_Maldonado = "MA";
	/** Soriano = SO */
	public static final String DEPARTMENT_Soriano = "SO";
	/** Río Negro = RN */
	public static final String DEPARTMENT_RíoNegro = "RN";
	/** Flores = FS */
	public static final String DEPARTMENT_Flores = "FS";
	/** Colonia = CO */
	public static final String DEPARTMENT_Colonia = "CO";
	/** Artigas = AR */
	public static final String DEPARTMENT_Artigas = "AR";
	/** Rivera = RV */
	public static final String DEPARTMENT_Rivera = "RV";
	/** Paysandú = PY */
	public static final String DEPARTMENT_Paysandú = "PY";
	/** Salto = SA */
	public static final String DEPARTMENT_Salto = "SA";
	/** Florida = FA */
	public static final String DEPARTMENT_Florida = "FA";
	/** Durazno = DZ */
	public static final String DEPARTMENT_Durazno = "DZ";
	/** San José = SJ */
	public static final String DEPARTMENT_SanJos� = "SJ";
	/** Set department.
		@param department department	  */
	public void setdepartment (String department)
	{

		set_Value (COLUMNNAME_department, department);
	}

	/** Get department.
		@return department	  */
	public String getdepartment () 
	{
		return (String)get_Value(COLUMNNAME_department);
	}

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Fax.
		@param Fax 
		Facsimile number
	  */
	public void setFax (String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Facsimile number
	  */
	public String getFax () 
	{
		return (String)get_Value(COLUMNNAME_Fax);
	}

	/** Set HoraEntrega1.
		@param HoraEntrega1 HoraEntrega1	  */
	public void setHoraEntrega1 (Timestamp HoraEntrega1)
	{
		set_Value (COLUMNNAME_HoraEntrega1, HoraEntrega1);
	}

	/** Get HoraEntrega1.
		@return HoraEntrega1	  */
	public Timestamp getHoraEntrega1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HoraEntrega1);
	}

	/** Set HoraEntrega2.
		@param HoraEntrega2 HoraEntrega2	  */
	public void setHoraEntrega2 (Timestamp HoraEntrega2)
	{
		set_Value (COLUMNNAME_HoraEntrega2, HoraEntrega2);
	}

	/** Get HoraEntrega2.
		@return HoraEntrega2	  */
	public Timestamp getHoraEntrega2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HoraEntrega2);
	}

	/** Set HoraEntrega3.
		@param HoraEntrega3 HoraEntrega3	  */
	public void setHoraEntrega3 (Timestamp HoraEntrega3)
	{
		set_Value (COLUMNNAME_HoraEntrega3, HoraEntrega3);
	}

	/** Get HoraEntrega3.
		@return HoraEntrega3	  */
	public Timestamp getHoraEntrega3 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HoraEntrega3);
	}

	/** Set HoraEntrega4.
		@param HoraEntrega4 HoraEntrega4	  */
	public void setHoraEntrega4 (Timestamp HoraEntrega4)
	{
		set_Value (COLUMNNAME_HoraEntrega4, HoraEntrega4);
	}

	/** Get HoraEntrega4.
		@return HoraEntrega4	  */
	public Timestamp getHoraEntrega4 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HoraEntrega4);
	}

	/** Set Invoice Address.
		@param IsBillTo 
		Business Partner Invoice/Bill Address
	  */
	public void setIsBillTo (boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Invoice Address.
		@return Business Partner Invoice/Bill Address
	  */
	public boolean isBillTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ISDN.
		@param ISDN 
		ISDN or modem line
	  */
	public void setISDN (String ISDN)
	{
		set_Value (COLUMNNAME_ISDN, ISDN);
	}

	/** Get ISDN.
		@return ISDN or modem line
	  */
	public String getISDN () 
	{
		return (String)get_Value(COLUMNNAME_ISDN);
	}

	/** Set Pay-From Address.
		@param IsPayFrom 
		Business Partner pays from that address and we'll send dunning letters there
	  */
	public void setIsPayFrom (boolean IsPayFrom)
	{
		set_Value (COLUMNNAME_IsPayFrom, Boolean.valueOf(IsPayFrom));
	}

	/** Get Pay-From Address.
		@return Business Partner pays from that address and we'll send dunning letters there
	  */
	public boolean isPayFrom () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayFrom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Remit-To Address.
		@param IsRemitTo 
		Business Partner payment address
	  */
	public void setIsRemitTo (boolean IsRemitTo)
	{
		set_Value (COLUMNNAME_IsRemitTo, Boolean.valueOf(IsRemitTo));
	}

	/** Get Remit-To Address.
		@return Business Partner payment address
	  */
	public boolean isRemitTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsRemitTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ship Address.
		@param IsShipTo 
		Business Partner Shipment Address
	  */
	public void setIsShipTo (boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Ship Address.
		@return Business Partner Shipment Address
	  */
	public boolean isShipTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set 2nd Phone.
		@param Phone2 
		Identifies an alternate telephone number.
	  */
	public void setPhone2 (String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get 2nd Phone.
		@return Identifies an alternate telephone number.
	  */
	public String getPhone2 () 
	{
		return (String)get_Value(COLUMNNAME_Phone2);
	}

	/** Set ZIP.
		@param Postal 
		Postal code
	  */
	public void setPostal (String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get ZIP.
		@return Postal code
	  */
	public String getPostal () 
	{
		return (String)get_Value(COLUMNNAME_Postal);
	}

	/** Set Region.
		@param RegionName 
		Name of the Region
	  */
	public void setRegionName (String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	/** Get Region.
		@return Name of the Region
	  */
	public String getRegionName () 
	{
		return (String)get_Value(COLUMNNAME_RegionName);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Tiempo de Entrega.
		@param TEntrega Tiempo de Entrega	  */
	public void setTEntrega (Timestamp TEntrega)
	{
		set_Value (COLUMNNAME_TEntrega, TEntrega);
	}

	/** Get Tiempo de Entrega.
		@return Tiempo de Entrega	  */
	public Timestamp getTEntrega () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TEntrega);
	}

	/** Set Tiempo de Espera.
		@param TEspera Tiempo de Espera	  */
	public void setTEspera (Timestamp TEspera)
	{
		set_Value (COLUMNNAME_TEspera, TEspera);
	}

	/** Get Tiempo de Espera.
		@return Tiempo de Espera	  */
	public Timestamp getTEspera () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TEspera);
	}

	public I_UY_Departamentos getUY_Departamentos() throws RuntimeException
    {
		return (I_UY_Departamentos)MTable.get(getCtx(), I_UY_Departamentos.Table_Name)
			.getPO(getUY_Departamentos_ID(), get_TrxName());	}

	/** Set Departamentos o regiones por Pais.
		@param UY_Departamentos_ID Departamentos o regiones por Pais	  */
	public void setUY_Departamentos_ID (int UY_Departamentos_ID)
	{
		if (UY_Departamentos_ID < 1) 
			set_Value (COLUMNNAME_UY_Departamentos_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Departamentos_ID, Integer.valueOf(UY_Departamentos_ID));
	}

	/** Get Departamentos o regiones por Pais.
		@return Departamentos o regiones por Pais	  */
	public int getUY_Departamentos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Departamentos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GLN.
		@param UY_GLN GLN	  */
	public void setUY_GLN (String UY_GLN)
	{
		set_Value (COLUMNNAME_UY_GLN, UY_GLN);
	}

	/** Get GLN.
		@return GLN	  */
	public String getUY_GLN () 
	{
		return (String)get_Value(COLUMNNAME_UY_GLN);
	}

	public I_UY_Localidades getUY_Localidades() throws RuntimeException
    {
		return (I_UY_Localidades)MTable.get(getCtx(), I_UY_Localidades.Table_Name)
			.getPO(getUY_Localidades_ID(), get_TrxName());	}

	/** Set Localidades por Departamentos.
		@param UY_Localidades_ID Localidades por Departamentos	  */
	public void setUY_Localidades_ID (int UY_Localidades_ID)
	{
		if (UY_Localidades_ID < 1) 
			set_Value (COLUMNNAME_UY_Localidades_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Localidades_ID, Integer.valueOf(UY_Localidades_ID));
	}

	/** Get Localidades por Departamentos.
		@return Localidades por Departamentos	  */
	public int getUY_Localidades_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Localidades_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ZonaReparto getUY_ZonaReparto() throws RuntimeException
    {
		return (I_UY_ZonaReparto)MTable.get(getCtx(), I_UY_ZonaReparto.Table_Name)
			.getPO(getUY_ZonaReparto_ID(), get_TrxName());	}

	/** Set UY_ZonaReparto.
		@param UY_ZonaReparto_ID UY_ZonaReparto	  */
	public void setUY_ZonaReparto_ID (int UY_ZonaReparto_ID)
	{
		if (UY_ZonaReparto_ID < 1) 
			set_Value (COLUMNNAME_UY_ZonaReparto_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ZonaReparto_ID, Integer.valueOf(UY_ZonaReparto_ID));
	}

	/** Get UY_ZonaReparto.
		@return UY_ZonaReparto	  */
	public int getUY_ZonaReparto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ZonaReparto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}