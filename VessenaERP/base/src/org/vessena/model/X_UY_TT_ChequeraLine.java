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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_ChequeraLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_TT_ChequeraLine extends PO implements I_UY_TT_ChequeraLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131002L;

    /** Standard Constructor */
    public X_UY_TT_ChequeraLine (Properties ctx, int UY_TT_ChequeraLine_ID, String trxName)
    {
      super (ctx, UY_TT_ChequeraLine_ID, trxName);
      /** if (UY_TT_ChequeraLine_ID == 0)
        {
			setSuccess (false);
			setUY_TT_Chequera_ID (0);
			setUY_TT_ChequeraLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_ChequeraLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_ChequeraLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
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

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
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

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
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

	/** Set Success.
		@param Success Success	  */
	public void setSuccess (boolean Success)
	{
		set_Value (COLUMNNAME_Success, Boolean.valueOf(Success));
	}

	/** Get Success.
		@return Success	  */
	public boolean isSuccess () 
	{
		Object oo = get_Value(COLUMNNAME_Success);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_TT_Chequera getUY_TT_Chequera() throws RuntimeException
    {
		return (I_UY_TT_Chequera)MTable.get(getCtx(), I_UY_TT_Chequera.Table_Name)
			.getPO(getUY_TT_Chequera_ID(), get_TrxName());	}

	/** Set UY_TT_Chequera.
		@param UY_TT_Chequera_ID UY_TT_Chequera	  */
	public void setUY_TT_Chequera_ID (int UY_TT_Chequera_ID)
	{
		if (UY_TT_Chequera_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Chequera_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Chequera_ID, Integer.valueOf(UY_TT_Chequera_ID));
	}

	/** Get UY_TT_Chequera.
		@return UY_TT_Chequera	  */
	public int getUY_TT_Chequera_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Chequera_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_ChequeraLine.
		@param UY_TT_ChequeraLine_ID UY_TT_ChequeraLine	  */
	public void setUY_TT_ChequeraLine_ID (int UY_TT_ChequeraLine_ID)
	{
		if (UY_TT_ChequeraLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ChequeraLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ChequeraLine_ID, Integer.valueOf(UY_TT_ChequeraLine_ID));
	}

	/** Get UY_TT_ChequeraLine.
		@return UY_TT_ChequeraLine	  */
	public int getUY_TT_ChequeraLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ChequeraLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}