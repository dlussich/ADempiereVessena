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

/** Generated Interface for UY_MB_StoreLOSaleLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_MB_StoreLOSaleLine 
{

    /** TableName=UY_MB_StoreLOSaleLine */
    public static final String Table_Name = "UY_MB_StoreLOSaleLine";

    /** AD_Table_ID=1001014 */
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

    /** Column name dev_SaleID */
    public static final String COLUMNNAME_dev_SaleID = "dev_SaleID";

	/** Set dev_SaleID	  */
	public void setdev_SaleID (int dev_SaleID);

	/** Get dev_SaleID	  */
	public int getdev_SaleID();

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

    /** Column name LineTotalAmt */
    public static final String COLUMNNAME_LineTotalAmt = "LineTotalAmt";

	/** Set Line Total.
	  * Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt);

	/** Get Line Total.
	  * Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt();

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

    /** Column name productpu */
    public static final String COLUMNNAME_productpu = "productpu";

	/** Set productpu	  */
	public void setproductpu (BigDecimal productpu);

	/** Get productpu	  */
	public BigDecimal getproductpu();

    /** Column name productqty */
    public static final String COLUMNNAME_productqty = "productqty";

	/** Set productqty	  */
	public void setproductqty (BigDecimal productqty);

	/** Get productqty	  */
	public BigDecimal getproductqty();

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

    /** Column name UY_MB_StoreLOSale_ID */
    public static final String COLUMNNAME_UY_MB_StoreLOSale_ID = "UY_MB_StoreLOSale_ID";

	/** Set UY_MB_StoreLOSale	  */
	public void setUY_MB_StoreLOSale_ID (int UY_MB_StoreLOSale_ID);

	/** Get UY_MB_StoreLOSale	  */
	public int getUY_MB_StoreLOSale_ID();

	public I_UY_MB_StoreLOSale getUY_MB_StoreLOSale() throws RuntimeException;

    /** Column name UY_MB_StoreLOSaleLine_ID */
    public static final String COLUMNNAME_UY_MB_StoreLOSaleLine_ID = "UY_MB_StoreLOSaleLine_ID";

	/** Set UY_MB_StoreLOSaleLine	  */
	public void setUY_MB_StoreLOSaleLine_ID (int UY_MB_StoreLOSaleLine_ID);

	/** Get UY_MB_StoreLOSaleLine	  */
	public int getUY_MB_StoreLOSaleLine_ID();
}
