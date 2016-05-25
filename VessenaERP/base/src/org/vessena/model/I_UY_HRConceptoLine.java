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

/** Generated Interface for UY_HRConceptoLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_HRConceptoLine 
{

    /** TableName=UY_HRConceptoLine */
    public static final String Table_Name = "UY_HRConceptoLine";

    /** AD_Table_ID=1000696 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

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

    /** Column name porcentaje */
    public static final String COLUMNNAME_porcentaje = "porcentaje";

	/** Set porcentaje	  */
	public void setporcentaje (BigDecimal porcentaje);

	/** Get porcentaje	  */
	public BigDecimal getporcentaje();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

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

    /** Column name UY_HRConceptoLine_ID */
    public static final String COLUMNNAME_UY_HRConceptoLine_ID = "UY_HRConceptoLine_ID";

	/** Set UY_HRConceptoLine	  */
	public void setUY_HRConceptoLine_ID (int UY_HRConceptoLine_ID);

	/** Get UY_HRConceptoLine	  */
	public int getUY_HRConceptoLine_ID();

    /** Column name UY_HRLoadDriver_ID */
    public static final String COLUMNNAME_UY_HRLoadDriver_ID = "UY_HRLoadDriver_ID";

	/** Set UY_HRLoadDriver	  */
	public void setUY_HRLoadDriver_ID (int UY_HRLoadDriver_ID);

	/** Get UY_HRLoadDriver	  */
	public int getUY_HRLoadDriver_ID();

	public I_UY_HRLoadDriver getUY_HRLoadDriver() throws RuntimeException;

    /** Column name UY_HRNovedades_ID */
    public static final String COLUMNNAME_UY_HRNovedades_ID = "UY_HRNovedades_ID";

	/** Set UY_HRNovedades	  */
	public void setUY_HRNovedades_ID (int UY_HRNovedades_ID);

	/** Get UY_HRNovedades	  */
	public int getUY_HRNovedades_ID();

	public I_UY_HRNovedades getUY_HRNovedades() throws RuntimeException;
}
