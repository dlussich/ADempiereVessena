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

/** Generated Model for UY_Molde_ConciliaDet
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Molde_ConciliaDet extends PO implements I_UY_Molde_ConciliaDet, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130503L;

    /** Standard Constructor */
    public X_UY_Molde_ConciliaDet (Properties ctx, int UY_Molde_ConciliaDet_ID, String trxName)
    {
      super (ctx, UY_Molde_ConciliaDet_ID, trxName);
      /** if (UY_Molde_ConciliaDet_ID == 0)
        {
			setAmount (Env.ZERO);
			setfecdoc (new Timestamp( System.currentTimeMillis() ));
			setfecreporte (new Timestamp( System.currentTimeMillis() ));
			setidcuenta (0);
			setidReporte (null);
			setidusuario (0);
			settipo (Env.ZERO);
			setUY_Molde_ConciliaDet_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Molde_ConciliaDet (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Molde_ConciliaDet[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
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

	/** Set descripcion.
		@param descripcion descripcion	  */
	public void setdescripcion (String descripcion)
	{
		set_Value (COLUMNNAME_descripcion, descripcion);
	}

	/** Get descripcion.
		@return descripcion	  */
	public String getdescripcion () 
	{
		return (String)get_Value(COLUMNNAME_descripcion);
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

	/** Set fecdoc.
		@param fecdoc fecdoc	  */
	public void setfecdoc (Timestamp fecdoc)
	{
		set_Value (COLUMNNAME_fecdoc, fecdoc);
	}

	/** Get fecdoc.
		@return fecdoc	  */
	public Timestamp getfecdoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fecdoc);
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

	/** Set Error.
		@param IsError 
		An Error occurred in the execution
	  */
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Error.
		@return An Error occurred in the execution
	  */
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set UY_Molde_ConciliaDet.
		@param UY_Molde_ConciliaDet_ID UY_Molde_ConciliaDet	  */
	public void setUY_Molde_ConciliaDet_ID (int UY_Molde_ConciliaDet_ID)
	{
		if (UY_Molde_ConciliaDet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_ConciliaDet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_ConciliaDet_ID, Integer.valueOf(UY_Molde_ConciliaDet_ID));
	}

	/** Get UY_Molde_ConciliaDet.
		@return UY_Molde_ConciliaDet	  */
	public int getUY_Molde_ConciliaDet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Molde_ConciliaDet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}