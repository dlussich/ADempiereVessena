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

/** Generated Interface for UY_TR_DuaMic
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_DuaMic 
{

    /** TableName=UY_TR_DuaMic */
    public static final String Table_Name = "UY_TR_DuaMic";

    /** AD_Table_ID=1000873 */
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

    /** Column name AnioDua */
    public static final String COLUMNNAME_AnioDua = "AnioDua";

	/** Set AnioDua	  */
	public void setAnioDua (String AnioDua);

	/** Get AnioDua	  */
	public String getAnioDua();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name description2 */
    public static final String COLUMNNAME_description2 = "description2";

	/** Set description2	  */
	public void setdescription2 (String description2);

	/** Get description2	  */
	public String getdescription2();

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

    /** Column name ExecuteAction2 */
    public static final String COLUMNNAME_ExecuteAction2 = "ExecuteAction2";

	/** Set ExecuteAction2	  */
	public void setExecuteAction2 (String ExecuteAction2);

	/** Get ExecuteAction2	  */
	public String getExecuteAction2();

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

    /** Column name IsExecuted */
    public static final String COLUMNNAME_IsExecuted = "IsExecuted";

	/** Set IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted);

	/** Get IsExecuted	  */
	public boolean isExecuted();

    /** Column name NumeroDua */
    public static final String COLUMNNAME_NumeroDua = "NumeroDua";

	/** Set NumeroDua	  */
	public void setNumeroDua (String NumeroDua);

	/** Get NumeroDua	  */
	public String getNumeroDua();

    /** Column name pesoBruto */
    public static final String COLUMNNAME_pesoBruto = "pesoBruto";

	/** Set pesoBruto	  */
	public void setpesoBruto (BigDecimal pesoBruto);

	/** Get pesoBruto	  */
	public BigDecimal getpesoBruto();

    /** Column name pesoNeto */
    public static final String COLUMNNAME_pesoNeto = "pesoNeto";

	/** Set pesoNeto	  */
	public void setpesoNeto (BigDecimal pesoNeto);

	/** Get pesoNeto	  */
	public BigDecimal getpesoNeto();

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

    /** Column name UY_TR_Dua_ID */
    public static final String COLUMNNAME_UY_TR_Dua_ID = "UY_TR_Dua_ID";

	/** Set UY_TR_Dua_ID	  */
	public void setUY_TR_Dua_ID (int UY_TR_Dua_ID);

	/** Get UY_TR_Dua_ID	  */
	public int getUY_TR_Dua_ID();

	public I_UY_TR_Dua getUY_TR_Dua() throws RuntimeException;

    /** Column name UY_TR_DuaMic_ID */
    public static final String COLUMNNAME_UY_TR_DuaMic_ID = "UY_TR_DuaMic_ID";

	/** Set UY_TR_DuaMic_ID	  */
	public void setUY_TR_DuaMic_ID (int UY_TR_DuaMic_ID);

	/** Get UY_TR_DuaMic_ID	  */
	public int getUY_TR_DuaMic_ID();

    /** Column name UY_TR_Mic_ID */
    public static final String COLUMNNAME_UY_TR_Mic_ID = "UY_TR_Mic_ID";

	/** Set UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID);

	/** Get UY_TR_Mic	  */
	public int getUY_TR_Mic_ID();

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException;

    /** Column name valorMercaderia */
    public static final String COLUMNNAME_valorMercaderia = "valorMercaderia";

	/** Set valorMercaderia	  */
	public void setvalorMercaderia (BigDecimal valorMercaderia);

	/** Get valorMercaderia	  */
	public BigDecimal getvalorMercaderia();

    /** Column name ValueNumber */
    public static final String COLUMNNAME_ValueNumber = "ValueNumber";

	/** Set Value.
	  * Numeric Value
	  */
	public void setValueNumber (BigDecimal ValueNumber);

	/** Get Value.
	  * Numeric Value
	  */
	public BigDecimal getValueNumber();
}
