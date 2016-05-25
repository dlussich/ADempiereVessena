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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_POPolicy
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_POPolicy extends PO implements I_UY_POPolicy, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140213L;

    /** Standard Constructor */
    public X_UY_POPolicy (Properties ctx, int UY_POPolicy_ID, String trxName)
    {
      super (ctx, UY_POPolicy_ID, trxName);
      /** if (UY_POPolicy_ID == 0)
        {
			setC_Currency_ID (0);
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setName (null);
			setUY_POPolicy_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_POPolicy (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_POPolicy[")
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

	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException
    {
		return (org.compiere.model.I_AD_WF_Responsible)MTable.get(getCtx(), org.compiere.model.I_AD_WF_Responsible.Table_Name)
			.getPO(getAD_WF_Responsible_ID(), get_TrxName());	}

	/** Set Workflow Responsible.
		@param AD_WF_Responsible_ID 
		Responsible for Workflow Execution
	  */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	/** Get Workflow Responsible.
		@return Responsible for Workflow Execution
	  */
	public int getAD_WF_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ApprovalUser_ID.
		@param ApprovalUser_ID ApprovalUser_ID	  */
	public void setApprovalUser_ID (int ApprovalUser_ID)
	{
		if (ApprovalUser_ID < 1) 
			set_Value (COLUMNNAME_ApprovalUser_ID, null);
		else 
			set_Value (COLUMNNAME_ApprovalUser_ID, Integer.valueOf(ApprovalUser_ID));
	}

	/** Get ApprovalUser_ID.
		@return ApprovalUser_ID	  */
	public int getApprovalUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ApprovalUser_ID);
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

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set PointABC.
		@param PointABC PointABC	  */
	public void setPointABC (int PointABC)
	{
		set_Value (COLUMNNAME_PointABC, Integer.valueOf(PointABC));
	}

	/** Get PointABC.
		@return PointABC	  */
	public int getPointABC () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PointABC);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PointDate.
		@param PointDate PointDate	  */
	public void setPointDate (int PointDate)
	{
		set_Value (COLUMNNAME_PointDate, Integer.valueOf(PointDate));
	}

	/** Get PointDate.
		@return PointDate	  */
	public int getPointDate () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PointDate);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PointPrice.
		@param PointPrice PointPrice	  */
	public void setPointPrice (int PointPrice)
	{
		set_Value (COLUMNNAME_PointPrice, Integer.valueOf(PointPrice));
	}

	/** Get PointPrice.
		@return PointPrice	  */
	public int getPointPrice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PointPrice);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_POPolicy.
		@param UY_POPolicy_ID UY_POPolicy	  */
	public void setUY_POPolicy_ID (int UY_POPolicy_ID)
	{
		if (UY_POPolicy_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_POPolicy_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_POPolicy_ID, Integer.valueOf(UY_POPolicy_ID));
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

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}