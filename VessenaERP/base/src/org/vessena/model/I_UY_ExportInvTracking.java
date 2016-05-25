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

/** Generated Interface for UY_ExportInvTracking
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_ExportInvTracking 
{

    /** TableName=UY_ExportInvTracking */
    public static final String Table_Name = "UY_ExportInvTracking";

    /** AD_Table_ID=1000365 */
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

    /** Column name docstatus_export */
    public static final String COLUMNNAME_docstatus_export = "docstatus_export";

	/** Set docstatus_export	  */
	public void setdocstatus_export (String docstatus_export);

	/** Get docstatus_export	  */
	public String getdocstatus_export();

    /** Column name docstatus_proform */
    public static final String COLUMNNAME_docstatus_proform = "docstatus_proform";

	/** Set docstatus_proform	  */
	public void setdocstatus_proform (String docstatus_proform);

	/** Get docstatus_proform	  */
	public String getdocstatus_proform();

    /** Column name doctype_export */
    public static final String COLUMNNAME_doctype_export = "doctype_export";

	/** Set doctype_export	  */
	public void setdoctype_export (int doctype_export);

	/** Get doctype_export	  */
	public int getdoctype_export();

    /** Column name doctype_proform */
    public static final String COLUMNNAME_doctype_proform = "doctype_proform";

	/** Set doctype_proform	  */
	public void setdoctype_proform (int doctype_proform);

	/** Get doctype_proform	  */
	public int getdoctype_proform();

    /** Column name exportinvoice */
    public static final String COLUMNNAME_exportinvoice = "exportinvoice";

	/** Set exportinvoice	  */
	public void setexportinvoice (int exportinvoice);

	/** Get exportinvoice	  */
	public int getexportinvoice();

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

    /** Column name proforminvoice */
    public static final String COLUMNNAME_proforminvoice = "proforminvoice";

	/** Set proforminvoice	  */
	public void setproforminvoice (int proforminvoice);

	/** Get proforminvoice	  */
	public int getproforminvoice();

    /** Column name sequence_export */
    public static final String COLUMNNAME_sequence_export = "sequence_export";

	/** Set sequence_export	  */
	public void setsequence_export (String sequence_export);

	/** Get sequence_export	  */
	public String getsequence_export();

    /** Column name sequence_proform */
    public static final String COLUMNNAME_sequence_proform = "sequence_proform";

	/** Set sequence_proform	  */
	public void setsequence_proform (String sequence_proform);

	/** Get sequence_proform	  */
	public String getsequence_proform();

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

    /** Column name UY_ExportInvTracking_ID */
    public static final String COLUMNNAME_UY_ExportInvTracking_ID = "UY_ExportInvTracking_ID";

	/** Set UY_ExportInvTracking	  */
	public void setUY_ExportInvTracking_ID (int UY_ExportInvTracking_ID);

	/** Get UY_ExportInvTracking	  */
	public int getUY_ExportInvTracking_ID();

    /** Column name version_export */
    public static final String COLUMNNAME_version_export = "version_export";

	/** Set version_export	  */
	public void setversion_export (String version_export);

	/** Get version_export	  */
	public String getversion_export();

    /** Column name version_proform */
    public static final String COLUMNNAME_version_proform = "version_proform";

	/** Set version_proform	  */
	public void setversion_proform (String version_proform);

	/** Get version_proform	  */
	public String getversion_proform();
}
