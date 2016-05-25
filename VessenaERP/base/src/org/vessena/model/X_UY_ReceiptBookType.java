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

/** Generated Model for UY_ReceiptBookType
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_ReceiptBookType extends PO implements I_UY_ReceiptBookType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_ReceiptBookType (Properties ctx, int UY_ReceiptBookType_ID, String trxName)
    {
      super (ctx, UY_ReceiptBookType_ID, trxName);
      /** if (UY_ReceiptBookType_ID == 0)
        {
			setUY_ReceiptBookControl_ID (0);
			setUY_ReceiptBookType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ReceiptBookType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ReceiptBookType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
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

	/** Set Counter.
		@param Counter 
		Count Value
	  */
	public void setCounter (int Counter)
	{
		set_Value (COLUMNNAME_Counter, Integer.valueOf(Counter));
	}

	/** Get Counter.
		@return Count Value
	  */
	public int getCounter () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Counter);
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

	/** Set UY_ReceiptBookType.
		@param UY_ReceiptBookType_ID UY_ReceiptBookType	  */
	public void setUY_ReceiptBookType_ID (int UY_ReceiptBookType_ID)
	{
		if (UY_ReceiptBookType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ReceiptBookType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ReceiptBookType_ID, Integer.valueOf(UY_ReceiptBookType_ID));
	}

	/** Get UY_ReceiptBookType.
		@return UY_ReceiptBookType	  */
	public int getUY_ReceiptBookType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReceiptBookType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}