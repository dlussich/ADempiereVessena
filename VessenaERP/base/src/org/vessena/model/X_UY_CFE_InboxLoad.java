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

/** Generated Model for UY_CFE_InboxLoad
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_InboxLoad extends PO implements I_UY_CFE_InboxLoad, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160114L;

    /** Standard Constructor */
    public X_UY_CFE_InboxLoad (Properties ctx, int UY_CFE_InboxLoad_ID, String trxName)
    {
      super (ctx, UY_CFE_InboxLoad_ID, trxName);
      /** if (UY_CFE_InboxLoad_ID == 0)
        {
			setC_DocType_ID (0);
			setCFELoadType (null);
			setDocumentNo (null);
			setIsExecuted (false);
// N
			setUY_CFE_Inbox_ID (0);
			setUY_CFE_InboxLoad_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_InboxLoad (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_InboxLoad[")
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

	/** CFELoadType AD_Reference_ID=1010395 */
	public static final int CFELOADTYPE_AD_Reference_ID=1010395;
	/** VISTA = VISTA */
	public static final String CFELOADTYPE_VISTA = "VISTA";
	/** EXCEL = EXCEL */
	public static final String CFELOADTYPE_EXCEL = "EXCEL";
	/** ERP = ERP */
	public static final String CFELOADTYPE_ERP = "ERP";
	/** Set CFELoadType.
		@param CFELoadType CFELoadType	  */
	public void setCFELoadType (String CFELoadType)
	{

		set_Value (COLUMNNAME_CFELoadType, CFELoadType);
	}

	/** Get CFELoadType.
		@return CFELoadType	  */
	public String getCFELoadType () 
	{
		return (String)get_Value(COLUMNNAME_CFELoadType);
	}

	/** DataType AD_Reference_ID=1010396 */
	public static final int DATATYPE_AD_Reference_ID=1010396;
	/** FDU108 = FDU108 */
	public static final String DATATYPE_FDU108 = "FDU108";
	/** Set Data Type.
		@param DataType 
		Type of data
	  */
	public void setDataType (String DataType)
	{

		set_Value (COLUMNNAME_DataType, DataType);
	}

	/** Get Data Type.
		@return Type of data
	  */
	public String getDataType () 
	{
		return (String)get_Value(COLUMNNAME_DataType);
	}

	/** Set DateAction.
		@param DateAction DateAction	  */
	public void setDateAction (Timestamp DateAction)
	{
		set_Value (COLUMNNAME_DateAction, DateAction);
	}

	/** Get DateAction.
		@return DateAction	  */
	public Timestamp getDateAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAction);
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

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_CFE_InboxFileType getUY_CFE_InboxFileType() throws RuntimeException
    {
		return (I_UY_CFE_InboxFileType)MTable.get(getCtx(), I_UY_CFE_InboxFileType.Table_Name)
			.getPO(getUY_CFE_InboxFileType_ID(), get_TrxName());	}

	/** Set UY_CFE_InboxFileType.
		@param UY_CFE_InboxFileType_ID UY_CFE_InboxFileType	  */
	public void setUY_CFE_InboxFileType_ID (int UY_CFE_InboxFileType_ID)
	{
		if (UY_CFE_InboxFileType_ID < 1) 
			set_Value (COLUMNNAME_UY_CFE_InboxFileType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CFE_InboxFileType_ID, Integer.valueOf(UY_CFE_InboxFileType_ID));
	}

	/** Get UY_CFE_InboxFileType.
		@return UY_CFE_InboxFileType	  */
	public int getUY_CFE_InboxFileType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InboxFileType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CFE_Inbox getUY_CFE_Inbox() throws RuntimeException
    {
		return (I_UY_CFE_Inbox)MTable.get(getCtx(), I_UY_CFE_Inbox.Table_Name)
			.getPO(getUY_CFE_Inbox_ID(), get_TrxName());	}

	/** Set UY_CFE_Inbox.
		@param UY_CFE_Inbox_ID UY_CFE_Inbox	  */
	public void setUY_CFE_Inbox_ID (int UY_CFE_Inbox_ID)
	{
		if (UY_CFE_Inbox_ID < 1) 
			set_Value (COLUMNNAME_UY_CFE_Inbox_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CFE_Inbox_ID, Integer.valueOf(UY_CFE_Inbox_ID));
	}

	/** Get UY_CFE_Inbox.
		@return UY_CFE_Inbox	  */
	public int getUY_CFE_Inbox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_Inbox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CFE_InboxLoad.
		@param UY_CFE_InboxLoad_ID UY_CFE_InboxLoad	  */
	public void setUY_CFE_InboxLoad_ID (int UY_CFE_InboxLoad_ID)
	{
		if (UY_CFE_InboxLoad_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InboxLoad_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InboxLoad_ID, Integer.valueOf(UY_CFE_InboxLoad_ID));
	}

	/** Get UY_CFE_InboxLoad.
		@return UY_CFE_InboxLoad	  */
	public int getUY_CFE_InboxLoad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InboxLoad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}