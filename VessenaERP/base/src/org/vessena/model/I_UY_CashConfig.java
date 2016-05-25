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

/** Generated Interface for UY_CashConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_CashConfig 
{

    /** TableName=UY_CashConfig */
    public static final String Table_Name = "UY_CashConfig";

    /** AD_Table_ID=1000938 */
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

    /** Column name CantBilletes */
    public static final String COLUMNNAME_CantBilletes = "CantBilletes";

	/** Set CantBilletes	  */
	public void setCantBilletes (int CantBilletes);

	/** Get CantBilletes	  */
	public int getCantBilletes();

    /** Column name CantTickets */
    public static final String COLUMNNAME_CantTickets = "CantTickets";

	/** Set CantTickets	  */
	public void setCantTickets (int CantTickets);

	/** Get CantTickets	  */
	public int getCantTickets();

    /** Column name CashCountCashier_Acct */
    public static final String COLUMNNAME_CashCountCashier_Acct = "CashCountCashier_Acct";

	/** Set CashCountCashier_Acct	  */
	public void setCashCountCashier_Acct (int CashCountCashier_Acct);

	/** Get CashCountCashier_Acct	  */
	public int getCashCountCashier_Acct();

	public I_C_ValidCombination getCashCountCashier_A() throws RuntimeException;

    /** Column name CashCountCredit_Acct */
    public static final String COLUMNNAME_CashCountCredit_Acct = "CashCountCredit_Acct";

	/** Set CashCountCredit_Acct	  */
	public void setCashCountCredit_Acct (int CashCountCredit_Acct);

	/** Get CashCountCredit_Acct	  */
	public int getCashCountCredit_Acct();

	public I_C_ValidCombination getCashCountCredit_A() throws RuntimeException;

    /** Column name CashCountCredit_Acct_1 */
    public static final String COLUMNNAME_CashCountCredit_Acct_1 = "CashCountCredit_Acct_1";

	/** Set CashCountCredit_Acct_1	  */
	public void setCashCountCredit_Acct_1 (int CashCountCredit_Acct_1);

	/** Get CashCountCredit_Acct_1	  */
	public int getCashCountCredit_Acct_1();

	public I_C_ValidCombination getCashCountCredit_Acc() throws RuntimeException;

    /** Column name CashCountDiff_Acct */
    public static final String COLUMNNAME_CashCountDiff_Acct = "CashCountDiff_Acct";

	/** Set CashCountDiff_Acct	  */
	public void setCashCountDiff_Acct (int CashCountDiff_Acct);

	/** Get CashCountDiff_Acct	  */
	public int getCashCountDiff_Acct();

	public I_C_ValidCombination getCashCountDiff_A() throws RuntimeException;

    /** Column name CashCountDiff_Acct_1 */
    public static final String COLUMNNAME_CashCountDiff_Acct_1 = "CashCountDiff_Acct_1";

	/** Set CashCountDiff_Acct_1	  */
	public void setCashCountDiff_Acct_1 (int CashCountDiff_Acct_1);

	/** Get CashCountDiff_Acct_1	  */
	public int getCashCountDiff_Acct_1();

	public I_C_ValidCombination getCashCountDiff_Acc() throws RuntimeException;

    /** Column name CashCountEnvase_Acct */
    public static final String COLUMNNAME_CashCountEnvase_Acct = "CashCountEnvase_Acct";

	/** Set CashCountEnvase_Acct	  */
	public void setCashCountEnvase_Acct (int CashCountEnvase_Acct);

	/** Get CashCountEnvase_Acct	  */
	public int getCashCountEnvase_Acct();

	public I_C_ValidCombination getCashCountEnvase_A() throws RuntimeException;

    /** Column name CashCountService_Acct */
    public static final String COLUMNNAME_CashCountService_Acct = "CashCountService_Acct";

	/** Set CashCountService_Acct	  */
	public void setCashCountService_Acct (int CashCountService_Acct);

	/** Get CashCountService_Acct	  */
	public int getCashCountService_Acct();

	public I_C_ValidCombination getCashCountService_A() throws RuntimeException;

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

    /** Column name C_BankAccount_ID_1 */
    public static final String COLUMNNAME_C_BankAccount_ID_1 = "C_BankAccount_ID_1";

	/** Set C_BankAccount_ID_1	  */
	public void setC_BankAccount_ID_1 (int C_BankAccount_ID_1);

	/** Get C_BankAccount_ID_1	  */
	public int getC_BankAccount_ID_1();

    /** Column name C_BankAccount_ID_2 */
    public static final String COLUMNNAME_C_BankAccount_ID_2 = "C_BankAccount_ID_2";

	/** Set C_BankAccount_ID_2	  */
	public void setC_BankAccount_ID_2 (int C_BankAccount_ID_2);

	/** Get C_BankAccount_ID_2	  */
	public int getC_BankAccount_ID_2();

    /** Column name C_BankAccount_ID_3 */
    public static final String COLUMNNAME_C_BankAccount_ID_3 = "C_BankAccount_ID_3";

	/** Set C_BankAccount_ID_3	  */
	public void setC_BankAccount_ID_3 (int C_BankAccount_ID_3);

	/** Get C_BankAccount_ID_3	  */
	public int getC_BankAccount_ID_3();

    /** Column name C_BankAccount_ID_4 */
    public static final String COLUMNNAME_C_BankAccount_ID_4 = "C_BankAccount_ID_4";

	/** Set C_BankAccount_ID_4	  */
	public void setC_BankAccount_ID_4 (int C_BankAccount_ID_4);

	/** Get C_BankAccount_ID_4	  */
	public int getC_BankAccount_ID_4();

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

    /** Column name Tolerance */
    public static final String COLUMNNAME_Tolerance = "Tolerance";

	/** Set Tolerance	  */
	public void setTolerance (BigDecimal Tolerance);

	/** Get Tolerance	  */
	public BigDecimal getTolerance();

    /** Column name ToleranceAmount */
    public static final String COLUMNNAME_ToleranceAmount = "ToleranceAmount";

	/** Set ToleranceAmount	  */
	public void setToleranceAmount (BigDecimal ToleranceAmount);

	/** Get ToleranceAmount	  */
	public BigDecimal getToleranceAmount();

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

    /** Column name UY_CashConfig_ID */
    public static final String COLUMNNAME_UY_CashConfig_ID = "UY_CashConfig_ID";

	/** Set UY_CashConfig	  */
	public void setUY_CashConfig_ID (int UY_CashConfig_ID);

	/** Get UY_CashConfig	  */
	public int getUY_CashConfig_ID();
}
