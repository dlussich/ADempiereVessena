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

/** Generated Interface for UY_ProvisionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_ProvisionLine 
{

    /** TableName=UY_ProvisionLine */
    public static final String Table_Name = "UY_ProvisionLine";

    /** AD_Table_ID=1000323 */
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

    /** Column name AmtSource */
    public static final String COLUMNNAME_AmtSource = "AmtSource";

	/** Set Source Amount.
	  * Amount Balance in Source Currency
	  */
	public void setAmtSource (BigDecimal AmtSource);

	/** Get Source Amount.
	  * Amount Balance in Source Currency
	  */
	public BigDecimal getAmtSource();

    /** Column name AmtSource1 */
    public static final String COLUMNNAME_AmtSource1 = "AmtSource1";

	/** Set AmtSource1.
	  * AmtSource1
	  */
	public void setAmtSource1 (BigDecimal AmtSource1);

	/** Get AmtSource1.
	  * AmtSource1
	  */
	public BigDecimal getAmtSource1();

    /** Column name AmtSource2 */
    public static final String COLUMNNAME_AmtSource2 = "AmtSource2";

	/** Set AmtSource2.
	  * AmtSource2
	  */
	public void setAmtSource2 (BigDecimal AmtSource2);

	/** Get AmtSource2.
	  * AmtSource2
	  */
	public BigDecimal getAmtSource2();

    /** Column name AmtSource3 */
    public static final String COLUMNNAME_AmtSource3 = "AmtSource3";

	/** Set AmtSource3.
	  * AmtSource3
	  */
	public void setAmtSource3 (BigDecimal AmtSource3);

	/** Get AmtSource3.
	  * AmtSource3
	  */
	public BigDecimal getAmtSource3();

    /** Column name AmtSourceAverage */
    public static final String COLUMNNAME_AmtSourceAverage = "AmtSourceAverage";

	/** Set AmtSourceAverage.
	  * AmtSourceAverage
	  */
	public void setAmtSourceAverage (BigDecimal AmtSourceAverage);

	/** Get AmtSourceAverage.
	  * AmtSourceAverage
	  */
	public BigDecimal getAmtSourceAverage();

    /** Column name C_Activity_ID_1 */
    public static final String COLUMNNAME_C_Activity_ID_1 = "C_Activity_ID_1";

	/** Set C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1);

	/** Get C_Activity_ID_1	  */
	public int getC_Activity_ID_1();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/** Set Unit Price.
	  * Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual);

	/** Get Unit Price.
	  * Actual Price 
	  */
	public BigDecimal getPriceActual();

    /** Column name QtySource */
    public static final String COLUMNNAME_QtySource = "QtySource";

	/** Set QtySource	  */
	public void setQtySource (BigDecimal QtySource);

	/** Get QtySource	  */
	public BigDecimal getQtySource();

    /** Column name QtySource1 */
    public static final String COLUMNNAME_QtySource1 = "QtySource1";

	/** Set QtySource1	  */
	public void setQtySource1 (BigDecimal QtySource1);

	/** Get QtySource1	  */
	public BigDecimal getQtySource1();

    /** Column name QtySource2 */
    public static final String COLUMNNAME_QtySource2 = "QtySource2";

	/** Set QtySource2	  */
	public void setQtySource2 (BigDecimal QtySource2);

	/** Get QtySource2	  */
	public BigDecimal getQtySource2();

    /** Column name QtySource3 */
    public static final String COLUMNNAME_QtySource3 = "QtySource3";

	/** Set QtySource3	  */
	public void setQtySource3 (BigDecimal QtySource3);

	/** Get QtySource3	  */
	public BigDecimal getQtySource3();

    /** Column name QtySourceAverage */
    public static final String COLUMNNAME_QtySourceAverage = "QtySourceAverage";

	/** Set QtySourceAverage	  */
	public void setQtySourceAverage (BigDecimal QtySourceAverage);

	/** Get QtySourceAverage	  */
	public BigDecimal getQtySourceAverage();

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

    /** Column name UY_Provision_ID */
    public static final String COLUMNNAME_UY_Provision_ID = "UY_Provision_ID";

	/** Set UY_Provision	  */
	public void setUY_Provision_ID (int UY_Provision_ID);

	/** Get UY_Provision	  */
	public int getUY_Provision_ID();

	public I_UY_Provision getUY_Provision() throws RuntimeException;

    /** Column name UY_ProvisionLine_ID */
    public static final String COLUMNNAME_UY_ProvisionLine_ID = "UY_ProvisionLine_ID";

	/** Set UY_ProvisionLine	  */
	public void setUY_ProvisionLine_ID (int UY_ProvisionLine_ID);

	/** Get UY_ProvisionLine	  */
	public int getUY_ProvisionLine_ID();

    /** Column name VerifiedType */
    public static final String COLUMNNAME_VerifiedType = "VerifiedType";

	/** Set VerifiedType	  */
	public void setVerifiedType (String VerifiedType);

	/** Get VerifiedType	  */
	public String getVerifiedType();
}
