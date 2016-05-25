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

/** Generated Model for UY_ProvisionPartner
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ProvisionPartner extends PO implements I_UY_ProvisionPartner, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130314L;

    /** Standard Constructor */
    public X_UY_ProvisionPartner (Properties ctx, int UY_ProvisionPartner_ID, String trxName)
    {
      super (ctx, UY_ProvisionPartner_ID, trxName);
      /** if (UY_ProvisionPartner_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_Provision_ID (0);
			setUY_ProvisionPartner_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ProvisionPartner (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ProvisionPartner[")
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

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_ExtProvision getUY_ExtProvision() throws RuntimeException
    {
		return (I_UY_ExtProvision)MTable.get(getCtx(), I_UY_ExtProvision.Table_Name)
			.getPO(getUY_ExtProvision_ID(), get_TrxName());	}

	/** Set UY_ExtProvision.
		@param UY_ExtProvision_ID UY_ExtProvision	  */
	public void setUY_ExtProvision_ID (int UY_ExtProvision_ID)
	{
		if (UY_ExtProvision_ID < 1) 
			set_Value (COLUMNNAME_UY_ExtProvision_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ExtProvision_ID, Integer.valueOf(UY_ExtProvision_ID));
	}

	/** Get UY_ExtProvision.
		@return UY_ExtProvision	  */
	public int getUY_ExtProvision_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ExtProvision_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Provision getUY_Provision() throws RuntimeException
    {
		return (I_UY_Provision)MTable.get(getCtx(), I_UY_Provision.Table_Name)
			.getPO(getUY_Provision_ID(), get_TrxName());	}

	/** Set UY_Provision.
		@param UY_Provision_ID UY_Provision	  */
	public void setUY_Provision_ID (int UY_Provision_ID)
	{
		if (UY_Provision_ID < 1) 
			set_Value (COLUMNNAME_UY_Provision_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Provision_ID, Integer.valueOf(UY_Provision_ID));
	}

	/** Get UY_Provision.
		@return UY_Provision	  */
	public int getUY_Provision_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Provision_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ProvisionPartner.
		@param UY_ProvisionPartner_ID UY_ProvisionPartner	  */
	public void setUY_ProvisionPartner_ID (int UY_ProvisionPartner_ID)
	{
		if (UY_ProvisionPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ProvisionPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ProvisionPartner_ID, Integer.valueOf(UY_ProvisionPartner_ID));
	}

	/** Get UY_ProvisionPartner.
		@return UY_ProvisionPartner	  */
	public int getUY_ProvisionPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProvisionPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}