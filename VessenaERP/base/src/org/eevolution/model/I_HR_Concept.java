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
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for HR_Concept
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_HR_Concept 
{

    /** TableName=HR_Concept */
    public static final String Table_Name = "HR_Concept";

    /** AD_Table_ID=53090 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AccountSign */
    public static final String COLUMNNAME_AccountSign = "AccountSign";

	/** Set Account Sign.
	  * Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	public void setAccountSign (String AccountSign);

	/** Get Account Sign.
	  * Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	public String getAccountSign();

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

    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/** Set Reference.
	  * System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/** Get Reference.
	  * System Reference and Validation
	  */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException;

    /** Column name asientoliquidacion */
    public static final String COLUMNNAME_asientoliquidacion = "asientoliquidacion";

	/** Set asientoliquidacion	  */
	public void setasientoliquidacion (boolean asientoliquidacion);

	/** Get asientoliquidacion	  */
	public boolean isasientoliquidacion();

    /** Column name ColumnType */
    public static final String COLUMNNAME_ColumnType = "ColumnType";

	/** Set Column Type	  */
	public void setColumnType (String ColumnType);

	/** Get Column Type	  */
	public String getColumnType();

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

    /** Column name explosion */
    public static final String COLUMNNAME_explosion = "explosion";

	/** Set explosion	  */
	public void setexplosion (boolean explosion);

	/** Get explosion	  */
	public boolean isexplosion();

    /** Column name generaAguinaldo */
    public static final String COLUMNNAME_generaAguinaldo = "generaAguinaldo";

	/** Set generaAguinaldo	  */
	public void setgeneraAguinaldo (boolean generaAguinaldo);

	/** Get generaAguinaldo	  */
	public boolean isgeneraAguinaldo();

    /** Column name grabado_bps */
    public static final String COLUMNNAME_grabado_bps = "grabado_bps";

	/** Set grabado_bps	  */
	public void setgrabado_bps (boolean grabado_bps);

	/** Get grabado_bps	  */
	public boolean isgrabado_bps();

    /** Column name grabado_bse */
    public static final String COLUMNNAME_grabado_bse = "grabado_bse";

	/** Set grabado_bse	  */
	public void setgrabado_bse (boolean grabado_bse);

	/** Get grabado_bse	  */
	public boolean isgrabado_bse();

    /** Column name grabado_irpf */
    public static final String COLUMNNAME_grabado_irpf = "grabado_irpf";

	/** Set grabado_irpf	  */
	public void setgrabado_irpf (boolean grabado_irpf);

	/** Get grabado_irpf	  */
	public boolean isgrabado_irpf();

    /** Column name HR_Concept_Category_ID */
    public static final String COLUMNNAME_HR_Concept_Category_ID = "HR_Concept_Category_ID";

	/** Set Payroll Concept Category	  */
	public void setHR_Concept_Category_ID (int HR_Concept_Category_ID);

	/** Get Payroll Concept Category	  */
	public int getHR_Concept_Category_ID();

	public org.eevolution.model.I_HR_Concept_Category getHR_Concept_Category() throws RuntimeException;

    /** Column name HR_Concept_ID */
    public static final String COLUMNNAME_HR_Concept_ID = "HR_Concept_ID";

	/** Set Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID);

	/** Get Payroll Concept	  */
	public int getHR_Concept_ID();

    /** Column name HR_ConceptPrintInfo_ID */
    public static final String COLUMNNAME_HR_ConceptPrintInfo_ID = "HR_ConceptPrintInfo_ID";

	/** Set HR_ConceptPrintInfo_ID	  */
	public void setHR_ConceptPrintInfo_ID (int HR_ConceptPrintInfo_ID);

	/** Get HR_ConceptPrintInfo_ID	  */
	public int getHR_ConceptPrintInfo_ID();

	public org.eevolution.model.I_HR_Concept getHR_ConceptPrintInfo() throws RuntimeException;

    /** Column name HR_Department_ID */
    public static final String COLUMNNAME_HR_Department_ID = "HR_Department_ID";

	/** Set Payroll Department	  */
	public void setHR_Department_ID (int HR_Department_ID);

	/** Get Payroll Department	  */
	public int getHR_Department_ID();

	public org.eevolution.model.I_HR_Department getHR_Department() throws RuntimeException;

    /** Column name HR_Job_ID */
    public static final String COLUMNNAME_HR_Job_ID = "HR_Job_ID";

	/** Set Payroll Job	  */
	public void setHR_Job_ID (int HR_Job_ID);

	/** Get Payroll Job	  */
	public int getHR_Job_ID();

	public org.eevolution.model.I_HR_Job getHR_Job() throws RuntimeException;

    /** Column name HR_Payroll_ID */
    public static final String COLUMNNAME_HR_Payroll_ID = "HR_Payroll_ID";

	/** Set Payroll	  */
	public void setHR_Payroll_ID (int HR_Payroll_ID);

	/** Get Payroll	  */
	public int getHR_Payroll_ID();

	public org.eevolution.model.I_HR_Payroll getHR_Payroll() throws RuntimeException;

    /** Column name incrementa_bpc */
    public static final String COLUMNNAME_incrementa_bpc = "incrementa_bpc";

	/** Set incrementa_bpc	  */
	public void setincrementa_bpc (boolean incrementa_bpc);

	/** Get incrementa_bpc	  */
	public boolean isincrementa_bpc();

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

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Default.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Default.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name IsEmployee */
    public static final String COLUMNNAME_IsEmployee = "IsEmployee";

	/** Set Employee.
	  * Indicates if  this Business Partner is an employee
	  */
	public void setIsEmployee (boolean IsEmployee);

	/** Get Employee.
	  * Indicates if  this Business Partner is an employee
	  */
	public boolean isEmployee();

    /** Column name IsExclude */
    public static final String COLUMNNAME_IsExclude = "IsExclude";

	/** Set Exclude.
	  * Exclude access to the data - if not selected Include access to the data
	  */
	public void setIsExclude (boolean IsExclude);

	/** Get Exclude.
	  * Exclude access to the data - if not selected Include access to the data
	  */
	public boolean isExclude();

    /** Column name IsInvoiced */
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/** Set Invoiced.
	  * Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced);

	/** Get Invoiced.
	  * Is this invoiced?
	  */
	public boolean isInvoiced();

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manual.
	  * This is a manual process
	  */
	public void setIsManual (boolean IsManual);

	/** Get Manual.
	  * This is a manual process
	  */
	public boolean isManual();

    /** Column name IsPaid */
    public static final String COLUMNNAME_IsPaid = "IsPaid";

	/** Set Paid.
	  * The document is paid
	  */
	public void setIsPaid (boolean IsPaid);

	/** Get Paid.
	  * The document is paid
	  */
	public boolean isPaid();

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Printed.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted);

	/** Get Printed.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

    /** Column name IsReceipt */
    public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/** Set Receipt.
	  * This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt);

	/** Get Receipt.
	  * This is a sales transaction (receipt)
	  */
	public boolean isReceipt();

    /** Column name IsRounding */
    public static final String COLUMNNAME_IsRounding = "IsRounding";

	/** Set IsRounding	  */
	public void setIsRounding (boolean IsRounding);

	/** Get IsRounding	  */
	public boolean isRounding();

    /** Column name IsSaveInHistoric */
    public static final String COLUMNNAME_IsSaveInHistoric = "IsSaveInHistoric";

	/** Set Save In Historic	  */
	public void setIsSaveInHistoric (boolean IsSaveInHistoric);

	/** Get Save In Historic	  */
	public boolean isSaveInHistoric();

    /** Column name IsTransport */
    public static final String COLUMNNAME_IsTransport = "IsTransport";

	/** Set IsTransport	  */
	public void setIsTransport (boolean IsTransport);

	/** Get IsTransport	  */
	public boolean isTransport();

    /** Column name IsTransportLine */
    public static final String COLUMNNAME_IsTransportLine = "IsTransportLine";

	/** Set IsTransportLine	  */
	public void setIsTransportLine (boolean IsTransportLine);

	/** Get IsTransportLine	  */
	public boolean isTransportLine();

    /** Column name IsUpdatePay */
    public static final String COLUMNNAME_IsUpdatePay = "IsUpdatePay";

	/** Set IsUpdatePay	  */
	public void setIsUpdatePay (boolean IsUpdatePay);

	/** Get IsUpdatePay	  */
	public boolean isUpdatePay();

    /** Column name IsViaticoExtranjero */
    public static final String COLUMNNAME_IsViaticoExtranjero = "IsViaticoExtranjero";

	/** Set IsViaticoExtranjero	  */
	public void setIsViaticoExtranjero (boolean IsViaticoExtranjero);

	/** Get IsViaticoExtranjero	  */
	public boolean isViaticoExtranjero();

    /** Column name IsViaticoNacional */
    public static final String COLUMNNAME_IsViaticoNacional = "IsViaticoNacional";

	/** Set IsViaticoNacional	  */
	public void setIsViaticoNacional (boolean IsViaticoNacional);

	/** Get IsViaticoNacional	  */
	public boolean isViaticoNacional();

    /** Column name limite */
    public static final String COLUMNNAME_limite = "limite";

	/** Set limite	  */
	public void setlimite (int limite);

	/** Get limite	  */
	public int getlimite();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name novedad */
    public static final String COLUMNNAME_novedad = "novedad";

	/** Set novedad	  */
	public void setnovedad (boolean novedad);

	/** Get novedad	  */
	public boolean isnovedad();

    /** Column name parahistlaboral */
    public static final String COLUMNNAME_parahistlaboral = "parahistlaboral";

	/** Set parahistlaboral	  */
	public void setparahistlaboral (int parahistlaboral);

	/** Get parahistlaboral	  */
	public int getparahistlaboral();

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

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

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

    /** Column name UY_HRConceptoHistLaboral_ID */
    public static final String COLUMNNAME_UY_HRConceptoHistLaboral_ID = "UY_HRConceptoHistLaboral_ID";

	/** Set UY_HRConceptoHistLaboral	  */
	public void setUY_HRConceptoHistLaboral_ID (int UY_HRConceptoHistLaboral_ID);

	/** Get UY_HRConceptoHistLaboral	  */
	public int getUY_HRConceptoHistLaboral_ID();

    /** Column name UY_HRTipoExplosion_ID */
    public static final String COLUMNNAME_UY_HRTipoExplosion_ID = "UY_HRTipoExplosion_ID";

	/** Set UY_HRTipoExplosion	  */
	public void setUY_HRTipoExplosion_ID (int UY_HRTipoExplosion_ID);

	/** Get UY_HRTipoExplosion	  */
	public int getUY_HRTipoExplosion_ID();

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Valid to.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Valid to.
	  * Valid to including this date (last day)
	  */
	public Timestamp getValidTo();

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
