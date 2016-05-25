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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_PR_Task
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PR_Task extends PO implements I_UY_PR_Task, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140904L;

    /** Standard Constructor */
    public X_UY_PR_Task (Properties ctx, int UY_PR_Task_ID, String trxName)
    {
      super (ctx, UY_PR_Task_ID, trxName);
      /** if (UY_PR_Task_ID == 0)
        {
			setName (null);
			setUY_PR_Task_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_PR_Task (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PR_Task[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DeadLine.
		@param DeadLine DeadLine	  */
	public void setDeadLine (BigDecimal DeadLine)
	{
		set_Value (COLUMNNAME_DeadLine, DeadLine);
	}

	/** Get DeadLine.
		@return DeadLine	  */
	public BigDecimal getDeadLine () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DeadLine);
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

	/** Set MediumTerm.
		@param MediumTerm MediumTerm	  */
	public void setMediumTerm (BigDecimal MediumTerm)
	{
		set_Value (COLUMNNAME_MediumTerm, MediumTerm);
	}

	/** Get MediumTerm.
		@return MediumTerm	  */
	public BigDecimal getMediumTerm () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MediumTerm);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set UY_PR_Task.
		@param UY_PR_Task_ID UY_PR_Task	  */
	public void setUY_PR_Task_ID (int UY_PR_Task_ID)
	{
		if (UY_PR_Task_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PR_Task_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PR_Task_ID, Integer.valueOf(UY_PR_Task_ID));
	}

	/** Get UY_PR_Task.
		@return UY_PR_Task	  */
	public int getUY_PR_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Cause getUY_R_Cause() throws RuntimeException
    {
		return (I_UY_R_Cause)MTable.get(getCtx(), I_UY_R_Cause.Table_Name)
			.getPO(getUY_R_Cause_ID(), get_TrxName());	}

	/** Set UY_R_Cause.
		@param UY_R_Cause_ID UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID)
	{
		if (UY_R_Cause_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Cause_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Cause_ID, Integer.valueOf(UY_R_Cause_ID));
	}

	/** Get UY_R_Cause.
		@return UY_R_Cause	  */
	public int getUY_R_Cause_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Cause_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_SubCause getUY_R_SubCause() throws RuntimeException
    {
		return (I_UY_R_SubCause)MTable.get(getCtx(), I_UY_R_SubCause.Table_Name)
			.getPO(getUY_R_SubCause_ID(), get_TrxName());	}

	/** Set UY_R_SubCause.
		@param UY_R_SubCause_ID UY_R_SubCause	  */
	public void setUY_R_SubCause_ID (int UY_R_SubCause_ID)
	{
		if (UY_R_SubCause_ID < 1) 
			set_Value (COLUMNNAME_UY_R_SubCause_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_SubCause_ID, Integer.valueOf(UY_R_SubCause_ID));
	}

	/** Get UY_R_SubCause.
		@return UY_R_SubCause	  */
	public int getUY_R_SubCause_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_SubCause_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Type getUY_R_Type() throws RuntimeException
    {
		return (I_UY_R_Type)MTable.get(getCtx(), I_UY_R_Type.Table_Name)
			.getPO(getUY_R_Type_ID(), get_TrxName());	}

	/** Set UY_R_Type.
		@param UY_R_Type_ID UY_R_Type	  */
	public void setUY_R_Type_ID (int UY_R_Type_ID)
	{
		if (UY_R_Type_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Type_ID, Integer.valueOf(UY_R_Type_ID));
	}

	/** Get UY_R_Type.
		@return UY_R_Type	  */
	public int getUY_R_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Type_ID);
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