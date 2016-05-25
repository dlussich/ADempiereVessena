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

/** Generated Model for UY_TT_Contract
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_Contract extends PO implements I_UY_TT_Contract, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151014L;

    /** Standard Constructor */
    public X_UY_TT_Contract (Properties ctx, int UY_TT_Contract_ID, String trxName)
    {
      super (ctx, UY_TT_Contract_ID, trxName);
      /** if (UY_TT_Contract_ID == 0)
        {
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsDelivered (false);
// N
			setIsDelivered1 (false);
// N
			setIsDelivered2 (false);
// N
			setIsDelivered3 (false);
// N
			setIsDelivered4 (false);
// N
			setIsDelivered5 (false);
// N
			setPrintDoc1 (false);
// N
			setPrintDoc2 (false);
// N
			setPrintDoc3 (false);
// N
			setPrintDoc4 (false);
// N
			setPrintDoc5 (false);
// N
			setProcessed (false);
			setScanText (null);
			setUY_TT_Box_ID (0);
			setUY_TT_Contract_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_Contract (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_Contract[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
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

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
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

	/** Set Delivered.
		@param IsDelivered Delivered	  */
	public void setIsDelivered (boolean IsDelivered)
	{
		set_Value (COLUMNNAME_IsDelivered, Boolean.valueOf(IsDelivered));
	}

	/** Get Delivered.
		@return Delivered	  */
	public boolean isDelivered () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDelivered1.
		@param IsDelivered1 IsDelivered1	  */
	public void setIsDelivered1 (boolean IsDelivered1)
	{
		set_Value (COLUMNNAME_IsDelivered1, Boolean.valueOf(IsDelivered1));
	}

	/** Get IsDelivered1.
		@return IsDelivered1	  */
	public boolean isDelivered1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDelivered2.
		@param IsDelivered2 IsDelivered2	  */
	public void setIsDelivered2 (boolean IsDelivered2)
	{
		set_Value (COLUMNNAME_IsDelivered2, Boolean.valueOf(IsDelivered2));
	}

	/** Get IsDelivered2.
		@return IsDelivered2	  */
	public boolean isDelivered2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDelivered3.
		@param IsDelivered3 IsDelivered3	  */
	public void setIsDelivered3 (boolean IsDelivered3)
	{
		set_Value (COLUMNNAME_IsDelivered3, Boolean.valueOf(IsDelivered3));
	}

	/** Get IsDelivered3.
		@return IsDelivered3	  */
	public boolean isDelivered3 () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered3);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDelivered4.
		@param IsDelivered4 IsDelivered4	  */
	public void setIsDelivered4 (boolean IsDelivered4)
	{
		set_Value (COLUMNNAME_IsDelivered4, Boolean.valueOf(IsDelivered4));
	}

	/** Get IsDelivered4.
		@return IsDelivered4	  */
	public boolean isDelivered4 () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered4);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDelivered5.
		@param IsDelivered5 IsDelivered5	  */
	public void setIsDelivered5 (boolean IsDelivered5)
	{
		set_Value (COLUMNNAME_IsDelivered5, Boolean.valueOf(IsDelivered5));
	}

	/** Get IsDelivered5.
		@return IsDelivered5	  */
	public boolean isDelivered5 () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered5);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, Integer.valueOf(LocatorValue));
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public int getLocatorValue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LocatorValue);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set PrintDoc1.
		@param PrintDoc1 PrintDoc1	  */
	public void setPrintDoc1 (boolean PrintDoc1)
	{
		set_Value (COLUMNNAME_PrintDoc1, Boolean.valueOf(PrintDoc1));
	}

	/** Get PrintDoc1.
		@return PrintDoc1	  */
	public boolean isPrintDoc1 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PrintDoc2.
		@param PrintDoc2 
		PrintDoc2
	  */
	public void setPrintDoc2 (boolean PrintDoc2)
	{
		set_Value (COLUMNNAME_PrintDoc2, Boolean.valueOf(PrintDoc2));
	}

	/** Get PrintDoc2.
		@return PrintDoc2
	  */
	public boolean isPrintDoc2 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PrintDoc3.
		@param PrintDoc3 
		PrintDoc3
	  */
	public void setPrintDoc3 (boolean PrintDoc3)
	{
		set_Value (COLUMNNAME_PrintDoc3, Boolean.valueOf(PrintDoc3));
	}

	/** Get PrintDoc3.
		@return PrintDoc3
	  */
	public boolean isPrintDoc3 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc3);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PrintDoc4.
		@param PrintDoc4 
		PrintDoc4
	  */
	public void setPrintDoc4 (boolean PrintDoc4)
	{
		set_Value (COLUMNNAME_PrintDoc4, Boolean.valueOf(PrintDoc4));
	}

	/** Get PrintDoc4.
		@return PrintDoc4
	  */
	public boolean isPrintDoc4 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc4);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PrintDoc5.
		@param PrintDoc5 PrintDoc5	  */
	public void setPrintDoc5 (boolean PrintDoc5)
	{
		set_Value (COLUMNNAME_PrintDoc5, Boolean.valueOf(PrintDoc5));
	}

	/** Get PrintDoc5.
		@return PrintDoc5	  */
	public boolean isPrintDoc5 () 
	{
		Object oo = get_Value(COLUMNNAME_PrintDoc5);
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

	/** Set ScanText.
		@param ScanText ScanText	  */
	public void setScanText (String ScanText)
	{
		set_Value (COLUMNNAME_ScanText, ScanText);
	}

	/** Get ScanText.
		@return ScanText	  */
	public String getScanText () 
	{
		return (String)get_Value(COLUMNNAME_ScanText);
	}

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID(), get_TrxName());	}

	/** Set UY_DeliveryPoint.
		@param UY_DeliveryPoint_ID UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID)
	{
		if (UY_DeliveryPoint_ID < 1) 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, Integer.valueOf(UY_DeliveryPoint_ID));
	}

	/** Get UY_DeliveryPoint.
		@return UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException
    {
		return (I_UY_R_Reclamo)MTable.get(getCtx(), I_UY_R_Reclamo.Table_Name)
			.getPO(getUY_R_Reclamo_ID(), get_TrxName());	}

	/** Set UY_R_Reclamo.
		@param UY_R_Reclamo_ID UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID)
	{
		if (UY_R_Reclamo_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, Integer.valueOf(UY_R_Reclamo_ID));
	}

	/** Get UY_R_Reclamo.
		@return UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Reclamo_ID_2.
		@param UY_R_Reclamo_ID_2 UY_R_Reclamo_ID_2	  */
	public void setUY_R_Reclamo_ID_2 (int UY_R_Reclamo_ID_2)
	{
		set_Value (COLUMNNAME_UY_R_Reclamo_ID_2, Integer.valueOf(UY_R_Reclamo_ID_2));
	}

	/** Get UY_R_Reclamo_ID_2.
		@return UY_R_Reclamo_ID_2	  */
	public int getUY_R_Reclamo_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException
    {
		return (I_UY_TT_Box)MTable.get(getCtx(), I_UY_TT_Box.Table_Name)
			.getPO(getUY_TT_Box_ID(), get_TrxName());	}

	/** Set UY_TT_Box.
		@param UY_TT_Box_ID UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID)
	{
		if (UY_TT_Box_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Box_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Box_ID, Integer.valueOf(UY_TT_Box_ID));
	}

	/** Get UY_TT_Box.
		@return UY_TT_Box	  */
	public int getUY_TT_Box_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_Contract.
		@param UY_TT_Contract_ID UY_TT_Contract	  */
	public void setUY_TT_Contract_ID (int UY_TT_Contract_ID)
	{
		if (UY_TT_Contract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Contract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Contract_ID, Integer.valueOf(UY_TT_Contract_ID));
	}

	/** Get UY_TT_Contract.
		@return UY_TT_Contract	  */
	public int getUY_TT_Contract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Contract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}