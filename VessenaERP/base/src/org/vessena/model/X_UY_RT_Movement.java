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

/** Generated Model for UY_RT_Movement
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Movement extends PO implements I_UY_RT_Movement, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160309L;

    /** Standard Constructor */
    public X_UY_RT_Movement (Properties ctx, int UY_RT_Movement_ID, String trxName)
    {
      super (ctx, UY_RT_Movement_ID, trxName);
      /** if (UY_RT_Movement_ID == 0)
        {
			setUY_RT_Movement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Movement (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Movement[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

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

	/** Set cantidaditems.
		@param cantidaditems cantidaditems	  */
	public void setcantidaditems (BigDecimal cantidaditems)
	{
		set_Value (COLUMNNAME_cantidaditems, cantidaditems);
	}

	/** Get cantidaditems.
		@return cantidaditems	  */
	public BigDecimal getcantidaditems () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cantidaditems);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set cnpjcpfcliente.
		@param cnpjcpfcliente cnpjcpfcliente	  */
	public void setcnpjcpfcliente (String cnpjcpfcliente)
	{
		set_Value (COLUMNNAME_cnpjcpfcliente, cnpjcpfcliente);
	}

	/** Get cnpjcpfcliente.
		@return cnpjcpfcliente	  */
	public String getcnpjcpfcliente () 
	{
		return (String)get_Value(COLUMNNAME_cnpjcpfcliente);
	}

	/** Set cnpjlocal.
		@param cnpjlocal cnpjlocal	  */
	public void setcnpjlocal (int cnpjlocal)
	{
		set_Value (COLUMNNAME_cnpjlocal, Integer.valueOf(cnpjlocal));
	}

	/** Get cnpjlocal.
		@return cnpjlocal	  */
	public int getcnpjlocal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cnpjlocal);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigocaja.
		@param codigocaja codigocaja	  */
	public void setcodigocaja (int codigocaja)
	{
		set_Value (COLUMNNAME_codigocaja, Integer.valueOf(codigocaja));
	}

	/** Get codigocaja.
		@return codigocaja	  */
	public int getcodigocaja () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigocaja);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigoempresa.
		@param codigoempresa codigoempresa	  */
	public void setcodigoempresa (int codigoempresa)
	{
		set_Value (COLUMNNAME_codigoempresa, Integer.valueOf(codigoempresa));
	}

	/** Get codigoempresa.
		@return codigoempresa	  */
	public int getcodigoempresa () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigoempresa);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigolocal.
		@param codigolocal codigolocal	  */
	public void setcodigolocal (int codigolocal)
	{
		set_Value (COLUMNNAME_codigolocal, Integer.valueOf(codigolocal));
	}

	/** Get codigolocal.
		@return codigolocal	  */
	public int getcodigolocal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigolocal);
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

	/** Set cuponanulada.
		@param cuponanulada cuponanulada	  */
	public void setcuponanulada (boolean cuponanulada)
	{
		set_Value (COLUMNNAME_cuponanulada, Boolean.valueOf(cuponanulada));
	}

	/** Get cuponanulada.
		@return cuponanulada	  */
	public boolean iscuponanulada () 
	{
		Object oo = get_Value(COLUMNNAME_cuponanulada);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set cuponcancelado.
		@param cuponcancelado cuponcancelado	  */
	public void setcuponcancelado (boolean cuponcancelado)
	{
		set_Value (COLUMNNAME_cuponcancelado, Boolean.valueOf(cuponcancelado));
	}

	/** Get cuponcancelado.
		@return cuponcancelado	  */
	public boolean iscuponcancelado () 
	{
		Object oo = get_Value(COLUMNNAME_cuponcancelado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set descuentototal.
		@param descuentototal descuentototal	  */
	public void setdescuentototal (BigDecimal descuentototal)
	{
		set_Value (COLUMNNAME_descuentototal, descuentototal);
	}

	/** Get descuentototal.
		@return descuentototal	  */
	public BigDecimal getdescuentototal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_descuentototal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set numerocuponfiscal.
		@param numerocuponfiscal numerocuponfiscal	  */
	public void setnumerocuponfiscal (String numerocuponfiscal)
	{
		set_Value (COLUMNNAME_numerocuponfiscal, numerocuponfiscal);
	}

	/** Get numerocuponfiscal.
		@return numerocuponfiscal	  */
	public String getnumerocuponfiscal () 
	{
		return (String)get_Value(COLUMNNAME_numerocuponfiscal);
	}

	/** Set numerooperacionfiscal.
		@param numerooperacionfiscal numerooperacionfiscal	  */
	public void setnumerooperacionfiscal (String numerooperacionfiscal)
	{
		set_Value (COLUMNNAME_numerooperacionfiscal, numerooperacionfiscal);
	}

	/** Get numerooperacionfiscal.
		@return numerooperacionfiscal	  */
	public String getnumerooperacionfiscal () 
	{
		return (String)get_Value(COLUMNNAME_numerooperacionfiscal);
	}

	/** Set numeroserieecf.
		@param numeroserieecf numeroserieecf	  */
	public void setnumeroserieecf (String numeroserieecf)
	{
		set_Value (COLUMNNAME_numeroserieecf, numeroserieecf);
	}

	/** Get numeroserieecf.
		@return numeroserieecf	  */
	public String getnumeroserieecf () 
	{
		return (String)get_Value(COLUMNNAME_numeroserieecf);
	}

	/** Set redondeo.
		@param redondeo redondeo	  */
	public void setredondeo (BigDecimal redondeo)
	{
		set_Value (COLUMNNAME_redondeo, redondeo);
	}

	/** Get redondeo.
		@return redondeo	  */
	public BigDecimal getredondeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_redondeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set total.
		@param total total	  */
	public void settotal (BigDecimal total)
	{
		set_Value (COLUMNNAME_total, total);
	}

	/** Get total.
		@return total	  */
	public BigDecimal gettotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_total);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_RT_LoadTicket getUY_RT_LoadTicket() throws RuntimeException
    {
		return (I_UY_RT_LoadTicket)MTable.get(getCtx(), I_UY_RT_LoadTicket.Table_Name)
			.getPO(getUY_RT_LoadTicket_ID(), get_TrxName());	}

	/** Set UY_RT_LoadTicket.
		@param UY_RT_LoadTicket_ID UY_RT_LoadTicket	  */
	public void setUY_RT_LoadTicket_ID (int UY_RT_LoadTicket_ID)
	{
		if (UY_RT_LoadTicket_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_LoadTicket_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_LoadTicket_ID, Integer.valueOf(UY_RT_LoadTicket_ID));
	}

	/** Get UY_RT_LoadTicket.
		@return UY_RT_LoadTicket	  */
	public int getUY_RT_LoadTicket_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_LoadTicket_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RT_Movement.
		@param UY_RT_Movement_ID UY_RT_Movement	  */
	public void setUY_RT_Movement_ID (int UY_RT_Movement_ID)
	{
		if (UY_RT_Movement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Movement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Movement_ID, Integer.valueOf(UY_RT_Movement_ID));
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
}