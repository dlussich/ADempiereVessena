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

/** Generated Model for UY_Molde_PresupAprobado
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Molde_PresupAprobado extends PO implements I_UY_Molde_PresupAprobado, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130527L;

    /** Standard Constructor */
    public X_UY_Molde_PresupAprobado (Properties ctx, int UY_Molde_PresupAprobado_ID, String trxName)
    {
      super (ctx, UY_Molde_PresupAprobado_ID, trxName);
      /** if (UY_Molde_PresupAprobado_ID == 0)
        {
			setAD_User_ID (0);
			setidReporte (null);
			setUY_Budget_ID (0);
			setUY_Molde_PresupAprobado_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Molde_PresupAprobado (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Molde_PresupAprobado[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set amtfacturado.
		@param amtfacturado amtfacturado	  */
	public void setamtfacturado (BigDecimal amtfacturado)
	{
		set_Value (COLUMNNAME_amtfacturado, amtfacturado);
	}

	/** Get amtfacturado.
		@return amtfacturado	  */
	public BigDecimal getamtfacturado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtfacturado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getBPartner_Parent() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getBPartner_Parent_ID(), get_TrxName());	}

	/** Set Partner Parent.
		@param BPartner_Parent_ID 
		Business Partner Parent
	  */
	public void setBPartner_Parent_ID (int BPartner_Parent_ID)
	{
		if (BPartner_Parent_ID < 1) 
			set_Value (COLUMNNAME_BPartner_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_BPartner_Parent_ID, Integer.valueOf(BPartner_Parent_ID));
	}

	/** Get Partner Parent.
		@return Business Partner Parent
	  */
	public int getBPartner_Parent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BPartner_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
    {
		return (org.compiere.model.I_C_Payment)MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateApproved.
		@param DateApproved DateApproved	  */
	public void setDateApproved (Timestamp DateApproved)
	{
		set_Value (COLUMNNAME_DateApproved, DateApproved);
	}

	/** Get DateApproved.
		@return DateApproved	  */
	public Timestamp getDateApproved () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateApproved);
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

	/** Set descripcion.
		@param descripcion descripcion	  */
	public void setdescripcion (String descripcion)
	{
		set_Value (COLUMNNAME_descripcion, descripcion);
	}

	/** Get descripcion.
		@return descripcion	  */
	public String getdescripcion () 
	{
		return (String)get_Value(COLUMNNAME_descripcion);
	}

	/** Set fechaentrega.
		@param fechaentrega fechaentrega	  */
	public void setfechaentrega (Timestamp fechaentrega)
	{
		set_Value (COLUMNNAME_fechaentrega, fechaentrega);
	}

	/** Get fechaentrega.
		@return fechaentrega	  */
	public Timestamp getfechaentrega () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechaentrega);
	}

	/** Set fechapresup.
		@param fechapresup fechapresup	  */
	public void setfechapresup (Timestamp fechapresup)
	{
		set_Value (COLUMNNAME_fechapresup, fechapresup);
	}

	/** Get fechapresup.
		@return fechapresup	  */
	public Timestamp getfechapresup () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechapresup);
	}

	/** Set Identificador Unico del Reporte.
		@param idReporte Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte)
	{
		set_Value (COLUMNNAME_idReporte, idReporte);
	}

	/** Get Identificador Unico del Reporte.
		@return Identificador Unico del Reporte	  */
	public String getidReporte () 
	{
		return (String)get_Value(COLUMNNAME_idReporte);
	}

	/** Set importeneto.
		@param importeneto importeneto	  */
	public void setimporteneto (BigDecimal importeneto)
	{
		set_Value (COLUMNNAME_importeneto, importeneto);
	}

	/** Get importeneto.
		@return importeneto	  */
	public BigDecimal getimporteneto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_importeneto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Invoiced.
		@param IsInvoiced 
		Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced)
	{
		set_Value (COLUMNNAME_IsInvoiced, Boolean.valueOf(IsInvoiced));
	}

	/** Get Invoiced.
		@return Is this invoiced?
	  */
	public boolean isInvoiced () 
	{
		Object oo = get_Value(COLUMNNAME_IsInvoiced);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Receipt.
		@param IsReceipt 
		This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	/** Get Receipt.
		@return This is a sales transaction (receipt)
	  */
	public boolean isReceipt () 
	{
		Object oo = get_Value(COLUMNNAME_IsReceipt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set iva.
		@param iva iva	  */
	public void setiva (BigDecimal iva)
	{
		set_Value (COLUMNNAME_iva, iva);
	}

	/** Get iva.
		@return iva	  */
	public BigDecimal getiva () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_iva);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set priceunit.
		@param priceunit priceunit	  */
	public void setpriceunit (BigDecimal priceunit)
	{
		set_Value (COLUMNNAME_priceunit, priceunit);
	}

	/** Get priceunit.
		@return priceunit	  */
	public BigDecimal getpriceunit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_priceunit);
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

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set serie.
		@param serie serie	  */
	public void setserie (String serie)
	{
		set_Value (COLUMNNAME_serie, serie);
	}

	/** Get serie.
		@return serie	  */
	public String getserie () 
	{
		return (String)get_Value(COLUMNNAME_serie);
	}

	/** Set Total Quantity.
		@param TotalQty 
		Total Quantity
	  */
	public void setTotalQty (BigDecimal TotalQty)
	{
		set_Value (COLUMNNAME_TotalQty, TotalQty);
	}

	/** Get Total Quantity.
		@return Total Quantity
	  */
	public BigDecimal getTotalQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_Budget getUY_Budget() throws RuntimeException
    {
		return (I_UY_Budget)MTable.get(getCtx(), I_UY_Budget.Table_Name)
			.getPO(getUY_Budget_ID(), get_TrxName());	}

	/** Set UY_Budget.
		@param UY_Budget_ID UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID)
	{
		if (UY_Budget_ID < 1) 
			set_Value (COLUMNNAME_UY_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Budget_ID, Integer.valueOf(UY_Budget_ID));
	}

	/** Get UY_Budget.
		@return UY_Budget	  */
	public int getUY_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BudgetLine.
		@param UY_BudgetLine_ID UY_BudgetLine	  */
	public void setUY_BudgetLine_ID (int UY_BudgetLine_ID)
	{
		if (UY_BudgetLine_ID < 1) 
			set_Value (COLUMNNAME_UY_BudgetLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BudgetLine_ID, Integer.valueOf(UY_BudgetLine_ID));
	}

	/** Get UY_BudgetLine.
		@return UY_BudgetLine	  */
	public int getUY_BudgetLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BudgetLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Molde_PresupAprobado.
		@param UY_Molde_PresupAprobado_ID UY_Molde_PresupAprobado	  */
	public void setUY_Molde_PresupAprobado_ID (int UY_Molde_PresupAprobado_ID)
	{
		if (UY_Molde_PresupAprobado_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_PresupAprobado_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_PresupAprobado_ID, Integer.valueOf(UY_Molde_PresupAprobado_ID));
	}

	/** Get UY_Molde_PresupAprobado.
		@return UY_Molde_PresupAprobado	  */
	public int getUY_Molde_PresupAprobado_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Molde_PresupAprobado_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}