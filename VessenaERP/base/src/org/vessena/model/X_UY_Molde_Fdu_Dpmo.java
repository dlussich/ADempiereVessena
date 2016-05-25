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

/** Generated Model for UY_Molde_Fdu_Dpmo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Molde_Fdu_Dpmo extends PO implements I_UY_Molde_Fdu_Dpmo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130228L;

    /** Standard Constructor */
    public X_UY_Molde_Fdu_Dpmo (Properties ctx, int UY_Molde_Fdu_Dpmo_ID, String trxName)
    {
      super (ctx, UY_Molde_Fdu_Dpmo_ID, trxName);
      /** if (UY_Molde_Fdu_Dpmo_ID == 0)
        {
			setAccion (null);
			setAD_User_ID (0);
			setFechaFin (new Timestamp( System.currentTimeMillis() ));
			setFechaInicio (new Timestamp( System.currentTimeMillis() ));
			setidReporte (null);
        } */
    }

    /** Load Constructor */
    public X_UY_Molde_Fdu_Dpmo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Molde_Fdu_Dpmo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accion.
		@param Accion Accion	  */
	public void setAccion (String Accion)
	{
		set_Value (COLUMNNAME_Accion, Accion);
	}

	/** Get Accion.
		@return Accion	  */
	public String getAccion () 
	{
		return (String)get_Value(COLUMNNAME_Accion);
	}

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cantidad.
		@param Cantidad Cantidad	  */
	public void setCantidad (BigDecimal Cantidad)
	{
		set_Value (COLUMNNAME_Cantidad, Cantidad);
	}

	/** Get Cantidad.
		@return Cantidad	  */
	public BigDecimal getCantidad () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cantidad);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CantidadTotal.
		@param CantidadTotal 
		CantidadTotal
	  */
	public void setCantidadTotal (BigDecimal CantidadTotal)
	{
		set_Value (COLUMNNAME_CantidadTotal, CantidadTotal);
	}

	/** Get CantidadTotal.
		@return CantidadTotal
	  */
	public BigDecimal getCantidadTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CantidadTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DPMO.
		@param DPMO 
		DPMO
	  */
	public void setDPMO (BigDecimal DPMO)
	{
		set_Value (COLUMNNAME_DPMO, DPMO);
	}

	/** Get DPMO.
		@return DPMO
	  */
	public BigDecimal getDPMO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DPMO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FechaFin.
		@param FechaFin FechaFin	  */
	public void setFechaFin (Timestamp FechaFin)
	{
		set_Value (COLUMNNAME_FechaFin, FechaFin);
	}

	/** Get FechaFin.
		@return FechaFin	  */
	public Timestamp getFechaFin () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaFin);
	}

	/** Set FechaInicio.
		@param FechaInicio FechaInicio	  */
	public void setFechaInicio (Timestamp FechaInicio)
	{
		set_Value (COLUMNNAME_FechaInicio, FechaInicio);
	}

	/** Get FechaInicio.
		@return FechaInicio	  */
	public Timestamp getFechaInicio () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaInicio);
	}

	/** Set Identificador Unico del Reporte.
		@param idReporte Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte)
	{
		set_Value (COLUMNNAME_idReporte, idReporte);
	}

	/** Get Identificador Unico del Reporte.
		@return Identificador Unico del Reporte	  */
	public String getidReporte () 
	{
		return (String)get_Value(COLUMNNAME_idReporte);
	}
}