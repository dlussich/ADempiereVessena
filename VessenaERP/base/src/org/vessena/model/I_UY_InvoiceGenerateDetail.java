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

/** Generated Interface for UY_InvoiceGenerateDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_InvoiceGenerateDetail 
{

    /** TableName=UY_InvoiceGenerateDetail */
    public static final String Table_Name = "UY_InvoiceGenerateDetail";

    /** AD_Table_ID=1000893 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name abonado */
    public static final String COLUMNNAME_abonado = "abonado";

	/** Set abonado	  */
	public void setabonado (String abonado);

	/** Get abonado	  */
	public String getabonado();

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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name concepto */
    public static final String COLUMNNAME_concepto = "concepto";

	/** Set concepto	  */
	public void setconcepto (String concepto);

	/** Get concepto	  */
	public String getconcepto();

    /** Column name convenio_cuota_id */
    public static final String COLUMNNAME_convenio_cuota_id = "convenio_cuota_id";

	/** Set convenio_cuota_id	  */
	public void setconvenio_cuota_id (int convenio_cuota_id);

	/** Get convenio_cuota_id	  */
	public int getconvenio_cuota_id();

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

    /** Column name deuda_id */
    public static final String COLUMNNAME_deuda_id = "deuda_id";

	/** Set deuda_id	  */
	public void setdeuda_id (int deuda_id);

	/** Get deuda_id	  */
	public int getdeuda_id();

    /** Column name dias_atraso */
    public static final String COLUMNNAME_dias_atraso = "dias_atraso";

	/** Set dias_atraso	  */
	public void setdias_atraso (int dias_atraso);

	/** Get dias_atraso	  */
	public int getdias_atraso();

    /** Column name empresa_id */
    public static final String COLUMNNAME_empresa_id = "empresa_id";

	/** Set empresa_id	  */
	public void setempresa_id (int empresa_id);

	/** Get empresa_id	  */
	public int getempresa_id();

    /** Column name fecha */
    public static final String COLUMNNAME_fecha = "fecha";

	/** Set fecha	  */
	public void setfecha (Timestamp fecha);

	/** Get fecha	  */
	public Timestamp getfecha();

    /** Column name forma */
    public static final String COLUMNNAME_forma = "forma";

	/** Set forma	  */
	public void setforma (String forma);

	/** Get forma	  */
	public String getforma();

    /** Column name id */
    public static final String COLUMNNAME_id = "id";

	/** Set id	  */
	public void setid (int id);

	/** Get id	  */
	public int getid();

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

    /** Column name lecturaAD360 */
    public static final String COLUMNNAME_lecturaAD360 = "lecturaAD360";

	/** Set lecturaAD360	  */
	public void setlecturaAD360 (Timestamp lecturaAD360);

	/** Get lecturaAD360	  */
	public Timestamp getlecturaAD360();

    /** Column name lugar */
    public static final String COLUMNNAME_lugar = "lugar";

	/** Set lugar	  */
	public void setlugar (String lugar);

	/** Get lugar	  */
	public String getlugar();

    /** Column name moneda_id */
    public static final String COLUMNNAME_moneda_id = "moneda_id";

	/** Set moneda_id	  */
	public void setmoneda_id (int moneda_id);

	/** Get moneda_id	  */
	public int getmoneda_id();

    /** Column name monto */
    public static final String COLUMNNAME_monto = "monto";

	/** Set monto	  */
	public void setmonto (BigDecimal monto);

	/** Get monto	  */
	public BigDecimal getmonto();

    /** Column name numero_recibo */
    public static final String COLUMNNAME_numero_recibo = "numero_recibo";

	/** Set numero_recibo	  */
	public void setnumero_recibo (String numero_recibo);

	/** Get numero_recibo	  */
	public String getnumero_recibo();

    /** Column name pago_borrado_id */
    public static final String COLUMNNAME_pago_borrado_id = "pago_borrado_id";

	/** Set pago_borrado_id	  */
	public void setpago_borrado_id (int pago_borrado_id);

	/** Get pago_borrado_id	  */
	public int getpago_borrado_id();

    /** Column name pago_id */
    public static final String COLUMNNAME_pago_id = "pago_id";

	/** Set pago_id	  */
	public void setpago_id (int pago_id);

	/** Get pago_id	  */
	public int getpago_id();

    /** Column name Percentage */
    public static final String COLUMNNAME_Percentage = "Percentage";

	/** Set Percentage.
	  * Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage);

	/** Get Percentage.
	  * Percent of the entire amount
	  */
	public BigDecimal getPercentage();

    /** Column name persona_documento */
    public static final String COLUMNNAME_persona_documento = "persona_documento";

	/** Set persona_documento	  */
	public void setpersona_documento (String persona_documento);

	/** Get persona_documento	  */
	public String getpersona_documento();

    /** Column name persona_nombre */
    public static final String COLUMNNAME_persona_nombre = "persona_nombre";

	/** Set persona_nombre	  */
	public void setpersona_nombre (String persona_nombre);

	/** Get persona_nombre	  */
	public String getpersona_nombre();

    /** Column name procesadoAD360 */
    public static final String COLUMNNAME_procesadoAD360 = "procesadoAD360";

	/** Set procesadoAD360	  */
	public void setprocesadoAD360 (Timestamp procesadoAD360);

	/** Get procesadoAD360	  */
	public Timestamp getprocesadoAD360();

    /** Column name segmento */
    public static final String COLUMNNAME_segmento = "segmento";

	/** Set segmento	  */
	public void setsegmento (String segmento);

	/** Get segmento	  */
	public String getsegmento();

    /** Column name sub_cartera */
    public static final String COLUMNNAME_sub_cartera = "sub_cartera";

	/** Set sub_cartera	  */
	public void setsub_cartera (String sub_cartera);

	/** Get sub_cartera	  */
	public String getsub_cartera();

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

    /** Column name user_id */
    public static final String COLUMNNAME_user_id = "user_id";

	/** Set user_id	  */
	public void setuser_id (int user_id);

	/** Get user_id	  */
	public int getuser_id();

    /** Column name UY_InvoiceGenerateDetail_ID */
    public static final String COLUMNNAME_UY_InvoiceGenerateDetail_ID = "UY_InvoiceGenerateDetail_ID";

	/** Set UY_InvoiceGenerateDetail	  */
	public void setUY_InvoiceGenerateDetail_ID (int UY_InvoiceGenerateDetail_ID);

	/** Get UY_InvoiceGenerateDetail	  */
	public int getUY_InvoiceGenerateDetail_ID();

    /** Column name UY_InvoiceGenerate_ID */
    public static final String COLUMNNAME_UY_InvoiceGenerate_ID = "UY_InvoiceGenerate_ID";

	/** Set UY_InvoiceGenerate	  */
	public void setUY_InvoiceGenerate_ID (int UY_InvoiceGenerate_ID);

	/** Get UY_InvoiceGenerate	  */
	public int getUY_InvoiceGenerate_ID();

	public I_UY_InvoiceGenerate getUY_InvoiceGenerate() throws RuntimeException;

    /** Column name UY_InvoiceGenerateLine_ID */
    public static final String COLUMNNAME_UY_InvoiceGenerateLine_ID = "UY_InvoiceGenerateLine_ID";

	/** Set UY_InvoiceGenerateLine	  */
	public void setUY_InvoiceGenerateLine_ID (int UY_InvoiceGenerateLine_ID);

	/** Get UY_InvoiceGenerateLine	  */
	public int getUY_InvoiceGenerateLine_ID();

	public I_UY_InvoiceGenerateLine getUY_InvoiceGenerateLine() throws RuntimeException;
}
