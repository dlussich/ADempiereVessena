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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_TR_LoadMsgLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_LoadMsgLog extends PO implements I_UY_TR_LoadMsgLog, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150303L;

    /** Standard Constructor */
    public X_UY_TR_LoadMsgLog (Properties ctx, int UY_TR_LoadMsgLog_ID, String trxName)
    {
      super (ctx, UY_TR_LoadMsgLog_ID, trxName);
      /** if (UY_TR_LoadMsgLog_ID == 0)
        {
			setIsSent (false);
// N
			setUY_TR_LoadMsg_ID (0);
			setUY_TR_LoadMsgLog_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_LoadMsgLog (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_LoadMsgLog[")
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set IsSent.
		@param IsSent IsSent	  */
	public void setIsSent (boolean IsSent)
	{
		set_Value (COLUMNNAME_IsSent, Boolean.valueOf(IsSent));
	}

	/** Get IsSent.
		@return IsSent	  */
	public boolean isSent () 
	{
		Object oo = get_Value(COLUMNNAME_IsSent);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_LoadMsg getUY_TR_LoadMsg() throws RuntimeException
    {
		return (I_UY_TR_LoadMsg)MTable.get(getCtx(), I_UY_TR_LoadMsg.Table_Name)
			.getPO(getUY_TR_LoadMsg_ID(), get_TrxName());	}

	/** Set UY_TR_LoadMsg.
		@param UY_TR_LoadMsg_ID UY_TR_LoadMsg	  */
	public void setUY_TR_LoadMsg_ID (int UY_TR_LoadMsg_ID)
	{
		if (UY_TR_LoadMsg_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_LoadMsg_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_LoadMsg_ID, Integer.valueOf(UY_TR_LoadMsg_ID));
	}

	/** Get UY_TR_LoadMsg.
		@return UY_TR_LoadMsg	  */
	public int getUY_TR_LoadMsg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMsg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_LoadMsgLine getUY_TR_LoadMsgLine() throws RuntimeException
    {
		return (I_UY_TR_LoadMsgLine)MTable.get(getCtx(), I_UY_TR_LoadMsgLine.Table_Name)
			.getPO(getUY_TR_LoadMsgLine_ID(), get_TrxName());	}

	/** Set UY_TR_LoadMsgLine.
		@param UY_TR_LoadMsgLine_ID UY_TR_LoadMsgLine	  */
	public void setUY_TR_LoadMsgLine_ID (int UY_TR_LoadMsgLine_ID)
	{
		if (UY_TR_LoadMsgLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_LoadMsgLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_LoadMsgLine_ID, Integer.valueOf(UY_TR_LoadMsgLine_ID));
	}

	/** Get UY_TR_LoadMsgLine.
		@return UY_TR_LoadMsgLine	  */
	public int getUY_TR_LoadMsgLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMsgLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_LoadMsgLog.
		@param UY_TR_LoadMsgLog_ID UY_TR_LoadMsgLog	  */
	public void setUY_TR_LoadMsgLog_ID (int UY_TR_LoadMsgLog_ID)
	{
		if (UY_TR_LoadMsgLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMsgLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMsgLog_ID, Integer.valueOf(UY_TR_LoadMsgLog_ID));
	}

	/** Get UY_TR_LoadMsgLog.
		@return UY_TR_LoadMsgLog	  */
	public int getUY_TR_LoadMsgLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMsgLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException
    {
		return (I_UY_TR_TransOrder)MTable.get(getCtx(), I_UY_TR_TransOrder.Table_Name)
			.getPO(getUY_TR_TransOrder_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrder.
		@param UY_TR_TransOrder_ID UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID)
	{
		if (UY_TR_TransOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, Integer.valueOf(UY_TR_TransOrder_ID));
	}

	/** Get UY_TR_TransOrder.
		@return UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TransOrderLine getUY_TR_TransOrderLine() throws RuntimeException
    {
		return (I_UY_TR_TransOrderLine)MTable.get(getCtx(), I_UY_TR_TransOrderLine.Table_Name)
			.getPO(getUY_TR_TransOrderLine_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrderLine.
		@param UY_TR_TransOrderLine_ID UY_TR_TransOrderLine	  */
	public void setUY_TR_TransOrderLine_ID (int UY_TR_TransOrderLine_ID)
	{
		if (UY_TR_TransOrderLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrderLine_ID, Integer.valueOf(UY_TR_TransOrderLine_ID));
	}

	/** Get UY_TR_TransOrderLine.
		@return UY_TR_TransOrderLine	  */
	public int getUY_TR_TransOrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException
    {
		return (I_UY_TR_Trip)MTable.get(getCtx(), I_UY_TR_Trip.Table_Name)
			.getPO(getUY_TR_Trip_ID(), get_TrxName());	}

	/** Set UY_TR_Trip.
		@param UY_TR_Trip_ID UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID)
	{
		if (UY_TR_Trip_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, Integer.valueOf(UY_TR_Trip_ID));
	}

	/** Get UY_TR_Trip.
		@return UY_TR_Trip	  */
	public int getUY_TR_Trip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Trip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}