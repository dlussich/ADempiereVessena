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

/** Generated Model for UY_DGI_F2181
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DGI_F2181 extends PO implements I_UY_DGI_F2181, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130820L;

    /** Standard Constructor */
    public X_UY_DGI_F2181 (Properties ctx, int UY_DGI_F2181_ID, String trxName)
    {
      super (ctx, UY_DGI_F2181_ID, trxName);
      /** if (UY_DGI_F2181_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_DGI_F2181_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DGI_F2181 (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DGI_F2181[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (boolean ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, Boolean.valueOf(ExecuteAction));
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public boolean isExecuteAction () 
	{
		Object oo = get_Value(COLUMNNAME_ExecuteAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ExecuteAction2.
		@param ExecuteAction2 ExecuteAction2	  */
	public void setExecuteAction2 (boolean ExecuteAction2)
	{
		set_Value (COLUMNNAME_ExecuteAction2, Boolean.valueOf(ExecuteAction2));
	}

	/** Get ExecuteAction2.
		@return ExecuteAction2	  */
	public boolean isExecuteAction2 () 
	{
		Object oo = get_Value(COLUMNNAME_ExecuteAction2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_DGI_F2181.
		@param UY_DGI_F2181_ID UY_DGI_F2181	  */
	public void setUY_DGI_F2181_ID (int UY_DGI_F2181_ID)
	{
		if (UY_DGI_F2181_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DGI_F2181_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DGI_F2181_ID, Integer.valueOf(UY_DGI_F2181_ID));
	}

	/** Get UY_DGI_F2181.
		@return UY_DGI_F2181	  */
	public int getUY_DGI_F2181_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DGI_F2181_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}