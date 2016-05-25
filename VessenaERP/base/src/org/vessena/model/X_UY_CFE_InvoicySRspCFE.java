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

/** Generated Model for UY_CFE_InvoicySRspCFE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_InvoicySRspCFE extends PO implements I_UY_CFE_InvoicySRspCFE, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160218L;

    /** Standard Constructor */
    public X_UY_CFE_InvoicySRspCFE (Properties ctx, int UY_CFE_InvoicySRspCFE_ID, String trxName)
    {
      super (ctx, UY_CFE_InvoicySRspCFE_ID, trxName);
      /** if (UY_CFE_InvoicySRspCFE_ID == 0)
        {
			setUY_CFE_InvoicySRspCFE_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_InvoicySRspCFE (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_InvoicySRspCFE[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Codigo de Seguridad CFE.
		@param CFECodigoSeguridad Codigo de Seguridad CFE	  */
	public void setCFECodigoSeguridad (String CFECodigoSeguridad)
	{
		set_Value (COLUMNNAME_CFECodigoSeguridad, CFECodigoSeguridad);
	}

	/** Get Codigo de Seguridad CFE.
		@return Codigo de Seguridad CFE	  */
	public String getCFECodigoSeguridad () 
	{
		return (String)get_Value(COLUMNNAME_CFECodigoSeguridad);
	}

	/** Set Estado Acuse CFE.
		@param CFEEstadoAcuse Estado Acuse CFE	  */
	public void setCFEEstadoAcuse (BigDecimal CFEEstadoAcuse)
	{
		set_Value (COLUMNNAME_CFEEstadoAcuse, CFEEstadoAcuse);
	}

	/** Get Estado Acuse CFE.
		@return Estado Acuse CFE	  */
	public BigDecimal getCFEEstadoAcuse () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFEEstadoAcuse);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Hash CFE.
		@param CFEHASH Hash CFE	  */
	public void setCFEHASH (String CFEHASH)
	{
		set_Value (COLUMNNAME_CFEHASH, CFEHASH);
	}

	/** Get Hash CFE.
		@return Hash CFE	  */
	public String getCFEHASH () 
	{
		return (String)get_Value(COLUMNNAME_CFEHASH);
	}

	/** Set Codigo de Mensaje CFE.
		@param CFEMsgCod Codigo de Mensaje CFE	  */
	public void setCFEMsgCod (BigDecimal CFEMsgCod)
	{
		set_Value (COLUMNNAME_CFEMsgCod, CFEMsgCod);
	}

	/** Get Codigo de Mensaje CFE.
		@return Codigo de Mensaje CFE	  */
	public BigDecimal getCFEMsgCod () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFEMsgCod);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Descripcion Mensaje CFE.
		@param CFEMsgDsc Descripcion Mensaje CFE	  */
	public void setCFEMsgDsc (String CFEMsgDsc)
	{
		set_Value (COLUMNNAME_CFEMsgDsc, CFEMsgDsc);
	}

	/** Get Descripcion Mensaje CFE.
		@return Descripcion Mensaje CFE	  */
	public String getCFEMsgDsc () 
	{
		return (String)get_Value(COLUMNNAME_CFEMsgDsc);
	}

	/** Set Numero de Serie CFE.
		@param CFENro Numero de Serie CFE	  */
	public void setCFENro (BigDecimal CFENro)
	{
		set_Value (COLUMNNAME_CFENro, CFENro);
	}

	/** Get Numero de Serie CFE.
		@return Numero de Serie CFE	  */
	public BigDecimal getCFENro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFENro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Numero de Referencia CFE.
		@param CFENumReferencia Numero de Referencia CFE	  */
	public void setCFENumReferencia (BigDecimal CFENumReferencia)
	{
		set_Value (COLUMNNAME_CFENumReferencia, CFENumReferencia);
	}

	/** Get Numero de Referencia CFE.
		@return Numero de Referencia CFE	  */
	public BigDecimal getCFENumReferencia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFENumReferencia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Representacion Impresa CFE.
		@param CFERepImpressa Representacion Impresa CFE	  */
	public void setCFERepImpressa (String CFERepImpressa)
	{
		set_Value (COLUMNNAME_CFERepImpressa, CFERepImpressa);
	}

	/** Get Representacion Impresa CFE.
		@return Representacion Impresa CFE	  */
	public String getCFERepImpressa () 
	{
		return (String)get_Value(COLUMNNAME_CFERepImpressa);
	}

	/** Set Serie de CFE.
		@param CFESerie Serie de CFE	  */
	public void setCFESerie (String CFESerie)
	{
		set_Value (COLUMNNAME_CFESerie, CFESerie);
	}

	/** Get Serie de CFE.
		@return Serie de CFE	  */
	public String getCFESerie () 
	{
		return (String)get_Value(COLUMNNAME_CFESerie);
	}

	/** Set Status CFE.
		@param CFEStatus Status CFE	  */
	public void setCFEStatus (BigDecimal CFEStatus)
	{
		set_Value (COLUMNNAME_CFEStatus, CFEStatus);
	}

	/** Get Status CFE.
		@return Status CFE	  */
	public BigDecimal getCFEStatus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFEStatus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tipo de CFE.
		@param CFETipo Tipo de CFE	  */
	public void setCFETipo (BigDecimal CFETipo)
	{
		set_Value (COLUMNNAME_CFETipo, CFETipo);
	}

	/** Get Tipo de CFE.
		@return Tipo de CFE	  */
	public BigDecimal getCFETipo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFETipo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_CFE_DocCFE getUY_CFE_DocCFE() throws RuntimeException
    {
		return (I_UY_CFE_DocCFE)MTable.get(getCtx(), I_UY_CFE_DocCFE.Table_Name)
			.getPO(getUY_CFE_DocCFE_ID(), get_TrxName());	}

	/** Set UY_CFE_DocCFE.
		@param UY_CFE_DocCFE_ID UY_CFE_DocCFE	  */
	public void setUY_CFE_DocCFE_ID (int UY_CFE_DocCFE_ID)
	{
		if (UY_CFE_DocCFE_ID < 1) 
			set_Value (COLUMNNAME_UY_CFE_DocCFE_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CFE_DocCFE_ID, Integer.valueOf(UY_CFE_DocCFE_ID));
	}

	/** Get UY_CFE_DocCFE.
		@return UY_CFE_DocCFE	  */
	public int getUY_CFE_DocCFE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_DocCFE_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CFE_InvoicySRspCFE.
		@param UY_CFE_InvoicySRspCFE_ID UY_CFE_InvoicySRspCFE	  */
	public void setUY_CFE_InvoicySRspCFE_ID (int UY_CFE_InvoicySRspCFE_ID)
	{
		if (UY_CFE_InvoicySRspCFE_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InvoicySRspCFE_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InvoicySRspCFE_ID, Integer.valueOf(UY_CFE_InvoicySRspCFE_ID));
	}

	/** Get UY_CFE_InvoicySRspCFE.
		@return UY_CFE_InvoicySRspCFE	  */
	public int getUY_CFE_InvoicySRspCFE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InvoicySRspCFE_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}