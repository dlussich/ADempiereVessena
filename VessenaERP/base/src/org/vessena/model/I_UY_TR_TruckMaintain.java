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

/** Generated Interface for UY_TR_TruckMaintain
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_TruckMaintain 
{

    /** TableName=UY_TR_TruckMaintain */
    public static final String Table_Name = "UY_TR_TruckMaintain";

    /** AD_Table_ID=1000776 */
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

    /** Column name DateLast */
    public static final String COLUMNNAME_DateLast = "DateLast";

	/** Set DateLast	  */
	public void setDateLast (Timestamp DateLast);

	/** Get DateLast	  */
	public Timestamp getDateLast();

    /** Column name DifferenceQty */
    public static final String COLUMNNAME_DifferenceQty = "DifferenceQty";

	/** Set Difference.
	  * Difference Quantity
	  */
	public void setDifferenceQty (int DifferenceQty);

	/** Get Difference.
	  * Difference Quantity
	  */
	public int getDifferenceQty();

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

    /** Column name IsExpired */
    public static final String COLUMNNAME_IsExpired = "IsExpired";

	/** Set IsExpired	  */
	public void setIsExpired (boolean IsExpired);

	/** Get IsExpired	  */
	public boolean isExpired();

    /** Column name Kilometros */
    public static final String COLUMNNAME_Kilometros = "Kilometros";

	/** Set Kilometros	  */
	public void setKilometros (int Kilometros);

	/** Get Kilometros	  */
	public int getKilometros();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

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

    /** Column name QtyKmLast */
    public static final String COLUMNNAME_QtyKmLast = "QtyKmLast";

	/** Set QtyKmLast	  */
	public void setQtyKmLast (int QtyKmLast);

	/** Get QtyKmLast	  */
	public int getQtyKmLast();

    /** Column name QtyKmNext */
    public static final String COLUMNNAME_QtyKmNext = "QtyKmNext";

	/** Set QtyKmNext	  */
	public void setQtyKmNext (int QtyKmNext);

	/** Get QtyKmNext	  */
	public int getQtyKmNext();

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

    /** Column name UY_TR_Maintain_ID */
    public static final String COLUMNNAME_UY_TR_Maintain_ID = "UY_TR_Maintain_ID";

	/** Set UY_TR_Maintain	  */
	public void setUY_TR_Maintain_ID (int UY_TR_Maintain_ID);

	/** Get UY_TR_Maintain	  */
	public int getUY_TR_Maintain_ID();

	public I_UY_TR_Maintain getUY_TR_Maintain() throws RuntimeException;

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException;

    /** Column name UY_TR_TruckMaintain_ID */
    public static final String COLUMNNAME_UY_TR_TruckMaintain_ID = "UY_TR_TruckMaintain_ID";

	/** Set UY_TR_TruckMaintain	  */
	public void setUY_TR_TruckMaintain_ID (int UY_TR_TruckMaintain_ID);

	/** Get UY_TR_TruckMaintain	  */
	public int getUY_TR_TruckMaintain_ID();
}
