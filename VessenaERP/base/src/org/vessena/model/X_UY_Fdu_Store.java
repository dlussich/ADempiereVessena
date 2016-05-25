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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_Fdu_Store
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_Store extends PO implements I_UY_Fdu_Store, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121120L;

    /** Standard Constructor */
    public X_UY_Fdu_Store (Properties ctx, int UY_Fdu_Store_ID, String trxName)
    {
      super (ctx, UY_Fdu_Store_ID, trxName);
      /** if (UY_Fdu_Store_ID == 0)
        {
			setName (null);
			setUY_Fdu_Store_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_Store (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_Store[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set C_ElementValue_ID_Cr.
		@param C_ElementValue_ID_Cr 
		C_ElementValue_ID_Cr
	  */
	public void setC_ElementValue_ID_Cr (int C_ElementValue_ID_Cr)
	{
		set_Value (COLUMNNAME_C_ElementValue_ID_Cr, Integer.valueOf(C_ElementValue_ID_Cr));
	}

	/** Get C_ElementValue_ID_Cr.
		@return C_ElementValue_ID_Cr
	  */
	public int getC_ElementValue_ID_Cr () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID_Cr);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_ElementValue_ID_Cr2.
		@param C_ElementValue_ID_Cr2 
		C_ElementValue_ID_Cr2
	  */
	public void setC_ElementValue_ID_Cr2 (int C_ElementValue_ID_Cr2)
	{
		set_Value (COLUMNNAME_C_ElementValue_ID_Cr2, Integer.valueOf(C_ElementValue_ID_Cr2));
	}

	/** Get C_ElementValue_ID_Cr2.
		@return C_ElementValue_ID_Cr2
	  */
	public int getC_ElementValue_ID_Cr2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID_Cr2);
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

	/** Set Rate.
		@param Rate 
		Rate or Tax or Exchange
	  */
	public void setRate (BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	/** Get Rate.
		@return Rate or Tax or Exchange
	  */
	public BigDecimal getRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_FduFile getUY_FduFile() throws RuntimeException
    {
		return (I_UY_FduFile)MTable.get(getCtx(), I_UY_FduFile.Table_Name)
			.getPO(getUY_FduFile_ID(), get_TrxName());	}

	/** Set UY_FduFile_ID.
		@param UY_FduFile_ID UY_FduFile_ID	  */
	public void setUY_FduFile_ID (int UY_FduFile_ID)
	{
		if (UY_FduFile_ID < 1) 
			set_Value (COLUMNNAME_UY_FduFile_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduFile_ID, Integer.valueOf(UY_FduFile_ID));
	}

	/** Get UY_FduFile_ID.
		@return UY_FduFile_ID	  */
	public int getUY_FduFile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduFile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_Store.
		@param UY_Fdu_Store_ID UY_Fdu_Store	  */
	public void setUY_Fdu_Store_ID (int UY_Fdu_Store_ID)
	{
		if (UY_Fdu_Store_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Store_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Store_ID, Integer.valueOf(UY_Fdu_Store_ID));
	}

	/** Get UY_Fdu_Store.
		@return UY_Fdu_Store	  */
	public int getUY_Fdu_Store_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Store_ID);
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