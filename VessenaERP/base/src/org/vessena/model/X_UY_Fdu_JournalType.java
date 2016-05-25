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

/** Generated Model for UY_Fdu_JournalType
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_JournalType extends PO implements I_UY_Fdu_JournalType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121102L;

    /** Standard Constructor */
    public X_UY_Fdu_JournalType (Properties ctx, int UY_Fdu_JournalType_ID, String trxName)
    {
      super (ctx, UY_Fdu_JournalType_ID, trxName);
      /** if (UY_Fdu_JournalType_ID == 0)
        {
			setC_ElementValue_ID (0);
			setC_ElementValue_ID_Cr (0);
			setUY_FduCod_ID (0);
			setUY_Fdu_JournalType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_JournalType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_JournalType[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set C_ElementValue_ID_Cr.
		@param C_ElementValue_ID_Cr 
		C_ElementValue_ID_Cr
	  */
	public void setC_ElementValue_ID_Cr (int C_ElementValue_ID_Cr)
	{
		set_Value (COLUMNNAME_C_ElementValue_ID_Cr, Integer.valueOf(C_ElementValue_ID_Cr));
	}

	/** Get C_ElementValue_ID_Cr.
		@return C_ElementValue_ID_Cr
	  */
	public int getC_ElementValue_ID_Cr () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID_Cr);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Summary Level.
		@param IsSummary 
		This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Summary Level Cr.
		@param IsSummaryCr 
		This is a summary entity
	  */
	public void setIsSummaryCr (boolean IsSummaryCr)
	{
		set_Value (COLUMNNAME_IsSummaryCr, Boolean.valueOf(IsSummaryCr));
	}

	/** Get Summary Level Cr.
		@return This is a summary entity
	  */
	public boolean isSummaryCr () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummaryCr);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_FduCod getUY_FduCod() throws RuntimeException
    {
		return (I_UY_FduCod)MTable.get(getCtx(), I_UY_FduCod.Table_Name)
			.getPO(getUY_FduCod_ID(), get_TrxName());	}

	/** Set UY_FduCod_ID.
		@param UY_FduCod_ID 
		UY_FduCod_ID
	  */
	public void setUY_FduCod_ID (int UY_FduCod_ID)
	{
		if (UY_FduCod_ID < 1) 
			set_Value (COLUMNNAME_UY_FduCod_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduCod_ID, Integer.valueOf(UY_FduCod_ID));
	}

	/** Get UY_FduCod_ID.
		@return UY_FduCod_ID
	  */
	public int getUY_FduCod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduCod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_JournalType.
		@param UY_Fdu_JournalType_ID UY_Fdu_JournalType	  */
	public void setUY_Fdu_JournalType_ID (int UY_Fdu_JournalType_ID)
	{
		if (UY_Fdu_JournalType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_JournalType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_JournalType_ID, Integer.valueOf(UY_Fdu_JournalType_ID));
	}

	/** Get UY_Fdu_JournalType.
		@return UY_Fdu_JournalType	  */
	public int getUY_Fdu_JournalType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_JournalType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}