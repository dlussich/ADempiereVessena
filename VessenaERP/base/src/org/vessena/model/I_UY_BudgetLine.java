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

/** Generated Interface for UY_BudgetLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_BudgetLine 
{

    /** TableName=UY_BudgetLine */
    public static final String Table_Name = "UY_BudgetLine";

    /** AD_Table_ID=1000320 */
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

    /** Column name amt1 */
    public static final String COLUMNNAME_amt1 = "amt1";

	/** Set amt1	  */
	public void setamt1 (BigDecimal amt1);

	/** Get amt1	  */
	public BigDecimal getamt1();

    /** Column name amt2 */
    public static final String COLUMNNAME_amt2 = "amt2";

	/** Set amt2	  */
	public void setamt2 (BigDecimal amt2);

	/** Get amt2	  */
	public BigDecimal getamt2();

    /** Column name amt3 */
    public static final String COLUMNNAME_amt3 = "amt3";

	/** Set amt3	  */
	public void setamt3 (BigDecimal amt3);

	/** Get amt3	  */
	public BigDecimal getamt3();

    /** Column name concept */
    public static final String COLUMNNAME_concept = "concept";

	/** Set concept	  */
	public void setconcept (String concept);

	/** Get concept	  */
	public String getconcept();

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

    /** Column name DetailInfo */
    public static final String COLUMNNAME_DetailInfo = "DetailInfo";

	/** Set Detail Information.
	  * Additional Detail Information
	  */
	public void setDetailInfo (String DetailInfo);

	/** Get Detail Information.
	  * Additional Detail Information
	  */
	public String getDetailInfo();

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Discount %.
	  * Discount in percent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Discount %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount();

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

    /** Column name IsApprovedQty1 */
    public static final String COLUMNNAME_IsApprovedQty1 = "IsApprovedQty1";

	/** Set IsApprovedQty1	  */
	public void setIsApprovedQty1 (boolean IsApprovedQty1);

	/** Get IsApprovedQty1	  */
	public boolean isApprovedQty1();

    /** Column name IsApprovedQty2 */
    public static final String COLUMNNAME_IsApprovedQty2 = "IsApprovedQty2";

	/** Set IsApprovedQty2	  */
	public void setIsApprovedQty2 (boolean IsApprovedQty2);

	/** Get IsApprovedQty2	  */
	public boolean isApprovedQty2();

    /** Column name IsApprovedQty3 */
    public static final String COLUMNNAME_IsApprovedQty3 = "IsApprovedQty3";

	/** Set IsApprovedQty3	  */
	public void setIsApprovedQty3 (boolean IsApprovedQty3);

	/** Get IsApprovedQty3	  */
	public boolean isApprovedQty3();

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

    /** Column name price1 */
    public static final String COLUMNNAME_price1 = "price1";

	/** Set price1	  */
	public void setprice1 (BigDecimal price1);

	/** Get price1	  */
	public BigDecimal getprice1();

    /** Column name price2 */
    public static final String COLUMNNAME_price2 = "price2";

	/** Set price2	  */
	public void setprice2 (BigDecimal price2);

	/** Get price2	  */
	public BigDecimal getprice2();

    /** Column name price3 */
    public static final String COLUMNNAME_price3 = "price3";

	/** Set price3	  */
	public void setprice3 (BigDecimal price3);

	/** Get price3	  */
	public BigDecimal getprice3();

    /** Column name qty1 */
    public static final String COLUMNNAME_qty1 = "qty1";

	/** Set qty1	  */
	public void setqty1 (BigDecimal qty1);

	/** Get qty1	  */
	public BigDecimal getqty1();

    /** Column name qty2 */
    public static final String COLUMNNAME_qty2 = "qty2";

	/** Set qty2	  */
	public void setqty2 (BigDecimal qty2);

	/** Get qty2	  */
	public BigDecimal getqty2();

    /** Column name qty3 */
    public static final String COLUMNNAME_qty3 = "qty3";

	/** Set qty3	  */
	public void setqty3 (BigDecimal qty3);

	/** Get qty3	  */
	public BigDecimal getqty3();

    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/** Set Quantity.
	  * The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered);

	/** Get Quantity.
	  * The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered();

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

    /** Column name UY_Budget_ID */
    public static final String COLUMNNAME_UY_Budget_ID = "UY_Budget_ID";

	/** Set UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID);

	/** Get UY_Budget	  */
	public int getUY_Budget_ID();

	public I_UY_Budget getUY_Budget() throws RuntimeException;

    /** Column name UY_BudgetLine_ID */
    public static final String COLUMNNAME_UY_BudgetLine_ID = "UY_BudgetLine_ID";

	/** Set UY_BudgetLine	  */
	public void setUY_BudgetLine_ID (int UY_BudgetLine_ID);

	/** Get UY_BudgetLine	  */
	public int getUY_BudgetLine_ID();
}
