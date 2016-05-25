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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_CancelReservationDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CancelReservationDetail extends PO implements I_UY_CancelReservationDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CancelReservationDetail (Properties ctx, int UY_CancelReservationDetail_ID, String trxName)
    {
      super (ctx, UY_CancelReservationDetail_ID, trxName);
      /** if (UY_CancelReservationDetail_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Order_ID (0);
			setdatereserved (new Timestamp( System.currentTimeMillis() ));
			setName (null);
			setSalesRep_ID (0);
			setUY_CancelReservationDetail_ID (0);
			setUY_CancelReservationFilter_ID (0);
			setuy_procesar (false);
			setUY_ReservaPedidoHdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CancelReservationDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CancelReservationDetail[")
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

	public I_C_Order getC_Order() throws RuntimeException
    {
		return (I_C_Order)MTable.get(getCtx(), I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set datereserved.
		@param datereserved datereserved	  */
	public void setdatereserved (Timestamp datereserved)
	{
		set_Value (COLUMNNAME_datereserved, datereserved);
	}

	/** Get datereserved.
		@return datereserved	  */
	public Timestamp getdatereserved () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datereserved);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	public I_AD_User getSalesRep() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CancelReservationDetail.
		@param UY_CancelReservationDetail_ID UY_CancelReservationDetail	  */
	public void setUY_CancelReservationDetail_ID (int UY_CancelReservationDetail_ID)
	{
		if (UY_CancelReservationDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CancelReservationDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CancelReservationDetail_ID, Integer.valueOf(UY_CancelReservationDetail_ID));
	}

	/** Get UY_CancelReservationDetail.
		@return UY_CancelReservationDetail	  */
	public int getUY_CancelReservationDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CancelReservationDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CancelReservationFilter getUY_CancelReservationFilter() throws RuntimeException
    {
		return (I_UY_CancelReservationFilter)MTable.get(getCtx(), I_UY_CancelReservationFilter.Table_Name)
			.getPO(getUY_CancelReservationFilter_ID(), get_TrxName());	}

	/** Set UY_CancelReservationFilter.
		@param UY_CancelReservationFilter_ID UY_CancelReservationFilter	  */
	public void setUY_CancelReservationFilter_ID (int UY_CancelReservationFilter_ID)
	{
		if (UY_CancelReservationFilter_ID < 1) 
			set_Value (COLUMNNAME_UY_CancelReservationFilter_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CancelReservationFilter_ID, Integer.valueOf(UY_CancelReservationFilter_ID));
	}

	/** Get UY_CancelReservationFilter.
		@return UY_CancelReservationFilter	  */
	public int getUY_CancelReservationFilter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CancelReservationFilter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_procesar.
		@param uy_procesar uy_procesar	  */
	public void setuy_procesar (boolean uy_procesar)
	{
		set_Value (COLUMNNAME_uy_procesar, Boolean.valueOf(uy_procesar));
	}

	/** Get uy_procesar.
		@return uy_procesar	  */
	public boolean isuy_procesar () 
	{
		Object oo = get_Value(COLUMNNAME_uy_procesar);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_ReservaPedidoHdr getUY_ReservaPedidoHdr() throws RuntimeException
    {
		return (I_UY_ReservaPedidoHdr)MTable.get(getCtx(), I_UY_ReservaPedidoHdr.Table_Name)
			.getPO(getUY_ReservaPedidoHdr_ID(), get_TrxName());	}

	/** Set UY_ReservaPedidoHdr.
		@param UY_ReservaPedidoHdr_ID UY_ReservaPedidoHdr	  */
	public void setUY_ReservaPedidoHdr_ID (int UY_ReservaPedidoHdr_ID)
	{
		if (UY_ReservaPedidoHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_ReservaPedidoHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ReservaPedidoHdr_ID, Integer.valueOf(UY_ReservaPedidoHdr_ID));
	}

	/** Get UY_ReservaPedidoHdr.
		@return UY_ReservaPedidoHdr	  */
	public int getUY_ReservaPedidoHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReservaPedidoHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ZonaReparto getUY_ZonaReparto() throws RuntimeException
    {
		return (I_UY_ZonaReparto)MTable.get(getCtx(), I_UY_ZonaReparto.Table_Name)
			.getPO(getUY_ZonaReparto_ID(), get_TrxName());	}

	/** Set UY_ZonaReparto.
		@param UY_ZonaReparto_ID UY_ZonaReparto	  */
	public void setUY_ZonaReparto_ID (int UY_ZonaReparto_ID)
	{
		if (UY_ZonaReparto_ID < 1) 
			set_Value (COLUMNNAME_UY_ZonaReparto_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ZonaReparto_ID, Integer.valueOf(UY_ZonaReparto_ID));
	}

	/** Get UY_ZonaReparto.
		@return UY_ZonaReparto	  */
	public int getUY_ZonaReparto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ZonaReparto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}