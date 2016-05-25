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

/** Generated Model for UY_TR_DuaCrt
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_DuaCrt extends PO implements I_UY_TR_DuaCrt, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150408L;

    /** Standard Constructor */
    public X_UY_TR_DuaCrt (Properties ctx, int UY_TR_DuaCrt_ID, String trxName)
    {
      super (ctx, UY_TR_DuaCrt_ID, trxName);
      /** if (UY_TR_DuaCrt_ID == 0)
        {
			setIsSelected (false);
// N
			setUY_TR_DuaCrt_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_DuaCrt (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_DuaCrt[")
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

	/** Set NumeroDna.
		@param NumeroDna NumeroDna	  */
	public void setNumeroDna (String NumeroDna)
	{
		set_Value (COLUMNNAME_NumeroDna, NumeroDna);
	}

	/** Get NumeroDna.
		@return NumeroDna	  */
	public String getNumeroDna () 
	{
		return (String)get_Value(COLUMNNAME_NumeroDna);
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

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_DuaCrt.
		@param UY_TR_DuaCrt_ID UY_TR_DuaCrt	  */
	public void setUY_TR_DuaCrt_ID (int UY_TR_DuaCrt_ID)
	{
		if (UY_TR_DuaCrt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_DuaCrt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_DuaCrt_ID, Integer.valueOf(UY_TR_DuaCrt_ID));
	}

	/** Get UY_TR_DuaCrt.
		@return UY_TR_DuaCrt	  */
	public int getUY_TR_DuaCrt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_DuaCrt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_DuaMic getUY_TR_DuaMic() throws RuntimeException
    {
		return (I_UY_TR_DuaMic)MTable.get(getCtx(), I_UY_TR_DuaMic.Table_Name)
			.getPO(getUY_TR_DuaMic_ID(), get_TrxName());	}

	/** Set UY_TR_DuaMic_ID.
		@param UY_TR_DuaMic_ID UY_TR_DuaMic_ID	  */
	public void setUY_TR_DuaMic_ID (int UY_TR_DuaMic_ID)
	{
		if (UY_TR_DuaMic_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_DuaMic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_DuaMic_ID, Integer.valueOf(UY_TR_DuaMic_ID));
	}

	/** Get UY_TR_DuaMic_ID.
		@return UY_TR_DuaMic_ID	  */
	public int getUY_TR_DuaMic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_DuaMic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException
    {
		return (I_UY_TR_Mic)MTable.get(getCtx(), I_UY_TR_Mic.Table_Name)
			.getPO(getUY_TR_Mic_ID(), get_TrxName());	}

	/** Set UY_TR_Mic.
		@param UY_TR_Mic_ID UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID)
	{
		if (UY_TR_Mic_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, Integer.valueOf(UY_TR_Mic_ID));
	}

	/** Get UY_TR_Mic.
		@return UY_TR_Mic	  */
	public int getUY_TR_Mic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Mic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set valorMercaderia.
		@param valorMercaderia valorMercaderia	  */
	public void setvalorMercaderia (BigDecimal valorMercaderia)
	{
		set_Value (COLUMNNAME_valorMercaderia, valorMercaderia);
	}

	/** Get valorMercaderia.
		@return valorMercaderia	  */
	public BigDecimal getvalorMercaderia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_valorMercaderia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set valorMercaderiaRestante.
		@param valorMercaderiaRestante valorMercaderiaRestante	  */
	public void setvalorMercaderiaRestante (BigDecimal valorMercaderiaRestante)
	{
		set_Value (COLUMNNAME_valorMercaderiaRestante, valorMercaderiaRestante);
	}

	/** Get valorMercaderiaRestante.
		@return valorMercaderiaRestante	  */
	public BigDecimal getvalorMercaderiaRestante () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_valorMercaderiaRestante);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}