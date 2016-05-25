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

/** Generated Model for UY_TR_ConfigClearing
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ConfigClearing extends PO implements I_UY_TR_ConfigClearing, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150609L;

    /** Standard Constructor */
    public X_UY_TR_ConfigClearing (Properties ctx, int UY_TR_ConfigClearing_ID, String trxName)
    {
      super (ctx, UY_TR_ConfigClearing_ID, trxName);
      /** if (UY_TR_ConfigClearing_ID == 0)
        {
			setIsSaldoInicial (false);
// N
			setUY_TR_Config_ID (0);
			setUY_TR_ConfigClearing_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ConfigClearing (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ConfigClearing[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IsSaldoInicial.
		@param IsSaldoInicial IsSaldoInicial	  */
	public void setIsSaldoInicial (boolean IsSaldoInicial)
	{
		set_Value (COLUMNNAME_IsSaldoInicial, Boolean.valueOf(IsSaldoInicial));
	}

	/** Get IsSaldoInicial.
		@return IsSaldoInicial	  */
	public boolean isSaldoInicial () 
	{
		Object oo = get_Value(COLUMNNAME_IsSaldoInicial);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set UY_TR_ConfigClearing.
		@param UY_TR_ConfigClearing_ID UY_TR_ConfigClearing	  */
	public void setUY_TR_ConfigClearing_ID (int UY_TR_ConfigClearing_ID)
	{
		if (UY_TR_ConfigClearing_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigClearing_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigClearing_ID, Integer.valueOf(UY_TR_ConfigClearing_ID));
	}

	/** Get UY_TR_ConfigClearing.
		@return UY_TR_ConfigClearing	  */
	public int getUY_TR_ConfigClearing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ConfigClearing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}