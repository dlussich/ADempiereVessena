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

/** Generated Interface for UY_FduLoadLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_FduLoadLine 
{

    /** TableName=UY_FduLoadLine */
    public static final String Table_Name = "UY_FduLoadLine";

    /** AD_Table_ID=1000340 */
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

    /** Column name AmtSourceCr */
    public static final String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/** Set Source Credit.
	  * Source Credit Amount
	  */
	public void setAmtSourceCr (BigDecimal AmtSourceCr);

	/** Get Source Credit.
	  * Source Credit Amount
	  */
	public BigDecimal getAmtSourceCr();

    /** Column name AmtSourceDr */
    public static final String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/** Set Source Debit.
	  * Source Debit Amount
	  */
	public void setAmtSourceDr (BigDecimal AmtSourceDr);

	/** Get Source Debit.
	  * Source Debit Amount
	  */
	public BigDecimal getAmtSourceDr();

    /** Column name C_Activity_ID_1 */
    public static final String COLUMNNAME_C_Activity_ID_1 = "C_Activity_ID_1";

	/** Set C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1);

	/** Get C_Activity_ID_1	  */
	public int getC_Activity_ID_1();

    /** Column name C_Activity_ID_2 */
    public static final String COLUMNNAME_C_Activity_ID_2 = "C_Activity_ID_2";

	/** Set C_Activity_ID_2	  */
	public void setC_Activity_ID_2 (int C_Activity_ID_2);

	/** Get C_Activity_ID_2	  */
	public int getC_Activity_ID_2();

    /** Column name C_Activity_ID_3 */
    public static final String COLUMNNAME_C_Activity_ID_3 = "C_Activity_ID_3";

	/** Set C_Activity_ID_3	  */
	public void setC_Activity_ID_3 (int C_Activity_ID_3);

	/** Get C_Activity_ID_3	  */
	public int getC_Activity_ID_3();

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

    /** Column name C_ElementValue_ID_Cr */
    public static final String COLUMNNAME_C_ElementValue_ID_Cr = "C_ElementValue_ID_Cr";

	/** Set C_ElementValue_ID_Cr	  */
	public void setC_ElementValue_ID_Cr (int C_ElementValue_ID_Cr);

	/** Get C_ElementValue_ID_Cr	  */
	public int getC_ElementValue_ID_Cr();

    /** Column name C_ElementValue_ID_Cr2 */
    public static final String COLUMNNAME_C_ElementValue_ID_Cr2 = "C_ElementValue_ID_Cr2";

	/** Set C_ElementValue_ID_Cr2.
	  * C_ElementValue_ID_Cr2
	  */
	public void setC_ElementValue_ID_Cr2 (int C_ElementValue_ID_Cr2);

	/** Get C_ElementValue_ID_Cr2.
	  * C_ElementValue_ID_Cr2
	  */
	public int getC_ElementValue_ID_Cr2();

    /** Column name C_ElementValue_ID_Dr */
    public static final String COLUMNNAME_C_ElementValue_ID_Dr = "C_ElementValue_ID_Dr";

	/** Set C_ElementValue_ID_Dr.
	  * C_ElementValue_ID_Dr
	  */
	public void setC_ElementValue_ID_Dr (int C_ElementValue_ID_Dr);

	/** Get C_ElementValue_ID_Dr.
	  * C_ElementValue_ID_Dr
	  */
	public int getC_ElementValue_ID_Dr();

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

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

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

    /** Column name UY_FduCod_ID */
    public static final String COLUMNNAME_UY_FduCod_ID = "UY_FduCod_ID";

	/** Set UY_FduCod_ID.
	  * UY_FduCod_ID
	  */
	public void setUY_FduCod_ID (int UY_FduCod_ID);

	/** Get UY_FduCod_ID.
	  * UY_FduCod_ID
	  */
	public int getUY_FduCod_ID();

	public I_UY_FduCod getUY_FduCod() throws RuntimeException;

    /** Column name UY_Fdu_GrupoCC_ID */
    public static final String COLUMNNAME_UY_Fdu_GrupoCC_ID = "UY_Fdu_GrupoCC_ID";

	/** Set UY_Fdu_GrupoCC	  */
	public void setUY_Fdu_GrupoCC_ID (int UY_Fdu_GrupoCC_ID);

	/** Get UY_Fdu_GrupoCC	  */
	public int getUY_Fdu_GrupoCC_ID();

	public I_UY_Fdu_GrupoCC getUY_Fdu_GrupoCC() throws RuntimeException;

    /** Column name UY_FduLoad_ID */
    public static final String COLUMNNAME_UY_FduLoad_ID = "UY_FduLoad_ID";

	/** Set UY_FduLoad	  */
	public void setUY_FduLoad_ID (int UY_FduLoad_ID);

	/** Get UY_FduLoad	  */
	public int getUY_FduLoad_ID();

	public I_UY_FduLoad getUY_FduLoad() throws RuntimeException;

    /** Column name UY_FduLoadLine_ID */
    public static final String COLUMNNAME_UY_FduLoadLine_ID = "UY_FduLoadLine_ID";

	/** Set UY_FduLoadLine	  */
	public void setUY_FduLoadLine_ID (int UY_FduLoadLine_ID);

	/** Get UY_FduLoadLine	  */
	public int getUY_FduLoadLine_ID();

    /** Column name UY_Fdu_Store_ID */
    public static final String COLUMNNAME_UY_Fdu_Store_ID = "UY_Fdu_Store_ID";

	/** Set UY_Fdu_Store	  */
	public void setUY_Fdu_Store_ID (int UY_Fdu_Store_ID);

	/** Get UY_Fdu_Store	  */
	public int getUY_Fdu_Store_ID();

	public I_UY_Fdu_Store getUY_Fdu_Store() throws RuntimeException;
}
