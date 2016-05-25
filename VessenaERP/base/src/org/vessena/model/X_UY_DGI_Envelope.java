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

/** Generated Model for UY_DGI_Envelope
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DGI_Envelope extends PO implements I_UY_DGI_Envelope, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150611L;

    /** Standard Constructor */
    public X_UY_DGI_Envelope (Properties ctx, int UY_DGI_Envelope_ID, String trxName)
    {
      super (ctx, UY_DGI_Envelope_ID, trxName);
      /** if (UY_DGI_Envelope_ID == 0)
        {
			setUY_DGI_Envelope_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DGI_Envelope (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DGI_Envelope[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CantidadCFE.
		@param CantidadCFE CantidadCFE	  */
	public void setCantidadCFE (BigDecimal CantidadCFE)
	{
		set_Value (COLUMNNAME_CantidadCFE, CantidadCFE);
	}

	/** Get CantidadCFE.
		@return CantidadCFE	  */
	public BigDecimal getCantidadCFE () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CantidadCFE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (boolean ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, Boolean.valueOf(ExecuteAction));
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public boolean isExecuteAction () 
	{
		Object oo = get_Value(COLUMNNAME_ExecuteAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ExecuteAction2.
		@param ExecuteAction2 ExecuteAction2	  */
	public void setExecuteAction2 (boolean ExecuteAction2)
	{
		set_Value (COLUMNNAME_ExecuteAction2, Boolean.valueOf(ExecuteAction2));
	}

	/** Get ExecuteAction2.
		@return ExecuteAction2	  */
	public boolean isExecuteAction2 () 
	{
		Object oo = get_Value(COLUMNNAME_ExecuteAction2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ExecuteAction3.
		@param ExecuteAction3 ExecuteAction3	  */
	public void setExecuteAction3 (boolean ExecuteAction3)
	{
		set_Value (COLUMNNAME_ExecuteAction3, Boolean.valueOf(ExecuteAction3));
	}

	/** Get ExecuteAction3.
		@return ExecuteAction3	  */
	public boolean isExecuteAction3 () 
	{
		Object oo = get_Value(COLUMNNAME_ExecuteAction3);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set FechaCreacion.
		@param FechaCreacion FechaCreacion	  */
	public void setFechaCreacion (Timestamp FechaCreacion)
	{
		set_Value (COLUMNNAME_FechaCreacion, FechaCreacion);
	}

	/** Get FechaCreacion.
		@return FechaCreacion	  */
	public Timestamp getFechaCreacion () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaCreacion);
	}

	/** Set FechaHoraProximaConsulta.
		@param FechaHoraProximaConsulta FechaHoraProximaConsulta	  */
	public void setFechaHoraProximaConsulta (Timestamp FechaHoraProximaConsulta)
	{
		set_Value (COLUMNNAME_FechaHoraProximaConsulta, FechaHoraProximaConsulta);
	}

	/** Get FechaHoraProximaConsulta.
		@return FechaHoraProximaConsulta	  */
	public Timestamp getFechaHoraProximaConsulta () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaHoraProximaConsulta);
	}

	/** Set FechaRecepcion.
		@param FechaRecepcion FechaRecepcion	  */
	public void setFechaRecepcion (Timestamp FechaRecepcion)
	{
		set_Value (COLUMNNAME_FechaRecepcion, FechaRecepcion);
	}

	/** Get FechaRecepcion.
		@return FechaRecepcion	  */
	public Timestamp getFechaRecepcion () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaRecepcion);
	}

	/** Set IdEmisor.
		@param IdEmisor IdEmisor	  */
	public void setIdEmisor (int IdEmisor)
	{
		set_Value (COLUMNNAME_IdEmisor, Integer.valueOf(IdEmisor));
	}

	/** Get IdEmisor.
		@return IdEmisor	  */
	public int getIdEmisor () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IdEmisor);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IdRespuesta.
		@param IdRespuesta IdRespuesta	  */
	public void setIdRespuesta (int IdRespuesta)
	{
		set_Value (COLUMNNAME_IdRespuesta, Integer.valueOf(IdRespuesta));
	}

	/** Get IdRespuesta.
		@return IdRespuesta	  */
	public int getIdRespuesta () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IdRespuesta);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RutEmisor.
		@param RutEmisor RutEmisor	  */
	public void setRutEmisor (BigDecimal RutEmisor)
	{
		set_Value (COLUMNNAME_RutEmisor, RutEmisor);
	}

	/** Get RutEmisor.
		@return RutEmisor	  */
	public BigDecimal getRutEmisor () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RutEmisor);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RutReceptor.
		@param RutReceptor RutReceptor	  */
	public void setRutReceptor (BigDecimal RutReceptor)
	{
		set_Value (COLUMNNAME_RutReceptor, RutReceptor);
	}

	/** Get RutReceptor.
		@return RutReceptor	  */
	public BigDecimal getRutReceptor () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RutReceptor);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set StatusSobre.
		@param StatusSobre StatusSobre	  */
	public void setStatusSobre (String StatusSobre)
	{
		set_Value (COLUMNNAME_StatusSobre, StatusSobre);
	}

	/** Get StatusSobre.
		@return StatusSobre	  */
	public String getStatusSobre () 
	{
		return (String)get_Value(COLUMNNAME_StatusSobre);
	}

	/** Set Token.
		@param Token Token	  */
	public void setToken (String Token)
	{
		set_Value (COLUMNNAME_Token, Token);
	}

	/** Get Token.
		@return Token	  */
	public String getToken () 
	{
		return (String)get_Value(COLUMNNAME_Token);
	}

	/** Set UY_DGI_Envelope.
		@param UY_DGI_Envelope_ID UY_DGI_Envelope	  */
	public void setUY_DGI_Envelope_ID (int UY_DGI_Envelope_ID)
	{
		if (UY_DGI_Envelope_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DGI_Envelope_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DGI_Envelope_ID, Integer.valueOf(UY_DGI_Envelope_ID));
	}

	/** Get UY_DGI_Envelope.
		@return UY_DGI_Envelope	  */
	public int getUY_DGI_Envelope_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DGI_Envelope_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}