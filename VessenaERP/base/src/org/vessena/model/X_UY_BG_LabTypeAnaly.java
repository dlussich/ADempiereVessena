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

/** Generated Model for UY_BG_LabTypeAnaly
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_LabTypeAnaly extends PO implements I_UY_BG_LabTypeAnaly, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150904L;

    /** Standard Constructor */
    public X_UY_BG_LabTypeAnaly (Properties ctx, int UY_BG_LabTypeAnaly_ID, String trxName)
    {
      super (ctx, UY_BG_LabTypeAnaly_ID, trxName);
      /** if (UY_BG_LabTypeAnaly_ID == 0)
        {
			setAmt (Env.ZERO);
			setName (null);
			setUY_BG_LabSection_ID (0);
			setUY_BG_LabTypeAnaly_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_LabTypeAnaly (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_LabTypeAnaly[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set nombe.
		@param nombe nombe	  */
	public void setnombe (String nombe)
	{
		set_Value (COLUMNNAME_nombe, nombe);
	}

	/** Get nombe.
		@return nombe	  */
	public String getnombe () 
	{
		return (String)get_Value(COLUMNNAME_nombe);
	}

	public I_UY_BG_LabSection getUY_BG_LabSection() throws RuntimeException
    {
		return (I_UY_BG_LabSection)MTable.get(getCtx(), I_UY_BG_LabSection.Table_Name)
			.getPO(getUY_BG_LabSection_ID(), get_TrxName());	}

	/** Set UY_BG_LabSection.
		@param UY_BG_LabSection_ID UY_BG_LabSection	  */
	public void setUY_BG_LabSection_ID (int UY_BG_LabSection_ID)
	{
		if (UY_BG_LabSection_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_LabSection_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_LabSection_ID, Integer.valueOf(UY_BG_LabSection_ID));
	}

	/** Get UY_BG_LabSection.
		@return UY_BG_LabSection	  */
	public int getUY_BG_LabSection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_LabSection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BG_LabTypeAnaly.
		@param UY_BG_LabTypeAnaly_ID UY_BG_LabTypeAnaly	  */
	public void setUY_BG_LabTypeAnaly_ID (int UY_BG_LabTypeAnaly_ID)
	{
		if (UY_BG_LabTypeAnaly_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_LabTypeAnaly_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_LabTypeAnaly_ID, Integer.valueOf(UY_BG_LabTypeAnaly_ID));
	}

	/** Get UY_BG_LabTypeAnaly.
		@return UY_BG_LabTypeAnaly	  */
	public int getUY_BG_LabTypeAnaly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_LabTypeAnaly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}