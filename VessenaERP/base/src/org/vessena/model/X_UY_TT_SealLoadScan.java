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

/** Generated Model for UY_TT_SealLoadScan
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_SealLoadScan extends PO implements I_UY_TT_SealLoadScan, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130830L;

    /** Standard Constructor */
    public X_UY_TT_SealLoadScan (Properties ctx, int UY_TT_SealLoadScan_ID, String trxName)
    {
      super (ctx, UY_TT_SealLoadScan_ID, trxName);
      /** if (UY_TT_SealLoadScan_ID == 0)
        {
			setScanText (null);
			setUY_TT_SealLoad_ID (0);
			setUY_TT_SealLoadScan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_SealLoadScan (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_SealLoadScan[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ScanText.
		@param ScanText ScanText	  */
	public void setScanText (String ScanText)
	{
		set_Value (COLUMNNAME_ScanText, ScanText);
	}

	/** Get ScanText.
		@return ScanText	  */
	public String getScanText () 
	{
		return (String)get_Value(COLUMNNAME_ScanText);
	}

	public I_UY_TT_SealLoad getUY_TT_SealLoad() throws RuntimeException
    {
		return (I_UY_TT_SealLoad)MTable.get(getCtx(), I_UY_TT_SealLoad.Table_Name)
			.getPO(getUY_TT_SealLoad_ID(), get_TrxName());	}

	/** Set UY_TT_SealLoad.
		@param UY_TT_SealLoad_ID UY_TT_SealLoad	  */
	public void setUY_TT_SealLoad_ID (int UY_TT_SealLoad_ID)
	{
		if (UY_TT_SealLoad_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_SealLoad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_SealLoad_ID, Integer.valueOf(UY_TT_SealLoad_ID));
	}

	/** Get UY_TT_SealLoad.
		@return UY_TT_SealLoad	  */
	public int getUY_TT_SealLoad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_SealLoad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_SealLoadScan.
		@param UY_TT_SealLoadScan_ID UY_TT_SealLoadScan	  */
	public void setUY_TT_SealLoadScan_ID (int UY_TT_SealLoadScan_ID)
	{
		if (UY_TT_SealLoadScan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_SealLoadScan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_SealLoadScan_ID, Integer.valueOf(UY_TT_SealLoadScan_ID));
	}

	/** Get UY_TT_SealLoadScan.
		@return UY_TT_SealLoadScan	  */
	public int getUY_TT_SealLoadScan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_SealLoadScan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}