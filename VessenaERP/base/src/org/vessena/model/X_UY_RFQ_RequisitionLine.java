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

/** Generated Model for UY_RFQ_RequisitionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RFQ_RequisitionLine extends PO implements I_UY_RFQ_RequisitionLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121107L;

    /** Standard Constructor */
    public X_UY_RFQ_RequisitionLine (Properties ctx, int UY_RFQ_RequisitionLine_ID, String trxName)
    {
      super (ctx, UY_RFQ_RequisitionLine_ID, trxName);
      /** if (UY_RFQ_RequisitionLine_ID == 0)
        {
			setC_UOM_ID (0);
			setDateRequired (new Timestamp( System.currentTimeMillis() ));
			setM_Product_ID (0);
			setQtyRequired (Env.ZERO);
			setUY_RFQ_Requisition_ID (0);
			setUY_RFQ_RequisitionLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RFQ_RequisitionLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RFQ_RequisitionLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
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

	/** Set Date Required.
		@param DateRequired 
		Date when required
	  */
	public void setDateRequired (Timestamp DateRequired)
	{
		set_Value (COLUMNNAME_DateRequired, DateRequired);
	}

	/** Get Date Required.
		@return Date when required
	  */
	public Timestamp getDateRequired () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRequired);
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

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
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

	/** Set Qty Required.
		@param QtyRequired Qty Required	  */
	public void setQtyRequired (BigDecimal QtyRequired)
	{
		set_Value (COLUMNNAME_QtyRequired, QtyRequired);
	}

	/** Get Qty Required.
		@return Qty Required	  */
	public BigDecimal getQtyRequired () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequired);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_RFQ_Requisition getUY_RFQ_Requisition() throws RuntimeException
    {
		return (I_UY_RFQ_Requisition)MTable.get(getCtx(), I_UY_RFQ_Requisition.Table_Name)
			.getPO(getUY_RFQ_Requisition_ID(), get_TrxName());	}

	/** Set UY_RFQ_Requisition.
		@param UY_RFQ_Requisition_ID UY_RFQ_Requisition	  */
	public void setUY_RFQ_Requisition_ID (int UY_RFQ_Requisition_ID)
	{
		if (UY_RFQ_Requisition_ID < 1) 
			set_Value (COLUMNNAME_UY_RFQ_Requisition_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RFQ_Requisition_ID, Integer.valueOf(UY_RFQ_Requisition_ID));
	}

	/** Get UY_RFQ_Requisition.
		@return UY_RFQ_Requisition	  */
	public int getUY_RFQ_Requisition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_Requisition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RFQ_RequisitionLine.
		@param UY_RFQ_RequisitionLine_ID UY_RFQ_RequisitionLine	  */
	public void setUY_RFQ_RequisitionLine_ID (int UY_RFQ_RequisitionLine_ID)
	{
		if (UY_RFQ_RequisitionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RFQ_RequisitionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RFQ_RequisitionLine_ID, Integer.valueOf(UY_RFQ_RequisitionLine_ID));
	}

	/** Get UY_RFQ_RequisitionLine.
		@return UY_RFQ_RequisitionLine	  */
	public int getUY_RFQ_RequisitionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_RequisitionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}