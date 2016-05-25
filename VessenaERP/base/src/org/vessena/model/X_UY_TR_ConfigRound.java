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

/** Generated Model for UY_TR_ConfigRound
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ConfigRound extends PO implements I_UY_TR_ConfigRound, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150717L;

    /** Standard Constructor */
    public X_UY_TR_ConfigRound (Properties ctx, int UY_TR_ConfigRound_ID, String trxName)
    {
      super (ctx, UY_TR_ConfigRound_ID, trxName);
      /** if (UY_TR_ConfigRound_ID == 0)
        {
			setAmount (0);
			setPayAmt (0);
			setProductAmt (0);
			setQtyPackage (0);
			setUY_TR_Config_ID (0);
			setUY_TR_ConfigRound_ID (0);
			setVolume (0);
			setWeight (0);
			setWeight2 (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ConfigRound (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ConfigRound[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (int Amount)
	{
		set_Value (COLUMNNAME_Amount, Integer.valueOf(Amount));
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public int getAmount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Amount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (int PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, Integer.valueOf(PayAmt));
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public int getPayAmt () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayAmt);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ProductAmt.
		@param ProductAmt ProductAmt	  */
	public void setProductAmt (int ProductAmt)
	{
		set_Value (COLUMNNAME_ProductAmt, Integer.valueOf(ProductAmt));
	}

	/** Get ProductAmt.
		@return ProductAmt	  */
	public int getProductAmt () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProductAmt);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyPackage.
		@param QtyPackage QtyPackage	  */
	public void setQtyPackage (int QtyPackage)
	{
		set_Value (COLUMNNAME_QtyPackage, Integer.valueOf(QtyPackage));
	}

	/** Get QtyPackage.
		@return QtyPackage	  */
	public int getQtyPackage () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyPackage);
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

	/** Set UY_TR_ConfigRound.
		@param UY_TR_ConfigRound_ID UY_TR_ConfigRound	  */
	public void setUY_TR_ConfigRound_ID (int UY_TR_ConfigRound_ID)
	{
		if (UY_TR_ConfigRound_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigRound_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ConfigRound_ID, Integer.valueOf(UY_TR_ConfigRound_ID));
	}

	/** Get UY_TR_ConfigRound.
		@return UY_TR_ConfigRound	  */
	public int getUY_TR_ConfigRound_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ConfigRound_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (int Volume)
	{
		set_Value (COLUMNNAME_Volume, Integer.valueOf(Volume));
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public int getVolume () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Volume);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (int Weight)
	{
		set_Value (COLUMNNAME_Weight, Integer.valueOf(Weight));
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public int getWeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Weight);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Weight2.
		@param Weight2 Weight2	  */
	public void setWeight2 (int Weight2)
	{
		set_Value (COLUMNNAME_Weight2, Integer.valueOf(Weight2));
	}

	/** Get Weight2.
		@return Weight2	  */
	public int getWeight2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Weight2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}