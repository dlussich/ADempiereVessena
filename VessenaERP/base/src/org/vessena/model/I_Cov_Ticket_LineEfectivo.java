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

/** Generated Interface for Cov_Ticket_LineEfectivo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_Cov_Ticket_LineEfectivo 
{

    /** TableName=Cov_Ticket_LineEfectivo */
    public static final String Table_Name = "Cov_Ticket_LineEfectivo";

    /** AD_Table_ID=1000609 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

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

    /** Column name autorizasupervisora */
    public static final String COLUMNNAME_autorizasupervisora = "autorizasupervisora";

	/** Set autorizasupervisora	  */
	public void setautorizasupervisora (String autorizasupervisora);

	/** Get autorizasupervisora	  */
	public String getautorizasupervisora();

    /** Column name cambio */
    public static final String COLUMNNAME_cambio = "cambio";

	/** Set cambio	  */
	public void setcambio (BigDecimal cambio);

	/** Get cambio	  */
	public BigDecimal getcambio();

    /** Column name codigomoneda */
    public static final String COLUMNNAME_codigomoneda = "codigomoneda";

	/** Set codigomoneda	  */
	public void setcodigomoneda (String codigomoneda);

	/** Get codigomoneda	  */
	public String getcodigomoneda();

    /** Column name codigosupervisora */
    public static final String COLUMNNAME_codigosupervisora = "codigosupervisora";

	/** Set codigosupervisora	  */
	public void setcodigosupervisora (String codigosupervisora);

	/** Get codigosupervisora	  */
	public String getcodigosupervisora();

    /** Column name Cov_CodigoMedioPago_ID */
    public static final String COLUMNNAME_Cov_CodigoMedioPago_ID = "Cov_CodigoMedioPago_ID";

	/** Set Cov_CodigoMedioPago	  */
	public void setCov_CodigoMedioPago_ID (int Cov_CodigoMedioPago_ID);

	/** Get Cov_CodigoMedioPago	  */
	public int getCov_CodigoMedioPago_ID();

	public I_Cov_CodigoMedioPago getCov_CodigoMedioPago() throws RuntimeException;

    /** Column name Cov_Ticket_Header_ID */
    public static final String COLUMNNAME_Cov_Ticket_Header_ID = "Cov_Ticket_Header_ID";

	/** Set Cov_Ticket_Header	  */
	public void setCov_Ticket_Header_ID (int Cov_Ticket_Header_ID);

	/** Get Cov_Ticket_Header	  */
	public int getCov_Ticket_Header_ID();

	public I_Cov_Ticket_Header getCov_Ticket_Header() throws RuntimeException;

    /** Column name Cov_Ticket_LineEfectivo_ID */
    public static final String COLUMNNAME_Cov_Ticket_LineEfectivo_ID = "Cov_Ticket_LineEfectivo_ID";

	/** Set Cov_Ticket_LineEfectivo	  */
	public void setCov_Ticket_LineEfectivo_ID (int Cov_Ticket_LineEfectivo_ID);

	/** Get Cov_Ticket_LineEfectivo	  */
	public int getCov_Ticket_LineEfectivo_ID();

    /** Column name Cov_TicketType_ID */
    public static final String COLUMNNAME_Cov_TicketType_ID = "Cov_TicketType_ID";

	/** Set Cov_TicketType	  */
	public void setCov_TicketType_ID (int Cov_TicketType_ID);

	/** Get Cov_TicketType	  */
	public int getCov_TicketType_ID();

	public I_Cov_TicketType getCov_TicketType() throws RuntimeException;

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

    /** Column name lineacancelada */
    public static final String COLUMNNAME_lineacancelada = "lineacancelada";

	/** Set lineacancelada	  */
	public void setlineacancelada (String lineacancelada);

	/** Get lineacancelada	  */
	public String getlineacancelada();

    /** Column name lineaultimopago */
    public static final String COLUMNNAME_lineaultimopago = "lineaultimopago";

	/** Set lineaultimopago	  */
	public void setlineaultimopago (String lineaultimopago);

	/** Get lineaultimopago	  */
	public String getlineaultimopago();

    /** Column name numerodelinea */
    public static final String COLUMNNAME_numerodelinea = "numerodelinea";

	/** Set numerodelinea	  */
	public void setnumerodelinea (String numerodelinea);

	/** Get numerodelinea	  */
	public String getnumerodelinea();

    /** Column name timestamplinea */
    public static final String COLUMNNAME_timestamplinea = "timestamplinea";

	/** Set timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea);

	/** Get timestamplinea	  */
	public Timestamp gettimestamplinea();

    /** Column name tipooperacion */
    public static final String COLUMNNAME_tipooperacion = "tipooperacion";

	/** Set tipooperacion	  */
	public void settipooperacion (String tipooperacion);

	/** Get tipooperacion	  */
	public String gettipooperacion();

    /** Column name totalentregado */
    public static final String COLUMNNAME_totalentregado = "totalentregado";

	/** Set totalentregado	  */
	public void settotalentregado (BigDecimal totalentregado);

	/** Get totalentregado	  */
	public BigDecimal gettotalentregado();

    /** Column name totalentregadomonedareferencia */
    public static final String COLUMNNAME_totalentregadomonedareferencia = "totalentregadomonedareferencia";

	/** Set totalentregadomonedareferencia	  */
	public void settotalentregadomonedareferencia (BigDecimal totalentregadomonedareferencia);

	/** Get totalentregadomonedareferencia	  */
	public BigDecimal gettotalentregadomonedareferencia();

    /** Column name totalmediopagomonedareferencia */
    public static final String COLUMNNAME_totalmediopagomonedareferencia = "totalmediopagomonedareferencia";

	/** Set totalmediopagomonedareferencia	  */
	public void settotalmediopagomonedareferencia (BigDecimal totalmediopagomonedareferencia);

	/** Get totalmediopagomonedareferencia	  */
	public BigDecimal gettotalmediopagomonedareferencia();

    /** Column name totalmeidopagomoneda */
    public static final String COLUMNNAME_totalmeidopagomoneda = "totalmeidopagomoneda";

	/** Set totalmeidopagomoneda	  */
	public void settotalmeidopagomoneda (BigDecimal totalmeidopagomoneda);

	/** Get totalmeidopagomoneda	  */
	public BigDecimal gettotalmeidopagomoneda();

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
}
