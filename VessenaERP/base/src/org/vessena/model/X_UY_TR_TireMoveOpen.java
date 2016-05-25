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

/** Generated Model for UY_TR_TireMoveOpen
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_TR_TireMoveOpen extends PO implements I_UY_TR_TireMoveOpen, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140611L;

    /** Standard Constructor */
    public X_UY_TR_TireMoveOpen (Properties ctx, int UY_TR_TireMoveOpen_ID, String trxName)
    {
      super (ctx, UY_TR_TireMoveOpen_ID, trxName);
      /** if (UY_TR_TireMoveOpen_ID == 0)
        {
			setIsAuxiliar (false);
			setIsChanged (false);
// N
			setUY_TR_TireMoveOpen_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_TireMoveOpen (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_TireMoveOpen[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** EstadoActual AD_Reference_ID=1000371 */
	public static final int ESTADOACTUAL_AD_Reference_ID=1000371;
	/** NUEVO = nuevo */
	public static final String ESTADOACTUAL_NUEVO = "nuevo";
	/** RECAUCHUTADO = primerrecauchutado */
	public static final String ESTADOACTUAL_RECAUCHUTADO = "primerrecauchutado";
	/** BAJA = baja */
	public static final String ESTADOACTUAL_BAJA = "baja";
	/** Set EstadoActual.
		@param EstadoActual 
		EstadoActual
	  */
	public void setEstadoActual (String EstadoActual)
	{

		set_Value (COLUMNNAME_EstadoActual, EstadoActual);
	}

	/** Get EstadoActual.
		@return EstadoActual
	  */
	public String getEstadoActual () 
	{
		return (String)get_Value(COLUMNNAME_EstadoActual);
	}

	/** Set IsAuxiliar.
		@param IsAuxiliar 
		IsAuxiliar
	  */
	public void setIsAuxiliar (boolean IsAuxiliar)
	{
		set_Value (COLUMNNAME_IsAuxiliar, Boolean.valueOf(IsAuxiliar));
	}

	/** Get IsAuxiliar.
		@return IsAuxiliar
	  */
	public boolean isAuxiliar () 
	{
		Object oo = get_Value(COLUMNNAME_IsAuxiliar);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsChanged.
		@param IsChanged IsChanged	  */
	public void setIsChanged (boolean IsChanged)
	{
		set_Value (COLUMNNAME_IsChanged, Boolean.valueOf(IsChanged));
	}

	/** Get IsChanged.
		@return IsChanged	  */
	public boolean isChanged () 
	{
		Object oo = get_Value(COLUMNNAME_IsChanged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	/** Set QtyRecauchutaje.
		@param Qty 
		QtyRecauchutaje
	  */
	public void setQty (int Qty)
	{
		set_Value (COLUMNNAME_Qty, Integer.valueOf(Qty));
	}

	/** Get QtyRecauchutaje.
		@return QtyRecauchutaje
	  */
	public int getQty () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Qty);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKm.
		@param QtyKm 
		QtyKm
	  */
	public void setQtyKm (BigDecimal QtyKm)
	{
		set_Value (COLUMNNAME_QtyKm, QtyKm);
	}

	/** Get QtyKm.
		@return QtyKm
	  */
	public BigDecimal getQtyKm () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyKm);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_TR_Tire getUY_TR_Tire() throws RuntimeException
    {
		return (I_UY_TR_Tire)MTable.get(getCtx(), I_UY_TR_Tire.Table_Name)
			.getPO(getUY_TR_Tire_ID(), get_TrxName());	}

	/** Set UY_TR_Tire.
		@param UY_TR_Tire_ID UY_TR_Tire	  */
	public void setUY_TR_Tire_ID (int UY_TR_Tire_ID)
	{
		if (UY_TR_Tire_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Tire_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Tire_ID, Integer.valueOf(UY_TR_Tire_ID));
	}

	/** Get UY_TR_Tire.
		@return UY_TR_Tire	  */
	public int getUY_TR_Tire_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Tire_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMark getUY_TR_TireMark() throws RuntimeException
    {
		return (I_UY_TR_TireMark)MTable.get(getCtx(), I_UY_TR_TireMark.Table_Name)
			.getPO(getUY_TR_TireMark_ID(), get_TrxName());	}

	/** Set UY_TR_TireMark.
		@param UY_TR_TireMark_ID UY_TR_TireMark	  */
	public void setUY_TR_TireMark_ID (int UY_TR_TireMark_ID)
	{
		if (UY_TR_TireMark_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMark_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMark_ID, Integer.valueOf(UY_TR_TireMark_ID));
	}

	/** Get UY_TR_TireMark.
		@return UY_TR_TireMark	  */
	public int getUY_TR_TireMark_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMark_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMeasure getUY_TR_TireMeasure() throws RuntimeException
    {
		return (I_UY_TR_TireMeasure)MTable.get(getCtx(), I_UY_TR_TireMeasure.Table_Name)
			.getPO(getUY_TR_TireMeasure_ID(), get_TrxName());	}

	/** Set UY_TR_TireMeasure.
		@param UY_TR_TireMeasure_ID UY_TR_TireMeasure	  */
	public void setUY_TR_TireMeasure_ID (int UY_TR_TireMeasure_ID)
	{
		if (UY_TR_TireMeasure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMeasure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMeasure_ID, Integer.valueOf(UY_TR_TireMeasure_ID));
	}

	/** Get UY_TR_TireMeasure.
		@return UY_TR_TireMeasure	  */
	public int getUY_TR_TireMeasure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMeasure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireModel getUY_TR_TireModel() throws RuntimeException
    {
		return (I_UY_TR_TireModel)MTable.get(getCtx(), I_UY_TR_TireModel.Table_Name)
			.getPO(getUY_TR_TireModel_ID(), get_TrxName());	}

	/** Set UY_TR_TireModel.
		@param UY_TR_TireModel_ID UY_TR_TireModel	  */
	public void setUY_TR_TireModel_ID (int UY_TR_TireModel_ID)
	{
		if (UY_TR_TireModel_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireModel_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireModel_ID, Integer.valueOf(UY_TR_TireModel_ID));
	}

	/** Get UY_TR_TireModel.
		@return UY_TR_TireModel	  */
	public int getUY_TR_TireModel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireModel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMove getUY_TR_TireMove() throws RuntimeException
    {
		return (I_UY_TR_TireMove)MTable.get(getCtx(), I_UY_TR_TireMove.Table_Name)
			.getPO(getUY_TR_TireMove_ID(), get_TrxName());	}

	/** Set UY_TR_TireMove.
		@param UY_TR_TireMove_ID UY_TR_TireMove	  */
	public void setUY_TR_TireMove_ID (int UY_TR_TireMove_ID)
	{
		if (UY_TR_TireMove_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMove_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMove_ID, Integer.valueOf(UY_TR_TireMove_ID));
	}

	/** Get UY_TR_TireMove.
		@return UY_TR_TireMove	  */
	public int getUY_TR_TireMove_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMove_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TireMoveOpen.
		@param UY_TR_TireMoveOpen_ID UY_TR_TireMoveOpen	  */
	public void setUY_TR_TireMoveOpen_ID (int UY_TR_TireMoveOpen_ID)
	{
		if (UY_TR_TireMoveOpen_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TireMoveOpen_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TireMoveOpen_ID, Integer.valueOf(UY_TR_TireMoveOpen_ID));
	}

	/** Get UY_TR_TireMoveOpen.
		@return UY_TR_TireMoveOpen	  */
	public int getUY_TR_TireMoveOpen_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMoveOpen_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}