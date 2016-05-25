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

/** Generated Interface for UY_Gerencial_Detail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Gerencial_Detail 
{

    /** TableName=UY_Gerencial_Detail */
    public static final String Table_Name = "UY_Gerencial_Detail";

    /** AD_Table_ID=1000432 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

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

    /** Column name UY_Gerencial_Account_ID */
    public static final String COLUMNNAME_UY_Gerencial_Account_ID = "UY_Gerencial_Account_ID";

	/** Set UY_Gerencial_Account	  */
	public void setUY_Gerencial_Account_ID (int UY_Gerencial_Account_ID);

	/** Get UY_Gerencial_Account	  */
	public int getUY_Gerencial_Account_ID();

	public I_UY_Gerencial_Account getUY_Gerencial_Account() throws RuntimeException;

    /** Column name UY_Gerencial_Detail_ID */
    public static final String COLUMNNAME_UY_Gerencial_Detail_ID = "UY_Gerencial_Detail_ID";

	/** Set UY_Gerencial_Detail	  */
	public void setUY_Gerencial_Detail_ID (int UY_Gerencial_Detail_ID);

	/** Get UY_Gerencial_Detail	  */
	public int getUY_Gerencial_Detail_ID();

    /** Column name UY_Gerencial_ID */
    public static final String COLUMNNAME_UY_Gerencial_ID = "UY_Gerencial_ID";

	/** Set UY_Gerencial	  */
	public void setUY_Gerencial_ID (int UY_Gerencial_ID);

	/** Get UY_Gerencial	  */
	public int getUY_Gerencial_ID();

	public I_UY_Gerencial getUY_Gerencial() throws RuntimeException;

    /** Column name UY_Gerencial_Main_ID */
    public static final String COLUMNNAME_UY_Gerencial_Main_ID = "UY_Gerencial_Main_ID";

	/** Set UY_Gerencial_Main	  */
	public void setUY_Gerencial_Main_ID (int UY_Gerencial_Main_ID);

	/** Get UY_Gerencial_Main	  */
	public int getUY_Gerencial_Main_ID();

	public I_UY_Gerencial_Main getUY_Gerencial_Main() throws RuntimeException;
}
