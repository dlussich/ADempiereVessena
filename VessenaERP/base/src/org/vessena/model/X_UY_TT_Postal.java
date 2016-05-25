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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_TT_Postal
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_Postal extends PO implements I_UY_TT_Postal, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131004L;

    /** Standard Constructor */
    public X_UY_TT_Postal (Properties ctx, int UY_TT_Postal_ID, String trxName)
    {
      super (ctx, UY_TT_Postal_ID, trxName);
      /** if (UY_TT_Postal_ID == 0)
        {
			setCodLocalidad (null);
			setNomDepto (null);
			setUY_TT_Postal_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_Postal (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_Postal[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CodLocalidad.
		@param CodLocalidad CodLocalidad	  */
	public void setCodLocalidad (String CodLocalidad)
	{
		set_Value (COLUMNNAME_CodLocalidad, CodLocalidad);
	}

	/** Get CodLocalidad.
		@return CodLocalidad	  */
	public String getCodLocalidad () 
	{
		return (String)get_Value(COLUMNNAME_CodLocalidad);
	}

	/** Set localidad.
		@param localidad localidad	  */
	public void setlocalidad (String localidad)
	{
		set_Value (COLUMNNAME_localidad, localidad);
	}

	/** Get localidad.
		@return localidad	  */
	public String getlocalidad () 
	{
		return (String)get_Value(COLUMNNAME_localidad);
	}

	/** Set NomDepto.
		@param NomDepto NomDepto	  */
	public void setNomDepto (String NomDepto)
	{
		set_Value (COLUMNNAME_NomDepto, NomDepto);
	}

	/** Get NomDepto.
		@return NomDepto	  */
	public String getNomDepto () 
	{
		return (String)get_Value(COLUMNNAME_NomDepto);
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

	/** Set UY_TT_Postal.
		@param UY_TT_Postal_ID UY_TT_Postal	  */
	public void setUY_TT_Postal_ID (int UY_TT_Postal_ID)
	{
		if (UY_TT_Postal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Postal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Postal_ID, Integer.valueOf(UY_TT_Postal_ID));
	}

	/** Get UY_TT_Postal.
		@return UY_TT_Postal	  */
	public int getUY_TT_Postal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Postal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}