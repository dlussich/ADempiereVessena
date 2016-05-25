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

/** Generated Model for UY_Gerencial
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Gerencial extends PO implements I_UY_Gerencial, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130722L;

    /** Standard Constructor */
    public X_UY_Gerencial (Properties ctx, int UY_Gerencial_ID, String trxName)
    {
      super (ctx, UY_Gerencial_ID, trxName);
      /** if (UY_Gerencial_ID == 0)
        {
			setProcessed (false);
			setUseCache (true);
// Y
			setUY_Gerencial_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Gerencial (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Gerencial[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_Period_ID_From.
		@param C_Period_ID_From C_Period_ID_From	  */
	public void setC_Period_ID_From (int C_Period_ID_From)
	{
		set_Value (COLUMNNAME_C_Period_ID_From, Integer.valueOf(C_Period_ID_From));
	}

	/** Get C_Period_ID_From.
		@return C_Period_ID_From	  */
	public int getC_Period_ID_From () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID_From);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Period_ID_To.
		@param C_Period_ID_To C_Period_ID_To	  */
	public void setC_Period_ID_To (int C_Period_ID_To)
	{
		set_Value (COLUMNNAME_C_Period_ID_To, Integer.valueOf(C_Period_ID_To));
	}

	/** Get C_Period_ID_To.
		@return C_Period_ID_To	  */
	public int getC_Period_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CurrencyType AD_Reference_ID=1000217 */
	public static final int CURRENCYTYPE_AD_Reference_ID=1000217;
	/** Moneda Nacional = MN */
	public static final String CURRENCYTYPE_MonedaNacional = "MN";
	/** Dolares = ME */
	public static final String CURRENCYTYPE_Dolares = "ME";
	/** Miles de Dolares = MM */
	public static final String CURRENCYTYPE_MilesDeDolares = "MM";
	/** Set Currency Type.
		@param CurrencyType Currency Type	  */
	public void setCurrencyType (String CurrencyType)
	{

		set_Value (COLUMNNAME_CurrencyType, CurrencyType);
	}

	/** Get Currency Type.
		@return Currency Type	  */
	public String getCurrencyType () 
	{
		return (String)get_Value(COLUMNNAME_CurrencyType);
	}

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set ExecuteAction2.
		@param ExecuteAction2 ExecuteAction2	  */
	public void setExecuteAction2 (String ExecuteAction2)
	{
		set_Value (COLUMNNAME_ExecuteAction2, ExecuteAction2);
	}

	/** Get ExecuteAction2.
		@return ExecuteAction2	  */
	public String getExecuteAction2 () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction2);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UseCache.
		@param UseCache UseCache	  */
	public void setUseCache (boolean UseCache)
	{
		set_Value (COLUMNNAME_UseCache, Boolean.valueOf(UseCache));
	}

	/** Get UseCache.
		@return UseCache	  */
	public boolean isUseCache () 
	{
		Object oo = get_Value(COLUMNNAME_UseCache);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_Gerencial.
		@param UY_Gerencial_ID UY_Gerencial	  */
	public void setUY_Gerencial_ID (int UY_Gerencial_ID)
	{
		if (UY_Gerencial_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Gerencial_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Gerencial_ID, Integer.valueOf(UY_Gerencial_ID));
	}

	/** Get UY_Gerencial.
		@return UY_Gerencial	  */
	public int getUY_Gerencial_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}