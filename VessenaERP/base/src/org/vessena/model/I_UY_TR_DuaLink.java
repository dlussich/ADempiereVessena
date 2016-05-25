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

/** Generated Interface for UY_TR_DuaLink
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_DuaLink 
{

    /** TableName=UY_TR_DuaLink */
    public static final String Table_Name = "UY_TR_DuaLink";

    /** AD_Table_ID=1000871 */
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

    /** Column name CantidadBultosAsociacion */
    public static final String COLUMNNAME_CantidadBultosAsociacion = "CantidadBultosAsociacion";

	/** Set CantidadBultosAsociacion	  */
	public void setCantidadBultosAsociacion (BigDecimal CantidadBultosAsociacion);

	/** Get CantidadBultosAsociacion	  */
	public BigDecimal getCantidadBultosAsociacion();

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

    /** Column name descripcion */
    public static final String COLUMNNAME_descripcion = "descripcion";

	/** Set descripcion	  */
	public void setdescripcion (String descripcion);

	/** Get descripcion	  */
	public String getdescripcion();

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

    /** Column name IsDrop */
    public static final String COLUMNNAME_IsDrop = "IsDrop";

	/** Set IsDrop	  */
	public void setIsDrop (boolean IsDrop);

	/** Get IsDrop	  */
	public boolean isDrop();

    /** Column name NumeroMicDna */
    public static final String COLUMNNAME_NumeroMicDna = "NumeroMicDna";

	/** Set NumeroMicDna	  */
	public void setNumeroMicDna (BigDecimal NumeroMicDna);

	/** Get NumeroMicDna	  */
	public BigDecimal getNumeroMicDna();

    /** Column name NumeroSerieItemDua */
    public static final String COLUMNNAME_NumeroSerieItemDua = "NumeroSerieItemDua";

	/** Set NumeroSerieItemDua	  */
	public void setNumeroSerieItemDua (String NumeroSerieItemDua);

	/** Get NumeroSerieItemDua	  */
	public String getNumeroSerieItemDua();

    /** Column name PesoBrutoAsociacion */
    public static final String COLUMNNAME_PesoBrutoAsociacion = "PesoBrutoAsociacion";

	/** Set PesoBrutoAsociacion	  */
	public void setPesoBrutoAsociacion (BigDecimal PesoBrutoAsociacion);

	/** Get PesoBrutoAsociacion	  */
	public BigDecimal getPesoBrutoAsociacion();

    /** Column name PesoNetoAsociacion */
    public static final String COLUMNNAME_PesoNetoAsociacion = "PesoNetoAsociacion";

	/** Set PesoNetoAsociacion	  */
	public void setPesoNetoAsociacion (BigDecimal PesoNetoAsociacion);

	/** Get PesoNetoAsociacion	  */
	public BigDecimal getPesoNetoAsociacion();

    /** Column name StatusAsociation */
    public static final String COLUMNNAME_StatusAsociation = "StatusAsociation";

	/** Set StatusAsociation	  */
	public void setStatusAsociation (String StatusAsociation);

	/** Get StatusAsociation	  */
	public String getStatusAsociation();

    /** Column name tipobulto */
    public static final String COLUMNNAME_tipobulto = "tipobulto";

	/** Set tipobulto	  */
	public void settipobulto (String tipobulto);

	/** Get tipobulto	  */
	public String gettipobulto();

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

    /** Column name UY_TR_Crt_ID */
    public static final String COLUMNNAME_UY_TR_Crt_ID = "UY_TR_Crt_ID";

	/** Set UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID);

	/** Get UY_TR_Crt	  */
	public int getUY_TR_Crt_ID();

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException;

    /** Column name UY_TR_DuaCrt_ID */
    public static final String COLUMNNAME_UY_TR_DuaCrt_ID = "UY_TR_DuaCrt_ID";

	/** Set UY_TR_DuaCrt	  */
	public void setUY_TR_DuaCrt_ID (int UY_TR_DuaCrt_ID);

	/** Get UY_TR_DuaCrt	  */
	public int getUY_TR_DuaCrt_ID();

	public I_UY_TR_DuaCrt getUY_TR_DuaCrt() throws RuntimeException;

    /** Column name UY_TR_Dua_ID */
    public static final String COLUMNNAME_UY_TR_Dua_ID = "UY_TR_Dua_ID";

	/** Set UY_TR_Dua	  */
	public void setUY_TR_Dua_ID (int UY_TR_Dua_ID);

	/** Get UY_TR_Dua	  */
	public int getUY_TR_Dua_ID();

	public I_UY_TR_Dua getUY_TR_Dua() throws RuntimeException;

    /** Column name UY_TR_DuaLine_ID */
    public static final String COLUMNNAME_UY_TR_DuaLine_ID = "UY_TR_DuaLine_ID";

	/** Set UY_TR_DuaLine	  */
	public void setUY_TR_DuaLine_ID (int UY_TR_DuaLine_ID);

	/** Get UY_TR_DuaLine	  */
	public int getUY_TR_DuaLine_ID();

	public I_UY_TR_DuaLine getUY_TR_DuaLine() throws RuntimeException;

    /** Column name UY_TR_DuaLink_ID */
    public static final String COLUMNNAME_UY_TR_DuaLink_ID = "UY_TR_DuaLink_ID";

	/** Set UY_TR_DuaLink	  */
	public void setUY_TR_DuaLink_ID (int UY_TR_DuaLink_ID);

	/** Get UY_TR_DuaLink	  */
	public int getUY_TR_DuaLink_ID();

    /** Column name ValorMercaderiaAsociacion */
    public static final String COLUMNNAME_ValorMercaderiaAsociacion = "ValorMercaderiaAsociacion";

	/** Set ValorMercaderiaAsociacion	  */
	public void setValorMercaderiaAsociacion (BigDecimal ValorMercaderiaAsociacion);

	/** Get ValorMercaderiaAsociacion	  */
	public BigDecimal getValorMercaderiaAsociacion();
}
