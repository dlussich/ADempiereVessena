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

/** Generated Interface for UY_TR_ClearingLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_ClearingLine 
{

    /** TableName=UY_TR_ClearingLine */
    public static final String Table_Name = "UY_TR_ClearingLine";

    /** AD_Table_ID=1000847 */
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

    /** Column name Amount2 */
    public static final String COLUMNNAME_Amount2 = "Amount2";

	/** Set Amount2	  */
	public void setAmount2 (BigDecimal Amount2);

	/** Get Amount2	  */
	public BigDecimal getAmount2();

    /** Column name Amount3 */
    public static final String COLUMNNAME_Amount3 = "Amount3";

	/** Set Amount3	  */
	public void setAmount3 (BigDecimal Amount3);

	/** Get Amount3	  */
	public BigDecimal getAmount3();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Bank Account.
	  * Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Bank Account.
	  * Account at the Bank
	  */
	public int getC_BankAccount_ID();

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException;

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

    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/** Set Difference.
	  * Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt);

	/** Get Difference.
	  * Difference Amount
	  */
	public BigDecimal getDifferenceAmt();

    /** Column name ExpenseAmt */
    public static final String COLUMNNAME_ExpenseAmt = "ExpenseAmt";

	/** Set Expense Amount.
	  * Amount for this expense
	  */
	public void setExpenseAmt (BigDecimal ExpenseAmt);

	/** Get Expense Amount.
	  * Amount for this expense
	  */
	public BigDecimal getExpenseAmt();

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

    /** Column name montopesos */
    public static final String COLUMNNAME_montopesos = "montopesos";

	/** Set montopesos	  */
	public void setmontopesos (BigDecimal montopesos);

	/** Get montopesos	  */
	public BigDecimal getmontopesos();

    /** Column name SaldoAnterior */
    public static final String COLUMNNAME_SaldoAnterior = "SaldoAnterior";

	/** Set SaldoAnterior.
	  * SaldoAnterior
	  */
	public void setSaldoAnterior (BigDecimal SaldoAnterior);

	/** Get SaldoAnterior.
	  * SaldoAnterior
	  */
	public BigDecimal getSaldoAnterior();

    /** Column name SaldoFinal */
    public static final String COLUMNNAME_SaldoFinal = "SaldoFinal";

	/** Set SaldoFinal	  */
	public void setSaldoFinal (BigDecimal SaldoFinal);

	/** Get SaldoFinal	  */
	public BigDecimal getSaldoFinal();

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

    /** Column name UY_TR_Clearing_ID */
    public static final String COLUMNNAME_UY_TR_Clearing_ID = "UY_TR_Clearing_ID";

	/** Set UY_TR_Clearing	  */
	public void setUY_TR_Clearing_ID (int UY_TR_Clearing_ID);

	/** Get UY_TR_Clearing	  */
	public int getUY_TR_Clearing_ID();

	public I_UY_TR_Clearing getUY_TR_Clearing() throws RuntimeException;

    /** Column name UY_TR_ClearingLine_ID */
    public static final String COLUMNNAME_UY_TR_ClearingLine_ID = "UY_TR_ClearingLine_ID";

	/** Set UY_TR_ClearingLine	  */
	public void setUY_TR_ClearingLine_ID (int UY_TR_ClearingLine_ID);

	/** Get UY_TR_ClearingLine	  */
	public int getUY_TR_ClearingLine_ID();
}
