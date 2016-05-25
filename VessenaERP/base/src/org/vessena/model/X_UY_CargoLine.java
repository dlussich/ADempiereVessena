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

/** Generated Model for UY_CargoLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_CargoLine extends PO implements I_UY_CargoLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130507L;

    /** Standard Constructor */
    public X_UY_CargoLine (Properties ctx, int UY_CargoLine_ID, String trxName)
    {
      super (ctx, UY_CargoLine_ID, trxName);
      /** if (UY_CargoLine_ID == 0)
        {
			setC_Activity_ID_1 (0);
			setC_ElementValue_ID (0);
			setIsDebit (false);
// N
			setUY_Cargo_ID (0);
			setUY_CargoLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CargoLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CargoLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_Activity_ID_1.
		@param C_Activity_ID_1 C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1)
	{
		set_Value (COLUMNNAME_C_Activity_ID_1, Integer.valueOf(C_Activity_ID_1));
	}

	/** Get C_Activity_ID_1.
		@return C_Activity_ID_1	  */
	public int getC_Activity_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Activity_ID_2.
		@param C_Activity_ID_2 C_Activity_ID_2	  */
	public void setC_Activity_ID_2 (int C_Activity_ID_2)
	{
		set_Value (COLUMNNAME_C_Activity_ID_2, Integer.valueOf(C_Activity_ID_2));
	}

	/** Get C_Activity_ID_2.
		@return C_Activity_ID_2	  */
	public int getC_Activity_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Activity_ID_3.
		@param C_Activity_ID_3 C_Activity_ID_3	  */
	public void setC_Activity_ID_3 (int C_Activity_ID_3)
	{
		set_Value (COLUMNNAME_C_Activity_ID_3, Integer.valueOf(C_Activity_ID_3));
	}

	/** Get C_Activity_ID_3.
		@return C_Activity_ID_3	  */
	public int getC_Activity_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_3);
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

	public I_UY_Cargo getUY_Cargo() throws RuntimeException
    {
		return (I_UY_Cargo)MTable.get(getCtx(), I_UY_Cargo.Table_Name)
			.getPO(getUY_Cargo_ID(), get_TrxName());	}

	/** Set UY_Cargo.
		@param UY_Cargo_ID UY_Cargo	  */
	public void setUY_Cargo_ID (int UY_Cargo_ID)
	{
		if (UY_Cargo_ID < 1) 
			set_Value (COLUMNNAME_UY_Cargo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Cargo_ID, Integer.valueOf(UY_Cargo_ID));
	}

	/** Get UY_Cargo.
		@return UY_Cargo	  */
	public int getUY_Cargo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Cargo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CargoLine.
		@param UY_CargoLine_ID UY_CargoLine	  */
	public void setUY_CargoLine_ID (int UY_CargoLine_ID)
	{
		if (UY_CargoLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CargoLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CargoLine_ID, Integer.valueOf(UY_CargoLine_ID));
	}

	/** Get UY_CargoLine.
		@return UY_CargoLine	  */
	public int getUY_CargoLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CargoLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}