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

/** Generated Model for UY_HRConceptoReloj
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRConceptoReloj extends PO implements I_UY_HRConceptoReloj, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120522L;

    /** Standard Constructor */
    public X_UY_HRConceptoReloj (Properties ctx, int UY_HRConceptoReloj_ID, String trxName)
    {
      super (ctx, UY_HRConceptoReloj_ID, trxName);
      /** if (UY_HRConceptoReloj_ID == 0)
        {
			sethr_concept_diastrab_id (0);
			sethr_concept_hrsextra_id (0);
			sethr_concept_hrsextranoct_id (0);
			sethr_concept_hrsnoct_id (0);
			sethr_concept_hrsnotrab_id (0);
			sethr_concept_hrstrab_id (0);
			sethr_concept_inasist_id (0);
			setUY_HRConceptoReloj_ID (0);
			setUY_HRParametros_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRConceptoReloj (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRConceptoReloj[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.eevolution.model.I_HR_Concept gethr_concept_diastrab() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(gethr_concept_diastrab_id(), get_TrxName());	}

	/** Set hr_concept_diastrab_id.
		@param hr_concept_diastrab_id hr_concept_diastrab_id	  */
	public void sethr_concept_diastrab_id (int hr_concept_diastrab_id)
	{
		set_Value (COLUMNNAME_hr_concept_diastrab_id, Integer.valueOf(hr_concept_diastrab_id));
	}

	/** Get hr_concept_diastrab_id.
		@return hr_concept_diastrab_id	  */
	public int gethr_concept_diastrab_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hr_concept_diastrab_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Concept gethr_concept_hrsextra() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(gethr_concept_hrsextra_id(), get_TrxName());	}

	/** Set hr_concept_hrsextra_id.
		@param hr_concept_hrsextra_id hr_concept_hrsextra_id	  */
	public void sethr_concept_hrsextra_id (int hr_concept_hrsextra_id)
	{
		set_Value (COLUMNNAME_hr_concept_hrsextra_id, Integer.valueOf(hr_concept_hrsextra_id));
	}

	/** Get hr_concept_hrsextra_id.
		@return hr_concept_hrsextra_id	  */
	public int gethr_concept_hrsextra_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hr_concept_hrsextra_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Concept gethr_concept_hrsextranoct() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(gethr_concept_hrsextranoct_id(), get_TrxName());	}

	/** Set hr_concept_hrsextranoct_id.
		@param hr_concept_hrsextranoct_id hr_concept_hrsextranoct_id	  */
	public void sethr_concept_hrsextranoct_id (int hr_concept_hrsextranoct_id)
	{
		set_Value (COLUMNNAME_hr_concept_hrsextranoct_id, Integer.valueOf(hr_concept_hrsextranoct_id));
	}

	/** Get hr_concept_hrsextranoct_id.
		@return hr_concept_hrsextranoct_id	  */
	public int gethr_concept_hrsextranoct_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hr_concept_hrsextranoct_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Concept gethr_concept_hrsnoct() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(gethr_concept_hrsnoct_id(), get_TrxName());	}

	/** Set hr_concept_hrsnoct_id.
		@param hr_concept_hrsnoct_id hr_concept_hrsnoct_id	  */
	public void sethr_concept_hrsnoct_id (int hr_concept_hrsnoct_id)
	{
		set_Value (COLUMNNAME_hr_concept_hrsnoct_id, Integer.valueOf(hr_concept_hrsnoct_id));
	}

	/** Get hr_concept_hrsnoct_id.
		@return hr_concept_hrsnoct_id	  */
	public int gethr_concept_hrsnoct_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hr_concept_hrsnoct_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Concept gethr_concept_hrsnotrab() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(gethr_concept_hrsnotrab_id(), get_TrxName());	}

	/** Set hr_concept_hrsnotrab_id.
		@param hr_concept_hrsnotrab_id hr_concept_hrsnotrab_id	  */
	public void sethr_concept_hrsnotrab_id (int hr_concept_hrsnotrab_id)
	{
		set_Value (COLUMNNAME_hr_concept_hrsnotrab_id, Integer.valueOf(hr_concept_hrsnotrab_id));
	}

	/** Get hr_concept_hrsnotrab_id.
		@return hr_concept_hrsnotrab_id	  */
	public int gethr_concept_hrsnotrab_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hr_concept_hrsnotrab_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Concept gethr_concept_hrstrab() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(gethr_concept_hrstrab_id(), get_TrxName());	}

	/** Set hr_concept_hrstrab_id.
		@param hr_concept_hrstrab_id hr_concept_hrstrab_id	  */
	public void sethr_concept_hrstrab_id (int hr_concept_hrstrab_id)
	{
		set_Value (COLUMNNAME_hr_concept_hrstrab_id, Integer.valueOf(hr_concept_hrstrab_id));
	}

	/** Get hr_concept_hrstrab_id.
		@return hr_concept_hrstrab_id	  */
	public int gethr_concept_hrstrab_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hr_concept_hrstrab_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Concept gethr_concept_inasist() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(gethr_concept_inasist_id(), get_TrxName());	}

	/** Set hr_concept_inasist_id.
		@param hr_concept_inasist_id hr_concept_inasist_id	  */
	public void sethr_concept_inasist_id (int hr_concept_inasist_id)
	{
		set_Value (COLUMNNAME_hr_concept_inasist_id, Integer.valueOf(hr_concept_inasist_id));
	}

	/** Get hr_concept_inasist_id.
		@return hr_concept_inasist_id	  */
	public int gethr_concept_inasist_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_hr_concept_inasist_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRConceptoReloj.
		@param UY_HRConceptoReloj_ID UY_HRConceptoReloj	  */
	public void setUY_HRConceptoReloj_ID (int UY_HRConceptoReloj_ID)
	{
		if (UY_HRConceptoReloj_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRConceptoReloj_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRConceptoReloj_ID, Integer.valueOf(UY_HRConceptoReloj_ID));
	}

	/** Get UY_HRConceptoReloj.
		@return UY_HRConceptoReloj	  */
	public int getUY_HRConceptoReloj_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRConceptoReloj_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_HRParametros getUY_HRParametros() throws RuntimeException
    {
		return (I_UY_HRParametros)MTable.get(getCtx(), I_UY_HRParametros.Table_Name)
			.getPO(getUY_HRParametros_ID(), get_TrxName());	}

	/** Set UY_HRParametros.
		@param UY_HRParametros_ID UY_HRParametros	  */
	public void setUY_HRParametros_ID (int UY_HRParametros_ID)
	{
		if (UY_HRParametros_ID < 1) 
			set_Value (COLUMNNAME_UY_HRParametros_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRParametros_ID, Integer.valueOf(UY_HRParametros_ID));
	}

	/** Get UY_HRParametros.
		@return UY_HRParametros	  */
	public int getUY_HRParametros_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRParametros_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}