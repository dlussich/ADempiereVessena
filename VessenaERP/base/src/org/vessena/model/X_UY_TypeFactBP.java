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

/** Generated Model for UY_TypeFactBP
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TypeFactBP extends PO implements I_UY_TypeFactBP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150602L;

    /** Standard Constructor */
    public X_UY_TypeFactBP (Properties ctx, int UY_TypeFactBP_ID, String trxName)
    {
      super (ctx, UY_TypeFactBP_ID, trxName);
      /** if (UY_TypeFactBP_ID == 0)
        {
			setUY_TypeFact_ID (0);
			setUY_TypeFactBP_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TypeFactBP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TypeFactBP[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
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

	public I_UY_TypeFact getUY_TypeFact() throws RuntimeException
    {
		return (I_UY_TypeFact)MTable.get(getCtx(), I_UY_TypeFact.Table_Name)
			.getPO(getUY_TypeFact_ID(), get_TrxName());	}

	/** Set UY_TypeFact.
		@param UY_TypeFact_ID UY_TypeFact	  */
	public void setUY_TypeFact_ID (int UY_TypeFact_ID)
	{
		if (UY_TypeFact_ID < 1) 
			set_Value (COLUMNNAME_UY_TypeFact_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TypeFact_ID, Integer.valueOf(UY_TypeFact_ID));
	}

	/** Get UY_TypeFact.
		@return UY_TypeFact	  */
	public int getUY_TypeFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TypeFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TypeFactBP.
		@param UY_TypeFactBP_ID UY_TypeFactBP	  */
	public void setUY_TypeFactBP_ID (int UY_TypeFactBP_ID)
	{
		if (UY_TypeFactBP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TypeFactBP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TypeFactBP_ID, Integer.valueOf(UY_TypeFactBP_ID));
	}

	/** Get UY_TypeFactBP.
		@return UY_TypeFactBP	  */
	public int getUY_TypeFactBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TypeFactBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}