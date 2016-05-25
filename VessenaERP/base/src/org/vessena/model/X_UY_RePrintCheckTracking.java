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

/** Generated Model for UY_RePrintCheckTracking
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_RePrintCheckTracking extends PO implements I_UY_RePrintCheckTracking, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130605L;

    /** Standard Constructor */
    public X_UY_RePrintCheckTracking (Properties ctx, int UY_RePrintCheckTracking_ID, String trxName)
    {
      super (ctx, UY_RePrintCheckTracking_ID, trxName);
      /** if (UY_RePrintCheckTracking_ID == 0)
        {
			setC_BPartner_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setIsPrinted (false);
			setPayAmt (Env.ZERO);
			setuy_newmediospago_id (0);
			setuy_oldmediospago_id (0);
			setUY_RePrintCheck_ID (0);
			setUY_RePrintCheckTracking_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RePrintCheckTracking (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RePrintCheckTracking[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_newmediospago_id.
		@param uy_newmediospago_id uy_newmediospago_id	  */
	public void setuy_newmediospago_id (int uy_newmediospago_id)
	{
		set_Value (COLUMNNAME_uy_newmediospago_id, Integer.valueOf(uy_newmediospago_id));
	}

	/** Get uy_newmediospago_id.
		@return uy_newmediospago_id	  */
	public int getuy_newmediospago_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_newmediospago_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_oldmediospago_id.
		@param uy_oldmediospago_id uy_oldmediospago_id	  */
	public void setuy_oldmediospago_id (int uy_oldmediospago_id)
	{
		set_Value (COLUMNNAME_uy_oldmediospago_id, Integer.valueOf(uy_oldmediospago_id));
	}

	/** Get uy_oldmediospago_id.
		@return uy_oldmediospago_id	  */
	public int getuy_oldmediospago_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_oldmediospago_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RePrintCheck getUY_RePrintCheck() throws RuntimeException
    {
		return (I_UY_RePrintCheck)MTable.get(getCtx(), I_UY_RePrintCheck.Table_Name)
			.getPO(getUY_RePrintCheck_ID(), get_TrxName());	}

	/** Set UY_RePrintCheck.
		@param UY_RePrintCheck_ID UY_RePrintCheck	  */
	public void setUY_RePrintCheck_ID (int UY_RePrintCheck_ID)
	{
		if (UY_RePrintCheck_ID < 1) 
			set_Value (COLUMNNAME_UY_RePrintCheck_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RePrintCheck_ID, Integer.valueOf(UY_RePrintCheck_ID));
	}

	/** Get UY_RePrintCheck.
		@return UY_RePrintCheck	  */
	public int getUY_RePrintCheck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RePrintCheck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RePrintCheckTracking.
		@param UY_RePrintCheckTracking_ID UY_RePrintCheckTracking	  */
	public void setUY_RePrintCheckTracking_ID (int UY_RePrintCheckTracking_ID)
	{
		if (UY_RePrintCheckTracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RePrintCheckTracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RePrintCheckTracking_ID, Integer.valueOf(UY_RePrintCheckTracking_ID));
	}

	/** Get UY_RePrintCheckTracking.
		@return UY_RePrintCheckTracking	  */
	public int getUY_RePrintCheckTracking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RePrintCheckTracking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}