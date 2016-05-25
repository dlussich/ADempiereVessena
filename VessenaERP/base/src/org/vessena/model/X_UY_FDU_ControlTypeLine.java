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

/** Generated Model for UY_FDU_ControlTypeLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_FDU_ControlTypeLine extends PO implements I_UY_FDU_ControlTypeLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130115L;

    /** Standard Constructor */
    public X_UY_FDU_ControlTypeLine (Properties ctx, int UY_FDU_ControlTypeLine_ID, String trxName)
    {
      super (ctx, UY_FDU_ControlTypeLine_ID, trxName);
      /** if (UY_FDU_ControlTypeLine_ID == 0)
        {
			setUY_FDU_Control_ID (0);
			setUY_FDU_ControlType_ID (0);
			setUY_FDU_ControlTypeLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FDU_ControlTypeLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FDU_ControlTypeLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FDU_Control getUY_FDU_Control() throws RuntimeException
    {
		return (I_UY_FDU_Control)MTable.get(getCtx(), I_UY_FDU_Control.Table_Name)
			.getPO(getUY_FDU_Control_ID(), get_TrxName());	}

	/** Set UY_FDU_Control.
		@param UY_FDU_Control_ID UY_FDU_Control	  */
	public void setUY_FDU_Control_ID (int UY_FDU_Control_ID)
	{
		if (UY_FDU_Control_ID < 1) 
			set_Value (COLUMNNAME_UY_FDU_Control_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FDU_Control_ID, Integer.valueOf(UY_FDU_Control_ID));
	}

	/** Get UY_FDU_Control.
		@return UY_FDU_Control	  */
	public int getUY_FDU_Control_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_Control_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FDU_ControlType getUY_FDU_ControlType() throws RuntimeException
    {
		return (I_UY_FDU_ControlType)MTable.get(getCtx(), I_UY_FDU_ControlType.Table_Name)
			.getPO(getUY_FDU_ControlType_ID(), get_TrxName());	}

	/** Set UY_FDU_ControlType.
		@param UY_FDU_ControlType_ID UY_FDU_ControlType	  */
	public void setUY_FDU_ControlType_ID (int UY_FDU_ControlType_ID)
	{
		if (UY_FDU_ControlType_ID < 1) 
			set_Value (COLUMNNAME_UY_FDU_ControlType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FDU_ControlType_ID, Integer.valueOf(UY_FDU_ControlType_ID));
	}

	/** Get UY_FDU_ControlType.
		@return UY_FDU_ControlType	  */
	public int getUY_FDU_ControlType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_ControlType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FDU_ControlTypeLine.
		@param UY_FDU_ControlTypeLine_ID UY_FDU_ControlTypeLine	  */
	public void setUY_FDU_ControlTypeLine_ID (int UY_FDU_ControlTypeLine_ID)
	{
		if (UY_FDU_ControlTypeLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FDU_ControlTypeLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FDU_ControlTypeLine_ID, Integer.valueOf(UY_FDU_ControlTypeLine_ID));
	}

	/** Get UY_FDU_ControlTypeLine.
		@return UY_FDU_ControlTypeLine	  */
	public int getUY_FDU_ControlTypeLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FDU_ControlTypeLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}