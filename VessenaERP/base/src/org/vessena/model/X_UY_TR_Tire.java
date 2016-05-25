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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TR_Tire
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Tire extends PO implements I_UY_TR_Tire, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160225L;

    /** Standard Constructor */
    public X_UY_TR_Tire (Properties ctx, int UY_TR_Tire_ID, String trxName)
    {
      super (ctx, UY_TR_Tire_ID, trxName);
      /** if (UY_TR_Tire_ID == 0)
        {
			setC_Currency_ID (0);
// 100
			setIsAuxiliar (false);
// N
			setIsChanged (false);
// N
			setIsRotated (false);
// N
			setUpdateQty (false);
// N
			setUY_TR_Tire_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Tire (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Tire[")
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_BPartner getC_BPartner_I() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID_P(), get_TrxName());	}

	/** Set C_BPartner_ID_P.
		@param C_BPartner_ID_P C_BPartner_ID_P	  */
	public void setC_BPartner_ID_P (int C_BPartner_ID_P)
	{
		set_Value (COLUMNNAME_C_BPartner_ID_P, Integer.valueOf(C_BPartner_ID_P));
	}

	/** Get C_BPartner_ID_P.
		@return C_BPartner_ID_P	  */
	public int getC_BPartner_ID_P () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID_P);
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

	/** Set C_Currency_ID_2.
		@param C_Currency_ID_2 C_Currency_ID_2	  */
	public void setC_Currency_ID_2 (int C_Currency_ID_2)
	{
		set_Value (COLUMNNAME_C_Currency_ID_2, Integer.valueOf(C_Currency_ID_2));
	}

	/** Get C_Currency_ID_2.
		@return C_Currency_ID_2	  */
	public int getC_Currency_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

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

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateOperation.
		@param DateOperation DateOperation	  */
	public void setDateOperation (Timestamp DateOperation)
	{
		set_Value (COLUMNNAME_DateOperation, DateOperation);
	}

	/** Get DateOperation.
		@return DateOperation	  */
	public Timestamp getDateOperation () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOperation);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** EstadoActual AD_Reference_ID=1000371 */
	public static final int ESTADOACTUAL_AD_Reference_ID=1000371;
	/** NUEVO = nuevo */
	public static final String ESTADOACTUAL_NUEVO = "nuevo";
	/** RECAUCHUTADO = primerrecauchutado */
	public static final String ESTADOACTUAL_RECAUCHUTADO = "primerrecauchutado";
	/** BAJA = baja */
	public static final String ESTADOACTUAL_BAJA = "baja";
	/** Set EstadoActual.
		@param EstadoActual 
		EstadoActual
	  */
	public void setEstadoActual (String EstadoActual)
	{

		set_ValueNoCheck (COLUMNNAME_EstadoActual, EstadoActual);
	}

	/** Get EstadoActual.
		@return EstadoActual
	  */
	public String getEstadoActual () 
	{
		return (String)get_Value(COLUMNNAME_EstadoActual);
	}

	/** Set Info1.
		@param Info1 Info1	  */
	public void setInfo1 (String Info1)
	{
		throw new IllegalArgumentException ("Info1 is virtual column");	}

	/** Get Info1.
		@return Info1	  */
	public String getInfo1 () 
	{
		return (String)get_Value(COLUMNNAME_Info1);
	}

	/** Set IsAuxiliar.
		@param IsAuxiliar 
		IsAuxiliar
	  */
	public void setIsAuxiliar (boolean IsAuxiliar)
	{
		set_Value (COLUMNNAME_IsAuxiliar, Boolean.valueOf(IsAuxiliar));
	}

	/** Get IsAuxiliar.
		@return IsAuxiliar
	  */
	public boolean isAuxiliar () 
	{
		Object oo = get_Value(COLUMNNAME_IsAuxiliar);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsChanged.
		@param IsChanged IsChanged	  */
	public void setIsChanged (boolean IsChanged)
	{
		set_Value (COLUMNNAME_IsChanged, Boolean.valueOf(IsChanged));
	}

	/** Get IsChanged.
		@return IsChanged	  */
	public boolean isChanged () 
	{
		Object oo = get_Value(COLUMNNAME_IsChanged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRotated.
		@param IsRotated IsRotated	  */
	public void setIsRotated (boolean IsRotated)
	{
		set_Value (COLUMNNAME_IsRotated, Boolean.valueOf(IsRotated));
	}

	/** Get IsRotated.
		@return IsRotated	  */
	public boolean isRotated () 
	{
		Object oo = get_Value(COLUMNNAME_IsRotated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LocatorValue.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, Integer.valueOf(LocatorValue));
	}

	/** Get LocatorValue.
		@return Key of the Warehouse Locator
	  */
	public int getLocatorValue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LocatorValue);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
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

	/** Set Cost Price.
		@param PriceCost 
		Price per Unit of Measure including all indirect costs (Freight, etc.)
	  */
	public void setPriceCost (BigDecimal PriceCost)
	{
		set_Value (COLUMNNAME_PriceCost, PriceCost);
	}

	/** Get Cost Price.
		@return Price per Unit of Measure including all indirect costs (Freight, etc.)
	  */
	public BigDecimal getPriceCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceCost2.
		@param PriceCost2 PriceCost2	  */
	public void setPriceCost2 (BigDecimal PriceCost2)
	{
		set_Value (COLUMNNAME_PriceCost2, PriceCost2);
	}

	/** Get PriceCost2.
		@return PriceCost2	  */
	public BigDecimal getPriceCost2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceCost2);
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

	/** Set PurchaseDate.
		@param PurchaseDate 
		PurchaseDate
	  */
	public void setPurchaseDate (Timestamp PurchaseDate)
	{
		set_Value (COLUMNNAME_PurchaseDate, PurchaseDate);
	}

	/** Get PurchaseDate.
		@return PurchaseDate
	  */
	public Timestamp getPurchaseDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PurchaseDate);
	}

	/** Set QtyKm.
		@param QtyKm 
		QtyKm
	  */
	public void setQtyKm (int QtyKm)
	{
		set_Value (COLUMNNAME_QtyKm, Integer.valueOf(QtyKm));
	}

	/** Get QtyKm.
		@return QtyKm
	  */
	public int getQtyKm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKm2.
		@param QtyKm2 
		QtyKm2
	  */
	public void setQtyKm2 (int QtyKm2)
	{
		set_Value (COLUMNNAME_QtyKm2, Integer.valueOf(QtyKm2));
	}

	/** Get QtyKm2.
		@return QtyKm2
	  */
	public int getQtyKm2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKm3.
		@param QtyKm3 
		QtyKm3
	  */
	public void setQtyKm3 (int QtyKm3)
	{
		set_Value (COLUMNNAME_QtyKm3, Integer.valueOf(QtyKm3));
	}

	/** Get QtyKm3.
		@return QtyKm3
	  */
	public int getQtyKm3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKm4.
		@param QtyKm4 
		QtyKm4
	  */
	public void setQtyKm4 (int QtyKm4)
	{
		set_Value (COLUMNNAME_QtyKm4, Integer.valueOf(QtyKm4));
	}

	/** Get QtyKm4.
		@return QtyKm4
	  */
	public int getQtyKm4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm4);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKm5.
		@param QtyKm5 
		QtyKm5
	  */
	public void setQtyKm5 (int QtyKm5)
	{
		set_Value (COLUMNNAME_QtyKm5, Integer.valueOf(QtyKm5));
	}

	/** Get QtyKm5.
		@return QtyKm5
	  */
	public int getQtyKm5 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm5);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKm6.
		@param QtyKm6 
		QtyKm6
	  */
	public void setQtyKm6 (int QtyKm6)
	{
		set_Value (COLUMNNAME_QtyKm6, Integer.valueOf(QtyKm6));
	}

	/** Get QtyKm6.
		@return QtyKm6
	  */
	public int getQtyKm6 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm6);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKmLocate.
		@param QtyKmLocate QtyKmLocate	  */
	public void setQtyKmLocate (int QtyKmLocate)
	{
		set_Value (COLUMNNAME_QtyKmLocate, Integer.valueOf(QtyKmLocate));
	}

	/** Get QtyKmLocate.
		@return QtyKmLocate	  */
	public int getQtyKmLocate () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKmLocate);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKmRecorrido.
		@param QtyKmRecorrido QtyKmRecorrido	  */
	public void setQtyKmRecorrido (int QtyKmRecorrido)
	{
		set_Value (COLUMNNAME_QtyKmRecorrido, Integer.valueOf(QtyKmRecorrido));
	}

	/** Get QtyKmRecorrido.
		@return QtyKmRecorrido	  */
	public int getQtyKmRecorrido () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKmRecorrido);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKmTruckLocate.
		@param QtyKmTruckLocate QtyKmTruckLocate	  */
	public void setQtyKmTruckLocate (int QtyKmTruckLocate)
	{
		set_Value (COLUMNNAME_QtyKmTruckLocate, Integer.valueOf(QtyKmTruckLocate));
	}

	/** Get QtyKmTruckLocate.
		@return QtyKmTruckLocate	  */
	public int getQtyKmTruckLocate () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKmTruckLocate);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyRecauchutaje.
		@param QtyRecauchutaje 
		QtyRecauchutaje
	  */
	public void setQtyRecauchutaje (BigDecimal QtyRecauchutaje)
	{
		set_Value (COLUMNNAME_QtyRecauchutaje, QtyRecauchutaje);
	}

	/** Get QtyRecauchutaje.
		@return QtyRecauchutaje
	  */
	public BigDecimal getQtyRecauchutaje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRecauchutaje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** TireType AD_Reference_ID=1000370 */
	public static final int TIRETYPE_AD_Reference_ID=1000370;
	/** LISA = lisa */
	public static final String TIRETYPE_LISA = "lisa";
	/** TRACCION = traccion */
	public static final String TIRETYPE_TRACCION = "traccion";
	/** Set TireType.
		@param TireType 
		TireType
	  */
	public void setTireType (String TireType)
	{

		set_Value (COLUMNNAME_TireType, TireType);
	}

	/** Get TireType.
		@return TireType
	  */
	public String getTireType () 
	{
		return (String)get_Value(COLUMNNAME_TireType);
	}

	/** Set Update Quantities.
		@param UpdateQty Update Quantities	  */
	public void setUpdateQty (boolean UpdateQty)
	{
		set_Value (COLUMNNAME_UpdateQty, Boolean.valueOf(UpdateQty));
	}

	/** Get Update Quantities.
		@return Update Quantities	  */
	public boolean isUpdateQty () 
	{
		Object oo = get_Value(COLUMNNAME_UpdateQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_TR_Recauchutaje.
		@param UY_TR_Recauchutaje_ID UY_TR_Recauchutaje	  */
	public void setUY_TR_Recauchutaje_ID (int UY_TR_Recauchutaje_ID)
	{
		if (UY_TR_Recauchutaje_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Recauchutaje_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Recauchutaje_ID, Integer.valueOf(UY_TR_Recauchutaje_ID));
	}

	/** Get UY_TR_Recauchutaje.
		@return UY_TR_Recauchutaje	  */
	public int getUY_TR_Recauchutaje_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Recauchutaje_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Tire.
		@param UY_TR_Tire_ID UY_TR_Tire	  */
	public void setUY_TR_Tire_ID (int UY_TR_Tire_ID)
	{
		if (UY_TR_Tire_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Tire_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Tire_ID, Integer.valueOf(UY_TR_Tire_ID));
	}

	/** Get UY_TR_Tire.
		@return UY_TR_Tire	  */
	public int getUY_TR_Tire_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Tire_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMark getUY_TR_TireMark() throws RuntimeException
    {
		return (I_UY_TR_TireMark)MTable.get(getCtx(), I_UY_TR_TireMark.Table_Name)
			.getPO(getUY_TR_TireMark_ID(), get_TrxName());	}

	/** Set UY_TR_TireMark.
		@param UY_TR_TireMark_ID UY_TR_TireMark	  */
	public void setUY_TR_TireMark_ID (int UY_TR_TireMark_ID)
	{
		if (UY_TR_TireMark_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMark_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMark_ID, Integer.valueOf(UY_TR_TireMark_ID));
	}

	/** Get UY_TR_TireMark.
		@return UY_TR_TireMark	  */
	public int getUY_TR_TireMark_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMark_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMeasure getUY_TR_TireMeasure() throws RuntimeException
    {
		return (I_UY_TR_TireMeasure)MTable.get(getCtx(), I_UY_TR_TireMeasure.Table_Name)
			.getPO(getUY_TR_TireMeasure_ID(), get_TrxName());	}

	/** Set UY_TR_TireMeasure.
		@param UY_TR_TireMeasure_ID UY_TR_TireMeasure	  */
	public void setUY_TR_TireMeasure_ID (int UY_TR_TireMeasure_ID)
	{
		if (UY_TR_TireMeasure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMeasure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMeasure_ID, Integer.valueOf(UY_TR_TireMeasure_ID));
	}

	/** Get UY_TR_TireMeasure.
		@return UY_TR_TireMeasure	  */
	public int getUY_TR_TireMeasure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMeasure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireModel getUY_TR_TireModel() throws RuntimeException
    {
		return (I_UY_TR_TireModel)MTable.get(getCtx(), I_UY_TR_TireModel.Table_Name)
			.getPO(getUY_TR_TireModel_ID(), get_TrxName());	}

	/** Set UY_TR_TireModel.
		@param UY_TR_TireModel_ID UY_TR_TireModel	  */
	public void setUY_TR_TireModel_ID (int UY_TR_TireModel_ID)
	{
		if (UY_TR_TireModel_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireModel_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireModel_ID, Integer.valueOf(UY_TR_TireModel_ID));
	}

	/** Get UY_TR_TireModel.
		@return UY_TR_TireModel	  */
	public int getUY_TR_TireModel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireModel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMove getUY_TR_TireMove() throws RuntimeException
    {
		return (I_UY_TR_TireMove)MTable.get(getCtx(), I_UY_TR_TireMove.Table_Name)
			.getPO(getUY_TR_TireMove_ID(), get_TrxName());	}

	/** Set UY_TR_TireMove.
		@param UY_TR_TireMove_ID UY_TR_TireMove	  */
	public void setUY_TR_TireMove_ID (int UY_TR_TireMove_ID)
	{
		if (UY_TR_TireMove_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMove_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMove_ID, Integer.valueOf(UY_TR_TireMove_ID));
	}

	/** Get UY_TR_TireMove.
		@return UY_TR_TireMove	  */
	public int getUY_TR_TireMove_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMove_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException
    {
		return (I_UY_TR_Truck)MTable.get(getCtx(), I_UY_TR_Truck.Table_Name)
			.getPO(getUY_TR_Truck_ID(), get_TrxName());	}

	/** Set UY_TR_Truck.
		@param UY_TR_Truck_ID UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID)
	{
		if (UY_TR_Truck_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, Integer.valueOf(UY_TR_Truck_ID));
	}

	/** Get UY_TR_Truck.
		@return UY_TR_Truck	  */
	public int getUY_TR_Truck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUY_TR_Truck_ID()));
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