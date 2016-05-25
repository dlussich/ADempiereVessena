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

/** Generated Interface for UY_Fdu_AdjCallFduLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_Fdu_AdjCallFduLine 
{

    /** TableName=UY_Fdu_AdjCallFduLine */
    public static final String Table_Name = "UY_Fdu_AdjCallFduLine";

    /** AD_Table_ID=1000682 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

	/** Set AccountNo.
	  * Account Number
	  */
	public void setAccountNo (String AccountNo);

	/** Get AccountNo.
	  * Account Number
	  */
	public String getAccountNo();

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

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

    /** Column name CodigoAjuste */
    public static final String COLUMNNAME_CodigoAjuste = "CodigoAjuste";

	/** Set CodigoAjuste.
	  * CodigoAjuste
	  */
	public void setCodigoAjuste (String CodigoAjuste);

	/** Get CodigoAjuste.
	  * CodigoAjuste
	  */
	public String getCodigoAjuste();

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

    /** Column name FechaLlamada */
    public static final String COLUMNNAME_FechaLlamada = "FechaLlamada";

	/** Set FechaLlamada.
	  * FechaLlamada
	  */
	public void setFechaLlamada (Timestamp FechaLlamada);

	/** Get FechaLlamada.
	  * FechaLlamada
	  */
	public Timestamp getFechaLlamada();

    /** Column name Importe */
    public static final String COLUMNNAME_Importe = "Importe";

	/** Set Importe	  */
	public void setImporte (BigDecimal Importe);

	/** Get Importe	  */
	public BigDecimal getImporte();

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

    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/** Set Valid.
	  * Element is valid
	  */
	public void setIsValid (boolean IsValid);

	/** Get Valid.
	  * Element is valid
	  */
	public boolean isValid();

    /** Column name NroTarjetaTitular */
    public static final String COLUMNNAME_NroTarjetaTitular = "NroTarjetaTitular";

	/** Set NroTarjetaTitular	  */
	public void setNroTarjetaTitular (String NroTarjetaTitular);

	/** Get NroTarjetaTitular	  */
	public String getNroTarjetaTitular();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

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

    /** Column name UY_Fdu_AdjCallFduLine_ID */
    public static final String COLUMNNAME_UY_Fdu_AdjCallFduLine_ID = "UY_Fdu_AdjCallFduLine_ID";

	/** Set UY_Fdu_AdjCallFduLine	  */
	public void setUY_Fdu_AdjCallFduLine_ID (int UY_Fdu_AdjCallFduLine_ID);

	/** Get UY_Fdu_AdjCallFduLine	  */
	public int getUY_Fdu_AdjCallFduLine_ID();

    /** Column name UY_Fdu_AdjustmentCallFdu_ID */
    public static final String COLUMNNAME_UY_Fdu_AdjustmentCallFdu_ID = "UY_Fdu_AdjustmentCallFdu_ID";

	/** Set UY_Fdu_AdjustmentCallFdu	  */
	public void setUY_Fdu_AdjustmentCallFdu_ID (int UY_Fdu_AdjustmentCallFdu_ID);

	/** Get UY_Fdu_AdjustmentCallFdu	  */
	public int getUY_Fdu_AdjustmentCallFdu_ID();

	public I_UY_Fdu_AdjustmentCallFdu getUY_Fdu_AdjustmentCallFdu() throws RuntimeException;

    /** Column name UY_Fdu_Afinidad_ID */
    public static final String COLUMNNAME_UY_Fdu_Afinidad_ID = "UY_Fdu_Afinidad_ID";

	/** Set UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID);

	/** Get UY_Fdu_Afinidad	  */
	public int getUY_Fdu_Afinidad_ID();

	public I_UY_Fdu_Afinidad getUY_Fdu_Afinidad() throws RuntimeException;

    /** Column name UY_Fdu_ModeloLiquidacion_ID */
    public static final String COLUMNNAME_UY_Fdu_ModeloLiquidacion_ID = "UY_Fdu_ModeloLiquidacion_ID";

	/** Set UY_Fdu_ModeloLiquidacion	  */
	public void setUY_Fdu_ModeloLiquidacion_ID (int UY_Fdu_ModeloLiquidacion_ID);

	/** Get UY_Fdu_ModeloLiquidacion	  */
	public int getUY_Fdu_ModeloLiquidacion_ID();

	public I_UY_Fdu_ModeloLiquidacion getUY_Fdu_ModeloLiquidacion() throws RuntimeException;

    /** Column name UY_Fdu_Productos_ID */
    public static final String COLUMNNAME_UY_Fdu_Productos_ID = "UY_Fdu_Productos_ID";

	/** Set UY_Fdu_Productos	  */
	public void setUY_Fdu_Productos_ID (int UY_Fdu_Productos_ID);

	/** Get UY_Fdu_Productos	  */
	public int getUY_Fdu_Productos_ID();

	public I_UY_Fdu_Productos getUY_Fdu_Productos() throws RuntimeException;
}
