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

/** Generated Model for UY_Fdu_CoefficientIpc
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_CoefficientIpc extends PO implements I_UY_Fdu_CoefficientIpc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130115L;

    /** Standard Constructor */
    public X_UY_Fdu_CoefficientIpc (Properties ctx, int UY_Fdu_CoefficientIpc_ID, String trxName)
    {
      super (ctx, UY_Fdu_CoefficientIpc_ID, trxName);
      /** if (UY_Fdu_CoefficientIpc_ID == 0)
        {
			setUY_Fdu_CoefficientHdr_ID (0);
			setUY_Fdu_CoefficientIpc_ID (0);
			setUY_Fdu_CoefficientLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_CoefficientIpc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_CoefficientIpc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CuotaNo.
		@param CuotaNo 
		CuotaNo
	  */
	public void setCuotaNo (String CuotaNo)
	{
		set_Value (COLUMNNAME_CuotaNo, CuotaNo);
	}

	/** Get CuotaNo.
		@return CuotaNo
	  */
	public String getCuotaNo () 
	{
		return (String)get_Value(COLUMNNAME_CuotaNo);
	}

	/** Set ipc.
		@param ipc ipc	  */
	public void setipc (BigDecimal ipc)
	{
		set_Value (COLUMNNAME_ipc, ipc);
	}

	/** Get ipc.
		@return ipc	  */
	public BigDecimal getipc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ipc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set tem.
		@param tem tem	  */
	public void settem (BigDecimal tem)
	{
		set_Value (COLUMNNAME_tem, tem);
	}

	/** Get tem.
		@return tem	  */
	public BigDecimal gettem () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_tem);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set UY_Fdu_CoefficientIpc.
		@param UY_Fdu_CoefficientIpc_ID UY_Fdu_CoefficientIpc	  */
	public void setUY_Fdu_CoefficientIpc_ID (int UY_Fdu_CoefficientIpc_ID)
	{
		if (UY_Fdu_CoefficientIpc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CoefficientIpc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CoefficientIpc_ID, Integer.valueOf(UY_Fdu_CoefficientIpc_ID));
	}

	/** Get UY_Fdu_CoefficientIpc.
		@return UY_Fdu_CoefficientIpc	  */
	public int getUY_Fdu_CoefficientIpc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_CoefficientIpc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_CoefficientLine getUY_Fdu_CoefficientLine() throws RuntimeException
    {
		return (I_UY_Fdu_CoefficientLine)MTable.get(getCtx(), I_UY_Fdu_CoefficientLine.Table_Name)
			.getPO(getUY_Fdu_CoefficientLine_ID(), get_TrxName());	}

	/** Set UY_Fdu_CoefficientLine.
		@param UY_Fdu_CoefficientLine_ID UY_Fdu_CoefficientLine	  */
	public void setUY_Fdu_CoefficientLine_ID (int UY_Fdu_CoefficientLine_ID)
	{
		if (UY_Fdu_CoefficientLine_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_CoefficientLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_CoefficientLine_ID, Integer.valueOf(UY_Fdu_CoefficientLine_ID));
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
}