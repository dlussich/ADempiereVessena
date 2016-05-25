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

/** Generated Interface for UY_ManufOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_ManufOrder 
{

    /** TableName=UY_ManufOrder */
    public static final String Table_Name = "UY_ManufOrder";

    /** AD_Table_ID=1000321 */
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

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException;

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

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/** Set Copy From.
	  * Copy From Record
	  */
	public void setCopyFrom (boolean CopyFrom);

	/** Get Copy From.
	  * Copy From Record
	  */
	public boolean isCopyFrom();

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

    /** Column name DateFinish */
    public static final String COLUMNNAME_DateFinish = "DateFinish";

	/** Set Finish Date.
	  * Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish);

	/** Get Finish Date.
	  * Finish or (planned) completion date
	  */
	public Timestamp getDateFinish();

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

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name InfoText */
    public static final String COLUMNNAME_InfoText = "InfoText";

	/** Set InfoText	  */
	public void setInfoText (String InfoText);

	/** Get InfoText	  */
	public String getInfoText();

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

    /** Column name IsDescription */
    public static final String COLUMNNAME_IsDescription = "IsDescription";

	/** Set Description Only.
	  * if true, the line is just description and no transaction
	  */
	public void setIsDescription (boolean IsDescription);

	/** Get Description Only.
	  * if true, the line is just description and no transaction
	  */
	public boolean isDescription();

    /** Column name IsSecondType */
    public static final String COLUMNNAME_IsSecondType = "IsSecondType";

	/** Set IsSecondType	  */
	public void setIsSecondType (boolean IsSecondType);

	/** Get IsSecondType	  */
	public boolean isSecondType();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name Observaciones2 */
    public static final String COLUMNNAME_Observaciones2 = "Observaciones2";

	/** Set Observaciones2	  */
	public void setObservaciones2 (String Observaciones2);

	/** Get Observaciones2	  */
	public String getObservaciones2();

    /** Column name Path1 */
    public static final String COLUMNNAME_Path1 = "Path1";

	/** Set Path1	  */
	public void setPath1 (String Path1);

	/** Get Path1	  */
	public String getPath1();

    /** Column name Path2 */
    public static final String COLUMNNAME_Path2 = "Path2";

	/** Set Path2	  */
	public void setPath2 (String Path2);

	/** Get Path2	  */
	public String getPath2();

    /** Column name Path3 */
    public static final String COLUMNNAME_Path3 = "Path3";

	/** Set Path3	  */
	public void setPath3 (String Path3);

	/** Get Path3	  */
	public String getPath3();

    /** Column name Pic1_ID */
    public static final String COLUMNNAME_Pic1_ID = "Pic1_ID";

	/** Set Pic1_ID	  */
	public void setPic1_ID (int Pic1_ID);

	/** Get Pic1_ID	  */
	public int getPic1_ID();

    /** Column name Pic2_ID */
    public static final String COLUMNNAME_Pic2_ID = "Pic2_ID";

	/** Set Pic2_ID	  */
	public void setPic2_ID (int Pic2_ID);

	/** Get Pic2_ID	  */
	public int getPic2_ID();

    /** Column name Pic3_ID */
    public static final String COLUMNNAME_Pic3_ID = "Pic3_ID";

	/** Set Pic3_ID	  */
	public void setPic3_ID (int Pic3_ID);

	/** Get Pic3_ID	  */
	public int getPic3_ID();

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

    /** Column name QtyDesign1 */
    public static final String COLUMNNAME_QtyDesign1 = "QtyDesign1";

	/** Set QtyDesign1	  */
	public void setQtyDesign1 (BigDecimal QtyDesign1);

	/** Get QtyDesign1	  */
	public BigDecimal getQtyDesign1();

    /** Column name QtyDesign2 */
    public static final String COLUMNNAME_QtyDesign2 = "QtyDesign2";

	/** Set QtyDesign2	  */
	public void setQtyDesign2 (BigDecimal QtyDesign2);

	/** Get QtyDesign2	  */
	public BigDecimal getQtyDesign2();

    /** Column name QtyDesign3 */
    public static final String COLUMNNAME_QtyDesign3 = "QtyDesign3";

	/** Set QtyDesign3	  */
	public void setQtyDesign3 (BigDecimal QtyDesign3);

	/** Get QtyDesign3	  */
	public BigDecimal getQtyDesign3();

    /** Column name serie */
    public static final String COLUMNNAME_serie = "serie";

	/** Set serie	  */
	public void setserie (String serie);

	/** Get serie	  */
	public String getserie();

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

    /** Column name UY_BudgetDelivery_ID */
    public static final String COLUMNNAME_UY_BudgetDelivery_ID = "UY_BudgetDelivery_ID";

	/** Set UY_BudgetDelivery	  */
	public void setUY_BudgetDelivery_ID (int UY_BudgetDelivery_ID);

	/** Get UY_BudgetDelivery	  */
	public int getUY_BudgetDelivery_ID();

	public I_UY_BudgetDelivery getUY_BudgetDelivery() throws RuntimeException;

    /** Column name UY_GenerateDelivOrder */
    public static final String COLUMNNAME_UY_GenerateDelivOrder = "UY_GenerateDelivOrder";

	/** Set UY_GenerateDelivOrder	  */
	public void setUY_GenerateDelivOrder (String UY_GenerateDelivOrder);

	/** Get UY_GenerateDelivOrder	  */
	public String getUY_GenerateDelivOrder();

    /** Column name UY_ManufOrder_ID */
    public static final String COLUMNNAME_UY_ManufOrder_ID = "UY_ManufOrder_ID";

	/** Set UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID);

	/** Get UY_ManufOrder	  */
	public int getUY_ManufOrder_ID();

    /** Column name UY_PrintMOrder */
    public static final String COLUMNNAME_UY_PrintMOrder = "UY_PrintMOrder";

	/** Set UY_PrintMOrder	  */
	public void setUY_PrintMOrder (String UY_PrintMOrder);

	/** Get UY_PrintMOrder	  */
	public String getUY_PrintMOrder();

    /** Column name UY_PrintMOrder2 */
    public static final String COLUMNNAME_UY_PrintMOrder2 = "UY_PrintMOrder2";

	/** Set UY_PrintMOrder2	  */
	public void setUY_PrintMOrder2 (String UY_PrintMOrder2);

	/** Get UY_PrintMOrder2	  */
	public String getUY_PrintMOrder2();
}
