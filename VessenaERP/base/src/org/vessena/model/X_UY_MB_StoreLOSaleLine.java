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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_MB_StoreLOSaleLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MB_StoreLOSaleLine extends PO implements I_UY_MB_StoreLOSaleLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160223L;

    /** Standard Constructor */
    public X_UY_MB_StoreLOSaleLine (Properties ctx, int UY_MB_StoreLOSaleLine_ID, String trxName)
    {
      super (ctx, UY_MB_StoreLOSaleLine_ID, trxName);
      /** if (UY_MB_StoreLOSaleLine_ID == 0)
        {
			setLineTotalAmt (Env.ZERO);
			setM_Product_ID (0);
			setproductpu (Env.ZERO);
			setUY_MB_StoreLOSale_ID (0);
			setUY_MB_StoreLOSaleLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_MB_StoreLOSaleLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MB_StoreLOSaleLine[")
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

	/** Set Line Total.
		@param LineTotalAmt 
		Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt)
	{
		set_Value (COLUMNNAME_LineTotalAmt, LineTotalAmt);
	}

	/** Get Line Total.
		@return Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineTotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set productpu.
		@param productpu productpu	  */
	public void setproductpu (BigDecimal productpu)
	{
		set_Value (COLUMNNAME_productpu, productpu);
	}

	/** Get productpu.
		@return productpu	  */
	public BigDecimal getproductpu () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_productpu);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set productqty.
		@param productqty productqty	  */
	public void setproductqty (BigDecimal productqty)
	{
		set_Value (COLUMNNAME_productqty, productqty);
	}

	/** Get productqty.
		@return productqty	  */
	public BigDecimal getproductqty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_productqty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_MB_StoreLOSale getUY_MB_StoreLOSale() throws RuntimeException
    {
		return (I_UY_MB_StoreLOSale)MTable.get(getCtx(), I_UY_MB_StoreLOSale.Table_Name)
			.getPO(getUY_MB_StoreLOSale_ID(), get_TrxName());	}

	/** Set UY_MB_StoreLOSale.
		@param UY_MB_StoreLOSale_ID UY_MB_StoreLOSale	  */
	public void setUY_MB_StoreLOSale_ID (int UY_MB_StoreLOSale_ID)
	{
		if (UY_MB_StoreLOSale_ID < 1) 
			set_Value (COLUMNNAME_UY_MB_StoreLOSale_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MB_StoreLOSale_ID, Integer.valueOf(UY_MB_StoreLOSale_ID));
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

	/** Set UY_MB_StoreLOSaleLine.
		@param UY_MB_StoreLOSaleLine_ID UY_MB_StoreLOSaleLine	  */
	public void setUY_MB_StoreLOSaleLine_ID (int UY_MB_StoreLOSaleLine_ID)
	{
		if (UY_MB_StoreLOSaleLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MB_StoreLOSaleLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MB_StoreLOSaleLine_ID, Integer.valueOf(UY_MB_StoreLOSaleLine_ID));
	}

	/** Get UY_MB_StoreLOSaleLine.
		@return UY_MB_StoreLOSaleLine	  */
	public int getUY_MB_StoreLOSaleLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_StoreLOSaleLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}