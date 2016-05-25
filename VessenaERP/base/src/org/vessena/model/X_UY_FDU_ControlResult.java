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

/** Generated Model for UY_FDU_ControlResult
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_FDU_ControlResult extends PO implements I_UY_FDU_ControlResult, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130115L;

    /** Standard Constructor */
    public X_UY_FDU_ControlResult (Properties ctx, int UY_FDU_ControlResult_ID, String trxName)
    {
      super (ctx, UY_FDU_ControlResult_ID, trxName);
      /** if (UY_FDU_ControlResult_ID == 0)
        {
			setreprocessing (false);
			setSuccess (false);
			setUY_FDU_Control_ID (0);
			setUY_FDU_ControlProcess_ID (0);
			setUY_FDU_ControlResult_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FDU_ControlResult (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FDU_ControlResult[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set codigo.
		@param codigo codigo	  */
	public void setcodigo (String codigo)
	{
		set_Value (COLUMNNAME_codigo, codigo);
	}

	/** Get codigo.
		@return codigo	  */
	public String getcodigo () 
	{
		return (String)get_Value(COLUMNNAME_codigo);
	}

	/** Set Message.
		@param Message 
		EMail Message
	  */
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return EMail Message
	  */
	public String getMessage () 
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	/** Set reprocessing.
		@param reprocessing reprocessing	  */
	public void setreprocessing (boolean reprocessing)
	{
		set_Value (COLUMNNAME_reprocessing, Boolean.valueOf(reprocessing));
	}

	/** Get reprocessing.
		@return reprocessing	  */
	public boolean isreprocessing () 
	{
		Object oo = get_Value(COLUMNNAME_reprocessing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Success.
		@param Success Success	  */
	public void setSuccess (boolean Success)
	{
		set_Value (COLUMNNAME_Success, Boolean.valueOf(Success));
	}

	/** Get Success.
		@return Success	  */
	public boolean isSuccess () 
	{
		Object oo = get_Value(COLUMNNAME_Success);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_FDU_Control getUY_FDU_Control() throws RuntimeException
    {
		return (I_UY_FDU_Control)MTable.get(getCtx(), I_UY_FDU_Control.Table_Name)
			.getPO(getUY_FDU_Control_ID(), get_TrxName());	}

	/** Set UY_FDU_Control.
		@param UY_FDU_Control_ID UY_FDU_Control	  */
	public void setUY_FDU_Control_ID (int UY_FDU_Control_ID)
	{
		if (UY_FDU_Control_ID < 1) 
			set_Value (COLUMNNAME_UY_FDU_Control_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FDU_Control_ID, Integer.valueOf(UY_FDU_Control_ID));
	}

	/** Get UY_FDU_Control.
		@return UY_FDU_Control	  */
	public int getUY_FDU_Control_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_Control_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FDU_ControlProcess getUY_FDU_ControlProcess() throws RuntimeException
    {
		return (I_UY_FDU_ControlProcess)MTable.get(getCtx(), I_UY_FDU_ControlProcess.Table_Name)
			.getPO(getUY_FDU_ControlProcess_ID(), get_TrxName());	}

	/** Set UY_FDU_ControlProcess.
		@param UY_FDU_ControlProcess_ID UY_FDU_ControlProcess	  */
	public void setUY_FDU_ControlProcess_ID (int UY_FDU_ControlProcess_ID)
	{
		if (UY_FDU_ControlProcess_ID < 1) 
			set_Value (COLUMNNAME_UY_FDU_ControlProcess_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FDU_ControlProcess_ID, Integer.valueOf(UY_FDU_ControlProcess_ID));
	}

	/** Get UY_FDU_ControlProcess.
		@return UY_FDU_ControlProcess	  */
	public int getUY_FDU_ControlProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_ControlProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FDU_ControlResult.
		@param UY_FDU_ControlResult_ID UY_FDU_ControlResult	  */
	public void setUY_FDU_ControlResult_ID (int UY_FDU_ControlResult_ID)
	{
		if (UY_FDU_ControlResult_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FDU_ControlResult_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FDU_ControlResult_ID, Integer.valueOf(UY_FDU_ControlResult_ID));
	}

	/** Get UY_FDU_ControlResult.
		@return UY_FDU_ControlResult	  */
	public int getUY_FDU_ControlResult_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_ControlResult_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Working Time.
		@param WorkingTime 
		Workflow Simulation Execution Time
	  */
	public void setWorkingTime (String WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, WorkingTime);
	}

	/** Get Working Time.
		@return Workflow Simulation Execution Time
	  */
	public String getWorkingTime () 
	{
		return (String)get_Value(COLUMNNAME_WorkingTime);
	}
}