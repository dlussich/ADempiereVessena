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

/** Generated Interface for UY_Molde_PromCons
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_Molde_PromCons 
{

    /** TableName=UY_Molde_PromCons */
    public static final String Table_Name = "UY_Molde_PromCons";

    /** AD_Table_ID=1000746 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name CostAmt */
    public static final String COLUMNNAME_CostAmt = "CostAmt";

	/** Set Cost Value.
	  * Value with Cost
	  */
	public void setCostAmt (BigDecimal CostAmt);

	/** Get Cost Value.
	  * Value with Cost
	  */
	public BigDecimal getCostAmt();

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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name factura */
    public static final String COLUMNNAME_factura = "factura";

	/** Set factura	  */
	public void setfactura (String factura);

	/** Get factura	  */
	public String getfactura();

    /** Column name fecreporte */
    public static final String COLUMNNAME_fecreporte = "fecreporte";

	/** Set fecreporte	  */
	public void setfecreporte (Timestamp fecreporte);

	/** Get fecreporte	  */
	public Timestamp getfecreporte();

    /** Column name idReporte */
    public static final String COLUMNNAME_idReporte = "idReporte";

	/** Set Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte);

	/** Get Identificador Unico del Reporte	  */
	public String getidReporte();

    /** Column name IsFullTank */
    public static final String COLUMNNAME_IsFullTank = "IsFullTank";

	/** Set IsFullTank	  */
	public void setIsFullTank (int IsFullTank);

	/** Get IsFullTank	  */
	public int getIsFullTank();

    /** Column name Kilometros */
    public static final String COLUMNNAME_Kilometros = "Kilometros";

	/** Set Kilometros	  */
	public void setKilometros (BigDecimal Kilometros);

	/** Get Kilometros	  */
	public BigDecimal getKilometros();

    /** Column name Litros */
    public static final String COLUMNNAME_Litros = "Litros";

	/** Set Litros	  */
	public void setLitros (BigDecimal Litros);

	/** Get Litros	  */
	public BigDecimal getLitros();

    /** Column name PromedioActual */
    public static final String COLUMNNAME_PromedioActual = "PromedioActual";

	/** Set PromedioActual	  */
	public void setPromedioActual (BigDecimal PromedioActual);

	/** Get PromedioActual	  */
	public BigDecimal getPromedioActual();

    /** Column name PromedioAcumulado */
    public static final String COLUMNNAME_PromedioAcumulado = "PromedioAcumulado";

	/** Set PromedioAcumulado	  */
	public void setPromedioAcumulado (BigDecimal PromedioAcumulado);

	/** Get PromedioAcumulado	  */
	public BigDecimal getPromedioAcumulado();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

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

    /** Column name UY_Molde_PromCons_ID */
    public static final String COLUMNNAME_UY_Molde_PromCons_ID = "UY_Molde_PromCons_ID";

	/** Set UY_Molde_PromCons	  */
	public void setUY_Molde_PromCons_ID (int UY_Molde_PromCons_ID);

	/** Get UY_Molde_PromCons	  */
	public int getUY_Molde_PromCons_ID();

    /** Column name UY_TR_Driver_ID */
    public static final String COLUMNNAME_UY_TR_Driver_ID = "UY_TR_Driver_ID";

	/** Set UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID);

	/** Get UY_TR_Driver	  */
	public int getUY_TR_Driver_ID();

	public I_UY_TR_Driver getUY_TR_Driver() throws RuntimeException;

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException;
}
