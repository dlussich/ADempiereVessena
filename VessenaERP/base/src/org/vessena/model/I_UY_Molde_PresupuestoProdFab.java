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

/** Generated Interface for UY_Molde_PresupuestoProdFab
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_Molde_PresupuestoProdFab 
{

    /** TableName=UY_Molde_PresupuestoProdFab */
    public static final String Table_Name = "UY_Molde_PresupuestoProdFab";

    /** AD_Table_ID=1000357 */
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

    /** Column name amountqtydelivered */
    public static final String COLUMNNAME_amountqtydelivered = "amountqtydelivered";

	/** Set amountqtydelivered	  */
	public void setamountqtydelivered (BigDecimal amountqtydelivered);

	/** Get amountqtydelivered	  */
	public BigDecimal getamountqtydelivered();

    /** Column name amountqtypend */
    public static final String COLUMNNAME_amountqtypend = "amountqtypend";

	/** Set amountqtypend	  */
	public void setamountqtypend (BigDecimal amountqtypend);

	/** Get amountqtypend	  */
	public BigDecimal getamountqtypend();

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

    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/** Set Date Promised.
	  * Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised);

	/** Get Date Promised.
	  * Date Order was promised
	  */
	public Timestamp getDatePromised();

    /** Column name descripcion */
    public static final String COLUMNNAME_descripcion = "descripcion";

	/** Set descripcion	  */
	public void setdescripcion (String descripcion);

	/** Get descripcion	  */
	public String getdescripcion();

    /** Column name fecharemito */
    public static final String COLUMNNAME_fecharemito = "fecharemito";

	/** Set fecharemito	  */
	public void setfecharemito (Timestamp fecharemito);

	/** Get fecharemito	  */
	public Timestamp getfecharemito();

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

    /** Column name priceunit */
    public static final String COLUMNNAME_priceunit = "priceunit";

	/** Set priceunit	  */
	public void setpriceunit (BigDecimal priceunit);

	/** Get priceunit	  */
	public BigDecimal getpriceunit();

    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/** Set Delivered Quantity.
	  * Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered);

	/** Get Delivered Quantity.
	  * Delivered Quantity
	  */
	public BigDecimal getQtyDelivered();

    /** Column name qtydeliverypend */
    public static final String COLUMNNAME_qtydeliverypend = "qtydeliverypend";

	/** Set qtydeliverypend	  */
	public void setqtydeliverypend (BigDecimal qtydeliverypend);

	/** Get qtydeliverypend	  */
	public BigDecimal getqtydeliverypend();

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

    /** Column name UY_Budget_ID */
    public static final String COLUMNNAME_UY_Budget_ID = "UY_Budget_ID";

	/** Set UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID);

	/** Get UY_Budget	  */
	public int getUY_Budget_ID();

	public I_UY_Budget getUY_Budget() throws RuntimeException;

    /** Column name UY_BudgetDelivery_ID */
    public static final String COLUMNNAME_UY_BudgetDelivery_ID = "UY_BudgetDelivery_ID";

	/** Set UY_BudgetDelivery	  */
	public void setUY_BudgetDelivery_ID (int UY_BudgetDelivery_ID);

	/** Get UY_BudgetDelivery	  */
	public int getUY_BudgetDelivery_ID();

	public I_UY_BudgetDelivery getUY_BudgetDelivery() throws RuntimeException;

    /** Column name UY_BudgetLine_ID */
    public static final String COLUMNNAME_UY_BudgetLine_ID = "UY_BudgetLine_ID";

	/** Set UY_BudgetLine	  */
	public void setUY_BudgetLine_ID (int UY_BudgetLine_ID);

	/** Get UY_BudgetLine	  */
	public int getUY_BudgetLine_ID();

	public I_UY_BudgetLine getUY_BudgetLine() throws RuntimeException;

    /** Column name UY_Molde_PresupuestoProdFab_ID */
    public static final String COLUMNNAME_UY_Molde_PresupuestoProdFab_ID = "UY_Molde_PresupuestoProdFab_ID";

	/** Set UY_Molde_PresupuestoProdFab	  */
	public void setUY_Molde_PresupuestoProdFab_ID (int UY_Molde_PresupuestoProdFab_ID);

	/** Get UY_Molde_PresupuestoProdFab	  */
	public int getUY_Molde_PresupuestoProdFab_ID();
}
