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

/** Generated Model for UY_BG_Offer
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_Offer extends PO implements I_UY_BG_Offer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150807L;

    /** Standard Constructor */
    public X_UY_BG_Offer (Properties ctx, int UY_BG_Offer_ID, String trxName)
    {
      super (ctx, UY_BG_Offer_ID, trxName);
      /** if (UY_BG_Offer_ID == 0)
        {
			setDocAction (null);
// CO
			setIsAll (false);
// N
			setisMatched (false);
// N
			setUY_BG_Offer_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_Offer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_Offer[")
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

	/** BuySell AD_Reference_ID=1000527 */
	public static final int BUYSELL_AD_Reference_ID=1000527;
	/** COMPRA = COMPRA */
	public static final String BUYSELL_COMPRA = "COMPRA";
	/** VENTA = VENTA */
	public static final String BUYSELL_VENTA = "VENTA";
	/** Set BuySell.
		@param BuySell 
		List document details
	  */
	public void setBuySell (String BuySell)
	{

		set_Value (COLUMNNAME_BuySell, BuySell);
	}

	/** Get BuySell.
		@return List document details
	  */
	public String getBuySell () 
	{
		return (String)get_Value(COLUMNNAME_BuySell);
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

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set IsAll.
		@param IsAll 
		Indica si la offerta forward aplica Todo o nada
	  */
	public void setIsAll (boolean IsAll)
	{
		set_Value (COLUMNNAME_IsAll, Boolean.valueOf(IsAll));
	}

	/** Get IsAll.
		@return Indica si la offerta forward aplica Todo o nada
	  */
	public boolean isAll () 
	{
		Object oo = get_Value(COLUMNNAME_IsAll);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isMatched.
		@param isMatched isMatched	  */
	public void setisMatched (boolean isMatched)
	{
		set_Value (COLUMNNAME_isMatched, Boolean.valueOf(isMatched));
	}

	/** Get isMatched.
		@return isMatched	  */
	public boolean isMatched () 
	{
		Object oo = get_Value(COLUMNNAME_isMatched);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPanic.
		@param IsPanic 
		Se indica si hubo accion de panico
	  */
	public void setIsPanic (boolean IsPanic)
	{
		set_Value (COLUMNNAME_IsPanic, Boolean.valueOf(IsPanic));
	}

	/** Get IsPanic.
		@return Se indica si hubo accion de panico
	  */
	public boolean isPanic () 
	{
		Object oo = get_Value(COLUMNNAME_IsPanic);
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

	public I_UY_BG_Bursa getUY_BG_Bursa() throws RuntimeException
    {
		return (I_UY_BG_Bursa)MTable.get(getCtx(), I_UY_BG_Bursa.Table_Name)
			.getPO(getUY_BG_Bursa_ID(), get_TrxName());	}

	/** Set UY_BG_Bursa.
		@param UY_BG_Bursa_ID UY_BG_Bursa	  */
	public void setUY_BG_Bursa_ID (int UY_BG_Bursa_ID)
	{
		if (UY_BG_Bursa_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Bursa_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Bursa_ID, Integer.valueOf(UY_BG_Bursa_ID));
	}

	/** Get UY_BG_Bursa.
		@return UY_BG_Bursa	  */
	public int getUY_BG_Bursa_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Bursa_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_Bursa getUY_BG_Bursa_I() throws RuntimeException
    {
		return (I_UY_BG_Bursa)MTable.get(getCtx(), I_UY_BG_Bursa.Table_Name)
			.getPO(getUY_BG_Bursa_ID_2(), get_TrxName());	}

	/** Set UY_BG_Bursa_ID_2.
		@param UY_BG_Bursa_ID_2 
		Bolsa contraparte
	  */
	public void setUY_BG_Bursa_ID_2 (int UY_BG_Bursa_ID_2)
	{
		set_Value (COLUMNNAME_UY_BG_Bursa_ID_2, Integer.valueOf(UY_BG_Bursa_ID_2));
	}

	/** Get UY_BG_Bursa_ID_2.
		@return Bolsa contraparte
	  */
	public int getUY_BG_Bursa_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Bursa_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_Instrument getUY_BG_Instrument() throws RuntimeException
    {
		return (I_UY_BG_Instrument)MTable.get(getCtx(), I_UY_BG_Instrument.Table_Name)
			.getPO(getUY_BG_Instrument_ID(), get_TrxName());	}

	/** Set UY_BG_Instrument.
		@param UY_BG_Instrument_ID UY_BG_Instrument	  */
	public void setUY_BG_Instrument_ID (int UY_BG_Instrument_ID)
	{
		if (UY_BG_Instrument_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Instrument_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Instrument_ID, Integer.valueOf(UY_BG_Instrument_ID));
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

	/** Set UY_BG_Offer.
		@param UY_BG_Offer_ID UY_BG_Offer	  */
	public void setUY_BG_Offer_ID (int UY_BG_Offer_ID)
	{
		if (UY_BG_Offer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_Offer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_Offer_ID, Integer.valueOf(UY_BG_Offer_ID));
	}

	/** Get UY_BG_Offer.
		@return UY_BG_Offer	  */
	public int getUY_BG_Offer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Offer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BG_Offer_ID_2.
		@param UY_BG_Offer_ID_2 UY_BG_Offer_ID_2	  */
	public void setUY_BG_Offer_ID_2 (int UY_BG_Offer_ID_2)
	{
		set_Value (COLUMNNAME_UY_BG_Offer_ID_2, Integer.valueOf(UY_BG_Offer_ID_2));
	}

	/** Get UY_BG_Offer_ID_2.
		@return UY_BG_Offer_ID_2	  */
	public int getUY_BG_Offer_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Offer_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_TypeOffers getUY_BG_TypeOffers() throws RuntimeException
    {
		return (I_UY_BG_TypeOffers)MTable.get(getCtx(), I_UY_BG_TypeOffers.Table_Name)
			.getPO(getUY_BG_TypeOffers_ID(), get_TrxName());	}

	/** Set UY_BG_TypeOffers.
		@param UY_BG_TypeOffers_ID UY_BG_TypeOffers	  */
	public void setUY_BG_TypeOffers_ID (int UY_BG_TypeOffers_ID)
	{
		if (UY_BG_TypeOffers_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_TypeOffers_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_TypeOffers_ID, Integer.valueOf(UY_BG_TypeOffers_ID));
	}

	/** Get UY_BG_TypeOffers.
		@return UY_BG_TypeOffers	  */
	public int getUY_BG_TypeOffers_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_TypeOffers_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_TypeOperation getUY_BG_TypeOperation() throws RuntimeException
    {
		return (I_UY_BG_TypeOperation)MTable.get(getCtx(), I_UY_BG_TypeOperation.Table_Name)
			.getPO(getUY_BG_TypeOperation_ID(), get_TrxName());	}

	/** Set UY_BG_TypeOperatio.
		@param UY_BG_TypeOperation_ID UY_BG_TypeOperatio	  */
	public void setUY_BG_TypeOperation_ID (int UY_BG_TypeOperation_ID)
	{
		if (UY_BG_TypeOperation_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_TypeOperation_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_TypeOperation_ID, Integer.valueOf(UY_BG_TypeOperation_ID));
	}

	/** Get UY_BG_TypeOperatio.
		@return UY_BG_TypeOperatio	  */
	public int getUY_BG_TypeOperation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_TypeOperation_ID);
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

	/** Set VolumeBase.
		@param VolumeBase 
		Volumen base 
	  */
	public void setVolumeBase (BigDecimal VolumeBase)
	{
		set_Value (COLUMNNAME_VolumeBase, VolumeBase);
	}

	/** Get VolumeBase.
		@return Volumen base 
	  */
	public BigDecimal getVolumeBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_VolumeBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}