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

/** Generated Model for UY_PickingLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_PickingLine extends PO implements I_UY_PickingLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_PickingLine (Properties ctx, int UY_PickingLine_ID, String trxName)
    {
      super (ctx, UY_PickingLine_ID, trxName);
      /** if (UY_PickingLine_ID == 0)
        {
			setC_UOM_ID (0);
			setM_Product_ID (0);
			setQtyEntered (Env.ZERO);
			setUY_PickingLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PickingLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PickingLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
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
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_PickingHdr getUY_PickingHdr() throws RuntimeException
    {
		return (I_UY_PickingHdr)MTable.get(getCtx(), I_UY_PickingHdr.Table_Name)
			.getPO(getUY_PickingHdr_ID(), get_TrxName());	}

	/** Set UY_PickingHdr_ID.
		@param UY_PickingHdr_ID UY_PickingHdr_ID	  */
	public void setUY_PickingHdr_ID (int UY_PickingHdr_ID)
	{
		if (UY_PickingHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_PickingHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PickingHdr_ID, Integer.valueOf(UY_PickingHdr_ID));
	}

	/** Get UY_PickingHdr_ID.
		@return UY_PickingHdr_ID	  */
	public int getUY_PickingHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PickingHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PickingLine.
		@param UY_PickingLine_ID UY_PickingLine	  */
	public void setUY_PickingLine_ID (int UY_PickingLine_ID)
	{
		if (UY_PickingLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PickingLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PickingLine_ID, Integer.valueOf(UY_PickingLine_ID));
	}

	/** Get UY_PickingLine.
		@return UY_PickingLine	  */
	public int getUY_PickingLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PickingLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}