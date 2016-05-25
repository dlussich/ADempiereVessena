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

/** Generated Interface for UY_Bcu_DatosTxtCapital
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Bcu_DatosTxtCapital 
{

    /** TableName=UY_Bcu_DatosTxtCapital */
    public static final String Table_Name = "UY_Bcu_DatosTxtCapital";

    /** AD_Table_ID=1000583 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name acumuladodecapital */
    public static final String COLUMNNAME_acumuladodecapital = "acumuladodecapital";

	/** Set acumuladodecapital	  */
	public void setacumuladodecapital (BigDecimal acumuladodecapital);

	/** Get acumuladodecapital	  */
	public BigDecimal getacumuladodecapital();

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

    /** Column name cantidadoperaciones */
    public static final String COLUMNNAME_cantidadoperaciones = "cantidadoperaciones";

	/** Set cantidadoperaciones	  */
	public void setcantidadoperaciones (BigDecimal cantidadoperaciones);

	/** Get cantidadoperaciones	  */
	public BigDecimal getcantidadoperaciones();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

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

    /** Column name orden */
    public static final String COLUMNNAME_orden = "orden";

	/** Set orden	  */
	public void setorden (int orden);

	/** Get orden	  */
	public int getorden();

    /** Column name plazoremanente */
    public static final String COLUMNNAME_plazoremanente = "plazoremanente";

	/** Set plazoremanente	  */
	public void setplazoremanente (BigDecimal plazoremanente);

	/** Get plazoremanente	  */
	public BigDecimal getplazoremanente();

    /** Column name Tasa */
    public static final String COLUMNNAME_Tasa = "Tasa";

	/** Set Tasa.
	  * Tasa
	  */
	public void setTasa (BigDecimal Tasa);

	/** Get Tasa.
	  * Tasa
	  */
	public BigDecimal getTasa();

    /** Column name TipoDato */
    public static final String COLUMNNAME_TipoDato = "TipoDato";

	/** Set TipoDato.
	  * TipoDato
	  */
	public void setTipoDato (String TipoDato);

	/** Get TipoDato.
	  * TipoDato
	  */
	public String getTipoDato();

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

    /** Column name UY_Bcu_DatosTxtCapital_ID */
    public static final String COLUMNNAME_UY_Bcu_DatosTxtCapital_ID = "UY_Bcu_DatosTxtCapital_ID";

	/** Set UY_Bcu_DatosTxtCapital	  */
	public void setUY_Bcu_DatosTxtCapital_ID (int UY_Bcu_DatosTxtCapital_ID);

	/** Get UY_Bcu_DatosTxtCapital	  */
	public int getUY_Bcu_DatosTxtCapital_ID();

    /** Column name UY_Bcu_GracionTxtCapital_ID */
    public static final String COLUMNNAME_UY_Bcu_GracionTxtCapital_ID = "UY_Bcu_GracionTxtCapital_ID";

	/** Set UY_Bcu_GracionTxtCapital	  */
	public void setUY_Bcu_GracionTxtCapital_ID (int UY_Bcu_GracionTxtCapital_ID);

	/** Get UY_Bcu_GracionTxtCapital	  */
	public int getUY_Bcu_GracionTxtCapital_ID();

	public I_UY_Bcu_GracionTxtCapital getUY_Bcu_GracionTxtCapital() throws RuntimeException;
}
