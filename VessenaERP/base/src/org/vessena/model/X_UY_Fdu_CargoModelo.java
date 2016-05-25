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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_Fdu_CargoModelo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Fdu_CargoModelo extends PO implements I_UY_Fdu_CargoModelo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131120L;

    /** Standard Constructor */
    public X_UY_Fdu_CargoModelo (Properties ctx, int UY_Fdu_CargoModelo_ID, String trxName)
    {
      super (ctx, UY_Fdu_CargoModelo_ID, trxName);
      /** if (UY_Fdu_CargoModelo_ID == 0)
        {
			setUY_Fdu_Cargo_ID (0);
			setUY_Fdu_CargoModelo_ID (0);
			setUY_Fdu_ModeloLiquidacion_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_CargoModelo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_CargoModelo[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set UY_Fdu_CargoModelo.
		@param UY_Fdu_CargoModelo_ID UY_Fdu_CargoModelo	  */
	public void setUY_Fdu_CargoModelo_ID (int UY_Fdu_CargoModelo_ID)
	{
		if (UY_Fdu_CargoModelo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CargoModelo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CargoModelo_ID, Integer.valueOf(UY_Fdu_CargoModelo_ID));
	}

	/** Get UY_Fdu_CargoModelo.
		@return UY_Fdu_CargoModelo	  */
	public int getUY_Fdu_CargoModelo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_CargoModelo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_ModeloLiquidacion getUY_Fdu_ModeloLiquidacion() throws RuntimeException
    {
		return (I_UY_Fdu_ModeloLiquidacion)MTable.get(getCtx(), I_UY_Fdu_ModeloLiquidacion.Table_Name)
			.getPO(getUY_Fdu_ModeloLiquidacion_ID(), get_TrxName());	}

	/** Set UY_Fdu_ModeloLiquidacion.
		@param UY_Fdu_ModeloLiquidacion_ID UY_Fdu_ModeloLiquidacion	  */
	public void setUY_Fdu_ModeloLiquidacion_ID (int UY_Fdu_ModeloLiquidacion_ID)
	{
		if (UY_Fdu_ModeloLiquidacion_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_ModeloLiquidacion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_ModeloLiquidacion_ID, Integer.valueOf(UY_Fdu_ModeloLiquidacion_ID));
	}

	/** Get UY_Fdu_ModeloLiquidacion.
		@return UY_Fdu_ModeloLiquidacion	  */
	public int getUY_Fdu_ModeloLiquidacion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_ModeloLiquidacion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}