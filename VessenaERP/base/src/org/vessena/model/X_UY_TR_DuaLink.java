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

/** Generated Model for UY_TR_DuaLink
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_DuaLink extends PO implements I_UY_TR_DuaLink, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150715L;

    /** Standard Constructor */
    public X_UY_TR_DuaLink (Properties ctx, int UY_TR_DuaLink_ID, String trxName)
    {
      super (ctx, UY_TR_DuaLink_ID, trxName);
      /** if (UY_TR_DuaLink_ID == 0)
        {
			setUY_TR_DuaLink_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_DuaLink (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_DuaLink[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CantidadBultosAsociacion.
		@param CantidadBultosAsociacion CantidadBultosAsociacion	  */
	public void setCantidadBultosAsociacion (BigDecimal CantidadBultosAsociacion)
	{
		set_Value (COLUMNNAME_CantidadBultosAsociacion, CantidadBultosAsociacion);
	}

	/** Get CantidadBultosAsociacion.
		@return CantidadBultosAsociacion	  */
	public BigDecimal getCantidadBultosAsociacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CantidadBultosAsociacion);
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

	/** Set IsDrop.
		@param IsDrop IsDrop	  */
	public void setIsDrop (boolean IsDrop)
	{
		set_Value (COLUMNNAME_IsDrop, Boolean.valueOf(IsDrop));
	}

	/** Get IsDrop.
		@return IsDrop	  */
	public boolean isDrop () 
	{
		Object oo = get_Value(COLUMNNAME_IsDrop);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NumeroMicDna.
		@param NumeroMicDna NumeroMicDna	  */
	public void setNumeroMicDna (BigDecimal NumeroMicDna)
	{
		set_Value (COLUMNNAME_NumeroMicDna, NumeroMicDna);
	}

	/** Get NumeroMicDna.
		@return NumeroMicDna	  */
	public BigDecimal getNumeroMicDna () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NumeroMicDna);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NumeroSerieItemDua.
		@param NumeroSerieItemDua NumeroSerieItemDua	  */
	public void setNumeroSerieItemDua (String NumeroSerieItemDua)
	{
		set_Value (COLUMNNAME_NumeroSerieItemDua, NumeroSerieItemDua);
	}

	/** Get NumeroSerieItemDua.
		@return NumeroSerieItemDua	  */
	public String getNumeroSerieItemDua () 
	{
		return (String)get_Value(COLUMNNAME_NumeroSerieItemDua);
	}

	/** Set PesoBrutoAsociacion.
		@param PesoBrutoAsociacion PesoBrutoAsociacion	  */
	public void setPesoBrutoAsociacion (BigDecimal PesoBrutoAsociacion)
	{
		set_Value (COLUMNNAME_PesoBrutoAsociacion, PesoBrutoAsociacion);
	}

	/** Get PesoBrutoAsociacion.
		@return PesoBrutoAsociacion	  */
	public BigDecimal getPesoBrutoAsociacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PesoBrutoAsociacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PesoNetoAsociacion.
		@param PesoNetoAsociacion PesoNetoAsociacion	  */
	public void setPesoNetoAsociacion (BigDecimal PesoNetoAsociacion)
	{
		set_Value (COLUMNNAME_PesoNetoAsociacion, PesoNetoAsociacion);
	}

	/** Get PesoNetoAsociacion.
		@return PesoNetoAsociacion	  */
	public BigDecimal getPesoNetoAsociacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PesoNetoAsociacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** StatusAsociation AD_Reference_ID=1000494 */
	public static final int STATUSASOCIATION_AD_Reference_ID=1000494;
	/** DESVINCULADO = DESVINCULADO */
	public static final String STATUSASOCIATION_DESVINCULADO = "DESVINCULADO";
	/** ENALTA = ENALTA */
	public static final String STATUSASOCIATION_ENALTA = "ENALTA";
	/** ENBAJA = ENBAJA */
	public static final String STATUSASOCIATION_ENBAJA = "ENBAJA";
	/** ENMODIFICACION = ENMODIFICACION */
	public static final String STATUSASOCIATION_ENMODIFICACION = "ENMODIFICACION";
	/** VINCULADO = VINCULADO */
	public static final String STATUSASOCIATION_VINCULADO = "VINCULADO";
	/** Set StatusAsociation.
		@param StatusAsociation StatusAsociation	  */
	public void setStatusAsociation (String StatusAsociation)
	{

		set_Value (COLUMNNAME_StatusAsociation, StatusAsociation);
	}

	/** Get StatusAsociation.
		@return StatusAsociation	  */
	public String getStatusAsociation () 
	{
		return (String)get_Value(COLUMNNAME_StatusAsociation);
	}

	/** Set tipobulto.
		@param tipobulto tipobulto	  */
	public void settipobulto (String tipobulto)
	{
		set_Value (COLUMNNAME_tipobulto, tipobulto);
	}

	/** Get tipobulto.
		@return tipobulto	  */
	public String gettipobulto () 
	{
		return (String)get_Value(COLUMNNAME_tipobulto);
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

	public I_UY_TR_DuaCrt getUY_TR_DuaCrt() throws RuntimeException
    {
		return (I_UY_TR_DuaCrt)MTable.get(getCtx(), I_UY_TR_DuaCrt.Table_Name)
			.getPO(getUY_TR_DuaCrt_ID(), get_TrxName());	}

	/** Set UY_TR_DuaCrt.
		@param UY_TR_DuaCrt_ID UY_TR_DuaCrt	  */
	public void setUY_TR_DuaCrt_ID (int UY_TR_DuaCrt_ID)
	{
		if (UY_TR_DuaCrt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_DuaCrt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_DuaCrt_ID, Integer.valueOf(UY_TR_DuaCrt_ID));
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

	public I_UY_TR_Dua getUY_TR_Dua() throws RuntimeException
    {
		return (I_UY_TR_Dua)MTable.get(getCtx(), I_UY_TR_Dua.Table_Name)
			.getPO(getUY_TR_Dua_ID(), get_TrxName());	}

	/** Set UY_TR_Dua.
		@param UY_TR_Dua_ID UY_TR_Dua	  */
	public void setUY_TR_Dua_ID (int UY_TR_Dua_ID)
	{
		if (UY_TR_Dua_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Dua_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Dua_ID, Integer.valueOf(UY_TR_Dua_ID));
	}

	/** Get UY_TR_Dua.
		@return UY_TR_Dua	  */
	public int getUY_TR_Dua_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Dua_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_DuaLine getUY_TR_DuaLine() throws RuntimeException
    {
		return (I_UY_TR_DuaLine)MTable.get(getCtx(), I_UY_TR_DuaLine.Table_Name)
			.getPO(getUY_TR_DuaLine_ID(), get_TrxName());	}

	/** Set UY_TR_DuaLine.
		@param UY_TR_DuaLine_ID UY_TR_DuaLine	  */
	public void setUY_TR_DuaLine_ID (int UY_TR_DuaLine_ID)
	{
		if (UY_TR_DuaLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_DuaLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_DuaLine_ID, Integer.valueOf(UY_TR_DuaLine_ID));
	}

	/** Get UY_TR_DuaLine.
		@return UY_TR_DuaLine	  */
	public int getUY_TR_DuaLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_DuaLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_DuaLink.
		@param UY_TR_DuaLink_ID UY_TR_DuaLink	  */
	public void setUY_TR_DuaLink_ID (int UY_TR_DuaLink_ID)
	{
		if (UY_TR_DuaLink_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_DuaLink_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_DuaLink_ID, Integer.valueOf(UY_TR_DuaLink_ID));
	}

	/** Get UY_TR_DuaLink.
		@return UY_TR_DuaLink	  */
	public int getUY_TR_DuaLink_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_DuaLink_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ValorMercaderiaAsociacion.
		@param ValorMercaderiaAsociacion ValorMercaderiaAsociacion	  */
	public void setValorMercaderiaAsociacion (BigDecimal ValorMercaderiaAsociacion)
	{
		set_Value (COLUMNNAME_ValorMercaderiaAsociacion, ValorMercaderiaAsociacion);
	}

	/** Get ValorMercaderiaAsociacion.
		@return ValorMercaderiaAsociacion	  */
	public BigDecimal getValorMercaderiaAsociacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValorMercaderiaAsociacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}