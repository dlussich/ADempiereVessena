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

/** Generated Model for UY_StockConsumeLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_StockConsumeLine extends PO implements I_UY_StockConsumeLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140722L;

    /** Standard Constructor */
    public X_UY_StockConsumeLine (Properties ctx, int UY_StockConsumeLine_ID, String trxName)
    {
      super (ctx, UY_StockConsumeLine_ID, trxName);
      /** if (UY_StockConsumeLine_ID == 0)
        {
			setAD_Table_ID (0);
			setC_DocType_ID (0);
			setC_Period_ID (0);
			setDocumentNo (null);
			setMovementDate (new Timestamp( System.currentTimeMillis() ));
			setMovementQty (Env.ZERO);
			setRecord_ID (0);
			setUY_StockConsume_ID (0);
			setUY_StockConsumeLine_ID (0);
			setUY_StockTransaction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_StockConsumeLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StockConsumeLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Line ID.
		@param Line_ID 
		Transaction line ID (internal)
	  */
	public void setLine_ID (int Line_ID)
	{
		if (Line_ID < 1) 
			set_Value (COLUMNNAME_Line_ID, null);
		else 
			set_Value (COLUMNNAME_Line_ID, Integer.valueOf(Line_ID));
	}

	/** Get Line ID.
		@return Transaction line ID (internal)
	  */
	public int getLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Movement Date.
		@param MovementDate 
		Date a product was moved in or out of inventory
	  */
	public void setMovementDate (Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Movement Date.
		@return Date a product was moved in or out of inventory
	  */
	public Timestamp getMovementDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MovementDate);
	}

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Movement Quantity.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_StockConsume getUY_StockConsume() throws RuntimeException
    {
		return (I_UY_StockConsume)MTable.get(getCtx(), I_UY_StockConsume.Table_Name)
			.getPO(getUY_StockConsume_ID(), get_TrxName());	}

	/** Set UY_StockConsume.
		@param UY_StockConsume_ID UY_StockConsume	  */
	public void setUY_StockConsume_ID (int UY_StockConsume_ID)
	{
		if (UY_StockConsume_ID < 1) 
			set_Value (COLUMNNAME_UY_StockConsume_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StockConsume_ID, Integer.valueOf(UY_StockConsume_ID));
	}

	/** Get UY_StockConsume.
		@return UY_StockConsume	  */
	public int getUY_StockConsume_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockConsume_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_StockConsumeLine.
		@param UY_StockConsumeLine_ID UY_StockConsumeLine	  */
	public void setUY_StockConsumeLine_ID (int UY_StockConsumeLine_ID)
	{
		if (UY_StockConsumeLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StockConsumeLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StockConsumeLine_ID, Integer.valueOf(UY_StockConsumeLine_ID));
	}

	/** Get UY_StockConsumeLine.
		@return UY_StockConsumeLine	  */
	public int getUY_StockConsumeLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockConsumeLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_StockTransaction getUY_StockTransaction() throws RuntimeException
    {
		return (I_UY_StockTransaction)MTable.get(getCtx(), I_UY_StockTransaction.Table_Name)
			.getPO(getUY_StockTransaction_ID(), get_TrxName());	}

	/** Set UY_StockTransaction.
		@param UY_StockTransaction_ID UY_StockTransaction	  */
	public void setUY_StockTransaction_ID (int UY_StockTransaction_ID)
	{
		if (UY_StockTransaction_ID < 1) 
			set_Value (COLUMNNAME_UY_StockTransaction_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StockTransaction_ID, Integer.valueOf(UY_StockTransaction_ID));
	}

	/** Get UY_StockTransaction.
		@return UY_StockTransaction	  */
	public int getUY_StockTransaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockTransaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}