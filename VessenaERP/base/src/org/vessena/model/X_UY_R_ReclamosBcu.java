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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_R_ReclamosBcu
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_ReclamosBcu extends PO implements I_UY_R_ReclamosBcu, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140210L;

    /** Standard Constructor */
    public X_UY_R_ReclamosBcu (Properties ctx, int UY_R_ReclamosBcu_ID, String trxName)
    {
      super (ctx, UY_R_ReclamosBcu_ID, trxName);
      /** if (UY_R_ReclamosBcu_ID == 0)
        {
			setUY_R_ReclamosBcu_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_ReclamosBcu (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_ReclamosBcu[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CantidadReclamos.
		@param CantidadReclamos 
		CantidadReclamos
	  */
	public void setCantidadReclamos (BigDecimal CantidadReclamos)
	{
		set_Value (COLUMNNAME_CantidadReclamos, CantidadReclamos);
	}

	/** Get CantidadReclamos.
		@return CantidadReclamos
	  */
	public BigDecimal getCantidadReclamos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CantidadReclamos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Clientes.
		@param Clientes 
		Clientes
	  */
	public void setClientes (BigDecimal Clientes)
	{
		set_Value (COLUMNNAME_Clientes, Clientes);
	}

	/** Get Clientes.
		@return Clientes
	  */
	public BigDecimal getClientes () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Clientes);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CliMayor15Dias.
		@param CliMayor15Dias 
		CliMayor15Dias
	  */
	public void setCliMayor15Dias (BigDecimal CliMayor15Dias)
	{
		set_Value (COLUMNNAME_CliMayor15Dias, CliMayor15Dias);
	}

	/** Get CliMayor15Dias.
		@return CliMayor15Dias
	  */
	public BigDecimal getCliMayor15Dias () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CliMayor15Dias);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CliMenor15Dias.
		@param CliMenor15Dias 
		CliMenor15Dias
	  */
	public void setCliMenor15Dias (BigDecimal CliMenor15Dias)
	{
		set_Value (COLUMNNAME_CliMenor15Dias, CliMenor15Dias);
	}

	/** Get CliMenor15Dias.
		@return CliMenor15Dias
	  */
	public BigDecimal getCliMenor15Dias () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CliMenor15Dias);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CliMenor2Dias.
		@param CliMenor2Dias 
		CliMenor2Dias
	  */
	public void setCliMenor2Dias (BigDecimal CliMenor2Dias)
	{
		set_Value (COLUMNNAME_CliMenor2Dias, CliMenor2Dias);
	}

	/** Get CliMenor2Dias.
		@return CliMenor2Dias
	  */
	public BigDecimal getCliMenor2Dias () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CliMenor2Dias);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CodigoInstitucion.
		@param CodigoInstitucion 
		CodigoInstitucion
	  */
	public void setCodigoInstitucion (String CodigoInstitucion)
	{
		set_Value (COLUMNNAME_CodigoInstitucion, CodigoInstitucion);
	}

	/** Get CodigoInstitucion.
		@return CodigoInstitucion
	  */
	public String getCodigoInstitucion () 
	{
		return (String)get_Value(COLUMNNAME_CodigoInstitucion);
	}

	/** Set EsperaSolucion.
		@param EsperaSolucion 
		EsperaSolucion
	  */
	public void setEsperaSolucion (BigDecimal EsperaSolucion)
	{
		set_Value (COLUMNNAME_EsperaSolucion, EsperaSolucion);
	}

	/** Get EsperaSolucion.
		@return EsperaSolucion
	  */
	public BigDecimal getEsperaSolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EsperaSolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Institucion.
		@param Institucion 
		Institucion
	  */
	public void setInstitucion (String Institucion)
	{
		set_Value (COLUMNNAME_Institucion, Institucion);
	}

	/** Get Institucion.
		@return Institucion
	  */
	public String getInstitucion () 
	{
		return (String)get_Value(COLUMNNAME_Institucion);
	}

	/** Set InstMayor15Dias.
		@param InstMayor15Dias 
		InstMayor15Dias
	  */
	public void setInstMayor15Dias (BigDecimal InstMayor15Dias)
	{
		set_Value (COLUMNNAME_InstMayor15Dias, InstMayor15Dias);
	}

	/** Get InstMayor15Dias.
		@return InstMayor15Dias
	  */
	public BigDecimal getInstMayor15Dias () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InstMayor15Dias);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set InstMenor15Dias.
		@param InstMenor15Dias 
		InstMenor15Dias
	  */
	public void setInstMenor15Dias (BigDecimal InstMenor15Dias)
	{
		set_Value (COLUMNNAME_InstMenor15Dias, InstMenor15Dias);
	}

	/** Get InstMenor15Dias.
		@return InstMenor15Dias
	  */
	public BigDecimal getInstMenor15Dias () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InstMenor15Dias);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set InstMenor2Dias.
		@param InstMenor2Dias 
		InstMenor2Dias
	  */
	public void setInstMenor2Dias (BigDecimal InstMenor2Dias)
	{
		set_Value (COLUMNNAME_InstMenor2Dias, InstMenor2Dias);
	}

	/** Get InstMenor2Dias.
		@return InstMenor2Dias
	  */
	public BigDecimal getInstMenor2Dias () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InstMenor2Dias);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PromedioCliente.
		@param PromedioCliente 
		PromedioCliente
	  */
	public void setPromedioCliente (BigDecimal PromedioCliente)
	{
		set_Value (COLUMNNAME_PromedioCliente, PromedioCliente);
	}

	/** Get PromedioCliente.
		@return PromedioCliente
	  */
	public BigDecimal getPromedioCliente () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PromedioCliente);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PromedioInstitucion.
		@param PromedioInstitucion 
		PromedioInstitucion
	  */
	public void setPromedioInstitucion (BigDecimal PromedioInstitucion)
	{
		set_Value (COLUMNNAME_PromedioInstitucion, PromedioInstitucion);
	}

	/** Get PromedioInstitucion.
		@return PromedioInstitucion
	  */
	public BigDecimal getPromedioInstitucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PromedioInstitucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RangoPeriodo.
		@param RangoPeriodo 
		RangoPeriodo
	  */
	public void setRangoPeriodo (String RangoPeriodo)
	{
		set_Value (COLUMNNAME_RangoPeriodo, RangoPeriodo);
	}

	/** Get RangoPeriodo.
		@return RangoPeriodo
	  */
	public String getRangoPeriodo () 
	{
		return (String)get_Value(COLUMNNAME_RangoPeriodo);
	}

	/** Set ReclamosPorClientes.
		@param ReclamosPorClientes 
		ReclamosPorClientes
	  */
	public void setReclamosPorClientes (BigDecimal ReclamosPorClientes)
	{
		set_Value (COLUMNNAME_ReclamosPorClientes, ReclamosPorClientes);
	}

	/** Get ReclamosPorClientes.
		@return ReclamosPorClientes
	  */
	public BigDecimal getReclamosPorClientes () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReclamosPorClientes);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Servicio.
		@param Servicio 
		Servicio
	  */
	public void setServicio (String Servicio)
	{
		set_Value (COLUMNNAME_Servicio, Servicio);
	}

	/** Get Servicio.
		@return Servicio
	  */
	public String getServicio () 
	{
		return (String)get_Value(COLUMNNAME_Servicio);
	}

	/** Set UY_R_ReclamosBcu.
		@param UY_R_ReclamosBcu_ID UY_R_ReclamosBcu	  */
	public void setUY_R_ReclamosBcu_ID (int UY_R_ReclamosBcu_ID)
	{
		if (UY_R_ReclamosBcu_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamosBcu_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamosBcu_ID, Integer.valueOf(UY_R_ReclamosBcu_ID));
	}

	/** Get UY_R_ReclamosBcu.
		@return UY_R_ReclamosBcu	  */
	public int getUY_R_ReclamosBcu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ReclamosBcu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}