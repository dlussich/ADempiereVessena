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

/** Generated Model for UY_TR_InvoiceTire
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_InvoiceTire extends PO implements I_UY_TR_InvoiceTire, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140707L;

    /** Standard Constructor */
    public X_UY_TR_InvoiceTire (Properties ctx, int UY_TR_InvoiceTire_ID, String trxName)
    {
      super (ctx, UY_TR_InvoiceTire_ID, trxName);
      /** if (UY_TR_InvoiceTire_ID == 0)
        {
			setC_InvoiceLine_ID (0);
			setM_Product_ID (0);
			setUY_TR_InvoiceTire_ID (0);
			setUY_TR_TireMark_ID (0);
			setUY_TR_TireMeasure_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_InvoiceTire (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_InvoiceTire[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set QtyKmNew.
		@param QtyKmNew QtyKmNew	  */
	public void setQtyKmNew (int QtyKmNew)
	{
		set_Value (COLUMNNAME_QtyKmNew, Integer.valueOf(QtyKmNew));
	}

	/** Get QtyKmNew.
		@return QtyKmNew	  */
	public int getQtyKmNew () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKmNew);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKmRecauchu.
		@param QtyKmRecauchu QtyKmRecauchu	  */
	public void setQtyKmRecauchu (int QtyKmRecauchu)
	{
		set_Value (COLUMNNAME_QtyKmRecauchu, Integer.valueOf(QtyKmRecauchu));
	}

	/** Get QtyKmRecauchu.
		@return QtyKmRecauchu	  */
	public int getQtyKmRecauchu () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKmRecauchu);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TireModel.
		@param TireModel 
		TireModel
	  */
	public void setTireModel (String TireModel)
	{
		set_Value (COLUMNNAME_TireModel, TireModel);
	}

	/** Get TireModel.
		@return TireModel
	  */
	public String getTireModel () 
	{
		return (String)get_Value(COLUMNNAME_TireModel);
	}

	/** Set UY_TR_InvoiceTire.
		@param UY_TR_InvoiceTire_ID UY_TR_InvoiceTire	  */
	public void setUY_TR_InvoiceTire_ID (int UY_TR_InvoiceTire_ID)
	{
		if (UY_TR_InvoiceTire_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_InvoiceTire_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_InvoiceTire_ID, Integer.valueOf(UY_TR_InvoiceTire_ID));
	}

	/** Get UY_TR_InvoiceTire.
		@return UY_TR_InvoiceTire	  */
	public int getUY_TR_InvoiceTire_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_InvoiceTire_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMark getUY_TR_TireMark() throws RuntimeException
    {
		return (I_UY_TR_TireMark)MTable.get(getCtx(), I_UY_TR_TireMark.Table_Name)
			.getPO(getUY_TR_TireMark_ID(), get_TrxName());	}

	/** Set UY_TR_TireMark.
		@param UY_TR_TireMark_ID UY_TR_TireMark	  */
	public void setUY_TR_TireMark_ID (int UY_TR_TireMark_ID)
	{
		if (UY_TR_TireMark_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMark_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMark_ID, Integer.valueOf(UY_TR_TireMark_ID));
	}

	/** Get UY_TR_TireMark.
		@return UY_TR_TireMark	  */
	public int getUY_TR_TireMark_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMark_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMeasure getUY_TR_TireMeasure() throws RuntimeException
    {
		return (I_UY_TR_TireMeasure)MTable.get(getCtx(), I_UY_TR_TireMeasure.Table_Name)
			.getPO(getUY_TR_TireMeasure_ID(), get_TrxName());	}

	/** Set UY_TR_TireMeasure.
		@param UY_TR_TireMeasure_ID UY_TR_TireMeasure	  */
	public void setUY_TR_TireMeasure_ID (int UY_TR_TireMeasure_ID)
	{
		if (UY_TR_TireMeasure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMeasure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMeasure_ID, Integer.valueOf(UY_TR_TireMeasure_ID));
	}

	/** Get UY_TR_TireMeasure.
		@return UY_TR_TireMeasure	  */
	public int getUY_TR_TireMeasure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMeasure_ID);
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