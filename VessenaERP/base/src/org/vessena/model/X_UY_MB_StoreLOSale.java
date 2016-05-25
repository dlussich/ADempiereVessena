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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_MB_StoreLOSale
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MB_StoreLOSale extends PO implements I_UY_MB_StoreLOSale, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160223L;

    /** Standard Constructor */
    public X_UY_MB_StoreLOSale (Properties ctx, int UY_MB_StoreLOSale_ID, String trxName)
    {
      super (ctx, UY_MB_StoreLOSale_ID, trxName);
      /** if (UY_MB_StoreLOSale_ID == 0)
        {
			setTotalAmt (Env.ZERO);
			setUY_MB_StoreLoadOrder_ID (0);
			setUY_MB_StoreLoadOrderWay_ID (0);
			setUY_MB_StoreLOSale_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_MB_StoreLOSale (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MB_StoreLOSale[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set dev_SaleID.
		@param dev_SaleID dev_SaleID	  */
	public void setdev_SaleID (int dev_SaleID)
	{
		set_Value (COLUMNNAME_dev_SaleID, Integer.valueOf(dev_SaleID));
	}

	/** Get dev_SaleID.
		@return dev_SaleID	  */
	public int getdev_SaleID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_dev_SaleID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set saledate.
		@param saledate saledate	  */
	public void setsaledate (Timestamp saledate)
	{
		set_Value (COLUMNNAME_saledate, saledate);
	}

	/** Get saledate.
		@return saledate	  */
	public Timestamp getsaledate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_saledate);
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_MB_StoreLoadOrder getUY_MB_StoreLoadOrder() throws RuntimeException
    {
		return (I_UY_MB_StoreLoadOrder)MTable.get(getCtx(), I_UY_MB_StoreLoadOrder.Table_Name)
			.getPO(getUY_MB_StoreLoadOrder_ID(), get_TrxName());	}

	/** Set UY_MB_StoreLoadOrder.
		@param UY_MB_StoreLoadOrder_ID UY_MB_StoreLoadOrder	  */
	public void setUY_MB_StoreLoadOrder_ID (int UY_MB_StoreLoadOrder_ID)
	{
		if (UY_MB_StoreLoadOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_MB_StoreLoadOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MB_StoreLoadOrder_ID, Integer.valueOf(UY_MB_StoreLoadOrder_ID));
	}

	/** Get UY_MB_StoreLoadOrder.
		@return UY_MB_StoreLoadOrder	  */
	public int getUY_MB_StoreLoadOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_StoreLoadOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MB_StoreLoadOrderWay getUY_MB_StoreLoadOrderWay() throws RuntimeException
    {
		return (I_UY_MB_StoreLoadOrderWay)MTable.get(getCtx(), I_UY_MB_StoreLoadOrderWay.Table_Name)
			.getPO(getUY_MB_StoreLoadOrderWay_ID(), get_TrxName());	}

	/** Set UY_MB_StoreLoadOrderWay.
		@param UY_MB_StoreLoadOrderWay_ID UY_MB_StoreLoadOrderWay	  */
	public void setUY_MB_StoreLoadOrderWay_ID (int UY_MB_StoreLoadOrderWay_ID)
	{
		if (UY_MB_StoreLoadOrderWay_ID < 1) 
			set_Value (COLUMNNAME_UY_MB_StoreLoadOrderWay_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MB_StoreLoadOrderWay_ID, Integer.valueOf(UY_MB_StoreLoadOrderWay_ID));
	}

	/** Get UY_MB_StoreLoadOrderWay.
		@return UY_MB_StoreLoadOrderWay	  */
	public int getUY_MB_StoreLoadOrderWay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_StoreLoadOrderWay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_MB_StoreLOSale.
		@param UY_MB_StoreLOSale_ID UY_MB_StoreLOSale	  */
	public void setUY_MB_StoreLOSale_ID (int UY_MB_StoreLOSale_ID)
	{
		if (UY_MB_StoreLOSale_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MB_StoreLOSale_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MB_StoreLOSale_ID, Integer.valueOf(UY_MB_StoreLOSale_ID));
	}

	/** Get UY_MB_StoreLOSale.
		@return UY_MB_StoreLOSale	  */
	public int getUY_MB_StoreLOSale_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_StoreLOSale_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}