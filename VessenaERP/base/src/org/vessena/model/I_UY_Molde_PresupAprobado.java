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

/** Generated Interface for UY_Molde_PresupAprobado
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_Molde_PresupAprobado 
{

    /** TableName=UY_Molde_PresupAprobado */
    public static final String Table_Name = "UY_Molde_PresupAprobado";

    /** AD_Table_ID=1000361 */
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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name amtfacturado */
    public static final String COLUMNNAME_amtfacturado = "amtfacturado";

	/** Set amtfacturado	  */
	public void setamtfacturado (BigDecimal amtfacturado);

	/** Get amtfacturado	  */
	public BigDecimal getamtfacturado();

    /** Column name BPartner_Parent_ID */
    public static final String COLUMNNAME_BPartner_Parent_ID = "BPartner_Parent_ID";

	/** Set Partner Parent.
	  * Business Partner Parent
	  */
	public void setBPartner_Parent_ID (int BPartner_Parent_ID);

	/** Get Partner Parent.
	  * Business Partner Parent
	  */
	public int getBPartner_Parent_ID();

	public org.compiere.model.I_C_BPartner getBPartner_Parent() throws RuntimeException;

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException;

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

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Payment.
	  * Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Payment.
	  * Payment identifier
	  */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException;

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

    /** Column name DateApproved */
    public static final String COLUMNNAME_DateApproved = "DateApproved";

	/** Set DateApproved	  */
	public void setDateApproved (Timestamp DateApproved);

	/** Get DateApproved	  */
	public Timestamp getDateApproved();

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

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

    /** Column name descripcion */
    public static final String COLUMNNAME_descripcion = "descripcion";

	/** Set descripcion	  */
	public void setdescripcion (String descripcion);

	/** Get descripcion	  */
	public String getdescripcion();

    /** Column name fechaentrega */
    public static final String COLUMNNAME_fechaentrega = "fechaentrega";

	/** Set fechaentrega	  */
	public void setfechaentrega (Timestamp fechaentrega);

	/** Get fechaentrega	  */
	public Timestamp getfechaentrega();

    /** Column name fechapresup */
    public static final String COLUMNNAME_fechapresup = "fechapresup";

	/** Set fechapresup	  */
	public void setfechapresup (Timestamp fechapresup);

	/** Get fechapresup	  */
	public Timestamp getfechapresup();

    /** Column name idReporte */
    public static final String COLUMNNAME_idReporte = "idReporte";

	/** Set Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte);

	/** Get Identificador Unico del Reporte	  */
	public String getidReporte();

    /** Column name importeneto */
    public static final String COLUMNNAME_importeneto = "importeneto";

	/** Set importeneto	  */
	public void setimporteneto (BigDecimal importeneto);

	/** Get importeneto	  */
	public BigDecimal getimporteneto();

    /** Column name IsInvoiced */
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/** Set Invoiced.
	  * Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced);

	/** Get Invoiced.
	  * Is this invoiced?
	  */
	public boolean isInvoiced();

    /** Column name IsReceipt */
    public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/** Set Receipt.
	  * This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt);

	/** Get Receipt.
	  * This is a sales transaction (receipt)
	  */
	public boolean isReceipt();

    /** Column name iva */
    public static final String COLUMNNAME_iva = "iva";

	/** Set iva	  */
	public void setiva (BigDecimal iva);

	/** Get iva	  */
	public BigDecimal getiva();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/** Set Payment amount.
	  * Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt);

	/** Get Payment amount.
	  * Amount being paid
	  */
	public BigDecimal getPayAmt();

    /** Column name priceunit */
    public static final String COLUMNNAME_priceunit = "priceunit";

	/** Set priceunit	  */
	public void setpriceunit (BigDecimal priceunit);

	/** Get priceunit	  */
	public BigDecimal getpriceunit();

    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/** Set Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced);

	/** Get Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException;

    /** Column name serie */
    public static final String COLUMNNAME_serie = "serie";

	/** Set serie	  */
	public void setserie (String serie);

	/** Get serie	  */
	public String getserie();

    /** Column name TotalQty */
    public static final String COLUMNNAME_TotalQty = "TotalQty";

	/** Set Total Quantity.
	  * Total Quantity
	  */
	public void setTotalQty (BigDecimal TotalQty);

	/** Get Total Quantity.
	  * Total Quantity
	  */
	public BigDecimal getTotalQty();

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

    /** Column name UY_Budget_ID */
    public static final String COLUMNNAME_UY_Budget_ID = "UY_Budget_ID";

	/** Set UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID);

	/** Get UY_Budget	  */
	public int getUY_Budget_ID();

	public I_UY_Budget getUY_Budget() throws RuntimeException;

    /** Column name UY_BudgetLine_ID */
    public static final String COLUMNNAME_UY_BudgetLine_ID = "UY_BudgetLine_ID";

	/** Set UY_BudgetLine	  */
	public void setUY_BudgetLine_ID (int UY_BudgetLine_ID);

	/** Get UY_BudgetLine	  */
	public int getUY_BudgetLine_ID();

    /** Column name UY_Molde_PresupAprobado_ID */
    public static final String COLUMNNAME_UY_Molde_PresupAprobado_ID = "UY_Molde_PresupAprobado_ID";

	/** Set UY_Molde_PresupAprobado	  */
	public void setUY_Molde_PresupAprobado_ID (int UY_Molde_PresupAprobado_ID);

	/** Get UY_Molde_PresupAprobado	  */
	public int getUY_Molde_PresupAprobado_ID();
}
