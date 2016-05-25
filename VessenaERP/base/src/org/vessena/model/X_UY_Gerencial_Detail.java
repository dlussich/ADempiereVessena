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

/** Generated Model for UY_Gerencial_Detail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Gerencial_Detail extends PO implements I_UY_Gerencial_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121221L;

    /** Standard Constructor */
    public X_UY_Gerencial_Detail (Properties ctx, int UY_Gerencial_Detail_ID, String trxName)
    {
      super (ctx, UY_Gerencial_Detail_ID, trxName);
      /** if (UY_Gerencial_Detail_ID == 0)
        {
			setUY_Gerencial_Account_ID (0);
			setUY_Gerencial_Detail_ID (0);
			setUY_Gerencial_ID (0);
			setUY_Gerencial_Main_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Gerencial_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Gerencial_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set acctname.
		@param acctname acctname	  */
	public void setacctname (String acctname)
	{
		set_Value (COLUMNNAME_acctname, acctname);
	}

	/** Get acctname.
		@return acctname	  */
	public String getacctname () 
	{
		return (String)get_Value(COLUMNNAME_acctname);
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

	/** Set Parent.
		@param Parent_ID 
		Parent of Entity
	  */
	public void setParent_ID (int Parent_ID)
	{
		if (Parent_ID < 1) 
			set_Value (COLUMNNAME_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_ID, Integer.valueOf(Parent_ID));
	}

	/** Get Parent.
		@return Parent of Entity
	  */
	public int getParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set parentname.
		@param parentname parentname	  */
	public void setparentname (String parentname)
	{
		set_Value (COLUMNNAME_parentname, parentname);
	}

	/** Get parentname.
		@return parentname	  */
	public String getparentname () 
	{
		return (String)get_Value(COLUMNNAME_parentname);
	}

	public I_UY_Gerencial_Account getUY_Gerencial_Account() throws RuntimeException
    {
		return (I_UY_Gerencial_Account)MTable.get(getCtx(), I_UY_Gerencial_Account.Table_Name)
			.getPO(getUY_Gerencial_Account_ID(), get_TrxName());	}

	/** Set UY_Gerencial_Account.
		@param UY_Gerencial_Account_ID UY_Gerencial_Account	  */
	public void setUY_Gerencial_Account_ID (int UY_Gerencial_Account_ID)
	{
		if (UY_Gerencial_Account_ID < 1) 
			set_Value (COLUMNNAME_UY_Gerencial_Account_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Gerencial_Account_ID, Integer.valueOf(UY_Gerencial_Account_ID));
	}

	/** Get UY_Gerencial_Account.
		@return UY_Gerencial_Account	  */
	public int getUY_Gerencial_Account_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Gerencial_Detail.
		@param UY_Gerencial_Detail_ID UY_Gerencial_Detail	  */
	public void setUY_Gerencial_Detail_ID (int UY_Gerencial_Detail_ID)
	{
		if (UY_Gerencial_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Gerencial_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Gerencial_Detail_ID, Integer.valueOf(UY_Gerencial_Detail_ID));
	}

	/** Get UY_Gerencial_Detail.
		@return UY_Gerencial_Detail	  */
	public int getUY_Gerencial_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Gerencial getUY_Gerencial() throws RuntimeException
    {
		return (I_UY_Gerencial)MTable.get(getCtx(), I_UY_Gerencial.Table_Name)
			.getPO(getUY_Gerencial_ID(), get_TrxName());	}

	/** Set UY_Gerencial.
		@param UY_Gerencial_ID UY_Gerencial	  */
	public void setUY_Gerencial_ID (int UY_Gerencial_ID)
	{
		if (UY_Gerencial_ID < 1) 
			set_Value (COLUMNNAME_UY_Gerencial_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Gerencial_ID, Integer.valueOf(UY_Gerencial_ID));
	}

	/** Get UY_Gerencial.
		@return UY_Gerencial	  */
	public int getUY_Gerencial_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Gerencial_Main getUY_Gerencial_Main() throws RuntimeException
    {
		return (I_UY_Gerencial_Main)MTable.get(getCtx(), I_UY_Gerencial_Main.Table_Name)
			.getPO(getUY_Gerencial_Main_ID(), get_TrxName());	}

	/** Set UY_Gerencial_Main.
		@param UY_Gerencial_Main_ID UY_Gerencial_Main	  */
	public void setUY_Gerencial_Main_ID (int UY_Gerencial_Main_ID)
	{
		if (UY_Gerencial_Main_ID < 1) 
			set_Value (COLUMNNAME_UY_Gerencial_Main_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Gerencial_Main_ID, Integer.valueOf(UY_Gerencial_Main_ID));
	}

	/** Get UY_Gerencial_Main.
		@return UY_Gerencial_Main	  */
	public int getUY_Gerencial_Main_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Gerencial_Main_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}