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

/** Generated Interface for UY_Fdu_EvolutionConceptsNav
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Fdu_EvolutionConceptsNav 
{

    /** TableName=UY_Fdu_EvolutionConceptsNav */
    public static final String Table_Name = "UY_Fdu_EvolutionConceptsNav";

    /** AD_Table_ID=1000479 */
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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

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

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

    /** Column name GraphicReport */
    public static final String COLUMNNAME_GraphicReport = "GraphicReport";

	/** Set GraphicReport.
	  * GraphicReport
	  */
	public void setGraphicReport (boolean GraphicReport);

	/** Get GraphicReport.
	  * GraphicReport
	  */
	public boolean isGraphicReport();

    /** Column name idReporte */
    public static final String COLUMNNAME_idReporte = "idReporte";

	/** Set Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte);

	/** Get Identificador Unico del Reporte	  */
	public String getidReporte();

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

    /** Column name LastYear */
    public static final String COLUMNNAME_LastYear = "LastYear";

	/** Set LastYear.
	  * LastYear
	  */
	public void setLastYear (boolean LastYear);

	/** Get LastYear.
	  * LastYear
	  */
	public boolean isLastYear();

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

    /** Column name ReportType */
    public static final String COLUMNNAME_ReportType = "ReportType";

	/** Set ReportType	  */
	public void setReportType (String ReportType);

	/** Get ReportType	  */
	public String getReportType();

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

    /** Column name UY_Fdu_EvolutionConceptsNav_ID */
    public static final String COLUMNNAME_UY_Fdu_EvolutionConceptsNav_ID = "UY_Fdu_EvolutionConceptsNav_ID";

	/** Set UY_Fdu_EvolutionConceptsNav	  */
	public void setUY_Fdu_EvolutionConceptsNav_ID (int UY_Fdu_EvolutionConceptsNav_ID);

	/** Get UY_Fdu_EvolutionConceptsNav	  */
	public int getUY_Fdu_EvolutionConceptsNav_ID();

    /** Column name UY_Fdu_LogisticMonthDates_ID */
    public static final String COLUMNNAME_UY_Fdu_LogisticMonthDates_ID = "UY_Fdu_LogisticMonthDates_ID";

	/** Set UY_Fdu_LogisticMonthDates	  */
	public void setUY_Fdu_LogisticMonthDates_ID (int UY_Fdu_LogisticMonthDates_ID);

	/** Get UY_Fdu_LogisticMonthDates	  */
	public int getUY_Fdu_LogisticMonthDates_ID();

	public I_UY_Fdu_LogisticMonthDates getUY_Fdu_LogisticMonthDates() throws RuntimeException;

    /** Column name UY_Fdu_LogisticMonth_ID */
    public static final String COLUMNNAME_UY_Fdu_LogisticMonth_ID = "UY_Fdu_LogisticMonth_ID";

	/** Set UY_Fdu_LogisticMonth	  */
	public void setUY_Fdu_LogisticMonth_ID (int UY_Fdu_LogisticMonth_ID);

	/** Get UY_Fdu_LogisticMonth	  */
	public int getUY_Fdu_LogisticMonth_ID();

	public I_UY_Fdu_LogisticMonth getUY_Fdu_LogisticMonth() throws RuntimeException;

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
