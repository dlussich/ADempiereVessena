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
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_TR_Tire
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Tire 
{

    /** TableName=UY_TR_Tire */
    public static final String Table_Name = "UY_TR_Tire";

    /** AD_Table_ID=1000726 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_ID_P */
    public static final String COLUMNNAME_C_BPartner_ID_P = "C_BPartner_ID_P";

	/** Set C_BPartner_ID_P	  */
	public void setC_BPartner_ID_P (int C_BPartner_ID_P);

	/** Get C_BPartner_ID_P	  */
	public int getC_BPartner_ID_P();

	public org.compiere.model.I_C_BPartner getC_BPartner_I() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_Currency_ID_2 */
    public static final String COLUMNNAME_C_Currency_ID_2 = "C_Currency_ID_2";

	/** Set C_Currency_ID_2	  */
	public void setC_Currency_ID_2 (int C_Currency_ID_2);

	/** Get C_Currency_ID_2	  */
	public int getC_Currency_ID_2();

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateOperation */
    public static final String COLUMNNAME_DateOperation = "DateOperation";

	/** Set DateOperation	  */
	public void setDateOperation (Timestamp DateOperation);

	/** Get DateOperation	  */
	public Timestamp getDateOperation();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name EstadoActual */
    public static final String COLUMNNAME_EstadoActual = "EstadoActual";

	/** Set EstadoActual.
	  * EstadoActual
	  */
	public void setEstadoActual (String EstadoActual);

	/** Get EstadoActual.
	  * EstadoActual
	  */
	public String getEstadoActual();

    /** Column name Info1 */
    public static final String COLUMNNAME_Info1 = "Info1";

	/** Set Info1	  */
	public void setInfo1 (String Info1);

	/** Get Info1	  */
	public String getInfo1();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsAuxiliar */
    public static final String COLUMNNAME_IsAuxiliar = "IsAuxiliar";

	/** Set IsAuxiliar.
	  * IsAuxiliar
	  */
	public void setIsAuxiliar (boolean IsAuxiliar);

	/** Get IsAuxiliar.
	  * IsAuxiliar
	  */
	public boolean isAuxiliar();

    /** Column name IsChanged */
    public static final String COLUMNNAME_IsChanged = "IsChanged";

	/** Set IsChanged	  */
	public void setIsChanged (boolean IsChanged);

	/** Get IsChanged	  */
	public boolean isChanged();

    /** Column name IsRotated */
    public static final String COLUMNNAME_IsRotated = "IsRotated";

	/** Set IsRotated	  */
	public void setIsRotated (boolean IsRotated);

	/** Get IsRotated	  */
	public boolean isRotated();

    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/** Set LocatorValue.
	  * Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue);

	/** Get LocatorValue.
	  * Key of the Warehouse Locator
	  */
	public int getLocatorValue();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/** Set Price.
	  * Price
	  */
	public void setPrice (BigDecimal Price);

	/** Get Price.
	  * Price
	  */
	public BigDecimal getPrice();

    /** Column name Price2 */
    public static final String COLUMNNAME_Price2 = "Price2";

	/** Set Price2	  */
	public void setPrice2 (BigDecimal Price2);

	/** Get Price2	  */
	public BigDecimal getPrice2();

    /** Column name PriceCost */
    public static final String COLUMNNAME_PriceCost = "PriceCost";

	/** Set Cost Price.
	  * Price per Unit of Measure including all indirect costs (Freight, etc.)
	  */
	public void setPriceCost (BigDecimal PriceCost);

	/** Get Cost Price.
	  * Price per Unit of Measure including all indirect costs (Freight, etc.)
	  */
	public BigDecimal getPriceCost();

    /** Column name PriceCost2 */
    public static final String COLUMNNAME_PriceCost2 = "PriceCost2";

	/** Set PriceCost2	  */
	public void setPriceCost2 (BigDecimal PriceCost2);

	/** Get PriceCost2	  */
	public BigDecimal getPriceCost2();

    /** Column name PriceCostFinal */
    public static final String COLUMNNAME_PriceCostFinal = "PriceCostFinal";

	/** Set PriceCostFinal	  */
	public void setPriceCostFinal (BigDecimal PriceCostFinal);

	/** Get PriceCostFinal	  */
	public BigDecimal getPriceCostFinal();

    /** Column name PurchaseDate */
    public static final String COLUMNNAME_PurchaseDate = "PurchaseDate";

	/** Set PurchaseDate.
	  * PurchaseDate
	  */
	public void setPurchaseDate (Timestamp PurchaseDate);

	/** Get PurchaseDate.
	  * PurchaseDate
	  */
	public Timestamp getPurchaseDate();

    /** Column name QtyKm */
    public static final String COLUMNNAME_QtyKm = "QtyKm";

	/** Set QtyKm.
	  * QtyKm
	  */
	public void setQtyKm (int QtyKm);

	/** Get QtyKm.
	  * QtyKm
	  */
	public int getQtyKm();

    /** Column name QtyKm2 */
    public static final String COLUMNNAME_QtyKm2 = "QtyKm2";

	/** Set QtyKm2.
	  * QtyKm2
	  */
	public void setQtyKm2 (int QtyKm2);

	/** Get QtyKm2.
	  * QtyKm2
	  */
	public int getQtyKm2();

    /** Column name QtyKm3 */
    public static final String COLUMNNAME_QtyKm3 = "QtyKm3";

	/** Set QtyKm3.
	  * QtyKm3
	  */
	public void setQtyKm3 (int QtyKm3);

	/** Get QtyKm3.
	  * QtyKm3
	  */
	public int getQtyKm3();

    /** Column name QtyKm4 */
    public static final String COLUMNNAME_QtyKm4 = "QtyKm4";

	/** Set QtyKm4.
	  * QtyKm4
	  */
	public void setQtyKm4 (int QtyKm4);

	/** Get QtyKm4.
	  * QtyKm4
	  */
	public int getQtyKm4();

    /** Column name QtyKm5 */
    public static final String COLUMNNAME_QtyKm5 = "QtyKm5";

	/** Set QtyKm5.
	  * QtyKm5
	  */
	public void setQtyKm5 (int QtyKm5);

	/** Get QtyKm5.
	  * QtyKm5
	  */
	public int getQtyKm5();

    /** Column name QtyKm6 */
    public static final String COLUMNNAME_QtyKm6 = "QtyKm6";

	/** Set QtyKm6.
	  * QtyKm6
	  */
	public void setQtyKm6 (int QtyKm6);

	/** Get QtyKm6.
	  * QtyKm6
	  */
	public int getQtyKm6();

    /** Column name QtyKmLocate */
    public static final String COLUMNNAME_QtyKmLocate = "QtyKmLocate";

	/** Set QtyKmLocate	  */
	public void setQtyKmLocate (int QtyKmLocate);

	/** Get QtyKmLocate	  */
	public int getQtyKmLocate();

    /** Column name QtyKmRecorrido */
    public static final String COLUMNNAME_QtyKmRecorrido = "QtyKmRecorrido";

	/** Set QtyKmRecorrido	  */
	public void setQtyKmRecorrido (int QtyKmRecorrido);

	/** Get QtyKmRecorrido	  */
	public int getQtyKmRecorrido();

    /** Column name QtyKmTruckLocate */
    public static final String COLUMNNAME_QtyKmTruckLocate = "QtyKmTruckLocate";

	/** Set QtyKmTruckLocate	  */
	public void setQtyKmTruckLocate (int QtyKmTruckLocate);

	/** Get QtyKmTruckLocate	  */
	public int getQtyKmTruckLocate();

    /** Column name QtyRecauchutaje */
    public static final String COLUMNNAME_QtyRecauchutaje = "QtyRecauchutaje";

	/** Set QtyRecauchutaje.
	  * QtyRecauchutaje
	  */
	public void setQtyRecauchutaje (BigDecimal QtyRecauchutaje);

	/** Get QtyRecauchutaje.
	  * QtyRecauchutaje
	  */
	public BigDecimal getQtyRecauchutaje();

    /** Column name TireType */
    public static final String COLUMNNAME_TireType = "TireType";

	/** Set TireType.
	  * TireType
	  */
	public void setTireType (String TireType);

	/** Get TireType.
	  * TireType
	  */
	public String getTireType();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UpdateQty */
    public static final String COLUMNNAME_UpdateQty = "UpdateQty";

	/** Set Update Quantities	  */
	public void setUpdateQty (boolean UpdateQty);

	/** Get Update Quantities	  */
	public boolean isUpdateQty();

    /** Column name UY_TR_Recauchutaje_ID */
    public static final String COLUMNNAME_UY_TR_Recauchutaje_ID = "UY_TR_Recauchutaje_ID";

	/** Set UY_TR_Recauchutaje	  */
	public void setUY_TR_Recauchutaje_ID (int UY_TR_Recauchutaje_ID);

	/** Get UY_TR_Recauchutaje	  */
	public int getUY_TR_Recauchutaje_ID();

    /** Column name UY_TR_Tire_ID */
    public static final String COLUMNNAME_UY_TR_Tire_ID = "UY_TR_Tire_ID";

	/** Set UY_TR_Tire	  */
	public void setUY_TR_Tire_ID (int UY_TR_Tire_ID);

	/** Get UY_TR_Tire	  */
	public int getUY_TR_Tire_ID();

    /** Column name UY_TR_TireMark_ID */
    public static final String COLUMNNAME_UY_TR_TireMark_ID = "UY_TR_TireMark_ID";

	/** Set UY_TR_TireMark	  */
	public void setUY_TR_TireMark_ID (int UY_TR_TireMark_ID);

	/** Get UY_TR_TireMark	  */
	public int getUY_TR_TireMark_ID();

	public I_UY_TR_TireMark getUY_TR_TireMark() throws RuntimeException;

    /** Column name UY_TR_TireMeasure_ID */
    public static final String COLUMNNAME_UY_TR_TireMeasure_ID = "UY_TR_TireMeasure_ID";

	/** Set UY_TR_TireMeasure	  */
	public void setUY_TR_TireMeasure_ID (int UY_TR_TireMeasure_ID);

	/** Get UY_TR_TireMeasure	  */
	public int getUY_TR_TireMeasure_ID();

	public I_UY_TR_TireMeasure getUY_TR_TireMeasure() throws RuntimeException;

    /** Column name UY_TR_TireModel_ID */
    public static final String COLUMNNAME_UY_TR_TireModel_ID = "UY_TR_TireModel_ID";

	/** Set UY_TR_TireModel	  */
	public void setUY_TR_TireModel_ID (int UY_TR_TireModel_ID);

	/** Get UY_TR_TireModel	  */
	public int getUY_TR_TireModel_ID();

	public I_UY_TR_TireModel getUY_TR_TireModel() throws RuntimeException;

    /** Column name UY_TR_TireMove_ID */
    public static final String COLUMNNAME_UY_TR_TireMove_ID = "UY_TR_TireMove_ID";

	/** Set UY_TR_TireMove	  */
	public void setUY_TR_TireMove_ID (int UY_TR_TireMove_ID);

	/** Get UY_TR_TireMove	  */
	public int getUY_TR_TireMove_ID();

	public I_UY_TR_TireMove getUY_TR_TireMove() throws RuntimeException;

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException;

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
