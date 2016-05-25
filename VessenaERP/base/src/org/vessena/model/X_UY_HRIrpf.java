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

/** Generated Model for UY_HRIrpf
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_HRIrpf extends PO implements I_UY_HRIrpf, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_HRIrpf (Properties ctx, int UY_HRIrpf_ID, String trxName)
    {
      super (ctx, UY_HRIrpf_ID, trxName);
      /** if (UY_HRIrpf_ID == 0)
        {
			setamtbpc (Env.ZERO);
			setamtbpcanual (Env.ZERO);
			setUY_HRIrpf_ID (0);
			setUY_HRParametros_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRIrpf (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRIrpf[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amtbpc.
		@param amtbpc amtbpc	  */
	public void setamtbpc (BigDecimal amtbpc)
	{
		set_Value (COLUMNNAME_amtbpc, amtbpc);
	}

	/** Get amtbpc.
		@return amtbpc	  */
	public BigDecimal getamtbpc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtbpc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtbpcanual.
		@param amtbpcanual amtbpcanual	  */
	public void setamtbpcanual (BigDecimal amtbpcanual)
	{
		set_Value (COLUMNNAME_amtbpcanual, amtbpcanual);
	}

	/** Get amtbpcanual.
		@return amtbpcanual	  */
	public BigDecimal getamtbpcanual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtbpcanual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_HRIrpf.
		@param UY_HRIrpf_ID UY_HRIrpf	  */
	public void setUY_HRIrpf_ID (int UY_HRIrpf_ID)
	{
		if (UY_HRIrpf_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRIrpf_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRIrpf_ID, Integer.valueOf(UY_HRIrpf_ID));
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