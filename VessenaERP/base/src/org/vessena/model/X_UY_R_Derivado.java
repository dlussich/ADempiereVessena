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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_R_Derivado
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_Derivado extends PO implements I_UY_R_Derivado, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130813L;

    /** Standard Constructor */
    public X_UY_R_Derivado (Properties ctx, int UY_R_Derivado_ID, String trxName)
    {
      super (ctx, UY_R_Derivado_ID, trxName);
      /** if (UY_R_Derivado_ID == 0)
        {
			setName (null);
			setUY_R_Derivado_ID (0);
			setUY_R_Reclamo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_Derivado (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_Derivado[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Cargos.
		@param Cargos Cargos	  */
	public void setCargos (int Cargos)
	{
		set_Value (COLUMNNAME_Cargos, Integer.valueOf(Cargos));
	}

	/** Get Cargos.
		@return Cargos	  */
	public int getCargos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cargos);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DigitoVerificador.
		@param DigitoVerificador DigitoVerificador	  */
	public void setDigitoVerificador (int DigitoVerificador)
	{
		set_Value (COLUMNNAME_DigitoVerificador, Integer.valueOf(DigitoVerificador));
	}

	/** Get DigitoVerificador.
		@return DigitoVerificador	  */
	public int getDigitoVerificador () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DigitoVerificador);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DueDateTitular.
		@param DueDateTitular DueDateTitular	  */
	public void setDueDateTitular (String DueDateTitular)
	{
		set_Value (COLUMNNAME_DueDateTitular, DueDateTitular);
	}

	/** Get DueDateTitular.
		@return DueDateTitular	  */
	public String getDueDateTitular () 
	{
		return (String)get_Value(COLUMNNAME_DueDateTitular);
	}

	/** Set GAFCOD.
		@param GAFCOD GAFCOD	  */
	public void setGAFCOD (int GAFCOD)
	{
		set_Value (COLUMNNAME_GAFCOD, Integer.valueOf(GAFCOD));
	}

	/** Get GAFCOD.
		@return GAFCOD	  */
	public int getGAFCOD () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GAFCOD);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GrpCtaCte.
		@param GrpCtaCte GrpCtaCte	  */
	public void setGrpCtaCte (int GrpCtaCte)
	{
		set_Value (COLUMNNAME_GrpCtaCte, Integer.valueOf(GrpCtaCte));
	}

	/** Get GrpCtaCte.
		@return GrpCtaCte	  */
	public int getGrpCtaCte () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GrpCtaCte);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MLCod.
		@param MLCod MLCod	  */
	public void setMLCod (String MLCod)
	{
		set_Value (COLUMNNAME_MLCod, MLCod);
	}

	/** Get MLCod.
		@return MLCod	  */
	public String getMLCod () 
	{
		return (String)get_Value(COLUMNNAME_MLCod);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set NroTarjetaTitular.
		@param NroTarjetaTitular NroTarjetaTitular	  */
	public void setNroTarjetaTitular (String NroTarjetaTitular)
	{
		set_Value (COLUMNNAME_NroTarjetaTitular, NroTarjetaTitular);
	}

	/** Get NroTarjetaTitular.
		@return NroTarjetaTitular	  */
	public String getNroTarjetaTitular () 
	{
		return (String)get_Value(COLUMNNAME_NroTarjetaTitular);
	}

	/** Set Parametros.
		@param Parametros Parametros	  */
	public void setParametros (int Parametros)
	{
		set_Value (COLUMNNAME_Parametros, Integer.valueOf(Parametros));
	}

	/** Get Parametros.
		@return Parametros	  */
	public int getParametros () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parametros);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tasas.
		@param Tasas Tasas	  */
	public void setTasas (int Tasas)
	{
		set_Value (COLUMNNAME_Tasas, Integer.valueOf(Tasas));
	}

	/** Get Tasas.
		@return Tasas	  */
	public int getTasas () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Tasas);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TipoSocio.
		@param TipoSocio TipoSocio	  */
	public void setTipoSocio (int TipoSocio)
	{
		set_Value (COLUMNNAME_TipoSocio, Integer.valueOf(TipoSocio));
	}

	/** Get TipoSocio.
		@return TipoSocio	  */
	public int getTipoSocio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TipoSocio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Derivado.
		@param UY_R_Derivado_ID UY_R_Derivado	  */
	public void setUY_R_Derivado_ID (int UY_R_Derivado_ID)
	{
		if (UY_R_Derivado_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_Derivado_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_Derivado_ID, Integer.valueOf(UY_R_Derivado_ID));
	}

	/** Get UY_R_Derivado.
		@return UY_R_Derivado	  */
	public int getUY_R_Derivado_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Derivado_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException
    {
		return (I_UY_R_Reclamo)MTable.get(getCtx(), I_UY_R_Reclamo.Table_Name)
			.getPO(getUY_R_Reclamo_ID(), get_TrxName());	}

	/** Set UY_R_Reclamo.
		@param UY_R_Reclamo_ID UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID)
	{
		if (UY_R_Reclamo_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, Integer.valueOf(UY_R_Reclamo_ID));
	}

	/** Get UY_R_Reclamo.
		@return UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}