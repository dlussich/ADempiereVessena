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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_StockStatusByDocHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_StockStatusByDocHdr extends PO implements I_UY_StockStatusByDocHdr, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140820L;

    /** Standard Constructor */
    public X_UY_StockStatusByDocHdr (Properties ctx, int UY_StockStatusByDocHdr_ID, String trxName)
    {
      super (ctx, UY_StockStatusByDocHdr_ID, trxName);
      /** if (UY_StockStatusByDocHdr_ID == 0)
        {
			setC_DocType_ID (0);
			setConsumeTypeAdd (null);
// NONE
			setConsumeTypeSub (null);
// NONE
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_StockStatusByDocHdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_StockStatusByDocHdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StockStatusByDocHdr[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set c_doctype_affected_id.
		@param c_doctype_affected_id c_doctype_affected_id	  */
	public void setc_doctype_affected_id (BigDecimal c_doctype_affected_id)
	{
		set_Value (COLUMNNAME_c_doctype_affected_id, c_doctype_affected_id);
	}

	/** Get c_doctype_affected_id.
		@return c_doctype_affected_id	  */
	public BigDecimal getc_doctype_affected_id () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_c_doctype_affected_id);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
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

	/** ConsumeTypeAdd AD_Reference_ID=1000364 */
	public static final int CONSUMETYPEADD_AD_Reference_ID=1000364;
	/** NINGUNO = NONE */
	public static final String CONSUMETYPEADD_NINGUNO = "NONE";
	/** GENERA NUEVA PARTIDA = NEW */
	public static final String CONSUMETYPEADD_GENERANUEVAPARTIDA = "NEW";
	/** CONSUME PARTIDA FIFO = CONSUME_FIFO */
	public static final String CONSUMETYPEADD_CONSUMEPARTIDAFIFO = "CONSUME_FIFO";
	/** NUEVA PARTIDA CONTRA CONSUMO FIFO = NEW_BY_CONSUME */
	public static final String CONSUMETYPEADD_NUEVAPARTIDACONTRACONSUMOFIFO = "NEW_BY_CONSUME";
	/** Set ConsumeTypeAdd.
		@param ConsumeTypeAdd 
		ConsumeTypeAdd
	  */
	public void setConsumeTypeAdd (String ConsumeTypeAdd)
	{

		set_Value (COLUMNNAME_ConsumeTypeAdd, ConsumeTypeAdd);
	}

	/** Get ConsumeTypeAdd.
		@return ConsumeTypeAdd
	  */
	public String getConsumeTypeAdd () 
	{
		return (String)get_Value(COLUMNNAME_ConsumeTypeAdd);
	}

	/** ConsumeTypeSub AD_Reference_ID=1000364 */
	public static final int CONSUMETYPESUB_AD_Reference_ID=1000364;
	/** NINGUNO = NONE */
	public static final String CONSUMETYPESUB_NINGUNO = "NONE";
	/** GENERA NUEVA PARTIDA = NEW */
	public static final String CONSUMETYPESUB_GENERANUEVAPARTIDA = "NEW";
	/** CONSUME PARTIDA FIFO = CONSUME_FIFO */
	public static final String CONSUMETYPESUB_CONSUMEPARTIDAFIFO = "CONSUME_FIFO";
	/** NUEVA PARTIDA CONTRA CONSUMO FIFO = NEW_BY_CONSUME */
	public static final String CONSUMETYPESUB_NUEVAPARTIDACONTRACONSUMOFIFO = "NEW_BY_CONSUME";
	/** Set ConsumeTypeSub.
		@param ConsumeTypeSub ConsumeTypeSub	  */
	public void setConsumeTypeSub (String ConsumeTypeSub)
	{

		set_Value (COLUMNNAME_ConsumeTypeSub, ConsumeTypeSub);
	}

	/** Get ConsumeTypeSub.
		@return ConsumeTypeSub	  */
	public String getConsumeTypeSub () 
	{
		return (String)get_Value(COLUMNNAME_ConsumeTypeSub);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** MovementType AD_Reference_ID=1000107 */
	public static final int MOVEMENTTYPE_AD_Reference_ID=1000107;
	/** Comercial - Clientes = COM */
	public static final String MOVEMENTTYPE_Comercial_Clientes = "COM";
	/** Compras - Proveedores = CPR */
	public static final String MOVEMENTTYPE_Compras_Proveedores = "CPR";
	/** Inventario Fisico = INV */
	public static final String MOVEMENTTYPE_InventarioFisico = "INV";
	/** Movimientos Fisicos = MVF */
	public static final String MOVEMENTTYPE_MovimientosFisicos = "MVF";
	/** Produccion = PRD */
	public static final String MOVEMENTTYPE_Produccion = "PRD";
	/** Set Movement Type.
		@param MovementType 
		Method of moving the inventory
	  */
	public void setMovementType (String MovementType)
	{

		set_Value (COLUMNNAME_MovementType, MovementType);
	}

	/** Get Movement Type.
		@return Method of moving the inventory
	  */
	public String getMovementType () 
	{
		return (String)get_Value(COLUMNNAME_MovementType);
	}

	/** Set UY_StockStatusByDocHdr.
		@param UY_StockStatusByDocHdr_ID UY_StockStatusByDocHdr	  */
	public void setUY_StockStatusByDocHdr_ID (int UY_StockStatusByDocHdr_ID)
	{
		if (UY_StockStatusByDocHdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StockStatusByDocHdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StockStatusByDocHdr_ID, Integer.valueOf(UY_StockStatusByDocHdr_ID));
	}

	/** Get UY_StockStatusByDocHdr.
		@return UY_StockStatusByDocHdr	  */
	public int getUY_StockStatusByDocHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockStatusByDocHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_StockStatusListHdr getUY_StockStatusListHdr() throws RuntimeException
    {
		return (I_UY_StockStatusListHdr)MTable.get(getCtx(), I_UY_StockStatusListHdr.Table_Name)
			.getPO(getUY_StockStatusListHdr_ID(), get_TrxName());	}

	/** Set UY_StockStatusListHdr.
		@param UY_StockStatusListHdr_ID UY_StockStatusListHdr	  */
	public void setUY_StockStatusListHdr_ID (int UY_StockStatusListHdr_ID)
	{
		if (UY_StockStatusListHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_StockStatusListHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StockStatusListHdr_ID, Integer.valueOf(UY_StockStatusListHdr_ID));
	}

	/** Get UY_StockStatusListHdr.
		@return UY_StockStatusListHdr	  */
	public int getUY_StockStatusListHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockStatusListHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}