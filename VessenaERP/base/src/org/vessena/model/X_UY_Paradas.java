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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_Paradas
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_Paradas extends PO implements I_UY_Paradas, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_Paradas (Properties ctx, int UY_Paradas_ID, String trxName)
    {
      super (ctx, UY_Paradas_ID, trxName);
      /** if (UY_Paradas_ID == 0)
        {
			setPP_Order_ID (0);
			setUY_Paradas_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Paradas (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Paradas[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
    {
		return (org.eevolution.model.I_PP_Order)MTable.get(getCtx(), org.eevolution.model.I_PP_Order.Table_Name)
			.getPO(getPP_Order_ID(), get_TrxName());	}

	/** Set Manufacturing Order.
		@param PP_Order_ID Manufacturing Order	  */
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Manufacturing Order.
		@return Manufacturing Order	  */
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Accion.
		@param UY_Accion UY_Accion	  */
	public void setUY_Accion (String UY_Accion)
	{
		set_Value (COLUMNNAME_UY_Accion, UY_Accion);
	}

	/** Get UY_Accion.
		@return UY_Accion	  */
	public String getUY_Accion () 
	{
		return (String)get_Value(COLUMNNAME_UY_Accion);
	}

	public I_UY_Confirmorderhdr getUY_Confirmorderhdr() throws RuntimeException
    {
		return (I_UY_Confirmorderhdr)MTable.get(getCtx(), I_UY_Confirmorderhdr.Table_Name)
			.getPO(getUY_Confirmorderhdr_ID(), get_TrxName());	}

	/** Set UY_Confirmorderhdr.
		@param UY_Confirmorderhdr_ID UY_Confirmorderhdr	  */
	public void setUY_Confirmorderhdr_ID (int UY_Confirmorderhdr_ID)
	{
		if (UY_Confirmorderhdr_ID < 1) 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, Integer.valueOf(UY_Confirmorderhdr_ID));
	}

	/** Get UY_Confirmorderhdr.
		@return UY_Confirmorderhdr	  */
	public int getUY_Confirmorderhdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Confirmorderhdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Detalle.
		@param UY_Detalle UY_Detalle	  */
	public void setUY_Detalle (String UY_Detalle)
	{
		set_Value (COLUMNNAME_UY_Detalle, UY_Detalle);
	}

	/** Get UY_Detalle.
		@return UY_Detalle	  */
	public String getUY_Detalle () 
	{
		return (String)get_Value(COLUMNNAME_UY_Detalle);
	}

	/** Set UY_horafin.
		@param UY_horafin UY_horafin	  */
	public void setUY_horafin (Timestamp UY_horafin)
	{
		set_Value (COLUMNNAME_UY_horafin, UY_horafin);
	}

	/** Get UY_horafin.
		@return UY_horafin	  */
	public Timestamp getUY_horafin () 
	{
		return (Timestamp)get_Value(COLUMNNAME_UY_horafin);
	}

	/** Set UY_horainicio.
		@param UY_horainicio UY_horainicio	  */
	public void setUY_horainicio (Timestamp UY_horainicio)
	{
		set_Value (COLUMNNAME_UY_horainicio, UY_horainicio);
	}

	/** Get UY_horainicio.
		@return UY_horainicio	  */
	public Timestamp getUY_horainicio () 
	{
		return (Timestamp)get_Value(COLUMNNAME_UY_horainicio);
	}

	/** Set UY_Paradas.
		@param UY_Paradas_ID UY_Paradas	  */
	public void setUY_Paradas_ID (int UY_Paradas_ID)
	{
		if (UY_Paradas_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Paradas_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Paradas_ID, Integer.valueOf(UY_Paradas_ID));
	}

	/** Get UY_Paradas.
		@return UY_Paradas	  */
	public int getUY_Paradas_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Paradas_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** uy_tipoincidencia AD_Reference_ID=1000032 */
	public static final int UY_TIPOINCIDENCIA_AD_Reference_ID=1000032;
	/** 1 - Falta MP / Componentes = 1 - Falta MP / Componentes */
	public static final String UY_TIPOINCIDENCIA_1_FaltaMPComponentes = "1 - Falta MP / Componentes";
	/** 2 - Falta Granel = 2 - Falta Granel */
	public static final String UY_TIPOINCIDENCIA_2_FaltaGranel = "2 - Falta Granel";
	/** 3 - Rotura / Ajuste de Máquinas = 3 - Rotura / Ajuste de Máquinas */
	public static final String UY_TIPOINCIDENCIA_3_RoturaAjusteDeaquinas = "3 - Rotura / Ajuste de Maquinas";
	/** 4 - Calidad = 4 - Calidad */
	public static final String UY_TIPOINCIDENCIA_4_Calidad = "4 - Calidad";
	/** 5 - Descanso = 5 - Descanso */
	public static final String UY_TIPOINCIDENCIA_5_Descanso = "5 - Descanso";
	/** 6 - Cambio de Jornada = 6 - Cambio de Jornada */
	public static final String UY_TIPOINCIDENCIA_6_CambioDeJornada = "6 - Cambio de Jornada";
	/** 7 - Otros = 7 - Otros */
	public static final String UY_TIPOINCIDENCIA_7_Otros = "7 - Otros";
	/** Set uy_tipoincidencia.
		@param uy_tipoincidencia uy_tipoincidencia	  */
	public void setuy_tipoincidencia (String uy_tipoincidencia)
	{

		set_Value (COLUMNNAME_uy_tipoincidencia, uy_tipoincidencia);
	}

	/** Get uy_tipoincidencia.
		@return uy_tipoincidencia	  */
	public String getuy_tipoincidencia () 
	{
		return (String)get_Value(COLUMNNAME_uy_tipoincidencia);
	}
}