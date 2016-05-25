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

/** Generated Interface for UY_Fdu_MassiveAdjustLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Fdu_MassiveAdjustLine 
{

    /** TableName=UY_Fdu_MassiveAdjustLine */
    public static final String Table_Name = "UY_Fdu_MassiveAdjustLine";

    /** AD_Table_ID=1000671 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

	/** Set Account No.
	  * Account Number
	  */
	public void setAccountNo (String AccountNo);

	/** Get Account No.
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

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

    /** Column name cierreanterior */
    public static final String COLUMNNAME_cierreanterior = "cierreanterior";

	/** Set cierreanterior	  */
	public void setcierreanterior (Timestamp cierreanterior);

	/** Get cierreanterior	  */
	public Timestamp getcierreanterior();

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

    /** Column name Cotizacion */
    public static final String COLUMNNAME_Cotizacion = "Cotizacion";

	/** Set Cotizacion.
	  * Cotizacion
	  */
	public void setCotizacion (BigDecimal Cotizacion);

	/** Get Cotizacion.
	  * Cotizacion
	  */
	public BigDecimal getCotizacion();

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

    /** Column name DiasAtraso */
    public static final String COLUMNNAME_DiasAtraso = "DiasAtraso";

	/** Set DiasAtraso.
	  * DiasAtraso
	  */
	public void setDiasAtraso (int DiasAtraso);

	/** Get DiasAtraso.
	  * DiasAtraso
	  */
	public int getDiasAtraso();

    /** Column name fechapago */
    public static final String COLUMNNAME_fechapago = "fechapago";

	/** Set fechapago	  */
	public void setfechapago (Timestamp fechapago);

	/** Get fechapago	  */
	public Timestamp getfechapago();

    /** Column name Importe */
    public static final String COLUMNNAME_Importe = "Importe";

	/** Set Importe	  */
	public void setImporte (BigDecimal Importe);

	/** Get Importe	  */
	public BigDecimal getImporte();

    /** Column name ImportePago */
    public static final String COLUMNNAME_ImportePago = "ImportePago";

	/** Set ImportePago.
	  * ImportePago
	  */
	public void setImportePago (BigDecimal ImportePago);

	/** Get ImportePago.
	  * ImportePago
	  */
	public BigDecimal getImportePago();

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

    /** Column name MoraEnAnio */
    public static final String COLUMNNAME_MoraEnAnio = "MoraEnAnio";

	/** Set MoraEnAnio.
	  * MoraEnAnio
	  */
	public void setMoraEnAnio (int MoraEnAnio);

	/** Get MoraEnAnio.
	  * MoraEnAnio
	  */
	public int getMoraEnAnio();

    /** Column name NroTarjetaTitular */
    public static final String COLUMNNAME_NroTarjetaTitular = "NroTarjetaTitular";

	/** Set NroTarjetaTitular	  */
	public void setNroTarjetaTitular (String NroTarjetaTitular);

	/** Get NroTarjetaTitular	  */
	public String getNroTarjetaTitular();

    /** Column name PagoMinimoAnterior */
    public static final String COLUMNNAME_PagoMinimoAnterior = "PagoMinimoAnterior";

	/** Set PagoMinimoAnterior.
	  * PagoMinimoAnterior
	  */
	public void setPagoMinimoAnterior (BigDecimal PagoMinimoAnterior);

	/** Get PagoMinimoAnterior.
	  * PagoMinimoAnterior
	  */
	public BigDecimal getPagoMinimoAnterior();

    /** Column name QtyCredits */
    public static final String COLUMNNAME_QtyCredits = "QtyCredits";

	/** Set QtyCredits.
	  * QtyCredits
	  */
	public void setQtyCredits (BigDecimal QtyCredits);

	/** Get QtyCredits.
	  * QtyCredits
	  */
	public BigDecimal getQtyCredits();

    /** Column name QtyDebits */
    public static final String COLUMNNAME_QtyDebits = "QtyDebits";

	/** Set QtyDebits.
	  * QtyDebits
	  */
	public void setQtyDebits (BigDecimal QtyDebits);

	/** Get QtyDebits.
	  * QtyDebits
	  */
	public BigDecimal getQtyDebits();

    /** Column name SaldoAnterior */
    public static final String COLUMNNAME_SaldoAnterior = "SaldoAnterior";

	/** Set SaldoAnterior.
	  * SaldoAnterior
	  */
	public void setSaldoAnterior (BigDecimal SaldoAnterior);

	/** Get SaldoAnterior.
	  * SaldoAnterior
	  */
	public BigDecimal getSaldoAnterior();

    /** Column name SignoSaldo */
    public static final String COLUMNNAME_SignoSaldo = "SignoSaldo";

	/** Set SignoSaldo.
	  * SignoSaldo
	  */
	public void setSignoSaldo (String SignoSaldo);

	/** Get SignoSaldo.
	  * SignoSaldo
	  */
	public String getSignoSaldo();

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

    /** Column name UY_Fdu_Afinidad_ID */
    public static final String COLUMNNAME_UY_Fdu_Afinidad_ID = "UY_Fdu_Afinidad_ID";

	/** Set UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID);

	/** Get UY_Fdu_Afinidad	  */
	public int getUY_Fdu_Afinidad_ID();

	public I_UY_Fdu_Afinidad getUY_Fdu_Afinidad() throws RuntimeException;

    /** Column name UY_Fdu_MassiveAdjustLine_ID */
    public static final String COLUMNNAME_UY_Fdu_MassiveAdjustLine_ID = "UY_Fdu_MassiveAdjustLine_ID";

	/** Set UY_Fdu_MassiveAdjustLine	  */
	public void setUY_Fdu_MassiveAdjustLine_ID (int UY_Fdu_MassiveAdjustLine_ID);

	/** Get UY_Fdu_MassiveAdjustLine	  */
	public int getUY_Fdu_MassiveAdjustLine_ID();

    /** Column name UY_Fdu_MassiveAdjustment_ID */
    public static final String COLUMNNAME_UY_Fdu_MassiveAdjustment_ID = "UY_Fdu_MassiveAdjustment_ID";

	/** Set UY_Fdu_MassiveAdjustment	  */
	public void setUY_Fdu_MassiveAdjustment_ID (int UY_Fdu_MassiveAdjustment_ID);

	/** Get UY_Fdu_MassiveAdjustment	  */
	public int getUY_Fdu_MassiveAdjustment_ID();

	public I_UY_Fdu_MassiveAdjustment getUY_Fdu_MassiveAdjustment() throws RuntimeException;

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

    /** Column name VencimientoAnterior */
    public static final String COLUMNNAME_VencimientoAnterior = "VencimientoAnterior";

	/** Set VencimientoAnterior.
	  * VencimientoAnterior
	  */
	public void setVencimientoAnterior (Timestamp VencimientoAnterior);

	/** Get VencimientoAnterior.
	  * VencimientoAnterior
	  */
	public Timestamp getVencimientoAnterior();
}
