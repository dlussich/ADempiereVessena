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

/** Generated Model for UY_HRConceptoLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRConceptoLine extends PO implements I_UY_HRConceptoLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140122L;

    /** Standard Constructor */
    public X_UY_HRConceptoLine (Properties ctx, int UY_HRConceptoLine_ID, String trxName)
    {
      super (ctx, UY_HRConceptoLine_ID, trxName);
      /** if (UY_HRConceptoLine_ID == 0)
        {
			setHR_Concept_ID (0);
			setUY_HRConceptoLine_ID (0);
			setUY_HRNovedades_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRConceptoLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRConceptoLine[")
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

	/** Set porcentaje.
		@param porcentaje porcentaje	  */
	public void setporcentaje (BigDecimal porcentaje)
	{
		set_Value (COLUMNNAME_porcentaje, porcentaje);
	}

	/** Get porcentaje.
		@return porcentaje	  */
	public BigDecimal getporcentaje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcentaje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_HRConceptoLine.
		@param UY_HRConceptoLine_ID UY_HRConceptoLine	  */
	public void setUY_HRConceptoLine_ID (int UY_HRConceptoLine_ID)
	{
		if (UY_HRConceptoLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRConceptoLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRConceptoLine_ID, Integer.valueOf(UY_HRConceptoLine_ID));
	}

	/** Get UY_HRConceptoLine.
		@return UY_HRConceptoLine	  */
	public int getUY_HRConceptoLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRConceptoLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_HRLoadDriver getUY_HRLoadDriver() throws RuntimeException
    {
		return (I_UY_HRLoadDriver)MTable.get(getCtx(), I_UY_HRLoadDriver.Table_Name)
			.getPO(getUY_HRLoadDriver_ID(), get_TrxName());	}

	/** Set UY_HRLoadDriver.
		@param UY_HRLoadDriver_ID UY_HRLoadDriver	  */
	public void setUY_HRLoadDriver_ID (int UY_HRLoadDriver_ID)
	{
		if (UY_HRLoadDriver_ID < 1) 
			set_Value (COLUMNNAME_UY_HRLoadDriver_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRLoadDriver_ID, Integer.valueOf(UY_HRLoadDriver_ID));
	}

	/** Get UY_HRLoadDriver.
		@return UY_HRLoadDriver	  */
	public int getUY_HRLoadDriver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRLoadDriver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_HRNovedades getUY_HRNovedades() throws RuntimeException
    {
		return (I_UY_HRNovedades)MTable.get(getCtx(), I_UY_HRNovedades.Table_Name)
			.getPO(getUY_HRNovedades_ID(), get_TrxName());	}

	/** Set UY_HRNovedades.
		@param UY_HRNovedades_ID UY_HRNovedades	  */
	public void setUY_HRNovedades_ID (int UY_HRNovedades_ID)
	{
		if (UY_HRNovedades_ID < 1) 
			set_Value (COLUMNNAME_UY_HRNovedades_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRNovedades_ID, Integer.valueOf(UY_HRNovedades_ID));
	}

	/** Get UY_HRNovedades.
		@return UY_HRNovedades	  */
	public int getUY_HRNovedades_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRNovedades_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}