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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_R_ReclamoSign
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_ReclamoSign extends PO implements I_UY_R_ReclamoSign, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130701L;

    /** Standard Constructor */
    public X_UY_R_ReclamoSign (Properties ctx, int UY_R_ReclamoSign_ID, String trxName)
    {
      super (ctx, UY_R_ReclamoSign_ID, trxName);
      /** if (UY_R_ReclamoSign_ID == 0)
        {
			setIsExecuted (false);
// N
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setUY_R_Reclamo_ID (0);
			setUY_R_ReclamoSign_ID (0);
			setUY_R_Sign_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_ReclamoSign (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_ReclamoSign[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** ReclamoSignType AD_Reference_ID=1000313 */
	public static final int RECLAMOSIGNTYPE_AD_Reference_ID=1000313;
	/** Normal = NORMAL */
	public static final String RECLAMOSIGNTYPE_Normal = "NORMAL";
	/** Critico = CRITICO */
	public static final String RECLAMOSIGNTYPE_Critico = "CRITICO";
	/** Set ReclamoSignType.
		@param ReclamoSignType ReclamoSignType	  */
	public void setReclamoSignType (String ReclamoSignType)
	{

		set_Value (COLUMNNAME_ReclamoSignType, ReclamoSignType);
	}

	/** Get ReclamoSignType.
		@return ReclamoSignType	  */
	public String getReclamoSignType () 
	{
		return (String)get_Value(COLUMNNAME_ReclamoSignType);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
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

	/** Set UY_R_ReclamoSign.
		@param UY_R_ReclamoSign_ID UY_R_ReclamoSign	  */
	public void setUY_R_ReclamoSign_ID (int UY_R_ReclamoSign_ID)
	{
		if (UY_R_ReclamoSign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamoSign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_ReclamoSign_ID, Integer.valueOf(UY_R_ReclamoSign_ID));
	}

	/** Get UY_R_ReclamoSign.
		@return UY_R_ReclamoSign	  */
	public int getUY_R_ReclamoSign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_ReclamoSign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Sign getUY_R_Sign() throws RuntimeException
    {
		return (I_UY_R_Sign)MTable.get(getCtx(), I_UY_R_Sign.Table_Name)
			.getPO(getUY_R_Sign_ID(), get_TrxName());	}

	/** Set UY_R_Sign.
		@param UY_R_Sign_ID UY_R_Sign	  */
	public void setUY_R_Sign_ID (int UY_R_Sign_ID)
	{
		if (UY_R_Sign_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Sign_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Sign_ID, Integer.valueOf(UY_R_Sign_ID));
	}

	/** Get UY_R_Sign.
		@return UY_R_Sign	  */
	public int getUY_R_Sign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Sign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}