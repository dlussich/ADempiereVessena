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

/** Generated Model for UY_RT_InterfaceBP
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_InterfaceBP extends PO implements I_UY_RT_InterfaceBP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151009L;

    /** Standard Constructor */
    public X_UY_RT_InterfaceBP (Properties ctx, int UY_RT_InterfaceBP_ID, String trxName)
    {
      super (ctx, UY_RT_InterfaceBP_ID, trxName);
      /** if (UY_RT_InterfaceBP_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_RT_Action_ID (0);
			setUY_RT_InterfaceBP_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_InterfaceBP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_InterfaceBP[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set attr_1.
		@param attr_1 attr_1	  */
	public void setattr_1 (int attr_1)
	{
		set_Value (COLUMNNAME_attr_1, Integer.valueOf(attr_1));
	}

	/** Get attr_1.
		@return attr_1	  */
	public int getattr_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_2.
		@param attr_2 attr_2	  */
	public void setattr_2 (int attr_2)
	{
		set_Value (COLUMNNAME_attr_2, Integer.valueOf(attr_2));
	}

	/** Get attr_2.
		@return attr_2	  */
	public int getattr_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set attr_3.
		@param attr_3 attr_3	  */
	public void setattr_3 (int attr_3)
	{
		set_Value (COLUMNNAME_attr_3, Integer.valueOf(attr_3));
	}

	/** Get attr_3.
		@return attr_3	  */
	public int getattr_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_attr_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set bpcode.
		@param bpcode bpcode	  */
	public void setbpcode (String bpcode)
	{
		set_Value (COLUMNNAME_bpcode, bpcode);
	}

	/** Get bpcode.
		@return bpcode	  */
	public String getbpcode () 
	{
		return (String)get_Value(COLUMNNAME_bpcode);
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigotipocliente.
		@param codigotipocliente codigotipocliente	  */
	public void setcodigotipocliente (int codigotipocliente)
	{
		set_Value (COLUMNNAME_codigotipocliente, Integer.valueOf(codigotipocliente));
	}

	/** Get codigotipocliente.
		@return codigotipocliente	  */
	public int getcodigotipocliente () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigotipocliente);
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

	/** Set Processing date.
		@param ProcessingDate Processing date	  */
	public void setProcessingDate (Timestamp ProcessingDate)
	{
		set_Value (COLUMNNAME_ProcessingDate, ProcessingDate);
	}

	/** Get Processing date.
		@return Processing date	  */
	public Timestamp getProcessingDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ProcessingDate);
	}

	/** Set ReadingDate.
		@param ReadingDate ReadingDate	  */
	public void setReadingDate (Timestamp ReadingDate)
	{
		set_Value (COLUMNNAME_ReadingDate, ReadingDate);
	}

	/** Get ReadingDate.
		@return ReadingDate	  */
	public Timestamp getReadingDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ReadingDate);
	}

	public I_UY_RT_Action getUY_RT_Action() throws RuntimeException
    {
		return (I_UY_RT_Action)MTable.get(getCtx(), I_UY_RT_Action.Table_Name)
			.getPO(getUY_RT_Action_ID(), get_TrxName());	}

	/** Set UY_RT_Action.
		@param UY_RT_Action_ID UY_RT_Action	  */
	public void setUY_RT_Action_ID (int UY_RT_Action_ID)
	{
		if (UY_RT_Action_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_Action_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_Action_ID, Integer.valueOf(UY_RT_Action_ID));
	}

	/** Get UY_RT_Action.
		@return UY_RT_Action	  */
	public int getUY_RT_Action_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Action_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RT_InterfaceBP.
		@param UY_RT_InterfaceBP_ID UY_RT_InterfaceBP	  */
	public void setUY_RT_InterfaceBP_ID (int UY_RT_InterfaceBP_ID)
	{
		if (UY_RT_InterfaceBP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InterfaceBP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InterfaceBP_ID, Integer.valueOf(UY_RT_InterfaceBP_ID));
	}

	/** Get UY_RT_InterfaceBP.
		@return UY_RT_InterfaceBP	  */
	public int getUY_RT_InterfaceBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_InterfaceBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}