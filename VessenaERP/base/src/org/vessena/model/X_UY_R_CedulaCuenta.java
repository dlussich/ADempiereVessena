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

/** Generated Model for UY_R_CedulaCuenta
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_CedulaCuenta extends PO implements I_UY_R_CedulaCuenta, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150828L;

    /** Standard Constructor */
    public X_UY_R_CedulaCuenta (Properties ctx, int UY_R_CedulaCuenta_ID, String trxName)
    {
      super (ctx, UY_R_CedulaCuenta_ID, trxName);
      /** if (UY_R_CedulaCuenta_ID == 0)
        {
			setAccountNo (null);
			setUY_R_CedulaCuenta_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_R_CedulaCuenta (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_CedulaCuenta[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set InternalCode.
		@param InternalCode InternalCode	  */
	public void setInternalCode (String InternalCode)
	{
		set_Value (COLUMNNAME_InternalCode, InternalCode);
	}

	/** Get InternalCode.
		@return InternalCode	  */
	public String getInternalCode () 
	{
		return (String)get_Value(COLUMNNAME_InternalCode);
	}

	/** Set UY_R_CedulaCuenta.
		@param UY_R_CedulaCuenta_ID UY_R_CedulaCuenta	  */
	public void setUY_R_CedulaCuenta_ID (int UY_R_CedulaCuenta_ID)
	{
		if (UY_R_CedulaCuenta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_CedulaCuenta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_CedulaCuenta_ID, Integer.valueOf(UY_R_CedulaCuenta_ID));
	}

	/** Get UY_R_CedulaCuenta.
		@return UY_R_CedulaCuenta	  */
	public int getUY_R_CedulaCuenta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_CedulaCuenta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException
    {
		return (I_UY_R_Reclamo)MTable.get(getCtx(), I_UY_R_Reclamo.Table_Name)
			.getPO(getUY_R_Reclamo_ID(), get_TrxName());	}

	/** Set UY_R_Reclamo.
		@param UY_R_Reclamo_ID UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID)
	{
		if (UY_R_Reclamo_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, Integer.valueOf(UY_R_Reclamo_ID));
	}

	/** Get UY_R_Reclamo.
		@return UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}