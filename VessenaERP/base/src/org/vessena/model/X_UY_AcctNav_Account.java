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

/** Generated Model for UY_AcctNav_Account
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_AcctNav_Account extends PO implements I_UY_AcctNav_Account, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121123L;

    /** Standard Constructor */
    public X_UY_AcctNav_Account (Properties ctx, int UY_AcctNav_Account_ID, String trxName)
    {
      super (ctx, UY_AcctNav_Account_ID, trxName);
      /** if (UY_AcctNav_Account_ID == 0)
        {
			setUY_AcctNav_Account_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_AcctNav_Account (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_AcctNav_Account[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_AcctNav_Account.
		@param UY_AcctNav_Account_ID UY_AcctNav_Account	  */
	public void setUY_AcctNav_Account_ID (int UY_AcctNav_Account_ID)
	{
		if (UY_AcctNav_Account_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_AcctNav_Account_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_AcctNav_Account_ID, Integer.valueOf(UY_AcctNav_Account_ID));
	}

	/** Get UY_AcctNav_Account.
		@return UY_AcctNav_Account	  */
	public int getUY_AcctNav_Account_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AcctNav_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_AcctNav getUY_AcctNav() throws RuntimeException
    {
		return (I_UY_AcctNav)MTable.get(getCtx(), I_UY_AcctNav.Table_Name)
			.getPO(getUY_AcctNav_ID(), get_TrxName());	}

	/** Set UY_AcctNav.
		@param UY_AcctNav_ID UY_AcctNav	  */
	public void setUY_AcctNav_ID (int UY_AcctNav_ID)
	{
		if (UY_AcctNav_ID < 1) 
			set_Value (COLUMNNAME_UY_AcctNav_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AcctNav_ID, Integer.valueOf(UY_AcctNav_ID));
	}

	/** Get UY_AcctNav.
		@return UY_AcctNav	  */
	public int getUY_AcctNav_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AcctNav_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}