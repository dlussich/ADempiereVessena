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

/** Generated Model for UY_TR_MicLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_MicLine extends PO implements I_UY_TR_MicLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150505L;

    /** Standard Constructor */
    public X_UY_TR_MicLine (Properties ctx, int UY_TR_MicLine_ID, String trxName)
    {
      super (ctx, UY_TR_MicLine_ID, trxName);
      /** if (UY_TR_MicLine_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_TR_Crt_ID (0);
			setUY_TR_Mic_ID (0);
			setUY_TR_MicLine_ID (0);
			setUY_TR_TransOrderLine_ID (0);
			setUY_TR_Trip_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_MicLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_MicLine[")
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

	/** Set D-U-N-S.
		@param DUNS 
		Dun & Bradstreet Number
	  */
	public void setDUNS (String DUNS)
	{
		set_Value (COLUMNNAME_DUNS, DUNS);
	}

	/** Get D-U-N-S.
		@return Dun & Bradstreet Number
	  */
	public String getDUNS () 
	{
		return (String)get_Value(COLUMNNAME_DUNS);
	}

	/** Set Reference No.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
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

	/** Set UY_TR_MicLine.
		@param UY_TR_MicLine_ID UY_TR_MicLine	  */
	public void setUY_TR_MicLine_ID (int UY_TR_MicLine_ID)
	{
		if (UY_TR_MicLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MicLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MicLine_ID, Integer.valueOf(UY_TR_MicLine_ID));
	}

	/** Get UY_TR_MicLine.
		@return UY_TR_MicLine	  */
	public int getUY_TR_MicLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_MicLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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