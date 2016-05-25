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

/** Generated Model for UY_TR_MicCountry
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_MicCountry extends PO implements I_UY_TR_MicCountry, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140910L;

    /** Standard Constructor */
    public X_UY_TR_MicCountry (Properties ctx, int UY_TR_MicCountry_ID, String trxName)
    {
      super (ctx, UY_TR_MicCountry_ID, trxName);
      /** if (UY_TR_MicCountry_ID == 0)
        {
			setC_Country_ID (0);
			setUY_Ciudad_ID (0);
			setUY_Ciudad_ID_1 (0);
			setUY_TR_Aduana_ID (0);
			setUY_TR_Aduana_ID_1 (0);
			setUY_TR_MicCountry_ID (0);
			setUY_TR_Mic_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_MicCountry (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_MicCountry[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
    {
		return (org.compiere.model.I_C_Country)MTable.get(getCtx(), org.compiere.model.I_C_Country.Table_Name)
			.getPO(getC_Country_ID(), get_TrxName());	}

	/** Set Country.
		@param C_Country_ID 
		Country 
	  */
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Country.
		@return Country 
	  */
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Numero.
		@param Numero Numero	  */
	public void setNumero (int Numero)
	{
		set_Value (COLUMNNAME_Numero, Integer.valueOf(Numero));
	}

	/** Get Numero.
		@return Numero	  */
	public int getNumero () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Numero);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException
    {
		return (I_UY_Ciudad)MTable.get(getCtx(), I_UY_Ciudad.Table_Name)
			.getPO(getUY_Ciudad_ID(), get_TrxName());	}

	/** Set UY_Ciudad.
		@param UY_Ciudad_ID UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID)
	{
		if (UY_Ciudad_ID < 1) 
			set_Value (COLUMNNAME_UY_Ciudad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Ciudad_ID, Integer.valueOf(UY_Ciudad_ID));
	}

	/** Get UY_Ciudad.
		@return UY_Ciudad	  */
	public int getUY_Ciudad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_1.
		@param UY_Ciudad_ID_1 UY_Ciudad_ID_1	  */
	public void setUY_Ciudad_ID_1 (int UY_Ciudad_ID_1)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_1, Integer.valueOf(UY_Ciudad_ID_1));
	}

	/** Get UY_Ciudad_ID_1.
		@return UY_Ciudad_ID_1	  */
	public int getUY_Ciudad_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Aduana getUY_TR_Aduana() throws RuntimeException
    {
		return (I_UY_TR_Aduana)MTable.get(getCtx(), I_UY_TR_Aduana.Table_Name)
			.getPO(getUY_TR_Aduana_ID(), get_TrxName());	}

	/** Set UY_TR_Aduana.
		@param UY_TR_Aduana_ID UY_TR_Aduana	  */
	public void setUY_TR_Aduana_ID (int UY_TR_Aduana_ID)
	{
		if (UY_TR_Aduana_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Aduana_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Aduana_ID, Integer.valueOf(UY_TR_Aduana_ID));
	}

	/** Get UY_TR_Aduana.
		@return UY_TR_Aduana	  */
	public int getUY_TR_Aduana_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Aduana_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Aduana_ID_1.
		@param UY_TR_Aduana_ID_1 UY_TR_Aduana_ID_1	  */
	public void setUY_TR_Aduana_ID_1 (int UY_TR_Aduana_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Aduana_ID_1, Integer.valueOf(UY_TR_Aduana_ID_1));
	}

	/** Get UY_TR_Aduana_ID_1.
		@return UY_TR_Aduana_ID_1	  */
	public int getUY_TR_Aduana_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Aduana_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_MicCountry.
		@param UY_TR_MicCountry_ID UY_TR_MicCountry	  */
	public void setUY_TR_MicCountry_ID (int UY_TR_MicCountry_ID)
	{
		if (UY_TR_MicCountry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MicCountry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MicCountry_ID, Integer.valueOf(UY_TR_MicCountry_ID));
	}

	/** Get UY_TR_MicCountry.
		@return UY_TR_MicCountry	  */
	public int getUY_TR_MicCountry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_MicCountry_ID);
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

	public I_UY_TR_OperativeSite getUY_TR_OperativeSite() throws RuntimeException
    {
		return (I_UY_TR_OperativeSite)MTable.get(getCtx(), I_UY_TR_OperativeSite.Table_Name)
			.getPO(getUY_TR_OperativeSite_ID(), get_TrxName());	}

	/** Set UY_TR_OperativeSite.
		@param UY_TR_OperativeSite_ID UY_TR_OperativeSite	  */
	public void setUY_TR_OperativeSite_ID (int UY_TR_OperativeSite_ID)
	{
		if (UY_TR_OperativeSite_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_OperativeSite_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_OperativeSite_ID, Integer.valueOf(UY_TR_OperativeSite_ID));
	}

	/** Get UY_TR_OperativeSite.
		@return UY_TR_OperativeSite	  */
	public int getUY_TR_OperativeSite_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_OperativeSite_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_OperativeSite_ID_1.
		@param UY_TR_OperativeSite_ID_1 UY_TR_OperativeSite_ID_1	  */
	public void setUY_TR_OperativeSite_ID_1 (int UY_TR_OperativeSite_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_OperativeSite_ID_1, Integer.valueOf(UY_TR_OperativeSite_ID_1));
	}

	/** Get UY_TR_OperativeSite_ID_1.
		@return UY_TR_OperativeSite_ID_1	  */
	public int getUY_TR_OperativeSite_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_OperativeSite_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}