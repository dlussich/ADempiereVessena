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

/** Generated Interface for UY_CashCountLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_CashCountLine 
{

    /** TableName=UY_CashCountLine */
    public static final String Table_Name = "UY_CashCountLine";

    /** AD_Table_ID=1000951 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name Amount2 */
    public static final String COLUMNNAME_Amount2 = "Amount2";

	/** Set Amount2	  */
	public void setAmount2 (BigDecimal Amount2);

	/** Get Amount2	  */
	public BigDecimal getAmount2();

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

    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/** Set Difference.
	  * Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt);

	/** Get Difference.
	  * Difference Amount
	  */
	public BigDecimal getDifferenceAmt();

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

    /** Column name Quebranto */
    public static final String COLUMNNAME_Quebranto = "Quebranto";

	/** Set Quebranto	  */
	public void setQuebranto (BigDecimal Quebranto);

	/** Get Quebranto	  */
	public BigDecimal getQuebranto();

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

    /** Column name UY_CashCountLine_ID */
    public static final String COLUMNNAME_UY_CashCountLine_ID = "UY_CashCountLine_ID";

	/** Set UY_CashCountLine	  */
	public void setUY_CashCountLine_ID (int UY_CashCountLine_ID);

	/** Get UY_CashCountLine	  */
	public int getUY_CashCountLine_ID();

    /** Column name UY_CashCountPayRule_ID */
    public static final String COLUMNNAME_UY_CashCountPayRule_ID = "UY_CashCountPayRule_ID";

	/** Set UY_CashCountPayRule	  */
	public void setUY_CashCountPayRule_ID (int UY_CashCountPayRule_ID);

	/** Get UY_CashCountPayRule	  */
	public int getUY_CashCountPayRule_ID();

	public I_UY_CashCountPayRule getUY_CashCountPayRule() throws RuntimeException;

    /** Column name UY_RT_CashBox_ID */
    public static final String COLUMNNAME_UY_RT_CashBox_ID = "UY_RT_CashBox_ID";

	/** Set UY_RT_CashBox	  */
	public void setUY_RT_CashBox_ID (int UY_RT_CashBox_ID);

	/** Get UY_RT_CashBox	  */
	public int getUY_RT_CashBox_ID();

	public I_UY_RT_CashBox getUY_RT_CashBox() throws RuntimeException;
}
