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

/** Generated Model for UY_PriceLoadLineOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PriceLoadLineOrg extends PO implements I_UY_PriceLoadLineOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160128L;

    /** Standard Constructor */
    public X_UY_PriceLoadLineOrg (Properties ctx, int UY_PriceLoadLineOrg_ID, String trxName)
    {
      super (ctx, UY_PriceLoadLineOrg_ID, trxName);
      /** if (UY_PriceLoadLineOrg_ID == 0)
        {
			setAD_Org_ID_To (0);
			setUY_PriceLoadLine_ID (0);
			setUY_PriceLoadLineOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PriceLoadLineOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PriceLoadLineOrg[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_Org_ID_To.
		@param AD_Org_ID_To AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To)
	{
		set_Value (COLUMNNAME_AD_Org_ID_To, Integer.valueOf(AD_Org_ID_To));
	}

	/** Get AD_Org_ID_To.
		@return AD_Org_ID_To	  */
	public int getAD_Org_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Org_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PriceLoadLine getUY_PriceLoadLine() throws RuntimeException
    {
		return (I_UY_PriceLoadLine)MTable.get(getCtx(), I_UY_PriceLoadLine.Table_Name)
			.getPO(getUY_PriceLoadLine_ID(), get_TrxName());	}

	/** Set UY_PriceLoadLine.
		@param UY_PriceLoadLine_ID UY_PriceLoadLine	  */
	public void setUY_PriceLoadLine_ID (int UY_PriceLoadLine_ID)
	{
		if (UY_PriceLoadLine_ID < 1) 
			set_Value (COLUMNNAME_UY_PriceLoadLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PriceLoadLine_ID, Integer.valueOf(UY_PriceLoadLine_ID));
	}

	/** Get UY_PriceLoadLine.
		@return UY_PriceLoadLine	  */
	public int getUY_PriceLoadLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PriceLoadLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PriceLoadLineOrg.
		@param UY_PriceLoadLineOrg_ID UY_PriceLoadLineOrg	  */
	public void setUY_PriceLoadLineOrg_ID (int UY_PriceLoadLineOrg_ID)
	{
		if (UY_PriceLoadLineOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PriceLoadLineOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PriceLoadLineOrg_ID, Integer.valueOf(UY_PriceLoadLineOrg_ID));
	}

	/** Get UY_PriceLoadLineOrg.
		@return UY_PriceLoadLineOrg	  */
	public int getUY_PriceLoadLineOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PriceLoadLineOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}