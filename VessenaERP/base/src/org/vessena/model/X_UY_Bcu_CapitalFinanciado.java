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

/** Generated Model for UY_Bcu_CapitalFinanciado
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Bcu_CapitalFinanciado extends PO implements I_UY_Bcu_CapitalFinanciado, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130906L;

    /** Standard Constructor */
    public X_UY_Bcu_CapitalFinanciado (Properties ctx, int UY_Bcu_CapitalFinanciado_ID, String trxName)
    {
      super (ctx, UY_Bcu_CapitalFinanciado_ID, trxName);
      /** if (UY_Bcu_CapitalFinanciado_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_Bcu_CapitalFinanciado_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Bcu_CapitalFinanciado (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Bcu_CapitalFinanciado[")
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

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cantidadoperaciones.
		@param cantidadoperaciones cantidadoperaciones	  */
	public void setcantidadoperaciones (BigDecimal cantidadoperaciones)
	{
		set_Value (COLUMNNAME_cantidadoperaciones, cantidadoperaciones);
	}

	/** Get cantidadoperaciones.
		@return cantidadoperaciones	  */
	public BigDecimal getcantidadoperaciones () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cantidadoperaciones);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CapitalInteres.
		@param CapitalInteres 
		CapitalInteres
	  */
	public void setCapitalInteres (BigDecimal CapitalInteres)
	{
		set_Value (COLUMNNAME_CapitalInteres, CapitalInteres);
	}

	/** Get CapitalInteres.
		@return CapitalInteres
	  */
	public BigDecimal getCapitalInteres () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CapitalInteres);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

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

	/** Set CuotaActual.
		@param CuotaActual 
		CuotaActual
	  */
	public void setCuotaActual (int CuotaActual)
	{
		set_Value (COLUMNNAME_CuotaActual, Integer.valueOf(CuotaActual));
	}

	/** Get CuotaActual.
		@return CuotaActual
	  */
	public int getCuotaActual () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CuotaActual);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cuotas.
		@param Cuotas 
		Cuotas
	  */
	public void setCuotas (int Cuotas)
	{
		set_Value (COLUMNNAME_Cuotas, Integer.valueOf(Cuotas));
	}

	/** Get Cuotas.
		@return Cuotas
	  */
	public int getCuotas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cuotas);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cupon.
		@param Cupon Cupon	  */
	public void setCupon (String Cupon)
	{
		set_Value (COLUMNNAME_Cupon, Cupon);
	}

	/** Get Cupon.
		@return Cupon	  */
	public String getCupon () 
	{
		return (String)get_Value(COLUMNNAME_Cupon);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set FechaCierre.
		@param FechaCierre FechaCierre	  */
	public void setFechaCierre (Timestamp FechaCierre)
	{
		set_Value (COLUMNNAME_FechaCierre, FechaCierre);
	}

	/** Get FechaCierre.
		@return FechaCierre	  */
	public Timestamp getFechaCierre () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaCierre);
	}

	/** Set FechaOperacion.
		@param FechaOperacion 
		FechaOperacion
	  */
	public void setFechaOperacion (Timestamp FechaOperacion)
	{
		set_Value (COLUMNNAME_FechaOperacion, FechaOperacion);
	}

	/** Get FechaOperacion.
		@return FechaOperacion
	  */
	public Timestamp getFechaOperacion () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaOperacion);
	}

	/** Set FechaPresentacion.
		@param FechaPresentacion 
		FechaPresentacion
	  */
	public void setFechaPresentacion (Timestamp FechaPresentacion)
	{
		set_Value (COLUMNNAME_FechaPresentacion, FechaPresentacion);
	}

	/** Get FechaPresentacion.
		@return FechaPresentacion
	  */
	public Timestamp getFechaPresentacion () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaPresentacion);
	}

	/** Set IsDebit.
		@param IsDebit IsDebit	  */
	public void setIsDebit (boolean IsDebit)
	{
		set_Value (COLUMNNAME_IsDebit, Boolean.valueOf(IsDebit));
	}

	/** Get IsDebit.
		@return IsDebit	  */
	public boolean isDebit () 
	{
		Object oo = get_Value(COLUMNNAME_IsDebit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsIncosistente.
		@param IsIncosistente 
		IsIncosistente
	  */
	public void setIsIncosistente (boolean IsIncosistente)
	{
		set_Value (COLUMNNAME_IsIncosistente, Boolean.valueOf(IsIncosistente));
	}

	/** Get IsIncosistente.
		@return IsIncosistente
	  */
	public boolean isIncosistente () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncosistente);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set plazoremanente.
		@param plazoremanente plazoremanente	  */
	public void setplazoremanente (BigDecimal plazoremanente)
	{
		set_Value (COLUMNNAME_plazoremanente, plazoremanente);
	}

	/** Get plazoremanente.
		@return plazoremanente	  */
	public BigDecimal getplazoremanente () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_plazoremanente);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tasa.
		@param Tasa 
		Tasa
	  */
	public void setTasa (BigDecimal Tasa)
	{
		set_Value (COLUMNNAME_Tasa, Tasa);
	}

	/** Get Tasa.
		@return Tasa
	  */
	public BigDecimal getTasa () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tasa);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tea.
		@param Tea 
		Tea
	  */
	public void setTea (BigDecimal Tea)
	{
		set_Value (COLUMNNAME_Tea, Tea);
	}

	/** Get Tea.
		@return Tea
	  */
	public BigDecimal getTea () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tea);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TipoDato.
		@param TipoDato 
		TipoDato
	  */
	public void setTipoDato (String TipoDato)
	{
		set_Value (COLUMNNAME_TipoDato, TipoDato);
	}

	/** Get TipoDato.
		@return TipoDato
	  */
	public String getTipoDato () 
	{
		return (String)get_Value(COLUMNNAME_TipoDato);
	}

	/** Set UY_Bcu_CapitalFinanciado.
		@param UY_Bcu_CapitalFinanciado_ID UY_Bcu_CapitalFinanciado	  */
	public void setUY_Bcu_CapitalFinanciado_ID (int UY_Bcu_CapitalFinanciado_ID)
	{
		if (UY_Bcu_CapitalFinanciado_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Bcu_CapitalFinanciado_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Bcu_CapitalFinanciado_ID, Integer.valueOf(UY_Bcu_CapitalFinanciado_ID));
	}

	/** Get UY_Bcu_CapitalFinanciado.
		@return UY_Bcu_CapitalFinanciado	  */
	public int getUY_Bcu_CapitalFinanciado_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Bcu_CapitalFinanciado_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Bcu_GracionTxtCapital.
		@param UY_Bcu_GracionTxtCapital_ID UY_Bcu_GracionTxtCapital	  */
	public void setUY_Bcu_GracionTxtCapital_ID (int UY_Bcu_GracionTxtCapital_ID)
	{
		if (UY_Bcu_GracionTxtCapital_ID < 1) 
			set_Value (COLUMNNAME_UY_Bcu_GracionTxtCapital_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Bcu_GracionTxtCapital_ID, Integer.valueOf(UY_Bcu_GracionTxtCapital_ID));
	}

	/** Get UY_Bcu_GracionTxtCapital.
		@return UY_Bcu_GracionTxtCapital	  */
	public int getUY_Bcu_GracionTxtCapital_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Bcu_GracionTxtCapital_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set vigencia.
		@param vigencia vigencia	  */
	public void setvigencia (Timestamp vigencia)
	{
		set_Value (COLUMNNAME_vigencia, vigencia);
	}

	/** Get vigencia.
		@return vigencia	  */
	public Timestamp getvigencia () 
	{
		return (Timestamp)get_Value(COLUMNNAME_vigencia);
	}
}