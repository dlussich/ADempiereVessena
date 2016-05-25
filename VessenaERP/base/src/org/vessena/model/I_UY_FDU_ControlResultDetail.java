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

/** Generated Interface for UY_FDU_ControlResultDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_FDU_ControlResultDetail 
{

    /** TableName=UY_FDU_ControlResultDetail */
    public static final String Table_Name = "UY_FDU_ControlResultDetail";

    /** AD_Table_ID=1000445 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name Account */
    public static final String COLUMNNAME_Account = "Account";

	/** Set Account	  */
	public void setAccount (String Account);

	/** Get Account	  */
	public String getAccount();

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

    /** Column name Agencia */
    public static final String COLUMNNAME_Agencia = "Agencia";

	/** Set Agencia.
	  * Agencia
	  */
	public void setAgencia (String Agencia);

	/** Get Agencia.
	  * Agencia
	  */
	public String getAgencia();

    /** Column name Autorizador */
    public static final String COLUMNNAME_Autorizador = "Autorizador";

	/** Set Autorizador.
	  * Autorizador
	  */
	public void setAutorizador (String Autorizador);

	/** Get Autorizador.
	  * Autorizador
	  */
	public String getAutorizador();

    /** Column name codigo */
    public static final String COLUMNNAME_codigo = "codigo";

	/** Set codigo	  */
	public void setcodigo (String codigo);

	/** Get codigo	  */
	public String getcodigo();

    /** Column name CoefAdempiere */
    public static final String COLUMNNAME_CoefAdempiere = "CoefAdempiere";

	/** Set CoefAdempiere.
	  * CoefAdempiere
	  */
	public void setCoefAdempiere (BigDecimal CoefAdempiere);

	/** Get CoefAdempiere.
	  * CoefAdempiere
	  */
	public BigDecimal getCoefAdempiere();

    /** Column name comercio */
    public static final String COLUMNNAME_comercio = "comercio";

	/** Set comercio	  */
	public void setcomercio (String comercio);

	/** Get comercio	  */
	public String getcomercio();

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

    /** Column name Cuotas */
    public static final String COLUMNNAME_Cuotas = "Cuotas";

	/** Set Cuotas.
	  * Cuotas
	  */
	public void setCuotas (String Cuotas);

	/** Get Cuotas.
	  * Cuotas
	  */
	public String getCuotas();

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

    /** Column name DeadLinePrevious */
    public static final String COLUMNNAME_DeadLinePrevious = "DeadLinePrevious";

	/** Set DeadLinePrevious.
	  * DeadLinePrevious
	  */
	public void setDeadLinePrevious (Timestamp DeadLinePrevious);

	/** Get DeadLinePrevious.
	  * DeadLinePrevious
	  */
	public Timestamp getDeadLinePrevious();

    /** Column name DiferenciaDolares */
    public static final String COLUMNNAME_DiferenciaDolares = "DiferenciaDolares";

	/** Set DiferenciaDolares.
	  * DiferenciaDolares
	  */
	public void setDiferenciaDolares (BigDecimal DiferenciaDolares);

	/** Get DiferenciaDolares.
	  * DiferenciaDolares
	  */
	public BigDecimal getDiferenciaDolares();

    /** Column name DiferenciaPesos */
    public static final String COLUMNNAME_DiferenciaPesos = "DiferenciaPesos";

	/** Set DiferenciaPesos.
	  * DiferenciaPesos
	  */
	public void setDiferenciaPesos (BigDecimal DiferenciaPesos);

	/** Get DiferenciaPesos.
	  * DiferenciaPesos
	  */
	public BigDecimal getDiferenciaPesos();

    /** Column name Ejecutor */
    public static final String COLUMNNAME_Ejecutor = "Ejecutor";

	/** Set Ejecutor.
	  * Ejecutor
	  */
	public void setEjecutor (String Ejecutor);

	/** Get Ejecutor.
	  * Ejecutor
	  */
	public String getEjecutor();

    /** Column name InteresAdempiere */
    public static final String COLUMNNAME_InteresAdempiere = "InteresAdempiere";

	/** Set InteresAdempiere.
	  * InteresAdempiere
	  */
	public void setInteresAdempiere (BigDecimal InteresAdempiere);

	/** Get InteresAdempiere.
	  * InteresAdempiere
	  */
	public BigDecimal getInteresAdempiere();

    /** Column name InteresFinancial */
    public static final String COLUMNNAME_InteresFinancial = "InteresFinancial";

	/** Set InteresFinancial.
	  * InteresFinancial
	  */
	public void setInteresFinancial (BigDecimal InteresFinancial);

	/** Get InteresFinancial.
	  * InteresFinancial
	  */
	public BigDecimal getInteresFinancial();

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

    /** Column name Message */
    public static final String COLUMNNAME_Message = "Message";

	/** Set Message.
	  * EMail Message
	  */
	public void setMessage (String Message);

	/** Get Message.
	  * EMail Message
	  */
	public String getMessage();

    /** Column name MontoCuota */
    public static final String COLUMNNAME_MontoCuota = "MontoCuota";

	/** Set MontoCuota.
	  * MontoCuota
	  */
	public void setMontoCuota (BigDecimal MontoCuota);

	/** Get MontoCuota.
	  * MontoCuota
	  */
	public BigDecimal getMontoCuota();

    /** Column name SaldoFinalDolares */
    public static final String COLUMNNAME_SaldoFinalDolares = "SaldoFinalDolares";

	/** Set SaldoFinalDolares.
	  * SaldoFinalDolares
	  */
	public void setSaldoFinalDolares (BigDecimal SaldoFinalDolares);

	/** Get SaldoFinalDolares.
	  * SaldoFinalDolares
	  */
	public BigDecimal getSaldoFinalDolares();

    /** Column name SaldoFinalPesos */
    public static final String COLUMNNAME_SaldoFinalPesos = "SaldoFinalPesos";

	/** Set SaldoFinalPesos.
	  * SaldoFinalPesos
	  */
	public void setSaldoFinalPesos (BigDecimal SaldoFinalPesos);

	/** Get SaldoFinalPesos.
	  * SaldoFinalPesos
	  */
	public BigDecimal getSaldoFinalPesos();

    /** Column name SaldoInicialDolares */
    public static final String COLUMNNAME_SaldoInicialDolares = "SaldoInicialDolares";

	/** Set SaldoInicialDolares.
	  * SaldoInicialDolares
	  */
	public void setSaldoInicialDolares (BigDecimal SaldoInicialDolares);

	/** Get SaldoInicialDolares.
	  * SaldoInicialDolares
	  */
	public BigDecimal getSaldoInicialDolares();

    /** Column name SaldoInicialPesos */
    public static final String COLUMNNAME_SaldoInicialPesos = "SaldoInicialPesos";

	/** Set SaldoInicialPesos.
	  * SaldoInicialPesos
	  */
	public void setSaldoInicialPesos (BigDecimal SaldoInicialPesos);

	/** Get SaldoInicialPesos.
	  * SaldoInicialPesos
	  */
	public BigDecimal getSaldoInicialPesos();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name Success */
    public static final String COLUMNNAME_Success = "Success";

	/** Set Success	  */
	public void setSuccess (boolean Success);

	/** Get Success	  */
	public boolean isSuccess();

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

    /** Column name UY_FDU_Control_ID */
    public static final String COLUMNNAME_UY_FDU_Control_ID = "UY_FDU_Control_ID";

	/** Set UY_FDU_Control	  */
	public void setUY_FDU_Control_ID (int UY_FDU_Control_ID);

	/** Get UY_FDU_Control	  */
	public int getUY_FDU_Control_ID();

	public I_UY_FDU_Control getUY_FDU_Control() throws RuntimeException;

    /** Column name UY_FDU_ControlResultDetail_ID */
    public static final String COLUMNNAME_UY_FDU_ControlResultDetail_ID = "UY_FDU_ControlResultDetail_ID";

	/** Set UY_FDU_ControlResultDetail	  */
	public void setUY_FDU_ControlResultDetail_ID (int UY_FDU_ControlResultDetail_ID);

	/** Get UY_FDU_ControlResultDetail	  */
	public int getUY_FDU_ControlResultDetail_ID();

    /** Column name UY_FDU_ControlResult_ID */
    public static final String COLUMNNAME_UY_FDU_ControlResult_ID = "UY_FDU_ControlResult_ID";

	/** Set UY_FDU_ControlResult	  */
	public void setUY_FDU_ControlResult_ID (int UY_FDU_ControlResult_ID);

	/** Get UY_FDU_ControlResult	  */
	public int getUY_FDU_ControlResult_ID();

	public I_UY_FDU_ControlResult getUY_FDU_ControlResult() throws RuntimeException;

    /** Column name UY_Fdu_Productos_ID */
    public static final String COLUMNNAME_UY_Fdu_Productos_ID = "UY_Fdu_Productos_ID";

	/** Set UY_Fdu_Productos	  */
	public void setUY_Fdu_Productos_ID (int UY_Fdu_Productos_ID);

	/** Get UY_Fdu_Productos	  */
	public int getUY_Fdu_Productos_ID();

	public I_UY_Fdu_Productos getUY_Fdu_Productos() throws RuntimeException;

    /** Column name WorkingTime */
    public static final String COLUMNNAME_WorkingTime = "WorkingTime";

	/** Set Working Time.
	  * Workflow Simulation Execution Time
	  */
	public void setWorkingTime (String WorkingTime);

	/** Get Working Time.
	  * Workflow Simulation Execution Time
	  */
	public String getWorkingTime();
}
