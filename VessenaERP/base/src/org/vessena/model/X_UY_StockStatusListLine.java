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

/** Generated Model for UY_StockStatusListLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_StockStatusListLine extends PO implements I_UY_StockStatusListLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_StockStatusListLine (Properties ctx, int UY_StockStatusListLine_ID, String trxName)
    {
      super (ctx, UY_StockStatusListLine_ID, trxName);
      /** if (UY_StockStatusListLine_ID == 0)
        {
			setUY_StockStatus_ID (0);
			setUY_StockStatusListHdr_ID (0);
			setUY_StockStatusListLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_StockStatusListLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StockStatusListLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_UY_StockStatus getUY_StockStatus() throws RuntimeException
    {
		return (I_UY_StockStatus)MTable.get(getCtx(), I_UY_StockStatus.Table_Name)
			.getPO(getUY_StockStatus_ID(), get_TrxName());	}

	/** Set UY_StockStatus.
		@param UY_StockStatus_ID UY_StockStatus	  */
	public void setUY_StockStatus_ID (int UY_StockStatus_ID)
	{
		if (UY_StockStatus_ID < 1) 
			set_Value (COLUMNNAME_UY_StockStatus_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StockStatus_ID, Integer.valueOf(UY_StockStatus_ID));
	}

	/** Get UY_StockStatus.
		@return UY_StockStatus	  */
	public int getUY_StockStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_StockStatusListHdr getUY_StockStatusListHdr() throws RuntimeException
    {
		return (I_UY_StockStatusListHdr)MTable.get(getCtx(), I_UY_StockStatusListHdr.Table_Name)
			.getPO(getUY_StockStatusListHdr_ID(), get_TrxName());	}

	/** Set UY_StockStatusListHdr.
		@param UY_StockStatusListHdr_ID UY_StockStatusListHdr	  */
	public void setUY_StockStatusListHdr_ID (int UY_StockStatusListHdr_ID)
	{
		if (UY_StockStatusListHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_StockStatusListHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StockStatusListHdr_ID, Integer.valueOf(UY_StockStatusListHdr_ID));
	}

	/** Get UY_StockStatusListHdr.
		@return UY_StockStatusListHdr	  */
	public int getUY_StockStatusListHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockStatusListHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_StockStatusListLine.
		@param UY_StockStatusListLine_ID UY_StockStatusListLine	  */
	public void setUY_StockStatusListLine_ID (int UY_StockStatusListLine_ID)
	{
		if (UY_StockStatusListLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StockStatusListLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StockStatusListLine_ID, Integer.valueOf(UY_StockStatusListLine_ID));
	}

	/** Get UY_StockStatusListLine.
		@return UY_StockStatusListLine	  */
	public int getUY_StockStatusListLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockStatusListLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}