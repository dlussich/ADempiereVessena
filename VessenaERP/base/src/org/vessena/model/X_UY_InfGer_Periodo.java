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

/** Generated Model for UY_InfGer_Periodo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_InfGer_Periodo extends PO implements I_UY_InfGer_Periodo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130724L;

    /** Standard Constructor */
    public X_UY_InfGer_Periodo (Properties ctx, int UY_InfGer_Periodo_ID, String trxName)
    {
      super (ctx, UY_InfGer_Periodo_ID, trxName);
      /** if (UY_InfGer_Periodo_ID == 0)
        {
			setC_Period_ID (0);
			setUY_InfGer_Periodo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InfGer_Periodo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InfGer_Periodo[")
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

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_InfGer_Periodo.
		@param UY_InfGer_Periodo_ID UY_InfGer_Periodo	  */
	public void setUY_InfGer_Periodo_ID (int UY_InfGer_Periodo_ID)
	{
		if (UY_InfGer_Periodo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InfGer_Periodo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InfGer_Periodo_ID, Integer.valueOf(UY_InfGer_Periodo_ID));
	}

	/** Get UY_InfGer_Periodo.
		@return UY_InfGer_Periodo	  */
	public int getUY_InfGer_Periodo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InfGer_Periodo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}