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

/** Generated Model for UY_PR_SchTask
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PR_SchTask extends PO implements I_UY_PR_SchTask, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140904L;

    /** Standard Constructor */
    public X_UY_PR_SchTask (Properties ctx, int UY_PR_SchTask_ID, String trxName)
    {
      super (ctx, UY_PR_SchTask_ID, trxName);
      /** if (UY_PR_SchTask_ID == 0)
        {
			setFrequencySubType (null);
// DIARIA
			setFrequencyType (null);
// RECURRENTE
			setStartMode (null);
// PARENT_END
			setUY_PR_Schedule_ID (0);
			setUY_PR_SchTask_ID (0);
			setUY_PR_Task_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PR_SchTask (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PR_SchTask[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Condition.
		@param Condition 
		Condition
	  */
	public void setCondition (String Condition)
	{
		set_Value (COLUMNNAME_Condition, Condition);
	}

	/** Get Condition.
		@return Condition
	  */
	public String getCondition () 
	{
		return (String)get_Value(COLUMNNAME_Condition);
	}

	/** FrequencySubType AD_Reference_ID=1000371 */
	public static final int FREQUENCYSUBTYPE_AD_Reference_ID=1000371;
	/** DIARIA = DIARIA */
	public static final String FREQUENCYSUBTYPE_DIARIA = "DIARIA";
	/** SEMANAL = SEMANAL */
	public static final String FREQUENCYSUBTYPE_SEMANAL = "SEMANAL";
	/** MENSUAL = MENSUAL */
	public static final String FREQUENCYSUBTYPE_MENSUAL = "MENSUAL";
	/** Set FrequencySubType.
		@param FrequencySubType FrequencySubType	  */
	public void setFrequencySubType (String FrequencySubType)
	{

		set_Value (COLUMNNAME_FrequencySubType, FrequencySubType);
	}

	/** Get FrequencySubType.
		@return FrequencySubType	  */
	public String getFrequencySubType () 
	{
		return (String)get_Value(COLUMNNAME_FrequencySubType);
	}

	/** FrequencyType AD_Reference_ID=1000370 */
	public static final int FREQUENCYTYPE_AD_Reference_ID=1000370;
	/** RECURRENTE = RECURRENTE */
	public static final String FREQUENCYTYPE_RECURRENTE = "RECURRENTE";
	/** SECUENCIAL = SECUENCIAL */
	public static final String FREQUENCYTYPE_SECUENCIAL = "SECUENCIAL";
	/** CALENDARIO = CALENDARIO */
	public static final String FREQUENCYTYPE_CALENDARIO = "CALENDARIO";
	/** CONDICIONAL = CONDICIONAL */
	public static final String FREQUENCYTYPE_CONDICIONAL = "CONDICIONAL";
	/** MANUAL = MANUAL */
	public static final String FREQUENCYTYPE_MANUAL = "MANUAL";
	/** Set FrequencyType.
		@param FrequencyType 
		Frequency of event
	  */
	public void setFrequencyType (String FrequencyType)
	{

		set_Value (COLUMNNAME_FrequencyType, FrequencyType);
	}

	/** Get FrequencyType.
		@return Frequency of event
	  */
	public String getFrequencyType () 
	{
		return (String)get_Value(COLUMNNAME_FrequencyType);
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

	/** StartMode AD_Reference_ID=1000375 */
	public static final int STARTMODE_AD_Reference_ID=1000375;
	/** Al finalizar Tarea Padre = PARENT_END */
	public static final String STARTMODE_AlFinalizarTareaPadre = "PARENT_END";
	/** Al Iniciar Tarea Padre = PARENT_START */
	public static final String STARTMODE_AlIniciarTareaPadre = "PARENT_START";
	/** Al Vencer Plazo Optimo del Padre = PARENT_MEDIUMTERM */
	public static final String STARTMODE_AlVencerPlazoOptimoDelPadre = "PARENT_MEDIUMTERM";
	/** Al Vencer Plazo Final del Padre = PARENT_DEADLINE */
	public static final String STARTMODE_AlVencerPlazoFinalDelPadre = "PARENT_DEADLINE";
	/** Set Start Mode.
		@param StartMode 
		Workflow Activity Start Mode 
	  */
	public void setStartMode (String StartMode)
	{

		set_Value (COLUMNNAME_StartMode, StartMode);
	}

	/** Get Start Mode.
		@return Workflow Activity Start Mode 
	  */
	public String getStartMode () 
	{
		return (String)get_Value(COLUMNNAME_StartMode);
	}

	/** Set UY_PR_ParentTask_ID.
		@param UY_PR_ParentTask_ID UY_PR_ParentTask_ID	  */
	public void setUY_PR_ParentTask_ID (int UY_PR_ParentTask_ID)
	{
		if (UY_PR_ParentTask_ID < 1) 
			set_Value (COLUMNNAME_UY_PR_ParentTask_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PR_ParentTask_ID, Integer.valueOf(UY_PR_ParentTask_ID));
	}

	/** Get UY_PR_ParentTask_ID.
		@return UY_PR_ParentTask_ID	  */
	public int getUY_PR_ParentTask_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_ParentTask_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PR_Schedule getUY_PR_Schedule() throws RuntimeException
    {
		return (I_UY_PR_Schedule)MTable.get(getCtx(), I_UY_PR_Schedule.Table_Name)
			.getPO(getUY_PR_Schedule_ID(), get_TrxName());	}

	/** Set UY_PR_Schedule.
		@param UY_PR_Schedule_ID UY_PR_Schedule	  */
	public void setUY_PR_Schedule_ID (int UY_PR_Schedule_ID)
	{
		if (UY_PR_Schedule_ID < 1) 
			set_Value (COLUMNNAME_UY_PR_Schedule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PR_Schedule_ID, Integer.valueOf(UY_PR_Schedule_ID));
	}

	/** Get UY_PR_Schedule.
		@return UY_PR_Schedule	  */
	public int getUY_PR_Schedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_Schedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PR_SchTask.
		@param UY_PR_SchTask_ID UY_PR_SchTask	  */
	public void setUY_PR_SchTask_ID (int UY_PR_SchTask_ID)
	{
		if (UY_PR_SchTask_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PR_SchTask_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PR_SchTask_ID, Integer.valueOf(UY_PR_SchTask_ID));
	}

	/** Get UY_PR_SchTask.
		@return UY_PR_SchTask	  */
	public int getUY_PR_SchTask_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_SchTask_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PR_Task getUY_PR_Task() throws RuntimeException
    {
		return (I_UY_PR_Task)MTable.get(getCtx(), I_UY_PR_Task.Table_Name)
			.getPO(getUY_PR_Task_ID(), get_TrxName());	}

	/** Set UY_PR_Task.
		@param UY_PR_Task_ID UY_PR_Task	  */
	public void setUY_PR_Task_ID (int UY_PR_Task_ID)
	{
		if (UY_PR_Task_ID < 1) 
			set_Value (COLUMNNAME_UY_PR_Task_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PR_Task_ID, Integer.valueOf(UY_PR_Task_ID));
	}

	/** Get UY_PR_Task.
		@return UY_PR_Task	  */
	public int getUY_PR_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PR_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}