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

/** Generated Model for UY_DiscountRule_Group
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DiscountRule_Group extends PO implements I_UY_DiscountRule_Group, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160405L;

    /** Standard Constructor */
    public X_UY_DiscountRule_Group (Properties ctx, int UY_DiscountRule_Group_ID, String trxName)
    {
      super (ctx, UY_DiscountRule_Group_ID, trxName);
      /** if (UY_DiscountRule_Group_ID == 0)
        {
			setName (null);
			setUY_DiscountRule_Group_ID (0);
			setUY_DiscountRule_Version_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DiscountRule_Group (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DiscountRule_Group[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set UY_DiscountRule_Group.
		@param UY_DiscountRule_Group_ID UY_DiscountRule_Group	  */
	public void setUY_DiscountRule_Group_ID (int UY_DiscountRule_Group_ID)
	{
		if (UY_DiscountRule_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DiscountRule_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DiscountRule_Group_ID, Integer.valueOf(UY_DiscountRule_Group_ID));
	}

	/** Get UY_DiscountRule_Group.
		@return UY_DiscountRule_Group	  */
	public int getUY_DiscountRule_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DiscountRule_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_DiscountRule_Version getUY_DiscountRule_Version() throws RuntimeException
    {
		return (I_UY_DiscountRule_Version)MTable.get(getCtx(), I_UY_DiscountRule_Version.Table_Name)
			.getPO(getUY_DiscountRule_Version_ID(), get_TrxName());	}

	/** Set UY_DiscountRule_Version.
		@param UY_DiscountRule_Version_ID UY_DiscountRule_Version	  */
	public void setUY_DiscountRule_Version_ID (int UY_DiscountRule_Version_ID)
	{
		if (UY_DiscountRule_Version_ID < 1) 
			set_Value (COLUMNNAME_UY_DiscountRule_Version_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DiscountRule_Version_ID, Integer.valueOf(UY_DiscountRule_Version_ID));
	}

	/** Get UY_DiscountRule_Version.
		@return UY_DiscountRule_Version	  */
	public int getUY_DiscountRule_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DiscountRule_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Familia getUY_Familia() throws RuntimeException
    {
		return (I_UY_Familia)MTable.get(getCtx(), I_UY_Familia.Table_Name)
			.getPO(getUY_Familia_ID(), get_TrxName());	}

	/** Set UY_Familia.
		@param UY_Familia_ID UY_Familia	  */
	public void setUY_Familia_ID (int UY_Familia_ID)
	{
		if (UY_Familia_ID < 1) 
			set_Value (COLUMNNAME_UY_Familia_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Familia_ID, Integer.valueOf(UY_Familia_ID));
	}

	/** Get UY_Familia.
		@return UY_Familia	  */
	public int getUY_Familia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Familia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Linea_Negocio getUY_Linea_Negocio() throws RuntimeException
    {
		return (I_UY_Linea_Negocio)MTable.get(getCtx(), I_UY_Linea_Negocio.Table_Name)
			.getPO(getUY_Linea_Negocio_ID(), get_TrxName());	}

	/** Set UY_Linea_Negocio_ID.
		@param UY_Linea_Negocio_ID UY_Linea_Negocio_ID	  */
	public void setUY_Linea_Negocio_ID (int UY_Linea_Negocio_ID)
	{
		if (UY_Linea_Negocio_ID < 1) 
			set_Value (COLUMNNAME_UY_Linea_Negocio_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Linea_Negocio_ID, Integer.valueOf(UY_Linea_Negocio_ID));
	}

	/** Get UY_Linea_Negocio_ID.
		@return UY_Linea_Negocio_ID	  */
	public int getUY_Linea_Negocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Linea_Negocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ProductGroup getUY_ProductGroup() throws RuntimeException
    {
		return (I_UY_ProductGroup)MTable.get(getCtx(), I_UY_ProductGroup.Table_Name)
			.getPO(getUY_ProductGroup_ID(), get_TrxName());	}

	/** Set UY_ProductGroup.
		@param UY_ProductGroup_ID UY_ProductGroup	  */
	public void setUY_ProductGroup_ID (int UY_ProductGroup_ID)
	{
		if (UY_ProductGroup_ID < 1) 
			set_Value (COLUMNNAME_UY_ProductGroup_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProductGroup_ID, Integer.valueOf(UY_ProductGroup_ID));
	}

	/** Get UY_ProductGroup.
		@return UY_ProductGroup	  */
	public int getUY_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProductGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_SubFamilia getUY_SubFamilia() throws RuntimeException
    {
		return (I_UY_SubFamilia)MTable.get(getCtx(), I_UY_SubFamilia.Table_Name)
			.getPO(getUY_SubFamilia_ID(), get_TrxName());	}

	/** Set UY_SubFamilia.
		@param UY_SubFamilia_ID UY_SubFamilia	  */
	public void setUY_SubFamilia_ID (int UY_SubFamilia_ID)
	{
		if (UY_SubFamilia_ID < 1) 
			set_Value (COLUMNNAME_UY_SubFamilia_ID, null);
		else 
			set_Value (COLUMNNAME_UY_SubFamilia_ID, Integer.valueOf(UY_SubFamilia_ID));
	}

	/** Get UY_SubFamilia.
		@return UY_SubFamilia	  */
	public int getUY_SubFamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SubFamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}