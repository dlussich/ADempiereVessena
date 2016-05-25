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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_TR_TireDrop
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_TireDrop extends PO implements I_UY_TR_TireDrop, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160226L;

    /** Standard Constructor */
    public X_UY_TR_TireDrop (Properties ctx, int UY_TR_TireDrop_ID, String trxName)
    {
      super (ctx, UY_TR_TireDrop_ID, trxName);
      /** if (UY_TR_TireDrop_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setMotivo (null);
			setProcessed (false);
			setUY_TR_TireDrop_ID (0);
			setUY_TR_Tire_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_TireDrop (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_TireDrop[")
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

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
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
	/** Apply = AY */
	public static final String DOCACTION_Apply = "AY";
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
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
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
	/** Applied = AY */
	public static final String DOCSTATUS_Applied = "AY";
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

	/** Motivo AD_Reference_ID=1000415 */
	public static final int MOTIVO_AD_Reference_ID=1000415;
	/** ROTURA = rotura */
	public static final String MOTIVO_ROTURA = "rotura";
	/** VENTA = venta */
	public static final String MOTIVO_VENTA = "venta";
	/** Set Motivo.
		@param Motivo 
		Motivo
	  */
	public void setMotivo (String Motivo)
	{

		set_Value (COLUMNNAME_Motivo, Motivo);
	}

	/** Get Motivo.
		@return Motivo
	  */
	public String getMotivo () 
	{
		return (String)get_Value(COLUMNNAME_Motivo);
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	/** oldstatus AD_Reference_ID=1000371 */
	public static final int OLDSTATUS_AD_Reference_ID=1000371;
	/** NUEVO = nuevo */
	public static final String OLDSTATUS_NUEVO = "nuevo";
	/** RECAUCHUTADO = primerrecauchutado */
	public static final String OLDSTATUS_RECAUCHUTADO = "primerrecauchutado";
	/** BAJA = baja */
	public static final String OLDSTATUS_BAJA = "baja";
	/** Set oldstatus.
		@param oldstatus oldstatus	  */
	public void setoldstatus (String oldstatus)
	{

		set_Value (COLUMNNAME_oldstatus, oldstatus);
	}

	/** Get oldstatus.
		@return oldstatus	  */
	public String getoldstatus () 
	{
		return (String)get_Value(COLUMNNAME_oldstatus);
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

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set QtyKm5.
		@param QtyKm5 
		QtyKm5
	  */
	public void setQtyKm5 (int QtyKm5)
	{
		set_Value (COLUMNNAME_QtyKm5, Integer.valueOf(QtyKm5));
	}

	/** Get QtyKm5.
		@return QtyKm5
	  */
	public int getQtyKm5 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm5);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TireDrop.
		@param UY_TR_TireDrop_ID UY_TR_TireDrop	  */
	public void setUY_TR_TireDrop_ID (int UY_TR_TireDrop_ID)
	{
		if (UY_TR_TireDrop_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TireDrop_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TireDrop_ID, Integer.valueOf(UY_TR_TireDrop_ID));
	}

	/** Get UY_TR_TireDrop.
		@return UY_TR_TireDrop	  */
	public int getUY_TR_TireDrop_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireDrop_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Tire getUY_TR_Tire() throws RuntimeException
    {
		return (I_UY_TR_Tire)MTable.get(getCtx(), I_UY_TR_Tire.Table_Name)
			.getPO(getUY_TR_Tire_ID(), get_TrxName());	}

	/** Set UY_TR_Tire.
		@param UY_TR_Tire_ID UY_TR_Tire	  */
	public void setUY_TR_Tire_ID (int UY_TR_Tire_ID)
	{
		if (UY_TR_Tire_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Tire_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Tire_ID, Integer.valueOf(UY_TR_Tire_ID));
	}

	/** Get UY_TR_Tire.
		@return UY_TR_Tire	  */
	public int getUY_TR_Tire_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Tire_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMark getUY_TR_TireMark() throws RuntimeException
    {
		return (I_UY_TR_TireMark)MTable.get(getCtx(), I_UY_TR_TireMark.Table_Name)
			.getPO(getUY_TR_TireMark_ID(), get_TrxName());	}

	/** Set UY_TR_TireMark.
		@param UY_TR_TireMark_ID UY_TR_TireMark	  */
	public void setUY_TR_TireMark_ID (int UY_TR_TireMark_ID)
	{
		if (UY_TR_TireMark_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMark_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMark_ID, Integer.valueOf(UY_TR_TireMark_ID));
	}

	/** Get UY_TR_TireMark.
		@return UY_TR_TireMark	  */
	public int getUY_TR_TireMark_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMark_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TireMeasure getUY_TR_TireMeasure() throws RuntimeException
    {
		return (I_UY_TR_TireMeasure)MTable.get(getCtx(), I_UY_TR_TireMeasure.Table_Name)
			.getPO(getUY_TR_TireMeasure_ID(), get_TrxName());	}

	/** Set UY_TR_TireMeasure.
		@param UY_TR_TireMeasure_ID UY_TR_TireMeasure	  */
	public void setUY_TR_TireMeasure_ID (int UY_TR_TireMeasure_ID)
	{
		if (UY_TR_TireMeasure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TireMeasure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TireMeasure_ID, Integer.valueOf(UY_TR_TireMeasure_ID));
	}

	/** Get UY_TR_TireMeasure.
		@return UY_TR_TireMeasure	  */
	public int getUY_TR_TireMeasure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TireMeasure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}