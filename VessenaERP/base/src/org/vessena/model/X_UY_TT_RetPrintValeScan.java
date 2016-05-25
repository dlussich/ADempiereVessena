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

/** Generated Model for UY_TT_RetPrintValeScan
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_RetPrintValeScan extends PO implements I_UY_TT_RetPrintValeScan, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150106L;

    /** Standard Constructor */
    public X_UY_TT_RetPrintValeScan (Properties ctx, int UY_TT_RetPrintValeScan_ID, String trxName)
    {
      super (ctx, UY_TT_RetPrintValeScan_ID, trxName);
      /** if (UY_TT_RetPrintValeScan_ID == 0)
        {
			setScanText (null);
			setUY_TT_RetPrintVale_ID (0);
			setUY_TT_RetPrintValeScan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_RetPrintValeScan (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_RetPrintValeScan[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, Integer.valueOf(LocatorValue));
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public int getLocatorValue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LocatorValue);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException
    {
		return (I_UY_TT_Box)MTable.get(getCtx(), I_UY_TT_Box.Table_Name)
			.getPO(getUY_TT_Box_ID(), get_TrxName());	}

	/** Set UY_TT_Box.
		@param UY_TT_Box_ID UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID)
	{
		if (UY_TT_Box_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Box_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Box_ID, Integer.valueOf(UY_TT_Box_ID));
	}

	/** Get UY_TT_Box.
		@return UY_TT_Box	  */
	public int getUY_TT_Box_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID);
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

	public I_UY_TT_RetPrintVale getUY_TT_RetPrintVale() throws RuntimeException
    {
		return (I_UY_TT_RetPrintVale)MTable.get(getCtx(), I_UY_TT_RetPrintVale.Table_Name)
			.getPO(getUY_TT_RetPrintVale_ID(), get_TrxName());	}

	/** Set UY_TT_RetPrintVale.
		@param UY_TT_RetPrintVale_ID UY_TT_RetPrintVale	  */
	public void setUY_TT_RetPrintVale_ID (int UY_TT_RetPrintVale_ID)
	{
		if (UY_TT_RetPrintVale_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_RetPrintVale_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_RetPrintVale_ID, Integer.valueOf(UY_TT_RetPrintVale_ID));
	}

	/** Get UY_TT_RetPrintVale.
		@return UY_TT_RetPrintVale	  */
	public int getUY_TT_RetPrintVale_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_RetPrintVale_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_RetPrintValeScan.
		@param UY_TT_RetPrintValeScan_ID UY_TT_RetPrintValeScan	  */
	public void setUY_TT_RetPrintValeScan_ID (int UY_TT_RetPrintValeScan_ID)
	{
		if (UY_TT_RetPrintValeScan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_RetPrintValeScan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_RetPrintValeScan_ID, Integer.valueOf(UY_TT_RetPrintValeScan_ID));
	}

	/** Get UY_TT_RetPrintValeScan.
		@return UY_TT_RetPrintValeScan	  */
	public int getUY_TT_RetPrintValeScan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_RetPrintValeScan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}