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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TR_TruckType
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_TruckType extends PO implements I_UY_TR_TruckType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141208L;

    /** Standard Constructor */
    public X_UY_TR_TruckType (Properties ctx, int UY_TR_TruckType_ID, String trxName)
    {
      super (ctx, UY_TR_TruckType_ID, trxName);
      /** if (UY_TR_TruckType_ID == 0)
        {
			setAxisType (null);
			setIsAssociated (false);
// N
			setIsContainer (false);
// N
			setLocatorValue (0);
			setName (null);
			setQty (Env.ZERO);
			setQtyAux (0);
			setQtyAxis (0);
			setTruckGroup (null);
			setUY_TR_TruckType_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_TruckType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_TruckType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AxisDistance.
		@param AxisDistance AxisDistance	  */
	public void setAxisDistance (String AxisDistance)
	{
		set_Value (COLUMNNAME_AxisDistance, AxisDistance);
	}

	/** Get AxisDistance.
		@return AxisDistance	  */
	public String getAxisDistance () 
	{
		return (String)get_Value(COLUMNNAME_AxisDistance);
	}

	/** AxisType AD_Reference_ID=1000376 */
	public static final int AXISTYPE_AD_Reference_ID=1000376;
	/** SIMPLE = simple */
	public static final String AXISTYPE_SIMPLE = "simple";
	/** DOBLE = doble */
	public static final String AXISTYPE_DOBLE = "doble";
	/** TRIPLE = triple */
	public static final String AXISTYPE_TRIPLE = "triple";
	/** Set AxisType.
		@param AxisType 
		AxisType
	  */
	public void setAxisType (String AxisType)
	{

		set_Value (COLUMNNAME_AxisType, AxisType);
	}

	/** Get AxisType.
		@return AxisType
	  */
	public String getAxisType () 
	{
		return (String)get_Value(COLUMNNAME_AxisType);
	}

	/** Set AxisWeight.
		@param AxisWeight AxisWeight	  */
	public void setAxisWeight (BigDecimal AxisWeight)
	{
		set_Value (COLUMNNAME_AxisWeight, AxisWeight);
	}

	/** Get AxisWeight.
		@return AxisWeight	  */
	public BigDecimal getAxisWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AxisWeight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set IsAssociated.
		@param IsAssociated IsAssociated	  */
	public void setIsAssociated (boolean IsAssociated)
	{
		set_Value (COLUMNNAME_IsAssociated, Boolean.valueOf(IsAssociated));
	}

	/** Get IsAssociated.
		@return IsAssociated	  */
	public boolean isAssociated () 
	{
		Object oo = get_Value(COLUMNNAME_IsAssociated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsContainer.
		@param IsContainer IsContainer	  */
	public void setIsContainer (boolean IsContainer)
	{
		set_Value (COLUMNNAME_IsContainer, Boolean.valueOf(IsContainer));
	}

	/** Get IsContainer.
		@return IsContainer	  */
	public boolean isContainer () 
	{
		Object oo = get_Value(COLUMNNAME_IsContainer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, Integer.valueOf(LocatorValue));
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public int getLocatorValue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LocatorValue);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyAux.
		@param QtyAux 
		QtyAux
	  */
	public void setQtyAux (int QtyAux)
	{
		set_Value (COLUMNNAME_QtyAux, Integer.valueOf(QtyAux));
	}

	/** Get QtyAux.
		@return QtyAux
	  */
	public int getQtyAux () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyAux);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyAxis.
		@param QtyAxis 
		QtyAxis
	  */
	public void setQtyAxis (int QtyAxis)
	{
		set_Value (COLUMNNAME_QtyAxis, Integer.valueOf(QtyAxis));
	}

	/** Get QtyAxis.
		@return QtyAxis
	  */
	public int getQtyAxis () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyAxis);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyLong.
		@param QtyLong QtyLong	  */
	public void setQtyLong (BigDecimal QtyLong)
	{
		set_Value (COLUMNNAME_QtyLong, QtyLong);
	}

	/** Get QtyLong.
		@return QtyLong	  */
	public BigDecimal getQtyLong () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyLong);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Traction AD_Reference_ID=1000377 */
	public static final int TRACTION_AD_Reference_ID=1000377;
	/** 6x2 = 6x2 */
	public static final String TRACTION_6x2 = "6x2";
	/** 6x4 = 6x4 */
	public static final String TRACTION_6x4 = "6x4";
	/** Set Traction.
		@param Traction Traction	  */
	public void setTraction (String Traction)
	{

		set_Value (COLUMNNAME_Traction, Traction);
	}

	/** Get Traction.
		@return Traction	  */
	public String getTraction () 
	{
		return (String)get_Value(COLUMNNAME_Traction);
	}

	/** TruckGroup AD_Reference_ID=1000378 */
	public static final int TRUCKGROUP_AD_Reference_ID=1000378;
	/** TRACTOR = TRACTOR */
	public static final String TRUCKGROUP_TRACTOR = "TRACTOR";
	/** REMOLQUE = REMOLQUE */
	public static final String TRUCKGROUP_REMOLQUE = "REMOLQUE";
	/** CAMION = CAMION */
	public static final String TRUCKGROUP_CAMION = "CAMION";
	/** ZORRA = ZORRA */
	public static final String TRUCKGROUP_ZORRA = "ZORRA";
	/** SIDER = SIDER */
	public static final String TRUCKGROUP_SIDER = "SIDER";
	/** OTRO = OTRO */
	public static final String TRUCKGROUP_OTRO = "OTRO";
	/** SEMIRREMOLQUE = SEMIRREMOLQUE */
	public static final String TRUCKGROUP_SEMIRREMOLQUE = "SEMIRREMOLQUE";
	/** Set TruckGroup.
		@param TruckGroup TruckGroup	  */
	public void setTruckGroup (String TruckGroup)
	{

		set_Value (COLUMNNAME_TruckGroup, TruckGroup);
	}

	/** Get TruckGroup.
		@return TruckGroup	  */
	public String getTruckGroup () 
	{
		return (String)get_Value(COLUMNNAME_TruckGroup);
	}

	/** Set UY_TR_TruckType.
		@param UY_TR_TruckType_ID UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID)
	{
		if (UY_TR_TruckType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TruckType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TruckType_ID, Integer.valueOf(UY_TR_TruckType_ID));
	}

	/** Get UY_TR_TruckType.
		@return UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID);
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

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}