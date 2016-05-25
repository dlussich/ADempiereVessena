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

/** Generated Interface for UY_HRResultDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_HRResultDetail 
{

    /** TableName=UY_HRResultDetail */
    public static final String Table_Name = "UY_HRResultDetail";

    /** AD_Table_ID=1000232 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountSign */
    public static final String COLUMNNAME_AccountSign = "AccountSign";

	/** Set Account Sign.
	  * Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	public void setAccountSign (String AccountSign);

	/** Get Account Sign.
	  * Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	public String getAccountSign();

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

    /** Column name codigo */
    public static final String COLUMNNAME_codigo = "codigo";

	/** Set codigo	  */
	public void setcodigo (String codigo);

	/** Get codigo	  */
	public String getcodigo();

    /** Column name ColumnType */
    public static final String COLUMNNAME_ColumnType = "ColumnType";

	/** Set Column Type	  */
	public void setColumnType (String ColumnType);

	/** Get Column Type	  */
	public String getColumnType();

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

    /** Column name HR_Concept_ID */
    public static final String COLUMNNAME_HR_Concept_ID = "HR_Concept_ID";

	/** Set Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID);

	/** Get Payroll Concept	  */
	public int getHR_Concept_ID();

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException;

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

    /** Column name Message */
    public static final String COLUMNNAME_Message = "Message";

	/** Set Message.
	  * EMail Message
	  */
	public void setMessage (String Message);

	/** Get Message.
	  * EMail Message
	  */
	public String getMessage();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name Success */
    public static final String COLUMNNAME_Success = "Success";

	/** Set Success	  */
	public void setSuccess (boolean Success);

	/** Get Success	  */
	public boolean isSuccess();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

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

    /** Column name UY_HRCalculos_ID */
    public static final String COLUMNNAME_UY_HRCalculos_ID = "UY_HRCalculos_ID";

	/** Set UY_HRCalculos	  */
	public void setUY_HRCalculos_ID (int UY_HRCalculos_ID);

	/** Get UY_HRCalculos	  */
	public int getUY_HRCalculos_ID();

	public I_UY_HRCalculos getUY_HRCalculos() throws RuntimeException;

    /** Column name UY_HRResult_ID */
    public static final String COLUMNNAME_UY_HRResult_ID = "UY_HRResult_ID";

	/** Set UY_HRResult	  */
	public void setUY_HRResult_ID (int UY_HRResult_ID);

	/** Get UY_HRResult	  */
	public int getUY_HRResult_ID();

	public I_UY_HRResult getUY_HRResult() throws RuntimeException;

    /** Column name UY_HRResultDetail_ID */
    public static final String COLUMNNAME_UY_HRResultDetail_ID = "UY_HRResultDetail_ID";

	/** Set UY_HRResultDetail	  */
	public void setUY_HRResultDetail_ID (int UY_HRResultDetail_ID);

	/** Get UY_HRResultDetail	  */
	public int getUY_HRResultDetail_ID();

    /** Column name WorkingTime */
    public static final String COLUMNNAME_WorkingTime = "WorkingTime";

	/** Set Working Time.
	  * Workflow Simulation Execution Time
	  */
	public void setWorkingTime (String WorkingTime);

	/** Get Working Time.
	  * Workflow Simulation Execution Time
	  */
	public String getWorkingTime();
}
