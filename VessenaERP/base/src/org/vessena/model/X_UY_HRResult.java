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

/** Generated Model for UY_HRResult
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_HRResult extends PO implements I_UY_HRResult, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120802L;

    /** Standard Constructor */
    public X_UY_HRResult (Properties ctx, int UY_HRResult_ID, String trxName)
    {
      super (ctx, UY_HRResult_ID, trxName);
      /** if (UY_HRResult_ID == 0)
        {
			setAmtAcctCr (Env.ZERO);
			setAmtAcctDr (Env.ZERO);
			setC_BPartner_ID (0);
			setReProcessing (false);
// N
			setSuccess (false);
			setTotalAmt (Env.ZERO);
			setUY_HRProcesoNomina_ID (0);
			setUY_HRResult_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRResult (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRResult[")
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

	/** Set imprimerecibos.
		@param imprimerecibos imprimerecibos	  */
	public void setimprimerecibos (boolean imprimerecibos)
	{
		set_Value (COLUMNNAME_imprimerecibos, Boolean.valueOf(imprimerecibos));
	}

	/** Get imprimerecibos.
		@return imprimerecibos	  */
	public boolean isimprimerecibos () 
	{
		Object oo = get_Value(COLUMNNAME_imprimerecibos);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LiteralNumber.
		@param LiteralNumber LiteralNumber	  */
	public void setLiteralNumber (String LiteralNumber)
	{
		set_Value (COLUMNNAME_LiteralNumber, LiteralNumber);
	}

	/** Get LiteralNumber.
		@return LiteralNumber	  */
	public String getLiteralNumber () 
	{
		return (String)get_Value(COLUMNNAME_LiteralNumber);
	}

	/** Set Message.
		@param Message 
		EMail Message
	  */
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return EMail Message
	  */
	public String getMessage () 
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	/** Set ReProcessing.
		@param ReProcessing ReProcessing	  */
	public void setReProcessing (boolean ReProcessing)
	{
		set_Value (COLUMNNAME_ReProcessing, Boolean.valueOf(ReProcessing));
	}

	/** Get ReProcessing.
		@return ReProcessing	  */
	public boolean isReProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_ReProcessing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Send EMail.
		@param SendEMail 
		Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail)
	{
		set_Value (COLUMNNAME_SendEMail, Boolean.valueOf(SendEMail));
	}

	/** Get Send EMail.
		@return Enable sending Document EMail
	  */
	public boolean isSendEMail () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Success.
		@param Success Success	  */
	public void setSuccess (boolean Success)
	{
		set_Value (COLUMNNAME_Success, Boolean.valueOf(Success));
	}

	/** Get Success.
		@return Success	  */
	public boolean isSuccess () 
	{
		Object oo = get_Value(COLUMNNAME_Success);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set sumCrBPS.
		@param sumCrBPS sumCrBPS	  */
	public void setsumCrBPS (BigDecimal sumCrBPS)
	{
		set_Value (COLUMNNAME_sumCrBPS, sumCrBPS);
	}

	/** Get sumCrBPS.
		@return sumCrBPS	  */
	public BigDecimal getsumCrBPS () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_sumCrBPS);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set sumDrBPS.
		@param sumDrBPS sumDrBPS	  */
	public void setsumDrBPS (BigDecimal sumDrBPS)
	{
		set_Value (COLUMNNAME_sumDrBPS, sumDrBPS);
	}

	/** Get sumDrBPS.
		@return sumDrBPS	  */
	public BigDecimal getsumDrBPS () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_sumDrBPS);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_HRProcesoNomina getUY_HRProcesoNomina() throws RuntimeException
    {
		return (I_UY_HRProcesoNomina)MTable.get(getCtx(), I_UY_HRProcesoNomina.Table_Name)
			.getPO(getUY_HRProcesoNomina_ID(), get_TrxName());	}

	/** Set UY_HRProcesoNomina.
		@param UY_HRProcesoNomina_ID UY_HRProcesoNomina	  */
	public void setUY_HRProcesoNomina_ID (int UY_HRProcesoNomina_ID)
	{
		if (UY_HRProcesoNomina_ID < 1) 
			set_Value (COLUMNNAME_UY_HRProcesoNomina_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRProcesoNomina_ID, Integer.valueOf(UY_HRProcesoNomina_ID));
	}

	/** Get UY_HRProcesoNomina.
		@return UY_HRProcesoNomina	  */
	public int getUY_HRProcesoNomina_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRProcesoNomina_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRResult.
		@param UY_HRResult_ID UY_HRResult	  */
	public void setUY_HRResult_ID (int UY_HRResult_ID)
	{
		if (UY_HRResult_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRResult_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRResult_ID, Integer.valueOf(UY_HRResult_ID));
	}

	/** Get UY_HRResult.
		@return UY_HRResult	  */
	public int getUY_HRResult_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRResult_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}