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

/** Generated Model for UY_TR_Way
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Way extends PO implements I_UY_TR_Way, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150104L;

    /** Standard Constructor */
    public X_UY_TR_Way (Properties ctx, int UY_TR_Way_ID, String trxName)
    {
      super (ctx, UY_TR_Way_ID, trxName);
      /** if (UY_TR_Way_ID == 0)
        {
			setAD_Sequence_ID (0);
			setC_Country_ID (0);
			setC_Country_ID_1 (0);
			setIsPrintDeclaration (false);
// N
			setIsPrintExpo (false);
// N
			setM_Product_ID (0);
			setUY_Ciudad_ID (0);
			setUY_Ciudad_ID_1 (0);
			setUY_TR_Way_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Way (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Way[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Sequence getAD_Sequence() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Sequence)MTable.get(getCtx(), org.compiere.model.I_AD_Sequence.Table_Name)
			.getPO(getAD_Sequence_ID(), get_TrxName());	}

	/** Set Sequence.
		@param AD_Sequence_ID 
		Document Sequence
	  */
	public void setAD_Sequence_ID (int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Sequence_ID, Integer.valueOf(AD_Sequence_ID));
	}

	/** Get Sequence.
		@return Document Sequence
	  */
	public int getAD_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Sequence_ID_1.
		@param AD_Sequence_ID_1 AD_Sequence_ID_1	  */
	public void setAD_Sequence_ID_1 (int AD_Sequence_ID_1)
	{
		set_Value (COLUMNNAME_AD_Sequence_ID_1, Integer.valueOf(AD_Sequence_ID_1));
	}

	/** Get AD_Sequence_ID_1.
		@return AD_Sequence_ID_1	  */
	public int getAD_Sequence_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ID_1);
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

	/** Set C_Country_ID_1.
		@param C_Country_ID_1 C_Country_ID_1	  */
	public void setC_Country_ID_1 (int C_Country_ID_1)
	{
		set_Value (COLUMNNAME_C_Country_ID_1, Integer.valueOf(C_Country_ID_1));
	}

	/** Get C_Country_ID_1.
		@return C_Country_ID_1	  */
	public int getC_Country_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID_1);
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

	/** Set IsPrintDeclaration.
		@param IsPrintDeclaration IsPrintDeclaration	  */
	public void setIsPrintDeclaration (boolean IsPrintDeclaration)
	{
		set_Value (COLUMNNAME_IsPrintDeclaration, Boolean.valueOf(IsPrintDeclaration));
	}

	/** Get IsPrintDeclaration.
		@return IsPrintDeclaration	  */
	public boolean isPrintDeclaration () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrintDeclaration);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPrintExpo.
		@param IsPrintExpo IsPrintExpo	  */
	public void setIsPrintExpo (boolean IsPrintExpo)
	{
		set_Value (COLUMNNAME_IsPrintExpo, Boolean.valueOf(IsPrintExpo));
	}

	/** Get IsPrintExpo.
		@return IsPrintExpo	  */
	public boolean isPrintExpo () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrintExpo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (String LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, LocatorValue);
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public String getLocatorValue () 
	{
		return (String)get_Value(COLUMNNAME_LocatorValue);
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException
    {
		return (I_UY_Ciudad)MTable.get(getCtx(), I_UY_Ciudad.Table_Name)
			.getPO(getUY_Ciudad_ID(), get_TrxName());	}

	/** Set UY_Ciudad.
		@param UY_Ciudad_ID UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID)
	{
		if (UY_Ciudad_ID < 1) 
			set_Value (COLUMNNAME_UY_Ciudad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Ciudad_ID, Integer.valueOf(UY_Ciudad_ID));
	}

	/** Get UY_Ciudad.
		@return UY_Ciudad	  */
	public int getUY_Ciudad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Ciudad getUY_Ciudad_I() throws RuntimeException
    {
		return (I_UY_Ciudad)MTable.get(getCtx(), I_UY_Ciudad.Table_Name)
			.getPO(getUY_Ciudad_ID_1(), get_TrxName());	}

	/** Set UY_Ciudad_ID_1.
		@param UY_Ciudad_ID_1 UY_Ciudad_ID_1	  */
	public void setUY_Ciudad_ID_1 (int UY_Ciudad_ID_1)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_1, Integer.valueOf(UY_Ciudad_ID_1));
	}

	/** Get UY_Ciudad_ID_1.
		@return UY_Ciudad_ID_1	  */
	public int getUY_Ciudad_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Way.
		@param UY_TR_Way_ID UY_TR_Way	  */
	public void setUY_TR_Way_ID (int UY_TR_Way_ID)
	{
		if (UY_TR_Way_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Way_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Way_ID, Integer.valueOf(UY_TR_Way_ID));
	}

	/** Get UY_TR_Way.
		@return UY_TR_Way	  */
	public int getUY_TR_Way_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Way_ID);
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