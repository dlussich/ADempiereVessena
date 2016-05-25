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

/** Generated Model for UY_R_Cause
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_Cause extends PO implements I_UY_R_Cause, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131023L;

    /** Standard Constructor */
    public X_UY_R_Cause (Properties ctx, int UY_R_Cause_ID, String trxName)
    {
      super (ctx, UY_R_Cause_ID, trxName);
      /** if (UY_R_Cause_ID == 0)
        {
			setGenerateInbox (true);
// Y
			setName (null);
			setUY_R_Area_ID (0);
			setUY_R_Cause_ID (0);
			setUY_R_Type_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_R_Cause (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_Cause[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ConLegajo.
		@param ConLegajo ConLegajo	  */
	public void setConLegajo (boolean ConLegajo)
	{
		set_Value (COLUMNNAME_ConLegajo, Boolean.valueOf(ConLegajo));
	}

	/** Get ConLegajo.
		@return ConLegajo	  */
	public boolean isConLegajo () 
	{
		Object oo = get_Value(COLUMNNAME_ConLegajo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set DeadLine.
		@param DeadLine DeadLine	  */
	public void setDeadLine (int DeadLine)
	{
		set_Value (COLUMNNAME_DeadLine, Integer.valueOf(DeadLine));
	}

	/** Get DeadLine.
		@return DeadLine	  */
	public int getDeadLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeadLine);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set DiasSeguimiento.
		@param DiasSeguimiento DiasSeguimiento	  */
	public void setDiasSeguimiento (int DiasSeguimiento)
	{
		set_Value (COLUMNNAME_DiasSeguimiento, Integer.valueOf(DiasSeguimiento));
	}

	/** Get DiasSeguimiento.
		@return DiasSeguimiento	  */
	public int getDiasSeguimiento () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiasSeguimiento);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GenerateInbox.
		@param GenerateInbox GenerateInbox	  */
	public void setGenerateInbox (boolean GenerateInbox)
	{
		set_Value (COLUMNNAME_GenerateInbox, Boolean.valueOf(GenerateInbox));
	}

	/** Get GenerateInbox.
		@return GenerateInbox	  */
	public boolean isGenerateInbox () 
	{
		Object oo = get_Value(COLUMNNAME_GenerateInbox);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set InformaBCU.
		@param InformaBCU InformaBCU	  */
	public void setInformaBCU (boolean InformaBCU)
	{
		set_Value (COLUMNNAME_InformaBCU, Boolean.valueOf(InformaBCU));
	}

	/** Get InformaBCU.
		@return InformaBCU	  */
	public boolean isInformaBCU () 
	{
		Object oo = get_Value(COLUMNNAME_InformaBCU);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsInternalIssue.
		@param IsInternalIssue 
		Incidencia Interna
	  */
	public void setIsInternalIssue (boolean IsInternalIssue)
	{
		set_Value (COLUMNNAME_IsInternalIssue, Boolean.valueOf(IsInternalIssue));
	}

	/** Get IsInternalIssue.
		@return Incidencia Interna
	  */
	public boolean isInternalIssue () 
	{
		Object oo = get_Value(COLUMNNAME_IsInternalIssue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MediumTerm.
		@param MediumTerm MediumTerm	  */
	public void setMediumTerm (int MediumTerm)
	{
		set_Value (COLUMNNAME_MediumTerm, Integer.valueOf(MediumTerm));
	}

	/** Get MediumTerm.
		@return MediumTerm	  */
	public int getMediumTerm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MediumTerm);
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

	/** Set Notificable.
		@param Notificable Notificable	  */
	public void setNotificable (boolean Notificable)
	{
		set_Value (COLUMNNAME_Notificable, Boolean.valueOf(Notificable));
	}

	/** Get Notificable.
		@return Notificable	  */
	public boolean isNotificable () 
	{
		Object oo = get_Value(COLUMNNAME_Notificable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NotificaEmail.
		@param NotificaEmail NotificaEmail	  */
	public void setNotificaEmail (boolean NotificaEmail)
	{
		set_Value (COLUMNNAME_NotificaEmail, Boolean.valueOf(NotificaEmail));
	}

	/** Get NotificaEmail.
		@return NotificaEmail	  */
	public boolean isNotificaEmail () 
	{
		Object oo = get_Value(COLUMNNAME_NotificaEmail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** PriorityBase AD_Reference_ID=154 */
	public static final int PRIORITYBASE_AD_Reference_ID=154;
	/** Medium = 3 */
	public static final String PRIORITYBASE_Medium = "3";
	/** Urgent = 1 */
	public static final String PRIORITYBASE_Urgent = "1";
	/** Minor = 5 */
	public static final String PRIORITYBASE_Minor = "5";
	/** Low = 4 */
	public static final String PRIORITYBASE_Low = "4";
	/** High = 2 */
	public static final String PRIORITYBASE_High = "2";
	/** Inmediate = 0 */
	public static final String PRIORITYBASE_Inmediate = "0";
	/** Set Priority Base.
		@param PriorityBase 
		Base of Priority
	  */
	public void setPriorityBase (String PriorityBase)
	{

		set_Value (COLUMNNAME_PriorityBase, PriorityBase);
	}

	/** Get Priority Base.
		@return Base of Priority
	  */
	public String getPriorityBase () 
	{
		return (String)get_Value(COLUMNNAME_PriorityBase);
	}

	/** Set RequiereSeguimiento.
		@param RequiereSeguimiento RequiereSeguimiento	  */
	public void setRequiereSeguimiento (boolean RequiereSeguimiento)
	{
		set_Value (COLUMNNAME_RequiereSeguimiento, Boolean.valueOf(RequiereSeguimiento));
	}

	/** Get RequiereSeguimiento.
		@return RequiereSeguimiento	  */
	public boolean isRequiereSeguimiento () 
	{
		Object oo = get_Value(COLUMNNAME_RequiereSeguimiento);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_R_Area getUY_R_Area() throws RuntimeException
    {
		return (I_UY_R_Area)MTable.get(getCtx(), I_UY_R_Area.Table_Name)
			.getPO(getUY_R_Area_ID(), get_TrxName());	}

	/** Set UY_R_Area.
		@param UY_R_Area_ID UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID)
	{
		if (UY_R_Area_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Area_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Area_ID, Integer.valueOf(UY_R_Area_ID));
	}

	/** Get UY_R_Area.
		@return UY_R_Area	  */
	public int getUY_R_Area_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Area_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Cause.
		@param UY_R_Cause_ID UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID)
	{
		if (UY_R_Cause_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_Cause_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_Cause_ID, Integer.valueOf(UY_R_Cause_ID));
	}

	/** Get UY_R_Cause.
		@return UY_R_Cause	  */
	public int getUY_R_Cause_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Cause_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException
    {
		return (I_UY_R_PtoResolucion)MTable.get(getCtx(), I_UY_R_PtoResolucion.Table_Name)
			.getPO(getUY_R_PtoResolucion_ID(), get_TrxName());	}

	/** Set UY_R_PtoResolucion.
		@param UY_R_PtoResolucion_ID UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID)
	{
		if (UY_R_PtoResolucion_ID < 1) 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, Integer.valueOf(UY_R_PtoResolucion_ID));
	}

	/** Get UY_R_PtoResolucion.
		@return UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_PtoResolucion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Type getUY_R_Type() throws RuntimeException
    {
		return (I_UY_R_Type)MTable.get(getCtx(), I_UY_R_Type.Table_Name)
			.getPO(getUY_R_Type_ID(), get_TrxName());	}

	/** Set UY_R_Type.
		@param UY_R_Type_ID UY_R_Type	  */
	public void setUY_R_Type_ID (int UY_R_Type_ID)
	{
		if (UY_R_Type_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Type_ID, Integer.valueOf(UY_R_Type_ID));
	}

	/** Get UY_R_Type.
		@return UY_R_Type	  */
	public int getUY_R_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Type_ID);
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