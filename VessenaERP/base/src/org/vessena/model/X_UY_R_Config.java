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

/** Generated Model for UY_R_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_Config extends PO implements I_UY_R_Config, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130710L;

    /** Standard Constructor */
    public X_UY_R_Config (Properties ctx, int UY_R_Config_ID, String trxName)
    {
      super (ctx, UY_R_Config_ID, trxName);
      /** if (UY_R_Config_ID == 0)
        {
			setUY_R_Config_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_R_Config (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_Config[")
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

	/** Set FirstEmailHourInterval.
		@param FirstEmailHourInterval FirstEmailHourInterval	  */
	public void setFirstEmailHourInterval (int FirstEmailHourInterval)
	{
		set_Value (COLUMNNAME_FirstEmailHourInterval, Integer.valueOf(FirstEmailHourInterval));
	}

	/** Get FirstEmailHourInterval.
		@return FirstEmailHourInterval	  */
	public int getFirstEmailHourInterval () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FirstEmailHourInterval);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mail Text.
		@param MailText 
		Text used for Mail message
	  */
	public void setMailText (String MailText)
	{
		set_Value (COLUMNNAME_MailText, MailText);
	}

	/** Get Mail Text.
		@return Text used for Mail message
	  */
	public String getMailText () 
	{
		return (String)get_Value(COLUMNNAME_MailText);
	}

	/** Set Mail Text 2.
		@param MailText2 
		Optional second text part used for Mail message
	  */
	public void setMailText2 (String MailText2)
	{
		set_Value (COLUMNNAME_MailText2, MailText2);
	}

	/** Get Mail Text 2.
		@return Optional second text part used for Mail message
	  */
	public String getMailText2 () 
	{
		return (String)get_Value(COLUMNNAME_MailText2);
	}

	/** Set Mail Text 3.
		@param MailText3 
		Optional third text part used for Mail message
	  */
	public void setMailText3 (String MailText3)
	{
		set_Value (COLUMNNAME_MailText3, MailText3);
	}

	/** Get Mail Text 3.
		@return Optional third text part used for Mail message
	  */
	public String getMailText3 () 
	{
		return (String)get_Value(COLUMNNAME_MailText3);
	}

	/** Set MailText4.
		@param MailText4 
		MailText4
	  */
	public void setMailText4 (String MailText4)
	{
		set_Value (COLUMNNAME_MailText4, MailText4);
	}

	/** Get MailText4.
		@return MailText4
	  */
	public String getMailText4 () 
	{
		return (String)get_Value(COLUMNNAME_MailText4);
	}

	/** Set MailText5.
		@param MailText5 
		MailText5
	  */
	public void setMailText5 (String MailText5)
	{
		set_Value (COLUMNNAME_MailText5, MailText5);
	}

	/** Get MailText5.
		@return MailText5
	  */
	public String getMailText5 () 
	{
		return (String)get_Value(COLUMNNAME_MailText5);
	}

	/** Set Password.
		@param Password 
		Password of any length (case sensitive)
	  */
	public void setPassword (String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Password.
		@return Password of any length (case sensitive)
	  */
	public String getPassword () 
	{
		return (String)get_Value(COLUMNNAME_Password);
	}

	/** Set SecondEmailDayInterval.
		@param SecondEmailDayInterval SecondEmailDayInterval	  */
	public void setSecondEmailDayInterval (int SecondEmailDayInterval)
	{
		set_Value (COLUMNNAME_SecondEmailDayInterval, Integer.valueOf(SecondEmailDayInterval));
	}

	/** Get SecondEmailDayInterval.
		@return SecondEmailDayInterval	  */
	public int getSecondEmailDayInterval () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SecondEmailDayInterval);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SendEMailFinish.
		@param SendEMailFinish SendEMailFinish	  */
	public void setSendEMailFinish (boolean SendEMailFinish)
	{
		set_Value (COLUMNNAME_SendEMailFinish, Boolean.valueOf(SendEMailFinish));
	}

	/** Get SendEMailFinish.
		@return SendEMailFinish	  */
	public boolean isSendEMailFinish () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMailFinish);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SendEMailFirstAdvice.
		@param SendEMailFirstAdvice SendEMailFirstAdvice	  */
	public void setSendEMailFirstAdvice (boolean SendEMailFirstAdvice)
	{
		set_Value (COLUMNNAME_SendEMailFirstAdvice, Boolean.valueOf(SendEMailFirstAdvice));
	}

	/** Get SendEMailFirstAdvice.
		@return SendEMailFirstAdvice	  */
	public boolean isSendEMailFirstAdvice () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMailFirstAdvice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SendEMailSecondAdvice.
		@param SendEMailSecondAdvice SendEMailSecondAdvice	  */
	public void setSendEMailSecondAdvice (boolean SendEMailSecondAdvice)
	{
		set_Value (COLUMNNAME_SendEMailSecondAdvice, Boolean.valueOf(SendEMailSecondAdvice));
	}

	/** Get SendEMailSecondAdvice.
		@return SendEMailSecondAdvice	  */
	public boolean isSendEMailSecondAdvice () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMailSecondAdvice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SendEMailStart.
		@param SendEMailStart SendEMailStart	  */
	public void setSendEMailStart (boolean SendEMailStart)
	{
		set_Value (COLUMNNAME_SendEMailStart, Boolean.valueOf(SendEMailStart));
	}

	/** Get SendEMailStart.
		@return SendEMailStart	  */
	public boolean isSendEMailStart () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMailStart);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SendEMailThirdAdvice.
		@param SendEMailThirdAdvice SendEMailThirdAdvice	  */
	public void setSendEMailThirdAdvice (boolean SendEMailThirdAdvice)
	{
		set_Value (COLUMNNAME_SendEMailThirdAdvice, Boolean.valueOf(SendEMailThirdAdvice));
	}

	/** Get SendEMailThirdAdvice.
		@return SendEMailThirdAdvice	  */
	public boolean isSendEMailThirdAdvice () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMailThirdAdvice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ThirdEmailDayInterval.
		@param ThirdEmailDayInterval ThirdEmailDayInterval	  */
	public void setThirdEmailDayInterval (int ThirdEmailDayInterval)
	{
		set_Value (COLUMNNAME_ThirdEmailDayInterval, Integer.valueOf(ThirdEmailDayInterval));
	}

	/** Get ThirdEmailDayInterval.
		@return ThirdEmailDayInterval	  */
	public int getThirdEmailDayInterval () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ThirdEmailDayInterval);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Config.
		@param UY_R_Config_ID UY_R_Config	  */
	public void setUY_R_Config_ID (int UY_R_Config_ID)
	{
		if (UY_R_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_Config_ID, Integer.valueOf(UY_R_Config_ID));
	}

	/** Get UY_R_Config.
		@return UY_R_Config	  */
	public int getUY_R_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Config_ID);
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
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}