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

/** Generated Model for UY_HRIrpfLineImpuesto
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRIrpfLineImpuesto extends PO implements I_UY_HRIrpfLineImpuesto, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120309L;

    /** Standard Constructor */
    public X_UY_HRIrpfLineImpuesto (Properties ctx, int UY_HRIrpfLineImpuesto_ID, String trxName)
    {
      super (ctx, UY_HRIrpfLineImpuesto_ID, trxName);
      /** if (UY_HRIrpfLineImpuesto_ID == 0)
        {
			setanual (false);
			setbpcdesde (Env.ZERO);
			setbpchasta (Env.ZERO);
			setdesde (0);
			sethasta (0);
			setporcentaje (Env.ZERO);
			setUY_HRIrpf_ID (0);
			setUY_HRIrpfLineImpuesto_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRIrpfLineImpuesto (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRIrpfLineImpuesto[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set anual.
		@param anual anual	  */
	public void setanual (boolean anual)
	{
		set_Value (COLUMNNAME_anual, Boolean.valueOf(anual));
	}

	/** Get anual.
		@return anual	  */
	public boolean isanual () 
	{
		Object oo = get_Value(COLUMNNAME_anual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set bpcdesde.
		@param bpcdesde bpcdesde	  */
	public void setbpcdesde (BigDecimal bpcdesde)
	{
		set_Value (COLUMNNAME_bpcdesde, bpcdesde);
	}

	/** Get bpcdesde.
		@return bpcdesde	  */
	public BigDecimal getbpcdesde () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_bpcdesde);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set bpchasta.
		@param bpchasta bpchasta	  */
	public void setbpchasta (BigDecimal bpchasta)
	{
		set_Value (COLUMNNAME_bpchasta, bpchasta);
	}

	/** Get bpchasta.
		@return bpchasta	  */
	public BigDecimal getbpchasta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_bpchasta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set desde.
		@param desde desde	  */
	public void setdesde (int desde)
	{
		set_Value (COLUMNNAME_desde, Integer.valueOf(desde));
	}

	/** Get desde.
		@return desde	  */
	public int getdesde () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_desde);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set hasta.
		@param hasta hasta	  */
	public void sethasta (int hasta)
	{
		set_Value (COLUMNNAME_hasta, Integer.valueOf(hasta));
	}

	/** Get hasta.
		@return hasta	  */
	public int gethasta () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hasta);
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

	public I_UY_HRIrpf getUY_HRIrpf() throws RuntimeException
    {
		return (I_UY_HRIrpf)MTable.get(getCtx(), I_UY_HRIrpf.Table_Name)
			.getPO(getUY_HRIrpf_ID(), get_TrxName());	}

	/** Set UY_HRIrpf.
		@param UY_HRIrpf_ID UY_HRIrpf	  */
	public void setUY_HRIrpf_ID (int UY_HRIrpf_ID)
	{
		if (UY_HRIrpf_ID < 1) 
			set_Value (COLUMNNAME_UY_HRIrpf_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRIrpf_ID, Integer.valueOf(UY_HRIrpf_ID));
	}

	/** Get UY_HRIrpf.
		@return UY_HRIrpf	  */
	public int getUY_HRIrpf_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRIrpf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRIrpfLineImpuesto.
		@param UY_HRIrpfLineImpuesto_ID UY_HRIrpfLineImpuesto	  */
	public void setUY_HRIrpfLineImpuesto_ID (int UY_HRIrpfLineImpuesto_ID)
	{
		if (UY_HRIrpfLineImpuesto_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRIrpfLineImpuesto_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRIrpfLineImpuesto_ID, Integer.valueOf(UY_HRIrpfLineImpuesto_ID));
	}

	/** Get UY_HRIrpfLineImpuesto.
		@return UY_HRIrpfLineImpuesto	  */
	public int getUY_HRIrpfLineImpuesto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRIrpfLineImpuesto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}