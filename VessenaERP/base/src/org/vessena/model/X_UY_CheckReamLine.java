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

/** Generated Model for UY_CheckReamLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CheckReamLine extends PO implements I_UY_CheckReamLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140709L;

    /** Standard Constructor */
    public X_UY_CheckReamLine (Properties ctx, int UY_CheckReamLine_ID, String trxName)
    {
      super (ctx, UY_CheckReamLine_ID, trxName);
      /** if (UY_CheckReamLine_ID == 0)
        {
			setisused (false);
			setUY_CheckReam_ID (0);
			setUY_CheckReamLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CheckReamLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CheckReamLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Check No.
		@param CheckNo 
		Check Number
	  */
	public void setCheckNo (int CheckNo)
	{
		set_Value (COLUMNNAME_CheckNo, Integer.valueOf(CheckNo));
	}

	/** Get Check No.
		@return Check Number
	  */
	public int getCheckNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CheckNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set isused.
		@param isused isused	  */
	public void setisused (boolean isused)
	{
		set_Value (COLUMNNAME_isused, Boolean.valueOf(isused));
	}

	/** Get isused.
		@return isused	  */
	public boolean isused () 
	{
		Object oo = get_Value(COLUMNNAME_isused);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_CheckReam getUY_CheckReam() throws RuntimeException
    {
		return (I_UY_CheckReam)MTable.get(getCtx(), I_UY_CheckReam.Table_Name)
			.getPO(getUY_CheckReam_ID(), get_TrxName());	}

	/** Set UY_CheckReam.
		@param UY_CheckReam_ID UY_CheckReam	  */
	public void setUY_CheckReam_ID (int UY_CheckReam_ID)
	{
		if (UY_CheckReam_ID < 1) 
			set_Value (COLUMNNAME_UY_CheckReam_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CheckReam_ID, Integer.valueOf(UY_CheckReam_ID));
	}

	/** Get UY_CheckReam.
		@return UY_CheckReam	  */
	public int getUY_CheckReam_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckReam_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CheckReamLine.
		@param UY_CheckReamLine_ID UY_CheckReamLine	  */
	public void setUY_CheckReamLine_ID (int UY_CheckReamLine_ID)
	{
		if (UY_CheckReamLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CheckReamLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CheckReamLine_ID, Integer.valueOf(UY_CheckReamLine_ID));
	}

	/** Get UY_CheckReamLine.
		@return UY_CheckReamLine	  */
	public int getUY_CheckReamLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckReamLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException
    {
		return (I_UY_MediosPago)MTable.get(getCtx(), I_UY_MediosPago.Table_Name)
			.getPO(getUY_MediosPago_ID(), get_TrxName());	}

	/** Set Medios de Pago.
		@param UY_MediosPago_ID Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID)
	{
		if (UY_MediosPago_ID < 1) 
			set_Value (COLUMNNAME_UY_MediosPago_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MediosPago_ID, Integer.valueOf(UY_MediosPago_ID));
	}

	/** Get Medios de Pago.
		@return Medios de Pago	  */
	public int getUY_MediosPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MediosPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}