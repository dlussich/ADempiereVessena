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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_CFE_MessageLogCFEDocs
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_MessageLogCFEDocs extends PO implements I_UY_CFE_MessageLogCFEDocs, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160212L;

    /** Standard Constructor */
    public X_UY_CFE_MessageLogCFEDocs (Properties ctx, int UY_CFE_MessageLogCFEDocs_ID, String trxName)
    {
      super (ctx, UY_CFE_MessageLogCFEDocs_ID, trxName);
      /** if (UY_CFE_MessageLogCFEDocs_ID == 0)
        {
			setUY_CFE_MessageLogCFEDocs_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_MessageLogCFEDocs (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_MessageLogCFEDocs[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (BigDecimal C_DocType_ID)
	{
		set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public BigDecimal getC_DocType_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_C_DocType_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CFE_MessageLogCFEDocs.
		@param UY_CFE_MessageLogCFEDocs_ID UY_CFE_MessageLogCFEDocs	  */
	public void setUY_CFE_MessageLogCFEDocs_ID (int UY_CFE_MessageLogCFEDocs_ID)
	{
		if (UY_CFE_MessageLogCFEDocs_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_MessageLogCFEDocs_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_MessageLogCFEDocs_ID, Integer.valueOf(UY_CFE_MessageLogCFEDocs_ID));
	}

	/** Get UY_CFE_MessageLogCFEDocs.
		@return UY_CFE_MessageLogCFEDocs	  */
	public int getUY_CFE_MessageLogCFEDocs_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_MessageLogCFEDocs_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CFE_MessageLog getUY_CFE_MessageLog() throws RuntimeException
    {
		return (I_UY_CFE_MessageLog)MTable.get(getCtx(), I_UY_CFE_MessageLog.Table_Name)
			.getPO(getUY_CFE_MessageLog_ID(), get_TrxName());	}

	/** Set UY_CFE_MessageLog.
		@param UY_CFE_MessageLog_ID UY_CFE_MessageLog	  */
	public void setUY_CFE_MessageLog_ID (int UY_CFE_MessageLog_ID)
	{
		if (UY_CFE_MessageLog_ID < 1) 
			set_Value (COLUMNNAME_UY_CFE_MessageLog_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CFE_MessageLog_ID, Integer.valueOf(UY_CFE_MessageLog_ID));
	}

	/** Get UY_CFE_MessageLog.
		@return UY_CFE_MessageLog	  */
	public int getUY_CFE_MessageLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_MessageLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}