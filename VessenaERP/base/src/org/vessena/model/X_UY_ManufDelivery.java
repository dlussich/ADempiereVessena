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

/** Generated Model for UY_ManufDelivery
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ManufDelivery extends PO implements I_UY_ManufDelivery, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121204L;

    /** Standard Constructor */
    public X_UY_ManufDelivery (Properties ctx, int UY_ManufDelivery_ID, String trxName)
    {
      super (ctx, UY_ManufDelivery_ID, trxName);
      /** if (UY_ManufDelivery_ID == 0)
        {
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setQty (Env.ZERO);
			setUY_ManufDelivery_ID (0);
			setUY_ManufOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ManufDelivery (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ManufDelivery[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** design AD_Reference_ID=1000188 */
	public static final int DESIGN_AD_Reference_ID=1000188;
	/** DISEÃ‘O 1 = D1 */
	public static final String DESIGN_DISEÑO1 = "D1";
	/** DISEÃ‘O 2 = D2 */
	public static final String DESIGN_DISEÑO2 = "D2";
	/** DISEÃ‘O 3 = D3 */
	public static final String DESIGN_DISEÑO3 = "D3";
	/** Set design.
		@param design design	  */
	public void setdesign (String design)
	{

		set_Value (COLUMNNAME_design, design);
	}

	/** Get design.
		@return design	  */
	public String getdesign () 
	{
		return (String)get_Value(COLUMNNAME_design);
	}

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_ManufDelivery.
		@param UY_ManufDelivery_ID UY_ManufDelivery	  */
	public void setUY_ManufDelivery_ID (int UY_ManufDelivery_ID)
	{
		if (UY_ManufDelivery_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ManufDelivery_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ManufDelivery_ID, Integer.valueOf(UY_ManufDelivery_ID));
	}

	/** Get UY_ManufDelivery.
		@return UY_ManufDelivery	  */
	public int getUY_ManufDelivery_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufDelivery_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ManufOrder getUY_ManufOrder() throws RuntimeException
    {
		return (I_UY_ManufOrder)MTable.get(getCtx(), I_UY_ManufOrder.Table_Name)
			.getPO(getUY_ManufOrder_ID(), get_TrxName());	}

	/** Set UY_ManufOrder.
		@param UY_ManufOrder_ID UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID)
	{
		if (UY_ManufOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, Integer.valueOf(UY_ManufOrder_ID));
	}

	/** Get UY_ManufOrder.
		@return UY_ManufOrder	  */
	public int getUY_ManufOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}