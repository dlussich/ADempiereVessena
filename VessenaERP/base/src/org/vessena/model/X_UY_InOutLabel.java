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

/** Generated Model for UY_InOutLabel
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_InOutLabel extends PO implements I_UY_InOutLabel, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160404L;

    /** Standard Constructor */
    public X_UY_InOutLabel (Properties ctx, int UY_InOutLabel_ID, String trxName)
    {
      super (ctx, UY_InOutLabel_ID, trxName);
      /** if (UY_InOutLabel_ID == 0)
        {
			setnumero (null);
			setQty (Env.ZERO);
// 1
			setUY_InOutLabel_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InOutLabel (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InOutLabel[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set numero.
		@param numero numero	  */
	public void setnumero (String numero)
	{
		set_Value (COLUMNNAME_numero, numero);
	}

	/** Get numero.
		@return numero	  */
	public String getnumero () 
	{
		return (String)get_Value(COLUMNNAME_numero);
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

	public I_UY_Confirmorderhdr getUY_Confirmorderhdr() throws RuntimeException
    {
		return (I_UY_Confirmorderhdr)MTable.get(getCtx(), I_UY_Confirmorderhdr.Table_Name)
			.getPO(getUY_Confirmorderhdr_ID(), get_TrxName());	}

	/** Set UY_Confirmorderhdr.
		@param UY_Confirmorderhdr_ID UY_Confirmorderhdr	  */
	public void setUY_Confirmorderhdr_ID (int UY_Confirmorderhdr_ID)
	{
		if (UY_Confirmorderhdr_ID < 1) 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, Integer.valueOf(UY_Confirmorderhdr_ID));
	}

	/** Get UY_Confirmorderhdr.
		@return UY_Confirmorderhdr	  */
	public int getUY_Confirmorderhdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Confirmorderhdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_InOutLabel.
		@param UY_InOutLabel_ID UY_InOutLabel	  */
	public void setUY_InOutLabel_ID (int UY_InOutLabel_ID)
	{
		if (UY_InOutLabel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InOutLabel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InOutLabel_ID, Integer.valueOf(UY_InOutLabel_ID));
	}

	/** Get UY_InOutLabel.
		@return UY_InOutLabel	  */
	public int getUY_InOutLabel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InOutLabel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}