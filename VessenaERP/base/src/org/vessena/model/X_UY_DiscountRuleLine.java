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

/** Generated Model for UY_DiscountRuleLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DiscountRuleLine extends PO implements I_UY_DiscountRuleLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150714L;

    /** Standard Constructor */
    public X_UY_DiscountRuleLine (Properties ctx, int UY_DiscountRuleLine_ID, String trxName)
    {
      super (ctx, UY_DiscountRuleLine_ID, trxName);
      /** if (UY_DiscountRuleLine_ID == 0)
        {
			setMinQty (Env.ZERO);
			setRewardType (null);
			setUY_DiscountRuleLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DiscountRuleLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DiscountRuleLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DateExecuted.
		@param DateExecuted DateExecuted	  */
	public void setDateExecuted (Timestamp DateExecuted)
	{
		set_Value (COLUMNNAME_DateExecuted, DateExecuted);
	}

	/** Get DateExecuted.
		@return DateExecuted	  */
	public Timestamp getDateExecuted () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateExecuted);
	}

	/** Set Start Plan.
		@param DateStartPlan 
		Planned Start Date
	  */
	public void setDateStartPlan (Timestamp DateStartPlan)
	{
		set_Value (COLUMNNAME_DateStartPlan, DateStartPlan);
	}

	/** Get Start Plan.
		@return Planned Start Date
	  */
	public Timestamp getDateStartPlan () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStartPlan);
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

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Minimum Quantity.
		@param MinQty 
		Minimum quantity for the business partner
	  */
	public void setMinQty (BigDecimal MinQty)
	{
		set_Value (COLUMNNAME_MinQty, MinQty);
	}

	/** Get Minimum Quantity.
		@return Minimum quantity for the business partner
	  */
	public BigDecimal getMinQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Periodicity AD_Reference_ID=1000510 */
	public static final int PERIODICITY_AD_Reference_ID=1000510;
	/** Trimestral = TM */
	public static final String PERIODICITY_Trimestral = "TM";
	/** Semestral = SM */
	public static final String PERIODICITY_Semestral = "SM";
	/** Cuatrimestral = CM */
	public static final String PERIODICITY_Cuatrimestral = "CM";
	/** Mensual = M */
	public static final String PERIODICITY_Mensual = "M";
	/** Bimensual = BM */
	public static final String PERIODICITY_Bimensual = "BM";
	/** Anual = A */
	public static final String PERIODICITY_Anual = "A";
	/** Set Periodicity.
		@param Periodicity Periodicity	  */
	public void setPeriodicity (String Periodicity)
	{

		set_Value (COLUMNNAME_Periodicity, Periodicity);
	}

	/** Get Periodicity.
		@return Periodicity	  */
	public String getPeriodicity () 
	{
		return (String)get_Value(COLUMNNAME_Periodicity);
	}

	/** Set QtySource1.
		@param QtySource1 QtySource1	  */
	public void setQtySource1 (BigDecimal QtySource1)
	{
		set_Value (COLUMNNAME_QtySource1, QtySource1);
	}

	/** Get QtySource1.
		@return QtySource1	  */
	public BigDecimal getQtySource1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySource1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtySource2.
		@param QtySource2 QtySource2	  */
	public void setQtySource2 (BigDecimal QtySource2)
	{
		set_Value (COLUMNNAME_QtySource2, QtySource2);
	}

	/** Get QtySource2.
		@return QtySource2	  */
	public BigDecimal getQtySource2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySource2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** RewardType AD_Reference_ID=53298 */
	public static final int REWARDTYPE_AD_Reference_ID=53298;
	/** Percentage = P */
	public static final String REWARDTYPE_Percentage = "P";
	/** Flat Discount = F */
	public static final String REWARDTYPE_FlatDiscount = "F";
	/** Absolute Amount = A */
	public static final String REWARDTYPE_AbsoluteAmount = "A";
	/** Bonificacion Simple = S */
	public static final String REWARDTYPE_BonificacionSimple = "S";
	/** Bonificacion Cruzada = C */
	public static final String REWARDTYPE_BonificacionCruzada = "C";
	/** DescuentoOperativo = O */
	public static final String REWARDTYPE_DescuentoOperativo = "O";
	/** DescuentoFinancieroEnFactura = I */
	public static final String REWARDTYPE_DescuentoFinancieroEnFactura = "I";
	/** Retorno = R */
	public static final String REWARDTYPE_Retorno = "R";
	/** DescuentoFinancieroFueraDeFactura = J */
	public static final String REWARDTYPE_DescuentoFinancieroFueraDeFactura = "J";
	/** DescuentoAlPago = K */
	public static final String REWARDTYPE_DescuentoAlPago = "K";
	/** Set Reward Type.
		@param RewardType 
		Type of reward which consists of percentage discount, flat discount or absolute amount
	  */
	public void setRewardType (String RewardType)
	{

		set_Value (COLUMNNAME_RewardType, RewardType);
	}

	/** Get Reward Type.
		@return Type of reward which consists of percentage discount, flat discount or absolute amount
	  */
	public String getRewardType () 
	{
		return (String)get_Value(COLUMNNAME_RewardType);
	}

	public I_UY_DiscountRule getUY_DiscountRule() throws RuntimeException
    {
		return (I_UY_DiscountRule)MTable.get(getCtx(), I_UY_DiscountRule.Table_Name)
			.getPO(getUY_DiscountRule_ID(), get_TrxName());	}

	/** Set UY_DiscountRule.
		@param UY_DiscountRule_ID UY_DiscountRule	  */
	public void setUY_DiscountRule_ID (int UY_DiscountRule_ID)
	{
		if (UY_DiscountRule_ID < 1) 
			set_Value (COLUMNNAME_UY_DiscountRule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DiscountRule_ID, Integer.valueOf(UY_DiscountRule_ID));
	}

	/** Get UY_DiscountRule.
		@return UY_DiscountRule	  */
	public int getUY_DiscountRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DiscountRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DiscountRuleLine.
		@param UY_DiscountRuleLine_ID UY_DiscountRuleLine	  */
	public void setUY_DiscountRuleLine_ID (int UY_DiscountRuleLine_ID)
	{
		if (UY_DiscountRuleLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DiscountRuleLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DiscountRuleLine_ID, Integer.valueOf(UY_DiscountRuleLine_ID));
	}

	/** Get UY_DiscountRuleLine.
		@return UY_DiscountRuleLine	  */
	public int getUY_DiscountRuleLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DiscountRuleLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}