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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_R_RegistroLlamada
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_RegistroLlamada extends PO implements I_UY_R_RegistroLlamada, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130731L;

    /** Standard Constructor */
    public X_UY_R_RegistroLlamada (Properties ctx, int UY_R_RegistroLlamada_ID, String trxName)
    {
      super (ctx, UY_R_RegistroLlamada_ID, trxName);
      /** if (UY_R_RegistroLlamada_ID == 0)
        {
			setComunicationResult (null);
			setComunicationType (null);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_R_Notification_ID (0);
			setUY_R_RegistroLlamada_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_RegistroLlamada (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_RegistroLlamada[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** ComunicationResult AD_Reference_ID=1000319 */
	public static final int COMUNICATIONRESULT_AD_Reference_ID=1000319;
	/** No Contesta = NOCONTESTA */
	public static final String COMUNICATIONRESULT_NoContesta = "NOCONTESTA";
	/** Se le envia SMS = SMS */
	public static final String COMUNICATIONRESULT_SeLeEnviaSMS = "SMS";
	/** Aviso a Familiar = AVISOFAMILIA */
	public static final String COMUNICATIONRESULT_AvisoAFamiliar = "AVISOFAMILIA";
	/** Contesta = CONTESTA */
	public static final String COMUNICATIONRESULT_Contesta = "CONTESTA";
	/** Otros = OTROS */
	public static final String COMUNICATIONRESULT_Otros = "OTROS";
	/** Set ComunicationResult.
		@param ComunicationResult ComunicationResult	  */
	public void setComunicationResult (String ComunicationResult)
	{

		set_Value (COLUMNNAME_ComunicationResult, ComunicationResult);
	}

	/** Get ComunicationResult.
		@return ComunicationResult	  */
	public String getComunicationResult () 
	{
		return (String)get_Value(COLUMNNAME_ComunicationResult);
	}

	/** ComunicationType AD_Reference_ID=1000318 */
	public static final int COMUNICATIONTYPE_AD_Reference_ID=1000318;
	/** Telefono Fijo = TELFIJO */
	public static final String COMUNICATIONTYPE_TelefonoFijo = "TELFIJO";
	/** Celular = CELULAR */
	public static final String COMUNICATIONTYPE_Celular = "CELULAR";
	/** Set ComunicationType.
		@param ComunicationType ComunicationType	  */
	public void setComunicationType (String ComunicationType)
	{

		set_Value (COLUMNNAME_ComunicationType, ComunicationType);
	}

	/** Get ComunicationType.
		@return ComunicationType	  */
	public String getComunicationType () 
	{
		return (String)get_Value(COLUMNNAME_ComunicationType);
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

	public I_UY_R_Notification getUY_R_Notification() throws RuntimeException
    {
		return (I_UY_R_Notification)MTable.get(getCtx(), I_UY_R_Notification.Table_Name)
			.getPO(getUY_R_Notification_ID(), get_TrxName());	}

	/** Set UY_R_Notification.
		@param UY_R_Notification_ID UY_R_Notification	  */
	public void setUY_R_Notification_ID (int UY_R_Notification_ID)
	{
		if (UY_R_Notification_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Notification_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Notification_ID, Integer.valueOf(UY_R_Notification_ID));
	}

	/** Get UY_R_Notification.
		@return UY_R_Notification	  */
	public int getUY_R_Notification_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Notification_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_RegistroLlamada.
		@param UY_R_RegistroLlamada_ID UY_R_RegistroLlamada	  */
	public void setUY_R_RegistroLlamada_ID (int UY_R_RegistroLlamada_ID)
	{
		if (UY_R_RegistroLlamada_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_RegistroLlamada_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_RegistroLlamada_ID, Integer.valueOf(UY_R_RegistroLlamada_ID));
	}

	/** Get UY_R_RegistroLlamada.
		@return UY_R_RegistroLlamada	  */
	public int getUY_R_RegistroLlamada_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_RegistroLlamada_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Vinculo AD_Reference_ID=1000320 */
	public static final int VINCULO_AD_Reference_ID=1000320;
	/** Conyuge = CONYUGE */
	public static final String VINCULO_Conyuge = "CONYUGE";
	/** Padre = PADRE */
	public static final String VINCULO_Padre = "PADRE";
	/** Madre = MADRE */
	public static final String VINCULO_Madre = "MADRE";
	/** Hermano = HERMANO */
	public static final String VINCULO_Hermano = "HERMANO";
	/** Hermana = HERMANA */
	public static final String VINCULO_Hermana = "HERMANA";
	/** Abuelos = ABUELOS */
	public static final String VINCULO_Abuelos = "ABUELOS";
	/** Tios = TIOS */
	public static final String VINCULO_Tios = "TIOS";
	/** Primos = PRIMOS */
	public static final String VINCULO_Primos = "PRIMOS";
	/** Otros = OTROS */
	public static final String VINCULO_Otros = "OTROS";
	/** Set Vinculo.
		@param Vinculo Vinculo	  */
	public void setVinculo (String Vinculo)
	{

		set_Value (COLUMNNAME_Vinculo, Vinculo);
	}

	/** Get Vinculo.
		@return Vinculo	  */
	public String getVinculo () 
	{
		return (String)get_Value(COLUMNNAME_Vinculo);
	}
}