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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_BPartner_Product
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_BPartner_Product extends PO implements I_C_BPartner_Product, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151203L;

    /** Standard Constructor */
    public X_C_BPartner_Product (Properties ctx, int C_BPartner_Product_ID, String trxName)
    {
      super (ctx, C_BPartner_Product_ID, trxName);
      /** if (C_BPartner_Product_ID == 0)
        {
			setC_BPartner_ID (0);
			setIsManufacturer (false);
// N
			setM_Product_ID (0);
			setUY_IsBonifC (false);
// N
			setUY_IsBonifS (false);
// N
			setUY_IsRetorno (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_BPartner_Product[")
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
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

	/** Set Is Manufacturer.
		@param IsManufacturer 
		Indicate role of this Business partner as Manufacturer
	  */
	public void setIsManufacturer (boolean IsManufacturer)
	{
		set_Value (COLUMNNAME_IsManufacturer, Boolean.valueOf(IsManufacturer));
	}

	/** Get Is Manufacturer.
		@return Indicate role of this Business partner as Manufacturer
	  */
	public boolean isManufacturer () 
	{
		Object oo = get_Value(COLUMNNAME_IsManufacturer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manufacturer.
		@param Manufacturer 
		Manufacturer of the Product
	  */
	public void setManufacturer (String Manufacturer)
	{
		set_Value (COLUMNNAME_Manufacturer, Manufacturer);
	}

	/** Get Manufacturer.
		@return Manufacturer of the Product
	  */
	public String getManufacturer () 
	{
		return (String)get_Value(COLUMNNAME_Manufacturer);
	}

	/** Set Margin %.
		@param Margin 
		Margin for a product as a percentage
	  */
	public void setMargin (BigDecimal Margin)
	{
		set_Value (COLUMNNAME_Margin, Margin);
	}

	/** Get Margin %.
		@return Margin for a product as a percentage
	  */
	public BigDecimal getMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price List Version.
		@param M_PriceList_Version_ID 
		Identifies a unique instance of a Price List
	  */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Price List Version.
		@return Identifies a unique instance of a Price List
	  */
	public int getM_PriceList_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_PriceList_Version_ID_2.
		@param M_PriceList_Version_ID_2 M_PriceList_Version_ID_2	  */
	public void setM_PriceList_Version_ID_2 (int M_PriceList_Version_ID_2)
	{
		set_Value (COLUMNNAME_M_PriceList_Version_ID_2, Integer.valueOf(M_PriceList_Version_ID_2));
	}

	/** Get M_PriceList_Version_ID_2.
		@return M_PriceList_Version_ID_2	  */
	public int getM_PriceList_Version_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID_2);
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceCostFinal.
		@param PriceCostFinal PriceCostFinal	  */
	public void setPriceCostFinal (BigDecimal PriceCostFinal)
	{
		set_Value (COLUMNNAME_PriceCostFinal, PriceCostFinal);
	}

	/** Get PriceCostFinal.
		@return PriceCostFinal	  */
	public BigDecimal getPriceCostFinal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceCostFinal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price Invoiced.
		@param PriceInvoiced 
		The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public void setPriceInvoiced (BigDecimal PriceInvoiced)
	{
		set_Value (COLUMNNAME_PriceInvoiced, PriceInvoiced);
	}

	/** Get Price Invoiced.
		@return The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public BigDecimal getPriceInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PO Price.
		@param PricePO 
		Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	/** Get PO Price.
		@return Price based on a purchase order
	  */
	public BigDecimal getPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceSOList.
		@param PriceSOList PriceSOList	  */
	public void setPriceSOList (BigDecimal PriceSOList)
	{
		set_Value (COLUMNNAME_PriceSOList, PriceSOList);
	}

	/** Get PriceSOList.
		@return PriceSOList	  */
	public BigDecimal getPriceSOList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceSOList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quality Rating.
		@param QualityRating 
		Method for rating vendors
	  */
	public void setQualityRating (BigDecimal QualityRating)
	{
		set_Value (COLUMNNAME_QualityRating, QualityRating);
	}

	/** Get Quality Rating.
		@return Method for rating vendors
	  */
	public BigDecimal getQualityRating () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityRating);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min Shelf Life Days.
		@param ShelfLifeMinDays 
		Minimum Shelf Life in days based on Product Instance Guarantee Date
	  */
	public void setShelfLifeMinDays (int ShelfLifeMinDays)
	{
		set_Value (COLUMNNAME_ShelfLifeMinDays, Integer.valueOf(ShelfLifeMinDays));
	}

	/** Get Min Shelf Life Days.
		@return Minimum Shelf Life in days based on Product Instance Guarantee Date
	  */
	public int getShelfLifeMinDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfLifeMinDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Min Shelf Life %.
		@param ShelfLifeMinPct 
		Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	public void setShelfLifeMinPct (int ShelfLifeMinPct)
	{
		set_Value (COLUMNNAME_ShelfLifeMinPct, Integer.valueOf(ShelfLifeMinPct));
	}

	/** Get Min Shelf Life %.
		@return Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	public int getShelfLifeMinPct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfLifeMinPct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DtoFianancAlPago.
		@param UY_DtoFianancAlPago 
		Descuento al Pago
	  */
	public void setUY_DtoFianancAlPago (BigDecimal UY_DtoFianancAlPago)
	{
		set_Value (COLUMNNAME_UY_DtoFianancAlPago, UY_DtoFianancAlPago);
	}

	/** Get UY_DtoFianancAlPago.
		@return Descuento al Pago
	  */
	public BigDecimal getUY_DtoFianancAlPago () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_DtoFianancAlPago);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_DtoFinancFact.
		@param UY_DtoFinancFact 
		Descuento Financiero en Factura
	  */
	public void setUY_DtoFinancFact (BigDecimal UY_DtoFinancFact)
	{
		set_Value (COLUMNNAME_UY_DtoFinancFact, UY_DtoFinancFact);
	}

	/** Get UY_DtoFinancFact.
		@return Descuento Financiero en Factura
	  */
	public BigDecimal getUY_DtoFinancFact () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_DtoFinancFact);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_DtoFinancFueraFact.
		@param UY_DtoFinancFueraFact 
		Descuento Financiero Fuera de Factura
	  */
	public void setUY_DtoFinancFueraFact (BigDecimal UY_DtoFinancFueraFact)
	{
		set_Value (COLUMNNAME_UY_DtoFinancFueraFact, UY_DtoFinancFueraFact);
	}

	/** Get UY_DtoFinancFueraFact.
		@return Descuento Financiero Fuera de Factura
	  */
	public BigDecimal getUY_DtoFinancFueraFact () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_DtoFinancFueraFact);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_DtoOperativo.
		@param UY_DtoOperativo 
		Descuento Operativo
	  */
	public void setUY_DtoOperativo (BigDecimal UY_DtoOperativo)
	{
		set_Value (COLUMNNAME_UY_DtoOperativo, UY_DtoOperativo);
	}

	/** Get UY_DtoOperativo.
		@return Descuento Operativo
	  */
	public BigDecimal getUY_DtoOperativo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_DtoOperativo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_IsBonifC.
		@param UY_IsBonifC 
		Es Bonificación Cruzada
	  */
	public void setUY_IsBonifC (boolean UY_IsBonifC)
	{
		set_Value (COLUMNNAME_UY_IsBonifC, Boolean.valueOf(UY_IsBonifC));
	}

	/** Get UY_IsBonifC.
		@return Es Bonificación Cruzada
	  */
	public boolean isUY_IsBonifC () 
	{
		Object oo = get_Value(COLUMNNAME_UY_IsBonifC);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_IsBonifS.
		@param UY_IsBonifS 
		Es Bonificación Simple
	  */
	public void setUY_IsBonifS (boolean UY_IsBonifS)
	{
		set_Value (COLUMNNAME_UY_IsBonifS, Boolean.valueOf(UY_IsBonifS));
	}

	/** Get UY_IsBonifS.
		@return Es Bonificación Simple
	  */
	public boolean isUY_IsBonifS () 
	{
		Object oo = get_Value(COLUMNNAME_UY_IsBonifS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_IsRetorno.
		@param UY_IsRetorno UY_IsRetorno	  */
	public void setUY_IsRetorno (boolean UY_IsRetorno)
	{
		set_Value (COLUMNNAME_UY_IsRetorno, Boolean.valueOf(UY_IsRetorno));
	}

	/** Get UY_IsRetorno.
		@return UY_IsRetorno	  */
	public boolean isUY_IsRetorno () 
	{
		Object oo = get_Value(COLUMNNAME_UY_IsRetorno);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_PorcentajeRetorno.
		@param UY_PorcentajeRetorno 
		Porcentaje de Retorno
	  */
	public void setUY_PorcentajeRetorno (BigDecimal UY_PorcentajeRetorno)
	{
		set_Value (COLUMNNAME_UY_PorcentajeRetorno, UY_PorcentajeRetorno);
	}

	/** Get UY_PorcentajeRetorno.
		@return Porcentaje de Retorno
	  */
	public BigDecimal getUY_PorcentajeRetorno () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_PorcentajeRetorno);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set ValidFrom2.
		@param ValidFrom2 ValidFrom2	  */
	public void setValidFrom2 (Timestamp ValidFrom2)
	{
		set_Value (COLUMNNAME_ValidFrom2, ValidFrom2);
	}

	/** Get ValidFrom2.
		@return ValidFrom2	  */
	public Timestamp getValidFrom2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom2);
	}

	/** Set Partner Category.
		@param VendorCategory 
		Product Category of the Business Partner
	  */
	public void setVendorCategory (String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	/** Get Partner Category.
		@return Product Category of the Business Partner
	  */
	public String getVendorCategory () 
	{
		return (String)get_Value(COLUMNNAME_VendorCategory);
	}

	/** Set Partner Product Key.
		@param VendorProductNo 
		Product Key of the Business Partner
	  */
	public void setVendorProductNo (String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	/** Get Partner Product Key.
		@return Product Key of the Business Partner
	  */
	public String getVendorProductNo () 
	{
		return (String)get_Value(COLUMNNAME_VendorProductNo);
	}
}