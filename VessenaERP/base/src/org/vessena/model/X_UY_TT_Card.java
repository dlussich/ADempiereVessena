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
/** Generated Model - DO NOT CHANGE */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_Card
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_Card extends PO implements I_UY_TT_Card, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160229L;

    /** Standard Constructor */
    public X_UY_TT_Card (Properties ctx, int UY_TT_Card_ID, String trxName)
    {
      super (ctx, UY_TT_Card_ID, trxName);
      /** if (UY_TT_Card_ID == 0)
        {
			setAccountNo (null);
			setCardType (null);
			setCedula (null);
			setCedulaSN (false);
// N
			setConsDomSN (false);
// N
			setDiasAccion (0);
// 0
			setDiasActual (0);
// 0
			setInVerification (false);
// N
			setIsAddressChanged (false);
// N
			setIsDeliverable (false);
// N
			setIsLegajoCheckSaved (false);
// N
			setIsLegajoSaved (false);
// N
			setIsMaster (false);
// N
			setIsPrinted (false);
// N
			setIsReadyAcuse (false);
// N
			setIsReceived (false);
// N
			setIsRequested (false);
// N
			setIsRetained (false);
// N
			setIsReturned (false);
// N
			setLink_ID (0);
// 1
			setName (null);
			setNeedPrint (false);
// N
			setPrintDoc1 (false);
// N
			setPrintDoc2 (false);
// N
			setPrintDoc3 (false);
// N
			setPrintDoc4 (false);
// N
			setRecSueSN (false);
// N
			setSolValeSN (false);
// N
			setUY_TT_Card_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_Card (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UY_TT_Card[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Address 1.
		@param Address1 
		Address line 1 for this location
	  */
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Address 1.
		@return Address line 1 for this location
	  */
	public String getAddress1 () 
	{
		return (String)get_Value(COLUMNNAME_Address1);
	}

	/** Set AddressOld.
		@param AddressOld AddressOld	  */
	public void setAddressOld (String AddressOld)
	{
		set_Value (COLUMNNAME_AddressOld, AddressOld);
	}

	/** Get AddressOld.
		@return AddressOld	  */
	public String getAddressOld () 
	{
		return (String)get_Value(COLUMNNAME_AddressOld);
	}

	/** Set Calle2.
		@param Calle2 Calle2	  */
	public void setCalle2 (String Calle2)
	{
		set_Value (COLUMNNAME_Calle2, Calle2);
	}

	/** Get Calle2.
		@return Calle2	  */
	public String getCalle2 () 
	{
		return (String)get_Value(COLUMNNAME_Calle2);
	}

	/** Set CampaniaCod.
		@param CampaniaCod CampaniaCod	  */
	public void setCampaniaCod (int CampaniaCod)
	{
		set_Value (COLUMNNAME_CampaniaCod, Integer.valueOf(CampaniaCod));
	}

	/** Get CampaniaCod.
		@return CampaniaCod	  */
	public int getCampaniaCod () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CampaniaCod);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CampaniaNom.
		@param CampaniaNom CampaniaNom	  */
	public void setCampaniaNom (String CampaniaNom)
	{
		set_Value (COLUMNNAME_CampaniaNom, CampaniaNom);
	}

	/** Get CampaniaNom.
		@return CampaniaNom	  */
	public String getCampaniaNom () 
	{
		return (String)get_Value(COLUMNNAME_CampaniaNom);
	}

	/** Set CanalCod.
		@param CanalCod CanalCod	  */
	public void setCanalCod (int CanalCod)
	{
		set_Value (COLUMNNAME_CanalCod, Integer.valueOf(CanalCod));
	}

	/** Get CanalCod.
		@return CanalCod	  */
	public int getCanalCod () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CanalCod);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CanalNom.
		@param CanalNom CanalNom	  */
	public void setCanalNom (String CanalNom)
	{
		set_Value (COLUMNNAME_CanalNom, CanalNom);
	}

	/** Get CanalNom.
		@return CanalNom	  */
	public String getCanalNom () 
	{
		return (String)get_Value(COLUMNNAME_CanalNom);
	}

	/** CardAction AD_Reference_ID=1000325 */
	public static final int CARDACTION_AD_Reference_ID=1000325;
	/** Renovacion = RENOVACION */
	public static final String CARDACTION_Renovacion = "RENOVACION";
	/** Reimpresion = REIMPRESION */
	public static final String CARDACTION_Reimpresion = "REIMPRESION";
	/** Nueva = NUEVA */
	public static final String CARDACTION_Nueva = "NUEVA";
	/** Set CardAction.
		@param CardAction CardAction	  */
	public void setCardAction (String CardAction)
	{

		set_Value (COLUMNNAME_CardAction, CardAction);
	}

	/** Get CardAction.
		@return CardAction	  */
	public String getCardAction () 
	{
		return (String)get_Value(COLUMNNAME_CardAction);
	}

	/** CardDestination AD_Reference_ID=1000326 */
	public static final int CARDDESTINATION_AD_Reference_ID=1000326;
	/** Sucursal = SUCURSAL */
	public static final String CARDDESTINATION_Sucursal = "SUCURSAL";
	/** Domicilio Particular = DOMICILIO */
	public static final String CARDDESTINATION_DomicilioParticular = "DOMICILIO";
	/** Red Pagos = REDPAGOS */
	public static final String CARDDESTINATION_RedPagos = "REDPAGOS";
	/** Set CardDestination.
		@param CardDestination CardDestination	  */
	public void setCardDestination (String CardDestination)
	{

		set_Value (COLUMNNAME_CardDestination, CardDestination);
	}

	/** Get CardDestination.
		@return CardDestination	  */
	public String getCardDestination () 
	{
		return (String)get_Value(COLUMNNAME_CardDestination);
	}

	/** CardType AD_Reference_ID=1000324 */
	public static final int CARDTYPE_AD_Reference_ID=1000324;
	/** Titular = TITULAR */
	public static final String CARDTYPE_Titular = "TITULAR";
	/** Derivado = DERIVADO */
	public static final String CARDTYPE_Derivado = "DERIVADO";
	/** Set CardType.
		@param CardType CardType	  */
	public void setCardType (String CardType)
	{

		set_Value (COLUMNNAME_CardType, CardType);
	}

	/** Get CardType.
		@return CardType	  */
	public String getCardType () 
	{
		return (String)get_Value(COLUMNNAME_CardType);
	}

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
	}

	/** Set CedulaSN.
		@param CedulaSN CedulaSN	  */
	public void setCedulaSN (boolean CedulaSN)
	{
		set_Value (COLUMNNAME_CedulaSN, Boolean.valueOf(CedulaSN));
	}

	/** Get CedulaSN.
		@return CedulaSN	  */
	public boolean isCedulaSN () 
	{
		Object oo = get_Value(COLUMNNAME_CedulaSN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set cliape1.
		@param cliape1 cliape1	  */
	public void setcliape1 (String cliape1)
	{
		set_Value (COLUMNNAME_cliape1, cliape1);
	}

	/** Get cliape1.
		@return cliape1	  */
	public String getcliape1 () 
	{
		return (String)get_Value(COLUMNNAME_cliape1);
	}

	/** Set cliape2.
		@param cliape2 cliape2	  */
	public void setcliape2 (String cliape2)
	{
		set_Value (COLUMNNAME_cliape2, cliape2);
	}

	/** Get cliape2.
		@return cliape2	  */
	public String getcliape2 () 
	{
		return (String)get_Value(COLUMNNAME_cliape2);
	}

	/** Set clicod.
		@param clicod clicod	  */
	public void setclicod (String clicod)
	{
		set_Value (COLUMNNAME_clicod, clicod);
	}

	/** Get clicod.
		@return clicod	  */
	public String getclicod () 
	{
		return (String)get_Value(COLUMNNAME_clicod);
	}

	/** Set CliDigCtrl.
		@param CliDigCtrl CliDigCtrl	  */
	public void setCliDigCtrl (int CliDigCtrl)
	{
		set_Value (COLUMNNAME_CliDigCtrl, Integer.valueOf(CliDigCtrl));
	}

	/** Get CliDigCtrl.
		@return CliDigCtrl	  */
	public int getCliDigCtrl () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CliDigCtrl);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set clinom1.
		@param clinom1 clinom1	  */
	public void setclinom1 (String clinom1)
	{
		set_Value (COLUMNNAME_clinom1, clinom1);
	}

	/** Get clinom1.
		@return clinom1	  */
	public String getclinom1 () 
	{
		return (String)get_Value(COLUMNNAME_clinom1);
	}

	/** Set clinom2.
		@param clinom2 clinom2	  */
	public void setclinom2 (String clinom2)
	{
		set_Value (COLUMNNAME_clinom2, clinom2);
	}

	/** Get clinom2.
		@return clinom2	  */
	public String getclinom2 () 
	{
		return (String)get_Value(COLUMNNAME_clinom2);
	}

	/** Set ConsDomSN.
		@param ConsDomSN ConsDomSN	  */
	public void setConsDomSN (boolean ConsDomSN)
	{
		set_Value (COLUMNNAME_ConsDomSN, Boolean.valueOf(ConsDomSN));
	}

	/** Get ConsDomSN.
		@return ConsDomSN	  */
	public boolean isConsDomSN () 
	{
		Object oo = get_Value(COLUMNNAME_ConsDomSN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Credit limit.
		@param CreditLimit 
		Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit)
	{
		set_Value (COLUMNNAME_CreditLimit, CreditLimit);
	}

	/** Get Credit limit.
		@return Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DateAction.
		@param DateAction DateAction	  */
	public void setDateAction (Timestamp DateAction)
	{
		set_Value (COLUMNNAME_DateAction, DateAction);
	}

	/** Get DateAction.
		@return DateAction	  */
	public Timestamp getDateAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAction);
	}

	/** Set DateAddressChanged.
		@param DateAddressChanged DateAddressChanged	  */
	public void setDateAddressChanged (Timestamp DateAddressChanged)
	{
		set_Value (COLUMNNAME_DateAddressChanged, DateAddressChanged);
	}

	/** Get DateAddressChanged.
		@return DateAddressChanged	  */
	public Timestamp getDateAddressChanged () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAddressChanged);
	}

	/** Set DateAssign.
		@param DateAssign 
		DateAssign
	  */
	public void setDateAssign (Timestamp DateAssign)
	{
		set_Value (COLUMNNAME_DateAssign, DateAssign);
	}

	/** Get DateAssign.
		@return DateAssign
	  */
	public Timestamp getDateAssign () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAssign);
	}

	/** Set Date Delivered.
		@param DateDelivered 
		Date when the product was delivered
	  */
	public void setDateDelivered (Timestamp DateDelivered)
	{
		set_Value (COLUMNNAME_DateDelivered, DateDelivered);
	}

	/** Get Date Delivered.
		@return Date when the product was delivered
	  */
	public Timestamp getDateDelivered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDelivered);
	}

	/** Set Date last run.
		@param DateLastRun 
		Date the process was last run.
	  */
	public void setDateLastRun (Timestamp DateLastRun)
	{
		set_Value (COLUMNNAME_DateLastRun, DateLastRun);
	}

	/** Get Date last run.
		@return Date the process was last run.
	  */
	public Timestamp getDateLastRun () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastRun);
	}

	/** Set Date received.
		@param DateReceived 
		Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Date received.
		@return Date a product was received
	  */
	public Timestamp getDateReceived () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateReceived);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Description2.
		@param Description2 
		Optional short description of the record
	  */
	public void setDescription2 (String Description2)
	{
		set_Value (COLUMNNAME_Description2, Description2);
	}

	/** Get Description2.
		@return Optional short description of the record
	  */
	public String getDescription2 () 
	{
		return (String)get_Value(COLUMNNAME_Description2);
	}

	/** Set DiasAccion.
		@param DiasAccion DiasAccion	  */
	public void setDiasAccion (int DiasAccion)
	{
		set_Value (COLUMNNAME_DiasAccion, Integer.valueOf(DiasAccion));
	}

	/** Get DiasAccion.
		@return DiasAccion	  */
	public int getDiasAccion () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasAccion);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DiasAccionView.
		@param DiasAccionView DiasAccionView	  */
	public void setDiasAccionView (int DiasAccionView)
	{
		throw new IllegalArgumentException ("DiasAccionView is virtual column");	}

	/** Get DiasAccionView.
		@return DiasAccionView	  */
	public int getDiasAccionView () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasAccionView);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DiasActual.
		@param DiasActual DiasActual	  */
	public void setDiasActual (int DiasActual)
	{
		set_Value (COLUMNNAME_DiasActual, Integer.valueOf(DiasActual));
	}

	/** Get DiasActual.
		@return DiasActual	  */
	public int getDiasActual () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasActual);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DiasActualView.
		@param DiasActualView DiasActualView	  */
	public void setDiasActualView (int DiasActualView)
	{
		throw new IllegalArgumentException ("DiasActualView is virtual column");	}

	/** Get DiasActualView.
		@return DiasActualView	  */
	public int getDiasActualView () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasActualView);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DiasEntrega.
		@param DiasEntrega DiasEntrega	  */
	public void setDiasEntrega (String DiasEntrega)
	{
		set_Value (COLUMNNAME_DiasEntrega, DiasEntrega);
	}

	/** Get DiasEntrega.
		@return DiasEntrega	  */
	public String getDiasEntrega () 
	{
		return (String)get_Value(COLUMNNAME_DiasEntrega);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (String DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public String getDueDate () 
	{
		return (String)get_Value(COLUMNNAME_DueDate);
	}

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Esquina2.
		@param Esquina2 Esquina2	  */
	public void setEsquina2 (String Esquina2)
	{
		set_Value (COLUMNNAME_Esquina2, Esquina2);
	}

	/** Get Esquina2.
		@return Esquina2	  */
	public String getEsquina2 () 
	{
		return (String)get_Value(COLUMNNAME_Esquina2);
	}

	/** Set FchActivado.
		@param FchActivado FchActivado	  */
	public void setFchActivado (Timestamp FchActivado)
	{
		set_Value (COLUMNNAME_FchActivado, FchActivado);
	}

	/** Get FchActivado.
		@return FchActivado	  */
	public Timestamp getFchActivado () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FchActivado);
	}

	/** Set FchAprobado.
		@param FchAprobado FchAprobado	  */
	public void setFchAprobado (Timestamp FchAprobado)
	{
		set_Value (COLUMNNAME_FchAprobado, FchAprobado);
	}

	/** Get FchAprobado.
		@return FchAprobado	  */
	public Timestamp getFchAprobado () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FchAprobado);
	}

	/** Set FchCredito.
		@param FchCredito FchCredito	  */
	public void setFchCredito (Timestamp FchCredito)
	{
		set_Value (COLUMNNAME_FchCredito, FchCredito);
	}

	/** Get FchCredito.
		@return FchCredito	  */
	public Timestamp getFchCredito () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FchCredito);
	}

	/** Set FchEmbozado.
		@param FchEmbozado FchEmbozado	  */
	public void setFchEmbozado (Timestamp FchEmbozado)
	{
		set_Value (COLUMNNAME_FchEmbozado, FchEmbozado);
	}

	/** Get FchEmbozado.
		@return FchEmbozado	  */
	public Timestamp getFchEmbozado () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FchEmbozado);
	}

	/** Set FchObservado.
		@param FchObservado FchObservado	  */
	public void setFchObservado (Timestamp FchObservado)
	{
		set_Value (COLUMNNAME_FchObservado, FchObservado);
	}

	/** Get FchObservado.
		@return FchObservado	  */
	public Timestamp getFchObservado () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FchObservado);
	}

	/** Set FchSolicitud.
		@param FchSolicitud FchSolicitud	  */
	public void setFchSolicitud (Timestamp FchSolicitud)
	{
		set_Value (COLUMNNAME_FchSolicitud, FchSolicitud);
	}

	/** Get FchSolicitud.
		@return FchSolicitud	  */
	public Timestamp getFchSolicitud () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FchSolicitud);
	}

	/** Set GAFCOD.
		@param GAFCOD GAFCOD	  */
	public void setGAFCOD (int GAFCOD)
	{
		set_Value (COLUMNNAME_GAFCOD, Integer.valueOf(GAFCOD));
	}

	/** Get GAFCOD.
		@return GAFCOD	  */
	public int getGAFCOD () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GAFCOD);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GAFNOM.
		@param GAFNOM GAFNOM	  */
	public void setGAFNOM (String GAFNOM)
	{
		set_Value (COLUMNNAME_GAFNOM, GAFNOM);
	}

	/** Get GAFNOM.
		@return GAFNOM	  */
	public String getGAFNOM () 
	{
		return (String)get_Value(COLUMNNAME_GAFNOM);
	}

	/** Set GrpCtaCte.
		@param GrpCtaCte GrpCtaCte	  */
	public void setGrpCtaCte (int GrpCtaCte)
	{
		set_Value (COLUMNNAME_GrpCtaCte, Integer.valueOf(GrpCtaCte));
	}

	/** Get GrpCtaCte.
		@return GrpCtaCte	  */
	public int getGrpCtaCte () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GrpCtaCte);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HoraEntrega1.
		@param HoraEntrega1 HoraEntrega1	  */
	public void setHoraEntrega1 (String HoraEntrega1)
	{
		set_Value (COLUMNNAME_HoraEntrega1, HoraEntrega1);
	}

	/** Get HoraEntrega1.
		@return HoraEntrega1	  */
	public String getHoraEntrega1 () 
	{
		return (String)get_Value(COLUMNNAME_HoraEntrega1);
	}

	/** Set HoraEntrega2.
		@param HoraEntrega2 HoraEntrega2	  */
	public void setHoraEntrega2 (String HoraEntrega2)
	{
		set_Value (COLUMNNAME_HoraEntrega2, HoraEntrega2);
	}

	/** Get HoraEntrega2.
		@return HoraEntrega2	  */
	public String getHoraEntrega2 () 
	{
		return (String)get_Value(COLUMNNAME_HoraEntrega2);
	}

	/** Set InVerification.
		@param InVerification InVerification	  */
	public void setInVerification (boolean InVerification)
	{
		set_Value (COLUMNNAME_InVerification, Boolean.valueOf(InVerification));
	}

	/** Get InVerification.
		@return InVerification	  */
	public boolean isInVerification () 
	{
		Object oo = get_Value(COLUMNNAME_InVerification);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsAddressChanged.
		@param IsAddressChanged IsAddressChanged	  */
	public void setIsAddressChanged (boolean IsAddressChanged)
	{
		set_Value (COLUMNNAME_IsAddressChanged, Boolean.valueOf(IsAddressChanged));
	}

	/** Get IsAddressChanged.
		@return IsAddressChanged	  */
	public boolean isAddressChanged () 
	{
		Object oo = get_Value(COLUMNNAME_IsAddressChanged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDeliverable.
		@param IsDeliverable IsDeliverable	  */
	public void setIsDeliverable (boolean IsDeliverable)
	{
		set_Value (COLUMNNAME_IsDeliverable, Boolean.valueOf(IsDeliverable));
	}

	/** Get IsDeliverable.
		@return IsDeliverable	  */
	public boolean isDeliverable () 
	{
		Object oo = get_Value(COLUMNNAME_IsDeliverable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsLegajoCheckSaved.
		@param IsLegajoCheckSaved IsLegajoCheckSaved	  */
	public void setIsLegajoCheckSaved (boolean IsLegajoCheckSaved)
	{
		set_Value (COLUMNNAME_IsLegajoCheckSaved, Boolean.valueOf(IsLegajoCheckSaved));
	}

	/** Get IsLegajoCheckSaved.
		@return IsLegajoCheckSaved	  */
	public boolean isLegajoCheckSaved () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegajoCheckSaved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsLegajoSaved.
		@param IsLegajoSaved IsLegajoSaved	  */
	public void setIsLegajoSaved (boolean IsLegajoSaved)
	{
		set_Value (COLUMNNAME_IsLegajoSaved, Boolean.valueOf(IsLegajoSaved));
	}

	/** Get IsLegajoSaved.
		@return IsLegajoSaved	  */
	public boolean isLegajoSaved () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegajoSaved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsMaster.
		@param IsMaster IsMaster	  */
	public void setIsMaster (boolean IsMaster)
	{
		set_Value (COLUMNNAME_IsMaster, Boolean.valueOf(IsMaster));
	}

	/** Get IsMaster.
		@return IsMaster	  */
	public boolean isMaster () 
	{
		Object oo = get_Value(COLUMNNAME_IsMaster);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsReadyAcuse.
		@param IsReadyAcuse IsReadyAcuse	  */
	public void setIsReadyAcuse (boolean IsReadyAcuse)
	{
		set_Value (COLUMNNAME_IsReadyAcuse, Boolean.valueOf(IsReadyAcuse));
	}

	/** Get IsReadyAcuse.
		@return IsReadyAcuse	  */
	public boolean isReadyAcuse () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadyAcuse);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsReceived.
		@param IsReceived IsReceived	  */
	public void setIsReceived (boolean IsReceived)
	{
		set_Value (COLUMNNAME_IsReceived, Boolean.valueOf(IsReceived));
	}

	/** Get IsReceived.
		@return IsReceived	  */
	public boolean isReceived () 
	{
		Object oo = get_Value(COLUMNNAME_IsReceived);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRequested.
		@param IsRequested IsRequested	  */
	public void setIsRequested (boolean IsRequested)
	{
		set_Value (COLUMNNAME_IsRequested, Boolean.valueOf(IsRequested));
	}

	/** Get IsRequested.
		@return IsRequested	  */
	public boolean isRequested () 
	{
		Object oo = get_Value(COLUMNNAME_IsRequested);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRetained.
		@param IsRetained IsRetained	  */
	public void setIsRetained (boolean IsRetained)
	{
		set_Value (COLUMNNAME_IsRetained, Boolean.valueOf(IsRetained));
	}

	/** Get IsRetained.
		@return IsRetained	  */
	public boolean isRetained () 
	{
		Object oo = get_Value(COLUMNNAME_IsRetained);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsReturned.
		@param IsReturned IsReturned	  */
	public void setIsReturned (boolean IsReturned)
	{
		set_Value (COLUMNNAME_IsReturned, Boolean.valueOf(IsReturned));
	}

	/** Get IsReturned.
		@return IsReturned	  */
	public boolean isReturned () 
	{
		Object oo = get_Value(COLUMNNAME_IsReturned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsValeSigned.
		@param IsValeSigned IsValeSigned	  */
	public void setIsValeSigned (boolean IsValeSigned)
	{
		set_Value (COLUMNNAME_IsValeSigned, Boolean.valueOf(IsValeSigned));
	}

	/** Get IsValeSigned.
		@return IsValeSigned	  */
	public boolean isValeSigned () 
	{
		Object oo = get_Value(COLUMNNAME_IsValeSigned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Jueves.
		@param Jueves Jueves	  */
	public void setJueves (boolean Jueves)
	{
		set_Value (COLUMNNAME_Jueves, Boolean.valueOf(Jueves));
	}

	/** Get Jueves.
		@return Jueves	  */
	public boolean isJueves () 
	{
		Object oo = get_Value(COLUMNNAME_Jueves);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Levante.
		@param Levante 
		Levante
	  */
	public void setLevante (String Levante)
	{
		set_Value (COLUMNNAME_Levante, Levante);
	}

	/** Get Levante.
		@return Levante
	  */
	public String getLevante () 
	{
		return (String)get_Value(COLUMNNAME_Levante);
	}

	/** Set Link_ID.
		@param Link_ID Link_ID	  */
	public void setLink_ID (int Link_ID)
	{
		if (Link_ID < 1) 
			set_Value (COLUMNNAME_Link_ID, null);
		else 
			set_Value (COLUMNNAME_Link_ID, Integer.valueOf(Link_ID));
	}

	/** Get Link_ID.
		@return Link_ID	  */
	public int getLink_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Link_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set localidad.
		@param localidad localidad	  */
	public void setlocalidad (String localidad)
	{
		set_Value (COLUMNNAME_localidad, localidad);
	}

	/** Get localidad.
		@return localidad	  */
	public String getlocalidad () 
	{
		return (String)get_Value(COLUMNNAME_localidad);
	}

	/** Set Localidad2.
		@param Localidad2 Localidad2	  */
	public void setLocalidad2 (String Localidad2)
	{
		set_Value (COLUMNNAME_Localidad2, Localidad2);
	}

	/** Get Localidad2.
		@return Localidad2	  */
	public String getLocalidad2 () 
	{
		return (String)get_Value(COLUMNNAME_Localidad2);
	}

	/** Set LocalidadOld.
		@param LocalidadOld LocalidadOld	  */
	public void setLocalidadOld (String LocalidadOld)
	{
		set_Value (COLUMNNAME_LocalidadOld, LocalidadOld);
	}

	/** Get LocalidadOld.
		@return LocalidadOld	  */
	public String getLocalidadOld () 
	{
		return (String)get_Value(COLUMNNAME_LocalidadOld);
	}

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, Integer.valueOf(LocatorValue));
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public int getLocatorValue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LocatorValue);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lunes.
		@param Lunes Lunes	  */
	public void setLunes (boolean Lunes)
	{
		set_Value (COLUMNNAME_Lunes, Boolean.valueOf(Lunes));
	}

	/** Get Lunes.
		@return Lunes	  */
	public boolean isLunes () 
	{
		Object oo = get_Value(COLUMNNAME_Lunes);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Martes.
		@param Martes Martes	  */
	public void setMartes (boolean Martes)
	{
		set_Value (COLUMNNAME_Martes, Boolean.valueOf(Martes));
	}

	/** Get Martes.
		@return Martes	  */
	public boolean isMartes () 
	{
		Object oo = get_Value(COLUMNNAME_Martes);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Miercoles.
		@param Miercoles Miercoles	  */
	public void setMiercoles (boolean Miercoles)
	{
		set_Value (COLUMNNAME_Miercoles, Boolean.valueOf(Miercoles));
	}

	/** Get Miercoles.
		@return Miercoles	  */
	public boolean isMiercoles () 
	{
		Object oo = get_Value(COLUMNNAME_Miercoles);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MLCod.
		@param MLCod MLCod	  */
	public void setMLCod (String MLCod)
	{
		set_Value (COLUMNNAME_MLCod, MLCod);
	}

	/** Get MLCod.
		@return MLCod	  */
	public String getMLCod () 
	{
		return (String)get_Value(COLUMNNAME_MLCod);
	}

	/** Set Mobile.
		@param Mobile Mobile	  */
	public void setMobile (String Mobile)
	{
		set_Value (COLUMNNAME_Mobile, Mobile);
	}

	/** Get Mobile.
		@return Mobile	  */
	public String getMobile () 
	{
		return (String)get_Value(COLUMNNAME_Mobile);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set NeedPrint.
		@param NeedPrint NeedPrint	  */
	public void setNeedPrint (boolean NeedPrint)
	{
		set_Value (COLUMNNAME_NeedPrint, Boolean.valueOf(NeedPrint));
	}

	/** Get NeedPrint.
		@return NeedPrint	  */
	public boolean isNeedPrint () 
	{
		Object oo = get_Value(COLUMNNAME_NeedPrint);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** NotDeliverableAction AD_Reference_ID=1000331 */
	public static final int NOTDELIVERABLEACTION_AD_Reference_ID=1000331;
	/** Destruccion = DESTRUIR */
	public static final String NOTDELIVERABLEACTION_Destruccion = "DESTRUIR";
	/** Retencion = RETENER */
	public static final String NOTDELIVERABLEACTION_Retencion = "RETENER";
	/** Set NotDeliverableAction.
		@param NotDeliverableAction NotDeliverableAction	  */
	public void setNotDeliverableAction (String NotDeliverableAction)
	{

		set_Value (COLUMNNAME_NotDeliverableAction, NotDeliverableAction);
	}

	/** Get NotDeliverableAction.
		@return NotDeliverableAction	  */
	public String getNotDeliverableAction () 
	{
		return (String)get_Value(COLUMNNAME_NotDeliverableAction);
	}

	/** Set NotValidText.
		@param NotValidText NotValidText	  */
	public void setNotValidText (String NotValidText)
	{
		set_Value (COLUMNNAME_NotValidText, NotValidText);
	}

	/** Get NotValidText.
		@return NotValidText	  */
	public String getNotValidText () 
	{
		return (String)get_Value(COLUMNNAME_NotValidText);
	}

	/** Set nrodoc.
		@param nrodoc nrodoc	  */
	public void setnrodoc (String nrodoc)
	{
		set_Value (COLUMNNAME_nrodoc, nrodoc);
	}

	/** Get nrodoc.
		@return nrodoc	  */
	public String getnrodoc () 
	{
		return (String)get_Value(COLUMNNAME_nrodoc);
	}

	/** Set NroPuerta2.
		@param NroPuerta2 NroPuerta2	  */
	public void setNroPuerta2 (String NroPuerta2)
	{
		set_Value (COLUMNNAME_NroPuerta2, NroPuerta2);
	}

	/** Get NroPuerta2.
		@return NroPuerta2	  */
	public String getNroPuerta2 () 
	{
		return (String)get_Value(COLUMNNAME_NroPuerta2);
	}

	/** Set NroTarjetaNueva.
		@param NroTarjetaNueva NroTarjetaNueva	  */
	public void setNroTarjetaNueva (String NroTarjetaNueva)
	{
		set_Value (COLUMNNAME_NroTarjetaNueva, NroTarjetaNueva);
	}

	/** Get NroTarjetaNueva.
		@return NroTarjetaNueva	  */
	public String getNroTarjetaNueva () 
	{
		return (String)get_Value(COLUMNNAME_NroTarjetaNueva);
	}

	/** Set NroTarjetaTitular.
		@param NroTarjetaTitular NroTarjetaTitular	  */
	public void setNroTarjetaTitular (String NroTarjetaTitular)
	{
		set_Value (COLUMNNAME_NroTarjetaTitular, NroTarjetaTitular);
	}

	/** Get NroTarjetaTitular.
		@return NroTarjetaTitular	  */
	public String getNroTarjetaTitular () 
	{
		return (String)get_Value(COLUMNNAME_NroTarjetaTitular);
	}

	/** Set ZIP.
		@param Postal 
		Postal code
	  */
	public void setPostal (String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get ZIP.
		@return Postal code
	  */
	public String getPostal () 
	{
		return (String)get_Value(COLUMNNAME_Postal);
	}

	/** Set Postal2.
		@param Postal2 Postal2	  */
	public void setPostal2 (String Postal2)
	{
		set_Value (COLUMNNAME_Postal2, Postal2);
	}

	/** Get Postal2.
		@return Postal2	  */
	public String getPostal2 () 
	{
		return (String)get_Value(COLUMNNAME_Postal2);
	}

	/** Set PostalOld.
		@param PostalOld PostalOld	  */
	public void setPostalOld (String PostalOld)
	{
		set_Value (COLUMNNAME_PostalOld, PostalOld);
	}

	/** Get PostalOld.
		@return PostalOld	  */
	public String getPostalOld () 
	{
		return (String)get_Value(COLUMNNAME_PostalOld);
	}

	/** Set PrintDoc1.
		@param PrintDoc1 PrintDoc1	  */
	public void setPrintDoc1 (boolean PrintDoc1)
	{
		set_Value (COLUMNNAME_PrintDoc1, Boolean.valueOf(PrintDoc1));
	}

	/** Get PrintDoc1.
		@return PrintDoc1	  */
	public boolean isPrintDoc1 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PrintDoc2.
		@param PrintDoc2 
		PrintDoc2
	  */
	public void setPrintDoc2 (boolean PrintDoc2)
	{
		set_Value (COLUMNNAME_PrintDoc2, Boolean.valueOf(PrintDoc2));
	}

	/** Get PrintDoc2.
		@return PrintDoc2
	  */
	public boolean isPrintDoc2 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PrintDoc3.
		@param PrintDoc3 
		PrintDoc3
	  */
	public void setPrintDoc3 (boolean PrintDoc3)
	{
		set_Value (COLUMNNAME_PrintDoc3, Boolean.valueOf(PrintDoc3));
	}

	/** Get PrintDoc3.
		@return PrintDoc3
	  */
	public boolean isPrintDoc3 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc3);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PrintDoc4.
		@param PrintDoc4 
		PrintDoc4
	  */
	public void setPrintDoc4 (boolean PrintDoc4)
	{
		set_Value (COLUMNNAME_PrintDoc4, Boolean.valueOf(PrintDoc4));
	}

	/** Get PrintDoc4.
		@return PrintDoc4
	  */
	public boolean isPrintDoc4 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc4);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ProductoAux.
		@param ProductoAux ProductoAux	  */
	public void setProductoAux (String ProductoAux)
	{
		set_Value (COLUMNNAME_ProductoAux, ProductoAux);
	}

	/** Get ProductoAux.
		@return ProductoAux	  */
	public String getProductoAux () 
	{
		return (String)get_Value(COLUMNNAME_ProductoAux);
	}

	/** Set QtyCall.
		@param QtyCall QtyCall	  */
	public void setQtyCall (int QtyCall)
	{
		set_Value (COLUMNNAME_QtyCall, Integer.valueOf(QtyCall));
	}

	/** Get QtyCall.
		@return QtyCall	  */
	public int getQtyCall () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyCall);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyCarrier.
		@param QtyCarrier QtyCarrier	  */
	public void setQtyCarrier (int QtyCarrier)
	{
		set_Value (COLUMNNAME_QtyCarrier, Integer.valueOf(QtyCarrier));
	}

	/** Get QtyCarrier.
		@return QtyCarrier	  */
	public int getQtyCarrier () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyCarrier);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyCarrierCounted.
		@param QtyCarrierCounted QtyCarrierCounted	  */
	public void setQtyCarrierCounted (int QtyCarrierCounted)
	{
		set_Value (COLUMNNAME_QtyCarrierCounted, Integer.valueOf(QtyCarrierCounted));
	}

	/** Get QtyCarrierCounted.
		@return QtyCarrierCounted	  */
	public int getQtyCarrierCounted () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyCarrierCounted);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyPlastic.
		@param QtyPlastic QtyPlastic	  */
	public void setQtyPlastic (int QtyPlastic)
	{
		set_Value (COLUMNNAME_QtyPlastic, Integer.valueOf(QtyPlastic));
	}

	/** Get QtyPlastic.
		@return QtyPlastic	  */
	public int getQtyPlastic () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyPlastic);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RecSueSN.
		@param RecSueSN RecSueSN	  */
	public void setRecSueSN (boolean RecSueSN)
	{
		set_Value (COLUMNNAME_RecSueSN, Boolean.valueOf(RecSueSN));
	}

	/** Get RecSueSN.
		@return RecSueSN	  */
	public boolean isRecSueSN () 
	{
		Object oo = get_Value(COLUMNNAME_RecSueSN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** RetainedStatus AD_Reference_ID=1000340 */
	public static final int RETAINEDSTATUS_AD_Reference_ID=1000340;
	/** Destruccion = DESTRUIR */
	public static final String RETAINEDSTATUS_Destruccion = "DESTRUIR";
	/** Inconsistente = INCONSISTENTE */
	public static final String RETAINEDSTATUS_Inconsistente = "INCONSISTENTE";
	/** Set RetainedStatus.
		@param RetainedStatus RetainedStatus	  */
	public void setRetainedStatus (String RetainedStatus)
	{

		set_Value (COLUMNNAME_RetainedStatus, RetainedStatus);
	}

	/** Get RetainedStatus.
		@return RetainedStatus	  */
	public String getRetainedStatus () 
	{
		return (String)get_Value(COLUMNNAME_RetainedStatus);
	}

	/** Set Sabado.
		@param Sabado Sabado	  */
	public void setSabado (boolean Sabado)
	{
		set_Value (COLUMNNAME_Sabado, Boolean.valueOf(Sabado));
	}

	/** Get Sabado.
		@return Sabado	  */
	public boolean isSabado () 
	{
		Object oo = get_Value(COLUMNNAME_Sabado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set sex.
		@param sex sex	  */
	public void setsex (String sex)
	{
		set_Value (COLUMNNAME_sex, sex);
	}

	/** Get sex.
		@return sex	  */
	public String getsex () 
	{
		return (String)get_Value(COLUMNNAME_sex);
	}

	/** Set SolEnvExt.
		@param SolEnvExt SolEnvExt	  */
	public void setSolEnvExt (String SolEnvExt)
	{
		set_Value (COLUMNNAME_SolEnvExt, SolEnvExt);
	}

	/** Get SolEnvExt.
		@return SolEnvExt	  */
	public String getSolEnvExt () 
	{
		return (String)get_Value(COLUMNNAME_SolEnvExt);
	}

	/** Set SolEnvRes.
		@param SolEnvRes SolEnvRes	  */
	public void setSolEnvRes (String SolEnvRes)
	{
		set_Value (COLUMNNAME_SolEnvRes, SolEnvRes);
	}

	/** Get SolEnvRes.
		@return SolEnvRes	  */
	public String getSolEnvRes () 
	{
		return (String)get_Value(COLUMNNAME_SolEnvRes);
	}

	/** Set solnro.
		@param solnro solnro	  */
	public void setsolnro (String solnro)
	{
		set_Value (COLUMNNAME_solnro, solnro);
	}

	/** Get solnro.
		@return solnro	  */
	public String getsolnro () 
	{
		return (String)get_Value(COLUMNNAME_solnro);
	}

	/** Set SolValeSN.
		@param SolValeSN SolValeSN	  */
	public void setSolValeSN (boolean SolValeSN)
	{
		set_Value (COLUMNNAME_SolValeSN, Boolean.valueOf(SolValeSN));
	}

	/** Get SolValeSN.
		@return SolValeSN	  */
	public boolean isSolValeSN () 
	{
		Object oo = get_Value(COLUMNNAME_SolValeSN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SubAgencia.
		@param SubAgencia SubAgencia	  */
	public void setSubAgencia (String SubAgencia)
	{
		set_Value (COLUMNNAME_SubAgencia, SubAgencia);
	}

	/** Get SubAgencia.
		@return SubAgencia	  */
	public String getSubAgencia () 
	{
		return (String)get_Value(COLUMNNAME_SubAgencia);
	}

	/** Set SucCod.
		@param SucCod SucCod	  */
	public void setSucCod (int SucCod)
	{
		set_Value (COLUMNNAME_SucCod, Integer.valueOf(SucCod));
	}

	/** Get SucCod.
		@return SucCod	  */
	public int getSucCod () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SucCod);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SucNom.
		@param SucNom SucNom	  */
	public void setSucNom (String SucNom)
	{
		set_Value (COLUMNNAME_SucNom, SucNom);
	}

	/** Get SucNom.
		@return SucNom	  */
	public String getSucNom () 
	{
		return (String)get_Value(COLUMNNAME_SucNom);
	}

	/** Set Telephone.
		@param Telephone Telephone	  */
	public void setTelephone (String Telephone)
	{
		set_Value (COLUMNNAME_Telephone, Telephone);
	}

	/** Get Telephone.
		@return Telephone	  */
	public String getTelephone () 
	{
		return (String)get_Value(COLUMNNAME_Telephone);
	}

	/** TipoDomicilio AD_Reference_ID=1000350 */
	public static final int TIPODOMICILIO_AD_Reference_ID=1000350;
	/** Particular = PARTICULAR */
	public static final String TIPODOMICILIO_Particular = "PARTICULAR";
	/** Laboral = LABORAL */
	public static final String TIPODOMICILIO_Laboral = "LABORAL";
	/** Garante = GARANTE */
	public static final String TIPODOMICILIO_Garante = "GARANTE";
	/** Otro = OTRO */
	public static final String TIPODOMICILIO_Otro = "OTRO";
	/** Set TipoDomicilio.
		@param TipoDomicilio TipoDomicilio	  */
	public void setTipoDomicilio (String TipoDomicilio)
	{

		set_Value (COLUMNNAME_TipoDomicilio, TipoDomicilio);
	}

	/** Get TipoDomicilio.
		@return TipoDomicilio	  */
	public String getTipoDomicilio () 
	{
		return (String)get_Value(COLUMNNAME_TipoDomicilio);
	}

	/** TipoDomicilioOld AD_Reference_ID=1000350 */
	public static final int TIPODOMICILIOOLD_AD_Reference_ID=1000350;
	/** Particular = PARTICULAR */
	public static final String TIPODOMICILIOOLD_Particular = "PARTICULAR";
	/** Laboral = LABORAL */
	public static final String TIPODOMICILIOOLD_Laboral = "LABORAL";
	/** Garante = GARANTE */
	public static final String TIPODOMICILIOOLD_Garante = "GARANTE";
	/** Otro = OTRO */
	public static final String TIPODOMICILIOOLD_Otro = "OTRO";
	/** Set TipoDomicilioOld.
		@param TipoDomicilioOld TipoDomicilioOld	  */
	public void setTipoDomicilioOld (String TipoDomicilioOld)
	{

		set_Value (COLUMNNAME_TipoDomicilioOld, TipoDomicilioOld);
	}

	/** Get TipoDomicilioOld.
		@return TipoDomicilioOld	  */
	public String getTipoDomicilioOld () 
	{
		return (String)get_Value(COLUMNNAME_TipoDomicilioOld);
	}

	/** Set TplCalleNr.
		@param TplCalleNr TplCalleNr	  */
	public void setTplCalleNr (String TplCalleNr)
	{
		set_Value (COLUMNNAME_TplCalleNr, TplCalleNr);
	}

	/** Get TplCalleNr.
		@return TplCalleNr	  */
	public String getTplCalleNr () 
	{
		return (String)get_Value(COLUMNNAME_TplCalleNr);
	}

	/** Set TplDpto.
		@param TplDpto TplDpto	  */
	public void setTplDpto (String TplDpto)
	{
		set_Value (COLUMNNAME_TplDpto, TplDpto);
	}

	/** Get TplDpto.
		@return TplDpto	  */
	public String getTplDpto () 
	{
		return (String)get_Value(COLUMNNAME_TplDpto);
	}

	/** Set TplNro.
		@param TplNro TplNro	  */
	public void setTplNro (String TplNro)
	{
		set_Value (COLUMNNAME_TplNro, TplNro);
	}

	/** Get TplNro.
		@return TplNro	  */
	public String getTplNro () 
	{
		return (String)get_Value(COLUMNNAME_TplNro);
	}

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID(), get_TrxName());	}

	/** Set UY_DeliveryPoint.
		@param UY_DeliveryPoint_ID UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID)
	{
		if (UY_DeliveryPoint_ID < 1) 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, Integer.valueOf(UY_DeliveryPoint_ID));
	}

	/** Get UY_DeliveryPoint.
		@return UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DeliveryPoint_ID_Actual.
		@param UY_DeliveryPoint_ID_Actual UY_DeliveryPoint_ID_Actual	  */
	public void setUY_DeliveryPoint_ID_Actual (int UY_DeliveryPoint_ID_Actual)
	{
		set_Value (COLUMNNAME_UY_DeliveryPoint_ID_Actual, Integer.valueOf(UY_DeliveryPoint_ID_Actual));
	}

	/** Get UY_DeliveryPoint_ID_Actual.
		@return UY_DeliveryPoint_ID_Actual	  */
	public int getUY_DeliveryPoint_ID_Actual () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID_Actual);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_DeliveryPoint getUY_DeliveryPoint_ID_() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID_Req(), get_TrxName());	}

	/** Set UY_DeliveryPoint_ID_Req.
		@param UY_DeliveryPoint_ID_Req UY_DeliveryPoint_ID_Req	  */
	public void setUY_DeliveryPoint_ID_Req (int UY_DeliveryPoint_ID_Req)
	{
		set_Value (COLUMNNAME_UY_DeliveryPoint_ID_Req, Integer.valueOf(UY_DeliveryPoint_ID_Req));
	}

	/** Get UY_DeliveryPoint_ID_Req.
		@return UY_DeliveryPoint_ID_Req	  */
	public int getUY_DeliveryPoint_ID_Req () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID_Req);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DeliveryPoint_ID_To.
		@param UY_DeliveryPoint_ID_To UY_DeliveryPoint_ID_To	  */
	public void setUY_DeliveryPoint_ID_To (int UY_DeliveryPoint_ID_To)
	{
		set_Value (COLUMNNAME_UY_DeliveryPoint_ID_To, Integer.valueOf(UY_DeliveryPoint_ID_To));
	}

	/** Get UY_DeliveryPoint_ID_To.
		@return UY_DeliveryPoint_ID_To	  */
	public int getUY_DeliveryPoint_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_AccionPtoResol getUY_R_AccionPtoResol() throws RuntimeException
    {
		return (I_UY_R_AccionPtoResol)MTable.get(getCtx(), I_UY_R_AccionPtoResol.Table_Name)
			.getPO(getUY_R_AccionPtoResol_ID(), get_TrxName());	}

	/** Set UY_R_AccionPtoResol.
		@param UY_R_AccionPtoResol_ID UY_R_AccionPtoResol	  */
	public void setUY_R_AccionPtoResol_ID (int UY_R_AccionPtoResol_ID)
	{
		if (UY_R_AccionPtoResol_ID < 1) 
			set_Value (COLUMNNAME_UY_R_AccionPtoResol_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_AccionPtoResol_ID, Integer.valueOf(UY_R_AccionPtoResol_ID));
	}

	/** Get UY_R_AccionPtoResol.
		@return UY_R_AccionPtoResol	  */
	public int getUY_R_AccionPtoResol_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_AccionPtoResol_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException
    {
		return (I_UY_R_Reclamo)MTable.get(getCtx(), I_UY_R_Reclamo.Table_Name)
			.getPO(getUY_R_Reclamo_ID(), get_TrxName());	}

	/** Set UY_R_Reclamo.
		@param UY_R_Reclamo_ID UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID)
	{
		if (UY_R_Reclamo_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, Integer.valueOf(UY_R_Reclamo_ID));
	}

	/** Get UY_R_Reclamo.
		@return UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Action getUY_TT_Action() throws RuntimeException
    {
		return (I_UY_TT_Action)MTable.get(getCtx(), I_UY_TT_Action.Table_Name)
			.getPO(getUY_TT_Action_ID(), get_TrxName());	}

	/** Set UY_TT_Action.
		@param UY_TT_Action_ID UY_TT_Action	  */
	public void setUY_TT_Action_ID (int UY_TT_Action_ID)
	{
		if (UY_TT_Action_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Action_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Action_ID, Integer.valueOf(UY_TT_Action_ID));
	}

	/** Get UY_TT_Action.
		@return UY_TT_Action	  */
	public int getUY_TT_Action_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Action_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException
    {
		return (I_UY_TT_Box)MTable.get(getCtx(), I_UY_TT_Box.Table_Name)
			.getPO(getUY_TT_Box_ID(), get_TrxName());	}

	/** Set UY_TT_Box.
		@param UY_TT_Box_ID UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID)
	{
		if (UY_TT_Box_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Box_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Box_ID, Integer.valueOf(UY_TT_Box_ID));
	}

	/** Get UY_TT_Box.
		@return UY_TT_Box	  */
	public int getUY_TT_Box_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_Card.
		@param UY_TT_Card_ID UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID)
	{
		if (UY_TT_Card_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Card_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Card_ID, Integer.valueOf(UY_TT_Card_ID));
	}

	/** Get UY_TT_Card.
		@return UY_TT_Card	  */
	public int getUY_TT_Card_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Card_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_CardStatus getUY_TT_CardStatus() throws RuntimeException
    {
		return (I_UY_TT_CardStatus)MTable.get(getCtx(), I_UY_TT_CardStatus.Table_Name)
			.getPO(getUY_TT_CardStatus_ID(), get_TrxName());	}

	/** Set UY_TT_CardStatus.
		@param UY_TT_CardStatus_ID UY_TT_CardStatus	  */
	public void setUY_TT_CardStatus_ID (int UY_TT_CardStatus_ID)
	{
		if (UY_TT_CardStatus_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_CardStatus_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_CardStatus_ID, Integer.valueOf(UY_TT_CardStatus_ID));
	}

	/** Get UY_TT_CardStatus.
		@return UY_TT_CardStatus	  */
	public int getUY_TT_CardStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_CardStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_ChequeraLine getUY_TT_ChequeraLine() throws RuntimeException
    {
		return (I_UY_TT_ChequeraLine)MTable.get(getCtx(), I_UY_TT_ChequeraLine.Table_Name)
			.getPO(getUY_TT_ChequeraLine_ID(), get_TrxName());	}

	/** Set UY_TT_ChequeraLine.
		@param UY_TT_ChequeraLine_ID UY_TT_ChequeraLine	  */
	public void setUY_TT_ChequeraLine_ID (int UY_TT_ChequeraLine_ID)
	{
		if (UY_TT_ChequeraLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_ChequeraLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_ChequeraLine_ID, Integer.valueOf(UY_TT_ChequeraLine_ID));
	}

	/** Get UY_TT_ChequeraLine.
		@return UY_TT_ChequeraLine	  */
	public int getUY_TT_ChequeraLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ChequeraLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Delivery getUY_TT_Delivery() throws RuntimeException
    {
		return (I_UY_TT_Delivery)MTable.get(getCtx(), I_UY_TT_Delivery.Table_Name)
			.getPO(getUY_TT_Delivery_ID(), get_TrxName());	}

	/** Set UY_TT_Delivery.
		@param UY_TT_Delivery_ID UY_TT_Delivery	  */
	public void setUY_TT_Delivery_ID (int UY_TT_Delivery_ID)
	{
		if (UY_TT_Delivery_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Delivery_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Delivery_ID, Integer.valueOf(UY_TT_Delivery_ID));
	}

	/** Get UY_TT_Delivery.
		@return UY_TT_Delivery	  */
	public int getUY_TT_Delivery_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Delivery_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_PrintValeLine getUY_TT_PrintValeLine() throws RuntimeException
    {
		return (I_UY_TT_PrintValeLine)MTable.get(getCtx(), I_UY_TT_PrintValeLine.Table_Name)
			.getPO(getUY_TT_PrintValeLine_ID(), get_TrxName());	}

	/** Set UY_TT_PrintValeLine.
		@param UY_TT_PrintValeLine_ID UY_TT_PrintValeLine	  */
	public void setUY_TT_PrintValeLine_ID (int UY_TT_PrintValeLine_ID)
	{
		if (UY_TT_PrintValeLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_PrintValeLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_PrintValeLine_ID, Integer.valueOf(UY_TT_PrintValeLine_ID));
	}

	/** Get UY_TT_PrintValeLine.
		@return UY_TT_PrintValeLine	  */
	public int getUY_TT_PrintValeLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_PrintValeLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_ReturnReasons getUY_TT_ReturnReasons() throws RuntimeException
    {
		return (I_UY_TT_ReturnReasons)MTable.get(getCtx(), I_UY_TT_ReturnReasons.Table_Name)
			.getPO(getUY_TT_ReturnReasons_ID(), get_TrxName());	}

	/** Set UY_TT_ReturnReasons.
		@param UY_TT_ReturnReasons_ID UY_TT_ReturnReasons	  */
	public void setUY_TT_ReturnReasons_ID (int UY_TT_ReturnReasons_ID)
	{
		if (UY_TT_ReturnReasons_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_ReturnReasons_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_ReturnReasons_ID, Integer.valueOf(UY_TT_ReturnReasons_ID));
	}

	/** Get UY_TT_ReturnReasons.
		@return UY_TT_ReturnReasons	  */
	public int getUY_TT_ReturnReasons_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ReturnReasons_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Seal getUY_TT_Seal() throws RuntimeException
    {
		return (I_UY_TT_Seal)MTable.get(getCtx(), I_UY_TT_Seal.Table_Name)
			.getPO(getUY_TT_Seal_ID(), get_TrxName());	}

	/** Set UY_TT_Seal_ID.
		@param UY_TT_Seal_ID UY_TT_Seal_ID	  */
	public void setUY_TT_Seal_ID (int UY_TT_Seal_ID)
	{
		if (UY_TT_Seal_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Seal_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Seal_ID, Integer.valueOf(UY_TT_Seal_ID));
	}

	/** Get UY_TT_Seal_ID.
		@return UY_TT_Seal_ID	  */
	public int getUY_TT_Seal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Seal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Vendedor.
		@param Vendedor Vendedor	  */
	public void setVendedor (String Vendedor)
	{
		set_Value (COLUMNNAME_Vendedor, Vendedor);
	}

	/** Get Vendedor.
		@return Vendedor	  */
	public String getVendedor () 
	{
		return (String)get_Value(COLUMNNAME_Vendedor);
	}

	/** Set Viernes.
		@param Viernes Viernes	  */
	public void setViernes (boolean Viernes)
	{
		set_Value (COLUMNNAME_Viernes, Boolean.valueOf(Viernes));
	}

	/** Get Viernes.
		@return Viernes	  */
	public boolean isViernes () 
	{
		Object oo = get_Value(COLUMNNAME_Viernes);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}