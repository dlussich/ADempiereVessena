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

/** Generated Interface for UY_HRResult
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_HRResult 
{

    /** TableName=UY_HRResult */
    public static final String Table_Name = "UY_HRResult";

    /** AD_Table_ID=1000231 */
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

    /** Column name AmtAcctCr */
    public static final String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/** Set Accounted Credit.
	  * Accounted Credit Amount
	  */
	public void setAmtAcctCr (BigDecimal AmtAcctCr);

	/** Get Accounted Credit.
	  * Accounted Credit Amount
	  */
	public BigDecimal getAmtAcctCr();

    /** Column name AmtAcctDr */
    public static final String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/** Set Accounted Debit.
	  * Accounted Debit Amount
	  */
	public void setAmtAcctDr (BigDecimal AmtAcctDr);

	/** Get Accounted Debit.
	  * Accounted Debit Amount
	  */
	public BigDecimal getAmtAcctDr();

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

    /** Column name imprimerecibos */
    public static final String COLUMNNAME_imprimerecibos = "imprimerecibos";

	/** Set imprimerecibos	  */
	public void setimprimerecibos (boolean imprimerecibos);

	/** Get imprimerecibos	  */
	public boolean isimprimerecibos();

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

    /** Column name LiteralNumber */
    public static final String COLUMNNAME_LiteralNumber = "LiteralNumber";

	/** Set LiteralNumber	  */
	public void setLiteralNumber (String LiteralNumber);

	/** Get LiteralNumber	  */
	public String getLiteralNumber();

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

    /** Column name ReProcessing */
    public static final String COLUMNNAME_ReProcessing = "ReProcessing";

	/** Set ReProcessing	  */
	public void setReProcessing (boolean ReProcessing);

	/** Get ReProcessing	  */
	public boolean isReProcessing();

    /** Column name SendEMail */
    public static final String COLUMNNAME_SendEMail = "SendEMail";

	/** Set Send EMail.
	  * Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail);

	/** Get Send EMail.
	  * Enable sending Document EMail
	  */
	public boolean isSendEMail();

    /** Column name Success */
    public static final String COLUMNNAME_Success = "Success";

	/** Set Success	  */
	public void setSuccess (boolean Success);

	/** Get Success	  */
	public boolean isSuccess();

    /** Column name sumCrBPS */
    public static final String COLUMNNAME_sumCrBPS = "sumCrBPS";

	/** Set sumCrBPS	  */
	public void setsumCrBPS (BigDecimal sumCrBPS);

	/** Get sumCrBPS	  */
	public BigDecimal getsumCrBPS();

    /** Column name sumDrBPS */
    public static final String COLUMNNAME_sumDrBPS = "sumDrBPS";

	/** Set sumDrBPS	  */
	public void setsumDrBPS (BigDecimal sumDrBPS);

	/** Get sumDrBPS	  */
	public BigDecimal getsumDrBPS();

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

    /** Column name UY_HRProcesoNomina_ID */
    public static final String COLUMNNAME_UY_HRProcesoNomina_ID = "UY_HRProcesoNomina_ID";

	/** Set UY_HRProcesoNomina	  */
	public void setUY_HRProcesoNomina_ID (int UY_HRProcesoNomina_ID);

	/** Get UY_HRProcesoNomina	  */
	public int getUY_HRProcesoNomina_ID();

	public I_UY_HRProcesoNomina getUY_HRProcesoNomina() throws RuntimeException;

    /** Column name UY_HRResult_ID */
    public static final String COLUMNNAME_UY_HRResult_ID = "UY_HRResult_ID";

	/** Set UY_HRResult	  */
	public void setUY_HRResult_ID (int UY_HRResult_ID);

	/** Get UY_HRResult	  */
	public int getUY_HRResult_ID();
}
