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

/** Generated Model for UY_Fdu_MassiveAdjustLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_MassiveAdjustLine extends PO implements I_UY_Fdu_MassiveAdjustLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140107L;

    /** Standard Constructor */
    public X_UY_Fdu_MassiveAdjustLine (Properties ctx, int UY_Fdu_MassiveAdjustLine_ID, String trxName)
    {
      super (ctx, UY_Fdu_MassiveAdjustLine_ID, trxName);
      /** if (UY_Fdu_MassiveAdjustLine_ID == 0)
        {
			setUY_Fdu_MassiveAdjustLine_ID (0);
			setUY_Fdu_MassiveAdjustment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_MassiveAdjustLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_MassiveAdjustLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
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

	/** Set cierreanterior.
		@param cierreanterior cierreanterior	  */
	public void setcierreanterior (Timestamp cierreanterior)
	{
		set_Value (COLUMNNAME_cierreanterior, cierreanterior);
	}

	/** Get cierreanterior.
		@return cierreanterior	  */
	public Timestamp getcierreanterior () 
	{
		return (Timestamp)get_Value(COLUMNNAME_cierreanterior);
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

	/** Set Cotizacion.
		@param Cotizacion 
		Cotizacion
	  */
	public void setCotizacion (BigDecimal Cotizacion)
	{
		set_Value (COLUMNNAME_Cotizacion, Cotizacion);
	}

	/** Get Cotizacion.
		@return Cotizacion
	  */
	public BigDecimal getCotizacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cotizacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DiasAtraso.
		@param DiasAtraso 
		DiasAtraso
	  */
	public void setDiasAtraso (int DiasAtraso)
	{
		set_Value (COLUMNNAME_DiasAtraso, Integer.valueOf(DiasAtraso));
	}

	/** Get DiasAtraso.
		@return DiasAtraso
	  */
	public int getDiasAtraso () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasAtraso);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set fechapago.
		@param fechapago fechapago	  */
	public void setfechapago (Timestamp fechapago)
	{
		set_Value (COLUMNNAME_fechapago, fechapago);
	}

	/** Get fechapago.
		@return fechapago	  */
	public Timestamp getfechapago () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechapago);
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

	/** Set ImportePago.
		@param ImportePago 
		ImportePago
	  */
	public void setImportePago (BigDecimal ImportePago)
	{
		set_Value (COLUMNNAME_ImportePago, ImportePago);
	}

	/** Get ImportePago.
		@return ImportePago
	  */
	public BigDecimal getImportePago () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ImportePago);
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

	/** Set MoraEnAnio.
		@param MoraEnAnio 
		MoraEnAnio
	  */
	public void setMoraEnAnio (int MoraEnAnio)
	{
		set_Value (COLUMNNAME_MoraEnAnio, Integer.valueOf(MoraEnAnio));
	}

	/** Get MoraEnAnio.
		@return MoraEnAnio
	  */
	public int getMoraEnAnio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MoraEnAnio);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set PagoMinimoAnterior.
		@param PagoMinimoAnterior 
		PagoMinimoAnterior
	  */
	public void setPagoMinimoAnterior (BigDecimal PagoMinimoAnterior)
	{
		set_Value (COLUMNNAME_PagoMinimoAnterior, PagoMinimoAnterior);
	}

	/** Get PagoMinimoAnterior.
		@return PagoMinimoAnterior
	  */
	public BigDecimal getPagoMinimoAnterior () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PagoMinimoAnterior);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyCredits.
		@param QtyCredits 
		QtyCredits
	  */
	public void setQtyCredits (BigDecimal QtyCredits)
	{
		set_Value (COLUMNNAME_QtyCredits, QtyCredits);
	}

	/** Get QtyCredits.
		@return QtyCredits
	  */
	public BigDecimal getQtyCredits () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCredits);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyDebits.
		@param QtyDebits 
		QtyDebits
	  */
	public void setQtyDebits (BigDecimal QtyDebits)
	{
		set_Value (COLUMNNAME_QtyDebits, QtyDebits);
	}

	/** Get QtyDebits.
		@return QtyDebits
	  */
	public BigDecimal getQtyDebits () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDebits);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SaldoAnterior.
		@param SaldoAnterior 
		SaldoAnterior
	  */
	public void setSaldoAnterior (BigDecimal SaldoAnterior)
	{
		set_Value (COLUMNNAME_SaldoAnterior, SaldoAnterior);
	}

	/** Get SaldoAnterior.
		@return SaldoAnterior
	  */
	public BigDecimal getSaldoAnterior () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SaldoAnterior);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SignoSaldo.
		@param SignoSaldo 
		SignoSaldo
	  */
	public void setSignoSaldo (String SignoSaldo)
	{
		set_Value (COLUMNNAME_SignoSaldo, SignoSaldo);
	}

	/** Get SignoSaldo.
		@return SignoSaldo
	  */
	public String getSignoSaldo () 
	{
		return (String)get_Value(COLUMNNAME_SignoSaldo);
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

	/** Set UY_Fdu_MassiveAdjustLine.
		@param UY_Fdu_MassiveAdjustLine_ID UY_Fdu_MassiveAdjustLine	  */
	public void setUY_Fdu_MassiveAdjustLine_ID (int UY_Fdu_MassiveAdjustLine_ID)
	{
		if (UY_Fdu_MassiveAdjustLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_MassiveAdjustLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_MassiveAdjustLine_ID, Integer.valueOf(UY_Fdu_MassiveAdjustLine_ID));
	}

	/** Get UY_Fdu_MassiveAdjustLine.
		@return UY_Fdu_MassiveAdjustLine	  */
	public int getUY_Fdu_MassiveAdjustLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_MassiveAdjustLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_MassiveAdjustment getUY_Fdu_MassiveAdjustment() throws RuntimeException
    {
		return (I_UY_Fdu_MassiveAdjustment)MTable.get(getCtx(), I_UY_Fdu_MassiveAdjustment.Table_Name)
			.getPO(getUY_Fdu_MassiveAdjustment_ID(), get_TrxName());	}

	/** Set UY_Fdu_MassiveAdjustment.
		@param UY_Fdu_MassiveAdjustment_ID UY_Fdu_MassiveAdjustment	  */
	public void setUY_Fdu_MassiveAdjustment_ID (int UY_Fdu_MassiveAdjustment_ID)
	{
		if (UY_Fdu_MassiveAdjustment_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_MassiveAdjustment_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_MassiveAdjustment_ID, Integer.valueOf(UY_Fdu_MassiveAdjustment_ID));
	}

	/** Get UY_Fdu_MassiveAdjustment.
		@return UY_Fdu_MassiveAdjustment	  */
	public int getUY_Fdu_MassiveAdjustment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_MassiveAdjustment_ID);
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

	/** Set VencimientoAnterior.
		@param VencimientoAnterior 
		VencimientoAnterior
	  */
	public void setVencimientoAnterior (Timestamp VencimientoAnterior)
	{
		set_Value (COLUMNNAME_VencimientoAnterior, VencimientoAnterior);
	}

	/** Get VencimientoAnterior.
		@return VencimientoAnterior
	  */
	public Timestamp getVencimientoAnterior () 
	{
		return (Timestamp)get_Value(COLUMNNAME_VencimientoAnterior);
	}
}