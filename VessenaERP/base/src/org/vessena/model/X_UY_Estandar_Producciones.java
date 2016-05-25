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

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_Persistent;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for UY_Estandar_Producciones
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_Estandar_Producciones extends PO implements I_UY_Estandar_Producciones, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_Estandar_Producciones (Properties ctx, int UY_Estandar_Producciones_ID, String trxName)
    {
      super (ctx, UY_Estandar_Producciones_ID, trxName);
      /** if (UY_Estandar_Producciones_ID == 0)
        {
			setM_Product_ID (0);
			setUY_Cantidad_Operarios_Dir (0);
			setUY_Estandar_Producciones_ID (0);
			setuy_product_teorica (Env.ZERO);
			setuy_tiempo_setup (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Estandar_Producciones (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Estandar_Producciones[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_UOM getC_UOM_ALM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_ALM_ID(), get_TrxName());	}

	/** Set C_UOM_ALM_ID.
		@param C_UOM_ALM_ID C_UOM_ALM_ID	  */
	public void setC_UOM_ALM_ID (int C_UOM_ALM_ID)
	{
		throw new IllegalArgumentException ("C_UOM_ALM_ID is virtual column");	}

	/** Get C_UOM_ALM_ID.
		@return C_UOM_ALM_ID	  */
	public int getC_UOM_ALM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ALM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SKU.
		@param SKU 
		Stock Keeping Unit
	  */
	public void setSKU (String SKU)
	{
		set_Value (COLUMNNAME_SKU, SKU);
	}

	/** Get SKU.
		@return Stock Keeping Unit
	  */
	public String getSKU () 
	{
		return (String)get_Value(COLUMNNAME_SKU);
	}

	public I_S_Resource getS_Resource() throws RuntimeException
    {
		return (I_S_Resource)MTable.get(getCtx(), I_S_Resource.Table_Name)
			.getPO(getS_Resource_ID(), get_TrxName());	}

	/** Set Resource.
		@param S_Resource_ID 
		Resource
	  */
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Resource.
		@return Resource
	  */
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Cantidad_Operarios_Dir.
		@param UY_Cantidad_Operarios_Dir UY_Cantidad_Operarios_Dir	  */
	public void setUY_Cantidad_Operarios_Dir (int UY_Cantidad_Operarios_Dir)
	{
		set_Value (COLUMNNAME_UY_Cantidad_Operarios_Dir, Integer.valueOf(UY_Cantidad_Operarios_Dir));
	}

	/** Get UY_Cantidad_Operarios_Dir.
		@return UY_Cantidad_Operarios_Dir	  */
	public int getUY_Cantidad_Operarios_Dir () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Cantidad_Operarios_Dir);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Cantidad_Operarios_Ind.
		@param UY_Cantidad_Operarios_Ind UY_Cantidad_Operarios_Ind	  */
	public void setUY_Cantidad_Operarios_Ind (int UY_Cantidad_Operarios_Ind)
	{
		set_Value (COLUMNNAME_UY_Cantidad_Operarios_Ind, Integer.valueOf(UY_Cantidad_Operarios_Ind));
	}

	/** Get UY_Cantidad_Operarios_Ind.
		@return UY_Cantidad_Operarios_Ind	  */
	public int getUY_Cantidad_Operarios_Ind () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Cantidad_Operarios_Ind);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_EmpaqueHora.
		@param UY_EmpaqueHora UY_EmpaqueHora	  */
	public void setUY_EmpaqueHora (BigDecimal UY_EmpaqueHora)
	{
		throw new IllegalArgumentException ("UY_EmpaqueHora is virtual column");	}

	/** Get UY_EmpaqueHora.
		@return UY_EmpaqueHora	  */
	public BigDecimal getUY_EmpaqueHora () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_EmpaqueHora);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Estandar Producciones.
		@param UY_Estandar_Producciones_ID Estandar Producciones	  */
	public void setUY_Estandar_Producciones_ID (int UY_Estandar_Producciones_ID)
	{
		if (UY_Estandar_Producciones_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Estandar_Producciones_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Estandar_Producciones_ID, Integer.valueOf(UY_Estandar_Producciones_ID));
	}

	/** Get Estandar Producciones.
		@return Estandar Producciones	  */
	public int getUY_Estandar_Producciones_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Estandar_Producciones_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_factor.
		@param uy_factor uy_factor	  */
	public void setuy_factor (BigDecimal uy_factor)
	{
		throw new IllegalArgumentException ("uy_factor is virtual column");	}

	/** Get uy_factor.
		@return uy_factor	  */
	public BigDecimal getuy_factor () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_factor);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_linea.
		@param uy_linea uy_linea	  */
	public void setuy_linea (String uy_linea)
	{
		set_Value (COLUMNNAME_uy_linea, uy_linea);
	}

	/** Get uy_linea.
		@return uy_linea	  */
	public String getuy_linea () 
	{
		return (String)get_Value(COLUMNNAME_uy_linea);
	}

	/** Set uy_product_teorica.
		@param uy_product_teorica uy_product_teorica	  */
	public void setuy_product_teorica (BigDecimal uy_product_teorica)
	{
		set_Value (COLUMNNAME_uy_product_teorica, uy_product_teorica);
	}

	/** Get uy_product_teorica.
		@return uy_product_teorica	  */
	public BigDecimal getuy_product_teorica () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_product_teorica);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_tiempo_paradas.
		@param uy_tiempo_paradas uy_tiempo_paradas	  */
	public void setuy_tiempo_paradas (int uy_tiempo_paradas)
	{
		set_Value (COLUMNNAME_uy_tiempo_paradas, Integer.valueOf(uy_tiempo_paradas));
	}

	/** Get uy_tiempo_paradas.
		@return uy_tiempo_paradas	  */
	public int getuy_tiempo_paradas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_tiempo_paradas);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_tiempo_setup.
		@param uy_tiempo_setup uy_tiempo_setup	  */
	public void setuy_tiempo_setup (int uy_tiempo_setup)
	{
		set_Value (COLUMNNAME_uy_tiempo_setup, Integer.valueOf(uy_tiempo_setup));
	}

	/** Get uy_tiempo_setup.
		@return uy_tiempo_setup	  */
	public int getuy_tiempo_setup () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_tiempo_setup);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_unidades_hora.
		@param uy_unidades_hora uy_unidades_hora	  */
	public void setuy_unidades_hora (int uy_unidades_hora)
	{
		set_Value (COLUMNNAME_uy_unidades_hora, Integer.valueOf(uy_unidades_hora));
	}

	/** Get uy_unidades_hora.
		@return uy_unidades_hora	  */
	public int getuy_unidades_hora () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_unidades_hora);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_UnidHoraOp.
		@param UY_UnidHoraOp UY_UnidHoraOp	  */
	public void setUY_UnidHoraOp (BigDecimal UY_UnidHoraOp)
	{
		set_Value(COLUMNNAME_UY_UnidHoraOp, UY_UnidHoraOp);
	}

	/** Get UY_UnidHoraOp.
		@return UY_UnidHoraOp	  */
	public BigDecimal getUY_UnidHoraOp () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_UnidHoraOp);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}