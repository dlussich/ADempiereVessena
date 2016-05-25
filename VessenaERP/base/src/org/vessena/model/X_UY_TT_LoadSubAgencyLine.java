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

/** Generated Model for UY_TT_LoadSubAgencyLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_LoadSubAgencyLine extends PO implements I_UY_TT_LoadSubAgencyLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150825L;

    /** Standard Constructor */
    public X_UY_TT_LoadSubAgencyLine (Properties ctx, int UY_TT_LoadSubAgencyLine_ID, String trxName)
    {
      super (ctx, UY_TT_LoadSubAgencyLine_ID, trxName);
      /** if (UY_TT_LoadSubAgencyLine_ID == 0)
        {
			setUY_R_Reclamo_ID (0);
			setUY_TT_Card_ID (0);
			setUY_TT_LoadSubAgency_ID (0);
			setUY_TT_LoadSubAgencyLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_LoadSubAgencyLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_LoadSubAgencyLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Date received.
		@param DateReceived 
		Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Date received.
		@return Date a product was received
	  */
	public Timestamp getDateReceived () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateReceived);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set SubAgencyNo.
		@param SubAgencyNo 
		SubAgencyNo
	  */
	public void setSubAgencyNo (String SubAgencyNo)
	{
		set_Value (COLUMNNAME_SubAgencyNo, SubAgencyNo);
	}

	/** Get SubAgencyNo.
		@return SubAgencyNo
	  */
	public String getSubAgencyNo () 
	{
		return (String)get_Value(COLUMNNAME_SubAgencyNo);
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

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException
    {
		return (I_UY_TT_Card)MTable.get(getCtx(), I_UY_TT_Card.Table_Name)
			.getPO(getUY_TT_Card_ID(), get_TrxName());	}

	/** Set UY_TT_Card.
		@param UY_TT_Card_ID UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID)
	{
		if (UY_TT_Card_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Card_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Card_ID, Integer.valueOf(UY_TT_Card_ID));
	}

	/** Get UY_TT_Card.
		@return UY_TT_Card	  */
	public int getUY_TT_Card_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Card_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_LoadSubAgency getUY_TT_LoadSubAgency() throws RuntimeException
    {
		return (I_UY_TT_LoadSubAgency)MTable.get(getCtx(), I_UY_TT_LoadSubAgency.Table_Name)
			.getPO(getUY_TT_LoadSubAgency_ID(), get_TrxName());	}

	/** Set UY_TT_LoadSubAgency.
		@param UY_TT_LoadSubAgency_ID UY_TT_LoadSubAgency	  */
	public void setUY_TT_LoadSubAgency_ID (int UY_TT_LoadSubAgency_ID)
	{
		if (UY_TT_LoadSubAgency_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_LoadSubAgency_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_LoadSubAgency_ID, Integer.valueOf(UY_TT_LoadSubAgency_ID));
	}

	/** Get UY_TT_LoadSubAgency.
		@return UY_TT_LoadSubAgency	  */
	public int getUY_TT_LoadSubAgency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_LoadSubAgency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_LoadSubAgencyLine.
		@param UY_TT_LoadSubAgencyLine_ID UY_TT_LoadSubAgencyLine	  */
	public void setUY_TT_LoadSubAgencyLine_ID (int UY_TT_LoadSubAgencyLine_ID)
	{
		if (UY_TT_LoadSubAgencyLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_LoadSubAgencyLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_LoadSubAgencyLine_ID, Integer.valueOf(UY_TT_LoadSubAgencyLine_ID));
	}

	/** Get UY_TT_LoadSubAgencyLine.
		@return UY_TT_LoadSubAgencyLine	  */
	public int getUY_TT_LoadSubAgencyLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_LoadSubAgencyLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}