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

/** Generated Interface for Cov_Ticket_LineFactura
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_Cov_Ticket_LineFactura 
{

    /** TableName=Cov_Ticket_LineFactura */
    public static final String Table_Name = "Cov_Ticket_LineFactura";

    /** AD_Table_ID=1000611 */
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

    /** Column name caja */
    public static final String COLUMNNAME_caja = "caja";

	/** Set caja	  */
	public void setcaja (String caja);

	/** Get caja	  */
	public String getcaja();

    /** Column name cajaticket */
    public static final String COLUMNNAME_cajaticket = "cajaticket";

	/** Set cajaticket	  */
	public void setcajaticket (String cajaticket);

	/** Get cajaticket	  */
	public String getcajaticket();

    /** Column name Cov_Ticket_Header_ID */
    public static final String COLUMNNAME_Cov_Ticket_Header_ID = "Cov_Ticket_Header_ID";

	/** Set Cov_Ticket_Header	  */
	public void setCov_Ticket_Header_ID (int Cov_Ticket_Header_ID);

	/** Get Cov_Ticket_Header	  */
	public int getCov_Ticket_Header_ID();

	public I_Cov_Ticket_Header getCov_Ticket_Header() throws RuntimeException;

    /** Column name Cov_Ticket_LineFactura_ID */
    public static final String COLUMNNAME_Cov_Ticket_LineFactura_ID = "Cov_Ticket_LineFactura_ID";

	/** Set Cov_Ticket_LineFactura	  */
	public void setCov_Ticket_LineFactura_ID (int Cov_Ticket_LineFactura_ID);

	/** Get Cov_Ticket_LineFactura	  */
	public int getCov_Ticket_LineFactura_ID();

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

    /** Column name fechafactura */
    public static final String COLUMNNAME_fechafactura = "fechafactura";

	/** Set fechafactura	  */
	public void setfechafactura (Timestamp fechafactura);

	/** Get fechafactura	  */
	public Timestamp getfechafactura();

    /** Column name impoteivacodigo */
    public static final String COLUMNNAME_impoteivacodigo = "impoteivacodigo";

	/** Set impoteivacodigo	  */
	public void setimpoteivacodigo (BigDecimal impoteivacodigo);

	/** Get impoteivacodigo	  */
	public BigDecimal getimpoteivacodigo();

    /** Column name impoteivacodigo1 */
    public static final String COLUMNNAME_impoteivacodigo1 = "impoteivacodigo1";

	/** Set impoteivacodigo1	  */
	public void setimpoteivacodigo1 (BigDecimal impoteivacodigo1);

	/** Get impoteivacodigo1	  */
	public BigDecimal getimpoteivacodigo1();

    /** Column name impoteivacodigo2 */
    public static final String COLUMNNAME_impoteivacodigo2 = "impoteivacodigo2";

	/** Set impoteivacodigo2	  */
	public void setimpoteivacodigo2 (BigDecimal impoteivacodigo2);

	/** Get impoteivacodigo2	  */
	public BigDecimal getimpoteivacodigo2();

    /** Column name impoteivacodigo3 */
    public static final String COLUMNNAME_impoteivacodigo3 = "impoteivacodigo3";

	/** Set impoteivacodigo3	  */
	public void setimpoteivacodigo3 (BigDecimal impoteivacodigo3);

	/** Get impoteivacodigo3	  */
	public BigDecimal getimpoteivacodigo3();

    /** Column name impoteivacodigo4 */
    public static final String COLUMNNAME_impoteivacodigo4 = "impoteivacodigo4";

	/** Set impoteivacodigo4	  */
	public void setimpoteivacodigo4 (BigDecimal impoteivacodigo4);

	/** Get impoteivacodigo4	  */
	public BigDecimal getimpoteivacodigo4();

    /** Column name impoteivacodigo5 */
    public static final String COLUMNNAME_impoteivacodigo5 = "impoteivacodigo5";

	/** Set impoteivacodigo5	  */
	public void setimpoteivacodigo5 (BigDecimal impoteivacodigo5);

	/** Get impoteivacodigo5	  */
	public BigDecimal getimpoteivacodigo5();

    /** Column name impoteivacodigo6 */
    public static final String COLUMNNAME_impoteivacodigo6 = "impoteivacodigo6";

	/** Set impoteivacodigo6	  */
	public void setimpoteivacodigo6 (BigDecimal impoteivacodigo6);

	/** Get impoteivacodigo6	  */
	public BigDecimal getimpoteivacodigo6();

    /** Column name impoteivacodigo7 */
    public static final String COLUMNNAME_impoteivacodigo7 = "impoteivacodigo7";

	/** Set impoteivacodigo7	  */
	public void setimpoteivacodigo7 (BigDecimal impoteivacodigo7);

	/** Get impoteivacodigo7	  */
	public BigDecimal getimpoteivacodigo7();

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

    /** Column name numerodelinea */
    public static final String COLUMNNAME_numerodelinea = "numerodelinea";

	/** Set numerodelinea	  */
	public void setnumerodelinea (String numerodelinea);

	/** Get numerodelinea	  */
	public String getnumerodelinea();

    /** Column name numeroticket */
    public static final String COLUMNNAME_numeroticket = "numeroticket";

	/** Set numeroticket	  */
	public void setnumeroticket (String numeroticket);

	/** Get numeroticket	  */
	public String getnumeroticket();

    /** Column name ruc */
    public static final String COLUMNNAME_ruc = "ruc";

	/** Set ruc	  */
	public void setruc (String ruc);

	/** Get ruc	  */
	public String getruc();

    /** Column name serienrofactura */
    public static final String COLUMNNAME_serienrofactura = "serienrofactura";

	/** Set serienrofactura	  */
	public void setserienrofactura (String serienrofactura);

	/** Get serienrofactura	  */
	public String getserienrofactura();

    /** Column name ticketreferencia */
    public static final String COLUMNNAME_ticketreferencia = "ticketreferencia";

	/** Set ticketreferencia	  */
	public void setticketreferencia (String ticketreferencia);

	/** Get ticketreferencia	  */
	public String getticketreferencia();

    /** Column name timestamplinea */
    public static final String COLUMNNAME_timestamplinea = "timestamplinea";

	/** Set timestamplinea	  */
	public void settimestamplinea (Timestamp timestamplinea);

	/** Get timestamplinea	  */
	public Timestamp gettimestamplinea();

    /** Column name totalfactura */
    public static final String COLUMNNAME_totalfactura = "totalfactura";

	/** Set totalfactura	  */
	public void settotalfactura (BigDecimal totalfactura);

	/** Get totalfactura	  */
	public BigDecimal gettotalfactura();

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
