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

import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_RecuentoConfLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_RecuentoConfLine extends PO implements I_UY_RecuentoConfLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_RecuentoConfLine (Properties ctx, int UY_RecuentoConfLine_ID, String trxName)
    {
      super (ctx, UY_RecuentoConfLine_ID, trxName);
      /** if (UY_RecuentoConfLine_ID == 0)
        {
			setUY_RecuentoConf_ID (0);
			setUY_RecuentoConfLine_ID (0);
			setuy_recuentohdr_id1 (0);
			setuy_recuentohdr_id2 (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RecuentoConfLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RecuentoConfLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	public I_M_Locator getM_Locator() throws RuntimeException
    {
		return (I_M_Locator)MTable.get(getCtx(), I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	public I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (I_M_Warehouse)MTable.get(getCtx(), I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set qty_approved.
		@param qty_approved qty_approved	  */
	public void setqty_approved (BigDecimal qty_approved)
	{
		set_Value (COLUMNNAME_qty_approved, qty_approved);
	}

	/** Get qty_approved.
		@return qty_approved	  */
	public BigDecimal getqty_approved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_approved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty_ApprovedBook.
		@param Qty_ApprovedBook Qty_ApprovedBook	  */
	public void setQty_ApprovedBook (BigDecimal Qty_ApprovedBook)
	{
		set_ValueNoCheck (COLUMNNAME_Qty_ApprovedBook, Qty_ApprovedBook);
	}

	/** Get Qty_ApprovedBook.
		@return Qty_ApprovedBook	  */
	public BigDecimal getQty_ApprovedBook () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_ApprovedBook);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_approvedr1.
		@param qty_approvedr1 qty_approvedr1	  */
	public void setqty_approvedr1 (BigDecimal qty_approvedr1)
	{
		set_ValueNoCheck (COLUMNNAME_qty_approvedr1, qty_approvedr1);
	}

	/** Get qty_approvedr1.
		@return qty_approvedr1	  */
	public BigDecimal getqty_approvedr1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_approvedr1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_approvedr2.
		@param qty_approvedr2 qty_approvedr2	  */
	public void setqty_approvedr2 (BigDecimal qty_approvedr2)
	{
		set_ValueNoCheck (COLUMNNAME_qty_approvedr2, qty_approvedr2);
	}

	/** Get qty_approvedr2.
		@return qty_approvedr2	  */
	public BigDecimal getqty_approvedr2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_approvedr2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_approvedr3.
		@param qty_approvedr3 qty_approvedr3	  */
	public void setqty_approvedr3 (BigDecimal qty_approvedr3)
	{
		set_ValueNoCheck (COLUMNNAME_qty_approvedr3, qty_approvedr3);
	}

	/** Get qty_approvedr3.
		@return qty_approvedr3	  */
	public BigDecimal getqty_approvedr3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_approvedr3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_blocked.
		@param qty_blocked qty_blocked	  */
	public void setqty_blocked (BigDecimal qty_blocked)
	{
		set_Value (COLUMNNAME_qty_blocked, qty_blocked);
	}

	/** Get qty_blocked.
		@return qty_blocked	  */
	public BigDecimal getqty_blocked () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_blocked);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty_BlockedBook.
		@param Qty_BlockedBook Qty_BlockedBook	  */
	public void setQty_BlockedBook (BigDecimal Qty_BlockedBook)
	{
		set_ValueNoCheck (COLUMNNAME_Qty_BlockedBook, Qty_BlockedBook);
	}

	/** Get Qty_BlockedBook.
		@return Qty_BlockedBook	  */
	public BigDecimal getQty_BlockedBook () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_BlockedBook);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_blockedr1.
		@param qty_blockedr1 qty_blockedr1	  */
	public void setqty_blockedr1 (BigDecimal qty_blockedr1)
	{
		set_ValueNoCheck (COLUMNNAME_qty_blockedr1, qty_blockedr1);
	}

	/** Get qty_blockedr1.
		@return qty_blockedr1	  */
	public BigDecimal getqty_blockedr1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_blockedr1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_blockedr2.
		@param qty_blockedr2 qty_blockedr2	  */
	public void setqty_blockedr2 (BigDecimal qty_blockedr2)
	{
		set_ValueNoCheck (COLUMNNAME_qty_blockedr2, qty_blockedr2);
	}

	/** Get qty_blockedr2.
		@return qty_blockedr2	  */
	public BigDecimal getqty_blockedr2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_blockedr2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_blockedr3.
		@param qty_blockedr3 qty_blockedr3	  */
	public void setqty_blockedr3 (BigDecimal qty_blockedr3)
	{
		set_ValueNoCheck (COLUMNNAME_qty_blockedr3, qty_blockedr3);
	}

	/** Get qty_blockedr3.
		@return qty_blockedr3	  */
	public BigDecimal getqty_blockedr3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_blockedr3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_quarantine.
		@param qty_quarantine qty_quarantine	  */
	public void setqty_quarantine (BigDecimal qty_quarantine)
	{
		set_Value (COLUMNNAME_qty_quarantine, qty_quarantine);
	}

	/** Get qty_quarantine.
		@return qty_quarantine	  */
	public BigDecimal getqty_quarantine () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_quarantine);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty_QuarantineBook.
		@param Qty_QuarantineBook Qty_QuarantineBook	  */
	public void setQty_QuarantineBook (BigDecimal Qty_QuarantineBook)
	{
		set_ValueNoCheck (COLUMNNAME_Qty_QuarantineBook, Qty_QuarantineBook);
	}

	/** Get Qty_QuarantineBook.
		@return Qty_QuarantineBook	  */
	public BigDecimal getQty_QuarantineBook () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_QuarantineBook);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_quarantiner1.
		@param qty_quarantiner1 qty_quarantiner1	  */
	public void setqty_quarantiner1 (BigDecimal qty_quarantiner1)
	{
		set_ValueNoCheck (COLUMNNAME_qty_quarantiner1, qty_quarantiner1);
	}

	/** Get qty_quarantiner1.
		@return qty_quarantiner1	  */
	public BigDecimal getqty_quarantiner1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_quarantiner1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_quarantiner2.
		@param qty_quarantiner2 qty_quarantiner2	  */
	public void setqty_quarantiner2 (BigDecimal qty_quarantiner2)
	{
		set_ValueNoCheck (COLUMNNAME_qty_quarantiner2, qty_quarantiner2);
	}

	/** Get qty_quarantiner2.
		@return qty_quarantiner2	  */
	public BigDecimal getqty_quarantiner2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_quarantiner2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qty_quarantiner3.
		@param qty_quarantiner3 qty_quarantiner3	  */
	public void setqty_quarantiner3 (BigDecimal qty_quarantiner3)
	{
		set_ValueNoCheck (COLUMNNAME_qty_quarantiner3, qty_quarantiner3);
	}

	/** Get qty_quarantiner3.
		@return qty_quarantiner3	  */
	public BigDecimal getqty_quarantiner3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qty_quarantiner3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_RecuentoConf getUY_RecuentoConf() throws RuntimeException
    {
		return (I_UY_RecuentoConf)MTable.get(getCtx(), I_UY_RecuentoConf.Table_Name)
			.getPO(getUY_RecuentoConf_ID(), get_TrxName());	}

	/** Set UY_RecuentoConf.
		@param UY_RecuentoConf_ID UY_RecuentoConf	  */
	public void setUY_RecuentoConf_ID (int UY_RecuentoConf_ID)
	{
		if (UY_RecuentoConf_ID < 1) 
			set_Value (COLUMNNAME_UY_RecuentoConf_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RecuentoConf_ID, Integer.valueOf(UY_RecuentoConf_ID));
	}

	/** Get UY_RecuentoConf.
		@return UY_RecuentoConf	  */
	public int getUY_RecuentoConf_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RecuentoConf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RecuentoConfLine.
		@param UY_RecuentoConfLine_ID UY_RecuentoConfLine	  */
	public void setUY_RecuentoConfLine_ID (int UY_RecuentoConfLine_ID)
	{
		if (UY_RecuentoConfLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RecuentoConfLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RecuentoConfLine_ID, Integer.valueOf(UY_RecuentoConfLine_ID));
	}

	/** Get UY_RecuentoConfLine.
		@return UY_RecuentoConfLine	  */
	public int getUY_RecuentoConfLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RecuentoConfLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RecuentoHdr getuy_recuentohdr_1() throws RuntimeException
    {
		return (I_UY_RecuentoHdr)MTable.get(getCtx(), I_UY_RecuentoHdr.Table_Name)
			.getPO(getuy_recuentohdr_id1(), get_TrxName());	}

	/** Set uy_recuentohdr_id1.
		@param uy_recuentohdr_id1 uy_recuentohdr_id1	  */
	public void setuy_recuentohdr_id1 (int uy_recuentohdr_id1)
	{
		set_ValueNoCheck (COLUMNNAME_uy_recuentohdr_id1, Integer.valueOf(uy_recuentohdr_id1));
	}

	/** Get uy_recuentohdr_id1.
		@return uy_recuentohdr_id1	  */
	public int getuy_recuentohdr_id1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_recuentohdr_id1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RecuentoHdr getuy_recuentohdr_2() throws RuntimeException
    {
		return (I_UY_RecuentoHdr)MTable.get(getCtx(), I_UY_RecuentoHdr.Table_Name)
			.getPO(getuy_recuentohdr_id2(), get_TrxName());	}

	/** Set uy_recuentohdr_id2.
		@param uy_recuentohdr_id2 uy_recuentohdr_id2	  */
	public void setuy_recuentohdr_id2 (int uy_recuentohdr_id2)
	{
		set_ValueNoCheck (COLUMNNAME_uy_recuentohdr_id2, Integer.valueOf(uy_recuentohdr_id2));
	}

	/** Get uy_recuentohdr_id2.
		@return uy_recuentohdr_id2	  */
	public int getuy_recuentohdr_id2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_recuentohdr_id2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RecuentoHdr getuy_recuentohdr_3() throws RuntimeException
    {
		return (I_UY_RecuentoHdr)MTable.get(getCtx(), I_UY_RecuentoHdr.Table_Name)
			.getPO(getuy_recuentohdr_id3(), get_TrxName());	}

	/** Set uy_recuentohdr_id3.
		@param uy_recuentohdr_id3 uy_recuentohdr_id3	  */
	public void setuy_recuentohdr_id3 (int uy_recuentohdr_id3)
	{
		set_ValueNoCheck (COLUMNNAME_uy_recuentohdr_id3, Integer.valueOf(uy_recuentohdr_id3));
	}

	/** Get uy_recuentohdr_id3.
		@return uy_recuentohdr_id3	  */
	public int getuy_recuentohdr_id3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_recuentohdr_id3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Value To.
		@param Value2 
		Value To
	  */
	public void setValue2 (String Value2)
	{
		set_Value (COLUMNNAME_Value2, Value2);
	}

	/** Get Value To.
		@return Value To
	  */
	public String getValue2 () 
	{
		return (String)get_Value(COLUMNNAME_Value2);
	}
}