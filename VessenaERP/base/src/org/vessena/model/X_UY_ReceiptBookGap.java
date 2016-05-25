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

/** Generated Model for UY_ReceiptBookGap
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_ReceiptBookGap extends PO implements I_UY_ReceiptBookGap, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_ReceiptBookGap (Properties ctx, int UY_ReceiptBookGap_ID, String trxName)
    {
      super (ctx, UY_ReceiptBookGap_ID, trxName);
      /** if (UY_ReceiptBookGap_ID == 0)
        {
			setUY_ReceiptBookControl_ID (0);
			setUY_ReceiptBookGap_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ReceiptBookGap (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ReceiptBookGap[")
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

	public I_C_Payment getC_Payment() throws RuntimeException
    {
		return (I_C_Payment)MTable.get(getCtx(), I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set End document number.
		@param DocumentNoEnd 
		End document number
	  */
	public void setDocumentNoEnd (int DocumentNoEnd)
	{
		set_Value (COLUMNNAME_DocumentNoEnd, Integer.valueOf(DocumentNoEnd));
	}

	/** Get End document number.
		@return End document number
	  */
	public int getDocumentNoEnd () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentNoEnd);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Start document number.
		@param DocumentNoStart 
		Start document number
	  */
	public void setDocumentNoStart (int DocumentNoStart)
	{
		set_Value (COLUMNNAME_DocumentNoStart, Integer.valueOf(DocumentNoStart));
	}

	/** Get Start document number.
		@return Start document number
	  */
	public int getDocumentNoStart () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentNoStart);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ReceiptBookControl getUY_ReceiptBookControl() throws RuntimeException
    {
		return (I_UY_ReceiptBookControl)MTable.get(getCtx(), I_UY_ReceiptBookControl.Table_Name)
			.getPO(getUY_ReceiptBookControl_ID(), get_TrxName());	}

	/** Set UY_ReceiptBookControl.
		@param UY_ReceiptBookControl_ID UY_ReceiptBookControl	  */
	public void setUY_ReceiptBookControl_ID (int UY_ReceiptBookControl_ID)
	{
		if (UY_ReceiptBookControl_ID < 1) 
			set_Value (COLUMNNAME_UY_ReceiptBookControl_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ReceiptBookControl_ID, Integer.valueOf(UY_ReceiptBookControl_ID));
	}

	/** Get UY_ReceiptBookControl.
		@return UY_ReceiptBookControl	  */
	public int getUY_ReceiptBookControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReceiptBookControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ReceiptBookGap.
		@param UY_ReceiptBookGap_ID UY_ReceiptBookGap	  */
	public void setUY_ReceiptBookGap_ID (int UY_ReceiptBookGap_ID)
	{
		if (UY_ReceiptBookGap_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ReceiptBookGap_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ReceiptBookGap_ID, Integer.valueOf(UY_ReceiptBookGap_ID));
	}

	/** Get UY_ReceiptBookGap.
		@return UY_ReceiptBookGap	  */
	public int getUY_ReceiptBookGap_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReceiptBookGap_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}