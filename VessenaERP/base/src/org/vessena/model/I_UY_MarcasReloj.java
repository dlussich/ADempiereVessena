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

/** Generated Interface for UY_MarcasReloj
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_MarcasReloj 
{

    /** TableName=UY_MarcasReloj */
    public static final String Table_Name = "UY_MarcasReloj";

    /** AD_Table_ID=1000173 */
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

    /** Column name ErrorReplication */
    public static final String COLUMNNAME_ErrorReplication = "ErrorReplication";

	/** Set ErrorReplication	  */
	public void setErrorReplication (boolean ErrorReplication);

	/** Get ErrorReplication	  */
	public boolean isErrorReplication();

    /** Column name fechahora */
    public static final String COLUMNNAME_fechahora = "fechahora";

	/** Set fechahora	  */
	public void setfechahora (Timestamp fechahora);

	/** Get fechahora	  */
	public Timestamp getfechahora();

    /** Column name FileName */
    public static final String COLUMNNAME_FileName = "FileName";

	/** Set File Name.
	  * Name of the local file or URL
	  */
	public void setFileName (String FileName);

	/** Get File Name.
	  * Name of the local file or URL
	  */
	public String getFileName();

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

    /** Column name manual */
    public static final String COLUMNNAME_manual = "manual";

	/** Set manual	  */
	public void setmanual (boolean manual);

	/** Get manual	  */
	public boolean ismanual();

    /** Column name nrotarjeta */
    public static final String COLUMNNAME_nrotarjeta = "nrotarjeta";

	/** Set nrotarjeta	  */
	public void setnrotarjeta (String nrotarjeta);

	/** Get nrotarjeta	  */
	public String getnrotarjeta();

    /** Column name original_line */
    public static final String COLUMNNAME_original_line = "original_line";

	/** Set original_line	  */
	public void setoriginal_line (String original_line);

	/** Get original_line	  */
	public String getoriginal_line();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Text */
    public static final String COLUMNNAME_Text = "Text";

	/** Set Text	  */
	public void setText (String Text);

	/** Get Text	  */
	public String getText();

    /** Column name tipomarca_id */
    public static final String COLUMNNAME_tipomarca_id = "tipomarca_id";

	/** Set tipomarca_id	  */
	public void settipomarca_id (int tipomarca_id);

	/** Get tipomarca_id	  */
	public int gettipomarca_id();

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

    /** Column name UY_MarcasReloj_ID */
    public static final String COLUMNNAME_UY_MarcasReloj_ID = "UY_MarcasReloj_ID";

	/** Set UY_MarcasReloj	  */
	public void setUY_MarcasReloj_ID (int UY_MarcasReloj_ID);

	/** Get UY_MarcasReloj	  */
	public int getUY_MarcasReloj_ID();
}
