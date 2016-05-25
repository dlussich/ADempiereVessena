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
/** Generated Model - DO NOT CHANGE */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_Molde_ConciliaTot
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Molde_ConciliaTot extends PO implements I_UY_Molde_ConciliaTot, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131031L;

    /** Standard Constructor */
    public X_UY_Molde_ConciliaTot (Properties ctx, int UY_Molde_ConciliaTot_ID, String trxName)
    {
      super (ctx, UY_Molde_ConciliaTot_ID, trxName);
      /** if (UY_Molde_ConciliaTot_ID == 0)
        {
			setfecreporte (new Timestamp( System.currentTimeMillis() ));
			setidcuenta (0);
			setidReporte (null);
			setidusuario (0);
			settipo (Env.ZERO);
			setUY_Molde_ConciliaTot_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Molde_ConciliaTot (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UY_Molde_ConciliaTot[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amount_bk.
		@param amount_bk amount_bk	  */
	public void setamount_bk (BigDecimal amount_bk)
	{
		set_Value (COLUMNNAME_amount_bk, amount_bk);
	}

	/** Get amount_bk.
		@return amount_bk	  */
	public BigDecimal getamount_bk () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amount_bk);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amount_sys.
		@param amount_sys amount_sys	  */
	public void setamount_sys (BigDecimal amount_sys)
	{
		set_Value (COLUMNNAME_amount_sys, amount_sys);
	}

	/** Get amount_sys.
		@return amount_sys	  */
	public BigDecimal getamount_sys () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amount_sys);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtsumbank.
		@param amtsumbank amtsumbank	  */
	public void setamtsumbank (BigDecimal amtsumbank)
	{
		set_Value (COLUMNNAME_amtsumbank, amtsumbank);
	}

	/** Get amtsumbank.
		@return amtsumbank	  */
	public BigDecimal getamtsumbank () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtsumbank);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtsumcrbank.
		@param amtsumcrbank amtsumcrbank	  */
	public void setamtsumcrbank (BigDecimal amtsumcrbank)
	{
		set_Value (COLUMNNAME_amtsumcrbank, amtsumcrbank);
	}

	/** Get amtsumcrbank.
		@return amtsumcrbank	  */
	public BigDecimal getamtsumcrbank () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtsumcrbank);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtsumcrsystem.
		@param amtsumcrsystem amtsumcrsystem	  */
	public void setamtsumcrsystem (BigDecimal amtsumcrsystem)
	{
		set_Value (COLUMNNAME_amtsumcrsystem, amtsumcrsystem);
	}

	/** Get amtsumcrsystem.
		@return amtsumcrsystem	  */
	public BigDecimal getamtsumcrsystem () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtsumcrsystem);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtsumdrbank.
		@param amtsumdrbank amtsumdrbank	  */
	public void setamtsumdrbank (BigDecimal amtsumdrbank)
	{
		set_Value (COLUMNNAME_amtsumdrbank, amtsumdrbank);
	}

	/** Get amtsumdrbank.
		@return amtsumdrbank	  */
	public BigDecimal getamtsumdrbank () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtsumdrbank);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtsumdrsystem.
		@param amtsumdrsystem amtsumdrsystem	  */
	public void setamtsumdrsystem (BigDecimal amtsumdrsystem)
	{
		set_Value (COLUMNNAME_amtsumdrsystem, amtsumdrsystem);
	}

	/** Get amtsumdrsystem.
		@return amtsumdrsystem	  */
	public BigDecimal getamtsumdrsystem () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtsumdrsystem);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtsumsystem.
		@param amtsumsystem amtsumsystem	  */
	public void setamtsumsystem (BigDecimal amtsumsystem)
	{
		set_Value (COLUMNNAME_amtsumsystem, amtsumsystem);
	}

	/** Get amtsumsystem.
		@return amtsumsystem	  */
	public BigDecimal getamtsumsystem () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtsumsystem);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set concsys.
		@param concsys concsys	  */
	public void setconcsys (BigDecimal concsys)
	{
		set_Value (COLUMNNAME_concsys, concsys);
	}

	/** Get concsys.
		@return concsys	  */
	public BigDecimal getconcsys () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_concsys);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set descgrupoder.
		@param descgrupoder descgrupoder	  */
	public void setdescgrupoder (String descgrupoder)
	{
		set_Value (COLUMNNAME_descgrupoder, descgrupoder);
	}

	/** Get descgrupoder.
		@return descgrupoder	  */
	public String getdescgrupoder () 
	{
		return (String)get_Value(COLUMNNAME_descgrupoder);
	}

	/** Set descgrupoizq.
		@param descgrupoizq descgrupoizq	  */
	public void setdescgrupoizq (String descgrupoizq)
	{
		set_Value (COLUMNNAME_descgrupoizq, descgrupoizq);
	}

	/** Get descgrupoizq.
		@return descgrupoizq	  */
	public String getdescgrupoizq () 
	{
		return (String)get_Value(COLUMNNAME_descgrupoizq);
	}

	/** Set descripcion_bk.
		@param descripcion_bk descripcion_bk	  */
	public void setdescripcion_bk (String descripcion_bk)
	{
		set_Value (COLUMNNAME_descripcion_bk, descripcion_bk);
	}

	/** Get descripcion_bk.
		@return descripcion_bk	  */
	public String getdescripcion_bk () 
	{
		return (String)get_Value(COLUMNNAME_descripcion_bk);
	}

	/** Set descripcion_sys.
		@param descripcion_sys descripcion_sys	  */
	public void setdescripcion_sys (String descripcion_sys)
	{
		set_Value (COLUMNNAME_descripcion_sys, descripcion_sys);
	}

	/** Get descripcion_sys.
		@return descripcion_sys	  */
	public String getdescripcion_sys () 
	{
		return (String)get_Value(COLUMNNAME_descripcion_sys);
	}

	/** Set diferencia.
		@param diferencia diferencia	  */
	public void setdiferencia (BigDecimal diferencia)
	{
		set_Value (COLUMNNAME_diferencia, diferencia);
	}

	/** Get diferencia.
		@return diferencia	  */
	public BigDecimal getdiferencia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_diferencia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set fecdoc_bk.
		@param fecdoc_bk fecdoc_bk	  */
	public void setfecdoc_bk (Timestamp fecdoc_bk)
	{
		set_Value (COLUMNNAME_fecdoc_bk, fecdoc_bk);
	}

	/** Get fecdoc_bk.
		@return fecdoc_bk	  */
	public Timestamp getfecdoc_bk () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecdoc_bk);
	}

	/** Set fecdoc_sys.
		@param fecdoc_sys fecdoc_sys	  */
	public void setfecdoc_sys (Timestamp fecdoc_sys)
	{
		set_Value (COLUMNNAME_fecdoc_sys, fecdoc_sys);
	}

	/** Get fecdoc_sys.
		@return fecdoc_sys	  */
	public Timestamp getfecdoc_sys () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecdoc_sys);
	}

	/** Set fecreporte.
		@param fecreporte fecreporte	  */
	public void setfecreporte (Timestamp fecreporte)
	{
		set_Value (COLUMNNAME_fecreporte, fecreporte);
	}

	/** Get fecreporte.
		@return fecreporte	  */
	public Timestamp getfecreporte () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecreporte);
	}

	/** Set idcuenta.
		@param idcuenta idcuenta	  */
	public void setidcuenta (int idcuenta)
	{
		set_Value (COLUMNNAME_idcuenta, Integer.valueOf(idcuenta));
	}

	/** Get idcuenta.
		@return idcuenta	  */
	public int getidcuenta () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_idcuenta);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set idgrupo.
		@param idgrupo idgrupo	  */
	public void setidgrupo (BigDecimal idgrupo)
	{
		set_Value (COLUMNNAME_idgrupo, idgrupo);
	}

	/** Get idgrupo.
		@return idgrupo	  */
	public BigDecimal getidgrupo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_idgrupo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set idperiodo.
		@param idperiodo idperiodo	  */
	public void setidperiodo (int idperiodo)
	{
		set_Value (COLUMNNAME_idperiodo, Integer.valueOf(idperiodo));
	}

	/** Get idperiodo.
		@return idperiodo	  */
	public int getidperiodo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_idperiodo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Identificador Unico del Reporte.
		@param idReporte Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte)
	{
		set_Value (COLUMNNAME_idReporte, idReporte);
	}

	/** Get Identificador Unico del Reporte.
		@return Identificador Unico del Reporte	  */
	public String getidReporte () 
	{
		return (String)get_Value(COLUMNNAME_idReporte);
	}

	/** Set idusuario.
		@param idusuario idusuario	  */
	public void setidusuario (int idusuario)
	{
		set_Value (COLUMNNAME_idusuario, Integer.valueOf(idusuario));
	}

	/** Get idusuario.
		@return idusuario	  */
	public int getidusuario () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_idusuario);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set saldobanco.
		@param saldobanco saldobanco	  */
	public void setsaldobanco (BigDecimal saldobanco)
	{
		set_Value (COLUMNNAME_saldobanco, saldobanco);
	}

	/** Get saldobanco.
		@return saldobanco	  */
	public BigDecimal getsaldobanco () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_saldobanco);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set saldobancoajus.
		@param saldobancoajus saldobancoajus	  */
	public void setsaldobancoajus (BigDecimal saldobancoajus)
	{
		set_Value (COLUMNNAME_saldobancoajus, saldobancoajus);
	}

	/** Get saldobancoajus.
		@return saldobancoajus	  */
	public BigDecimal getsaldobancoajus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_saldobancoajus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set saldocont.
		@param saldocont saldocont	  */
	public void setsaldocont (BigDecimal saldocont)
	{
		set_Value (COLUMNNAME_saldocont, saldocont);
	}

	/** Get saldocont.
		@return saldocont	  */
	public BigDecimal getsaldocont () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_saldocont);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set saldocontajus.
		@param saldocontajus saldocontajus	  */
	public void setsaldocontajus (BigDecimal saldocontajus)
	{
		set_Value (COLUMNNAME_saldocontajus, saldocontajus);
	}

	/** Get saldocontajus.
		@return saldocontajus	  */
	public BigDecimal getsaldocontajus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_saldocontajus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set tipo.
		@param tipo tipo	  */
	public void settipo (BigDecimal tipo)
	{
		set_Value (COLUMNNAME_tipo, tipo);
	}

	/** Get tipo.
		@return tipo	  */
	public BigDecimal gettipo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_tipo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set titulo.
		@param titulo titulo	  */
	public void settitulo (String titulo)
	{
		set_Value (COLUMNNAME_titulo, titulo);
	}

	/** Get titulo.
		@return titulo	  */
	public String gettitulo () 
	{
		return (String)get_Value(COLUMNNAME_titulo);
	}

	public I_UY_ConciliaBank getUY_ConciliaBank() throws RuntimeException
    {
		return (I_UY_ConciliaBank)MTable.get(getCtx(), I_UY_ConciliaBank.Table_Name)
			.getPO(getUY_ConciliaBank_ID(), get_TrxName());	}

	/** Set UY_ConciliaBank.
		@param UY_ConciliaBank_ID UY_ConciliaBank	  */
	public void setUY_ConciliaBank_ID (int UY_ConciliaBank_ID)
	{
		if (UY_ConciliaBank_ID < 1) 
			set_Value (COLUMNNAME_UY_ConciliaBank_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ConciliaBank_ID, Integer.valueOf(UY_ConciliaBank_ID));
	}

	/** Get UY_ConciliaBank.
		@return UY_ConciliaBank	  */
	public int getUY_ConciliaBank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ConciliaBank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ConciliaSystem getUY_ConciliaSystem() throws RuntimeException
    {
		return (I_UY_ConciliaSystem)MTable.get(getCtx(), I_UY_ConciliaSystem.Table_Name)
			.getPO(getUY_ConciliaSystem_ID(), get_TrxName());	}

	/** Set UY_ConciliaSystem.
		@param UY_ConciliaSystem_ID UY_ConciliaSystem	  */
	public void setUY_ConciliaSystem_ID (int UY_ConciliaSystem_ID)
	{
		if (UY_ConciliaSystem_ID < 1) 
			set_Value (COLUMNNAME_UY_ConciliaSystem_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ConciliaSystem_ID, Integer.valueOf(UY_ConciliaSystem_ID));
	}

	/** Get UY_ConciliaSystem.
		@return UY_ConciliaSystem	  */
	public int getUY_ConciliaSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ConciliaSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Conciliation getUY_Conciliation() throws RuntimeException
    {
		return (I_UY_Conciliation)MTable.get(getCtx(), I_UY_Conciliation.Table_Name)
			.getPO(getUY_Conciliation_ID(), get_TrxName());	}

	/** Set UY_Conciliation.
		@param UY_Conciliation_ID UY_Conciliation	  */
	public void setUY_Conciliation_ID (int UY_Conciliation_ID)
	{
		if (UY_Conciliation_ID < 1) 
			set_Value (COLUMNNAME_UY_Conciliation_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Conciliation_ID, Integer.valueOf(UY_Conciliation_ID));
	}

	/** Get UY_Conciliation.
		@return UY_Conciliation	  */
	public int getUY_Conciliation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Conciliation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_LoadExtract getUY_LoadExtract() throws RuntimeException
    {
		return (I_UY_LoadExtract)MTable.get(getCtx(), I_UY_LoadExtract.Table_Name)
			.getPO(getUY_LoadExtract_ID(), get_TrxName());	}

	/** Set UY_LoadExtract.
		@param UY_LoadExtract_ID UY_LoadExtract	  */
	public void setUY_LoadExtract_ID (int UY_LoadExtract_ID)
	{
		if (UY_LoadExtract_ID < 1) 
			set_Value (COLUMNNAME_UY_LoadExtract_ID, null);
		else 
			set_Value (COLUMNNAME_UY_LoadExtract_ID, Integer.valueOf(UY_LoadExtract_ID));
	}

	/** Get UY_LoadExtract.
		@return UY_LoadExtract	  */
	public int getUY_LoadExtract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LoadExtract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_molde_conciliadet_id_bk.
		@param uy_molde_conciliadet_id_bk uy_molde_conciliadet_id_bk	  */
	public void setuy_molde_conciliadet_id_bk (int uy_molde_conciliadet_id_bk)
	{
		set_Value (COLUMNNAME_uy_molde_conciliadet_id_bk, Integer.valueOf(uy_molde_conciliadet_id_bk));
	}

	/** Get uy_molde_conciliadet_id_bk.
		@return uy_molde_conciliadet_id_bk	  */
	public int getuy_molde_conciliadet_id_bk () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_molde_conciliadet_id_bk);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_molde_conciliadet_id_sys.
		@param uy_molde_conciliadet_id_sys uy_molde_conciliadet_id_sys	  */
	public void setuy_molde_conciliadet_id_sys (int uy_molde_conciliadet_id_sys)
	{
		set_Value (COLUMNNAME_uy_molde_conciliadet_id_sys, Integer.valueOf(uy_molde_conciliadet_id_sys));
	}

	/** Get uy_molde_conciliadet_id_sys.
		@return uy_molde_conciliadet_id_sys	  */
	public int getuy_molde_conciliadet_id_sys () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_molde_conciliadet_id_sys);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Molde_ConciliaTot.
		@param UY_Molde_ConciliaTot_ID UY_Molde_ConciliaTot	  */
	public void setUY_Molde_ConciliaTot_ID (int UY_Molde_ConciliaTot_ID)
	{
		if (UY_Molde_ConciliaTot_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_ConciliaTot_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_ConciliaTot_ID, Integer.valueOf(UY_Molde_ConciliaTot_ID));
	}

	/** Get UY_Molde_ConciliaTot.
		@return UY_Molde_ConciliaTot	  */
	public int getUY_Molde_ConciliaTot_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Molde_ConciliaTot_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}