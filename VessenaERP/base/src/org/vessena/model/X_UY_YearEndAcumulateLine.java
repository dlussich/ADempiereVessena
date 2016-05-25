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

/** Generated Model for UY_YearEndAcumulateLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_YearEndAcumulateLine extends PO implements I_UY_YearEndAcumulateLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130528L;

    /** Standard Constructor */
    public X_UY_YearEndAcumulateLine (Properties ctx, int UY_YearEndAcumulateLine_ID, String trxName)
    {
      super (ctx, UY_YearEndAcumulateLine_ID, trxName);
      /** if (UY_YearEndAcumulateLine_ID == 0)
        {
			setAmtSourceCr (Env.ZERO);
			setAmtSourceDr (Env.ZERO);
			setC_ElementValue_ID (0);
			setUY_YearEndAcumulate_ID (0);
			setUY_YearEndAcumulateLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_YearEndAcumulateLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_YearEndAcumulateLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Source Credit.
		@param AmtSourceCr 
		Source Credit Amount
	  */
	public void setAmtSourceCr (BigDecimal AmtSourceCr)
	{
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	/** Get Source Credit.
		@return Source Credit Amount
	  */
	public BigDecimal getAmtSourceCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Source Debit.
		@param AmtSourceDr 
		Source Debit Amount
	  */
	public void setAmtSourceDr (BigDecimal AmtSourceDr)
	{
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	/** Get Source Debit.
		@return Source Debit Amount
	  */
	public BigDecimal getAmtSourceDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceDr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_YearEndAcumulate getUY_YearEndAcumulate() throws RuntimeException
    {
		return (I_UY_YearEndAcumulate)MTable.get(getCtx(), I_UY_YearEndAcumulate.Table_Name)
			.getPO(getUY_YearEndAcumulate_ID(), get_TrxName());	}

	/** Set UY_YearEndAcumulate.
		@param UY_YearEndAcumulate_ID UY_YearEndAcumulate	  */
	public void setUY_YearEndAcumulate_ID (int UY_YearEndAcumulate_ID)
	{
		if (UY_YearEndAcumulate_ID < 1) 
			set_Value (COLUMNNAME_UY_YearEndAcumulate_ID, null);
		else 
			set_Value (COLUMNNAME_UY_YearEndAcumulate_ID, Integer.valueOf(UY_YearEndAcumulate_ID));
	}

	/** Get UY_YearEndAcumulate.
		@return UY_YearEndAcumulate	  */
	public int getUY_YearEndAcumulate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_YearEndAcumulate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_YearEndAcumulateLine.
		@param UY_YearEndAcumulateLine_ID UY_YearEndAcumulateLine	  */
	public void setUY_YearEndAcumulateLine_ID (int UY_YearEndAcumulateLine_ID)
	{
		if (UY_YearEndAcumulateLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_YearEndAcumulateLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_YearEndAcumulateLine_ID, Integer.valueOf(UY_YearEndAcumulateLine_ID));
	}

	/** Get UY_YearEndAcumulateLine.
		@return UY_YearEndAcumulateLine	  */
	public int getUY_YearEndAcumulateLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_YearEndAcumulateLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}