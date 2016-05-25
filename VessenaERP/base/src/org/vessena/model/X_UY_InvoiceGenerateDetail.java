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

/** Generated Model for UY_InvoiceGenerateDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_InvoiceGenerateDetail extends PO implements I_UY_InvoiceGenerateDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151228L;

    /** Standard Constructor */
    public X_UY_InvoiceGenerateDetail (Properties ctx, int UY_InvoiceGenerateDetail_ID, String trxName)
    {
      super (ctx, UY_InvoiceGenerateDetail_ID, trxName);
      /** if (UY_InvoiceGenerateDetail_ID == 0)
        {
			setC_Currency_ID (0);
			setdeuda_id (0);
			setfecha (new Timestamp( System.currentTimeMillis() ));
			setid (0);
			setmonto (Env.ZERO);
			setuser_id (0);
			setUY_InvoiceGenerateDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvoiceGenerateDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvoiceGenerateDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set abonado.
		@param abonado abonado	  */
	public void setabonado (String abonado)
	{
		set_Value (COLUMNNAME_abonado, abonado);
	}

	/** Get abonado.
		@return abonado	  */
	public String getabonado () 
	{
		return (String)get_Value(COLUMNNAME_abonado);
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

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set concepto.
		@param concepto concepto	  */
	public void setconcepto (String concepto)
	{
		set_Value (COLUMNNAME_concepto, concepto);
	}

	/** Get concepto.
		@return concepto	  */
	public String getconcepto () 
	{
		return (String)get_Value(COLUMNNAME_concepto);
	}

	/** Set convenio_cuota_id.
		@param convenio_cuota_id convenio_cuota_id	  */
	public void setconvenio_cuota_id (int convenio_cuota_id)
	{
		set_Value (COLUMNNAME_convenio_cuota_id, Integer.valueOf(convenio_cuota_id));
	}

	/** Get convenio_cuota_id.
		@return convenio_cuota_id	  */
	public int getconvenio_cuota_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_convenio_cuota_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set deuda_id.
		@param deuda_id deuda_id	  */
	public void setdeuda_id (int deuda_id)
	{
		set_Value (COLUMNNAME_deuda_id, Integer.valueOf(deuda_id));
	}

	/** Get deuda_id.
		@return deuda_id	  */
	public int getdeuda_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_deuda_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set dias_atraso.
		@param dias_atraso dias_atraso	  */
	public void setdias_atraso (int dias_atraso)
	{
		set_Value (COLUMNNAME_dias_atraso, Integer.valueOf(dias_atraso));
	}

	/** Get dias_atraso.
		@return dias_atraso	  */
	public int getdias_atraso () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_dias_atraso);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set empresa_id.
		@param empresa_id empresa_id	  */
	public void setempresa_id (int empresa_id)
	{
		set_Value (COLUMNNAME_empresa_id, Integer.valueOf(empresa_id));
	}

	/** Get empresa_id.
		@return empresa_id	  */
	public int getempresa_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_empresa_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set fecha.
		@param fecha fecha	  */
	public void setfecha (Timestamp fecha)
	{
		set_Value (COLUMNNAME_fecha, fecha);
	}

	/** Get fecha.
		@return fecha	  */
	public Timestamp getfecha () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecha);
	}

	/** Set forma.
		@param forma forma	  */
	public void setforma (String forma)
	{
		set_Value (COLUMNNAME_forma, forma);
	}

	/** Get forma.
		@return forma	  */
	public String getforma () 
	{
		return (String)get_Value(COLUMNNAME_forma);
	}

	/** Set id.
		@param id id	  */
	public void setid (int id)
	{
		set_Value (COLUMNNAME_id, Integer.valueOf(id));
	}

	/** Get id.
		@return id	  */
	public int getid () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set lecturaAD360.
		@param lecturaAD360 lecturaAD360	  */
	public void setlecturaAD360 (Timestamp lecturaAD360)
	{
		set_Value (COLUMNNAME_lecturaAD360, lecturaAD360);
	}

	/** Get lecturaAD360.
		@return lecturaAD360	  */
	public Timestamp getlecturaAD360 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_lecturaAD360);
	}

	/** Set lugar.
		@param lugar lugar	  */
	public void setlugar (String lugar)
	{
		set_Value (COLUMNNAME_lugar, lugar);
	}

	/** Get lugar.
		@return lugar	  */
	public String getlugar () 
	{
		return (String)get_Value(COLUMNNAME_lugar);
	}

	/** Set moneda_id.
		@param moneda_id moneda_id	  */
	public void setmoneda_id (int moneda_id)
	{
		set_Value (COLUMNNAME_moneda_id, Integer.valueOf(moneda_id));
	}

	/** Get moneda_id.
		@return moneda_id	  */
	public int getmoneda_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_moneda_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set monto.
		@param monto monto	  */
	public void setmonto (BigDecimal monto)
	{
		set_Value (COLUMNNAME_monto, monto);
	}

	/** Get monto.
		@return monto	  */
	public BigDecimal getmonto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_monto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set numero_recibo.
		@param numero_recibo numero_recibo	  */
	public void setnumero_recibo (String numero_recibo)
	{
		set_Value (COLUMNNAME_numero_recibo, numero_recibo);
	}

	/** Get numero_recibo.
		@return numero_recibo	  */
	public String getnumero_recibo () 
	{
		return (String)get_Value(COLUMNNAME_numero_recibo);
	}

	/** Set pago_borrado_id.
		@param pago_borrado_id pago_borrado_id	  */
	public void setpago_borrado_id (int pago_borrado_id)
	{
		set_Value (COLUMNNAME_pago_borrado_id, Integer.valueOf(pago_borrado_id));
	}

	/** Get pago_borrado_id.
		@return pago_borrado_id	  */
	public int getpago_borrado_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_pago_borrado_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set pago_id.
		@param pago_id pago_id	  */
	public void setpago_id (int pago_id)
	{
		set_Value (COLUMNNAME_pago_id, Integer.valueOf(pago_id));
	}

	/** Get pago_id.
		@return pago_id	  */
	public int getpago_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_pago_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Percentage.
		@param Percentage 
		Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Percentage.
		@return Percent of the entire amount
	  */
	public BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set persona_documento.
		@param persona_documento persona_documento	  */
	public void setpersona_documento (String persona_documento)
	{
		set_Value (COLUMNNAME_persona_documento, persona_documento);
	}

	/** Get persona_documento.
		@return persona_documento	  */
	public String getpersona_documento () 
	{
		return (String)get_Value(COLUMNNAME_persona_documento);
	}

	/** Set persona_nombre.
		@param persona_nombre persona_nombre	  */
	public void setpersona_nombre (String persona_nombre)
	{
		set_Value (COLUMNNAME_persona_nombre, persona_nombre);
	}

	/** Get persona_nombre.
		@return persona_nombre	  */
	public String getpersona_nombre () 
	{
		return (String)get_Value(COLUMNNAME_persona_nombre);
	}

	/** Set procesadoAD360.
		@param procesadoAD360 procesadoAD360	  */
	public void setprocesadoAD360 (Timestamp procesadoAD360)
	{
		set_Value (COLUMNNAME_procesadoAD360, procesadoAD360);
	}

	/** Get procesadoAD360.
		@return procesadoAD360	  */
	public Timestamp getprocesadoAD360 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_procesadoAD360);
	}

	/** Set segmento.
		@param segmento segmento	  */
	public void setsegmento (String segmento)
	{
		set_Value (COLUMNNAME_segmento, segmento);
	}

	/** Get segmento.
		@return segmento	  */
	public String getsegmento () 
	{
		return (String)get_Value(COLUMNNAME_segmento);
	}

	/** Set sub_cartera.
		@param sub_cartera sub_cartera	  */
	public void setsub_cartera (String sub_cartera)
	{
		set_Value (COLUMNNAME_sub_cartera, sub_cartera);
	}

	/** Get sub_cartera.
		@return sub_cartera	  */
	public String getsub_cartera () 
	{
		return (String)get_Value(COLUMNNAME_sub_cartera);
	}

	/** Set user_id.
		@param user_id user_id	  */
	public void setuser_id (int user_id)
	{
		set_Value (COLUMNNAME_user_id, Integer.valueOf(user_id));
	}

	/** Get user_id.
		@return user_id	  */
	public int getuser_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_user_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_InvoiceGenerateDetail.
		@param UY_InvoiceGenerateDetail_ID UY_InvoiceGenerateDetail	  */
	public void setUY_InvoiceGenerateDetail_ID (int UY_InvoiceGenerateDetail_ID)
	{
		if (UY_InvoiceGenerateDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceGenerateDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceGenerateDetail_ID, Integer.valueOf(UY_InvoiceGenerateDetail_ID));
	}

	/** Get UY_InvoiceGenerateDetail.
		@return UY_InvoiceGenerateDetail	  */
	public int getUY_InvoiceGenerateDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceGenerateDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_InvoiceGenerate getUY_InvoiceGenerate() throws RuntimeException
    {
		return (I_UY_InvoiceGenerate)MTable.get(getCtx(), I_UY_InvoiceGenerate.Table_Name)
			.getPO(getUY_InvoiceGenerate_ID(), get_TrxName());	}

	/** Set UY_InvoiceGenerate.
		@param UY_InvoiceGenerate_ID UY_InvoiceGenerate	  */
	public void setUY_InvoiceGenerate_ID (int UY_InvoiceGenerate_ID)
	{
		if (UY_InvoiceGenerate_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceGenerate_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceGenerate_ID, Integer.valueOf(UY_InvoiceGenerate_ID));
	}

	/** Get UY_InvoiceGenerate.
		@return UY_InvoiceGenerate	  */
	public int getUY_InvoiceGenerate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceGenerate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_InvoiceGenerateLine getUY_InvoiceGenerateLine() throws RuntimeException
    {
		return (I_UY_InvoiceGenerateLine)MTable.get(getCtx(), I_UY_InvoiceGenerateLine.Table_Name)
			.getPO(getUY_InvoiceGenerateLine_ID(), get_TrxName());	}

	/** Set UY_InvoiceGenerateLine.
		@param UY_InvoiceGenerateLine_ID UY_InvoiceGenerateLine	  */
	public void setUY_InvoiceGenerateLine_ID (int UY_InvoiceGenerateLine_ID)
	{
		if (UY_InvoiceGenerateLine_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceGenerateLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceGenerateLine_ID, Integer.valueOf(UY_InvoiceGenerateLine_ID));
	}

	/** Get UY_InvoiceGenerateLine.
		@return UY_InvoiceGenerateLine	  */
	public int getUY_InvoiceGenerateLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceGenerateLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}