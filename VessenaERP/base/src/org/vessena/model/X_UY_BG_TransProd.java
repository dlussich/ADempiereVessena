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

/** Generated Model for UY_BG_TransProd
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_TransProd extends PO implements I_UY_BG_TransProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150902L;

    /** Standard Constructor */
    public X_UY_BG_TransProd (Properties ctx, int UY_BG_TransProd_ID, String trxName)
    {
      super (ctx, UY_BG_TransProd_ID, trxName);
      /** if (UY_BG_TransProd_ID == 0)
        {
			setM_Product_ID (0);
			setUY_BG_Transaction_ID (0);
			setUY_BG_TransProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_TransProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_TransProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address 1.
		@param Address1 
		Address line 1 for this location
	  */
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Address 1.
		@return Address line 1 for this location
	  */
	public String getAddress1 () 
	{
		return (String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Address 2.
		@param Address2 
		Address line 2 for this location
	  */
	public void setAddress2 (String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Address 2.
		@return Address line 2 for this location
	  */
	public String getAddress2 () 
	{
		return (String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt3.
		@param amt3 amt3	  */
	public void setamt3 (int amt3)
	{
		set_Value (COLUMNNAME_amt3, Integer.valueOf(amt3));
	}

	/** Get amt3.
		@return amt3	  */
	public int getamt3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_amt3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** AttributeValueType AD_Reference_ID=1000517 */
	public static final int ATTRIBUTEVALUETYPE_AD_Reference_ID=1000517;
	/** Compra = 1- Compra */
	public static final String ATTRIBUTEVALUETYPE_Compra = "1- Compra";
	/** Venta = 2- Venta */
	public static final String ATTRIBUTEVALUETYPE_Venta = "2- Venta";
	/** Set Attribute Value Type.
		@param AttributeValueType 
		Type of Attribute Value
	  */
	public void setAttributeValueType (String AttributeValueType)
	{

		set_Value (COLUMNNAME_AttributeValueType, AttributeValueType);
	}

	/** Get Attribute Value Type.
		@return Type of Attribute Value
	  */
	public String getAttributeValueType () 
	{
		return (String)get_Value(COLUMNNAME_AttributeValueType);
	}

	/** Set Cantidad.
		@param Cantidad Cantidad	  */
	public void setCantidad (BigDecimal Cantidad)
	{
		set_Value (COLUMNNAME_Cantidad, Cantidad);
	}

	/** Get Cantidad.
		@return Cantidad	  */
	public BigDecimal getCantidad () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cantidad);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_City getC_City() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getC_City_ID(), get_TrxName());	}

	/** Set City.
		@param C_City_ID 
		City
	  */
	public void setC_City_ID (int C_City_ID)
	{
		if (C_City_ID < 1) 
			set_Value (COLUMNNAME_C_City_ID, null);
		else 
			set_Value (COLUMNNAME_C_City_ID, Integer.valueOf(C_City_ID));
	}

	/** Get City.
		@return City
	  */
	public int getC_City_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_City_ID_2.
		@param C_City_ID_2 C_City_ID_2	  */
	public void setC_City_ID_2 (int C_City_ID_2)
	{
		set_Value (COLUMNNAME_C_City_ID_2, Integer.valueOf(C_City_ID_2));
	}

	/** Get C_City_ID_2.
		@return C_City_ID_2	  */
	public int getC_City_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
	}

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
    {
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getC_Region_ID(), get_TrxName());	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Region_ID_2.
		@param C_Region_ID_2 C_Region_ID_2	  */
	public void setC_Region_ID_2 (int C_Region_ID_2)
	{
		set_Value (COLUMNNAME_C_Region_ID_2, Integer.valueOf(C_Region_ID_2));
	}

	/** Get C_Region_ID_2.
		@return C_Region_ID_2	  */
	public int getC_Region_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	/** Set No_Constancia.
		@param No_Constancia No_Constancia	  */
	public void setNo_Constancia (int No_Constancia)
	{
		set_Value (COLUMNNAME_No_Constancia, Integer.valueOf(No_Constancia));
	}

	/** Get No_Constancia.
		@return No_Constancia	  */
	public int getNo_Constancia () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_No_Constancia);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set No_Factura.
		@param No_Factura No_Factura	  */
	public void setNo_Factura (int No_Factura)
	{
		set_Value (COLUMNNAME_No_Factura, Integer.valueOf(No_Factura));
	}

	/** Get No_Factura.
		@return No_Factura	  */
	public int getNo_Factura () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_No_Factura);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set No_Recibo.
		@param No_Recibo No_Recibo	  */
	public void setNo_Recibo (int No_Recibo)
	{
		set_Value (COLUMNNAME_No_Recibo, Integer.valueOf(No_Recibo));
	}

	/** Get No_Recibo.
		@return No_Recibo	  */
	public int getNo_Recibo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_No_Recibo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Price2.
		@param Price2 Price2	  */
	public void setPrice2 (BigDecimal Price2)
	{
		set_Value (COLUMNNAME_Price2, Price2);
	}

	/** Get Price2.
		@return Price2	  */
	public BigDecimal getPrice2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price.
		@param PriceEntered 
		Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Price.
		@return Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set total2.
		@param total2 total2	  */
	public void settotal2 (BigDecimal total2)
	{
		set_Value (COLUMNNAME_total2, total2);
	}

	/** Get total2.
		@return total2	  */
	public BigDecimal gettotal2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_total2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_BG_PackingMode getUY_BG_PackingMode() throws RuntimeException
    {
		return (I_UY_BG_PackingMode)MTable.get(getCtx(), I_UY_BG_PackingMode.Table_Name)
			.getPO(getUY_BG_PackingMode_ID(), get_TrxName());	}

	/** Set UY_BG_PackingMode.
		@param UY_BG_PackingMode_ID UY_BG_PackingMode	  */
	public void setUY_BG_PackingMode_ID (int UY_BG_PackingMode_ID)
	{
		if (UY_BG_PackingMode_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_PackingMode_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_PackingMode_ID, Integer.valueOf(UY_BG_PackingMode_ID));
	}

	/** Get UY_BG_PackingMode.
		@return UY_BG_PackingMode	  */
	public int getUY_BG_PackingMode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_PackingMode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_Renta getUY_BG_Renta() throws RuntimeException
    {
		return (I_UY_BG_Renta)MTable.get(getCtx(), I_UY_BG_Renta.Table_Name)
			.getPO(getUY_BG_Renta_ID(), get_TrxName());	}

	/** Set UY_BG_Renta.
		@param UY_BG_Renta_ID UY_BG_Renta	  */
	public void setUY_BG_Renta_ID (int UY_BG_Renta_ID)
	{
		if (UY_BG_Renta_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Renta_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Renta_ID, Integer.valueOf(UY_BG_Renta_ID));
	}

	/** Get UY_BG_Renta.
		@return UY_BG_Renta	  */
	public int getUY_BG_Renta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Renta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_SubCustomer getUY_BG_SubCustomer() throws RuntimeException
    {
		return (I_UY_BG_SubCustomer)MTable.get(getCtx(), I_UY_BG_SubCustomer.Table_Name)
			.getPO(getUY_BG_SubCustomer_ID(), get_TrxName());	}

	/** Set UY_BG_SubCustomer.
		@param UY_BG_SubCustomer_ID UY_BG_SubCustomer	  */
	public void setUY_BG_SubCustomer_ID (int UY_BG_SubCustomer_ID)
	{
		if (UY_BG_SubCustomer_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_SubCustomer_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_SubCustomer_ID, Integer.valueOf(UY_BG_SubCustomer_ID));
	}

	/** Get UY_BG_SubCustomer.
		@return UY_BG_SubCustomer	  */
	public int getUY_BG_SubCustomer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_SubCustomer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_Transaction getUY_BG_Transaction() throws RuntimeException
    {
		return (I_UY_BG_Transaction)MTable.get(getCtx(), I_UY_BG_Transaction.Table_Name)
			.getPO(getUY_BG_Transaction_ID(), get_TrxName());	}

	/** Set UY_BG_Transaction.
		@param UY_BG_Transaction_ID UY_BG_Transaction	  */
	public void setUY_BG_Transaction_ID (int UY_BG_Transaction_ID)
	{
		if (UY_BG_Transaction_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Transaction_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Transaction_ID, Integer.valueOf(UY_BG_Transaction_ID));
	}

	/** Get UY_BG_Transaction.
		@return UY_BG_Transaction	  */
	public int getUY_BG_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BG_TransProd.
		@param UY_BG_TransProd_ID UY_BG_TransProd	  */
	public void setUY_BG_TransProd_ID (int UY_BG_TransProd_ID)
	{
		if (UY_BG_TransProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_TransProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_TransProd_ID, Integer.valueOf(UY_BG_TransProd_ID));
	}

	/** Get UY_BG_TransProd.
		@return UY_BG_TransProd	  */
	public int getUY_BG_TransProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_TransProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}