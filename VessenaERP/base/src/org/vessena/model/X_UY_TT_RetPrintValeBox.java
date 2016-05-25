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

/** Generated Model for UY_TT_RetPrintValeBox
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_RetPrintValeBox extends PO implements I_UY_TT_RetPrintValeBox, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150106L;

    /** Standard Constructor */
    public X_UY_TT_RetPrintValeBox (Properties ctx, int UY_TT_RetPrintValeBox_ID, String trxName)
    {
      super (ctx, UY_TT_RetPrintValeBox_ID, trxName);
      /** if (UY_TT_RetPrintValeBox_ID == 0)
        {
			setIsRetained (false);
			setUY_TT_RetPrintValeBox_ID (0);
			setUY_TT_RetPrintVale_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_RetPrintValeBox (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_RetPrintValeBox[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set ImpresionVale.
		@param ImpresionVale ImpresionVale	  */
	public void setImpresionVale (boolean ImpresionVale)
	{
		set_Value (COLUMNNAME_ImpresionVale, Boolean.valueOf(ImpresionVale));
	}

	/** Get ImpresionVale.
		@return ImpresionVale	  */
	public boolean isImpresionVale () 
	{
		Object oo = get_Value(COLUMNNAME_ImpresionVale);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRetained.
		@param IsRetained IsRetained	  */
	public void setIsRetained (boolean IsRetained)
	{
		set_Value (COLUMNNAME_IsRetained, Boolean.valueOf(IsRetained));
	}

	/** Get IsRetained.
		@return IsRetained	  */
	public boolean isRetained () 
	{
		Object oo = get_Value(COLUMNNAME_IsRetained);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set RetainedStatus.
		@param RetainedStatus RetainedStatus	  */
	public void setRetainedStatus (String RetainedStatus)
	{
		set_Value (COLUMNNAME_RetainedStatus, RetainedStatus);
	}

	/** Get RetainedStatus.
		@return RetainedStatus	  */
	public String getRetainedStatus () 
	{
		return (String)get_Value(COLUMNNAME_RetainedStatus);
	}

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID(), get_TrxName());	}

	/** Set UY_DeliveryPoint.
		@param UY_DeliveryPoint_ID UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID)
	{
		if (UY_DeliveryPoint_ID < 1) 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, Integer.valueOf(UY_DeliveryPoint_ID));
	}

	/** Get UY_DeliveryPoint.
		@return UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException
    {
		return (I_UY_TT_Box)MTable.get(getCtx(), I_UY_TT_Box.Table_Name)
			.getPO(getUY_TT_Box_ID(), get_TrxName());	}

	/** Set UY_TT_Box.
		@param UY_TT_Box_ID UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID)
	{
		if (UY_TT_Box_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Box_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Box_ID, Integer.valueOf(UY_TT_Box_ID));
	}

	/** Get UY_TT_Box.
		@return UY_TT_Box	  */
	public int getUY_TT_Box_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_RetPrintValeBox.
		@param UY_TT_RetPrintValeBox_ID UY_TT_RetPrintValeBox	  */
	public void setUY_TT_RetPrintValeBox_ID (int UY_TT_RetPrintValeBox_ID)
	{
		if (UY_TT_RetPrintValeBox_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_RetPrintValeBox_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_RetPrintValeBox_ID, Integer.valueOf(UY_TT_RetPrintValeBox_ID));
	}

	/** Get UY_TT_RetPrintValeBox.
		@return UY_TT_RetPrintValeBox	  */
	public int getUY_TT_RetPrintValeBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_RetPrintValeBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_RetPrintVale getUY_TT_RetPrintVale() throws RuntimeException
    {
		return (I_UY_TT_RetPrintVale)MTable.get(getCtx(), I_UY_TT_RetPrintVale.Table_Name)
			.getPO(getUY_TT_RetPrintVale_ID(), get_TrxName());	}

	/** Set UY_TT_RetPrintVale.
		@param UY_TT_RetPrintVale_ID UY_TT_RetPrintVale	  */
	public void setUY_TT_RetPrintVale_ID (int UY_TT_RetPrintVale_ID)
	{
		if (UY_TT_RetPrintVale_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_RetPrintVale_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_RetPrintVale_ID, Integer.valueOf(UY_TT_RetPrintVale_ID));
	}

	/** Get UY_TT_RetPrintVale.
		@return UY_TT_RetPrintVale	  */
	public int getUY_TT_RetPrintVale_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_RetPrintVale_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}