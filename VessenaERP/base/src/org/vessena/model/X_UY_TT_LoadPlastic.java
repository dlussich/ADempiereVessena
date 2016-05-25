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

/** Generated Model for UY_TT_LoadPlastic
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_LoadPlastic extends PO implements I_UY_TT_LoadPlastic, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130920L;

    /** Standard Constructor */
    public X_UY_TT_LoadPlastic (Properties ctx, int UY_TT_LoadPlastic_ID, String trxName)
    {
      super (ctx, UY_TT_LoadPlastic_ID, trxName);
      /** if (UY_TT_LoadPlastic_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setUY_TT_LoadPlastic_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_LoadPlastic (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_LoadPlastic[")
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

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set UY_TT_LoadPlastic.
		@param UY_TT_LoadPlastic_ID UY_TT_LoadPlastic	  */
	public void setUY_TT_LoadPlastic_ID (int UY_TT_LoadPlastic_ID)
	{
		if (UY_TT_LoadPlastic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_LoadPlastic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_LoadPlastic_ID, Integer.valueOf(UY_TT_LoadPlastic_ID));
	}

	/** Get UY_TT_LoadPlastic.
		@return UY_TT_LoadPlastic	  */
	public int getUY_TT_LoadPlastic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_LoadPlastic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}