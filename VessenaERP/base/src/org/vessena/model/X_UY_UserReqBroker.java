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

/** Generated Model for UY_UserReqBroker
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_UserReqBroker extends PO implements I_UY_UserReqBroker, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150605L;

    /** Standard Constructor */
    public X_UY_UserReqBroker (Properties ctx, int UY_UserReqBroker_ID, String trxName)
    {
      super (ctx, UY_UserReqBroker_ID, trxName);
      /** if (UY_UserReqBroker_ID == 0)
        {
			setIsSmartPhone1 (false);
// N
			setIsSmartPhone2 (false);
// N
			setUY_UserReqBroker_ID (0);
			setUY_UserReq_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_UserReqBroker (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_UserReqBroker[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set FirstName.
		@param FirstName FirstName	  */
	public void setFirstName (String FirstName)
	{
		set_Value (COLUMNNAME_FirstName, FirstName);
	}

	/** Get FirstName.
		@return FirstName	  */
	public String getFirstName () 
	{
		return (String)get_Value(COLUMNNAME_FirstName);
	}

	/** Set FirstSurname.
		@param FirstSurname FirstSurname	  */
	public void setFirstSurname (String FirstSurname)
	{
		set_Value (COLUMNNAME_FirstSurname, FirstSurname);
	}

	/** Get FirstSurname.
		@return FirstSurname	  */
	public String getFirstSurname () 
	{
		return (String)get_Value(COLUMNNAME_FirstSurname);
	}

	/** Set IsSmartPhone1.
		@param IsSmartPhone1 IsSmartPhone1	  */
	public void setIsSmartPhone1 (boolean IsSmartPhone1)
	{
		set_Value (COLUMNNAME_IsSmartPhone1, Boolean.valueOf(IsSmartPhone1));
	}

	/** Get IsSmartPhone1.
		@return IsSmartPhone1	  */
	public boolean isSmartPhone1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsSmartPhone1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSmartPhone2.
		@param IsSmartPhone2 IsSmartPhone2	  */
	public void setIsSmartPhone2 (boolean IsSmartPhone2)
	{
		set_Value (COLUMNNAME_IsSmartPhone2, Boolean.valueOf(IsSmartPhone2));
	}

	/** Get IsSmartPhone2.
		@return IsSmartPhone2	  */
	public boolean isSmartPhone2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsSmartPhone2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mobile1.
		@param Mobile1 Mobile1	  */
	public void setMobile1 (String Mobile1)
	{
		set_Value (COLUMNNAME_Mobile1, Mobile1);
	}

	/** Get Mobile1.
		@return Mobile1	  */
	public String getMobile1 () 
	{
		return (String)get_Value(COLUMNNAME_Mobile1);
	}

	/** Set Mobile2.
		@param Mobile2 Mobile2	  */
	public void setMobile2 (String Mobile2)
	{
		set_Value (COLUMNNAME_Mobile2, Mobile2);
	}

	/** Get Mobile2.
		@return Mobile2	  */
	public String getMobile2 () 
	{
		return (String)get_Value(COLUMNNAME_Mobile2);
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

	/** Set SecondName.
		@param SecondName SecondName	  */
	public void setSecondName (String SecondName)
	{
		set_Value (COLUMNNAME_SecondName, SecondName);
	}

	/** Get SecondName.
		@return SecondName	  */
	public String getSecondName () 
	{
		return (String)get_Value(COLUMNNAME_SecondName);
	}

	/** Set SecondSurname.
		@param SecondSurname SecondSurname	  */
	public void setSecondSurname (String SecondSurname)
	{
		set_Value (COLUMNNAME_SecondSurname, SecondSurname);
	}

	/** Get SecondSurname.
		@return SecondSurname	  */
	public String getSecondSurname () 
	{
		return (String)get_Value(COLUMNNAME_SecondSurname);
	}

	/** Set UY_UserReqBroker.
		@param UY_UserReqBroker_ID UY_UserReqBroker	  */
	public void setUY_UserReqBroker_ID (int UY_UserReqBroker_ID)
	{
		if (UY_UserReqBroker_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_UserReqBroker_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_UserReqBroker_ID, Integer.valueOf(UY_UserReqBroker_ID));
	}

	/** Get UY_UserReqBroker.
		@return UY_UserReqBroker	  */
	public int getUY_UserReqBroker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_UserReqBroker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_UserReq getUY_UserReq() throws RuntimeException
    {
		return (I_UY_UserReq)MTable.get(getCtx(), I_UY_UserReq.Table_Name)
			.getPO(getUY_UserReq_ID(), get_TrxName());	}

	/** Set UY_UserReq.
		@param UY_UserReq_ID UY_UserReq	  */
	public void setUY_UserReq_ID (int UY_UserReq_ID)
	{
		if (UY_UserReq_ID < 1) 
			set_Value (COLUMNNAME_UY_UserReq_ID, null);
		else 
			set_Value (COLUMNNAME_UY_UserReq_ID, Integer.valueOf(UY_UserReq_ID));
	}

	/** Get UY_UserReq.
		@return UY_UserReq	  */
	public int getUY_UserReq_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_UserReq_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}