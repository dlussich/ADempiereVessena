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

/** Generated Model for UY_TR_LoadFuelLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_LoadFuelLine extends PO implements I_UY_TR_LoadFuelLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141121L;

    /** Standard Constructor */
    public X_UY_TR_LoadFuelLine (Properties ctx, int UY_TR_LoadFuelLine_ID, String trxName)
    {
      super (ctx, UY_TR_LoadFuelLine_ID, trxName);
      /** if (UY_TR_LoadFuelLine_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setTotalAmt (Env.ZERO);
			setUY_TR_LoadFuel_ID (0);
			setUY_TR_LoadFuelLine_ID (0);
			setUY_TR_Truck_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_LoadFuelLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_LoadFuelLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set IsFullTank.
		@param IsFullTank IsFullTank	  */
	public void setIsFullTank (boolean IsFullTank)
	{
		set_Value (COLUMNNAME_IsFullTank, Boolean.valueOf(IsFullTank));
	}

	/** Get IsFullTank.
		@return IsFullTank	  */
	public boolean isFullTank () 
	{
		Object oo = get_Value(COLUMNNAME_IsFullTank);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public I_UY_TR_LoadFuel getUY_TR_LoadFuel() throws RuntimeException
    {
		return (I_UY_TR_LoadFuel)MTable.get(getCtx(), I_UY_TR_LoadFuel.Table_Name)
			.getPO(getUY_TR_LoadFuel_ID(), get_TrxName());	}

	/** Set UY_TR_LoadFuel.
		@param UY_TR_LoadFuel_ID UY_TR_LoadFuel	  */
	public void setUY_TR_LoadFuel_ID (int UY_TR_LoadFuel_ID)
	{
		if (UY_TR_LoadFuel_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_LoadFuel_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_LoadFuel_ID, Integer.valueOf(UY_TR_LoadFuel_ID));
	}

	/** Get UY_TR_LoadFuel.
		@return UY_TR_LoadFuel	  */
	public int getUY_TR_LoadFuel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadFuel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_LoadFuelLine.
		@param UY_TR_LoadFuelLine_ID UY_TR_LoadFuelLine	  */
	public void setUY_TR_LoadFuelLine_ID (int UY_TR_LoadFuelLine_ID)
	{
		if (UY_TR_LoadFuelLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadFuelLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadFuelLine_ID, Integer.valueOf(UY_TR_LoadFuelLine_ID));
	}

	/** Get UY_TR_LoadFuelLine.
		@return UY_TR_LoadFuelLine	  */
	public int getUY_TR_LoadFuelLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadFuelLine_ID);
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