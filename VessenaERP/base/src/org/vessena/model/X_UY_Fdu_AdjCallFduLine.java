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

/** Generated Model for UY_Fdu_AdjCallFduLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Fdu_AdjCallFduLine extends PO implements I_UY_Fdu_AdjCallFduLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131122L;

    /** Standard Constructor */
    public X_UY_Fdu_AdjCallFduLine (Properties ctx, int UY_Fdu_AdjCallFduLine_ID, String trxName)
    {
      super (ctx, UY_Fdu_AdjCallFduLine_ID, trxName);
      /** if (UY_Fdu_AdjCallFduLine_ID == 0)
        {
			setUY_Fdu_AdjCallFduLine_ID (0);
			setUY_Fdu_AdjustmentCallFdu_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_AdjCallFduLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_AdjCallFduLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AccountNo.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get AccountNo.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
	}

	/** Set CodigoAjuste.
		@param CodigoAjuste 
		CodigoAjuste
	  */
	public void setCodigoAjuste (String CodigoAjuste)
	{
		set_Value (COLUMNNAME_CodigoAjuste, CodigoAjuste);
	}

	/** Get CodigoAjuste.
		@return CodigoAjuste
	  */
	public String getCodigoAjuste () 
	{
		return (String)get_Value(COLUMNNAME_CodigoAjuste);
	}

	/** Set FechaLlamada.
		@param FechaLlamada 
		FechaLlamada
	  */
	public void setFechaLlamada (Timestamp FechaLlamada)
	{
		set_Value (COLUMNNAME_FechaLlamada, FechaLlamada);
	}

	/** Get FechaLlamada.
		@return FechaLlamada
	  */
	public Timestamp getFechaLlamada () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaLlamada);
	}

	/** Set Importe.
		@param Importe Importe	  */
	public void setImporte (BigDecimal Importe)
	{
		set_Value (COLUMNNAME_Importe, Importe);
	}

	/** Get Importe.
		@return Importe	  */
	public BigDecimal getImporte () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Importe);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NroTarjetaTitular.
		@param NroTarjetaTitular NroTarjetaTitular	  */
	public void setNroTarjetaTitular (String NroTarjetaTitular)
	{
		set_Value (COLUMNNAME_NroTarjetaTitular, NroTarjetaTitular);
	}

	/** Get NroTarjetaTitular.
		@return NroTarjetaTitular	  */
	public String getNroTarjetaTitular () 
	{
		return (String)get_Value(COLUMNNAME_NroTarjetaTitular);
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

	/** Set UY_Fdu_AdjCallFduLine.
		@param UY_Fdu_AdjCallFduLine_ID UY_Fdu_AdjCallFduLine	  */
	public void setUY_Fdu_AdjCallFduLine_ID (int UY_Fdu_AdjCallFduLine_ID)
	{
		if (UY_Fdu_AdjCallFduLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_AdjCallFduLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_AdjCallFduLine_ID, Integer.valueOf(UY_Fdu_AdjCallFduLine_ID));
	}

	/** Get UY_Fdu_AdjCallFduLine.
		@return UY_Fdu_AdjCallFduLine	  */
	public int getUY_Fdu_AdjCallFduLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_AdjCallFduLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_AdjustmentCallFdu getUY_Fdu_AdjustmentCallFdu() throws RuntimeException
    {
		return (I_UY_Fdu_AdjustmentCallFdu)MTable.get(getCtx(), I_UY_Fdu_AdjustmentCallFdu.Table_Name)
			.getPO(getUY_Fdu_AdjustmentCallFdu_ID(), get_TrxName());	}

	/** Set UY_Fdu_AdjustmentCallFdu.
		@param UY_Fdu_AdjustmentCallFdu_ID UY_Fdu_AdjustmentCallFdu	  */
	public void setUY_Fdu_AdjustmentCallFdu_ID (int UY_Fdu_AdjustmentCallFdu_ID)
	{
		if (UY_Fdu_AdjustmentCallFdu_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_AdjustmentCallFdu_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_AdjustmentCallFdu_ID, Integer.valueOf(UY_Fdu_AdjustmentCallFdu_ID));
	}

	/** Get UY_Fdu_AdjustmentCallFdu.
		@return UY_Fdu_AdjustmentCallFdu	  */
	public int getUY_Fdu_AdjustmentCallFdu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_AdjustmentCallFdu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Afinidad getUY_Fdu_Afinidad() throws RuntimeException
    {
		return (I_UY_Fdu_Afinidad)MTable.get(getCtx(), I_UY_Fdu_Afinidad.Table_Name)
			.getPO(getUY_Fdu_Afinidad_ID(), get_TrxName());	}

	/** Set UY_Fdu_Afinidad.
		@param UY_Fdu_Afinidad_ID UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID)
	{
		if (UY_Fdu_Afinidad_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Afinidad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Afinidad_ID, Integer.valueOf(UY_Fdu_Afinidad_ID));
	}

	/** Get UY_Fdu_Afinidad.
		@return UY_Fdu_Afinidad	  */
	public int getUY_Fdu_Afinidad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Afinidad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_ModeloLiquidacion getUY_Fdu_ModeloLiquidacion() throws RuntimeException
    {
		return (I_UY_Fdu_ModeloLiquidacion)MTable.get(getCtx(), I_UY_Fdu_ModeloLiquidacion.Table_Name)
			.getPO(getUY_Fdu_ModeloLiquidacion_ID(), get_TrxName());	}

	/** Set UY_Fdu_ModeloLiquidacion.
		@param UY_Fdu_ModeloLiquidacion_ID UY_Fdu_ModeloLiquidacion	  */
	public void setUY_Fdu_ModeloLiquidacion_ID (int UY_Fdu_ModeloLiquidacion_ID)
	{
		if (UY_Fdu_ModeloLiquidacion_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_ModeloLiquidacion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_ModeloLiquidacion_ID, Integer.valueOf(UY_Fdu_ModeloLiquidacion_ID));
	}

	/** Get UY_Fdu_ModeloLiquidacion.
		@return UY_Fdu_ModeloLiquidacion	  */
	public int getUY_Fdu_ModeloLiquidacion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_ModeloLiquidacion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Productos getUY_Fdu_Productos() throws RuntimeException
    {
		return (I_UY_Fdu_Productos)MTable.get(getCtx(), I_UY_Fdu_Productos.Table_Name)
			.getPO(getUY_Fdu_Productos_ID(), get_TrxName());	}

	/** Set UY_Fdu_Productos.
		@param UY_Fdu_Productos_ID UY_Fdu_Productos	  */
	public void setUY_Fdu_Productos_ID (int UY_Fdu_Productos_ID)
	{
		if (UY_Fdu_Productos_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Productos_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Productos_ID, Integer.valueOf(UY_Fdu_Productos_ID));
	}

	/** Get UY_Fdu_Productos.
		@return UY_Fdu_Productos	  */
	public int getUY_Fdu_Productos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Productos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}