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

/** Generated Model for UY_TR_Config_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Config_Acct extends PO implements I_UY_TR_Config_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140910L;

    /** Standard Constructor */
    public X_UY_TR_Config_Acct (Properties ctx, int UY_TR_Config_Acct_ID, String trxName)
    {
      super (ctx, UY_TR_Config_Acct_ID, trxName);
      /** if (UY_TR_Config_Acct_ID == 0)
        {
			setUY_TR_Config_Acct_ID (0);
			setUY_TR_Config_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Config_Acct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Config_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getP_TR_IVA_NoResidente_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_TR_IVA_NoResidente_Acct(), get_TrxName());	}

	/** Set P_TR_IVA_NoResidente_Acct.
		@param P_TR_IVA_NoResidente_Acct P_TR_IVA_NoResidente_Acct	  */
	public void setP_TR_IVA_NoResidente_Acct (int P_TR_IVA_NoResidente_Acct)
	{
		set_Value (COLUMNNAME_P_TR_IVA_NoResidente_Acct, Integer.valueOf(P_TR_IVA_NoResidente_Acct));
	}

	/** Get P_TR_IVA_NoResidente_Acct.
		@return P_TR_IVA_NoResidente_Acct	  */
	public int getP_TR_IVA_NoResidente_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TR_IVA_NoResidente_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_TR_IVA_Representado_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_TR_IVA_Representado_Acct(), get_TrxName());	}

	/** Set P_TR_IVA_Representado_Acct.
		@param P_TR_IVA_Representado_Acct P_TR_IVA_Representado_Acct	  */
	public void setP_TR_IVA_Representado_Acct (int P_TR_IVA_Representado_Acct)
	{
		set_Value (COLUMNNAME_P_TR_IVA_Representado_Acct, Integer.valueOf(P_TR_IVA_Representado_Acct));
	}

	/** Get P_TR_IVA_Representado_Acct.
		@return P_TR_IVA_Representado_Acct	  */
	public int getP_TR_IVA_Representado_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TR_IVA_Representado_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_TR_PagaRepresentado_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_TR_PagaRepresentado_Acct(), get_TrxName());	}

	/** Set P_TR_PagaRepresentado_Acct.
		@param P_TR_PagaRepresentado_Acct P_TR_PagaRepresentado_Acct	  */
	public void setP_TR_PagaRepresentado_Acct (int P_TR_PagaRepresentado_Acct)
	{
		set_Value (COLUMNNAME_P_TR_PagaRepresentado_Acct, Integer.valueOf(P_TR_PagaRepresentado_Acct));
	}

	/** Get P_TR_PagaRepresentado_Acct.
		@return P_TR_PagaRepresentado_Acct	  */
	public int getP_TR_PagaRepresentado_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TR_PagaRepresentado_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_TR_Representado_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_TR_Representado_Acct(), get_TrxName());	}

	/** Set P_TR_Representado_Acct.
		@param P_TR_Representado_Acct P_TR_Representado_Acct	  */
	public void setP_TR_Representado_Acct (int P_TR_Representado_Acct)
	{
		set_Value (COLUMNNAME_P_TR_Representado_Acct, Integer.valueOf(P_TR_Representado_Acct));
	}

	/** Get P_TR_Representado_Acct.
		@return P_TR_Representado_Acct	  */
	public int getP_TR_Representado_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TR_Representado_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Config_Acct.
		@param UY_TR_Config_Acct_ID UY_TR_Config_Acct	  */
	public void setUY_TR_Config_Acct_ID (int UY_TR_Config_Acct_ID)
	{
		if (UY_TR_Config_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Config_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Config_Acct_ID, Integer.valueOf(UY_TR_Config_Acct_ID));
	}

	/** Get UY_TR_Config_Acct.
		@return UY_TR_Config_Acct	  */
	public int getUY_TR_Config_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Config_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Config getUY_TR_Config() throws RuntimeException
    {
		return (I_UY_TR_Config)MTable.get(getCtx(), I_UY_TR_Config.Table_Name)
			.getPO(getUY_TR_Config_ID(), get_TrxName());	}

	/** Set UY_TR_Config.
		@param UY_TR_Config_ID UY_TR_Config	  */
	public void setUY_TR_Config_ID (int UY_TR_Config_ID)
	{
		if (UY_TR_Config_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Config_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Config_ID, Integer.valueOf(UY_TR_Config_ID));
	}

	/** Get UY_TR_Config.
		@return UY_TR_Config	  */
	public int getUY_TR_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}