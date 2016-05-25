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

/** Generated Model for UY_TT_ActionCard
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_ActionCard extends PO implements I_UY_TT_ActionCard, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131013L;

    /** Standard Constructor */
    public X_UY_TT_ActionCard (Properties ctx, int UY_TT_ActionCard_ID, String trxName)
    {
      super (ctx, UY_TT_ActionCard_ID, trxName);
      /** if (UY_TT_ActionCard_ID == 0)
        {
			setAD_User_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setIsExecuted (false);
// N
			setLink_ID (0);
// 1
			setTTActionType (null);
			setUY_TT_ActionCard_ID (0);
			setUY_TT_Action_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_ActionCard (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_ActionCard[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** TTActionType AD_Reference_ID=1000343 */
	public static final int TTACTIONTYPE_AD_Reference_ID=1000343;
	/** Generar Incidencia = INCIDENCIA */
	public static final String TTACTIONTYPE_GenerarIncidencia = "INCIDENCIA";
	/** Modificar Status = MODSTATUS */
	public static final String TTACTIONTYPE_ModificarStatus = "MODSTATUS";
	/** Set TTActionType.
		@param TTActionType TTActionType	  */
	public void setTTActionType (String TTActionType)
	{

		set_Value (COLUMNNAME_TTActionType, TTActionType);
	}

	/** Get TTActionType.
		@return TTActionType	  */
	public String getTTActionType () 
	{
		return (String)get_Value(COLUMNNAME_TTActionType);
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

	/** Set UY_TT_ActionCard.
		@param UY_TT_ActionCard_ID UY_TT_ActionCard	  */
	public void setUY_TT_ActionCard_ID (int UY_TT_ActionCard_ID)
	{
		if (UY_TT_ActionCard_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ActionCard_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ActionCard_ID, Integer.valueOf(UY_TT_ActionCard_ID));
	}

	/** Get UY_TT_ActionCard.
		@return UY_TT_ActionCard	  */
	public int getUY_TT_ActionCard_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ActionCard_ID);
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

	public I_UY_TT_Mensaje getUY_TT_Mensaje() throws RuntimeException
    {
		return (I_UY_TT_Mensaje)MTable.get(getCtx(), I_UY_TT_Mensaje.Table_Name)
			.getPO(getUY_TT_Mensaje_ID(), get_TrxName());	}

	/** Set UY_TT_Mensaje.
		@param UY_TT_Mensaje_ID UY_TT_Mensaje	  */
	public void setUY_TT_Mensaje_ID (int UY_TT_Mensaje_ID)
	{
		if (UY_TT_Mensaje_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Mensaje_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Mensaje_ID, Integer.valueOf(UY_TT_Mensaje_ID));
	}

	/** Get UY_TT_Mensaje.
		@return UY_TT_Mensaje	  */
	public int getUY_TT_Mensaje_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Mensaje_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_SMS getUY_TT_SMS() throws RuntimeException
    {
		return (I_UY_TT_SMS)MTable.get(getCtx(), I_UY_TT_SMS.Table_Name)
			.getPO(getUY_TT_SMS_ID(), get_TrxName());	}

	/** Set UY_TT_SMS.
		@param UY_TT_SMS_ID UY_TT_SMS	  */
	public void setUY_TT_SMS_ID (int UY_TT_SMS_ID)
	{
		if (UY_TT_SMS_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_SMS_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_SMS_ID, Integer.valueOf(UY_TT_SMS_ID));
	}

	/** Get UY_TT_SMS.
		@return UY_TT_SMS	  */
	public int getUY_TT_SMS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_SMS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}