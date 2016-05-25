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

/** Generated Model for UY_Bcu_DatosTxtCapital
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Bcu_DatosTxtCapital extends PO implements I_UY_Bcu_DatosTxtCapital, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130801L;

    /** Standard Constructor */
    public X_UY_Bcu_DatosTxtCapital (Properties ctx, int UY_Bcu_DatosTxtCapital_ID, String trxName)
    {
      super (ctx, UY_Bcu_DatosTxtCapital_ID, trxName);
      /** if (UY_Bcu_DatosTxtCapital_ID == 0)
        {
			setUY_Bcu_DatosTxtCapital_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Bcu_DatosTxtCapital (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Bcu_DatosTxtCapital[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set acumuladodecapital.
		@param acumuladodecapital acumuladodecapital	  */
	public void setacumuladodecapital (BigDecimal acumuladodecapital)
	{
		set_Value (COLUMNNAME_acumuladodecapital, acumuladodecapital);
	}

	/** Get acumuladodecapital.
		@return acumuladodecapital	  */
	public BigDecimal getacumuladodecapital () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_acumuladodecapital);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cantidadoperaciones.
		@param cantidadoperaciones cantidadoperaciones	  */
	public void setcantidadoperaciones (BigDecimal cantidadoperaciones)
	{
		set_Value (COLUMNNAME_cantidadoperaciones, cantidadoperaciones);
	}

	/** Get cantidadoperaciones.
		@return cantidadoperaciones	  */
	public BigDecimal getcantidadoperaciones () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cantidadoperaciones);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set orden.
		@param orden orden	  */
	public void setorden (int orden)
	{
		set_Value (COLUMNNAME_orden, Integer.valueOf(orden));
	}

	/** Get orden.
		@return orden	  */
	public int getorden () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_orden);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set plazoremanente.
		@param plazoremanente plazoremanente	  */
	public void setplazoremanente (BigDecimal plazoremanente)
	{
		set_Value (COLUMNNAME_plazoremanente, plazoremanente);
	}

	/** Get plazoremanente.
		@return plazoremanente	  */
	public BigDecimal getplazoremanente () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_plazoremanente);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tasa.
		@param Tasa 
		Tasa
	  */
	public void setTasa (BigDecimal Tasa)
	{
		set_Value (COLUMNNAME_Tasa, Tasa);
	}

	/** Get Tasa.
		@return Tasa
	  */
	public BigDecimal getTasa () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tasa);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TipoDato.
		@param TipoDato 
		TipoDato
	  */
	public void setTipoDato (String TipoDato)
	{
		set_Value (COLUMNNAME_TipoDato, TipoDato);
	}

	/** Get TipoDato.
		@return TipoDato
	  */
	public String getTipoDato () 
	{
		return (String)get_Value(COLUMNNAME_TipoDato);
	}

	/** Set UY_Bcu_DatosTxtCapital.
		@param UY_Bcu_DatosTxtCapital_ID UY_Bcu_DatosTxtCapital	  */
	public void setUY_Bcu_DatosTxtCapital_ID (int UY_Bcu_DatosTxtCapital_ID)
	{
		if (UY_Bcu_DatosTxtCapital_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Bcu_DatosTxtCapital_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Bcu_DatosTxtCapital_ID, Integer.valueOf(UY_Bcu_DatosTxtCapital_ID));
	}

	/** Get UY_Bcu_DatosTxtCapital.
		@return UY_Bcu_DatosTxtCapital	  */
	public int getUY_Bcu_DatosTxtCapital_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Bcu_DatosTxtCapital_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Bcu_GracionTxtCapital getUY_Bcu_GracionTxtCapital() throws RuntimeException
    {
		return (I_UY_Bcu_GracionTxtCapital)MTable.get(getCtx(), I_UY_Bcu_GracionTxtCapital.Table_Name)
			.getPO(getUY_Bcu_GracionTxtCapital_ID(), get_TrxName());	}

	/** Set UY_Bcu_GracionTxtCapital.
		@param UY_Bcu_GracionTxtCapital_ID UY_Bcu_GracionTxtCapital	  */
	public void setUY_Bcu_GracionTxtCapital_ID (int UY_Bcu_GracionTxtCapital_ID)
	{
		if (UY_Bcu_GracionTxtCapital_ID < 1) 
			set_Value (COLUMNNAME_UY_Bcu_GracionTxtCapital_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Bcu_GracionTxtCapital_ID, Integer.valueOf(UY_Bcu_GracionTxtCapital_ID));
	}

	/** Get UY_Bcu_GracionTxtCapital.
		@return UY_Bcu_GracionTxtCapital	  */
	public int getUY_Bcu_GracionTxtCapital_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Bcu_GracionTxtCapital_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}