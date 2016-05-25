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

/** Generated Model for UY_R_Notification
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_Notification extends PO implements I_UY_R_Notification, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130624L;

    /** Standard Constructor */
    public X_UY_R_Notification (Properties ctx, int UY_R_Notification_ID, String trxName)
    {
      super (ctx, UY_R_Notification_ID, trxName);
      /** if (UY_R_Notification_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_R_Notification_ID (0);
			setUY_R_Reclamo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_Notification (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_Notification[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set IsNotified.
		@param IsNotified 
		IsNotified
	  */
	public void setIsNotified (boolean IsNotified)
	{
		set_Value (COLUMNNAME_IsNotified, Boolean.valueOf(IsNotified));
	}

	/** Get IsNotified.
		@return IsNotified
	  */
	public boolean isNotified () 
	{
		Object oo = get_Value(COLUMNNAME_IsNotified);
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

	/** NotificationActionType AD_Reference_ID=1000303 */
	public static final int NOTIFICATIONACTIONTYPE_AD_Reference_ID=1000303;
	/** Cerrar Incidencia = CERRAR */
	public static final String NOTIFICATIONACTIONTYPE_CerrarIncidencia = "CERRAR";
	/** Reabrir Incidencia = REABRIR */
	public static final String NOTIFICATIONACTIONTYPE_ReabrirIncidencia = "REABRIR";
	/** PreNotificar Cliente = PRENOTIFICA */
	public static final String NOTIFICATIONACTIONTYPE_PreNotificarCliente = "PRENOTIFICA";
	/** Set NotificationActionType.
		@param NotificationActionType NotificationActionType	  */
	public void setNotificationActionType (String NotificationActionType)
	{

		set_Value (COLUMNNAME_NotificationActionType, NotificationActionType);
	}

	/** Get NotificationActionType.
		@return NotificationActionType	  */
	public String getNotificationActionType () 
	{
		return (String)get_Value(COLUMNNAME_NotificationActionType);
	}

	/** PreActionType AD_Reference_ID=1000308 */
	public static final int PREACTIONTYPE_AD_Reference_ID=1000308;
	/** PreNotificar Cliente = PRENOTIF */
	public static final String PREACTIONTYPE_PreNotificarCliente = "PRENOTIF";
	/** Set PreActionType.
		@param PreActionType 
		PreActionType
	  */
	public void setPreActionType (String PreActionType)
	{

		set_Value (COLUMNNAME_PreActionType, PreActionType);
	}

	/** Get PreActionType.
		@return PreActionType
	  */
	public String getPreActionType () 
	{
		return (String)get_Value(COLUMNNAME_PreActionType);
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

	/** Set UY_R_Notification.
		@param UY_R_Notification_ID UY_R_Notification	  */
	public void setUY_R_Notification_ID (int UY_R_Notification_ID)
	{
		if (UY_R_Notification_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_Notification_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_Notification_ID, Integer.valueOf(UY_R_Notification_ID));
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
}