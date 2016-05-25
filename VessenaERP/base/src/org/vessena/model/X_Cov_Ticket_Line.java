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

/** Generated Model for Cov_Ticket_Line
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_Cov_Ticket_Line extends PO implements I_Cov_Ticket_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150428L;

    /** Standard Constructor */
    public X_Cov_Ticket_Line (Properties ctx, int Cov_Ticket_Line_ID, String trxName)
    {
      super (ctx, Cov_Ticket_Line_ID, trxName);
      /** if (Cov_Ticket_Line_ID == 0)
        {
			setCov_Ticket_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Cov_Ticket_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Cov_Ticket_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set cantdescmanuales.
		@param cantdescmanuales cantdescmanuales	  */
	public void setcantdescmanuales (int cantdescmanuales)
	{
		set_Value (COLUMNNAME_cantdescmanuales, Integer.valueOf(cantdescmanuales));
	}

	/** Get cantdescmanuales.
		@return cantdescmanuales	  */
	public int getcantdescmanuales () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cantdescmanuales);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set cantidad.
		@param cantidad cantidad	  */
	public void setcantidad (int cantidad)
	{
		set_Value (COLUMNNAME_cantidad, Integer.valueOf(cantidad));
	}

	/** Get cantidad.
		@return cantidad	  */
	public int getcantidad () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cantidad);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set cantidadartobsequio.
		@param cantidadartobsequio cantidadartobsequio	  */
	public void setcantidadartobsequio (int cantidadartobsequio)
	{
		set_Value (COLUMNNAME_cantidadartobsequio, Integer.valueOf(cantidadartobsequio));
	}

	/** Get cantidadartobsequio.
		@return cantidadartobsequio	  */
	public int getcantidadartobsequio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cantidadartobsequio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigoarticulo.
		@param codigoarticulo codigoarticulo	  */
	public void setcodigoarticulo (String codigoarticulo)
	{
		set_Value (COLUMNNAME_codigoarticulo, codigoarticulo);
	}

	/** Get codigoarticulo.
		@return codigoarticulo	  */
	public String getcodigoarticulo () 
	{
		return (String)get_Value(COLUMNNAME_codigoarticulo);
	}

	/** Set codigoarticulooriginal.
		@param codigoarticulooriginal codigoarticulooriginal	  */
	public void setcodigoarticulooriginal (String codigoarticulooriginal)
	{
		set_Value (COLUMNNAME_codigoarticulooriginal, codigoarticulooriginal);
	}

	/** Get codigoarticulooriginal.
		@return codigoarticulooriginal	  */
	public String getcodigoarticulooriginal () 
	{
		return (String)get_Value(COLUMNNAME_codigoarticulooriginal);
	}

	/** Set codigoiva.
		@param codigoiva codigoiva	  */
	public void setcodigoiva (String codigoiva)
	{
		set_Value (COLUMNNAME_codigoiva, codigoiva);
	}

	/** Get codigoiva.
		@return codigoiva	  */
	public String getcodigoiva () 
	{
		return (String)get_Value(COLUMNNAME_codigoiva);
	}

	/** Set codigomediodepago.
		@param codigomediodepago codigomediodepago	  */
	public void setcodigomediodepago (String codigomediodepago)
	{
		set_Value (COLUMNNAME_codigomediodepago, codigomediodepago);
	}

	/** Get codigomediodepago.
		@return codigomediodepago	  */
	public String getcodigomediodepago () 
	{
		return (String)get_Value(COLUMNNAME_codigomediodepago);
	}

	/** Set codigovendedor.
		@param codigovendedor codigovendedor	  */
	public void setcodigovendedor (String codigovendedor)
	{
		set_Value (COLUMNNAME_codigovendedor, codigovendedor);
	}

	/** Get codigovendedor.
		@return codigovendedor	  */
	public String getcodigovendedor () 
	{
		return (String)get_Value(COLUMNNAME_codigovendedor);
	}

	/** Set color.
		@param color color	  */
	public void setcolor (String color)
	{
		set_Value (COLUMNNAME_color, color);
	}

	/** Get color.
		@return color	  */
	public String getcolor () 
	{
		return (String)get_Value(COLUMNNAME_color);
	}

	public I_Cov_Ticket_Header getCov_Ticket_Header() throws RuntimeException
    {
		return (I_Cov_Ticket_Header)MTable.get(getCtx(), I_Cov_Ticket_Header.Table_Name)
			.getPO(getCov_Ticket_Header_ID(), get_TrxName());	}

	/** Set Cov_Ticket_Header.
		@param Cov_Ticket_Header_ID Cov_Ticket_Header	  */
	public void setCov_Ticket_Header_ID (int Cov_Ticket_Header_ID)
	{
		if (Cov_Ticket_Header_ID < 1) 
			set_Value (COLUMNNAME_Cov_Ticket_Header_ID, null);
		else 
			set_Value (COLUMNNAME_Cov_Ticket_Header_ID, Integer.valueOf(Cov_Ticket_Header_ID));
	}

	/** Get Cov_Ticket_Header.
		@return Cov_Ticket_Header	  */
	public int getCov_Ticket_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_Ticket_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cov_Ticket_Line.
		@param Cov_Ticket_Line_ID Cov_Ticket_Line	  */
	public void setCov_Ticket_Line_ID (int Cov_Ticket_Line_ID)
	{
		if (Cov_Ticket_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Cov_Ticket_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Cov_Ticket_Line_ID, Integer.valueOf(Cov_Ticket_Line_ID));
	}

	/** Get Cov_Ticket_Line.
		@return Cov_Ticket_Line	  */
	public int getCov_Ticket_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_Ticket_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Cov_TicketType getCov_TicketType() throws RuntimeException
    {
		return (I_Cov_TicketType)MTable.get(getCtx(), I_Cov_TicketType.Table_Name)
			.getPO(getCov_TicketType_ID(), get_TrxName());	}

	/** Set Cov_TicketType.
		@param Cov_TicketType_ID Cov_TicketType	  */
	public void setCov_TicketType_ID (int Cov_TicketType_ID)
	{
		if (Cov_TicketType_ID < 1) 
			set_Value (COLUMNNAME_Cov_TicketType_ID, null);
		else 
			set_Value (COLUMNNAME_Cov_TicketType_ID, Integer.valueOf(Cov_TicketType_ID));
	}

	/** Get Cov_TicketType.
		@return Cov_TicketType	  */
	public int getCov_TicketType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_TicketType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set iva.
		@param iva iva	  */
	public void setiva (BigDecimal iva)
	{
		set_Value (COLUMNNAME_iva, iva);
	}

	/** Get iva.
		@return iva	  */
	public BigDecimal getiva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_iva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ivadescuento.
		@param ivadescuento ivadescuento	  */
	public void setivadescuento (BigDecimal ivadescuento)
	{
		set_Value (COLUMNNAME_ivadescuento, ivadescuento);
	}

	/** Get ivadescuento.
		@return ivadescuento	  */
	public BigDecimal getivadescuento () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivadescuento);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ivadescuentocombo.
		@param ivadescuentocombo ivadescuentocombo	  */
	public void setivadescuentocombo (BigDecimal ivadescuentocombo)
	{
		set_Value (COLUMNNAME_ivadescuentocombo, ivadescuentocombo);
	}

	/** Get ivadescuentocombo.
		@return ivadescuentocombo	  */
	public BigDecimal getivadescuentocombo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivadescuentocombo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ivadescuentomarca.
		@param ivadescuentomarca ivadescuentomarca	  */
	public void setivadescuentomarca (BigDecimal ivadescuentomarca)
	{
		set_Value (COLUMNNAME_ivadescuentomarca, ivadescuentomarca);
	}

	/** Get ivadescuentomarca.
		@return ivadescuentomarca	  */
	public BigDecimal getivadescuentomarca () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivadescuentomarca);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ivadescuentototal.
		@param ivadescuentototal ivadescuentototal	  */
	public void setivadescuentototal (BigDecimal ivadescuentototal)
	{
		set_Value (COLUMNNAME_ivadescuentototal, ivadescuentototal);
	}

	/** Get ivadescuentototal.
		@return ivadescuentototal	  */
	public BigDecimal getivadescuentototal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ivadescuentototal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set lineacancelada.
		@param lineacancelada lineacancelada	  */
	public void setlineacancelada (int lineacancelada)
	{
		set_Value (COLUMNNAME_lineacancelada, Integer.valueOf(lineacancelada));
	}

	/** Get lineacancelada.
		@return lineacancelada	  */
	public int getlineacancelada () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_lineacancelada);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set marca.
		@param marca marca	  */
	public void setmarca (String marca)
	{
		set_Value (COLUMNNAME_marca, marca);
	}

	/** Get marca.
		@return marca	  */
	public String getmarca () 
	{
		return (String)get_Value(COLUMNNAME_marca);
	}

	/** Set modelo.
		@param modelo modelo	  */
	public void setmodelo (String modelo)
	{
		set_Value (COLUMNNAME_modelo, modelo);
	}

	/** Get modelo.
		@return modelo	  */
	public String getmodelo () 
	{
		return (String)get_Value(COLUMNNAME_modelo);
	}

	/** Set modoingreso.
		@param modoingreso modoingreso	  */
	public void setmodoingreso (String modoingreso)
	{
		set_Value (COLUMNNAME_modoingreso, modoingreso);
	}

	/** Get modoingreso.
		@return modoingreso	  */
	public String getmodoingreso () 
	{
		return (String)get_Value(COLUMNNAME_modoingreso);
	}

	/** Set montorealdescfidel.
		@param montorealdescfidel montorealdescfidel	  */
	public void setmontorealdescfidel (BigDecimal montorealdescfidel)
	{
		set_Value (COLUMNNAME_montorealdescfidel, montorealdescfidel);
	}

	/** Get montorealdescfidel.
		@return montorealdescfidel	  */
	public BigDecimal getmontorealdescfidel () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montorealdescfidel);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set nrolineaconvenio.
		@param nrolineaconvenio nrolineaconvenio	  */
	public void setnrolineaconvenio (int nrolineaconvenio)
	{
		set_Value (COLUMNNAME_nrolineaconvenio, Integer.valueOf(nrolineaconvenio));
	}

	/** Get nrolineaconvenio.
		@return nrolineaconvenio	  */
	public int getnrolineaconvenio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_nrolineaconvenio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set numerodelinea.
		@param numerodelinea numerodelinea	  */
	public void setnumerodelinea (String numerodelinea)
	{
		set_Value (COLUMNNAME_numerodelinea, numerodelinea);
	}

	/** Get numerodelinea.
		@return numerodelinea	  */
	public String getnumerodelinea () 
	{
		return (String)get_Value(COLUMNNAME_numerodelinea);
	}

	/** Set preciodescuento.
		@param preciodescuento preciodescuento	  */
	public void setpreciodescuento (BigDecimal preciodescuento)
	{
		set_Value (COLUMNNAME_preciodescuento, preciodescuento);
	}

	/** Get preciodescuento.
		@return preciodescuento	  */
	public BigDecimal getpreciodescuento () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciodescuento);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set preciodescuentocombo.
		@param preciodescuentocombo preciodescuentocombo	  */
	public void setpreciodescuentocombo (BigDecimal preciodescuentocombo)
	{
		set_Value (COLUMNNAME_preciodescuentocombo, preciodescuentocombo);
	}

	/** Get preciodescuentocombo.
		@return preciodescuentocombo	  */
	public BigDecimal getpreciodescuentocombo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciodescuentocombo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set preciodescuentomarca.
		@param preciodescuentomarca preciodescuentomarca	  */
	public void setpreciodescuentomarca (BigDecimal preciodescuentomarca)
	{
		set_Value (COLUMNNAME_preciodescuentomarca, preciodescuentomarca);
	}

	/** Get preciodescuentomarca.
		@return preciodescuentomarca	  */
	public BigDecimal getpreciodescuentomarca () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciodescuentomarca);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set preciodescuentototal.
		@param preciodescuentototal preciodescuentototal	  */
	public void setpreciodescuentototal (BigDecimal preciodescuentototal)
	{
		set_Value (COLUMNNAME_preciodescuentototal, preciodescuentototal);
	}

	/** Get preciodescuentototal.
		@return preciodescuentototal	  */
	public BigDecimal getpreciodescuentototal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciodescuentototal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set preciounitario.
		@param preciounitario preciounitario	  */
	public void setpreciounitario (BigDecimal preciounitario)
	{
		set_Value (COLUMNNAME_preciounitario, preciounitario);
	}

	/** Get preciounitario.
		@return preciounitario	  */
	public BigDecimal getpreciounitario () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_preciounitario);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set puntosoferta.
		@param puntosoferta puntosoferta	  */
	public void setpuntosoferta (BigDecimal puntosoferta)
	{
		set_Value (COLUMNNAME_puntosoferta, puntosoferta);
	}

	/** Get puntosoferta.
		@return puntosoferta	  */
	public BigDecimal getpuntosoferta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_puntosoferta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set siaplicadescfidel.
		@param siaplicadescfidel siaplicadescfidel	  */
	public void setsiaplicadescfidel (String siaplicadescfidel)
	{
		set_Value (COLUMNNAME_siaplicadescfidel, siaplicadescfidel);
	}

	/** Get siaplicadescfidel.
		@return siaplicadescfidel	  */
	public String getsiaplicadescfidel () 
	{
		return (String)get_Value(COLUMNNAME_siaplicadescfidel);
	}

	/** Set siesconvenio.
		@param siesconvenio siesconvenio	  */
	public void setsiesconvenio (String siesconvenio)
	{
		set_Value (COLUMNNAME_siesconvenio, siesconvenio);
	}

	/** Get siesconvenio.
		@return siesconvenio	  */
	public String getsiesconvenio () 
	{
		return (String)get_Value(COLUMNNAME_siesconvenio);
	}

	/** Set siesobsequio.
		@param siesobsequio siesobsequio	  */
	public void setsiesobsequio (String siesobsequio)
	{
		set_Value (COLUMNNAME_siesobsequio, siesobsequio);
	}

	/** Get siesobsequio.
		@return siesobsequio	  */
	public String getsiesobsequio () 
	{
		return (String)get_Value(COLUMNNAME_siesobsequio);
	}

	/** Set siestandem.
		@param siestandem siestandem	  */
	public void setsiestandem (String siestandem)
	{
		set_Value (COLUMNNAME_siestandem, siestandem);
	}

	/** Get siestandem.
		@return siestandem	  */
	public String getsiestandem () 
	{
		return (String)get_Value(COLUMNNAME_siestandem);
	}

	/** Set talle.
		@param talle talle	  */
	public void settalle (String talle)
	{
		set_Value (COLUMNNAME_talle, talle);
	}

	/** Get talle.
		@return talle	  */
	public String gettalle () 
	{
		return (String)get_Value(COLUMNNAME_talle);
	}

	/** Set timestamplinea.
		@param timestamplinea timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea)
	{
		set_Value (COLUMNNAME_timestamplinea, timestamplinea);
	}

	/** Get timestamplinea.
		@return timestamplinea	  */
	public Timestamp gettimestamplinea () 
	{
		return (Timestamp)get_Value(COLUMNNAME_timestamplinea);
	}
}