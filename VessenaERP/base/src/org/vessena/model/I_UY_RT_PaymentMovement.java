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

/** Generated Interface for UY_RT_PaymentMovement
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_RT_PaymentMovement 
{

    /** TableName=UY_RT_PaymentMovement */
    public static final String Table_Name = "UY_RT_PaymentMovement";

    /** AD_Table_ID=1000999 */
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

    /** Column name bin */
    public static final String COLUMNNAME_bin = "bin";

	/** Set bin	  */
	public void setbin (String bin);

	/** Get bin	  */
	public String getbin();

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

    /** Column name codigomoneda */
    public static final String COLUMNNAME_codigomoneda = "codigomoneda";

	/** Set codigomoneda	  */
	public void setcodigomoneda (String codigomoneda);

	/** Get codigomoneda	  */
	public String getcodigomoneda();

    /** Column name codigoplanpagos */
    public static final String COLUMNNAME_codigoplanpagos = "codigoplanpagos";

	/** Set codigoplanpagos	  */
	public void setcodigoplanpagos (int codigoplanpagos);

	/** Get codigoplanpagos	  */
	public int getcodigoplanpagos();

    /** Column name codigotipopago */
    public static final String COLUMNNAME_codigotipopago = "codigotipopago";

	/** Set codigotipopago	  */
	public void setcodigotipopago (int codigotipopago);

	/** Get codigotipopago	  */
	public int getcodigotipopago();

    /** Column name cotizacioncompra */
    public static final String COLUMNNAME_cotizacioncompra = "cotizacioncompra";

	/** Set cotizacioncompra	  */
	public void setcotizacioncompra (BigDecimal cotizacioncompra);

	/** Get cotizacioncompra	  */
	public BigDecimal getcotizacioncompra();

    /** Column name cotizacionventa */
    public static final String COLUMNNAME_cotizacionventa = "cotizacionventa";

	/** Set cotizacionventa	  */
	public void setcotizacionventa (BigDecimal cotizacionventa);

	/** Get cotizacionventa	  */
	public BigDecimal getcotizacionventa();

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

    /** Column name descripcionformapago */
    public static final String COLUMNNAME_descripcionformapago = "descripcionformapago";

	/** Set descripcionformapago	  */
	public void setdescripcionformapago (String descripcionformapago);

	/** Get descripcionformapago	  */
	public String getdescripcionformapago();

    /** Column name descripcionplanpagos */
    public static final String COLUMNNAME_descripcionplanpagos = "descripcionplanpagos";

	/** Set descripcionplanpagos	  */
	public void setdescripcionplanpagos (String descripcionplanpagos);

	/** Get descripcionplanpagos	  */
	public String getdescripcionplanpagos();

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

    /** Column name documentocliente */
    public static final String COLUMNNAME_documentocliente = "documentocliente";

	/** Set documentocliente	  */
	public void setdocumentocliente (String documentocliente);

	/** Get documentocliente	  */
	public String getdocumentocliente();

    /** Column name fechavencimiento */
    public static final String COLUMNNAME_fechavencimiento = "fechavencimiento";

	/** Set fechavencimiento	  */
	public void setfechavencimiento (Timestamp fechavencimiento);

	/** Get fechavencimiento	  */
	public Timestamp getfechavencimiento();

    /** Column name Importe */
    public static final String COLUMNNAME_Importe = "Importe";

	/** Set Importe	  */
	public void setImporte (BigDecimal Importe);

	/** Get Importe	  */
	public BigDecimal getImporte();

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

    /** Column name nsuhostautorizador */
    public static final String COLUMNNAME_nsuhostautorizador = "nsuhostautorizador";

	/** Set nsuhostautorizador	  */
	public void setnsuhostautorizador (String nsuhostautorizador);

	/** Get nsuhostautorizador	  */
	public String getnsuhostautorizador();

    /** Column name nsusitef */
    public static final String COLUMNNAME_nsusitef = "nsusitef";

	/** Set nsusitef	  */
	public void setnsusitef (String nsusitef);

	/** Get nsusitef	  */
	public String getnsusitef();

    /** Column name numeroautorizacion */
    public static final String COLUMNNAME_numeroautorizacion = "numeroautorizacion";

	/** Set numeroautorizacion	  */
	public void setnumeroautorizacion (BigDecimal numeroautorizacion);

	/** Get numeroautorizacion	  */
	public BigDecimal getnumeroautorizacion();

    /** Column name numerocuotaspago */
    public static final String COLUMNNAME_numerocuotaspago = "numerocuotaspago";

	/** Set numerocuotaspago	  */
	public void setnumerocuotaspago (int numerocuotaspago);

	/** Get numerocuotaspago	  */
	public int getnumerocuotaspago();

    /** Column name numerodocumentopago */
    public static final String COLUMNNAME_numerodocumentopago = "numerodocumentopago";

	/** Set numerodocumentopago	  */
	public void setnumerodocumentopago (String numerodocumentopago);

	/** Get numerodocumentopago	  */
	public String getnumerodocumentopago();

    /** Column name numerotarjeta */
    public static final String COLUMNNAME_numerotarjeta = "numerotarjeta";

	/** Set numerotarjeta	  */
	public void setnumerotarjeta (String numerotarjeta);

	/** Get numerotarjeta	  */
	public String getnumerotarjeta();

    /** Column name productositef */
    public static final String COLUMNNAME_productositef = "productositef";

	/** Set productositef	  */
	public void setproductositef (String productositef);

	/** Get productositef	  */
	public String getproductositef();

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

    /** Column name UY_RT_Movement_ID */
    public static final String COLUMNNAME_UY_RT_Movement_ID = "UY_RT_Movement_ID";

	/** Set UY_RT_Movement	  */
	public void setUY_RT_Movement_ID (int UY_RT_Movement_ID);

	/** Get UY_RT_Movement	  */
	public int getUY_RT_Movement_ID();

	public I_UY_RT_Movement getUY_RT_Movement() throws RuntimeException;

    /** Column name UY_RT_PaymentMovement_ID */
    public static final String COLUMNNAME_UY_RT_PaymentMovement_ID = "UY_RT_PaymentMovement_ID";

	/** Set UY_RT_PaymentMovement	  */
	public void setUY_RT_PaymentMovement_ID (int UY_RT_PaymentMovement_ID);

	/** Get UY_RT_PaymentMovement	  */
	public int getUY_RT_PaymentMovement_ID();
}
