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

/** Generated Model for UY_R_HandlerAjuste
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_HandlerAjuste extends PO implements I_UY_R_HandlerAjuste, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130717L;

    /** Standard Constructor */
    public X_UY_R_HandlerAjuste (Properties ctx, int UY_R_HandlerAjuste_ID, String trxName)
    {
      super (ctx, UY_R_HandlerAjuste_ID, trxName);
      /** if (UY_R_HandlerAjuste_ID == 0)
        {
			setUY_R_HandlerAjuste_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_HandlerAjuste (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_HandlerAjuste[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AplicaIVA.
		@param AplicaIVA 
		AplicaI IVA
	  */
	public void setAplicaIVA (boolean AplicaIVA)
	{
		set_Value (COLUMNNAME_AplicaIVA, Boolean.valueOf(AplicaIVA));
	}

	/** Get AplicaIVA.
		@return AplicaI IVA
	  */
	public boolean isAplicaIVA () 
	{
		Object oo = get_Value(COLUMNNAME_AplicaIVA);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set C_Activity_ID_1.
		@param C_Activity_ID_1 C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1)
	{
		set_Value (COLUMNNAME_C_Activity_ID_1, Integer.valueOf(C_Activity_ID_1));
	}

	/** Get C_Activity_ID_1.
		@return C_Activity_ID_1	  */
	public int getC_Activity_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
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

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DateAction.
		@param DateAction DateAction	  */
	public void setDateAction (Timestamp DateAction)
	{
		set_Value (COLUMNNAME_DateAction, DateAction);
	}

	/** Get DateAction.
		@return DateAction	  */
	public Timestamp getDateAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAction);
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

	/** DrCr AD_Reference_ID=1000315 */
	public static final int DRCR_AD_Reference_ID=1000315;
	/** DB = DB */
	public static final String DRCR_DB = "DB";
	/** CR = CR */
	public static final String DRCR_CR = "CR";
	/** Set DrCr.
		@param DrCr DrCr	  */
	public void setDrCr (String DrCr)
	{

		throw new IllegalArgumentException ("DrCr is virtual column");	}

	/** Get DrCr.
		@return DrCr	  */
	public String getDrCr () 
	{
		return (String)get_Value(COLUMNNAME_DrCr);
	}

	/** Set Confirmed.
		@param IsConfirmed 
		Assignment is confirmed
	  */
	public void setIsConfirmed (boolean IsConfirmed)
	{
		set_Value (COLUMNNAME_IsConfirmed, Boolean.valueOf(IsConfirmed));
	}

	/** Get Confirmed.
		@return Assignment is confirmed
	  */
	public boolean isConfirmed () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfirmed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRejected.
		@param IsRejected IsRejected	  */
	public void setIsRejected (boolean IsRejected)
	{
		set_Value (COLUMNNAME_IsRejected, Boolean.valueOf(IsRejected));
	}

	/** Get IsRejected.
		@return IsRejected	  */
	public boolean isRejected () 
	{
		Object oo = get_Value(COLUMNNAME_IsRejected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LineNetAmtAcct.
		@param LineNetAmtAcct LineNetAmtAcct	  */
	public void setLineNetAmtAcct (BigDecimal LineNetAmtAcct)
	{
		set_Value (COLUMNNAME_LineNetAmtAcct, LineNetAmtAcct);
	}

	/** Get LineNetAmtAcct.
		@return LineNetAmtAcct	  */
	public BigDecimal getLineNetAmtAcct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmtAcct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Line Total.
		@param LineTotalAmt 
		Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt)
	{
		set_Value (COLUMNNAME_LineTotalAmt, LineTotalAmt);
	}

	/** Get Line Total.
		@return Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineTotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LineTotalAmtAcct.
		@param LineTotalAmtAcct LineTotalAmtAcct	  */
	public void setLineTotalAmtAcct (BigDecimal LineTotalAmtAcct)
	{
		set_Value (COLUMNNAME_LineTotalAmtAcct, LineTotalAmtAcct);
	}

	/** Get LineTotalAmtAcct.
		@return LineTotalAmtAcct	  */
	public BigDecimal getLineTotalAmtAcct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineTotalAmtAcct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyQuote.
		@param QtyQuote QtyQuote	  */
	public void setQtyQuote (BigDecimal QtyQuote)
	{
		set_Value (COLUMNNAME_QtyQuote, QtyQuote);
	}

	/** Get QtyQuote.
		@return QtyQuote	  */
	public BigDecimal getQtyQuote () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyQuote);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax Amount.
		@param TaxAmt 
		Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TaxAmtAcct.
		@param TaxAmtAcct TaxAmtAcct	  */
	public void setTaxAmtAcct (BigDecimal TaxAmtAcct)
	{
		set_Value (COLUMNNAME_TaxAmtAcct, TaxAmtAcct);
	}

	/** Get TaxAmtAcct.
		@return TaxAmtAcct	  */
	public BigDecimal getTaxAmtAcct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmtAcct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_R_AjusteAction getUY_R_AjusteAction() throws RuntimeException
    {
		return (I_UY_R_AjusteAction)MTable.get(getCtx(), I_UY_R_AjusteAction.Table_Name)
			.getPO(getUY_R_AjusteAction_ID(), get_TrxName());	}

	/** Set UY_R_AjusteAction.
		@param UY_R_AjusteAction_ID UY_R_AjusteAction	  */
	public void setUY_R_AjusteAction_ID (int UY_R_AjusteAction_ID)
	{
		if (UY_R_AjusteAction_ID < 1) 
			set_Value (COLUMNNAME_UY_R_AjusteAction_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_AjusteAction_ID, Integer.valueOf(UY_R_AjusteAction_ID));
	}

	/** Get UY_R_AjusteAction.
		@return UY_R_AjusteAction	  */
	public int getUY_R_AjusteAction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_AjusteAction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Ajuste getUY_R_Ajuste() throws RuntimeException
    {
		return (I_UY_R_Ajuste)MTable.get(getCtx(), I_UY_R_Ajuste.Table_Name)
			.getPO(getUY_R_Ajuste_ID(), get_TrxName());	}

	/** Set UY_R_Ajuste.
		@param UY_R_Ajuste_ID UY_R_Ajuste	  */
	public void setUY_R_Ajuste_ID (int UY_R_Ajuste_ID)
	{
		if (UY_R_Ajuste_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Ajuste_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Ajuste_ID, Integer.valueOf(UY_R_Ajuste_ID));
	}

	/** Get UY_R_Ajuste.
		@return UY_R_Ajuste	  */
	public int getUY_R_Ajuste_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Ajuste_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Gestion getUY_R_Gestion() throws RuntimeException
    {
		return (I_UY_R_Gestion)MTable.get(getCtx(), I_UY_R_Gestion.Table_Name)
			.getPO(getUY_R_Gestion_ID(), get_TrxName());	}

	/** Set UY_R_Gestion.
		@param UY_R_Gestion_ID UY_R_Gestion	  */
	public void setUY_R_Gestion_ID (int UY_R_Gestion_ID)
	{
		if (UY_R_Gestion_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Gestion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Gestion_ID, Integer.valueOf(UY_R_Gestion_ID));
	}

	/** Get UY_R_Gestion.
		@return UY_R_Gestion	  */
	public int getUY_R_Gestion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Gestion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_HandlerAjuste.
		@param UY_R_HandlerAjuste_ID UY_R_HandlerAjuste	  */
	public void setUY_R_HandlerAjuste_ID (int UY_R_HandlerAjuste_ID)
	{
		if (UY_R_HandlerAjuste_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_HandlerAjuste_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_HandlerAjuste_ID, Integer.valueOf(UY_R_HandlerAjuste_ID));
	}

	/** Get UY_R_HandlerAjuste.
		@return UY_R_HandlerAjuste	  */
	public int getUY_R_HandlerAjuste_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_HandlerAjuste_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}