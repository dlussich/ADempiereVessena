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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_RT_InterfaceProd
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_InterfaceProd extends PO implements I_UY_RT_InterfaceProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150624L;

    /** Standard Constructor */
    public X_UY_RT_InterfaceProd (Properties ctx, int UY_RT_InterfaceProd_ID, String trxName)
    {
      super (ctx, UY_RT_InterfaceProd_ID, trxName);
      /** if (UY_RT_InterfaceProd_ID == 0)
        {
			setM_Product_ID (0);
			setUY_RT_Action_ID (0);
			setUY_RT_InterfaceProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_InterfaceProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_InterfaceProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set attr_1.
		@param attr_1 attr_1	  */
	public void setattr_1 (int attr_1)
	{
		set_Value (COLUMNNAME_attr_1, Integer.valueOf(attr_1));
	}

	/** Get attr_1.
		@return attr_1	  */
	public int getattr_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_11.
		@param attr_11 attr_11	  */
	public void setattr_11 (int attr_11)
	{
		set_Value (COLUMNNAME_attr_11, Integer.valueOf(attr_11));
	}

	/** Get attr_11.
		@return attr_11	  */
	public int getattr_11 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_11);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_12.
		@param attr_12 attr_12	  */
	public void setattr_12 (int attr_12)
	{
		set_Value (COLUMNNAME_attr_12, Integer.valueOf(attr_12));
	}

	/** Get attr_12.
		@return attr_12	  */
	public int getattr_12 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_12);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_13.
		@param attr_13 attr_13	  */
	public void setattr_13 (int attr_13)
	{
		set_Value (COLUMNNAME_attr_13, Integer.valueOf(attr_13));
	}

	/** Get attr_13.
		@return attr_13	  */
	public int getattr_13 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_13);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_14.
		@param attr_14 attr_14	  */
	public void setattr_14 (int attr_14)
	{
		set_Value (COLUMNNAME_attr_14, Integer.valueOf(attr_14));
	}

	/** Get attr_14.
		@return attr_14	  */
	public int getattr_14 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_14);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_15.
		@param attr_15 attr_15	  */
	public void setattr_15 (int attr_15)
	{
		set_Value (COLUMNNAME_attr_15, Integer.valueOf(attr_15));
	}

	/** Get attr_15.
		@return attr_15	  */
	public int getattr_15 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_15);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_16.
		@param attr_16 attr_16	  */
	public void setattr_16 (int attr_16)
	{
		set_Value (COLUMNNAME_attr_16, Integer.valueOf(attr_16));
	}

	/** Get attr_16.
		@return attr_16	  */
	public int getattr_16 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_16);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_17.
		@param attr_17 attr_17	  */
	public void setattr_17 (int attr_17)
	{
		set_Value (COLUMNNAME_attr_17, Integer.valueOf(attr_17));
	}

	/** Get attr_17.
		@return attr_17	  */
	public int getattr_17 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_17);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_18.
		@param attr_18 attr_18	  */
	public void setattr_18 (int attr_18)
	{
		set_Value (COLUMNNAME_attr_18, Integer.valueOf(attr_18));
	}

	/** Get attr_18.
		@return attr_18	  */
	public int getattr_18 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_18);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_19.
		@param attr_19 attr_19	  */
	public void setattr_19 (int attr_19)
	{
		set_Value (COLUMNNAME_attr_19, Integer.valueOf(attr_19));
	}

	/** Get attr_19.
		@return attr_19	  */
	public int getattr_19 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_19);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_2.
		@param attr_2 attr_2	  */
	public void setattr_2 (int attr_2)
	{
		set_Value (COLUMNNAME_attr_2, Integer.valueOf(attr_2));
	}

	/** Get attr_2.
		@return attr_2	  */
	public int getattr_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_20.
		@param attr_20 attr_20	  */
	public void setattr_20 (int attr_20)
	{
		set_Value (COLUMNNAME_attr_20, Integer.valueOf(attr_20));
	}

	/** Get attr_20.
		@return attr_20	  */
	public int getattr_20 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_20);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_21.
		@param attr_21 attr_21	  */
	public void setattr_21 (int attr_21)
	{
		set_Value (COLUMNNAME_attr_21, Integer.valueOf(attr_21));
	}

	/** Get attr_21.
		@return attr_21	  */
	public int getattr_21 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_21);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_22.
		@param attr_22 attr_22	  */
	public void setattr_22 (int attr_22)
	{
		set_Value (COLUMNNAME_attr_22, Integer.valueOf(attr_22));
	}

	/** Get attr_22.
		@return attr_22	  */
	public int getattr_22 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_22);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_24.
		@param attr_24 attr_24	  */
	public void setattr_24 (int attr_24)
	{
		set_Value (COLUMNNAME_attr_24, Integer.valueOf(attr_24));
	}

	/** Get attr_24.
		@return attr_24	  */
	public int getattr_24 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_24);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_25.
		@param attr_25 attr_25	  */
	public void setattr_25 (int attr_25)
	{
		set_Value (COLUMNNAME_attr_25, Integer.valueOf(attr_25));
	}

	/** Get attr_25.
		@return attr_25	  */
	public int getattr_25 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_25);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_27.
		@param attr_27 attr_27	  */
	public void setattr_27 (int attr_27)
	{
		set_Value (COLUMNNAME_attr_27, Integer.valueOf(attr_27));
	}

	/** Get attr_27.
		@return attr_27	  */
	public int getattr_27 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_27);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_29.
		@param attr_29 attr_29	  */
	public void setattr_29 (int attr_29)
	{
		set_Value (COLUMNNAME_attr_29, Integer.valueOf(attr_29));
	}

	/** Get attr_29.
		@return attr_29	  */
	public int getattr_29 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_29);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_3.
		@param attr_3 attr_3	  */
	public void setattr_3 (int attr_3)
	{
		set_Value (COLUMNNAME_attr_3, Integer.valueOf(attr_3));
	}

	/** Get attr_3.
		@return attr_3	  */
	public int getattr_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_30.
		@param attr_30 attr_30	  */
	public void setattr_30 (int attr_30)
	{
		set_Value (COLUMNNAME_attr_30, Integer.valueOf(attr_30));
	}

	/** Get attr_30.
		@return attr_30	  */
	public int getattr_30 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_30);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_31.
		@param attr_31 attr_31	  */
	public void setattr_31 (int attr_31)
	{
		set_Value (COLUMNNAME_attr_31, Integer.valueOf(attr_31));
	}

	/** Get attr_31.
		@return attr_31	  */
	public int getattr_31 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_31);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_32.
		@param attr_32 attr_32	  */
	public void setattr_32 (int attr_32)
	{
		set_Value (COLUMNNAME_attr_32, Integer.valueOf(attr_32));
	}

	/** Get attr_32.
		@return attr_32	  */
	public int getattr_32 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_32);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_36.
		@param attr_36 attr_36	  */
	public void setattr_36 (int attr_36)
	{
		set_Value (COLUMNNAME_attr_36, Integer.valueOf(attr_36));
	}

	/** Get attr_36.
		@return attr_36	  */
	public int getattr_36 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_36);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_38.
		@param attr_38 attr_38	  */
	public void setattr_38 (int attr_38)
	{
		set_Value (COLUMNNAME_attr_38, Integer.valueOf(attr_38));
	}

	/** Get attr_38.
		@return attr_38	  */
	public int getattr_38 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_38);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_39.
		@param attr_39 attr_39	  */
	public void setattr_39 (int attr_39)
	{
		set_Value (COLUMNNAME_attr_39, Integer.valueOf(attr_39));
	}

	/** Get attr_39.
		@return attr_39	  */
	public int getattr_39 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_39);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_4.
		@param attr_4 attr_4	  */
	public void setattr_4 (int attr_4)
	{
		set_Value (COLUMNNAME_attr_4, Integer.valueOf(attr_4));
	}

	/** Get attr_4.
		@return attr_4	  */
	public int getattr_4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_4);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_40.
		@param attr_40 attr_40	  */
	public void setattr_40 (int attr_40)
	{
		set_Value (COLUMNNAME_attr_40, Integer.valueOf(attr_40));
	}

	/** Get attr_40.
		@return attr_40	  */
	public int getattr_40 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_40);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_41.
		@param attr_41 attr_41	  */
	public void setattr_41 (int attr_41)
	{
		set_Value (COLUMNNAME_attr_41, Integer.valueOf(attr_41));
	}

	/** Get attr_41.
		@return attr_41	  */
	public int getattr_41 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_41);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_45.
		@param attr_45 attr_45	  */
	public void setattr_45 (int attr_45)
	{
		set_Value (COLUMNNAME_attr_45, Integer.valueOf(attr_45));
	}

	/** Get attr_45.
		@return attr_45	  */
	public int getattr_45 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_45);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_46.
		@param attr_46 attr_46	  */
	public void setattr_46 (int attr_46)
	{
		set_Value (COLUMNNAME_attr_46, Integer.valueOf(attr_46));
	}

	/** Get attr_46.
		@return attr_46	  */
	public int getattr_46 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_46);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_47.
		@param attr_47 attr_47	  */
	public void setattr_47 (int attr_47)
	{
		set_Value (COLUMNNAME_attr_47, Integer.valueOf(attr_47));
	}

	/** Get attr_47.
		@return attr_47	  */
	public int getattr_47 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_47);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_48.
		@param attr_48 attr_48	  */
	public void setattr_48 (int attr_48)
	{
		set_Value (COLUMNNAME_attr_48, Integer.valueOf(attr_48));
	}

	/** Get attr_48.
		@return attr_48	  */
	public int getattr_48 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_48);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_5.
		@param attr_5 attr_5	  */
	public void setattr_5 (int attr_5)
	{
		set_Value (COLUMNNAME_attr_5, Integer.valueOf(attr_5));
	}

	/** Get attr_5.
		@return attr_5	  */
	public int getattr_5 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_5);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_6.
		@param attr_6 attr_6	  */
	public void setattr_6 (int attr_6)
	{
		set_Value (COLUMNNAME_attr_6, Integer.valueOf(attr_6));
	}

	/** Get attr_6.
		@return attr_6	  */
	public int getattr_6 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_6);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_7.
		@param attr_7 attr_7	  */
	public void setattr_7 (int attr_7)
	{
		set_Value (COLUMNNAME_attr_7, Integer.valueOf(attr_7));
	}

	/** Get attr_7.
		@return attr_7	  */
	public int getattr_7 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_7);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_9.
		@param attr_9 attr_9	  */
	public void setattr_9 (int attr_9)
	{
		set_Value (COLUMNNAME_attr_9, Integer.valueOf(attr_9));
	}

	/** Get attr_9.
		@return attr_9	  */
	public int getattr_9 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_9);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
    {
		return (org.compiere.model.I_C_TaxCategory)MTable.get(getCtx(), org.compiere.model.I_C_TaxCategory.Table_Name)
			.getPO(getC_TaxCategory_ID(), get_TrxName());	}

	/** Set Tax Category.
		@param C_TaxCategory_ID 
		Tax Category
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Tax Category.
		@return Tax Category
	  */
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set EnvCode.
		@param EnvCode EnvCode	  */
	public void setEnvCode (int EnvCode)
	{
		set_Value (COLUMNNAME_EnvCode, Integer.valueOf(EnvCode));
	}

	/** Get EnvCode.
		@return EnvCode	  */
	public int getEnvCode () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EnvCode);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MeasureCode.
		@param MeasureCode MeasureCode	  */
	public void setMeasureCode (String MeasureCode)
	{
		set_Value (COLUMNNAME_MeasureCode, MeasureCode);
	}

	/** Get MeasureCode.
		@return MeasureCode	  */
	public String getMeasureCode () 
	{
		return (String)get_Value(COLUMNNAME_MeasureCode);
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

	public org.compiere.model.I_M_Product getM_Product_Tandem() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_Tandem_ID(), get_TrxName());	}

	/** Set M_Product_Tandem_ID.
		@param M_Product_Tandem_ID M_Product_Tandem_ID	  */
	public void setM_Product_Tandem_ID (int M_Product_Tandem_ID)
	{
		if (M_Product_Tandem_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Tandem_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Tandem_ID, Integer.valueOf(M_Product_Tandem_ID));
	}

	/** Get M_Product_Tandem_ID.
		@return M_Product_Tandem_ID	  */
	public int getM_Product_Tandem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Tandem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processing date.
		@param ProcessingDate Processing date	  */
	public void setProcessingDate (Timestamp ProcessingDate)
	{
		set_Value (COLUMNNAME_ProcessingDate, ProcessingDate);
	}

	/** Get Processing date.
		@return Processing date	  */
	public Timestamp getProcessingDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ProcessingDate);
	}

	/** Set Product code.
		@param ProdCode 
		Product code
	  */
	public void setProdCode (String ProdCode)
	{
		set_Value (COLUMNNAME_ProdCode, ProdCode);
	}

	/** Get Product code.
		@return Product code
	  */
	public String getProdCode () 
	{
		return (String)get_Value(COLUMNNAME_ProdCode);
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

	/** Set TablaOrigen.
		@param TablaOrigen TablaOrigen	  */
	public void setTablaOrigen (String TablaOrigen)
	{
		set_Value (COLUMNNAME_TablaOrigen, TablaOrigen);
	}

	/** Get TablaOrigen.
		@return TablaOrigen	  */
	public String getTablaOrigen () 
	{
		return (String)get_Value(COLUMNNAME_TablaOrigen);
	}

	/** Set UnitsPerPack.
		@param UnitsPerPack 
		The Units Per Pack indicates the no of units of a product packed together.
	  */
	public void setUnitsPerPack (int UnitsPerPack)
	{
		set_Value (COLUMNNAME_UnitsPerPack, Integer.valueOf(UnitsPerPack));
	}

	/** Get UnitsPerPack.
		@return The Units Per Pack indicates the no of units of a product packed together.
	  */
	public int getUnitsPerPack () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnitsPerPack);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
	}

	public I_UY_RT_Action getUY_RT_Action() throws RuntimeException
    {
		return (I_UY_RT_Action)MTable.get(getCtx(), I_UY_RT_Action.Table_Name)
			.getPO(getUY_RT_Action_ID(), get_TrxName());	}

	/** Set UY_RT_Action.
		@param UY_RT_Action_ID UY_RT_Action	  */
	public void setUY_RT_Action_ID (int UY_RT_Action_ID)
	{
		if (UY_RT_Action_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_Action_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_Action_ID, Integer.valueOf(UY_RT_Action_ID));
	}

	/** Get UY_RT_Action.
		@return UY_RT_Action	  */
	public int getUY_RT_Action_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Action_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RT_InterfaceProd.
		@param UY_RT_InterfaceProd_ID UY_RT_InterfaceProd	  */
	public void setUY_RT_InterfaceProd_ID (int UY_RT_InterfaceProd_ID)
	{
		if (UY_RT_InterfaceProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InterfaceProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InterfaceProd_ID, Integer.valueOf(UY_RT_InterfaceProd_ID));
	}

	/** Get UY_RT_InterfaceProd.
		@return UY_RT_InterfaceProd	  */
	public int getUY_RT_InterfaceProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_InterfaceProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_SubFamilia getUY_SubFamilia() throws RuntimeException
    {
		return (I_UY_SubFamilia)MTable.get(getCtx(), I_UY_SubFamilia.Table_Name)
			.getPO(getUY_SubFamilia_ID(), get_TrxName());	}

	/** Set UY_SubFamilia.
		@param UY_SubFamilia_ID UY_SubFamilia	  */
	public void setUY_SubFamilia_ID (int UY_SubFamilia_ID)
	{
		if (UY_SubFamilia_ID < 1) 
			set_Value (COLUMNNAME_UY_SubFamilia_ID, null);
		else 
			set_Value (COLUMNNAME_UY_SubFamilia_ID, Integer.valueOf(UY_SubFamilia_ID));
	}

	/** Get UY_SubFamilia.
		@return UY_SubFamilia	  */
	public int getUY_SubFamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SubFamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}