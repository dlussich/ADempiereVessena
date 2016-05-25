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

/** Generated Interface for UY_TR_MicCountry
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_MicCountry 
{

    /** TableName=UY_TR_MicCountry */
    public static final String Table_Name = "UY_TR_MicCountry";

    /** AD_Table_ID=1000807 */
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

    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Country.
	  * Country 
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Country.
	  * Country 
	  */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException;

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

    /** Column name Numero */
    public static final String COLUMNNAME_Numero = "Numero";

	/** Set Numero	  */
	public void setNumero (int Numero);

	/** Get Numero	  */
	public int getNumero();

    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/** Set Status.
	  * Status of the currently running check
	  */
	public void setStatus (String Status);

	/** Get Status.
	  * Status of the currently running check
	  */
	public String getStatus();

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

    /** Column name UY_Ciudad_ID */
    public static final String COLUMNNAME_UY_Ciudad_ID = "UY_Ciudad_ID";

	/** Set UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID);

	/** Get UY_Ciudad	  */
	public int getUY_Ciudad_ID();

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException;

    /** Column name UY_Ciudad_ID_1 */
    public static final String COLUMNNAME_UY_Ciudad_ID_1 = "UY_Ciudad_ID_1";

	/** Set UY_Ciudad_ID_1	  */
	public void setUY_Ciudad_ID_1 (int UY_Ciudad_ID_1);

	/** Get UY_Ciudad_ID_1	  */
	public int getUY_Ciudad_ID_1();

    /** Column name UY_TR_Aduana_ID */
    public static final String COLUMNNAME_UY_TR_Aduana_ID = "UY_TR_Aduana_ID";

	/** Set UY_TR_Aduana	  */
	public void setUY_TR_Aduana_ID (int UY_TR_Aduana_ID);

	/** Get UY_TR_Aduana	  */
	public int getUY_TR_Aduana_ID();

	public I_UY_TR_Aduana getUY_TR_Aduana() throws RuntimeException;

    /** Column name UY_TR_Aduana_ID_1 */
    public static final String COLUMNNAME_UY_TR_Aduana_ID_1 = "UY_TR_Aduana_ID_1";

	/** Set UY_TR_Aduana_ID_1	  */
	public void setUY_TR_Aduana_ID_1 (int UY_TR_Aduana_ID_1);

	/** Get UY_TR_Aduana_ID_1	  */
	public int getUY_TR_Aduana_ID_1();

    /** Column name UY_TR_MicCountry_ID */
    public static final String COLUMNNAME_UY_TR_MicCountry_ID = "UY_TR_MicCountry_ID";

	/** Set UY_TR_MicCountry	  */
	public void setUY_TR_MicCountry_ID (int UY_TR_MicCountry_ID);

	/** Get UY_TR_MicCountry	  */
	public int getUY_TR_MicCountry_ID();

    /** Column name UY_TR_Mic_ID */
    public static final String COLUMNNAME_UY_TR_Mic_ID = "UY_TR_Mic_ID";

	/** Set UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID);

	/** Get UY_TR_Mic	  */
	public int getUY_TR_Mic_ID();

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException;

    /** Column name UY_TR_OperativeSite_ID */
    public static final String COLUMNNAME_UY_TR_OperativeSite_ID = "UY_TR_OperativeSite_ID";

	/** Set UY_TR_OperativeSite	  */
	public void setUY_TR_OperativeSite_ID (int UY_TR_OperativeSite_ID);

	/** Get UY_TR_OperativeSite	  */
	public int getUY_TR_OperativeSite_ID();

	public I_UY_TR_OperativeSite getUY_TR_OperativeSite() throws RuntimeException;

    /** Column name UY_TR_OperativeSite_ID_1 */
    public static final String COLUMNNAME_UY_TR_OperativeSite_ID_1 = "UY_TR_OperativeSite_ID_1";

	/** Set UY_TR_OperativeSite_ID_1	  */
	public void setUY_TR_OperativeSite_ID_1 (int UY_TR_OperativeSite_ID_1);

	/** Get UY_TR_OperativeSite_ID_1	  */
	public int getUY_TR_OperativeSite_ID_1();
}
