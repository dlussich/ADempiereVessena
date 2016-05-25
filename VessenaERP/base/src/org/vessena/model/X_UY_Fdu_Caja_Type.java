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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_Fdu_Caja_Type
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_Caja_Type extends PO implements I_UY_Fdu_Caja_Type, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140402L;

    /** Standard Constructor */
    public X_UY_Fdu_Caja_Type (Properties ctx, int UY_Fdu_Caja_Type_ID, String trxName)
    {
      super (ctx, UY_Fdu_Caja_Type_ID, trxName);
      /** if (UY_Fdu_Caja_Type_ID == 0)
        {
			setName (null);
			setUY_Fdu_Caja_Type_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_Caja_Type (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_Caja_Type[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set concept_mn.
		@param concept_mn concept_mn	  */
	public void setconcept_mn (String concept_mn)
	{
		set_Value (COLUMNNAME_concept_mn, concept_mn);
	}

	/** Get concept_mn.
		@return concept_mn	  */
	public String getconcept_mn () 
	{
		return (String)get_Value(COLUMNNAME_concept_mn);
	}

	/** Set concept_usd.
		@param concept_usd concept_usd	  */
	public void setconcept_usd (String concept_usd)
	{
		set_Value (COLUMNNAME_concept_usd, concept_usd);
	}

	/** Get concept_usd.
		@return concept_usd	  */
	public String getconcept_usd () 
	{
		return (String)get_Value(COLUMNNAME_concept_usd);
	}

	/** Set descripcion.
		@param descripcion descripcion	  */
	public void setdescripcion (String descripcion)
	{
		set_Value (COLUMNNAME_descripcion, descripcion);
	}

	/** Get descripcion.
		@return descripcion	  */
	public String getdescripcion () 
	{
		return (String)get_Value(COLUMNNAME_descripcion);
	}

	/** Set Cancelled.
		@param IsCancelled 
		The transaction was cancelled
	  */
	public void setIsCancelled (boolean IsCancelled)
	{
		set_Value (COLUMNNAME_IsCancelled, Boolean.valueOf(IsCancelled));
	}

	/** Get Cancelled.
		@return The transaction was cancelled
	  */
	public boolean isCancelled () 
	{
		Object oo = get_Value(COLUMNNAME_IsCancelled);
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

	/** Set UY_Fdu_Caja_Type.
		@param UY_Fdu_Caja_Type_ID UY_Fdu_Caja_Type	  */
	public void setUY_Fdu_Caja_Type_ID (int UY_Fdu_Caja_Type_ID)
	{
		if (UY_Fdu_Caja_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Caja_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Caja_Type_ID, Integer.valueOf(UY_Fdu_Caja_Type_ID));
	}

	/** Get UY_Fdu_Caja_Type.
		@return UY_Fdu_Caja_Type	  */
	public int getUY_Fdu_Caja_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Caja_Type_ID);
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