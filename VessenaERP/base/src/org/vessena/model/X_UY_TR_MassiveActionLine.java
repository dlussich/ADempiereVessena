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

/** Generated Model for UY_TR_MassiveActionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_MassiveActionLine extends PO implements I_UY_TR_MassiveActionLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150421L;

    /** Standard Constructor */
    public X_UY_TR_MassiveActionLine (Properties ctx, int UY_TR_MassiveActionLine_ID, String trxName)
    {
      super (ctx, UY_TR_MassiveActionLine_ID, trxName);
      /** if (UY_TR_MassiveActionLine_ID == 0)
        {
			setIsExecuted (false);
// N
			setIsSelected (false);
// N
			setUY_TR_MassiveAction_ID (0);
			setUY_TR_MassiveActionLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_MassiveActionLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_MassiveActionLine[")
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

	/** Set ImageNo.
		@param ImageNo ImageNo	  */
	public void setImageNo (String ImageNo)
	{
		set_Value (COLUMNNAME_ImageNo, ImageNo);
	}

	/** Get ImageNo.
		@return ImageNo	  */
	public String getImageNo () 
	{
		return (String)get_Value(COLUMNNAME_ImageNo);
	}

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set NroDNA.
		@param NroDNA NroDNA	  */
	public void setNroDNA (String NroDNA)
	{
		set_Value (COLUMNNAME_NroDNA, NroDNA);
	}

	/** Get NroDNA.
		@return NroDNA	  */
	public String getNroDNA () 
	{
		return (String)get_Value(COLUMNNAME_NroDNA);
	}

	/** Set Tractor_ID.
		@param Tractor_ID Tractor_ID	  */
	public void setTractor_ID (int Tractor_ID)
	{
		if (Tractor_ID < 1) 
			set_Value (COLUMNNAME_Tractor_ID, null);
		else 
			set_Value (COLUMNNAME_Tractor_ID, Integer.valueOf(Tractor_ID));
	}

	/** Get Tractor_ID.
		@return Tractor_ID	  */
	public int getTractor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Tractor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException
    {
		return (I_UY_Ciudad)MTable.get(getCtx(), I_UY_Ciudad.Table_Name)
			.getPO(getUY_Ciudad_ID(), get_TrxName());	}

	/** Set UY_Ciudad.
		@param UY_Ciudad_ID UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID)
	{
		if (UY_Ciudad_ID < 1) 
			set_Value (COLUMNNAME_UY_Ciudad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Ciudad_ID, Integer.valueOf(UY_Ciudad_ID));
	}

	/** Get UY_Ciudad.
		@return UY_Ciudad	  */
	public int getUY_Ciudad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_1.
		@param UY_Ciudad_ID_1 UY_Ciudad_ID_1	  */
	public void setUY_Ciudad_ID_1 (int UY_Ciudad_ID_1)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_1, Integer.valueOf(UY_Ciudad_ID_1));
	}

	/** Get UY_Ciudad_ID_1.
		@return UY_Ciudad_ID_1	  */
	public int getUY_Ciudad_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UY_TR_MassiveAction getUY_TR_MassiveAction() throws RuntimeException
    {
		return (I_UY_TR_MassiveAction)MTable.get(getCtx(), I_UY_TR_MassiveAction.Table_Name)
			.getPO(getUY_TR_MassiveAction_ID(), get_TrxName());	}

	/** Set UY_TR_MassiveAction.
		@param UY_TR_MassiveAction_ID UY_TR_MassiveAction	  */
	public void setUY_TR_MassiveAction_ID (int UY_TR_MassiveAction_ID)
	{
		if (UY_TR_MassiveAction_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_MassiveAction_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_MassiveAction_ID, Integer.valueOf(UY_TR_MassiveAction_ID));
	}

	/** Get UY_TR_MassiveAction.
		@return UY_TR_MassiveAction	  */
	public int getUY_TR_MassiveAction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_MassiveAction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_MassiveActionLine.
		@param UY_TR_MassiveActionLine_ID UY_TR_MassiveActionLine	  */
	public void setUY_TR_MassiveActionLine_ID (int UY_TR_MassiveActionLine_ID)
	{
		if (UY_TR_MassiveActionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MassiveActionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MassiveActionLine_ID, Integer.valueOf(UY_TR_MassiveActionLine_ID));
	}

	/** Get UY_TR_MassiveActionLine.
		@return UY_TR_MassiveActionLine	  */
	public int getUY_TR_MassiveActionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_MassiveActionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException
    {
		return (I_UY_TR_Mic)MTable.get(getCtx(), I_UY_TR_Mic.Table_Name)
			.getPO(getUY_TR_Mic_ID(), get_TrxName());	}

	/** Set UY_TR_Mic.
		@param UY_TR_Mic_ID UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID)
	{
		if (UY_TR_Mic_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, Integer.valueOf(UY_TR_Mic_ID));
	}

	/** Get UY_TR_Mic.
		@return UY_TR_Mic	  */
	public int getUY_TR_Mic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Mic_ID);
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
}