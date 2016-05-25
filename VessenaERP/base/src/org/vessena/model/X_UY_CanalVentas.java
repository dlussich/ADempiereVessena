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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_CanalVentas
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CanalVentas extends PO implements I_UY_CanalVentas, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CanalVentas (Properties ctx, int UY_CanalVentas_ID, String trxName)
    {
      super (ctx, UY_CanalVentas_ID, trxName);
      /** if (UY_CanalVentas_ID == 0)
        {
			setUY_CanalVentas_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CanalVentas (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CanalVentas[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set descripcion.
		@param descripcion descripcion	  */
	public void setdescripcion (String descripcion)
	{
		set_Value (COLUMNNAME_descripcion, descripcion);
	}

	/** Get descripcion.
		@return descripcion	  */
	public String getdescripcion () 
	{
		return (String)get_Value(COLUMNNAME_descripcion);
	}

	/** Set nombre.
		@param nombre nombre	  */
	public void setnombre (String nombre)
	{
		set_Value (COLUMNNAME_nombre, nombre);
	}

	/** Get nombre.
		@return nombre	  */
	public String getnombre () 
	{
		return (String)get_Value(COLUMNNAME_nombre);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getnombre());
    }

	/** Set UY_CanalVentas.
		@param UY_CanalVentas_ID UY_CanalVentas	  */
	public void setUY_CanalVentas_ID (int UY_CanalVentas_ID)
	{
		if (UY_CanalVentas_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CanalVentas_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CanalVentas_ID, Integer.valueOf(UY_CanalVentas_ID));
	}

	/** Get UY_CanalVentas.
		@return UY_CanalVentas	  */
	public int getUY_CanalVentas_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CanalVentas_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_prioridadgenent.
		@param uy_prioridadgenent uy_prioridadgenent	  */
	public void setuy_prioridadgenent (String uy_prioridadgenent)
	{
		set_Value (COLUMNNAME_uy_prioridadgenent, uy_prioridadgenent);
	}

	/** Get uy_prioridadgenent.
		@return uy_prioridadgenent	  */
	public String getuy_prioridadgenent () 
	{
		return (String)get_Value(COLUMNNAME_uy_prioridadgenent);
	}
}