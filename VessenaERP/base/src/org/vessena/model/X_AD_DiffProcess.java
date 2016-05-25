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

/** Generated Model for AD_DiffProcess
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_DiffProcess extends PO implements I_AD_DiffProcess, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_DiffProcess (Properties ctx, int AD_DiffProcess_ID, String trxName)
    {
      super (ctx, AD_DiffProcess_ID, trxName);
      /** if (AD_DiffProcess_ID == 0)
        {
			setAD_DiffProcess_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_DiffProcess (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_AD_DiffProcess[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Active Dictionary Difference Process.
		@param AD_DiffProcess_ID Active Dictionary Difference Process	  */
	public void setAD_DiffProcess_ID (int AD_DiffProcess_ID)
	{
		if (AD_DiffProcess_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_DiffProcess_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_DiffProcess_ID, Integer.valueOf(AD_DiffProcess_ID));
	}

	/** Get Active Dictionary Difference Process.
		@return Active Dictionary Difference Process	  */
	public int getAD_DiffProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_DiffProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Version.
		@param Version 
		Version of the table definition
	  */
	public void setVersion (BigDecimal Version)
	{
		set_Value (COLUMNNAME_Version, Version);
	}

	/** Get Version.
		@return Version of the table definition
	  */
	public BigDecimal getVersion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Version);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}