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

/** Generated Model for UY_Molde_FduLoad_CC120
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Molde_FduLoad_CC120 extends PO implements I_UY_Molde_FduLoad_CC120, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140502L;

    /** Standard Constructor */
    public X_UY_Molde_FduLoad_CC120 (Properties ctx, int UY_Molde_FduLoad_CC120_ID, String trxName)
    {
      super (ctx, UY_Molde_FduLoad_CC120_ID, trxName);
      /** if (UY_Molde_FduLoad_CC120_ID == 0)
        {
			setcodigo (null);
			setcuenta (null);
			setDebCred (0);
			setfecha (new Timestamp( System.currentTimeMillis() ));
			setUY_Molde_FduLoad_CC120_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Molde_FduLoad_CC120 (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Molde_FduLoad_CC120[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CantCtas.
		@param CantCtas 
		CantCtas
	  */
	public void setCantCtas (int CantCtas)
	{
		set_Value (COLUMNNAME_CantCtas, Integer.valueOf(CantCtas));
	}

	/** Get CantCtas.
		@return CantCtas
	  */
	public int getCantCtas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CantCtas);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set cuenta.
		@param cuenta cuenta	  */
	public void setcuenta (String cuenta)
	{
		set_Value (COLUMNNAME_cuenta, cuenta);
	}

	/** Get cuenta.
		@return cuenta	  */
	public String getcuenta () 
	{
		return (String)get_Value(COLUMNNAME_cuenta);
	}

	/** Set Deb/Cred.
		@param DebCred Deb/Cred	  */
	public void setDebCred (int DebCred)
	{
		set_Value (COLUMNNAME_DebCred, Integer.valueOf(DebCred));
	}

	/** Get Deb/Cred.
		@return Deb/Cred	  */
	public int getDebCred () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DebCred);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set entrada.
		@param entrada entrada	  */
	public void setentrada (BigDecimal entrada)
	{
		set_Value (COLUMNNAME_entrada, entrada);
	}

	/** Get entrada.
		@return entrada	  */
	public BigDecimal getentrada () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_entrada);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set fecha.
		@param fecha fecha	  */
	public void setfecha (Timestamp fecha)
	{
		set_Value (COLUMNNAME_fecha, fecha);
	}

	/** Get fecha.
		@return fecha	  */
	public Timestamp getfecha () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecha);
	}

	/** Set intereses.
		@param intereses intereses	  */
	public void setintereses (BigDecimal intereses)
	{
		set_Value (COLUMNNAME_intereses, intereses);
	}

	/** Get intereses.
		@return intereses	  */
	public BigDecimal getintereses () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_intereses);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_Molde_FduLoad_CC120.
		@param UY_Molde_FduLoad_CC120_ID UY_Molde_FduLoad_CC120	  */
	public void setUY_Molde_FduLoad_CC120_ID (int UY_Molde_FduLoad_CC120_ID)
	{
		if (UY_Molde_FduLoad_CC120_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_FduLoad_CC120_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_FduLoad_CC120_ID, Integer.valueOf(UY_Molde_FduLoad_CC120_ID));
	}

	/** Get UY_Molde_FduLoad_CC120.
		@return UY_Molde_FduLoad_CC120	  */
	public int getUY_Molde_FduLoad_CC120_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Molde_FduLoad_CC120_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}