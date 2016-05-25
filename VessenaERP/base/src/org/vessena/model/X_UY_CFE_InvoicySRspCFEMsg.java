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

/** Generated Model for UY_CFE_InvoicySRspCFEMsg
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_InvoicySRspCFEMsg extends PO implements I_UY_CFE_InvoicySRspCFEMsg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160128L;

    /** Standard Constructor */
    public X_UY_CFE_InvoicySRspCFEMsg (Properties ctx, int UY_CFE_InvoicySRspCFEMsg_ID, String trxName)
    {
      super (ctx, UY_CFE_InvoicySRspCFEMsg_ID, trxName);
      /** if (UY_CFE_InvoicySRspCFEMsg_ID == 0)
        {
			setUY_CFE_InvoicySRspCFE_ID (0);
			setUY_CFE_InvoicySRspCFEMsg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_InvoicySRspCFEMsg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_InvoicySRspCFEMsg[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Codigo Mensaje Item CFE.
		@param CFEErrCod Codigo Mensaje Item CFE	  */
	public void setCFEErrCod (BigDecimal CFEErrCod)
	{
		set_Value (COLUMNNAME_CFEErrCod, CFEErrCod);
	}

	/** Get Codigo Mensaje Item CFE.
		@return Codigo Mensaje Item CFE	  */
	public BigDecimal getCFEErrCod () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CFEErrCod);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Mensaje Item CFE.
		@param CFEErrDesc Mensaje Item CFE	  */
	public void setCFEErrDesc (String CFEErrDesc)
	{
		set_Value (COLUMNNAME_CFEErrDesc, CFEErrDesc);
	}

	/** Get Mensaje Item CFE.
		@return Mensaje Item CFE	  */
	public String getCFEErrDesc () 
	{
		return (String)get_Value(COLUMNNAME_CFEErrDesc);
	}

	public I_UY_CFE_InvoicySRspCFE getUY_CFE_InvoicySRspCFE() throws RuntimeException
    {
		return (I_UY_CFE_InvoicySRspCFE)MTable.get(getCtx(), I_UY_CFE_InvoicySRspCFE.Table_Name)
			.getPO(getUY_CFE_InvoicySRspCFE_ID(), get_TrxName());	}

	/** Set UY_CFE_InvoicySRspCFE.
		@param UY_CFE_InvoicySRspCFE_ID UY_CFE_InvoicySRspCFE	  */
	public void setUY_CFE_InvoicySRspCFE_ID (int UY_CFE_InvoicySRspCFE_ID)
	{
		if (UY_CFE_InvoicySRspCFE_ID < 1) 
			set_Value (COLUMNNAME_UY_CFE_InvoicySRspCFE_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CFE_InvoicySRspCFE_ID, Integer.valueOf(UY_CFE_InvoicySRspCFE_ID));
	}

	/** Get UY_CFE_InvoicySRspCFE.
		@return UY_CFE_InvoicySRspCFE	  */
	public int getUY_CFE_InvoicySRspCFE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InvoicySRspCFE_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CFE_InvoicySRspCFEMsg.
		@param UY_CFE_InvoicySRspCFEMsg_ID UY_CFE_InvoicySRspCFEMsg	  */
	public void setUY_CFE_InvoicySRspCFEMsg_ID (int UY_CFE_InvoicySRspCFEMsg_ID)
	{
		if (UY_CFE_InvoicySRspCFEMsg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InvoicySRspCFEMsg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InvoicySRspCFEMsg_ID, Integer.valueOf(UY_CFE_InvoicySRspCFEMsg_ID));
	}

	/** Get UY_CFE_InvoicySRspCFEMsg.
		@return UY_CFE_InvoicySRspCFEMsg	  */
	public int getUY_CFE_InvoicySRspCFEMsg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InvoicySRspCFEMsg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}