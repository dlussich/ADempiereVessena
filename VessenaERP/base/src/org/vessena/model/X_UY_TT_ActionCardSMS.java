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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_ActionCardSMS
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_TT_ActionCardSMS extends PO implements I_UY_TT_ActionCardSMS, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131022L;

    /** Standard Constructor */
    public X_UY_TT_ActionCardSMS (Properties ctx, int UY_TT_ActionCardSMS_ID, String trxName)
    {
      super (ctx, UY_TT_ActionCardSMS_ID, trxName);
      /** if (UY_TT_ActionCardSMS_ID == 0)
        {
			setUY_TT_ActionCard_ID (0);
			setUY_TT_ActionCardSMS_ID (0);
			setUY_TT_SMS_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_ActionCardSMS (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_ActionCardSMS[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Mobile.
		@param Mobile Mobile	  */
	public void setMobile (String Mobile)
	{
		set_Value (COLUMNNAME_Mobile, Mobile);
	}

	/** Get Mobile.
		@return Mobile	  */
	public String getMobile () 
	{
		return (String)get_Value(COLUMNNAME_Mobile);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	public I_UY_TT_ActionCard getUY_TT_ActionCard() throws RuntimeException
    {
		return (I_UY_TT_ActionCard)MTable.get(getCtx(), I_UY_TT_ActionCard.Table_Name)
			.getPO(getUY_TT_ActionCard_ID(), get_TrxName());	}

	/** Set UY_TT_ActionCard.
		@param UY_TT_ActionCard_ID UY_TT_ActionCard	  */
	public void setUY_TT_ActionCard_ID (int UY_TT_ActionCard_ID)
	{
		if (UY_TT_ActionCard_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_ActionCard_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_ActionCard_ID, Integer.valueOf(UY_TT_ActionCard_ID));
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

	/** Set UY_TT_ActionCardSMS.
		@param UY_TT_ActionCardSMS_ID UY_TT_ActionCardSMS	  */
	public void setUY_TT_ActionCardSMS_ID (int UY_TT_ActionCardSMS_ID)
	{
		if (UY_TT_ActionCardSMS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ActionCardSMS_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ActionCardSMS_ID, Integer.valueOf(UY_TT_ActionCardSMS_ID));
	}

	/** Get UY_TT_ActionCardSMS.
		@return UY_TT_ActionCardSMS	  */
	public int getUY_TT_ActionCardSMS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ActionCardSMS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException
    {
		return (I_UY_TT_Card)MTable.get(getCtx(), I_UY_TT_Card.Table_Name)
			.getPO(getUY_TT_Card_ID(), get_TrxName());	}

	/** Set UY_TT_Card.
		@param UY_TT_Card_ID UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID)
	{
		if (UY_TT_Card_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Card_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Card_ID, Integer.valueOf(UY_TT_Card_ID));
	}

	/** Get UY_TT_Card.
		@return UY_TT_Card	  */
	public int getUY_TT_Card_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Card_ID);
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