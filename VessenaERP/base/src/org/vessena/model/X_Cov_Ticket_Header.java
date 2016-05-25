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

/** Generated Model for Cov_Ticket_Header
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_Cov_Ticket_Header extends PO implements I_Cov_Ticket_Header, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150427L;

    /** Standard Constructor */
    public X_Cov_Ticket_Header (Properties ctx, int Cov_Ticket_Header_ID, String trxName)
    {
      super (ctx, Cov_Ticket_Header_ID, trxName);
      /** if (Cov_Ticket_Header_ID == 0)
        {
			setCov_Ticket_Header_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Cov_Ticket_Header (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Cov_Ticket_Header[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set cantidadarticulos.
		@param cantidadarticulos cantidadarticulos	  */
	public void setcantidadarticulos (int cantidadarticulos)
	{
		set_Value (COLUMNNAME_cantidadarticulos, Integer.valueOf(cantidadarticulos));
	}

	/** Get cantidadarticulos.
		@return cantidadarticulos	  */
	public int getcantidadarticulos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cantidadarticulos);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set cantidadlineas.
		@param cantidadlineas cantidadlineas	  */
	public void setcantidadlineas (int cantidadlineas)
	{
		set_Value (COLUMNNAME_cantidadlineas, Integer.valueOf(cantidadlineas));
	}

	/** Get cantidadlineas.
		@return cantidadlineas	  */
	public int getcantidadlineas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cantidadlineas);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigocaja.
		@param codigocaja codigocaja	  */
	public void setcodigocaja (String codigocaja)
	{
		set_Value (COLUMNNAME_codigocaja, codigocaja);
	}

	/** Get codigocaja.
		@return codigocaja	  */
	public String getcodigocaja () 
	{
		return (String)get_Value(COLUMNNAME_codigocaja);
	}

	/** Set codigocajadevolucion.
		@param codigocajadevolucion codigocajadevolucion	  */
	public void setcodigocajadevolucion (int codigocajadevolucion)
	{
		set_Value (COLUMNNAME_codigocajadevolucion, Integer.valueOf(codigocajadevolucion));
	}

	/** Get codigocajadevolucion.
		@return codigocajadevolucion	  */
	public int getcodigocajadevolucion () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigocajadevolucion);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigocajera.
		@param codigocajera codigocajera	  */
	public void setcodigocajera (String codigocajera)
	{
		set_Value (COLUMNNAME_codigocajera, codigocajera);
	}

	/** Get codigocajera.
		@return codigocajera	  */
	public String getcodigocajera () 
	{
		return (String)get_Value(COLUMNNAME_codigocajera);
	}

	public I_Cov_LoadTicket getCov_LoadTicket() throws RuntimeException
    {
		return (I_Cov_LoadTicket)MTable.get(getCtx(), I_Cov_LoadTicket.Table_Name)
			.getPO(getCov_LoadTicket_ID(), get_TrxName());	}

	/** Set Cov_LoadTicket.
		@param Cov_LoadTicket_ID Cov_LoadTicket	  */
	public void setCov_LoadTicket_ID (int Cov_LoadTicket_ID)
	{
		if (Cov_LoadTicket_ID < 1) 
			set_Value (COLUMNNAME_Cov_LoadTicket_ID, null);
		else 
			set_Value (COLUMNNAME_Cov_LoadTicket_ID, Integer.valueOf(Cov_LoadTicket_ID));
	}

	/** Get Cov_LoadTicket.
		@return Cov_LoadTicket	  */
	public int getCov_LoadTicket_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_LoadTicket_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cov_Ticket_Header.
		@param Cov_Ticket_Header_ID Cov_Ticket_Header	  */
	public void setCov_Ticket_Header_ID (int Cov_Ticket_Header_ID)
	{
		if (Cov_Ticket_Header_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Cov_Ticket_Header_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Cov_Ticket_Header_ID, Integer.valueOf(Cov_Ticket_Header_ID));
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

	/** Set estadoticket.
		@param estadoticket estadoticket	  */
	public void setestadoticket (String estadoticket)
	{
		set_Value (COLUMNNAME_estadoticket, estadoticket);
	}

	/** Get estadoticket.
		@return estadoticket	  */
	public String getestadoticket () 
	{
		return (String)get_Value(COLUMNNAME_estadoticket);
	}

	/** Set identificadorlinea.
		@param identificadorlinea identificadorlinea	  */
	public void setidentificadorlinea (boolean identificadorlinea)
	{
		set_Value (COLUMNNAME_identificadorlinea, Boolean.valueOf(identificadorlinea));
	}

	/** Get identificadorlinea.
		@return identificadorlinea	  */
	public boolean isidentificadorlinea () 
	{
		Object oo = get_Value(COLUMNNAME_identificadorlinea);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set numerolinefile.
		@param numerolinefile numerolinefile	  */
	public void setnumerolinefile (String numerolinefile)
	{
		set_Value (COLUMNNAME_numerolinefile, numerolinefile);
	}

	/** Get numerolinefile.
		@return numerolinefile	  */
	public String getnumerolinefile () 
	{
		return (String)get_Value(COLUMNNAME_numerolinefile);
	}

	/** Set numeroticket.
		@param numeroticket numeroticket	  */
	public void setnumeroticket (String numeroticket)
	{
		set_Value (COLUMNNAME_numeroticket, numeroticket);
	}

	/** Get numeroticket.
		@return numeroticket	  */
	public String getnumeroticket () 
	{
		return (String)get_Value(COLUMNNAME_numeroticket);
	}

	/** Set numeroticketdevolucion.
		@param numeroticketdevolucion numeroticketdevolucion	  */
	public void setnumeroticketdevolucion (int numeroticketdevolucion)
	{
		set_Value (COLUMNNAME_numeroticketdevolucion, Integer.valueOf(numeroticketdevolucion));
	}

	/** Get numeroticketdevolucion.
		@return numeroticketdevolucion	  */
	public int getnumeroticketdevolucion () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_numeroticketdevolucion);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set timestampticket.
		@param timestampticket timestampticket	  */
	public void settimestampticket (Timestamp timestampticket)
	{
		set_Value (COLUMNNAME_timestampticket, timestampticket);
	}

	/** Get timestampticket.
		@return timestampticket	  */
	public Timestamp gettimestampticket () 
	{
		return (Timestamp)get_Value(COLUMNNAME_timestampticket);
	}

	/** Set tipocliente.
		@param tipocliente tipocliente	  */
	public void settipocliente (String tipocliente)
	{
		set_Value (COLUMNNAME_tipocliente, tipocliente);
	}

	/** Get tipocliente.
		@return tipocliente	  */
	public String gettipocliente () 
	{
		return (String)get_Value(COLUMNNAME_tipocliente);
	}

	/** Set totalapagar.
		@param totalapagar totalapagar	  */
	public void settotalapagar (BigDecimal totalapagar)
	{
		set_Value (COLUMNNAME_totalapagar, totalapagar);
	}

	/** Get totalapagar.
		@return totalapagar	  */
	public BigDecimal gettotalapagar () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalapagar);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}