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

/** Generated Model for UY_LabelPrintScan
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_LabelPrintScan extends PO implements I_UY_LabelPrintScan, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150714L;

    /** Standard Constructor */
    public X_UY_LabelPrintScan (Properties ctx, int UY_LabelPrintScan_ID, String trxName)
    {
      super (ctx, UY_LabelPrintScan_ID, trxName);
      /** if (UY_LabelPrintScan_ID == 0)
        {
			setUY_LabelPrint_ID (0);
			setUY_LabelPrintScan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_LabelPrintScan (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_LabelPrintScan[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Text.
		@param ScanText Text	  */
	public void setScanText (String ScanText)
	{
		set_Value (COLUMNNAME_ScanText, ScanText);
	}

	/** Get Text.
		@return Text	  */
	public String getScanText () 
	{
		return (String)get_Value(COLUMNNAME_ScanText);
	}

	/** Set Text.
		@param Text Text	  */
	public void setText (String Text)
	{
		set_Value (COLUMNNAME_Text, Text);
	}

	/** Get Text.
		@return Text	  */
	public String getText () 
	{
		return (String)get_Value(COLUMNNAME_Text);
	}

	public I_UY_LabelPrint getUY_LabelPrint() throws RuntimeException
    {
		return (I_UY_LabelPrint)MTable.get(getCtx(), I_UY_LabelPrint.Table_Name)
			.getPO(getUY_LabelPrint_ID(), get_TrxName());	}

	/** Set UY_LabelPrint.
		@param UY_LabelPrint_ID UY_LabelPrint	  */
	public void setUY_LabelPrint_ID (int UY_LabelPrint_ID)
	{
		if (UY_LabelPrint_ID < 1) 
			set_Value (COLUMNNAME_UY_LabelPrint_ID, null);
		else 
			set_Value (COLUMNNAME_UY_LabelPrint_ID, Integer.valueOf(UY_LabelPrint_ID));
	}

	/** Get UY_LabelPrint.
		@return UY_LabelPrint	  */
	public int getUY_LabelPrint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LabelPrint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_LabelPrintScan.
		@param UY_LabelPrintScan_ID UY_LabelPrintScan	  */
	public void setUY_LabelPrintScan_ID (int UY_LabelPrintScan_ID)
	{
		if (UY_LabelPrintScan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_LabelPrintScan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_LabelPrintScan_ID, Integer.valueOf(UY_LabelPrintScan_ID));
	}

	/** Get UY_LabelPrintScan.
		@return UY_LabelPrintScan	  */
	public int getUY_LabelPrintScan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LabelPrintScan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}