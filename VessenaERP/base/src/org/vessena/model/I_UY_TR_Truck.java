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

/** Generated Interface for UY_TR_Truck
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Truck 
{

    /** TableName=UY_TR_Truck */
    public static final String Table_Name = "UY_TR_Truck";

    /** AD_Table_ID=1000725 */
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

    /** Column name anio */
    public static final String COLUMNNAME_anio = "anio";

	/** Set anio	  */
	public void setanio (String anio);

	/** Get anio	  */
	public String getanio();

    /** Column name Arrastre */
    public static final String COLUMNNAME_Arrastre = "Arrastre";

	/** Set Arrastre	  */
	public void setArrastre (BigDecimal Arrastre);

	/** Get Arrastre	  */
	public BigDecimal getArrastre();

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

    /** Column name C_BPartner_ID_Aux */
    public static final String COLUMNNAME_C_BPartner_ID_Aux = "C_BPartner_ID_Aux";

	/** Set C_BPartner_ID_Aux	  */
	public void setC_BPartner_ID_Aux (int C_BPartner_ID_Aux);

	/** Get C_BPartner_ID_Aux	  */
	public int getC_BPartner_ID_Aux();

    /** Column name C_BPartner_ID_P */
    public static final String COLUMNNAME_C_BPartner_ID_P = "C_BPartner_ID_P";

	/** Set C_BPartner_ID_P	  */
	public void setC_BPartner_ID_P (int C_BPartner_ID_P);

	/** Get C_BPartner_ID_P	  */
	public int getC_BPartner_ID_P();

	public org.compiere.model.I_C_BPartner getC_BPartner_I() throws RuntimeException;

    /** Column name ChasisNo */
    public static final String COLUMNNAME_ChasisNo = "ChasisNo";

	/** Set ChasisNo	  */
	public void setChasisNo (String ChasisNo);

	/** Get ChasisNo	  */
	public String getChasisNo();

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

    /** Column name DateAction */
    public static final String COLUMNNAME_DateAction = "DateAction";

	/** Set DateAction	  */
	public void setDateAction (Timestamp DateAction);

	/** Get DateAction	  */
	public Timestamp getDateAction();

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

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

    /** Column name DueDate2 */
    public static final String COLUMNNAME_DueDate2 = "DueDate2";

	/** Set DueDate2	  */
	public void setDueDate2 (Timestamp DueDate2);

	/** Get DueDate2	  */
	public Timestamp getDueDate2();

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

    /** Column name Height */
    public static final String COLUMNNAME_Height = "Height";

	/** Set Height	  */
	public void setHeight (BigDecimal Height);

	/** Get Height	  */
	public BigDecimal getHeight();

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

    /** Column name IsOwn */
    public static final String COLUMNNAME_IsOwn = "IsOwn";

	/** Set IsOwn	  */
	public void setIsOwn (boolean IsOwn);

	/** Get IsOwn	  */
	public boolean isOwn();

    /** Column name IsPledged */
    public static final String COLUMNNAME_IsPledged = "IsPledged";

	/** Set IsPledged	  */
	public void setIsPledged (boolean IsPledged);

	/** Get IsPledged	  */
	public boolean isPledged();

    /** Column name IsPurchased */
    public static final String COLUMNNAME_IsPurchased = "IsPurchased";

	/** Set Purchased.
	  * Organization purchases this product
	  */
	public void setIsPurchased (boolean IsPurchased);

	/** Get Purchased.
	  * Organization purchases this product
	  */
	public boolean isPurchased();

    /** Column name IsSold */
    public static final String COLUMNNAME_IsSold = "IsSold";

	/** Set Sold.
	  * Organization sells this product
	  */
	public void setIsSold (boolean IsSold);

	/** Get Sold.
	  * Organization sells this product
	  */
	public boolean isSold();

    /** Column name MotorNo */
    public static final String COLUMNNAME_MotorNo = "MotorNo";

	/** Set MotorNo	  */
	public void setMotorNo (String MotorNo);

	/** Get MotorNo	  */
	public String getMotorNo();

    /** Column name MTOPAdhesiveNo */
    public static final String COLUMNNAME_MTOPAdhesiveNo = "MTOPAdhesiveNo";

	/** Set MTOPAdhesiveNo	  */
	public void setMTOPAdhesiveNo (String MTOPAdhesiveNo);

	/** Get MTOPAdhesiveNo	  */
	public String getMTOPAdhesiveNo();

    /** Column name MTOPDate */
    public static final String COLUMNNAME_MTOPDate = "MTOPDate";

	/** Set MTOPDate	  */
	public void setMTOPDate (Timestamp MTOPDate);

	/** Get MTOPDate	  */
	public Timestamp getMTOPDate();

    /** Column name MTOPNo */
    public static final String COLUMNNAME_MTOPNo = "MTOPNo";

	/** Set MTOPNo	  */
	public void setMTOPNo (String MTOPNo);

	/** Get MTOPNo	  */
	public String getMTOPNo();

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

    /** Column name PadronNo */
    public static final String COLUMNNAME_PadronNo = "PadronNo";

	/** Set PadronNo	  */
	public void setPadronNo (String PadronNo);

	/** Get PadronNo	  */
	public String getPadronNo();

    /** Column name Permisa */
    public static final String COLUMNNAME_Permisa = "Permisa";

	/** Set Permisa	  */
	public void setPermisa (String Permisa);

	/** Get Permisa	  */
	public String getPermisa();

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

    /** Column name PicPlate_ID */
    public static final String COLUMNNAME_PicPlate_ID = "PicPlate_ID";

	/** Set PicPlate_ID	  */
	public void setPicPlate_ID (int PicPlate_ID);

	/** Get PicPlate_ID	  */
	public int getPicPlate_ID();

    /** Column name PurchaseDate */
    public static final String COLUMNNAME_PurchaseDate = "PurchaseDate";

	/** Set PurchaseDate.
	  * PurchaseDate
	  */
	public void setPurchaseDate (Timestamp PurchaseDate);

	/** Get PurchaseDate.
	  * PurchaseDate
	  */
	public Timestamp getPurchaseDate();

    /** Column name PurchaseType */
    public static final String COLUMNNAME_PurchaseType = "PurchaseType";

	/** Set PurchaseType	  */
	public void setPurchaseType (String PurchaseType);

	/** Get PurchaseType	  */
	public String getPurchaseType();

    /** Column name QtyKm */
    public static final String COLUMNNAME_QtyKm = "QtyKm";

	/** Set QtyKm.
	  * QtyKm
	  */
	public void setQtyKm (int QtyKm);

	/** Get QtyKm.
	  * QtyKm
	  */
	public int getQtyKm();

    /** Column name QtyKmHubo */
    public static final String COLUMNNAME_QtyKmHubo = "QtyKmHubo";

	/** Set QtyKmHubo	  */
	public void setQtyKmHubo (int QtyKmHubo);

	/** Get QtyKmHubo	  */
	public int getQtyKmHubo();

    /** Column name RailType */
    public static final String COLUMNNAME_RailType = "RailType";

	/** Set RailType	  */
	public void setRailType (String RailType);

	/** Get RailType	  */
	public String getRailType();

    /** Column name SoldDate */
    public static final String COLUMNNAME_SoldDate = "SoldDate";

	/** Set SoldDate	  */
	public void setSoldDate (Timestamp SoldDate);

	/** Get SoldDate	  */
	public Timestamp getSoldDate();

    /** Column name SuctaDate */
    public static final String COLUMNNAME_SuctaDate = "SuctaDate";

	/** Set SuctaDate	  */
	public void setSuctaDate (Timestamp SuctaDate);

	/** Get SuctaDate	  */
	public Timestamp getSuctaDate();

    /** Column name SuctaDesc */
    public static final String COLUMNNAME_SuctaDesc = "SuctaDesc";

	/** Set SuctaDesc	  */
	public void setSuctaDesc (String SuctaDesc);

	/** Get SuctaDesc	  */
	public String getSuctaDesc();

    /** Column name SuctaNo */
    public static final String COLUMNNAME_SuctaNo = "SuctaNo";

	/** Set SuctaNo	  */
	public void setSuctaNo (String SuctaNo);

	/** Get SuctaNo	  */
	public String getSuctaNo();

    /** Column name TacografoNo */
    public static final String COLUMNNAME_TacografoNo = "TacografoNo";

	/** Set TacografoNo	  */
	public void setTacografoNo (String TacografoNo);

	/** Get TacografoNo	  */
	public String getTacografoNo();

    /** Column name Tara */
    public static final String COLUMNNAME_Tara = "Tara";

	/** Set Tara	  */
	public void setTara (BigDecimal Tara);

	/** Get Tara	  */
	public BigDecimal getTara();

    /** Column name TermometroNo */
    public static final String COLUMNNAME_TermometroNo = "TermometroNo";

	/** Set TermometroNo	  */
	public void setTermometroNo (String TermometroNo);

	/** Get TermometroNo	  */
	public String getTermometroNo();

    /** Column name TruckMode */
    public static final String COLUMNNAME_TruckMode = "TruckMode";

	/** Set TruckMode	  */
	public void setTruckMode (String TruckMode);

	/** Get TruckMode	  */
	public String getTruckMode();

    /** Column name TruckNo */
    public static final String COLUMNNAME_TruckNo = "TruckNo";

	/** Set TruckNo.
	  * TruckNo
	  */
	public void setTruckNo (String TruckNo);

	/** Get TruckNo.
	  * TruckNo
	  */
	public String getTruckNo();

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

    /** Column name UY_TR_Driver_ID */
    public static final String COLUMNNAME_UY_TR_Driver_ID = "UY_TR_Driver_ID";

	/** Set UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID);

	/** Get UY_TR_Driver	  */
	public int getUY_TR_Driver_ID();

	public I_UY_TR_Driver getUY_TR_Driver() throws RuntimeException;

    /** Column name UY_TR_Driver_ID_2 */
    public static final String COLUMNNAME_UY_TR_Driver_ID_2 = "UY_TR_Driver_ID_2";

	/** Set UY_TR_Driver_ID_2	  */
	public void setUY_TR_Driver_ID_2 (int UY_TR_Driver_ID_2);

	/** Get UY_TR_Driver_ID_2	  */
	public int getUY_TR_Driver_ID_2();

    /** Column name UY_TR_Mark_ID */
    public static final String COLUMNNAME_UY_TR_Mark_ID = "UY_TR_Mark_ID";

	/** Set UY_TR_Mark	  */
	public void setUY_TR_Mark_ID (int UY_TR_Mark_ID);

	/** Get UY_TR_Mark	  */
	public int getUY_TR_Mark_ID();

	public I_UY_TR_Mark getUY_TR_Mark() throws RuntimeException;

    /** Column name UY_TR_MarkModel_ID */
    public static final String COLUMNNAME_UY_TR_MarkModel_ID = "UY_TR_MarkModel_ID";

	/** Set UY_TR_MarkModel	  */
	public void setUY_TR_MarkModel_ID (int UY_TR_MarkModel_ID);

	/** Get UY_TR_MarkModel	  */
	public int getUY_TR_MarkModel_ID();

	public I_UY_TR_MarkModel getUY_TR_MarkModel() throws RuntimeException;

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

    /** Column name UY_TR_Truck_ID_2 */
    public static final String COLUMNNAME_UY_TR_Truck_ID_2 = "UY_TR_Truck_ID_2";

	/** Set UY_TR_Truck_ID_2	  */
	public void setUY_TR_Truck_ID_2 (int UY_TR_Truck_ID_2);

	/** Get UY_TR_Truck_ID_2	  */
	public int getUY_TR_Truck_ID_2();

    /** Column name UY_TR_Truck_ID_Aux */
    public static final String COLUMNNAME_UY_TR_Truck_ID_Aux = "UY_TR_Truck_ID_Aux";

	/** Set UY_TR_Truck_ID_Aux	  */
	public void setUY_TR_Truck_ID_Aux (int UY_TR_Truck_ID_Aux);

	/** Get UY_TR_Truck_ID_Aux	  */
	public int getUY_TR_Truck_ID_Aux();

    /** Column name UY_TR_TruckType_ID */
    public static final String COLUMNNAME_UY_TR_TruckType_ID = "UY_TR_TruckType_ID";

	/** Set UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID);

	/** Get UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID();

	public I_UY_TR_TruckType getUY_TR_TruckType() throws RuntimeException;

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
