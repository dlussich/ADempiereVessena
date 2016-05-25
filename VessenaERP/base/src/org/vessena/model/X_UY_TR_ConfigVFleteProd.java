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

/** Generated Model for UY_TR_ConfigVFleteProd
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ConfigVFleteProd extends PO implements I_UY_TR_ConfigVFleteProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150101L;

    /** Standard Constructor */
    public X_UY_TR_ConfigVFleteProd (Properties ctx, int UY_TR_ConfigVFleteProd_ID, String trxName)
    {
      super (ctx, UY_TR_ConfigVFleteProd_ID, trxName);
      /** if (UY_TR_ConfigVFleteProd_ID == 0)
        {
			setUY_TR_ConfigVFlete_ID (0);
			setUY_TR_ConfigVFleteProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ConfigVFleteProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ConfigVFleteProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_Client_ID_Aux.
		@param AD_Client_ID_Aux AD_Client_ID_Aux	  */
	public void setAD_Client_ID_Aux (int AD_Client_ID_Aux)
	{
		set_Value (COLUMNNAME_AD_Client_ID_Aux, Integer.valueOf(AD_Client_ID_Aux));
	}

	/** Get AD_Client_ID_Aux.
		@return AD_Client_ID_Aux	  */
	public int getAD_Client_ID_Aux () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Client_ID_Aux);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UY_TR_ConfigVFlete getUY_TR_ConfigVFlete() throws RuntimeException
    {
		return (I_UY_TR_ConfigVFlete)MTable.get(getCtx(), I_UY_TR_ConfigVFlete.Table_Name)
			.getPO(getUY_TR_ConfigVFlete_ID(), get_TrxName());	}

	/** Set UY_TR_ConfigVFlete.
		@param UY_TR_ConfigVFlete_ID UY_TR_ConfigVFlete	  */
	public void setUY_TR_ConfigVFlete_ID (int UY_TR_ConfigVFlete_ID)
	{
		if (UY_TR_ConfigVFlete_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_ConfigVFlete_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_ConfigVFlete_ID, Integer.valueOf(UY_TR_ConfigVFlete_ID));
	}

	/** Get UY_TR_ConfigVFlete.
		@return UY_TR_ConfigVFlete	  */
	public int getUY_TR_ConfigVFlete_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ConfigVFlete_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_ConfigVFleteProd.
		@param UY_TR_ConfigVFleteProd_ID UY_TR_ConfigVFleteProd	  */
	public void setUY_TR_ConfigVFleteProd_ID (int UY_TR_ConfigVFleteProd_ID)
	{
		if (UY_TR_ConfigVFleteProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigVFleteProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigVFleteProd_ID, Integer.valueOf(UY_TR_ConfigVFleteProd_ID));
	}

	/** Get UY_TR_ConfigVFleteProd.
		@return UY_TR_ConfigVFleteProd	  */
	public int getUY_TR_ConfigVFleteProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ConfigVFleteProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}