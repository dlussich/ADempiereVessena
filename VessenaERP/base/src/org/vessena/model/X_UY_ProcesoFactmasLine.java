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

/** Generated Model for UY_ProcesoFactmasLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_ProcesoFactmasLine extends PO implements I_UY_ProcesoFactmasLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_ProcesoFactmasLine (Properties ctx, int UY_ProcesoFactmasLine_ID, String trxName)
    {
      super (ctx, UY_ProcesoFactmasLine_ID, trxName);
      /** if (UY_ProcesoFactmasLine_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Order_ID (0);
			setdatereserved (new Timestamp( System.currentTimeMillis() ));
			setUY_AsignaTransporteHdr_ID (0);
			setUY_AsignaTransporteLine_ID (0);
			setUY_ProcesoFactmasHdr_ID (0);
			setUY_ProcesoFactmasLine_ID (0);
			setUY_ReservaPedidoHdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ProcesoFactmasLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ProcesoFactmasLine[")
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

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
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

	public I_UY_AsignaTransporteLine getUY_AsignaTransporteLine() throws RuntimeException
    {
		return (I_UY_AsignaTransporteLine)MTable.get(getCtx(), I_UY_AsignaTransporteLine.Table_Name)
			.getPO(getUY_AsignaTransporteLine_ID(), get_TrxName());	}

	/** Set UY_AsignaTransporteLine.
		@param UY_AsignaTransporteLine_ID UY_AsignaTransporteLine	  */
	public void setUY_AsignaTransporteLine_ID (int UY_AsignaTransporteLine_ID)
	{
		if (UY_AsignaTransporteLine_ID < 1) 
			set_Value (COLUMNNAME_UY_AsignaTransporteLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AsignaTransporteLine_ID, Integer.valueOf(UY_AsignaTransporteLine_ID));
	}

	/** Get UY_AsignaTransporteLine.
		@return UY_AsignaTransporteLine	  */
	public int getUY_AsignaTransporteLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AsignaTransporteLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ProcesoFactmasHdr getUY_ProcesoFactmasHdr() throws RuntimeException
    {
		return (I_UY_ProcesoFactmasHdr)MTable.get(getCtx(), I_UY_ProcesoFactmasHdr.Table_Name)
			.getPO(getUY_ProcesoFactmasHdr_ID(), get_TrxName());	}

	/** Set UY_ProcesoFactmasHdr.
		@param UY_ProcesoFactmasHdr_ID UY_ProcesoFactmasHdr	  */
	public void setUY_ProcesoFactmasHdr_ID (int UY_ProcesoFactmasHdr_ID)
	{
		if (UY_ProcesoFactmasHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_ProcesoFactmasHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProcesoFactmasHdr_ID, Integer.valueOf(UY_ProcesoFactmasHdr_ID));
	}

	/** Get UY_ProcesoFactmasHdr.
		@return UY_ProcesoFactmasHdr	  */
	public int getUY_ProcesoFactmasHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProcesoFactmasHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ProcesoFactmasLine.
		@param UY_ProcesoFactmasLine_ID UY_ProcesoFactmasLine	  */
	public void setUY_ProcesoFactmasLine_ID (int UY_ProcesoFactmasLine_ID)
	{
		if (UY_ProcesoFactmasLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ProcesoFactmasLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ProcesoFactmasLine_ID, Integer.valueOf(UY_ProcesoFactmasLine_ID));
	}

	/** Get UY_ProcesoFactmasLine.
		@return UY_ProcesoFactmasLine	  */
	public int getUY_ProcesoFactmasLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProcesoFactmasLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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