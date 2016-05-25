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

/** Generated Model for UY_PR_SchTaskMonth
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PR_SchTaskMonth extends PO implements I_UY_PR_SchTaskMonth, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140903L;

    /** Standard Constructor */
    public X_UY_PR_SchTaskMonth (Properties ctx, int UY_PR_SchTaskMonth_ID, String trxName)
    {
      super (ctx, UY_PR_SchTaskMonth_ID, trxName);
      /** if (UY_PR_SchTaskMonth_ID == 0)
        {
			setDayNo (0);
			setDayType (null);
// DIAS_HABILES
			setTaskHour (new Timestamp( System.currentTimeMillis() ));
			setUY_PR_SchTask_ID (0);
			setUY_PR_SchTaskMonth_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PR_SchTaskMonth (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PR_SchTaskMonth[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Condition.
		@param Condition 
		Condition
	  */
	public void setCondition (String Condition)
	{
		set_Value (COLUMNNAME_Condition, Condition);
	}

	/** Get Condition.
		@return Condition
	  */
	public String getCondition () 
	{
		return (String)get_Value(COLUMNNAME_Condition);
	}

	/** Set DayNo.
		@param DayNo 
		DayNo
	  */
	public void setDayNo (int DayNo)
	{
		set_Value (COLUMNNAME_DayNo, Integer.valueOf(DayNo));
	}

	/** Get DayNo.
		@return DayNo
	  */
	public int getDayNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DayNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** DayType AD_Reference_ID=1000372 */
	public static final int DAYTYPE_AD_Reference_ID=1000372;
	/** DIAS HABILES = DIAS_HABILES */
	public static final String DAYTYPE_DIASHABILES = "DIAS_HABILES";
	/** DIAS CORRIDOS = DIAS_CORRIDOS */
	public static final String DAYTYPE_DIASCORRIDOS = "DIAS_CORRIDOS";
	/** Set DayType.
		@param DayType DayType	  */
	public void setDayType (String DayType)
	{

		set_Value (COLUMNNAME_DayType, DayType);
	}

	/** Get DayType.
		@return DayType	  */
	public String getDayType () 
	{
		return (String)get_Value(COLUMNNAME_DayType);
	}

	/** Set TaskHour.
		@param TaskHour 
		TaskHour
	  */
	public void setTaskHour (Timestamp TaskHour)
	{
		set_Value (COLUMNNAME_TaskHour, TaskHour);
	}

	/** Get TaskHour.
		@return TaskHour
	  */
	public Timestamp getTaskHour () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TaskHour);
	}

	public I_UY_PR_SchTask getUY_PR_SchTask() throws RuntimeException
    {
		return (I_UY_PR_SchTask)MTable.get(getCtx(), I_UY_PR_SchTask.Table_Name)
			.getPO(getUY_PR_SchTask_ID(), get_TrxName());	}

	/** Set UY_PR_SchTask.
		@param UY_PR_SchTask_ID UY_PR_SchTask	  */
	public void setUY_PR_SchTask_ID (int UY_PR_SchTask_ID)
	{
		if (UY_PR_SchTask_ID < 1) 
			set_Value (COLUMNNAME_UY_PR_SchTask_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PR_SchTask_ID, Integer.valueOf(UY_PR_SchTask_ID));
	}

	/** Get UY_PR_SchTask.
		@return UY_PR_SchTask	  */
	public int getUY_PR_SchTask_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_SchTask_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PR_SchTaskMonth.
		@param UY_PR_SchTaskMonth_ID UY_PR_SchTaskMonth	  */
	public void setUY_PR_SchTaskMonth_ID (int UY_PR_SchTaskMonth_ID)
	{
		if (UY_PR_SchTaskMonth_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PR_SchTaskMonth_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PR_SchTaskMonth_ID, Integer.valueOf(UY_PR_SchTaskMonth_ID));
	}

	/** Get UY_PR_SchTaskMonth.
		@return UY_PR_SchTaskMonth	  */
	public int getUY_PR_SchTaskMonth_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_SchTaskMonth_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}