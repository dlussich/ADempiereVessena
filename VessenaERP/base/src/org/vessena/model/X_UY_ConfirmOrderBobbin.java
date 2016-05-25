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

/** Generated Model for UY_ConfirmOrderBobbin
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ConfirmOrderBobbin extends PO implements I_UY_ConfirmOrderBobbin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151116L;

    /** Standard Constructor */
    public X_UY_ConfirmOrderBobbin (Properties ctx, int UY_ConfirmOrderBobbin_ID, String trxName)
    {
      super (ctx, UY_ConfirmOrderBobbin_ID, trxName);
      /** if (UY_ConfirmOrderBobbin_ID == 0)
        {
			setUY_ConfirmOrderBobbin_ID (0);
			setUY_Confirmorderhdr_ID (0);
			setUY_Devolucion (Env.ZERO);
			setWeight (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UY_ConfirmOrderBobbin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ConfirmOrderBobbin[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set numero.
		@param numero numero	  */
	public void setnumero (String numero)
	{
		set_Value (COLUMNNAME_numero, numero);
	}

	/** Get numero.
		@return numero	  */
	public String getnumero () 
	{
		return (String)get_Value(COLUMNNAME_numero);
	}

	/** Set Ordered Quantity.
		@param QtyOrdered 
		Ordered Quantity
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Ordered Quantity.
		@return Ordered Quantity
	  */
	public BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_ConfirmOrderBobbin.
		@param UY_ConfirmOrderBobbin_ID UY_ConfirmOrderBobbin	  */
	public void setUY_ConfirmOrderBobbin_ID (int UY_ConfirmOrderBobbin_ID)
	{
		if (UY_ConfirmOrderBobbin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ConfirmOrderBobbin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ConfirmOrderBobbin_ID, Integer.valueOf(UY_ConfirmOrderBobbin_ID));
	}

	/** Get UY_ConfirmOrderBobbin.
		@return UY_ConfirmOrderBobbin	  */
	public int getUY_ConfirmOrderBobbin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ConfirmOrderBobbin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Confirmorderhdr getUY_Confirmorderhdr() throws RuntimeException
    {
		return (I_UY_Confirmorderhdr)MTable.get(getCtx(), I_UY_Confirmorderhdr.Table_Name)
			.getPO(getUY_Confirmorderhdr_ID(), get_TrxName());	}

	/** Set UY_Confirmorderhdr.
		@param UY_Confirmorderhdr_ID UY_Confirmorderhdr	  */
	public void setUY_Confirmorderhdr_ID (int UY_Confirmorderhdr_ID)
	{
		if (UY_Confirmorderhdr_ID < 1) 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, Integer.valueOf(UY_Confirmorderhdr_ID));
	}

	/** Get UY_Confirmorderhdr.
		@return UY_Confirmorderhdr	  */
	public int getUY_Confirmorderhdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Confirmorderhdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Desperdicio.
		@param UY_Desperdicio 
		kg de Desperdicio
	  */
	public void setUY_Desperdicio (BigDecimal UY_Desperdicio)
	{
		set_Value (COLUMNNAME_UY_Desperdicio, UY_Desperdicio);
	}

	/** Get UY_Desperdicio.
		@return kg de Desperdicio
	  */
	public BigDecimal getUY_Desperdicio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_Desperdicio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_Devolucion.
		@param UY_Devolucion 
		kg Devueltos
	  */
	public void setUY_Devolucion (BigDecimal UY_Devolucion)
	{
		set_Value (COLUMNNAME_UY_Devolucion, UY_Devolucion);
	}

	/** Get UY_Devolucion.
		@return kg Devueltos
	  */
	public BigDecimal getUY_Devolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_Devolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_RefileYDescarne.
		@param UY_RefileYDescarne 
		kg de Refile Y Descarne
	  */
	public void setUY_RefileYDescarne (BigDecimal UY_RefileYDescarne)
	{
		set_Value (COLUMNNAME_UY_RefileYDescarne, UY_RefileYDescarne);
	}

	/** Get UY_RefileYDescarne.
		@return kg de Refile Y Descarne
	  */
	public BigDecimal getUY_RefileYDescarne () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_RefileYDescarne);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}