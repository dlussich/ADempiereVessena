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
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_CFE_ProviderConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_CFE_ProviderConfig 
{

    /** TableName=UY_CFE_ProviderConfig */
    public static final String Table_Name = "UY_CFE_ProviderConfig";

    /** AD_Table_ID=1000892 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name catchExceptions */
    public static final String COLUMNNAME_catchExceptions = "catchExceptions";

	/** Set Capturar Excepciones	  */
	public void setcatchExceptions (boolean catchExceptions);

	/** Get Capturar Excepciones	  */
	public boolean iscatchExceptions();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name InvoicyActionRecepDoc */
    public static final String COLUMNNAME_InvoicyActionRecepDoc = "InvoicyActionRecepDoc";

	/** Set Invoicy Accion Recepcion Documento	  */
	public void setInvoicyActionRecepDoc (String InvoicyActionRecepDoc);

	/** Get Invoicy Accion Recepcion Documento	  */
	public String getInvoicyActionRecepDoc();

    /** Column name InvoicyEmpPK */
    public static final String COLUMNNAME_InvoicyEmpPK = "InvoicyEmpPK";

	/** Set Invoicy EmpPK OpenUp	  */
	public void setInvoicyEmpPK (String InvoicyEmpPK);

	/** Get Invoicy EmpPK OpenUp	  */
	public String getInvoicyEmpPK();

    /** Column name InvoicyEndpoint */
    public static final String COLUMNNAME_InvoicyEndpoint = "InvoicyEndpoint";

	/** Set Invoicy Endpoint	  */
	public void setInvoicyEndpoint (String InvoicyEndpoint);

	/** Get Invoicy Endpoint	  */
	public String getInvoicyEndpoint();

    /** Column name InvoicyMethodRecepDoc */
    public static final String COLUMNNAME_InvoicyMethodRecepDoc = "InvoicyMethodRecepDoc";

	/** Set Invoicy Metodo Recepcion Documento	  */
	public void setInvoicyMethodRecepDoc (String InvoicyMethodRecepDoc);

	/** Get Invoicy Metodo Recepcion Documento	  */
	public String getInvoicyMethodRecepDoc();

    /** Column name InvoicyNamespaceRecepDoc */
    public static final String COLUMNNAME_InvoicyNamespaceRecepDoc = "InvoicyNamespaceRecepDoc";

	/** Set Invoicy Namespace Recepcion Documento	  */
	public void setInvoicyNamespaceRecepDoc (String InvoicyNamespaceRecepDoc);

	/** Get Invoicy Namespace Recepcion Documento	  */
	public String getInvoicyNamespaceRecepDoc();

    /** Column name InvoicyParameterInRecepDoc */
    public static final String COLUMNNAME_InvoicyParameterInRecepDoc = "InvoicyParameterInRecepDoc";

	/** Set Invoicy Parametro Entrada Recepcion Documento	  */
	public void setInvoicyParameterInRecepDoc (String InvoicyParameterInRecepDoc);

	/** Get Invoicy Parametro Entrada Recepcion Documento	  */
	public String getInvoicyParameterInRecepDoc();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name ProviderAgent */
    public static final String COLUMNNAME_ProviderAgent = "ProviderAgent";

	/** Set ProviderAgent	  */
	public void setProviderAgent (String ProviderAgent);

	/** Get ProviderAgent	  */
	public String getProviderAgent();

    /** Column name SistecoActionConsDoc */
    public static final String COLUMNNAME_SistecoActionConsDoc = "SistecoActionConsDoc";

	/** Set Sisteco Accion Consulta Documento	  */
	public void setSistecoActionConsDoc (String SistecoActionConsDoc);

	/** Get Sisteco Accion Consulta Documento	  */
	public String getSistecoActionConsDoc();

    /** Column name SistecoActionRecepDoc */
    public static final String COLUMNNAME_SistecoActionRecepDoc = "SistecoActionRecepDoc";

	/** Set Sisteco Accion Recepcion Documento	  */
	public void setSistecoActionRecepDoc (String SistecoActionRecepDoc);

	/** Get Sisteco Accion Recepcion Documento	  */
	public String getSistecoActionRecepDoc();

    /** Column name SistecoEndpoint */
    public static final String COLUMNNAME_SistecoEndpoint = "SistecoEndpoint";

	/** Set Endpoint Sisteco	  */
	public void setSistecoEndpoint (String SistecoEndpoint);

	/** Get Endpoint Sisteco	  */
	public String getSistecoEndpoint();

    /** Column name SistecoMethodConsDoc */
    public static final String COLUMNNAME_SistecoMethodConsDoc = "SistecoMethodConsDoc";

	/** Set Sisteco Metodo Consulta Documento	  */
	public void setSistecoMethodConsDoc (String SistecoMethodConsDoc);

	/** Get Sisteco Metodo Consulta Documento	  */
	public String getSistecoMethodConsDoc();

    /** Column name SistecoMethodRecepDoc */
    public static final String COLUMNNAME_SistecoMethodRecepDoc = "SistecoMethodRecepDoc";

	/** Set Sisteco Metodo Recepcion Documento	  */
	public void setSistecoMethodRecepDoc (String SistecoMethodRecepDoc);

	/** Get Sisteco Metodo Recepcion Documento	  */
	public String getSistecoMethodRecepDoc();

    /** Column name SistecoNamespaceConsDoc */
    public static final String COLUMNNAME_SistecoNamespaceConsDoc = "SistecoNamespaceConsDoc";

	/** Set Sisteco Namespace Consulta Documento	  */
	public void setSistecoNamespaceConsDoc (String SistecoNamespaceConsDoc);

	/** Get Sisteco Namespace Consulta Documento	  */
	public String getSistecoNamespaceConsDoc();

    /** Column name SistecoNamespaceRecepDoc */
    public static final String COLUMNNAME_SistecoNamespaceRecepDoc = "SistecoNamespaceRecepDoc";

	/** Set Sisteco Namespace Recepcion Documento	  */
	public void setSistecoNamespaceRecepDoc (String SistecoNamespaceRecepDoc);

	/** Get Sisteco Namespace Recepcion Documento	  */
	public String getSistecoNamespaceRecepDoc();

    /** Column name SistecoParameterInConsDoc */
    public static final String COLUMNNAME_SistecoParameterInConsDoc = "SistecoParameterInConsDoc";

	/** Set Sisteco Parametro Entrada Consulta Documento	  */
	public void setSistecoParameterInConsDoc (String SistecoParameterInConsDoc);

	/** Get Sisteco Parametro Entrada Consulta Documento	  */
	public String getSistecoParameterInConsDoc();

    /** Column name SistecoParameterInRecepDoc */
    public static final String COLUMNNAME_SistecoParameterInRecepDoc = "SistecoParameterInRecepDoc";

	/** Set Sisteco Parametro Entrada Recepcion Documento	  */
	public void setSistecoParameterInRecepDoc (String SistecoParameterInRecepDoc);

	/** Get Sisteco Parametro Entrada Recepcion Documento	  */
	public String getSistecoParameterInRecepDoc();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UY_CFE_ProviderConfig_ID */
    public static final String COLUMNNAME_UY_CFE_ProviderConfig_ID = "UY_CFE_ProviderConfig_ID";

	/** Set UY_CFE_ProviderConfig	  */
	public void setUY_CFE_ProviderConfig_ID (int UY_CFE_ProviderConfig_ID);

	/** Get UY_CFE_ProviderConfig	  */
	public int getUY_CFE_ProviderConfig_ID();
}
