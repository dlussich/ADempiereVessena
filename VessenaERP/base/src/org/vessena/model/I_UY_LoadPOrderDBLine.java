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

/** Generated Interface for UY_LoadPOrderDBLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_LoadPOrderDBLine 
{

    /** TableName=UY_LoadPOrderDBLine */
    public static final String Table_Name = "UY_LoadPOrderDBLine";

    /** AD_Table_ID=1000251 */
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

    /** Column name DocumentNoInvoice */
    public static final String COLUMNNAME_DocumentNoInvoice = "DocumentNoInvoice";

	/** Set DocumentNoInvoice	  */
	public void setDocumentNoInvoice (String DocumentNoInvoice);

	/** Get DocumentNoInvoice	  */
	public String getDocumentNoInvoice();

    /** Column name idinvoice */
    public static final String COLUMNNAME_idinvoice = "idinvoice";

	/** Set idinvoice	  */
	public void setidinvoice (int idinvoice);

	/** Get idinvoice	  */
	public int getidinvoice();

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

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Total Lines.
	  * Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines);

	/** Get Total Lines.
	  * Total of all document lines
	  */
	public BigDecimal getTotalLines();

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

    /** Column name UY_LoadPOrderDBFilter_ID */
    public static final String COLUMNNAME_UY_LoadPOrderDBFilter_ID = "UY_LoadPOrderDBFilter_ID";

	/** Set UY_LoadPOrderDBFilter	  */
	public void setUY_LoadPOrderDBFilter_ID (int UY_LoadPOrderDBFilter_ID);

	/** Get UY_LoadPOrderDBFilter	  */
	public int getUY_LoadPOrderDBFilter_ID();

	public I_UY_LoadPOrderDBFilter getUY_LoadPOrderDBFilter() throws RuntimeException;

    /** Column name UY_LoadPOrderDBLine_ID */
    public static final String COLUMNNAME_UY_LoadPOrderDBLine_ID = "UY_LoadPOrderDBLine_ID";

	/** Set UY_LoadPOrderDBLine	  */
	public void setUY_LoadPOrderDBLine_ID (int UY_LoadPOrderDBLine_ID);

	/** Get UY_LoadPOrderDBLine	  */
	public int getUY_LoadPOrderDBLine_ID();

    /** Column name uy_procesar */
    public static final String COLUMNNAME_uy_procesar = "uy_procesar";

	/** Set uy_procesar	  */
	public void setuy_procesar (boolean uy_procesar);

	/** Get uy_procesar	  */
	public boolean isuy_procesar();
}
