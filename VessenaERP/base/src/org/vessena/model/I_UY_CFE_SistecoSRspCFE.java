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

/** Generated Interface for UY_CFE_SistecoSRspCFE
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_CFE_SistecoSRspCFE 
{

    /** TableName=UY_CFE_SistecoSRspCFE */
    public static final String Table_Name = "UY_CFE_SistecoSRspCFE";

    /** AD_Table_ID=1001023 */
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

    /** Column name CFEAnioResolucion */
    public static final String COLUMNNAME_CFEAnioResolucion = "CFEAnioResolucion";

	/** Set Año Resolucion CFE	  */
	public void setCFEAnioResolucion (BigDecimal CFEAnioResolucion);

	/** Get Año Resolucion CFE	  */
	public BigDecimal getCFEAnioResolucion();

    /** Column name CFECAEID */
    public static final String COLUMNNAME_CFECAEID = "CFECAEID";

	/** Set ID CAE	  */
	public void setCFECAEID (BigDecimal CFECAEID);

	/** Get ID CAE	  */
	public BigDecimal getCFECAEID();

    /** Column name CFEDescripcion */
    public static final String COLUMNNAME_CFEDescripcion = "CFEDescripcion";

	/** Set Descripcion Mensaje CFE	  */
	public void setCFEDescripcion (String CFEDescripcion);

	/** Get Descripcion Mensaje CFE	  */
	public String getCFEDescripcion();

    /** Column name CFEDigestValue */
    public static final String COLUMNNAME_CFEDigestValue = "CFEDigestValue";

	/** Set CFE Digest Value	  */
	public void setCFEDigestValue (String CFEDigestValue);

	/** Get CFE Digest Value	  */
	public String getCFEDigestValue();

    /** Column name CFEDNro */
    public static final String COLUMNNAME_CFEDNro = "CFEDNro";

	/** Set Numero de Inicio del CAE	  */
	public void setCFEDNro (BigDecimal CFEDNro);

	/** Get Numero de Inicio del CAE	  */
	public BigDecimal getCFEDNro();

    /** Column name CFEFecVenc */
    public static final String COLUMNNAME_CFEFecVenc = "CFEFecVenc";

	/** Set Fecha Vencimiento CAE	  */
	public void setCFEFecVenc (Timestamp CFEFecVenc);

	/** Get Fecha Vencimiento CAE	  */
	public Timestamp getCFEFecVenc();

    /** Column name CFEHNro */
    public static final String COLUMNNAME_CFEHNro = "CFEHNro";

	/** Set Numero de Fin del CAE	  */
	public void setCFEHNro (BigDecimal CFEHNro);

	/** Get Numero de Fin del CAE	  */
	public BigDecimal getCFEHNro();

    /** Column name CFEMro */
    public static final String COLUMNNAME_CFEMro = "CFEMro";

	/** Set Numero CFE	  */
	public void setCFEMro (BigDecimal CFEMro);

	/** Get Numero CFE	  */
	public BigDecimal getCFEMro();

    /** Column name CFEResolucion */
    public static final String COLUMNNAME_CFEResolucion = "CFEResolucion";

	/** Set Resolucion CFE	  */
	public void setCFEResolucion (String CFEResolucion);

	/** Get Resolucion CFE	  */
	public String getCFEResolucion();

    /** Column name CFESerie */
    public static final String COLUMNNAME_CFESerie = "CFESerie";

	/** Set Serie de CFE	  */
	public void setCFESerie (String CFESerie);

	/** Get Serie de CFE	  */
	public String getCFESerie();

    /** Column name CFEStatus */
    public static final String COLUMNNAME_CFEStatus = "CFEStatus";

	/** Set Status CFE	  */
	public void setCFEStatus (String CFEStatus);

	/** Get Status CFE	  */
	public String getCFEStatus();

    /** Column name CFETipo */
    public static final String COLUMNNAME_CFETipo = "CFETipo";

	/** Set Tipo de CFE	  */
	public void setCFETipo (BigDecimal CFETipo);

	/** Get Tipo de CFE	  */
	public BigDecimal getCFETipo();

    /** Column name CFETmstFirma */
    public static final String COLUMNNAME_CFETmstFirma = "CFETmstFirma";

	/** Set Fecha Hora CFE	  */
	public void setCFETmstFirma (Timestamp CFETmstFirma);

	/** Get Fecha Hora CFE	  */
	public Timestamp getCFETmstFirma();

    /** Column name CFEUrlDocumentoDGI */
    public static final String COLUMNNAME_CFEUrlDocumentoDGI = "CFEUrlDocumentoDGI";

	/** Set URL Documento DGI	  */
	public void setCFEUrlDocumentoDGI (String CFEUrlDocumentoDGI);

	/** Get URL Documento DGI	  */
	public String getCFEUrlDocumentoDGI();

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

    /** Column name UY_CFE_SistecoSRspCFE_ID */
    public static final String COLUMNNAME_UY_CFE_SistecoSRspCFE_ID = "UY_CFE_SistecoSRspCFE_ID";

	/** Set UY_CFE_SistecoSRspCFE	  */
	public void setUY_CFE_SistecoSRspCFE_ID (int UY_CFE_SistecoSRspCFE_ID);

	/** Get UY_CFE_SistecoSRspCFE	  */
	public int getUY_CFE_SistecoSRspCFE_ID();
}
