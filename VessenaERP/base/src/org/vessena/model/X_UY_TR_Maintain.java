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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TR_Maintain
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Maintain extends PO implements I_UY_TR_Maintain, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150817L;

    /** Standard Constructor */
    public X_UY_TR_Maintain (Properties ctx, int UY_TR_Maintain_ID, String trxName)
    {
      super (ctx, UY_TR_Maintain_ID, trxName);
      /** if (UY_TR_Maintain_ID == 0)
        {
			setMaintainProg (null);
// KILOMETRAJE
			setMaintainType (null);
// PREVISION
			setName (null);
			setUY_TR_Maintain_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Maintain (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Maintain[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getConsume_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getConsume_Acct_ID(), get_TrxName());	}

	/** Set Consume_Acct_ID.
		@param Consume_Acct_ID 
		Consume_Acct_ID
	  */
	public void setConsume_Acct_ID (int Consume_Acct_ID)
	{
		if (Consume_Acct_ID < 1) 
			set_Value (COLUMNNAME_Consume_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_Consume_Acct_ID, Integer.valueOf(Consume_Acct_ID));
	}

	/** Get Consume_Acct_ID.
		@return Consume_Acct_ID
	  */
	public int getConsume_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Consume_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Kilometros.
		@param Kilometros Kilometros	  */
	public void setKilometros (int Kilometros)
	{
		set_Value (COLUMNNAME_Kilometros, Integer.valueOf(Kilometros));
	}

	/** Get Kilometros.
		@return Kilometros	  */
	public int getKilometros () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Kilometros);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** MaintainProg AD_Reference_ID=1000389 */
	public static final int MAINTAINPROG_AD_Reference_ID=1000389;
	/** POR KILOMETRAJE = KILOMETRAJE */
	public static final String MAINTAINPROG_PORKILOMETRAJE = "KILOMETRAJE";
	/** POR PERIODOS DE FECHA = FECHA */
	public static final String MAINTAINPROG_PORPERIODOSDEFECHA = "FECHA";
	/** POR KILOMETRAJE Y FECHA = KILOFECHA */
	public static final String MAINTAINPROG_PORKILOMETRAJEYFECHA = "KILOFECHA";
	/** PROGRAMACION LIBRE = LIBRE */
	public static final String MAINTAINPROG_PROGRAMACIONLIBRE = "LIBRE";
	/** Set MaintainProg.
		@param MaintainProg 
		Tipo de programacion de una tarea de mantenimiento de transporte
	  */
	public void setMaintainProg (String MaintainProg)
	{

		set_Value (COLUMNNAME_MaintainProg, MaintainProg);
	}

	/** Get MaintainProg.
		@return Tipo de programacion de una tarea de mantenimiento de transporte
	  */
	public String getMaintainProg () 
	{
		return (String)get_Value(COLUMNNAME_MaintainProg);
	}

	/** MaintainType AD_Reference_ID=1000388 */
	public static final int MAINTAINTYPE_AD_Reference_ID=1000388;
	/** PREVENTIVO = PREVENTIVO */
	public static final String MAINTAINTYPE_PREVENTIVO = "PREVENTIVO";
	/** REVISION = REVISION */
	public static final String MAINTAINTYPE_REVISION = "REVISION";
	/** Set MaintainType.
		@param MaintainType MaintainType	  */
	public void setMaintainType (String MaintainType)
	{

		set_Value (COLUMNNAME_MaintainType, MaintainType);
	}

	/** Get MaintainType.
		@return MaintainType	  */
	public String getMaintainType () 
	{
		return (String)get_Value(COLUMNNAME_MaintainType);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	public I_C_ValidCombination getPO_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getPO_Acct_ID(), get_TrxName());	}

	/** Set PO_Acct_ID.
		@param PO_Acct_ID 
		PO_Acct_ID
	  */
	public void setPO_Acct_ID (int PO_Acct_ID)
	{
		if (PO_Acct_ID < 1) 
			set_Value (COLUMNNAME_PO_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_PO_Acct_ID, Integer.valueOf(PO_Acct_ID));
	}

	/** Get PO_Acct_ID.
		@return PO_Acct_ID
	  */
	public int getPO_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set porcentaje.
		@param porcentaje porcentaje	  */
	public void setporcentaje (BigDecimal porcentaje)
	{
		set_Value (COLUMNNAME_porcentaje, porcentaje);
	}

	/** Get porcentaje.
		@return porcentaje	  */
	public BigDecimal getporcentaje () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcentaje);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (int Qty)
	{
		set_Value (COLUMNNAME_Qty, Integer.valueOf(Qty));
	}

	/** Get Quantity.
		@return Quantity
	  */
	public int getQty () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Qty);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** TimeFrequencyType AD_Reference_ID=1000390 */
	public static final int TIMEFREQUENCYTYPE_AD_Reference_ID=1000390;
	/** DIA = DIA */
	public static final String TIMEFREQUENCYTYPE_DIA = "DIA";
	/** MES = MES */
	public static final String TIMEFREQUENCYTYPE_MES = "MES";
	/** TRIMESTRE = TRIMESTRE */
	public static final String TIMEFREQUENCYTYPE_TRIMESTRE = "TRIMESTRE";
	/** SEMANA = SEMANA */
	public static final String TIMEFREQUENCYTYPE_SEMANA = "SEMANA";
	/** SEMESTRE = SEMESTRE */
	public static final String TIMEFREQUENCYTYPE_SEMESTRE = "SEMESTRE";
	/** AÃ‘O = ANIO */
	public static final String TIMEFREQUENCYTYPE_AÑO = "ANIO";
	/** Set TimeFrequencyType.
		@param TimeFrequencyType TimeFrequencyType	  */
	public void setTimeFrequencyType (String TimeFrequencyType)
	{

		set_Value (COLUMNNAME_TimeFrequencyType, TimeFrequencyType);
	}

	/** Get TimeFrequencyType.
		@return TimeFrequencyType	  */
	public String getTimeFrequencyType () 
	{
		return (String)get_Value(COLUMNNAME_TimeFrequencyType);
	}

	public I_UY_TR_Failure getUY_TR_Failure() throws RuntimeException
    {
		return (I_UY_TR_Failure)MTable.get(getCtx(), I_UY_TR_Failure.Table_Name)
			.getPO(getUY_TR_Failure_ID(), get_TrxName());	}

	/** Set UY_TR_Failure.
		@param UY_TR_Failure_ID UY_TR_Failure	  */
	public void setUY_TR_Failure_ID (int UY_TR_Failure_ID)
	{
		if (UY_TR_Failure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Failure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Failure_ID, Integer.valueOf(UY_TR_Failure_ID));
	}

	/** Get UY_TR_Failure.
		@return UY_TR_Failure	  */
	public int getUY_TR_Failure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Failure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Maintain.
		@param UY_TR_Maintain_ID UY_TR_Maintain	  */
	public void setUY_TR_Maintain_ID (int UY_TR_Maintain_ID)
	{
		if (UY_TR_Maintain_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Maintain_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Maintain_ID, Integer.valueOf(UY_TR_Maintain_ID));
	}

	/** Get UY_TR_Maintain.
		@return UY_TR_Maintain	  */
	public int getUY_TR_Maintain_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Maintain_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}