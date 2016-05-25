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

/** Generated Model for UY_TT_PostalGrp
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_PostalGrp extends PO implements I_UY_TT_PostalGrp, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131015L;

    /** Standard Constructor */
    public X_UY_TT_PostalGrp (Properties ctx, int UY_TT_PostalGrp_ID, String trxName)
    {
      super (ctx, UY_TT_PostalGrp_ID, trxName);
      /** if (UY_TT_PostalGrp_ID == 0)
        {
			setUY_TT_PostalGrp_ID (0);
			setUY_TT_PostalLiq_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_PostalGrp (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_PostalGrp[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID(), get_TrxName());	}

	/** Set UY_DeliveryPoint.
		@param UY_DeliveryPoint_ID UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID)
	{
		if (UY_DeliveryPoint_ID < 1) 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, Integer.valueOf(UY_DeliveryPoint_ID));
	}

	/** Get UY_DeliveryPoint.
		@return UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_PostalGrp.
		@param UY_TT_PostalGrp_ID UY_TT_PostalGrp	  */
	public void setUY_TT_PostalGrp_ID (int UY_TT_PostalGrp_ID)
	{
		if (UY_TT_PostalGrp_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_PostalGrp_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_PostalGrp_ID, Integer.valueOf(UY_TT_PostalGrp_ID));
	}

	/** Get UY_TT_PostalGrp.
		@return UY_TT_PostalGrp	  */
	public int getUY_TT_PostalGrp_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_PostalGrp_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_PostalLiq getUY_TT_PostalLiq() throws RuntimeException
    {
		return (I_UY_TT_PostalLiq)MTable.get(getCtx(), I_UY_TT_PostalLiq.Table_Name)
			.getPO(getUY_TT_PostalLiq_ID(), get_TrxName());	}

	/** Set UY_TT_PostalLiq.
		@param UY_TT_PostalLiq_ID UY_TT_PostalLiq	  */
	public void setUY_TT_PostalLiq_ID (int UY_TT_PostalLiq_ID)
	{
		if (UY_TT_PostalLiq_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_PostalLiq_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_PostalLiq_ID, Integer.valueOf(UY_TT_PostalLiq_ID));
	}

	/** Get UY_TT_PostalLiq.
		@return UY_TT_PostalLiq	  */
	public int getUY_TT_PostalLiq_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_PostalLiq_ID);
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