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

import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for UY_CierreTransporteHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CierreTransporteHdr extends PO implements I_UY_CierreTransporteHdr, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CierreTransporteHdr (Properties ctx, int UY_CierreTransporteHdr_ID, String trxName)
    {
      super (ctx, UY_CierreTransporteHdr_ID, trxName);
      /** if (UY_CierreTransporteHdr_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setProcessed (false);
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setUY_AsignaTransporteHdr_ID (0);
			setUY_CierreTransporteHdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CierreTransporteHdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CierreTransporteHdr[")
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Request = RQ */
	public static final String DOCACTION_Request = "RQ";
	/** Asign = AS */
	public static final String DOCACTION_Asign = "AS";
	/** Pick = PK */
	public static final String DOCACTION_Pick = "PK";
	/** Recive = RV */
	public static final String DOCACTION_Recive = "RV";
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
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

	/** Set uy_bultosapagar.
		@param uy_bultosapagar uy_bultosapagar	  */
	public void setuy_bultosapagar (int uy_bultosapagar)
	{
		set_Value (COLUMNNAME_uy_bultosapagar, Integer.valueOf(uy_bultosapagar));
	}

	/** Get uy_bultosapagar.
		@return uy_bultosapagar	  */
	public int getuy_bultosapagar () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_bultosapagar);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CierreTransporteHdr.
		@param UY_CierreTransporteHdr_ID UY_CierreTransporteHdr	  */
	public void setUY_CierreTransporteHdr_ID (int UY_CierreTransporteHdr_ID)
	{
		if (UY_CierreTransporteHdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CierreTransporteHdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CierreTransporteHdr_ID, Integer.valueOf(UY_CierreTransporteHdr_ID));
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

	/*
	 * public I_UY_Drivers getUY_Drivers() throws RuntimeException { return
	 * (I_UY_Drivers)MTable.get(getCtx(), I_UY_Drivers.Table_Name)
	 * .getPO(getUY_Drivers_ID(), get_TrxName()); }
	 */

	/** Set UY_Drivers.
		@param UY_Drivers_ID UY_Drivers	  */
	public void setUY_Drivers_ID (int UY_Drivers_ID)
	{
		if (UY_Drivers_ID < 1) 
			set_Value (COLUMNNAME_UY_Drivers_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Drivers_ID, Integer.valueOf(UY_Drivers_ID));
	}

	/** Get UY_Drivers.
		@return UY_Drivers	  */
	public int getUY_Drivers_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Drivers_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_fechavalores.
		@param uy_fechavalores uy_fechavalores	  */
	public void setuy_fechavalores (Timestamp uy_fechavalores)
	{
		set_Value (COLUMNNAME_uy_fechavalores, uy_fechavalores);
	}

	/** Get uy_fechavalores.
		@return uy_fechavalores	  */
	public Timestamp getuy_fechavalores () 
	{
		return (Timestamp)get_Value(COLUMNNAME_uy_fechavalores);
	}

	/** Set uy_km_fin.
		@param uy_km_fin uy_km_fin	  */
	public void setuy_km_fin (int uy_km_fin)
	{
		set_Value (COLUMNNAME_uy_km_fin, Integer.valueOf(uy_km_fin));
	}

	/** Get uy_km_fin.
		@return uy_km_fin	  */
	public int getuy_km_fin () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_km_fin);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_km_inicio.
		@param uy_km_inicio uy_km_inicio	  */
	public void setuy_km_inicio (int uy_km_inicio)
	{
		set_Value (COLUMNNAME_uy_km_inicio, Integer.valueOf(uy_km_inicio));
	}

	/** Get uy_km_inicio.
		@return uy_km_inicio	  */
	public int getuy_km_inicio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_km_inicio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Vincular_Cobranza.
		@param UY_Vincular_Cobranza UY_Vincular_Cobranza	  */
	public void setUY_Vincular_Cobranza (String UY_Vincular_Cobranza)
	{
		set_Value (COLUMNNAME_UY_Vincular_Cobranza, UY_Vincular_Cobranza);
	}

	/** Get UY_Vincular_Cobranza.
		@return UY_Vincular_Cobranza	  */
	public String getUY_Vincular_Cobranza () 
	{
		return (String)get_Value(COLUMNNAME_UY_Vincular_Cobranza);
	}

	/** Set UY_Vincular_DevDirectas.
		@param UY_Vincular_DevDirectas UY_Vincular_DevDirectas	  */
	public void setUY_Vincular_DevDirectas (String UY_Vincular_DevDirectas)
	{
		set_Value (COLUMNNAME_UY_Vincular_DevDirectas, UY_Vincular_DevDirectas);
	}

	/** Get UY_Vincular_DevDirectas.
		@return UY_Vincular_DevDirectas	  */
	public String getUY_Vincular_DevDirectas () 
	{
		return (String)get_Value(COLUMNNAME_UY_Vincular_DevDirectas);
	}
}