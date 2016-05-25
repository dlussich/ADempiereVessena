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

/** Generated Model for UY_ExchangeDiffLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_ExchangeDiffLine extends PO implements I_UY_ExchangeDiffLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_ExchangeDiffLine (Properties ctx, int UY_ExchangeDiffLine_ID, String trxName)
    {
      super (ctx, UY_ExchangeDiffLine_ID, trxName);
      /** if (UY_ExchangeDiffLine_ID == 0)
        {
			setAmtAcctCr (Env.ZERO);
			setAmtAcctDr (Env.ZERO);
			setamtdiffcr (Env.ZERO);
			setamtdiffdr (Env.ZERO);
			setAmtSourceCr (Env.ZERO);
			setAmtSourceDr (Env.ZERO);
			setC_Currency_ID (0);
			setC_ElementValue_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setDivideRate (Env.ZERO);
			setUY_ExchangeDiffHdr_ID (0);
			setUY_ExchangeDiffLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ExchangeDiffLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ExchangeDiffLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accounted Credit.
		@param AmtAcctCr 
		Accounted Credit Amount
	  */
	public void setAmtAcctCr (BigDecimal AmtAcctCr)
	{
		set_Value (COLUMNNAME_AmtAcctCr, AmtAcctCr);
	}

	/** Get Accounted Credit.
		@return Accounted Credit Amount
	  */
	public BigDecimal getAmtAcctCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Accounted Debit.
		@param AmtAcctDr 
		Accounted Debit Amount
	  */
	public void setAmtAcctDr (BigDecimal AmtAcctDr)
	{
		set_Value (COLUMNNAME_AmtAcctDr, AmtAcctDr);
	}

	/** Get Accounted Debit.
		@return Accounted Debit Amount
	  */
	public BigDecimal getAmtAcctDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctDr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtdiffcr.
		@param amtdiffcr amtdiffcr	  */
	public void setamtdiffcr (BigDecimal amtdiffcr)
	{
		set_Value (COLUMNNAME_amtdiffcr, amtdiffcr);
	}

	/** Get amtdiffcr.
		@return amtdiffcr	  */
	public BigDecimal getamtdiffcr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtdiffcr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtdiffdr.
		@param amtdiffdr amtdiffdr	  */
	public void setamtdiffdr (BigDecimal amtdiffdr)
	{
		set_Value (COLUMNNAME_amtdiffdr, amtdiffdr);
	}

	/** Get amtdiffdr.
		@return amtdiffdr	  */
	public BigDecimal getamtdiffdr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtdiffdr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (I_C_ElementValue)MTable.get(getCtx(), I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Divide Rate.
		@param DivideRate 
		To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divide Rate.
		@return To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_ExchangeDiffHdr getUY_ExchangeDiffHdr() throws RuntimeException
    {
		return (I_UY_ExchangeDiffHdr)MTable.get(getCtx(), I_UY_ExchangeDiffHdr.Table_Name)
			.getPO(getUY_ExchangeDiffHdr_ID(), get_TrxName());	}

	/** Set UY_ExchangeDiffHdr.
		@param UY_ExchangeDiffHdr_ID UY_ExchangeDiffHdr	  */
	public void setUY_ExchangeDiffHdr_ID (int UY_ExchangeDiffHdr_ID)
	{
		if (UY_ExchangeDiffHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_ExchangeDiffHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ExchangeDiffHdr_ID, Integer.valueOf(UY_ExchangeDiffHdr_ID));
	}

	/** Get UY_ExchangeDiffHdr.
		@return UY_ExchangeDiffHdr	  */
	public int getUY_ExchangeDiffHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ExchangeDiffHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ExchangeDiffLine.
		@param UY_ExchangeDiffLine_ID UY_ExchangeDiffLine	  */
	public void setUY_ExchangeDiffLine_ID (int UY_ExchangeDiffLine_ID)
	{
		if (UY_ExchangeDiffLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ExchangeDiffLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ExchangeDiffLine_ID, Integer.valueOf(UY_ExchangeDiffLine_ID));
	}

	/** Get UY_ExchangeDiffLine.
		@return UY_ExchangeDiffLine	  */
	public int getUY_ExchangeDiffLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ExchangeDiffLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}