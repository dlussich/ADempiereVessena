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

/** Generated Model for UY_TR_TruckAssociation
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_TruckAssociation extends PO implements I_UY_TR_TruckAssociation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150130L;

    /** Standard Constructor */
    public X_UY_TR_TruckAssociation (Properties ctx, int UY_TR_TruckAssociation_ID, String trxName)
    {
      super (ctx, UY_TR_TruckAssociation_ID, trxName);
      /** if (UY_TR_TruckAssociation_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setProcessed (false);
			setUY_TR_Truck_ID (0);
			setUY_TR_TruckAssociation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_TruckAssociation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_TruckAssociation[")
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

	/** Set QtyKm.
		@param QtyKm 
		QtyKm
	  */
	public void setQtyKm (int QtyKm)
	{
		set_Value (COLUMNNAME_QtyKm, Integer.valueOf(QtyKm));
	}

	/** Get QtyKm.
		@return QtyKm
	  */
	public int getQtyKm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** TruckAssociationType AD_Reference_ID=1000424 */
	public static final int TRUCKASSOCIATIONTYPE_AD_Reference_ID=1000424;
	/** ASOCIAR = ASOCIAR */
	public static final String TRUCKASSOCIATIONTYPE_ASOCIAR = "ASOCIAR";
	/** DES-ASOCIAR = DES-ASOCIAR */
	public static final String TRUCKASSOCIATIONTYPE_DES_ASOCIAR = "DES-ASOCIAR";
	/** Set TruckAssociationType.
		@param TruckAssociationType 
		TruckAssociationType
	  */
	public void setTruckAssociationType (String TruckAssociationType)
	{

		set_Value (COLUMNNAME_TruckAssociationType, TruckAssociationType);
	}

	/** Get TruckAssociationType.
		@return TruckAssociationType
	  */
	public String getTruckAssociationType () 
	{
		return (String)get_Value(COLUMNNAME_TruckAssociationType);
	}

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException
    {
		return (I_UY_TR_Truck)MTable.get(getCtx(), I_UY_TR_Truck.Table_Name)
			.getPO(getUY_TR_Truck_ID(), get_TrxName());	}

	/** Set UY_TR_Truck.
		@param UY_TR_Truck_ID UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID)
	{
		if (UY_TR_Truck_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Truck_ID, Integer.valueOf(UY_TR_Truck_ID));
	}

	/** Get UY_TR_Truck.
		@return UY_TR_Truck	  */
	public int getUY_TR_Truck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck_ID_New.
		@param UY_TR_Truck_ID_New UY_TR_Truck_ID_New	  */
	public void setUY_TR_Truck_ID_New (int UY_TR_Truck_ID_New)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_New, Integer.valueOf(UY_TR_Truck_ID_New));
	}

	/** Get UY_TR_Truck_ID_New.
		@return UY_TR_Truck_ID_New	  */
	public int getUY_TR_Truck_ID_New () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_New);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck_ID_Old.
		@param UY_TR_Truck_ID_Old UY_TR_Truck_ID_Old	  */
	public void setUY_TR_Truck_ID_Old (int UY_TR_Truck_ID_Old)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_Old, Integer.valueOf(UY_TR_Truck_ID_Old));
	}

	/** Get UY_TR_Truck_ID_Old.
		@return UY_TR_Truck_ID_Old	  */
	public int getUY_TR_Truck_ID_Old () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_Old);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TruckAssociation.
		@param UY_TR_TruckAssociation_ID UY_TR_TruckAssociation	  */
	public void setUY_TR_TruckAssociation_ID (int UY_TR_TruckAssociation_ID)
	{
		if (UY_TR_TruckAssociation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TruckAssociation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TruckAssociation_ID, Integer.valueOf(UY_TR_TruckAssociation_ID));
	}

	/** Get UY_TR_TruckAssociation.
		@return UY_TR_TruckAssociation	  */
	public int getUY_TR_TruckAssociation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckAssociation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TruckType getUY_TR_TruckType() throws RuntimeException
    {
		return (I_UY_TR_TruckType)MTable.get(getCtx(), I_UY_TR_TruckType.Table_Name)
			.getPO(getUY_TR_TruckType_ID(), get_TrxName());	}

	/** Set UY_TR_TruckType.
		@param UY_TR_TruckType_ID UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID)
	{
		if (UY_TR_TruckType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TruckType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TruckType_ID, Integer.valueOf(UY_TR_TruckType_ID));
	}

	/** Get UY_TR_TruckType.
		@return UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TruckType_ID_2.
		@param UY_TR_TruckType_ID_2 UY_TR_TruckType_ID_2	  */
	public void setUY_TR_TruckType_ID_2 (int UY_TR_TruckType_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_TruckType_ID_2, Integer.valueOf(UY_TR_TruckType_ID_2));
	}

	/** Get UY_TR_TruckType_ID_2.
		@return UY_TR_TruckType_ID_2	  */
	public int getUY_TR_TruckType_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TruckType_ID_3.
		@param UY_TR_TruckType_ID_3 UY_TR_TruckType_ID_3	  */
	public void setUY_TR_TruckType_ID_3 (int UY_TR_TruckType_ID_3)
	{
		set_Value (COLUMNNAME_UY_TR_TruckType_ID_3, Integer.valueOf(UY_TR_TruckType_ID_3));
	}

	/** Get UY_TR_TruckType_ID_3.
		@return UY_TR_TruckType_ID_3	  */
	public int getUY_TR_TruckType_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}