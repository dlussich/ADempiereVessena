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

/** Generated Interface for UY_TypeFactLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TypeFactLine 
{

    /** TableName=UY_TypeFactLine */
    public static final String Table_Name = "UY_TypeFactLine";

    /** AD_Table_ID=1000819 */
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

    /** Column name Calculate */
    public static final String COLUMNNAME_Calculate = "Calculate";

	/** Set Calculate	  */
	public void setCalculate (String Calculate);

	/** Get Calculate	  */
	public String getCalculate();

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

    /** Column name DocumentNoAux */
    public static final String COLUMNNAME_DocumentNoAux = "DocumentNoAux";

	/** Set DocumentNoAux.
	  * DocumentNoAux
	  */
	public void setDocumentNoAux (String DocumentNoAux);

	/** Get DocumentNoAux.
	  * DocumentNoAux
	  */
	public String getDocumentNoAux();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

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

    /** Column name IsCalculated */
    public static final String COLUMNNAME_IsCalculated = "IsCalculated";

	/** Set Calculated.
	  * The value is calculated by the system
	  */
	public void setIsCalculated (boolean IsCalculated);

	/** Get Calculated.
	  * The value is calculated by the system
	  */
	public boolean isCalculated();

    /** Column name IsDebit */
    public static final String COLUMNNAME_IsDebit = "IsDebit";

	/** Set IsDebit	  */
	public void setIsDebit (boolean IsDebit);

	/** Get IsDebit	  */
	public boolean isDebit();

    /** Column name ManageBPartner */
    public static final String COLUMNNAME_ManageBPartner = "ManageBPartner";

	/** Set ManageBPartner	  */
	public void setManageBPartner (boolean ManageBPartner);

	/** Get ManageBPartner	  */
	public boolean isManageBPartner();

    /** Column name ManageDateTrx */
    public static final String COLUMNNAME_ManageDateTrx = "ManageDateTrx";

	/** Set ManageDateTrx.
	  * ManageDateTrx
	  */
	public void setManageDateTrx (boolean ManageDateTrx);

	/** Get ManageDateTrx.
	  * ManageDateTrx
	  */
	public boolean isManageDateTrx();

    /** Column name ManageDocument */
    public static final String COLUMNNAME_ManageDocument = "ManageDocument";

	/** Set ManageDocument	  */
	public void setManageDocument (boolean ManageDocument);

	/** Get ManageDocument	  */
	public boolean isManageDocument();

    /** Column name ManageDueDate */
    public static final String COLUMNNAME_ManageDueDate = "ManageDueDate";

	/** Set ManageDueDate.
	  * ManageDueDate
	  */
	public void setManageDueDate (boolean ManageDueDate);

	/** Get ManageDueDate.
	  * ManageDueDate
	  */
	public boolean isManageDueDate();

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

    /** Column name UY_DocTypeFact_ID */
    public static final String COLUMNNAME_UY_DocTypeFact_ID = "UY_DocTypeFact_ID";

	/** Set UY_DocTypeFact	  */
	public void setUY_DocTypeFact_ID (int UY_DocTypeFact_ID);

	/** Get UY_DocTypeFact	  */
	public int getUY_DocTypeFact_ID();

	public I_UY_DocTypeFact getUY_DocTypeFact() throws RuntimeException;

    /** Column name UY_TypeFact_ID */
    public static final String COLUMNNAME_UY_TypeFact_ID = "UY_TypeFact_ID";

	/** Set UY_TypeFact	  */
	public void setUY_TypeFact_ID (int UY_TypeFact_ID);

	/** Get UY_TypeFact	  */
	public int getUY_TypeFact_ID();

	public I_UY_TypeFact getUY_TypeFact() throws RuntimeException;

    /** Column name UY_TypeFactLine_ID */
    public static final String COLUMNNAME_UY_TypeFactLine_ID = "UY_TypeFactLine_ID";

	/** Set UY_TypeFactLine	  */
	public void setUY_TypeFactLine_ID (int UY_TypeFactLine_ID);

	/** Get UY_TypeFactLine	  */
	public int getUY_TypeFactLine_ID();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
