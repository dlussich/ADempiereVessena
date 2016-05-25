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

/** Generated Model for UY_RT_InterfaceScales
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_InterfaceScales extends PO implements I_UY_RT_InterfaceScales, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160120L;

    /** Standard Constructor */
    public X_UY_RT_InterfaceScales (Properties ctx, int UY_RT_InterfaceScales_ID, String trxName)
    {
      super (ctx, UY_RT_InterfaceScales_ID, trxName);
      /** if (UY_RT_InterfaceScales_ID == 0)
        {
			setAction (null);
			setM_Product_ID (0);
			setUY_RT_InterfaceScales_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_InterfaceScales (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_InterfaceScales[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Action.
		@param Action 
		Indicates the Action to be performed
	  */
	public void setAction (String Action)
	{
		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Action.
		@return Indicates the Action to be performed
	  */
	public String getAction () 
	{
		return (String)get_Value(COLUMNNAME_Action);
	}

	/** Set codprod.
		@param codprod codprod	  */
	public void setcodprod (String codprod)
	{
		set_Value (COLUMNNAME_codprod, codprod);
	}

	/** Get codprod.
		@return codprod	  */
	public String getcodprod () 
	{
		return (String)get_Value(COLUMNNAME_codprod);
	}

	/** Set codupc.
		@param codupc codupc	  */
	public void setcodupc (String codupc)
	{
		set_Value (COLUMNNAME_codupc, codupc);
	}

	/** Get codupc.
		@return codupc	  */
	public String getcodupc () 
	{
		return (String)get_Value(COLUMNNAME_codupc);
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

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ReadingDate.
		@param ReadingDate ReadingDate	  */
	public void setReadingDate (Timestamp ReadingDate)
	{
		set_Value (COLUMNNAME_ReadingDate, ReadingDate);
	}

	/** Get ReadingDate.
		@return ReadingDate	  */
	public Timestamp getReadingDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ReadingDate);
	}

	/** Set UY_RT_InterfaceScales.
		@param UY_RT_InterfaceScales_ID UY_RT_InterfaceScales	  */
	public void setUY_RT_InterfaceScales_ID (int UY_RT_InterfaceScales_ID)
	{
		if (UY_RT_InterfaceScales_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InterfaceScales_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InterfaceScales_ID, Integer.valueOf(UY_RT_InterfaceScales_ID));
	}

	/** Get UY_RT_InterfaceScales.
		@return UY_RT_InterfaceScales	  */
	public int getUY_RT_InterfaceScales_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_InterfaceScales_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}