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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_RFdu_CajaControl
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RFdu_CajaControl extends PO implements I_UY_RFdu_CajaControl, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140321L;

    /** Standard Constructor */
    public X_UY_RFdu_CajaControl (Properties ctx, int UY_RFdu_CajaControl_ID, String trxName)
    {
      super (ctx, UY_RFdu_CajaControl_ID, trxName);
      /** if (UY_RFdu_CajaControl_ID == 0)
        {
			setName (null);
			setUY_RFdu_CajaControl_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_RFdu_CajaControl (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RFdu_CajaControl[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set fecha.
		@param fecha fecha	  */
	public void setfecha (Timestamp fecha)
	{
		set_Value (COLUMNNAME_fecha, fecha);
	}

	/** Get fecha.
		@return fecha	  */
	public Timestamp getfecha () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecha);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set saldo.
		@param saldo saldo	  */
	public void setsaldo (BigDecimal saldo)
	{
		set_Value (COLUMNNAME_saldo, saldo);
	}

	/** Get saldo.
		@return saldo	  */
	public BigDecimal getsaldo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_saldo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SaldoFinalPesos.
		@param SaldoFinalPesos 
		SaldoFinalPesos
	  */
	public void setSaldoFinalPesos (BigDecimal SaldoFinalPesos)
	{
		set_Value (COLUMNNAME_SaldoFinalPesos, SaldoFinalPesos);
	}

	/** Get SaldoFinalPesos.
		@return SaldoFinalPesos
	  */
	public BigDecimal getSaldoFinalPesos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SaldoFinalPesos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set saldoinicial.
		@param saldoinicial saldoinicial	  */
	public void setsaldoinicial (BigDecimal saldoinicial)
	{
		set_Value (COLUMNNAME_saldoinicial, saldoinicial);
	}

	/** Get saldoinicial.
		@return saldoinicial	  */
	public BigDecimal getsaldoinicial () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_saldoinicial);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_RFdu_CajaControl.
		@param UY_RFdu_CajaControl_ID UY_RFdu_CajaControl	  */
	public void setUY_RFdu_CajaControl_ID (int UY_RFdu_CajaControl_ID)
	{
		if (UY_RFdu_CajaControl_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RFdu_CajaControl_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RFdu_CajaControl_ID, Integer.valueOf(UY_RFdu_CajaControl_ID));
	}

	/** Get UY_RFdu_CajaControl.
		@return UY_RFdu_CajaControl	  */
	public int getUY_RFdu_CajaControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFdu_CajaControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}