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

/** Generated Model for UY_POPolicyCategory
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_POPolicyCategory extends PO implements I_UY_POPolicyCategory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140216L;

    /** Standard Constructor */
    public X_UY_POPolicyCategory (Properties ctx, int UY_POPolicyCategory_ID, String trxName)
    {
      super (ctx, UY_POPolicyCategory_ID, trxName);
      /** if (UY_POPolicyCategory_ID == 0)
        {
			setAD_User_ID (0);
			setM_Product_Category_ID (0);
			setUY_POPolicyCategory_ID (0);
			setUY_POPolicy_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_POPolicyCategory (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_POPolicyCategory[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Category)MTable.get(getCtx(), org.compiere.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_POPolicyCategory.
		@param UY_POPolicyCategory_ID UY_POPolicyCategory	  */
	public void setUY_POPolicyCategory_ID (int UY_POPolicyCategory_ID)
	{
		if (UY_POPolicyCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_POPolicyCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_POPolicyCategory_ID, Integer.valueOf(UY_POPolicyCategory_ID));
	}

	/** Get UY_POPolicyCategory.
		@return UY_POPolicyCategory	  */
	public int getUY_POPolicyCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POPolicyCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_POPolicy getUY_POPolicy() throws RuntimeException
    {
		return (I_UY_POPolicy)MTable.get(getCtx(), I_UY_POPolicy.Table_Name)
			.getPO(getUY_POPolicy_ID(), get_TrxName());	}

	/** Set UY_POPolicy.
		@param UY_POPolicy_ID UY_POPolicy	  */
	public void setUY_POPolicy_ID (int UY_POPolicy_ID)
	{
		if (UY_POPolicy_ID < 1) 
			set_Value (COLUMNNAME_UY_POPolicy_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POPolicy_ID, Integer.valueOf(UY_POPolicy_ID));
	}

	/** Get UY_POPolicy.
		@return UY_POPolicy	  */
	public int getUY_POPolicy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POPolicy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}