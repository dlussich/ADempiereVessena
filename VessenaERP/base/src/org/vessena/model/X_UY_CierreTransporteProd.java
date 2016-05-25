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

/** Generated Model for UY_CierreTransporteProd
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CierreTransporteProd extends PO implements I_UY_CierreTransporteProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CierreTransporteProd (Properties ctx, int UY_CierreTransporteProd_ID, String trxName)
    {
      super (ctx, UY_CierreTransporteProd_ID, trxName);
      /** if (UY_CierreTransporteProd_ID == 0)
        {
			setC_UOM_ID (0);
			setM_Product_ID (0);
			setQtyEntered (Env.ZERO);
			setUY_CierreTransporteHdr_ID (0);
			setUY_CierreTransporteProd_ID (0);
			setuy_estado_producto (null);
// G
        } */
    }

    /** Load Constructor */
    public X_UY_CierreTransporteProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CierreTransporteProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_CierreTransporteHdr getUY_CierreTransporteHdr() throws RuntimeException
    {
		return (I_UY_CierreTransporteHdr)MTable.get(getCtx(), I_UY_CierreTransporteHdr.Table_Name)
			.getPO(getUY_CierreTransporteHdr_ID(), get_TrxName());	}

	/** Set UY_CierreTransporteHdr.
		@param UY_CierreTransporteHdr_ID UY_CierreTransporteHdr	  */
	public void setUY_CierreTransporteHdr_ID (int UY_CierreTransporteHdr_ID)
	{
		if (UY_CierreTransporteHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_CierreTransporteHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CierreTransporteHdr_ID, Integer.valueOf(UY_CierreTransporteHdr_ID));
	}

	/** Get UY_CierreTransporteHdr.
		@return UY_CierreTransporteHdr	  */
	public int getUY_CierreTransporteHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CierreTransporteHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CierreTransporteProd.
		@param UY_CierreTransporteProd_ID UY_CierreTransporteProd	  */
	public void setUY_CierreTransporteProd_ID (int UY_CierreTransporteProd_ID)
	{
		if (UY_CierreTransporteProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CierreTransporteProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CierreTransporteProd_ID, Integer.valueOf(UY_CierreTransporteProd_ID));
	}

	/** Get UY_CierreTransporteProd.
		@return UY_CierreTransporteProd	  */
	public int getUY_CierreTransporteProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CierreTransporteProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** uy_estado_producto AD_Reference_ID=1000075 */
	public static final int UY_ESTADO_PRODUCTO_AD_Reference_ID=1000075;
	/** Buena = G */
	public static final String UY_ESTADO_PRODUCTO_Buena = "G";
	/** Mala = B */
	public static final String UY_ESTADO_PRODUCTO_Mala = "B";
	/** Set uy_estado_producto.
		@param uy_estado_producto uy_estado_producto	  */
	public void setuy_estado_producto (String uy_estado_producto)
	{

		set_Value (COLUMNNAME_uy_estado_producto, uy_estado_producto);
	}

	/** Get uy_estado_producto.
		@return uy_estado_producto	  */
	public String getuy_estado_producto () 
	{
		return (String)get_Value(COLUMNNAME_uy_estado_producto);
	}
}