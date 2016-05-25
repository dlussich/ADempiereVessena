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

/** Generated Model for UY_RouteLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RouteLine extends PO implements I_UY_RouteLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140923L;

    /** Standard Constructor */
    public X_UY_RouteLine (Properties ctx, int UY_RouteLine_ID, String trxName)
    {
      super (ctx, UY_RouteLine_ID, trxName);
      /** if (UY_RouteLine_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setSeqNo (0);
			setUY_Route_ID (0);
			setUY_RouteLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RouteLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_UY_RouteLine[")
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Route getUY_Route() throws RuntimeException
    {
		return (I_UY_Route)MTable.get(getCtx(), I_UY_Route.Table_Name)
			.getPO(getUY_Route_ID(), get_TrxName());	}

	/** Set UY_Route.
		@param UY_Route_ID UY_Route	  */
	public void setUY_Route_ID (int UY_Route_ID)
	{
		if (UY_Route_ID < 1) 
			set_Value (COLUMNNAME_UY_Route_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Route_ID, Integer.valueOf(UY_Route_ID));
	}

	/** Get UY_Route.
		@return UY_Route	  */
	public int getUY_Route_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Route_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RouteLine.
		@param UY_RouteLine_ID UY_RouteLine	  */
	public void setUY_RouteLine_ID (int UY_RouteLine_ID)
	{
		if (UY_RouteLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RouteLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RouteLine_ID, Integer.valueOf(UY_RouteLine_ID));
	}

	/** Get UY_RouteLine.
		@return UY_RouteLine	  */
	public int getUY_RouteLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RouteLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}