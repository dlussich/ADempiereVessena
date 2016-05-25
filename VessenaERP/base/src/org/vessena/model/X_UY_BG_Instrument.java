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

/** Generated Model for UY_BG_Instrument
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_Instrument extends PO implements I_UY_BG_Instrument, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150728L;

    /** Standard Constructor */
    public X_UY_BG_Instrument (Properties ctx, int UY_BG_Instrument_ID, String trxName)
    {
      super (ctx, UY_BG_Instrument_ID, trxName);
      /** if (UY_BG_Instrument_ID == 0)
        {
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setUY_BG_Instrument_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_Instrument (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_Instrument[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Action AD_Reference_ID=1000537 */
	public static final int ACTION_AD_Reference_ID=1000537;
	/** CALL = CALL */
	public static final String ACTION_CALL = "CALL";
	/** PUT = PUT */
	public static final String ACTION_PUT = "PUT";
	/** Set Action.
		@param Action 
		Indicates the Action to be performed
	  */
	public void setAction (String Action)
	{

		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Action.
		@return Indicates the Action to be performed
	  */
	public String getAction () 
	{
		return (String)get_Value(COLUMNNAME_Action);
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Validation code.
		@param Code 
		Validation Code
	  */
	public void setCode (String Code)
	{
		set_Value (COLUMNNAME_Code, Code);
	}

	/** Get Validation code.
		@return Validation Code
	  */
	public String getCode () 
	{
		return (String)get_Value(COLUMNNAME_Code);
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Request = RQ */
	public static final String DOCACTION_Request = "RQ";
	/** Asign = AS */
	public static final String DOCACTION_Asign = "AS";
	/** Pick = PK */
	public static final String DOCACTION_Pick = "PK";
	/** Recive = RV */
	public static final String DOCACTION_Recive = "RV";
	/** Apply = AY */
	public static final String DOCACTION_Apply = "AY";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Asigned = AS */
	public static final String DOCSTATUS_Asigned = "AS";
	/** Requested = RQ */
	public static final String DOCSTATUS_Requested = "RQ";
	/** Recived = RV */
	public static final String DOCSTATUS_Recived = "RV";
	/** Picking = PK */
	public static final String DOCSTATUS_Picking = "PK";
	/** Applied = AY */
	public static final String DOCSTATUS_Applied = "AY";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name 2.
		@param Name2 
		Additional Name
	  */
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
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

	/** Set PO Price.
		@param PricePO 
		Indico el monto de Precio de compra acumulado
	  */
	public void setPricePO (BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	/** Get PO Price.
		@return Indico el monto de Precio de compra acumulado
	  */
	public BigDecimal getPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceSales.
		@param PriceSales 
		Campor acumula precio de venta
	  */
	public void setPriceSales (BigDecimal PriceSales)
	{
		set_Value (COLUMNNAME_PriceSales, PriceSales);
	}

	/** Get PriceSales.
		@return Campor acumula precio de venta
	  */
	public BigDecimal getPriceSales () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceSales);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyPO.
		@param QtyPO 
		Indico cantidad de Volumen de compra
	  */
	public void setQtyPO (BigDecimal QtyPO)
	{
		set_Value (COLUMNNAME_QtyPO, QtyPO);
	}

	/** Get QtyPO.
		@return Indico cantidad de Volumen de compra
	  */
	public BigDecimal getQtyPO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtySales.
		@param QtySales 
		Indica cantidad de volumen de venta
	  */
	public void setQtySales (BigDecimal QtySales)
	{
		set_Value (COLUMNNAME_QtySales, QtySales);
	}

	/** Get QtySales.
		@return Indica cantidad de volumen de venta
	  */
	public BigDecimal getQtySales () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySales);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Type AD_Reference_ID=1000533 */
	public static final int TYPE_AD_Reference_ID=1000533;
	/** FORWARD = FORWARD */
	public static final String TYPE_FORWARD = "FORWARD";
	/** OPCIONES = OPCIONES */
	public static final String TYPE_OPCIONES = "OPCIONES";
	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}

	/** Set UY_BG_Instrument.
		@param UY_BG_Instrument_ID UY_BG_Instrument	  */
	public void setUY_BG_Instrument_ID (int UY_BG_Instrument_ID)
	{
		if (UY_BG_Instrument_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_Instrument_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_Instrument_ID, Integer.valueOf(UY_BG_Instrument_ID));
	}

	/** Get UY_BG_Instrument.
		@return UY_BG_Instrument	  */
	public int getUY_BG_Instrument_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Instrument_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_MarketType getUY_BG_MarketType() throws RuntimeException
    {
		return (I_UY_BG_MarketType)MTable.get(getCtx(), I_UY_BG_MarketType.Table_Name)
			.getPO(getUY_BG_MarketType_ID(), get_TrxName());	}

	/** Set UY_BG_MarketType.
		@param UY_BG_MarketType_ID UY_BG_MarketType	  */
	public void setUY_BG_MarketType_ID (int UY_BG_MarketType_ID)
	{
		if (UY_BG_MarketType_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_MarketType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_MarketType_ID, Integer.valueOf(UY_BG_MarketType_ID));
	}

	/** Get UY_BG_MarketType.
		@return UY_BG_MarketType	  */
	public int getUY_BG_MarketType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_MarketType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UY_BG_Quality getUY_BG_Quality() throws RuntimeException
    {
		return (I_UY_BG_Quality)MTable.get(getCtx(), I_UY_BG_Quality.Table_Name)
			.getPO(getUY_BG_Quality_ID(), get_TrxName());	}

	/** Set UY_BG_Quality.
		@param UY_BG_Quality_ID UY_BG_Quality	  */
	public void setUY_BG_Quality_ID (int UY_BG_Quality_ID)
	{
		if (UY_BG_Quality_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Quality_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Quality_ID, Integer.valueOf(UY_BG_Quality_ID));
	}

	/** Get UY_BG_Quality.
		@return UY_BG_Quality	  */
	public int getUY_BG_Quality_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Quality_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_Retention getUY_BG_Retention() throws RuntimeException
    {
		return (I_UY_BG_Retention)MTable.get(getCtx(), I_UY_BG_Retention.Table_Name)
			.getPO(getUY_BG_Retention_ID(), get_TrxName());	}

	/** Set UY_BG_Retentio.
		@param UY_BG_Retention_ID UY_BG_Retentio	  */
	public void setUY_BG_Retention_ID (int UY_BG_Retention_ID)
	{
		if (UY_BG_Retention_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Retention_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Retention_ID, Integer.valueOf(UY_BG_Retention_ID));
	}

	/** Get UY_BG_Retentio.
		@return UY_BG_Retentio	  */
	public int getUY_BG_Retention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Retention_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}