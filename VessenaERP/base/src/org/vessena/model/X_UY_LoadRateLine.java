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

/** Generated Model for UY_LoadRateLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_LoadRateLine extends PO implements I_UY_LoadRateLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140714L;

    /** Standard Constructor */
    public X_UY_LoadRateLine (Properties ctx, int UY_LoadRateLine_ID, String trxName)
    {
      super (ctx, UY_LoadRateLine_ID, trxName);
      /** if (UY_LoadRateLine_ID == 0)
        {
			setC_Currency_ID (0);
			setCode (null);
			setUY_LoadRate_ID (0);
			setUY_LoadRateLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_LoadRateLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_LoadRateLine[")
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

	public I_UY_LoadRate getUY_LoadRate() throws RuntimeException
    {
		return (I_UY_LoadRate)MTable.get(getCtx(), I_UY_LoadRate.Table_Name)
			.getPO(getUY_LoadRate_ID(), get_TrxName());	}

	/** Set UY_LoadRate.
		@param UY_LoadRate_ID UY_LoadRate	  */
	public void setUY_LoadRate_ID (int UY_LoadRate_ID)
	{
		if (UY_LoadRate_ID < 1) 
			set_Value (COLUMNNAME_UY_LoadRate_ID, null);
		else 
			set_Value (COLUMNNAME_UY_LoadRate_ID, Integer.valueOf(UY_LoadRate_ID));
	}

	/** Get UY_LoadRate.
		@return UY_LoadRate	  */
	public int getUY_LoadRate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LoadRate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_LoadRateLine.
		@param UY_LoadRateLine_ID UY_LoadRateLine	  */
	public void setUY_LoadRateLine_ID (int UY_LoadRateLine_ID)
	{
		if (UY_LoadRateLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_LoadRateLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_LoadRateLine_ID, Integer.valueOf(UY_LoadRateLine_ID));
	}

	/** Get UY_LoadRateLine.
		@return UY_LoadRateLine	  */
	public int getUY_LoadRateLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LoadRateLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}