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

/** Generated Model for C_InvoicePrintConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_InvoicePrintConfig extends PO implements I_C_InvoicePrintConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150107L;

    /** Standard Constructor */
    public X_C_InvoicePrintConfig (Properties ctx, int C_InvoicePrintConfig_ID, String trxName)
    {
      super (ctx, C_InvoicePrintConfig_ID, trxName);
      /** if (C_InvoicePrintConfig_ID == 0)
        {
			setC_Invoice_ID (0);
			setC_InvoicePrintConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_InvoicePrintConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_InvoicePrintConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amt1.
		@param amt1 amt1	  */
	public void setamt1 (BigDecimal amt1)
	{
		set_Value (COLUMNNAME_amt1, amt1);
	}

	/** Get amt1.
		@return amt1	  */
	public BigDecimal getamt1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt2.
		@param amt2 amt2	  */
	public void setamt2 (BigDecimal amt2)
	{
		set_Value (COLUMNNAME_amt2, amt2);
	}

	/** Get amt2.
		@return amt2	  */
	public BigDecimal getamt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_InvoicePrintConfig.
		@param C_InvoicePrintConfig_ID C_InvoicePrintConfig	  */
	public void setC_InvoicePrintConfig_ID (int C_InvoicePrintConfig_ID)
	{
		if (C_InvoicePrintConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoicePrintConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoicePrintConfig_ID, Integer.valueOf(C_InvoicePrintConfig_ID));
	}

	/** Get C_InvoicePrintConfig.
		@return C_InvoicePrintConfig	  */
	public int getC_InvoicePrintConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoicePrintConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Legend1.
		@param Legend1 Legend1	  */
	public void setLegend1 (String Legend1)
	{
		set_Value (COLUMNNAME_Legend1, Legend1);
	}

	/** Get Legend1.
		@return Legend1	  */
	public String getLegend1 () 
	{
		return (String)get_Value(COLUMNNAME_Legend1);
	}

	/** Set Legend2.
		@param Legend2 Legend2	  */
	public void setLegend2 (String Legend2)
	{
		set_Value (COLUMNNAME_Legend2, Legend2);
	}

	/** Get Legend2.
		@return Legend2	  */
	public String getLegend2 () 
	{
		return (String)get_Value(COLUMNNAME_Legend2);
	}
}