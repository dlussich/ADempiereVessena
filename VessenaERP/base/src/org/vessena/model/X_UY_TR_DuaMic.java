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

/** Generated Model for UY_TR_DuaMic
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_DuaMic extends PO implements I_UY_TR_DuaMic, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150407L;

    /** Standard Constructor */
    public X_UY_TR_DuaMic (Properties ctx, int UY_TR_DuaMic_ID, String trxName)
    {
      super (ctx, UY_TR_DuaMic_ID, trxName);
      /** if (UY_TR_DuaMic_ID == 0)
        {
			setUY_TR_DuaMic_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_DuaMic (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_DuaMic[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AnioDua.
		@param AnioDua AnioDua	  */
	public void setAnioDua (String AnioDua)
	{
		set_Value (COLUMNNAME_AnioDua, AnioDua);
	}

	/** Get AnioDua.
		@return AnioDua	  */
	public String getAnioDua () 
	{
		return (String)get_Value(COLUMNNAME_AnioDua);
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

	/** Set description2.
		@param description2 description2	  */
	public void setdescription2 (String description2)
	{
		set_Value (COLUMNNAME_description2, description2);
	}

	/** Get description2.
		@return description2	  */
	public String getdescription2 () 
	{
		return (String)get_Value(COLUMNNAME_description2);
	}

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set ExecuteAction2.
		@param ExecuteAction2 ExecuteAction2	  */
	public void setExecuteAction2 (String ExecuteAction2)
	{
		set_Value (COLUMNNAME_ExecuteAction2, ExecuteAction2);
	}

	/** Get ExecuteAction2.
		@return ExecuteAction2	  */
	public String getExecuteAction2 () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction2);
	}

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NumeroDua.
		@param NumeroDua NumeroDua	  */
	public void setNumeroDua (String NumeroDua)
	{
		set_Value (COLUMNNAME_NumeroDua, NumeroDua);
	}

	/** Get NumeroDua.
		@return NumeroDua	  */
	public String getNumeroDua () 
	{
		return (String)get_Value(COLUMNNAME_NumeroDua);
	}

	/** Set pesoBruto.
		@param pesoBruto pesoBruto	  */
	public void setpesoBruto (BigDecimal pesoBruto)
	{
		set_Value (COLUMNNAME_pesoBruto, pesoBruto);
	}

	/** Get pesoBruto.
		@return pesoBruto	  */
	public BigDecimal getpesoBruto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoBruto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set pesoNeto.
		@param pesoNeto pesoNeto	  */
	public void setpesoNeto (BigDecimal pesoNeto)
	{
		set_Value (COLUMNNAME_pesoNeto, pesoNeto);
	}

	/** Get pesoNeto.
		@return pesoNeto	  */
	public BigDecimal getpesoNeto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoNeto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_TR_Dua getUY_TR_Dua() throws RuntimeException
    {
		return (I_UY_TR_Dua)MTable.get(getCtx(), I_UY_TR_Dua.Table_Name)
			.getPO(getUY_TR_Dua_ID(), get_TrxName());	}

	/** Set UY_TR_Dua_ID.
		@param UY_TR_Dua_ID UY_TR_Dua_ID	  */
	public void setUY_TR_Dua_ID (int UY_TR_Dua_ID)
	{
		if (UY_TR_Dua_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Dua_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Dua_ID, Integer.valueOf(UY_TR_Dua_ID));
	}

	/** Get UY_TR_Dua_ID.
		@return UY_TR_Dua_ID	  */
	public int getUY_TR_Dua_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Dua_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_DuaMic_ID.
		@param UY_TR_DuaMic_ID UY_TR_DuaMic_ID	  */
	public void setUY_TR_DuaMic_ID (int UY_TR_DuaMic_ID)
	{
		if (UY_TR_DuaMic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_DuaMic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_DuaMic_ID, Integer.valueOf(UY_TR_DuaMic_ID));
	}

	/** Get UY_TR_DuaMic_ID.
		@return UY_TR_DuaMic_ID	  */
	public int getUY_TR_DuaMic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_DuaMic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException
    {
		return (I_UY_TR_Mic)MTable.get(getCtx(), I_UY_TR_Mic.Table_Name)
			.getPO(getUY_TR_Mic_ID(), get_TrxName());	}

	/** Set UY_TR_Mic.
		@param UY_TR_Mic_ID UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID)
	{
		if (UY_TR_Mic_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, Integer.valueOf(UY_TR_Mic_ID));
	}

	/** Get UY_TR_Mic.
		@return UY_TR_Mic	  */
	public int getUY_TR_Mic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Mic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set valorMercaderia.
		@param valorMercaderia valorMercaderia	  */
	public void setvalorMercaderia (BigDecimal valorMercaderia)
	{
		set_Value (COLUMNNAME_valorMercaderia, valorMercaderia);
	}

	/** Get valorMercaderia.
		@return valorMercaderia	  */
	public BigDecimal getvalorMercaderia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_valorMercaderia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Value.
		@param ValueNumber 
		Numeric Value
	  */
	public void setValueNumber (BigDecimal ValueNumber)
	{
		set_Value (COLUMNNAME_ValueNumber, ValueNumber);
	}

	/** Get Value.
		@return Numeric Value
	  */
	public BigDecimal getValueNumber () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueNumber);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}