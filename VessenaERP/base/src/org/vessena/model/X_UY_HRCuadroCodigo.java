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

/** Generated Model for UY_HRCuadroCodigo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRCuadroCodigo extends PO implements I_UY_HRCuadroCodigo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140106L;

    /** Standard Constructor */
    public X_UY_HRCuadroCodigo (Properties ctx, int UY_HRCuadroCodigo_ID, String trxName)
    {
      super (ctx, UY_HRCuadroCodigo_ID, trxName);
      /** if (UY_HRCuadroCodigo_ID == 0)
        {
			setC_DocType_ID (0);
			setC_JobCategory_ID (0);
			setDocumentNo (null);
			setHR_Concept_ID (0);
			setUY_HRCuadroCodigo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRCuadroCodigo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRCuadroCodigo[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_JobCategory getC_JobCategory() throws RuntimeException
    {
		return (org.compiere.model.I_C_JobCategory)MTable.get(getCtx(), org.compiere.model.I_C_JobCategory.Table_Name)
			.getPO(getC_JobCategory_ID(), get_TrxName());	}

	/** Set Position Category.
		@param C_JobCategory_ID 
		Job Position Category
	  */
	public void setC_JobCategory_ID (int C_JobCategory_ID)
	{
		if (C_JobCategory_ID < 1) 
			set_Value (COLUMNNAME_C_JobCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_JobCategory_ID, Integer.valueOf(C_JobCategory_ID));
	}

	/** Get Position Category.
		@return Job Position Category
	  */
	public int getC_JobCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_JobCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(getHR_Concept_ID(), get_TrxName());	}

	/** Set Payroll Concept.
		@param HR_Concept_ID Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get Payroll Concept.
		@return Payroll Concept	  */
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRCuadroCodigo.
		@param UY_HRCuadroCodigo_ID UY_HRCuadroCodigo	  */
	public void setUY_HRCuadroCodigo_ID (int UY_HRCuadroCodigo_ID)
	{
		if (UY_HRCuadroCodigo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRCuadroCodigo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRCuadroCodigo_ID, Integer.valueOf(UY_HRCuadroCodigo_ID));
	}

	/** Get UY_HRCuadroCodigo.
		@return UY_HRCuadroCodigo	  */
	public int getUY_HRCuadroCodigo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRCuadroCodigo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}