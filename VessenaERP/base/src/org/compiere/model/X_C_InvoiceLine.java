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
import org.compiere.util.KeyNamePair;
import org.openup.model.I_UY_ManufLine;
import org.openup.model.I_UY_ManufOrder;

/** Generated Model for C_InvoiceLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_InvoiceLine extends PO implements I_C_InvoiceLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130609L;

    /** Standard Constructor */
    public X_C_InvoiceLine (Properties ctx, int C_InvoiceLine_ID, String trxName)
    {
      super (ctx, C_InvoiceLine_ID, trxName);
      /** if (C_InvoiceLine_ID == 0)
        {
			setC_Invoice_ID (0);
			setC_InvoiceLine_ID (0);
			setC_Tax_ID (0);
			setIsDescription (false);
// N
			setIsPrinted (true);
// Y
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM C_InvoiceLine WHERE C_Invoice_ID=@C_Invoice_ID@
			setLineNetAmt (Env.ZERO);
			setM_AttributeSetInstance_ID (0);
			setPriceActual (Env.ZERO);
			setPriceEntered (Env.ZERO);
			setPriceLimit (Env.ZERO);
			setPriceList (Env.ZERO);
			setProcessed (false);
			setQtyEntered (Env.ZERO);
// 1
			setQtyInvoiced (Env.ZERO);
// 1
        } */
    }

    /** Load Constructor */
    public X_C_InvoiceLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_InvoiceLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset_Group)MTable.get(getCtx(), org.compiere.model.I_A_Asset_Group.Table_Name)
			.getPO(getA_Asset_Group_ID(), get_TrxName());	}

	/** Set Asset Group.
		@param A_Asset_Group_ID 
		Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
	}

	/** Get Asset Group.
		@return Group of Assets
	  */
	public int getA_Asset_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** A_CapvsExp AD_Reference_ID=53277 */
	public static final int A_CAPVSEXP_AD_Reference_ID=53277;
	/** Capital = Cap */
	public static final String A_CAPVSEXP_Capital = "Cap";
	/** Expense = Exp */
	public static final String A_CAPVSEXP_Expense = "Exp";
	/** Set Capital vs Expense.
		@param A_CapvsExp Capital vs Expense	  */
	public void setA_CapvsExp (String A_CapvsExp)
	{

		set_Value (COLUMNNAME_A_CapvsExp, A_CapvsExp);
	}

	/** Get Capital vs Expense.
		@return Capital vs Expense	  */
	public String getA_CapvsExp () 
	{
		return (String)get_Value(COLUMNNAME_A_CapvsExp);
	}

	/** Set Asset Related?.
		@param A_CreateAsset Asset Related?	  */
	public void setA_CreateAsset (boolean A_CreateAsset)
	{
		set_Value (COLUMNNAME_A_CreateAsset, Boolean.valueOf(A_CreateAsset));
	}

	/** Get Asset Related?.
		@return Asset Related?	  */
	public boolean isA_CreateAsset () 
	{
		Object oo = get_Value(COLUMNNAME_A_CreateAsset);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param A_Processed Processed	  */
	public void setA_Processed (boolean A_Processed)
	{
		set_Value (COLUMNNAME_A_Processed, Boolean.valueOf(A_Processed));
	}

	/** Get Processed.
		@return Processed	  */
	public boolean isA_Processed () 
	{
		Object oo = get_Value(COLUMNNAME_A_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Activity_ID_1.
		@param C_Activity_ID_1 C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1)
	{
		set_Value (COLUMNNAME_C_Activity_ID_1, Integer.valueOf(C_Activity_ID_1));
	}

	/** Get C_Activity_ID_1.
		@return C_Activity_ID_1	  */
	public int getC_Activity_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Activity_ID_2.
		@param C_Activity_ID_2 C_Activity_ID_2	  */
	public void setC_Activity_ID_2 (int C_Activity_ID_2)
	{
		set_Value (COLUMNNAME_C_Activity_ID_2, Integer.valueOf(C_Activity_ID_2));
	}

	/** Get C_Activity_ID_2.
		@return C_Activity_ID_2	  */
	public int getC_Activity_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Activity_ID_3.
		@param C_Activity_ID_3 C_Activity_ID_3	  */
	public void setC_Activity_ID_3 (int C_Activity_ID_3)
	{
		set_Value (COLUMNNAME_C_Activity_ID_3, Integer.valueOf(C_Activity_ID_3));
	}

	/** Get C_Activity_ID_3.
		@return C_Activity_ID_3	  */
	public int getC_Activity_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
    {
		return (org.compiere.model.I_C_Campaign)MTable.get(getCtx(), org.compiere.model.I_C_Campaign.Table_Name)
			.getPO(getC_Campaign_ID(), get_TrxName());	}

	/** Set Campaign.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Campaign.
		@return Marketing Campaign
	  */
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Invoice getC_InvoiceFactoring() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_InvoiceFactoring_ID(), get_TrxName());	}

	/** Set C_InvoiceFactoring_ID.
		@param C_InvoiceFactoring_ID C_InvoiceFactoring_ID	  */
	public void setC_InvoiceFactoring_ID (int C_InvoiceFactoring_ID)
	{
		if (C_InvoiceFactoring_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceFactoring_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceFactoring_ID, Integer.valueOf(C_InvoiceFactoring_ID));
	}

	/** Get C_InvoiceFactoring_ID.
		@return C_InvoiceFactoring_ID	  */
	public int getC_InvoiceFactoring_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceFactoring_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_Invoice_ID()));
    }

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
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

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Payment getC_PaymentCanje() throws RuntimeException
    {
		return (org.compiere.model.I_C_Payment)MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
			.getPO(getC_PaymentCanje_ID(), get_TrxName());	}

	/** Set C_PaymentCanje_ID.
		@param C_PaymentCanje_ID C_PaymentCanje_ID	  */
	public void setC_PaymentCanje_ID (int C_PaymentCanje_ID)
	{
		if (C_PaymentCanje_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentCanje_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentCanje_ID, Integer.valueOf(C_PaymentCanje_ID));
	}

	/** Get C_PaymentCanje_ID.
		@return C_PaymentCanje_ID	  */
	public int getC_PaymentCanje_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentCanje_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Payment getC_PaymentFactoring() throws RuntimeException
    {
		return (org.compiere.model.I_C_Payment)MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
			.getPO(getC_PaymentFactoring_ID(), get_TrxName());	}

	/** Set C_PaymentFactoring_ID.
		@param C_PaymentFactoring_ID C_PaymentFactoring_ID	  */
	public void setC_PaymentFactoring_ID (int C_PaymentFactoring_ID)
	{
		if (C_PaymentFactoring_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentFactoring_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentFactoring_ID, Integer.valueOf(C_PaymentFactoring_ID));
	}

	/** Get C_PaymentFactoring_ID.
		@return C_PaymentFactoring_ID	  */
	public int getC_PaymentFactoring_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentFactoring_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
    {
		return (org.compiere.model.I_C_Project)MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase() throws RuntimeException
    {
		return (org.compiere.model.I_C_ProjectPhase)MTable.get(getCtx(), org.compiere.model.I_C_ProjectPhase.Table_Name)
			.getPO(getC_ProjectPhase_ID(), get_TrxName());	}

	/** Set Project Phase.
		@param C_ProjectPhase_ID 
		Phase of a Project
	  */
	public void setC_ProjectPhase_ID (int C_ProjectPhase_ID)
	{
		if (C_ProjectPhase_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectPhase_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectPhase_ID, Integer.valueOf(C_ProjectPhase_ID));
	}

	/** Get Project Phase.
		@return Phase of a Project
	  */
	public int getC_ProjectPhase_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectPhase_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ProjectTask getC_ProjectTask() throws RuntimeException
    {
		return (org.compiere.model.I_C_ProjectTask)MTable.get(getCtx(), org.compiere.model.I_C_ProjectTask.Table_Name)
			.getPO(getC_ProjectTask_ID(), get_TrxName());	}

	/** Set Project Task.
		@param C_ProjectTask_ID 
		Actual Project Task in a Phase
	  */
	public void setC_ProjectTask_ID (int C_ProjectTask_ID)
	{
		if (C_ProjectTask_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectTask_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectTask_ID, Integer.valueOf(C_ProjectTask_ID));
	}

	/** Get Project Task.
		@return Actual Project Task in a Phase
	  */
	public int getC_ProjectTask_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectTask_ID);
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

	/** Set Flat Discount %.
		@param FlatDiscount 
		Flat discount percentage 
	  */
	public void setFlatDiscount (BigDecimal FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

	/** Get Flat Discount %.
		@return Flat discount percentage 
	  */
	public BigDecimal getFlatDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Description Only.
		@param IsDescription 
		if true, the line is just description and no transaction
	  */
	public void setIsDescription (boolean IsDescription)
	{
		set_Value (COLUMNNAME_IsDescription, Boolean.valueOf(IsDescription));
	}

	/** Get Description Only.
		@return if true, the line is just description and no transaction
	  */
	public boolean isDescription () 
	{
		Object oo = get_Value(COLUMNNAME_IsDescription);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsProvisioned.
		@param IsProvisioned IsProvisioned	  */
	public void setIsProvisioned (boolean IsProvisioned)
	{
		set_Value (COLUMNNAME_IsProvisioned, Boolean.valueOf(IsProvisioned));
	}

	/** Get IsProvisioned.
		@return IsProvisioned	  */
	public boolean isProvisioned () 
	{
		Object oo = get_Value(COLUMNNAME_IsProvisioned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Line Total.
		@param LineTotalAmt 
		Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt)
	{
		set_Value (COLUMNNAME_LineTotalAmt, LineTotalAmt);
	}

	/** Get Line Total.
		@return Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineTotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
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

	public org.compiere.model.I_M_RMALine getM_RMALine() throws RuntimeException
    {
		return (org.compiere.model.I_M_RMALine)MTable.get(getCtx(), org.compiere.model.I_M_RMALine.Table_Name)
			.getPO(getM_RMALine_ID(), get_TrxName());	}

	/** Set RMA Line.
		@param M_RMALine_ID 
		Return Material Authorization Line
	  */
	public void setM_RMALine_ID (int M_RMALine_ID)
	{
		if (M_RMALine_ID < 1) 
			set_Value (COLUMNNAME_M_RMALine_ID, null);
		else 
			set_Value (COLUMNNAME_M_RMALine_ID, Integer.valueOf(M_RMALine_ID));
	}

	/** Get RMA Line.
		@return Return Material Authorization Line
	  */
	public int getM_RMALine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMALine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set num_budget.
		@param num_budget num_budget	  */
	public void setnum_budget (String num_budget)
	{
		set_Value (COLUMNNAME_num_budget, num_budget);
	}

	/** Get num_budget.
		@return num_budget	  */
	public String getnum_budget () 
	{
		return (String)get_Value(COLUMNNAME_num_budget);
	}

	/** Set Unit Price.
		@param PriceActual 
		Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_ValueNoCheck (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price 
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
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

	/** Set Limit Price.
		@param PriceLimit 
		Lowest price for a product
	  */
	public void setPriceLimit (BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Limit Price.
		@return Lowest price for a product
	  */
	public BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set PrintMark.
		@param PrintMark 
		Print mark
	  */
	public void setPrintMark (String PrintMark)
	{
		set_Value (COLUMNNAME_PrintMark, PrintMark);
	}

	/** Get PrintMark.
		@return Print mark
	  */
	public String getPrintMark () 
	{
		return (String)get_Value(COLUMNNAME_PrintMark);
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

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Referenced Invoice Line.
		@param Ref_InvoiceLine_ID Referenced Invoice Line	  */
	public void setRef_InvoiceLine_ID (int Ref_InvoiceLine_ID)
	{
		if (Ref_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_Ref_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_InvoiceLine_ID, Integer.valueOf(Ref_InvoiceLine_ID));
	}

	/** Get Referenced Invoice Line.
		@return Referenced Invoice Line	  */
	public int getRef_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Revenue Recognition Amt.
		@param RRAmt 
		Revenue Recognition Amount
	  */
	public void setRRAmt (BigDecimal RRAmt)
	{
		set_Value (COLUMNNAME_RRAmt, RRAmt);
	}

	/** Get Revenue Recognition Amt.
		@return Revenue Recognition Amount
	  */
	public BigDecimal getRRAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RRAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Revenue Recognition Start.
		@param RRStartDate 
		Revenue Recognition Start Date
	  */
	public void setRRStartDate (Timestamp RRStartDate)
	{
		set_Value (COLUMNNAME_RRStartDate, RRStartDate);
	}

	/** Get Revenue Recognition Start.
		@return Revenue Recognition Start Date
	  */
	public Timestamp getRRStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_RRStartDate);
	}

	/** Set Resource Assignment.
		@param S_ResourceAssignment_ID 
		Resource Assignment
	  */
	public void setS_ResourceAssignment_ID (int S_ResourceAssignment_ID)
	{
		if (S_ResourceAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ResourceAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ResourceAssignment_ID, Integer.valueOf(S_ResourceAssignment_ID));
	}

	/** Get Resource Assignment.
		@return Resource Assignment
	  */
	public int getS_ResourceAssignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_ResourceAssignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tax Amount.
		@param TaxAmt 
		Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getUser1_ID(), get_TrxName());	}

	/** Set User List 1.
		@param User1_ID 
		User defined list element #1
	  */
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get User List 1.
		@return User defined list element #1
	  */
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getUser2_ID(), get_TrxName());	}

	/** Set User List 2.
		@param User2_ID 
		User defined list element #2
	  */
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get User List 2.
		@return User defined list element #2
	  */
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_bonificaregla.
		@param uy_bonificaregla uy_bonificaregla	  */
	public void setuy_bonificaregla (BigDecimal uy_bonificaregla)
	{
		set_Value (COLUMNNAME_uy_bonificaregla, uy_bonificaregla);
	}

	/** Get uy_bonificaregla.
		@return uy_bonificaregla	  */
	public BigDecimal getuy_bonificaregla () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_bonificaregla);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_BonificaReglaUM.
		@param UY_BonificaReglaUM UY_BonificaReglaUM	  */
	public void setUY_BonificaReglaUM (BigDecimal UY_BonificaReglaUM)
	{
		set_Value (COLUMNNAME_UY_BonificaReglaUM, UY_BonificaReglaUM);
	}

	/** Get UY_BonificaReglaUM.
		@return UY_BonificaReglaUM	  */
	public BigDecimal getUY_BonificaReglaUM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_BonificaReglaUM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_cantidadpedido.
		@param uy_cantidadpedido uy_cantidadpedido	  */
	public void setuy_cantidadpedido (BigDecimal uy_cantidadpedido)
	{
		set_Value (COLUMNNAME_uy_cantidadpedido, uy_cantidadpedido);
	}

	/** Get uy_cantidadpedido.
		@return uy_cantidadpedido	  */
	public BigDecimal getuy_cantidadpedido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_cantidadpedido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_c_uom_id.
		@param uy_c_uom_id uy_c_uom_id	  */
	public void setuy_c_uom_id (int uy_c_uom_id)
	{
		set_Value (COLUMNNAME_uy_c_uom_id, Integer.valueOf(uy_c_uom_id));
	}

	/** Get uy_c_uom_id.
		@return uy_c_uom_id	  */
	public int getuy_c_uom_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_uom_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_EsBonificCruzada.
		@param UY_EsBonificCruzada UY_EsBonificCruzada	  */
	public void setUY_EsBonificCruzada (boolean UY_EsBonificCruzada)
	{
		set_Value (COLUMNNAME_UY_EsBonificCruzada, Boolean.valueOf(UY_EsBonificCruzada));
	}

	/** Get UY_EsBonificCruzada.
		@return UY_EsBonificCruzada	  */
	public boolean isUY_EsBonificCruzada () 
	{
		Object oo = get_Value(COLUMNNAME_UY_EsBonificCruzada);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_InvoiceAux_ID.
		@param UY_InvoiceAux_ID UY_InvoiceAux_ID	  */
	public void setUY_InvoiceAux_ID (int UY_InvoiceAux_ID)
	{
		if (UY_InvoiceAux_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceAux_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceAux_ID, Integer.valueOf(UY_InvoiceAux_ID));
	}

	/** Get UY_InvoiceAux_ID.
		@return UY_InvoiceAux_ID	  */
	public int getUY_InvoiceAux_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceAux_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_InvoiceLineAux_ID.
		@param UY_InvoiceLineAux_ID UY_InvoiceLineAux_ID	  */
	public void setUY_InvoiceLineAux_ID (int UY_InvoiceLineAux_ID)
	{
		if (UY_InvoiceLineAux_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceLineAux_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceLineAux_ID, Integer.valueOf(UY_InvoiceLineAux_ID));
	}

	/** Get UY_InvoiceLineAux_ID.
		@return UY_InvoiceLineAux_ID	  */
	public int getUY_InvoiceLineAux_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceLineAux_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ManufLine getUY_ManufLine() throws RuntimeException
    {
		return (I_UY_ManufLine)MTable.get(getCtx(), I_UY_ManufLine.Table_Name)
			.getPO(getUY_ManufLine_ID(), get_TrxName());	}

	/** Set UY_ManufLine.
		@param UY_ManufLine_ID UY_ManufLine	  */
	public void setUY_ManufLine_ID (int UY_ManufLine_ID)
	{
		if (UY_ManufLine_ID < 1) 
			set_Value (COLUMNNAME_UY_ManufLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ManufLine_ID, Integer.valueOf(UY_ManufLine_ID));
	}

	/** Get UY_ManufLine.
		@return UY_ManufLine	  */
	public int getUY_ManufLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ManufOrder getUY_ManufOrder() throws RuntimeException
    {
		return (I_UY_ManufOrder)MTable.get(getCtx(), I_UY_ManufOrder.Table_Name)
			.getPO(getUY_ManufOrder_ID(), get_TrxName());	}

	/** Set UY_ManufOrder.
		@param UY_ManufOrder_ID UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID)
	{
		if (UY_ManufOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, Integer.valueOf(UY_ManufOrder_ID));
	}

	/** Get UY_ManufOrder.
		@return UY_ManufOrder	  */
	public int getUY_ManufOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_printprice.
		@param uy_printprice 
		uy_printprice
	  */
	public void setuy_printprice (BigDecimal uy_printprice)
	{
		set_Value (COLUMNNAME_uy_printprice, uy_printprice);
	}

	/** Get uy_printprice.
		@return uy_printprice
	  */
	public BigDecimal getuy_printprice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_printprice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_promodiscount.
		@param uy_promodiscount uy_promodiscount	  */
	public void setuy_promodiscount (BigDecimal uy_promodiscount)
	{
		set_Value (COLUMNNAME_uy_promodiscount, uy_promodiscount);
	}

	/** Get uy_promodiscount.
		@return uy_promodiscount	  */
	public BigDecimal getuy_promodiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_promodiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_TieneBonificCruzada.
		@param UY_TieneBonificCruzada UY_TieneBonificCruzada	  */
	public void setUY_TieneBonificCruzada (boolean UY_TieneBonificCruzada)
	{
		set_Value (COLUMNNAME_UY_TieneBonificCruzada, Boolean.valueOf(UY_TieneBonificCruzada));
	}

	/** Get UY_TieneBonificCruzada.
		@return UY_TieneBonificCruzada	  */
	public boolean isUY_TieneBonificCruzada () 
	{
		Object oo = get_Value(COLUMNNAME_UY_TieneBonificCruzada);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}