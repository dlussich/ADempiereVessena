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

/** Generated Interface for UY_Gerencial_Main
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Gerencial_Main 
{

    /** TableName=UY_Gerencial_Main */
    public static final String Table_Name = "UY_Gerencial_Main";

    /** AD_Table_ID=1000427 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountType */
    public static final String COLUMNNAME_AccountType = "AccountType";

	/** Set Account Type.
	  * Indicates the type of account
	  */
	public void setAccountType (String AccountType);

	/** Get Account Type.
	  * Indicates the type of account
	  */
	public String getAccountType();

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

    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/** Set Process.
	  * Process or Report
	  */
	public void setAD_Process_ID (int AD_Process_ID);

	/** Get Process.
	  * Process or Report
	  */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException;

    /** Column name amt1 */
    public static final String COLUMNNAME_amt1 = "amt1";

	/** Set amt1	  */
	public void setamt1 (BigDecimal amt1);

	/** Get amt1	  */
	public BigDecimal getamt1();

    /** Column name amt10 */
    public static final String COLUMNNAME_amt10 = "amt10";

	/** Set amt10	  */
	public void setamt10 (BigDecimal amt10);

	/** Get amt10	  */
	public BigDecimal getamt10();

    /** Column name amt11 */
    public static final String COLUMNNAME_amt11 = "amt11";

	/** Set amt11	  */
	public void setamt11 (BigDecimal amt11);

	/** Get amt11	  */
	public BigDecimal getamt11();

    /** Column name amt12 */
    public static final String COLUMNNAME_amt12 = "amt12";

	/** Set amt12	  */
	public void setamt12 (BigDecimal amt12);

	/** Get amt12	  */
	public BigDecimal getamt12();

    /** Column name amt13 */
    public static final String COLUMNNAME_amt13 = "amt13";

	/** Set amt13	  */
	public void setamt13 (BigDecimal amt13);

	/** Get amt13	  */
	public BigDecimal getamt13();

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

    /** Column name amt4 */
    public static final String COLUMNNAME_amt4 = "amt4";

	/** Set amt4	  */
	public void setamt4 (BigDecimal amt4);

	/** Get amt4	  */
	public BigDecimal getamt4();

    /** Column name amt5 */
    public static final String COLUMNNAME_amt5 = "amt5";

	/** Set amt5	  */
	public void setamt5 (BigDecimal amt5);

	/** Get amt5	  */
	public BigDecimal getamt5();

    /** Column name amt6 */
    public static final String COLUMNNAME_amt6 = "amt6";

	/** Set amt6	  */
	public void setamt6 (BigDecimal amt6);

	/** Get amt6	  */
	public BigDecimal getamt6();

    /** Column name amt7 */
    public static final String COLUMNNAME_amt7 = "amt7";

	/** Set amt7	  */
	public void setamt7 (BigDecimal amt7);

	/** Get amt7	  */
	public BigDecimal getamt7();

    /** Column name amt8 */
    public static final String COLUMNNAME_amt8 = "amt8";

	/** Set amt8	  */
	public void setamt8 (BigDecimal amt8);

	/** Get amt8	  */
	public BigDecimal getamt8();

    /** Column name amt9 */
    public static final String COLUMNNAME_amt9 = "amt9";

	/** Set amt9	  */
	public void setamt9 (BigDecimal amt9);

	/** Get amt9	  */
	public BigDecimal getamt9();

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

    /** Column name ColorSelector */
    public static final String COLUMNNAME_ColorSelector = "ColorSelector";

	/** Set ColorSelector	  */
	public void setColorSelector (String ColorSelector);

	/** Get ColorSelector	  */
	public String getColorSelector();

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

    /** Column name Parent_ID */
    public static final String COLUMNNAME_Parent_ID = "Parent_ID";

	/** Set Parent.
	  * Parent of Entity
	  */
	public void setParent_ID (int Parent_ID);

	/** Get Parent.
	  * Parent of Entity
	  */
	public int getParent_ID();

    /** Column name parentname */
    public static final String COLUMNNAME_parentname = "parentname";

	/** Set parentname	  */
	public void setparentname (String parentname);

	/** Get parentname	  */
	public String getparentname();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name seqnoparent */
    public static final String COLUMNNAME_seqnoparent = "seqnoparent";

	/** Set seqnoparent	  */
	public void setseqnoparent (int seqnoparent);

	/** Get seqnoparent	  */
	public int getseqnoparent();

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

    /** Column name UY_Gerencial_ID */
    public static final String COLUMNNAME_UY_Gerencial_ID = "UY_Gerencial_ID";

	/** Set UY_Gerencial	  */
	public void setUY_Gerencial_ID (int UY_Gerencial_ID);

	/** Get UY_Gerencial	  */
	public int getUY_Gerencial_ID();

	public I_UY_Gerencial getUY_Gerencial() throws RuntimeException;

    /** Column name UY_Gerencial_Main_ID */
    public static final String COLUMNNAME_UY_Gerencial_Main_ID = "UY_Gerencial_Main_ID";

	/** Set UY_Gerencial_Main	  */
	public void setUY_Gerencial_Main_ID (int UY_Gerencial_Main_ID);

	/** Get UY_Gerencial_Main	  */
	public int getUY_Gerencial_Main_ID();
}
