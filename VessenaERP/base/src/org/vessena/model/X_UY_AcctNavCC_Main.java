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

/** Generated Model for UY_AcctNavCC_Main
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_AcctNavCC_Main extends PO implements I_UY_AcctNavCC_Main, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130131L;

    /** Standard Constructor */
    public X_UY_AcctNavCC_Main (Properties ctx, int UY_AcctNavCC_Main_ID, String trxName)
    {
      super (ctx, UY_AcctNavCC_Main_ID, trxName);
      /** if (UY_AcctNavCC_Main_ID == 0)
        {
			setUY_AcctNavCC_ID (0);
			setUY_AcctNavCC_Main_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_AcctNavCC_Main (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_AcctNavCC_Main[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amt1.
		@param amt1 amt1	  */
	public void setamt1 (BigDecimal amt1)
	{
		set_Value (COLUMNNAME_amt1, amt1);
	}

	/** Get amt1.
		@return amt1	  */
	public BigDecimal getamt1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt10.
		@param amt10 amt10	  */
	public void setamt10 (BigDecimal amt10)
	{
		set_Value (COLUMNNAME_amt10, amt10);
	}

	/** Get amt10.
		@return amt10	  */
	public BigDecimal getamt10 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt10);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt11.
		@param amt11 amt11	  */
	public void setamt11 (BigDecimal amt11)
	{
		set_Value (COLUMNNAME_amt11, amt11);
	}

	/** Get amt11.
		@return amt11	  */
	public BigDecimal getamt11 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt11);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt12.
		@param amt12 amt12	  */
	public void setamt12 (BigDecimal amt12)
	{
		set_Value (COLUMNNAME_amt12, amt12);
	}

	/** Get amt12.
		@return amt12	  */
	public BigDecimal getamt12 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt12);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt13.
		@param amt13 amt13	  */
	public void setamt13 (BigDecimal amt13)
	{
		set_Value (COLUMNNAME_amt13, amt13);
	}

	/** Get amt13.
		@return amt13	  */
	public BigDecimal getamt13 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt13);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt2.
		@param amt2 amt2	  */
	public void setamt2 (BigDecimal amt2)
	{
		set_Value (COLUMNNAME_amt2, amt2);
	}

	/** Get amt2.
		@return amt2	  */
	public BigDecimal getamt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt3.
		@param amt3 amt3	  */
	public void setamt3 (BigDecimal amt3)
	{
		set_Value (COLUMNNAME_amt3, amt3);
	}

	/** Get amt3.
		@return amt3	  */
	public BigDecimal getamt3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt4.
		@param amt4 amt4	  */
	public void setamt4 (BigDecimal amt4)
	{
		set_Value (COLUMNNAME_amt4, amt4);
	}

	/** Get amt4.
		@return amt4	  */
	public BigDecimal getamt4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt5.
		@param amt5 amt5	  */
	public void setamt5 (BigDecimal amt5)
	{
		set_Value (COLUMNNAME_amt5, amt5);
	}

	/** Get amt5.
		@return amt5	  */
	public BigDecimal getamt5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt6.
		@param amt6 amt6	  */
	public void setamt6 (BigDecimal amt6)
	{
		set_Value (COLUMNNAME_amt6, amt6);
	}

	/** Get amt6.
		@return amt6	  */
	public BigDecimal getamt6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt7.
		@param amt7 amt7	  */
	public void setamt7 (BigDecimal amt7)
	{
		set_Value (COLUMNNAME_amt7, amt7);
	}

	/** Get amt7.
		@return amt7	  */
	public BigDecimal getamt7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt8.
		@param amt8 amt8	  */
	public void setamt8 (BigDecimal amt8)
	{
		set_Value (COLUMNNAME_amt8, amt8);
	}

	/** Get amt8.
		@return amt8	  */
	public BigDecimal getamt8 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt8);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt9.
		@param amt9 amt9	  */
	public void setamt9 (BigDecimal amt9)
	{
		set_Value (COLUMNNAME_amt9, amt9);
	}

	/** Get amt9.
		@return amt9	  */
	public BigDecimal getamt9 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt9);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_AcctNavCC getUY_AcctNavCC() throws RuntimeException
    {
		return (I_UY_AcctNavCC)MTable.get(getCtx(), I_UY_AcctNavCC.Table_Name)
			.getPO(getUY_AcctNavCC_ID(), get_TrxName());	}

	/** Set UY_AcctNavCC.
		@param UY_AcctNavCC_ID UY_AcctNavCC	  */
	public void setUY_AcctNavCC_ID (int UY_AcctNavCC_ID)
	{
		if (UY_AcctNavCC_ID < 1) 
			set_Value (COLUMNNAME_UY_AcctNavCC_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AcctNavCC_ID, Integer.valueOf(UY_AcctNavCC_ID));
	}

	/** Get UY_AcctNavCC.
		@return UY_AcctNavCC	  */
	public int getUY_AcctNavCC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AcctNavCC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_AcctNavCC_Main.
		@param UY_AcctNavCC_Main_ID UY_AcctNavCC_Main	  */
	public void setUY_AcctNavCC_Main_ID (int UY_AcctNavCC_Main_ID)
	{
		if (UY_AcctNavCC_Main_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_AcctNavCC_Main_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_AcctNavCC_Main_ID, Integer.valueOf(UY_AcctNavCC_Main_ID));
	}

	/** Get UY_AcctNavCC_Main.
		@return UY_AcctNavCC_Main	  */
	public int getUY_AcctNavCC_Main_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AcctNavCC_Main_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}