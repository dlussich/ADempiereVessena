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

/** Generated Interface for UY_HRConceptoReloj
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_HRConceptoReloj 
{

    /** TableName=UY_HRConceptoReloj */
    public static final String Table_Name = "UY_HRConceptoReloj";

    /** AD_Table_ID=1000236 */
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

    /** Column name hr_concept_diastrab_id */
    public static final String COLUMNNAME_hr_concept_diastrab_id = "hr_concept_diastrab_id";

	/** Set hr_concept_diastrab_id	  */
	public void sethr_concept_diastrab_id (int hr_concept_diastrab_id);

	/** Get hr_concept_diastrab_id	  */
	public int gethr_concept_diastrab_id();

	public org.eevolution.model.I_HR_Concept gethr_concept_diastrab() throws RuntimeException;

    /** Column name hr_concept_hrsextra_id */
    public static final String COLUMNNAME_hr_concept_hrsextra_id = "hr_concept_hrsextra_id";

	/** Set hr_concept_hrsextra_id	  */
	public void sethr_concept_hrsextra_id (int hr_concept_hrsextra_id);

	/** Get hr_concept_hrsextra_id	  */
	public int gethr_concept_hrsextra_id();

	public org.eevolution.model.I_HR_Concept gethr_concept_hrsextra() throws RuntimeException;

    /** Column name hr_concept_hrsextranoct_id */
    public static final String COLUMNNAME_hr_concept_hrsextranoct_id = "hr_concept_hrsextranoct_id";

	/** Set hr_concept_hrsextranoct_id	  */
	public void sethr_concept_hrsextranoct_id (int hr_concept_hrsextranoct_id);

	/** Get hr_concept_hrsextranoct_id	  */
	public int gethr_concept_hrsextranoct_id();

	public org.eevolution.model.I_HR_Concept gethr_concept_hrsextranoct() throws RuntimeException;

    /** Column name hr_concept_hrsnoct_id */
    public static final String COLUMNNAME_hr_concept_hrsnoct_id = "hr_concept_hrsnoct_id";

	/** Set hr_concept_hrsnoct_id	  */
	public void sethr_concept_hrsnoct_id (int hr_concept_hrsnoct_id);

	/** Get hr_concept_hrsnoct_id	  */
	public int gethr_concept_hrsnoct_id();

	public org.eevolution.model.I_HR_Concept gethr_concept_hrsnoct() throws RuntimeException;

    /** Column name hr_concept_hrsnotrab_id */
    public static final String COLUMNNAME_hr_concept_hrsnotrab_id = "hr_concept_hrsnotrab_id";

	/** Set hr_concept_hrsnotrab_id	  */
	public void sethr_concept_hrsnotrab_id (int hr_concept_hrsnotrab_id);

	/** Get hr_concept_hrsnotrab_id	  */
	public int gethr_concept_hrsnotrab_id();

	public org.eevolution.model.I_HR_Concept gethr_concept_hrsnotrab() throws RuntimeException;

    /** Column name hr_concept_hrstrab_id */
    public static final String COLUMNNAME_hr_concept_hrstrab_id = "hr_concept_hrstrab_id";

	/** Set hr_concept_hrstrab_id	  */
	public void sethr_concept_hrstrab_id (int hr_concept_hrstrab_id);

	/** Get hr_concept_hrstrab_id	  */
	public int gethr_concept_hrstrab_id();

	public org.eevolution.model.I_HR_Concept gethr_concept_hrstrab() throws RuntimeException;

    /** Column name hr_concept_inasist_id */
    public static final String COLUMNNAME_hr_concept_inasist_id = "hr_concept_inasist_id";

	/** Set hr_concept_inasist_id	  */
	public void sethr_concept_inasist_id (int hr_concept_inasist_id);

	/** Get hr_concept_inasist_id	  */
	public int gethr_concept_inasist_id();

	public org.eevolution.model.I_HR_Concept gethr_concept_inasist() throws RuntimeException;

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

    /** Column name UY_HRConceptoReloj_ID */
    public static final String COLUMNNAME_UY_HRConceptoReloj_ID = "UY_HRConceptoReloj_ID";

	/** Set UY_HRConceptoReloj	  */
	public void setUY_HRConceptoReloj_ID (int UY_HRConceptoReloj_ID);

	/** Get UY_HRConceptoReloj	  */
	public int getUY_HRConceptoReloj_ID();

    /** Column name UY_HRParametros_ID */
    public static final String COLUMNNAME_UY_HRParametros_ID = "UY_HRParametros_ID";

	/** Set UY_HRParametros	  */
	public void setUY_HRParametros_ID (int UY_HRParametros_ID);

	/** Get UY_HRParametros	  */
	public int getUY_HRParametros_ID();

	public I_UY_HRParametros getUY_HRParametros() throws RuntimeException;
}
