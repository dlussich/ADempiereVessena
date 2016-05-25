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

/** Generated Model for UY_R_LoadXlsLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_LoadXlsLine extends PO implements I_UY_R_LoadXlsLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150930L;

    /** Standard Constructor */
    public X_UY_R_LoadXlsLine (Properties ctx, int UY_R_LoadXlsLine_ID, String trxName)
    {
      super (ctx, UY_R_LoadXlsLine_ID, trxName);
      /** if (UY_R_LoadXlsLine_ID == 0)
        {
			setUY_R_LoadXls_ID (0);
			setUY_R_LoadXlsLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_LoadXlsLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_LoadXlsLine[")
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

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
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

	/** NotificationVia AD_Reference_ID=1000288 */
	public static final int NOTIFICATIONVIA_AD_Reference_ID=1000288;
	/** Telefono Fijo = TEL */
	public static final String NOTIFICATIONVIA_TelefonoFijo = "TEL";
	/** Celular = CEL */
	public static final String NOTIFICATIONVIA_Celular = "CEL";
	/** Email = EMA */
	public static final String NOTIFICATIONVIA_Email = "EMA";
	/** Set NotificationVia.
		@param NotificationVia 
		Via Notificacion
	  */
	public void setNotificationVia (String NotificationVia)
	{

		set_Value (COLUMNNAME_NotificationVia, NotificationVia);
	}

	/** Get NotificationVia.
		@return Via Notificacion
	  */
	public String getNotificationVia () 
	{
		return (String)get_Value(COLUMNNAME_NotificationVia);
	}

	public I_UY_R_Canal getUY_R_Canal() throws RuntimeException
    {
		return (I_UY_R_Canal)MTable.get(getCtx(), I_UY_R_Canal.Table_Name)
			.getPO(getUY_R_Canal_ID(), get_TrxName());	}

	/** Set UY_R_Canal.
		@param UY_R_Canal_ID UY_R_Canal	  */
	public void setUY_R_Canal_ID (int UY_R_Canal_ID)
	{
		if (UY_R_Canal_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Canal_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Canal_ID, Integer.valueOf(UY_R_Canal_ID));
	}

	/** Get UY_R_Canal.
		@return UY_R_Canal	  */
	public int getUY_R_Canal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Canal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Cause getUY_R_Cause() throws RuntimeException
    {
		return (I_UY_R_Cause)MTable.get(getCtx(), I_UY_R_Cause.Table_Name)
			.getPO(getUY_R_Cause_ID(), get_TrxName());	}

	/** Set UY_R_Cause.
		@param UY_R_Cause_ID UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID)
	{
		if (UY_R_Cause_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Cause_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Cause_ID, Integer.valueOf(UY_R_Cause_ID));
	}

	/** Get UY_R_Cause.
		@return UY_R_Cause	  */
	public int getUY_R_Cause_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Cause_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_CedulaCuenta getUY_R_CedulaCuenta() throws RuntimeException
    {
		return (I_UY_R_CedulaCuenta)MTable.get(getCtx(), I_UY_R_CedulaCuenta.Table_Name)
			.getPO(getUY_R_CedulaCuenta_ID(), get_TrxName());	}

	/** Set UY_R_CedulaCuenta.
		@param UY_R_CedulaCuenta_ID UY_R_CedulaCuenta	  */
	public void setUY_R_CedulaCuenta_ID (int UY_R_CedulaCuenta_ID)
	{
		if (UY_R_CedulaCuenta_ID < 1) 
			set_Value (COLUMNNAME_UY_R_CedulaCuenta_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_CedulaCuenta_ID, Integer.valueOf(UY_R_CedulaCuenta_ID));
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

	public I_UY_R_LoadXls getUY_R_LoadXls() throws RuntimeException
    {
		return (I_UY_R_LoadXls)MTable.get(getCtx(), I_UY_R_LoadXls.Table_Name)
			.getPO(getUY_R_LoadXls_ID(), get_TrxName());	}

	/** Set UY_R_LoadXls.
		@param UY_R_LoadXls_ID UY_R_LoadXls	  */
	public void setUY_R_LoadXls_ID (int UY_R_LoadXls_ID)
	{
		if (UY_R_LoadXls_ID < 1) 
			set_Value (COLUMNNAME_UY_R_LoadXls_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_LoadXls_ID, Integer.valueOf(UY_R_LoadXls_ID));
	}

	/** Get UY_R_LoadXls.
		@return UY_R_LoadXls	  */
	public int getUY_R_LoadXls_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_LoadXls_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_LoadXlsLine.
		@param UY_R_LoadXlsLine_ID UY_R_LoadXlsLine	  */
	public void setUY_R_LoadXlsLine_ID (int UY_R_LoadXlsLine_ID)
	{
		if (UY_R_LoadXlsLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_LoadXlsLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_LoadXlsLine_ID, Integer.valueOf(UY_R_LoadXlsLine_ID));
	}

	/** Get UY_R_LoadXlsLine.
		@return UY_R_LoadXlsLine	  */
	public int getUY_R_LoadXlsLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_LoadXlsLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_SubCause getUY_R_SubCause() throws RuntimeException
    {
		return (I_UY_R_SubCause)MTable.get(getCtx(), I_UY_R_SubCause.Table_Name)
			.getPO(getUY_R_SubCause_ID(), get_TrxName());	}

	/** Set UY_R_SubCause.
		@param UY_R_SubCause_ID UY_R_SubCause	  */
	public void setUY_R_SubCause_ID (int UY_R_SubCause_ID)
	{
		if (UY_R_SubCause_ID < 1) 
			set_Value (COLUMNNAME_UY_R_SubCause_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_SubCause_ID, Integer.valueOf(UY_R_SubCause_ID));
	}

	/** Get UY_R_SubCause.
		@return UY_R_SubCause	  */
	public int getUY_R_SubCause_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_SubCause_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Type getUY_R_Type() throws RuntimeException
    {
		return (I_UY_R_Type)MTable.get(getCtx(), I_UY_R_Type.Table_Name)
			.getPO(getUY_R_Type_ID(), get_TrxName());	}

	/** Set UY_R_Type.
		@param UY_R_Type_ID UY_R_Type	  */
	public void setUY_R_Type_ID (int UY_R_Type_ID)
	{
		if (UY_R_Type_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Type_ID, Integer.valueOf(UY_R_Type_ID));
	}

	/** Get UY_R_Type.
		@return UY_R_Type	  */
	public int getUY_R_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}