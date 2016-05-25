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

/** Generated Model for UY_Conciliated
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Conciliated extends PO implements I_UY_Conciliated, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121128L;

    /** Standard Constructor */
    public X_UY_Conciliated (Properties ctx, int UY_Conciliated_ID, String trxName)
    {
      super (ctx, UY_Conciliated_ID, trxName);
      /** if (UY_Conciliated_ID == 0)
        {
			setC_DocType_ID (0);
			setDocumentNo (null);
			setUY_Conciliated_ID (0);
			setUY_Conciliation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Conciliated (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Conciliated[")
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

	/** Set UY_Conciliated.
		@param UY_Conciliated_ID UY_Conciliated	  */
	public void setUY_Conciliated_ID (int UY_Conciliated_ID)
	{
		if (UY_Conciliated_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Conciliated_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Conciliated_ID, Integer.valueOf(UY_Conciliated_ID));
	}

	/** Get UY_Conciliated.
		@return UY_Conciliated	  */
	public int getUY_Conciliated_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Conciliated_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Conciliation getUY_Conciliation() throws RuntimeException
    {
		return (I_UY_Conciliation)MTable.get(getCtx(), I_UY_Conciliation.Table_Name)
			.getPO(getUY_Conciliation_ID(), get_TrxName());	}

	/** Set UY_Conciliation.
		@param UY_Conciliation_ID UY_Conciliation	  */
	public void setUY_Conciliation_ID (int UY_Conciliation_ID)
	{
		if (UY_Conciliation_ID < 1) 
			set_Value (COLUMNNAME_UY_Conciliation_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Conciliation_ID, Integer.valueOf(UY_Conciliation_ID));
	}

	/** Get UY_Conciliation.
		@return UY_Conciliation	  */
	public int getUY_Conciliation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Conciliation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}