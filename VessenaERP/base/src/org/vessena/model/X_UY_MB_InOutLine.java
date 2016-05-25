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

/** Generated Model for UY_MB_InOutLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MB_InOutLine extends PO implements I_UY_MB_InOutLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150925L;

    /** Standard Constructor */
    public X_UY_MB_InOutLine (Properties ctx, int UY_MB_InOutLine_ID, String trxName)
    {
      super (ctx, UY_MB_InOutLine_ID, trxName);
      /** if (UY_MB_InOutLine_ID == 0)
        {
			setC_OrderLine_ID (0);
			setUY_MB_InOut_ID (0);
			setUY_MB_InOutLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_MB_InOutLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MB_InOutLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set fechafactura.
		@param fechafactura fechafactura	  */
	public void setfechafactura (Timestamp fechafactura)
	{
		set_Value (COLUMNNAME_fechafactura, fechafactura);
	}

	/** Get fechafactura.
		@return fechafactura	  */
	public Timestamp getfechafactura () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechafactura);
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

	/** Set nrofactura.
		@param nrofactura nrofactura	  */
	public void setnrofactura (String nrofactura)
	{
		set_Value (COLUMNNAME_nrofactura, nrofactura);
	}

	/** Get nrofactura.
		@return nrofactura	  */
	public String getnrofactura () 
	{
		return (String)get_Value(COLUMNNAME_nrofactura);
	}

	/** Set Delivered Quantity.
		@param QtyDelivered 
		Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Delivered Quantity.
		@return Delivered Quantity
	  */
	public BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_MB_InOut getUY_MB_InOut() throws RuntimeException
    {
		return (I_UY_MB_InOut)MTable.get(getCtx(), I_UY_MB_InOut.Table_Name)
			.getPO(getUY_MB_InOut_ID(), get_TrxName());	}

	/** Set UY_MB_InOut.
		@param UY_MB_InOut_ID UY_MB_InOut	  */
	public void setUY_MB_InOut_ID (int UY_MB_InOut_ID)
	{
		if (UY_MB_InOut_ID < 1) 
			set_Value (COLUMNNAME_UY_MB_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MB_InOut_ID, Integer.valueOf(UY_MB_InOut_ID));
	}

	/** Get UY_MB_InOut.
		@return UY_MB_InOut	  */
	public int getUY_MB_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_MB_InOutLine.
		@param UY_MB_InOutLine_ID UY_MB_InOutLine	  */
	public void setUY_MB_InOutLine_ID (int UY_MB_InOutLine_ID)
	{
		if (UY_MB_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MB_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MB_InOutLine_ID, Integer.valueOf(UY_MB_InOutLine_ID));
	}

	/** Get UY_MB_InOutLine.
		@return UY_MB_InOutLine	  */
	public int getUY_MB_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}