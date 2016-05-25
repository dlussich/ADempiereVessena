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

/** Generated Model for UY_Molde_Fdu_Caja
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Molde_Fdu_Caja extends PO implements I_UY_Molde_Fdu_Caja, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140313L;

    /** Standard Constructor */
    public X_UY_Molde_Fdu_Caja (Properties ctx, int UY_Molde_Fdu_Caja_ID, String trxName)
    {
      super (ctx, UY_Molde_Fdu_Caja_ID, trxName);
      /** if (UY_Molde_Fdu_Caja_ID == 0)
        {
			setfecha (new Timestamp( System.currentTimeMillis() ));
			setUY_Fdu_Caja_Ref_ID (0);
			setUY_Fdu_Caja_Type_ID (0);
			setuy_molde_fdu_caja_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Molde_Fdu_Caja (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Molde_Fdu_Caja[")
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

	/** Set entrada.
		@param entrada entrada	  */
	public void setentrada (BigDecimal entrada)
	{
		set_Value (COLUMNNAME_entrada, entrada);
	}

	/** Get entrada.
		@return entrada	  */
	public BigDecimal getentrada () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_entrada);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set referencia.
		@param referencia referencia	  */
	public void setreferencia (String referencia)
	{
		set_Value (COLUMNNAME_referencia, referencia);
	}

	/** Get referencia.
		@return referencia	  */
	public String getreferencia () 
	{
		return (String)get_Value(COLUMNNAME_referencia);
	}

	/** Set salida.
		@param salida salida	  */
	public void setsalida (BigDecimal salida)
	{
		set_Value (COLUMNNAME_salida, salida);
	}

	/** Get salida.
		@return salida	  */
	public BigDecimal getsalida () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_salida);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set tc.
		@param tc tc	  */
	public void settc (BigDecimal tc)
	{
		set_Value (COLUMNNAME_tc, tc);
	}

	/** Get tc.
		@return tc	  */
	public BigDecimal gettc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_tc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_Fdu_Caja_Ref getUY_Fdu_Caja_Ref() throws RuntimeException
    {
		return (I_UY_Fdu_Caja_Ref)MTable.get(getCtx(), I_UY_Fdu_Caja_Ref.Table_Name)
			.getPO(getUY_Fdu_Caja_Ref_ID(), get_TrxName());	}

	/** Set UY_Fdu_Caja_Ref.
		@param UY_Fdu_Caja_Ref_ID UY_Fdu_Caja_Ref	  */
	public void setUY_Fdu_Caja_Ref_ID (int UY_Fdu_Caja_Ref_ID)
	{
		if (UY_Fdu_Caja_Ref_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Caja_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Caja_Ref_ID, Integer.valueOf(UY_Fdu_Caja_Ref_ID));
	}

	/** Get UY_Fdu_Caja_Ref.
		@return UY_Fdu_Caja_Ref	  */
	public int getUY_Fdu_Caja_Ref_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Caja_Ref_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Caja_Type getUY_Fdu_Caja_Type() throws RuntimeException
    {
		return (I_UY_Fdu_Caja_Type)MTable.get(getCtx(), I_UY_Fdu_Caja_Type.Table_Name)
			.getPO(getUY_Fdu_Caja_Type_ID(), get_TrxName());	}

	/** Set UY_Fdu_Caja_Type.
		@param UY_Fdu_Caja_Type_ID UY_Fdu_Caja_Type	  */
	public void setUY_Fdu_Caja_Type_ID (int UY_Fdu_Caja_Type_ID)
	{
		if (UY_Fdu_Caja_Type_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Caja_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Caja_Type_ID, Integer.valueOf(UY_Fdu_Caja_Type_ID));
	}

	/** Get UY_Fdu_Caja_Type.
		@return UY_Fdu_Caja_Type	  */
	public int getUY_Fdu_Caja_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Caja_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_molde_fdu_caja.
		@param uy_molde_fdu_caja_ID uy_molde_fdu_caja	  */
	public void setuy_molde_fdu_caja_ID (int uy_molde_fdu_caja_ID)
	{
		if (uy_molde_fdu_caja_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_uy_molde_fdu_caja_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_uy_molde_fdu_caja_ID, Integer.valueOf(uy_molde_fdu_caja_ID));
	}

	/** Get uy_molde_fdu_caja.
		@return uy_molde_fdu_caja	  */
	public int getuy_molde_fdu_caja_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_molde_fdu_caja_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}