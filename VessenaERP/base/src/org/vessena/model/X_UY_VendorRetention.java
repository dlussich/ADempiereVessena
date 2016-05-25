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

/** Generated Model for UY_VendorRetention
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_VendorRetention extends PO implements I_UY_VendorRetention, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121121L;

    /** Standard Constructor */
    public X_UY_VendorRetention (Properties ctx, int UY_VendorRetention_ID, String trxName)
    {
      super (ctx, UY_VendorRetention_ID, trxName);
      /** if (UY_VendorRetention_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_Retention_ID (0);
			setUY_VendorRetention_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_VendorRetention (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_VendorRetention[")
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

	public I_UY_Retention getUY_Retention() throws RuntimeException
    {
		return (I_UY_Retention)MTable.get(getCtx(), I_UY_Retention.Table_Name)
			.getPO(getUY_Retention_ID(), get_TrxName());	}

	/** Set UY_Retention.
		@param UY_Retention_ID UY_Retention	  */
	public void setUY_Retention_ID (int UY_Retention_ID)
	{
		if (UY_Retention_ID < 1) 
			set_Value (COLUMNNAME_UY_Retention_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Retention_ID, Integer.valueOf(UY_Retention_ID));
	}

	/** Get UY_Retention.
		@return UY_Retention	  */
	public int getUY_Retention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Retention_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_VendorRetention.
		@param UY_VendorRetention_ID UY_VendorRetention	  */
	public void setUY_VendorRetention_ID (int UY_VendorRetention_ID)
	{
		if (UY_VendorRetention_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_VendorRetention_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_VendorRetention_ID, Integer.valueOf(UY_VendorRetention_ID));
	}

	/** Get UY_VendorRetention.
		@return UY_VendorRetention	  */
	public int getUY_VendorRetention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_VendorRetention_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}