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

/** Generated Model for UY_TR_MicTask
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_MicTask extends PO implements I_UY_TR_MicTask, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141128L;

    /** Standard Constructor */
    public X_UY_TR_MicTask (Properties ctx, int UY_TR_MicTask_ID, String trxName)
    {
      super (ctx, UY_TR_MicTask_ID, trxName);
      /** if (UY_TR_MicTask_ID == 0)
        {
			setUY_TR_Crt_ID (0);
			setUY_TR_Mic_ID (0);
			setUY_TR_MicTask_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_MicTask (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_MicTask[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CrtImgNum.
		@param CrtImgNum CrtImgNum	  */
	public void setCrtImgNum (String CrtImgNum)
	{
		set_Value (COLUMNNAME_CrtImgNum, CrtImgNum);
	}

	/** Get CrtImgNum.
		@return CrtImgNum	  */
	public String getCrtImgNum () 
	{
		return (String)get_Value(COLUMNNAME_CrtImgNum);
	}

	/** Set CrtImgStatus.
		@param CrtImgStatus CrtImgStatus	  */
	public void setCrtImgStatus (String CrtImgStatus)
	{
		set_Value (COLUMNNAME_CrtImgStatus, CrtImgStatus);
	}

	/** Get CrtImgStatus.
		@return CrtImgStatus	  */
	public String getCrtImgStatus () 
	{
		return (String)get_Value(COLUMNNAME_CrtImgStatus);
	}

	/** Set CrtLineStatus.
		@param CrtLineStatus CrtLineStatus	  */
	public void setCrtLineStatus (String CrtLineStatus)
	{
		set_Value (COLUMNNAME_CrtLineStatus, CrtLineStatus);
	}

	/** Get CrtLineStatus.
		@return CrtLineStatus	  */
	public String getCrtLineStatus () 
	{
		return (String)get_Value(COLUMNNAME_CrtLineStatus);
	}

	/** Set CrtStatus.
		@param CrtStatus CrtStatus	  */
	public void setCrtStatus (String CrtStatus)
	{
		set_Value (COLUMNNAME_CrtStatus, CrtStatus);
	}

	/** Get CrtStatus.
		@return CrtStatus	  */
	public String getCrtStatus () 
	{
		return (String)get_Value(COLUMNNAME_CrtStatus);
	}

	/** Set SecNoCrt.
		@param SecNoCrt SecNoCrt	  */
	public void setSecNoCrt (String SecNoCrt)
	{
		set_Value (COLUMNNAME_SecNoCrt, SecNoCrt);
	}

	/** Get SecNoCrt.
		@return SecNoCrt	  */
	public String getSecNoCrt () 
	{
		return (String)get_Value(COLUMNNAME_SecNoCrt);
	}

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
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

	/** Set UY_TR_MicTask.
		@param UY_TR_MicTask_ID UY_TR_MicTask	  */
	public void setUY_TR_MicTask_ID (int UY_TR_MicTask_ID)
	{
		if (UY_TR_MicTask_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MicTask_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MicTask_ID, Integer.valueOf(UY_TR_MicTask_ID));
	}

	/** Get UY_TR_MicTask.
		@return UY_TR_MicTask	  */
	public int getUY_TR_MicTask_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_MicTask_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}