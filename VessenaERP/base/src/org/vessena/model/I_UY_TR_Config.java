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

/** Generated Interface for UY_TR_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Config 
{

    /** TableName=UY_TR_Config */
    public static final String Table_Name = "UY_TR_Config";

    /** AD_Table_ID=1000773 */
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

    /** Column name AD_Client_ID_Aux */
    public static final String COLUMNNAME_AD_Client_ID_Aux = "AD_Client_ID_Aux";

	/** Set AD_Client_ID_Aux	  */
	public void setAD_Client_ID_Aux (int AD_Client_ID_Aux);

	/** Get AD_Client_ID_Aux	  */
	public int getAD_Client_ID_Aux();

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

    /** Column name CityCodeWay */
    public static final String COLUMNNAME_CityCodeWay = "CityCodeWay";

	/** Set CityCodeWay	  */
	public void setCityCodeWay (boolean CityCodeWay);

	/** Get CityCodeWay	  */
	public boolean isCityCodeWay();

    /** Column name ControlTripValue */
    public static final String COLUMNNAME_ControlTripValue = "ControlTripValue";

	/** Set ControlTripValue	  */
	public void setControlTripValue (boolean ControlTripValue);

	/** Get ControlTripValue	  */
	public boolean isControlTripValue();

    /** Column name ConvertedAmt */
    public static final String COLUMNNAME_ConvertedAmt = "ConvertedAmt";

	/** Set Converted Amount.
	  * Converted Amount
	  */
	public void setConvertedAmt (boolean ConvertedAmt);

	/** Get Converted Amount.
	  * Converted Amount
	  */
	public boolean isConvertedAmt();

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

    /** Column name IsOrderValeFlete */
    public static final String COLUMNNAME_IsOrderValeFlete = "IsOrderValeFlete";

	/** Set IsOrderValeFlete.
	  * IsOrderValeFlete
	  */
	public void setIsOrderValeFlete (boolean IsOrderValeFlete);

	/** Get IsOrderValeFlete.
	  * IsOrderValeFlete
	  */
	public boolean isOrderValeFlete();

    /** Column name IsVehiculo */
    public static final String COLUMNNAME_IsVehiculo = "IsVehiculo";

	/** Set IsVehiculo	  */
	public void setIsVehiculo (boolean IsVehiculo);

	/** Get IsVehiculo	  */
	public boolean isVehiculo();

    /** Column name IsYearIncluded */
    public static final String COLUMNNAME_IsYearIncluded = "IsYearIncluded";

	/** Set IsYearIncluded	  */
	public void setIsYearIncluded (boolean IsYearIncluded);

	/** Get IsYearIncluded	  */
	public boolean isYearIncluded();

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

    /** Column name Pic1_ID */
    public static final String COLUMNNAME_Pic1_ID = "Pic1_ID";

	/** Set Pic1_ID	  */
	public void setPic1_ID (int Pic1_ID);

	/** Get Pic1_ID	  */
	public int getPic1_ID();

    /** Column name PrintInvoiceFooter */
    public static final String COLUMNNAME_PrintInvoiceFooter = "PrintInvoiceFooter";

	/** Set PrintInvoiceFooter	  */
	public void setPrintInvoiceFooter (boolean PrintInvoiceFooter);

	/** Get PrintInvoiceFooter	  */
	public boolean isPrintInvoiceFooter();

    /** Column name QtyDecimal */
    public static final String COLUMNNAME_QtyDecimal = "QtyDecimal";

	/** Set QtyDecimal	  */
	public void setQtyDecimal (int QtyDecimal);

	/** Get QtyDecimal	  */
	public int getQtyDecimal();

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

    /** Column name UY_TR_Config_ID */
    public static final String COLUMNNAME_UY_TR_Config_ID = "UY_TR_Config_ID";

	/** Set UY_TR_Config	  */
	public void setUY_TR_Config_ID (int UY_TR_Config_ID);

	/** Get UY_TR_Config	  */
	public int getUY_TR_Config_ID();

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
