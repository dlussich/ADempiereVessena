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

/** Generated Interface for Cov_Ticket_Header
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_Cov_Ticket_Header 
{

    /** TableName=Cov_Ticket_Header */
    public static final String Table_Name = "Cov_Ticket_Header";

    /** AD_Table_ID=1000604 */
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

    /** Column name cantidadarticulos */
    public static final String COLUMNNAME_cantidadarticulos = "cantidadarticulos";

	/** Set cantidadarticulos	  */
	public void setcantidadarticulos (int cantidadarticulos);

	/** Get cantidadarticulos	  */
	public int getcantidadarticulos();

    /** Column name cantidadlineas */
    public static final String COLUMNNAME_cantidadlineas = "cantidadlineas";

	/** Set cantidadlineas	  */
	public void setcantidadlineas (int cantidadlineas);

	/** Get cantidadlineas	  */
	public int getcantidadlineas();

    /** Column name codigocaja */
    public static final String COLUMNNAME_codigocaja = "codigocaja";

	/** Set codigocaja	  */
	public void setcodigocaja (String codigocaja);

	/** Get codigocaja	  */
	public String getcodigocaja();

    /** Column name codigocajadevolucion */
    public static final String COLUMNNAME_codigocajadevolucion = "codigocajadevolucion";

	/** Set codigocajadevolucion	  */
	public void setcodigocajadevolucion (int codigocajadevolucion);

	/** Get codigocajadevolucion	  */
	public int getcodigocajadevolucion();

    /** Column name codigocajera */
    public static final String COLUMNNAME_codigocajera = "codigocajera";

	/** Set codigocajera	  */
	public void setcodigocajera (String codigocajera);

	/** Get codigocajera	  */
	public String getcodigocajera();

    /** Column name Cov_LoadTicket_ID */
    public static final String COLUMNNAME_Cov_LoadTicket_ID = "Cov_LoadTicket_ID";

	/** Set Cov_LoadTicket	  */
	public void setCov_LoadTicket_ID (int Cov_LoadTicket_ID);

	/** Get Cov_LoadTicket	  */
	public int getCov_LoadTicket_ID();

	public I_Cov_LoadTicket getCov_LoadTicket() throws RuntimeException;

    /** Column name Cov_Ticket_Header_ID */
    public static final String COLUMNNAME_Cov_Ticket_Header_ID = "Cov_Ticket_Header_ID";

	/** Set Cov_Ticket_Header	  */
	public void setCov_Ticket_Header_ID (int Cov_Ticket_Header_ID);

	/** Get Cov_Ticket_Header	  */
	public int getCov_Ticket_Header_ID();

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

    /** Column name estadoticket */
    public static final String COLUMNNAME_estadoticket = "estadoticket";

	/** Set estadoticket	  */
	public void setestadoticket (String estadoticket);

	/** Get estadoticket	  */
	public String getestadoticket();

    /** Column name identificadorlinea */
    public static final String COLUMNNAME_identificadorlinea = "identificadorlinea";

	/** Set identificadorlinea	  */
	public void setidentificadorlinea (boolean identificadorlinea);

	/** Get identificadorlinea	  */
	public boolean isidentificadorlinea();

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

    /** Column name numerolinefile */
    public static final String COLUMNNAME_numerolinefile = "numerolinefile";

	/** Set numerolinefile	  */
	public void setnumerolinefile (String numerolinefile);

	/** Get numerolinefile	  */
	public String getnumerolinefile();

    /** Column name numeroticket */
    public static final String COLUMNNAME_numeroticket = "numeroticket";

	/** Set numeroticket	  */
	public void setnumeroticket (String numeroticket);

	/** Get numeroticket	  */
	public String getnumeroticket();

    /** Column name numeroticketdevolucion */
    public static final String COLUMNNAME_numeroticketdevolucion = "numeroticketdevolucion";

	/** Set numeroticketdevolucion	  */
	public void setnumeroticketdevolucion (int numeroticketdevolucion);

	/** Get numeroticketdevolucion	  */
	public int getnumeroticketdevolucion();

    /** Column name timestampticket */
    public static final String COLUMNNAME_timestampticket = "timestampticket";

	/** Set timestampticket	  */
	public void settimestampticket (Timestamp timestampticket);

	/** Get timestampticket	  */
	public Timestamp gettimestampticket();

    /** Column name tipocliente */
    public static final String COLUMNNAME_tipocliente = "tipocliente";

	/** Set tipocliente	  */
	public void settipocliente (String tipocliente);

	/** Get tipocliente	  */
	public String gettipocliente();

    /** Column name totalapagar */
    public static final String COLUMNNAME_totalapagar = "totalapagar";

	/** Set totalapagar	  */
	public void settotalapagar (BigDecimal totalapagar);

	/** Get totalapagar	  */
	public BigDecimal gettotalapagar();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();
}
