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

/** Generated Model for UY_Fdu_Afinidad
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_Afinidad extends PO implements I_UY_Fdu_Afinidad, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121025L;

    /** Standard Constructor */
    public X_UY_Fdu_Afinidad (Properties ctx, int UY_Fdu_Afinidad_ID, String trxName)
    {
      super (ctx, UY_Fdu_Afinidad_ID, trxName);
      /** if (UY_Fdu_Afinidad_ID == 0)
        {
			setUY_Fdu_Afinidad_ID (0);
			setUY_FduFile_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_Afinidad (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_Afinidad[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_Afinidad.
		@param UY_Fdu_Afinidad_ID UY_Fdu_Afinidad	  */
	public void setUY_Fdu_Afinidad_ID (int UY_Fdu_Afinidad_ID)
	{
		if (UY_Fdu_Afinidad_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Afinidad_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_Afinidad_ID, Integer.valueOf(UY_Fdu_Afinidad_ID));
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

	public I_UY_FduFile getUY_FduFile() throws RuntimeException
    {
		return (I_UY_FduFile)MTable.get(getCtx(), I_UY_FduFile.Table_Name)
			.getPO(getUY_FduFile_ID(), get_TrxName());	}

	/** Set UY_FduFile_ID.
		@param UY_FduFile_ID UY_FduFile_ID	  */
	public void setUY_FduFile_ID (int UY_FduFile_ID)
	{
		if (UY_FduFile_ID < 1) 
			set_Value (COLUMNNAME_UY_FduFile_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduFile_ID, Integer.valueOf(UY_FduFile_ID));
	}

	/** Get UY_FduFile_ID.
		@return UY_FduFile_ID	  */
	public int getUY_FduFile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduFile_ID);
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