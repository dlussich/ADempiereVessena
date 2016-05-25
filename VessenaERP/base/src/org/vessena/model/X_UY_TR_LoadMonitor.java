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

/** Generated Model for UY_TR_LoadMonitor
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_LoadMonitor extends PO implements I_UY_TR_LoadMonitor, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141223L;

    /** Standard Constructor */
    public X_UY_TR_LoadMonitor (Properties ctx, int UY_TR_LoadMonitor_ID, String trxName)
    {
      super (ctx, UY_TR_LoadMonitor_ID, trxName);
      /** if (UY_TR_LoadMonitor_ID == 0)
        {
			setC_DocType_ID (0);
			setChangeDriver (false);
// N
			setChangeTruck (false);
// N
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// AY
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setHandleCRT (true);
// Y
			setIsAssociated (true);
// Y
			setIsAssociated2 (true);
// Y
			setIsBeforeLoad (false);
// N
			setIsConfirmation (false);
// N
			setIsDelivered (false);
// N
			setIsLastre (false);
// N
			setIsOwn (true);
// Y
			setIsOwn2 (true);
// Y
			setIsPlanification (false);
// N
			setIsTrasbordo (false);
// N
			setIsWarehouseRequired (false);
// N
			setLoadProduct (false);
// N
			setOnMove (false);
// N
			setProcessed (false);
// N
			setUnloadProduct (false);
// N
			setUY_TR_LoadMonitor_ID (0);
			setUY_TR_LoadStatus_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_LoadMonitor (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_LoadMonitor[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Currency_ID_2.
		@param C_Currency_ID_2 C_Currency_ID_2	  */
	public void setC_Currency_ID_2 (int C_Currency_ID_2)
	{
		set_Value (COLUMNNAME_C_Currency_ID_2, Integer.valueOf(C_Currency_ID_2));
	}

	/** Get C_Currency_ID_2.
		@return C_Currency_ID_2	  */
	public int getC_Currency_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Currency_ID_3.
		@param C_Currency_ID_3 C_Currency_ID_3	  */
	public void setC_Currency_ID_3 (int C_Currency_ID_3)
	{
		set_Value (COLUMNNAME_C_Currency_ID_3, Integer.valueOf(C_Currency_ID_3));
	}

	/** Get C_Currency_ID_3.
		@return C_Currency_ID_3	  */
	public int getC_Currency_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_3);
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

	/** Set ChangeDriver.
		@param ChangeDriver ChangeDriver	  */
	public void setChangeDriver (boolean ChangeDriver)
	{
		set_Value (COLUMNNAME_ChangeDriver, Boolean.valueOf(ChangeDriver));
	}

	/** Get ChangeDriver.
		@return ChangeDriver	  */
	public boolean isChangeDriver () 
	{
		Object oo = get_Value(COLUMNNAME_ChangeDriver);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ChangeTruck.
		@param ChangeTruck ChangeTruck	  */
	public void setChangeTruck (boolean ChangeTruck)
	{
		set_Value (COLUMNNAME_ChangeTruck, Boolean.valueOf(ChangeTruck));
	}

	/** Get ChangeTruck.
		@return ChangeTruck	  */
	public boolean isChangeTruck () 
	{
		Object oo = get_Value(COLUMNNAME_ChangeTruck);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set HandleCRT.
		@param HandleCRT HandleCRT	  */
	public void setHandleCRT (boolean HandleCRT)
	{
		set_Value (COLUMNNAME_HandleCRT, Boolean.valueOf(HandleCRT));
	}

	/** Get HandleCRT.
		@return HandleCRT	  */
	public boolean isHandleCRT () 
	{
		Object oo = get_Value(COLUMNNAME_HandleCRT);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsAssociated.
		@param IsAssociated 
		IsAssociated
	  */
	public void setIsAssociated (boolean IsAssociated)
	{
		set_Value (COLUMNNAME_IsAssociated, Boolean.valueOf(IsAssociated));
	}

	/** Get IsAssociated.
		@return IsAssociated
	  */
	public boolean isAssociated () 
	{
		Object oo = get_Value(COLUMNNAME_IsAssociated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsAssociated2.
		@param IsAssociated2 IsAssociated2	  */
	public void setIsAssociated2 (boolean IsAssociated2)
	{
		set_Value (COLUMNNAME_IsAssociated2, Boolean.valueOf(IsAssociated2));
	}

	/** Get IsAssociated2.
		@return IsAssociated2	  */
	public boolean isAssociated2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsAssociated2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsBeforeLoad.
		@param IsBeforeLoad IsBeforeLoad	  */
	public void setIsBeforeLoad (boolean IsBeforeLoad)
	{
		set_Value (COLUMNNAME_IsBeforeLoad, Boolean.valueOf(IsBeforeLoad));
	}

	/** Get IsBeforeLoad.
		@return IsBeforeLoad	  */
	public boolean isBeforeLoad () 
	{
		Object oo = get_Value(COLUMNNAME_IsBeforeLoad);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsConfirmation.
		@param IsConfirmation IsConfirmation	  */
	public void setIsConfirmation (boolean IsConfirmation)
	{
		set_Value (COLUMNNAME_IsConfirmation, Boolean.valueOf(IsConfirmation));
	}

	/** Get IsConfirmation.
		@return IsConfirmation	  */
	public boolean isConfirmation () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfirmation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set IsLastre.
		@param IsLastre IsLastre	  */
	public void setIsLastre (boolean IsLastre)
	{
		set_Value (COLUMNNAME_IsLastre, Boolean.valueOf(IsLastre));
	}

	/** Get IsLastre.
		@return IsLastre	  */
	public boolean isLastre () 
	{
		Object oo = get_Value(COLUMNNAME_IsLastre);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsOwn.
		@param IsOwn IsOwn	  */
	public void setIsOwn (boolean IsOwn)
	{
		set_Value (COLUMNNAME_IsOwn, Boolean.valueOf(IsOwn));
	}

	/** Get IsOwn.
		@return IsOwn	  */
	public boolean isOwn () 
	{
		Object oo = get_Value(COLUMNNAME_IsOwn);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsOwn2.
		@param IsOwn2 IsOwn2	  */
	public void setIsOwn2 (boolean IsOwn2)
	{
		set_Value (COLUMNNAME_IsOwn2, Boolean.valueOf(IsOwn2));
	}

	/** Get IsOwn2.
		@return IsOwn2	  */
	public boolean isOwn2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsOwn2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPlanification.
		@param IsPlanification IsPlanification	  */
	public void setIsPlanification (boolean IsPlanification)
	{
		set_Value (COLUMNNAME_IsPlanification, Boolean.valueOf(IsPlanification));
	}

	/** Get IsPlanification.
		@return IsPlanification	  */
	public boolean isPlanification () 
	{
		Object oo = get_Value(COLUMNNAME_IsPlanification);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsTrasbordo.
		@param IsTrasbordo IsTrasbordo	  */
	public void setIsTrasbordo (boolean IsTrasbordo)
	{
		set_Value (COLUMNNAME_IsTrasbordo, Boolean.valueOf(IsTrasbordo));
	}

	/** Get IsTrasbordo.
		@return IsTrasbordo	  */
	public boolean isTrasbordo () 
	{
		Object oo = get_Value(COLUMNNAME_IsTrasbordo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsWarehouseRequired.
		@param IsWarehouseRequired 
		IsWarehouseRequired
	  */
	public void setIsWarehouseRequired (boolean IsWarehouseRequired)
	{
		set_Value (COLUMNNAME_IsWarehouseRequired, Boolean.valueOf(IsWarehouseRequired));
	}

	/** Get IsWarehouseRequired.
		@return IsWarehouseRequired
	  */
	public boolean isWarehouseRequired () 
	{
		Object oo = get_Value(COLUMNNAME_IsWarehouseRequired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LoadProduct.
		@param LoadProduct LoadProduct	  */
	public void setLoadProduct (boolean LoadProduct)
	{
		set_Value (COLUMNNAME_LoadProduct, Boolean.valueOf(LoadProduct));
	}

	/** Get LoadProduct.
		@return LoadProduct	  */
	public boolean isLoadProduct () 
	{
		Object oo = get_Value(COLUMNNAME_LoadProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set OnMove.
		@param OnMove 
		OnMove
	  */
	public void setOnMove (boolean OnMove)
	{
		set_Value (COLUMNNAME_OnMove, Boolean.valueOf(OnMove));
	}

	/** Get OnMove.
		@return OnMove
	  */
	public boolean isOnMove () 
	{
		Object oo = get_Value(COLUMNNAME_OnMove);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PayAmt2.
		@param PayAmt2 PayAmt2	  */
	public void setPayAmt2 (BigDecimal PayAmt2)
	{
		set_Value (COLUMNNAME_PayAmt2, PayAmt2);
	}

	/** Get PayAmt2.
		@return PayAmt2	  */
	public BigDecimal getPayAmt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Remolque_ID.
		@param Remolque_ID 
		Remolque_ID
	  */
	public void setRemolque_ID (int Remolque_ID)
	{
		if (Remolque_ID < 1) 
			set_Value (COLUMNNAME_Remolque_ID, null);
		else 
			set_Value (COLUMNNAME_Remolque_ID, Integer.valueOf(Remolque_ID));
	}

	/** Get Remolque_ID.
		@return Remolque_ID
	  */
	public int getRemolque_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Remolque_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Remolque_ID_2.
		@param Remolque_ID_2 Remolque_ID_2	  */
	public void setRemolque_ID_2 (int Remolque_ID_2)
	{
		set_Value (COLUMNNAME_Remolque_ID_2, Integer.valueOf(Remolque_ID_2));
	}

	/** Get Remolque_ID_2.
		@return Remolque_ID_2	  */
	public int getRemolque_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Remolque_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalWeight.
		@param TotalWeight TotalWeight	  */
	public void setTotalWeight (BigDecimal TotalWeight)
	{
		set_Value (COLUMNNAME_TotalWeight, TotalWeight);
	}

	/** Get TotalWeight.
		@return TotalWeight	  */
	public BigDecimal getTotalWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWeight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalWeight2.
		@param TotalWeight2 TotalWeight2	  */
	public void setTotalWeight2 (BigDecimal TotalWeight2)
	{
		set_Value (COLUMNNAME_TotalWeight2, TotalWeight2);
	}

	/** Get TotalWeight2.
		@return TotalWeight2	  */
	public BigDecimal getTotalWeight2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWeight2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tractor_ID.
		@param Tractor_ID Tractor_ID	  */
	public void setTractor_ID (int Tractor_ID)
	{
		if (Tractor_ID < 1) 
			set_Value (COLUMNNAME_Tractor_ID, null);
		else 
			set_Value (COLUMNNAME_Tractor_ID, Integer.valueOf(Tractor_ID));
	}

	/** Get Tractor_ID.
		@return Tractor_ID	  */
	public int getTractor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Tractor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tractor_ID_2.
		@param Tractor_ID_2 Tractor_ID_2	  */
	public void setTractor_ID_2 (int Tractor_ID_2)
	{
		set_Value (COLUMNNAME_Tractor_ID_2, Integer.valueOf(Tractor_ID_2));
	}

	/** Get Tractor_ID_2.
		@return Tractor_ID_2	  */
	public int getTractor_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Tractor_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** TypeLoad AD_Reference_ID=1000463 */
	public static final int TYPELOAD_AD_Reference_ID=1000463;
	/** CLIENTE = CLIENTE */
	public static final String TYPELOAD_CLIENTE = "CLIENTE";
	/** ALMACEN PROPIO = ALMACEN */
	public static final String TYPELOAD_ALMACENPROPIO = "ALMACEN";
	/** Set TypeLoad.
		@param TypeLoad TypeLoad	  */
	public void setTypeLoad (String TypeLoad)
	{

		set_Value (COLUMNNAME_TypeLoad, TypeLoad);
	}

	/** Get TypeLoad.
		@return TypeLoad	  */
	public String getTypeLoad () 
	{
		return (String)get_Value(COLUMNNAME_TypeLoad);
	}

	/** Set UnloadProduct.
		@param UnloadProduct 
		UnloadProduct
	  */
	public void setUnloadProduct (boolean UnloadProduct)
	{
		set_Value (COLUMNNAME_UnloadProduct, Boolean.valueOf(UnloadProduct));
	}

	/** Get UnloadProduct.
		@return UnloadProduct
	  */
	public boolean isUnloadProduct () 
	{
		Object oo = get_Value(COLUMNNAME_UnloadProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException
    {
		return (I_UY_Ciudad)MTable.get(getCtx(), I_UY_Ciudad.Table_Name)
			.getPO(getUY_Ciudad_ID(), get_TrxName());	}

	/** Set UY_Ciudad.
		@param UY_Ciudad_ID UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID)
	{
		if (UY_Ciudad_ID < 1) 
			set_Value (COLUMNNAME_UY_Ciudad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Ciudad_ID, Integer.valueOf(UY_Ciudad_ID));
	}

	/** Get UY_Ciudad.
		@return UY_Ciudad	  */
	public int getUY_Ciudad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_1.
		@param UY_Ciudad_ID_1 UY_Ciudad_ID_1	  */
	public void setUY_Ciudad_ID_1 (int UY_Ciudad_ID_1)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_1, Integer.valueOf(UY_Ciudad_ID_1));
	}

	/** Get UY_Ciudad_ID_1.
		@return UY_Ciudad_ID_1	  */
	public int getUY_Ciudad_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_2.
		@param UY_Ciudad_ID_2 UY_Ciudad_ID_2	  */
	public void setUY_Ciudad_ID_2 (int UY_Ciudad_ID_2)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_2, Integer.valueOf(UY_Ciudad_ID_2));
	}

	/** Get UY_Ciudad_ID_2.
		@return UY_Ciudad_ID_2	  */
	public int getUY_Ciudad_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Ciudad_ID_3.
		@param UY_Ciudad_ID_3 UY_Ciudad_ID_3	  */
	public void setUY_Ciudad_ID_3 (int UY_Ciudad_ID_3)
	{
		set_Value (COLUMNNAME_UY_Ciudad_ID_3, Integer.valueOf(UY_Ciudad_ID_3));
	}

	/** Get UY_Ciudad_ID_3.
		@return UY_Ciudad_ID_3	  */
	public int getUY_Ciudad_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Driver getUY_TR_Driver() throws RuntimeException
    {
		return (I_UY_TR_Driver)MTable.get(getCtx(), I_UY_TR_Driver.Table_Name)
			.getPO(getUY_TR_Driver_ID(), get_TrxName());	}

	/** Set UY_TR_Driver.
		@param UY_TR_Driver_ID UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID)
	{
		if (UY_TR_Driver_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Driver_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Driver_ID, Integer.valueOf(UY_TR_Driver_ID));
	}

	/** Get UY_TR_Driver.
		@return UY_TR_Driver	  */
	public int getUY_TR_Driver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Driver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Driver_ID_2.
		@param UY_TR_Driver_ID_2 UY_TR_Driver_ID_2	  */
	public void setUY_TR_Driver_ID_2 (int UY_TR_Driver_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_Driver_ID_2, Integer.valueOf(UY_TR_Driver_ID_2));
	}

	/** Get UY_TR_Driver_ID_2.
		@return UY_TR_Driver_ID_2	  */
	public int getUY_TR_Driver_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Driver_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_LoadMonitor.
		@param UY_TR_LoadMonitor_ID UY_TR_LoadMonitor	  */
	public void setUY_TR_LoadMonitor_ID (int UY_TR_LoadMonitor_ID)
	{
		if (UY_TR_LoadMonitor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMonitor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMonitor_ID, Integer.valueOf(UY_TR_LoadMonitor_ID));
	}

	/** Get UY_TR_LoadMonitor.
		@return UY_TR_LoadMonitor	  */
	public int getUY_TR_LoadMonitor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMonitor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_LoadStatus getUY_TR_LoadStatus() throws RuntimeException
    {
		return (I_UY_TR_LoadStatus)MTable.get(getCtx(), I_UY_TR_LoadStatus.Table_Name)
			.getPO(getUY_TR_LoadStatus_ID(), get_TrxName());	}

	/** Set UY_TR_LoadStatus.
		@param UY_TR_LoadStatus_ID UY_TR_LoadStatus	  */
	public void setUY_TR_LoadStatus_ID (int UY_TR_LoadStatus_ID)
	{
		if (UY_TR_LoadStatus_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_LoadStatus_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_LoadStatus_ID, Integer.valueOf(UY_TR_LoadStatus_ID));
	}

	/** Get UY_TR_LoadStatus.
		@return UY_TR_LoadStatus	  */
	public int getUY_TR_LoadStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException
    {
		return (I_UY_TR_TransOrder)MTable.get(getCtx(), I_UY_TR_TransOrder.Table_Name)
			.getPO(getUY_TR_TransOrder_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrder.
		@param UY_TR_TransOrder_ID UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID)
	{
		if (UY_TR_TransOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, Integer.valueOf(UY_TR_TransOrder_ID));
	}

	/** Get UY_TR_TransOrder.
		@return UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TransOrder_ID_2.
		@param UY_TR_TransOrder_ID_2 UY_TR_TransOrder_ID_2	  */
	public void setUY_TR_TransOrder_ID_2 (int UY_TR_TransOrder_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_TransOrder_ID_2, Integer.valueOf(UY_TR_TransOrder_ID_2));
	}

	/** Get UY_TR_TransOrder_ID_2.
		@return UY_TR_TransOrder_ID_2	  */
	public int getUY_TR_TransOrder_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrder_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UY_TR_Truck_ID_2.
		@param UY_TR_Truck_ID_2 UY_TR_Truck_ID_2	  */
	public void setUY_TR_Truck_ID_2 (int UY_TR_Truck_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_2, Integer.valueOf(UY_TR_Truck_ID_2));
	}

	/** Get UY_TR_Truck_ID_2.
		@return UY_TR_Truck_ID_2	  */
	public int getUY_TR_Truck_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck_ID_3.
		@param UY_TR_Truck_ID_3 UY_TR_Truck_ID_3	  */
	public void setUY_TR_Truck_ID_3 (int UY_TR_Truck_ID_3)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_3, Integer.valueOf(UY_TR_Truck_ID_3));
	}

	/** Get UY_TR_Truck_ID_3.
		@return UY_TR_Truck_ID_3	  */
	public int getUY_TR_Truck_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck_ID_4.
		@param UY_TR_Truck_ID_4 UY_TR_Truck_ID_4	  */
	public void setUY_TR_Truck_ID_4 (int UY_TR_Truck_ID_4)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_4, Integer.valueOf(UY_TR_Truck_ID_4));
	}

	/** Get UY_TR_Truck_ID_4.
		@return UY_TR_Truck_ID_4	  */
	public int getUY_TR_Truck_ID_4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_4);
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

	/** Set UY_TR_TruckType_ID_4.
		@param UY_TR_TruckType_ID_4 UY_TR_TruckType_ID_4	  */
	public void setUY_TR_TruckType_ID_4 (int UY_TR_TruckType_ID_4)
	{
		set_Value (COLUMNNAME_UY_TR_TruckType_ID_4, Integer.valueOf(UY_TR_TruckType_ID_4));
	}

	/** Get UY_TR_TruckType_ID_4.
		@return UY_TR_TruckType_ID_4	  */
	public int getUY_TR_TruckType_ID_4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID_4);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}