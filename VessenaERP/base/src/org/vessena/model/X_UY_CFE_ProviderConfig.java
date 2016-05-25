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

/** Generated Model for UY_CFE_ProviderConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_ProviderConfig extends PO implements I_UY_CFE_ProviderConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160226L;

    /** Standard Constructor */
    public X_UY_CFE_ProviderConfig (Properties ctx, int UY_CFE_ProviderConfig_ID, String trxName)
    {
      super (ctx, UY_CFE_ProviderConfig_ID, trxName);
      /** if (UY_CFE_ProviderConfig_ID == 0)
        {
			setUY_CFE_ProviderConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_ProviderConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_ProviderConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Capturar Excepciones.
		@param catchExceptions Capturar Excepciones	  */
	public void setcatchExceptions (boolean catchExceptions)
	{
		set_Value (COLUMNNAME_catchExceptions, Boolean.valueOf(catchExceptions));
	}

	/** Get Capturar Excepciones.
		@return Capturar Excepciones	  */
	public boolean iscatchExceptions () 
	{
		Object oo = get_Value(COLUMNNAME_catchExceptions);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoicy Accion Recepcion Documento.
		@param InvoicyActionRecepDoc Invoicy Accion Recepcion Documento	  */
	public void setInvoicyActionRecepDoc (String InvoicyActionRecepDoc)
	{
		set_Value (COLUMNNAME_InvoicyActionRecepDoc, InvoicyActionRecepDoc);
	}

	/** Get Invoicy Accion Recepcion Documento.
		@return Invoicy Accion Recepcion Documento	  */
	public String getInvoicyActionRecepDoc () 
	{
		return (String)get_Value(COLUMNNAME_InvoicyActionRecepDoc);
	}

	/** Set Invoicy EmpPK OpenUp.
		@param InvoicyEmpPK Invoicy EmpPK OpenUp	  */
	public void setInvoicyEmpPK (String InvoicyEmpPK)
	{
		set_Value (COLUMNNAME_InvoicyEmpPK, InvoicyEmpPK);
	}

	/** Get Invoicy EmpPK OpenUp.
		@return Invoicy EmpPK OpenUp	  */
	public String getInvoicyEmpPK () 
	{
		return (String)get_Value(COLUMNNAME_InvoicyEmpPK);
	}

	/** Set Invoicy Endpoint.
		@param InvoicyEndpoint Invoicy Endpoint	  */
	public void setInvoicyEndpoint (String InvoicyEndpoint)
	{
		set_Value (COLUMNNAME_InvoicyEndpoint, InvoicyEndpoint);
	}

	/** Get Invoicy Endpoint.
		@return Invoicy Endpoint	  */
	public String getInvoicyEndpoint () 
	{
		return (String)get_Value(COLUMNNAME_InvoicyEndpoint);
	}

	/** Set Invoicy Metodo Recepcion Documento.
		@param InvoicyMethodRecepDoc Invoicy Metodo Recepcion Documento	  */
	public void setInvoicyMethodRecepDoc (String InvoicyMethodRecepDoc)
	{
		set_Value (COLUMNNAME_InvoicyMethodRecepDoc, InvoicyMethodRecepDoc);
	}

	/** Get Invoicy Metodo Recepcion Documento.
		@return Invoicy Metodo Recepcion Documento	  */
	public String getInvoicyMethodRecepDoc () 
	{
		return (String)get_Value(COLUMNNAME_InvoicyMethodRecepDoc);
	}

	/** Set Invoicy Namespace Recepcion Documento.
		@param InvoicyNamespaceRecepDoc Invoicy Namespace Recepcion Documento	  */
	public void setInvoicyNamespaceRecepDoc (String InvoicyNamespaceRecepDoc)
	{
		set_Value (COLUMNNAME_InvoicyNamespaceRecepDoc, InvoicyNamespaceRecepDoc);
	}

	/** Get Invoicy Namespace Recepcion Documento.
		@return Invoicy Namespace Recepcion Documento	  */
	public String getInvoicyNamespaceRecepDoc () 
	{
		return (String)get_Value(COLUMNNAME_InvoicyNamespaceRecepDoc);
	}

	/** Set Invoicy Parametro Entrada Recepcion Documento.
		@param InvoicyParameterInRecepDoc Invoicy Parametro Entrada Recepcion Documento	  */
	public void setInvoicyParameterInRecepDoc (String InvoicyParameterInRecepDoc)
	{
		set_Value (COLUMNNAME_InvoicyParameterInRecepDoc, InvoicyParameterInRecepDoc);
	}

	/** Get Invoicy Parametro Entrada Recepcion Documento.
		@return Invoicy Parametro Entrada Recepcion Documento	  */
	public String getInvoicyParameterInRecepDoc () 
	{
		return (String)get_Value(COLUMNNAME_InvoicyParameterInRecepDoc);
	}

	/** ProviderAgent AD_Reference_ID=1000501 */
	public static final int PROVIDERAGENT_AD_Reference_ID=1000501;
	/** InvoiCy = INVOICY */
	public static final String PROVIDERAGENT_InvoiCy = "INVOICY";
	/** OpenUp = OPENUP */
	public static final String PROVIDERAGENT_OpenUp = "OPENUP";
	/** Sisteco = SISTECO */
	public static final String PROVIDERAGENT_Sisteco = "SISTECO";
	/** Set ProviderAgent.
		@param ProviderAgent ProviderAgent	  */
	public void setProviderAgent (String ProviderAgent)
	{

		set_Value (COLUMNNAME_ProviderAgent, ProviderAgent);
	}

	/** Get ProviderAgent.
		@return ProviderAgent	  */
	public String getProviderAgent () 
	{
		return (String)get_Value(COLUMNNAME_ProviderAgent);
	}

	/** Set Sisteco Accion Consulta Documento.
		@param SistecoActionConsDoc Sisteco Accion Consulta Documento	  */
	public void setSistecoActionConsDoc (String SistecoActionConsDoc)
	{
		set_Value (COLUMNNAME_SistecoActionConsDoc, SistecoActionConsDoc);
	}

	/** Get Sisteco Accion Consulta Documento.
		@return Sisteco Accion Consulta Documento	  */
	public String getSistecoActionConsDoc () 
	{
		return (String)get_Value(COLUMNNAME_SistecoActionConsDoc);
	}

	/** Set Sisteco Accion Recepcion Documento.
		@param SistecoActionRecepDoc Sisteco Accion Recepcion Documento	  */
	public void setSistecoActionRecepDoc (String SistecoActionRecepDoc)
	{
		set_Value (COLUMNNAME_SistecoActionRecepDoc, SistecoActionRecepDoc);
	}

	/** Get Sisteco Accion Recepcion Documento.
		@return Sisteco Accion Recepcion Documento	  */
	public String getSistecoActionRecepDoc () 
	{
		return (String)get_Value(COLUMNNAME_SistecoActionRecepDoc);
	}

	/** Set Endpoint Sisteco.
		@param SistecoEndpoint Endpoint Sisteco	  */
	public void setSistecoEndpoint (String SistecoEndpoint)
	{
		set_Value (COLUMNNAME_SistecoEndpoint, SistecoEndpoint);
	}

	/** Get Endpoint Sisteco.
		@return Endpoint Sisteco	  */
	public String getSistecoEndpoint () 
	{
		return (String)get_Value(COLUMNNAME_SistecoEndpoint);
	}

	/** Set Sisteco Metodo Consulta Documento.
		@param SistecoMethodConsDoc Sisteco Metodo Consulta Documento	  */
	public void setSistecoMethodConsDoc (String SistecoMethodConsDoc)
	{
		set_Value (COLUMNNAME_SistecoMethodConsDoc, SistecoMethodConsDoc);
	}

	/** Get Sisteco Metodo Consulta Documento.
		@return Sisteco Metodo Consulta Documento	  */
	public String getSistecoMethodConsDoc () 
	{
		return (String)get_Value(COLUMNNAME_SistecoMethodConsDoc);
	}

	/** Set Sisteco Metodo Recepcion Documento.
		@param SistecoMethodRecepDoc Sisteco Metodo Recepcion Documento	  */
	public void setSistecoMethodRecepDoc (String SistecoMethodRecepDoc)
	{
		set_Value (COLUMNNAME_SistecoMethodRecepDoc, SistecoMethodRecepDoc);
	}

	/** Get Sisteco Metodo Recepcion Documento.
		@return Sisteco Metodo Recepcion Documento	  */
	public String getSistecoMethodRecepDoc () 
	{
		return (String)get_Value(COLUMNNAME_SistecoMethodRecepDoc);
	}

	/** Set Sisteco Namespace Consulta Documento.
		@param SistecoNamespaceConsDoc Sisteco Namespace Consulta Documento	  */
	public void setSistecoNamespaceConsDoc (String SistecoNamespaceConsDoc)
	{
		set_Value (COLUMNNAME_SistecoNamespaceConsDoc, SistecoNamespaceConsDoc);
	}

	/** Get Sisteco Namespace Consulta Documento.
		@return Sisteco Namespace Consulta Documento	  */
	public String getSistecoNamespaceConsDoc () 
	{
		return (String)get_Value(COLUMNNAME_SistecoNamespaceConsDoc);
	}

	/** Set Sisteco Namespace Recepcion Documento.
		@param SistecoNamespaceRecepDoc Sisteco Namespace Recepcion Documento	  */
	public void setSistecoNamespaceRecepDoc (String SistecoNamespaceRecepDoc)
	{
		set_Value (COLUMNNAME_SistecoNamespaceRecepDoc, SistecoNamespaceRecepDoc);
	}

	/** Get Sisteco Namespace Recepcion Documento.
		@return Sisteco Namespace Recepcion Documento	  */
	public String getSistecoNamespaceRecepDoc () 
	{
		return (String)get_Value(COLUMNNAME_SistecoNamespaceRecepDoc);
	}

	/** Set Sisteco Parametro Entrada Consulta Documento.
		@param SistecoParameterInConsDoc Sisteco Parametro Entrada Consulta Documento	  */
	public void setSistecoParameterInConsDoc (String SistecoParameterInConsDoc)
	{
		set_Value (COLUMNNAME_SistecoParameterInConsDoc, SistecoParameterInConsDoc);
	}

	/** Get Sisteco Parametro Entrada Consulta Documento.
		@return Sisteco Parametro Entrada Consulta Documento	  */
	public String getSistecoParameterInConsDoc () 
	{
		return (String)get_Value(COLUMNNAME_SistecoParameterInConsDoc);
	}

	/** Set Sisteco Parametro Entrada Recepcion Documento.
		@param SistecoParameterInRecepDoc Sisteco Parametro Entrada Recepcion Documento	  */
	public void setSistecoParameterInRecepDoc (String SistecoParameterInRecepDoc)
	{
		set_Value (COLUMNNAME_SistecoParameterInRecepDoc, SistecoParameterInRecepDoc);
	}

	/** Get Sisteco Parametro Entrada Recepcion Documento.
		@return Sisteco Parametro Entrada Recepcion Documento	  */
	public String getSistecoParameterInRecepDoc () 
	{
		return (String)get_Value(COLUMNNAME_SistecoParameterInRecepDoc);
	}

	/** Set UY_CFE_ProviderConfig.
		@param UY_CFE_ProviderConfig_ID UY_CFE_ProviderConfig	  */
	public void setUY_CFE_ProviderConfig_ID (int UY_CFE_ProviderConfig_ID)
	{
		if (UY_CFE_ProviderConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_ProviderConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_ProviderConfig_ID, Integer.valueOf(UY_CFE_ProviderConfig_ID));
	}

	/** Get UY_CFE_ProviderConfig.
		@return UY_CFE_ProviderConfig	  */
	public int getUY_CFE_ProviderConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_ProviderConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}