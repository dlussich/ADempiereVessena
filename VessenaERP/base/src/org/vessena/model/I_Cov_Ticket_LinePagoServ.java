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

/** Generated Interface for Cov_Ticket_LinePagoServ
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_Cov_Ticket_LinePagoServ 
{

    /** TableName=Cov_Ticket_LinePagoServ */
    public static final String Table_Name = "Cov_Ticket_LinePagoServ";

    /** AD_Table_ID=1000623 */
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

    /** Column name codigomoneda */
    public static final String COLUMNNAME_codigomoneda = "codigomoneda";

	/** Set codigomoneda	  */
	public void setcodigomoneda (String codigomoneda);

	/** Get codigomoneda	  */
	public String getcodigomoneda();

    /** Column name codigovendedor */
    public static final String COLUMNNAME_codigovendedor = "codigovendedor";

	/** Set codigovendedor	  */
	public void setcodigovendedor (String codigovendedor);

	/** Get codigovendedor	  */
	public String getcodigovendedor();

    /** Column name Cov_CodigoServicios_ID */
    public static final String COLUMNNAME_Cov_CodigoServicios_ID = "Cov_CodigoServicios_ID";

	/** Set Cov_CodigoServicios	  */
	public void setCov_CodigoServicios_ID (int Cov_CodigoServicios_ID);

	/** Get Cov_CodigoServicios	  */
	public int getCov_CodigoServicios_ID();

	public I_Cov_CodigoServicios getCov_CodigoServicios() throws RuntimeException;

    /** Column name Cov_Ticket_Header_ID */
    public static final String COLUMNNAME_Cov_Ticket_Header_ID = "Cov_Ticket_Header_ID";

	/** Set Cov_Ticket_Header	  */
	public void setCov_Ticket_Header_ID (int Cov_Ticket_Header_ID);

	/** Get Cov_Ticket_Header	  */
	public int getCov_Ticket_Header_ID();

	public I_Cov_Ticket_Header getCov_Ticket_Header() throws RuntimeException;

    /** Column name Cov_Ticket_LinePagoServ_ID */
    public static final String COLUMNNAME_Cov_Ticket_LinePagoServ_ID = "Cov_Ticket_LinePagoServ_ID";

	/** Set Cov_Ticket_LinePagoServ	  */
	public void setCov_Ticket_LinePagoServ_ID (int Cov_Ticket_LinePagoServ_ID);

	/** Get Cov_Ticket_LinePagoServ	  */
	public int getCov_Ticket_LinePagoServ_ID();

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

    /** Column name modoingreso */
    public static final String COLUMNNAME_modoingreso = "modoingreso";

	/** Set modoingreso	  */
	public void setmodoingreso (String modoingreso);

	/** Get modoingreso	  */
	public String getmodoingreso();

    /** Column name monto */
    public static final String COLUMNNAME_monto = "monto";

	/** Set monto	  */
	public void setmonto (BigDecimal monto);

	/** Get monto	  */
	public BigDecimal getmonto();

    /** Column name numerodelinea */
    public static final String COLUMNNAME_numerodelinea = "numerodelinea";

	/** Set numerodelinea	  */
	public void setnumerodelinea (String numerodelinea);

	/** Get numerodelinea	  */
	public String getnumerodelinea();

    /** Column name referencia */
    public static final String COLUMNNAME_referencia = "referencia";

	/** Set referencia	  */
	public void setreferencia (String referencia);

	/** Get referencia	  */
	public String getreferencia();

    /** Column name timestamplinea */
    public static final String COLUMNNAME_timestamplinea = "timestamplinea";

	/** Set timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea);

	/** Get timestamplinea	  */
	public Timestamp gettimestamplinea();

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
