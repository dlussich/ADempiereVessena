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

/** Generated Model for UY_TR_LoadMsgLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_LoadMsgLine extends PO implements I_UY_TR_LoadMsgLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150305L;

    /** Standard Constructor */
    public X_UY_TR_LoadMsgLine (Properties ctx, int UY_TR_LoadMsgLine_ID, String trxName)
    {
      super (ctx, UY_TR_LoadMsgLine_ID, trxName);
      /** if (UY_TR_LoadMsgLine_ID == 0)
        {
			setIsSelected (false);
// N
			setIsSent (false);
// N
			setUY_TR_LoadMsg_ID (0);
			setUY_TR_LoadMsgLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_LoadMsgLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_LoadMsgLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_BPartner_ID_From.
		@param C_BPartner_ID_From C_BPartner_ID_From	  */
	public void setC_BPartner_ID_From (int C_BPartner_ID_From)
	{
		set_Value (COLUMNNAME_C_BPartner_ID_From, Integer.valueOf(C_BPartner_ID_From));
	}

	/** Get C_BPartner_ID_From.
		@return C_BPartner_ID_From	  */
	public int getC_BPartner_ID_From () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID_From);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BPartner_ID_To.
		@param C_BPartner_ID_To C_BPartner_ID_To	  */
	public void setC_BPartner_ID_To (int C_BPartner_ID_To)
	{
		set_Value (COLUMNNAME_C_BPartner_ID_To, Integer.valueOf(C_BPartner_ID_To));
	}

	/** Get C_BPartner_ID_To.
		@return C_BPartner_ID_To	  */
	public int getC_BPartner_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set IsSent.
		@param IsSent IsSent	  */
	public void setIsSent (boolean IsSent)
	{
		set_Value (COLUMNNAME_IsSent, Boolean.valueOf(IsSent));
	}

	/** Get IsSent.
		@return IsSent	  */
	public boolean isSent () 
	{
		Object oo = get_Value(COLUMNNAME_IsSent);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_LoadMsg getUY_TR_LoadMsg() throws RuntimeException
    {
		return (I_UY_TR_LoadMsg)MTable.get(getCtx(), I_UY_TR_LoadMsg.Table_Name)
			.getPO(getUY_TR_LoadMsg_ID(), get_TrxName());	}

	/** Set UY_TR_LoadMsg.
		@param UY_TR_LoadMsg_ID UY_TR_LoadMsg	  */
	public void setUY_TR_LoadMsg_ID (int UY_TR_LoadMsg_ID)
	{
		if (UY_TR_LoadMsg_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_LoadMsg_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_LoadMsg_ID, Integer.valueOf(UY_TR_LoadMsg_ID));
	}

	/** Get UY_TR_LoadMsg.
		@return UY_TR_LoadMsg	  */
	public int getUY_TR_LoadMsg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMsg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_LoadMsgLine.
		@param UY_TR_LoadMsgLine_ID UY_TR_LoadMsgLine	  */
	public void setUY_TR_LoadMsgLine_ID (int UY_TR_LoadMsgLine_ID)
	{
		if (UY_TR_LoadMsgLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMsgLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMsgLine_ID, Integer.valueOf(UY_TR_LoadMsgLine_ID));
	}

	/** Get UY_TR_LoadMsgLine.
		@return UY_TR_LoadMsgLine	  */
	public int getUY_TR_LoadMsgLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMsgLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException
    {
		return (I_UY_TR_TransOrder)MTable.get(getCtx(), I_UY_TR_TransOrder.Table_Name)
			.getPO(getUY_TR_TransOrder_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrder.
		@param UY_TR_TransOrder_ID UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID)
	{
		if (UY_TR_TransOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, Integer.valueOf(UY_TR_TransOrder_ID));
	}

	/** Get UY_TR_TransOrder.
		@return UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TransOrderLine getUY_TR_TransOrderLine() throws RuntimeException
    {
		return (I_UY_TR_TransOrderLine)MTable.get(getCtx(), I_UY_TR_TransOrderLine.Table_Name)
			.getPO(getUY_TR_TransOrderLine_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrderLine.
		@param UY_TR_TransOrderLine_ID UY_TR_TransOrderLine	  */
	public void setUY_TR_TransOrderLine_ID (int UY_TR_TransOrderLine_ID)
	{
		if (UY_TR_TransOrderLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrderLine_ID, Integer.valueOf(UY_TR_TransOrderLine_ID));
	}

	/** Get UY_TR_TransOrderLine.
		@return UY_TR_TransOrderLine	  */
	public int getUY_TR_TransOrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException
    {
		return (I_UY_TR_Trip)MTable.get(getCtx(), I_UY_TR_Trip.Table_Name)
			.getPO(getUY_TR_Trip_ID(), get_TrxName());	}

	/** Set UY_TR_Trip.
		@param UY_TR_Trip_ID UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID)
	{
		if (UY_TR_Trip_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, Integer.valueOf(UY_TR_Trip_ID));
	}

	/** Get UY_TR_Trip.
		@return UY_TR_Trip	  */
	public int getUY_TR_Trip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Trip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}