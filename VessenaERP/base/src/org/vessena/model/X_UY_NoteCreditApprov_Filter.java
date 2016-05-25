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

/** Generated Model for UY_NoteCreditApprov_Filter
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_NoteCreditApprov_Filter extends PO implements I_UY_NoteCreditApprov_Filter, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_NoteCreditApprov_Filter (Properties ctx, int UY_NoteCreditApprov_Filter_ID, String trxName)
    {
      super (ctx, UY_NoteCreditApprov_Filter_ID, trxName);
      /** if (UY_NoteCreditApprov_Filter_ID == 0)
        {
			setProcessed (false);
// N
			setuy_docaction_update (null);
// 'AP'
			setuy_doctype (null);
// 'NC'
			setUY_NoteCreditApprov_Filter_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_NoteCreditApprov_Filter (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_NoteCreditApprov_Filter[")
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

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** DocAction AD_Reference_ID=1000047 */
	public static final int DOCACTION_AD_Reference_ID=1000047;
	/** Aplicar = PR */
	public static final String DOCACTION_Aplicar = "PR";
	/** Generar = CO */
	public static final String DOCACTION_Generar = "CO";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** En Proceso = IP */
	public static final String DOCSTATUS_EnProceso = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Asigned = AS */
	public static final String DOCSTATUS_Asigned = "AS";
	/** Requested = RQ */
	public static final String DOCSTATUS_Requested = "RQ";
	/** Recived = RV */
	public static final String DOCSTATUS_Recived = "RV";
	/** Picking = PK */
	public static final String DOCSTATUS_Picking = "PK";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
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

	/** uy_docaction_update AD_Reference_ID=1000140 */
	public static final int UY_DOCACTION_UPDATE_AD_Reference_ID=1000140;
	/** Aprobar = AP */
	public static final String UY_DOCACTION_UPDATE_Aprobar = "AP";
	/** Completar = CO */
	public static final String UY_DOCACTION_UPDATE_Completar = "CO";
	/** Set uy_docaction_update.
		@param uy_docaction_update uy_docaction_update	  */
	public void setuy_docaction_update (String uy_docaction_update)
	{

		set_Value (COLUMNNAME_uy_docaction_update, uy_docaction_update);
	}

	/** Get uy_docaction_update.
		@return uy_docaction_update	  */
	public String getuy_docaction_update () 
	{
		return (String)get_Value(COLUMNNAME_uy_docaction_update);
	}

	/** uy_doctype AD_Reference_ID=1000156 */
	public static final int UY_DOCTYPE_AD_Reference_ID=1000156;
	/** Factura por Diferencia y Apoyo = FA */
	public static final String UY_DOCTYPE_FacturaPorDiferenciaYApoyo = "FA";
	/** Nota de Crédito por Diferencia y Apoyo = NC */
	public static final String UY_DOCTYPE_NotaDeCreditoPorDiferenciaYApoyo = "NC";
	/** Set uy_doctype.
		@param uy_doctype uy_doctype	  */
	public void setuy_doctype (String uy_doctype)
	{

		set_Value (COLUMNNAME_uy_doctype, uy_doctype);
	}

	/** Get uy_doctype.
		@return uy_doctype	  */
	public String getuy_doctype () 
	{
		return (String)get_Value(COLUMNNAME_uy_doctype);
	}

	
	
	/** Set UY_NoteCreditApprov_Filter.
		@param UY_NoteCreditApprov_Filter_ID UY_NoteCreditApprov_Filter	  */
	public void setUY_NoteCreditApprov_Filter_ID (int UY_NoteCreditApprov_Filter_ID)
	{
		if (UY_NoteCreditApprov_Filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_NoteCreditApprov_Filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_NoteCreditApprov_Filter_ID, Integer.valueOf(UY_NoteCreditApprov_Filter_ID));
	}

	/** Get UY_NoteCreditApprov_Filter.
		@return UY_NoteCreditApprov_Filter	  */
	public int getUY_NoteCreditApprov_Filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_NoteCreditApprov_Filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}