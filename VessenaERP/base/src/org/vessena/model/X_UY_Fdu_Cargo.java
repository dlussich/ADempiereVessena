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

/** Generated Model for UY_Fdu_Cargo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Fdu_Cargo extends PO implements I_UY_Fdu_Cargo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131106L;

    /** Standard Constructor */
    public X_UY_Fdu_Cargo (Properties ctx, int UY_Fdu_Cargo_ID, String trxName)
    {
      super (ctx, UY_Fdu_Cargo_ID, trxName);
      /** if (UY_Fdu_Cargo_ID == 0)
        {
			setName (null);
			setUY_Fdu_Cargo_ID (0);
			setUY_FduCod_ID (0);
			setvalidity (new Timestamp( System.currentTimeMillis() ));
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_Cargo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_Cargo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set DiasAtraso.
		@param DiasAtraso 
		DiasAtraso
	  */
	public void setDiasAtraso (int DiasAtraso)
	{
		set_Value (COLUMNNAME_DiasAtraso, Integer.valueOf(DiasAtraso));
	}

	/** Get DiasAtraso.
		@return DiasAtraso
	  */
	public int getDiasAtraso () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasAtraso);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MoraEnAnio.
		@param MoraEnAnio 
		MoraEnAnio
	  */
	public void setMoraEnAnio (int MoraEnAnio)
	{
		set_Value (COLUMNNAME_MoraEnAnio, Integer.valueOf(MoraEnAnio));
	}

	/** Get MoraEnAnio.
		@return MoraEnAnio
	  */
	public int getMoraEnAnio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MoraEnAnio);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set UY_Fdu_Cargo.
		@param UY_Fdu_Cargo_ID UY_Fdu_Cargo	  */
	public void setUY_Fdu_Cargo_ID (int UY_Fdu_Cargo_ID)
	{
		if (UY_Fdu_Cargo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Cargo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Cargo_ID, Integer.valueOf(UY_Fdu_Cargo_ID));
	}

	/** Get UY_Fdu_Cargo.
		@return UY_Fdu_Cargo	  */
	public int getUY_Fdu_Cargo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Cargo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FduCod_ID.
		@param UY_FduCod_ID 
		UY_FduCod_ID
	  */
	public void setUY_FduCod_ID (int UY_FduCod_ID)
	{
		if (UY_FduCod_ID < 1) 
			set_Value (COLUMNNAME_UY_FduCod_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduCod_ID, Integer.valueOf(UY_FduCod_ID));
	}

	/** Get UY_FduCod_ID.
		@return UY_FduCod_ID
	  */
	public int getUY_FduCod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduCod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set validity.
		@param validity validity	  */
	public void setvalidity (Timestamp validity)
	{
		set_Value (COLUMNNAME_validity, validity);
	}

	/** Get validity.
		@return validity	  */
	public Timestamp getvalidity () 
	{
		return (Timestamp)get_Value(COLUMNNAME_validity);
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