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

/** Generated Model for UY_ManufOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ManufOrder extends PO implements I_UY_ManufOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131217L;

    /** Standard Constructor */
    public X_UY_ManufOrder (Properties ctx, int UY_ManufOrder_ID, String trxName)
    {
      super (ctx, UY_ManufOrder_ID, trxName);
      /** if (UY_ManufOrder_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsDescription (false);
			setIsSecondType (false);
// N
			setIsSOTrx (true);
// Y
			setProcessed (false);
// N
			setUY_Budget_ID (0);
			setUY_ManufOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ManufOrder (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ManufOrder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
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

	/** Set Copy From.
		@param CopyFrom 
		Copy From Record
	  */
	public void setCopyFrom (boolean CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, Boolean.valueOf(CopyFrom));
	}

	/** Get Copy From.
		@return Copy From Record
	  */
	public boolean isCopyFrom () 
	{
		Object oo = get_Value(COLUMNNAME_CopyFrom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Detail Information.
		@param DetailInfo 
		Additional Detail Information
	  */
	public void setDetailInfo (String DetailInfo)
	{
		set_Value (COLUMNNAME_DetailInfo, DetailInfo);
	}

	/** Get Detail Information.
		@return Additional Detail Information
	  */
	public String getDetailInfo () 
	{
		return (String)get_Value(COLUMNNAME_DetailInfo);
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

	/** Set InfoText.
		@param InfoText InfoText	  */
	public void setInfoText (String InfoText)
	{
		throw new IllegalArgumentException ("InfoText is virtual column");	}

	/** Get InfoText.
		@return InfoText	  */
	public String getInfoText () 
	{
		return (String)get_Value(COLUMNNAME_InfoText);
	}

	/** Set Description Only.
		@param IsDescription 
		if true, the line is just description and no transaction
	  */
	public void setIsDescription (boolean IsDescription)
	{
		set_Value (COLUMNNAME_IsDescription, Boolean.valueOf(IsDescription));
	}

	/** Get Description Only.
		@return if true, the line is just description and no transaction
	  */
	public boolean isDescription () 
	{
		Object oo = get_Value(COLUMNNAME_IsDescription);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSecondType.
		@param IsSecondType IsSecondType	  */
	public void setIsSecondType (boolean IsSecondType)
	{
		set_Value (COLUMNNAME_IsSecondType, Boolean.valueOf(IsSecondType));
	}

	/** Get IsSecondType.
		@return IsSecondType	  */
	public boolean isSecondType () 
	{
		Object oo = get_Value(COLUMNNAME_IsSecondType);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
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

	/** Set Observaciones2.
		@param Observaciones2 Observaciones2	  */
	public void setObservaciones2 (String Observaciones2)
	{
		set_Value (COLUMNNAME_Observaciones2, Observaciones2);
	}

	/** Get Observaciones2.
		@return Observaciones2	  */
	public String getObservaciones2 () 
	{
		return (String)get_Value(COLUMNNAME_Observaciones2);
	}

	/** Set Path1.
		@param Path1 Path1	  */
	public void setPath1 (String Path1)
	{
		set_Value (COLUMNNAME_Path1, Path1);
	}

	/** Get Path1.
		@return Path1	  */
	public String getPath1 () 
	{
		return (String)get_Value(COLUMNNAME_Path1);
	}

	/** Set Path2.
		@param Path2 Path2	  */
	public void setPath2 (String Path2)
	{
		set_Value (COLUMNNAME_Path2, Path2);
	}

	/** Get Path2.
		@return Path2	  */
	public String getPath2 () 
	{
		return (String)get_Value(COLUMNNAME_Path2);
	}

	/** Set Path3.
		@param Path3 Path3	  */
	public void setPath3 (String Path3)
	{
		set_Value (COLUMNNAME_Path3, Path3);
	}

	/** Get Path3.
		@return Path3	  */
	public String getPath3 () 
	{
		return (String)get_Value(COLUMNNAME_Path3);
	}

	/** Set Pic1_ID.
		@param Pic1_ID Pic1_ID	  */
	public void setPic1_ID (int Pic1_ID)
	{
		if (Pic1_ID < 1) 
			set_Value (COLUMNNAME_Pic1_ID, null);
		else 
			set_Value (COLUMNNAME_Pic1_ID, Integer.valueOf(Pic1_ID));
	}

	/** Get Pic1_ID.
		@return Pic1_ID	  */
	public int getPic1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pic1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pic2_ID.
		@param Pic2_ID Pic2_ID	  */
	public void setPic2_ID (int Pic2_ID)
	{
		if (Pic2_ID < 1) 
			set_Value (COLUMNNAME_Pic2_ID, null);
		else 
			set_Value (COLUMNNAME_Pic2_ID, Integer.valueOf(Pic2_ID));
	}

	/** Get Pic2_ID.
		@return Pic2_ID	  */
	public int getPic2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pic2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pic3_ID.
		@param Pic3_ID Pic3_ID	  */
	public void setPic3_ID (int Pic3_ID)
	{
		if (Pic3_ID < 1) 
			set_Value (COLUMNNAME_Pic3_ID, null);
		else 
			set_Value (COLUMNNAME_Pic3_ID, Integer.valueOf(Pic3_ID));
	}

	/** Get Pic3_ID.
		@return Pic3_ID	  */
	public int getPic3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pic3_ID);
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

	/** Set QtyDesign1.
		@param QtyDesign1 QtyDesign1	  */
	public void setQtyDesign1 (BigDecimal QtyDesign1)
	{
		set_Value (COLUMNNAME_QtyDesign1, QtyDesign1);
	}

	/** Get QtyDesign1.
		@return QtyDesign1	  */
	public BigDecimal getQtyDesign1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDesign1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyDesign2.
		@param QtyDesign2 QtyDesign2	  */
	public void setQtyDesign2 (BigDecimal QtyDesign2)
	{
		set_Value (COLUMNNAME_QtyDesign2, QtyDesign2);
	}

	/** Get QtyDesign2.
		@return QtyDesign2	  */
	public BigDecimal getQtyDesign2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDesign2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyDesign3.
		@param QtyDesign3 QtyDesign3	  */
	public void setQtyDesign3 (BigDecimal QtyDesign3)
	{
		set_Value (COLUMNNAME_QtyDesign3, QtyDesign3);
	}

	/** Get QtyDesign3.
		@return QtyDesign3	  */
	public BigDecimal getQtyDesign3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDesign3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set serie.
		@param serie serie	  */
	public void setserie (String serie)
	{
		set_Value (COLUMNNAME_serie, serie);
	}

	/** Get serie.
		@return serie	  */
	public String getserie () 
	{
		return (String)get_Value(COLUMNNAME_serie);
	}

	public I_UY_Budget getUY_Budget() throws RuntimeException
    {
		return (I_UY_Budget)MTable.get(getCtx(), I_UY_Budget.Table_Name)
			.getPO(getUY_Budget_ID(), get_TrxName());	}

	/** Set UY_Budget.
		@param UY_Budget_ID UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID)
	{
		if (UY_Budget_ID < 1) 
			set_Value (COLUMNNAME_UY_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Budget_ID, Integer.valueOf(UY_Budget_ID));
	}

	/** Get UY_Budget.
		@return UY_Budget	  */
	public int getUY_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BudgetDelivery getUY_BudgetDelivery() throws RuntimeException
    {
		return (I_UY_BudgetDelivery)MTable.get(getCtx(), I_UY_BudgetDelivery.Table_Name)
			.getPO(getUY_BudgetDelivery_ID(), get_TrxName());	}

	/** Set UY_BudgetDelivery.
		@param UY_BudgetDelivery_ID UY_BudgetDelivery	  */
	public void setUY_BudgetDelivery_ID (int UY_BudgetDelivery_ID)
	{
		if (UY_BudgetDelivery_ID < 1) 
			set_Value (COLUMNNAME_UY_BudgetDelivery_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BudgetDelivery_ID, Integer.valueOf(UY_BudgetDelivery_ID));
	}

	/** Get UY_BudgetDelivery.
		@return UY_BudgetDelivery	  */
	public int getUY_BudgetDelivery_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BudgetDelivery_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_GenerateDelivOrder.
		@param UY_GenerateDelivOrder UY_GenerateDelivOrder	  */
	public void setUY_GenerateDelivOrder (String UY_GenerateDelivOrder)
	{
		set_Value (COLUMNNAME_UY_GenerateDelivOrder, UY_GenerateDelivOrder);
	}

	/** Get UY_GenerateDelivOrder.
		@return UY_GenerateDelivOrder	  */
	public String getUY_GenerateDelivOrder () 
	{
		return (String)get_Value(COLUMNNAME_UY_GenerateDelivOrder);
	}

	/** Set UY_ManufOrder.
		@param UY_ManufOrder_ID UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID)
	{
		if (UY_ManufOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ManufOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ManufOrder_ID, Integer.valueOf(UY_ManufOrder_ID));
	}

	/** Get UY_ManufOrder.
		@return UY_ManufOrder	  */
	public int getUY_ManufOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PrintMOrder.
		@param UY_PrintMOrder UY_PrintMOrder	  */
	public void setUY_PrintMOrder (String UY_PrintMOrder)
	{
		set_Value (COLUMNNAME_UY_PrintMOrder, UY_PrintMOrder);
	}

	/** Get UY_PrintMOrder.
		@return UY_PrintMOrder	  */
	public String getUY_PrintMOrder () 
	{
		return (String)get_Value(COLUMNNAME_UY_PrintMOrder);
	}

	/** Set UY_PrintMOrder2.
		@param UY_PrintMOrder2 UY_PrintMOrder2	  */
	public void setUY_PrintMOrder2 (String UY_PrintMOrder2)
	{
		set_Value (COLUMNNAME_UY_PrintMOrder2, UY_PrintMOrder2);
	}

	/** Get UY_PrintMOrder2.
		@return UY_PrintMOrder2	  */
	public String getUY_PrintMOrder2 () 
	{
		return (String)get_Value(COLUMNNAME_UY_PrintMOrder2);
	}
}