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

/** Generated Model for UY_R_ReclamoAdjunto
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_ReclamoAdjunto extends PO implements I_UY_R_ReclamoAdjunto, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130620L;

    /** Standard Constructor */
    public X_UY_R_ReclamoAdjunto (Properties ctx, int UY_R_ReclamoAdjunto_ID, String trxName)
    {
      super (ctx, UY_R_ReclamoAdjunto_ID, trxName);
      /** if (UY_R_ReclamoAdjunto_ID == 0)
        {
			setIsMandatory (true);
// Y
			setUY_R_Adjunto_ID (0);
			setUY_R_ReclamoAdjunto_ID (0);
			setUY_R_Reclamo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_ReclamoAdjunto (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_ReclamoAdjunto[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Mandatory.
		@param IsMandatory 
		Data entry is required in this column
	  */
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Mandatory.
		@return Data entry is required in this column
	  */
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_R_Adjunto.
		@param UY_R_Adjunto_ID UY_R_Adjunto	  */
	public void setUY_R_Adjunto_ID (int UY_R_Adjunto_ID)
	{
		if (UY_R_Adjunto_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Adjunto_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Adjunto_ID, Integer.valueOf(UY_R_Adjunto_ID));
	}

	/** Get UY_R_Adjunto.
		@return UY_R_Adjunto	  */
	public int getUY_R_Adjunto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Adjunto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_ReclamoAdjunto.
		@param UY_R_ReclamoAdjunto_ID UY_R_ReclamoAdjunto	  */
	public void setUY_R_ReclamoAdjunto_ID (int UY_R_ReclamoAdjunto_ID)
	{
		if (UY_R_ReclamoAdjunto_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamoAdjunto_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamoAdjunto_ID, Integer.valueOf(UY_R_ReclamoAdjunto_ID));
	}

	/** Get UY_R_ReclamoAdjunto.
		@return UY_R_ReclamoAdjunto	  */
	public int getUY_R_ReclamoAdjunto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ReclamoAdjunto_ID);
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
}