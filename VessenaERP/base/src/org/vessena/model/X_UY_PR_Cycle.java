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

/** Generated Model for UY_PR_Cycle
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PR_Cycle extends PO implements I_UY_PR_Cycle, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140904L;

    /** Standard Constructor */
    public X_UY_PR_Cycle (Properties ctx, int UY_PR_Cycle_ID, String trxName)
    {
      super (ctx, UY_PR_Cycle_ID, trxName);
      /** if (UY_PR_Cycle_ID == 0)
        {
			setCycleStatus (null);
// AGENDADO
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setDateTo (new Timestamp( System.currentTimeMillis() ));
			setOnSaturday (false);
// N
			setOnSunday (false);
// N
			setUY_PR_Cycle_ID (0);
			setUY_PR_Schedule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PR_Cycle (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PR_Cycle[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** CycleStatus AD_Reference_ID=1000374 */
	public static final int CYCLESTATUS_AD_Reference_ID=1000374;
	/** AGENDADO = AGENDADO */
	public static final String CYCLESTATUS_AGENDADO = "AGENDADO";
	/** EN PROCESO = EN PROCESO */
	public static final String CYCLESTATUS_ENPROCESO = "EN PROCESO";
	/** FINALIZADO = FINALIZADO */
	public static final String CYCLESTATUS_FINALIZADO = "FINALIZADO";
	/** CON DEMORAS = CON_DEMORAS */
	public static final String CYCLESTATUS_CONDEMORAS = "CON_DEMORAS";
	/** ATRASADO = ATRASADO */
	public static final String CYCLESTATUS_ATRASADO = "ATRASADO";
	/** Set CycleStatus.
		@param CycleStatus CycleStatus	  */
	public void setCycleStatus (String CycleStatus)
	{

		set_Value (COLUMNNAME_CycleStatus, CycleStatus);
	}

	/** Get CycleStatus.
		@return CycleStatus	  */
	public String getCycleStatus () 
	{
		return (String)get_Value(COLUMNNAME_CycleStatus);
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** Set Saturday.
		@param OnSaturday 
		Available on Saturday
	  */
	public void setOnSaturday (boolean OnSaturday)
	{
		set_Value (COLUMNNAME_OnSaturday, Boolean.valueOf(OnSaturday));
	}

	/** Get Saturday.
		@return Available on Saturday
	  */
	public boolean isOnSaturday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSaturday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sunday.
		@param OnSunday 
		Available on Sundays
	  */
	public void setOnSunday (boolean OnSunday)
	{
		set_Value (COLUMNNAME_OnSunday, Boolean.valueOf(OnSunday));
	}

	/** Get Sunday.
		@return Available on Sundays
	  */
	public boolean isOnSunday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSunday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_PR_Cycle.
		@param UY_PR_Cycle_ID UY_PR_Cycle	  */
	public void setUY_PR_Cycle_ID (int UY_PR_Cycle_ID)
	{
		if (UY_PR_Cycle_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PR_Cycle_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PR_Cycle_ID, Integer.valueOf(UY_PR_Cycle_ID));
	}

	/** Get UY_PR_Cycle.
		@return UY_PR_Cycle	  */
	public int getUY_PR_Cycle_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_Cycle_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PR_Schedule getUY_PR_Schedule() throws RuntimeException
    {
		return (I_UY_PR_Schedule)MTable.get(getCtx(), I_UY_PR_Schedule.Table_Name)
			.getPO(getUY_PR_Schedule_ID(), get_TrxName());	}

	/** Set UY_PR_Schedule.
		@param UY_PR_Schedule_ID UY_PR_Schedule	  */
	public void setUY_PR_Schedule_ID (int UY_PR_Schedule_ID)
	{
		if (UY_PR_Schedule_ID < 1) 
			set_Value (COLUMNNAME_UY_PR_Schedule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PR_Schedule_ID, Integer.valueOf(UY_PR_Schedule_ID));
	}

	/** Get UY_PR_Schedule.
		@return UY_PR_Schedule	  */
	public int getUY_PR_Schedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_Schedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}