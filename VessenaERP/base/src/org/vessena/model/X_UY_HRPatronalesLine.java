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

/** Generated Model for UY_HRPatronalesLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRPatronalesLine extends PO implements I_UY_HRPatronalesLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130715L;

    /** Standard Constructor */
    public X_UY_HRPatronalesLine (Properties ctx, int UY_HRPatronalesLine_ID, String trxName)
    {
      super (ctx, UY_HRPatronalesLine_ID, trxName);
      /** if (UY_HRPatronalesLine_ID == 0)
        {
			setUY_HRPatronalesLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRPatronalesLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRPatronalesLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set gravado.
		@param gravado gravado	  */
	public void setgravado (BigDecimal gravado)
	{
		set_Value (COLUMNNAME_gravado, gravado);
	}

	/** Get gravado.
		@return gravado	  */
	public BigDecimal getgravado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_gravado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(getHR_Concept_ID(), get_TrxName());	}

	/** Set Payroll Concept.
		@param HR_Concept_ID Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get Payroll Concept.
		@return Payroll Concept	  */
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_HRPatronalesDetail getUY_HRPatronalesDetail() throws RuntimeException
    {
		return (I_UY_HRPatronalesDetail)MTable.get(getCtx(), I_UY_HRPatronalesDetail.Table_Name)
			.getPO(getUY_HRPatronalesDetail_ID(), get_TrxName());	}

	/** Set UY_HRPatronalesDetail.
		@param UY_HRPatronalesDetail_ID UY_HRPatronalesDetail	  */
	public void setUY_HRPatronalesDetail_ID (int UY_HRPatronalesDetail_ID)
	{
		if (UY_HRPatronalesDetail_ID < 1) 
			set_Value (COLUMNNAME_UY_HRPatronalesDetail_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRPatronalesDetail_ID, Integer.valueOf(UY_HRPatronalesDetail_ID));
	}

	/** Get UY_HRPatronalesDetail.
		@return UY_HRPatronalesDetail	  */
	public int getUY_HRPatronalesDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRPatronalesDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRPatronalesLine.
		@param UY_HRPatronalesLine_ID UY_HRPatronalesLine	  */
	public void setUY_HRPatronalesLine_ID (int UY_HRPatronalesLine_ID)
	{
		if (UY_HRPatronalesLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRPatronalesLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRPatronalesLine_ID, Integer.valueOf(UY_HRPatronalesLine_ID));
	}

	/** Get UY_HRPatronalesLine.
		@return UY_HRPatronalesLine	  */
	public int getUY_HRPatronalesLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRPatronalesLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}