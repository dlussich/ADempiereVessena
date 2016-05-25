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

/** Generated Model for UY_BG_TransTracking
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_TransTracking extends PO implements I_UY_BG_TransTracking, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150604L;

    /** Standard Constructor */
    public X_UY_BG_TransTracking (Properties ctx, int UY_BG_TransTracking_ID, String trxName)
    {
      super (ctx, UY_BG_TransTracking_ID, trxName);
      /** if (UY_BG_TransTracking_ID == 0)
        {
			setUY_BG_Transaction_ID (0);
			setUY_BG_TransTracking_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_TransTracking (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_TransTracking[")
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

	public I_UY_BG_Transaction getUY_BG_Transaction() throws RuntimeException
    {
		return (I_UY_BG_Transaction)MTable.get(getCtx(), I_UY_BG_Transaction.Table_Name)
			.getPO(getUY_BG_Transaction_ID(), get_TrxName());	}

	/** Set UY_BG_Transaction.
		@param UY_BG_Transaction_ID UY_BG_Transaction	  */
	public void setUY_BG_Transaction_ID (int UY_BG_Transaction_ID)
	{
		if (UY_BG_Transaction_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Transaction_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Transaction_ID, Integer.valueOf(UY_BG_Transaction_ID));
	}

	/** Get UY_BG_Transaction.
		@return UY_BG_Transaction	  */
	public int getUY_BG_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BG_TransTracking.
		@param UY_BG_TransTracking_ID UY_BG_TransTracking	  */
	public void setUY_BG_TransTracking_ID (int UY_BG_TransTracking_ID)
	{
		if (UY_BG_TransTracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_TransTracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_TransTracking_ID, Integer.valueOf(UY_BG_TransTracking_ID));
	}

	/** Get UY_BG_TransTracking.
		@return UY_BG_TransTracking	  */
	public int getUY_BG_TransTracking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_TransTracking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}