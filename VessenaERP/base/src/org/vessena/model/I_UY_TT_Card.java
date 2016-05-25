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

/** Generated Interface for UY_TT_Card
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_Card 
{

    /** TableName=UY_TT_Card */
    public static final String Table_Name = "UY_TT_Card";

    /** AD_Table_ID=1000574 */
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

    /** Column name AddressOld */
    public static final String COLUMNNAME_AddressOld = "AddressOld";

	/** Set AddressOld	  */
	public void setAddressOld (String AddressOld);

	/** Get AddressOld	  */
	public String getAddressOld();

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

    /** Column name Calle2 */
    public static final String COLUMNNAME_Calle2 = "Calle2";

	/** Set Calle2	  */
	public void setCalle2 (String Calle2);

	/** Get Calle2	  */
	public String getCalle2();

    /** Column name CampaniaCod */
    public static final String COLUMNNAME_CampaniaCod = "CampaniaCod";

	/** Set CampaniaCod	  */
	public void setCampaniaCod (int CampaniaCod);

	/** Get CampaniaCod	  */
	public int getCampaniaCod();

    /** Column name CampaniaNom */
    public static final String COLUMNNAME_CampaniaNom = "CampaniaNom";

	/** Set CampaniaNom	  */
	public void setCampaniaNom (String CampaniaNom);

	/** Get CampaniaNom	  */
	public String getCampaniaNom();

    /** Column name CanalCod */
    public static final String COLUMNNAME_CanalCod = "CanalCod";

	/** Set CanalCod	  */
	public void setCanalCod (int CanalCod);

	/** Get CanalCod	  */
	public int getCanalCod();

    /** Column name CanalNom */
    public static final String COLUMNNAME_CanalNom = "CanalNom";

	/** Set CanalNom	  */
	public void setCanalNom (String CanalNom);

	/** Get CanalNom	  */
	public String getCanalNom();

    /** Column name CardAction */
    public static final String COLUMNNAME_CardAction = "CardAction";

	/** Set CardAction	  */
	public void setCardAction (String CardAction);

	/** Get CardAction	  */
	public String getCardAction();

    /** Column name CardDestination */
    public static final String COLUMNNAME_CardDestination = "CardDestination";

	/** Set CardDestination	  */
	public void setCardDestination (String CardDestination);

	/** Get CardDestination	  */
	public String getCardDestination();

    /** Column name CardType */
    public static final String COLUMNNAME_CardType = "CardType";

	/** Set CardType	  */
	public void setCardType (String CardType);

	/** Get CardType	  */
	public String getCardType();

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

    /** Column name CedulaSN */
    public static final String COLUMNNAME_CedulaSN = "CedulaSN";

	/** Set CedulaSN	  */
	public void setCedulaSN (boolean CedulaSN);

	/** Get CedulaSN	  */
	public boolean isCedulaSN();

    /** Column name cliape1 */
    public static final String COLUMNNAME_cliape1 = "cliape1";

	/** Set cliape1	  */
	public void setcliape1 (String cliape1);

	/** Get cliape1	  */
	public String getcliape1();

    /** Column name cliape2 */
    public static final String COLUMNNAME_cliape2 = "cliape2";

	/** Set cliape2	  */
	public void setcliape2 (String cliape2);

	/** Get cliape2	  */
	public String getcliape2();

    /** Column name clicod */
    public static final String COLUMNNAME_clicod = "clicod";

	/** Set clicod	  */
	public void setclicod (String clicod);

	/** Get clicod	  */
	public String getclicod();

    /** Column name CliDigCtrl */
    public static final String COLUMNNAME_CliDigCtrl = "CliDigCtrl";

	/** Set CliDigCtrl	  */
	public void setCliDigCtrl (int CliDigCtrl);

	/** Get CliDigCtrl	  */
	public int getCliDigCtrl();

    /** Column name clinom1 */
    public static final String COLUMNNAME_clinom1 = "clinom1";

	/** Set clinom1	  */
	public void setclinom1 (String clinom1);

	/** Get clinom1	  */
	public String getclinom1();

    /** Column name clinom2 */
    public static final String COLUMNNAME_clinom2 = "clinom2";

	/** Set clinom2	  */
	public void setclinom2 (String clinom2);

	/** Get clinom2	  */
	public String getclinom2();

    /** Column name ConsDomSN */
    public static final String COLUMNNAME_ConsDomSN = "ConsDomSN";

	/** Set ConsDomSN	  */
	public void setConsDomSN (boolean ConsDomSN);

	/** Get ConsDomSN	  */
	public boolean isConsDomSN();

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

    /** Column name CreditLimit */
    public static final String COLUMNNAME_CreditLimit = "CreditLimit";

	/** Set Credit limit.
	  * Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit);

	/** Get Credit limit.
	  * Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit();

    /** Column name DateAction */
    public static final String COLUMNNAME_DateAction = "DateAction";

	/** Set DateAction	  */
	public void setDateAction (Timestamp DateAction);

	/** Get DateAction	  */
	public Timestamp getDateAction();

    /** Column name DateAddressChanged */
    public static final String COLUMNNAME_DateAddressChanged = "DateAddressChanged";

	/** Set DateAddressChanged	  */
	public void setDateAddressChanged (Timestamp DateAddressChanged);

	/** Get DateAddressChanged	  */
	public Timestamp getDateAddressChanged();

    /** Column name DateAssign */
    public static final String COLUMNNAME_DateAssign = "DateAssign";

	/** Set DateAssign.
	  * DateAssign
	  */
	public void setDateAssign (Timestamp DateAssign);

	/** Get DateAssign.
	  * DateAssign
	  */
	public Timestamp getDateAssign();

    /** Column name DateDelivered */
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	/** Set Date Delivered.
	  * Date when the product was delivered
	  */
	public void setDateDelivered (Timestamp DateDelivered);

	/** Get Date Delivered.
	  * Date when the product was delivered
	  */
	public Timestamp getDateDelivered();

    /** Column name DateLastRun */
    public static final String COLUMNNAME_DateLastRun = "DateLastRun";

	/** Set Date last run.
	  * Date the process was last run.
	  */
	public void setDateLastRun (Timestamp DateLastRun);

	/** Get Date last run.
	  * Date the process was last run.
	  */
	public Timestamp getDateLastRun();

    /** Column name DateReceived */
    public static final String COLUMNNAME_DateReceived = "DateReceived";

	/** Set Date received.
	  * Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived);

	/** Get Date received.
	  * Date a product was received
	  */
	public Timestamp getDateReceived();

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

    /** Column name Description2 */
    public static final String COLUMNNAME_Description2 = "Description2";

	/** Set Description2.
	  * Optional short description of the record
	  */
	public void setDescription2 (String Description2);

	/** Get Description2.
	  * Optional short description of the record
	  */
	public String getDescription2();

    /** Column name DiasAccion */
    public static final String COLUMNNAME_DiasAccion = "DiasAccion";

	/** Set DiasAccion	  */
	public void setDiasAccion (int DiasAccion);

	/** Get DiasAccion	  */
	public int getDiasAccion();

    /** Column name DiasAccionView */
    public static final String COLUMNNAME_DiasAccionView = "DiasAccionView";

	/** Set DiasAccionView	  */
	public void setDiasAccionView (int DiasAccionView);

	/** Get DiasAccionView	  */
	public int getDiasAccionView();

    /** Column name DiasActual */
    public static final String COLUMNNAME_DiasActual = "DiasActual";

	/** Set DiasActual	  */
	public void setDiasActual (int DiasActual);

	/** Get DiasActual	  */
	public int getDiasActual();

    /** Column name DiasActualView */
    public static final String COLUMNNAME_DiasActualView = "DiasActualView";

	/** Set DiasActualView	  */
	public void setDiasActualView (int DiasActualView);

	/** Get DiasActualView	  */
	public int getDiasActualView();

    /** Column name DiasEntrega */
    public static final String COLUMNNAME_DiasEntrega = "DiasEntrega";

	/** Set DiasEntrega	  */
	public void setDiasEntrega (String DiasEntrega);

	/** Get DiasEntrega	  */
	public String getDiasEntrega();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (String DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public String getDueDate();

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

    /** Column name Esquina2 */
    public static final String COLUMNNAME_Esquina2 = "Esquina2";

	/** Set Esquina2	  */
	public void setEsquina2 (String Esquina2);

	/** Get Esquina2	  */
	public String getEsquina2();

    /** Column name FchActivado */
    public static final String COLUMNNAME_FchActivado = "FchActivado";

	/** Set FchActivado	  */
	public void setFchActivado (Timestamp FchActivado);

	/** Get FchActivado	  */
	public Timestamp getFchActivado();

    /** Column name FchAprobado */
    public static final String COLUMNNAME_FchAprobado = "FchAprobado";

	/** Set FchAprobado	  */
	public void setFchAprobado (Timestamp FchAprobado);

	/** Get FchAprobado	  */
	public Timestamp getFchAprobado();

    /** Column name FchCredito */
    public static final String COLUMNNAME_FchCredito = "FchCredito";

	/** Set FchCredito	  */
	public void setFchCredito (Timestamp FchCredito);

	/** Get FchCredito	  */
	public Timestamp getFchCredito();

    /** Column name FchEmbozado */
    public static final String COLUMNNAME_FchEmbozado = "FchEmbozado";

	/** Set FchEmbozado	  */
	public void setFchEmbozado (Timestamp FchEmbozado);

	/** Get FchEmbozado	  */
	public Timestamp getFchEmbozado();

    /** Column name FchObservado */
    public static final String COLUMNNAME_FchObservado = "FchObservado";

	/** Set FchObservado	  */
	public void setFchObservado (Timestamp FchObservado);

	/** Get FchObservado	  */
	public Timestamp getFchObservado();

    /** Column name FchSolicitud */
    public static final String COLUMNNAME_FchSolicitud = "FchSolicitud";

	/** Set FchSolicitud	  */
	public void setFchSolicitud (Timestamp FchSolicitud);

	/** Get FchSolicitud	  */
	public Timestamp getFchSolicitud();

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

    /** Column name GrpCtaCte */
    public static final String COLUMNNAME_GrpCtaCte = "GrpCtaCte";

	/** Set GrpCtaCte	  */
	public void setGrpCtaCte (int GrpCtaCte);

	/** Get GrpCtaCte	  */
	public int getGrpCtaCte();

    /** Column name HoraEntrega1 */
    public static final String COLUMNNAME_HoraEntrega1 = "HoraEntrega1";

	/** Set HoraEntrega1	  */
	public void setHoraEntrega1 (String HoraEntrega1);

	/** Get HoraEntrega1	  */
	public String getHoraEntrega1();

    /** Column name HoraEntrega2 */
    public static final String COLUMNNAME_HoraEntrega2 = "HoraEntrega2";

	/** Set HoraEntrega2	  */
	public void setHoraEntrega2 (String HoraEntrega2);

	/** Get HoraEntrega2	  */
	public String getHoraEntrega2();

    /** Column name InVerification */
    public static final String COLUMNNAME_InVerification = "InVerification";

	/** Set InVerification	  */
	public void setInVerification (boolean InVerification);

	/** Get InVerification	  */
	public boolean isInVerification();

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

    /** Column name IsAddressChanged */
    public static final String COLUMNNAME_IsAddressChanged = "IsAddressChanged";

	/** Set IsAddressChanged	  */
	public void setIsAddressChanged (boolean IsAddressChanged);

	/** Get IsAddressChanged	  */
	public boolean isAddressChanged();

    /** Column name IsDeliverable */
    public static final String COLUMNNAME_IsDeliverable = "IsDeliverable";

	/** Set IsDeliverable	  */
	public void setIsDeliverable (boolean IsDeliverable);

	/** Get IsDeliverable	  */
	public boolean isDeliverable();

    /** Column name IsLegajoCheckSaved */
    public static final String COLUMNNAME_IsLegajoCheckSaved = "IsLegajoCheckSaved";

	/** Set IsLegajoCheckSaved	  */
	public void setIsLegajoCheckSaved (boolean IsLegajoCheckSaved);

	/** Get IsLegajoCheckSaved	  */
	public boolean isLegajoCheckSaved();

    /** Column name IsLegajoSaved */
    public static final String COLUMNNAME_IsLegajoSaved = "IsLegajoSaved";

	/** Set IsLegajoSaved	  */
	public void setIsLegajoSaved (boolean IsLegajoSaved);

	/** Get IsLegajoSaved	  */
	public boolean isLegajoSaved();

    /** Column name IsMaster */
    public static final String COLUMNNAME_IsMaster = "IsMaster";

	/** Set IsMaster	  */
	public void setIsMaster (boolean IsMaster);

	/** Get IsMaster	  */
	public boolean isMaster();

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Printed.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted);

	/** Get Printed.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

    /** Column name IsReadyAcuse */
    public static final String COLUMNNAME_IsReadyAcuse = "IsReadyAcuse";

	/** Set IsReadyAcuse	  */
	public void setIsReadyAcuse (boolean IsReadyAcuse);

	/** Get IsReadyAcuse	  */
	public boolean isReadyAcuse();

    /** Column name IsReceived */
    public static final String COLUMNNAME_IsReceived = "IsReceived";

	/** Set IsReceived	  */
	public void setIsReceived (boolean IsReceived);

	/** Get IsReceived	  */
	public boolean isReceived();

    /** Column name IsRequested */
    public static final String COLUMNNAME_IsRequested = "IsRequested";

	/** Set IsRequested	  */
	public void setIsRequested (boolean IsRequested);

	/** Get IsRequested	  */
	public boolean isRequested();

    /** Column name IsRetained */
    public static final String COLUMNNAME_IsRetained = "IsRetained";

	/** Set IsRetained	  */
	public void setIsRetained (boolean IsRetained);

	/** Get IsRetained	  */
	public boolean isRetained();

    /** Column name IsReturned */
    public static final String COLUMNNAME_IsReturned = "IsReturned";

	/** Set IsReturned	  */
	public void setIsReturned (boolean IsReturned);

	/** Get IsReturned	  */
	public boolean isReturned();

    /** Column name IsValeSigned */
    public static final String COLUMNNAME_IsValeSigned = "IsValeSigned";

	/** Set IsValeSigned	  */
	public void setIsValeSigned (boolean IsValeSigned);

	/** Get IsValeSigned	  */
	public boolean isValeSigned();

    /** Column name Jueves */
    public static final String COLUMNNAME_Jueves = "Jueves";

	/** Set Jueves	  */
	public void setJueves (boolean Jueves);

	/** Get Jueves	  */
	public boolean isJueves();

    /** Column name Levante */
    public static final String COLUMNNAME_Levante = "Levante";

	/** Set Levante.
	  * Levante
	  */
	public void setLevante (String Levante);

	/** Get Levante.
	  * Levante
	  */
	public String getLevante();

    /** Column name Link_ID */
    public static final String COLUMNNAME_Link_ID = "Link_ID";

	/** Set Link_ID	  */
	public void setLink_ID (int Link_ID);

	/** Get Link_ID	  */
	public int getLink_ID();

    /** Column name localidad */
    public static final String COLUMNNAME_localidad = "localidad";

	/** Set localidad	  */
	public void setlocalidad (String localidad);

	/** Get localidad	  */
	public String getlocalidad();

    /** Column name Localidad2 */
    public static final String COLUMNNAME_Localidad2 = "Localidad2";

	/** Set Localidad2	  */
	public void setLocalidad2 (String Localidad2);

	/** Get Localidad2	  */
	public String getLocalidad2();

    /** Column name LocalidadOld */
    public static final String COLUMNNAME_LocalidadOld = "LocalidadOld";

	/** Set LocalidadOld	  */
	public void setLocalidadOld (String LocalidadOld);

	/** Get LocalidadOld	  */
	public String getLocalidadOld();

    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/** Set Locator Key.
	  * Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue);

	/** Get Locator Key.
	  * Key of the Warehouse Locator
	  */
	public int getLocatorValue();

    /** Column name Lunes */
    public static final String COLUMNNAME_Lunes = "Lunes";

	/** Set Lunes	  */
	public void setLunes (boolean Lunes);

	/** Get Lunes	  */
	public boolean isLunes();

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

    /** Column name NeedPrint */
    public static final String COLUMNNAME_NeedPrint = "NeedPrint";

	/** Set NeedPrint	  */
	public void setNeedPrint (boolean NeedPrint);

	/** Get NeedPrint	  */
	public boolean isNeedPrint();

    /** Column name NotDeliverableAction */
    public static final String COLUMNNAME_NotDeliverableAction = "NotDeliverableAction";

	/** Set NotDeliverableAction	  */
	public void setNotDeliverableAction (String NotDeliverableAction);

	/** Get NotDeliverableAction	  */
	public String getNotDeliverableAction();

    /** Column name NotValidText */
    public static final String COLUMNNAME_NotValidText = "NotValidText";

	/** Set NotValidText	  */
	public void setNotValidText (String NotValidText);

	/** Get NotValidText	  */
	public String getNotValidText();

    /** Column name nrodoc */
    public static final String COLUMNNAME_nrodoc = "nrodoc";

	/** Set nrodoc	  */
	public void setnrodoc (String nrodoc);

	/** Get nrodoc	  */
	public String getnrodoc();

    /** Column name NroPuerta2 */
    public static final String COLUMNNAME_NroPuerta2 = "NroPuerta2";

	/** Set NroPuerta2	  */
	public void setNroPuerta2 (String NroPuerta2);

	/** Get NroPuerta2	  */
	public String getNroPuerta2();

    /** Column name NroTarjetaNueva */
    public static final String COLUMNNAME_NroTarjetaNueva = "NroTarjetaNueva";

	/** Set NroTarjetaNueva	  */
	public void setNroTarjetaNueva (String NroTarjetaNueva);

	/** Get NroTarjetaNueva	  */
	public String getNroTarjetaNueva();

    /** Column name NroTarjetaTitular */
    public static final String COLUMNNAME_NroTarjetaTitular = "NroTarjetaTitular";

	/** Set NroTarjetaTitular	  */
	public void setNroTarjetaTitular (String NroTarjetaTitular);

	/** Get NroTarjetaTitular	  */
	public String getNroTarjetaTitular();

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

    /** Column name Postal2 */
    public static final String COLUMNNAME_Postal2 = "Postal2";

	/** Set Postal2	  */
	public void setPostal2 (String Postal2);

	/** Get Postal2	  */
	public String getPostal2();

    /** Column name PostalOld */
    public static final String COLUMNNAME_PostalOld = "PostalOld";

	/** Set PostalOld	  */
	public void setPostalOld (String PostalOld);

	/** Get PostalOld	  */
	public String getPostalOld();

    /** Column name PrintDoc1 */
    public static final String COLUMNNAME_PrintDoc1 = "PrintDoc1";

	/** Set PrintDoc1	  */
	public void setPrintDoc1 (boolean PrintDoc1);

	/** Get PrintDoc1	  */
	public boolean isPrintDoc1();

    /** Column name PrintDoc2 */
    public static final String COLUMNNAME_PrintDoc2 = "PrintDoc2";

	/** Set PrintDoc2.
	  * PrintDoc2
	  */
	public void setPrintDoc2 (boolean PrintDoc2);

	/** Get PrintDoc2.
	  * PrintDoc2
	  */
	public boolean isPrintDoc2();

    /** Column name PrintDoc3 */
    public static final String COLUMNNAME_PrintDoc3 = "PrintDoc3";

	/** Set PrintDoc3.
	  * PrintDoc3
	  */
	public void setPrintDoc3 (boolean PrintDoc3);

	/** Get PrintDoc3.
	  * PrintDoc3
	  */
	public boolean isPrintDoc3();

    /** Column name PrintDoc4 */
    public static final String COLUMNNAME_PrintDoc4 = "PrintDoc4";

	/** Set PrintDoc4.
	  * PrintDoc4
	  */
	public void setPrintDoc4 (boolean PrintDoc4);

	/** Get PrintDoc4.
	  * PrintDoc4
	  */
	public boolean isPrintDoc4();

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

    /** Column name QtyCall */
    public static final String COLUMNNAME_QtyCall = "QtyCall";

	/** Set QtyCall	  */
	public void setQtyCall (int QtyCall);

	/** Get QtyCall	  */
	public int getQtyCall();

    /** Column name QtyCarrier */
    public static final String COLUMNNAME_QtyCarrier = "QtyCarrier";

	/** Set QtyCarrier	  */
	public void setQtyCarrier (int QtyCarrier);

	/** Get QtyCarrier	  */
	public int getQtyCarrier();

    /** Column name QtyCarrierCounted */
    public static final String COLUMNNAME_QtyCarrierCounted = "QtyCarrierCounted";

	/** Set QtyCarrierCounted	  */
	public void setQtyCarrierCounted (int QtyCarrierCounted);

	/** Get QtyCarrierCounted	  */
	public int getQtyCarrierCounted();

    /** Column name QtyPlastic */
    public static final String COLUMNNAME_QtyPlastic = "QtyPlastic";

	/** Set QtyPlastic	  */
	public void setQtyPlastic (int QtyPlastic);

	/** Get QtyPlastic	  */
	public int getQtyPlastic();

    /** Column name RecSueSN */
    public static final String COLUMNNAME_RecSueSN = "RecSueSN";

	/** Set RecSueSN	  */
	public void setRecSueSN (boolean RecSueSN);

	/** Get RecSueSN	  */
	public boolean isRecSueSN();

    /** Column name RetainedStatus */
    public static final String COLUMNNAME_RetainedStatus = "RetainedStatus";

	/** Set RetainedStatus	  */
	public void setRetainedStatus (String RetainedStatus);

	/** Get RetainedStatus	  */
	public String getRetainedStatus();

    /** Column name Sabado */
    public static final String COLUMNNAME_Sabado = "Sabado";

	/** Set Sabado	  */
	public void setSabado (boolean Sabado);

	/** Get Sabado	  */
	public boolean isSabado();

    /** Column name sex */
    public static final String COLUMNNAME_sex = "sex";

	/** Set sex	  */
	public void setsex (String sex);

	/** Get sex	  */
	public String getsex();

    /** Column name SolEnvExt */
    public static final String COLUMNNAME_SolEnvExt = "SolEnvExt";

	/** Set SolEnvExt	  */
	public void setSolEnvExt (String SolEnvExt);

	/** Get SolEnvExt	  */
	public String getSolEnvExt();

    /** Column name SolEnvRes */
    public static final String COLUMNNAME_SolEnvRes = "SolEnvRes";

	/** Set SolEnvRes	  */
	public void setSolEnvRes (String SolEnvRes);

	/** Get SolEnvRes	  */
	public String getSolEnvRes();

    /** Column name solnro */
    public static final String COLUMNNAME_solnro = "solnro";

	/** Set solnro	  */
	public void setsolnro (String solnro);

	/** Get solnro	  */
	public String getsolnro();

    /** Column name SolValeSN */
    public static final String COLUMNNAME_SolValeSN = "SolValeSN";

	/** Set SolValeSN	  */
	public void setSolValeSN (boolean SolValeSN);

	/** Get SolValeSN	  */
	public boolean isSolValeSN();

    /** Column name SubAgencia */
    public static final String COLUMNNAME_SubAgencia = "SubAgencia";

	/** Set SubAgencia	  */
	public void setSubAgencia (String SubAgencia);

	/** Get SubAgencia	  */
	public String getSubAgencia();

    /** Column name SucCod */
    public static final String COLUMNNAME_SucCod = "SucCod";

	/** Set SucCod	  */
	public void setSucCod (int SucCod);

	/** Get SucCod	  */
	public int getSucCod();

    /** Column name SucNom */
    public static final String COLUMNNAME_SucNom = "SucNom";

	/** Set SucNom	  */
	public void setSucNom (String SucNom);

	/** Get SucNom	  */
	public String getSucNom();

    /** Column name Telephone */
    public static final String COLUMNNAME_Telephone = "Telephone";

	/** Set Telephone	  */
	public void setTelephone (String Telephone);

	/** Get Telephone	  */
	public String getTelephone();

    /** Column name TipoDomicilio */
    public static final String COLUMNNAME_TipoDomicilio = "TipoDomicilio";

	/** Set TipoDomicilio	  */
	public void setTipoDomicilio (String TipoDomicilio);

	/** Get TipoDomicilio	  */
	public String getTipoDomicilio();

    /** Column name TipoDomicilioOld */
    public static final String COLUMNNAME_TipoDomicilioOld = "TipoDomicilioOld";

	/** Set TipoDomicilioOld	  */
	public void setTipoDomicilioOld (String TipoDomicilioOld);

	/** Get TipoDomicilioOld	  */
	public String getTipoDomicilioOld();

    /** Column name TplCalleNr */
    public static final String COLUMNNAME_TplCalleNr = "TplCalleNr";

	/** Set TplCalleNr	  */
	public void setTplCalleNr (String TplCalleNr);

	/** Get TplCalleNr	  */
	public String getTplCalleNr();

    /** Column name TplDpto */
    public static final String COLUMNNAME_TplDpto = "TplDpto";

	/** Set TplDpto	  */
	public void setTplDpto (String TplDpto);

	/** Get TplDpto	  */
	public String getTplDpto();

    /** Column name TplNro */
    public static final String COLUMNNAME_TplNro = "TplNro";

	/** Set TplNro	  */
	public void setTplNro (String TplNro);

	/** Get TplNro	  */
	public String getTplNro();

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

    /** Column name UY_DeliveryPoint_ID */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID = "UY_DeliveryPoint_ID";

	/** Set UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID);

	/** Get UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID();

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException;

    /** Column name UY_DeliveryPoint_ID_Actual */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID_Actual = "UY_DeliveryPoint_ID_Actual";

	/** Set UY_DeliveryPoint_ID_Actual	  */
	public void setUY_DeliveryPoint_ID_Actual (int UY_DeliveryPoint_ID_Actual);

	/** Get UY_DeliveryPoint_ID_Actual	  */
	public int getUY_DeliveryPoint_ID_Actual();

    /** Column name UY_DeliveryPoint_ID_Req */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID_Req = "UY_DeliveryPoint_ID_Req";

	/** Set UY_DeliveryPoint_ID_Req	  */
	public void setUY_DeliveryPoint_ID_Req (int UY_DeliveryPoint_ID_Req);

	/** Get UY_DeliveryPoint_ID_Req	  */
	public int getUY_DeliveryPoint_ID_Req();

	public I_UY_DeliveryPoint getUY_DeliveryPoint_ID_() throws RuntimeException;

    /** Column name UY_DeliveryPoint_ID_To */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID_To = "UY_DeliveryPoint_ID_To";

	/** Set UY_DeliveryPoint_ID_To	  */
	public void setUY_DeliveryPoint_ID_To (int UY_DeliveryPoint_ID_To);

	/** Get UY_DeliveryPoint_ID_To	  */
	public int getUY_DeliveryPoint_ID_To();

    /** Column name UY_R_AccionPtoResol_ID */
    public static final String COLUMNNAME_UY_R_AccionPtoResol_ID = "UY_R_AccionPtoResol_ID";

	/** Set UY_R_AccionPtoResol	  */
	public void setUY_R_AccionPtoResol_ID (int UY_R_AccionPtoResol_ID);

	/** Get UY_R_AccionPtoResol	  */
	public int getUY_R_AccionPtoResol_ID();

	public I_UY_R_AccionPtoResol getUY_R_AccionPtoResol() throws RuntimeException;

    /** Column name UY_R_Reclamo_ID */
    public static final String COLUMNNAME_UY_R_Reclamo_ID = "UY_R_Reclamo_ID";

	/** Set UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID);

	/** Get UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID();

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException;

    /** Column name UY_TT_Action_ID */
    public static final String COLUMNNAME_UY_TT_Action_ID = "UY_TT_Action_ID";

	/** Set UY_TT_Action	  */
	public void setUY_TT_Action_ID (int UY_TT_Action_ID);

	/** Get UY_TT_Action	  */
	public int getUY_TT_Action_ID();

	public I_UY_TT_Action getUY_TT_Action() throws RuntimeException;

    /** Column name UY_TT_Box_ID */
    public static final String COLUMNNAME_UY_TT_Box_ID = "UY_TT_Box_ID";

	/** Set UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID);

	/** Get UY_TT_Box	  */
	public int getUY_TT_Box_ID();

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException;

    /** Column name UY_TT_Card_ID */
    public static final String COLUMNNAME_UY_TT_Card_ID = "UY_TT_Card_ID";

	/** Set UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID);

	/** Get UY_TT_Card	  */
	public int getUY_TT_Card_ID();

    /** Column name UY_TT_CardStatus_ID */
    public static final String COLUMNNAME_UY_TT_CardStatus_ID = "UY_TT_CardStatus_ID";

	/** Set UY_TT_CardStatus	  */
	public void setUY_TT_CardStatus_ID (int UY_TT_CardStatus_ID);

	/** Get UY_TT_CardStatus	  */
	public int getUY_TT_CardStatus_ID();

	public I_UY_TT_CardStatus getUY_TT_CardStatus() throws RuntimeException;

    /** Column name UY_TT_ChequeraLine_ID */
    public static final String COLUMNNAME_UY_TT_ChequeraLine_ID = "UY_TT_ChequeraLine_ID";

	/** Set UY_TT_ChequeraLine	  */
	public void setUY_TT_ChequeraLine_ID (int UY_TT_ChequeraLine_ID);

	/** Get UY_TT_ChequeraLine	  */
	public int getUY_TT_ChequeraLine_ID();

	public I_UY_TT_ChequeraLine getUY_TT_ChequeraLine() throws RuntimeException;

    /** Column name UY_TT_Delivery_ID */
    public static final String COLUMNNAME_UY_TT_Delivery_ID = "UY_TT_Delivery_ID";

	/** Set UY_TT_Delivery	  */
	public void setUY_TT_Delivery_ID (int UY_TT_Delivery_ID);

	/** Get UY_TT_Delivery	  */
	public int getUY_TT_Delivery_ID();

	public I_UY_TT_Delivery getUY_TT_Delivery() throws RuntimeException;

    /** Column name UY_TT_PrintValeLine_ID */
    public static final String COLUMNNAME_UY_TT_PrintValeLine_ID = "UY_TT_PrintValeLine_ID";

	/** Set UY_TT_PrintValeLine	  */
	public void setUY_TT_PrintValeLine_ID (int UY_TT_PrintValeLine_ID);

	/** Get UY_TT_PrintValeLine	  */
	public int getUY_TT_PrintValeLine_ID();

	public I_UY_TT_PrintValeLine getUY_TT_PrintValeLine() throws RuntimeException;

    /** Column name UY_TT_ReturnReasons_ID */
    public static final String COLUMNNAME_UY_TT_ReturnReasons_ID = "UY_TT_ReturnReasons_ID";

	/** Set UY_TT_ReturnReasons	  */
	public void setUY_TT_ReturnReasons_ID (int UY_TT_ReturnReasons_ID);

	/** Get UY_TT_ReturnReasons	  */
	public int getUY_TT_ReturnReasons_ID();

	public I_UY_TT_ReturnReasons getUY_TT_ReturnReasons() throws RuntimeException;

    /** Column name UY_TT_Seal_ID */
    public static final String COLUMNNAME_UY_TT_Seal_ID = "UY_TT_Seal_ID";

	/** Set UY_TT_Seal_ID	  */
	public void setUY_TT_Seal_ID (int UY_TT_Seal_ID);

	/** Get UY_TT_Seal_ID	  */
	public int getUY_TT_Seal_ID();

	public I_UY_TT_Seal getUY_TT_Seal() throws RuntimeException;

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name Vendedor */
    public static final String COLUMNNAME_Vendedor = "Vendedor";

	/** Set Vendedor	  */
	public void setVendedor (String Vendedor);

	/** Get Vendedor	  */
	public String getVendedor();

    /** Column name Viernes */
    public static final String COLUMNNAME_Viernes = "Viernes";

	/** Set Viernes	  */
	public void setViernes (boolean Viernes);

	/** Get Viernes	  */
	public boolean isViernes();
}
