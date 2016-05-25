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

/** Generated Model for Cov_Ticket_LineCtaCte
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_Cov_Ticket_LineCtaCte extends PO implements I_Cov_Ticket_LineCtaCte, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150427L;

    /** Standard Constructor */
    public X_Cov_Ticket_LineCtaCte (Properties ctx, int Cov_Ticket_LineCtaCte_ID, String trxName)
    {
      super (ctx, Cov_Ticket_LineCtaCte_ID, trxName);
      /** if (Cov_Ticket_LineCtaCte_ID == 0)
        {
			setCov_Ticket_LineCtaCte_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Cov_Ticket_LineCtaCte (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Cov_Ticket_LineCtaCte[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set autorizasupervisora.
		@param autorizasupervisora autorizasupervisora	  */
	public void setautorizasupervisora (String autorizasupervisora)
	{
		set_Value (COLUMNNAME_autorizasupervisora, autorizasupervisora);
	}

	/** Get autorizasupervisora.
		@return autorizasupervisora	  */
	public String getautorizasupervisora () 
	{
		return (String)get_Value(COLUMNNAME_autorizasupervisora);
	}

	/** Set cambio.
		@param cambio cambio	  */
	public void setcambio (BigDecimal cambio)
	{
		set_Value (COLUMNNAME_cambio, cambio);
	}

	/** Get cambio.
		@return cambio	  */
	public BigDecimal getcambio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cambio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set codigosupervisora.
		@param codigosupervisora codigosupervisora	  */
	public void setcodigosupervisora (String codigosupervisora)
	{
		set_Value (COLUMNNAME_codigosupervisora, codigosupervisora);
	}

	/** Get codigosupervisora.
		@return codigosupervisora	  */
	public String getcodigosupervisora () 
	{
		return (String)get_Value(COLUMNNAME_codigosupervisora);
	}

	public I_Cov_CodigoMedioPago getCov_CodigoMedioPago() throws RuntimeException
    {
		return (I_Cov_CodigoMedioPago)MTable.get(getCtx(), I_Cov_CodigoMedioPago.Table_Name)
			.getPO(getCov_CodigoMedioPago_ID(), get_TrxName());	}

	/** Set Cov_CodigoMedioPago.
		@param Cov_CodigoMedioPago_ID Cov_CodigoMedioPago	  */
	public void setCov_CodigoMedioPago_ID (int Cov_CodigoMedioPago_ID)
	{
		if (Cov_CodigoMedioPago_ID < 1) 
			set_Value (COLUMNNAME_Cov_CodigoMedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Cov_CodigoMedioPago_ID, Integer.valueOf(Cov_CodigoMedioPago_ID));
	}

	/** Get Cov_CodigoMedioPago.
		@return Cov_CodigoMedioPago	  */
	public int getCov_CodigoMedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_CodigoMedioPago_ID);
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

	/** Set Cov_Ticket_LineCtaCte.
		@param Cov_Ticket_LineCtaCte_ID Cov_Ticket_LineCtaCte	  */
	public void setCov_Ticket_LineCtaCte_ID (int Cov_Ticket_LineCtaCte_ID)
	{
		if (Cov_Ticket_LineCtaCte_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Cov_Ticket_LineCtaCte_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Cov_Ticket_LineCtaCte_ID, Integer.valueOf(Cov_Ticket_LineCtaCte_ID));
	}

	/** Get Cov_Ticket_LineCtaCte.
		@return Cov_Ticket_LineCtaCte	  */
	public int getCov_Ticket_LineCtaCte_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_Ticket_LineCtaCte_ID);
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

	/** Set lineaultimopago.
		@param lineaultimopago lineaultimopago	  */
	public void setlineaultimopago (String lineaultimopago)
	{
		set_Value (COLUMNNAME_lineaultimopago, lineaultimopago);
	}

	/** Get lineaultimopago.
		@return lineaultimopago	  */
	public String getlineaultimopago () 
	{
		return (String)get_Value(COLUMNNAME_lineaultimopago);
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

	/** Set tipooperacion.
		@param tipooperacion tipooperacion	  */
	public void settipooperacion (String tipooperacion)
	{
		set_Value (COLUMNNAME_tipooperacion, tipooperacion);
	}

	/** Get tipooperacion.
		@return tipooperacion	  */
	public String gettipooperacion () 
	{
		return (String)get_Value(COLUMNNAME_tipooperacion);
	}

	/** Set totalentregado.
		@param totalentregado totalentregado	  */
	public void settotalentregado (BigDecimal totalentregado)
	{
		set_Value (COLUMNNAME_totalentregado, totalentregado);
	}

	/** Get totalentregado.
		@return totalentregado	  */
	public BigDecimal gettotalentregado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalentregado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalentregadomonedareferencia.
		@param totalentregadomonedareferencia totalentregadomonedareferencia	  */
	public void settotalentregadomonedareferencia (BigDecimal totalentregadomonedareferencia)
	{
		set_Value (COLUMNNAME_totalentregadomonedareferencia, totalentregadomonedareferencia);
	}

	/** Get totalentregadomonedareferencia.
		@return totalentregadomonedareferencia	  */
	public BigDecimal gettotalentregadomonedareferencia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalentregadomonedareferencia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalmediopagomonedareferencia.
		@param totalmediopagomonedareferencia totalmediopagomonedareferencia	  */
	public void settotalmediopagomonedareferencia (BigDecimal totalmediopagomonedareferencia)
	{
		set_Value (COLUMNNAME_totalmediopagomonedareferencia, totalmediopagomonedareferencia);
	}

	/** Get totalmediopagomonedareferencia.
		@return totalmediopagomonedareferencia	  */
	public BigDecimal gettotalmediopagomonedareferencia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalmediopagomonedareferencia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalmeidopagomoneda.
		@param totalmeidopagomoneda totalmeidopagomoneda	  */
	public void settotalmeidopagomoneda (BigDecimal totalmeidopagomoneda)
	{
		set_Value (COLUMNNAME_totalmeidopagomoneda, totalmeidopagomoneda);
	}

	/** Get totalmeidopagomoneda.
		@return totalmeidopagomoneda	  */
	public BigDecimal gettotalmeidopagomoneda () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalmeidopagomoneda);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}