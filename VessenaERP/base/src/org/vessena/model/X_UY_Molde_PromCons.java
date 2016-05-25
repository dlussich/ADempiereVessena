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

/** Generated Model for UY_Molde_PromCons
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Molde_PromCons extends PO implements I_UY_Molde_PromCons, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131206L;

    /** Standard Constructor */
    public X_UY_Molde_PromCons (Properties ctx, int UY_Molde_PromCons_ID, String trxName)
    {
      super (ctx, UY_Molde_PromCons_ID, trxName);
      /** if (UY_Molde_PromCons_ID == 0)
        {
			setAD_User_ID (0);
			setC_BPartner_ID (0);
			setCostAmt (Env.ZERO);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setidReporte (null);
			setKilometros (Env.ZERO);
			setLitros (Env.ZERO);
			setTotalAmt (Env.ZERO);
			setUY_Molde_PromCons_ID (0);
			setUY_TR_Truck_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Molde_PromCons (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Molde_PromCons[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cost Value.
		@param CostAmt 
		Value with Cost
	  */
	public void setCostAmt (BigDecimal CostAmt)
	{
		set_Value (COLUMNNAME_CostAmt, CostAmt);
	}

	/** Get Cost Value.
		@return Value with Cost
	  */
	public BigDecimal getCostAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set factura.
		@param factura factura	  */
	public void setfactura (String factura)
	{
		set_Value (COLUMNNAME_factura, factura);
	}

	/** Get factura.
		@return factura	  */
	public String getfactura () 
	{
		return (String)get_Value(COLUMNNAME_factura);
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

	/** Set IsFullTank.
		@param IsFullTank IsFullTank	  */
	public void setIsFullTank (int IsFullTank)
	{
		set_Value (COLUMNNAME_IsFullTank, Integer.valueOf(IsFullTank));
	}

	/** Get IsFullTank.
		@return IsFullTank	  */
	public int getIsFullTank () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IsFullTank);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kilometros.
		@param Kilometros Kilometros	  */
	public void setKilometros (BigDecimal Kilometros)
	{
		set_Value (COLUMNNAME_Kilometros, Kilometros);
	}

	/** Get Kilometros.
		@return Kilometros	  */
	public BigDecimal getKilometros () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Kilometros);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Litros.
		@param Litros Litros	  */
	public void setLitros (BigDecimal Litros)
	{
		set_Value (COLUMNNAME_Litros, Litros);
	}

	/** Get Litros.
		@return Litros	  */
	public BigDecimal getLitros () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Litros);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PromedioActual.
		@param PromedioActual PromedioActual	  */
	public void setPromedioActual (BigDecimal PromedioActual)
	{
		set_Value (COLUMNNAME_PromedioActual, PromedioActual);
	}

	/** Get PromedioActual.
		@return PromedioActual	  */
	public BigDecimal getPromedioActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PromedioActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PromedioAcumulado.
		@param PromedioAcumulado PromedioAcumulado	  */
	public void setPromedioAcumulado (BigDecimal PromedioAcumulado)
	{
		set_Value (COLUMNNAME_PromedioAcumulado, PromedioAcumulado);
	}

	/** Get PromedioAcumulado.
		@return PromedioAcumulado	  */
	public BigDecimal getPromedioAcumulado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PromedioAcumulado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_Molde_PromCons.
		@param UY_Molde_PromCons_ID UY_Molde_PromCons	  */
	public void setUY_Molde_PromCons_ID (int UY_Molde_PromCons_ID)
	{
		if (UY_Molde_PromCons_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_PromCons_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Molde_PromCons_ID, Integer.valueOf(UY_Molde_PromCons_ID));
	}

	/** Get UY_Molde_PromCons.
		@return UY_Molde_PromCons	  */
	public int getUY_Molde_PromCons_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Molde_PromCons_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Driver getUY_TR_Driver() throws RuntimeException
    {
		return (I_UY_TR_Driver)MTable.get(getCtx(), I_UY_TR_Driver.Table_Name)
			.getPO(getUY_TR_Driver_ID(), get_TrxName());	}

	/** Set UY_TR_Driver.
		@param UY_TR_Driver_ID UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID)
	{
		if (UY_TR_Driver_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Driver_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Driver_ID, Integer.valueOf(UY_TR_Driver_ID));
	}

	/** Get UY_TR_Driver.
		@return UY_TR_Driver	  */
	public int getUY_TR_Driver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Driver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException
    {
		return (I_UY_TR_Truck)MTable.get(getCtx(), I_UY_TR_Truck.Table_Name)
			.getPO(getUY_TR_Truck_ID(), get_TrxName());	}

	/** Set UY_TR_Truck.
		@param UY_TR_Truck_ID UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID)
	{
		if (UY_TR_Truck_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, Integer.valueOf(UY_TR_Truck_ID));
	}

	/** Get UY_TR_Truck.
		@return UY_TR_Truck	  */
	public int getUY_TR_Truck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}