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

/** Generated Interface for UY_HRIrpfLineImpuesto
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_HRIrpfLineImpuesto 
{

    /** TableName=UY_HRIrpfLineImpuesto */
    public static final String Table_Name = "UY_HRIrpfLineImpuesto";

    /** AD_Table_ID=1000207 */
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

    /** Column name anual */
    public static final String COLUMNNAME_anual = "anual";

	/** Set anual	  */
	public void setanual (boolean anual);

	/** Get anual	  */
	public boolean isanual();

    /** Column name bpcdesde */
    public static final String COLUMNNAME_bpcdesde = "bpcdesde";

	/** Set bpcdesde	  */
	public void setbpcdesde (BigDecimal bpcdesde);

	/** Get bpcdesde	  */
	public BigDecimal getbpcdesde();

    /** Column name bpchasta */
    public static final String COLUMNNAME_bpchasta = "bpchasta";

	/** Set bpchasta	  */
	public void setbpchasta (BigDecimal bpchasta);

	/** Get bpchasta	  */
	public BigDecimal getbpchasta();

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

    /** Column name desde */
    public static final String COLUMNNAME_desde = "desde";

	/** Set desde	  */
	public void setdesde (int desde);

	/** Get desde	  */
	public int getdesde();

    /** Column name hasta */
    public static final String COLUMNNAME_hasta = "hasta";

	/** Set hasta	  */
	public void sethasta (int hasta);

	/** Get hasta	  */
	public int gethasta();

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

    /** Column name porcentaje */
    public static final String COLUMNNAME_porcentaje = "porcentaje";

	/** Set porcentaje	  */
	public void setporcentaje (BigDecimal porcentaje);

	/** Get porcentaje	  */
	public BigDecimal getporcentaje();

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

    /** Column name UY_HRIrpf_ID */
    public static final String COLUMNNAME_UY_HRIrpf_ID = "UY_HRIrpf_ID";

	/** Set UY_HRIrpf	  */
	public void setUY_HRIrpf_ID (int UY_HRIrpf_ID);

	/** Get UY_HRIrpf	  */
	public int getUY_HRIrpf_ID();

	public I_UY_HRIrpf getUY_HRIrpf() throws RuntimeException;

    /** Column name UY_HRIrpfLineImpuesto_ID */
    public static final String COLUMNNAME_UY_HRIrpfLineImpuesto_ID = "UY_HRIrpfLineImpuesto_ID";

	/** Set UY_HRIrpfLineImpuesto	  */
	public void setUY_HRIrpfLineImpuesto_ID (int UY_HRIrpfLineImpuesto_ID);

	/** Get UY_HRIrpfLineImpuesto	  */
	public int getUY_HRIrpfLineImpuesto_ID();
}
