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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_Audit_Cov_LoadTicket
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Audit_Cov_LoadTicket extends PO implements I_UY_Audit_Cov_LoadTicket, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150417L;

    /** Standard Constructor */
    public X_UY_Audit_Cov_LoadTicket (Properties ctx, int UY_Audit_Cov_LoadTicket_ID, String trxName)
    {
      super (ctx, UY_Audit_Cov_LoadTicket_ID, trxName);
      /** if (UY_Audit_Cov_LoadTicket_ID == 0)
        {
			setamtapgaveta (Env.ZERO);
			setamtcajera (Env.ZERO);
			setamtcanje (Env.ZERO);
			setamtceditodevolucion (Env.ZERO);
			setamtconsulta (Env.ZERO);
			setamtdevolucion (Env.ZERO);
			setamtestadocta (Env.ZERO);
			setamtexentoiva (Env.ZERO);
			setamtfactura (Env.ZERO);
			setamtfondeo (Env.ZERO);
			setamtgift (Env.ZERO);
			setamtingredopersonalretiroprod (Env.ZERO);
			setamtinventario (Env.ZERO);
			setamtnodefinido (Env.ZERO);
			setamtnogenerado (Env.ZERO);
			setamtpagocaja (Env.ZERO);
			setamtpedido (Env.ZERO);
			setamtretiro (Env.ZERO);
			setamtventas (Env.ZERO);
			setamtzprestamoefect (Env.ZERO);
			setCov_LoadTicket_ID (0);
			setName (null);
			setUY_Audit_Cov_LoadTicket_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_Audit_Cov_LoadTicket (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Audit_Cov_LoadTicket[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amtapgaveta.
		@param amtapgaveta amtapgaveta	  */
	public void setamtapgaveta (BigDecimal amtapgaveta)
	{
		set_Value (COLUMNNAME_amtapgaveta, amtapgaveta);
	}

	/** Get amtapgaveta.
		@return amtapgaveta	  */
	public BigDecimal getamtapgaveta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtapgaveta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcabezalcod1.
		@param amtcabezalcod1 amtcabezalcod1	  */
	public void setamtcabezalcod1 (BigDecimal amtcabezalcod1)
	{
		set_Value (COLUMNNAME_amtcabezalcod1, amtcabezalcod1);
	}

	/** Get amtcabezalcod1.
		@return amtcabezalcod1	  */
	public BigDecimal getamtcabezalcod1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcabezalcod1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcajera.
		@param amtcajera amtcajera	  */
	public void setamtcajera (BigDecimal amtcajera)
	{
		set_Value (COLUMNNAME_amtcajera, amtcajera);
	}

	/** Get amtcajera.
		@return amtcajera	  */
	public BigDecimal getamtcajera () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcajera);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcanje.
		@param amtcanje amtcanje	  */
	public void setamtcanje (BigDecimal amtcanje)
	{
		set_Value (COLUMNNAME_amtcanje, amtcanje);
	}

	/** Get amtcanje.
		@return amtcanje	  */
	public BigDecimal getamtcanje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcanje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtceditodevolucion.
		@param amtceditodevolucion amtceditodevolucion	  */
	public void setamtceditodevolucion (BigDecimal amtceditodevolucion)
	{
		set_Value (COLUMNNAME_amtceditodevolucion, amtceditodevolucion);
	}

	/** Get amtceditodevolucion.
		@return amtceditodevolucion	  */
	public BigDecimal getamtceditodevolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtceditodevolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtconsistenciavtas.
		@param amtconsistenciavtas amtconsistenciavtas	  */
	public void setamtconsistenciavtas (BigDecimal amtconsistenciavtas)
	{
		set_Value (COLUMNNAME_amtconsistenciavtas, amtconsistenciavtas);
	}

	/** Get amtconsistenciavtas.
		@return amtconsistenciavtas	  */
	public BigDecimal getamtconsistenciavtas () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtconsistenciavtas);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtconsulta.
		@param amtconsulta amtconsulta	  */
	public void setamtconsulta (BigDecimal amtconsulta)
	{
		set_Value (COLUMNNAME_amtconsulta, amtconsulta);
	}

	/** Get amtconsulta.
		@return amtconsulta	  */
	public BigDecimal getamtconsulta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtconsulta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtdevolucion.
		@param amtdevolucion amtdevolucion	  */
	public void setamtdevolucion (BigDecimal amtdevolucion)
	{
		set_Value (COLUMNNAME_amtdevolucion, amtdevolucion);
	}

	/** Get amtdevolucion.
		@return amtdevolucion	  */
	public BigDecimal getamtdevolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtdevolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtestadocta.
		@param amtestadocta amtestadocta	  */
	public void setamtestadocta (BigDecimal amtestadocta)
	{
		set_Value (COLUMNNAME_amtestadocta, amtestadocta);
	}

	/** Get amtestadocta.
		@return amtestadocta	  */
	public BigDecimal getamtestadocta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtestadocta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtexentoiva.
		@param amtexentoiva amtexentoiva	  */
	public void setamtexentoiva (BigDecimal amtexentoiva)
	{
		set_Value (COLUMNNAME_amtexentoiva, amtexentoiva);
	}

	/** Get amtexentoiva.
		@return amtexentoiva	  */
	public BigDecimal getamtexentoiva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtexentoiva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtfactura.
		@param amtfactura amtfactura	  */
	public void setamtfactura (BigDecimal amtfactura)
	{
		set_Value (COLUMNNAME_amtfactura, amtfactura);
	}

	/** Get amtfactura.
		@return amtfactura	  */
	public BigDecimal getamtfactura () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtfactura);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtfondeo.
		@param amtfondeo amtfondeo	  */
	public void setamtfondeo (BigDecimal amtfondeo)
	{
		set_Value (COLUMNNAME_amtfondeo, amtfondeo);
	}

	/** Get amtfondeo.
		@return amtfondeo	  */
	public BigDecimal getamtfondeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtfondeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtgift.
		@param amtgift amtgift	  */
	public void setamtgift (BigDecimal amtgift)
	{
		set_Value (COLUMNNAME_amtgift, amtgift);
	}

	/** Get amtgift.
		@return amtgift	  */
	public BigDecimal getamtgift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtgift);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtingredopersonalretiroprod.
		@param amtingredopersonalretiroprod amtingredopersonalretiroprod	  */
	public void setamtingredopersonalretiroprod (BigDecimal amtingredopersonalretiroprod)
	{
		set_Value (COLUMNNAME_amtingredopersonalretiroprod, amtingredopersonalretiroprod);
	}

	/** Get amtingredopersonalretiroprod.
		@return amtingredopersonalretiroprod	  */
	public BigDecimal getamtingredopersonalretiroprod () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtingredopersonalretiroprod);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtinventario.
		@param amtinventario amtinventario	  */
	public void setamtinventario (BigDecimal amtinventario)
	{
		set_Value (COLUMNNAME_amtinventario, amtinventario);
	}

	/** Get amtinventario.
		@return amtinventario	  */
	public BigDecimal getamtinventario () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtinventario);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtlineacod4.
		@param amtlineacod4 amtlineacod4	  */
	public void setamtlineacod4 (BigDecimal amtlineacod4)
	{
		set_Value (COLUMNNAME_amtlineacod4, amtlineacod4);
	}

	/** Get amtlineacod4.
		@return amtlineacod4	  */
	public BigDecimal getamtlineacod4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtlineacod4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtnodefinido.
		@param amtnodefinido amtnodefinido	  */
	public void setamtnodefinido (BigDecimal amtnodefinido)
	{
		set_Value (COLUMNNAME_amtnodefinido, amtnodefinido);
	}

	/** Get amtnodefinido.
		@return amtnodefinido	  */
	public BigDecimal getamtnodefinido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtnodefinido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtnogenerado.
		@param amtnogenerado amtnogenerado	  */
	public void setamtnogenerado (BigDecimal amtnogenerado)
	{
		set_Value (COLUMNNAME_amtnogenerado, amtnogenerado);
	}

	/** Get amtnogenerado.
		@return amtnogenerado	  */
	public BigDecimal getamtnogenerado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtnogenerado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtpagocaja.
		@param amtpagocaja amtpagocaja	  */
	public void setamtpagocaja (BigDecimal amtpagocaja)
	{
		set_Value (COLUMNNAME_amtpagocaja, amtpagocaja);
	}

	/** Get amtpagocaja.
		@return amtpagocaja	  */
	public BigDecimal getamtpagocaja () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtpagocaja);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtpedido.
		@param amtpedido amtpedido	  */
	public void setamtpedido (BigDecimal amtpedido)
	{
		set_Value (COLUMNNAME_amtpedido, amtpedido);
	}

	/** Get amtpedido.
		@return amtpedido	  */
	public BigDecimal getamtpedido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtpedido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtretiro.
		@param amtretiro amtretiro	  */
	public void setamtretiro (BigDecimal amtretiro)
	{
		set_Value (COLUMNNAME_amtretiro, amtretiro);
	}

	/** Get amtretiro.
		@return amtretiro	  */
	public BigDecimal getamtretiro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtretiro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtventas.
		@param amtventas amtventas	  */
	public void setamtventas (BigDecimal amtventas)
	{
		set_Value (COLUMNNAME_amtventas, amtventas);
	}

	/** Get amtventas.
		@return amtventas	  */
	public BigDecimal getamtventas () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtventas);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtzprestamoefect.
		@param amtzprestamoefect amtzprestamoefect	  */
	public void setamtzprestamoefect (BigDecimal amtzprestamoefect)
	{
		set_Value (COLUMNNAME_amtzprestamoefect, amtzprestamoefect);
	}

	/** Get amtzprestamoefect.
		@return amtzprestamoefect	  */
	public BigDecimal getamtzprestamoefect () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtzprestamoefect);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set codigo.
		@param codigo codigo	  */
	public void setcodigo (String codigo)
	{
		set_Value (COLUMNNAME_codigo, codigo);
	}

	/** Get codigo.
		@return codigo	  */
	public String getcodigo () 
	{
		return (String)get_Value(COLUMNNAME_codigo);
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

	/** Set End Time.
		@param EndTime 
		End of the time span
	  */
	public void setEndTime (Timestamp EndTime)
	{
		set_Value (COLUMNNAME_EndTime, EndTime);
	}

	/** Get End Time.
		@return End of the time span
	  */
	public Timestamp getEndTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndTime);
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set lchequecobranza.
		@param lchequecobranza lchequecobranza	  */
	public void setlchequecobranza (String lchequecobranza)
	{
		set_Value (COLUMNNAME_lchequecobranza, lchequecobranza);
	}

	/** Get lchequecobranza.
		@return lchequecobranza	  */
	public String getlchequecobranza () 
	{
		return (String)get_Value(COLUMNNAME_lchequecobranza);
	}

	/** Set ldevenvases.
		@param ldevenvases ldevenvases	  */
	public void setldevenvases (String ldevenvases)
	{
		set_Value (COLUMNNAME_ldevenvases, ldevenvases);
	}

	/** Get ldevenvases.
		@return ldevenvases	  */
	public String getldevenvases () 
	{
		return (String)get_Value(COLUMNNAME_ldevenvases);
	}

	/** Set llineafacturas2.
		@param llineafacturas2 llineafacturas2	  */
	public void setllineafacturas2 (String llineafacturas2)
	{
		set_Value (COLUMNNAME_llineafacturas2, llineafacturas2);
	}

	/** Get llineafacturas2.
		@return llineafacturas2	  */
	public String getllineafacturas2 () 
	{
		return (String)get_Value(COLUMNNAME_llineafacturas2);
	}

	/** Set llineafondeo.
		@param llineafondeo llineafondeo	  */
	public void setllineafondeo (String llineafondeo)
	{
		set_Value (COLUMNNAME_llineafondeo, llineafondeo);
	}

	/** Get llineafondeo.
		@return llineafondeo	  */
	public String getllineafondeo () 
	{
		return (String)get_Value(COLUMNNAME_llineafondeo);
	}

	/** Set llinearetiro.
		@param llinearetiro llinearetiro	  */
	public void setllinearetiro (String llinearetiro)
	{
		set_Value (COLUMNNAME_llinearetiro, llinearetiro);
	}

	/** Get llinearetiro.
		@return llinearetiro	  */
	public String getllinearetiro () 
	{
		return (String)get_Value(COLUMNNAME_llinearetiro);
	}

	/** Set lpagodeservicio.
		@param lpagodeservicio lpagodeservicio	  */
	public void setlpagodeservicio (String lpagodeservicio)
	{
		set_Value (COLUMNNAME_lpagodeservicio, lpagodeservicio);
	}

	/** Get lpagodeservicio.
		@return lpagodeservicio	  */
	public String getlpagodeservicio () 
	{
		return (String)get_Value(COLUMNNAME_lpagodeservicio);
	}

	/** Set lventaefectivodolares.
		@param lventaefectivodolares lventaefectivodolares	  */
	public void setlventaefectivodolares (String lventaefectivodolares)
	{
		set_Value (COLUMNNAME_lventaefectivodolares, lventaefectivodolares);
	}

	/** Get lventaefectivodolares.
		@return lventaefectivodolares	  */
	public String getlventaefectivodolares () 
	{
		return (String)get_Value(COLUMNNAME_lventaefectivodolares);
	}

	/** Set lventaenefectivo.
		@param lventaenefectivo lventaenefectivo	  */
	public void setlventaenefectivo (String lventaenefectivo)
	{
		set_Value (COLUMNNAME_lventaenefectivo, lventaenefectivo);
	}

	/** Get lventaenefectivo.
		@return lventaenefectivo	  */
	public String getlventaenefectivo () 
	{
		return (String)get_Value(COLUMNNAME_lventaenefectivo);
	}

	/** Set lventascheque.
		@param lventascheque lventascheque	  */
	public void setlventascheque (String lventascheque)
	{
		set_Value (COLUMNNAME_lventascheque, lventascheque);
	}

	/** Get lventascheque.
		@return lventascheque	  */
	public String getlventascheque () 
	{
		return (String)get_Value(COLUMNNAME_lventascheque);
	}

	/** Set lventasclientesfidelizacion.
		@param lventasclientesfidelizacion lventasclientesfidelizacion	  */
	public void setlventasclientesfidelizacion (String lventasclientesfidelizacion)
	{
		set_Value (COLUMNNAME_lventasclientesfidelizacion, lventasclientesfidelizacion);
	}

	/** Get lventasclientesfidelizacion.
		@return lventasclientesfidelizacion	  */
	public String getlventasclientesfidelizacion () 
	{
		return (String)get_Value(COLUMNNAME_lventasclientesfidelizacion);
	}

	/** Set lventasefectivosodexo.
		@param lventasefectivosodexo lventasefectivosodexo	  */
	public void setlventasefectivosodexo (String lventasefectivosodexo)
	{
		set_Value (COLUMNNAME_lventasefectivosodexo, lventasefectivosodexo);
	}

	/** Get lventasefectivosodexo.
		@return lventasefectivosodexo	  */
	public String getlventasefectivosodexo () 
	{
		return (String)get_Value(COLUMNNAME_lventasefectivosodexo);
	}

	/** Set lventasluncheon.
		@param lventasluncheon lventasluncheon	  */
	public void setlventasluncheon (String lventasluncheon)
	{
		set_Value (COLUMNNAME_lventasluncheon, lventasluncheon);
	}

	/** Get lventasluncheon.
		@return lventasluncheon	  */
	public String getlventasluncheon () 
	{
		return (String)get_Value(COLUMNNAME_lventasluncheon);
	}

	/** Set lventastalimentacion.
		@param lventastalimentacion lventastalimentacion	  */
	public void setlventastalimentacion (String lventastalimentacion)
	{
		set_Value (COLUMNNAME_lventastalimentacion, lventastalimentacion);
	}

	/** Get lventastalimentacion.
		@return lventastalimentacion	  */
	public String getlventastalimentacion () 
	{
		return (String)get_Value(COLUMNNAME_lventastalimentacion);
	}

	/** Set lventastarjeta.
		@param lventastarjeta lventastarjeta	  */
	public void setlventastarjeta (String lventastarjeta)
	{
		set_Value (COLUMNNAME_lventastarjeta, lventastarjeta);
	}

	/** Get lventastarjeta.
		@return lventastarjeta	  */
	public String getlventastarjeta () 
	{
		return (String)get_Value(COLUMNNAME_lventastarjeta);
	}

	/** Set lventastarjetacuota.
		@param lventastarjetacuota lventastarjetacuota	  */
	public void setlventastarjetacuota (String lventastarjetacuota)
	{
		set_Value (COLUMNNAME_lventastarjetacuota, lventastarjetacuota);
	}

	/** Get lventastarjetacuota.
		@return lventastarjetacuota	  */
	public String getlventastarjetacuota () 
	{
		return (String)get_Value(COLUMNNAME_lventastarjetacuota);
	}

	/** Set lventastarjetaofline.
		@param lventastarjetaofline lventastarjetaofline	  */
	public void setlventastarjetaofline (String lventastarjetaofline)
	{
		set_Value (COLUMNNAME_lventastarjetaofline, lventastarjetaofline);
	}

	/** Get lventastarjetaofline.
		@return lventastarjetaofline	  */
	public String getlventastarjetaofline () 
	{
		return (String)get_Value(COLUMNNAME_lventastarjetaofline);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set path.
		@param path path	  */
	public void setpath (String path)
	{
		set_Value (COLUMNNAME_path, path);
	}

	/** Get path.
		@return path	  */
	public String getpath () 
	{
		return (String)get_Value(COLUMNNAME_path);
	}

	/** Set qtyapgaveta.
		@param qtyapgaveta qtyapgaveta	  */
	public void setqtyapgaveta (BigDecimal qtyapgaveta)
	{
		set_Value (COLUMNNAME_qtyapgaveta, qtyapgaveta);
	}

	/** Get qtyapgaveta.
		@return qtyapgaveta	  */
	public BigDecimal getqtyapgaveta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyapgaveta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtycajera.
		@param qtycajera qtycajera	  */
	public void setqtycajera (BigDecimal qtycajera)
	{
		set_Value (COLUMNNAME_qtycajera, qtycajera);
	}

	/** Get qtycajera.
		@return qtycajera	  */
	public BigDecimal getqtycajera () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtycajera);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtycanje.
		@param qtycanje qtycanje	  */
	public void setqtycanje (BigDecimal qtycanje)
	{
		set_Value (COLUMNNAME_qtycanje, qtycanje);
	}

	/** Get qtycanje.
		@return qtycanje	  */
	public BigDecimal getqtycanje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtycanje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyceditodevolucion.
		@param qtyceditodevolucion qtyceditodevolucion	  */
	public void setqtyceditodevolucion (BigDecimal qtyceditodevolucion)
	{
		set_Value (COLUMNNAME_qtyceditodevolucion, qtyceditodevolucion);
	}

	/** Get qtyceditodevolucion.
		@return qtyceditodevolucion	  */
	public BigDecimal getqtyceditodevolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyceditodevolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyconsulta.
		@param qtyconsulta qtyconsulta	  */
	public void setqtyconsulta (BigDecimal qtyconsulta)
	{
		set_Value (COLUMNNAME_qtyconsulta, qtyconsulta);
	}

	/** Get qtyconsulta.
		@return qtyconsulta	  */
	public BigDecimal getqtyconsulta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyconsulta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtydevolucion.
		@param qtydevolucion qtydevolucion	  */
	public void setqtydevolucion (BigDecimal qtydevolucion)
	{
		set_Value (COLUMNNAME_qtydevolucion, qtydevolucion);
	}

	/** Get qtydevolucion.
		@return qtydevolucion	  */
	public BigDecimal getqtydevolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtydevolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyestadocta.
		@param qtyestadocta qtyestadocta	  */
	public void setqtyestadocta (BigDecimal qtyestadocta)
	{
		set_Value (COLUMNNAME_qtyestadocta, qtyestadocta);
	}

	/** Get qtyestadocta.
		@return qtyestadocta	  */
	public BigDecimal getqtyestadocta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyestadocta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyexentoiva.
		@param qtyexentoiva qtyexentoiva	  */
	public void setqtyexentoiva (BigDecimal qtyexentoiva)
	{
		set_Value (COLUMNNAME_qtyexentoiva, qtyexentoiva);
	}

	/** Get qtyexentoiva.
		@return qtyexentoiva	  */
	public BigDecimal getqtyexentoiva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyexentoiva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyfactura.
		@param qtyfactura qtyfactura	  */
	public void setqtyfactura (BigDecimal qtyfactura)
	{
		set_Value (COLUMNNAME_qtyfactura, qtyfactura);
	}

	/** Get qtyfactura.
		@return qtyfactura	  */
	public BigDecimal getqtyfactura () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyfactura);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyfondeo.
		@param qtyfondeo qtyfondeo	  */
	public void setqtyfondeo (BigDecimal qtyfondeo)
	{
		set_Value (COLUMNNAME_qtyfondeo, qtyfondeo);
	}

	/** Get qtyfondeo.
		@return qtyfondeo	  */
	public BigDecimal getqtyfondeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyfondeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtygift.
		@param qtygift qtygift	  */
	public void setqtygift (BigDecimal qtygift)
	{
		set_Value (COLUMNNAME_qtygift, qtygift);
	}

	/** Get qtygift.
		@return qtygift	  */
	public BigDecimal getqtygift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtygift);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyingredopersonalretiroprod.
		@param qtyingredopersonalretiroprod qtyingredopersonalretiroprod	  */
	public void setqtyingredopersonalretiroprod (BigDecimal qtyingredopersonalretiroprod)
	{
		set_Value (COLUMNNAME_qtyingredopersonalretiroprod, qtyingredopersonalretiroprod);
	}

	/** Get qtyingredopersonalretiroprod.
		@return qtyingredopersonalretiroprod	  */
	public BigDecimal getqtyingredopersonalretiroprod () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyingredopersonalretiroprod);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyinventario.
		@param qtyinventario qtyinventario	  */
	public void setqtyinventario (BigDecimal qtyinventario)
	{
		set_Value (COLUMNNAME_qtyinventario, qtyinventario);
	}

	/** Get qtyinventario.
		@return qtyinventario	  */
	public BigDecimal getqtyinventario () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyinventario);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtynodefinido.
		@param qtynodefinido qtynodefinido	  */
	public void setqtynodefinido (BigDecimal qtynodefinido)
	{
		set_Value (COLUMNNAME_qtynodefinido, qtynodefinido);
	}

	/** Get qtynodefinido.
		@return qtynodefinido	  */
	public BigDecimal getqtynodefinido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtynodefinido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtynogenerado.
		@param qtynogenerado qtynogenerado	  */
	public void setqtynogenerado (BigDecimal qtynogenerado)
	{
		set_Value (COLUMNNAME_qtynogenerado, qtynogenerado);
	}

	/** Get qtynogenerado.
		@return qtynogenerado	  */
	public BigDecimal getqtynogenerado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtynogenerado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtypagocaja.
		@param qtypagocaja qtypagocaja	  */
	public void setqtypagocaja (BigDecimal qtypagocaja)
	{
		set_Value (COLUMNNAME_qtypagocaja, qtypagocaja);
	}

	/** Get qtypagocaja.
		@return qtypagocaja	  */
	public BigDecimal getqtypagocaja () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtypagocaja);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtypedido.
		@param qtypedido qtypedido	  */
	public void setqtypedido (BigDecimal qtypedido)
	{
		set_Value (COLUMNNAME_qtypedido, qtypedido);
	}

	/** Get qtypedido.
		@return qtypedido	  */
	public BigDecimal getqtypedido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtypedido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyretiro.
		@param qtyretiro qtyretiro	  */
	public void setqtyretiro (BigDecimal qtyretiro)
	{
		set_Value (COLUMNNAME_qtyretiro, qtyretiro);
	}

	/** Get qtyretiro.
		@return qtyretiro	  */
	public BigDecimal getqtyretiro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyretiro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyventas.
		@param qtyventas qtyventas	  */
	public void setqtyventas (BigDecimal qtyventas)
	{
		set_Value (COLUMNNAME_qtyventas, qtyventas);
	}

	/** Get qtyventas.
		@return qtyventas	  */
	public BigDecimal getqtyventas () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyventas);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyzprestamoefect.
		@param qtyzprestamoefect qtyzprestamoefect	  */
	public void setqtyzprestamoefect (BigDecimal qtyzprestamoefect)
	{
		set_Value (COLUMNNAME_qtyzprestamoefect, qtyzprestamoefect);
	}

	/** Get qtyzprestamoefect.
		@return qtyzprestamoefect	  */
	public BigDecimal getqtyzprestamoefect () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyzprestamoefect);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Start Time.
		@param StartTime 
		Time started
	  */
	public void setStartTime (Timestamp StartTime)
	{
		set_Value (COLUMNNAME_StartTime, StartTime);
	}

	/** Get Start Time.
		@return Time started
	  */
	public Timestamp getStartTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartTime);
	}

	/** Set totalheaders.
		@param totalheaders totalheaders	  */
	public void settotalheaders (String totalheaders)
	{
		set_Value (COLUMNNAME_totalheaders, totalheaders);
	}

	/** Get totalheaders.
		@return totalheaders	  */
	public String gettotalheaders () 
	{
		return (String)get_Value(COLUMNNAME_totalheaders);
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (String TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public String getTotalLines () 
	{
		return (String)get_Value(COLUMNNAME_TotalLines);
	}

	/** Set totallinesfile.
		@param totallinesfile totallinesfile	  */
	public void settotallinesfile (String totallinesfile)
	{
		set_Value (COLUMNNAME_totallinesfile, totallinesfile);
	}

	/** Get totallinesfile.
		@return totallinesfile	  */
	public String gettotallinesfile () 
	{
		return (String)get_Value(COLUMNNAME_totallinesfile);
	}

	/** Set UY_Audit_Cov_LoadTicket.
		@param UY_Audit_Cov_LoadTicket_ID UY_Audit_Cov_LoadTicket	  */
	public void setUY_Audit_Cov_LoadTicket_ID (int UY_Audit_Cov_LoadTicket_ID)
	{
		if (UY_Audit_Cov_LoadTicket_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Audit_Cov_LoadTicket_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Audit_Cov_LoadTicket_ID, Integer.valueOf(UY_Audit_Cov_LoadTicket_ID));
	}

	/** Get UY_Audit_Cov_LoadTicket.
		@return UY_Audit_Cov_LoadTicket	  */
	public int getUY_Audit_Cov_LoadTicket_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Audit_Cov_LoadTicket_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}