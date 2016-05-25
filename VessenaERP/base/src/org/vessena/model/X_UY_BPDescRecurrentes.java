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

/** Generated Model for UY_BPDescRecurrentes
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_BPDescRecurrentes extends PO implements I_UY_BPDescRecurrentes, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120612L;

    /** Standard Constructor */
    public X_UY_BPDescRecurrentes (Properties ctx, int UY_BPDescRecurrentes_ID, String trxName)
    {
      super (ctx, UY_BPDescRecurrentes_ID, trxName);
      /** if (UY_BPDescRecurrentes_ID == 0)
        {
			setC_BPartner_ID (0);
			setcuotas (0);
			settipo (null);
			setUY_BPDescRecurrentes_ID (0);
			setUY_HRTipoDescuento_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BPDescRecurrentes (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BPDescRecurrentes[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amountcuota.
		@param amountcuota amountcuota	  */
	public void setamountcuota (BigDecimal amountcuota)
	{
		set_Value (COLUMNNAME_amountcuota, amountcuota);
	}

	/** Get amountcuota.
		@return amountcuota	  */
	public BigDecimal getamountcuota () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amountcuota);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amountfijo.
		@param amountfijo amountfijo	  */
	public void setamountfijo (BigDecimal amountfijo)
	{
		set_Value (COLUMNNAME_amountfijo, amountfijo);
	}

	/** Get amountfijo.
		@return amountfijo	  */
	public BigDecimal getamountfijo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amountfijo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set cuotas.
		@param cuotas cuotas	  */
	public void setcuotas (int cuotas)
	{
		set_Value (COLUMNNAME_cuotas, Integer.valueOf(cuotas));
	}

	/** Get cuotas.
		@return cuotas	  */
	public int getcuotas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cuotas);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set fechacuota.
		@param fechacuota fechacuota	  */
	public void setfechacuota (Timestamp fechacuota)
	{
		set_Value (COLUMNNAME_fechacuota, fechacuota);
	}

	/** Get fechacuota.
		@return fechacuota	  */
	public Timestamp getfechacuota () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechacuota);
	}

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(getHR_Concept_ID(), get_TrxName());	}

	/** Set Payroll Concept.
		@param HR_Concept_ID Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get Payroll Concept.
		@return Payroll Concept	  */
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set porcentaje.
		@param porcentaje porcentaje	  */
	public void setporcentaje (BigDecimal porcentaje)
	{
		set_Value (COLUMNNAME_porcentaje, porcentaje);
	}

	/** Get porcentaje.
		@return porcentaje	  */
	public BigDecimal getporcentaje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcentaje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** tipo AD_Reference_ID=1000191 */
	public static final int TIPO_AD_Reference_ID=1000191;
	/** Fijo Mensual = FM */
	public static final String TIPO_FijoMensual = "FM";
	/** % Nominal = PN */
	public static final String TIPO_Nominal = "PN";
	/** Cuotas = CU */
	public static final String TIPO_Cuotas = "CU";
	/** Set tipo.
		@param tipo tipo	  */
	public void settipo (String tipo)
	{

		set_Value (COLUMNNAME_tipo, tipo);
	}

	/** Get tipo.
		@return tipo	  */
	public String gettipo () 
	{
		return (String)get_Value(COLUMNNAME_tipo);
	}

	/** Set UY_BPDescRecurrentes.
		@param UY_BPDescRecurrentes_ID UY_BPDescRecurrentes	  */
	public void setUY_BPDescRecurrentes_ID (int UY_BPDescRecurrentes_ID)
	{
		if (UY_BPDescRecurrentes_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BPDescRecurrentes_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BPDescRecurrentes_ID, Integer.valueOf(UY_BPDescRecurrentes_ID));
	}

	/** Get UY_BPDescRecurrentes.
		@return UY_BPDescRecurrentes	  */
	public int getUY_BPDescRecurrentes_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BPDescRecurrentes_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/*public I_UY_HRTipoDescuento getUY_HRTipoDescuento() throws RuntimeException
    {
		return (I_UY_HRTipoDescuento)MTable.get(getCtx(), I_UY_HRTipoDescuento.Table_Name)
			.getPO(getUY_HRTipoDescuento_ID(), get_TrxName());	}*/

	/** Set UY_HRTipoDescuento.
		@param UY_HRTipoDescuento_ID UY_HRTipoDescuento	  */
	public void setUY_HRTipoDescuento_ID (int UY_HRTipoDescuento_ID)
	{
		if (UY_HRTipoDescuento_ID < 1) 
			set_Value (COLUMNNAME_UY_HRTipoDescuento_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRTipoDescuento_ID, Integer.valueOf(UY_HRTipoDescuento_ID));
	}

	/** Get UY_HRTipoDescuento.
		@return UY_HRTipoDescuento	  */
	public int getUY_HRTipoDescuento_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRTipoDescuento_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}