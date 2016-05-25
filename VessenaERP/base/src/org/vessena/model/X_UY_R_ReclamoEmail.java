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

/** Generated Model for UY_R_ReclamoEmail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_ReclamoEmail extends PO implements I_UY_R_ReclamoEmail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130814L;

    /** Standard Constructor */
    public X_UY_R_ReclamoEmail (Properties ctx, int UY_R_ReclamoEmail_ID, String trxName)
    {
      super (ctx, UY_R_ReclamoEmail_ID, trxName);
      /** if (UY_R_ReclamoEmail_ID == 0)
        {
			setIsExecuted (false);
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setUY_R_ReclamoEmail_ID (0);
			setUY_R_Reclamo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_ReclamoEmail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_ReclamoEmail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** ReclamoEmailType AD_Reference_ID=1000312 */
	public static final int RECLAMOEMAILTYPE_AD_Reference_ID=1000312;
	/** Primer Aviso = FIRSTADVICE */
	public static final String RECLAMOEMAILTYPE_PrimerAviso = "FIRSTADVICE";
	/** Segundo Aviso = SECONDADVICE */
	public static final String RECLAMOEMAILTYPE_SegundoAviso = "SECONDADVICE";
	/** Tercer Aviso = THIRDADVICE */
	public static final String RECLAMOEMAILTYPE_TercerAviso = "THIRDADVICE";
	/** Cuarto Aviso = FOURTHADVICE */
	public static final String RECLAMOEMAILTYPE_CuartoAviso = "FOURTHADVICE";
	/** Creacion de Incidencia = CREATE */
	public static final String RECLAMOEMAILTYPE_CreacionDeIncidencia = "CREATE";
	/** Cierre de Incidencia = CLOSE */
	public static final String RECLAMOEMAILTYPE_CierreDeIncidencia = "CLOSE";
	/** Notificacion de Canal Escrito = NOTIFESCRITO */
	public static final String RECLAMOEMAILTYPE_NotificacionDeCanalEscrito = "NOTIFESCRITO";
	/** Set ReclamoEmailType.
		@param ReclamoEmailType ReclamoEmailType	  */
	public void setReclamoEmailType (String ReclamoEmailType)
	{

		set_Value (COLUMNNAME_ReclamoEmailType, ReclamoEmailType);
	}

	/** Get ReclamoEmailType.
		@return ReclamoEmailType	  */
	public String getReclamoEmailType () 
	{
		return (String)get_Value(COLUMNNAME_ReclamoEmailType);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set UY_R_ReclamoEmail.
		@param UY_R_ReclamoEmail_ID UY_R_ReclamoEmail	  */
	public void setUY_R_ReclamoEmail_ID (int UY_R_ReclamoEmail_ID)
	{
		if (UY_R_ReclamoEmail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamoEmail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamoEmail_ID, Integer.valueOf(UY_R_ReclamoEmail_ID));
	}

	/** Get UY_R_ReclamoEmail.
		@return UY_R_ReclamoEmail	  */
	public int getUY_R_ReclamoEmail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ReclamoEmail_ID);
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
}