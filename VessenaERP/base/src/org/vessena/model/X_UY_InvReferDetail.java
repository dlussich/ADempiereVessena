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

/** Generated Model for UY_InvReferDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_InvReferDetail extends PO implements I_UY_InvReferDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_InvReferDetail (Properties ctx, int UY_InvReferDetail_ID, String trxName)
    {
      super (ctx, UY_InvReferDetail_ID, trxName);
      /** if (UY_InvReferDetail_ID == 0)
        {
			setbultos (0);
			setC_BPartner_ID (0);
			setC_DocType_ID (0);
			setC_Invoice_ID (0);
			setDateInvoiced (new Timestamp( System.currentTimeMillis() ));
			setUY_AsignaTransporteHdr_ID (0);
			setUY_InvReferDetail_ID (0);
			setUY_InvReferFilter_ID (0);
			setuy_precio (null);
// PED
			setuy_procesar (true);
// Y
			setUY_ReservaPedidoHdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvReferDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvReferDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set bultos.
		@param bultos bultos	  */
	public void setbultos (int bultos)
	{
		set_Value (COLUMNNAME_bultos, Integer.valueOf(bultos));
	}

	/** Get bultos.
		@return bultos	  */
	public int getbultos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_bultos);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
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

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException
    {
		return (I_UY_AsignaTransporteHdr)MTable.get(getCtx(), I_UY_AsignaTransporteHdr.Table_Name)
			.getPO(getUY_AsignaTransporteHdr_ID(), get_TrxName());	}

	/** Set UY_AsignaTransporteHdr_ID.
		@param UY_AsignaTransporteHdr_ID UY_AsignaTransporteHdr_ID	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID)
	{
		if (UY_AsignaTransporteHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_AsignaTransporteHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AsignaTransporteHdr_ID, Integer.valueOf(UY_AsignaTransporteHdr_ID));
	}

	/** Get UY_AsignaTransporteHdr_ID.
		@return UY_AsignaTransporteHdr_ID	  */
	public int getUY_AsignaTransporteHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AsignaTransporteHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_InvReferDetail.
		@param UY_InvReferDetail_ID UY_InvReferDetail	  */
	public void setUY_InvReferDetail_ID (int UY_InvReferDetail_ID)
	{
		if (UY_InvReferDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvReferDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvReferDetail_ID, Integer.valueOf(UY_InvReferDetail_ID));
	}

	/** Get UY_InvReferDetail.
		@return UY_InvReferDetail	  */
	public int getUY_InvReferDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvReferDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_InvReferFilter getUY_InvReferFilter() throws RuntimeException
    {
		return (I_UY_InvReferFilter)MTable.get(getCtx(), I_UY_InvReferFilter.Table_Name)
			.getPO(getUY_InvReferFilter_ID(), get_TrxName());	}

	/** Set UY_InvReferFilter.
		@param UY_InvReferFilter_ID UY_InvReferFilter	  */
	public void setUY_InvReferFilter_ID (int UY_InvReferFilter_ID)
	{
		if (UY_InvReferFilter_ID < 1) 
			set_Value (COLUMNNAME_UY_InvReferFilter_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvReferFilter_ID, Integer.valueOf(UY_InvReferFilter_ID));
	}

	/** Get UY_InvReferFilter.
		@return UY_InvReferFilter	  */
	public int getUY_InvReferFilter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvReferFilter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** uy_precio AD_Reference_ID=1000163 */
	public static final int UY_PRECIO_AD_Reference_ID=1000163;
	/** Precio Pedido = PED */
	public static final String UY_PRECIO_PrecioPedido = "PED";
	/** Precio Actual = ACT */
	public static final String UY_PRECIO_PrecioActual = "ACT";
	/** Set uy_precio.
		@param uy_precio uy_precio	  */
	public void setuy_precio (String uy_precio)
	{

		set_Value (COLUMNNAME_uy_precio, uy_precio);
	}

	/** Get uy_precio.
		@return uy_precio	  */
	public String getuy_precio () 
	{
		return (String)get_Value(COLUMNNAME_uy_precio);
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
}