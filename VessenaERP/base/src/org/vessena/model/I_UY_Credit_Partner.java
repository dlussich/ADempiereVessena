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

/** Generated Interface for UY_Credit_Partner
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_Credit_Partner 
{

    /** TableName=UY_Credit_Partner */
    public static final String Table_Name = "UY_Credit_Partner";

    /** AD_Table_ID=1000118 */
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

	public I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name so_creditlimt */
    public static final String COLUMNNAME_so_creditlimt = "so_creditlimt";

	/** Set so_creditlimt	  */
	public void setso_creditlimt (BigDecimal so_creditlimt);

	/** Get so_creditlimt	  */
	public BigDecimal getso_creditlimt();

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

    /** Column name uy_approval_amt */
    public static final String COLUMNNAME_uy_approval_amt = "uy_approval_amt";

	/** Set uy_approval_amt	  */
	public void setuy_approval_amt (BigDecimal uy_approval_amt);

	/** Get uy_approval_amt	  */
	public BigDecimal getuy_approval_amt();

    /** Column name UY_Credit_Filter_ID */
    public static final String COLUMNNAME_UY_Credit_Filter_ID = "UY_Credit_Filter_ID";

	/** Set UY_Credit_Filter	  */
	public void setUY_Credit_Filter_ID (int UY_Credit_Filter_ID);

	/** Get UY_Credit_Filter	  */
	public int getUY_Credit_Filter_ID();

	public I_UY_Credit_Filter getUY_Credit_Filter() throws RuntimeException;

    /** Column name UY_Credit_Partner_ID */
    public static final String COLUMNNAME_UY_Credit_Partner_ID = "UY_Credit_Partner_ID";

	/** Set UY_Credit_Partner	  */
	public void setUY_Credit_Partner_ID (int UY_Credit_Partner_ID);

	/** Get UY_Credit_Partner	  */
	public int getUY_Credit_Partner_ID();

    /** Column name uy_credit_status */
    public static final String COLUMNNAME_uy_credit_status = "uy_credit_status";

	/** Set uy_credit_status	  */
	public void setuy_credit_status (String uy_credit_status);

	/** Get uy_credit_status	  */
	public String getuy_credit_status();

    /** Column name uy_creditlimt_doc */
    public static final String COLUMNNAME_uy_creditlimt_doc = "uy_creditlimt_doc";

	/** Set uy_creditlimt_doc	  */
	public void setuy_creditlimt_doc (BigDecimal uy_creditlimt_doc);

	/** Get uy_creditlimt_doc	  */
	public BigDecimal getuy_creditlimt_doc();

    /** Column name uy_ctacte_saldo */
    public static final String COLUMNNAME_uy_ctacte_saldo = "uy_ctacte_saldo";

	/** Set uy_ctacte_saldo	  */
	public void setuy_ctacte_saldo (BigDecimal uy_ctacte_saldo);

	/** Get uy_ctacte_saldo	  */
	public BigDecimal getuy_ctacte_saldo();

    /** Column name uy_ctacte_vencido */
    public static final String COLUMNNAME_uy_ctacte_vencido = "uy_ctacte_vencido";

	/** Set uy_ctacte_vencido	  */
	public void setuy_ctacte_vencido (BigDecimal uy_ctacte_vencido);

	/** Get uy_ctacte_vencido	  */
	public BigDecimal getuy_ctacte_vencido();

    /** Column name uy_ctadoc_saldo */
    public static final String COLUMNNAME_uy_ctadoc_saldo = "uy_ctadoc_saldo";

	/** Set uy_ctadoc_saldo	  */
	public void setuy_ctadoc_saldo (BigDecimal uy_ctadoc_saldo);

	/** Get uy_ctadoc_saldo	  */
	public BigDecimal getuy_ctadoc_saldo();

    /** Column name uy_nrotrx */
    public static final String COLUMNNAME_uy_nrotrx = "uy_nrotrx";

	/** Set uy_nrotrx	  */
	public void setuy_nrotrx (int uy_nrotrx);

	/** Get uy_nrotrx	  */
	public int getuy_nrotrx();
}
