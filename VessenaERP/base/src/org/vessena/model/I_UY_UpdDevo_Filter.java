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

/** Generated Interface for UY_UpdDevo_Filter
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_UpdDevo_Filter 
{

    /** TableName=UY_UpdDevo_Filter */
    public static final String Table_Name = "UY_UpdDevo_Filter";

    /** AD_Table_ID=1000205 */
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

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

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

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public I_AD_User getSalesRep() throws RuntimeException;

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

    /** Column name UY_AsignaTransporteHdr_ID */
    public static final String COLUMNNAME_UY_AsignaTransporteHdr_ID = "UY_AsignaTransporteHdr_ID";

	/** Set UY_AsignaTransporteHdr_ID	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID);

	/** Get UY_AsignaTransporteHdr_ID	  */
	public int getUY_AsignaTransporteHdr_ID();

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException;

    /** Column name uy_dateordered_from */
    public static final String COLUMNNAME_uy_dateordered_from = "uy_dateordered_from";

	/** Set uy_dateordered_from	  */
	public void setuy_dateordered_from (Timestamp uy_dateordered_from);

	/** Get uy_dateordered_from	  */
	public Timestamp getuy_dateordered_from();

    /** Column name uy_dateordered_to */
    public static final String COLUMNNAME_uy_dateordered_to = "uy_dateordered_to";

	/** Set uy_dateordered_to	  */
	public void setuy_dateordered_to (Timestamp uy_dateordered_to);

	/** Get uy_dateordered_to	  */
	public Timestamp getuy_dateordered_to();

    /** Column name uy_docaction_update */
    public static final String COLUMNNAME_uy_docaction_update = "uy_docaction_update";

	/** Set uy_docaction_update	  */
	public void setuy_docaction_update (String uy_docaction_update);

	/** Get uy_docaction_update	  */
	public String getuy_docaction_update();

    /** Column name UY_UpdDevo_Filter_ID */
    public static final String COLUMNNAME_UY_UpdDevo_Filter_ID = "UY_UpdDevo_Filter_ID";

	/** Set UY_UpdDevo_Filter	  */
	public void setUY_UpdDevo_Filter_ID (int UY_UpdDevo_Filter_ID);

	/** Get UY_UpdDevo_Filter	  */
	public int getUY_UpdDevo_Filter_ID();

    /** Column name uy_verpedidos */
    public static final String COLUMNNAME_uy_verpedidos = "uy_verpedidos";

	/** Set uy_verpedidos	  */
	public void setuy_verpedidos (String uy_verpedidos);

	/** Get uy_verpedidos	  */
	public String getuy_verpedidos();
}
