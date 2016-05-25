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

/** Generated Model for UY_TR_LoadStatus
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_LoadStatus extends PO implements I_UY_TR_LoadStatus, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141223L;

    /** Standard Constructor */
    public X_UY_TR_LoadStatus (Properties ctx, int UY_TR_LoadStatus_ID, String trxName)
    {
      super (ctx, UY_TR_LoadStatus_ID, trxName);
      /** if (UY_TR_LoadStatus_ID == 0)
        {
			setChangeDriver (false);
// N
			setChangeTruck (false);
// N
			setEndTrackStatus (false);
// N
			setHandleCRT (true);
// Y
			setIsBeforeLoad (false);
// N
			setIsConfirmation (false);
// N
			setIsDelivered (false);
// N
			setIsLastre (false);
// N
			setIsPlanification (false);
// N
			setIsTrasbordo (false);
// N
			setIsWarehouseRequired (false);
// N
			setLoadProduct (false);
// N
			setName (null);
			setOnMove (false);
// N
			setUY_TR_LoadStatus_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_LoadStatus (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_LoadStatus[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ChangeDriver.
		@param ChangeDriver ChangeDriver	  */
	public void setChangeDriver (boolean ChangeDriver)
	{
		set_Value (COLUMNNAME_ChangeDriver, Boolean.valueOf(ChangeDriver));
	}

	/** Get ChangeDriver.
		@return ChangeDriver	  */
	public boolean isChangeDriver () 
	{
		Object oo = get_Value(COLUMNNAME_ChangeDriver);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ChangeTruck.
		@param ChangeTruck ChangeTruck	  */
	public void setChangeTruck (boolean ChangeTruck)
	{
		set_Value (COLUMNNAME_ChangeTruck, Boolean.valueOf(ChangeTruck));
	}

	/** Get ChangeTruck.
		@return ChangeTruck	  */
	public boolean isChangeTruck () 
	{
		Object oo = get_Value(COLUMNNAME_ChangeTruck);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set EndTrackStatus.
		@param EndTrackStatus EndTrackStatus	  */
	public void setEndTrackStatus (boolean EndTrackStatus)
	{
		set_Value (COLUMNNAME_EndTrackStatus, Boolean.valueOf(EndTrackStatus));
	}

	/** Get EndTrackStatus.
		@return EndTrackStatus	  */
	public boolean isEndTrackStatus () 
	{
		Object oo = get_Value(COLUMNNAME_EndTrackStatus);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HandleCRT.
		@param HandleCRT HandleCRT	  */
	public void setHandleCRT (boolean HandleCRT)
	{
		set_Value (COLUMNNAME_HandleCRT, Boolean.valueOf(HandleCRT));
	}

	/** Get HandleCRT.
		@return HandleCRT	  */
	public boolean isHandleCRT () 
	{
		Object oo = get_Value(COLUMNNAME_HandleCRT);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsBeforeLoad.
		@param IsBeforeLoad IsBeforeLoad	  */
	public void setIsBeforeLoad (boolean IsBeforeLoad)
	{
		set_Value (COLUMNNAME_IsBeforeLoad, Boolean.valueOf(IsBeforeLoad));
	}

	/** Get IsBeforeLoad.
		@return IsBeforeLoad	  */
	public boolean isBeforeLoad () 
	{
		Object oo = get_Value(COLUMNNAME_IsBeforeLoad);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsConfirmation.
		@param IsConfirmation IsConfirmation	  */
	public void setIsConfirmation (boolean IsConfirmation)
	{
		set_Value (COLUMNNAME_IsConfirmation, Boolean.valueOf(IsConfirmation));
	}

	/** Get IsConfirmation.
		@return IsConfirmation	  */
	public boolean isConfirmation () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfirmation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Delivered.
		@param IsDelivered Delivered	  */
	public void setIsDelivered (boolean IsDelivered)
	{
		set_Value (COLUMNNAME_IsDelivered, Boolean.valueOf(IsDelivered));
	}

	/** Get Delivered.
		@return Delivered	  */
	public boolean isDelivered () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsLastre.
		@param IsLastre IsLastre	  */
	public void setIsLastre (boolean IsLastre)
	{
		set_Value (COLUMNNAME_IsLastre, Boolean.valueOf(IsLastre));
	}

	/** Get IsLastre.
		@return IsLastre	  */
	public boolean isLastre () 
	{
		Object oo = get_Value(COLUMNNAME_IsLastre);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPlanification.
		@param IsPlanification IsPlanification	  */
	public void setIsPlanification (boolean IsPlanification)
	{
		set_Value (COLUMNNAME_IsPlanification, Boolean.valueOf(IsPlanification));
	}

	/** Get IsPlanification.
		@return IsPlanification	  */
	public boolean isPlanification () 
	{
		Object oo = get_Value(COLUMNNAME_IsPlanification);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsTrasbordo.
		@param IsTrasbordo IsTrasbordo	  */
	public void setIsTrasbordo (boolean IsTrasbordo)
	{
		set_Value (COLUMNNAME_IsTrasbordo, Boolean.valueOf(IsTrasbordo));
	}

	/** Get IsTrasbordo.
		@return IsTrasbordo	  */
	public boolean isTrasbordo () 
	{
		Object oo = get_Value(COLUMNNAME_IsTrasbordo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsWarehouseRequired.
		@param IsWarehouseRequired 
		IsWarehouseRequired
	  */
	public void setIsWarehouseRequired (boolean IsWarehouseRequired)
	{
		set_Value (COLUMNNAME_IsWarehouseRequired, Boolean.valueOf(IsWarehouseRequired));
	}

	/** Get IsWarehouseRequired.
		@return IsWarehouseRequired
	  */
	public boolean isWarehouseRequired () 
	{
		Object oo = get_Value(COLUMNNAME_IsWarehouseRequired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LoadProduct.
		@param LoadProduct LoadProduct	  */
	public void setLoadProduct (boolean LoadProduct)
	{
		set_Value (COLUMNNAME_LoadProduct, Boolean.valueOf(LoadProduct));
	}

	/** Get LoadProduct.
		@return LoadProduct	  */
	public boolean isLoadProduct () 
	{
		Object oo = get_Value(COLUMNNAME_LoadProduct);
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

	/** Set OnMove.
		@param OnMove 
		OnMove
	  */
	public void setOnMove (boolean OnMove)
	{
		set_Value (COLUMNNAME_OnMove, Boolean.valueOf(OnMove));
	}

	/** Get OnMove.
		@return OnMove
	  */
	public boolean isOnMove () 
	{
		Object oo = get_Value(COLUMNNAME_OnMove);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_TR_LoadStatus.
		@param UY_TR_LoadStatus_ID UY_TR_LoadStatus	  */
	public void setUY_TR_LoadStatus_ID (int UY_TR_LoadStatus_ID)
	{
		if (UY_TR_LoadStatus_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadStatus_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadStatus_ID, Integer.valueOf(UY_TR_LoadStatus_ID));
	}

	/** Get UY_TR_LoadStatus.
		@return UY_TR_LoadStatus	  */
	public int getUY_TR_LoadStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadStatus_ID);
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
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}