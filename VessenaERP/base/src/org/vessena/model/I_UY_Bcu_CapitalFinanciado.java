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

/** Generated Interface for UY_Bcu_CapitalFinanciado
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Bcu_CapitalFinanciado 
{

    /** TableName=UY_Bcu_CapitalFinanciado */
    public static final String Table_Name = "UY_Bcu_CapitalFinanciado";

    /** AD_Table_ID=1000561 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name cantidadoperaciones */
    public static final String COLUMNNAME_cantidadoperaciones = "cantidadoperaciones";

	/** Set cantidadoperaciones	  */
	public void setcantidadoperaciones (BigDecimal cantidadoperaciones);

	/** Get cantidadoperaciones	  */
	public BigDecimal getcantidadoperaciones();

    /** Column name CapitalInteres */
    public static final String COLUMNNAME_CapitalInteres = "CapitalInteres";

	/** Set CapitalInteres.
	  * CapitalInteres
	  */
	public void setCapitalInteres (BigDecimal CapitalInteres);

	/** Get CapitalInteres.
	  * CapitalInteres
	  */
	public BigDecimal getCapitalInteres();

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

    /** Column name CuotaActual */
    public static final String COLUMNNAME_CuotaActual = "CuotaActual";

	/** Set CuotaActual.
	  * CuotaActual
	  */
	public void setCuotaActual (int CuotaActual);

	/** Get CuotaActual.
	  * CuotaActual
	  */
	public int getCuotaActual();

    /** Column name Cuotas */
    public static final String COLUMNNAME_Cuotas = "Cuotas";

	/** Set Cuotas.
	  * Cuotas
	  */
	public void setCuotas (int Cuotas);

	/** Get Cuotas.
	  * Cuotas
	  */
	public int getCuotas();

    /** Column name Cupon */
    public static final String COLUMNNAME_Cupon = "Cupon";

	/** Set Cupon	  */
	public void setCupon (String Cupon);

	/** Get Cupon	  */
	public String getCupon();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name FechaCierre */
    public static final String COLUMNNAME_FechaCierre = "FechaCierre";

	/** Set FechaCierre	  */
	public void setFechaCierre (Timestamp FechaCierre);

	/** Get FechaCierre	  */
	public Timestamp getFechaCierre();

    /** Column name FechaOperacion */
    public static final String COLUMNNAME_FechaOperacion = "FechaOperacion";

	/** Set FechaOperacion.
	  * FechaOperacion
	  */
	public void setFechaOperacion (Timestamp FechaOperacion);

	/** Get FechaOperacion.
	  * FechaOperacion
	  */
	public Timestamp getFechaOperacion();

    /** Column name FechaPresentacion */
    public static final String COLUMNNAME_FechaPresentacion = "FechaPresentacion";

	/** Set FechaPresentacion.
	  * FechaPresentacion
	  */
	public void setFechaPresentacion (Timestamp FechaPresentacion);

	/** Get FechaPresentacion.
	  * FechaPresentacion
	  */
	public Timestamp getFechaPresentacion();

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

    /** Column name IsDebit */
    public static final String COLUMNNAME_IsDebit = "IsDebit";

	/** Set IsDebit	  */
	public void setIsDebit (boolean IsDebit);

	/** Get IsDebit	  */
	public boolean isDebit();

    /** Column name IsIncosistente */
    public static final String COLUMNNAME_IsIncosistente = "IsIncosistente";

	/** Set IsIncosistente.
	  * IsIncosistente
	  */
	public void setIsIncosistente (boolean IsIncosistente);

	/** Get IsIncosistente.
	  * IsIncosistente
	  */
	public boolean isIncosistente();

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

    /** Column name Tea */
    public static final String COLUMNNAME_Tea = "Tea";

	/** Set Tea.
	  * Tea
	  */
	public void setTea (BigDecimal Tea);

	/** Get Tea.
	  * Tea
	  */
	public BigDecimal getTea();

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

    /** Column name UY_Bcu_CapitalFinanciado_ID */
    public static final String COLUMNNAME_UY_Bcu_CapitalFinanciado_ID = "UY_Bcu_CapitalFinanciado_ID";

	/** Set UY_Bcu_CapitalFinanciado	  */
	public void setUY_Bcu_CapitalFinanciado_ID (int UY_Bcu_CapitalFinanciado_ID);

	/** Get UY_Bcu_CapitalFinanciado	  */
	public int getUY_Bcu_CapitalFinanciado_ID();

    /** Column name UY_Bcu_GracionTxtCapital_ID */
    public static final String COLUMNNAME_UY_Bcu_GracionTxtCapital_ID = "UY_Bcu_GracionTxtCapital_ID";

	/** Set UY_Bcu_GracionTxtCapital	  */
	public void setUY_Bcu_GracionTxtCapital_ID (int UY_Bcu_GracionTxtCapital_ID);

	/** Get UY_Bcu_GracionTxtCapital	  */
	public int getUY_Bcu_GracionTxtCapital_ID();

    /** Column name UY_Fdu_Afinidad_ID */
    public static final String COLUMNNAME_UY_Fdu_Afinidad_ID = "UY_Fdu_Afinidad_ID";

	/** Set UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID);

	/** Get UY_Fdu_Afinidad	  */
	public int getUY_Fdu_Afinidad_ID();

    /** Column name UY_Fdu_Productos_ID */
    public static final String COLUMNNAME_UY_Fdu_Productos_ID = "UY_Fdu_Productos_ID";

	/** Set UY_Fdu_Productos	  */
	public void setUY_Fdu_Productos_ID (int UY_Fdu_Productos_ID);

	/** Get UY_Fdu_Productos	  */
	public int getUY_Fdu_Productos_ID();

    /** Column name vigencia */
    public static final String COLUMNNAME_vigencia = "vigencia";

	/** Set vigencia	  */
	public void setvigencia (Timestamp vigencia);

	/** Get vigencia	  */
	public Timestamp getvigencia();
}
