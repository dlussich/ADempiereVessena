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

/** Generated Model for UY_Operarios
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_Operarios extends PO implements I_UY_Operarios, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_Operarios (Properties ctx, int UY_Operarios_ID, String trxName)
    {
      super (ctx, UY_Operarios_ID, trxName);
      /** if (UY_Operarios_ID == 0)
        {
			setC_BPartner_ID (0);
			setporcento (0);
			setPP_Order_ID (0);
			setUY_Confirmorderhdr_ID (0);
			setUY_Operarios_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Operarios (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Operarios[")
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

	/** Set Finish Date.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	/** Get Finish Date.
		@return Finish or (planned) completion date
	  */
	public Timestamp getDateFinish () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFinish);
	}

	/** Set DateStart.
		@param DateStart DateStart	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get DateStart.
		@return DateStart	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
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

	/** Set porcento.
		@param porcento porcento	  */
	public void setporcento (int porcento)
	{
		set_Value (COLUMNNAME_porcento, Integer.valueOf(porcento));
	}

	/** Get porcento.
		@return porcento	  */
	public int getporcento () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_porcento);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
    {
		return (org.eevolution.model.I_PP_Order)MTable.get(getCtx(), org.eevolution.model.I_PP_Order.Table_Name)
			.getPO(getPP_Order_ID(), get_TrxName());	}

	/** Set Manufacturing Order.
		@param PP_Order_ID Manufacturing Order	  */
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Manufacturing Order.
		@return Manufacturing Order	  */
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Confirmorderhdr getUY_Confirmorderhdr() throws RuntimeException
    {
		return (I_UY_Confirmorderhdr)MTable.get(getCtx(), I_UY_Confirmorderhdr.Table_Name)
			.getPO(getUY_Confirmorderhdr_ID(), get_TrxName());	}

	/** Set UY_Confirmorderhdr.
		@param UY_Confirmorderhdr_ID UY_Confirmorderhdr	  */
	public void setUY_Confirmorderhdr_ID (int UY_Confirmorderhdr_ID)
	{
		if (UY_Confirmorderhdr_ID < 1) 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Confirmorderhdr_ID, Integer.valueOf(UY_Confirmorderhdr_ID));
	}

	/** Get UY_Confirmorderhdr.
		@return UY_Confirmorderhdr	  */
	public int getUY_Confirmorderhdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Confirmorderhdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Operario_ID.
		@param UY_Operario_ID UY_Operario_ID	  */
	public void setUY_Operario_ID (int UY_Operario_ID)
	{
		if (UY_Operario_ID < 1) 
			set_Value (COLUMNNAME_UY_Operario_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Operario_ID, Integer.valueOf(UY_Operario_ID));
	}

	/** Get UY_Operario_ID.
		@return UY_Operario_ID	  */
	public int getUY_Operario_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Operario_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Operarios.
		@param UY_Operarios_ID Operarios	  */
	public void setUY_Operarios_ID (int UY_Operarios_ID)
	{
		if (UY_Operarios_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Operarios_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Operarios_ID, Integer.valueOf(UY_Operarios_ID));
	}

	/** Get Operarios.
		@return Operarios	  */
	public int getUY_Operarios_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Operarios_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}