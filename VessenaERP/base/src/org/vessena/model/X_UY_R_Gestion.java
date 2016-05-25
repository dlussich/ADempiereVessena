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

/** Generated Model for UY_R_Gestion
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_Gestion extends PO implements I_UY_R_Gestion, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150827L;

    /** Standard Constructor */
    public X_UY_R_Gestion (Properties ctx, int UY_R_Gestion_ID, String trxName)
    {
      super (ctx, UY_R_Gestion_ID, trxName);
      /** if (UY_R_Gestion_ID == 0)
        {
			setJueves (false);
// N
			setLunes (false);
// N
			setMartes (false);
// N
			setMiercoles (false);
// N
			setSabado (false);
// N
			setUY_R_Gestion_ID (0);
			setUY_R_Reclamo_ID (0);
			setViernes (false);
// N
        } */
    }

    /** Load Constructor */
    public X_UY_R_Gestion (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_Gestion[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Address 2.
		@param Address2 
		Address line 2 for this location
	  */
	public void setAddress2 (String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Address 2.
		@return Address line 2 for this location
	  */
	public String getAddress2 () 
	{
		return (String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Barrio.
		@param Barrio Barrio	  */
	public void setBarrio (String Barrio)
	{
		set_Value (COLUMNNAME_Barrio, Barrio);
	}

	/** Get Barrio.
		@return Barrio	  */
	public String getBarrio () 
	{
		return (String)get_Value(COLUMNNAME_Barrio);
	}

	/** Set Bloque.
		@param Bloque Bloque	  */
	public void setBloque (String Bloque)
	{
		set_Value (COLUMNNAME_Bloque, Bloque);
	}

	/** Get Bloque.
		@return Bloque	  */
	public String getBloque () 
	{
		return (String)get_Value(COLUMNNAME_Bloque);
	}

	/** Set Calle.
		@param Calle Calle	  */
	public void setCalle (String Calle)
	{
		set_Value (COLUMNNAME_Calle, Calle);
	}

	/** Get Calle.
		@return Calle	  */
	public String getCalle () 
	{
		return (String)get_Value(COLUMNNAME_Calle);
	}

	/** Set Calle1.
		@param Calle1 Calle1	  */
	public void setCalle1 (String Calle1)
	{
		set_Value (COLUMNNAME_Calle1, Calle1);
	}

	/** Get Calle1.
		@return Calle1	  */
	public String getCalle1 () 
	{
		return (String)get_Value(COLUMNNAME_Calle1);
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

	/** Set ConfirmacionEscrita.
		@param ConfirmacionEscrita ConfirmacionEscrita	  */
	public void setConfirmacionEscrita (boolean ConfirmacionEscrita)
	{
		set_Value (COLUMNNAME_ConfirmacionEscrita, Boolean.valueOf(ConfirmacionEscrita));
	}

	/** Get ConfirmacionEscrita.
		@return ConfirmacionEscrita	  */
	public boolean isConfirmacionEscrita () 
	{
		Object oo = get_Value(COLUMNNAME_ConfirmacionEscrita);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set DateExecuted.
		@param DateExecuted DateExecuted	  */
	public void setDateExecuted (Timestamp DateExecuted)
	{
		set_Value (COLUMNNAME_DateExecuted, DateExecuted);
	}

	/** Get DateExecuted.
		@return DateExecuted	  */
	public Timestamp getDateExecuted () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateExecuted);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set DeliveryTime.
		@param DeliveryTime DeliveryTime	  */
	public void setDeliveryTime (String DeliveryTime)
	{
		set_Value (COLUMNNAME_DeliveryTime, DeliveryTime);
	}

	/** Get DeliveryTime.
		@return DeliveryTime	  */
	public String getDeliveryTime () 
	{
		return (String)get_Value(COLUMNNAME_DeliveryTime);
	}

	/** Set DeliveryTime2.
		@param DeliveryTime2 DeliveryTime2	  */
	public void setDeliveryTime2 (String DeliveryTime2)
	{
		set_Value (COLUMNNAME_DeliveryTime2, DeliveryTime2);
	}

	/** Get DeliveryTime2.
		@return DeliveryTime2	  */
	public String getDeliveryTime2 () 
	{
		return (String)get_Value(COLUMNNAME_DeliveryTime2);
	}

	/** Set DeliveryTime3.
		@param DeliveryTime3 DeliveryTime3	  */
	public void setDeliveryTime3 (String DeliveryTime3)
	{
		set_Value (COLUMNNAME_DeliveryTime3, DeliveryTime3);
	}

	/** Get DeliveryTime3.
		@return DeliveryTime3	  */
	public String getDeliveryTime3 () 
	{
		return (String)get_Value(COLUMNNAME_DeliveryTime3);
	}

	/** Set Depto.
		@param Depto Depto	  */
	public void setDepto (String Depto)
	{
		set_Value (COLUMNNAME_Depto, Depto);
	}

	/** Get Depto.
		@return Depto	  */
	public String getDepto () 
	{
		return (String)get_Value(COLUMNNAME_Depto);
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

	/** Set Detail Information.
		@param DetailInfo 
		Additional Detail Information
	  */
	public void setDetailInfo (String DetailInfo)
	{
		set_Value (COLUMNNAME_DetailInfo, DetailInfo);
	}

	/** Get Detail Information.
		@return Additional Detail Information
	  */
	public String getDetailInfo () 
	{
		return (String)get_Value(COLUMNNAME_DetailInfo);
	}

	/** DomicilioActual AD_Reference_ID=1000350 */
	public static final int DOMICILIOACTUAL_AD_Reference_ID=1000350;
	/** Particular = PARTICULAR */
	public static final String DOMICILIOACTUAL_Particular = "PARTICULAR";
	/** Laboral = LABORAL */
	public static final String DOMICILIOACTUAL_Laboral = "LABORAL";
	/** Garante = GARANTE */
	public static final String DOMICILIOACTUAL_Garante = "GARANTE";
	/** Otro = OTRO */
	public static final String DOMICILIOACTUAL_Otro = "OTRO";
	/** Set DomicilioActual.
		@param DomicilioActual DomicilioActual	  */
	public void setDomicilioActual (String DomicilioActual)
	{

		set_Value (COLUMNNAME_DomicilioActual, DomicilioActual);
	}

	/** Get DomicilioActual.
		@return DomicilioActual	  */
	public String getDomicilioActual () 
	{
		return (String)get_Value(COLUMNNAME_DomicilioActual);
	}

	/** Set Esquina.
		@param Esquina Esquina	  */
	public void setEsquina (String Esquina)
	{
		set_Value (COLUMNNAME_Esquina, Esquina);
	}

	/** Get Esquina.
		@return Esquina	  */
	public String getEsquina () 
	{
		return (String)get_Value(COLUMNNAME_Esquina);
	}

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set ExecuteAction2.
		@param ExecuteAction2 ExecuteAction2	  */
	public void setExecuteAction2 (String ExecuteAction2)
	{
		set_Value (COLUMNNAME_ExecuteAction2, ExecuteAction2);
	}

	/** Get ExecuteAction2.
		@return ExecuteAction2	  */
	public String getExecuteAction2 () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction2);
	}

	/** Set ExecuteAction3.
		@param ExecuteAction3 ExecuteAction3	  */
	public void setExecuteAction3 (String ExecuteAction3)
	{
		set_Value (COLUMNNAME_ExecuteAction3, ExecuteAction3);
	}

	/** Get ExecuteAction3.
		@return ExecuteAction3	  */
	public String getExecuteAction3 () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction3);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set HoraEntrega1.
		@param HoraEntrega1 HoraEntrega1	  */
	public void setHoraEntrega1 (Timestamp HoraEntrega1)
	{
		set_Value (COLUMNNAME_HoraEntrega1, HoraEntrega1);
	}

	/** Get HoraEntrega1.
		@return HoraEntrega1	  */
	public Timestamp getHoraEntrega1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HoraEntrega1);
	}

	/** Set HoraEntrega2.
		@param HoraEntrega2 HoraEntrega2	  */
	public void setHoraEntrega2 (Timestamp HoraEntrega2)
	{
		set_Value (COLUMNNAME_HoraEntrega2, HoraEntrega2);
	}

	/** Get HoraEntrega2.
		@return HoraEntrega2	  */
	public Timestamp getHoraEntrega2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HoraEntrega2);
	}

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsOver.
		@param IsOver 
		IsOver
	  */
	public void setIsOver (boolean IsOver)
	{
		set_Value (COLUMNNAME_IsOver, Boolean.valueOf(IsOver));
	}

	/** Get IsOver.
		@return IsOver
	  */
	public boolean isOver () 
	{
		Object oo = get_Value(COLUMNNAME_IsOver);
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

	/** Set Justification.
		@param Justification Justification	  */
	public void setJustification (String Justification)
	{
		set_Value (COLUMNNAME_Justification, Justification);
	}

	/** Get Justification.
		@return Justification	  */
	public String getJustification () 
	{
		return (String)get_Value(COLUMNNAME_Justification);
	}

	/** Set JustificationApproved.
		@param JustificationApproved JustificationApproved	  */
	public void setJustificationApproved (String JustificationApproved)
	{
		set_Value (COLUMNNAME_JustificationApproved, JustificationApproved);
	}

	/** Get JustificationApproved.
		@return JustificationApproved	  */
	public String getJustificationApproved () 
	{
		return (String)get_Value(COLUMNNAME_JustificationApproved);
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

	/** Set Mail Text.
		@param MailText 
		Text used for Mail message
	  */
	public void setMailText (String MailText)
	{
		set_Value (COLUMNNAME_MailText, MailText);
	}

	/** Get Mail Text.
		@return Text used for Mail message
	  */
	public String getMailText () 
	{
		return (String)get_Value(COLUMNNAME_MailText);
	}

	/** Set Manzana.
		@param Manzana Manzana	  */
	public void setManzana (String Manzana)
	{
		set_Value (COLUMNNAME_Manzana, Manzana);
	}

	/** Get Manzana.
		@return Manzana	  */
	public String getManzana () 
	{
		return (String)get_Value(COLUMNNAME_Manzana);
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

	/** Set NroApto.
		@param NroApto NroApto	  */
	public void setNroApto (String NroApto)
	{
		set_Value (COLUMNNAME_NroApto, NroApto);
	}

	/** Get NroApto.
		@return NroApto	  */
	public String getNroApto () 
	{
		return (String)get_Value(COLUMNNAME_NroApto);
	}

	/** Set NroPuerta.
		@param NroPuerta NroPuerta	  */
	public void setNroPuerta (String NroPuerta)
	{
		set_Value (COLUMNNAME_NroPuerta, NroPuerta);
	}

	/** Get NroPuerta.
		@return NroPuerta	  */
	public String getNroPuerta () 
	{
		return (String)get_Value(COLUMNNAME_NroPuerta);
	}

	/** Set NuevoDestino.
		@param NuevoDestino NuevoDestino	  */
	public void setNuevoDestino (String NuevoDestino)
	{
		set_Value (COLUMNNAME_NuevoDestino, NuevoDestino);
	}

	/** Get NuevoDestino.
		@return NuevoDestino	  */
	public String getNuevoDestino () 
	{
		return (String)get_Value(COLUMNNAME_NuevoDestino);
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

	/** Set Receptor_ID.
		@param Receptor_ID Receptor_ID	  */
	public void setReceptor_ID (int Receptor_ID)
	{
		if (Receptor_ID < 1) 
			set_Value (COLUMNNAME_Receptor_ID, null);
		else 
			set_Value (COLUMNNAME_Receptor_ID, Integer.valueOf(Receptor_ID));
	}

	/** Get Receptor_ID.
		@return Receptor_ID	  */
	public int getReceptor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Receptor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ReclamoAccionType AD_Reference_ID=1000275 */
	public static final int RECLAMOACCIONTYPE_AD_Reference_ID=1000275;
	/** Enviar Consulta Proveedor Externo = CONSPROV */
	public static final String RECLAMOACCIONTYPE_EnviarConsultaProveedorExterno = "CONSPROV";
	/** Enviar Consulta Canal = CONSSUC */
	public static final String RECLAMOACCIONTYPE_EnviarConsultaCanal = "CONSSUC";
	/** Derivar Reclamo = DERIVAR */
	public static final String RECLAMOACCIONTYPE_DerivarReclamo = "DERIVAR";
	/** Cerrar Reclamo = CERRAR */
	public static final String RECLAMOACCIONTYPE_CerrarReclamo = "CERRAR";
	/** Solicitar Ajuste = AJUSTE */
	public static final String RECLAMOACCIONTYPE_SolicitarAjuste = "AJUSTE";
	/** Notificacion a Cliente = NOTIFICA */
	public static final String RECLAMOACCIONTYPE_NotificacionACliente = "NOTIFICA";
	/** Otra Accion = OTRA */
	public static final String RECLAMOACCIONTYPE_OtraAccion = "OTRA";
	/** Set ReclamoAccionType.
		@param ReclamoAccionType ReclamoAccionType	  */
	public void setReclamoAccionType (String ReclamoAccionType)
	{

		set_Value (COLUMNNAME_ReclamoAccionType, ReclamoAccionType);
	}

	/** Get ReclamoAccionType.
		@return ReclamoAccionType	  */
	public String getReclamoAccionType () 
	{
		return (String)get_Value(COLUMNNAME_ReclamoAccionType);
	}

	/** Set ReclamoResuelto.
		@param ReclamoResuelto ReclamoResuelto	  */
	public void setReclamoResuelto (boolean ReclamoResuelto)
	{
		set_Value (COLUMNNAME_ReclamoResuelto, Boolean.valueOf(ReclamoResuelto));
	}

	/** Get ReclamoResuelto.
		@return ReclamoResuelto	  */
	public boolean isReclamoResuelto () 
	{
		Object oo = get_Value(COLUMNNAME_ReclamoResuelto);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Responsable_ID.
		@param Responsable_ID Responsable_ID	  */
	public void setResponsable_ID (int Responsable_ID)
	{
		if (Responsable_ID < 1) 
			set_Value (COLUMNNAME_Responsable_ID, null);
		else 
			set_Value (COLUMNNAME_Responsable_ID, Integer.valueOf(Responsable_ID));
	}

	/** Get Responsable_ID.
		@return Responsable_ID	  */
	public int getResponsable_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Responsable_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ResueltoType AD_Reference_ID=1000301 */
	public static final int RESUELTOTYPE_AD_Reference_ID=1000301;
	/** Cliente = CLI */
	public static final String RESUELTOTYPE_Cliente = "CLI";
	/** Empresa = EMP */
	public static final String RESUELTOTYPE_Empresa = "EMP";
	/** Set ResueltoType.
		@param ResueltoType 
		ResueltoType
	  */
	public void setResueltoType (String ResueltoType)
	{

		set_Value (COLUMNNAME_ResueltoType, ResueltoType);
	}

	/** Get ResueltoType.
		@return ResueltoType
	  */
	public String getResueltoType () 
	{
		return (String)get_Value(COLUMNNAME_ResueltoType);
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

	/** Set Solar.
		@param Solar Solar	  */
	public void setSolar (String Solar)
	{
		set_Value (COLUMNNAME_Solar, Solar);
	}

	/** Get Solar.
		@return Solar	  */
	public String getSolar () 
	{
		return (String)get_Value(COLUMNNAME_Solar);
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

	/** Set Torre.
		@param Torre Torre	  */
	public void setTorre (String Torre)
	{
		set_Value (COLUMNNAME_Torre, Torre);
	}

	/** Get Torre.
		@return Torre	  */
	public String getTorre () 
	{
		return (String)get_Value(COLUMNNAME_Torre);
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UserConfirmaEscrita_ID.
		@param UserConfirmaEscrita_ID UserConfirmaEscrita_ID	  */
	public void setUserConfirmaEscrita_ID (int UserConfirmaEscrita_ID)
	{
		if (UserConfirmaEscrita_ID < 1) 
			set_Value (COLUMNNAME_UserConfirmaEscrita_ID, null);
		else 
			set_Value (COLUMNNAME_UserConfirmaEscrita_ID, Integer.valueOf(UserConfirmaEscrita_ID));
	}

	/** Get UserConfirmaEscrita_ID.
		@return UserConfirmaEscrita_ID	  */
	public int getUserConfirmaEscrita_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserConfirmaEscrita_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UY_DeliveryPoint_ID_SubAge.
		@param UY_DeliveryPoint_ID_SubAge UY_DeliveryPoint_ID_SubAge	  */
	public void setUY_DeliveryPoint_ID_SubAge (int UY_DeliveryPoint_ID_SubAge)
	{
		set_Value (COLUMNNAME_UY_DeliveryPoint_ID_SubAge, Integer.valueOf(UY_DeliveryPoint_ID_SubAge));
	}

	/** Get UY_DeliveryPoint_ID_SubAge.
		@return UY_DeliveryPoint_ID_SubAge	  */
	public int getUY_DeliveryPoint_ID_SubAge () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID_SubAge);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_DeliveryPoint getUY_DeliveryPoint_To() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID_To(), get_TrxName());	}

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

	public I_UY_Departamentos getUY_Departamentos() throws RuntimeException
    {
		return (I_UY_Departamentos)MTable.get(getCtx(), I_UY_Departamentos.Table_Name)
			.getPO(getUY_Departamentos_ID(), get_TrxName());	}

	/** Set Departamentos o regiones por Pais.
		@param UY_Departamentos_ID Departamentos o regiones por Pais	  */
	public void setUY_Departamentos_ID (int UY_Departamentos_ID)
	{
		if (UY_Departamentos_ID < 1) 
			set_Value (COLUMNNAME_UY_Departamentos_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Departamentos_ID, Integer.valueOf(UY_Departamentos_ID));
	}

	/** Get Departamentos o regiones por Pais.
		@return Departamentos o regiones por Pais	  */
	public int getUY_Departamentos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Departamentos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Localidades getUY_Localidades() throws RuntimeException
    {
		return (I_UY_Localidades)MTable.get(getCtx(), I_UY_Localidades.Table_Name)
			.getPO(getUY_Localidades_ID(), get_TrxName());	}

	/** Set Localidades por Departamentos.
		@param UY_Localidades_ID Localidades por Departamentos	  */
	public void setUY_Localidades_ID (int UY_Localidades_ID)
	{
		if (UY_Localidades_ID < 1) 
			set_Value (COLUMNNAME_UY_Localidades_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Localidades_ID, Integer.valueOf(UY_Localidades_ID));
	}

	/** Get Localidades por Departamentos.
		@return Localidades por Departamentos	  */
	public int getUY_Localidades_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Localidades_ID);
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

	public I_UY_R_Action getUY_R_Action() throws RuntimeException
    {
		return (I_UY_R_Action)MTable.get(getCtx(), I_UY_R_Action.Table_Name)
			.getPO(getUY_R_Action_ID(), get_TrxName());	}

	/** Set UY_R_Action.
		@param UY_R_Action_ID UY_R_Action	  */
	public void setUY_R_Action_ID (int UY_R_Action_ID)
	{
		if (UY_R_Action_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Action_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Action_ID, Integer.valueOf(UY_R_Action_ID));
	}

	/** Get UY_R_Action.
		@return UY_R_Action	  */
	public int getUY_R_Action_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Action_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Ajuste getUY_R_Ajuste() throws RuntimeException
    {
		return (I_UY_R_Ajuste)MTable.get(getCtx(), I_UY_R_Ajuste.Table_Name)
			.getPO(getUY_R_Ajuste_ID(), get_TrxName());	}

	/** Set UY_R_Ajuste.
		@param UY_R_Ajuste_ID UY_R_Ajuste	  */
	public void setUY_R_Ajuste_ID (int UY_R_Ajuste_ID)
	{
		if (UY_R_Ajuste_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Ajuste_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Ajuste_ID, Integer.valueOf(UY_R_Ajuste_ID));
	}

	/** Get UY_R_Ajuste.
		@return UY_R_Ajuste	  */
	public int getUY_R_Ajuste_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Ajuste_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Area getUY_R_Area() throws RuntimeException
    {
		return (I_UY_R_Area)MTable.get(getCtx(), I_UY_R_Area.Table_Name)
			.getPO(getUY_R_Area_ID(), get_TrxName());	}

	/** Set UY_R_Area.
		@param UY_R_Area_ID UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID)
	{
		if (UY_R_Area_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Area_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Area_ID, Integer.valueOf(UY_R_Area_ID));
	}

	/** Get UY_R_Area.
		@return UY_R_Area	  */
	public int getUY_R_Area_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Area_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Gestion.
		@param UY_R_Gestion_ID UY_R_Gestion	  */
	public void setUY_R_Gestion_ID (int UY_R_Gestion_ID)
	{
		if (UY_R_Gestion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_Gestion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_Gestion_ID, Integer.valueOf(UY_R_Gestion_ID));
	}

	/** Get UY_R_Gestion.
		@return UY_R_Gestion	  */
	public int getUY_R_Gestion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Gestion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_MotivoCierre getUY_R_MotivoCierre() throws RuntimeException
    {
		return (I_UY_R_MotivoCierre)MTable.get(getCtx(), I_UY_R_MotivoCierre.Table_Name)
			.getPO(getUY_R_MotivoCierre_ID(), get_TrxName());	}

	/** Set UY_R_MotivoCierre.
		@param UY_R_MotivoCierre_ID UY_R_MotivoCierre	  */
	public void setUY_R_MotivoCierre_ID (int UY_R_MotivoCierre_ID)
	{
		if (UY_R_MotivoCierre_ID < 1) 
			set_Value (COLUMNNAME_UY_R_MotivoCierre_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_MotivoCierre_ID, Integer.valueOf(UY_R_MotivoCierre_ID));
	}

	/** Get UY_R_MotivoCierre.
		@return UY_R_MotivoCierre	  */
	public int getUY_R_MotivoCierre_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_MotivoCierre_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException
    {
		return (I_UY_R_PtoResolucion)MTable.get(getCtx(), I_UY_R_PtoResolucion.Table_Name)
			.getPO(getUY_R_PtoResolucion_ID(), get_TrxName());	}

	/** Set UY_R_PtoResolucion.
		@param UY_R_PtoResolucion_ID UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID)
	{
		if (UY_R_PtoResolucion_ID < 1) 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, Integer.valueOf(UY_R_PtoResolucion_ID));
	}

	/** Get UY_R_PtoResolucion.
		@return UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_PtoResolucion_ID);
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

	/** Set Vivienda.
		@param Vivienda Vivienda	  */
	public void setVivienda (String Vivienda)
	{
		set_Value (COLUMNNAME_Vivienda, Vivienda);
	}

	/** Get Vivienda.
		@return Vivienda	  */
	public String getVivienda () 
	{
		return (String)get_Value(COLUMNNAME_Vivienda);
	}
}