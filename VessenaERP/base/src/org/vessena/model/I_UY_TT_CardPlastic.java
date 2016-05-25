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

/** Generated Interface for UY_TT_CardPlastic
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_CardPlastic 
{

    /** TableName=UY_TT_CardPlastic */
    public static final String Table_Name = "UY_TT_CardPlastic";

    /** AD_Table_ID=1000588 */
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

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Address 1.
	  * Address line 1 for this location
	  */
	public void setAddress1 (String Address1);

	/** Get Address 1.
	  * Address line 1 for this location
	  */
	public String getAddress1();

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

    /** Column name CardType */
    public static final String COLUMNNAME_CardType = "CardType";

	/** Set CardType	  */
	public void setCardType (String CardType);

	/** Get CardType	  */
	public String getCardType();

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

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

    /** Column name CreditLimit */
    public static final String COLUMNNAME_CreditLimit = "CreditLimit";

	/** Set Credit limit.
	  * Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit);

	/** Get Credit limit.
	  * Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (String DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public String getDueDate();

    /** Column name GAFCOD */
    public static final String COLUMNNAME_GAFCOD = "GAFCOD";

	/** Set GAFCOD	  */
	public void setGAFCOD (int GAFCOD);

	/** Get GAFCOD	  */
	public int getGAFCOD();

    /** Column name GAFNOM */
    public static final String COLUMNNAME_GAFNOM = "GAFNOM";

	/** Set GAFNOM	  */
	public void setGAFNOM (String GAFNOM);

	/** Get GAFNOM	  */
	public String getGAFNOM();

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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name IsSent */
    public static final String COLUMNNAME_IsSent = "IsSent";

	/** Set IsSent	  */
	public void setIsSent (boolean IsSent);

	/** Get IsSent	  */
	public boolean isSent();

    /** Column name IsTitular */
    public static final String COLUMNNAME_IsTitular = "IsTitular";

	/** Set IsTitular	  */
	public void setIsTitular (boolean IsTitular);

	/** Get IsTitular	  */
	public boolean isTitular();

    /** Column name localidad */
    public static final String COLUMNNAME_localidad = "localidad";

	/** Set localidad	  */
	public void setlocalidad (String localidad);

	/** Get localidad	  */
	public String getlocalidad();

    /** Column name MLCod */
    public static final String COLUMNNAME_MLCod = "MLCod";

	/** Set MLCod	  */
	public void setMLCod (String MLCod);

	/** Get MLCod	  */
	public String getMLCod();

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

    /** Column name NroTarjetaNueva */
    public static final String COLUMNNAME_NroTarjetaNueva = "NroTarjetaNueva";

	/** Set NroTarjetaNueva	  */
	public void setNroTarjetaNueva (String NroTarjetaNueva);

	/** Get NroTarjetaNueva	  */
	public String getNroTarjetaNueva();

    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/** Set ZIP.
	  * Postal code
	  */
	public void setPostal (String Postal);

	/** Get ZIP.
	  * Postal code
	  */
	public String getPostal();

    /** Column name TipoSocio */
    public static final String COLUMNNAME_TipoSocio = "TipoSocio";

	/** Set TipoSocio	  */
	public void setTipoSocio (int TipoSocio);

	/** Get TipoSocio	  */
	public int getTipoSocio();

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

    /** Column name UY_TT_Card_ID */
    public static final String COLUMNNAME_UY_TT_Card_ID = "UY_TT_Card_ID";

	/** Set UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID);

	/** Get UY_TT_Card	  */
	public int getUY_TT_Card_ID();

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException;

    /** Column name UY_TT_CardPlastic_ID */
    public static final String COLUMNNAME_UY_TT_CardPlastic_ID = "UY_TT_CardPlastic_ID";

	/** Set UY_TT_CardPlastic	  */
	public void setUY_TT_CardPlastic_ID (int UY_TT_CardPlastic_ID);

	/** Get UY_TT_CardPlastic	  */
	public int getUY_TT_CardPlastic_ID();

    /** Column name UY_TT_Receipt_ID */
    public static final String COLUMNNAME_UY_TT_Receipt_ID = "UY_TT_Receipt_ID";

	/** Set UY_TT_Receipt	  */
	public void setUY_TT_Receipt_ID (int UY_TT_Receipt_ID);

	/** Get UY_TT_Receipt	  */
	public int getUY_TT_Receipt_ID();

	public I_UY_TT_Receipt getUY_TT_Receipt() throws RuntimeException;

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
