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

/** Generated Model for UY_TT_UnifyBox
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_UnifyBox extends PO implements I_UY_TT_UnifyBox, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131006L;

    /** Standard Constructor */
    public X_UY_TT_UnifyBox (Properties ctx, int UY_TT_UnifyBox_ID, String trxName)
    {
      super (ctx, UY_TT_UnifyBox_ID, trxName);
      /** if (UY_TT_UnifyBox_ID == 0)
        {
			setComunicaUsuario (false);
// N
			setIsRetained (false);
// N
			setUY_TT_UnifyBox_ID (0);
			setUY_TT_Unify_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_UnifyBox (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_UnifyBox[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ComunicaUsuario.
		@param ComunicaUsuario ComunicaUsuario	  */
	public void setComunicaUsuario (boolean ComunicaUsuario)
	{
		set_Value (COLUMNNAME_ComunicaUsuario, Boolean.valueOf(ComunicaUsuario));
	}

	/** Get ComunicaUsuario.
		@return ComunicaUsuario	  */
	public boolean isComunicaUsuario () 
	{
		Object oo = get_Value(COLUMNNAME_ComunicaUsuario);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** RetainedStatus AD_Reference_ID=1000340 */
	public static final int RETAINEDSTATUS_AD_Reference_ID=1000340;
	/** Destruccion = DESTRUIR */
	public static final String RETAINEDSTATUS_Destruccion = "DESTRUIR";
	/** Inconsistente = INCONSISTENTE */
	public static final String RETAINEDSTATUS_Inconsistente = "INCONSISTENTE";
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

	/** Set UnificaCardCarrier.
		@param UnificaCardCarrier UnificaCardCarrier	  */
	public void setUnificaCardCarrier (boolean UnificaCardCarrier)
	{
		set_Value (COLUMNNAME_UnificaCardCarrier, Boolean.valueOf(UnificaCardCarrier));
	}

	/** Get UnificaCardCarrier.
		@return UnificaCardCarrier	  */
	public boolean isUnificaCardCarrier () 
	{
		Object oo = get_Value(COLUMNNAME_UnificaCardCarrier);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set UY_TT_UnifyBox.
		@param UY_TT_UnifyBox_ID UY_TT_UnifyBox	  */
	public void setUY_TT_UnifyBox_ID (int UY_TT_UnifyBox_ID)
	{
		if (UY_TT_UnifyBox_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_UnifyBox_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_UnifyBox_ID, Integer.valueOf(UY_TT_UnifyBox_ID));
	}

	/** Get UY_TT_UnifyBox.
		@return UY_TT_UnifyBox	  */
	public int getUY_TT_UnifyBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_UnifyBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Unify getUY_TT_Unify() throws RuntimeException
    {
		return (I_UY_TT_Unify)MTable.get(getCtx(), I_UY_TT_Unify.Table_Name)
			.getPO(getUY_TT_Unify_ID(), get_TrxName());	}

	/** Set UY_TT_Unify.
		@param UY_TT_Unify_ID UY_TT_Unify	  */
	public void setUY_TT_Unify_ID (int UY_TT_Unify_ID)
	{
		if (UY_TT_Unify_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Unify_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Unify_ID, Integer.valueOf(UY_TT_Unify_ID));
	}

	/** Get UY_TT_Unify.
		@return UY_TT_Unify	  */
	public int getUY_TT_Unify_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Unify_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}