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

/** Generated Model for UY_Fdu_Caja_Type_Line
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_Caja_Type_Line extends PO implements I_UY_Fdu_Caja_Type_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140402L;

    /** Standard Constructor */
    public X_UY_Fdu_Caja_Type_Line (Properties ctx, int UY_Fdu_Caja_Type_Line_ID, String trxName)
    {
      super (ctx, UY_Fdu_Caja_Type_Line_ID, trxName);
      /** if (UY_Fdu_Caja_Type_Line_ID == 0)
        {
			setUY_Fdu_Caja_Type_ID (0);
			setUY_Fdu_Caja_Type_Line_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_Caja_Type_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_Caja_Type_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set c_elementvalue_id_2.
		@param c_elementvalue_id_2 c_elementvalue_id_2	  */
	public void setc_elementvalue_id_2 (int c_elementvalue_id_2)
	{
		set_Value (COLUMNNAME_c_elementvalue_id_2, Integer.valueOf(c_elementvalue_id_2));
	}

	/** Get c_elementvalue_id_2.
		@return c_elementvalue_id_2	  */
	public int getc_elementvalue_id_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_c_elementvalue_id_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set c_elementvalue_id_3.
		@param c_elementvalue_id_3 c_elementvalue_id_3	  */
	public void setc_elementvalue_id_3 (int c_elementvalue_id_3)
	{
		set_Value (COLUMNNAME_c_elementvalue_id_3, Integer.valueOf(c_elementvalue_id_3));
	}

	/** Get c_elementvalue_id_3.
		@return c_elementvalue_id_3	  */
	public int getc_elementvalue_id_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_c_elementvalue_id_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set c_elementvalue_id_4.
		@param c_elementvalue_id_4 c_elementvalue_id_4	  */
	public void setc_elementvalue_id_4 (int c_elementvalue_id_4)
	{
		set_Value (COLUMNNAME_c_elementvalue_id_4, Integer.valueOf(c_elementvalue_id_4));
	}

	/** Get c_elementvalue_id_4.
		@return c_elementvalue_id_4	  */
	public int getc_elementvalue_id_4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_c_elementvalue_id_4);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set IsTotalizador.
		@param IsTotalizador 
		IsTotalizador
	  */
	public void setIsTotalizador (boolean IsTotalizador)
	{
		set_Value (COLUMNNAME_IsTotalizador, Boolean.valueOf(IsTotalizador));
	}

	/** Get IsTotalizador.
		@return IsTotalizador
	  */
	public boolean isTotalizador () 
	{
		Object oo = get_Value(COLUMNNAME_IsTotalizador);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsTotalizadorDEBE.
		@param IsTotalizadorDEBE 
		IsTotalizadorDEBE
	  */
	public void setIsTotalizadorDEBE (boolean IsTotalizadorDEBE)
	{
		set_Value (COLUMNNAME_IsTotalizadorDEBE, Boolean.valueOf(IsTotalizadorDEBE));
	}

	/** Get IsTotalizadorDEBE.
		@return IsTotalizadorDEBE
	  */
	public boolean isTotalizadorDEBE () 
	{
		Object oo = get_Value(COLUMNNAME_IsTotalizadorDEBE);
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

	public I_UY_Fdu_Caja_Type getUY_Fdu_Caja_Type() throws RuntimeException
    {
		return (I_UY_Fdu_Caja_Type)MTable.get(getCtx(), I_UY_Fdu_Caja_Type.Table_Name)
			.getPO(getUY_Fdu_Caja_Type_ID(), get_TrxName());	}

	/** Set UY_Fdu_Caja_Type.
		@param UY_Fdu_Caja_Type_ID UY_Fdu_Caja_Type	  */
	public void setUY_Fdu_Caja_Type_ID (int UY_Fdu_Caja_Type_ID)
	{
		if (UY_Fdu_Caja_Type_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Caja_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Caja_Type_ID, Integer.valueOf(UY_Fdu_Caja_Type_ID));
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

	/** Set UY_Fdu_Caja_Type_Line.
		@param UY_Fdu_Caja_Type_Line_ID UY_Fdu_Caja_Type_Line	  */
	public void setUY_Fdu_Caja_Type_Line_ID (int UY_Fdu_Caja_Type_Line_ID)
	{
		if (UY_Fdu_Caja_Type_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Caja_Type_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Caja_Type_Line_ID, Integer.valueOf(UY_Fdu_Caja_Type_Line_ID));
	}

	/** Get UY_Fdu_Caja_Type_Line.
		@return UY_Fdu_Caja_Type_Line	  */
	public int getUY_Fdu_Caja_Type_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Caja_Type_Line_ID);
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