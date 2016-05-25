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

/** Generated Model for UY_ResguardoGenDet
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ResguardoGenDet extends PO implements I_UY_ResguardoGenDet, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160112L;

    /** Standard Constructor */
    public X_UY_ResguardoGenDet (Properties ctx, int UY_ResguardoGenDet_ID, String trxName)
    {
      super (ctx, UY_ResguardoGenDet_ID, trxName);
      /** if (UY_ResguardoGenDet_ID == 0)
        {
			setUY_ResguardoGenDet_ID (0);
			setUY_ResguardoGenDoc_ID (0);
			setUY_Retention_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ResguardoGenDet (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ResguardoGenDet[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtRetention.
		@param AmtRetention AmtRetention	  */
	public void setAmtRetention (BigDecimal AmtRetention)
	{
		set_Value (COLUMNNAME_AmtRetention, AmtRetention);
	}

	/** Get AmtRetention.
		@return AmtRetention	  */
	public BigDecimal getAmtRetention () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtRetention);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtretentionsource.
		@param amtretentionsource amtretentionsource	  */
	public void setamtretentionsource (BigDecimal amtretentionsource)
	{
		set_Value (COLUMNNAME_amtretentionsource, amtretentionsource);
	}

	/** Get amtretentionsource.
		@return amtretentionsource	  */
	public BigDecimal getamtretentionsource () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtretentionsource);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_ResguardoGenDet.
		@param UY_ResguardoGenDet_ID UY_ResguardoGenDet	  */
	public void setUY_ResguardoGenDet_ID (int UY_ResguardoGenDet_ID)
	{
		if (UY_ResguardoGenDet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ResguardoGenDet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ResguardoGenDet_ID, Integer.valueOf(UY_ResguardoGenDet_ID));
	}

	/** Get UY_ResguardoGenDet.
		@return UY_ResguardoGenDet	  */
	public int getUY_ResguardoGenDet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ResguardoGenDet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ResguardoGenDoc getUY_ResguardoGenDoc() throws RuntimeException
    {
		return (I_UY_ResguardoGenDoc)MTable.get(getCtx(), I_UY_ResguardoGenDoc.Table_Name)
			.getPO(getUY_ResguardoGenDoc_ID(), get_TrxName());	}

	/** Set UY_ResguardoGenDoc.
		@param UY_ResguardoGenDoc_ID UY_ResguardoGenDoc	  */
	public void setUY_ResguardoGenDoc_ID (int UY_ResguardoGenDoc_ID)
	{
		if (UY_ResguardoGenDoc_ID < 1) 
			set_Value (COLUMNNAME_UY_ResguardoGenDoc_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ResguardoGenDoc_ID, Integer.valueOf(UY_ResguardoGenDoc_ID));
	}

	/** Get UY_ResguardoGenDoc.
		@return UY_ResguardoGenDoc	  */
	public int getUY_ResguardoGenDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ResguardoGenDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Retention getUY_Retention() throws RuntimeException
    {
		return (I_UY_Retention)MTable.get(getCtx(), I_UY_Retention.Table_Name)
			.getPO(getUY_Retention_ID(), get_TrxName());	}

	/** Set UY_Retention.
		@param UY_Retention_ID UY_Retention	  */
	public void setUY_Retention_ID (int UY_Retention_ID)
	{
		if (UY_Retention_ID < 1) 
			set_Value (COLUMNNAME_UY_Retention_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Retention_ID, Integer.valueOf(UY_Retention_ID));
	}

	/** Get UY_Retention.
		@return UY_Retention	  */
	public int getUY_Retention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Retention_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}