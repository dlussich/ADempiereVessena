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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_HRNovedades
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRNovedades extends PO implements I_UY_HRNovedades, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140123L;

    /** Standard Constructor */
    public X_UY_HRNovedades (Properties ctx, int UY_HRNovedades_ID, String trxName)
    {
      super (ctx, UY_HRNovedades_ID, trxName);
      /** if (UY_HRNovedades_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_HRNovedades_ID (0);
			setUY_HRProcess_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRNovedades (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRNovedades[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	public I_UY_HRLoadDriver getUY_HRLoadDriver() throws RuntimeException
    {
		return (I_UY_HRLoadDriver)MTable.get(getCtx(), I_UY_HRLoadDriver.Table_Name)
			.getPO(getUY_HRLoadDriver_ID(), get_TrxName());	}

	/** Set UY_HRLoadDriver.
		@param UY_HRLoadDriver_ID UY_HRLoadDriver	  */
	public void setUY_HRLoadDriver_ID (int UY_HRLoadDriver_ID)
	{
		if (UY_HRLoadDriver_ID < 1) 
			set_Value (COLUMNNAME_UY_HRLoadDriver_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRLoadDriver_ID, Integer.valueOf(UY_HRLoadDriver_ID));
	}

	/** Get UY_HRLoadDriver.
		@return UY_HRLoadDriver	  */
	public int getUY_HRLoadDriver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRLoadDriver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRNovedades.
		@param UY_HRNovedades_ID UY_HRNovedades	  */
	public void setUY_HRNovedades_ID (int UY_HRNovedades_ID)
	{
		if (UY_HRNovedades_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRNovedades_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRNovedades_ID, Integer.valueOf(UY_HRNovedades_ID));
	}

	/** Get UY_HRNovedades.
		@return UY_HRNovedades	  */
	public int getUY_HRNovedades_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRNovedades_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_HRProcess getUY_HRProcess() throws RuntimeException
    {
		return (I_UY_HRProcess)MTable.get(getCtx(), I_UY_HRProcess.Table_Name)
			.getPO(getUY_HRProcess_ID(), get_TrxName());	}

	/** Set UY_HRProcess.
		@param UY_HRProcess_ID UY_HRProcess	  */
	public void setUY_HRProcess_ID (int UY_HRProcess_ID)
	{
		if (UY_HRProcess_ID < 1) 
			set_Value (COLUMNNAME_UY_HRProcess_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRProcess_ID, Integer.valueOf(UY_HRProcess_ID));
	}

	/** Get UY_HRProcess.
		@return UY_HRProcess	  */
	public int getUY_HRProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}