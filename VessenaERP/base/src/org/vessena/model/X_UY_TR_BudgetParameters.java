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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_TR_BudgetParameters
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_BudgetParameters extends PO implements I_UY_TR_BudgetParameters, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141208L;

    /** Standard Constructor */
    public X_UY_TR_BudgetParameters (Properties ctx, int UY_TR_BudgetParameters_ID, String trxName)
    {
      super (ctx, UY_TR_BudgetParameters_ID, trxName);
      /** if (UY_TR_BudgetParameters_ID == 0)
        {
			setUY_TR_BudgetParameters_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_BudgetParameters (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_BudgetParameters[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Expense.
		@param Expense Expense	  */
	public void setExpense (String Expense)
	{
		set_Value (COLUMNNAME_Expense, Expense);
	}

	/** Get Expense.
		@return Expense	  */
	public String getExpense () 
	{
		return (String)get_Value(COLUMNNAME_Expense);
	}

	/** Set Note.
		@param Note 
		Optional additional user defined information
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Note.
		@return Optional additional user defined information
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Set Payment Term Note.
		@param PaymentTermNote 
		Note of a Payment Term
	  */
	public void setPaymentTermNote (String PaymentTermNote)
	{
		set_Value (COLUMNNAME_PaymentTermNote, PaymentTermNote);
	}

	/** Get Payment Term Note.
		@return Note of a Payment Term
	  */
	public String getPaymentTermNote () 
	{
		return (String)get_Value(COLUMNNAME_PaymentTermNote);
	}

	/** Set UY_TR_BudgetParameters.
		@param UY_TR_BudgetParameters_ID UY_TR_BudgetParameters	  */
	public void setUY_TR_BudgetParameters_ID (int UY_TR_BudgetParameters_ID)
	{
		if (UY_TR_BudgetParameters_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_BudgetParameters_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_BudgetParameters_ID, Integer.valueOf(UY_TR_BudgetParameters_ID));
	}

	/** Get UY_TR_BudgetParameters.
		@return UY_TR_BudgetParameters	  */
	public int getUY_TR_BudgetParameters_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_BudgetParameters_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}