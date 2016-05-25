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

/** Generated Interface for UY_TR_MicCont
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_MicCont 
{

    /** TableName=UY_TR_MicCont */
    public static final String Table_Name = "UY_TR_MicCont";

    /** AD_Table_ID=1000781 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AcumuladoBultos */
    public static final String COLUMNNAME_AcumuladoBultos = "AcumuladoBultos";

	/** Set AcumuladoBultos	  */
	public void setAcumuladoBultos (BigDecimal AcumuladoBultos);

	/** Get AcumuladoBultos	  */
	public BigDecimal getAcumuladoBultos();

    /** Column name AcumuladoPeso */
    public static final String COLUMNNAME_AcumuladoPeso = "AcumuladoPeso";

	/** Set AcumuladoPeso	  */
	public void setAcumuladoPeso (BigDecimal AcumuladoPeso);

	/** Get AcumuladoPeso	  */
	public BigDecimal getAcumuladoPeso();

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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name Amount2 */
    public static final String COLUMNNAME_Amount2 = "Amount2";

	/** Set Amount2	  */
	public void setAmount2 (BigDecimal Amount2);

	/** Get Amount2	  */
	public BigDecimal getAmount2();

    /** Column name C_Currency2_ID */
    public static final String COLUMNNAME_C_Currency2_ID = "C_Currency2_ID";

	/** Set C_Currency2_ID	  */
	public void setC_Currency2_ID (int C_Currency2_ID);

	/** Get C_Currency2_ID	  */
	public int getC_Currency2_ID();

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

    /** Column name Consignatario */
    public static final String COLUMNNAME_Consignatario = "Consignatario";

	/** Set Consignatario	  */
	public void setConsignatario (String Consignatario);

	/** Get Consignatario	  */
	public String getConsignatario();

    /** Column name Consignatario2 */
    public static final String COLUMNNAME_Consignatario2 = "Consignatario2";

	/** Set Consignatario2	  */
	public void setConsignatario2 (String Consignatario2);

	/** Get Consignatario2	  */
	public String getConsignatario2();

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

    /** Column name CrtImgNum1 */
    public static final String COLUMNNAME_CrtImgNum1 = "CrtImgNum1";

	/** Set CrtImgNum1	  */
	public void setCrtImgNum1 (String CrtImgNum1);

	/** Get CrtImgNum1	  */
	public String getCrtImgNum1();

    /** Column name CrtImgNum2 */
    public static final String COLUMNNAME_CrtImgNum2 = "CrtImgNum2";

	/** Set CrtImgNum2	  */
	public void setCrtImgNum2 (String CrtImgNum2);

	/** Get CrtImgNum2	  */
	public String getCrtImgNum2();

    /** Column name CrtImgStatus1 */
    public static final String COLUMNNAME_CrtImgStatus1 = "CrtImgStatus1";

	/** Set CrtImgStatus1	  */
	public void setCrtImgStatus1 (String CrtImgStatus1);

	/** Get CrtImgStatus1	  */
	public String getCrtImgStatus1();

    /** Column name CrtImgStatus2 */
    public static final String COLUMNNAME_CrtImgStatus2 = "CrtImgStatus2";

	/** Set CrtImgStatus2	  */
	public void setCrtImgStatus2 (String CrtImgStatus2);

	/** Get CrtImgStatus2	  */
	public String getCrtImgStatus2();

    /** Column name CrtLineStatus1 */
    public static final String COLUMNNAME_CrtLineStatus1 = "CrtLineStatus1";

	/** Set CrtLineStatus1	  */
	public void setCrtLineStatus1 (String CrtLineStatus1);

	/** Get CrtLineStatus1	  */
	public String getCrtLineStatus1();

    /** Column name CrtLineStatus2 */
    public static final String COLUMNNAME_CrtLineStatus2 = "CrtLineStatus2";

	/** Set CrtLineStatus2	  */
	public void setCrtLineStatus2 (String CrtLineStatus2);

	/** Get CrtLineStatus2	  */
	public String getCrtLineStatus2();

    /** Column name CrtStatus1 */
    public static final String COLUMNNAME_CrtStatus1 = "CrtStatus1";

	/** Set CrtStatus1	  */
	public void setCrtStatus1 (String CrtStatus1);

	/** Get CrtStatus1	  */
	public String getCrtStatus1();

    /** Column name CrtStatus2 */
    public static final String COLUMNNAME_CrtStatus2 = "CrtStatus2";

	/** Set CrtStatus2	  */
	public void setCrtStatus2 (String CrtStatus2);

	/** Get CrtStatus2	  */
	public String getCrtStatus2();

    /** Column name descripcion */
    public static final String COLUMNNAME_descripcion = "descripcion";

	/** Set descripcion	  */
	public void setdescripcion (String descripcion);

	/** Get descripcion	  */
	public String getdescripcion();

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

    /** Column name Destinatario */
    public static final String COLUMNNAME_Destinatario = "Destinatario";

	/** Set Destinatario	  */
	public void setDestinatario (String Destinatario);

	/** Get Destinatario	  */
	public String getDestinatario();

    /** Column name Destinatario2 */
    public static final String COLUMNNAME_Destinatario2 = "Destinatario2";

	/** Set Destinatario2	  */
	public void setDestinatario2 (String Destinatario2);

	/** Get Destinatario2	  */
	public String getDestinatario2();

    /** Column name Importe */
    public static final String COLUMNNAME_Importe = "Importe";

	/** Set Importe	  */
	public void setImporte (BigDecimal Importe);

	/** Get Importe	  */
	public BigDecimal getImporte();

    /** Column name Importe2 */
    public static final String COLUMNNAME_Importe2 = "Importe2";

	/** Set Importe2	  */
	public void setImporte2 (BigDecimal Importe2);

	/** Get Importe2	  */
	public BigDecimal getImporte2();

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

    /** Column name LocationComment */
    public static final String COLUMNNAME_LocationComment = "LocationComment";

	/** Set Location comment.
	  * Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment);

	/** Get Location comment.
	  * Additional comments or remarks concerning the location
	  */
	public String getLocationComment();

    /** Column name LocationComment2 */
    public static final String COLUMNNAME_LocationComment2 = "LocationComment2";

	/** Set LocationComment2	  */
	public void setLocationComment2 (String LocationComment2);

	/** Get LocationComment2	  */
	public String getLocationComment2();

    /** Column name Observaciones2 */
    public static final String COLUMNNAME_Observaciones2 = "Observaciones2";

	/** Set Observaciones2	  */
	public void setObservaciones2 (String Observaciones2);

	/** Get Observaciones2	  */
	public String getObservaciones2();

    /** Column name Observaciones3 */
    public static final String COLUMNNAME_Observaciones3 = "Observaciones3";

	/** Set Observaciones3	  */
	public void setObservaciones3 (String Observaciones3);

	/** Get Observaciones3	  */
	public String getObservaciones3();

    /** Column name Observaciones4 */
    public static final String COLUMNNAME_Observaciones4 = "Observaciones4";

	/** Set Observaciones4	  */
	public void setObservaciones4 (String Observaciones4);

	/** Get Observaciones4	  */
	public String getObservaciones4();

    /** Column name pesoBruto */
    public static final String COLUMNNAME_pesoBruto = "pesoBruto";

	/** Set pesoBruto	  */
	public void setpesoBruto (BigDecimal pesoBruto);

	/** Get pesoBruto	  */
	public BigDecimal getpesoBruto();

    /** Column name pesoBruto2 */
    public static final String COLUMNNAME_pesoBruto2 = "pesoBruto2";

	/** Set pesoBruto2	  */
	public void setpesoBruto2 (BigDecimal pesoBruto2);

	/** Get pesoBruto2	  */
	public BigDecimal getpesoBruto2();

    /** Column name pesoNeto */
    public static final String COLUMNNAME_pesoNeto = "pesoNeto";

	/** Set pesoNeto	  */
	public void setpesoNeto (BigDecimal pesoNeto);

	/** Get pesoNeto	  */
	public BigDecimal getpesoNeto();

    /** Column name pesoNeto2 */
    public static final String COLUMNNAME_pesoNeto2 = "pesoNeto2";

	/** Set pesoNeto2	  */
	public void setpesoNeto2 (BigDecimal pesoNeto2);

	/** Get pesoNeto2	  */
	public BigDecimal getpesoNeto2();

    /** Column name precinto */
    public static final String COLUMNNAME_precinto = "precinto";

	/** Set precinto	  */
	public void setprecinto (String precinto);

	/** Get precinto	  */
	public String getprecinto();

    /** Column name Precinto2 */
    public static final String COLUMNNAME_Precinto2 = "Precinto2";

	/** Set Precinto2	  */
	public void setPrecinto2 (String Precinto2);

	/** Get Precinto2	  */
	public String getPrecinto2();

    /** Column name QtyPackage */
    public static final String COLUMNNAME_QtyPackage = "QtyPackage";

	/** Set QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage);

	/** Get QtyPackage	  */
	public BigDecimal getQtyPackage();

    /** Column name QtyPackage2 */
    public static final String COLUMNNAME_QtyPackage2 = "QtyPackage2";

	/** Set QtyPackage2	  */
	public void setQtyPackage2 (BigDecimal QtyPackage2);

	/** Get QtyPackage2	  */
	public BigDecimal getQtyPackage2();

    /** Column name Remitente */
    public static final String COLUMNNAME_Remitente = "Remitente";

	/** Set Remitente	  */
	public void setRemitente (String Remitente);

	/** Get Remitente	  */
	public String getRemitente();

    /** Column name Remitente2 */
    public static final String COLUMNNAME_Remitente2 = "Remitente2";

	/** Set Remitente2	  */
	public void setRemitente2 (String Remitente2);

	/** Get Remitente2	  */
	public String getRemitente2();

    /** Column name SecNoCrt */
    public static final String COLUMNNAME_SecNoCrt = "SecNoCrt";

	/** Set SecNoCrt	  */
	public void setSecNoCrt (String SecNoCrt);

	/** Get SecNoCrt	  */
	public String getSecNoCrt();

    /** Column name SecNoCrt2 */
    public static final String COLUMNNAME_SecNoCrt2 = "SecNoCrt2";

	/** Set SecNoCrt2	  */
	public void setSecNoCrt2 (String SecNoCrt2);

	/** Get SecNoCrt2	  */
	public String getSecNoCrt2();

    /** Column name SecNoImg1 */
    public static final String COLUMNNAME_SecNoImg1 = "SecNoImg1";

	/** Set SecNoImg1	  */
	public void setSecNoImg1 (String SecNoImg1);

	/** Get SecNoImg1	  */
	public String getSecNoImg1();

    /** Column name SecNoImg2 */
    public static final String COLUMNNAME_SecNoImg2 = "SecNoImg2";

	/** Set SecNoImg2	  */
	public void setSecNoImg2 (String SecNoImg2);

	/** Get SecNoImg2	  */
	public String getSecNoImg2();

    /** Column name SecNoLine1 */
    public static final String COLUMNNAME_SecNoLine1 = "SecNoLine1";

	/** Set SecNoLine1	  */
	public void setSecNoLine1 (String SecNoLine1);

	/** Get SecNoLine1	  */
	public String getSecNoLine1();

    /** Column name SecNoLine2 */
    public static final String COLUMNNAME_SecNoLine2 = "SecNoLine2";

	/** Set SecNoLine2	  */
	public void setSecNoLine2 (String SecNoLine2);

	/** Get SecNoLine2	  */
	public String getSecNoLine2();

    /** Column name Seguro */
    public static final String COLUMNNAME_Seguro = "Seguro";

	/** Set Seguro	  */
	public void setSeguro (BigDecimal Seguro);

	/** Get Seguro	  */
	public BigDecimal getSeguro();

    /** Column name Seguro2 */
    public static final String COLUMNNAME_Seguro2 = "Seguro2";

	/** Set Seguro2	  */
	public void setSeguro2 (BigDecimal Seguro2);

	/** Get Seguro2	  */
	public BigDecimal getSeguro2();

    /** Column name sheet */
    public static final String COLUMNNAME_sheet = "sheet";

	/** Set sheet	  */
	public void setsheet (int sheet);

	/** Get sheet	  */
	public int getsheet();

    /** Column name SubtotalBultos */
    public static final String COLUMNNAME_SubtotalBultos = "SubtotalBultos";

	/** Set SubtotalBultos	  */
	public void setSubtotalBultos (BigDecimal SubtotalBultos);

	/** Get SubtotalBultos	  */
	public BigDecimal getSubtotalBultos();

    /** Column name SubtotalPeso */
    public static final String COLUMNNAME_SubtotalPeso = "SubtotalPeso";

	/** Set SubtotalPeso	  */
	public void setSubtotalPeso (BigDecimal SubtotalPeso);

	/** Get SubtotalPeso	  */
	public BigDecimal getSubtotalPeso();

    /** Column name TotalAntBultos */
    public static final String COLUMNNAME_TotalAntBultos = "TotalAntBultos";

	/** Set TotalAntBultos	  */
	public void setTotalAntBultos (BigDecimal TotalAntBultos);

	/** Get TotalAntBultos	  */
	public BigDecimal getTotalAntBultos();

    /** Column name TotalAntPeso */
    public static final String COLUMNNAME_TotalAntPeso = "TotalAntPeso";

	/** Set TotalAntPeso	  */
	public void setTotalAntPeso (BigDecimal TotalAntPeso);

	/** Get TotalAntPeso	  */
	public BigDecimal getTotalAntPeso();

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

    /** Column name UY_TR_Border_ID */
    public static final String COLUMNNAME_UY_TR_Border_ID = "UY_TR_Border_ID";

	/** Set UY_TR_Border	  */
	public void setUY_TR_Border_ID (int UY_TR_Border_ID);

	/** Get UY_TR_Border	  */
	public int getUY_TR_Border_ID();

	public I_UY_TR_Border getUY_TR_Border() throws RuntimeException;

    /** Column name UY_TR_Border_ID_1 */
    public static final String COLUMNNAME_UY_TR_Border_ID_1 = "UY_TR_Border_ID_1";

	/** Set UY_TR_Border_ID_1.
	  * UY_TR_Border_ID_1
	  */
	public void setUY_TR_Border_ID_1 (int UY_TR_Border_ID_1);

	/** Get UY_TR_Border_ID_1.
	  * UY_TR_Border_ID_1
	  */
	public int getUY_TR_Border_ID_1();

    /** Column name UY_TR_Crt_ID */
    public static final String COLUMNNAME_UY_TR_Crt_ID = "UY_TR_Crt_ID";

	/** Set UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID);

	/** Get UY_TR_Crt	  */
	public int getUY_TR_Crt_ID();

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException;

    /** Column name UY_TR_Crt_ID_1 */
    public static final String COLUMNNAME_UY_TR_Crt_ID_1 = "UY_TR_Crt_ID_1";

	/** Set UY_TR_Crt_ID_1	  */
	public void setUY_TR_Crt_ID_1 (int UY_TR_Crt_ID_1);

	/** Get UY_TR_Crt_ID_1	  */
	public int getUY_TR_Crt_ID_1();

    /** Column name UY_TR_MicCont_ID */
    public static final String COLUMNNAME_UY_TR_MicCont_ID = "UY_TR_MicCont_ID";

	/** Set UY_TR_MicCont	  */
	public void setUY_TR_MicCont_ID (int UY_TR_MicCont_ID);

	/** Get UY_TR_MicCont	  */
	public int getUY_TR_MicCont_ID();

    /** Column name UY_TR_Mic_ID */
    public static final String COLUMNNAME_UY_TR_Mic_ID = "UY_TR_Mic_ID";

	/** Set UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID);

	/** Get UY_TR_Mic	  */
	public int getUY_TR_Mic_ID();

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException;

    /** Column name UY_TR_PackageType_ID */
    public static final String COLUMNNAME_UY_TR_PackageType_ID = "UY_TR_PackageType_ID";

	/** Set UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID);

	/** Get UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID();

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException;

    /** Column name UY_TR_PackageType_ID_1 */
    public static final String COLUMNNAME_UY_TR_PackageType_ID_1 = "UY_TR_PackageType_ID_1";

	/** Set UY_TR_PackageType_ID_1	  */
	public void setUY_TR_PackageType_ID_1 (int UY_TR_PackageType_ID_1);

	/** Get UY_TR_PackageType_ID_1	  */
	public int getUY_TR_PackageType_ID_1();
}
