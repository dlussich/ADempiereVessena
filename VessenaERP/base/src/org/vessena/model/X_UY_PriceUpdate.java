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

/** Generated Model for UY_PriceUpdate
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PriceUpdate extends PO implements I_UY_PriceUpdate, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151001L;

    /** Standard Constructor */
    public X_UY_PriceUpdate (Properties ctx, int UY_PriceUpdate_ID, String trxName)
    {
      super (ctx, UY_PriceUpdate_ID, trxName);
      /** if (UY_PriceUpdate_ID == 0)
        {
			setAmount (Env.ZERO);
			setC_Currency_ID (0);
			setC_Currency_ID_2 (0);
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
			setDocStatus (null);
			setDocumentNo (null);
			setM_PriceList_ID (0);
			setM_PriceList_ID_2 (0);
			setPercentage (Env.ZERO);
			setPricePrecision (0);
// 2
			setProcessed (false);
			setUY_PriceUpdate_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UY_PriceUpdate (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PriceUpdate[")
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

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_PriceList_ID_2.
		@param M_PriceList_ID_2 M_PriceList_ID_2	  */
	public void setM_PriceList_ID_2 (int M_PriceList_ID_2)
	{
		set_Value (COLUMNNAME_M_PriceList_ID_2, Integer.valueOf(M_PriceList_ID_2));
	}

	/** Get M_PriceList_ID_2.
		@return M_PriceList_ID_2	  */
	public int getM_PriceList_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList_Version)MTable.get(getCtx(), org.compiere.model.I_M_PriceList_Version.Table_Name)
			.getPO(getM_PriceList_Version_ID(), get_TrxName());	}

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

	/** Set Percentage.
		@param Percentage 
		Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Percentage.
		@return Percent of the entire amount
	  */
	public BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price Precision.
		@param PricePrecision 
		Precision (number of decimals) for the Price
	  */
	public void setPricePrecision (int PricePrecision)
	{
		set_Value (COLUMNNAME_PricePrecision, Integer.valueOf(PricePrecision));
	}

	/** Get Price Precision.
		@return Precision (number of decimals) for the Price
	  */
	public int getPricePrecision () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PricePrecision);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UY_Familia getUY_Familia() throws RuntimeException
    {
		return (I_UY_Familia)MTable.get(getCtx(), I_UY_Familia.Table_Name)
			.getPO(getUY_Familia_ID(), get_TrxName());	}

	/** Set UY_Familia.
		@param UY_Familia_ID UY_Familia	  */
	public void setUY_Familia_ID (int UY_Familia_ID)
	{
		if (UY_Familia_ID < 1) 
			set_Value (COLUMNNAME_UY_Familia_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Familia_ID, Integer.valueOf(UY_Familia_ID));
	}

	/** Get UY_Familia.
		@return UY_Familia	  */
	public int getUY_Familia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Familia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Linea_Negocio getUY_Linea_Negocio() throws RuntimeException
    {
		return (I_UY_Linea_Negocio)MTable.get(getCtx(), I_UY_Linea_Negocio.Table_Name)
			.getPO(getUY_Linea_Negocio_ID(), get_TrxName());	}

	/** Set UY_Linea_Negocio_ID.
		@param UY_Linea_Negocio_ID UY_Linea_Negocio_ID	  */
	public void setUY_Linea_Negocio_ID (int UY_Linea_Negocio_ID)
	{
		if (UY_Linea_Negocio_ID < 1) 
			set_Value (COLUMNNAME_UY_Linea_Negocio_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Linea_Negocio_ID, Integer.valueOf(UY_Linea_Negocio_ID));
	}

	/** Get UY_Linea_Negocio_ID.
		@return UY_Linea_Negocio_ID	  */
	public int getUY_Linea_Negocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Linea_Negocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PriceUpdate.
		@param UY_PriceUpdate_ID UY_PriceUpdate	  */
	public void setUY_PriceUpdate_ID (int UY_PriceUpdate_ID)
	{
		if (UY_PriceUpdate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PriceUpdate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PriceUpdate_ID, Integer.valueOf(UY_PriceUpdate_ID));
	}

	/** Get UY_PriceUpdate.
		@return UY_PriceUpdate	  */
	public int getUY_PriceUpdate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PriceUpdate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ProductGroup getUY_ProductGroup() throws RuntimeException
    {
		return (I_UY_ProductGroup)MTable.get(getCtx(), I_UY_ProductGroup.Table_Name)
			.getPO(getUY_ProductGroup_ID(), get_TrxName());	}

	/** Set UY_ProductGroup.
		@param UY_ProductGroup_ID UY_ProductGroup	  */
	public void setUY_ProductGroup_ID (int UY_ProductGroup_ID)
	{
		if (UY_ProductGroup_ID < 1) 
			set_Value (COLUMNNAME_UY_ProductGroup_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProductGroup_ID, Integer.valueOf(UY_ProductGroup_ID));
	}

	/** Get UY_ProductGroup.
		@return UY_ProductGroup	  */
	public int getUY_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProductGroup_ID);
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
}