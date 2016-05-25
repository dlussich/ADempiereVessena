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

/** Generated Model for UY_CFE_SistecoSRspCFE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_SistecoSRspCFE extends PO implements I_UY_CFE_SistecoSRspCFE, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160219L;

    /** Standard Constructor */
    public X_UY_CFE_SistecoSRspCFE (Properties ctx, int UY_CFE_SistecoSRspCFE_ID, String trxName)
    {
      super (ctx, UY_CFE_SistecoSRspCFE_ID, trxName);
      /** if (UY_CFE_SistecoSRspCFE_ID == 0)
        {
			setUY_CFE_SistecoSRspCFE_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_SistecoSRspCFE (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_SistecoSRspCFE[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Año Resolucion CFE.
		@param CFEAnioResolucion Año Resolucion CFE	  */
	public void setCFEAnioResolucion (BigDecimal CFEAnioResolucion)
	{
		set_Value (COLUMNNAME_CFEAnioResolucion, CFEAnioResolucion);
	}

	/** Get Año Resolucion CFE.
		@return Año Resolucion CFE	  */
	public BigDecimal getCFEAnioResolucion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFEAnioResolucion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ID CAE.
		@param CFECAEID ID CAE	  */
	public void setCFECAEID (BigDecimal CFECAEID)
	{
		set_Value (COLUMNNAME_CFECAEID, CFECAEID);
	}

	/** Get ID CAE.
		@return ID CAE	  */
	public BigDecimal getCFECAEID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFECAEID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Descripcion Mensaje CFE.
		@param CFEDescripcion Descripcion Mensaje CFE	  */
	public void setCFEDescripcion (String CFEDescripcion)
	{
		set_Value (COLUMNNAME_CFEDescripcion, CFEDescripcion);
	}

	/** Get Descripcion Mensaje CFE.
		@return Descripcion Mensaje CFE	  */
	public String getCFEDescripcion () 
	{
		return (String)get_Value(COLUMNNAME_CFEDescripcion);
	}

	/** Set CFE Digest Value.
		@param CFEDigestValue CFE Digest Value	  */
	public void setCFEDigestValue (String CFEDigestValue)
	{
		set_Value (COLUMNNAME_CFEDigestValue, CFEDigestValue);
	}

	/** Get CFE Digest Value.
		@return CFE Digest Value	  */
	public String getCFEDigestValue () 
	{
		return (String)get_Value(COLUMNNAME_CFEDigestValue);
	}

	/** Set Numero de Inicio del CAE.
		@param CFEDNro Numero de Inicio del CAE	  */
	public void setCFEDNro (BigDecimal CFEDNro)
	{
		set_Value (COLUMNNAME_CFEDNro, CFEDNro);
	}

	/** Get Numero de Inicio del CAE.
		@return Numero de Inicio del CAE	  */
	public BigDecimal getCFEDNro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFEDNro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fecha Vencimiento CAE.
		@param CFEFecVenc Fecha Vencimiento CAE	  */
	public void setCFEFecVenc (Timestamp CFEFecVenc)
	{
		set_Value (COLUMNNAME_CFEFecVenc, CFEFecVenc);
	}

	/** Get Fecha Vencimiento CAE.
		@return Fecha Vencimiento CAE	  */
	public Timestamp getCFEFecVenc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CFEFecVenc);
	}

	/** Set Numero de Fin del CAE.
		@param CFEHNro Numero de Fin del CAE	  */
	public void setCFEHNro (BigDecimal CFEHNro)
	{
		set_Value (COLUMNNAME_CFEHNro, CFEHNro);
	}

	/** Get Numero de Fin del CAE.
		@return Numero de Fin del CAE	  */
	public BigDecimal getCFEHNro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFEHNro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Numero CFE.
		@param CFEMro Numero CFE	  */
	public void setCFEMro (BigDecimal CFEMro)
	{
		set_Value (COLUMNNAME_CFEMro, CFEMro);
	}

	/** Get Numero CFE.
		@return Numero CFE	  */
	public BigDecimal getCFEMro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFEMro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Resolucion CFE.
		@param CFEResolucion Resolucion CFE	  */
	public void setCFEResolucion (String CFEResolucion)
	{
		set_Value (COLUMNNAME_CFEResolucion, CFEResolucion);
	}

	/** Get Resolucion CFE.
		@return Resolucion CFE	  */
	public String getCFEResolucion () 
	{
		return (String)get_Value(COLUMNNAME_CFEResolucion);
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
	public void setCFEStatus (String CFEStatus)
	{
		set_Value (COLUMNNAME_CFEStatus, CFEStatus);
	}

	/** Get Status CFE.
		@return Status CFE	  */
	public String getCFEStatus () 
	{
		return (String)get_Value(COLUMNNAME_CFEStatus);
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

	/** Set Fecha Hora CFE.
		@param CFETmstFirma Fecha Hora CFE	  */
	public void setCFETmstFirma (Timestamp CFETmstFirma)
	{
		set_Value (COLUMNNAME_CFETmstFirma, CFETmstFirma);
	}

	/** Get Fecha Hora CFE.
		@return Fecha Hora CFE	  */
	public Timestamp getCFETmstFirma () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CFETmstFirma);
	}

	/** Set URL Documento DGI.
		@param CFEUrlDocumentoDGI URL Documento DGI	  */
	public void setCFEUrlDocumentoDGI (String CFEUrlDocumentoDGI)
	{
		set_Value (COLUMNNAME_CFEUrlDocumentoDGI, CFEUrlDocumentoDGI);
	}

	/** Get URL Documento DGI.
		@return URL Documento DGI	  */
	public String getCFEUrlDocumentoDGI () 
	{
		return (String)get_Value(COLUMNNAME_CFEUrlDocumentoDGI);
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

	/** Set UY_CFE_SistecoSRspCFE.
		@param UY_CFE_SistecoSRspCFE_ID UY_CFE_SistecoSRspCFE	  */
	public void setUY_CFE_SistecoSRspCFE_ID (int UY_CFE_SistecoSRspCFE_ID)
	{
		if (UY_CFE_SistecoSRspCFE_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_SistecoSRspCFE_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_SistecoSRspCFE_ID, Integer.valueOf(UY_CFE_SistecoSRspCFE_ID));
	}

	/** Get UY_CFE_SistecoSRspCFE.
		@return UY_CFE_SistecoSRspCFE	  */
	public int getUY_CFE_SistecoSRspCFE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_SistecoSRspCFE_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}