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

/** Generated Model for UY_HRCuadroCodigoLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRCuadroCodigoLine extends PO implements I_UY_HRCuadroCodigoLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140106L;

    /** Standard Constructor */
    public X_UY_HRCuadroCodigoLine (Properties ctx, int UY_HRCuadroCodigoLine_ID, String trxName)
    {
      super (ctx, UY_HRCuadroCodigoLine_ID, trxName);
      /** if (UY_HRCuadroCodigoLine_ID == 0)
        {
			setAmount (Env.ZERO);
			setFactor (Env.ZERO);
			setHR_Concept_ID (0);
			setTotalAmt (Env.ZERO);
			setUY_HRCuadroCodigo_ID (0);
			setUY_HRCuadroCodigoLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRCuadroCodigoLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRCuadroCodigoLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Factor.
		@param Factor 
		Scaling factor.
	  */
	public void setFactor (BigDecimal Factor)
	{
		set_Value (COLUMNNAME_Factor, Factor);
	}

	/** Get Factor.
		@return Scaling factor.
	  */
	public BigDecimal getFactor () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Factor);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(getHR_Concept_ID(), get_TrxName());	}

	/** Set Payroll Concept.
		@param HR_Concept_ID Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get Payroll Concept.
		@return Payroll Concept	  */
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_HRCuadroCodigo getUY_HRCuadroCodigo() throws RuntimeException
    {
		return (I_UY_HRCuadroCodigo)MTable.get(getCtx(), I_UY_HRCuadroCodigo.Table_Name)
			.getPO(getUY_HRCuadroCodigo_ID(), get_TrxName());	}

	/** Set UY_HRCuadroCodigo.
		@param UY_HRCuadroCodigo_ID UY_HRCuadroCodigo	  */
	public void setUY_HRCuadroCodigo_ID (int UY_HRCuadroCodigo_ID)
	{
		if (UY_HRCuadroCodigo_ID < 1) 
			set_Value (COLUMNNAME_UY_HRCuadroCodigo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRCuadroCodigo_ID, Integer.valueOf(UY_HRCuadroCodigo_ID));
	}

	/** Get UY_HRCuadroCodigo.
		@return UY_HRCuadroCodigo	  */
	public int getUY_HRCuadroCodigo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRCuadroCodigo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRCuadroCodigoLine.
		@param UY_HRCuadroCodigoLine_ID UY_HRCuadroCodigoLine	  */
	public void setUY_HRCuadroCodigoLine_ID (int UY_HRCuadroCodigoLine_ID)
	{
		if (UY_HRCuadroCodigoLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRCuadroCodigoLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRCuadroCodigoLine_ID, Integer.valueOf(UY_HRCuadroCodigoLine_ID));
	}

	/** Get UY_HRCuadroCodigoLine.
		@return UY_HRCuadroCodigoLine	  */
	public int getUY_HRCuadroCodigoLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRCuadroCodigoLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}