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

/** Generated Model for UY_TR_BudgetLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_BudgetLine extends PO implements I_UY_TR_BudgetLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141216L;

    /** Standard Constructor */
    public X_UY_TR_BudgetLine (Properties ctx, int UY_TR_BudgetLine_ID, String trxName)
    {
      super (ctx, UY_TR_BudgetLine_ID, trxName);
      /** if (UY_TR_BudgetLine_ID == 0)
        {
			setAmount (Env.ZERO);
			setC_Currency_ID (0);
			setCityType (null);
			setIsApproved (false);
			setIsConsolidated (false);
			setIsDangerous (false);
			setIsRepresentation (false);
			setM_Product_ID (0);
			setProductAmt (Env.ZERO);
			setUY_Ciudad_ID (0);
			setUY_TR_Budget_ID (0);
			setUY_TR_BudgetLine_ID (0);
			setUY_TR_TruckType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_BudgetLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_BudgetLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount2.
		@param Amount2 Amount2	  */
	public void setAmount2 (BigDecimal Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Amount2);
	}

	/** Get Amount2.
		@return Amount2	  */
	public BigDecimal getAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** CityType AD_Reference_ID=1000455 */
	public static final int CITYTYPE_AD_Reference_ID=1000455;
	/** ORIGEN = O */
	public static final String CITYTYPE_ORIGEN = "O";
	/** DESTINO = D */
	public static final String CITYTYPE_DESTINO = "D";
	/** Set CityType.
		@param CityType CityType	  */
	public void setCityType (String CityType)
	{

		set_Value (COLUMNNAME_CityType, CityType);
	}

	/** Get CityType.
		@return CityType	  */
	public String getCityType () 
	{
		return (String)get_Value(COLUMNNAME_CityType);
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

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsConsolidated.
		@param IsConsolidated IsConsolidated	  */
	public void setIsConsolidated (boolean IsConsolidated)
	{
		set_Value (COLUMNNAME_IsConsolidated, Boolean.valueOf(IsConsolidated));
	}

	/** Get IsConsolidated.
		@return IsConsolidated	  */
	public boolean isConsolidated () 
	{
		Object oo = get_Value(COLUMNNAME_IsConsolidated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDangerous.
		@param IsDangerous IsDangerous	  */
	public void setIsDangerous (boolean IsDangerous)
	{
		set_Value (COLUMNNAME_IsDangerous, Boolean.valueOf(IsDangerous));
	}

	/** Get IsDangerous.
		@return IsDangerous	  */
	public boolean isDangerous () 
	{
		Object oo = get_Value(COLUMNNAME_IsDangerous);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRepresentation.
		@param IsRepresentation IsRepresentation	  */
	public void setIsRepresentation (boolean IsRepresentation)
	{
		set_Value (COLUMNNAME_IsRepresentation, Boolean.valueOf(IsRepresentation));
	}

	/** Get IsRepresentation.
		@return IsRepresentation	  */
	public boolean isRepresentation () 
	{
		Object oo = get_Value(COLUMNNAME_IsRepresentation);
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

	/** Set OnuNO.
		@param OnuNO OnuNO	  */
	public void setOnuNO (String OnuNO)
	{
		set_Value (COLUMNNAME_OnuNO, OnuNO);
	}

	/** Get OnuNO.
		@return OnuNO	  */
	public String getOnuNO () 
	{
		return (String)get_Value(COLUMNNAME_OnuNO);
	}

	/** Set ProductAmt.
		@param ProductAmt ProductAmt	  */
	public void setProductAmt (BigDecimal ProductAmt)
	{
		set_Value (COLUMNNAME_ProductAmt, ProductAmt);
	}

	/** Get ProductAmt.
		@return ProductAmt	  */
	public BigDecimal getProductAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyPackage.
		@param QtyPackage QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage)
	{
		set_Value (COLUMNNAME_QtyPackage, QtyPackage);
	}

	/** Get QtyPackage.
		@return QtyPackage	  */
	public BigDecimal getQtyPackage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPackage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getRepresentado() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getRepresentado_ID(), get_TrxName());	}

	/** Set Representado_ID.
		@param Representado_ID Representado_ID	  */
	public void setRepresentado_ID (int Representado_ID)
	{
		if (Representado_ID < 1) 
			set_Value (COLUMNNAME_Representado_ID, null);
		else 
			set_Value (COLUMNNAME_Representado_ID, Integer.valueOf(Representado_ID));
	}

	/** Get Representado_ID.
		@return Representado_ID	  */
	public int getRepresentado_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Representado_ID);
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

	public I_UY_TR_Budget getUY_TR_Budget() throws RuntimeException
    {
		return (I_UY_TR_Budget)MTable.get(getCtx(), I_UY_TR_Budget.Table_Name)
			.getPO(getUY_TR_Budget_ID(), get_TrxName());	}

	/** Set UY_TR_Budget.
		@param UY_TR_Budget_ID UY_TR_Budget	  */
	public void setUY_TR_Budget_ID (int UY_TR_Budget_ID)
	{
		if (UY_TR_Budget_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Budget_ID, Integer.valueOf(UY_TR_Budget_ID));
	}

	/** Get UY_TR_Budget.
		@return UY_TR_Budget	  */
	public int getUY_TR_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_BudgetLine.
		@param UY_TR_BudgetLine_ID UY_TR_BudgetLine	  */
	public void setUY_TR_BudgetLine_ID (int UY_TR_BudgetLine_ID)
	{
		if (UY_TR_BudgetLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_BudgetLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_BudgetLine_ID, Integer.valueOf(UY_TR_BudgetLine_ID));
	}

	/** Get UY_TR_BudgetLine.
		@return UY_TR_BudgetLine	  */
	public int getUY_TR_BudgetLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_BudgetLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException
    {
		return (I_UY_TR_PackageType)MTable.get(getCtx(), I_UY_TR_PackageType.Table_Name)
			.getPO(getUY_TR_PackageType_ID(), get_TrxName());	}

	/** Set UY_TR_PackageType.
		@param UY_TR_PackageType_ID UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID)
	{
		if (UY_TR_PackageType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_PackageType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_PackageType_ID, Integer.valueOf(UY_TR_PackageType_ID));
	}

	/** Get UY_TR_PackageType.
		@return UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_PackageType_ID);
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

	public I_UY_TR_TruckType getUY_TR_TruckType() throws RuntimeException
    {
		return (I_UY_TR_TruckType)MTable.get(getCtx(), I_UY_TR_TruckType.Table_Name)
			.getPO(getUY_TR_TruckType_ID(), get_TrxName());	}

	/** Set UY_TR_TruckType.
		@param UY_TR_TruckType_ID UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID)
	{
		if (UY_TR_TruckType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TruckType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TruckType_ID, Integer.valueOf(UY_TR_TruckType_ID));
	}

	/** Get UY_TR_TruckType.
		@return UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Weight2.
		@param Weight2 Weight2	  */
	public void setWeight2 (BigDecimal Weight2)
	{
		set_Value (COLUMNNAME_Weight2, Weight2);
	}

	/** Get Weight2.
		@return Weight2	  */
	public BigDecimal getWeight2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}