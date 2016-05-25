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

/** Generated Interface for UY_R_Gestion
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_Gestion 
{

    /** TableName=UY_R_Gestion */
    public static final String Table_Name = "UY_R_Gestion";

    /** AD_Table_ID=1000529 */
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

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Address 1.
	  * Address line 1 for this location
	  */
	public void setAddress1 (String Address1);

	/** Get Address 1.
	  * Address line 1 for this location
	  */
	public String getAddress1();

    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/** Set Address 2.
	  * Address line 2 for this location
	  */
	public void setAddress2 (String Address2);

	/** Get Address 2.
	  * Address line 2 for this location
	  */
	public String getAddress2();

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

    /** Column name Barrio */
    public static final String COLUMNNAME_Barrio = "Barrio";

	/** Set Barrio	  */
	public void setBarrio (String Barrio);

	/** Get Barrio	  */
	public String getBarrio();

    /** Column name Bloque */
    public static final String COLUMNNAME_Bloque = "Bloque";

	/** Set Bloque	  */
	public void setBloque (String Bloque);

	/** Get Bloque	  */
	public String getBloque();

    /** Column name Calle */
    public static final String COLUMNNAME_Calle = "Calle";

	/** Set Calle	  */
	public void setCalle (String Calle);

	/** Get Calle	  */
	public String getCalle();

    /** Column name Calle1 */
    public static final String COLUMNNAME_Calle1 = "Calle1";

	/** Set Calle1	  */
	public void setCalle1 (String Calle1);

	/** Get Calle1	  */
	public String getCalle1();

    /** Column name Calle2 */
    public static final String COLUMNNAME_Calle2 = "Calle2";

	/** Set Calle2	  */
	public void setCalle2 (String Calle2);

	/** Get Calle2	  */
	public String getCalle2();

    /** Column name ConfirmacionEscrita */
    public static final String COLUMNNAME_ConfirmacionEscrita = "ConfirmacionEscrita";

	/** Set ConfirmacionEscrita	  */
	public void setConfirmacionEscrita (boolean ConfirmacionEscrita);

	/** Get ConfirmacionEscrita	  */
	public boolean isConfirmacionEscrita();

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

    /** Column name DateAction */
    public static final String COLUMNNAME_DateAction = "DateAction";

	/** Set DateAction	  */
	public void setDateAction (Timestamp DateAction);

	/** Get DateAction	  */
	public Timestamp getDateAction();

    /** Column name DateExecuted */
    public static final String COLUMNNAME_DateExecuted = "DateExecuted";

	/** Set DateExecuted	  */
	public void setDateExecuted (Timestamp DateExecuted);

	/** Get DateExecuted	  */
	public Timestamp getDateExecuted();

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

    /** Column name DeliveryTime */
    public static final String COLUMNNAME_DeliveryTime = "DeliveryTime";

	/** Set DeliveryTime	  */
	public void setDeliveryTime (String DeliveryTime);

	/** Get DeliveryTime	  */
	public String getDeliveryTime();

    /** Column name DeliveryTime2 */
    public static final String COLUMNNAME_DeliveryTime2 = "DeliveryTime2";

	/** Set DeliveryTime2	  */
	public void setDeliveryTime2 (String DeliveryTime2);

	/** Get DeliveryTime2	  */
	public String getDeliveryTime2();

    /** Column name DeliveryTime3 */
    public static final String COLUMNNAME_DeliveryTime3 = "DeliveryTime3";

	/** Set DeliveryTime3	  */
	public void setDeliveryTime3 (String DeliveryTime3);

	/** Get DeliveryTime3	  */
	public String getDeliveryTime3();

    /** Column name Depto */
    public static final String COLUMNNAME_Depto = "Depto";

	/** Set Depto	  */
	public void setDepto (String Depto);

	/** Get Depto	  */
	public String getDepto();

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

    /** Column name DetailInfo */
    public static final String COLUMNNAME_DetailInfo = "DetailInfo";

	/** Set Detail Information.
	  * Additional Detail Information
	  */
	public void setDetailInfo (String DetailInfo);

	/** Get Detail Information.
	  * Additional Detail Information
	  */
	public String getDetailInfo();

    /** Column name DomicilioActual */
    public static final String COLUMNNAME_DomicilioActual = "DomicilioActual";

	/** Set DomicilioActual	  */
	public void setDomicilioActual (String DomicilioActual);

	/** Get DomicilioActual	  */
	public String getDomicilioActual();

    /** Column name Esquina */
    public static final String COLUMNNAME_Esquina = "Esquina";

	/** Set Esquina	  */
	public void setEsquina (String Esquina);

	/** Get Esquina	  */
	public String getEsquina();

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

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

    /** Column name HoraEntrega1 */
    public static final String COLUMNNAME_HoraEntrega1 = "HoraEntrega1";

	/** Set HoraEntrega1	  */
	public void setHoraEntrega1 (Timestamp HoraEntrega1);

	/** Get HoraEntrega1	  */
	public Timestamp getHoraEntrega1();

    /** Column name HoraEntrega2 */
    public static final String COLUMNNAME_HoraEntrega2 = "HoraEntrega2";

	/** Set HoraEntrega2	  */
	public void setHoraEntrega2 (Timestamp HoraEntrega2);

	/** Get HoraEntrega2	  */
	public Timestamp getHoraEntrega2();

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

    /** Column name IsExecuted */
    public static final String COLUMNNAME_IsExecuted = "IsExecuted";

	/** Set IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted);

	/** Get IsExecuted	  */
	public boolean isExecuted();

    /** Column name IsOver */
    public static final String COLUMNNAME_IsOver = "IsOver";

	/** Set IsOver.
	  * IsOver
	  */
	public void setIsOver (boolean IsOver);

	/** Get IsOver.
	  * IsOver
	  */
	public boolean isOver();

    /** Column name Jueves */
    public static final String COLUMNNAME_Jueves = "Jueves";

	/** Set Jueves	  */
	public void setJueves (boolean Jueves);

	/** Get Jueves	  */
	public boolean isJueves();

    /** Column name Justification */
    public static final String COLUMNNAME_Justification = "Justification";

	/** Set Justification	  */
	public void setJustification (String Justification);

	/** Get Justification	  */
	public String getJustification();

    /** Column name JustificationApproved */
    public static final String COLUMNNAME_JustificationApproved = "JustificationApproved";

	/** Set JustificationApproved	  */
	public void setJustificationApproved (String JustificationApproved);

	/** Get JustificationApproved	  */
	public String getJustificationApproved();

    /** Column name localidad */
    public static final String COLUMNNAME_localidad = "localidad";

	/** Set localidad	  */
	public void setlocalidad (String localidad);

	/** Get localidad	  */
	public String getlocalidad();

    /** Column name Lunes */
    public static final String COLUMNNAME_Lunes = "Lunes";

	/** Set Lunes	  */
	public void setLunes (boolean Lunes);

	/** Get Lunes	  */
	public boolean isLunes();

    /** Column name MailText */
    public static final String COLUMNNAME_MailText = "MailText";

	/** Set Mail Text.
	  * Text used for Mail message
	  */
	public void setMailText (String MailText);

	/** Get Mail Text.
	  * Text used for Mail message
	  */
	public String getMailText();

    /** Column name Manzana */
    public static final String COLUMNNAME_Manzana = "Manzana";

	/** Set Manzana	  */
	public void setManzana (String Manzana);

	/** Get Manzana	  */
	public String getManzana();

    /** Column name Martes */
    public static final String COLUMNNAME_Martes = "Martes";

	/** Set Martes	  */
	public void setMartes (boolean Martes);

	/** Get Martes	  */
	public boolean isMartes();

    /** Column name Miercoles */
    public static final String COLUMNNAME_Miercoles = "Miercoles";

	/** Set Miercoles	  */
	public void setMiercoles (boolean Miercoles);

	/** Get Miercoles	  */
	public boolean isMiercoles();

    /** Column name NroApto */
    public static final String COLUMNNAME_NroApto = "NroApto";

	/** Set NroApto	  */
	public void setNroApto (String NroApto);

	/** Get NroApto	  */
	public String getNroApto();

    /** Column name NroPuerta */
    public static final String COLUMNNAME_NroPuerta = "NroPuerta";

	/** Set NroPuerta	  */
	public void setNroPuerta (String NroPuerta);

	/** Get NroPuerta	  */
	public String getNroPuerta();

    /** Column name NuevoDestino */
    public static final String COLUMNNAME_NuevoDestino = "NuevoDestino";

	/** Set NuevoDestino	  */
	public void setNuevoDestino (String NuevoDestino);

	/** Get NuevoDestino	  */
	public String getNuevoDestino();

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

    /** Column name Receptor_ID */
    public static final String COLUMNNAME_Receptor_ID = "Receptor_ID";

	/** Set Receptor_ID	  */
	public void setReceptor_ID (int Receptor_ID);

	/** Get Receptor_ID	  */
	public int getReceptor_ID();

    /** Column name ReclamoAccionType */
    public static final String COLUMNNAME_ReclamoAccionType = "ReclamoAccionType";

	/** Set ReclamoAccionType	  */
	public void setReclamoAccionType (String ReclamoAccionType);

	/** Get ReclamoAccionType	  */
	public String getReclamoAccionType();

    /** Column name ReclamoResuelto */
    public static final String COLUMNNAME_ReclamoResuelto = "ReclamoResuelto";

	/** Set ReclamoResuelto	  */
	public void setReclamoResuelto (boolean ReclamoResuelto);

	/** Get ReclamoResuelto	  */
	public boolean isReclamoResuelto();

    /** Column name Responsable_ID */
    public static final String COLUMNNAME_Responsable_ID = "Responsable_ID";

	/** Set Responsable_ID	  */
	public void setResponsable_ID (int Responsable_ID);

	/** Get Responsable_ID	  */
	public int getResponsable_ID();

    /** Column name ResueltoType */
    public static final String COLUMNNAME_ResueltoType = "ResueltoType";

	/** Set ResueltoType.
	  * ResueltoType
	  */
	public void setResueltoType (String ResueltoType);

	/** Get ResueltoType.
	  * ResueltoType
	  */
	public String getResueltoType();

    /** Column name Sabado */
    public static final String COLUMNNAME_Sabado = "Sabado";

	/** Set Sabado	  */
	public void setSabado (boolean Sabado);

	/** Get Sabado	  */
	public boolean isSabado();

    /** Column name Solar */
    public static final String COLUMNNAME_Solar = "Solar";

	/** Set Solar	  */
	public void setSolar (String Solar);

	/** Get Solar	  */
	public String getSolar();

    /** Column name Telephone */
    public static final String COLUMNNAME_Telephone = "Telephone";

	/** Set Telephone	  */
	public void setTelephone (String Telephone);

	/** Get Telephone	  */
	public String getTelephone();

    /** Column name Torre */
    public static final String COLUMNNAME_Torre = "Torre";

	/** Set Torre	  */
	public void setTorre (String Torre);

	/** Get Torre	  */
	public String getTorre();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Total Lines.
	  * Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines);

	/** Get Total Lines.
	  * Total of all document lines
	  */
	public BigDecimal getTotalLines();

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

    /** Column name UserConfirmaEscrita_ID */
    public static final String COLUMNNAME_UserConfirmaEscrita_ID = "UserConfirmaEscrita_ID";

	/** Set UserConfirmaEscrita_ID	  */
	public void setUserConfirmaEscrita_ID (int UserConfirmaEscrita_ID);

	/** Get UserConfirmaEscrita_ID	  */
	public int getUserConfirmaEscrita_ID();

    /** Column name UY_DeliveryPoint_ID */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID = "UY_DeliveryPoint_ID";

	/** Set UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID);

	/** Get UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID();

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException;

    /** Column name UY_DeliveryPoint_ID_SubAge */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID_SubAge = "UY_DeliveryPoint_ID_SubAge";

	/** Set UY_DeliveryPoint_ID_SubAge	  */
	public void setUY_DeliveryPoint_ID_SubAge (int UY_DeliveryPoint_ID_SubAge);

	/** Get UY_DeliveryPoint_ID_SubAge	  */
	public int getUY_DeliveryPoint_ID_SubAge();

    /** Column name UY_DeliveryPoint_ID_To */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID_To = "UY_DeliveryPoint_ID_To";

	/** Set UY_DeliveryPoint_ID_To	  */
	public void setUY_DeliveryPoint_ID_To (int UY_DeliveryPoint_ID_To);

	/** Get UY_DeliveryPoint_ID_To	  */
	public int getUY_DeliveryPoint_ID_To();

	public I_UY_DeliveryPoint getUY_DeliveryPoint_To() throws RuntimeException;

    /** Column name UY_Departamentos_ID */
    public static final String COLUMNNAME_UY_Departamentos_ID = "UY_Departamentos_ID";

	/** Set Departamentos o regiones por Pais	  */
	public void setUY_Departamentos_ID (int UY_Departamentos_ID);

	/** Get Departamentos o regiones por Pais	  */
	public int getUY_Departamentos_ID();

	public I_UY_Departamentos getUY_Departamentos() throws RuntimeException;

    /** Column name UY_Localidades_ID */
    public static final String COLUMNNAME_UY_Localidades_ID = "UY_Localidades_ID";

	/** Set Localidades por Departamentos	  */
	public void setUY_Localidades_ID (int UY_Localidades_ID);

	/** Get Localidades por Departamentos	  */
	public int getUY_Localidades_ID();

	public I_UY_Localidades getUY_Localidades() throws RuntimeException;

    /** Column name UY_R_AccionPtoResol_ID */
    public static final String COLUMNNAME_UY_R_AccionPtoResol_ID = "UY_R_AccionPtoResol_ID";

	/** Set UY_R_AccionPtoResol	  */
	public void setUY_R_AccionPtoResol_ID (int UY_R_AccionPtoResol_ID);

	/** Get UY_R_AccionPtoResol	  */
	public int getUY_R_AccionPtoResol_ID();

	public I_UY_R_AccionPtoResol getUY_R_AccionPtoResol() throws RuntimeException;

    /** Column name UY_R_Action_ID */
    public static final String COLUMNNAME_UY_R_Action_ID = "UY_R_Action_ID";

	/** Set UY_R_Action	  */
	public void setUY_R_Action_ID (int UY_R_Action_ID);

	/** Get UY_R_Action	  */
	public int getUY_R_Action_ID();

	public I_UY_R_Action getUY_R_Action() throws RuntimeException;

    /** Column name UY_R_Ajuste_ID */
    public static final String COLUMNNAME_UY_R_Ajuste_ID = "UY_R_Ajuste_ID";

	/** Set UY_R_Ajuste	  */
	public void setUY_R_Ajuste_ID (int UY_R_Ajuste_ID);

	/** Get UY_R_Ajuste	  */
	public int getUY_R_Ajuste_ID();

	public I_UY_R_Ajuste getUY_R_Ajuste() throws RuntimeException;

    /** Column name UY_R_Area_ID */
    public static final String COLUMNNAME_UY_R_Area_ID = "UY_R_Area_ID";

	/** Set UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID);

	/** Get UY_R_Area	  */
	public int getUY_R_Area_ID();

	public I_UY_R_Area getUY_R_Area() throws RuntimeException;

    /** Column name UY_R_Gestion_ID */
    public static final String COLUMNNAME_UY_R_Gestion_ID = "UY_R_Gestion_ID";

	/** Set UY_R_Gestion	  */
	public void setUY_R_Gestion_ID (int UY_R_Gestion_ID);

	/** Get UY_R_Gestion	  */
	public int getUY_R_Gestion_ID();

    /** Column name UY_R_MotivoCierre_ID */
    public static final String COLUMNNAME_UY_R_MotivoCierre_ID = "UY_R_MotivoCierre_ID";

	/** Set UY_R_MotivoCierre	  */
	public void setUY_R_MotivoCierre_ID (int UY_R_MotivoCierre_ID);

	/** Get UY_R_MotivoCierre	  */
	public int getUY_R_MotivoCierre_ID();

	public I_UY_R_MotivoCierre getUY_R_MotivoCierre() throws RuntimeException;

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

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException;

    /** Column name Viernes */
    public static final String COLUMNNAME_Viernes = "Viernes";

	/** Set Viernes	  */
	public void setViernes (boolean Viernes);

	/** Get Viernes	  */
	public boolean isViernes();

    /** Column name Vivienda */
    public static final String COLUMNNAME_Vivienda = "Vivienda";

	/** Set Vivienda	  */
	public void setVivienda (String Vivienda);

	/** Get Vivienda	  */
	public String getVivienda();
}
