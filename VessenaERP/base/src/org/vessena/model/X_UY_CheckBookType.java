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

/** Generated Model for UY_CheckBookType
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CheckBookType extends PO implements I_UY_CheckBookType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CheckBookType (Properties ctx, int UY_CheckBookType_ID, String trxName)
    {
      super (ctx, UY_CheckBookType_ID, trxName);
      /** if (UY_CheckBookType_ID == 0)
        {
			setUY_CheckBookControl_ID (0);
			setUY_CheckBookType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CheckBookType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CheckBookType[")
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

	public I_UY_CheckBookControl getUY_CheckBookControl() throws RuntimeException
    {
		return (I_UY_CheckBookControl)MTable.get(getCtx(), I_UY_CheckBookControl.Table_Name)
			.getPO(getUY_CheckBookControl_ID(), get_TrxName());	}

	/** Set UY_CheckBookControl.
		@param UY_CheckBookControl_ID UY_CheckBookControl	  */
	public void setUY_CheckBookControl_ID (int UY_CheckBookControl_ID)
	{
		if (UY_CheckBookControl_ID < 1) 
			set_Value (COLUMNNAME_UY_CheckBookControl_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CheckBookControl_ID, Integer.valueOf(UY_CheckBookControl_ID));
	}

	/** Get UY_CheckBookControl.
		@return UY_CheckBookControl	  */
	public int getUY_CheckBookControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckBookControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CheckBookType.
		@param UY_CheckBookType_ID UY_CheckBookType	  */
	public void setUY_CheckBookType_ID (int UY_CheckBookType_ID)
	{
		if (UY_CheckBookType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CheckBookType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CheckBookType_ID, Integer.valueOf(UY_CheckBookType_ID));
	}

	/** Get UY_CheckBookType.
		@return UY_CheckBookType	  */
	public int getUY_CheckBookType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckBookType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}