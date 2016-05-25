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

/** Generated Model for UY_TR_ConfigCrt
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ConfigCrt extends PO implements I_UY_TR_ConfigCrt, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150518L;

    /** Standard Constructor */
    public X_UY_TR_ConfigCrt (Properties ctx, int UY_TR_ConfigCrt_ID, String trxName)
    {
      super (ctx, UY_TR_ConfigCrt_ID, trxName);
      /** if (UY_TR_ConfigCrt_ID == 0)
        {
			setIsPermit (false);
// N
			setIsPesoNeto (false);
// N
			setIsValueIncluded (false);
// N
			setUY_TR_ConfigCrt_ID (0);
			setUY_TR_Config_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ConfigCrt (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ConfigCrt[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set IsPermit.
		@param IsPermit IsPermit	  */
	public void setIsPermit (boolean IsPermit)
	{
		set_Value (COLUMNNAME_IsPermit, Boolean.valueOf(IsPermit));
	}

	/** Get IsPermit.
		@return IsPermit	  */
	public boolean isPermit () 
	{
		Object oo = get_Value(COLUMNNAME_IsPermit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPesoNeto.
		@param IsPesoNeto IsPesoNeto	  */
	public void setIsPesoNeto (boolean IsPesoNeto)
	{
		set_Value (COLUMNNAME_IsPesoNeto, Boolean.valueOf(IsPesoNeto));
	}

	/** Get IsPesoNeto.
		@return IsPesoNeto	  */
	public boolean isPesoNeto () 
	{
		Object oo = get_Value(COLUMNNAME_IsPesoNeto);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsValueIncluded.
		@param IsValueIncluded IsValueIncluded	  */
	public void setIsValueIncluded (boolean IsValueIncluded)
	{
		set_Value (COLUMNNAME_IsValueIncluded, Boolean.valueOf(IsValueIncluded));
	}

	/** Get IsValueIncluded.
		@return IsValueIncluded	  */
	public boolean isValueIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsValueIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	/** Set UY_TR_ConfigCrt.
		@param UY_TR_ConfigCrt_ID UY_TR_ConfigCrt	  */
	public void setUY_TR_ConfigCrt_ID (int UY_TR_ConfigCrt_ID)
	{
		if (UY_TR_ConfigCrt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigCrt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigCrt_ID, Integer.valueOf(UY_TR_ConfigCrt_ID));
	}

	/** Get UY_TR_ConfigCrt.
		@return UY_TR_ConfigCrt	  */
	public int getUY_TR_ConfigCrt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ConfigCrt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}