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

/** Generated Interface for UY_Estandar_Producciones
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_Estandar_Producciones 
{

    /** TableName=UY_Estandar_Producciones */
    public static final String Table_Name = "UY_Estandar_Producciones";

    /** AD_Table_ID=1000064 */
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

    /** Column name C_UOM_ALM_ID */
    public static final String COLUMNNAME_C_UOM_ALM_ID = "C_UOM_ALM_ID";

	/** Set C_UOM_ALM_ID	  */
	public void setC_UOM_ALM_ID (int C_UOM_ALM_ID);

	/** Get C_UOM_ALM_ID	  */
	public int getC_UOM_ALM_ID();

	public I_C_UOM getC_UOM_ALM() throws RuntimeException;

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name SKU */
    public static final String COLUMNNAME_SKU = "SKU";

	/** Set SKU.
	  * Stock Keeping Unit
	  */
	public void setSKU (String SKU);

	/** Get SKU.
	  * Stock Keeping Unit
	  */
	public String getSKU();

    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/** Set Resource.
	  * Resource
	  */
	public void setS_Resource_ID (int S_Resource_ID);

	/** Get Resource.
	  * Resource
	  */
	public int getS_Resource_ID();

	public I_S_Resource getS_Resource() throws RuntimeException;

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

    /** Column name UY_Cantidad_Operarios_Dir */
    public static final String COLUMNNAME_UY_Cantidad_Operarios_Dir = "UY_Cantidad_Operarios_Dir";

	/** Set UY_Cantidad_Operarios_Dir	  */
	public void setUY_Cantidad_Operarios_Dir (int UY_Cantidad_Operarios_Dir);

	/** Get UY_Cantidad_Operarios_Dir	  */
	public int getUY_Cantidad_Operarios_Dir();

    /** Column name UY_Cantidad_Operarios_Ind */
    public static final String COLUMNNAME_UY_Cantidad_Operarios_Ind = "UY_Cantidad_Operarios_Ind";

	/** Set UY_Cantidad_Operarios_Ind	  */
	public void setUY_Cantidad_Operarios_Ind (int UY_Cantidad_Operarios_Ind);

	/** Get UY_Cantidad_Operarios_Ind	  */
	public int getUY_Cantidad_Operarios_Ind();

    /** Column name UY_EmpaqueHora */
    public static final String COLUMNNAME_UY_EmpaqueHora = "UY_EmpaqueHora";

	/** Set UY_EmpaqueHora	  */
	public void setUY_EmpaqueHora (BigDecimal UY_EmpaqueHora);

	/** Get UY_EmpaqueHora	  */
	public BigDecimal getUY_EmpaqueHora();

    /** Column name UY_Estandar_Producciones_ID */
    public static final String COLUMNNAME_UY_Estandar_Producciones_ID = "UY_Estandar_Producciones_ID";

	/** Set Estandar Producciones	  */
	public void setUY_Estandar_Producciones_ID (int UY_Estandar_Producciones_ID);

	/** Get Estandar Producciones	  */
	public int getUY_Estandar_Producciones_ID();

    /** Column name uy_factor */
    public static final String COLUMNNAME_uy_factor = "uy_factor";

	/** Set uy_factor	  */
	public void setuy_factor (BigDecimal uy_factor);

	/** Get uy_factor	  */
	public BigDecimal getuy_factor();

    /** Column name uy_linea */
    public static final String COLUMNNAME_uy_linea = "uy_linea";

	/** Set uy_linea	  */
	public void setuy_linea (String uy_linea);

	/** Get uy_linea	  */
	public String getuy_linea();

    /** Column name uy_product_teorica */
    public static final String COLUMNNAME_uy_product_teorica = "uy_product_teorica";

	/** Set uy_product_teorica	  */
	public void setuy_product_teorica (BigDecimal uy_product_teorica);

	/** Get uy_product_teorica	  */
	public BigDecimal getuy_product_teorica();

    /** Column name uy_tiempo_paradas */
    public static final String COLUMNNAME_uy_tiempo_paradas = "uy_tiempo_paradas";

	/** Set uy_tiempo_paradas	  */
	public void setuy_tiempo_paradas (int uy_tiempo_paradas);

	/** Get uy_tiempo_paradas	  */
	public int getuy_tiempo_paradas();

    /** Column name uy_tiempo_setup */
    public static final String COLUMNNAME_uy_tiempo_setup = "uy_tiempo_setup";

	/** Set uy_tiempo_setup	  */
	public void setuy_tiempo_setup (int uy_tiempo_setup);

	/** Get uy_tiempo_setup	  */
	public int getuy_tiempo_setup();

    /** Column name uy_unidades_hora */
    public static final String COLUMNNAME_uy_unidades_hora = "uy_unidades_hora";

	/** Set uy_unidades_hora	  */
	public void setuy_unidades_hora (int uy_unidades_hora);

	/** Get uy_unidades_hora	  */
	public int getuy_unidades_hora();

    /** Column name UY_UnidHoraOp */
    public static final String COLUMNNAME_UY_UnidHoraOp = "UY_UnidHoraOp";

	/** Set UY_UnidHoraOp	  */
	public void setUY_UnidHoraOp (BigDecimal UY_UnidHoraOp);

	/** Get UY_UnidHoraOp	  */
	public BigDecimal getUY_UnidHoraOp();
}
