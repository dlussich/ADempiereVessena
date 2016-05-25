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

/** Generated Interface for UY_Fdu_CoefficientLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Fdu_CoefficientLine 
{

    /** TableName=UY_Fdu_CoefficientLine */
    public static final String Table_Name = "UY_Fdu_CoefficientLine";

    /** AD_Table_ID=1000442 */
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

    /** Column name cuotas_10 */
    public static final String COLUMNNAME_cuotas_10 = "cuotas_10";

	/** Set cuotas_10	  */
	public void setcuotas_10 (BigDecimal cuotas_10);

	/** Get cuotas_10	  */
	public BigDecimal getcuotas_10();

    /** Column name cuotas_11 */
    public static final String COLUMNNAME_cuotas_11 = "cuotas_11";

	/** Set cuotas_11	  */
	public void setcuotas_11 (BigDecimal cuotas_11);

	/** Get cuotas_11	  */
	public BigDecimal getcuotas_11();

    /** Column name cuotas_12 */
    public static final String COLUMNNAME_cuotas_12 = "cuotas_12";

	/** Set cuotas_12	  */
	public void setcuotas_12 (BigDecimal cuotas_12);

	/** Get cuotas_12	  */
	public BigDecimal getcuotas_12();

    /** Column name cuotas_13 */
    public static final String COLUMNNAME_cuotas_13 = "cuotas_13";

	/** Set cuotas_13	  */
	public void setcuotas_13 (BigDecimal cuotas_13);

	/** Get cuotas_13	  */
	public BigDecimal getcuotas_13();

    /** Column name cuotas_14 */
    public static final String COLUMNNAME_cuotas_14 = "cuotas_14";

	/** Set cuotas_14	  */
	public void setcuotas_14 (BigDecimal cuotas_14);

	/** Get cuotas_14	  */
	public BigDecimal getcuotas_14();

    /** Column name cuotas_15 */
    public static final String COLUMNNAME_cuotas_15 = "cuotas_15";

	/** Set cuotas_15	  */
	public void setcuotas_15 (BigDecimal cuotas_15);

	/** Get cuotas_15	  */
	public BigDecimal getcuotas_15();

    /** Column name cuotas_16 */
    public static final String COLUMNNAME_cuotas_16 = "cuotas_16";

	/** Set cuotas_16	  */
	public void setcuotas_16 (BigDecimal cuotas_16);

	/** Get cuotas_16	  */
	public BigDecimal getcuotas_16();

    /** Column name cuotas_17 */
    public static final String COLUMNNAME_cuotas_17 = "cuotas_17";

	/** Set cuotas_17	  */
	public void setcuotas_17 (BigDecimal cuotas_17);

	/** Get cuotas_17	  */
	public BigDecimal getcuotas_17();

    /** Column name cuotas_18 */
    public static final String COLUMNNAME_cuotas_18 = "cuotas_18";

	/** Set cuotas_18	  */
	public void setcuotas_18 (BigDecimal cuotas_18);

	/** Get cuotas_18	  */
	public BigDecimal getcuotas_18();

    /** Column name cuotas_19 */
    public static final String COLUMNNAME_cuotas_19 = "cuotas_19";

	/** Set cuotas_19	  */
	public void setcuotas_19 (BigDecimal cuotas_19);

	/** Get cuotas_19	  */
	public BigDecimal getcuotas_19();

    /** Column name Cuotas_2 */
    public static final String COLUMNNAME_Cuotas_2 = "Cuotas_2";

	/** Set Cuotas_2	  */
	public void setCuotas_2 (BigDecimal Cuotas_2);

	/** Get Cuotas_2	  */
	public BigDecimal getCuotas_2();

    /** Column name cuotas_20 */
    public static final String COLUMNNAME_cuotas_20 = "cuotas_20";

	/** Set cuotas_20	  */
	public void setcuotas_20 (BigDecimal cuotas_20);

	/** Get cuotas_20	  */
	public BigDecimal getcuotas_20();

    /** Column name cuotas_21 */
    public static final String COLUMNNAME_cuotas_21 = "cuotas_21";

	/** Set cuotas_21	  */
	public void setcuotas_21 (BigDecimal cuotas_21);

	/** Get cuotas_21	  */
	public BigDecimal getcuotas_21();

    /** Column name cuotas_22 */
    public static final String COLUMNNAME_cuotas_22 = "cuotas_22";

	/** Set cuotas_22	  */
	public void setcuotas_22 (BigDecimal cuotas_22);

	/** Get cuotas_22	  */
	public BigDecimal getcuotas_22();

    /** Column name cuotas_23 */
    public static final String COLUMNNAME_cuotas_23 = "cuotas_23";

	/** Set cuotas_23	  */
	public void setcuotas_23 (BigDecimal cuotas_23);

	/** Get cuotas_23	  */
	public BigDecimal getcuotas_23();

    /** Column name cuotas_24 */
    public static final String COLUMNNAME_cuotas_24 = "cuotas_24";

	/** Set cuotas_24	  */
	public void setcuotas_24 (BigDecimal cuotas_24);

	/** Get cuotas_24	  */
	public BigDecimal getcuotas_24();

    /** Column name cuotas_3 */
    public static final String COLUMNNAME_cuotas_3 = "cuotas_3";

	/** Set cuotas_3	  */
	public void setcuotas_3 (BigDecimal cuotas_3);

	/** Get cuotas_3	  */
	public BigDecimal getcuotas_3();

    /** Column name cuotas_4 */
    public static final String COLUMNNAME_cuotas_4 = "cuotas_4";

	/** Set cuotas_4	  */
	public void setcuotas_4 (BigDecimal cuotas_4);

	/** Get cuotas_4	  */
	public BigDecimal getcuotas_4();

    /** Column name cuotas_5 */
    public static final String COLUMNNAME_cuotas_5 = "cuotas_5";

	/** Set cuotas_5	  */
	public void setcuotas_5 (BigDecimal cuotas_5);

	/** Get cuotas_5	  */
	public BigDecimal getcuotas_5();

    /** Column name cuotas_6 */
    public static final String COLUMNNAME_cuotas_6 = "cuotas_6";

	/** Set cuotas_6	  */
	public void setcuotas_6 (BigDecimal cuotas_6);

	/** Get cuotas_6	  */
	public BigDecimal getcuotas_6();

    /** Column name cuotas_7 */
    public static final String COLUMNNAME_cuotas_7 = "cuotas_7";

	/** Set cuotas_7	  */
	public void setcuotas_7 (BigDecimal cuotas_7);

	/** Get cuotas_7	  */
	public BigDecimal getcuotas_7();

    /** Column name cuotas_8 */
    public static final String COLUMNNAME_cuotas_8 = "cuotas_8";

	/** Set cuotas_8	  */
	public void setcuotas_8 (BigDecimal cuotas_8);

	/** Get cuotas_8	  */
	public BigDecimal getcuotas_8();

    /** Column name cuotas_9 */
    public static final String COLUMNNAME_cuotas_9 = "cuotas_9";

	/** Set cuotas_9	  */
	public void setcuotas_9 (BigDecimal cuotas_9);

	/** Get cuotas_9	  */
	public BigDecimal getcuotas_9();

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

    /** Column name UY_Fdu_Afinidad_ID */
    public static final String COLUMNNAME_UY_Fdu_Afinidad_ID = "UY_Fdu_Afinidad_ID";

	/** Set UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID);

	/** Get UY_Fdu_Afinidad	  */
	public int getUY_Fdu_Afinidad_ID();

	public I_UY_Fdu_Afinidad getUY_Fdu_Afinidad() throws RuntimeException;

    /** Column name UY_Fdu_CoefficientHdr_ID */
    public static final String COLUMNNAME_UY_Fdu_CoefficientHdr_ID = "UY_Fdu_CoefficientHdr_ID";

	/** Set UY_Fdu_CoefficientHdr	  */
	public void setUY_Fdu_CoefficientHdr_ID (int UY_Fdu_CoefficientHdr_ID);

	/** Get UY_Fdu_CoefficientHdr	  */
	public int getUY_Fdu_CoefficientHdr_ID();

	public I_UY_Fdu_CoefficientHdr getUY_Fdu_CoefficientHdr() throws RuntimeException;

    /** Column name UY_Fdu_CoefficientLine_ID */
    public static final String COLUMNNAME_UY_Fdu_CoefficientLine_ID = "UY_Fdu_CoefficientLine_ID";

	/** Set UY_Fdu_CoefficientLine	  */
	public void setUY_Fdu_CoefficientLine_ID (int UY_Fdu_CoefficientLine_ID);

	/** Get UY_Fdu_CoefficientLine	  */
	public int getUY_Fdu_CoefficientLine_ID();

    /** Column name UY_Fdu_Currency_ID */
    public static final String COLUMNNAME_UY_Fdu_Currency_ID = "UY_Fdu_Currency_ID";

	/** Set UY_Fdu_Currency	  */
	public void setUY_Fdu_Currency_ID (int UY_Fdu_Currency_ID);

	/** Get UY_Fdu_Currency	  */
	public int getUY_Fdu_Currency_ID();

	public I_UY_Fdu_Currency getUY_Fdu_Currency() throws RuntimeException;

    /** Column name UY_Fdu_Productos_ID */
    public static final String COLUMNNAME_UY_Fdu_Productos_ID = "UY_Fdu_Productos_ID";

	/** Set UY_Fdu_Productos	  */
	public void setUY_Fdu_Productos_ID (int UY_Fdu_Productos_ID);

	/** Get UY_Fdu_Productos	  */
	public int getUY_Fdu_Productos_ID();

	public I_UY_Fdu_Productos getUY_Fdu_Productos() throws RuntimeException;
}
