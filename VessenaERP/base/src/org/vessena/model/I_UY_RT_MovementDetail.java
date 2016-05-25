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

/** Generated Interface for UY_RT_MovementDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_MovementDetail 
{

    /** TableName=UY_RT_MovementDetail */
    public static final String Table_Name = "UY_RT_MovementDetail";

    /** AD_Table_ID=1000998 */
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

    /** Column name AD_Org_ID_To */
    public static final String COLUMNNAME_AD_Org_ID_To = "AD_Org_ID_To";

	/** Set AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To);

	/** Get AD_Org_ID_To	  */
	public int getAD_Org_ID_To();

	public org.compiere.model.I_AD_Org getAD_Org_To() throws RuntimeException;

    /** Column name Cantidad */
    public static final String COLUMNNAME_Cantidad = "Cantidad";

	/** Set Cantidad	  */
	public void setCantidad (BigDecimal Cantidad);

	/** Get Cantidad	  */
	public BigDecimal getCantidad();

    /** Column name codigoarticulo */
    public static final String COLUMNNAME_codigoarticulo = "codigoarticulo";

	/** Set codigoarticulo	  */
	public void setcodigoarticulo (String codigoarticulo);

	/** Get codigoarticulo	  */
	public String getcodigoarticulo();

    /** Column name codigoarticulopadre */
    public static final String COLUMNNAME_codigoarticulopadre = "codigoarticulopadre";

	/** Set codigoarticulopadre	  */
	public void setcodigoarticulopadre (String codigoarticulopadre);

	/** Get codigoarticulopadre	  */
	public String getcodigoarticulopadre();

    /** Column name codigobarras */
    public static final String COLUMNNAME_codigobarras = "codigobarras";

	/** Set codigobarras	  */
	public void setcodigobarras (String codigobarras);

	/** Get codigobarras	  */
	public String getcodigobarras();

    /** Column name codigoservicio */
    public static final String COLUMNNAME_codigoservicio = "codigoservicio";

	/** Set codigoservicio	  */
	public void setcodigoservicio (int codigoservicio);

	/** Get codigoservicio	  */
	public int getcodigoservicio();

    /** Column name codigotipodetalle */
    public static final String COLUMNNAME_codigotipodetalle = "codigotipodetalle";

	/** Set codigotipodetalle	  */
	public void setcodigotipodetalle (int codigotipodetalle);

	/** Get codigotipodetalle	  */
	public int getcodigotipodetalle();

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

    /** Column name descripcionarticulo */
    public static final String COLUMNNAME_descripcionarticulo = "descripcionarticulo";

	/** Set descripcionarticulo	  */
	public void setdescripcionarticulo (String descripcionarticulo);

	/** Get descripcionarticulo	  */
	public String getdescripcionarticulo();

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

    /** Column name descuento */
    public static final String COLUMNNAME_descuento = "descuento";

	/** Set descuento	  */
	public void setdescuento (BigDecimal descuento);

	/** Get descuento	  */
	public BigDecimal getdescuento();

    /** Column name fechaservicio */
    public static final String COLUMNNAME_fechaservicio = "fechaservicio";

	/** Set fechaservicio	  */
	public void setfechaservicio (Timestamp fechaservicio);

	/** Get fechaservicio	  */
	public Timestamp getfechaservicio();

    /** Column name Importe */
    public static final String COLUMNNAME_Importe = "Importe";

	/** Set Importe	  */
	public void setImporte (BigDecimal Importe);

	/** Get Importe	  */
	public BigDecimal getImporte();

    /** Column name importeunitario */
    public static final String COLUMNNAME_importeunitario = "importeunitario";

	/** Set importeunitario	  */
	public void setimporteunitario (BigDecimal importeunitario);

	/** Get importeunitario	  */
	public BigDecimal getimporteunitario();

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

    /** Column name medidaventa */
    public static final String COLUMNNAME_medidaventa = "medidaventa";

	/** Set medidaventa	  */
	public void setmedidaventa (String medidaventa);

	/** Get medidaventa	  */
	public String getmedidaventa();

    /** Column name montoicms */
    public static final String COLUMNNAME_montoicms = "montoicms";

	/** Set montoicms	  */
	public void setmontoicms (BigDecimal montoicms);

	/** Get montoicms	  */
	public BigDecimal getmontoicms();

    /** Column name MontoIVA */
    public static final String COLUMNNAME_MontoIVA = "MontoIVA";

	/** Set MontoIVA	  */
	public void setMontoIVA (BigDecimal MontoIVA);

	/** Get MontoIVA	  */
	public BigDecimal getMontoIVA();

    /** Column name numeroservicio */
    public static final String COLUMNNAME_numeroservicio = "numeroservicio";

	/** Set numeroservicio	  */
	public void setnumeroservicio (String numeroservicio);

	/** Get numeroservicio	  */
	public String getnumeroservicio();

    /** Column name PorcentajeIVA */
    public static final String COLUMNNAME_PorcentajeIVA = "PorcentajeIVA";

	/** Set PorcentajeIVA	  */
	public void setPorcentajeIVA (BigDecimal PorcentajeIVA);

	/** Get PorcentajeIVA	  */
	public BigDecimal getPorcentajeIVA();

    /** Column name tasaicms */
    public static final String COLUMNNAME_tasaicms = "tasaicms";

	/** Set tasaicms	  */
	public void settasaicms (BigDecimal tasaicms);

	/** Get tasaicms	  */
	public BigDecimal gettasaicms();

    /** Column name tipotributosalida */
    public static final String COLUMNNAME_tipotributosalida = "tipotributosalida";

	/** Set tipotributosalida	  */
	public void settipotributosalida (String tipotributosalida);

	/** Get tipotributosalida	  */
	public String gettipotributosalida();

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

    /** Column name UY_RT_MovementDetail_ID */
    public static final String COLUMNNAME_UY_RT_MovementDetail_ID = "UY_RT_MovementDetail_ID";

	/** Set UY_RT_MovementDetail	  */
	public void setUY_RT_MovementDetail_ID (int UY_RT_MovementDetail_ID);

	/** Get UY_RT_MovementDetail	  */
	public int getUY_RT_MovementDetail_ID();

    /** Column name UY_RT_Movement_ID */
    public static final String COLUMNNAME_UY_RT_Movement_ID = "UY_RT_Movement_ID";

	/** Set UY_RT_Movement	  */
	public void setUY_RT_Movement_ID (int UY_RT_Movement_ID);

	/** Get UY_RT_Movement	  */
	public int getUY_RT_Movement_ID();

	public I_UY_RT_Movement getUY_RT_Movement() throws RuntimeException;
}
