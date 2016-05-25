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

/** Generated Model for UY_TT_ConfigNeed
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_ConfigNeed extends PO implements I_UY_TT_ConfigNeed, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150827L;

    /** Standard Constructor */
    public X_UY_TT_ConfigNeed (Properties ctx, int UY_TT_ConfigNeed_ID, String trxName)
    {
      super (ctx, UY_TT_ConfigNeed_ID, trxName);
      /** if (UY_TT_ConfigNeed_ID == 0)
        {
			setCardAction (null);
			setRetireType (null);
			setUY_TT_Config_ID (0);
			setUY_TT_ConfigNeed_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_ConfigNeed (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_ConfigNeed[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** CardAction AD_Reference_ID=1000325 */
	public static final int CARDACTION_AD_Reference_ID=1000325;
	/** Renovacion = RENOVACION */
	public static final String CARDACTION_Renovacion = "RENOVACION";
	/** Reimpresion = REIMPRESION */
	public static final String CARDACTION_Reimpresion = "REIMPRESION";
	/** Nueva = NUEVA */
	public static final String CARDACTION_Nueva = "NUEVA";
	/** Set CardAction.
		@param CardAction CardAction	  */
	public void setCardAction (String CardAction)
	{

		set_Value (COLUMNNAME_CardAction, CardAction);
	}

	/** Get CardAction.
		@return CardAction	  */
	public String getCardAction () 
	{
		return (String)get_Value(COLUMNNAME_CardAction);
	}

	/** RetireType AD_Reference_ID=1010383 */
	public static final int RETIRETYPE_AD_Reference_ID=1010383;
	/** SOLO TITULAR = TITULAR */
	public static final String RETIRETYPE_SOLOTITULAR = "TITULAR";
	/** TODOS = TODOS */
	public static final String RETIRETYPE_TODOS = "TODOS";
	/** Set RetireType.
		@param RetireType RetireType	  */
	public void setRetireType (String RetireType)
	{

		set_Value (COLUMNNAME_RetireType, RetireType);
	}

	/** Get RetireType.
		@return RetireType	  */
	public String getRetireType () 
	{
		return (String)get_Value(COLUMNNAME_RetireType);
	}

	public I_UY_TT_Config getUY_TT_Config() throws RuntimeException
    {
		return (I_UY_TT_Config)MTable.get(getCtx(), I_UY_TT_Config.Table_Name)
			.getPO(getUY_TT_Config_ID(), get_TrxName());	}

	/** Set UY_TT_Config.
		@param UY_TT_Config_ID UY_TT_Config	  */
	public void setUY_TT_Config_ID (int UY_TT_Config_ID)
	{
		if (UY_TT_Config_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Config_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Config_ID, Integer.valueOf(UY_TT_Config_ID));
	}

	/** Get UY_TT_Config.
		@return UY_TT_Config	  */
	public int getUY_TT_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_ConfigNeed.
		@param UY_TT_ConfigNeed_ID UY_TT_ConfigNeed	  */
	public void setUY_TT_ConfigNeed_ID (int UY_TT_ConfigNeed_ID)
	{
		if (UY_TT_ConfigNeed_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ConfigNeed_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ConfigNeed_ID, Integer.valueOf(UY_TT_ConfigNeed_ID));
	}

	/** Get UY_TT_ConfigNeed.
		@return UY_TT_ConfigNeed	  */
	public int getUY_TT_ConfigNeed_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ConfigNeed_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}