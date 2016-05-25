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

/** Generated Model for UY_R_Tracking
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_Tracking extends PO implements I_UY_R_Tracking, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130618L;

    /** Standard Constructor */
    public X_UY_R_Tracking (Properties ctx, int UY_R_Tracking_ID, String trxName)
    {
      super (ctx, UY_R_Tracking_ID, trxName);
      /** if (UY_R_Tracking_ID == 0)
        {
			setUY_R_Tracking_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_Tracking (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_Tracking[")
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

	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	public I_UY_R_ActionResponse getUY_R_ActionResponse() throws RuntimeException
    {
		return (I_UY_R_ActionResponse)MTable.get(getCtx(), I_UY_R_ActionResponse.Table_Name)
			.getPO(getUY_R_ActionResponse_ID(), get_TrxName());	}

	/** Set UY_R_ActionResponse.
		@param UY_R_ActionResponse_ID UY_R_ActionResponse	  */
	public void setUY_R_ActionResponse_ID (int UY_R_ActionResponse_ID)
	{
		if (UY_R_ActionResponse_ID < 1) 
			set_Value (COLUMNNAME_UY_R_ActionResponse_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_ActionResponse_ID, Integer.valueOf(UY_R_ActionResponse_ID));
	}

	/** Get UY_R_ActionResponse.
		@return UY_R_ActionResponse	  */
	public int getUY_R_ActionResponse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ActionResponse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_ReclamoAccion getUY_R_ReclamoAccion() throws RuntimeException
    {
		return (I_UY_R_ReclamoAccion)MTable.get(getCtx(), I_UY_R_ReclamoAccion.Table_Name)
			.getPO(getUY_R_ReclamoAccion_ID(), get_TrxName());	}

	/** Set UY_R_ReclamoAccion.
		@param UY_R_ReclamoAccion_ID UY_R_ReclamoAccion	  */
	public void setUY_R_ReclamoAccion_ID (int UY_R_ReclamoAccion_ID)
	{
		if (UY_R_ReclamoAccion_ID < 1) 
			set_Value (COLUMNNAME_UY_R_ReclamoAccion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_ReclamoAccion_ID, Integer.valueOf(UY_R_ReclamoAccion_ID));
	}

	/** Get UY_R_ReclamoAccion.
		@return UY_R_ReclamoAccion	  */
	public int getUY_R_ReclamoAccion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ReclamoAccion_ID);
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

	public I_UY_R_Tarea getUY_R_Tarea() throws RuntimeException
    {
		return (I_UY_R_Tarea)MTable.get(getCtx(), I_UY_R_Tarea.Table_Name)
			.getPO(getUY_R_Tarea_ID(), get_TrxName());	}

	/** Set UY_R_Tarea.
		@param UY_R_Tarea_ID UY_R_Tarea	  */
	public void setUY_R_Tarea_ID (int UY_R_Tarea_ID)
	{
		if (UY_R_Tarea_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Tarea_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Tarea_ID, Integer.valueOf(UY_R_Tarea_ID));
	}

	/** Get UY_R_Tarea.
		@return UY_R_Tarea	  */
	public int getUY_R_Tarea_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Tarea_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Tracking.
		@param UY_R_Tracking_ID UY_R_Tracking	  */
	public void setUY_R_Tracking_ID (int UY_R_Tracking_ID)
	{
		if (UY_R_Tracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_Tracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_Tracking_ID, Integer.valueOf(UY_R_Tracking_ID));
	}

	/** Get UY_R_Tracking.
		@return UY_R_Tracking	  */
	public int getUY_R_Tracking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Tracking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}