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

/** Generated Model for UY_TR_LoadMsgUser
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_LoadMsgUser extends PO implements I_UY_TR_LoadMsgUser, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150225L;

    /** Standard Constructor */
    public X_UY_TR_LoadMsgUser (Properties ctx, int UY_TR_LoadMsgUser_ID, String trxName)
    {
      super (ctx, UY_TR_LoadMsgUser_ID, trxName);
      /** if (UY_TR_LoadMsgUser_ID == 0)
        {
			setIsSelected (false);
// N
			setUY_TR_LoadMsg_ID (0);
			setUY_TR_LoadMsgUser_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_LoadMsgUser (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_LoadMsgUser[")
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

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set UY_TR_LoadMsgUser.
		@param UY_TR_LoadMsgUser_ID UY_TR_LoadMsgUser	  */
	public void setUY_TR_LoadMsgUser_ID (int UY_TR_LoadMsgUser_ID)
	{
		if (UY_TR_LoadMsgUser_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMsgUser_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMsgUser_ID, Integer.valueOf(UY_TR_LoadMsgUser_ID));
	}

	/** Get UY_TR_LoadMsgUser.
		@return UY_TR_LoadMsgUser	  */
	public int getUY_TR_LoadMsgUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMsgUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}