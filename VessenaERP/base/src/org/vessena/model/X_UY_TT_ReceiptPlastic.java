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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_ReceiptPlastic
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_ReceiptPlastic extends PO implements I_UY_TT_ReceiptPlastic, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130905L;

    /** Standard Constructor */
    public X_UY_TT_ReceiptPlastic (Properties ctx, int UY_TT_ReceiptPlastic_ID, String trxName)
    {
      super (ctx, UY_TT_ReceiptPlastic_ID, trxName);
      /** if (UY_TT_ReceiptPlastic_ID == 0)
        {
			setCedula (null);
			setDueDate (null);
			setIsTitular (false);
			setName (null);
			setTipoSocio (0);
			setUY_TT_Card_ID (0);
			setUY_TT_CardPlastic_ID (0);
			setUY_TT_Receipt_ID (0);
			setUY_TT_ReceiptPlastic_ID (0);
			setUY_TT_ReceiptScan_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_ReceiptPlastic (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_ReceiptPlastic[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (String DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public String getDueDate () 
	{
		return (String)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsTitular.
		@param IsTitular IsTitular	  */
	public void setIsTitular (boolean IsTitular)
	{
		set_Value (COLUMNNAME_IsTitular, Boolean.valueOf(IsTitular));
	}

	/** Get IsTitular.
		@return IsTitular	  */
	public boolean isTitular () 
	{
		Object oo = get_Value(COLUMNNAME_IsTitular);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set TipoSocio.
		@param TipoSocio TipoSocio	  */
	public void setTipoSocio (int TipoSocio)
	{
		set_Value (COLUMNNAME_TipoSocio, Integer.valueOf(TipoSocio));
	}

	/** Get TipoSocio.
		@return TipoSocio	  */
	public int getTipoSocio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TipoSocio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException
    {
		return (I_UY_TT_Card)MTable.get(getCtx(), I_UY_TT_Card.Table_Name)
			.getPO(getUY_TT_Card_ID(), get_TrxName());	}

	/** Set UY_TT_Card.
		@param UY_TT_Card_ID UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID)
	{
		if (UY_TT_Card_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Card_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Card_ID, Integer.valueOf(UY_TT_Card_ID));
	}

	/** Get UY_TT_Card.
		@return UY_TT_Card	  */
	public int getUY_TT_Card_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Card_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_CardPlastic getUY_TT_CardPlastic() throws RuntimeException
    {
		return (I_UY_TT_CardPlastic)MTable.get(getCtx(), I_UY_TT_CardPlastic.Table_Name)
			.getPO(getUY_TT_CardPlastic_ID(), get_TrxName());	}

	/** Set UY_TT_CardPlastic.
		@param UY_TT_CardPlastic_ID UY_TT_CardPlastic	  */
	public void setUY_TT_CardPlastic_ID (int UY_TT_CardPlastic_ID)
	{
		if (UY_TT_CardPlastic_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_CardPlastic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_CardPlastic_ID, Integer.valueOf(UY_TT_CardPlastic_ID));
	}

	/** Get UY_TT_CardPlastic.
		@return UY_TT_CardPlastic	  */
	public int getUY_TT_CardPlastic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_CardPlastic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Receipt getUY_TT_Receipt() throws RuntimeException
    {
		return (I_UY_TT_Receipt)MTable.get(getCtx(), I_UY_TT_Receipt.Table_Name)
			.getPO(getUY_TT_Receipt_ID(), get_TrxName());	}

	/** Set UY_TT_Receipt.
		@param UY_TT_Receipt_ID UY_TT_Receipt	  */
	public void setUY_TT_Receipt_ID (int UY_TT_Receipt_ID)
	{
		if (UY_TT_Receipt_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Receipt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Receipt_ID, Integer.valueOf(UY_TT_Receipt_ID));
	}

	/** Get UY_TT_Receipt.
		@return UY_TT_Receipt	  */
	public int getUY_TT_Receipt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Receipt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_ReceiptPlastic.
		@param UY_TT_ReceiptPlastic_ID UY_TT_ReceiptPlastic	  */
	public void setUY_TT_ReceiptPlastic_ID (int UY_TT_ReceiptPlastic_ID)
	{
		if (UY_TT_ReceiptPlastic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ReceiptPlastic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ReceiptPlastic_ID, Integer.valueOf(UY_TT_ReceiptPlastic_ID));
	}

	/** Get UY_TT_ReceiptPlastic.
		@return UY_TT_ReceiptPlastic	  */
	public int getUY_TT_ReceiptPlastic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ReceiptPlastic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_ReceiptScan getUY_TT_ReceiptScan() throws RuntimeException
    {
		return (I_UY_TT_ReceiptScan)MTable.get(getCtx(), I_UY_TT_ReceiptScan.Table_Name)
			.getPO(getUY_TT_ReceiptScan_ID(), get_TrxName());	}

	/** Set UY_TT_ReceiptScan.
		@param UY_TT_ReceiptScan_ID UY_TT_ReceiptScan	  */
	public void setUY_TT_ReceiptScan_ID (int UY_TT_ReceiptScan_ID)
	{
		if (UY_TT_ReceiptScan_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_ReceiptScan_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_ReceiptScan_ID, Integer.valueOf(UY_TT_ReceiptScan_ID));
	}

	/** Get UY_TT_ReceiptScan.
		@return UY_TT_ReceiptScan	  */
	public int getUY_TT_ReceiptScan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ReceiptScan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}