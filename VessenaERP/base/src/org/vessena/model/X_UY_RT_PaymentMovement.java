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

/** Generated Model for UY_RT_PaymentMovement
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_PaymentMovement extends PO implements I_UY_RT_PaymentMovement, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160309L;

    /** Standard Constructor */
    public X_UY_RT_PaymentMovement (Properties ctx, int UY_RT_PaymentMovement_ID, String trxName)
    {
      super (ctx, UY_RT_PaymentMovement_ID, trxName);
      /** if (UY_RT_PaymentMovement_ID == 0)
        {
			setUY_RT_PaymentMovement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_PaymentMovement (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_PaymentMovement[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Org getAD_Org_To() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Org)MTable.get(getCtx(), org.compiere.model.I_AD_Org.Table_Name)
			.getPO(getAD_Org_ID_To(), get_TrxName());	}

	/** Set AD_Org_ID_To.
		@param AD_Org_ID_To AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To)
	{
		set_Value (COLUMNNAME_AD_Org_ID_To, Integer.valueOf(AD_Org_ID_To));
	}

	/** Get AD_Org_ID_To.
		@return AD_Org_ID_To	  */
	public int getAD_Org_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Org_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set bin.
		@param bin bin	  */
	public void setbin (String bin)
	{
		set_Value (COLUMNNAME_bin, bin);
	}

	/** Get bin.
		@return bin	  */
	public String getbin () 
	{
		return (String)get_Value(COLUMNNAME_bin);
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

	/** Set codigomoneda.
		@param codigomoneda codigomoneda	  */
	public void setcodigomoneda (String codigomoneda)
	{
		set_Value (COLUMNNAME_codigomoneda, codigomoneda);
	}

	/** Get codigomoneda.
		@return codigomoneda	  */
	public String getcodigomoneda () 
	{
		return (String)get_Value(COLUMNNAME_codigomoneda);
	}

	/** Set codigoplanpagos.
		@param codigoplanpagos codigoplanpagos	  */
	public void setcodigoplanpagos (int codigoplanpagos)
	{
		set_Value (COLUMNNAME_codigoplanpagos, Integer.valueOf(codigoplanpagos));
	}

	/** Get codigoplanpagos.
		@return codigoplanpagos	  */
	public int getcodigoplanpagos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigoplanpagos);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigotipopago.
		@param codigotipopago codigotipopago	  */
	public void setcodigotipopago (int codigotipopago)
	{
		set_Value (COLUMNNAME_codigotipopago, Integer.valueOf(codigotipopago));
	}

	/** Get codigotipopago.
		@return codigotipopago	  */
	public int getcodigotipopago () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigotipopago);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set cotizacioncompra.
		@param cotizacioncompra cotizacioncompra	  */
	public void setcotizacioncompra (BigDecimal cotizacioncompra)
	{
		set_Value (COLUMNNAME_cotizacioncompra, cotizacioncompra);
	}

	/** Get cotizacioncompra.
		@return cotizacioncompra	  */
	public BigDecimal getcotizacioncompra () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cotizacioncompra);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cotizacionventa.
		@param cotizacionventa cotizacionventa	  */
	public void setcotizacionventa (BigDecimal cotizacionventa)
	{
		set_Value (COLUMNNAME_cotizacionventa, cotizacionventa);
	}

	/** Get cotizacionventa.
		@return cotizacionventa	  */
	public BigDecimal getcotizacionventa () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cotizacionventa);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set descripcionformapago.
		@param descripcionformapago descripcionformapago	  */
	public void setdescripcionformapago (String descripcionformapago)
	{
		set_Value (COLUMNNAME_descripcionformapago, descripcionformapago);
	}

	/** Get descripcionformapago.
		@return descripcionformapago	  */
	public String getdescripcionformapago () 
	{
		return (String)get_Value(COLUMNNAME_descripcionformapago);
	}

	/** Set descripcionplanpagos.
		@param descripcionplanpagos descripcionplanpagos	  */
	public void setdescripcionplanpagos (String descripcionplanpagos)
	{
		set_Value (COLUMNNAME_descripcionplanpagos, descripcionplanpagos);
	}

	/** Get descripcionplanpagos.
		@return descripcionplanpagos	  */
	public String getdescripcionplanpagos () 
	{
		return (String)get_Value(COLUMNNAME_descripcionplanpagos);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set documentocliente.
		@param documentocliente documentocliente	  */
	public void setdocumentocliente (String documentocliente)
	{
		set_Value (COLUMNNAME_documentocliente, documentocliente);
	}

	/** Get documentocliente.
		@return documentocliente	  */
	public String getdocumentocliente () 
	{
		return (String)get_Value(COLUMNNAME_documentocliente);
	}

	/** Set fechavencimiento.
		@param fechavencimiento fechavencimiento	  */
	public void setfechavencimiento (Timestamp fechavencimiento)
	{
		set_Value (COLUMNNAME_fechavencimiento, fechavencimiento);
	}

	/** Get fechavencimiento.
		@return fechavencimiento	  */
	public Timestamp getfechavencimiento () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechavencimiento);
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

	/** Set nsuhostautorizador.
		@param nsuhostautorizador nsuhostautorizador	  */
	public void setnsuhostautorizador (String nsuhostautorizador)
	{
		set_Value (COLUMNNAME_nsuhostautorizador, nsuhostautorizador);
	}

	/** Get nsuhostautorizador.
		@return nsuhostautorizador	  */
	public String getnsuhostautorizador () 
	{
		return (String)get_Value(COLUMNNAME_nsuhostautorizador);
	}

	/** Set nsusitef.
		@param nsusitef nsusitef	  */
	public void setnsusitef (String nsusitef)
	{
		set_Value (COLUMNNAME_nsusitef, nsusitef);
	}

	/** Get nsusitef.
		@return nsusitef	  */
	public String getnsusitef () 
	{
		return (String)get_Value(COLUMNNAME_nsusitef);
	}

	/** Set numeroautorizacion.
		@param numeroautorizacion numeroautorizacion	  */
	public void setnumeroautorizacion (BigDecimal numeroautorizacion)
	{
		set_Value (COLUMNNAME_numeroautorizacion, numeroautorizacion);
	}

	/** Get numeroautorizacion.
		@return numeroautorizacion	  */
	public BigDecimal getnumeroautorizacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_numeroautorizacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set numerocuotaspago.
		@param numerocuotaspago numerocuotaspago	  */
	public void setnumerocuotaspago (int numerocuotaspago)
	{
		set_Value (COLUMNNAME_numerocuotaspago, Integer.valueOf(numerocuotaspago));
	}

	/** Get numerocuotaspago.
		@return numerocuotaspago	  */
	public int getnumerocuotaspago () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_numerocuotaspago);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set numerodocumentopago.
		@param numerodocumentopago numerodocumentopago	  */
	public void setnumerodocumentopago (String numerodocumentopago)
	{
		set_Value (COLUMNNAME_numerodocumentopago, numerodocumentopago);
	}

	/** Get numerodocumentopago.
		@return numerodocumentopago	  */
	public String getnumerodocumentopago () 
	{
		return (String)get_Value(COLUMNNAME_numerodocumentopago);
	}

	/** Set numerotarjeta.
		@param numerotarjeta numerotarjeta	  */
	public void setnumerotarjeta (String numerotarjeta)
	{
		set_Value (COLUMNNAME_numerotarjeta, numerotarjeta);
	}

	/** Get numerotarjeta.
		@return numerotarjeta	  */
	public String getnumerotarjeta () 
	{
		return (String)get_Value(COLUMNNAME_numerotarjeta);
	}

	/** Set productositef.
		@param productositef productositef	  */
	public void setproductositef (String productositef)
	{
		set_Value (COLUMNNAME_productositef, productositef);
	}

	/** Get productositef.
		@return productositef	  */
	public String getproductositef () 
	{
		return (String)get_Value(COLUMNNAME_productositef);
	}

	public I_UY_RT_Movement getUY_RT_Movement() throws RuntimeException
    {
		return (I_UY_RT_Movement)MTable.get(getCtx(), I_UY_RT_Movement.Table_Name)
			.getPO(getUY_RT_Movement_ID(), get_TrxName());	}

	/** Set UY_RT_Movement.
		@param UY_RT_Movement_ID UY_RT_Movement	  */
	public void setUY_RT_Movement_ID (int UY_RT_Movement_ID)
	{
		if (UY_RT_Movement_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_Movement_ID, Integer.valueOf(UY_RT_Movement_ID));
	}

	/** Get UY_RT_Movement.
		@return UY_RT_Movement	  */
	public int getUY_RT_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Movement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RT_PaymentMovement.
		@param UY_RT_PaymentMovement_ID UY_RT_PaymentMovement	  */
	public void setUY_RT_PaymentMovement_ID (int UY_RT_PaymentMovement_ID)
	{
		if (UY_RT_PaymentMovement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_PaymentMovement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_PaymentMovement_ID, Integer.valueOf(UY_RT_PaymentMovement_ID));
	}

	/** Get UY_RT_PaymentMovement.
		@return UY_RT_PaymentMovement	  */
	public int getUY_RT_PaymentMovement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_PaymentMovement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}