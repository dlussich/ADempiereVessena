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

/** Generated Interface for UY_Molde_ConciliaTot
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_Molde_ConciliaTot 
{

    /** TableName=UY_Molde_ConciliaTot */
    public static final String Table_Name = "UY_Molde_ConciliaTot";

    /** AD_Table_ID=1000520 */
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

    /** Column name amount_bk */
    public static final String COLUMNNAME_amount_bk = "amount_bk";

	/** Set amount_bk	  */
	public void setamount_bk (BigDecimal amount_bk);

	/** Get amount_bk	  */
	public BigDecimal getamount_bk();

    /** Column name amount_sys */
    public static final String COLUMNNAME_amount_sys = "amount_sys";

	/** Set amount_sys	  */
	public void setamount_sys (BigDecimal amount_sys);

	/** Get amount_sys	  */
	public BigDecimal getamount_sys();

    /** Column name amtsumbank */
    public static final String COLUMNNAME_amtsumbank = "amtsumbank";

	/** Set amtsumbank	  */
	public void setamtsumbank (BigDecimal amtsumbank);

	/** Get amtsumbank	  */
	public BigDecimal getamtsumbank();

    /** Column name amtsumcrbank */
    public static final String COLUMNNAME_amtsumcrbank = "amtsumcrbank";

	/** Set amtsumcrbank	  */
	public void setamtsumcrbank (BigDecimal amtsumcrbank);

	/** Get amtsumcrbank	  */
	public BigDecimal getamtsumcrbank();

    /** Column name amtsumcrsystem */
    public static final String COLUMNNAME_amtsumcrsystem = "amtsumcrsystem";

	/** Set amtsumcrsystem	  */
	public void setamtsumcrsystem (BigDecimal amtsumcrsystem);

	/** Get amtsumcrsystem	  */
	public BigDecimal getamtsumcrsystem();

    /** Column name amtsumdrbank */
    public static final String COLUMNNAME_amtsumdrbank = "amtsumdrbank";

	/** Set amtsumdrbank	  */
	public void setamtsumdrbank (BigDecimal amtsumdrbank);

	/** Get amtsumdrbank	  */
	public BigDecimal getamtsumdrbank();

    /** Column name amtsumdrsystem */
    public static final String COLUMNNAME_amtsumdrsystem = "amtsumdrsystem";

	/** Set amtsumdrsystem	  */
	public void setamtsumdrsystem (BigDecimal amtsumdrsystem);

	/** Get amtsumdrsystem	  */
	public BigDecimal getamtsumdrsystem();

    /** Column name amtsumsystem */
    public static final String COLUMNNAME_amtsumsystem = "amtsumsystem";

	/** Set amtsumsystem	  */
	public void setamtsumsystem (BigDecimal amtsumsystem);

	/** Get amtsumsystem	  */
	public BigDecimal getamtsumsystem();

    /** Column name concsys */
    public static final String COLUMNNAME_concsys = "concsys";

	/** Set concsys	  */
	public void setconcsys (BigDecimal concsys);

	/** Get concsys	  */
	public BigDecimal getconcsys();

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

    /** Column name descgrupoder */
    public static final String COLUMNNAME_descgrupoder = "descgrupoder";

	/** Set descgrupoder	  */
	public void setdescgrupoder (String descgrupoder);

	/** Get descgrupoder	  */
	public String getdescgrupoder();

    /** Column name descgrupoizq */
    public static final String COLUMNNAME_descgrupoizq = "descgrupoizq";

	/** Set descgrupoizq	  */
	public void setdescgrupoizq (String descgrupoizq);

	/** Get descgrupoizq	  */
	public String getdescgrupoizq();

    /** Column name descripcion_bk */
    public static final String COLUMNNAME_descripcion_bk = "descripcion_bk";

	/** Set descripcion_bk	  */
	public void setdescripcion_bk (String descripcion_bk);

	/** Get descripcion_bk	  */
	public String getdescripcion_bk();

    /** Column name descripcion_sys */
    public static final String COLUMNNAME_descripcion_sys = "descripcion_sys";

	/** Set descripcion_sys	  */
	public void setdescripcion_sys (String descripcion_sys);

	/** Get descripcion_sys	  */
	public String getdescripcion_sys();

    /** Column name diferencia */
    public static final String COLUMNNAME_diferencia = "diferencia";

	/** Set diferencia	  */
	public void setdiferencia (BigDecimal diferencia);

	/** Get diferencia	  */
	public BigDecimal getdiferencia();

    /** Column name fecdoc_bk */
    public static final String COLUMNNAME_fecdoc_bk = "fecdoc_bk";

	/** Set fecdoc_bk	  */
	public void setfecdoc_bk (Timestamp fecdoc_bk);

	/** Get fecdoc_bk	  */
	public Timestamp getfecdoc_bk();

    /** Column name fecdoc_sys */
    public static final String COLUMNNAME_fecdoc_sys = "fecdoc_sys";

	/** Set fecdoc_sys	  */
	public void setfecdoc_sys (Timestamp fecdoc_sys);

	/** Get fecdoc_sys	  */
	public Timestamp getfecdoc_sys();

    /** Column name fecreporte */
    public static final String COLUMNNAME_fecreporte = "fecreporte";

	/** Set fecreporte	  */
	public void setfecreporte (Timestamp fecreporte);

	/** Get fecreporte	  */
	public Timestamp getfecreporte();

    /** Column name idcuenta */
    public static final String COLUMNNAME_idcuenta = "idcuenta";

	/** Set idcuenta	  */
	public void setidcuenta (int idcuenta);

	/** Get idcuenta	  */
	public int getidcuenta();

    /** Column name idgrupo */
    public static final String COLUMNNAME_idgrupo = "idgrupo";

	/** Set idgrupo	  */
	public void setidgrupo (BigDecimal idgrupo);

	/** Get idgrupo	  */
	public BigDecimal getidgrupo();

    /** Column name idperiodo */
    public static final String COLUMNNAME_idperiodo = "idperiodo";

	/** Set idperiodo	  */
	public void setidperiodo (int idperiodo);

	/** Get idperiodo	  */
	public int getidperiodo();

    /** Column name idReporte */
    public static final String COLUMNNAME_idReporte = "idReporte";

	/** Set Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte);

	/** Get Identificador Unico del Reporte	  */
	public String getidReporte();

    /** Column name idusuario */
    public static final String COLUMNNAME_idusuario = "idusuario";

	/** Set idusuario	  */
	public void setidusuario (int idusuario);

	/** Get idusuario	  */
	public int getidusuario();

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

    /** Column name saldobanco */
    public static final String COLUMNNAME_saldobanco = "saldobanco";

	/** Set saldobanco	  */
	public void setsaldobanco (BigDecimal saldobanco);

	/** Get saldobanco	  */
	public BigDecimal getsaldobanco();

    /** Column name saldobancoajus */
    public static final String COLUMNNAME_saldobancoajus = "saldobancoajus";

	/** Set saldobancoajus	  */
	public void setsaldobancoajus (BigDecimal saldobancoajus);

	/** Get saldobancoajus	  */
	public BigDecimal getsaldobancoajus();

    /** Column name saldocont */
    public static final String COLUMNNAME_saldocont = "saldocont";

	/** Set saldocont	  */
	public void setsaldocont (BigDecimal saldocont);

	/** Get saldocont	  */
	public BigDecimal getsaldocont();

    /** Column name saldocontajus */
    public static final String COLUMNNAME_saldocontajus = "saldocontajus";

	/** Set saldocontajus	  */
	public void setsaldocontajus (BigDecimal saldocontajus);

	/** Get saldocontajus	  */
	public BigDecimal getsaldocontajus();

    /** Column name tipo */
    public static final String COLUMNNAME_tipo = "tipo";

	/** Set tipo	  */
	public void settipo (BigDecimal tipo);

	/** Get tipo	  */
	public BigDecimal gettipo();

    /** Column name titulo */
    public static final String COLUMNNAME_titulo = "titulo";

	/** Set titulo	  */
	public void settitulo (String titulo);

	/** Get titulo	  */
	public String gettitulo();

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

    /** Column name UY_ConciliaBank_ID */
    public static final String COLUMNNAME_UY_ConciliaBank_ID = "UY_ConciliaBank_ID";

	/** Set UY_ConciliaBank	  */
	public void setUY_ConciliaBank_ID (int UY_ConciliaBank_ID);

	/** Get UY_ConciliaBank	  */
	public int getUY_ConciliaBank_ID();

	public I_UY_ConciliaBank getUY_ConciliaBank() throws RuntimeException;

    /** Column name UY_ConciliaSystem_ID */
    public static final String COLUMNNAME_UY_ConciliaSystem_ID = "UY_ConciliaSystem_ID";

	/** Set UY_ConciliaSystem	  */
	public void setUY_ConciliaSystem_ID (int UY_ConciliaSystem_ID);

	/** Get UY_ConciliaSystem	  */
	public int getUY_ConciliaSystem_ID();

	public I_UY_ConciliaSystem getUY_ConciliaSystem() throws RuntimeException;

    /** Column name UY_Conciliation_ID */
    public static final String COLUMNNAME_UY_Conciliation_ID = "UY_Conciliation_ID";

	/** Set UY_Conciliation	  */
	public void setUY_Conciliation_ID (int UY_Conciliation_ID);

	/** Get UY_Conciliation	  */
	public int getUY_Conciliation_ID();

	public I_UY_Conciliation getUY_Conciliation() throws RuntimeException;

    /** Column name UY_LoadExtract_ID */
    public static final String COLUMNNAME_UY_LoadExtract_ID = "UY_LoadExtract_ID";

	/** Set UY_LoadExtract	  */
	public void setUY_LoadExtract_ID (int UY_LoadExtract_ID);

	/** Get UY_LoadExtract	  */
	public int getUY_LoadExtract_ID();

	public I_UY_LoadExtract getUY_LoadExtract() throws RuntimeException;

    /** Column name uy_molde_conciliadet_id_bk */
    public static final String COLUMNNAME_uy_molde_conciliadet_id_bk = "uy_molde_conciliadet_id_bk";

	/** Set uy_molde_conciliadet_id_bk	  */
	public void setuy_molde_conciliadet_id_bk (int uy_molde_conciliadet_id_bk);

	/** Get uy_molde_conciliadet_id_bk	  */
	public int getuy_molde_conciliadet_id_bk();

    /** Column name uy_molde_conciliadet_id_sys */
    public static final String COLUMNNAME_uy_molde_conciliadet_id_sys = "uy_molde_conciliadet_id_sys";

	/** Set uy_molde_conciliadet_id_sys	  */
	public void setuy_molde_conciliadet_id_sys (int uy_molde_conciliadet_id_sys);

	/** Get uy_molde_conciliadet_id_sys	  */
	public int getuy_molde_conciliadet_id_sys();

    /** Column name UY_Molde_ConciliaTot_ID */
    public static final String COLUMNNAME_UY_Molde_ConciliaTot_ID = "UY_Molde_ConciliaTot_ID";

	/** Set UY_Molde_ConciliaTot	  */
	public void setUY_Molde_ConciliaTot_ID (int UY_Molde_ConciliaTot_ID);

	/** Get UY_Molde_ConciliaTot	  */
	public int getUY_Molde_ConciliaTot_ID();
}
