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

/** Generated Interface for UY_TT_RetPrintValeCard
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_RetPrintValeCard 
{

    /** TableName=UY_TT_RetPrintValeCard */
    public static final String Table_Name = "UY_TT_RetPrintValeCard";

    /** AD_Table_ID=1010758 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

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

    /** Column name CardAction */
    public static final String COLUMNNAME_CardAction = "CardAction";

	/** Set CardAction	  */
	public void setCardAction (String CardAction);

	/** Get CardAction	  */
	public String getCardAction();

    /** Column name CardDestination */
    public static final String COLUMNNAME_CardDestination = "CardDestination";

	/** Set CardDestination	  */
	public void setCardDestination (String CardDestination);

	/** Get CardDestination	  */
	public String getCardDestination();

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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

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

    /** Column name GrpCtaCte */
    public static final String COLUMNNAME_GrpCtaCte = "GrpCtaCte";

	/** Set GrpCtaCte	  */
	public void setGrpCtaCte (int GrpCtaCte);

	/** Get GrpCtaCte	  */
	public int getGrpCtaCte();

    /** Column name InvalidText */
    public static final String COLUMNNAME_InvalidText = "InvalidText";

	/** Set InvalidText	  */
	public void setInvalidText (String InvalidText);

	/** Get InvalidText	  */
	public String getInvalidText();

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

    /** Column name IsRetained */
    public static final String COLUMNNAME_IsRetained = "IsRetained";

	/** Set IsRetained	  */
	public void setIsRetained (boolean IsRetained);

	/** Get IsRetained	  */
	public boolean isRetained();

    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/** Set Valid.
	  * Element is valid
	  */
	public void setIsValid (boolean IsValid);

	/** Get Valid.
	  * Element is valid
	  */
	public boolean isValid();

    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/** Set Locator Key.
	  * Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue);

	/** Get Locator Key.
	  * Key of the Warehouse Locator
	  */
	public int getLocatorValue();

    /** Column name MLCod */
    public static final String COLUMNNAME_MLCod = "MLCod";

	/** Set MLCod	  */
	public void setMLCod (String MLCod);

	/** Get MLCod	  */
	public String getMLCod();

    /** Column name ProductoAux */
    public static final String COLUMNNAME_ProductoAux = "ProductoAux";

	/** Set ProductoAux	  */
	public void setProductoAux (String ProductoAux);

	/** Get ProductoAux	  */
	public String getProductoAux();

    /** Column name ScanText */
    public static final String COLUMNNAME_ScanText = "ScanText";

	/** Set ScanText	  */
	public void setScanText (String ScanText);

	/** Get ScanText	  */
	public String getScanText();

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

    /** Column name UY_TT_Box_ID */
    public static final String COLUMNNAME_UY_TT_Box_ID = "UY_TT_Box_ID";

	/** Set UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID);

	/** Get UY_TT_Box	  */
	public int getUY_TT_Box_ID();

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException;

    /** Column name UY_TT_Card_ID */
    public static final String COLUMNNAME_UY_TT_Card_ID = "UY_TT_Card_ID";

	/** Set UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID);

	/** Get UY_TT_Card	  */
	public int getUY_TT_Card_ID();

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException;

    /** Column name UY_TT_RetPrintValeCard_ID */
    public static final String COLUMNNAME_UY_TT_RetPrintValeCard_ID = "UY_TT_RetPrintValeCard_ID";

	/** Set UY_TT_RetPrintValeCard	  */
	public void setUY_TT_RetPrintValeCard_ID (int UY_TT_RetPrintValeCard_ID);

	/** Get UY_TT_RetPrintValeCard	  */
	public int getUY_TT_RetPrintValeCard_ID();

    /** Column name UY_TT_RetPrintVale_ID */
    public static final String COLUMNNAME_UY_TT_RetPrintVale_ID = "UY_TT_RetPrintVale_ID";

	/** Set UY_TT_RetPrintVale	  */
	public void setUY_TT_RetPrintVale_ID (int UY_TT_RetPrintVale_ID);

	/** Get UY_TT_RetPrintVale	  */
	public int getUY_TT_RetPrintVale_ID();

	public I_UY_TT_RetPrintVale getUY_TT_RetPrintVale() throws RuntimeException;
}
