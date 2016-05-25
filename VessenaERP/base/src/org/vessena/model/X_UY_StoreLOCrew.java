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

/** Generated Model for UY_StoreLOCrew
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_StoreLOCrew extends PO implements I_UY_StoreLOCrew, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160216L;

    /** Standard Constructor */
    public X_UY_StoreLOCrew (Properties ctx, int UY_StoreLOCrew_ID, String trxName)
    {
      super (ctx, UY_StoreLOCrew_ID, trxName);
      /** if (UY_StoreLOCrew_ID == 0)
        {
			setAD_User_ID (0);
			setUY_StoreLoadOrderWay_ID (0);
			setUY_StoreLOCrew_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_StoreLOCrew (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StoreLOCrew[")
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

	/** Set cockpitboss.
		@param cockpitboss cockpitboss	  */
	public void setcockpitboss (boolean cockpitboss)
	{
		set_Value (COLUMNNAME_cockpitboss, Boolean.valueOf(cockpitboss));
	}

	/** Get cockpitboss.
		@return cockpitboss	  */
	public boolean iscockpitboss () 
	{
		Object oo = get_Value(COLUMNNAME_cockpitboss);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_StoreLoadOrderWay getUY_StoreLoadOrderWay() throws RuntimeException
    {
		return (I_UY_StoreLoadOrderWay)MTable.get(getCtx(), I_UY_StoreLoadOrderWay.Table_Name)
			.getPO(getUY_StoreLoadOrderWay_ID(), get_TrxName());	}

	/** Set UY_StoreLoadOrderWay.
		@param UY_StoreLoadOrderWay_ID UY_StoreLoadOrderWay	  */
	public void setUY_StoreLoadOrderWay_ID (int UY_StoreLoadOrderWay_ID)
	{
		if (UY_StoreLoadOrderWay_ID < 1) 
			set_Value (COLUMNNAME_UY_StoreLoadOrderWay_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StoreLoadOrderWay_ID, Integer.valueOf(UY_StoreLoadOrderWay_ID));
	}

	/** Get UY_StoreLoadOrderWay.
		@return UY_StoreLoadOrderWay	  */
	public int getUY_StoreLoadOrderWay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLoadOrderWay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_StoreLOCrew.
		@param UY_StoreLOCrew_ID UY_StoreLOCrew	  */
	public void setUY_StoreLOCrew_ID (int UY_StoreLOCrew_ID)
	{
		if (UY_StoreLOCrew_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StoreLOCrew_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StoreLOCrew_ID, Integer.valueOf(UY_StoreLOCrew_ID));
	}

	/** Get UY_StoreLOCrew.
		@return UY_StoreLOCrew	  */
	public int getUY_StoreLOCrew_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLOCrew_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}