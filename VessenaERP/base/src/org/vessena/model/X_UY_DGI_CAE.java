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

/** Generated Model for UY_DGI_CAE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DGI_CAE extends PO implements I_UY_DGI_CAE, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150603L;

    /** Standard Constructor */
    public X_UY_DGI_CAE (Properties ctx, int UY_DGI_CAE_ID, String trxName)
    {
      super (ctx, UY_DGI_CAE_ID, trxName);
      /** if (UY_DGI_CAE_ID == 0)
        {
			setdocumentoNumero (Env.ZERO);
			setfechaVencimiento (new Timestamp( System.currentTimeMillis() ));
			setnumeroFin (Env.ZERO);
			setnumeroInicio (Env.ZERO);
			setUY_DGI_CAE_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DGI_CAE (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DGI_CAE[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set documentoNumero.
		@param documentoNumero documentoNumero	  */
	public void setdocumentoNumero (BigDecimal documentoNumero)
	{
		set_Value (COLUMNNAME_documentoNumero, documentoNumero);
	}

	/** Get documentoNumero.
		@return documentoNumero	  */
	public BigDecimal getdocumentoNumero () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_documentoNumero);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set fechaVencimiento.
		@param fechaVencimiento fechaVencimiento	  */
	public void setfechaVencimiento (Timestamp fechaVencimiento)
	{
		set_Value (COLUMNNAME_fechaVencimiento, fechaVencimiento);
	}

	/** Get fechaVencimiento.
		@return fechaVencimiento	  */
	public Timestamp getfechaVencimiento () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechaVencimiento);
	}

	/** Set numeroFin.
		@param numeroFin numeroFin	  */
	public void setnumeroFin (BigDecimal numeroFin)
	{
		set_Value (COLUMNNAME_numeroFin, numeroFin);
	}

	/** Get numeroFin.
		@return numeroFin	  */
	public BigDecimal getnumeroFin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_numeroFin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set numeroInicio.
		@param numeroInicio numeroInicio	  */
	public void setnumeroInicio (BigDecimal numeroInicio)
	{
		set_Value (COLUMNNAME_numeroInicio, numeroInicio);
	}

	/** Get numeroInicio.
		@return numeroInicio	  */
	public BigDecimal getnumeroInicio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_numeroInicio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_DGI_CAE.
		@param UY_DGI_CAE_ID UY_DGI_CAE	  */
	public void setUY_DGI_CAE_ID (int UY_DGI_CAE_ID)
	{
		if (UY_DGI_CAE_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DGI_CAE_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DGI_CAE_ID, Integer.valueOf(UY_DGI_CAE_ID));
	}

	/** Get UY_DGI_CAE.
		@return UY_DGI_CAE	  */
	public int getUY_DGI_CAE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DGI_CAE_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}