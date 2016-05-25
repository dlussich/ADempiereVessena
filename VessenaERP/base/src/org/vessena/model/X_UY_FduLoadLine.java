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

/** Generated Model for UY_FduLoadLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_FduLoadLine extends PO implements I_UY_FduLoadLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121207L;

    /** Standard Constructor */
    public X_UY_FduLoadLine (Properties ctx, int UY_FduLoadLine_ID, String trxName)
    {
      super (ctx, UY_FduLoadLine_ID, trxName);
      /** if (UY_FduLoadLine_ID == 0)
        {
			setAmtSourceCr (Env.ZERO);
			setAmtSourceDr (Env.ZERO);
			setC_Currency_ID (0);
			setC_ElementValue_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setUY_FduLoad_ID (0);
			setUY_FduLoadLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FduLoadLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FduLoadLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Source Credit.
		@param AmtSourceCr 
		Source Credit Amount
	  */
	public void setAmtSourceCr (BigDecimal AmtSourceCr)
	{
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	/** Get Source Credit.
		@return Source Credit Amount
	  */
	public BigDecimal getAmtSourceCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Source Debit.
		@param AmtSourceDr 
		Source Debit Amount
	  */
	public void setAmtSourceDr (BigDecimal AmtSourceDr)
	{
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	/** Get Source Debit.
		@return Source Debit Amount
	  */
	public BigDecimal getAmtSourceDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceDr);
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
		@param C_ElementValue_ID_Cr C_ElementValue_ID_Cr	  */
	public void setC_ElementValue_ID_Cr (int C_ElementValue_ID_Cr)
	{
		set_Value (COLUMNNAME_C_ElementValue_ID_Cr, Integer.valueOf(C_ElementValue_ID_Cr));
	}

	/** Get C_ElementValue_ID_Cr.
		@return C_ElementValue_ID_Cr	  */
	public int getC_ElementValue_ID_Cr () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID_Cr);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_ElementValue_ID_Cr2.
		@param C_ElementValue_ID_Cr2 
		C_ElementValue_ID_Cr2
	  */
	public void setC_ElementValue_ID_Cr2 (int C_ElementValue_ID_Cr2)
	{
		set_Value (COLUMNNAME_C_ElementValue_ID_Cr2, Integer.valueOf(C_ElementValue_ID_Cr2));
	}

	/** Get C_ElementValue_ID_Cr2.
		@return C_ElementValue_ID_Cr2
	  */
	public int getC_ElementValue_ID_Cr2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID_Cr2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_ElementValue_ID_Dr.
		@param C_ElementValue_ID_Dr 
		C_ElementValue_ID_Dr
	  */
	public void setC_ElementValue_ID_Dr (int C_ElementValue_ID_Dr)
	{
		set_Value (COLUMNNAME_C_ElementValue_ID_Dr, Integer.valueOf(C_ElementValue_ID_Dr));
	}

	/** Get C_ElementValue_ID_Dr.
		@return C_ElementValue_ID_Dr
	  */
	public int getC_ElementValue_ID_Dr () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID_Dr);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	public I_UY_Fdu_GrupoCC getUY_Fdu_GrupoCC() throws RuntimeException
    {
		return (I_UY_Fdu_GrupoCC)MTable.get(getCtx(), I_UY_Fdu_GrupoCC.Table_Name)
			.getPO(getUY_Fdu_GrupoCC_ID(), get_TrxName());	}

	/** Set UY_Fdu_GrupoCC.
		@param UY_Fdu_GrupoCC_ID UY_Fdu_GrupoCC	  */
	public void setUY_Fdu_GrupoCC_ID (int UY_Fdu_GrupoCC_ID)
	{
		if (UY_Fdu_GrupoCC_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_GrupoCC_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_GrupoCC_ID, Integer.valueOf(UY_Fdu_GrupoCC_ID));
	}

	/** Get UY_Fdu_GrupoCC.
		@return UY_Fdu_GrupoCC	  */
	public int getUY_Fdu_GrupoCC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_GrupoCC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FduLoad getUY_FduLoad() throws RuntimeException
    {
		return (I_UY_FduLoad)MTable.get(getCtx(), I_UY_FduLoad.Table_Name)
			.getPO(getUY_FduLoad_ID(), get_TrxName());	}

	/** Set UY_FduLoad.
		@param UY_FduLoad_ID UY_FduLoad	  */
	public void setUY_FduLoad_ID (int UY_FduLoad_ID)
	{
		if (UY_FduLoad_ID < 1) 
			set_Value (COLUMNNAME_UY_FduLoad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduLoad_ID, Integer.valueOf(UY_FduLoad_ID));
	}

	/** Get UY_FduLoad.
		@return UY_FduLoad	  */
	public int getUY_FduLoad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduLoad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FduLoadLine.
		@param UY_FduLoadLine_ID UY_FduLoadLine	  */
	public void setUY_FduLoadLine_ID (int UY_FduLoadLine_ID)
	{
		if (UY_FduLoadLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FduLoadLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FduLoadLine_ID, Integer.valueOf(UY_FduLoadLine_ID));
	}

	/** Get UY_FduLoadLine.
		@return UY_FduLoadLine	  */
	public int getUY_FduLoadLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduLoadLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Store getUY_Fdu_Store() throws RuntimeException
    {
		return (I_UY_Fdu_Store)MTable.get(getCtx(), I_UY_Fdu_Store.Table_Name)
			.getPO(getUY_Fdu_Store_ID(), get_TrxName());	}

	/** Set UY_Fdu_Store.
		@param UY_Fdu_Store_ID UY_Fdu_Store	  */
	public void setUY_Fdu_Store_ID (int UY_Fdu_Store_ID)
	{
		if (UY_Fdu_Store_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Store_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Store_ID, Integer.valueOf(UY_Fdu_Store_ID));
	}

	/** Get UY_Fdu_Store.
		@return UY_Fdu_Store	  */
	public int getUY_Fdu_Store_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Store_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}