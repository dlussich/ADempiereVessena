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

/** Generated Model for UY_BPIrpf
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_BPIrpf extends PO implements I_UY_BPIrpf, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120607L;

    /** Standard Constructor */
    public X_UY_BPIrpf (Properties ctx, int UY_BPIrpf_ID, String trxName)
    {
      super (ctx, UY_BPIrpf_ID, trxName);
      /** if (UY_BPIrpf_ID == 0)
        {
			setad_fondosolidaridad (false);
			setC_BPartner_ID (0);
			setmin_noimponible (false);
			setreduccionnf (false);
			setUY_BPIrpf_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BPIrpf (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BPIrpf[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ad_fondosolidaridad.
		@param ad_fondosolidaridad ad_fondosolidaridad	  */
	public void setad_fondosolidaridad (boolean ad_fondosolidaridad)
	{
		set_Value (COLUMNNAME_ad_fondosolidaridad, Boolean.valueOf(ad_fondosolidaridad));
	}

	/** Get ad_fondosolidaridad.
		@return ad_fondosolidaridad	  */
	public boolean isad_fondosolidaridad () 
	{
		Object oo = get_Value(COLUMNNAME_ad_fondosolidaridad);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set aporte_mensual.
		@param aporte_mensual aporte_mensual	  */
	public void setaporte_mensual (BigDecimal aporte_mensual)
	{
		set_Value (COLUMNNAME_aporte_mensual, aporte_mensual);
	}

	/** Get aporte_mensual.
		@return aporte_mensual	  */
	public BigDecimal getaporte_mensual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_aporte_mensual);
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

	/** Set con_discapacidad.
		@param con_discapacidad con_discapacidad	  */
	public void setcon_discapacidad (int con_discapacidad)
	{
		set_Value (COLUMNNAME_con_discapacidad, Integer.valueOf(con_discapacidad));
	}

	/** Get con_discapacidad.
		@return con_discapacidad	  */
	public int getcon_discapacidad () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_con_discapacidad);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** fondosolidaridad AD_Reference_ID=1000176 */
	public static final int FONDOSOLIDARIDAD_AD_Reference_ID=1000176;
	/** 0 = 0 */
	public static final String FONDOSOLIDARIDAD_0 = "0";
	/** 1/2 BPC = 2 */
	public static final String FONDOSOLIDARIDAD_12BPC = "2";
	/** 1 BPC = 1 */
	public static final String FONDOSOLIDARIDAD_1BPC = "1";
	/** 5/3 BPC = 3 */
	public static final String FONDOSOLIDARIDAD_53BPC = "3";
	/** Set fondosolidaridad.
		@param fondosolidaridad fondosolidaridad	  */
	public void setfondosolidaridad (String fondosolidaridad)
	{

		set_Value (COLUMNNAME_fondosolidaridad, fondosolidaridad);
	}

	/** Get fondosolidaridad.
		@return fondosolidaridad	  */
	public String getfondosolidaridad () 
	{
		return (String)get_Value(COLUMNNAME_fondosolidaridad);
	}

	/** Set min_noimponible.
		@param min_noimponible min_noimponible	  */
	public void setmin_noimponible (boolean min_noimponible)
	{
		set_Value (COLUMNNAME_min_noimponible, Boolean.valueOf(min_noimponible));
	}

	/** Get min_noimponible.
		@return min_noimponible	  */
	public boolean ismin_noimponible () 
	{
		Object oo = get_Value(COLUMNNAME_min_noimponible);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set otros.
		@param otros otros	  */
	public void setotros (BigDecimal otros)
	{
		set_Value (COLUMNNAME_otros, otros);
	}

	/** Get otros.
		@return otros	  */
	public BigDecimal getotros () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_otros);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** porc_deduccion AD_Reference_ID=1000175 */
	public static final int PORC_DEDUCCION_AD_Reference_ID=1000175;
	/** No hay deduccion = 0 */
	public static final String PORC_DEDUCCION_NoHayDeduccion = "0";
	/** Deduccion de 50% = 50 */
	public static final String PORC_DEDUCCION_DeduccionDe50 = "50";
	/** Deduccion de 100% = 100 */
	public static final String PORC_DEDUCCION_DeduccionDe100 = "100";
	/** Set porc_deduccion.
		@param porc_deduccion porc_deduccion	  */
	public void setporc_deduccion (String porc_deduccion)
	{

		set_Value (COLUMNNAME_porc_deduccion, porc_deduccion);
	}

	/** Get porc_deduccion.
		@return porc_deduccion	  */
	public String getporc_deduccion () 
	{
		return (String)get_Value(COLUMNNAME_porc_deduccion);
	}

	/** Set reduccionnf.
		@param reduccionnf reduccionnf	  */
	public void setreduccionnf (boolean reduccionnf)
	{
		set_Value (COLUMNNAME_reduccionnf, Boolean.valueOf(reduccionnf));
	}

	/** Get reduccionnf.
		@return reduccionnf	  */
	public boolean isreduccionnf () 
	{
		Object oo = get_Value(COLUMNNAME_reduccionnf);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set sin_discapacidad.
		@param sin_discapacidad sin_discapacidad	  */
	public void setsin_discapacidad (int sin_discapacidad)
	{
		set_Value (COLUMNNAME_sin_discapacidad, Integer.valueOf(sin_discapacidad));
	}

	/** Get sin_discapacidad.
		@return sin_discapacidad	  */
	public int getsin_discapacidad () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_sin_discapacidad);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BPIrpf.
		@param UY_BPIrpf_ID UY_BPIrpf	  */
	public void setUY_BPIrpf_ID (int UY_BPIrpf_ID)
	{
		if (UY_BPIrpf_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BPIrpf_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BPIrpf_ID, Integer.valueOf(UY_BPIrpf_ID));
	}

	/** Get UY_BPIrpf.
		@return UY_BPIrpf	  */
	public int getUY_BPIrpf_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BPIrpf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}