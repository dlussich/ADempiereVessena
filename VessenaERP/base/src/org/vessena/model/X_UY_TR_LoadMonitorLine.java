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

/** Generated Model for UY_TR_LoadMonitorLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_LoadMonitorLine extends PO implements I_UY_TR_LoadMonitorLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141221L;

    /** Standard Constructor */
    public X_UY_TR_LoadMonitorLine (Properties ctx, int UY_TR_LoadMonitorLine_ID, String trxName)
    {
      super (ctx, UY_TR_LoadMonitorLine_ID, trxName);
      /** if (UY_TR_LoadMonitorLine_ID == 0)
        {
			setIsManual (true);
// Y
			setIsSelected (true);
// Y
			setProrrateo (true);
// Y
			setUY_TR_LoadMonitor_ID (0);
			setUY_TR_LoadMonitorLine_ID (0);
			setUY_TR_Trip_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_LoadMonitorLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_LoadMonitorLine[")
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

	/** Set Amount2.
		@param Amount2 Amount2	  */
	public void setAmount2 (BigDecimal Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Amount2);
	}

	/** Get Amount2.
		@return Amount2	  */
	public BigDecimal getAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ProductAmt.
		@param ProductAmt ProductAmt	  */
	public void setProductAmt (BigDecimal ProductAmt)
	{
		set_Value (COLUMNNAME_ProductAmt, ProductAmt);
	}

	/** Get ProductAmt.
		@return ProductAmt	  */
	public BigDecimal getProductAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ProductAmt2.
		@param ProductAmt2 ProductAmt2	  */
	public void setProductAmt2 (BigDecimal ProductAmt2)
	{
		set_Value (COLUMNNAME_ProductAmt2, ProductAmt2);
	}

	/** Get ProductAmt2.
		@return ProductAmt2	  */
	public BigDecimal getProductAmt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductAmt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prorrateo.
		@param Prorrateo Prorrateo	  */
	public void setProrrateo (boolean Prorrateo)
	{
		set_Value (COLUMNNAME_Prorrateo, Boolean.valueOf(Prorrateo));
	}

	/** Get Prorrateo.
		@return Prorrateo	  */
	public boolean isProrrateo () 
	{
		Object oo = get_Value(COLUMNNAME_Prorrateo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set QtyPackage.
		@param QtyPackage QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage)
	{
		set_Value (COLUMNNAME_QtyPackage, QtyPackage);
	}

	/** Get QtyPackage.
		@return QtyPackage	  */
	public BigDecimal getQtyPackage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPackage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyPackage2.
		@param QtyPackage2 QtyPackage2	  */
	public void setQtyPackage2 (BigDecimal QtyPackage2)
	{
		set_Value (COLUMNNAME_QtyPackage2, QtyPackage2);
	}

	/** Get QtyPackage2.
		@return QtyPackage2	  */
	public BigDecimal getQtyPackage2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPackage2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_LoadMonitor getUY_TR_LoadMonitor() throws RuntimeException
    {
		return (I_UY_TR_LoadMonitor)MTable.get(getCtx(), I_UY_TR_LoadMonitor.Table_Name)
			.getPO(getUY_TR_LoadMonitor_ID(), get_TrxName());	}

	/** Set UY_TR_LoadMonitor.
		@param UY_TR_LoadMonitor_ID UY_TR_LoadMonitor	  */
	public void setUY_TR_LoadMonitor_ID (int UY_TR_LoadMonitor_ID)
	{
		if (UY_TR_LoadMonitor_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_LoadMonitor_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_LoadMonitor_ID, Integer.valueOf(UY_TR_LoadMonitor_ID));
	}

	/** Get UY_TR_LoadMonitor.
		@return UY_TR_LoadMonitor	  */
	public int getUY_TR_LoadMonitor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMonitor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_LoadMonitorLine.
		@param UY_TR_LoadMonitorLine_ID UY_TR_LoadMonitorLine	  */
	public void setUY_TR_LoadMonitorLine_ID (int UY_TR_LoadMonitorLine_ID)
	{
		if (UY_TR_LoadMonitorLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMonitorLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMonitorLine_ID, Integer.valueOf(UY_TR_LoadMonitorLine_ID));
	}

	/** Get UY_TR_LoadMonitorLine.
		@return UY_TR_LoadMonitorLine	  */
	public int getUY_TR_LoadMonitorLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMonitorLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException
    {
		return (I_UY_TR_PackageType)MTable.get(getCtx(), I_UY_TR_PackageType.Table_Name)
			.getPO(getUY_TR_PackageType_ID(), get_TrxName());	}

	/** Set UY_TR_PackageType.
		@param UY_TR_PackageType_ID UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID)
	{
		if (UY_TR_PackageType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_PackageType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_PackageType_ID, Integer.valueOf(UY_TR_PackageType_ID));
	}

	/** Get UY_TR_PackageType.
		@return UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_PackageType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Remito.
		@param UY_TR_Remito_ID UY_TR_Remito	  */
	public void setUY_TR_Remito_ID (int UY_TR_Remito_ID)
	{
		if (UY_TR_Remito_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Remito_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Remito_ID, Integer.valueOf(UY_TR_Remito_ID));
	}

	/** Get UY_TR_Remito.
		@return UY_TR_Remito	  */
	public int getUY_TR_Remito_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Remito_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TransOrderLine getUY_TR_TransOrderLine() throws RuntimeException
    {
		return (I_UY_TR_TransOrderLine)MTable.get(getCtx(), I_UY_TR_TransOrderLine.Table_Name)
			.getPO(getUY_TR_TransOrderLine_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrderLine.
		@param UY_TR_TransOrderLine_ID UY_TR_TransOrderLine	  */
	public void setUY_TR_TransOrderLine_ID (int UY_TR_TransOrderLine_ID)
	{
		if (UY_TR_TransOrderLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrderLine_ID, Integer.valueOf(UY_TR_TransOrderLine_ID));
	}

	/** Get UY_TR_TransOrderLine.
		@return UY_TR_TransOrderLine	  */
	public int getUY_TR_TransOrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException
    {
		return (I_UY_TR_Trip)MTable.get(getCtx(), I_UY_TR_Trip.Table_Name)
			.getPO(getUY_TR_Trip_ID(), get_TrxName());	}

	/** Set UY_TR_Trip.
		@param UY_TR_Trip_ID UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID)
	{
		if (UY_TR_Trip_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, Integer.valueOf(UY_TR_Trip_ID));
	}

	/** Get UY_TR_Trip.
		@return UY_TR_Trip	  */
	public int getUY_TR_Trip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Trip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Volume2.
		@param Volume2 Volume2	  */
	public void setVolume2 (BigDecimal Volume2)
	{
		set_Value (COLUMNNAME_Volume2, Volume2);
	}

	/** Get Volume2.
		@return Volume2	  */
	public BigDecimal getVolume2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight2.
		@param Weight2 Weight2	  */
	public void setWeight2 (BigDecimal Weight2)
	{
		set_Value (COLUMNNAME_Weight2, Weight2);
	}

	/** Get Weight2.
		@return Weight2	  */
	public BigDecimal getWeight2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight2Aux.
		@param Weight2Aux Weight2Aux	  */
	public void setWeight2Aux (BigDecimal Weight2Aux)
	{
		set_Value (COLUMNNAME_Weight2Aux, Weight2Aux);
	}

	/** Get Weight2Aux.
		@return Weight2Aux	  */
	public BigDecimal getWeight2Aux () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight2Aux);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set WeightAux.
		@param WeightAux WeightAux	  */
	public void setWeightAux (BigDecimal WeightAux)
	{
		set_Value (COLUMNNAME_WeightAux, WeightAux);
	}

	/** Get WeightAux.
		@return WeightAux	  */
	public BigDecimal getWeightAux () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WeightAux);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}