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

/** Generated Model for UY_CierreTransporteDoc
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CierreTransporteDoc extends PO implements I_UY_CierreTransporteDoc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CierreTransporteDoc (Properties ctx, int UY_CierreTransporteDoc_ID, String trxName)
    {
      super (ctx, UY_CierreTransporteDoc_ID, trxName);
      /** if (UY_CierreTransporteDoc_ID == 0)
        {
			setC_DocType_ID (0);
			setUY_CierreTransporteDoc_ID (0);
			setUY_CierreTransporteHdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CierreTransporteDoc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CierreTransporteDoc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
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

	/** Set UY_CierreTransporteDoc.
		@param UY_CierreTransporteDoc_ID UY_CierreTransporteDoc	  */
	public void setUY_CierreTransporteDoc_ID (int UY_CierreTransporteDoc_ID)
	{
		if (UY_CierreTransporteDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CierreTransporteDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CierreTransporteDoc_ID, Integer.valueOf(UY_CierreTransporteDoc_ID));
	}

	/** Get UY_CierreTransporteDoc.
		@return UY_CierreTransporteDoc	  */
	public int getUY_CierreTransporteDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CierreTransporteDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CierreTransporteHdr getUY_CierreTransporteHdr() throws RuntimeException
    {
		return (I_UY_CierreTransporteHdr)MTable.get(getCtx(), I_UY_CierreTransporteHdr.Table_Name)
			.getPO(getUY_CierreTransporteHdr_ID(), get_TrxName());	}

	/** Set UY_CierreTransporteHdr.
		@param UY_CierreTransporteHdr_ID UY_CierreTransporteHdr	  */
	public void setUY_CierreTransporteHdr_ID (int UY_CierreTransporteHdr_ID)
	{
		if (UY_CierreTransporteHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_CierreTransporteHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CierreTransporteHdr_ID, Integer.valueOf(UY_CierreTransporteHdr_ID));
	}

	/** Get UY_CierreTransporteHdr.
		@return UY_CierreTransporteHdr	  */
	public int getUY_CierreTransporteHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CierreTransporteHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}