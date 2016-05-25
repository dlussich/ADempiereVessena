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

/** Generated Model for UY_Fdu_CoefficientLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_CoefficientLine extends PO implements I_UY_Fdu_CoefficientLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130114L;

    /** Standard Constructor */
    public X_UY_Fdu_CoefficientLine (Properties ctx, int UY_Fdu_CoefficientLine_ID, String trxName)
    {
      super (ctx, UY_Fdu_CoefficientLine_ID, trxName);
      /** if (UY_Fdu_CoefficientLine_ID == 0)
        {
			setcuotas_10 (Env.ZERO);
			setcuotas_11 (Env.ZERO);
			setcuotas_12 (Env.ZERO);
			setcuotas_13 (Env.ZERO);
			setcuotas_14 (Env.ZERO);
			setcuotas_15 (Env.ZERO);
			setcuotas_16 (Env.ZERO);
			setcuotas_17 (Env.ZERO);
			setcuotas_18 (Env.ZERO);
			setcuotas_19 (Env.ZERO);
			setCuotas_2 (Env.ZERO);
			setcuotas_20 (Env.ZERO);
			setcuotas_21 (Env.ZERO);
			setcuotas_22 (Env.ZERO);
			setcuotas_23 (Env.ZERO);
			setcuotas_24 (Env.ZERO);
			setcuotas_3 (Env.ZERO);
			setcuotas_4 (Env.ZERO);
			setcuotas_5 (Env.ZERO);
			setcuotas_6 (Env.ZERO);
			setcuotas_7 (Env.ZERO);
			setcuotas_8 (Env.ZERO);
			setcuotas_9 (Env.ZERO);
			setUY_Fdu_Afinidad_ID (0);
			setUY_Fdu_CoefficientHdr_ID (0);
			setUY_Fdu_CoefficientLine_ID (0);
			setUY_Fdu_Currency_ID (0);
			setUY_Fdu_Productos_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_CoefficientLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_CoefficientLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set cuotas_10.
		@param cuotas_10 cuotas_10	  */
	public void setcuotas_10 (BigDecimal cuotas_10)
	{
		set_Value (COLUMNNAME_cuotas_10, cuotas_10);
	}

	/** Get cuotas_10.
		@return cuotas_10	  */
	public BigDecimal getcuotas_10 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_10);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_11.
		@param cuotas_11 cuotas_11	  */
	public void setcuotas_11 (BigDecimal cuotas_11)
	{
		set_Value (COLUMNNAME_cuotas_11, cuotas_11);
	}

	/** Get cuotas_11.
		@return cuotas_11	  */
	public BigDecimal getcuotas_11 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_11);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_12.
		@param cuotas_12 cuotas_12	  */
	public void setcuotas_12 (BigDecimal cuotas_12)
	{
		set_Value (COLUMNNAME_cuotas_12, cuotas_12);
	}

	/** Get cuotas_12.
		@return cuotas_12	  */
	public BigDecimal getcuotas_12 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_12);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_13.
		@param cuotas_13 cuotas_13	  */
	public void setcuotas_13 (BigDecimal cuotas_13)
	{
		set_Value (COLUMNNAME_cuotas_13, cuotas_13);
	}

	/** Get cuotas_13.
		@return cuotas_13	  */
	public BigDecimal getcuotas_13 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_13);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_14.
		@param cuotas_14 cuotas_14	  */
	public void setcuotas_14 (BigDecimal cuotas_14)
	{
		set_Value (COLUMNNAME_cuotas_14, cuotas_14);
	}

	/** Get cuotas_14.
		@return cuotas_14	  */
	public BigDecimal getcuotas_14 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_14);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_15.
		@param cuotas_15 cuotas_15	  */
	public void setcuotas_15 (BigDecimal cuotas_15)
	{
		set_Value (COLUMNNAME_cuotas_15, cuotas_15);
	}

	/** Get cuotas_15.
		@return cuotas_15	  */
	public BigDecimal getcuotas_15 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_15);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_16.
		@param cuotas_16 cuotas_16	  */
	public void setcuotas_16 (BigDecimal cuotas_16)
	{
		set_Value (COLUMNNAME_cuotas_16, cuotas_16);
	}

	/** Get cuotas_16.
		@return cuotas_16	  */
	public BigDecimal getcuotas_16 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_16);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_17.
		@param cuotas_17 cuotas_17	  */
	public void setcuotas_17 (BigDecimal cuotas_17)
	{
		set_Value (COLUMNNAME_cuotas_17, cuotas_17);
	}

	/** Get cuotas_17.
		@return cuotas_17	  */
	public BigDecimal getcuotas_17 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_17);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_18.
		@param cuotas_18 cuotas_18	  */
	public void setcuotas_18 (BigDecimal cuotas_18)
	{
		set_Value (COLUMNNAME_cuotas_18, cuotas_18);
	}

	/** Get cuotas_18.
		@return cuotas_18	  */
	public BigDecimal getcuotas_18 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_18);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_19.
		@param cuotas_19 cuotas_19	  */
	public void setcuotas_19 (BigDecimal cuotas_19)
	{
		set_Value (COLUMNNAME_cuotas_19, cuotas_19);
	}

	/** Get cuotas_19.
		@return cuotas_19	  */
	public BigDecimal getcuotas_19 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_19);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Cuotas_2.
		@param Cuotas_2 Cuotas_2	  */
	public void setCuotas_2 (BigDecimal Cuotas_2)
	{
		set_Value (COLUMNNAME_Cuotas_2, Cuotas_2);
	}

	/** Get Cuotas_2.
		@return Cuotas_2	  */
	public BigDecimal getCuotas_2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cuotas_2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_20.
		@param cuotas_20 cuotas_20	  */
	public void setcuotas_20 (BigDecimal cuotas_20)
	{
		set_Value (COLUMNNAME_cuotas_20, cuotas_20);
	}

	/** Get cuotas_20.
		@return cuotas_20	  */
	public BigDecimal getcuotas_20 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_20);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_21.
		@param cuotas_21 cuotas_21	  */
	public void setcuotas_21 (BigDecimal cuotas_21)
	{
		set_Value (COLUMNNAME_cuotas_21, cuotas_21);
	}

	/** Get cuotas_21.
		@return cuotas_21	  */
	public BigDecimal getcuotas_21 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_21);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_22.
		@param cuotas_22 cuotas_22	  */
	public void setcuotas_22 (BigDecimal cuotas_22)
	{
		set_Value (COLUMNNAME_cuotas_22, cuotas_22);
	}

	/** Get cuotas_22.
		@return cuotas_22	  */
	public BigDecimal getcuotas_22 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_22);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_23.
		@param cuotas_23 cuotas_23	  */
	public void setcuotas_23 (BigDecimal cuotas_23)
	{
		set_Value (COLUMNNAME_cuotas_23, cuotas_23);
	}

	/** Get cuotas_23.
		@return cuotas_23	  */
	public BigDecimal getcuotas_23 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_23);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_24.
		@param cuotas_24 cuotas_24	  */
	public void setcuotas_24 (BigDecimal cuotas_24)
	{
		set_Value (COLUMNNAME_cuotas_24, cuotas_24);
	}

	/** Get cuotas_24.
		@return cuotas_24	  */
	public BigDecimal getcuotas_24 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_24);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_3.
		@param cuotas_3 cuotas_3	  */
	public void setcuotas_3 (BigDecimal cuotas_3)
	{
		set_Value (COLUMNNAME_cuotas_3, cuotas_3);
	}

	/** Get cuotas_3.
		@return cuotas_3	  */
	public BigDecimal getcuotas_3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_4.
		@param cuotas_4 cuotas_4	  */
	public void setcuotas_4 (BigDecimal cuotas_4)
	{
		set_Value (COLUMNNAME_cuotas_4, cuotas_4);
	}

	/** Get cuotas_4.
		@return cuotas_4	  */
	public BigDecimal getcuotas_4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_5.
		@param cuotas_5 cuotas_5	  */
	public void setcuotas_5 (BigDecimal cuotas_5)
	{
		set_Value (COLUMNNAME_cuotas_5, cuotas_5);
	}

	/** Get cuotas_5.
		@return cuotas_5	  */
	public BigDecimal getcuotas_5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_6.
		@param cuotas_6 cuotas_6	  */
	public void setcuotas_6 (BigDecimal cuotas_6)
	{
		set_Value (COLUMNNAME_cuotas_6, cuotas_6);
	}

	/** Get cuotas_6.
		@return cuotas_6	  */
	public BigDecimal getcuotas_6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_7.
		@param cuotas_7 cuotas_7	  */
	public void setcuotas_7 (BigDecimal cuotas_7)
	{
		set_Value (COLUMNNAME_cuotas_7, cuotas_7);
	}

	/** Get cuotas_7.
		@return cuotas_7	  */
	public BigDecimal getcuotas_7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_8.
		@param cuotas_8 cuotas_8	  */
	public void setcuotas_8 (BigDecimal cuotas_8)
	{
		set_Value (COLUMNNAME_cuotas_8, cuotas_8);
	}

	/** Get cuotas_8.
		@return cuotas_8	  */
	public BigDecimal getcuotas_8 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_8);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set cuotas_9.
		@param cuotas_9 cuotas_9	  */
	public void setcuotas_9 (BigDecimal cuotas_9)
	{
		set_Value (COLUMNNAME_cuotas_9, cuotas_9);
	}

	/** Get cuotas_9.
		@return cuotas_9	  */
	public BigDecimal getcuotas_9 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cuotas_9);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_Fdu_Afinidad getUY_Fdu_Afinidad() throws RuntimeException
    {
		return (I_UY_Fdu_Afinidad)MTable.get(getCtx(), I_UY_Fdu_Afinidad.Table_Name)
			.getPO(getUY_Fdu_Afinidad_ID(), get_TrxName());	}

	/** Set UY_Fdu_Afinidad.
		@param UY_Fdu_Afinidad_ID UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID)
	{
		if (UY_Fdu_Afinidad_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Afinidad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Afinidad_ID, Integer.valueOf(UY_Fdu_Afinidad_ID));
	}

	/** Get UY_Fdu_Afinidad.
		@return UY_Fdu_Afinidad	  */
	public int getUY_Fdu_Afinidad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Afinidad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_CoefficientHdr getUY_Fdu_CoefficientHdr() throws RuntimeException
    {
		return (I_UY_Fdu_CoefficientHdr)MTable.get(getCtx(), I_UY_Fdu_CoefficientHdr.Table_Name)
			.getPO(getUY_Fdu_CoefficientHdr_ID(), get_TrxName());	}

	/** Set UY_Fdu_CoefficientHdr.
		@param UY_Fdu_CoefficientHdr_ID UY_Fdu_CoefficientHdr	  */
	public void setUY_Fdu_CoefficientHdr_ID (int UY_Fdu_CoefficientHdr_ID)
	{
		if (UY_Fdu_CoefficientHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_CoefficientHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_CoefficientHdr_ID, Integer.valueOf(UY_Fdu_CoefficientHdr_ID));
	}

	/** Get UY_Fdu_CoefficientHdr.
		@return UY_Fdu_CoefficientHdr	  */
	public int getUY_Fdu_CoefficientHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_CoefficientHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_CoefficientLine.
		@param UY_Fdu_CoefficientLine_ID UY_Fdu_CoefficientLine	  */
	public void setUY_Fdu_CoefficientLine_ID (int UY_Fdu_CoefficientLine_ID)
	{
		if (UY_Fdu_CoefficientLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CoefficientLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CoefficientLine_ID, Integer.valueOf(UY_Fdu_CoefficientLine_ID));
	}

	/** Get UY_Fdu_CoefficientLine.
		@return UY_Fdu_CoefficientLine	  */
	public int getUY_Fdu_CoefficientLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_CoefficientLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Currency getUY_Fdu_Currency() throws RuntimeException
    {
		return (I_UY_Fdu_Currency)MTable.get(getCtx(), I_UY_Fdu_Currency.Table_Name)
			.getPO(getUY_Fdu_Currency_ID(), get_TrxName());	}

	/** Set UY_Fdu_Currency.
		@param UY_Fdu_Currency_ID UY_Fdu_Currency	  */
	public void setUY_Fdu_Currency_ID (int UY_Fdu_Currency_ID)
	{
		if (UY_Fdu_Currency_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Currency_ID, Integer.valueOf(UY_Fdu_Currency_ID));
	}

	/** Get UY_Fdu_Currency.
		@return UY_Fdu_Currency	  */
	public int getUY_Fdu_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Productos getUY_Fdu_Productos() throws RuntimeException
    {
		return (I_UY_Fdu_Productos)MTable.get(getCtx(), I_UY_Fdu_Productos.Table_Name)
			.getPO(getUY_Fdu_Productos_ID(), get_TrxName());	}

	/** Set UY_Fdu_Productos.
		@param UY_Fdu_Productos_ID UY_Fdu_Productos	  */
	public void setUY_Fdu_Productos_ID (int UY_Fdu_Productos_ID)
	{
		if (UY_Fdu_Productos_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Productos_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Productos_ID, Integer.valueOf(UY_Fdu_Productos_ID));
	}

	/** Get UY_Fdu_Productos.
		@return UY_Fdu_Productos	  */
	public int getUY_Fdu_Productos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Productos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}