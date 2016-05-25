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

/** Generated Model for UY_GruposClientesAsociados
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_GruposClientesAsociados extends PO implements I_UY_GruposClientesAsociados, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_GruposClientesAsociados (Properties ctx, int UY_GruposClientesAsociados_ID, String trxName)
    {
      super (ctx, UY_GruposClientesAsociados_ID, trxName);
      /** if (UY_GruposClientesAsociados_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_GruposClientes_ID (0);
			setUY_GruposClientesAsociados_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_GruposClientesAsociados (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_GruposClientesAsociados[")
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

	/** Set UY_GruposClientes.
		@param UY_GruposClientes_ID UY_GruposClientes	  */
	public void setUY_GruposClientes_ID (int UY_GruposClientes_ID)
	{
		if (UY_GruposClientes_ID < 1) 
			set_Value (COLUMNNAME_UY_GruposClientes_ID, null);
		else 
			set_Value (COLUMNNAME_UY_GruposClientes_ID, Integer.valueOf(UY_GruposClientes_ID));
	}

	/** Get UY_GruposClientes.
		@return UY_GruposClientes	  */
	public int getUY_GruposClientes_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_GruposClientes_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_GruposClientesAsociados.
		@param UY_GruposClientesAsociados_ID UY_GruposClientesAsociados	  */
	public void setUY_GruposClientesAsociados_ID (int UY_GruposClientesAsociados_ID)
	{
		if (UY_GruposClientesAsociados_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_GruposClientesAsociados_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_GruposClientesAsociados_ID, Integer.valueOf(UY_GruposClientesAsociados_ID));
	}

	/** Get UY_GruposClientesAsociados.
		@return UY_GruposClientesAsociados	  */
	public int getUY_GruposClientesAsociados_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_GruposClientesAsociados_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}