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

/** Generated Interface for UY_TR_DuaLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_DuaLine 
{

    /** TableName=UY_TR_DuaLine */
    public static final String Table_Name = "UY_TR_DuaLine";

    /** AD_Table_ID=1000878 */
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

    /** Column name CantidadBultos */
    public static final String COLUMNNAME_CantidadBultos = "CantidadBultos";

	/** Set CantidadBultos	  */
	public void setCantidadBultos (BigDecimal CantidadBultos);

	/** Get CantidadBultos	  */
	public BigDecimal getCantidadBultos();

    /** Column name CantidadBultosRestantes */
    public static final String COLUMNNAME_CantidadBultosRestantes = "CantidadBultosRestantes";

	/** Set CantidadBultosRestantes	  */
	public void setCantidadBultosRestantes (BigDecimal CantidadBultosRestantes);

	/** Get CantidadBultosRestantes	  */
	public BigDecimal getCantidadBultosRestantes();

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

    /** Column name descripcion2 */
    public static final String COLUMNNAME_descripcion2 = "descripcion2";

	/** Set descripcion2	  */
	public void setdescripcion2 (String descripcion2);

	/** Get descripcion2	  */
	public String getdescripcion2();

    /** Column name descripcion3 */
    public static final String COLUMNNAME_descripcion3 = "descripcion3";

	/** Set descripcion3	  */
	public void setdescripcion3 (String descripcion3);

	/** Get descripcion3	  */
	public String getdescripcion3();

    /** Column name descripcion4 */
    public static final String COLUMNNAME_descripcion4 = "descripcion4";

	/** Set descripcion4	  */
	public void setdescripcion4 (String descripcion4);

	/** Get descripcion4	  */
	public String getdescripcion4();

    /** Column name descripcion5 */
    public static final String COLUMNNAME_descripcion5 = "descripcion5";

	/** Set descripcion5	  */
	public void setdescripcion5 (String descripcion5);

	/** Get descripcion5	  */
	public String getdescripcion5();

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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name NumeroSerie */
    public static final String COLUMNNAME_NumeroSerie = "NumeroSerie";

	/** Set NumeroSerie	  */
	public void setNumeroSerie (String NumeroSerie);

	/** Get NumeroSerie	  */
	public String getNumeroSerie();

    /** Column name pesoBruto */
    public static final String COLUMNNAME_pesoBruto = "pesoBruto";

	/** Set pesoBruto	  */
	public void setpesoBruto (BigDecimal pesoBruto);

	/** Get pesoBruto	  */
	public BigDecimal getpesoBruto();

    /** Column name pesoBrutoRestante */
    public static final String COLUMNNAME_pesoBrutoRestante = "pesoBrutoRestante";

	/** Set pesoBrutoRestante	  */
	public void setpesoBrutoRestante (BigDecimal pesoBrutoRestante);

	/** Get pesoBrutoRestante	  */
	public BigDecimal getpesoBrutoRestante();

    /** Column name pesoNeto */
    public static final String COLUMNNAME_pesoNeto = "pesoNeto";

	/** Set pesoNeto	  */
	public void setpesoNeto (BigDecimal pesoNeto);

	/** Get pesoNeto	  */
	public BigDecimal getpesoNeto();

    /** Column name pesoNetoRestante */
    public static final String COLUMNNAME_pesoNetoRestante = "pesoNetoRestante";

	/** Set pesoNetoRestante	  */
	public void setpesoNetoRestante (BigDecimal pesoNetoRestante);

	/** Get pesoNetoRestante	  */
	public BigDecimal getpesoNetoRestante();

    /** Column name TipoBulto */
    public static final String COLUMNNAME_TipoBulto = "TipoBulto";

	/** Set TipoBulto	  */
	public void setTipoBulto (String TipoBulto);

	/** Get TipoBulto	  */
	public String getTipoBulto();

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

    /** Column name UY_TR_Dua_ID */
    public static final String COLUMNNAME_UY_TR_Dua_ID = "UY_TR_Dua_ID";

	/** Set UY_TR_Dua_ID	  */
	public void setUY_TR_Dua_ID (int UY_TR_Dua_ID);

	/** Get UY_TR_Dua_ID	  */
	public int getUY_TR_Dua_ID();

	public I_UY_TR_Dua getUY_TR_Dua() throws RuntimeException;

    /** Column name UY_TR_DuaLine_ID */
    public static final String COLUMNNAME_UY_TR_DuaLine_ID = "UY_TR_DuaLine_ID";

	/** Set UY_TR_DuaLine_ID	  */
	public void setUY_TR_DuaLine_ID (int UY_TR_DuaLine_ID);

	/** Get UY_TR_DuaLine_ID	  */
	public int getUY_TR_DuaLine_ID();
}
