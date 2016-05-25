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

/** Generated Model for UY_R_ReclamoObserver
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_ReclamoObserver extends PO implements I_UY_R_ReclamoObserver, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130520L;

    /** Standard Constructor */
    public X_UY_R_ReclamoObserver (Properties ctx, int UY_R_ReclamoObserver_ID, String trxName)
    {
      super (ctx, UY_R_ReclamoObserver_ID, trxName);
      /** if (UY_R_ReclamoObserver_ID == 0)
        {
			setAD_User_ID (0);
			setUY_R_Reclamo_ID (0);
			setUY_R_ReclamoObserver_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_ReclamoObserver (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_ReclamoObserver[")
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

	/** Set UY_R_ReclamoObserver.
		@param UY_R_ReclamoObserver_ID UY_R_ReclamoObserver	  */
	public void setUY_R_ReclamoObserver_ID (int UY_R_ReclamoObserver_ID)
	{
		if (UY_R_ReclamoObserver_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamoObserver_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamoObserver_ID, Integer.valueOf(UY_R_ReclamoObserver_ID));
	}

	/** Get UY_R_ReclamoObserver.
		@return UY_R_ReclamoObserver	  */
	public int getUY_R_ReclamoObserver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ReclamoObserver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}