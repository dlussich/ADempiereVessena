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

/** Generated Model for UY_ProvisionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ProvisionLine extends PO implements I_UY_ProvisionLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121005L;

    /** Standard Constructor */
    public X_UY_ProvisionLine (Properties ctx, int UY_ProvisionLine_ID, String trxName)
    {
      super (ctx, UY_ProvisionLine_ID, trxName);
      /** if (UY_ProvisionLine_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_ElementValue_ID (0);
			setUY_Provision_ID (0);
			setUY_ProvisionLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ProvisionLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ProvisionLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Source Amount.
		@param AmtSource 
		Amount Balance in Source Currency
	  */
	public void setAmtSource (BigDecimal AmtSource)
	{
		set_Value (COLUMNNAME_AmtSource, AmtSource);
	}

	/** Get Source Amount.
		@return Amount Balance in Source Currency
	  */
	public BigDecimal getAmtSource () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSource);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtSource1.
		@param AmtSource1 
		AmtSource1
	  */
	public void setAmtSource1 (BigDecimal AmtSource1)
	{
		set_Value (COLUMNNAME_AmtSource1, AmtSource1);
	}

	/** Get AmtSource1.
		@return AmtSource1
	  */
	public BigDecimal getAmtSource1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSource1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtSource2.
		@param AmtSource2 
		AmtSource2
	  */
	public void setAmtSource2 (BigDecimal AmtSource2)
	{
		set_Value (COLUMNNAME_AmtSource2, AmtSource2);
	}

	/** Get AmtSource2.
		@return AmtSource2
	  */
	public BigDecimal getAmtSource2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSource2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtSource3.
		@param AmtSource3 
		AmtSource3
	  */
	public void setAmtSource3 (BigDecimal AmtSource3)
	{
		set_Value (COLUMNNAME_AmtSource3, AmtSource3);
	}

	/** Get AmtSource3.
		@return AmtSource3
	  */
	public BigDecimal getAmtSource3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSource3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtSourceAverage.
		@param AmtSourceAverage 
		AmtSourceAverage
	  */
	public void setAmtSourceAverage (BigDecimal AmtSourceAverage)
	{
		set_Value (COLUMNNAME_AmtSourceAverage, AmtSourceAverage);
	}

	/** Get AmtSourceAverage.
		@return AmtSourceAverage
	  */
	public BigDecimal getAmtSourceAverage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceAverage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set C_Activity_ID_1.
		@param C_Activity_ID_1 C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1)
	{
		set_Value (COLUMNNAME_C_Activity_ID_1, Integer.valueOf(C_Activity_ID_1));
	}

	/** Get C_Activity_ID_1.
		@return C_Activity_ID_1	  */
	public int getC_Activity_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Unit Price.
		@param PriceActual 
		Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price 
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtySource.
		@param QtySource QtySource	  */
	public void setQtySource (BigDecimal QtySource)
	{
		set_Value (COLUMNNAME_QtySource, QtySource);
	}

	/** Get QtySource.
		@return QtySource	  */
	public BigDecimal getQtySource () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySource);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtySource1.
		@param QtySource1 QtySource1	  */
	public void setQtySource1 (BigDecimal QtySource1)
	{
		set_Value (COLUMNNAME_QtySource1, QtySource1);
	}

	/** Get QtySource1.
		@return QtySource1	  */
	public BigDecimal getQtySource1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySource1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtySource2.
		@param QtySource2 QtySource2	  */
	public void setQtySource2 (BigDecimal QtySource2)
	{
		set_Value (COLUMNNAME_QtySource2, QtySource2);
	}

	/** Get QtySource2.
		@return QtySource2	  */
	public BigDecimal getQtySource2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySource2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtySource3.
		@param QtySource3 QtySource3	  */
	public void setQtySource3 (BigDecimal QtySource3)
	{
		set_Value (COLUMNNAME_QtySource3, QtySource3);
	}

	/** Get QtySource3.
		@return QtySource3	  */
	public BigDecimal getQtySource3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySource3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtySourceAverage.
		@param QtySourceAverage QtySourceAverage	  */
	public void setQtySourceAverage (BigDecimal QtySourceAverage)
	{
		set_Value (COLUMNNAME_QtySourceAverage, QtySourceAverage);
	}

	/** Get QtySourceAverage.
		@return QtySourceAverage	  */
	public BigDecimal getQtySourceAverage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySourceAverage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set UY_ProvisionLine.
		@param UY_ProvisionLine_ID UY_ProvisionLine	  */
	public void setUY_ProvisionLine_ID (int UY_ProvisionLine_ID)
	{
		if (UY_ProvisionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ProvisionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ProvisionLine_ID, Integer.valueOf(UY_ProvisionLine_ID));
	}

	/** Get UY_ProvisionLine.
		@return UY_ProvisionLine	  */
	public int getUY_ProvisionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProvisionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** VerifiedType AD_Reference_ID=1000190 */
	public static final int VERIFIEDTYPE_AD_Reference_ID=1000190;
	/** Yes = YES */
	public static final String VERIFIEDTYPE_Yes = "YES";
	/** No = NO */
	public static final String VERIFIEDTYPE_No = "NO";
	/** Changed = CHANGED */
	public static final String VERIFIEDTYPE_Changed = "CHANGED";
	/** Set VerifiedType.
		@param VerifiedType VerifiedType	  */
	public void setVerifiedType (String VerifiedType)
	{

		set_Value (COLUMNNAME_VerifiedType, VerifiedType);
	}

	/** Get VerifiedType.
		@return VerifiedType	  */
	public String getVerifiedType () 
	{
		return (String)get_Value(COLUMNNAME_VerifiedType);
	}
}