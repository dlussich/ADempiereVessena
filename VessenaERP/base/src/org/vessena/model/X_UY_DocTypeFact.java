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

/** Generated Model for UY_DocTypeFact
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DocTypeFact extends PO implements I_UY_DocTypeFact, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141120L;

    /** Standard Constructor */
    public X_UY_DocTypeFact (Properties ctx, int UY_DocTypeFact_ID, String trxName)
    {
      super (ctx, UY_DocTypeFact_ID, trxName);
      /** if (UY_DocTypeFact_ID == 0)
        {
			setC_DocType_ID (0);
			setC_ElementValue_ID (0);
			setIsDebit (false);
// N
			setUY_DocTypeFact_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DocTypeFact (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DocTypeFact[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
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

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Calculate.
		@param Calculate Calculate	  */
	public void setCalculate (String Calculate)
	{
		set_Value (COLUMNNAME_Calculate, Calculate);
	}

	/** Get Calculate.
		@return Calculate	  */
	public String getCalculate () 
	{
		return (String)get_Value(COLUMNNAME_Calculate);
	}

	/** Set IsDebit.
		@param IsDebit IsDebit	  */
	public void setIsDebit (boolean IsDebit)
	{
		set_Value (COLUMNNAME_IsDebit, Boolean.valueOf(IsDebit));
	}

	/** Get IsDebit.
		@return IsDebit	  */
	public boolean isDebit () 
	{
		Object oo = get_Value(COLUMNNAME_IsDebit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_DocTypeFact.
		@param UY_DocTypeFact_ID UY_DocTypeFact	  */
	public void setUY_DocTypeFact_ID (int UY_DocTypeFact_ID)
	{
		if (UY_DocTypeFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DocTypeFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DocTypeFact_ID, Integer.valueOf(UY_DocTypeFact_ID));
	}

	/** Get UY_DocTypeFact.
		@return UY_DocTypeFact	  */
	public int getUY_DocTypeFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DocTypeFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}