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

/** Generated Model for UY_CashCloseLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_CashCloseLine extends PO implements I_UY_CashCloseLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130603L;

    /** Standard Constructor */
    public X_UY_CashCloseLine (Properties ctx, int UY_CashCloseLine_ID, String trxName)
    {
      super (ctx, UY_CashCloseLine_ID, trxName);
      /** if (UY_CashCloseLine_ID == 0)
        {
			setAmtSourceCr (Env.ZERO);
			setAmtSourceDr (Env.ZERO);
			setC_DocType_ID (0);
			setDocumentNo (null);
			setUY_CashClose_ID (0);
			setUY_CashCloseLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashCloseLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashCloseLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Source Credit.
		@param AmtSourceCr 
		Source Credit Amount
	  */
	public void setAmtSourceCr (BigDecimal AmtSourceCr)
	{
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	/** Get Source Credit.
		@return Source Credit Amount
	  */
	public BigDecimal getAmtSourceCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Source Debit.
		@param AmtSourceDr 
		Source Debit Amount
	  */
	public void setAmtSourceDr (BigDecimal AmtSourceDr)
	{
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	/** Get Source Debit.
		@return Source Debit Amount
	  */
	public BigDecimal getAmtSourceDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceDr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Check No.
		@param CheckNo 
		Check Number
	  */
	public void setCheckNo (String CheckNo)
	{
		set_Value (COLUMNNAME_CheckNo, CheckNo);
	}

	/** Get Check No.
		@return Check Number
	  */
	public String getCheckNo () 
	{
		return (String)get_Value(COLUMNNAME_CheckNo);
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

	public I_UY_CashClose getUY_CashClose() throws RuntimeException
    {
		return (I_UY_CashClose)MTable.get(getCtx(), I_UY_CashClose.Table_Name)
			.getPO(getUY_CashClose_ID(), get_TrxName());	}

	/** Set UY_CashClose.
		@param UY_CashClose_ID UY_CashClose	  */
	public void setUY_CashClose_ID (int UY_CashClose_ID)
	{
		if (UY_CashClose_ID < 1) 
			set_Value (COLUMNNAME_UY_CashClose_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashClose_ID, Integer.valueOf(UY_CashClose_ID));
	}

	/** Get UY_CashClose.
		@return UY_CashClose	  */
	public int getUY_CashClose_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashClose_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CashCloseLine.
		@param UY_CashCloseLine_ID UY_CashCloseLine	  */
	public void setUY_CashCloseLine_ID (int UY_CashCloseLine_ID)
	{
		if (UY_CashCloseLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashCloseLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashCloseLine_ID, Integer.valueOf(UY_CashCloseLine_ID));
	}

	/** Get UY_CashCloseLine.
		@return UY_CashCloseLine	  */
	public int getUY_CashCloseLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCloseLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}