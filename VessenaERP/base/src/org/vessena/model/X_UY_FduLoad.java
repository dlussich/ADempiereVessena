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

/** Generated Model for UY_FduLoad
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_FduLoad extends PO implements I_UY_FduLoad, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121019L;

    /** Standard Constructor */
    public X_UY_FduLoad (Properties ctx, int UY_FduLoad_ID, String trxName)
    {
      super (ctx, UY_FduLoad_ID, trxName);
      /** if (UY_FduLoad_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setIsManual (false);
			setProcessed (false);
			setProcessingDate (new Timestamp( System.currentTimeMillis() ));
			setUY_FduFile_ID (0);
			setUY_FduLoad_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FduLoad (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FduLoad[")
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

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processing date.
		@param ProcessingDate Processing date	  */
	public void setProcessingDate (Timestamp ProcessingDate)
	{
		set_Value (COLUMNNAME_ProcessingDate, ProcessingDate);
	}

	/** Get Processing date.
		@return Processing date	  */
	public Timestamp getProcessingDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ProcessingDate);
	}

	public I_UY_FduFile getUY_FduFile() throws RuntimeException
    {
		return (I_UY_FduFile)MTable.get(getCtx(), I_UY_FduFile.Table_Name)
			.getPO(getUY_FduFile_ID(), get_TrxName());	}

	/** Set UY_FduFile_ID.
		@param UY_FduFile_ID UY_FduFile_ID	  */
	public void setUY_FduFile_ID (int UY_FduFile_ID)
	{
		if (UY_FduFile_ID < 1) 
			set_Value (COLUMNNAME_UY_FduFile_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduFile_ID, Integer.valueOf(UY_FduFile_ID));
	}

	/** Get UY_FduFile_ID.
		@return UY_FduFile_ID	  */
	public int getUY_FduFile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduFile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FduLoad.
		@param UY_FduLoad_ID UY_FduLoad	  */
	public void setUY_FduLoad_ID (int UY_FduLoad_ID)
	{
		if (UY_FduLoad_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FduLoad_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FduLoad_ID, Integer.valueOf(UY_FduLoad_ID));
	}

	/** Get UY_FduLoad.
		@return UY_FduLoad	  */
	public int getUY_FduLoad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduLoad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}