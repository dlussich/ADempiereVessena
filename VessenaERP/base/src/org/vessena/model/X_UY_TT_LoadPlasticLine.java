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
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_TT_LoadPlasticLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_LoadPlasticLine extends PO implements I_UY_TT_LoadPlasticLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130926L;

    /** Standard Constructor */
    public X_UY_TT_LoadPlasticLine (Properties ctx, int UY_TT_LoadPlasticLine_ID, String trxName)
    {
      super (ctx, UY_TT_LoadPlasticLine_ID, trxName);
      /** if (UY_TT_LoadPlasticLine_ID == 0)
        {
			setAccountNo (null);
			setCedula (null);
			setInternalCode (null);
			setNroTarjetaNueva (null);
			setUY_TT_LoadPlastic_ID (0);
			setUY_TT_LoadPlasticLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_LoadPlasticLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_LoadPlasticLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
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

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
	}

	/** Set Date received.
		@param DateReceived 
		Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Date received.
		@return Date a product was received
	  */
	public Timestamp getDateReceived () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateReceived);
	}

	/** Set Dpto.
		@param Dpto Dpto	  */
	public void setDpto (String Dpto)
	{
		set_Value (COLUMNNAME_Dpto, Dpto);
	}

	/** Get Dpto.
		@return Dpto	  */
	public String getDpto () 
	{
		return (String)get_Value(COLUMNNAME_Dpto);
	}

	/** Set GAFCOD.
		@param GAFCOD GAFCOD	  */
	public void setGAFCOD (int GAFCOD)
	{
		set_Value (COLUMNNAME_GAFCOD, Integer.valueOf(GAFCOD));
	}

	/** Get GAFCOD.
		@return GAFCOD	  */
	public int getGAFCOD () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GAFCOD);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set InternalCode.
		@param InternalCode InternalCode	  */
	public void setInternalCode (String InternalCode)
	{
		set_Value (COLUMNNAME_InternalCode, InternalCode);
	}

	/** Get InternalCode.
		@return InternalCode	  */
	public String getInternalCode () 
	{
		return (String)get_Value(COLUMNNAME_InternalCode);
	}

	/** Set MonthFrom.
		@param MonthFrom MonthFrom	  */
	public void setMonthFrom (int MonthFrom)
	{
		set_Value (COLUMNNAME_MonthFrom, Integer.valueOf(MonthFrom));
	}

	/** Get MonthFrom.
		@return MonthFrom	  */
	public int getMonthFrom () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MonthFrom);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MonthTo.
		@param MonthTo MonthTo	  */
	public void setMonthTo (int MonthTo)
	{
		set_Value (COLUMNNAME_MonthTo, Integer.valueOf(MonthTo));
	}

	/** Get MonthTo.
		@return MonthTo	  */
	public int getMonthTo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MonthTo);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set NroTarjetaAnterior.
		@param NroTarjetaAnterior NroTarjetaAnterior	  */
	public void setNroTarjetaAnterior (String NroTarjetaAnterior)
	{
		set_Value (COLUMNNAME_NroTarjetaAnterior, NroTarjetaAnterior);
	}

	/** Get NroTarjetaAnterior.
		@return NroTarjetaAnterior	  */
	public String getNroTarjetaAnterior () 
	{
		return (String)get_Value(COLUMNNAME_NroTarjetaAnterior);
	}

	/** Set NroTarjetaNueva.
		@param NroTarjetaNueva NroTarjetaNueva	  */
	public void setNroTarjetaNueva (String NroTarjetaNueva)
	{
		set_Value (COLUMNNAME_NroTarjetaNueva, NroTarjetaNueva);
	}

	/** Get NroTarjetaNueva.
		@return NroTarjetaNueva	  */
	public String getNroTarjetaNueva () 
	{
		return (String)get_Value(COLUMNNAME_NroTarjetaNueva);
	}

	/** Set Piso.
		@param Piso Piso	  */
	public void setPiso (String Piso)
	{
		set_Value (COLUMNNAME_Piso, Piso);
	}

	/** Get Piso.
		@return Piso	  */
	public String getPiso () 
	{
		return (String)get_Value(COLUMNNAME_Piso);
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

	/** Set TipoSocio.
		@param TipoSocio TipoSocio	  */
	public void setTipoSocio (int TipoSocio)
	{
		set_Value (COLUMNNAME_TipoSocio, Integer.valueOf(TipoSocio));
	}

	/** Get TipoSocio.
		@return TipoSocio	  */
	public int getTipoSocio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TipoSocio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException
    {
		return (I_UY_TT_Card)MTable.get(getCtx(), I_UY_TT_Card.Table_Name)
			.getPO(getUY_TT_Card_ID(), get_TrxName());	}

	/** Set UY_TT_Card.
		@param UY_TT_Card_ID UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID)
	{
		if (UY_TT_Card_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Card_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Card_ID, Integer.valueOf(UY_TT_Card_ID));
	}

	/** Get UY_TT_Card.
		@return UY_TT_Card	  */
	public int getUY_TT_Card_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Card_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_CardPlastic getUY_TT_CardPlastic() throws RuntimeException
    {
		return (I_UY_TT_CardPlastic)MTable.get(getCtx(), I_UY_TT_CardPlastic.Table_Name)
			.getPO(getUY_TT_CardPlastic_ID(), get_TrxName());	}

	/** Set UY_TT_CardPlastic.
		@param UY_TT_CardPlastic_ID UY_TT_CardPlastic	  */
	public void setUY_TT_CardPlastic_ID (int UY_TT_CardPlastic_ID)
	{
		if (UY_TT_CardPlastic_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_CardPlastic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_CardPlastic_ID, Integer.valueOf(UY_TT_CardPlastic_ID));
	}

	/** Get UY_TT_CardPlastic.
		@return UY_TT_CardPlastic	  */
	public int getUY_TT_CardPlastic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_CardPlastic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_LoadPlastic getUY_TT_LoadPlastic() throws RuntimeException
    {
		return (I_UY_TT_LoadPlastic)MTable.get(getCtx(), I_UY_TT_LoadPlastic.Table_Name)
			.getPO(getUY_TT_LoadPlastic_ID(), get_TrxName());	}

	/** Set UY_TT_LoadPlastic.
		@param UY_TT_LoadPlastic_ID UY_TT_LoadPlastic	  */
	public void setUY_TT_LoadPlastic_ID (int UY_TT_LoadPlastic_ID)
	{
		if (UY_TT_LoadPlastic_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_LoadPlastic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_LoadPlastic_ID, Integer.valueOf(UY_TT_LoadPlastic_ID));
	}

	/** Get UY_TT_LoadPlastic.
		@return UY_TT_LoadPlastic	  */
	public int getUY_TT_LoadPlastic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_LoadPlastic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_LoadPlasticLine.
		@param UY_TT_LoadPlasticLine_ID UY_TT_LoadPlasticLine	  */
	public void setUY_TT_LoadPlasticLine_ID (int UY_TT_LoadPlasticLine_ID)
	{
		if (UY_TT_LoadPlasticLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_LoadPlasticLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_LoadPlasticLine_ID, Integer.valueOf(UY_TT_LoadPlasticLine_ID));
	}

	/** Get UY_TT_LoadPlasticLine.
		@return UY_TT_LoadPlasticLine	  */
	public int getUY_TT_LoadPlasticLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_LoadPlasticLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set YearFrom.
		@param YearFrom YearFrom	  */
	public void setYearFrom (int YearFrom)
	{
		set_Value (COLUMNNAME_YearFrom, Integer.valueOf(YearFrom));
	}

	/** Get YearFrom.
		@return YearFrom	  */
	public int getYearFrom () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_YearFrom);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set YearMonth.
		@param YearMonth YearMonth	  */
	public void setYearMonth (int YearMonth)
	{
		set_Value (COLUMNNAME_YearMonth, Integer.valueOf(YearMonth));
	}

	/** Get YearMonth.
		@return YearMonth	  */
	public int getYearMonth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_YearMonth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set YearTo.
		@param YearTo YearTo	  */
	public void setYearTo (int YearTo)
	{
		set_Value (COLUMNNAME_YearTo, Integer.valueOf(YearTo));
	}

	/** Get YearTo.
		@return YearTo	  */
	public int getYearTo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_YearTo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}