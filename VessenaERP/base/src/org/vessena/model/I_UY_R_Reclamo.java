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

/** Generated Interface for UY_R_Reclamo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_Reclamo 
{

    /** TableName=UY_R_Reclamo */
    public static final String Table_Name = "UY_R_Reclamo";

    /** AD_Table_ID=1000455 */
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

    /** Column name Adjunto_ID_Obligatorio */
    public static final String COLUMNNAME_Adjunto_ID_Obligatorio = "Adjunto_ID_Obligatorio";

	/** Set Adjunto_ID_Obligatorio	  */
	public void setAdjunto_ID_Obligatorio (int Adjunto_ID_Obligatorio);

	/** Get Adjunto_ID_Obligatorio	  */
	public int getAdjunto_ID_Obligatorio();

    /** Column name Adjunto_ID_Opcional */
    public static final String COLUMNNAME_Adjunto_ID_Opcional = "Adjunto_ID_Opcional";

	/** Set Adjunto_ID_Opcional	  */
	public void setAdjunto_ID_Opcional (int Adjunto_ID_Opcional);

	/** Get Adjunto_ID_Opcional	  */
	public int getAdjunto_ID_Opcional();

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

    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/** Set Amount.
	  * Amount
	  */
	public void setAmt (BigDecimal Amt);

	/** Get Amount.
	  * Amount
	  */
	public BigDecimal getAmt();

    /** Column name AmtRequested */
    public static final String COLUMNNAME_AmtRequested = "AmtRequested";

	/** Set AmtRequested	  */
	public void setAmtRequested (BigDecimal AmtRequested);

	/** Get AmtRequested	  */
	public BigDecimal getAmtRequested();

    /** Column name AssignDateFrom */
    public static final String COLUMNNAME_AssignDateFrom = "AssignDateFrom";

	/** Set Assign From.
	  * Assign resource from
	  */
	public void setAssignDateFrom (Timestamp AssignDateFrom);

	/** Get Assign From.
	  * Assign resource from
	  */
	public Timestamp getAssignDateFrom();

    /** Column name AssignDateTo */
    public static final String COLUMNNAME_AssignDateTo = "AssignDateTo";

	/** Set Assign To.
	  * Assign resource until
	  */
	public void setAssignDateTo (Timestamp AssignDateTo);

	/** Get Assign To.
	  * Assign resource until
	  */
	public Timestamp getAssignDateTo();

    /** Column name AssignTo_ID */
    public static final String COLUMNNAME_AssignTo_ID = "AssignTo_ID";

	/** Set AssignTo_ID	  */
	public void setAssignTo_ID (int AssignTo_ID);

	/** Get AssignTo_ID	  */
	public int getAssignTo_ID();

    /** Column name Boletinada */
    public static final String COLUMNNAME_Boletinada = "Boletinada";

	/** Set Boletinada	  */
	public void setBoletinada (boolean Boletinada);

	/** Get Boletinada	  */
	public boolean isBoletinada();

    /** Column name Cargos */
    public static final String COLUMNNAME_Cargos = "Cargos";

	/** Set Cargos	  */
	public void setCargos (int Cargos);

	/** Get Cargos	  */
	public int getCargos();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

    /** Column name CoefAdelanto */
    public static final String COLUMNNAME_CoefAdelanto = "CoefAdelanto";

	/** Set CoefAdelanto	  */
	public void setCoefAdelanto (BigDecimal CoefAdelanto);

	/** Get CoefAdelanto	  */
	public BigDecimal getCoefAdelanto();

    /** Column name Coeficiente */
    public static final String COLUMNNAME_Coeficiente = "Coeficiente";

	/** Set Coeficiente	  */
	public void setCoeficiente (BigDecimal Coeficiente);

	/** Get Coeficiente	  */
	public BigDecimal getCoeficiente();

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

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

    /** Column name Cupon */
    public static final String COLUMNNAME_Cupon = "Cupon";

	/** Set Cupon	  */
	public void setCupon (String Cupon);

	/** Get Cupon	  */
	public String getCupon();

    /** Column name CustomerName */
    public static final String COLUMNNAME_CustomerName = "CustomerName";

	/** Set CustomerName	  */
	public void setCustomerName (String CustomerName);

	/** Get CustomerName	  */
	public String getCustomerName();

    /** Column name DateAlta */
    public static final String COLUMNNAME_DateAlta = "DateAlta";

	/** Set DateAlta	  */
	public void setDateAlta (Timestamp DateAlta);

	/** Get DateAlta	  */
	public Timestamp getDateAlta();

    /** Column name DateOperation */
    public static final String COLUMNNAME_DateOperation = "DateOperation";

	/** Set DateOperation	  */
	public void setDateOperation (Timestamp DateOperation);

	/** Get DateOperation	  */
	public Timestamp getDateOperation();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DiasCierre */
    public static final String COLUMNNAME_DiasCierre = "DiasCierre";

	/** Set DiasCierre	  */
	public void setDiasCierre (int DiasCierre);

	/** Get DiasCierre	  */
	public int getDiasCierre();

    /** Column name DiasGestion */
    public static final String COLUMNNAME_DiasGestion = "DiasGestion";

	/** Set DiasGestion	  */
	public void setDiasGestion (int DiasGestion);

	/** Get DiasGestion	  */
	public int getDiasGestion();

    /** Column name DiasNotificacion */
    public static final String COLUMNNAME_DiasNotificacion = "DiasNotificacion";

	/** Set DiasNotificacion	  */
	public void setDiasNotificacion (int DiasNotificacion);

	/** Get DiasNotificacion	  */
	public int getDiasNotificacion();

    /** Column name diasresolucion */
    public static final String COLUMNNAME_diasresolucion = "diasresolucion";

	/** Set diasresolucion	  */
	public void setdiasresolucion (int diasresolucion);

	/** Get diasresolucion	  */
	public int getdiasresolucion();

    /** Column name DiasVencido */
    public static final String COLUMNNAME_DiasVencido = "DiasVencido";

	/** Set DiasVencido	  */
	public void setDiasVencido (int DiasVencido);

	/** Get DiasVencido	  */
	public int getDiasVencido();

    /** Column name DigitoVerificador */
    public static final String COLUMNNAME_DigitoVerificador = "DigitoVerificador";

	/** Set DigitoVerificador	  */
	public void setDigitoVerificador (int DigitoVerificador);

	/** Get DigitoVerificador	  */
	public int getDigitoVerificador();

    /** Column name Direction */
    public static final String COLUMNNAME_Direction = "Direction";

	/** Set Direction	  */
	public void setDirection (String Direction);

	/** Get Direction	  */
	public String getDirection();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

    /** Column name DueDateTitular */
    public static final String COLUMNNAME_DueDateTitular = "DueDateTitular";

	/** Set DueDateTitular	  */
	public void setDueDateTitular (String DueDateTitular);

	/** Get DueDateTitular	  */
	public String getDueDateTitular();

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name EnvioExt */
    public static final String COLUMNNAME_EnvioExt = "EnvioExt";

	/** Set EnvioExt	  */
	public void setEnvioExt (boolean EnvioExt);

	/** Get EnvioExt	  */
	public boolean isEnvioExt();

    /** Column name EnvioResumen */
    public static final String COLUMNNAME_EnvioResumen = "EnvioResumen";

	/** Set EnvioResumen	  */
	public void setEnvioResumen (boolean EnvioResumen);

	/** Get EnvioResumen	  */
	public boolean isEnvioResumen();

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

    /** Column name ExecuteAction2 */
    public static final String COLUMNNAME_ExecuteAction2 = "ExecuteAction2";

	/** Set ExecuteAction2	  */
	public void setExecuteAction2 (String ExecuteAction2);

	/** Get ExecuteAction2	  */
	public String getExecuteAction2();

    /** Column name ExecuteAction3 */
    public static final String COLUMNNAME_ExecuteAction3 = "ExecuteAction3";

	/** Set ExecuteAction3	  */
	public void setExecuteAction3 (String ExecuteAction3);

	/** Get ExecuteAction3	  */
	public String getExecuteAction3();

    /** Column name ExecuteAction4 */
    public static final String COLUMNNAME_ExecuteAction4 = "ExecuteAction4";

	/** Set ExecuteAction4	  */
	public void setExecuteAction4 (String ExecuteAction4);

	/** Get ExecuteAction4	  */
	public String getExecuteAction4();

    /** Column name GAFCOD */
    public static final String COLUMNNAME_GAFCOD = "GAFCOD";

	/** Set GAFCOD	  */
	public void setGAFCOD (int GAFCOD);

	/** Get GAFCOD	  */
	public int getGAFCOD();

    /** Column name GAFNOM */
    public static final String COLUMNNAME_GAFNOM = "GAFNOM";

	/** Set GAFNOM	  */
	public void setGAFNOM (String GAFNOM);

	/** Get GAFNOM	  */
	public String getGAFNOM();

    /** Column name Gestor_ID */
    public static final String COLUMNNAME_Gestor_ID = "Gestor_ID";

	/** Set Gestor_ID	  */
	public void setGestor_ID (int Gestor_ID);

	/** Get Gestor_ID	  */
	public int getGestor_ID();

    /** Column name GrpCtaCte */
    public static final String COLUMNNAME_GrpCtaCte = "GrpCtaCte";

	/** Set GrpCtaCte	  */
	public void setGrpCtaCte (int GrpCtaCte);

	/** Get GrpCtaCte	  */
	public int getGrpCtaCte();

    /** Column name HideInfo */
    public static final String COLUMNNAME_HideInfo = "HideInfo";

	/** Set HideInfo	  */
	public void setHideInfo (boolean HideInfo);

	/** Get HideInfo	  */
	public boolean isHideInfo();

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

    /** Column name IsDenounced */
    public static final String COLUMNNAME_IsDenounced = "IsDenounced";

	/** Set IsDenounced	  */
	public void setIsDenounced (boolean IsDenounced);

	/** Get IsDenounced	  */
	public boolean isDenounced();

    /** Column name isinmediate */
    public static final String COLUMNNAME_isinmediate = "isinmediate";

	/** Set isinmediate	  */
	public void setisinmediate (boolean isinmediate);

	/** Get isinmediate	  */
	public boolean isinmediate();

    /** Column name IsInternalIssue */
    public static final String COLUMNNAME_IsInternalIssue = "IsInternalIssue";

	/** Set IsInternalIssue.
	  * Incidencia Interna
	  */
	public void setIsInternalIssue (boolean IsInternalIssue);

	/** Get IsInternalIssue.
	  * Incidencia Interna
	  */
	public boolean isInternalIssue();

    /** Column name IsObserver */
    public static final String COLUMNNAME_IsObserver = "IsObserver";

	/** Set IsObserver.
	  * IsObserver
	  */
	public void setIsObserver (boolean IsObserver);

	/** Get IsObserver.
	  * IsObserver
	  */
	public boolean isObserver();

    /** Column name IsPreNotificacion */
    public static final String COLUMNNAME_IsPreNotificacion = "IsPreNotificacion";

	/** Set IsPreNotificacion	  */
	public void setIsPreNotificacion (boolean IsPreNotificacion);

	/** Get IsPreNotificacion	  */
	public boolean isPreNotificacion();

    /** Column name Justification */
    public static final String COLUMNNAME_Justification = "Justification";

	/** Set Justification	  */
	public void setJustification (String Justification);

	/** Get Justification	  */
	public String getJustification();

    /** Column name LetraCompra */
    public static final String COLUMNNAME_LetraCompra = "LetraCompra";

	/** Set LetraCompra	  */
	public void setLetraCompra (String LetraCompra);

	/** Get LetraCompra	  */
	public String getLetraCompra();

    /** Column name LimCreditoTitular */
    public static final String COLUMNNAME_LimCreditoTitular = "LimCreditoTitular";

	/** Set LimCreditoTitular	  */
	public void setLimCreditoTitular (BigDecimal LimCreditoTitular);

	/** Get LimCreditoTitular	  */
	public BigDecimal getLimCreditoTitular();

    /** Column name MLCod */
    public static final String COLUMNNAME_MLCod = "MLCod";

	/** Set MLCod	  */
	public void setMLCod (String MLCod);

	/** Get MLCod	  */
	public String getMLCod();

    /** Column name Mobile */
    public static final String COLUMNNAME_Mobile = "Mobile";

	/** Set Mobile	  */
	public void setMobile (String Mobile);

	/** Get Mobile	  */
	public String getMobile();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Additional Name
	  */
	public void setName2 (String Name2);

	/** Get Name 2.
	  * Additional Name
	  */
	public String getName2();

    /** Column name NombreTitular */
    public static final String COLUMNNAME_NombreTitular = "NombreTitular";

	/** Set NombreTitular	  */
	public void setNombreTitular (String NombreTitular);

	/** Get NombreTitular	  */
	public String getNombreTitular();

    /** Column name Notificable */
    public static final String COLUMNNAME_Notificable = "Notificable";

	/** Set Notificable	  */
	public void setNotificable (boolean Notificable);

	/** Get Notificable	  */
	public boolean isNotificable();

    /** Column name NotificationAssignTo_ID */
    public static final String COLUMNNAME_NotificationAssignTo_ID = "NotificationAssignTo_ID";

	/** Set NotificationAssignTo_ID	  */
	public void setNotificationAssignTo_ID (int NotificationAssignTo_ID);

	/** Get NotificationAssignTo_ID	  */
	public int getNotificationAssignTo_ID();

    /** Column name NotificationDateFrom */
    public static final String COLUMNNAME_NotificationDateFrom = "NotificationDateFrom";

	/** Set NotificationDateFrom	  */
	public void setNotificationDateFrom (Timestamp NotificationDateFrom);

	/** Get NotificationDateFrom	  */
	public Timestamp getNotificationDateFrom();

    /** Column name NotificationDateTo */
    public static final String COLUMNNAME_NotificationDateTo = "NotificationDateTo";

	/** Set NotificationDateTo	  */
	public void setNotificationDateTo (Timestamp NotificationDateTo);

	/** Get NotificationDateTo	  */
	public Timestamp getNotificationDateTo();

    /** Column name NotificationVia */
    public static final String COLUMNNAME_NotificationVia = "NotificationVia";

	/** Set NotificationVia.
	  * Via Notificacion
	  */
	public void setNotificationVia (String NotificationVia);

	/** Get NotificationVia.
	  * Via Notificacion
	  */
	public String getNotificationVia();

    /** Column name Notificator_ID */
    public static final String COLUMNNAME_Notificator_ID = "Notificator_ID";

	/** Set Notificator_ID	  */
	public void setNotificator_ID (int Notificator_ID);

	/** Get Notificator_ID	  */
	public int getNotificator_ID();

    /** Column name NroCuentaTitular */
    public static final String COLUMNNAME_NroCuentaTitular = "NroCuentaTitular";

	/** Set NroCuentaTitular	  */
	public void setNroCuentaTitular (String NroCuentaTitular);

	/** Get NroCuentaTitular	  */
	public String getNroCuentaTitular();

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

    /** Column name Parametros */
    public static final String COLUMNNAME_Parametros = "Parametros";

	/** Set Parametros	  */
	public void setParametros (int Parametros);

	/** Get Parametros	  */
	public int getParametros();

    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/** Set Payment amount.
	  * Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt);

	/** Get Payment amount.
	  * Amount being paid
	  */
	public BigDecimal getPayAmt();

    /** Column name PorcBonif */
    public static final String COLUMNNAME_PorcBonif = "PorcBonif";

	/** Set PorcBonif	  */
	public void setPorcBonif (BigDecimal PorcBonif);

	/** Get PorcBonif	  */
	public BigDecimal getPorcBonif();

    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/** Set ZIP.
	  * Postal code
	  */
	public void setPostal (String Postal);

	/** Get ZIP.
	  * Postal code
	  */
	public String getPostal();

    /** Column name PriorityBase */
    public static final String COLUMNNAME_PriorityBase = "PriorityBase";

	/** Set Priority Base.
	  * Base of Priority
	  */
	public void setPriorityBase (String PriorityBase);

	/** Get Priority Base.
	  * Base of Priority
	  */
	public String getPriorityBase();

    /** Column name PriorityManual */
    public static final String COLUMNNAME_PriorityManual = "PriorityManual";

	/** Set PriorityManual	  */
	public void setPriorityManual (String PriorityManual);

	/** Get PriorityManual	  */
	public String getPriorityManual();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name ProductoAux */
    public static final String COLUMNNAME_ProductoAux = "ProductoAux";

	/** Set ProductoAux	  */
	public void setProductoAux (String ProductoAux);

	/** Get ProductoAux	  */
	public String getProductoAux();

    /** Column name QtyDerivados */
    public static final String COLUMNNAME_QtyDerivados = "QtyDerivados";

	/** Set QtyDerivados	  */
	public void setQtyDerivados (int QtyDerivados);

	/** Get QtyDerivados	  */
	public int getQtyDerivados();

    /** Column name QtyQuote */
    public static final String COLUMNNAME_QtyQuote = "QtyQuote";

	/** Set QtyQuote	  */
	public void setQtyQuote (int QtyQuote);

	/** Get QtyQuote	  */
	public int getQtyQuote();

    /** Column name Receptor_ID */
    public static final String COLUMNNAME_Receptor_ID = "Receptor_ID";

	/** Set Receptor_ID	  */
	public void setReceptor_ID (int Receptor_ID);

	/** Get Receptor_ID	  */
	public int getReceptor_ID();

    /** Column name ReclamoNotificado */
    public static final String COLUMNNAME_ReclamoNotificado = "ReclamoNotificado";

	/** Set ReclamoNotificado	  */
	public void setReclamoNotificado (boolean ReclamoNotificado);

	/** Get ReclamoNotificado	  */
	public boolean isReclamoNotificado();

    /** Column name ReclamoResuelto */
    public static final String COLUMNNAME_ReclamoResuelto = "ReclamoResuelto";

	/** Set ReclamoResuelto	  */
	public void setReclamoResuelto (boolean ReclamoResuelto);

	/** Get ReclamoResuelto	  */
	public boolean isReclamoResuelto();

    /** Column name Renovacion */
    public static final String COLUMNNAME_Renovacion = "Renovacion";

	/** Set Renovacion	  */
	public void setRenovacion (boolean Renovacion);

	/** Get Renovacion	  */
	public boolean isRenovacion();

    /** Column name ResueltoEmpresa */
    public static final String COLUMNNAME_ResueltoEmpresa = "ResueltoEmpresa";

	/** Set ResueltoEmpresa	  */
	public void setResueltoEmpresa (boolean ResueltoEmpresa);

	/** Get ResueltoEmpresa	  */
	public boolean isResueltoEmpresa();

    /** Column name StatusReclamo */
    public static final String COLUMNNAME_StatusReclamo = "StatusReclamo";

	/** Set StatusReclamo	  */
	public void setStatusReclamo (String StatusReclamo);

	/** Get StatusReclamo	  */
	public String getStatusReclamo();

    /** Column name Tasas */
    public static final String COLUMNNAME_Tasas = "Tasas";

	/** Set Tasas	  */
	public void setTasas (int Tasas);

	/** Get Tasas	  */
	public int getTasas();

    /** Column name Telephone */
    public static final String COLUMNNAME_Telephone = "Telephone";

	/** Set Telephone	  */
	public void setTelephone (String Telephone);

	/** Get Telephone	  */
	public String getTelephone();

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

    /** Column name UY_PR_Schedule_ID */
    public static final String COLUMNNAME_UY_PR_Schedule_ID = "UY_PR_Schedule_ID";

	/** Set UY_PR_Schedule	  */
	public void setUY_PR_Schedule_ID (int UY_PR_Schedule_ID);

	/** Get UY_PR_Schedule	  */
	public int getUY_PR_Schedule_ID();

	public I_UY_PR_Schedule getUY_PR_Schedule() throws RuntimeException;

    /** Column name UY_PR_SchTask_ID */
    public static final String COLUMNNAME_UY_PR_SchTask_ID = "UY_PR_SchTask_ID";

	/** Set UY_PR_SchTask	  */
	public void setUY_PR_SchTask_ID (int UY_PR_SchTask_ID);

	/** Get UY_PR_SchTask	  */
	public int getUY_PR_SchTask_ID();

	public I_UY_PR_SchTask getUY_PR_SchTask() throws RuntimeException;

    /** Column name UY_PR_Task_ID */
    public static final String COLUMNNAME_UY_PR_Task_ID = "UY_PR_Task_ID";

	/** Set UY_PR_Task	  */
	public void setUY_PR_Task_ID (int UY_PR_Task_ID);

	/** Get UY_PR_Task	  */
	public int getUY_PR_Task_ID();

	public I_UY_PR_Task getUY_PR_Task() throws RuntimeException;

    /** Column name UY_R_ActionType_ID */
    public static final String COLUMNNAME_UY_R_ActionType_ID = "UY_R_ActionType_ID";

	/** Set UY_R_ActionType	  */
	public void setUY_R_ActionType_ID (int UY_R_ActionType_ID);

	/** Get UY_R_ActionType	  */
	public int getUY_R_ActionType_ID();

	public I_UY_R_ActionType getUY_R_ActionType() throws RuntimeException;

    /** Column name UY_R_ActionType_ID_Notif */
    public static final String COLUMNNAME_UY_R_ActionType_ID_Notif = "UY_R_ActionType_ID_Notif";

	/** Set UY_R_ActionType_ID_Notif	  */
	public void setUY_R_ActionType_ID_Notif (int UY_R_ActionType_ID_Notif);

	/** Get UY_R_ActionType_ID_Notif	  */
	public int getUY_R_ActionType_ID_Notif();

    /** Column name UY_R_Area_ID */
    public static final String COLUMNNAME_UY_R_Area_ID = "UY_R_Area_ID";

	/** Set UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID);

	/** Get UY_R_Area	  */
	public int getUY_R_Area_ID();

	public I_UY_R_Area getUY_R_Area() throws RuntimeException;

    /** Column name UY_R_Canal_ID */
    public static final String COLUMNNAME_UY_R_Canal_ID = "UY_R_Canal_ID";

	/** Set UY_R_Canal	  */
	public void setUY_R_Canal_ID (int UY_R_Canal_ID);

	/** Get UY_R_Canal	  */
	public int getUY_R_Canal_ID();

	public I_UY_R_Canal getUY_R_Canal() throws RuntimeException;

    /** Column name UY_R_CanalNotifica_ID */
    public static final String COLUMNNAME_UY_R_CanalNotifica_ID = "UY_R_CanalNotifica_ID";

	/** Set UY_R_CanalNotifica_ID	  */
	public void setUY_R_CanalNotifica_ID (int UY_R_CanalNotifica_ID);

	/** Get UY_R_CanalNotifica_ID	  */
	public int getUY_R_CanalNotifica_ID();

    /** Column name UY_R_Cause_ID */
    public static final String COLUMNNAME_UY_R_Cause_ID = "UY_R_Cause_ID";

	/** Set UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID);

	/** Get UY_R_Cause	  */
	public int getUY_R_Cause_ID();

	public I_UY_R_Cause getUY_R_Cause() throws RuntimeException;

    /** Column name UY_R_CedulaCuenta_ID */
    public static final String COLUMNNAME_UY_R_CedulaCuenta_ID = "UY_R_CedulaCuenta_ID";

	/** Set UY_R_CedulaCuenta	  */
	public void setUY_R_CedulaCuenta_ID (int UY_R_CedulaCuenta_ID);

	/** Get UY_R_CedulaCuenta	  */
	public int getUY_R_CedulaCuenta_ID();

	public I_UY_R_CedulaCuenta getUY_R_CedulaCuenta() throws RuntimeException;

    /** Column name UY_R_Comercio_ID */
    public static final String COLUMNNAME_UY_R_Comercio_ID = "UY_R_Comercio_ID";

	/** Set UY_R_Comercio	  */
	public void setUY_R_Comercio_ID (int UY_R_Comercio_ID);

	/** Get UY_R_Comercio	  */
	public int getUY_R_Comercio_ID();

	public I_UY_R_Comercio getUY_R_Comercio() throws RuntimeException;

    /** Column name UY_R_PtoResolucion_ID */
    public static final String COLUMNNAME_UY_R_PtoResolucion_ID = "UY_R_PtoResolucion_ID";

	/** Set UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID);

	/** Get UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID();

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException;

    /** Column name UY_R_Reclamo_ID */
    public static final String COLUMNNAME_UY_R_Reclamo_ID = "UY_R_Reclamo_ID";

	/** Set UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID);

	/** Get UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID();

    /** Column name UY_R_Reclamo_ID_2 */
    public static final String COLUMNNAME_UY_R_Reclamo_ID_2 = "UY_R_Reclamo_ID_2";

	/** Set UY_R_Reclamo_ID_2	  */
	public void setUY_R_Reclamo_ID_2 (int UY_R_Reclamo_ID_2);

	/** Get UY_R_Reclamo_ID_2	  */
	public int getUY_R_Reclamo_ID_2();

    /** Column name uy_r_subcause_id_1 */
    public static final String COLUMNNAME_uy_r_subcause_id_1 = "uy_r_subcause_id_1";

	/** Set uy_r_subcause_id_1	  */
	public void setuy_r_subcause_id_1 (int uy_r_subcause_id_1);

	/** Get uy_r_subcause_id_1	  */
	public int getuy_r_subcause_id_1();

    /** Column name uy_r_subcause_id_2 */
    public static final String COLUMNNAME_uy_r_subcause_id_2 = "uy_r_subcause_id_2";

	/** Set uy_r_subcause_id_2	  */
	public void setuy_r_subcause_id_2 (int uy_r_subcause_id_2);

	/** Get uy_r_subcause_id_2	  */
	public int getuy_r_subcause_id_2();

    /** Column name uy_r_subcause_id_3 */
    public static final String COLUMNNAME_uy_r_subcause_id_3 = "uy_r_subcause_id_3";

	/** Set uy_r_subcause_id_3	  */
	public void setuy_r_subcause_id_3 (int uy_r_subcause_id_3);

	/** Get uy_r_subcause_id_3	  */
	public int getuy_r_subcause_id_3();

    /** Column name uy_r_subcause_id_4 */
    public static final String COLUMNNAME_uy_r_subcause_id_4 = "uy_r_subcause_id_4";

	/** Set uy_r_subcause_id_4	  */
	public void setuy_r_subcause_id_4 (int uy_r_subcause_id_4);

	/** Get uy_r_subcause_id_4	  */
	public int getuy_r_subcause_id_4();

    /** Column name UY_R_Type_ID */
    public static final String COLUMNNAME_UY_R_Type_ID = "UY_R_Type_ID";

	/** Set UY_R_Type	  */
	public void setUY_R_Type_ID (int UY_R_Type_ID);

	/** Get UY_R_Type	  */
	public int getUY_R_Type_ID();

	public I_UY_R_Type getUY_R_Type() throws RuntimeException;

    /** Column name Validez */
    public static final String COLUMNNAME_Validez = "Validez";

	/** Set Validez	  */
	public void setValidez (String Validez);

	/** Get Validez	  */
	public String getValidez();

    /** Column name Vencido */
    public static final String COLUMNNAME_Vencido = "Vencido";

	/** Set Vencido	  */
	public void setVencido (boolean Vencido);

	/** Get Vencido	  */
	public boolean isVencido();
}
