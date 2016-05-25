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

/** Generated Interface for UY_Fdu_StoreAcct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Fdu_StoreAcct 
{

    /** TableName=UY_Fdu_StoreAcct */
    public static final String Table_Name = "UY_Fdu_StoreAcct";

    /** AD_Table_ID=1000628 */
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

    /** Column name GL_Journal_ID */
    public static final String COLUMNNAME_GL_Journal_ID = "GL_Journal_ID";

	/** Set Journal.
	  * General Ledger Journal
	  */
	public void setGL_Journal_ID (int GL_Journal_ID);

	/** Get Journal.
	  * General Ledger Journal
	  */
	public int getGL_Journal_ID();

	public org.compiere.model.I_GL_Journal getGL_Journal() throws RuntimeException;

    /** Column name GL_JournalLine_ID */
    public static final String COLUMNNAME_GL_JournalLine_ID = "GL_JournalLine_ID";

	/** Set Journal Line.
	  * General Ledger Journal Line
	  */
	public void setGL_JournalLine_ID (int GL_JournalLine_ID);

	/** Get Journal Line.
	  * General Ledger Journal Line
	  */
	public int getGL_JournalLine_ID();

	public org.compiere.model.I_GL_JournalLine getGL_JournalLine() throws RuntimeException;

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

    /** Column name TipoDato */
    public static final String COLUMNNAME_TipoDato = "TipoDato";

	/** Set TipoDato.
	  * TipoDato
	  */
	public void setTipoDato (BigDecimal TipoDato);

	/** Get TipoDato.
	  * TipoDato
	  */
	public BigDecimal getTipoDato();

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

    /** Column name UY_FduFile_ID */
    public static final String COLUMNNAME_UY_FduFile_ID = "UY_FduFile_ID";

	/** Set UY_FduFile_ID	  */
	public void setUY_FduFile_ID (int UY_FduFile_ID);

	/** Get UY_FduFile_ID	  */
	public int getUY_FduFile_ID();

	public I_UY_FduFile getUY_FduFile() throws RuntimeException;

    /** Column name UY_FduLoad_ID */
    public static final String COLUMNNAME_UY_FduLoad_ID = "UY_FduLoad_ID";

	/** Set UY_FduLoad	  */
	public void setUY_FduLoad_ID (int UY_FduLoad_ID);

	/** Get UY_FduLoad	  */
	public int getUY_FduLoad_ID();

	public I_UY_FduLoad getUY_FduLoad() throws RuntimeException;

    /** Column name UY_Fdu_StoreAcct_ID */
    public static final String COLUMNNAME_UY_Fdu_StoreAcct_ID = "UY_Fdu_StoreAcct_ID";

	/** Set UY_Fdu_StoreAcct	  */
	public void setUY_Fdu_StoreAcct_ID (int UY_Fdu_StoreAcct_ID);

	/** Get UY_Fdu_StoreAcct	  */
	public int getUY_Fdu_StoreAcct_ID();

    /** Column name UY_Fdu_Store_ID */
    public static final String COLUMNNAME_UY_Fdu_Store_ID = "UY_Fdu_Store_ID";

	/** Set UY_Fdu_Store	  */
	public void setUY_Fdu_Store_ID (int UY_Fdu_Store_ID);

	/** Get UY_Fdu_Store	  */
	public int getUY_Fdu_Store_ID();

	public I_UY_Fdu_Store getUY_Fdu_Store() throws RuntimeException;
}
