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

/** Generated Model for UY_PickingLineDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_PickingLineDetail extends PO implements I_UY_PickingLineDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_PickingLineDetail (Properties ctx, int UY_PickingLineDetail_ID, String trxName)
    {
      super (ctx, UY_PickingLineDetail_ID, trxName);
      /** if (UY_PickingLineDetail_ID == 0)
        {
			setC_UOM_ID (0);
			setM_Product_ID (0);
			setQtyEntered (Env.ZERO);
			setQtyReserved (Env.ZERO);
			setUY_PickingLineDetail_ID (0);
			setUY_PickingLine_ID (Env.ZERO);
			setuy_qtyreserved_original (Env.ZERO);
			setUY_ReservaPedidoHdr_ID (0);
			setUY_ReservaPedidoLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PickingLineDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PickingLineDetail[")
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Reserved Quantity.
		@param QtyReserved 
		Reserved Quantity
	  */
	public void setQtyReserved (BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Reserved Quantity.
		@return Reserved Quantity
	  */
	public BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_bonificaregla.
		@param uy_bonificaregla uy_bonificaregla	  */
	public void setuy_bonificaregla (BigDecimal uy_bonificaregla)
	{
		set_Value (COLUMNNAME_uy_bonificaregla, uy_bonificaregla);
	}

	/** Get uy_bonificaregla.
		@return uy_bonificaregla	  */
	public BigDecimal getuy_bonificaregla () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_bonificaregla);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_EsBonificCruzada.
		@param UY_EsBonificCruzada UY_EsBonificCruzada	  */
	public void setUY_EsBonificCruzada (boolean UY_EsBonificCruzada)
	{
		set_Value (COLUMNNAME_UY_EsBonificCruzada, Boolean.valueOf(UY_EsBonificCruzada));
	}

	/** Get UY_EsBonificCruzada.
		@return UY_EsBonificCruzada	  */
	public boolean isUY_EsBonificCruzada () 
	{
		Object oo = get_Value(COLUMNNAME_UY_EsBonificCruzada);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_PickingLineDetail.
		@param UY_PickingLineDetail_ID UY_PickingLineDetail	  */
	public void setUY_PickingLineDetail_ID (int UY_PickingLineDetail_ID)
	{
		if (UY_PickingLineDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PickingLineDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PickingLineDetail_ID, Integer.valueOf(UY_PickingLineDetail_ID));
	}

	/** Get UY_PickingLineDetail.
		@return UY_PickingLineDetail	  */
	public int getUY_PickingLineDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PickingLineDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PickingLine.
		@param UY_PickingLine_ID UY_PickingLine	  */
	public void setUY_PickingLine_ID (BigDecimal UY_PickingLine_ID)
	{
		set_Value (COLUMNNAME_UY_PickingLine_ID, UY_PickingLine_ID);
	}

	/** Get UY_PickingLine.
		@return UY_PickingLine	  */
	public BigDecimal getUY_PickingLine_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_PickingLine_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_qtyentered_original.
		@param uy_qtyentered_original uy_qtyentered_original	  */
	public void setuy_qtyentered_original (BigDecimal uy_qtyentered_original)
	{
		set_Value (COLUMNNAME_uy_qtyentered_original, uy_qtyentered_original);
	}

	/** Get uy_qtyentered_original.
		@return uy_qtyentered_original	  */
	public BigDecimal getuy_qtyentered_original () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_qtyentered_original);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_qtyreserved_original.
		@param uy_qtyreserved_original uy_qtyreserved_original	  */
	public void setuy_qtyreserved_original (BigDecimal uy_qtyreserved_original)
	{
		set_Value (COLUMNNAME_uy_qtyreserved_original, uy_qtyreserved_original);
	}

	/** Get uy_qtyreserved_original.
		@return uy_qtyreserved_original	  */
	public BigDecimal getuy_qtyreserved_original () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_qtyreserved_original);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_ReservaPedidoHdr getUY_ReservaPedidoHdr() throws RuntimeException
    {
		return (I_UY_ReservaPedidoHdr)MTable.get(getCtx(), I_UY_ReservaPedidoHdr.Table_Name)
			.getPO(getUY_ReservaPedidoHdr_ID(), get_TrxName());	}

	/** Set UY_ReservaPedidoHdr.
		@param UY_ReservaPedidoHdr_ID UY_ReservaPedidoHdr	  */
	public void setUY_ReservaPedidoHdr_ID (int UY_ReservaPedidoHdr_ID)
	{
		if (UY_ReservaPedidoHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_ReservaPedidoHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ReservaPedidoHdr_ID, Integer.valueOf(UY_ReservaPedidoHdr_ID));
	}

	/** Get UY_ReservaPedidoHdr.
		@return UY_ReservaPedidoHdr	  */
	public int getUY_ReservaPedidoHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReservaPedidoHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ReservaPedidoLine getUY_ReservaPedidoLine() throws RuntimeException
    {
		return (I_UY_ReservaPedidoLine)MTable.get(getCtx(), I_UY_ReservaPedidoLine.Table_Name)
			.getPO(getUY_ReservaPedidoLine_ID(), get_TrxName());	}

	/** Set UY_ReservaPedidoLine.
		@param UY_ReservaPedidoLine_ID UY_ReservaPedidoLine	  */
	public void setUY_ReservaPedidoLine_ID (int UY_ReservaPedidoLine_ID)
	{
		if (UY_ReservaPedidoLine_ID < 1) 
			set_Value (COLUMNNAME_UY_ReservaPedidoLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ReservaPedidoLine_ID, Integer.valueOf(UY_ReservaPedidoLine_ID));
	}

	/** Get UY_ReservaPedidoLine.
		@return UY_ReservaPedidoLine	  */
	public int getUY_ReservaPedidoLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReservaPedidoLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TieneBonificCruzada.
		@param UY_TieneBonificCruzada UY_TieneBonificCruzada	  */
	public void setUY_TieneBonificCruzada (boolean UY_TieneBonificCruzada)
	{
		set_Value (COLUMNNAME_UY_TieneBonificCruzada, Boolean.valueOf(UY_TieneBonificCruzada));
	}

	/** Get UY_TieneBonificCruzada.
		@return UY_TieneBonificCruzada	  */
	public boolean isUY_TieneBonificCruzada () 
	{
		Object oo = get_Value(COLUMNNAME_UY_TieneBonificCruzada);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}