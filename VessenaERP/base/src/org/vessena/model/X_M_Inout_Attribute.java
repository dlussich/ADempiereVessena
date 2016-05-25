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

/** Generated Model for M_Inout_Attribute
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_M_Inout_Attribute extends PO implements I_M_Inout_Attribute, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150115L;

    /** Standard Constructor */
    public X_M_Inout_Attribute (Properties ctx, int M_Inout_Attribute_ID, String trxName)
    {
      super (ctx, M_Inout_Attribute_ID, trxName);
      /** if (M_Inout_Attribute_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setM_Inout_Attribute_ID (0);
			setM_InOutLine_ID (0);
			setM_ProductAttribute_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Inout_Attribute (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_Inout_Attribute[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set M_Inout_Attribute.
		@param M_Inout_Attribute_ID M_Inout_Attribute	  */
	public void setM_Inout_Attribute_ID (int M_Inout_Attribute_ID)
	{
		if (M_Inout_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Inout_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Inout_Attribute_ID, Integer.valueOf(M_Inout_Attribute_ID));
	}

	/** Get M_Inout_Attribute.
		@return M_Inout_Attribute	  */
	public int getM_Inout_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Inout_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_M_Product getM_ProductAttribute() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_ProductAttribute_ID(), get_TrxName());	}

	/** Set M_ProductAttribute.
		@param M_ProductAttribute_ID M_ProductAttribute	  */
	public void setM_ProductAttribute_ID (int M_ProductAttribute_ID)
	{
		if (M_ProductAttribute_ID < 1) 
			set_Value (COLUMNNAME_M_ProductAttribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductAttribute_ID, Integer.valueOf(M_ProductAttribute_ID));
	}

	/** Get M_ProductAttribute.
		@return M_ProductAttribute	  */
	public int getM_ProductAttribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductAttribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}