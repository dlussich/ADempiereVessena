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

/** Generated Model for UY_ExportInvTracking
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ExportInvTracking extends PO implements I_UY_ExportInvTracking, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130805L;

    /** Standard Constructor */
    public X_UY_ExportInvTracking (Properties ctx, int UY_ExportInvTracking_ID, String trxName)
    {
      super (ctx, UY_ExportInvTracking_ID, trxName);
      /** if (UY_ExportInvTracking_ID == 0)
        {
			setdocstatus_proform (null);
			setdoctype_proform (0);
			setproforminvoice (0);
			setsequence_proform (null);
			setUY_ExportInvTracking_ID (0);
			setversion_proform (null);
        } */
    }

    /** Load Constructor */
    public X_UY_ExportInvTracking (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ExportInvTracking[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set docstatus_export.
		@param docstatus_export docstatus_export	  */
	public void setdocstatus_export (String docstatus_export)
	{
		set_Value (COLUMNNAME_docstatus_export, docstatus_export);
	}

	/** Get docstatus_export.
		@return docstatus_export	  */
	public String getdocstatus_export () 
	{
		return (String)get_Value(COLUMNNAME_docstatus_export);
	}

	/** Set docstatus_proform.
		@param docstatus_proform docstatus_proform	  */
	public void setdocstatus_proform (String docstatus_proform)
	{
		set_Value (COLUMNNAME_docstatus_proform, docstatus_proform);
	}

	/** Get docstatus_proform.
		@return docstatus_proform	  */
	public String getdocstatus_proform () 
	{
		return (String)get_Value(COLUMNNAME_docstatus_proform);
	}

	/** Set doctype_export.
		@param doctype_export doctype_export	  */
	public void setdoctype_export (int doctype_export)
	{
		set_Value (COLUMNNAME_doctype_export, Integer.valueOf(doctype_export));
	}

	/** Get doctype_export.
		@return doctype_export	  */
	public int getdoctype_export () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_doctype_export);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set doctype_proform.
		@param doctype_proform doctype_proform	  */
	public void setdoctype_proform (int doctype_proform)
	{
		set_Value (COLUMNNAME_doctype_proform, Integer.valueOf(doctype_proform));
	}

	/** Get doctype_proform.
		@return doctype_proform	  */
	public int getdoctype_proform () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_doctype_proform);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set exportinvoice.
		@param exportinvoice exportinvoice	  */
	public void setexportinvoice (int exportinvoice)
	{
		set_Value (COLUMNNAME_exportinvoice, Integer.valueOf(exportinvoice));
	}

	/** Get exportinvoice.
		@return exportinvoice	  */
	public int getexportinvoice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_exportinvoice);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set proforminvoice.
		@param proforminvoice proforminvoice	  */
	public void setproforminvoice (int proforminvoice)
	{
		set_Value (COLUMNNAME_proforminvoice, Integer.valueOf(proforminvoice));
	}

	/** Get proforminvoice.
		@return proforminvoice	  */
	public int getproforminvoice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_proforminvoice);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set sequence_export.
		@param sequence_export sequence_export	  */
	public void setsequence_export (String sequence_export)
	{
		set_Value (COLUMNNAME_sequence_export, sequence_export);
	}

	/** Get sequence_export.
		@return sequence_export	  */
	public String getsequence_export () 
	{
		return (String)get_Value(COLUMNNAME_sequence_export);
	}

	/** Set sequence_proform.
		@param sequence_proform sequence_proform	  */
	public void setsequence_proform (String sequence_proform)
	{
		set_Value (COLUMNNAME_sequence_proform, sequence_proform);
	}

	/** Get sequence_proform.
		@return sequence_proform	  */
	public String getsequence_proform () 
	{
		return (String)get_Value(COLUMNNAME_sequence_proform);
	}

	/** Set UY_ExportInvTracking.
		@param UY_ExportInvTracking_ID UY_ExportInvTracking	  */
	public void setUY_ExportInvTracking_ID (int UY_ExportInvTracking_ID)
	{
		if (UY_ExportInvTracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ExportInvTracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ExportInvTracking_ID, Integer.valueOf(UY_ExportInvTracking_ID));
	}

	/** Get UY_ExportInvTracking.
		@return UY_ExportInvTracking	  */
	public int getUY_ExportInvTracking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ExportInvTracking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set version_export.
		@param version_export version_export	  */
	public void setversion_export (String version_export)
	{
		set_Value (COLUMNNAME_version_export, version_export);
	}

	/** Get version_export.
		@return version_export	  */
	public String getversion_export () 
	{
		return (String)get_Value(COLUMNNAME_version_export);
	}

	/** Set version_proform.
		@param version_proform version_proform	  */
	public void setversion_proform (String version_proform)
	{
		set_Value (COLUMNNAME_version_proform, version_proform);
	}

	/** Get version_proform.
		@return version_proform	  */
	public String getversion_proform () 
	{
		return (String)get_Value(COLUMNNAME_version_proform);
	}
}