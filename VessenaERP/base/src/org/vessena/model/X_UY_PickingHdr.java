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

/** Generated Model for UY_PickingHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_PickingHdr extends PO implements I_UY_PickingHdr, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_PickingHdr (Properties ctx, int UY_PickingHdr_ID, String trxName)
    {
      super (ctx, UY_PickingHdr_ID, trxName);
      /** if (UY_PickingHdr_ID == 0)
        {
			setC_DocType_ID (0);
			setUY_AsignaTransporteHdr_ID (0);
			setUY_PickingHdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PickingHdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PickingHdr[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_M_Shipper getM_Shipper() throws RuntimeException
    {
		return (I_M_Shipper)MTable.get(getCtx(), I_M_Shipper.Table_Name)
			.getPO(getM_Shipper_ID(), get_TrxName());	}

	/** Set Shipper.
		@param M_Shipper_ID 
		Method or manner of product delivery
	  */
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Shipper.
		@return Method or manner of product delivery
	  */
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException
    {
		return (I_UY_AsignaTransporteHdr)MTable.get(getCtx(), I_UY_AsignaTransporteHdr.Table_Name)
			.getPO(getUY_AsignaTransporteHdr_ID(), get_TrxName());	}

	/** Set UY_AsignaTransporteHdr_ID.
		@param UY_AsignaTransporteHdr_ID UY_AsignaTransporteHdr_ID	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID)
	{
		if (UY_AsignaTransporteHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_AsignaTransporteHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AsignaTransporteHdr_ID, Integer.valueOf(UY_AsignaTransporteHdr_ID));
	}

	/** Get UY_AsignaTransporteHdr_ID.
		@return UY_AsignaTransporteHdr_ID	  */
	public int getUY_AsignaTransporteHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AsignaTransporteHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_imprimepickfunda.
		@param uy_imprimepickfunda uy_imprimepickfunda	  */
	public void setuy_imprimepickfunda (String uy_imprimepickfunda)
	{
		set_Value (COLUMNNAME_uy_imprimepickfunda, uy_imprimepickfunda);
	}

	/** Get uy_imprimepickfunda.
		@return uy_imprimepickfunda	  */
	public String getuy_imprimepickfunda () 
	{
		return (String)get_Value(COLUMNNAME_uy_imprimepickfunda);
	}

	/** Set uy_imprimepickunidad.
		@param uy_imprimepickunidad uy_imprimepickunidad	  */
	public void setuy_imprimepickunidad (String uy_imprimepickunidad)
	{
		set_Value (COLUMNNAME_uy_imprimepickunidad, uy_imprimepickunidad);
	}

	/** Get uy_imprimepickunidad.
		@return uy_imprimepickunidad	  */
	public String getuy_imprimepickunidad () 
	{
		return (String)get_Value(COLUMNNAME_uy_imprimepickunidad);
	}

	/** Set UY_PickingHdr_ID.
		@param UY_PickingHdr_ID UY_PickingHdr_ID	  */
	public void setUY_PickingHdr_ID (int UY_PickingHdr_ID)
	{
		if (UY_PickingHdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PickingHdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PickingHdr_ID, Integer.valueOf(UY_PickingHdr_ID));
	}

	/** Get UY_PickingHdr_ID.
		@return UY_PickingHdr_ID	  */
	public int getUY_PickingHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PickingHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_reportesociofundas.
		@param uy_reportesociofundas uy_reportesociofundas	  */
	public void setuy_reportesociofundas (String uy_reportesociofundas)
	{
		set_Value (COLUMNNAME_uy_reportesociofundas, uy_reportesociofundas);
	}

	/** Get uy_reportesociofundas.
		@return uy_reportesociofundas	  */
	public String getuy_reportesociofundas () 
	{
		return (String)get_Value(COLUMNNAME_uy_reportesociofundas);
	}

	/** Set uy_reportesociounidades.
		@param uy_reportesociounidades uy_reportesociounidades	  */
	public void setuy_reportesociounidades (String uy_reportesociounidades)
	{
		set_Value (COLUMNNAME_uy_reportesociounidades, uy_reportesociounidades);
	}

	/** Get uy_reportesociounidades.
		@return uy_reportesociounidades	  */
	public String getuy_reportesociounidades () 
	{
		return (String)get_Value(COLUMNNAME_uy_reportesociounidades);
	}
}