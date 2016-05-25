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

/** Generated Interface for UY_AcctNavCC_CCDET
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_AcctNavCC_CCDET 
{

    /** TableName=UY_AcctNavCC_CCDET */
    public static final String Table_Name = "UY_AcctNavCC_CCDET";

    /** AD_Table_ID=1010743 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountType */
    public static final String COLUMNNAME_AccountType = "AccountType";

	/** Set Account Type.
	  * Indicates the type of account
	  */
	public void setAccountType (String AccountType);

	/** Get Account Type.
	  * Indicates the type of account
	  */
	public String getAccountType();

    /** Column name acctname */
    public static final String COLUMNNAME_acctname = "acctname";

	/** Set acctname	  */
	public void setacctname (String acctname);

	/** Get acctname	  */
	public String getacctname();

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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/** Set Amount.
	  * Amount
	  */
	public void setAmt (BigDecimal Amt);

	/** Get Amount.
	  * Amount
	  */
	public BigDecimal getAmt();

    /** Column name C_Activity_ID_1 */
    public static final String COLUMNNAME_C_Activity_ID_1 = "C_Activity_ID_1";

	/** Set C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1);

	/** Get C_Activity_ID_1	  */
	public int getC_Activity_ID_1();

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

    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/** Set Account Element.
	  * Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/** Get Account Element.
	  * Account Element
	  */
	public int getC_ElementValue_ID();

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException;

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

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name DateAudit */
    public static final String COLUMNNAME_DateAudit = "DateAudit";

	/** Set DateAudit	  */
	public void setDateAudit (Timestamp DateAudit);

	/** Get DateAudit	  */
	public Timestamp getDateAudit();

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

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (boolean ExecuteAction);

	/** Get ExecuteAction	  */
	public boolean isExecuteAction();

    /** Column name GL_Journal_ID */
    public static final String COLUMNNAME_GL_Journal_ID = "GL_Journal_ID";

	/** Set Journal.
	  * General Ledger Journal
	  */
	public void setGL_Journal_ID (int GL_Journal_ID);

	/** Get Journal.
	  * General Ledger Journal
	  */
	public int getGL_Journal_ID();

	public org.compiere.model.I_GL_Journal getGL_Journal() throws RuntimeException;

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

    /** Column name Parent_ID */
    public static final String COLUMNNAME_Parent_ID = "Parent_ID";

	/** Set Parent.
	  * Parent of Entity
	  */
	public void setParent_ID (int Parent_ID);

	/** Get Parent.
	  * Parent of Entity
	  */
	public int getParent_ID();

    /** Column name parentname */
    public static final String COLUMNNAME_parentname = "parentname";

	/** Set parentname	  */
	public void setparentname (String parentname);

	/** Get parentname	  */
	public String getparentname();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

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

    /** Column name UserAudit */
    public static final String COLUMNNAME_UserAudit = "UserAudit";

	/** Set UserAudit	  */
	public void setUserAudit (int UserAudit);

	/** Get UserAudit	  */
	public int getUserAudit();

    /** Column name UY_AcctNavCC_Account_ID */
    public static final String COLUMNNAME_UY_AcctNavCC_Account_ID = "UY_AcctNavCC_Account_ID";

	/** Set UY_AcctNavCC_Account	  */
	public void setUY_AcctNavCC_Account_ID (int UY_AcctNavCC_Account_ID);

	/** Get UY_AcctNavCC_Account	  */
	public int getUY_AcctNavCC_Account_ID();

	public I_UY_AcctNavCC_Account getUY_AcctNavCC_Account() throws RuntimeException;

    /** Column name UY_AcctNavCC_CCDET_ID */
    public static final String COLUMNNAME_UY_AcctNavCC_CCDET_ID = "UY_AcctNavCC_CCDET_ID";

	/** Set UY_AcctNavCC_CCDET	  */
	public void setUY_AcctNavCC_CCDET_ID (int UY_AcctNavCC_CCDET_ID);

	/** Get UY_AcctNavCC_CCDET	  */
	public int getUY_AcctNavCC_CCDET_ID();

    /** Column name UY_AcctNavCC_CC_ID */
    public static final String COLUMNNAME_UY_AcctNavCC_CC_ID = "UY_AcctNavCC_CC_ID";

	/** Set UY_AcctNavCC_CC	  */
	public void setUY_AcctNavCC_CC_ID (int UY_AcctNavCC_CC_ID);

	/** Get UY_AcctNavCC_CC	  */
	public int getUY_AcctNavCC_CC_ID();

	public I_UY_AcctNavCC_CC getUY_AcctNavCC_CC() throws RuntimeException;

    /** Column name UY_AcctNavCC_ID */
    public static final String COLUMNNAME_UY_AcctNavCC_ID = "UY_AcctNavCC_ID";

	/** Set UY_AcctNavCC	  */
	public void setUY_AcctNavCC_ID (int UY_AcctNavCC_ID);

	/** Get UY_AcctNavCC	  */
	public int getUY_AcctNavCC_ID();

	public I_UY_AcctNavCC getUY_AcctNavCC() throws RuntimeException;

    /** Column name UY_AcctNavCC_Main_ID */
    public static final String COLUMNNAME_UY_AcctNavCC_Main_ID = "UY_AcctNavCC_Main_ID";

	/** Set UY_AcctNavCC_Main	  */
	public void setUY_AcctNavCC_Main_ID (int UY_AcctNavCC_Main_ID);

	/** Get UY_AcctNavCC_Main	  */
	public int getUY_AcctNavCC_Main_ID();

	public I_UY_AcctNavCC_Main getUY_AcctNavCC_Main() throws RuntimeException;
}
