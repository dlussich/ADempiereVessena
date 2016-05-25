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

/** Generated Interface for UY_TT_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_Config 
{

    /** TableName=UY_TT_Config */
    public static final String Table_Name = "UY_TT_Config";

    /** AD_Table_ID=1000570 */
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

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name EmpCodeRedPagos */
    public static final String COLUMNNAME_EmpCodeRedPagos = "EmpCodeRedPagos";

	/** Set EmpCodeRedPagos	  */
	public void setEmpCodeRedPagos (String EmpCodeRedPagos);

	/** Get EmpCodeRedPagos	  */
	public String getEmpCodeRedPagos();

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

    /** Column name IsForzed */
    public static final String COLUMNNAME_IsForzed = "IsForzed";

	/** Set IsForzed.
	  * IsForzed
	  */
	public void setIsForzed (boolean IsForzed);

	/** Get IsForzed.
	  * IsForzed
	  */
	public boolean isForzed();

    /** Column name MailText1 */
    public static final String COLUMNNAME_MailText1 = "MailText1";

	/** Set MailText1	  */
	public void setMailText1 (String MailText1);

	/** Get MailText1	  */
	public String getMailText1();

    /** Column name Mora */
    public static final String COLUMNNAME_Mora = "Mora";

	/** Set Mora.
	  * Mora
	  */
	public void setMora (int Mora);

	/** Get Mora.
	  * Mora
	  */
	public int getMora();

    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/** Set Password.
	  * Password of any length (case sensitive)
	  */
	public void setPassword (String Password);

	/** Get Password.
	  * Password of any length (case sensitive)
	  */
	public String getPassword();

    /** Column name TopBox */
    public static final String COLUMNNAME_TopBox = "TopBox";

	/** Set TopBox	  */
	public void setTopBox (int TopBox);

	/** Get TopBox	  */
	public int getTopBox();

    /** Column name TopSeal */
    public static final String COLUMNNAME_TopSeal = "TopSeal";

	/** Set TopSeal	  */
	public void setTopSeal (int TopSeal);

	/** Get TopSeal	  */
	public int getTopSeal();

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

    /** Column name UY_DeliveryPoint_ID */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID = "UY_DeliveryPoint_ID";

	/** Set UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID);

	/** Get UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID();

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException;

    /** Column name UY_R_Area_ID */
    public static final String COLUMNNAME_UY_R_Area_ID = "UY_R_Area_ID";

	/** Set UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID);

	/** Get UY_R_Area	  */
	public int getUY_R_Area_ID();

	public I_UY_R_Area getUY_R_Area() throws RuntimeException;

    /** Column name UY_R_Area_ID_2 */
    public static final String COLUMNNAME_UY_R_Area_ID_2 = "UY_R_Area_ID_2";

	/** Set UY_R_Area_ID_2	  */
	public void setUY_R_Area_ID_2 (int UY_R_Area_ID_2);

	/** Get UY_R_Area_ID_2	  */
	public int getUY_R_Area_ID_2();

    /** Column name UY_R_PtoResolucion_ID */
    public static final String COLUMNNAME_UY_R_PtoResolucion_ID = "UY_R_PtoResolucion_ID";

	/** Set UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID);

	/** Get UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID();

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException;

    /** Column name UY_R_PtoResolucion_ID_2 */
    public static final String COLUMNNAME_UY_R_PtoResolucion_ID_2 = "UY_R_PtoResolucion_ID_2";

	/** Set UY_R_PtoResolucion_ID_2	  */
	public void setUY_R_PtoResolucion_ID_2 (int UY_R_PtoResolucion_ID_2);

	/** Get UY_R_PtoResolucion_ID_2	  */
	public int getUY_R_PtoResolucion_ID_2();

    /** Column name UY_TT_Config_ID */
    public static final String COLUMNNAME_UY_TT_Config_ID = "UY_TT_Config_ID";

	/** Set UY_TT_Config	  */
	public void setUY_TT_Config_ID (int UY_TT_Config_ID);

	/** Get UY_TT_Config	  */
	public int getUY_TT_Config_ID();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
