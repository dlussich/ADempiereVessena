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

/** Generated Interface for UY_R_ReclamosBcu
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_ReclamosBcu 
{

    /** TableName=UY_R_ReclamosBcu */
    public static final String Table_Name = "UY_R_ReclamosBcu";

    /** AD_Table_ID=1000695 */
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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

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

    /** Column name CantidadReclamos */
    public static final String COLUMNNAME_CantidadReclamos = "CantidadReclamos";

	/** Set CantidadReclamos.
	  * CantidadReclamos
	  */
	public void setCantidadReclamos (BigDecimal CantidadReclamos);

	/** Get CantidadReclamos.
	  * CantidadReclamos
	  */
	public BigDecimal getCantidadReclamos();

    /** Column name Clientes */
    public static final String COLUMNNAME_Clientes = "Clientes";

	/** Set Clientes.
	  * Clientes
	  */
	public void setClientes (BigDecimal Clientes);

	/** Get Clientes.
	  * Clientes
	  */
	public BigDecimal getClientes();

    /** Column name CliMayor15Dias */
    public static final String COLUMNNAME_CliMayor15Dias = "CliMayor15Dias";

	/** Set CliMayor15Dias.
	  * CliMayor15Dias
	  */
	public void setCliMayor15Dias (BigDecimal CliMayor15Dias);

	/** Get CliMayor15Dias.
	  * CliMayor15Dias
	  */
	public BigDecimal getCliMayor15Dias();

    /** Column name CliMenor15Dias */
    public static final String COLUMNNAME_CliMenor15Dias = "CliMenor15Dias";

	/** Set CliMenor15Dias.
	  * CliMenor15Dias
	  */
	public void setCliMenor15Dias (BigDecimal CliMenor15Dias);

	/** Get CliMenor15Dias.
	  * CliMenor15Dias
	  */
	public BigDecimal getCliMenor15Dias();

    /** Column name CliMenor2Dias */
    public static final String COLUMNNAME_CliMenor2Dias = "CliMenor2Dias";

	/** Set CliMenor2Dias.
	  * CliMenor2Dias
	  */
	public void setCliMenor2Dias (BigDecimal CliMenor2Dias);

	/** Get CliMenor2Dias.
	  * CliMenor2Dias
	  */
	public BigDecimal getCliMenor2Dias();

    /** Column name CodigoInstitucion */
    public static final String COLUMNNAME_CodigoInstitucion = "CodigoInstitucion";

	/** Set CodigoInstitucion.
	  * CodigoInstitucion
	  */
	public void setCodigoInstitucion (String CodigoInstitucion);

	/** Get CodigoInstitucion.
	  * CodigoInstitucion
	  */
	public String getCodigoInstitucion();

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

    /** Column name EsperaSolucion */
    public static final String COLUMNNAME_EsperaSolucion = "EsperaSolucion";

	/** Set EsperaSolucion.
	  * EsperaSolucion
	  */
	public void setEsperaSolucion (BigDecimal EsperaSolucion);

	/** Get EsperaSolucion.
	  * EsperaSolucion
	  */
	public BigDecimal getEsperaSolucion();

    /** Column name Institucion */
    public static final String COLUMNNAME_Institucion = "Institucion";

	/** Set Institucion.
	  * Institucion
	  */
	public void setInstitucion (String Institucion);

	/** Get Institucion.
	  * Institucion
	  */
	public String getInstitucion();

    /** Column name InstMayor15Dias */
    public static final String COLUMNNAME_InstMayor15Dias = "InstMayor15Dias";

	/** Set InstMayor15Dias.
	  * InstMayor15Dias
	  */
	public void setInstMayor15Dias (BigDecimal InstMayor15Dias);

	/** Get InstMayor15Dias.
	  * InstMayor15Dias
	  */
	public BigDecimal getInstMayor15Dias();

    /** Column name InstMenor15Dias */
    public static final String COLUMNNAME_InstMenor15Dias = "InstMenor15Dias";

	/** Set InstMenor15Dias.
	  * InstMenor15Dias
	  */
	public void setInstMenor15Dias (BigDecimal InstMenor15Dias);

	/** Get InstMenor15Dias.
	  * InstMenor15Dias
	  */
	public BigDecimal getInstMenor15Dias();

    /** Column name InstMenor2Dias */
    public static final String COLUMNNAME_InstMenor2Dias = "InstMenor2Dias";

	/** Set InstMenor2Dias.
	  * InstMenor2Dias
	  */
	public void setInstMenor2Dias (BigDecimal InstMenor2Dias);

	/** Get InstMenor2Dias.
	  * InstMenor2Dias
	  */
	public BigDecimal getInstMenor2Dias();

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

    /** Column name PromedioCliente */
    public static final String COLUMNNAME_PromedioCliente = "PromedioCliente";

	/** Set PromedioCliente.
	  * PromedioCliente
	  */
	public void setPromedioCliente (BigDecimal PromedioCliente);

	/** Get PromedioCliente.
	  * PromedioCliente
	  */
	public BigDecimal getPromedioCliente();

    /** Column name PromedioInstitucion */
    public static final String COLUMNNAME_PromedioInstitucion = "PromedioInstitucion";

	/** Set PromedioInstitucion.
	  * PromedioInstitucion
	  */
	public void setPromedioInstitucion (BigDecimal PromedioInstitucion);

	/** Get PromedioInstitucion.
	  * PromedioInstitucion
	  */
	public BigDecimal getPromedioInstitucion();

    /** Column name RangoPeriodo */
    public static final String COLUMNNAME_RangoPeriodo = "RangoPeriodo";

	/** Set RangoPeriodo.
	  * RangoPeriodo
	  */
	public void setRangoPeriodo (String RangoPeriodo);

	/** Get RangoPeriodo.
	  * RangoPeriodo
	  */
	public String getRangoPeriodo();

    /** Column name ReclamosPorClientes */
    public static final String COLUMNNAME_ReclamosPorClientes = "ReclamosPorClientes";

	/** Set ReclamosPorClientes.
	  * ReclamosPorClientes
	  */
	public void setReclamosPorClientes (BigDecimal ReclamosPorClientes);

	/** Get ReclamosPorClientes.
	  * ReclamosPorClientes
	  */
	public BigDecimal getReclamosPorClientes();

    /** Column name Servicio */
    public static final String COLUMNNAME_Servicio = "Servicio";

	/** Set Servicio.
	  * Servicio
	  */
	public void setServicio (String Servicio);

	/** Get Servicio.
	  * Servicio
	  */
	public String getServicio();

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

    /** Column name UY_R_ReclamosBcu_ID */
    public static final String COLUMNNAME_UY_R_ReclamosBcu_ID = "UY_R_ReclamosBcu_ID";

	/** Set UY_R_ReclamosBcu	  */
	public void setUY_R_ReclamosBcu_ID (int UY_R_ReclamosBcu_ID);

	/** Get UY_R_ReclamosBcu	  */
	public int getUY_R_ReclamosBcu_ID();
}
