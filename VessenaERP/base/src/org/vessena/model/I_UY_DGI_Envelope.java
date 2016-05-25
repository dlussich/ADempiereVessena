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

/** Generated Interface for UY_DGI_Envelope
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_DGI_Envelope 
{

    /** TableName=UY_DGI_Envelope */
    public static final String Table_Name = "UY_DGI_Envelope";

    /** AD_Table_ID=1000894 */
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

    /** Column name CantidadCFE */
    public static final String COLUMNNAME_CantidadCFE = "CantidadCFE";

	/** Set CantidadCFE	  */
	public void setCantidadCFE (BigDecimal CantidadCFE);

	/** Get CantidadCFE	  */
	public BigDecimal getCantidadCFE();

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

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (boolean ExecuteAction);

	/** Get ExecuteAction	  */
	public boolean isExecuteAction();

    /** Column name ExecuteAction2 */
    public static final String COLUMNNAME_ExecuteAction2 = "ExecuteAction2";

	/** Set ExecuteAction2	  */
	public void setExecuteAction2 (boolean ExecuteAction2);

	/** Get ExecuteAction2	  */
	public boolean isExecuteAction2();

    /** Column name ExecuteAction3 */
    public static final String COLUMNNAME_ExecuteAction3 = "ExecuteAction3";

	/** Set ExecuteAction3	  */
	public void setExecuteAction3 (boolean ExecuteAction3);

	/** Get ExecuteAction3	  */
	public boolean isExecuteAction3();

    /** Column name FechaCreacion */
    public static final String COLUMNNAME_FechaCreacion = "FechaCreacion";

	/** Set FechaCreacion	  */
	public void setFechaCreacion (Timestamp FechaCreacion);

	/** Get FechaCreacion	  */
	public Timestamp getFechaCreacion();

    /** Column name FechaHoraProximaConsulta */
    public static final String COLUMNNAME_FechaHoraProximaConsulta = "FechaHoraProximaConsulta";

	/** Set FechaHoraProximaConsulta	  */
	public void setFechaHoraProximaConsulta (Timestamp FechaHoraProximaConsulta);

	/** Get FechaHoraProximaConsulta	  */
	public Timestamp getFechaHoraProximaConsulta();

    /** Column name FechaRecepcion */
    public static final String COLUMNNAME_FechaRecepcion = "FechaRecepcion";

	/** Set FechaRecepcion	  */
	public void setFechaRecepcion (Timestamp FechaRecepcion);

	/** Get FechaRecepcion	  */
	public Timestamp getFechaRecepcion();

    /** Column name IdEmisor */
    public static final String COLUMNNAME_IdEmisor = "IdEmisor";

	/** Set IdEmisor	  */
	public void setIdEmisor (int IdEmisor);

	/** Get IdEmisor	  */
	public int getIdEmisor();

    /** Column name IdRespuesta */
    public static final String COLUMNNAME_IdRespuesta = "IdRespuesta";

	/** Set IdRespuesta	  */
	public void setIdRespuesta (int IdRespuesta);

	/** Get IdRespuesta	  */
	public int getIdRespuesta();

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

    /** Column name RutEmisor */
    public static final String COLUMNNAME_RutEmisor = "RutEmisor";

	/** Set RutEmisor	  */
	public void setRutEmisor (BigDecimal RutEmisor);

	/** Get RutEmisor	  */
	public BigDecimal getRutEmisor();

    /** Column name RutReceptor */
    public static final String COLUMNNAME_RutReceptor = "RutReceptor";

	/** Set RutReceptor	  */
	public void setRutReceptor (BigDecimal RutReceptor);

	/** Get RutReceptor	  */
	public BigDecimal getRutReceptor();

    /** Column name StatusSobre */
    public static final String COLUMNNAME_StatusSobre = "StatusSobre";

	/** Set StatusSobre	  */
	public void setStatusSobre (String StatusSobre);

	/** Get StatusSobre	  */
	public String getStatusSobre();

    /** Column name Token */
    public static final String COLUMNNAME_Token = "Token";

	/** Set Token	  */
	public void setToken (String Token);

	/** Get Token	  */
	public String getToken();

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

    /** Column name UY_DGI_Envelope_ID */
    public static final String COLUMNNAME_UY_DGI_Envelope_ID = "UY_DGI_Envelope_ID";

	/** Set UY_DGI_Envelope	  */
	public void setUY_DGI_Envelope_ID (int UY_DGI_Envelope_ID);

	/** Get UY_DGI_Envelope	  */
	public int getUY_DGI_Envelope_ID();
}
