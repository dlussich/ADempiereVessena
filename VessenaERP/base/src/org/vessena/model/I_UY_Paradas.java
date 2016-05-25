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

/** Generated Interface for UY_Paradas
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_Paradas 
{

    /** TableName=UY_Paradas */
    public static final String Table_Name = "UY_Paradas";

    /** AD_Table_ID=1000049 */
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

	public I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/** Set Manufacturing Order	  */
	public void setPP_Order_ID (int PP_Order_ID);

	/** Get Manufacturing Order	  */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException;

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

    /** Column name UY_Accion */
    public static final String COLUMNNAME_UY_Accion = "UY_Accion";

	/** Set UY_Accion	  */
	public void setUY_Accion (String UY_Accion);

	/** Get UY_Accion	  */
	public String getUY_Accion();

    /** Column name UY_Confirmorderhdr_ID */
    public static final String COLUMNNAME_UY_Confirmorderhdr_ID = "UY_Confirmorderhdr_ID";

	/** Set UY_Confirmorderhdr	  */
	public void setUY_Confirmorderhdr_ID (int UY_Confirmorderhdr_ID);

	/** Get UY_Confirmorderhdr	  */
	public int getUY_Confirmorderhdr_ID();

	public I_UY_Confirmorderhdr getUY_Confirmorderhdr() throws RuntimeException;

    /** Column name UY_Detalle */
    public static final String COLUMNNAME_UY_Detalle = "UY_Detalle";

	/** Set UY_Detalle	  */
	public void setUY_Detalle (String UY_Detalle);

	/** Get UY_Detalle	  */
	public String getUY_Detalle();

    /** Column name UY_horafin */
    public static final String COLUMNNAME_UY_horafin = "UY_horafin";

	/** Set UY_horafin	  */
	public void setUY_horafin (Timestamp UY_horafin);

	/** Get UY_horafin	  */
	public Timestamp getUY_horafin();

    /** Column name UY_horainicio */
    public static final String COLUMNNAME_UY_horainicio = "UY_horainicio";

	/** Set UY_horainicio	  */
	public void setUY_horainicio (Timestamp UY_horainicio);

	/** Get UY_horainicio	  */
	public Timestamp getUY_horainicio();

    /** Column name UY_Paradas_ID */
    public static final String COLUMNNAME_UY_Paradas_ID = "UY_Paradas_ID";

	/** Set UY_Paradas	  */
	public void setUY_Paradas_ID (int UY_Paradas_ID);

	/** Get UY_Paradas	  */
	public int getUY_Paradas_ID();

    /** Column name uy_tipoincidencia */
    public static final String COLUMNNAME_uy_tipoincidencia = "uy_tipoincidencia";

	/** Set uy_tipoincidencia	  */
	public void setuy_tipoincidencia (String uy_tipoincidencia);

	/** Get uy_tipoincidencia	  */
	public String getuy_tipoincidencia();
}
