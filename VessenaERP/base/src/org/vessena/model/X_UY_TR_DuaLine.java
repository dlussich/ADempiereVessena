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

/** Generated Model for UY_TR_DuaLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_DuaLine extends PO implements I_UY_TR_DuaLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150410L;

    /** Standard Constructor */
    public X_UY_TR_DuaLine (Properties ctx, int UY_TR_DuaLine_ID, String trxName)
    {
      super (ctx, UY_TR_DuaLine_ID, trxName);
      /** if (UY_TR_DuaLine_ID == 0)
        {
			setIsSelected (false);
// N
			setUY_TR_DuaLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_DuaLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_DuaLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CantidadBultos.
		@param CantidadBultos CantidadBultos	  */
	public void setCantidadBultos (BigDecimal CantidadBultos)
	{
		set_Value (COLUMNNAME_CantidadBultos, CantidadBultos);
	}

	/** Get CantidadBultos.
		@return CantidadBultos	  */
	public BigDecimal getCantidadBultos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CantidadBultos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CantidadBultosRestantes.
		@param CantidadBultosRestantes CantidadBultosRestantes	  */
	public void setCantidadBultosRestantes (BigDecimal CantidadBultosRestantes)
	{
		set_Value (COLUMNNAME_CantidadBultosRestantes, CantidadBultosRestantes);
	}

	/** Get CantidadBultosRestantes.
		@return CantidadBultosRestantes	  */
	public BigDecimal getCantidadBultosRestantes () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CantidadBultosRestantes);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set descripcion.
		@param descripcion descripcion	  */
	public void setdescripcion (String descripcion)
	{
		set_Value (COLUMNNAME_descripcion, descripcion);
	}

	/** Get descripcion.
		@return descripcion	  */
	public String getdescripcion () 
	{
		return (String)get_Value(COLUMNNAME_descripcion);
	}

	/** Set descripcion2.
		@param descripcion2 descripcion2	  */
	public void setdescripcion2 (String descripcion2)
	{
		set_Value (COLUMNNAME_descripcion2, descripcion2);
	}

	/** Get descripcion2.
		@return descripcion2	  */
	public String getdescripcion2 () 
	{
		return (String)get_Value(COLUMNNAME_descripcion2);
	}

	/** Set descripcion3.
		@param descripcion3 descripcion3	  */
	public void setdescripcion3 (String descripcion3)
	{
		set_Value (COLUMNNAME_descripcion3, descripcion3);
	}

	/** Get descripcion3.
		@return descripcion3	  */
	public String getdescripcion3 () 
	{
		return (String)get_Value(COLUMNNAME_descripcion3);
	}

	/** Set descripcion4.
		@param descripcion4 descripcion4	  */
	public void setdescripcion4 (String descripcion4)
	{
		set_Value (COLUMNNAME_descripcion4, descripcion4);
	}

	/** Get descripcion4.
		@return descripcion4	  */
	public String getdescripcion4 () 
	{
		return (String)get_Value(COLUMNNAME_descripcion4);
	}

	/** Set descripcion5.
		@param descripcion5 descripcion5	  */
	public void setdescripcion5 (String descripcion5)
	{
		set_Value (COLUMNNAME_descripcion5, descripcion5);
	}

	/** Get descripcion5.
		@return descripcion5	  */
	public String getdescripcion5 () 
	{
		return (String)get_Value(COLUMNNAME_descripcion5);
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NumeroSerie.
		@param NumeroSerie NumeroSerie	  */
	public void setNumeroSerie (String NumeroSerie)
	{
		set_Value (COLUMNNAME_NumeroSerie, NumeroSerie);
	}

	/** Get NumeroSerie.
		@return NumeroSerie	  */
	public String getNumeroSerie () 
	{
		return (String)get_Value(COLUMNNAME_NumeroSerie);
	}

	/** Set pesoBruto.
		@param pesoBruto pesoBruto	  */
	public void setpesoBruto (BigDecimal pesoBruto)
	{
		set_Value (COLUMNNAME_pesoBruto, pesoBruto);
	}

	/** Get pesoBruto.
		@return pesoBruto	  */
	public BigDecimal getpesoBruto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoBruto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set pesoBrutoRestante.
		@param pesoBrutoRestante pesoBrutoRestante	  */
	public void setpesoBrutoRestante (BigDecimal pesoBrutoRestante)
	{
		set_Value (COLUMNNAME_pesoBrutoRestante, pesoBrutoRestante);
	}

	/** Get pesoBrutoRestante.
		@return pesoBrutoRestante	  */
	public BigDecimal getpesoBrutoRestante () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoBrutoRestante);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set pesoNeto.
		@param pesoNeto pesoNeto	  */
	public void setpesoNeto (BigDecimal pesoNeto)
	{
		set_Value (COLUMNNAME_pesoNeto, pesoNeto);
	}

	/** Get pesoNeto.
		@return pesoNeto	  */
	public BigDecimal getpesoNeto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoNeto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set pesoNetoRestante.
		@param pesoNetoRestante pesoNetoRestante	  */
	public void setpesoNetoRestante (BigDecimal pesoNetoRestante)
	{
		set_Value (COLUMNNAME_pesoNetoRestante, pesoNetoRestante);
	}

	/** Get pesoNetoRestante.
		@return pesoNetoRestante	  */
	public BigDecimal getpesoNetoRestante () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoNetoRestante);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TipoBulto.
		@param TipoBulto TipoBulto	  */
	public void setTipoBulto (String TipoBulto)
	{
		set_Value (COLUMNNAME_TipoBulto, TipoBulto);
	}

	/** Get TipoBulto.
		@return TipoBulto	  */
	public String getTipoBulto () 
	{
		return (String)get_Value(COLUMNNAME_TipoBulto);
	}

	public I_UY_TR_Dua getUY_TR_Dua() throws RuntimeException
    {
		return (I_UY_TR_Dua)MTable.get(getCtx(), I_UY_TR_Dua.Table_Name)
			.getPO(getUY_TR_Dua_ID(), get_TrxName());	}

	/** Set UY_TR_Dua_ID.
		@param UY_TR_Dua_ID UY_TR_Dua_ID	  */
	public void setUY_TR_Dua_ID (int UY_TR_Dua_ID)
	{
		if (UY_TR_Dua_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Dua_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Dua_ID, Integer.valueOf(UY_TR_Dua_ID));
	}

	/** Get UY_TR_Dua_ID.
		@return UY_TR_Dua_ID	  */
	public int getUY_TR_Dua_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Dua_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_DuaLine_ID.
		@param UY_TR_DuaLine_ID UY_TR_DuaLine_ID	  */
	public void setUY_TR_DuaLine_ID (int UY_TR_DuaLine_ID)
	{
		if (UY_TR_DuaLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_DuaLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_DuaLine_ID, Integer.valueOf(UY_TR_DuaLine_ID));
	}

	/** Get UY_TR_DuaLine_ID.
		@return UY_TR_DuaLine_ID	  */
	public int getUY_TR_DuaLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_DuaLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}