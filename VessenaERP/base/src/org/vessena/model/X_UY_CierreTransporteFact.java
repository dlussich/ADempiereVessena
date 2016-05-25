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

/** Generated Model for UY_CierreTransporteFact
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CierreTransporteFact extends PO implements I_UY_CierreTransporteFact, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CierreTransporteFact (Properties ctx, int UY_CierreTransporteFact_ID, String trxName)
    {
      super (ctx, UY_CierreTransporteFact_ID, trxName);
      /** if (UY_CierreTransporteFact_ID == 0)
        {
			setUY_CierreTransporteFact_ID (0);
			setUY_CierreTransporteHdr_ID (0);
			setuy_recibe_conductor (null);
// Y
        } */
    }

    /** Load Constructor */
    public X_UY_CierreTransporteFact (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CierreTransporteFact[")
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

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (I_C_BPartner_Location)MTable.get(getCtx(), I_C_BPartner_Location.Table_Name)
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

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
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

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException
    {
		return (I_UY_AsignaTransporteHdr)MTable.get(getCtx(), I_UY_AsignaTransporteHdr.Table_Name)
			.getPO(getUY_AsignaTransporteHdr_ID(), get_TrxName());	}

	/** Set UY_AsignaTransporteHdr.
		@param UY_AsignaTransporteHdr_ID UY_AsignaTransporteHdr	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID)
	{
		if (UY_AsignaTransporteHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_AsignaTransporteHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AsignaTransporteHdr_ID, Integer.valueOf(UY_AsignaTransporteHdr_ID));
	}

	/** Get UY_AsignaTransporteHdr.
		@return UY_AsignaTransporteHdr	  */
	public int getUY_AsignaTransporteHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AsignaTransporteHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CierreTransporteFact.
		@param UY_CierreTransporteFact_ID UY_CierreTransporteFact	  */
	public void setUY_CierreTransporteFact_ID (int UY_CierreTransporteFact_ID)
	{
		if (UY_CierreTransporteFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CierreTransporteFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CierreTransporteFact_ID, Integer.valueOf(UY_CierreTransporteFact_ID));
	}

	/** Get UY_CierreTransporteFact.
		@return UY_CierreTransporteFact	  */
	public int getUY_CierreTransporteFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CierreTransporteFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CierreTransporteHdr getUY_CierreTransporteHdr() throws RuntimeException
    {
		return (I_UY_CierreTransporteHdr)MTable.get(getCtx(), I_UY_CierreTransporteHdr.Table_Name)
			.getPO(getUY_CierreTransporteHdr_ID(), get_TrxName());	}

	/** Set UY_CierreTransporteHdr.
		@param UY_CierreTransporteHdr_ID UY_CierreTransporteHdr	  */
	public void setUY_CierreTransporteHdr_ID (int UY_CierreTransporteHdr_ID)
	{
		if (UY_CierreTransporteHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_CierreTransporteHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CierreTransporteHdr_ID, Integer.valueOf(UY_CierreTransporteHdr_ID));
	}

	/** Get UY_CierreTransporteHdr.
		@return UY_CierreTransporteHdr	  */
	public int getUY_CierreTransporteHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CierreTransporteHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** uy_recibe_conductor AD_Reference_ID=1000074 */
	public static final int UY_RECIBE_CONDUCTOR_AD_Reference_ID=1000074;
	/** Si = Y */
	public static final String UY_RECIBE_CONDUCTOR_Si = "Y";
	/** No = N */
	public static final String UY_RECIBE_CONDUCTOR_No = "N";
	/** Parcial = P */
	public static final String UY_RECIBE_CONDUCTOR_Parcial = "P";
	/** Set uy_recibe_conductor.
		@param uy_recibe_conductor uy_recibe_conductor	  */
	public void setuy_recibe_conductor (String uy_recibe_conductor)
	{

		set_Value (COLUMNNAME_uy_recibe_conductor, uy_recibe_conductor);
	}

	/** Get uy_recibe_conductor.
		@return uy_recibe_conductor	  */
	public String getuy_recibe_conductor () 
	{
		return (String)get_Value(COLUMNNAME_uy_recibe_conductor);
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
}