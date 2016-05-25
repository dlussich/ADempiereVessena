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

/** Generated Model for UY_CFE_Invoice
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_Invoice extends PO implements I_UY_CFE_Invoice, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140818L;

    /** Standard Constructor */
    public X_UY_CFE_Invoice (Properties ctx, int UY_CFE_Invoice_ID, String trxName)
    {
      super (ctx, UY_CFE_Invoice_ID, trxName);
      /** if (UY_CFE_Invoice_ID == 0)
        {
			setUY_CFE_Invoice_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_Invoice (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_Invoice[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** CFEStatusType AD_Reference_ID=1000365 */
	public static final int CFESTATUSTYPE_AD_Reference_ID=1000365;
	/** 1 - No procesado = 1 */
	public static final String CFESTATUSTYPE_1_NoProcesado = "1";
	/** 2 - En procesamiento = 2 */
	public static final String CFESTATUSTYPE_2_EnProcesamiento = "2";
	/** 3 - Rechazado = 3 */
	public static final String CFESTATUSTYPE_3_Rechazado = "3";
	/** 4 - En Espera = 4 */
	public static final String CFESTATUSTYPE_4_EnEspera = "4";
	/** 5 - Autorizado = 5 */
	public static final String CFESTATUSTYPE_5_Autorizado = "5";
	/** 6 - Anulado = 6 */
	public static final String CFESTATUSTYPE_6_Anulado = "6";
	/** 8 - Rechazado por DGI = 8 */
	public static final String CFESTATUSTYPE_8_RechazadoPorDGI = "8";
	/** 9 - En Digitacion = 9 */
	public static final String CFESTATUSTYPE_9_EnDigitacion = "9";
	/** Set CFEStatusType.
		@param CFEStatusType CFEStatusType	  */
	public void setCFEStatusType (String CFEStatusType)
	{

		set_Value (COLUMNNAME_CFEStatusType, CFEStatusType);
	}

	/** Get CFEStatusType.
		@return CFEStatusType	  */
	public String getCFEStatusType () 
	{
		return (String)get_Value(COLUMNNAME_CFEStatusType);
	}

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set UY_CFE_Invoice.
		@param UY_CFE_Invoice_ID UY_CFE_Invoice	  */
	public void setUY_CFE_Invoice_ID (int UY_CFE_Invoice_ID)
	{
		if (UY_CFE_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_Invoice_ID, Integer.valueOf(UY_CFE_Invoice_ID));
	}

	/** Get UY_CFE_Invoice.
		@return UY_CFE_Invoice	  */
	public int getUY_CFE_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}