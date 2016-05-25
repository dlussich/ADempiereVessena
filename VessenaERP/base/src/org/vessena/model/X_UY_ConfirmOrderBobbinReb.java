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

/** Generated Model for UY_ConfirmOrderBobbinReb
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ConfirmOrderBobbinReb extends PO implements I_UY_ConfirmOrderBobbinReb, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151112L;

    /** Standard Constructor */
    public X_UY_ConfirmOrderBobbinReb (Properties ctx, int UY_ConfirmOrderBobbinReb_ID, String trxName)
    {
      super (ctx, UY_ConfirmOrderBobbinReb_ID, trxName);
      /** if (UY_ConfirmOrderBobbinReb_ID == 0)
        {
			setAD_User_ID (0);
			setM_Warehouse_ID (0);
			setnumero (null);
			setUY_ConfirmOrderBobbinReb_ID (0);
			setUY_Confirmorderhdr_ID (0);
			setWeight (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UY_ConfirmOrderBobbinReb (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ConfirmOrderBobbinReb[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set marcas.
		@param marcas marcas	  */
	public void setmarcas (String marcas)
	{
		set_Value (COLUMNNAME_marcas, marcas);
	}

	/** Get marcas.
		@return marcas	  */
	public String getmarcas () 
	{
		return (String)get_Value(COLUMNNAME_marcas);
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set numero.
		@param numero numero	  */
	public void setnumero (String numero)
	{
		set_Value (COLUMNNAME_numero, numero);
	}

	/** Get numero.
		@return numero	  */
	public String getnumero () 
	{
		return (String)get_Value(COLUMNNAME_numero);
	}

	/** Set UY_ConfirmOrderBobbinReb.
		@param UY_ConfirmOrderBobbinReb_ID UY_ConfirmOrderBobbinReb	  */
	public void setUY_ConfirmOrderBobbinReb_ID (int UY_ConfirmOrderBobbinReb_ID)
	{
		if (UY_ConfirmOrderBobbinReb_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ConfirmOrderBobbinReb_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ConfirmOrderBobbinReb_ID, Integer.valueOf(UY_ConfirmOrderBobbinReb_ID));
	}

	/** Get UY_ConfirmOrderBobbinReb.
		@return UY_ConfirmOrderBobbinReb	  */
	public int getUY_ConfirmOrderBobbinReb_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ConfirmOrderBobbinReb_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Confirmorderhdr getUY_Confirmorderhdr() throws RuntimeException
    {
		return (I_UY_Confirmorderhdr)MTable.get(getCtx(), I_UY_Confirmorderhdr.Table_Name)
			.getPO(getUY_Confirmorderhdr_ID(), get_TrxName());	}

	/** Set UY_Confirmorderhdr.
		@param UY_Confirmorderhdr_ID UY_Confirmorderhdr	  */
	public void setUY_Confirmorderhdr_ID (int UY_Confirmorderhdr_ID)
	{
		if (UY_Confirmorderhdr_ID < 1) 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, Integer.valueOf(UY_Confirmorderhdr_ID));
	}

	/** Get UY_Confirmorderhdr.
		@return UY_Confirmorderhdr	  */
	public int getUY_Confirmorderhdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Confirmorderhdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}