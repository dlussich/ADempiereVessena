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

/** Generated Model for UY_BPOtrasRemuneraciones
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_BPOtrasRemuneraciones extends PO implements I_UY_BPOtrasRemuneraciones, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_BPOtrasRemuneraciones (Properties ctx, int UY_BPOtrasRemuneraciones_ID, String trxName)
    {
      super (ctx, UY_BPOtrasRemuneraciones_ID, trxName);
      /** if (UY_BPOtrasRemuneraciones_ID == 0)
        {
			setAmount (Env.ZERO);
			setC_BPartner_ID (0);
			setC_Remuneration_ID (0);
			setsalminimo (false);
			setUY_BPOtrasRemuneraciones_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BPOtrasRemuneraciones (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BPOtrasRemuneraciones[")
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

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Remuneration getC_Remuneration() throws RuntimeException
    {
		return (I_C_Remuneration)MTable.get(getCtx(), I_C_Remuneration.Table_Name)
			.getPO(getC_Remuneration_ID(), get_TrxName());	}

	/** Set Remuneration.
		@param C_Remuneration_ID 
		Wage or Salary
	  */
	public void setC_Remuneration_ID (int C_Remuneration_ID)
	{
		if (C_Remuneration_ID < 1) 
			set_Value (COLUMNNAME_C_Remuneration_ID, null);
		else 
			set_Value (COLUMNNAME_C_Remuneration_ID, Integer.valueOf(C_Remuneration_ID));
	}

	/** Get Remuneration.
		@return Wage or Salary
	  */
	public int getC_Remuneration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Remuneration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set salminimo.
		@param salminimo salminimo	  */
	public void setsalminimo (boolean salminimo)
	{
		set_Value (COLUMNNAME_salminimo, Boolean.valueOf(salminimo));
	}

	/** Get salminimo.
		@return salminimo	  */
	public boolean issalminimo () 
	{
		Object oo = get_Value(COLUMNNAME_salminimo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_BPOtrasRemuneraciones.
		@param UY_BPOtrasRemuneraciones_ID UY_BPOtrasRemuneraciones	  */
	public void setUY_BPOtrasRemuneraciones_ID (int UY_BPOtrasRemuneraciones_ID)
	{
		if (UY_BPOtrasRemuneraciones_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BPOtrasRemuneraciones_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BPOtrasRemuneraciones_ID, Integer.valueOf(UY_BPOtrasRemuneraciones_ID));
	}

	/** Get UY_BPOtrasRemuneraciones.
		@return UY_BPOtrasRemuneraciones	  */
	public int getUY_BPOtrasRemuneraciones_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BPOtrasRemuneraciones_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}