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

/** Generated Interface for UY_Molde_Fdu_Caja
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Molde_Fdu_Caja 
{

    /** TableName=UY_Molde_Fdu_Caja */
    public static final String Table_Name = "UY_Molde_Fdu_Caja";

    /** AD_Table_ID=1000703 */
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

    /** Column name entrada */
    public static final String COLUMNNAME_entrada = "entrada";

	/** Set entrada	  */
	public void setentrada (BigDecimal entrada);

	/** Get entrada	  */
	public BigDecimal getentrada();

    /** Column name fecha */
    public static final String COLUMNNAME_fecha = "fecha";

	/** Set fecha	  */
	public void setfecha (Timestamp fecha);

	/** Get fecha	  */
	public Timestamp getfecha();

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

    /** Column name referencia */
    public static final String COLUMNNAME_referencia = "referencia";

	/** Set referencia	  */
	public void setreferencia (String referencia);

	/** Get referencia	  */
	public String getreferencia();

    /** Column name salida */
    public static final String COLUMNNAME_salida = "salida";

	/** Set salida	  */
	public void setsalida (BigDecimal salida);

	/** Get salida	  */
	public BigDecimal getsalida();

    /** Column name tc */
    public static final String COLUMNNAME_tc = "tc";

	/** Set tc	  */
	public void settc (BigDecimal tc);

	/** Get tc	  */
	public BigDecimal gettc();

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

    /** Column name UY_Fdu_Caja_Ref_ID */
    public static final String COLUMNNAME_UY_Fdu_Caja_Ref_ID = "UY_Fdu_Caja_Ref_ID";

	/** Set UY_Fdu_Caja_Ref	  */
	public void setUY_Fdu_Caja_Ref_ID (int UY_Fdu_Caja_Ref_ID);

	/** Get UY_Fdu_Caja_Ref	  */
	public int getUY_Fdu_Caja_Ref_ID();

	public I_UY_Fdu_Caja_Ref getUY_Fdu_Caja_Ref() throws RuntimeException;

    /** Column name UY_Fdu_Caja_Type_ID */
    public static final String COLUMNNAME_UY_Fdu_Caja_Type_ID = "UY_Fdu_Caja_Type_ID";

	/** Set UY_Fdu_Caja_Type	  */
	public void setUY_Fdu_Caja_Type_ID (int UY_Fdu_Caja_Type_ID);

	/** Get UY_Fdu_Caja_Type	  */
	public int getUY_Fdu_Caja_Type_ID();

	public I_UY_Fdu_Caja_Type getUY_Fdu_Caja_Type() throws RuntimeException;

    /** Column name uy_molde_fdu_caja_ID */
    public static final String COLUMNNAME_uy_molde_fdu_caja_ID = "uy_molde_fdu_caja_ID";

	/** Set uy_molde_fdu_caja	  */
	public void setuy_molde_fdu_caja_ID (int uy_molde_fdu_caja_ID);

	/** Get uy_molde_fdu_caja	  */
	public int getuy_molde_fdu_caja_ID();
}
