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

/** Generated Interface for UY_BPIrpf
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_BPIrpf 
{

    /** TableName=UY_BPIrpf */
    public static final String Table_Name = "UY_BPIrpf";

    /** AD_Table_ID=1000194 */
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

    /** Column name ad_fondosolidaridad */
    public static final String COLUMNNAME_ad_fondosolidaridad = "ad_fondosolidaridad";

	/** Set ad_fondosolidaridad	  */
	public void setad_fondosolidaridad (boolean ad_fondosolidaridad);

	/** Get ad_fondosolidaridad	  */
	public boolean isad_fondosolidaridad();

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

    /** Column name aporte_mensual */
    public static final String COLUMNNAME_aporte_mensual = "aporte_mensual";

	/** Set aporte_mensual	  */
	public void setaporte_mensual (BigDecimal aporte_mensual);

	/** Get aporte_mensual	  */
	public BigDecimal getaporte_mensual();

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

    /** Column name con_discapacidad */
    public static final String COLUMNNAME_con_discapacidad = "con_discapacidad";

	/** Set con_discapacidad	  */
	public void setcon_discapacidad (int con_discapacidad);

	/** Get con_discapacidad	  */
	public int getcon_discapacidad();

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

    /** Column name fondosolidaridad */
    public static final String COLUMNNAME_fondosolidaridad = "fondosolidaridad";

	/** Set fondosolidaridad	  */
	public void setfondosolidaridad (String fondosolidaridad);

	/** Get fondosolidaridad	  */
	public String getfondosolidaridad();

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

    /** Column name min_noimponible */
    public static final String COLUMNNAME_min_noimponible = "min_noimponible";

	/** Set min_noimponible	  */
	public void setmin_noimponible (boolean min_noimponible);

	/** Get min_noimponible	  */
	public boolean ismin_noimponible();

    /** Column name otros */
    public static final String COLUMNNAME_otros = "otros";

	/** Set otros	  */
	public void setotros (BigDecimal otros);

	/** Get otros	  */
	public BigDecimal getotros();

    /** Column name porc_deduccion */
    public static final String COLUMNNAME_porc_deduccion = "porc_deduccion";

	/** Set porc_deduccion	  */
	public void setporc_deduccion (String porc_deduccion);

	/** Get porc_deduccion	  */
	public String getporc_deduccion();

    /** Column name reduccionnf */
    public static final String COLUMNNAME_reduccionnf = "reduccionnf";

	/** Set reduccionnf	  */
	public void setreduccionnf (boolean reduccionnf);

	/** Get reduccionnf	  */
	public boolean isreduccionnf();

    /** Column name sin_discapacidad */
    public static final String COLUMNNAME_sin_discapacidad = "sin_discapacidad";

	/** Set sin_discapacidad	  */
	public void setsin_discapacidad (int sin_discapacidad);

	/** Get sin_discapacidad	  */
	public int getsin_discapacidad();

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

    /** Column name UY_BPIrpf_ID */
    public static final String COLUMNNAME_UY_BPIrpf_ID = "UY_BPIrpf_ID";

	/** Set UY_BPIrpf	  */
	public void setUY_BPIrpf_ID (int UY_BPIrpf_ID);

	/** Get UY_BPIrpf	  */
	public int getUY_BPIrpf_ID();
}
