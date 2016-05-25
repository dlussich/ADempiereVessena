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

/** Generated Interface for UY_CFE_InvoicySRspCFE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_CFE_InvoicySRspCFE 
{

    /** TableName=UY_CFE_InvoicySRspCFE */
    public static final String Table_Name = "UY_CFE_InvoicySRspCFE";

    /** AD_Table_ID=1000889 */
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

    /** Column name CFECodigoSeguridad */
    public static final String COLUMNNAME_CFECodigoSeguridad = "CFECodigoSeguridad";

	/** Set Codigo de Seguridad CFE	  */
	public void setCFECodigoSeguridad (String CFECodigoSeguridad);

	/** Get Codigo de Seguridad CFE	  */
	public String getCFECodigoSeguridad();

    /** Column name CFEEstadoAcuse */
    public static final String COLUMNNAME_CFEEstadoAcuse = "CFEEstadoAcuse";

	/** Set Estado Acuse CFE	  */
	public void setCFEEstadoAcuse (BigDecimal CFEEstadoAcuse);

	/** Get Estado Acuse CFE	  */
	public BigDecimal getCFEEstadoAcuse();

    /** Column name CFEHASH */
    public static final String COLUMNNAME_CFEHASH = "CFEHASH";

	/** Set Hash CFE	  */
	public void setCFEHASH (String CFEHASH);

	/** Get Hash CFE	  */
	public String getCFEHASH();

    /** Column name CFEMsgCod */
    public static final String COLUMNNAME_CFEMsgCod = "CFEMsgCod";

	/** Set Codigo de Mensaje CFE	  */
	public void setCFEMsgCod (BigDecimal CFEMsgCod);

	/** Get Codigo de Mensaje CFE	  */
	public BigDecimal getCFEMsgCod();

    /** Column name CFEMsgDsc */
    public static final String COLUMNNAME_CFEMsgDsc = "CFEMsgDsc";

	/** Set Descripcion Mensaje CFE	  */
	public void setCFEMsgDsc (String CFEMsgDsc);

	/** Get Descripcion Mensaje CFE	  */
	public String getCFEMsgDsc();

    /** Column name CFENro */
    public static final String COLUMNNAME_CFENro = "CFENro";

	/** Set Numero de Serie CFE	  */
	public void setCFENro (BigDecimal CFENro);

	/** Get Numero de Serie CFE	  */
	public BigDecimal getCFENro();

    /** Column name CFENumReferencia */
    public static final String COLUMNNAME_CFENumReferencia = "CFENumReferencia";

	/** Set Numero de Referencia CFE	  */
	public void setCFENumReferencia (BigDecimal CFENumReferencia);

	/** Get Numero de Referencia CFE	  */
	public BigDecimal getCFENumReferencia();

    /** Column name CFERepImpressa */
    public static final String COLUMNNAME_CFERepImpressa = "CFERepImpressa";

	/** Set Representacion Impresa CFE	  */
	public void setCFERepImpressa (String CFERepImpressa);

	/** Get Representacion Impresa CFE	  */
	public String getCFERepImpressa();

    /** Column name CFESerie */
    public static final String COLUMNNAME_CFESerie = "CFESerie";

	/** Set Serie de CFE	  */
	public void setCFESerie (String CFESerie);

	/** Get Serie de CFE	  */
	public String getCFESerie();

    /** Column name CFEStatus */
    public static final String COLUMNNAME_CFEStatus = "CFEStatus";

	/** Set Status CFE	  */
	public void setCFEStatus (BigDecimal CFEStatus);

	/** Get Status CFE	  */
	public BigDecimal getCFEStatus();

    /** Column name CFETipo */
    public static final String COLUMNNAME_CFETipo = "CFETipo";

	/** Set Tipo de CFE	  */
	public void setCFETipo (BigDecimal CFETipo);

	/** Get Tipo de CFE	  */
	public BigDecimal getCFETipo();

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

    /** Column name UY_CFE_DocCFE_ID */
    public static final String COLUMNNAME_UY_CFE_DocCFE_ID = "UY_CFE_DocCFE_ID";

	/** Set UY_CFE_DocCFE	  */
	public void setUY_CFE_DocCFE_ID (int UY_CFE_DocCFE_ID);

	/** Get UY_CFE_DocCFE	  */
	public int getUY_CFE_DocCFE_ID();

	public I_UY_CFE_DocCFE getUY_CFE_DocCFE() throws RuntimeException;

    /** Column name UY_CFE_InvoicySRspCFE_ID */
    public static final String COLUMNNAME_UY_CFE_InvoicySRspCFE_ID = "UY_CFE_InvoicySRspCFE_ID";

	/** Set UY_CFE_InvoicySRspCFE	  */
	public void setUY_CFE_InvoicySRspCFE_ID (int UY_CFE_InvoicySRspCFE_ID);

	/** Get UY_CFE_InvoicySRspCFE	  */
	public int getUY_CFE_InvoicySRspCFE_ID();
}
