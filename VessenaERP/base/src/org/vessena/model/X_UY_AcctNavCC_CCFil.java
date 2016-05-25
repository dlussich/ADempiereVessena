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

/** Generated Model for UY_AcctNavCC_CCFil
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_AcctNavCC_CCFil extends PO implements I_UY_AcctNavCC_CCFil, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130131L;

    /** Standard Constructor */
    public X_UY_AcctNavCC_CCFil (Properties ctx, int UY_AcctNavCC_CCFil_ID, String trxName)
    {
      super (ctx, UY_AcctNavCC_CCFil_ID, trxName);
      /** if (UY_AcctNavCC_CCFil_ID == 0)
        {
			setUY_AcctNavCC_CCFil_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_AcctNavCC_CCFil (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_AcctNavCC_CCFil[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_Activity_ID_1.
		@param C_Activity_ID_1 C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1)
	{
		set_Value (COLUMNNAME_C_Activity_ID_1, Integer.valueOf(C_Activity_ID_1));
	}

	/** Get C_Activity_ID_1.
		@return C_Activity_ID_1	  */
	public int getC_Activity_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_AcctNavCC_CCFil.
		@param UY_AcctNavCC_CCFil_ID UY_AcctNavCC_CCFil	  */
	public void setUY_AcctNavCC_CCFil_ID (int UY_AcctNavCC_CCFil_ID)
	{
		if (UY_AcctNavCC_CCFil_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_AcctNavCC_CCFil_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_AcctNavCC_CCFil_ID, Integer.valueOf(UY_AcctNavCC_CCFil_ID));
	}

	/** Get UY_AcctNavCC_CCFil.
		@return UY_AcctNavCC_CCFil	  */
	public int getUY_AcctNavCC_CCFil_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AcctNavCC_CCFil_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_AcctNavCC getUY_AcctNavCC() throws RuntimeException
    {
		return (I_UY_AcctNavCC)MTable.get(getCtx(), I_UY_AcctNavCC.Table_Name)
			.getPO(getUY_AcctNavCC_ID(), get_TrxName());	}

	/** Set UY_AcctNavCC.
		@param UY_AcctNavCC_ID UY_AcctNavCC	  */
	public void setUY_AcctNavCC_ID (int UY_AcctNavCC_ID)
	{
		if (UY_AcctNavCC_ID < 1) 
			set_Value (COLUMNNAME_UY_AcctNavCC_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AcctNavCC_ID, Integer.valueOf(UY_AcctNavCC_ID));
	}

	/** Get UY_AcctNavCC.
		@return UY_AcctNavCC	  */
	public int getUY_AcctNavCC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AcctNavCC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}