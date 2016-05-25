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

/** Generated Model for Cov_Ticket_LinePagoServ
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_Cov_Ticket_LinePagoServ extends PO implements I_Cov_Ticket_LinePagoServ, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150429L;

    /** Standard Constructor */
    public X_Cov_Ticket_LinePagoServ (Properties ctx, int Cov_Ticket_LinePagoServ_ID, String trxName)
    {
      super (ctx, Cov_Ticket_LinePagoServ_ID, trxName);
      /** if (Cov_Ticket_LinePagoServ_ID == 0)
        {
			setCov_Ticket_LinePagoServ_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Cov_Ticket_LinePagoServ (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_Cov_Ticket_LinePagoServ[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_Cov_CodigoServicios getCov_CodigoServicios() throws RuntimeException
    {
		return (I_Cov_CodigoServicios)MTable.get(getCtx(), I_Cov_CodigoServicios.Table_Name)
			.getPO(getCov_CodigoServicios_ID(), get_TrxName());	}

	/** Set Cov_CodigoServicios.
		@param Cov_CodigoServicios_ID Cov_CodigoServicios	  */
	public void setCov_CodigoServicios_ID (int Cov_CodigoServicios_ID)
	{
		if (Cov_CodigoServicios_ID < 1) 
			set_Value (COLUMNNAME_Cov_CodigoServicios_ID, null);
		else 
			set_Value (COLUMNNAME_Cov_CodigoServicios_ID, Integer.valueOf(Cov_CodigoServicios_ID));
	}

	/** Get Cov_CodigoServicios.
		@return Cov_CodigoServicios	  */
	public int getCov_CodigoServicios_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_CodigoServicios_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Cov_Ticket_LinePagoServ.
		@param Cov_Ticket_LinePagoServ_ID Cov_Ticket_LinePagoServ	  */
	public void setCov_Ticket_LinePagoServ_ID (int Cov_Ticket_LinePagoServ_ID)
	{
		if (Cov_Ticket_LinePagoServ_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Cov_Ticket_LinePagoServ_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Cov_Ticket_LinePagoServ_ID, Integer.valueOf(Cov_Ticket_LinePagoServ_ID));
	}

	/** Get Cov_Ticket_LinePagoServ.
		@return Cov_Ticket_LinePagoServ	  */
	public int getCov_Ticket_LinePagoServ_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_Ticket_LinePagoServ_ID);
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

	/** Set lineacancelada.
		@param lineacancelada lineacancelada	  */
	public void setlineacancelada (String lineacancelada)
	{
		set_Value (COLUMNNAME_lineacancelada, lineacancelada);
	}

	/** Get lineacancelada.
		@return lineacancelada	  */
	public String getlineacancelada () 
	{
		return (String)get_Value(COLUMNNAME_lineacancelada);
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

	/** Set referencia.
		@param referencia referencia	  */
	public void setreferencia (String referencia)
	{
		set_Value (COLUMNNAME_referencia, referencia);
	}

	/** Get referencia.
		@return referencia	  */
	public String getreferencia () 
	{
		return (String)get_Value(COLUMNNAME_referencia);
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