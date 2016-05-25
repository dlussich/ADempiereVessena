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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.openup.model.I_UY_StockStatus;

/** Generated Model for UY_Confirmorderhdr
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_Confirmorderhdr extends PO implements I_UY_Confirmorderhdr, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120216L;

    /** Standard Constructor */
    public X_UY_Confirmorderhdr (Properties ctx, int UY_Confirmorderhdr_ID, String trxName)
    {
      super (ctx, UY_Confirmorderhdr_ID, trxName);
      /** if (UY_Confirmorderhdr_ID == 0)
        {
			setAD_Workflow_ID (0);
			setC_DocType_ID (0);
			setC_DocTypeTarget_ID (0);
			setC_UOM_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setDateFinish (new Timestamp( System.currentTimeMillis() ));
			setDateOrdered (new Timestamp( System.currentTimeMillis() ));
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setPP_Order_BOM_ID (0);
			setPP_Order_ID (0);
			setProcessed (false);
			setQtyDelivered (Env.ZERO);
			setQtyOrdered (Env.ZERO);
			setQtyReject (Env.ZERO);
			setQtyScrap (Env.ZERO);
			setS_Resource_ID (0);
			setUY_Confirmorderhdr_ID (0);
			setUY_Packs (Env.ZERO);
			setUY_Resource_ID (0);
			setYield (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UY_Confirmorderhdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Confirmorderhdr[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Workflow)MTable.get(getCtx(), org.compiere.model.I_AD_Workflow.Table_Name)
			.getPO(getAD_Workflow_ID(), get_TrxName());	}

	/** Set Workflow.
		@param AD_Workflow_ID 
		Workflow or combination of tasks
	  */
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or combination of tasks
	  */
	public int getAD_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workflow_ID);
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

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocTypeTarget_ID(), get_TrxName());	}

	/** Set Target Document Type.
		@param C_DocTypeTarget_ID 
		Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, Integer.valueOf(C_DocTypeTarget_ID));
	}

	/** Get Target Document Type.
		@return Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	/** Set Finish Date.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	/** Get Finish Date.
		@return Finish or (planned) completion date
	  */
	public Timestamp getDateFinish () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFinish);
	}

	/** Set DateFinishOrder.
		@param DateFinishOrder DateFinishOrder	  */
	public void setDateFinishOrder (Timestamp DateFinishOrder)
	{
		throw new IllegalArgumentException ("DateFinishOrder is virtual column");	}

	/** Get DateFinishOrder.
		@return DateFinishOrder	  */
	public Timestamp getDateFinishOrder () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFinishOrder);
	}

	/** Set Date Ordered.
		@param DateOrdered 
		Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Date Ordered.
		@return Date of Order
	  */
	public Timestamp getDateOrdered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Date Start.
		@param DateStart 
		Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get Date Start.
		@return Date Start for this Order
	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
	}

	/** Set DateStartOrder.
		@param DateStartOrder DateStartOrder	  */
	public void setDateStartOrder (Timestamp DateStartOrder)
	{
		throw new IllegalArgumentException ("DateStartOrder is virtual column");	}

	/** Get DateStartOrder.
		@return DateStartOrder	  */
	public Timestamp getDateStartOrder () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStartOrder);
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

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.eevolution.model.I_PP_Order_BOM getPP_Order_BOM() throws RuntimeException
    {
		return (org.eevolution.model.I_PP_Order_BOM)MTable.get(getCtx(), org.eevolution.model.I_PP_Order_BOM.Table_Name)
			.getPO(getPP_Order_BOM_ID(), get_TrxName());	}

	/** Set Manufacturing Order BOM.
		@param PP_Order_BOM_ID Manufacturing Order BOM	  */
	public void setPP_Order_BOM_ID (int PP_Order_BOM_ID)
	{
		if (PP_Order_BOM_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_BOM_ID, Integer.valueOf(PP_Order_BOM_ID));
	}

	/** Get Manufacturing Order BOM.
		@return Manufacturing Order BOM	  */
	public int getPP_Order_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
    {
		return (org.eevolution.model.I_PP_Order)MTable.get(getCtx(), org.eevolution.model.I_PP_Order.Table_Name)
			.getPO(getPP_Order_ID(), get_TrxName());	}

	/** Set Manufacturing Order.
		@param PP_Order_ID 
		Manufacturing Order
	  */
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Manufacturing Order.
		@return Manufacturing Order
	  */
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
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

	/** Set Qty Batchs.
		@param QtyBatchs Qty Batchs	  */
	public void setQtyBatchs (BigDecimal QtyBatchs)
	{
		set_Value (COLUMNNAME_QtyBatchs, QtyBatchs);
	}

	/** Get Qty Batchs.
		@return Qty Batchs	  */
	public BigDecimal getQtyBatchs () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBatchs);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Batch Size.
		@param QtyBatchSize Qty Batch Size	  */
	public void setQtyBatchSize (BigDecimal QtyBatchSize)
	{
		set_Value (COLUMNNAME_QtyBatchSize, QtyBatchSize);
	}

	/** Get Qty Batch Size.
		@return Qty Batch Size	  */
	public BigDecimal getQtyBatchSize () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBatchSize);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Delivered Quantity.
		@param QtyDelivered 
		Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Delivered Quantity.
		@return Delivered Quantity
	  */
	public BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ordered Quantity.
		@param QtyOrdered 
		Ordered Quantity
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Ordered Quantity.
		@return Ordered Quantity
	  */
	public BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Reject.
		@param QtyReject Qty Reject	  */
	public void setQtyReject (BigDecimal QtyReject)
	{
		set_Value (COLUMNNAME_QtyReject, QtyReject);
	}

	/** Get Qty Reject.
		@return Qty Reject	  */
	public BigDecimal getQtyReject () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReject);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scrap %.
		@param QtyScrap 
		Scrap % Quantity for this componet
	  */
	public void setQtyScrap (BigDecimal QtyScrap)
	{
		set_Value (COLUMNNAME_QtyScrap, QtyScrap);
	}

	/** Get Scrap %.
		@return Scrap % Quantity for this componet
	  */
	public BigDecimal getQtyScrap () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyScrap);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_S_Resource getS_Resource() throws RuntimeException
    {
		return (org.compiere.model.I_S_Resource)MTable.get(getCtx(), org.compiere.model.I_S_Resource.Table_Name)
			.getPO(getS_Resource_ID(), get_TrxName());	}

	/** Set Resource.
		@param S_Resource_ID 
		Resource
	  */
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Resource.
		@return Resource
	  */
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Turno.
		@param Turno Turno	  */
	public void setTurno (int Turno)
	{
		set_Value (COLUMNNAME_Turno, Integer.valueOf(Turno));
	}

	/** Get Turno.
		@return Turno	  */
	public int getTurno () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Turno);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Warehouse getUY_Almacendestino() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getUY_Almacendestino_ID(), get_TrxName());	}

	/** Set UY_Almacendestino_ID.
		@param UY_Almacendestino_ID UY_Almacendestino_ID	  */
	public void setUY_Almacendestino_ID (int UY_Almacendestino_ID)
	{
		if (UY_Almacendestino_ID < 1) 
			set_Value (COLUMNNAME_UY_Almacendestino_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Almacendestino_ID, Integer.valueOf(UY_Almacendestino_ID));
	}

	/** Get UY_Almacendestino_ID.
		@return UY_Almacendestino_ID	  */
	public int getUY_Almacendestino_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Almacendestino_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CerroConfirmacion.
		@param UY_CerroConfirmacion UY_CerroConfirmacion	  */
	public void setUY_CerroConfirmacion (boolean UY_CerroConfirmacion)
	{
		set_Value (COLUMNNAME_UY_CerroConfirmacion, Boolean.valueOf(UY_CerroConfirmacion));
	}

	/** Get UY_CerroConfirmacion.
		@return UY_CerroConfirmacion	  */
	public boolean isUY_CerroConfirmacion () 
	{
		Object oo = get_Value(COLUMNNAME_UY_CerroConfirmacion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_Confirmorderhdr.
		@param UY_Confirmorderhdr_ID UY_Confirmorderhdr	  */
	public void setUY_Confirmorderhdr_ID (int UY_Confirmorderhdr_ID)
	{
		if (UY_Confirmorderhdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Confirmorderhdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Confirmorderhdr_ID, Integer.valueOf(UY_Confirmorderhdr_ID));
	}

	/** Get UY_Confirmorderhdr.
		@return UY_Confirmorderhdr	  */
	public int getUY_Confirmorderhdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Confirmorderhdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Cantidad de Packs.
		@param UY_Packs UY_Cantidad de Packs	  */
	public void setUY_Packs (BigDecimal UY_Packs)
	{
		set_Value (COLUMNNAME_UY_Packs, UY_Packs);
	}

	/** Get UY_Cantidad de Packs.
		@return UY_Cantidad de Packs	  */
	public BigDecimal getUY_Packs () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_Packs);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_S_Resource getUY_Resource() throws RuntimeException
    {
		return (org.compiere.model.I_S_Resource)MTable.get(getCtx(), org.compiere.model.I_S_Resource.Table_Name)
			.getPO(getUY_Resource_ID(), get_TrxName());	}

	/** Set UY_Resource_ID.
		@param UY_Resource_ID 
		UY_Resource_ID
	  */
	public void setUY_Resource_ID (int UY_Resource_ID)
	{
		if (UY_Resource_ID < 1) 
			set_Value (COLUMNNAME_UY_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Resource_ID, Integer.valueOf(UY_Resource_ID));
	}

	/** Get UY_Resource_ID.
		@return UY_Resource_ID
	  */
	public int getUY_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_StockStatus getUY_StockStatus() throws RuntimeException
    {
		return (I_UY_StockStatus)MTable.get(getCtx(), I_UY_StockStatus.Table_Name)
			.getPO(getUY_StockStatus_ID(), get_TrxName());	}

	/** Set UY_StockStatus.
		@param UY_StockStatus_ID UY_StockStatus	  */
	public void setUY_StockStatus_ID (int UY_StockStatus_ID)
	{
		if (UY_StockStatus_ID < 1) 
			set_Value (COLUMNNAME_UY_StockStatus_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StockStatus_ID, Integer.valueOf(UY_StockStatus_ID));
	}

	/** Get UY_StockStatus.
		@return UY_StockStatus	  */
	public int getUY_StockStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Yield %.
		@param Yield 
		The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	public void setYield (BigDecimal Yield)
	{
		set_Value (COLUMNNAME_Yield, Yield);
	}

	/** Get Yield %.
		@return The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	public BigDecimal getYield () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Yield);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}