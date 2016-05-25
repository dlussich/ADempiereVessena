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

/** Generated Interface for UY_TR_TireMoveLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_TireMoveLine 
{

    /** TableName=UY_TR_TireMoveLine */
    public static final String Table_Name = "UY_TR_TireMoveLine";

    /** AD_Table_ID=1000730 */
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

    /** Column name EstadoActual */
    public static final String COLUMNNAME_EstadoActual = "EstadoActual";

	/** Set EstadoActual.
	  * EstadoActual
	  */
	public void setEstadoActual (String EstadoActual);

	/** Get EstadoActual.
	  * EstadoActual
	  */
	public String getEstadoActual();

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

    /** Column name IsAuxiliar */
    public static final String COLUMNNAME_IsAuxiliar = "IsAuxiliar";

	/** Set IsAuxiliar.
	  * IsAuxiliar
	  */
	public void setIsAuxiliar (boolean IsAuxiliar);

	/** Get IsAuxiliar.
	  * IsAuxiliar
	  */
	public boolean isAuxiliar();

    /** Column name IsChanged */
    public static final String COLUMNNAME_IsChanged = "IsChanged";

	/** Set IsChanged	  */
	public void setIsChanged (boolean IsChanged);

	/** Get IsChanged	  */
	public boolean isChanged();

    /** Column name IsRotate */
    public static final String COLUMNNAME_IsRotate = "IsRotate";

	/** Set IsRotate	  */
	public void setIsRotate (boolean IsRotate);

	/** Get IsRotate	  */
	public boolean isRotate();

    /** Column name IsRotated */
    public static final String COLUMNNAME_IsRotated = "IsRotated";

	/** Set IsRotated	  */
	public void setIsRotated (boolean IsRotated);

	/** Get IsRotated	  */
	public boolean isRotated();

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/** Set Locator Key.
	  * Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue);

	/** Get Locator Key.
	  * Key of the Warehouse Locator
	  */
	public int getLocatorValue();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set QtyRecauchutaje.
	  * QtyRecauchutaje
	  */
	public void setQty (int Qty);

	/** Get QtyRecauchutaje.
	  * QtyRecauchutaje
	  */
	public int getQty();

    /** Column name QtyKm */
    public static final String COLUMNNAME_QtyKm = "QtyKm";

	/** Set QtyKm.
	  * QtyKm
	  */
	public void setQtyKm (BigDecimal QtyKm);

	/** Get QtyKm.
	  * QtyKm
	  */
	public BigDecimal getQtyKm();

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

    /** Column name UpdateQty */
    public static final String COLUMNNAME_UpdateQty = "UpdateQty";

	/** Set Update Quantities	  */
	public void setUpdateQty (boolean UpdateQty);

	/** Get Update Quantities	  */
	public boolean isUpdateQty();

    /** Column name UY_TR_Tire_ID */
    public static final String COLUMNNAME_UY_TR_Tire_ID = "UY_TR_Tire_ID";

	/** Set UY_TR_Tire_ID	  */
	public void setUY_TR_Tire_ID (int UY_TR_Tire_ID);

	/** Get UY_TR_Tire_ID	  */
	public int getUY_TR_Tire_ID();

	public I_UY_TR_Tire getUY_TR_Tire() throws RuntimeException;

    /** Column name UY_TR_TireMark_ID */
    public static final String COLUMNNAME_UY_TR_TireMark_ID = "UY_TR_TireMark_ID";

	/** Set UY_TR_TireMark	  */
	public void setUY_TR_TireMark_ID (int UY_TR_TireMark_ID);

	/** Get UY_TR_TireMark	  */
	public int getUY_TR_TireMark_ID();

	public I_UY_TR_TireMark getUY_TR_TireMark() throws RuntimeException;

    /** Column name UY_TR_TireMeasure_ID */
    public static final String COLUMNNAME_UY_TR_TireMeasure_ID = "UY_TR_TireMeasure_ID";

	/** Set UY_TR_TireMeasure	  */
	public void setUY_TR_TireMeasure_ID (int UY_TR_TireMeasure_ID);

	/** Get UY_TR_TireMeasure	  */
	public int getUY_TR_TireMeasure_ID();

	public I_UY_TR_TireMeasure getUY_TR_TireMeasure() throws RuntimeException;

    /** Column name UY_TR_TireModel_ID */
    public static final String COLUMNNAME_UY_TR_TireModel_ID = "UY_TR_TireModel_ID";

	/** Set UY_TR_TireModel	  */
	public void setUY_TR_TireModel_ID (int UY_TR_TireModel_ID);

	/** Get UY_TR_TireModel	  */
	public int getUY_TR_TireModel_ID();

	public I_UY_TR_TireModel getUY_TR_TireModel() throws RuntimeException;

    /** Column name UY_TR_TireMove_ID */
    public static final String COLUMNNAME_UY_TR_TireMove_ID = "UY_TR_TireMove_ID";

	/** Set UY_TR_TireMove	  */
	public void setUY_TR_TireMove_ID (int UY_TR_TireMove_ID);

	/** Get UY_TR_TireMove	  */
	public int getUY_TR_TireMove_ID();

	public I_UY_TR_TireMove getUY_TR_TireMove() throws RuntimeException;

    /** Column name UY_TR_TireMoveLine_ID */
    public static final String COLUMNNAME_UY_TR_TireMoveLine_ID = "UY_TR_TireMoveLine_ID";

	/** Set UY_TR_TireMoveLine	  */
	public void setUY_TR_TireMoveLine_ID (int UY_TR_TireMoveLine_ID);

	/** Get UY_TR_TireMoveLine	  */
	public int getUY_TR_TireMoveLine_ID();
}
