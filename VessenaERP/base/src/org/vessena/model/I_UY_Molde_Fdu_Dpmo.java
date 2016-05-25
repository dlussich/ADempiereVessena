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

/** Generated Interface for UY_Molde_Fdu_Dpmo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Molde_Fdu_Dpmo 
{

    /** TableName=UY_Molde_Fdu_Dpmo */
    public static final String Table_Name = "UY_Molde_Fdu_Dpmo";

    /** AD_Table_ID=1000492 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name Accion */
    public static final String COLUMNNAME_Accion = "Accion";

	/** Set Accion	  */
	public void setAccion (String Accion);

	/** Get Accion	  */
	public String getAccion();

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

    /** Column name Cantidad */
    public static final String COLUMNNAME_Cantidad = "Cantidad";

	/** Set Cantidad	  */
	public void setCantidad (BigDecimal Cantidad);

	/** Get Cantidad	  */
	public BigDecimal getCantidad();

    /** Column name CantidadTotal */
    public static final String COLUMNNAME_CantidadTotal = "CantidadTotal";

	/** Set CantidadTotal.
	  * CantidadTotal
	  */
	public void setCantidadTotal (BigDecimal CantidadTotal);

	/** Get CantidadTotal.
	  * CantidadTotal
	  */
	public BigDecimal getCantidadTotal();

    /** Column name DPMO */
    public static final String COLUMNNAME_DPMO = "DPMO";

	/** Set DPMO.
	  * DPMO
	  */
	public void setDPMO (BigDecimal DPMO);

	/** Get DPMO.
	  * DPMO
	  */
	public BigDecimal getDPMO();

    /** Column name FechaFin */
    public static final String COLUMNNAME_FechaFin = "FechaFin";

	/** Set FechaFin	  */
	public void setFechaFin (Timestamp FechaFin);

	/** Get FechaFin	  */
	public Timestamp getFechaFin();

    /** Column name FechaInicio */
    public static final String COLUMNNAME_FechaInicio = "FechaInicio";

	/** Set FechaInicio	  */
	public void setFechaInicio (Timestamp FechaInicio);

	/** Get FechaInicio	  */
	public Timestamp getFechaInicio();

    /** Column name idReporte */
    public static final String COLUMNNAME_idReporte = "idReporte";

	/** Set Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte);

	/** Get Identificador Unico del Reporte	  */
	public String getidReporte();
}
