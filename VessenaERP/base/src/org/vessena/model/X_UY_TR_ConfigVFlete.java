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

/** Generated Model for UY_TR_ConfigVFlete
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ConfigVFlete extends PO implements I_UY_TR_ConfigVFlete, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150316L;

    /** Standard Constructor */
    public X_UY_TR_ConfigVFlete (Properties ctx, int UY_TR_ConfigVFlete_ID, String trxName)
    {
      super (ctx, UY_TR_ConfigVFlete_ID, trxName);
      /** if (UY_TR_ConfigVFlete_ID == 0)
        {
			setAmtManualVF (false);
// N
			setHandleCRT (false);
// N
			setIsSeparated (false);
// N
			setUY_TR_Config_ID (0);
			setUY_TR_ConfigVFlete_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ConfigVFlete (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ConfigVFlete[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtManualVF.
		@param AmtManualVF AmtManualVF	  */
	public void setAmtManualVF (boolean AmtManualVF)
	{
		set_Value (COLUMNNAME_AmtManualVF, Boolean.valueOf(AmtManualVF));
	}

	/** Get AmtManualVF.
		@return AmtManualVF	  */
	public boolean isAmtManualVF () 
	{
		Object oo = get_Value(COLUMNNAME_AmtManualVF);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HandleCRT.
		@param HandleCRT HandleCRT	  */
	public void setHandleCRT (boolean HandleCRT)
	{
		set_Value (COLUMNNAME_HandleCRT, Boolean.valueOf(HandleCRT));
	}

	/** Get HandleCRT.
		@return HandleCRT	  */
	public boolean isHandleCRT () 
	{
		Object oo = get_Value(COLUMNNAME_HandleCRT);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSeparated.
		@param IsSeparated IsSeparated	  */
	public void setIsSeparated (boolean IsSeparated)
	{
		set_Value (COLUMNNAME_IsSeparated, Boolean.valueOf(IsSeparated));
	}

	/** Get IsSeparated.
		@return IsSeparated	  */
	public boolean isSeparated () 
	{
		Object oo = get_Value(COLUMNNAME_IsSeparated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set M_Product_ID_2.
		@param M_Product_ID_2 M_Product_ID_2	  */
	public void setM_Product_ID_2 (int M_Product_ID_2)
	{
		set_Value (COLUMNNAME_M_Product_ID_2, Integer.valueOf(M_Product_ID_2));
	}

	/** Get M_Product_ID_2.
		@return M_Product_ID_2	  */
	public int getM_Product_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_Product_ID_3.
		@param M_Product_ID_3 M_Product_ID_3	  */
	public void setM_Product_ID_3 (int M_Product_ID_3)
	{
		set_Value (COLUMNNAME_M_Product_ID_3, Integer.valueOf(M_Product_ID_3));
	}

	/** Get M_Product_ID_3.
		@return M_Product_ID_3	  */
	public int getM_Product_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Asset_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Asset_Acct(), get_TrxName());	}

	/** Set Product Asset.
		@param P_Asset_Acct 
		Account for Product Asset (Inventory)
	  */
	public void setP_Asset_Acct (int P_Asset_Acct)
	{
		set_Value (COLUMNNAME_P_Asset_Acct, Integer.valueOf(P_Asset_Acct));
	}

	/** Get Product Asset.
		@return Account for Product Asset (Inventory)
	  */
	public int getP_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Config getUY_TR_Config() throws RuntimeException
    {
		return (I_UY_TR_Config)MTable.get(getCtx(), I_UY_TR_Config.Table_Name)
			.getPO(getUY_TR_Config_ID(), get_TrxName());	}

	/** Set UY_TR_Config.
		@param UY_TR_Config_ID UY_TR_Config	  */
	public void setUY_TR_Config_ID (int UY_TR_Config_ID)
	{
		if (UY_TR_Config_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Config_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Config_ID, Integer.valueOf(UY_TR_Config_ID));
	}

	/** Get UY_TR_Config.
		@return UY_TR_Config	  */
	public int getUY_TR_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_ConfigVFlete.
		@param UY_TR_ConfigVFlete_ID UY_TR_ConfigVFlete	  */
	public void setUY_TR_ConfigVFlete_ID (int UY_TR_ConfigVFlete_ID)
	{
		if (UY_TR_ConfigVFlete_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigVFlete_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigVFlete_ID, Integer.valueOf(UY_TR_ConfigVFlete_ID));
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
}