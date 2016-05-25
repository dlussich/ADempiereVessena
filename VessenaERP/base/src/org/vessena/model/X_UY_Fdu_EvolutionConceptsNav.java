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

/** Generated Model for UY_Fdu_EvolutionConceptsNav
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_EvolutionConceptsNav extends PO implements I_UY_Fdu_EvolutionConceptsNav, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130214L;

    /** Standard Constructor */
    public X_UY_Fdu_EvolutionConceptsNav (Properties ctx, int UY_Fdu_EvolutionConceptsNav_ID, String trxName)
    {
      super (ctx, UY_Fdu_EvolutionConceptsNav_ID, trxName);
      /** if (UY_Fdu_EvolutionConceptsNav_ID == 0)
        {
			setUY_Fdu_EvolutionConceptsNav_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_EvolutionConceptsNav (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_EvolutionConceptsNav[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
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

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set GraphicReport.
		@param GraphicReport 
		GraphicReport
	  */
	public void setGraphicReport (boolean GraphicReport)
	{
		set_Value (COLUMNNAME_GraphicReport, Boolean.valueOf(GraphicReport));
	}

	/** Get GraphicReport.
		@return GraphicReport
	  */
	public boolean isGraphicReport () 
	{
		Object oo = get_Value(COLUMNNAME_GraphicReport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Identificador Unico del Reporte.
		@param idReporte Identificador Unico del Reporte	  */
	public void setidReporte (String idReporte)
	{
		set_Value (COLUMNNAME_idReporte, idReporte);
	}

	/** Get Identificador Unico del Reporte.
		@return Identificador Unico del Reporte	  */
	public String getidReporte () 
	{
		return (String)get_Value(COLUMNNAME_idReporte);
	}

	/** Set LastYear.
		@param LastYear 
		LastYear
	  */
	public void setLastYear (boolean LastYear)
	{
		set_Value (COLUMNNAME_LastYear, Boolean.valueOf(LastYear));
	}

	/** Get LastYear.
		@return LastYear
	  */
	public boolean isLastYear () 
	{
		Object oo = get_Value(COLUMNNAME_LastYear);
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

	/** ReportType AD_Reference_ID=1000239 */
	public static final int REPORTTYPE_AD_Reference_ID=1000239;
	/** Unico Cierre = UC */
	public static final String REPORTTYPE_UnicoCierre = "UC";
	/** Mes calendario = MC */
	public static final String REPORTTYPE_MesCalendario = "MC";
	/** Mes logistico = ML */
	public static final String REPORTTYPE_MesLogistico = "ML";
	/** Set ReportType.
		@param ReportType ReportType	  */
	public void setReportType (String ReportType)
	{

		set_Value (COLUMNNAME_ReportType, ReportType);
	}

	/** Get ReportType.
		@return ReportType	  */
	public String getReportType () 
	{
		return (String)get_Value(COLUMNNAME_ReportType);
	}

	/** Set UY_Fdu_EvolutionConceptsNav.
		@param UY_Fdu_EvolutionConceptsNav_ID UY_Fdu_EvolutionConceptsNav	  */
	public void setUY_Fdu_EvolutionConceptsNav_ID (int UY_Fdu_EvolutionConceptsNav_ID)
	{
		if (UY_Fdu_EvolutionConceptsNav_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_EvolutionConceptsNav_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_EvolutionConceptsNav_ID, Integer.valueOf(UY_Fdu_EvolutionConceptsNav_ID));
	}

	/** Get UY_Fdu_EvolutionConceptsNav.
		@return UY_Fdu_EvolutionConceptsNav	  */
	public int getUY_Fdu_EvolutionConceptsNav_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_EvolutionConceptsNav_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_LogisticMonthDates getUY_Fdu_LogisticMonthDates() throws RuntimeException
    {
		return (I_UY_Fdu_LogisticMonthDates)MTable.get(getCtx(), I_UY_Fdu_LogisticMonthDates.Table_Name)
			.getPO(getUY_Fdu_LogisticMonthDates_ID(), get_TrxName());	}

	/** Set UY_Fdu_LogisticMonthDates.
		@param UY_Fdu_LogisticMonthDates_ID UY_Fdu_LogisticMonthDates	  */
	public void setUY_Fdu_LogisticMonthDates_ID (int UY_Fdu_LogisticMonthDates_ID)
	{
		if (UY_Fdu_LogisticMonthDates_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_LogisticMonthDates_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_LogisticMonthDates_ID, Integer.valueOf(UY_Fdu_LogisticMonthDates_ID));
	}

	/** Get UY_Fdu_LogisticMonthDates.
		@return UY_Fdu_LogisticMonthDates	  */
	public int getUY_Fdu_LogisticMonthDates_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_LogisticMonthDates_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_LogisticMonth getUY_Fdu_LogisticMonth() throws RuntimeException
    {
		return (I_UY_Fdu_LogisticMonth)MTable.get(getCtx(), I_UY_Fdu_LogisticMonth.Table_Name)
			.getPO(getUY_Fdu_LogisticMonth_ID(), get_TrxName());	}

	/** Set UY_Fdu_LogisticMonth.
		@param UY_Fdu_LogisticMonth_ID UY_Fdu_LogisticMonth	  */
	public void setUY_Fdu_LogisticMonth_ID (int UY_Fdu_LogisticMonth_ID)
	{
		if (UY_Fdu_LogisticMonth_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_LogisticMonth_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_LogisticMonth_ID, Integer.valueOf(UY_Fdu_LogisticMonth_ID));
	}

	/** Get UY_Fdu_LogisticMonth.
		@return UY_Fdu_LogisticMonth	  */
	public int getUY_Fdu_LogisticMonth_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_LogisticMonth_ID);
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