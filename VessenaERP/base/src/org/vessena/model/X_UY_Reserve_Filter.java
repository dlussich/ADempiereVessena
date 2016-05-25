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

/** Generated Model for UY_Reserve_Filter
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_Reserve_Filter extends PO implements I_UY_Reserve_Filter, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_Reserve_Filter (Properties ctx, int UY_Reserve_Filter_ID, String trxName)
    {
      super (ctx, UY_Reserve_Filter_ID, trxName);
      /** if (UY_Reserve_Filter_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_Reserve_Filter_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Reserve_Filter (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Reserve_Filter[")
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

	public I_C_SalesRegion getC_SalesRegion() throws RuntimeException
    {
		return (I_C_SalesRegion)MTable.get(getCtx(), I_C_SalesRegion.Table_Name)
			.getPO(getC_SalesRegion_ID(), get_TrxName());	}

	/** Set Sales Region.
		@param C_SalesRegion_ID 
		Sales coverage region
	  */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
	}

	/** Get Sales Region.
		@return Sales coverage region
	  */
	public int getC_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SalesRegion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** DocAction AD_Reference_ID=1000047 */
	public static final int DOCACTION_AD_Reference_ID=1000047;
	/** Aplicar = PR */
	public static final String DOCACTION_Aplicar = "PR";
	/** Generar = CO */
	public static final String DOCACTION_Generar = "CO";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** En Proceso = IP */
	public static final String DOCSTATUS_EnProceso = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Asigned = AS */
	public static final String DOCSTATUS_Asigned = "AS";
	/** Requested = RQ */
	public static final String DOCSTATUS_Requested = "RQ";
	/** Recived = RV */
	public static final String DOCSTATUS_Recived = "RV";
	/** Picking = PK */
	public static final String DOCSTATUS_Picking = "PK";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
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

	public I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (I_M_Warehouse)MTable.get(getCtx(), I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set OnlyAvailables.
		@param OnlyAvailables OnlyAvailables	  */
	public void setOnlyAvailables (boolean OnlyAvailables)
	{
		set_Value (COLUMNNAME_OnlyAvailables, Boolean.valueOf(OnlyAvailables));
	}

	/** Get OnlyAvailables.
		@return OnlyAvailables	  */
	public boolean isOnlyAvailables () 
	{
		Object oo = get_Value(COLUMNNAME_OnlyAvailables);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_CanalVentas getUY_CanalVentas() throws RuntimeException
    {
		return (I_UY_CanalVentas)MTable.get(getCtx(), I_UY_CanalVentas.Table_Name)
			.getPO(getUY_CanalVentas_ID(), get_TrxName());	}

	/** Set UY_CanalVentas.
		@param UY_CanalVentas_ID UY_CanalVentas	  */
	public void setUY_CanalVentas_ID (int UY_CanalVentas_ID)
	{
		if (UY_CanalVentas_ID < 1) 
			set_Value (COLUMNNAME_UY_CanalVentas_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CanalVentas_ID, Integer.valueOf(UY_CanalVentas_ID));
	}

	/** Get UY_CanalVentas.
		@return UY_CanalVentas	  */
	public int getUY_CanalVentas_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CanalVentas_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_dateordered_from.
		@param uy_dateordered_from uy_dateordered_from	  */
	public void setuy_dateordered_from (Timestamp uy_dateordered_from)
	{
		set_Value (COLUMNNAME_uy_dateordered_from, uy_dateordered_from);
	}

	/** Get uy_dateordered_from.
		@return uy_dateordered_from	  */
	public Timestamp getuy_dateordered_from () 
	{
		return (Timestamp)get_Value(COLUMNNAME_uy_dateordered_from);
	}

	/** Set uy_dateordered_to.
		@param uy_dateordered_to uy_dateordered_to	  */
	public void setuy_dateordered_to (Timestamp uy_dateordered_to)
	{
		set_Value (COLUMNNAME_uy_dateordered_to, uy_dateordered_to);
	}

	/** Get uy_dateordered_to.
		@return uy_dateordered_to	  */
	public Timestamp getuy_dateordered_to () 
	{
		return (Timestamp)get_Value(COLUMNNAME_uy_dateordered_to);
	}

	/** Set uy_datepromised_from.
		@param uy_datepromised_from uy_datepromised_from	  */
	public void setuy_datepromised_from (Timestamp uy_datepromised_from)
	{
		set_Value (COLUMNNAME_uy_datepromised_from, uy_datepromised_from);
	}

	/** Get uy_datepromised_from.
		@return uy_datepromised_from	  */
	public Timestamp getuy_datepromised_from () 
	{
		return (Timestamp)get_Value(COLUMNNAME_uy_datepromised_from);
	}

	/** Set uy_datepromised_to.
		@param uy_datepromised_to uy_datepromised_to	  */
	public void setuy_datepromised_to (Timestamp uy_datepromised_to)
	{
		set_Value (COLUMNNAME_uy_datepromised_to, uy_datepromised_to);
	}

	/** Get uy_datepromised_to.
		@return uy_datepromised_to	  */
	public Timestamp getuy_datepromised_to () 
	{
		return (Timestamp)get_Value(COLUMNNAME_uy_datepromised_to);
	}

	/** Set uy_nrotrx.
		@param uy_nrotrx uy_nrotrx	  */
	public void setuy_nrotrx (int uy_nrotrx)
	{
		set_Value (COLUMNNAME_uy_nrotrx, Integer.valueOf(uy_nrotrx));
	}

	/** Get uy_nrotrx.
		@return uy_nrotrx	  */
	public int getuy_nrotrx () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_nrotrx);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Reserve_Filter.
		@param UY_Reserve_Filter_ID UY_Reserve_Filter	  */
	public void setUY_Reserve_Filter_ID (int UY_Reserve_Filter_ID)
	{
		if (UY_Reserve_Filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Reserve_Filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Reserve_Filter_ID, Integer.valueOf(UY_Reserve_Filter_ID));
	}

	/** Get UY_Reserve_Filter.
		@return UY_Reserve_Filter	  */
	public int getUY_Reserve_Filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Reserve_Filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_verpedidos.
		@param uy_verpedidos uy_verpedidos	  */
	public void setuy_verpedidos (String uy_verpedidos)
	{
		set_Value (COLUMNNAME_uy_verpedidos, uy_verpedidos);
	}

	/** Get uy_verpedidos.
		@return uy_verpedidos	  */
	public String getuy_verpedidos () 
	{
		return (String)get_Value(COLUMNNAME_uy_verpedidos);
	}

	/** Set uy_zonareparto_filtro1.
		@param uy_zonareparto_filtro1 uy_zonareparto_filtro1	  */
	public void setuy_zonareparto_filtro1 (int uy_zonareparto_filtro1)
	{
		set_Value (COLUMNNAME_uy_zonareparto_filtro1, Integer.valueOf(uy_zonareparto_filtro1));
	}

	/** Get uy_zonareparto_filtro1.
		@return uy_zonareparto_filtro1	  */
	public int getuy_zonareparto_filtro1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_zonareparto_filtro1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}


	/** Set uy_zonareparto_filtro2.
		@param uy_zonareparto_filtro2 uy_zonareparto_filtro2	  */
	public void setuy_zonareparto_filtro2 (int uy_zonareparto_filtro2)
	{
		set_Value (COLUMNNAME_uy_zonareparto_filtro2, Integer.valueOf(uy_zonareparto_filtro2));
	}

	/** Get uy_zonareparto_filtro2.
		@return uy_zonareparto_filtro2	  */
	public int getuy_zonareparto_filtro2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_zonareparto_filtro2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ZonaReparto getuy_zonareparto_filt() throws RuntimeException
    {
		return (I_UY_ZonaReparto)MTable.get(getCtx(), I_UY_ZonaReparto.Table_Name)
			.getPO(getuy_zonareparto_filtro3(), get_TrxName());	}

	/** Set uy_zonareparto_filtro3.
		@param uy_zonareparto_filtro3 uy_zonareparto_filtro3	  */
	public void setuy_zonareparto_filtro3 (int uy_zonareparto_filtro3)
	{
		set_Value (COLUMNNAME_uy_zonareparto_filtro3, Integer.valueOf(uy_zonareparto_filtro3));
	}

	/** Get uy_zonareparto_filtro3.
		@return uy_zonareparto_filtro3	  */
	public int getuy_zonareparto_filtro3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_zonareparto_filtro3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}