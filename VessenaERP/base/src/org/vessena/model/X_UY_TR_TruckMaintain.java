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

/** Generated Model for UY_TR_TruckMaintain
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_TruckMaintain extends PO implements I_UY_TR_TruckMaintain, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150520L;

    /** Standard Constructor */
    public X_UY_TR_TruckMaintain (Properties ctx, int UY_TR_TruckMaintain_ID, String trxName)
    {
      super (ctx, UY_TR_TruckMaintain_ID, trxName);
      /** if (UY_TR_TruckMaintain_ID == 0)
        {
			setIsExpired (false);
// N
			setKilometros (0);
			setUY_TR_Maintain_ID (0);
			setUY_TR_Truck_ID (0);
			setUY_TR_TruckMaintain_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_TruckMaintain (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_TruckMaintain[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DateLast.
		@param DateLast DateLast	  */
	public void setDateLast (Timestamp DateLast)
	{
		set_Value (COLUMNNAME_DateLast, DateLast);
	}

	/** Get DateLast.
		@return DateLast	  */
	public Timestamp getDateLast () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLast);
	}

	/** Set Difference.
		@param DifferenceQty 
		Difference Quantity
	  */
	public void setDifferenceQty (int DifferenceQty)
	{
		throw new IllegalArgumentException ("DifferenceQty is virtual column");	}

	/** Get Difference.
		@return Difference Quantity
	  */
	public int getDifferenceQty () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DifferenceQty);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IsExpired.
		@param IsExpired IsExpired	  */
	public void setIsExpired (boolean IsExpired)
	{
		set_Value (COLUMNNAME_IsExpired, Boolean.valueOf(IsExpired));
	}

	/** Get IsExpired.
		@return IsExpired	  */
	public boolean isExpired () 
	{
		Object oo = get_Value(COLUMNNAME_IsExpired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kilometros.
		@param Kilometros Kilometros	  */
	public void setKilometros (int Kilometros)
	{
		set_Value (COLUMNNAME_Kilometros, Integer.valueOf(Kilometros));
	}

	/** Get Kilometros.
		@return Kilometros	  */
	public int getKilometros () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Kilometros);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	/** Set QtyKm.
		@param QtyKm 
		QtyKm
	  */
	public void setQtyKm (int QtyKm)
	{
		set_Value (COLUMNNAME_QtyKm, Integer.valueOf(QtyKm));
	}

	/** Get QtyKm.
		@return QtyKm
	  */
	public int getQtyKm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKmLast.
		@param QtyKmLast QtyKmLast	  */
	public void setQtyKmLast (int QtyKmLast)
	{
		set_Value (COLUMNNAME_QtyKmLast, Integer.valueOf(QtyKmLast));
	}

	/** Get QtyKmLast.
		@return QtyKmLast	  */
	public int getQtyKmLast () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKmLast);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKmNext.
		@param QtyKmNext QtyKmNext	  */
	public void setQtyKmNext (int QtyKmNext)
	{
		set_Value (COLUMNNAME_QtyKmNext, Integer.valueOf(QtyKmNext));
	}

	/** Get QtyKmNext.
		@return QtyKmNext	  */
	public int getQtyKmNext () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKmNext);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Maintain getUY_TR_Maintain() throws RuntimeException
    {
		return (I_UY_TR_Maintain)MTable.get(getCtx(), I_UY_TR_Maintain.Table_Name)
			.getPO(getUY_TR_Maintain_ID(), get_TrxName());	}

	/** Set UY_TR_Maintain.
		@param UY_TR_Maintain_ID UY_TR_Maintain	  */
	public void setUY_TR_Maintain_ID (int UY_TR_Maintain_ID)
	{
		if (UY_TR_Maintain_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Maintain_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Maintain_ID, Integer.valueOf(UY_TR_Maintain_ID));
	}

	/** Get UY_TR_Maintain.
		@return UY_TR_Maintain	  */
	public int getUY_TR_Maintain_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Maintain_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException
    {
		return (I_UY_TR_Truck)MTable.get(getCtx(), I_UY_TR_Truck.Table_Name)
			.getPO(getUY_TR_Truck_ID(), get_TrxName());	}

	/** Set UY_TR_Truck.
		@param UY_TR_Truck_ID UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID)
	{
		if (UY_TR_Truck_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, Integer.valueOf(UY_TR_Truck_ID));
	}

	/** Get UY_TR_Truck.
		@return UY_TR_Truck	  */
	public int getUY_TR_Truck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TruckMaintain.
		@param UY_TR_TruckMaintain_ID UY_TR_TruckMaintain	  */
	public void setUY_TR_TruckMaintain_ID (int UY_TR_TruckMaintain_ID)
	{
		if (UY_TR_TruckMaintain_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TruckMaintain_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TruckMaintain_ID, Integer.valueOf(UY_TR_TruckMaintain_ID));
	}

	/** Get UY_TR_TruckMaintain.
		@return UY_TR_TruckMaintain	  */
	public int getUY_TR_TruckMaintain_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckMaintain_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}