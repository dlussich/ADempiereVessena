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

/** Generated Model for UY_FDU_ControlResultDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_FDU_ControlResultDetail extends PO implements I_UY_FDU_ControlResultDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130227L;

    /** Standard Constructor */
    public X_UY_FDU_ControlResultDetail (Properties ctx, int UY_FDU_ControlResultDetail_ID, String trxName)
    {
      super (ctx, UY_FDU_ControlResultDetail_ID, trxName);
      /** if (UY_FDU_ControlResultDetail_ID == 0)
        {
			setSeqNo (0);
			setSuccess (false);
			setUY_FDU_ControlResultDetail_ID (0);
			setUY_FDU_ControlResult_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FDU_ControlResultDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FDU_ControlResultDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account.
		@param Account Account	  */
	public void setAccount (String Account)
	{
		set_Value (COLUMNNAME_Account, Account);
	}

	/** Get Account.
		@return Account	  */
	public String getAccount () 
	{
		return (String)get_Value(COLUMNNAME_Account);
	}

	/** Set Agencia.
		@param Agencia 
		Agencia
	  */
	public void setAgencia (String Agencia)
	{
		set_Value (COLUMNNAME_Agencia, Agencia);
	}

	/** Get Agencia.
		@return Agencia
	  */
	public String getAgencia () 
	{
		return (String)get_Value(COLUMNNAME_Agencia);
	}

	/** Set Autorizador.
		@param Autorizador 
		Autorizador
	  */
	public void setAutorizador (String Autorizador)
	{
		set_Value (COLUMNNAME_Autorizador, Autorizador);
	}

	/** Get Autorizador.
		@return Autorizador
	  */
	public String getAutorizador () 
	{
		return (String)get_Value(COLUMNNAME_Autorizador);
	}

	/** Set codigo.
		@param codigo codigo	  */
	public void setcodigo (String codigo)
	{
		set_Value (COLUMNNAME_codigo, codigo);
	}

	/** Get codigo.
		@return codigo	  */
	public String getcodigo () 
	{
		return (String)get_Value(COLUMNNAME_codigo);
	}

	/** Set CoefAdempiere.
		@param CoefAdempiere 
		CoefAdempiere
	  */
	public void setCoefAdempiere (BigDecimal CoefAdempiere)
	{
		set_Value (COLUMNNAME_CoefAdempiere, CoefAdempiere);
	}

	/** Get CoefAdempiere.
		@return CoefAdempiere
	  */
	public BigDecimal getCoefAdempiere () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CoefAdempiere);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set comercio.
		@param comercio comercio	  */
	public void setcomercio (String comercio)
	{
		set_Value (COLUMNNAME_comercio, comercio);
	}

	/** Get comercio.
		@return comercio	  */
	public String getcomercio () 
	{
		return (String)get_Value(COLUMNNAME_comercio);
	}

	/** Set Cuotas.
		@param Cuotas 
		Cuotas
	  */
	public void setCuotas (String Cuotas)
	{
		set_Value (COLUMNNAME_Cuotas, Cuotas);
	}

	/** Get Cuotas.
		@return Cuotas
	  */
	public String getCuotas () 
	{
		return (String)get_Value(COLUMNNAME_Cuotas);
	}

	/** Set Cupon.
		@param Cupon Cupon	  */
	public void setCupon (String Cupon)
	{
		set_Value (COLUMNNAME_Cupon, Cupon);
	}

	/** Get Cupon.
		@return Cupon	  */
	public String getCupon () 
	{
		return (String)get_Value(COLUMNNAME_Cupon);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set DeadLinePrevious.
		@param DeadLinePrevious 
		DeadLinePrevious
	  */
	public void setDeadLinePrevious (Timestamp DeadLinePrevious)
	{
		set_Value (COLUMNNAME_DeadLinePrevious, DeadLinePrevious);
	}

	/** Get DeadLinePrevious.
		@return DeadLinePrevious
	  */
	public Timestamp getDeadLinePrevious () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DeadLinePrevious);
	}

	/** Set DiferenciaDolares.
		@param DiferenciaDolares 
		DiferenciaDolares
	  */
	public void setDiferenciaDolares (BigDecimal DiferenciaDolares)
	{
		set_Value (COLUMNNAME_DiferenciaDolares, DiferenciaDolares);
	}

	/** Get DiferenciaDolares.
		@return DiferenciaDolares
	  */
	public BigDecimal getDiferenciaDolares () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiferenciaDolares);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DiferenciaPesos.
		@param DiferenciaPesos 
		DiferenciaPesos
	  */
	public void setDiferenciaPesos (BigDecimal DiferenciaPesos)
	{
		set_Value (COLUMNNAME_DiferenciaPesos, DiferenciaPesos);
	}

	/** Get DiferenciaPesos.
		@return DiferenciaPesos
	  */
	public BigDecimal getDiferenciaPesos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiferenciaPesos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ejecutor.
		@param Ejecutor 
		Ejecutor
	  */
	public void setEjecutor (String Ejecutor)
	{
		set_Value (COLUMNNAME_Ejecutor, Ejecutor);
	}

	/** Get Ejecutor.
		@return Ejecutor
	  */
	public String getEjecutor () 
	{
		return (String)get_Value(COLUMNNAME_Ejecutor);
	}

	/** Set InteresAdempiere.
		@param InteresAdempiere 
		InteresAdempiere
	  */
	public void setInteresAdempiere (BigDecimal InteresAdempiere)
	{
		set_Value (COLUMNNAME_InteresAdempiere, InteresAdempiere);
	}

	/** Get InteresAdempiere.
		@return InteresAdempiere
	  */
	public BigDecimal getInteresAdempiere () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InteresAdempiere);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set InteresFinancial.
		@param InteresFinancial 
		InteresFinancial
	  */
	public void setInteresFinancial (BigDecimal InteresFinancial)
	{
		set_Value (COLUMNNAME_InteresFinancial, InteresFinancial);
	}

	/** Get InteresFinancial.
		@return InteresFinancial
	  */
	public BigDecimal getInteresFinancial () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InteresFinancial);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Message.
		@param Message 
		EMail Message
	  */
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return EMail Message
	  */
	public String getMessage () 
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	/** Set MontoCuota.
		@param MontoCuota 
		MontoCuota
	  */
	public void setMontoCuota (BigDecimal MontoCuota)
	{
		set_Value (COLUMNNAME_MontoCuota, MontoCuota);
	}

	/** Get MontoCuota.
		@return MontoCuota
	  */
	public BigDecimal getMontoCuota () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MontoCuota);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SaldoFinalDolares.
		@param SaldoFinalDolares 
		SaldoFinalDolares
	  */
	public void setSaldoFinalDolares (BigDecimal SaldoFinalDolares)
	{
		set_Value (COLUMNNAME_SaldoFinalDolares, SaldoFinalDolares);
	}

	/** Get SaldoFinalDolares.
		@return SaldoFinalDolares
	  */
	public BigDecimal getSaldoFinalDolares () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SaldoFinalDolares);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SaldoFinalPesos.
		@param SaldoFinalPesos 
		SaldoFinalPesos
	  */
	public void setSaldoFinalPesos (BigDecimal SaldoFinalPesos)
	{
		set_Value (COLUMNNAME_SaldoFinalPesos, SaldoFinalPesos);
	}

	/** Get SaldoFinalPesos.
		@return SaldoFinalPesos
	  */
	public BigDecimal getSaldoFinalPesos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SaldoFinalPesos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SaldoInicialDolares.
		@param SaldoInicialDolares 
		SaldoInicialDolares
	  */
	public void setSaldoInicialDolares (BigDecimal SaldoInicialDolares)
	{
		set_Value (COLUMNNAME_SaldoInicialDolares, SaldoInicialDolares);
	}

	/** Get SaldoInicialDolares.
		@return SaldoInicialDolares
	  */
	public BigDecimal getSaldoInicialDolares () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SaldoInicialDolares);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SaldoInicialPesos.
		@param SaldoInicialPesos 
		SaldoInicialPesos
	  */
	public void setSaldoInicialPesos (BigDecimal SaldoInicialPesos)
	{
		set_Value (COLUMNNAME_SaldoInicialPesos, SaldoInicialPesos);
	}

	/** Get SaldoInicialPesos.
		@return SaldoInicialPesos
	  */
	public BigDecimal getSaldoInicialPesos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SaldoInicialPesos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Success.
		@param Success Success	  */
	public void setSuccess (boolean Success)
	{
		set_Value (COLUMNNAME_Success, Boolean.valueOf(Success));
	}

	/** Get Success.
		@return Success	  */
	public boolean isSuccess () 
	{
		Object oo = get_Value(COLUMNNAME_Success);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_Fdu_Afinidad getUY_Fdu_Afinidad() throws RuntimeException
    {
		return (I_UY_Fdu_Afinidad)MTable.get(getCtx(), I_UY_Fdu_Afinidad.Table_Name)
			.getPO(getUY_Fdu_Afinidad_ID(), get_TrxName());	}

	/** Set UY_Fdu_Afinidad.
		@param UY_Fdu_Afinidad_ID UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID)
	{
		if (UY_Fdu_Afinidad_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Afinidad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Afinidad_ID, Integer.valueOf(UY_Fdu_Afinidad_ID));
	}

	/** Get UY_Fdu_Afinidad.
		@return UY_Fdu_Afinidad	  */
	public int getUY_Fdu_Afinidad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Afinidad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FDU_Control getUY_FDU_Control() throws RuntimeException
    {
		return (I_UY_FDU_Control)MTable.get(getCtx(), I_UY_FDU_Control.Table_Name)
			.getPO(getUY_FDU_Control_ID(), get_TrxName());	}

	/** Set UY_FDU_Control.
		@param UY_FDU_Control_ID UY_FDU_Control	  */
	public void setUY_FDU_Control_ID (int UY_FDU_Control_ID)
	{
		if (UY_FDU_Control_ID < 1) 
			set_Value (COLUMNNAME_UY_FDU_Control_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FDU_Control_ID, Integer.valueOf(UY_FDU_Control_ID));
	}

	/** Get UY_FDU_Control.
		@return UY_FDU_Control	  */
	public int getUY_FDU_Control_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_Control_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FDU_ControlResultDetail.
		@param UY_FDU_ControlResultDetail_ID UY_FDU_ControlResultDetail	  */
	public void setUY_FDU_ControlResultDetail_ID (int UY_FDU_ControlResultDetail_ID)
	{
		if (UY_FDU_ControlResultDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FDU_ControlResultDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FDU_ControlResultDetail_ID, Integer.valueOf(UY_FDU_ControlResultDetail_ID));
	}

	/** Get UY_FDU_ControlResultDetail.
		@return UY_FDU_ControlResultDetail	  */
	public int getUY_FDU_ControlResultDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_ControlResultDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FDU_ControlResult getUY_FDU_ControlResult() throws RuntimeException
    {
		return (I_UY_FDU_ControlResult)MTable.get(getCtx(), I_UY_FDU_ControlResult.Table_Name)
			.getPO(getUY_FDU_ControlResult_ID(), get_TrxName());	}

	/** Set UY_FDU_ControlResult.
		@param UY_FDU_ControlResult_ID UY_FDU_ControlResult	  */
	public void setUY_FDU_ControlResult_ID (int UY_FDU_ControlResult_ID)
	{
		if (UY_FDU_ControlResult_ID < 1) 
			set_Value (COLUMNNAME_UY_FDU_ControlResult_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FDU_ControlResult_ID, Integer.valueOf(UY_FDU_ControlResult_ID));
	}

	/** Get UY_FDU_ControlResult.
		@return UY_FDU_ControlResult	  */
	public int getUY_FDU_ControlResult_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_ControlResult_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Productos getUY_Fdu_Productos() throws RuntimeException
    {
		return (I_UY_Fdu_Productos)MTable.get(getCtx(), I_UY_Fdu_Productos.Table_Name)
			.getPO(getUY_Fdu_Productos_ID(), get_TrxName());	}

	/** Set UY_Fdu_Productos.
		@param UY_Fdu_Productos_ID UY_Fdu_Productos	  */
	public void setUY_Fdu_Productos_ID (int UY_Fdu_Productos_ID)
	{
		if (UY_Fdu_Productos_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Productos_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Productos_ID, Integer.valueOf(UY_Fdu_Productos_ID));
	}

	/** Get UY_Fdu_Productos.
		@return UY_Fdu_Productos	  */
	public int getUY_Fdu_Productos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Productos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Working Time.
		@param WorkingTime 
		Workflow Simulation Execution Time
	  */
	public void setWorkingTime (String WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, WorkingTime);
	}

	/** Get Working Time.
		@return Workflow Simulation Execution Time
	  */
	public String getWorkingTime () 
	{
		return (String)get_Value(COLUMNNAME_WorkingTime);
	}
}