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

/** Generated Model for UY_Credit_Partner
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_Credit_Partner extends PO implements I_UY_Credit_Partner, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_Credit_Partner (Properties ctx, int UY_Credit_Partner_ID, String trxName)
    {
      super (ctx, UY_Credit_Partner_ID, trxName);
      /** if (UY_Credit_Partner_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_Credit_Filter_ID (0);
			setUY_Credit_Partner_ID (0);
			setuy_ctacte_saldo (Env.ZERO);
			setuy_ctacte_vencido (Env.ZERO);
			setuy_ctadoc_saldo (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UY_Credit_Partner (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Credit_Partner[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
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

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set so_creditlimt.
		@param so_creditlimt so_creditlimt	  */
	public void setso_creditlimt (BigDecimal so_creditlimt)
	{
		set_Value (COLUMNNAME_so_creditlimt, so_creditlimt);
	}

	/** Get so_creditlimt.
		@return so_creditlimt	  */
	public BigDecimal getso_creditlimt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_so_creditlimt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_approval_amt.
		@param uy_approval_amt uy_approval_amt	  */
	public void setuy_approval_amt (BigDecimal uy_approval_amt)
	{
		set_Value (COLUMNNAME_uy_approval_amt, uy_approval_amt);
	}

	/** Get uy_approval_amt.
		@return uy_approval_amt	  */
	public BigDecimal getuy_approval_amt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_approval_amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_Credit_Filter getUY_Credit_Filter() throws RuntimeException
    {
		return (I_UY_Credit_Filter)MTable.get(getCtx(), I_UY_Credit_Filter.Table_Name)
			.getPO(getUY_Credit_Filter_ID(), get_TrxName());	}

	/** Set UY_Credit_Filter.
		@param UY_Credit_Filter_ID UY_Credit_Filter	  */
	public void setUY_Credit_Filter_ID (int UY_Credit_Filter_ID)
	{
		if (UY_Credit_Filter_ID < 1) 
			set_Value (COLUMNNAME_UY_Credit_Filter_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Credit_Filter_ID, Integer.valueOf(UY_Credit_Filter_ID));
	}

	/** Get UY_Credit_Filter.
		@return UY_Credit_Filter	  */
	public int getUY_Credit_Filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Credit_Filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Credit_Partner.
		@param UY_Credit_Partner_ID UY_Credit_Partner	  */
	public void setUY_Credit_Partner_ID (int UY_Credit_Partner_ID)
	{
		if (UY_Credit_Partner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Credit_Partner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Credit_Partner_ID, Integer.valueOf(UY_Credit_Partner_ID));
	}

	/** Get UY_Credit_Partner.
		@return UY_Credit_Partner	  */
	public int getUY_Credit_Partner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Credit_Partner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_credit_status.
		@param uy_credit_status uy_credit_status	  */
	public void setuy_credit_status (String uy_credit_status)
	{
		set_Value (COLUMNNAME_uy_credit_status, uy_credit_status);
	}

	/** Get uy_credit_status.
		@return uy_credit_status	  */
	public String getuy_credit_status () 
	{
		return (String)get_Value(COLUMNNAME_uy_credit_status);
	}

	/** Set uy_creditlimt_doc.
		@param uy_creditlimt_doc uy_creditlimt_doc	  */
	public void setuy_creditlimt_doc (BigDecimal uy_creditlimt_doc)
	{
		set_Value (COLUMNNAME_uy_creditlimt_doc, uy_creditlimt_doc);
	}

	/** Get uy_creditlimt_doc.
		@return uy_creditlimt_doc	  */
	public BigDecimal getuy_creditlimt_doc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_creditlimt_doc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_ctacte_saldo.
		@param uy_ctacte_saldo uy_ctacte_saldo	  */
	public void setuy_ctacte_saldo (BigDecimal uy_ctacte_saldo)
	{
		set_Value (COLUMNNAME_uy_ctacte_saldo, uy_ctacte_saldo);
	}

	/** Get uy_ctacte_saldo.
		@return uy_ctacte_saldo	  */
	public BigDecimal getuy_ctacte_saldo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_ctacte_saldo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_ctacte_vencido.
		@param uy_ctacte_vencido uy_ctacte_vencido	  */
	public void setuy_ctacte_vencido (BigDecimal uy_ctacte_vencido)
	{
		set_Value (COLUMNNAME_uy_ctacte_vencido, uy_ctacte_vencido);
	}

	/** Get uy_ctacte_vencido.
		@return uy_ctacte_vencido	  */
	public BigDecimal getuy_ctacte_vencido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_ctacte_vencido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_ctadoc_saldo.
		@param uy_ctadoc_saldo uy_ctadoc_saldo	  */
	public void setuy_ctadoc_saldo (BigDecimal uy_ctadoc_saldo)
	{
		set_Value (COLUMNNAME_uy_ctadoc_saldo, uy_ctadoc_saldo);
	}

	/** Get uy_ctadoc_saldo.
		@return uy_ctadoc_saldo	  */
	public BigDecimal getuy_ctadoc_saldo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_ctadoc_saldo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_nrotrx.
		@param uy_nrotrx uy_nrotrx	  */
	public void setuy_nrotrx (int uy_nrotrx)
	{
		set_Value (COLUMNNAME_uy_nrotrx, Integer.valueOf(uy_nrotrx));
	}

	/** Get uy_nrotrx.
		@return uy_nrotrx	  */
	public int getuy_nrotrx () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_nrotrx);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}