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

/** Generated Model for UY_RFQGen_Vendor
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RFQGen_Vendor extends PO implements I_UY_RFQGen_Vendor, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121107L;

    /** Standard Constructor */
    public X_UY_RFQGen_Vendor (Properties ctx, int UY_RFQGen_Vendor_ID, String trxName)
    {
      super (ctx, UY_RFQGen_Vendor_ID, trxName);
      /** if (UY_RFQGen_Vendor_ID == 0)
        {
			setIsSelected (false);
			setUY_RFQGen_Prod_ID (0);
			setUY_RFQGen_Vendor_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RFQGen_Vendor (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RFQGen_Vendor[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
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

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_RFQGen_Prod getUY_RFQGen_Prod() throws RuntimeException
    {
		return (I_UY_RFQGen_Prod)MTable.get(getCtx(), I_UY_RFQGen_Prod.Table_Name)
			.getPO(getUY_RFQGen_Prod_ID(), get_TrxName());	}

	/** Set UY_RFQGen_Prod.
		@param UY_RFQGen_Prod_ID UY_RFQGen_Prod	  */
	public void setUY_RFQGen_Prod_ID (int UY_RFQGen_Prod_ID)
	{
		if (UY_RFQGen_Prod_ID < 1) 
			set_Value (COLUMNNAME_UY_RFQGen_Prod_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RFQGen_Prod_ID, Integer.valueOf(UY_RFQGen_Prod_ID));
	}

	/** Get UY_RFQGen_Prod.
		@return UY_RFQGen_Prod	  */
	public int getUY_RFQGen_Prod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQGen_Prod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RFQGen_Vendor.
		@param UY_RFQGen_Vendor_ID UY_RFQGen_Vendor	  */
	public void setUY_RFQGen_Vendor_ID (int UY_RFQGen_Vendor_ID)
	{
		if (UY_RFQGen_Vendor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RFQGen_Vendor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RFQGen_Vendor_ID, Integer.valueOf(UY_RFQGen_Vendor_ID));
	}

	/** Get UY_RFQGen_Vendor.
		@return UY_RFQGen_Vendor	  */
	public int getUY_RFQGen_Vendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQGen_Vendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}