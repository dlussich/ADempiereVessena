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

/** Generated Model for UY_RT_DiscMovementDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_DiscMovementDetail extends PO implements I_UY_RT_DiscMovementDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160308L;

    /** Standard Constructor */
    public X_UY_RT_DiscMovementDetail (Properties ctx, int UY_RT_DiscMovementDetail_ID, String trxName)
    {
      super (ctx, UY_RT_DiscMovementDetail_ID, trxName);
      /** if (UY_RT_DiscMovementDetail_ID == 0)
        {
			setUY_RT_DiscMovementDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_DiscMovementDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_DiscMovementDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Org getAD_Org_To() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Org)MTable.get(getCtx(), org.compiere.model.I_AD_Org.Table_Name)
			.getPO(getAD_Org_ID_To(), get_TrxName());	}

	/** Set AD_Org_ID_To.
		@param AD_Org_ID_To AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To)
	{
		set_Value (COLUMNNAME_AD_Org_ID_To, Integer.valueOf(AD_Org_ID_To));
	}

	/** Get AD_Org_ID_To.
		@return AD_Org_ID_To	  */
	public int getAD_Org_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Org_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set iddescuento.
		@param iddescuento iddescuento	  */
	public void setiddescuento (int iddescuento)
	{
		set_Value (COLUMNNAME_iddescuento, Integer.valueOf(iddescuento));
	}

	/** Get iddescuento.
		@return iddescuento	  */
	public int getiddescuento () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_iddescuento);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set idpromocion.
		@param idpromocion idpromocion	  */
	public void setidpromocion (String idpromocion)
	{
		set_Value (COLUMNNAME_idpromocion, idpromocion);
	}

	/** Get idpromocion.
		@return idpromocion	  */
	public String getidpromocion () 
	{
		return (String)get_Value(COLUMNNAME_idpromocion);
	}

	/** Set importedescuento.
		@param importedescuento importedescuento	  */
	public void setimportedescuento (BigDecimal importedescuento)
	{
		set_Value (COLUMNNAME_importedescuento, importedescuento);
	}

	/** Get importedescuento.
		@return importedescuento	  */
	public BigDecimal getimportedescuento () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_importedescuento);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set tipodescuento.
		@param tipodescuento tipodescuento	  */
	public void settipodescuento (String tipodescuento)
	{
		set_Value (COLUMNNAME_tipodescuento, tipodescuento);
	}

	/** Get tipodescuento.
		@return tipodescuento	  */
	public String gettipodescuento () 
	{
		return (String)get_Value(COLUMNNAME_tipodescuento);
	}

	/** Set UY_RT_DiscMovementDetail.
		@param UY_RT_DiscMovementDetail_ID UY_RT_DiscMovementDetail	  */
	public void setUY_RT_DiscMovementDetail_ID (int UY_RT_DiscMovementDetail_ID)
	{
		if (UY_RT_DiscMovementDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_DiscMovementDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_DiscMovementDetail_ID, Integer.valueOf(UY_RT_DiscMovementDetail_ID));
	}

	/** Get UY_RT_DiscMovementDetail.
		@return UY_RT_DiscMovementDetail	  */
	public int getUY_RT_DiscMovementDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_DiscMovementDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_MovementDetail getUY_RT_MovementDetail() throws RuntimeException
    {
		return (I_UY_RT_MovementDetail)MTable.get(getCtx(), I_UY_RT_MovementDetail.Table_Name)
			.getPO(getUY_RT_MovementDetail_ID(), get_TrxName());	}

	/** Set UY_RT_MovementDetail.
		@param UY_RT_MovementDetail_ID UY_RT_MovementDetail	  */
	public void setUY_RT_MovementDetail_ID (int UY_RT_MovementDetail_ID)
	{
		if (UY_RT_MovementDetail_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_MovementDetail_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_MovementDetail_ID, Integer.valueOf(UY_RT_MovementDetail_ID));
	}

	/** Get UY_RT_MovementDetail.
		@return UY_RT_MovementDetail	  */
	public int getUY_RT_MovementDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_MovementDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_Movement getUY_RT_Movement() throws RuntimeException
    {
		return (I_UY_RT_Movement)MTable.get(getCtx(), I_UY_RT_Movement.Table_Name)
			.getPO(getUY_RT_Movement_ID(), get_TrxName());	}

	/** Set UY_RT_Movement.
		@param UY_RT_Movement_ID UY_RT_Movement	  */
	public void setUY_RT_Movement_ID (int UY_RT_Movement_ID)
	{
		if (UY_RT_Movement_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_Movement_ID, Integer.valueOf(UY_RT_Movement_ID));
	}

	/** Get UY_RT_Movement.
		@return UY_RT_Movement	  */
	public int getUY_RT_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Movement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}