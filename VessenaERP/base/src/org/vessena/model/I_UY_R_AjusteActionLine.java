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

/** Generated Interface for UY_R_AjusteActionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_AjusteActionLine 
{

    /** TableName=UY_R_AjusteActionLine */
    public static final String Table_Name = "UY_R_AjusteActionLine";

    /** AD_Table_ID=1000507 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

	/** Set Account No.
	  * Account Number
	  */
	public void setAccountNo (String AccountNo);

	/** Get Account No.
	  * Account Number
	  */
	public String getAccountNo();

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

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

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

    /** Column name DateReclamo */
    public static final String COLUMNNAME_DateReclamo = "DateReclamo";

	/** Set DateReclamo	  */
	public void setDateReclamo (Timestamp DateReclamo);

	/** Get DateReclamo	  */
	public Timestamp getDateReclamo();

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

    /** Column name IsRejected */
    public static final String COLUMNNAME_IsRejected = "IsRejected";

	/** Set IsRejected	  */
	public void setIsRejected (boolean IsRejected);

	/** Get IsRejected	  */
	public boolean isRejected();

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name QtyQuote */
    public static final String COLUMNNAME_QtyQuote = "QtyQuote";

	/** Set QtyQuote	  */
	public void setQtyQuote (BigDecimal QtyQuote);

	/** Get QtyQuote	  */
	public BigDecimal getQtyQuote();

    /** Column name Receptor_ID */
    public static final String COLUMNNAME_Receptor_ID = "Receptor_ID";

	/** Set Receptor_ID	  */
	public void setReceptor_ID (int Receptor_ID);

	/** Get Receptor_ID	  */
	public int getReceptor_ID();

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

    /** Column name UY_R_AjusteAction_ID */
    public static final String COLUMNNAME_UY_R_AjusteAction_ID = "UY_R_AjusteAction_ID";

	/** Set UY_R_AjusteAction	  */
	public void setUY_R_AjusteAction_ID (int UY_R_AjusteAction_ID);

	/** Get UY_R_AjusteAction	  */
	public int getUY_R_AjusteAction_ID();

	public I_UY_R_AjusteAction getUY_R_AjusteAction() throws RuntimeException;

    /** Column name UY_R_AjusteActionLine_ID */
    public static final String COLUMNNAME_UY_R_AjusteActionLine_ID = "UY_R_AjusteActionLine_ID";

	/** Set UY_R_AjusteActionLine	  */
	public void setUY_R_AjusteActionLine_ID (int UY_R_AjusteActionLine_ID);

	/** Get UY_R_AjusteActionLine	  */
	public int getUY_R_AjusteActionLine_ID();

    /** Column name UY_R_Ajuste_ID */
    public static final String COLUMNNAME_UY_R_Ajuste_ID = "UY_R_Ajuste_ID";

	/** Set UY_R_Ajuste	  */
	public void setUY_R_Ajuste_ID (int UY_R_Ajuste_ID);

	/** Get UY_R_Ajuste	  */
	public int getUY_R_Ajuste_ID();

	public I_UY_R_Ajuste getUY_R_Ajuste() throws RuntimeException;

    /** Column name UY_R_AjusteRequest_ID */
    public static final String COLUMNNAME_UY_R_AjusteRequest_ID = "UY_R_AjusteRequest_ID";

	/** Set UY_R_AjusteRequest	  */
	public void setUY_R_AjusteRequest_ID (int UY_R_AjusteRequest_ID);

	/** Get UY_R_AjusteRequest	  */
	public int getUY_R_AjusteRequest_ID();

	public I_UY_R_AjusteRequest getUY_R_AjusteRequest() throws RuntimeException;

    /** Column name UY_R_AjusteRequestLine_ID */
    public static final String COLUMNNAME_UY_R_AjusteRequestLine_ID = "UY_R_AjusteRequestLine_ID";

	/** Set UY_R_AjusteRequestLine	  */
	public void setUY_R_AjusteRequestLine_ID (int UY_R_AjusteRequestLine_ID);

	/** Get UY_R_AjusteRequestLine	  */
	public int getUY_R_AjusteRequestLine_ID();

	public I_UY_R_AjusteRequestLine getUY_R_AjusteRequestLine() throws RuntimeException;

    /** Column name UY_R_Canal_ID */
    public static final String COLUMNNAME_UY_R_Canal_ID = "UY_R_Canal_ID";

	/** Set UY_R_Canal	  */
	public void setUY_R_Canal_ID (int UY_R_Canal_ID);

	/** Get UY_R_Canal	  */
	public int getUY_R_Canal_ID();

	public I_UY_R_Canal getUY_R_Canal() throws RuntimeException;

    /** Column name UY_R_HandlerAjuste_ID */
    public static final String COLUMNNAME_UY_R_HandlerAjuste_ID = "UY_R_HandlerAjuste_ID";

	/** Set UY_R_HandlerAjuste	  */
	public void setUY_R_HandlerAjuste_ID (int UY_R_HandlerAjuste_ID);

	/** Get UY_R_HandlerAjuste	  */
	public int getUY_R_HandlerAjuste_ID();

	public I_UY_R_HandlerAjuste getUY_R_HandlerAjuste() throws RuntimeException;

    /** Column name UY_R_Reclamo_ID */
    public static final String COLUMNNAME_UY_R_Reclamo_ID = "UY_R_Reclamo_ID";

	/** Set UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID);

	/** Get UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID();

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException;
}
