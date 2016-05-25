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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_Fdu_LogisticMonthDates
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_LogisticMonthDates extends PO implements I_UY_Fdu_LogisticMonthDates, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130213L;

    /** Standard Constructor */
    public X_UY_Fdu_LogisticMonthDates (Properties ctx, int UY_Fdu_LogisticMonthDates_ID, String trxName)
    {
      super (ctx, UY_Fdu_LogisticMonthDates_ID, trxName);
      /** if (UY_Fdu_LogisticMonthDates_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_Fdu_LogisticMonthDates_ID (0);
			setUY_Fdu_LogisticMonth_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_LogisticMonthDates (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_LogisticMonthDates[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set FechaString.
		@param FechaString 
		FechaString
	  */
	public void setFechaString (String FechaString)
	{
		set_Value (COLUMNNAME_FechaString, FechaString);
	}

	/** Get FechaString.
		@return FechaString
	  */
	public String getFechaString () 
	{
		return (String)get_Value(COLUMNNAME_FechaString);
	}

	public I_UY_Fdu_GrupoCC getUY_Fdu_GrupoCC() throws RuntimeException
    {
		return (I_UY_Fdu_GrupoCC)MTable.get(getCtx(), I_UY_Fdu_GrupoCC.Table_Name)
			.getPO(getUY_Fdu_GrupoCC_ID(), get_TrxName());	}

	/** Set UY_Fdu_GrupoCC.
		@param UY_Fdu_GrupoCC_ID UY_Fdu_GrupoCC	  */
	public void setUY_Fdu_GrupoCC_ID (int UY_Fdu_GrupoCC_ID)
	{
		if (UY_Fdu_GrupoCC_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_GrupoCC_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_GrupoCC_ID, Integer.valueOf(UY_Fdu_GrupoCC_ID));
	}

	/** Get UY_Fdu_GrupoCC.
		@return UY_Fdu_GrupoCC	  */
	public int getUY_Fdu_GrupoCC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_GrupoCC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_LogisticMonthDates.
		@param UY_Fdu_LogisticMonthDates_ID UY_Fdu_LogisticMonthDates	  */
	public void setUY_Fdu_LogisticMonthDates_ID (int UY_Fdu_LogisticMonthDates_ID)
	{
		if (UY_Fdu_LogisticMonthDates_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_LogisticMonthDates_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_LogisticMonthDates_ID, Integer.valueOf(UY_Fdu_LogisticMonthDates_ID));
	}

	/** Get UY_Fdu_LogisticMonthDates.
		@return UY_Fdu_LogisticMonthDates	  */
	public int getUY_Fdu_LogisticMonthDates_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_LogisticMonthDates_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_LogisticMonth getUY_Fdu_LogisticMonth() throws RuntimeException
    {
		return (I_UY_Fdu_LogisticMonth)MTable.get(getCtx(), I_UY_Fdu_LogisticMonth.Table_Name)
			.getPO(getUY_Fdu_LogisticMonth_ID(), get_TrxName());	}

	/** Set UY_Fdu_LogisticMonth.
		@param UY_Fdu_LogisticMonth_ID UY_Fdu_LogisticMonth	  */
	public void setUY_Fdu_LogisticMonth_ID (int UY_Fdu_LogisticMonth_ID)
	{
		if (UY_Fdu_LogisticMonth_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_LogisticMonth_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_LogisticMonth_ID, Integer.valueOf(UY_Fdu_LogisticMonth_ID));
	}

	/** Get UY_Fdu_LogisticMonth.
		@return UY_Fdu_LogisticMonth	  */
	public int getUY_Fdu_LogisticMonth_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_LogisticMonth_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}