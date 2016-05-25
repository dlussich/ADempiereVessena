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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_RT_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Config extends PO implements I_UY_RT_Config, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160308L;

    /** Standard Constructor */
    public X_UY_RT_Config (Properties ctx, int UY_RT_Config_ID, String trxName)
    {
      super (ctx, UY_RT_Config_ID, trxName);
      /** if (UY_RT_Config_ID == 0)
        {
			setUY_RT_Config_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Config (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Config[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
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

	/** Set identifempresa.
		@param identifempresa identifempresa	  */
	public void setidentifempresa (int identifempresa)
	{
		set_Value (COLUMNNAME_identifempresa, Integer.valueOf(identifempresa));
	}

	/** Get identifempresa.
		@return identifempresa	  */
	public int getidentifempresa () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_identifempresa);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verified.
		@param IsVerified 
		The BOM configuration has been verified
	  */
	public void setIsVerified (boolean IsVerified)
	{
		set_Value (COLUMNNAME_IsVerified, Boolean.valueOf(IsVerified));
	}

	/** Get Verified.
		@return The BOM configuration has been verified
	  */
	public boolean isVerified () 
	{
		Object oo = get_Value(COLUMNNAME_IsVerified);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set mantartbarra.
		@param mantartbarra mantartbarra	  */
	public void setmantartbarra (String mantartbarra)
	{
		set_Value (COLUMNNAME_mantartbarra, mantartbarra);
	}

	/** Get mantartbarra.
		@return mantartbarra	  */
	public String getmantartbarra () 
	{
		return (String)get_Value(COLUMNNAME_mantartbarra);
	}

	/** Set mantarticulo.
		@param mantarticulo mantarticulo	  */
	public void setmantarticulo (String mantarticulo)
	{
		set_Value (COLUMNNAME_mantarticulo, mantarticulo);
	}

	/** Get mantarticulo.
		@return mantarticulo	  */
	public String getmantarticulo () 
	{
		return (String)get_Value(COLUMNNAME_mantarticulo);
	}

	/** Set mantartprecio.
		@param mantartprecio mantartprecio	  */
	public void setmantartprecio (String mantartprecio)
	{
		set_Value (COLUMNNAME_mantartprecio, mantartprecio);
	}

	/** Get mantartprecio.
		@return mantartprecio	  */
	public String getmantartprecio () 
	{
		return (String)get_Value(COLUMNNAME_mantartprecio);
	}

	/** Set mantcaja.
		@param mantcaja mantcaja	  */
	public void setmantcaja (String mantcaja)
	{
		set_Value (COLUMNNAME_mantcaja, mantcaja);
	}

	/** Get mantcaja.
		@return mantcaja	  */
	public String getmantcaja () 
	{
		return (String)get_Value(COLUMNNAME_mantcaja);
	}

	/** Set mantcategoria.
		@param mantcategoria mantcategoria	  */
	public void setmantcategoria (String mantcategoria)
	{
		set_Value (COLUMNNAME_mantcategoria, mantcategoria);
	}

	/** Get mantcategoria.
		@return mantcategoria	  */
	public String getmantcategoria () 
	{
		return (String)get_Value(COLUMNNAME_mantcategoria);
	}

	/** Set mantcliente.
		@param mantcliente mantcliente	  */
	public void setmantcliente (String mantcliente)
	{
		set_Value (COLUMNNAME_mantcliente, mantcliente);
	}

	/** Get mantcliente.
		@return mantcliente	  */
	public String getmantcliente () 
	{
		return (String)get_Value(COLUMNNAME_mantcliente);
	}

	/** Set mantcombo.
		@param mantcombo mantcombo	  */
	public void setmantcombo (String mantcombo)
	{
		set_Value (COLUMNNAME_mantcombo, mantcombo);
	}

	/** Get mantcombo.
		@return mantcombo	  */
	public String getmantcombo () 
	{
		return (String)get_Value(COLUMNNAME_mantcombo);
	}

	/** Set mantfamilia.
		@param mantfamilia mantfamilia	  */
	public void setmantfamilia (String mantfamilia)
	{
		set_Value (COLUMNNAME_mantfamilia, mantfamilia);
	}

	/** Get mantfamilia.
		@return mantfamilia	  */
	public String getmantfamilia () 
	{
		return (String)get_Value(COLUMNNAME_mantfamilia);
	}

	/** Set mantlocal.
		@param mantlocal mantlocal	  */
	public void setmantlocal (String mantlocal)
	{
		set_Value (COLUMNNAME_mantlocal, mantlocal);
	}

	/** Get mantlocal.
		@return mantlocal	  */
	public String getmantlocal () 
	{
		return (String)get_Value(COLUMNNAME_mantlocal);
	}

	/** Set mantmovimiento.
		@param mantmovimiento mantmovimiento	  */
	public void setmantmovimiento (String mantmovimiento)
	{
		set_Value (COLUMNNAME_mantmovimiento, mantmovimiento);
	}

	/** Get mantmovimiento.
		@return mantmovimiento	  */
	public String getmantmovimiento () 
	{
		return (String)get_Value(COLUMNNAME_mantmovimiento);
	}

	/** Set mantpreciolista.
		@param mantpreciolista mantpreciolista	  */
	public void setmantpreciolista (String mantpreciolista)
	{
		set_Value (COLUMNNAME_mantpreciolista, mantpreciolista);
	}

	/** Get mantpreciolista.
		@return mantpreciolista	  */
	public String getmantpreciolista () 
	{
		return (String)get_Value(COLUMNNAME_mantpreciolista);
	}

	/** Set mantpreciolistaprecio.
		@param mantpreciolistaprecio mantpreciolistaprecio	  */
	public void setmantpreciolistaprecio (String mantpreciolistaprecio)
	{
		set_Value (COLUMNNAME_mantpreciolistaprecio, mantpreciolistaprecio);
	}

	/** Get mantpreciolistaprecio.
		@return mantpreciolistaprecio	  */
	public String getmantpreciolistaprecio () 
	{
		return (String)get_Value(COLUMNNAME_mantpreciolistaprecio);
	}

	/** Set mantsemejante.
		@param mantsemejante mantsemejante	  */
	public void setmantsemejante (String mantsemejante)
	{
		set_Value (COLUMNNAME_mantsemejante, mantsemejante);
	}

	/** Get mantsemejante.
		@return mantsemejante	  */
	public String getmantsemejante () 
	{
		return (String)get_Value(COLUMNNAME_mantsemejante);
	}

	/** Set mantsubfamilia.
		@param mantsubfamilia mantsubfamilia	  */
	public void setmantsubfamilia (String mantsubfamilia)
	{
		set_Value (COLUMNNAME_mantsubfamilia, mantsubfamilia);
	}

	/** Get mantsubfamilia.
		@return mantsubfamilia	  */
	public String getmantsubfamilia () 
	{
		return (String)get_Value(COLUMNNAME_mantsubfamilia);
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

	/** Set pswscanntech.
		@param pswscanntech pswscanntech	  */
	public void setpswscanntech (String pswscanntech)
	{
		set_Value (COLUMNNAME_pswscanntech, pswscanntech);
	}

	/** Get pswscanntech.
		@return pswscanntech	  */
	public String getpswscanntech () 
	{
		return (String)get_Value(COLUMNNAME_pswscanntech);
	}

	/** Set URL.
		@param URL 
		Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL () 
	{
		return (String)get_Value(COLUMNNAME_URL);
	}

	/** Set urlmetodo.
		@param urlmetodo urlmetodo	  */
	public void seturlmetodo (String urlmetodo)
	{
		set_Value (COLUMNNAME_urlmetodo, urlmetodo);
	}

	/** Get urlmetodo.
		@return urlmetodo	  */
	public String geturlmetodo () 
	{
		return (String)get_Value(COLUMNNAME_urlmetodo);
	}

	/** Set userscannatech.
		@param userscannatech userscannatech	  */
	public void setuserscannatech (String userscannatech)
	{
		set_Value (COLUMNNAME_userscannatech, userscannatech);
	}

	/** Get userscannatech.
		@return userscannatech	  */
	public String getuserscannatech () 
	{
		return (String)get_Value(COLUMNNAME_userscannatech);
	}

	/** Set UY_RT_Config.
		@param UY_RT_Config_ID UY_RT_Config	  */
	public void setUY_RT_Config_ID (int UY_RT_Config_ID)
	{
		if (UY_RT_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Config_ID, Integer.valueOf(UY_RT_Config_ID));
	}

	/** Get UY_RT_Config.
		@return UY_RT_Config	  */
	public int getUY_RT_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}