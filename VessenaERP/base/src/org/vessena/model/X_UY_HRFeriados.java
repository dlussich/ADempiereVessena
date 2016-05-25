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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_HRFeriados
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRFeriados extends PO implements I_UY_HRFeriados, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120323L;

    /** Standard Constructor */
    public X_UY_HRFeriados (Properties ctx, int UY_HRFeriados_ID, String trxName)
    {
      super (ctx, UY_HRFeriados_ID, trxName);
      /** if (UY_HRFeriados_ID == 0)
        {
			setdias (null);
			setmes (null);
			setUY_HRFeriados_ID (0);
			setUY_HRParametros_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRFeriados (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRFeriados[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** dias AD_Reference_ID=1000197 */
	public static final int DIAS_AD_Reference_ID=1000197;
	/** 1 = 1 */
	public static final String DIAS_1 = "1";
	/** 2 = 2 */
	public static final String DIAS_2 = "2";
	/** 3 = 3 */
	public static final String DIAS_3 = "3";
	/** 4 = 4 */
	public static final String DIAS_4 = "4";
	/** 5 = 5 */
	public static final String DIAS_5 = "5";
	/** 6 = 6 */
	public static final String DIAS_6 = "6";
	/** 7 = 7 */
	public static final String DIAS_7 = "7";
	/** 8 = 8 */
	public static final String DIAS_8 = "8";
	/** 9 = 9 */
	public static final String DIAS_9 = "9";
	/** 10 = 10 */
	public static final String DIAS_10 = "10";
	/** 11 = 11 */
	public static final String DIAS_11 = "11";
	/** 12 = 12 */
	public static final String DIAS_12 = "12";
	/** 13 = 13 */
	public static final String DIAS_13 = "13";
	/** 14 = 14 */
	public static final String DIAS_14 = "14";
	/** 15 = 15 */
	public static final String DIAS_15 = "15";
	/** 16 = 16 */
	public static final String DIAS_16 = "16";
	/** 17 = 17 */
	public static final String DIAS_17 = "17";
	/** 18 = 18 */
	public static final String DIAS_18 = "18";
	/** 19 = 19 */
	public static final String DIAS_19 = "19";
	/** 20 = 20 */
	public static final String DIAS_20 = "20";
	/** 21 = 21 */
	public static final String DIAS_21 = "21";
	/** 22 = 22 */
	public static final String DIAS_22 = "22";
	/** 23 = 23 */
	public static final String DIAS_23 = "23";
	/** 24 = 24 */
	public static final String DIAS_24 = "24";
	/** 25 = 25 */
	public static final String DIAS_25 = "25";
	/** 26 = 26 */
	public static final String DIAS_26 = "26";
	/** 27 = 27 */
	public static final String DIAS_27 = "27";
	/** 28 = 28 */
	public static final String DIAS_28 = "28";
	/** 29 = 29 */
	public static final String DIAS_29 = "29";
	/** 30 = 30 */
	public static final String DIAS_30 = "30";
	/** 31 = 31 */
	public static final String DIAS_31 = "31";
	/** Set dias.
		@param dias dias	  */
	public void setdias (String dias)
	{

		set_Value (COLUMNNAME_dias, dias);
	}

	/** Get dias.
		@return dias	  */
	public String getdias () 
	{
		return (String)get_Value(COLUMNNAME_dias);
	}

	/** mes AD_Reference_ID=1000192 */
	public static final int MES_AD_Reference_ID=1000192;
	/** Enero = 1 */
	public static final String MES_Enero = "1";
	/** Febrero = 2 */
	public static final String MES_Febrero = "2";
	/** Marzo = 3 */
	public static final String MES_Marzo = "3";
	/** Abril = 4 */
	public static final String MES_Abril = "4";
	/** Mayo = 5 */
	public static final String MES_Mayo = "5";
	/** Junio = 6 */
	public static final String MES_Junio = "6";
	/** Julio = 7 */
	public static final String MES_Julio = "7";
	/** Agosto = 8 */
	public static final String MES_Agosto = "8";
	/** Septiembre = 9 */
	public static final String MES_Septiembre = "9";
	/** Octubre = 10 */
	public static final String MES_Octubre = "10";
	/** Noviembre = 11 */
	public static final String MES_Noviembre = "11";
	/** Diciembre = 12 */
	public static final String MES_Diciembre = "12";
	/** Set mes.
		@param mes mes	  */
	public void setmes (String mes)
	{

		set_Value (COLUMNNAME_mes, mes);
	}

	/** Get mes.
		@return mes	  */
	public String getmes () 
	{
		return (String)get_Value(COLUMNNAME_mes);
	}

	/** Set UY_HRFeriados.
		@param UY_HRFeriados_ID UY_HRFeriados	  */
	public void setUY_HRFeriados_ID (int UY_HRFeriados_ID)
	{
		if (UY_HRFeriados_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRFeriados_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRFeriados_ID, Integer.valueOf(UY_HRFeriados_ID));
	}

	/** Get UY_HRFeriados.
		@return UY_HRFeriados	  */
	public int getUY_HRFeriados_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRFeriados_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_HRParametros getUY_HRParametros() throws RuntimeException
    {
		return (I_UY_HRParametros)MTable.get(getCtx(), I_UY_HRParametros.Table_Name)
			.getPO(getUY_HRParametros_ID(), get_TrxName());	}

	/** Set UY_HRParametros.
		@param UY_HRParametros_ID UY_HRParametros	  */
	public void setUY_HRParametros_ID (int UY_HRParametros_ID)
	{
		if (UY_HRParametros_ID < 1) 
			set_Value (COLUMNNAME_UY_HRParametros_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRParametros_ID, Integer.valueOf(UY_HRParametros_ID));
	}

	/** Get UY_HRParametros.
		@return UY_HRParametros	  */
	public int getUY_HRParametros_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRParametros_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}