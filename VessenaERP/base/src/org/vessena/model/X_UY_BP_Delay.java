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

/** Generated Model for UY_BP_Delay
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BP_Delay extends PO implements I_UY_BP_Delay, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151124L;

    /** Standard Constructor */
    public X_UY_BP_Delay (Properties ctx, int UY_BP_Delay_ID, String trxName)
    {
      super (ctx, UY_BP_Delay_ID, trxName);
      /** if (UY_BP_Delay_ID == 0)
        {
			setUY_BP_Delay_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BP_Delay (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BP_Delay[")
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

	/** Set porcentaje.
		@param porcentaje porcentaje	  */
	public void setporcentaje (BigDecimal porcentaje)
	{
		set_Value (COLUMNNAME_porcentaje, porcentaje);
	}

	/** Get porcentaje.
		@return porcentaje	  */
	public BigDecimal getporcentaje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcentaje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyFrom.
		@param QtyFrom QtyFrom	  */
	public void setQtyFrom (BigDecimal QtyFrom)
	{
		set_Value (COLUMNNAME_QtyFrom, QtyFrom);
	}

	/** Get QtyFrom.
		@return QtyFrom	  */
	public BigDecimal getQtyFrom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyFrom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyTo.
		@param QtyTo QtyTo	  */
	public void setQtyTo (BigDecimal QtyTo)
	{
		set_Value (COLUMNNAME_QtyTo, QtyTo);
	}

	/** Get QtyTo.
		@return QtyTo	  */
	public BigDecimal getQtyTo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyTo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_BP_Delay.
		@param UY_BP_Delay_ID UY_BP_Delay	  */
	public void setUY_BP_Delay_ID (int UY_BP_Delay_ID)
	{
		if (UY_BP_Delay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BP_Delay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BP_Delay_ID, Integer.valueOf(UY_BP_Delay_ID));
	}

	/** Get UY_BP_Delay.
		@return UY_BP_Delay	  */
	public int getUY_BP_Delay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BP_Delay_ID);
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