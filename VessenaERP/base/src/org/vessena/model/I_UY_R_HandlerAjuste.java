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

/** Generated Interface for UY_R_HandlerAjuste
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_HandlerAjuste 
{

    /** TableName=UY_R_HandlerAjuste */
    public static final String Table_Name = "UY_R_HandlerAjuste";

    /** AD_Table_ID=1000516 */
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

    /** Column name AplicaIVA */
    public static final String COLUMNNAME_AplicaIVA = "AplicaIVA";

	/** Set AplicaIVA.
	  * AplicaI IVA
	  */
	public void setAplicaIVA (boolean AplicaIVA);

	/** Get AplicaIVA.
	  * AplicaI IVA
	  */
	public boolean isAplicaIVA();

    /** Column name C_Activity_ID_1 */
    public static final String COLUMNNAME_C_Activity_ID_1 = "C_Activity_ID_1";

	/** Set C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1);

	/** Get C_Activity_ID_1	  */
	public int getC_Activity_ID_1();

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

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException;

    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/** Set Rate.
	  * Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate);

	/** Get Rate.
	  * Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate();

    /** Column name DateAction */
    public static final String COLUMNNAME_DateAction = "DateAction";

	/** Set DateAction	  */
	public void setDateAction (Timestamp DateAction);

	/** Get DateAction	  */
	public Timestamp getDateAction();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DrCr */
    public static final String COLUMNNAME_DrCr = "DrCr";

	/** Set DrCr	  */
	public void setDrCr (String DrCr);

	/** Get DrCr	  */
	public String getDrCr();

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

    /** Column name IsConfirmed */
    public static final String COLUMNNAME_IsConfirmed = "IsConfirmed";

	/** Set Confirmed.
	  * Assignment is confirmed
	  */
	public void setIsConfirmed (boolean IsConfirmed);

	/** Get Confirmed.
	  * Assignment is confirmed
	  */
	public boolean isConfirmed();

    /** Column name IsRejected */
    public static final String COLUMNNAME_IsRejected = "IsRejected";

	/** Set IsRejected	  */
	public void setIsRejected (boolean IsRejected);

	/** Get IsRejected	  */
	public boolean isRejected();

    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/** Set Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt);

	/** Get Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt();

    /** Column name LineNetAmtAcct */
    public static final String COLUMNNAME_LineNetAmtAcct = "LineNetAmtAcct";

	/** Set LineNetAmtAcct	  */
	public void setLineNetAmtAcct (BigDecimal LineNetAmtAcct);

	/** Get LineNetAmtAcct	  */
	public BigDecimal getLineNetAmtAcct();

    /** Column name LineTotalAmt */
    public static final String COLUMNNAME_LineTotalAmt = "LineTotalAmt";

	/** Set Line Total.
	  * Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt);

	/** Get Line Total.
	  * Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt();

    /** Column name LineTotalAmtAcct */
    public static final String COLUMNNAME_LineTotalAmtAcct = "LineTotalAmtAcct";

	/** Set LineTotalAmtAcct	  */
	public void setLineTotalAmtAcct (BigDecimal LineTotalAmtAcct);

	/** Get LineTotalAmtAcct	  */
	public BigDecimal getLineTotalAmtAcct();

    /** Column name QtyQuote */
    public static final String COLUMNNAME_QtyQuote = "QtyQuote";

	/** Set QtyQuote	  */
	public void setQtyQuote (BigDecimal QtyQuote);

	/** Get QtyQuote	  */
	public BigDecimal getQtyQuote();

    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/** Set Tax Amount.
	  * Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt);

	/** Get Tax Amount.
	  * Tax Amount for a document
	  */
	public BigDecimal getTaxAmt();

    /** Column name TaxAmtAcct */
    public static final String COLUMNNAME_TaxAmtAcct = "TaxAmtAcct";

	/** Set TaxAmtAcct	  */
	public void setTaxAmtAcct (BigDecimal TaxAmtAcct);

	/** Get TaxAmtAcct	  */
	public BigDecimal getTaxAmtAcct();

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

    /** Column name UY_R_AjusteAction_ID */
    public static final String COLUMNNAME_UY_R_AjusteAction_ID = "UY_R_AjusteAction_ID";

	/** Set UY_R_AjusteAction	  */
	public void setUY_R_AjusteAction_ID (int UY_R_AjusteAction_ID);

	/** Get UY_R_AjusteAction	  */
	public int getUY_R_AjusteAction_ID();

	public I_UY_R_AjusteAction getUY_R_AjusteAction() throws RuntimeException;

    /** Column name UY_R_Ajuste_ID */
    public static final String COLUMNNAME_UY_R_Ajuste_ID = "UY_R_Ajuste_ID";

	/** Set UY_R_Ajuste	  */
	public void setUY_R_Ajuste_ID (int UY_R_Ajuste_ID);

	/** Get UY_R_Ajuste	  */
	public int getUY_R_Ajuste_ID();

	public I_UY_R_Ajuste getUY_R_Ajuste() throws RuntimeException;

    /** Column name UY_R_Gestion_ID */
    public static final String COLUMNNAME_UY_R_Gestion_ID = "UY_R_Gestion_ID";

	/** Set UY_R_Gestion	  */
	public void setUY_R_Gestion_ID (int UY_R_Gestion_ID);

	/** Get UY_R_Gestion	  */
	public int getUY_R_Gestion_ID();

	public I_UY_R_Gestion getUY_R_Gestion() throws RuntimeException;

    /** Column name UY_R_HandlerAjuste_ID */
    public static final String COLUMNNAME_UY_R_HandlerAjuste_ID = "UY_R_HandlerAjuste_ID";

	/** Set UY_R_HandlerAjuste	  */
	public void setUY_R_HandlerAjuste_ID (int UY_R_HandlerAjuste_ID);

	/** Get UY_R_HandlerAjuste	  */
	public int getUY_R_HandlerAjuste_ID();
}
