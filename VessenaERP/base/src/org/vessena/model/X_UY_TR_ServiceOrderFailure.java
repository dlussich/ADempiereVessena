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

/** Generated Model for UY_TR_ServiceOrderFailure
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_TR_ServiceOrderFailure extends PO implements I_UY_TR_ServiceOrderFailure, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140513L;

    /** Standard Constructor */
    public X_UY_TR_ServiceOrderFailure (Properties ctx, int UY_TR_ServiceOrderFailure_ID, String trxName)
    {
      super (ctx, UY_TR_ServiceOrderFailure_ID, trxName);
      /** if (UY_TR_ServiceOrderFailure_ID == 0)
        {
			setUY_TR_Failure_ID (0);
			setUY_TR_FaultManage_ID (0);
			setUY_TR_ServiceOrder_ID (0);
			setUY_TR_ServiceOrderFailure_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ServiceOrderFailure (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ServiceOrderFailure[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	public I_UY_TR_Failure getUY_TR_Failure() throws RuntimeException
    {
		return (I_UY_TR_Failure)MTable.get(getCtx(), I_UY_TR_Failure.Table_Name)
			.getPO(getUY_TR_Failure_ID(), get_TrxName());	}

	/** Set UY_TR_Failure.
		@param UY_TR_Failure_ID UY_TR_Failure	  */
	public void setUY_TR_Failure_ID (int UY_TR_Failure_ID)
	{
		if (UY_TR_Failure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Failure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Failure_ID, Integer.valueOf(UY_TR_Failure_ID));
	}

	/** Get UY_TR_Failure.
		@return UY_TR_Failure	  */
	public int getUY_TR_Failure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Failure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_FaultManage getUY_TR_FaultManage() throws RuntimeException
    {
		return (I_UY_TR_FaultManage)MTable.get(getCtx(), I_UY_TR_FaultManage.Table_Name)
			.getPO(getUY_TR_FaultManage_ID(), get_TrxName());	}

	/** Set UY_TR_FaultManage.
		@param UY_TR_FaultManage_ID UY_TR_FaultManage	  */
	public void setUY_TR_FaultManage_ID (int UY_TR_FaultManage_ID)
	{
		if (UY_TR_FaultManage_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_FaultManage_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_FaultManage_ID, Integer.valueOf(UY_TR_FaultManage_ID));
	}

	/** Get UY_TR_FaultManage.
		@return UY_TR_FaultManage	  */
	public int getUY_TR_FaultManage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_FaultManage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_ServiceOrder getUY_TR_ServiceOrder() throws RuntimeException
    {
		return (I_UY_TR_ServiceOrder)MTable.get(getCtx(), I_UY_TR_ServiceOrder.Table_Name)
			.getPO(getUY_TR_ServiceOrder_ID(), get_TrxName());	}

	/** Set UY_TR_ServiceOrder.
		@param UY_TR_ServiceOrder_ID UY_TR_ServiceOrder	  */
	public void setUY_TR_ServiceOrder_ID (int UY_TR_ServiceOrder_ID)
	{
		if (UY_TR_ServiceOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_ServiceOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_ServiceOrder_ID, Integer.valueOf(UY_TR_ServiceOrder_ID));
	}

	/** Get UY_TR_ServiceOrder.
		@return UY_TR_ServiceOrder	  */
	public int getUY_TR_ServiceOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ServiceOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_ServiceOrderFailure.
		@param UY_TR_ServiceOrderFailure_ID UY_TR_ServiceOrderFailure	  */
	public void setUY_TR_ServiceOrderFailure_ID (int UY_TR_ServiceOrderFailure_ID)
	{
		if (UY_TR_ServiceOrderFailure_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ServiceOrderFailure_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ServiceOrderFailure_ID, Integer.valueOf(UY_TR_ServiceOrderFailure_ID));
	}

	/** Get UY_TR_ServiceOrderFailure.
		@return UY_TR_ServiceOrderFailure	  */
	public int getUY_TR_ServiceOrderFailure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ServiceOrderFailure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}