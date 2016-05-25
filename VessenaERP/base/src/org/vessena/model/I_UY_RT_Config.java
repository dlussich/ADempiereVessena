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

/** Generated Interface for UY_RT_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_Config 
{

    /** TableName=UY_RT_Config */
    public static final String Table_Name = "UY_RT_Config";

    /** AD_Table_ID=1000996 */
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

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

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

    /** Column name identifempresa */
    public static final String COLUMNNAME_identifempresa = "identifempresa";

	/** Set identifempresa	  */
	public void setidentifempresa (int identifempresa);

	/** Get identifempresa	  */
	public int getidentifempresa();

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

    /** Column name IsVerified */
    public static final String COLUMNNAME_IsVerified = "IsVerified";

	/** Set Verified.
	  * The BOM configuration has been verified
	  */
	public void setIsVerified (boolean IsVerified);

	/** Get Verified.
	  * The BOM configuration has been verified
	  */
	public boolean isVerified();

    /** Column name mantartbarra */
    public static final String COLUMNNAME_mantartbarra = "mantartbarra";

	/** Set mantartbarra	  */
	public void setmantartbarra (String mantartbarra);

	/** Get mantartbarra	  */
	public String getmantartbarra();

    /** Column name mantarticulo */
    public static final String COLUMNNAME_mantarticulo = "mantarticulo";

	/** Set mantarticulo	  */
	public void setmantarticulo (String mantarticulo);

	/** Get mantarticulo	  */
	public String getmantarticulo();

    /** Column name mantartprecio */
    public static final String COLUMNNAME_mantartprecio = "mantartprecio";

	/** Set mantartprecio	  */
	public void setmantartprecio (String mantartprecio);

	/** Get mantartprecio	  */
	public String getmantartprecio();

    /** Column name mantcaja */
    public static final String COLUMNNAME_mantcaja = "mantcaja";

	/** Set mantcaja	  */
	public void setmantcaja (String mantcaja);

	/** Get mantcaja	  */
	public String getmantcaja();

    /** Column name mantcategoria */
    public static final String COLUMNNAME_mantcategoria = "mantcategoria";

	/** Set mantcategoria	  */
	public void setmantcategoria (String mantcategoria);

	/** Get mantcategoria	  */
	public String getmantcategoria();

    /** Column name mantcliente */
    public static final String COLUMNNAME_mantcliente = "mantcliente";

	/** Set mantcliente	  */
	public void setmantcliente (String mantcliente);

	/** Get mantcliente	  */
	public String getmantcliente();

    /** Column name mantcombo */
    public static final String COLUMNNAME_mantcombo = "mantcombo";

	/** Set mantcombo	  */
	public void setmantcombo (String mantcombo);

	/** Get mantcombo	  */
	public String getmantcombo();

    /** Column name mantfamilia */
    public static final String COLUMNNAME_mantfamilia = "mantfamilia";

	/** Set mantfamilia	  */
	public void setmantfamilia (String mantfamilia);

	/** Get mantfamilia	  */
	public String getmantfamilia();

    /** Column name mantlocal */
    public static final String COLUMNNAME_mantlocal = "mantlocal";

	/** Set mantlocal	  */
	public void setmantlocal (String mantlocal);

	/** Get mantlocal	  */
	public String getmantlocal();

    /** Column name mantmovimiento */
    public static final String COLUMNNAME_mantmovimiento = "mantmovimiento";

	/** Set mantmovimiento	  */
	public void setmantmovimiento (String mantmovimiento);

	/** Get mantmovimiento	  */
	public String getmantmovimiento();

    /** Column name mantpreciolista */
    public static final String COLUMNNAME_mantpreciolista = "mantpreciolista";

	/** Set mantpreciolista	  */
	public void setmantpreciolista (String mantpreciolista);

	/** Get mantpreciolista	  */
	public String getmantpreciolista();

    /** Column name mantpreciolistaprecio */
    public static final String COLUMNNAME_mantpreciolistaprecio = "mantpreciolistaprecio";

	/** Set mantpreciolistaprecio	  */
	public void setmantpreciolistaprecio (String mantpreciolistaprecio);

	/** Get mantpreciolistaprecio	  */
	public String getmantpreciolistaprecio();

    /** Column name mantsemejante */
    public static final String COLUMNNAME_mantsemejante = "mantsemejante";

	/** Set mantsemejante	  */
	public void setmantsemejante (String mantsemejante);

	/** Get mantsemejante	  */
	public String getmantsemejante();

    /** Column name mantsubfamilia */
    public static final String COLUMNNAME_mantsubfamilia = "mantsubfamilia";

	/** Set mantsubfamilia	  */
	public void setmantsubfamilia (String mantsubfamilia);

	/** Get mantsubfamilia	  */
	public String getmantsubfamilia();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name pswscanntech */
    public static final String COLUMNNAME_pswscanntech = "pswscanntech";

	/** Set pswscanntech	  */
	public void setpswscanntech (String pswscanntech);

	/** Get pswscanntech	  */
	public String getpswscanntech();

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

    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/** Set URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL);

	/** Get URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL();

    /** Column name urlmetodo */
    public static final String COLUMNNAME_urlmetodo = "urlmetodo";

	/** Set urlmetodo	  */
	public void seturlmetodo (String urlmetodo);

	/** Get urlmetodo	  */
	public String geturlmetodo();

    /** Column name userscannatech */
    public static final String COLUMNNAME_userscannatech = "userscannatech";

	/** Set userscannatech	  */
	public void setuserscannatech (String userscannatech);

	/** Get userscannatech	  */
	public String getuserscannatech();

    /** Column name UY_RT_Config_ID */
    public static final String COLUMNNAME_UY_RT_Config_ID = "UY_RT_Config_ID";

	/** Set UY_RT_Config	  */
	public void setUY_RT_Config_ID (int UY_RT_Config_ID);

	/** Get UY_RT_Config	  */
	public int getUY_RT_Config_ID();

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
