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

/** Generated Model for UY_CheckBookNotUsed
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CheckBookNotUsed extends PO implements I_UY_CheckBookNotUsed, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CheckBookNotUsed (Properties ctx, int UY_CheckBookNotUsed_ID, String trxName)
    {
      super (ctx, UY_CheckBookNotUsed_ID, trxName);
      /** if (UY_CheckBookNotUsed_ID == 0)
        {
			setUY_CheckBookControl_ID (0);
			setUY_CheckBookNotUsed_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CheckBookNotUsed (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CheckBookNotUsed[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
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

	/** Set DocumentNoNotUsed.
		@param DocumentNoNotUsed DocumentNoNotUsed	  */
	public void setDocumentNoNotUsed (int DocumentNoNotUsed)
	{
		set_Value (COLUMNNAME_DocumentNoNotUsed, Integer.valueOf(DocumentNoNotUsed));
	}

	/** Get DocumentNoNotUsed.
		@return DocumentNoNotUsed	  */
	public int getDocumentNoNotUsed () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentNoNotUsed);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CheckBookControl getUY_CheckBookControl() throws RuntimeException
    {
		return (I_UY_CheckBookControl)MTable.get(getCtx(), I_UY_CheckBookControl.Table_Name)
			.getPO(getUY_CheckBookControl_ID(), get_TrxName());	}

	/** Set UY_CheckBookControl.
		@param UY_CheckBookControl_ID UY_CheckBookControl	  */
	public void setUY_CheckBookControl_ID (int UY_CheckBookControl_ID)
	{
		if (UY_CheckBookControl_ID < 1) 
			set_Value (COLUMNNAME_UY_CheckBookControl_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CheckBookControl_ID, Integer.valueOf(UY_CheckBookControl_ID));
	}

	/** Get UY_CheckBookControl.
		@return UY_CheckBookControl	  */
	public int getUY_CheckBookControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckBookControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CheckBookNotUsed.
		@param UY_CheckBookNotUsed_ID UY_CheckBookNotUsed	  */
	public void setUY_CheckBookNotUsed_ID (int UY_CheckBookNotUsed_ID)
	{
		if (UY_CheckBookNotUsed_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CheckBookNotUsed_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CheckBookNotUsed_ID, Integer.valueOf(UY_CheckBookNotUsed_ID));
	}

	/** Get UY_CheckBookNotUsed.
		@return UY_CheckBookNotUsed	  */
	public int getUY_CheckBookNotUsed_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckBookNotUsed_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException
    {
		return (I_UY_MediosPago)MTable.get(getCtx(), I_UY_MediosPago.Table_Name)
			.getPO(getUY_MediosPago_ID(), get_TrxName());	}

	/** Set Medios de Pago.
		@param UY_MediosPago_ID Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID)
	{
		if (UY_MediosPago_ID < 1) 
			set_Value (COLUMNNAME_UY_MediosPago_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MediosPago_ID, Integer.valueOf(UY_MediosPago_ID));
	}

	/** Get Medios de Pago.
		@return Medios de Pago	  */
	public int getUY_MediosPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MediosPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}