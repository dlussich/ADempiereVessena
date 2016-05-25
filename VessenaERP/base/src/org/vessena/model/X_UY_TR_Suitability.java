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

/** Generated Model for UY_TR_Suitability
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Suitability extends PO implements I_UY_TR_Suitability, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150101L;

    /** Standard Constructor */
    public X_UY_TR_Suitability (Properties ctx, int UY_TR_Suitability_ID, String trxName)
    {
      super (ctx, UY_TR_Suitability_ID, trxName);
      /** if (UY_TR_Suitability_ID == 0)
        {
			setC_Country_ID (0);
			setC_Country_ID_1 (0);
			setCode (null);
			setUY_TR_Config_ID (0);
			setUY_TR_Suitability_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Suitability (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Suitability[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Validation code.
		@param Code 
		Validation Code
	  */
	public void setCode (String Code)
	{
		set_Value (COLUMNNAME_Code, Code);
	}

	/** Get Validation code.
		@return Validation Code
	  */
	public String getCode () 
	{
		return (String)get_Value(COLUMNNAME_Code);
	}

	public I_UY_TR_Config getUY_TR_Config() throws RuntimeException
    {
		return (I_UY_TR_Config)MTable.get(getCtx(), I_UY_TR_Config.Table_Name)
			.getPO(getUY_TR_Config_ID(), get_TrxName());	}

	/** Set UY_TR_Config.
		@param UY_TR_Config_ID UY_TR_Config	  */
	public void setUY_TR_Config_ID (int UY_TR_Config_ID)
	{
		if (UY_TR_Config_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Config_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Config_ID, Integer.valueOf(UY_TR_Config_ID));
	}

	/** Get UY_TR_Config.
		@return UY_TR_Config	  */
	public int getUY_TR_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Suitability.
		@param UY_TR_Suitability_ID UY_TR_Suitability	  */
	public void setUY_TR_Suitability_ID (int UY_TR_Suitability_ID)
	{
		if (UY_TR_Suitability_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Suitability_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Suitability_ID, Integer.valueOf(UY_TR_Suitability_ID));
	}

	/** Get UY_TR_Suitability.
		@return UY_TR_Suitability	  */
	public int getUY_TR_Suitability_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Suitability_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}