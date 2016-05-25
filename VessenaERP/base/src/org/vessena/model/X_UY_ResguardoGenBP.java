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

/** Generated Model for UY_ResguardoGenBP
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ResguardoGenBP extends PO implements I_UY_ResguardoGenBP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151207L;

    /** Standard Constructor */
    public X_UY_ResguardoGenBP (Properties ctx, int UY_ResguardoGenBP_ID, String trxName)
    {
      super (ctx, UY_ResguardoGenBP_ID, trxName);
      /** if (UY_ResguardoGenBP_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_ResguardoGenBP_ID (0);
			setUY_ResguardoGen_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ResguardoGenBP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ResguardoGenBP[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set UY_ResguardoGenBP.
		@param UY_ResguardoGenBP_ID UY_ResguardoGenBP	  */
	public void setUY_ResguardoGenBP_ID (int UY_ResguardoGenBP_ID)
	{
		if (UY_ResguardoGenBP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ResguardoGenBP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ResguardoGenBP_ID, Integer.valueOf(UY_ResguardoGenBP_ID));
	}

	/** Get UY_ResguardoGenBP.
		@return UY_ResguardoGenBP	  */
	public int getUY_ResguardoGenBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ResguardoGenBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ResguardoGen getUY_ResguardoGen() throws RuntimeException
    {
		return (I_UY_ResguardoGen)MTable.get(getCtx(), I_UY_ResguardoGen.Table_Name)
			.getPO(getUY_ResguardoGen_ID(), get_TrxName());	}

	/** Set UY_ResguardoGen.
		@param UY_ResguardoGen_ID UY_ResguardoGen	  */
	public void setUY_ResguardoGen_ID (int UY_ResguardoGen_ID)
	{
		if (UY_ResguardoGen_ID < 1) 
			set_Value (COLUMNNAME_UY_ResguardoGen_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ResguardoGen_ID, Integer.valueOf(UY_ResguardoGen_ID));
	}

	/** Get UY_ResguardoGen.
		@return UY_ResguardoGen	  */
	public int getUY_ResguardoGen_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ResguardoGen_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}