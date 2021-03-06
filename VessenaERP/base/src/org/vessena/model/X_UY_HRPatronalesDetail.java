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

/** Generated Model for UY_HRPatronalesDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRPatronalesDetail extends PO implements I_UY_HRPatronalesDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130702L;

    /** Standard Constructor */
    public X_UY_HRPatronalesDetail (Properties ctx, int UY_HRPatronalesDetail_ID, String trxName)
    {
      super (ctx, UY_HRPatronalesDetail_ID, trxName);
      /** if (UY_HRPatronalesDetail_ID == 0)
        {
			setAmount (Env.ZERO);
			setUY_HRAportesPatronales_ID (0);
			setUY_HRPatronalesDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRPatronalesDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRPatronalesDetail[")
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

	public I_UY_HRAportesPatronales getUY_HRAportesPatronales() throws RuntimeException
    {
		return (I_UY_HRAportesPatronales)MTable.get(getCtx(), I_UY_HRAportesPatronales.Table_Name)
			.getPO(getUY_HRAportesPatronales_ID(), get_TrxName());	}

	/** Set UY_HRAportesPatronales.
		@param UY_HRAportesPatronales_ID UY_HRAportesPatronales	  */
	public void setUY_HRAportesPatronales_ID (int UY_HRAportesPatronales_ID)
	{
		if (UY_HRAportesPatronales_ID < 1) 
			set_Value (COLUMNNAME_UY_HRAportesPatronales_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRAportesPatronales_ID, Integer.valueOf(UY_HRAportesPatronales_ID));
	}

	/** Get UY_HRAportesPatronales.
		@return UY_HRAportesPatronales	  */
	public int getUY_HRAportesPatronales_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRAportesPatronales_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRPatronalesDetail.
		@param UY_HRPatronalesDetail_ID UY_HRPatronalesDetail	  */
	public void setUY_HRPatronalesDetail_ID (int UY_HRPatronalesDetail_ID)
	{
		if (UY_HRPatronalesDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRPatronalesDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRPatronalesDetail_ID, Integer.valueOf(UY_HRPatronalesDetail_ID));
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
}