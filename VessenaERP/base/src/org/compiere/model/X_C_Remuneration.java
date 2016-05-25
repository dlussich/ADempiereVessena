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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_Remuneration
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_Remuneration extends PO implements I_C_Remuneration, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120914L;

    /** Standard Constructor */
    public X_C_Remuneration (Properties ctx, int C_Remuneration_ID, String trxName)
    {
      super (ctx, C_Remuneration_ID, trxName);
      /** if (C_Remuneration_ID == 0)
        {
			setC_Remuneration_ID (0);
			setcompensa (false);
			setGrossRAmt (Env.ZERO);
			setGrossRCost (Env.ZERO);
			setimporte_cero (false);
			setName (null);
			setOvertimeAmt (Env.ZERO);
			setOvertimeCost (Env.ZERO);
			setStandardHours (Env.ZERO);
			setWeekHours (Env.ZERO);
// 40
        } */
    }

    /** Load Constructor */
    public X_C_Remuneration (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_C_Remuneration[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set breakminutes.
		@param breakminutes breakminutes	  */
	public void setbreakminutes (int breakminutes)
	{
		set_Value (COLUMNNAME_breakminutes, Integer.valueOf(breakminutes));
	}

	/** Get breakminutes.
		@return breakminutes	  */
	public int getbreakminutes () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_breakminutes);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Remuneration.
		@param C_Remuneration_ID 
		Wage or Salary
	  */
	public void setC_Remuneration_ID (int C_Remuneration_ID)
	{
		if (C_Remuneration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Remuneration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Remuneration_ID, Integer.valueOf(C_Remuneration_ID));
	}

	/** Get Remuneration.
		@return Wage or Salary
	  */
	public int getC_Remuneration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Remuneration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigo.
		@param codigo codigo	  */
	public void setcodigo (int codigo)
	{
		set_Value (COLUMNNAME_codigo, Integer.valueOf(codigo));
	}

	/** Get codigo.
		@return codigo	  */
	public int getcodigo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set compensa.
		@param compensa compensa	  */
	public void setcompensa (boolean compensa)
	{
		set_Value (COLUMNNAME_compensa, Boolean.valueOf(compensa));
	}

	/** Get compensa.
		@return compensa	  */
	public boolean iscompensa () 
	{
		Object oo = get_Value(COLUMNNAME_compensa);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Gross Amount.
		@param GrossRAmt 
		Gross Remuneration Amount
	  */
	public void setGrossRAmt (BigDecimal GrossRAmt)
	{
		set_Value (COLUMNNAME_GrossRAmt, GrossRAmt);
	}

	/** Get Gross Amount.
		@return Gross Remuneration Amount
	  */
	public BigDecimal getGrossRAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrossRAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Gross Cost.
		@param GrossRCost 
		Gross Remuneration Costs
	  */
	public void setGrossRCost (BigDecimal GrossRCost)
	{
		set_Value (COLUMNNAME_GrossRCost, GrossRCost);
	}

	/** Get Gross Cost.
		@return Gross Remuneration Costs
	  */
	public BigDecimal getGrossRCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrossRCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set importe_cero.
		@param importe_cero importe_cero	  */
	public void setimporte_cero (boolean importe_cero)
	{
		set_Value (COLUMNNAME_importe_cero, Boolean.valueOf(importe_cero));
	}

	/** Get importe_cero.
		@return importe_cero	  */
	public boolean isimporte_cero () 
	{
		Object oo = get_Value(COLUMNNAME_importe_cero);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Overtime Amount.
		@param OvertimeAmt 
		Hourly Overtime Rate
	  */
	public void setOvertimeAmt (BigDecimal OvertimeAmt)
	{
		set_Value (COLUMNNAME_OvertimeAmt, OvertimeAmt);
	}

	/** Get Overtime Amount.
		@return Hourly Overtime Rate
	  */
	public BigDecimal getOvertimeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OvertimeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Overtime Cost.
		@param OvertimeCost 
		Hourly Overtime Cost
	  */
	public void setOvertimeCost (BigDecimal OvertimeCost)
	{
		set_Value (COLUMNNAME_OvertimeCost, OvertimeCost);
	}

	/** Get Overtime Cost.
		@return Hourly Overtime Cost
	  */
	public BigDecimal getOvertimeCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OvertimeCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** RemunerationType AD_Reference_ID=346 */
	public static final int REMUNERATIONTYPE_AD_Reference_ID=346;
	/** Hourly = H */
	public static final String REMUNERATIONTYPE_Hourly = "H";
	/** Daily = D */
	public static final String REMUNERATIONTYPE_Daily = "D";
	/** Weekly = W */
	public static final String REMUNERATIONTYPE_Weekly = "W";
	/** Monthly = M */
	public static final String REMUNERATIONTYPE_Monthly = "M";
	/** Twice Monthly = T */
	public static final String REMUNERATIONTYPE_TwiceMonthly = "T";
	/** Bi-Weekly = B */
	public static final String REMUNERATIONTYPE_Bi_Weekly = "B";
	/** Set Remuneration Type.
		@param RemunerationType 
		Type of Remuneration
	  */
	public void setRemunerationType (String RemunerationType)
	{

		set_Value (COLUMNNAME_RemunerationType, RemunerationType);
	}

	/** Get Remuneration Type.
		@return Type of Remuneration
	  */
	public String getRemunerationType () 
	{
		return (String)get_Value(COLUMNNAME_RemunerationType);
	}

	/** Set Standard Hours.
		@param StandardHours 
		Standard Work Hours based on Remuneration Type
	  */
	public void setStandardHours (BigDecimal StandardHours)
	{
		set_Value (COLUMNNAME_StandardHours, StandardHours);
	}

	/** Get Standard Hours.
		@return Standard Work Hours based on Remuneration Type
	  */
	public BigDecimal getStandardHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_StandardHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** tipo_horario AD_Reference_ID=1000202 */
	public static final int TIPO_HORARIO_AD_Reference_ID=1000202;
	/** Corrido = CRR */
	public static final String TIPO_HORARIO_Corrido = "CRR";
	/** Cortado = CRT */
	public static final String TIPO_HORARIO_Cortado = "CRT";
	/** No Aplicable = NAP */
	public static final String TIPO_HORARIO_NoAplicable = "NAP";
	/** Set tipo_horario.
		@param tipo_horario tipo_horario	  */
	public void settipo_horario (String tipo_horario)
	{

		set_Value (COLUMNNAME_tipo_horario, tipo_horario);
	}

	/** Get tipo_horario.
		@return tipo_horario	  */
	public String gettipo_horario () 
	{
		return (String)get_Value(COLUMNNAME_tipo_horario);
	}

	/** Set UY_HRRemunerationGroup_ID.
		@param UY_HRRemunerationGroup_ID UY_HRRemunerationGroup_ID	  */
	public void setUY_HRRemunerationGroup_ID (int UY_HRRemunerationGroup_ID)
	{
		if (UY_HRRemunerationGroup_ID < 1) 
			set_Value (COLUMNNAME_UY_HRRemunerationGroup_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRRemunerationGroup_ID, Integer.valueOf(UY_HRRemunerationGroup_ID));
	}

	/** Get UY_HRRemunerationGroup_ID.
		@return UY_HRRemunerationGroup_ID	  */
	public int getUY_HRRemunerationGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRRemunerationGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WeekHours.
		@param WeekHours WeekHours	  */
	public void setWeekHours (BigDecimal WeekHours)
	{
		set_Value (COLUMNNAME_WeekHours, WeekHours);
	}

	/** Get WeekHours.
		@return WeekHours	  */
	public BigDecimal getWeekHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WeekHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}