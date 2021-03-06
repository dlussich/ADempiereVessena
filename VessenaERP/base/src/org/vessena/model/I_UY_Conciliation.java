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

/** Generated Interface for UY_Conciliation
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_Conciliation 
{

    /** TableName=UY_Conciliation */
    public static final String Table_Name = "UY_Conciliation";

    /** AD_Table_ID=1000401 */
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

    /** Column name amt1 */
    public static final String COLUMNNAME_amt1 = "amt1";

	/** Set amt1	  */
	public void setamt1 (BigDecimal amt1);

	/** Get amt1	  */
	public BigDecimal getamt1();

    /** Column name amt2 */
    public static final String COLUMNNAME_amt2 = "amt2";

	/** Set amt2	  */
	public void setamt2 (BigDecimal amt2);

	/** Get amt2	  */
	public BigDecimal getamt2();

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

    /** Column name C_Bank_ID */
    public static final String COLUMNNAME_C_Bank_ID = "C_Bank_ID";

	/** Set Bank.
	  * Bank
	  */
	public void setC_Bank_ID (int C_Bank_ID);

	/** Get Bank.
	  * Bank
	  */
	public int getC_Bank_ID();

	public org.compiere.model.I_C_Bank getC_Bank() throws RuntimeException;

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

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

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

    /** Column name Diference */
    public static final String COLUMNNAME_Diference = "Diference";

	/** Set Diference	  */
	public void setDiference (BigDecimal Diference);

	/** Get Diference	  */
	public BigDecimal getDiference();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

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

    /** Column name LoadConciliated */
    public static final String COLUMNNAME_LoadConciliated = "LoadConciliated";

	/** Set LoadConciliated	  */
	public void setLoadConciliated (boolean LoadConciliated);

	/** Get LoadConciliated	  */
	public boolean isLoadConciliated();

    /** Column name ManualConciliation */
    public static final String COLUMNNAME_ManualConciliation = "ManualConciliation";

	/** Set ManualConciliation	  */
	public void setManualConciliation (String ManualConciliation);

	/** Get ManualConciliation	  */
	public String getManualConciliation();

    /** Column name Orden1 */
    public static final String COLUMNNAME_Orden1 = "Orden1";

	/** Set Orden1	  */
	public void setOrden1 (String Orden1);

	/** Get Orden1	  */
	public String getOrden1();

    /** Column name Orden2 */
    public static final String COLUMNNAME_Orden2 = "Orden2";

	/** Set Orden2	  */
	public void setOrden2 (String Orden2);

	/** Get Orden2	  */
	public String getOrden2();

    /** Column name Orden3 */
    public static final String COLUMNNAME_Orden3 = "Orden3";

	/** Set Orden3	  */
	public void setOrden3 (String Orden3);

	/** Get Orden3	  */
	public String getOrden3();

    /** Column name Orden4 */
    public static final String COLUMNNAME_Orden4 = "Orden4";

	/** Set Orden4	  */
	public void setOrden4 (String Orden4);

	/** Get Orden4	  */
	public String getOrden4();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Refresh */
    public static final String COLUMNNAME_Refresh = "Refresh";

	/** Set Refresh	  */
	public void setRefresh (String Refresh);

	/** Get Refresh	  */
	public String getRefresh();

    /** Column name Tolerance */
    public static final String COLUMNNAME_Tolerance = "Tolerance";

	/** Set Tolerance	  */
	public void setTolerance (int Tolerance);

	/** Get Tolerance	  */
	public int getTolerance();

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

    /** Column name UY_Conciliation_ID */
    public static final String COLUMNNAME_UY_Conciliation_ID = "UY_Conciliation_ID";

	/** Set UY_Conciliation	  */
	public void setUY_Conciliation_ID (int UY_Conciliation_ID);

	/** Get UY_Conciliation	  */
	public int getUY_Conciliation_ID();
}
