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

/** Generated Model for UY_Fdu_CargoImporte
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_CargoImporte extends PO implements I_UY_Fdu_CargoImporte, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130527L;

    /** Standard Constructor */
    public X_UY_Fdu_CargoImporte (Properties ctx, int UY_Fdu_CargoImporte_ID, String trxName)
    {
      super (ctx, UY_Fdu_CargoImporte_ID, trxName);
      /** if (UY_Fdu_CargoImporte_ID == 0)
        {
			setAmount (Env.ZERO);
			setUY_Fdu_Afinidad_ID (0);
			setUY_Fdu_Cargo_ID (0);
			setUY_Fdu_CargoImporte_ID (0);
			setUY_Fdu_Productos_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_CargoImporte (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_CargoImporte[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_UY_Fdu_Afinidad getUY_Fdu_Afinidad() throws RuntimeException
    {
		return (I_UY_Fdu_Afinidad)MTable.get(getCtx(), I_UY_Fdu_Afinidad.Table_Name)
			.getPO(getUY_Fdu_Afinidad_ID(), get_TrxName());	}

	/** Set UY_Fdu_Afinidad.
		@param UY_Fdu_Afinidad_ID UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID)
	{
		if (UY_Fdu_Afinidad_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Afinidad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Afinidad_ID, Integer.valueOf(UY_Fdu_Afinidad_ID));
	}

	/** Get UY_Fdu_Afinidad.
		@return UY_Fdu_Afinidad	  */
	public int getUY_Fdu_Afinidad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Afinidad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Cargo getUY_Fdu_Cargo() throws RuntimeException
    {
		return (I_UY_Fdu_Cargo)MTable.get(getCtx(), I_UY_Fdu_Cargo.Table_Name)
			.getPO(getUY_Fdu_Cargo_ID(), get_TrxName());	}

	/** Set UY_Fdu_Cargo.
		@param UY_Fdu_Cargo_ID UY_Fdu_Cargo	  */
	public void setUY_Fdu_Cargo_ID (int UY_Fdu_Cargo_ID)
	{
		if (UY_Fdu_Cargo_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Cargo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Cargo_ID, Integer.valueOf(UY_Fdu_Cargo_ID));
	}

	/** Get UY_Fdu_Cargo.
		@return UY_Fdu_Cargo	  */
	public int getUY_Fdu_Cargo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Cargo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_CargoImporte.
		@param UY_Fdu_CargoImporte_ID UY_Fdu_CargoImporte	  */
	public void setUY_Fdu_CargoImporte_ID (int UY_Fdu_CargoImporte_ID)
	{
		if (UY_Fdu_CargoImporte_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CargoImporte_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CargoImporte_ID, Integer.valueOf(UY_Fdu_CargoImporte_ID));
	}

	/** Get UY_Fdu_CargoImporte.
		@return UY_Fdu_CargoImporte	  */
	public int getUY_Fdu_CargoImporte_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_CargoImporte_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Productos getUY_Fdu_Productos() throws RuntimeException
    {
		return (I_UY_Fdu_Productos)MTable.get(getCtx(), I_UY_Fdu_Productos.Table_Name)
			.getPO(getUY_Fdu_Productos_ID(), get_TrxName());	}

	/** Set UY_Fdu_Productos.
		@param UY_Fdu_Productos_ID UY_Fdu_Productos	  */
	public void setUY_Fdu_Productos_ID (int UY_Fdu_Productos_ID)
	{
		if (UY_Fdu_Productos_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Productos_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Productos_ID, Integer.valueOf(UY_Fdu_Productos_ID));
	}

	/** Get UY_Fdu_Productos.
		@return UY_Fdu_Productos	  */
	public int getUY_Fdu_Productos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Productos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}