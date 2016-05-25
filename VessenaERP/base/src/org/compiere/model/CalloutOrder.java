/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.openup.model.MProductUpc;

/**
 *	Order Callouts.
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutOrder.java,v 1.5 2006/10/08 06:57:33 comdivision Exp $
 *  
 *  @author Michael McKay (mjmckay)
 *  		<li> BF3468458 - Attribute Set Instance not filled on Orders when product lookup not used.
 *  			 See https://sourceforge.net/tracker/?func=detail&aid=3468458&group_id=176962&atid=879332
 */
public class CalloutOrder extends CalloutEngine
{
	/**	Debug Steps			*/
	private boolean steps = false;

	/**
	 *	Order Header Change - DocType.
	 *		- InvoiceRule/DeliveryRule/PaymentRule
	 *		- temporary Document
	 *  Context:
	 *  	- DocSubTypeSO
	 *		- HasCharges
	 *	- (re-sets Business Partner info of required)
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String docType (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_DocType_ID = (Integer)value;		//	Actually C_DocTypeTarget_ID
		if (C_DocType_ID == null || C_DocType_ID.intValue() == 0)
			return "";

		//	Re-Create new DocNo, if there is a doc number already
		//	and the existing source used a different Sequence number
		String oldDocNo = (String)mTab.getValue("DocumentNo");
		boolean newDocNo = (oldDocNo == null);
		if (!newDocNo && oldDocNo.startsWith("<") && oldDocNo.endsWith(">"))
			newDocNo = true;
		Integer oldC_DocType_ID = (Integer)mTab.getValue("C_DocType_ID");

		String sql = "SELECT d.DocSubTypeSO,d.HasCharges,'N',"			//	1..3
			+ "d.IsDocNoControlled,s.CurrentNext,s.CurrentNextSys,"     //  4..6
			+ "s.AD_Sequence_ID,d.IsSOTrx, "                             //	7..8
			+ "s.StartNewYear, s.DateColumn "							//  9..10
			+ "FROM C_DocType d, AD_Sequence s "
			+ "WHERE C_DocType_ID=?"	//	#1
			+ " AND d.DocNoSequence_ID=s.AD_Sequence_ID(+)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			int AD_Sequence_ID = 0;

			//	Get old AD_SeqNo for comparison
			if (!newDocNo && oldC_DocType_ID.intValue() != 0)
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, oldC_DocType_ID.intValue());
				rs = pstmt.executeQuery();
				if (rs.next())
					AD_Sequence_ID = rs.getInt(7);
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_DocType_ID.intValue());
			rs = pstmt.executeQuery();
			String DocSubTypeSO = "";
			boolean IsSOTrx = true;
			if (rs.next())		//	we found document type
			{
				//	Set Context:	Document Sub Type for Sales Orders
				DocSubTypeSO = rs.getString(1);
				if (DocSubTypeSO == null)
					DocSubTypeSO = "--";
				Env.setContext(ctx, WindowNo, "OrderType", DocSubTypeSO);
				//	No Drop Ship other than Standard
				if (!DocSubTypeSO.equals(MOrder.DocSubTypeSO_Standard))
					mTab.setValue ("IsDropShip", "N");
				
				//	Delivery Rule
				if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_POS))
					mTab.setValue ("DeliveryRule", X_C_Order.DELIVERYRULE_Force);
				else if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_Prepay))
					mTab.setValue ("DeliveryRule", X_C_Order.DELIVERYRULE_AfterReceipt);
				else
					mTab.setValue ("DeliveryRule", X_C_Order.DELIVERYRULE_Availability);
				
				//	Invoice Rule
				if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_POS)
					|| DocSubTypeSO.equals(MOrder.DocSubTypeSO_Prepay)
					|| DocSubTypeSO.equals(MOrder.DocSubTypeSO_OnCredit) )
					mTab.setValue ("InvoiceRule", X_C_Order.INVOICERULE_Immediate);
				else
					mTab.setValue ("InvoiceRule", X_C_Order.INVOICERULE_AfterDelivery);
				
				//	Payment Rule - POS Order
				if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_POS))
					mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_Cash);
				else
					mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_OnCredit);

				//	IsSOTrx
				if ("N".equals(rs.getString(8)))
					IsSOTrx = false;

				//	Set Context:
				Env.setContext(ctx, WindowNo, "HasCharges", rs.getString(2));

				//	DocumentNo
				if (rs.getString(4).equals("Y"))			//	IsDocNoControlled
				{
					if (!newDocNo && AD_Sequence_ID != rs.getInt(7))
						newDocNo = true;
					if (newDocNo)
						if (Ini.isPropertyBool(Ini.P_ADEMPIERESYS) && Env.getAD_Client_ID(Env.getCtx()) < 1000000)
							mTab.setValue("DocumentNo", "<" + rs.getString(6) + ">");
						else
						{
							if ("Y".equals(rs.getString(9)))
							{
								String dateColumn = rs.getString(10);
								mTab.setValue("DocumentNo", 
										"<" 
										+ MSequence.getPreliminaryNoByYear(mTab, rs.getInt(7), dateColumn, null) 
										+ ">");
							}
							else
							{
								mTab.setValue("DocumentNo", "<" + rs.getString(5) + ">");
							}
						}
				}
			}
			
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
			
			//  When BPartner is changed, the Rules are not set if
			//  it is a POS or Credit Order (i.e. defaults from Standard BPartner)
			//  This re-reads the Rules and applies them.
			if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_POS) 
				|| DocSubTypeSO.equals(MOrder.DocSubTypeSO_Prepay))    //  not for POS/PrePay
				;
			else
			{
				sql = "SELECT PaymentRule,C_PaymentTerm_ID,"            //  1..2
					+ "InvoiceRule,DeliveryRule,"                       //  3..4
					+ "FreightCostRule,DeliveryViaRule, "               //  5..6
					+ "PaymentRulePO,PO_PaymentTerm_ID "
					+ "FROM C_BPartner "
					+ "WHERE C_BPartner_ID=?";		//	#1
				pstmt = DB.prepareStatement(sql, null);
				int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
				pstmt.setInt(1, C_BPartner_ID);
				//
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					//	PaymentRule
					String s = rs.getString(IsSOTrx ? "PaymentRule" : "PaymentRulePO");
					if (s != null && s.length() != 0)
					{
						if (IsSOTrx && (s.equals("B") || s.equals("S") || s.equals("U")))	//	No Cash/Check/Transfer for SO_Trx
							s = "P";										//  Payment Term
						if (!IsSOTrx && (s.equals("B")))					//	No Cash for PO_Trx
							s = "P";										//  Payment Term
						mTab.setValue("PaymentRule", s);
					}
					//	Payment Term
					Integer ii =new Integer(rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID"));
					if (!rs.wasNull())
						mTab.setValue("C_PaymentTerm_ID", ii);
					//	InvoiceRule
					s = rs.getString(3);
					if (s != null && s.length() != 0)
						mTab.setValue("InvoiceRule", s);
					//	DeliveryRule
					s = rs.getString(4);
					if (s != null && s.length() != 0)
						mTab.setValue("DeliveryRule", s);
					//	FreightCostRule
					s = rs.getString(5);
					if (s != null && s.length() != 0)
						mTab.setValue("FreightCostRule", s);
					//	DeliveryViaRule
					s = rs.getString(6);
					if (s != null && s.length() != 0)
						mTab.setValue("DeliveryViaRule", s);
				}
			} 
			//  re-read customer rules
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}	//	docType

	/**
	 *	Order Header - BPartner.
	 *		- M_PriceList_ID (+ Context)
	 *		- C_BPartner_Location_ID
	 *		- Bill_BPartner_ID/Bill_Location_ID
	 *		- AD_User_ID
	 *		- POReference
	 *		- SO_Description
	 *		- IsDiscountPrinted
	 *		- InvoiceRule/DeliveryRule/PaymentRule/FreightCost/DeliveryViaRule
	 *		- C_PaymentTerm_ID
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String bPartner (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_BPartner_ID = (Integer)value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
			return "";
		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
			+ " COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID, p.PaymentRule,p.POReference,"
			+ " p.SO_Description,p.IsDiscountPrinted,"
			+ " p.InvoiceRule,p.DeliveryRule,p.FreightCostRule,DeliveryViaRule,"
			+ " p.SO_CreditLimit, p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
			+ " lship.C_BPartner_Location_ID,c.AD_User_ID,"
			+ " COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID, p.PaymentRulePO,p.PO_PaymentTerm_ID," 
			+ " lbill.C_BPartner_Location_ID AS Bill_Location_ID, p.SOCreditStatus, "
			+ " p.SalesRep_ID "
			+ "FROM C_BPartner p"
			+ " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID)"			
			+ " LEFT OUTER JOIN C_BPartner_Location lbill ON (p.C_BPartner_ID=lbill.C_BPartner_ID AND lbill.IsBillTo='Y' AND lbill.IsActive='Y')"
			+ " LEFT OUTER JOIN C_BPartner_Location lship ON (p.C_BPartner_ID=lship.C_BPartner_ID AND lship.IsShipTo='Y' AND lship.IsActive='Y')"
			+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
			+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'";		//	#1

		boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				// Sales Rep - If BP has a default SalesRep then default it
				Integer salesRep = rs.getInt("SalesRep_ID");
				if (IsSOTrx && salesRep != 0 )
				{
					mTab.setValue("SalesRep_ID", salesRep);
				}
				
				//	PriceList (indirect: IsTaxIncluded & Currency)
				Integer ii = new Integer(rs.getInt(IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID"));
				if (!rs.wasNull())
					mTab.setValue("M_PriceList_ID", ii);
				//OpenUp. Nicolas Sarlabos. 07/07/2015. Se comenta codigo innecesario.
				/*else
				{	//	get default PriceList
					int i = Env.getContextAsInt(ctx, "#M_PriceList_ID");
					if (i != 0)
						mTab.setValue("M_PriceList_ID", new Integer(i));
				}*/
				//Fin OpenUp.

				//	Bill-To
				mTab.setValue("Bill_BPartner_ID", C_BPartner_ID);
				int bill_Location_ID = rs.getInt("Bill_Location_ID");
				if (bill_Location_ID == 0)
					mTab.setValue("Bill_Location_ID", null);
				else
					mTab.setValue("Bill_Location_ID", new Integer(bill_Location_ID));
				// Ship-To Location
				int shipTo_ID = rs.getInt("C_BPartner_Location_ID");
				//	overwritten by InfoBP selection - works only if InfoWindow
				//	was used otherwise creates error (uses last value, may belong to different BP)
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String loc = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0)
						shipTo_ID = Integer.parseInt(loc);
				}
				if (shipTo_ID == 0)
					mTab.setValue("C_BPartner_Location_ID", null);
				else
					mTab.setValue("C_BPartner_Location_ID", new Integer(shipTo_ID));

				//	Contact - overwritten by InfoBP selection
				int contID = rs.getInt("AD_User_ID");
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String cont = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "AD_User_ID");
					if (cont.length() > 0)
						contID = Integer.parseInt(cont);
				}
				if (contID == 0)
					mTab.setValue("AD_User_ID", null);
				else
				{
					mTab.setValue("AD_User_ID", new Integer(contID));
					mTab.setValue("Bill_User_ID", new Integer(contID));
				}

				// OpenUp. Gabriel Vila. 14/06/2012. 
				// Comento warning de verificacion de credito al seleccionar el cliente. Ademas esto estaba mirando estructuras de adempiere y no las de openup.
				
				/*
				//	CreditAvailable 
				if (IsSOTrx)
				{
					double CreditLimit = rs.getDouble("SO_CreditLimit");
					//String SOCreditStatus = rs.getString("SOCreditStatus");
					if (CreditLimit != 0)
					{
						double CreditAvailable = rs.getDouble("CreditAvailable");
						if (!rs.wasNull() && CreditAvailable < 0)
							mTab.fireDataStatusEEvent("CreditLimitOver",
								DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
								false);
					}
				}
				*/
				// Fin OpenUp

				//	PO Reference
				String s = rs.getString("POReference");
				if (s != null && s.length() != 0)
					mTab.setValue("POReference", s);
				// should not be reset to null if we entered already value! VHARCQ, accepted YS makes sense that way
				// TODO: should get checked and removed if no longer needed!
				/*else
					mTab.setValue("POReference", null);*/ 
				
				//	SO Description
				s = rs.getString("SO_Description");
				if (s != null && s.trim().length() != 0)
					mTab.setValue("Description", s);
				//	IsDiscountPrinted
				s = rs.getString("IsDiscountPrinted");
				if (s != null && s.length() != 0)
					mTab.setValue("IsDiscountPrinted", s);
				else
					mTab.setValue("IsDiscountPrinted", "N");

				//	Defaults, if not Walkin Receipt or Walkin Invoice
				String OrderType = Env.getContext(ctx, WindowNo, "OrderType");
				mTab.setValue("InvoiceRule", X_C_Order.INVOICERULE_AfterDelivery);
				mTab.setValue("DeliveryRule", X_C_Order.DELIVERYRULE_Availability);
				mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_OnCredit);
				if (OrderType.equals(MOrder.DocSubTypeSO_Prepay))
				{
					mTab.setValue("InvoiceRule", X_C_Order.INVOICERULE_Immediate);
					mTab.setValue("DeliveryRule", X_C_Order.DELIVERYRULE_AfterReceipt);
				}
				else if (OrderType.equals(MOrder.DocSubTypeSO_POS))	//  for POS
					mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_Cash);
				else
				{
					//	PaymentRule
					s = rs.getString(IsSOTrx ? "PaymentRule" : "PaymentRulePO");
					if (s != null && s.length() != 0)
					{
						if (s.equals("B"))				//	No Cache in Non POS
							s = "P";
						if (IsSOTrx && (s.equals("S") || s.equals("U")))	//	No Check/Transfer for SO_Trx
							s = "P";										//  Payment Term
						mTab.setValue("PaymentRule", s);
					}
					//	Payment Term
					ii = new Integer(rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID"));
					if (!rs.wasNull())
						mTab.setValue("C_PaymentTerm_ID", ii);
					//	InvoiceRule
					s = rs.getString("InvoiceRule");
					if (s != null && s.length() != 0)
						mTab.setValue("InvoiceRule", s);
					//	DeliveryRule
					s = rs.getString("DeliveryRule");
					if (s != null && s.length() != 0)
						mTab.setValue("DeliveryRule", s);
					//	FreightCostRule
					s = rs.getString("FreightCostRule");
					if (s != null && s.length() != 0)
						mTab.setValue("FreightCostRule", s);
					//	DeliveryViaRule
					s = rs.getString("DeliveryViaRule");
					if (s != null && s.length() != 0)
						mTab.setValue("DeliveryViaRule", s);
				}
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}	//	bPartner

	/**
	 *	Order Header - Invoice BPartner.
	 *		- M_PriceList_ID (+ Context)
	 *		- Bill_Location_ID
	 *		- Bill_User_ID
	 *		- POReference
	 *		- SO_Description
	 *		- IsDiscountPrinted
	 *		- InvoiceRule/PaymentRule
	 *		- C_PaymentTerm_ID
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String bPartnerBill (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())
			return "";
		Integer bill_BPartner_ID = (Integer)value;
		if (bill_BPartner_ID == null || bill_BPartner_ID.intValue() == 0)
			return "";

		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
			+ "p.M_PriceList_ID,p.PaymentRule,p.POReference,"
			+ "p.SO_Description,p.IsDiscountPrinted,"
			+ "p.InvoiceRule,p.DeliveryRule,p.FreightCostRule,DeliveryViaRule,"
			+ "p.SO_CreditLimit, p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
			+ "c.AD_User_ID,"
			+ "p.PO_PriceList_ID, p.PaymentRulePO, p.PO_PaymentTerm_ID,"
			+ "lbill.C_BPartner_Location_ID AS Bill_Location_ID "
			+ "FROM C_BPartner p"
			+ " LEFT OUTER JOIN C_BPartner_Location lbill ON (p.C_BPartner_ID=lbill.C_BPartner_ID AND lbill.IsBillTo='Y' AND lbill.IsActive='Y')"
			+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
			+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'";		//	#1

		boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, bill_BPartner_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				//	PriceList (indirect: IsTaxIncluded & Currency)
				Integer ii = new Integer(rs.getInt(IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID"));
				if (!rs.wasNull())
					mTab.setValue("M_PriceList_ID", ii);
				else
				{	//	get default PriceList
					int i = Env.getContextAsInt(ctx, "#M_PriceList_ID");
					if (i != 0)
						mTab.setValue("M_PriceList_ID", new Integer(i));
				}

				int bill_Location_ID = rs.getInt("Bill_Location_ID");
				//	overwritten by InfoBP selection - works only if InfoWindow
				//	was used otherwise creates error (uses last value, may belong to different BP)
				if (bill_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String loc = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0)
						bill_Location_ID = Integer.parseInt(loc);
				}
				if (bill_Location_ID == 0)
					mTab.setValue("Bill_Location_ID", null);
				else
					mTab.setValue("Bill_Location_ID", new Integer(bill_Location_ID));

				//	Contact - overwritten by InfoBP selection
				int contID = rs.getInt("AD_User_ID");
				if (bill_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String cont = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "AD_User_ID");
					if (cont.length() > 0)
						contID = Integer.parseInt(cont);
				}
				if (contID == 0)
					mTab.setValue("Bill_User_ID", null);
				else
					mTab.setValue("Bill_User_ID", new Integer(contID));

				//	CreditAvailable 
				if (IsSOTrx)
				{
					double CreditLimit = rs.getDouble("SO_CreditLimit");
					if (CreditLimit != 0)
					{
						double CreditAvailable = rs.getDouble("CreditAvailable");
						if (!rs.wasNull() && CreditAvailable < 0)
							mTab.fireDataStatusEEvent("CreditLimitOver",
								DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
								false);
					}
				}

				//	PO Reference
				String s = rs.getString("POReference");
				if (s != null && s.length() != 0)
					mTab.setValue("POReference", s);
				else
					mTab.setValue("POReference", null);
				//	SO Description
				s = rs.getString("SO_Description");
				if (s != null && s.trim().length() != 0)
					mTab.setValue("Description", s);
				//	IsDiscountPrinted
				s = rs.getString("IsDiscountPrinted");
				if (s != null && s.length() != 0)
					mTab.setValue("IsDiscountPrinted", s);
				else
					mTab.setValue("IsDiscountPrinted", "N");

				//	Defaults, if not Walkin Receipt or Walkin Invoice
				String OrderType = Env.getContext(ctx, WindowNo, "OrderType");
				mTab.setValue("InvoiceRule", X_C_Order.INVOICERULE_AfterDelivery);
				mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_OnCredit);
				if (OrderType.equals(MOrder.DocSubTypeSO_Prepay))
					mTab.setValue("InvoiceRule", X_C_Order.INVOICERULE_Immediate);
				else if (OrderType.equals(MOrder.DocSubTypeSO_POS))	//  for POS
					mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_Cash);
				else
				{
					//	PaymentRule
					s = rs.getString(IsSOTrx ? "PaymentRule" : "PaymentRulePO");
					if (s != null && s.length() != 0)
					{
						if (s.equals("B"))				//	No Cache in Non POS
							s = "P";
						if (IsSOTrx && (s.equals("S") || s.equals("U")))	//	No Check/Transfer for SO_Trx
							s = "P";										//  Payment Term
						mTab.setValue("PaymentRule", s);
					}
					//	Payment Term
					ii = new Integer(rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID"));
					if (!rs.wasNull())
						mTab.setValue("C_PaymentTerm_ID", ii);
					//	InvoiceRule
					s = rs.getString("InvoiceRule");
					if (s != null && s.length() != 0)
						mTab.setValue("InvoiceRule", s);
				}
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "bPartnerBill", e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}	//	bPartnerBill


	/**
	 *	Order Header - PriceList.
	 *	(used also in Invoice)
	 *		- C_Currency_ID
	 *		- IsTaxIncluded
	 *	Window Context:
	 *		- EnforcePriceLimit
	 *		- StdPrecision
	 *		- M_PriceList_Version_ID
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String priceList (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_PriceList_ID = (Integer) mTab.getValue("M_PriceList_ID");
		if (M_PriceList_ID == null || M_PriceList_ID.intValue()== 0)
			return "";
		if (steps) log.warning("init");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT pl.IsTaxIncluded,pl.EnforcePriceLimit,pl.C_Currency_ID,c.StdPrecision,"
			+ "plv.M_PriceList_Version_ID,plv.ValidFrom "
			+ "FROM M_PriceList pl,C_Currency c,M_PriceList_Version plv "
			+ "WHERE pl.C_Currency_ID=c.C_Currency_ID"
			+ " AND pl.M_PriceList_ID=plv.M_PriceList_ID"
			+ " AND pl.M_PriceList_ID=? "						//	1
			+ " AND plv.ValidFrom <= ? "
			+ "ORDER BY plv.ValidFrom DESC";
		//	Use newest price list - may not be future
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_PriceList_ID.intValue());
			Timestamp date = new Timestamp(System.currentTimeMillis());
			if (mTab.getAD_Table_ID() == I_C_Order.Table_ID)
				date = Env.getContextAsDate(ctx, WindowNo, "DateOrdered");
			else if (mTab.getAD_Table_ID() == I_C_Invoice.Table_ID)
				date = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
			pstmt.setTimestamp(2, date);
			
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				//	Tax Included
				mTab.setValue("IsTaxIncluded", new Boolean("Y".equals(rs.getString(1))));
				//	Price Limit Enforce
				Env.setContext(ctx, WindowNo, "EnforcePriceLimit", rs.getString(2));
				//	Currency
				Integer ii = new Integer(rs.getInt(3));
				mTab.setValue("C_Currency_ID", ii);
				//	PriceList Version
				Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", rs.getInt(5));
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (steps) log.warning("fini");

		return "";
	}	//	priceList

	
	/*************************************************************************
	 *	Order Line - Product.
	 *		- reset C_Charge_ID / M_AttributeSetInstance_ID
	 *		- PriceList, PriceStd, PriceLimit, C_Currency_ID, EnforcePriceLimit
	 *		- UOM
	 *	Calls Tax
	 *
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";
		if (steps) log.warning("init");

		MProduct product = MProduct.get (ctx, M_Product_ID.intValue());
		I_M_AttributeSetInstance asi = product.getM_AttributeSetInstance();
		//
		mTab.setValue("C_Charge_ID", null);
		//	Set Attribute from context or, if null, from the Product
		if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID.intValue()
			&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID") != 0)
			mTab.setValue("M_AttributeSetInstance_ID", Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID"));
		else {
			mTab.setValue("M_AttributeSetInstance_ID", asi.getM_AttributeSetInstance_ID());
		}
		/*****	Price Calculation see also qty	****/
		int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
		BigDecimal Qty = (BigDecimal)mTab.getValue("QtyOrdered");
		boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		MProductPricing pp = new MProductPricing (M_Product_ID.intValue(), C_BPartner_ID, Qty, IsSOTrx);
		//
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		pp.setM_PriceList_ID(M_PriceList_ID);
		Timestamp orderDate = (Timestamp)mTab.getValue("DateOrdered");
		/** PLV is only accurate if PL selected in header */
		int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
		if ( M_PriceList_Version_ID == 0 && M_PriceList_ID > 0)
		{
			String sql = "SELECT plv.M_PriceList_Version_ID "
				+ "FROM M_PriceList_Version plv "
				+ "WHERE plv.M_PriceList_ID=? "						//	1
				+ " AND plv.ValidFrom <= ? "
				+ " AND IsActive='Y' "  // OpenUp. Gabriel Vila. 15/02/2012. Issue #867.
				+ "ORDER BY plv.ValidFrom DESC";
			//	Use newest price list - may not be future
			
			M_PriceList_Version_ID = DB.getSQLValueEx(null, sql, M_PriceList_ID, orderDate);
			if ( M_PriceList_Version_ID > 0 )
				Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", M_PriceList_Version_ID );
		}
		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID); 
		pp.setPriceDate(orderDate);
		//		
		mTab.setValue("PriceList", pp.getPriceList());
		mTab.setValue("PriceLimit", pp.getPriceLimit());
		mTab.setValue("PriceActual", pp.getPriceStd());
		mTab.setValue("PriceEntered", pp.getPriceStd());
		mTab.setValue("C_Currency_ID", new Integer(pp.getC_Currency_ID()));
		mTab.setValue("Discount", pp.getDiscount());
		
		// OpenUp. Gabriel Vila. 30/12/2010.
		// Seteo valor de descuento plano que es igual al descuento que obtiene adempiere en esta instancia
		
		mTab.setValue("flatdiscount", pp.getDiscount());
		
		// Fin OpenUp
		
		mTab.setValue("C_UOM_ID", new Integer(pp.getC_UOM_ID()));
		mTab.setValue("QtyOrdered", mTab.getValue("QtyEntered"));
		Env.setContext(ctx, WindowNo, "EnforcePriceLimit", pp.isEnforcePriceLimit() ? "Y" : "N");
		Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		
		//	Check/Update Warehouse Setting
		//	int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
		//	Integer wh = (Integer)mTab.getValue("M_Warehouse_ID");
		//	if (wh.intValue() != M_Warehouse_ID)
		//	{
		//		mTab.setValue("M_Warehouse_ID", new Integer(M_Warehouse_ID));
		//		ADialog.warn(,WindowNo, "WarehouseChanged");
		//	}

		// OpenUp. Gabriel Vila. 15/02/2012. Issue #719.
		// No me interesa verificacion de stock en este momento.
		// Comento codigo.
		
		/*
		if (Env.isSOTrx(ctx, WindowNo))
		{
			MProduct product = MProduct.get (ctx, M_Product_ID.intValue());
			if (product.isStocked())
			{
				BigDecimal QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
				int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
				int M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, "M_AttributeSetInstance_ID");
				BigDecimal available = MStorage.getQtyAvailable
						(M_Warehouse_ID, M_Product_ID.intValue(), M_AttributeSetInstance_ID, null);
				
				if (available == null)
					available = Env.ZERO;
				// OpenUp. Gabriel Vila. 28/10/2010.
				// Comento para no ver ventanas de warnings
				if (available.signum() == 0){
					//mTab.fireDataStatusEEvent ("NoQtyAvailable", "0", false);
				}	
				else if (available.compareTo(QtyOrdered) < 0){
					//mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", available.toString(), false);
					// Fin OpenUp
				}
				else
				{
					Integer C_OrderLine_ID = (Integer)mTab.getValue("C_OrderLine_ID");
					if (C_OrderLine_ID == null)
						C_OrderLine_ID = new Integer(0);
					BigDecimal notReserved = MOrderLine.getNotReserved(ctx, 
						M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
						C_OrderLine_ID.intValue());
					if (notReserved == null)
						notReserved = Env.ZERO;
					BigDecimal total = available.subtract(notReserved);
					if (total.compareTo(QtyOrdered) < 0)
					{
						String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + available 
							+ " - @QtyNotReserved@=" + notReserved + " = " + total);
						mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", 
							info, false);
					}
				}
			}
		}
		
		*/
		// Fin OpenUp.
		
		//
		if (steps) log.warning("fini");
		return tax (ctx, WindowNo, mTab, mField, value);
	}	//	product

	/**
	 *	Order Line - Charge.
	 * 		- updates PriceActual from Charge
	 * 		- sets PriceLimit, PriceList to zero
	 * 	Calls tax
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String charge (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Charge_ID = (Integer)value;
		if (C_Charge_ID == null || C_Charge_ID.intValue() == 0)
			return "";
		//	No Product defined
		if (mTab.getValue("M_Product_ID") != null)
		{
			mTab.setValue("C_Charge_ID", null);
			return "ChargeExclusively";
		}
		mTab.setValue("M_AttributeSetInstance_ID", null);
		mTab.setValue("S_ResourceAssignment_ID", null);
		mTab.setValue("C_UOM_ID", new Integer(100));	//	EA
		
		Env.setContext(ctx, WindowNo, "DiscountSchema", "N");
		String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Charge_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				mTab.setValue ("PriceEntered", rs.getBigDecimal (1));
				mTab.setValue ("PriceActual", rs.getBigDecimal (1));
				mTab.setValue ("PriceLimit", Env.ZERO);
				mTab.setValue ("PriceList", Env.ZERO);
				mTab.setValue ("Discount", Env.ZERO);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//
		return tax (ctx, WindowNo, mTab, mField, value);
	}	//	charge


	/**
	 *	Order Line - Tax.
	 *		- basis: Product, Charge, BPartner Location
	 *		- sets C_Tax_ID
	 *  Calls Amount
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String tax (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String column = mField.getColumnName();
		if (value == null || (MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, Env.getAD_Client_ID(ctx))))
			return "";
		if (steps) log.warning("init");
		
		//	Check Product
		int M_Product_ID = 0;
		if (column.equals("M_Product_ID"))
			M_Product_ID = ((Integer)value).intValue();
		else
			M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int C_Charge_ID = 0;
		if (column.equals("C_Charge_ID"))
			C_Charge_ID = ((Integer)value).intValue();
		else
			C_Charge_ID = Env.getContextAsInt(ctx, WindowNo, "C_Charge_ID");
		log.fine("Product=" + M_Product_ID + ", C_Charge_ID=" + C_Charge_ID);
		if (M_Product_ID == 0 && C_Charge_ID == 0)
			return amt(ctx, WindowNo, mTab, mField, value);		//

		//	Check Partner Location
		int shipC_BPartner_Location_ID = 0;
		if (column.equals("C_BPartner_Location_ID"))
			shipC_BPartner_Location_ID = ((Integer)value).intValue();
		else
			shipC_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_Location_ID");
		if (shipC_BPartner_Location_ID == 0)
			return amt(ctx, WindowNo, mTab, mField, value);		//
		log.fine("Ship BP_Location=" + shipC_BPartner_Location_ID);

		//
		Timestamp billDate = Env.getContextAsDate(ctx, WindowNo, "DateOrdered");
		log.fine("Bill Date=" + billDate);

		Timestamp shipDate = Env.getContextAsDate(ctx, WindowNo, "DatePromised");
		log.fine("Ship Date=" + shipDate);

		int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");
		log.fine("Org=" + AD_Org_ID);

		int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
		log.fine("Warehouse=" + M_Warehouse_ID);

		int billC_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "Bill_Location_ID");
		if (billC_BPartner_Location_ID == 0)
			billC_BPartner_Location_ID = shipC_BPartner_Location_ID;
		log.fine("Bill BP_Location=" + billC_BPartner_Location_ID);

		//
		int C_Tax_ID = Tax.get (ctx, M_Product_ID, C_Charge_ID, billDate, shipDate,
			AD_Org_ID, M_Warehouse_ID, billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
			"Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx")));
		log.info("Tax ID=" + C_Tax_ID);
		//
		if (C_Tax_ID == 0)
			mTab.fireDataStatusEEvent(CLogger.retrieveError());
		else
			mTab.setValue("C_Tax_ID", new Integer(C_Tax_ID));
		//
		if (steps) log.warning("fini");
		return amt(ctx, WindowNo, mTab, mField, value);
	}	//	tax


	/**
	 *	Order Line - Amount.
	 *		- called from QtyOrdered, Discount and PriceActual
	 *		- calculates Discount or Actual Amount
	 *		- calculates LineNetAmt
	 *		- enforces PriceLimit
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String amt (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

		if (steps) log.warning("init");
		int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");

		// OpenUp. INes Fernandez. 20/03/2015. Issue #3148. 
		// la precision usada para los calculos se modifica stdPrecision: usaba la definida para la moneda, ahora toma la 
		//precisión definida para la Lista de Precios.
		
		//int StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
		int StdPrecision = MPriceList.getPricePrecision(ctx, M_PriceList_ID);
		
		// Fin OpenUp. Issue #3148
		
		
		BigDecimal QtyEntered, QtyOrdered, PriceEntered, PriceActual, PriceLimit, Discount, PriceList;
		//	get values
		QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
		QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
		log.fine("QtyEntered=" + QtyEntered + ", Ordered=" + QtyOrdered + ", UOM=" + C_UOM_To_ID);
		
//		PriceEntered = ((BigDecimal)mTab.getValue("PriceEntered")).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP); 
//		PriceActual = ((BigDecimal)mTab.getValue("PriceActual")).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		PriceEntered = ((BigDecimal)mTab.getValue("PriceEntered")).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP); 
		PriceActual = ((BigDecimal)mTab.getValue("PriceActual")).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		Discount = (BigDecimal)mTab.getValue("Discount");
		PriceLimit = (BigDecimal)mTab.getValue("PriceLimit");
		PriceList = (BigDecimal)mTab.getValue("PriceList");
		log.fine("PriceList=" + PriceList + ", Limit=" + PriceLimit + ", Precision=" + StdPrecision);
		log.fine("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);

		
		//OpenUp. Nicolas Sarlabos. 18/10/2012
		//redondeo precio entrado segun escala de redondeo definida en la moneda
		//redondeo sin decimales si la unidad es una CAJA
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, Env.getAD_Client_ID(ctx))){
			MUOM uom = new MUOM(ctx,C_UOM_To_ID,null);
			if(uom!=null){
				if(uom.getX12DE355().equalsIgnoreCase("CX")) StdPrecision=0;
			}
			PriceEntered = ((BigDecimal)mTab.getValue("PriceEntered")).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			if(StdPrecision==0){
				int priceEntered = PriceEntered.intValue();
				int priceEnt = new Integer (priceEntered);
				PriceEntered = new BigDecimal (priceEnt);
			}
			mTab.setValue("PriceEntered", PriceEntered);
			//PriceActual = (BigDecimal)mTab.getValue("PriceActual");
			StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
			PriceActual = ((BigDecimal)mTab.getValue("PriceActual")).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			//Fin OpenUp
			//Discount = (BigDecimal)mTab.getValue("Discount");
			//OpenUp. Nicolas Sarlabos. 11/11/2012, obtengo porcentaje de descuento desde cliente o grupo
			int partnerID = Env.getContextAsInt (ctx, WindowNo, 0, "C_BPartner_ID"); //obtengo el ID del cliente en el cabezal
			MBPartner partner = new MBPartner(ctx,partnerID,null); //instancio modelo del cliente
			Discount = partner.getDiscountFromPartner();
			mTab.setValue("Discount", Discount);
			//Fin OpenUp.
			PriceLimit = (BigDecimal)mTab.getValue("PriceLimit");
			PriceList = (BigDecimal)mTab.getValue("PriceList");
			log.fine("PriceList=" + PriceList + ", Limit=" + PriceLimit + ", Precision=" + StdPrecision);
			log.fine("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);
		}

		//		No Product
		if (M_Product_ID == 0)
		{
			// if price change sync price actual and entered
			// else ignore
			if (mField.getColumnName().equals("PriceActual"))
			{
				PriceEntered = (BigDecimal) value;
				mTab.setValue("PriceEntered", value);
			}
			else if (mField.getColumnName().equals("PriceEntered"))
			{
				PriceActual = (BigDecimal) value;
				mTab.setValue("PriceActual", value);
			}
		}
		//	Product Qty changed - recalc price
		else if ((mField.getColumnName().equals("QtyOrdered") 
			|| mField.getColumnName().equals("QtyEntered")
			|| mField.getColumnName().equals("C_UOM_ID")
			|| mField.getColumnName().equals("M_Product_ID")) 
			&& !"N".equals(Env.getContext(ctx, WindowNo, "DiscountSchema")))
		{
			int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
			if (mField.getColumnName().equals("QtyEntered"))
				QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
					C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
			MProductPricing pp = new MProductPricing (M_Product_ID, C_BPartner_ID, QtyOrdered, IsSOTrx);
			pp.setM_PriceList_ID(M_PriceList_ID);
			int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
			pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
			Timestamp date = (Timestamp)mTab.getValue("DateOrdered");
			pp.setPriceDate(date);
			//
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, pp.getPriceStd());
			if (PriceEntered == null)
				PriceEntered = pp.getPriceStd();
			//
			log.fine("QtyChanged -> PriceActual=" + pp.getPriceStd() 
				+ ", PriceEntered=" + PriceEntered + ", Discount=" + pp.getDiscount());
			PriceActual = pp.getPriceStd();
			mTab.setValue("PriceActual", pp.getPriceStd());
			
			// OpenUp. Gabriel Vila. 20/12/2010.
			// COmento linea para que no modifique descuentos, ya que se calcula segun promociones.
			//mTab.setValue("Discount", pp.getDiscount());
			// Fin OpenUp.
			
			mTab.setValue("PriceEntered", PriceEntered);
			Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		}
		else if (mField.getColumnName().equals("PriceActual"))
		{
			PriceActual = (BigDecimal)value;
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			//
			log.fine("PriceActual=" + PriceActual 
				+ " -> PriceEntered=" + PriceEntered);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		else if (mField.getColumnName().equals("PriceEntered"))
		{
			PriceEntered = (BigDecimal)value;
			PriceActual = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceEntered);
			if (PriceActual == null)
				PriceActual = PriceEntered;
			//
			log.fine("PriceEntered=" + PriceEntered 
				+ " -> PriceActual=" + PriceActual);
			mTab.setValue("PriceActual", PriceActual);
		}
		
		//  Discount entered - Calculate Actual/Entered
		if (mField.getColumnName().equals("Discount"))
		{
			if ( PriceList.doubleValue() != 0 )
				PriceActual = new BigDecimal ((100.0 - Discount.doubleValue()) / 100.0 * PriceList.doubleValue());
			if (PriceActual.scale() > StdPrecision)
				PriceActual = PriceActual.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			mTab.setValue("PriceActual", PriceActual);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		//	calculate Discount

		// OpenUp. Gabriel Vila. 20/12/2010.
		// Comento calculo de descuento, ya que se hace por promociones
				
		/*else
		{
			if (PriceList.intValue() == 0)
				Discount = Env.ZERO;
			else
				Discount = new BigDecimal ((PriceList.doubleValue() - PriceActual.doubleValue()) / PriceList.doubleValue() * 100.0);
			if (Discount.scale() > 2)
				Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
			mTab.setValue("Discount", Discount);
		}*/
		
		// Fin OpenUp.
		
		log.fine("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);

		//	Check PriceLimit
		String epl = Env.getContext(ctx, WindowNo, "EnforcePriceLimit");
		boolean enforce = Env.isSOTrx(ctx, WindowNo) && epl != null && epl.equals("Y");
		if (enforce && MRole.getDefault().isOverwritePriceLimit())
			enforce = false;
		//	Check Price Limit?
		if (enforce && PriceLimit.doubleValue() != 0.0
		  && PriceActual.compareTo(PriceLimit) < 0)
		{
			PriceActual = PriceLimit;
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceLimit);
			if (PriceEntered == null)
				PriceEntered = PriceLimit;
			log.fine("(under) PriceEntered=" + PriceEntered + ", Actual" + PriceLimit);
			mTab.setValue ("PriceActual", PriceLimit);
			mTab.setValue ("PriceEntered", PriceEntered);
			mTab.fireDataStatusEEvent ("UnderLimitPrice", "", false);

			//	Repeat Discount calc
			// OpenUp. Gabriel Vila. 20/12/2010.
			// Comento calculo de descuento, ya que se hace por promociones
			/*
			if (PriceList.intValue() != 0)
			{
				Discount = new BigDecimal ((PriceList.doubleValue () - PriceActual.doubleValue ()) / PriceList.doubleValue () * 100.0);
				if (Discount.scale () > 2)
					Discount = Discount.setScale (2, BigDecimal.ROUND_HALF_UP);
				mTab.setValue ("Discount", Discount);
			}
			*/
			// Fin OpenUp.
		}

		//	Line Net Amt
		BigDecimal LineNetAmt = QtyOrdered.multiply(PriceActual);
		if (LineNetAmt.scale() > StdPrecision)
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		log.info("LineNetAmt=" + LineNetAmt);
		mTab.setValue("LineNetAmt", LineNetAmt);
		//
		return "";
	}	//	amt

	/**
	 *	Order Line - Quantity.
	 *		- called from C_UOM_ID, QtyEntered, QtyOrdered
	 *		- enforces qty UOM relationship
	 *  @param ctx context 
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String qty (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";
		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		if (steps) log.warning("init - M_Product_ID=" + M_Product_ID + " - " );
		BigDecimal QtyOrdered = Env.ZERO;
		BigDecimal QtyEntered, PriceActual, PriceEntered;
		
		//	No Product
		if (M_Product_ID == 0)
		{
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			QtyOrdered = QtyEntered;
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		//	UOM Changed - convert from Entered -> Product
		else if (mField.getColumnName().equals("C_UOM_ID"))
		{
			int C_UOM_To_ID = ((Integer)value).intValue();
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.fine("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID 
					+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);  
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			PriceActual = (BigDecimal)mTab.getValue("PriceActual");
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual; 
			log.fine("UOM=" + C_UOM_To_ID 
				+ ", QtyEntered/PriceActual=" + QtyEntered + "/" + PriceActual
				+ " -> " + conversion 
				+ " QtyOrdered/PriceEntered=" + QtyOrdered + "/" + PriceEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		//	QtyEntered changed - calculate QtyOrdered
		else if (mField.getColumnName().equals("QtyEntered"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyEntered = (BigDecimal)value;
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.fine("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID 
					+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);  
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			log.fine("UOM=" + C_UOM_To_ID 
				+ ", QtyEntered=" + QtyEntered
				+ " -> " + conversion 
				+ " QtyOrdered=" + QtyOrdered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		//	QtyOrdered changed - calculate QtyEntered (should not happen)
		else if (mField.getColumnName().equals("QtyOrdered"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyOrdered = (BigDecimal)value;
			int precision = MProduct.get(ctx, M_Product_ID).getUOMPrecision(); 
			BigDecimal QtyOrdered1 = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (QtyOrdered.compareTo(QtyOrdered1) != 0)
			{
				log.fine("Corrected QtyOrdered Scale " 
					+ QtyOrdered + "->" + QtyOrdered1);  
				QtyOrdered = QtyOrdered1;
				mTab.setValue("QtyOrdered", QtyOrdered);
			}
			QtyEntered = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyOrdered);
			if (QtyEntered == null)
				QtyEntered = QtyOrdered;
			boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
			log.fine("UOM=" + C_UOM_To_ID 
				+ ", QtyOrdered=" + QtyOrdered
				+ " -> " + conversion 
				+ " QtyEntered=" + QtyEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyEntered", QtyEntered);
		}
		else
		{
		//	QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
		}
		
		//	Storage
		if (M_Product_ID != 0 
			&& Env.isSOTrx(ctx, WindowNo)
			&& QtyOrdered.signum() > 0)		//	no negative (returns)
		{
			
			// OpenUp. Gabriel Vila. 15/02/2012. Issue #719.
			// No me interesa verificar stock en este momento
			// Comento codigo
			
			/*
			MProduct product = MProduct.get (ctx, M_Product_ID);
			if (product.isStocked())
			{
				int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
				int M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, "M_AttributeSetInstance_ID");
				BigDecimal available = MStorage.getQtyAvailable
					(M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID, null);
				if (available == null)
					available = Env.ZERO;
				if (available.signum() == 0)
					mTab.fireDataStatusEEvent ("NoQtyAvailable", "0", false);
				else if (available.compareTo(QtyOrdered) < 0)
					mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", available.toString(), false);
				else
				{
					Integer C_OrderLine_ID = (Integer)mTab.getValue("C_OrderLine_ID");
					if (C_OrderLine_ID == null)
						C_OrderLine_ID = new Integer(0);
					BigDecimal notReserved = MOrderLine.getNotReserved(ctx, 
						M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
						C_OrderLine_ID.intValue());
					if (notReserved == null)
						notReserved = Env.ZERO;
					BigDecimal total = available.subtract(notReserved);
					if (total.compareTo(QtyOrdered) < 0)
					{
						String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + available 
							+ "  -  @QtyNotReserved@=" + notReserved + "  =  " + total);
						mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", 
							info, false);
					}
				}
			}
			*/
			// Fin OpenUp.
		}
		//
		return "";
	}	//	qty
	
	/**
	 * Se setea porcentaje de descuento al pie de factura al cambiarse la sucursal.(cliente SOLUTION)
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 27/12/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String globalDiscount (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		BigDecimal Discount = Env.ZERO;
		int partnerLocID = Env.getContextAsInt (ctx, WindowNo, 0, "C_BPartner_Location_ID"); //obtengo el ID de sucursal del cliente en el cabezal
		
		if(partnerLocID>0){
			
			MBPartnerLocation loc = new MBPartnerLocation(ctx,partnerLocID,null);
			Discount = loc.getDiscount(); //obtengo descuento al pie de la sucursal
								
		}
						
		if(Discount.compareTo(Env.ZERO)<=0){ //si no obtuve descuento desde la sucursal busco en el cliente
			
			int partnerID = Env.getContextAsInt (ctx, WindowNo, 0, "C_BPartner_ID"); //obtengo el ID del cliente en el cabezal
			MBPartner partner = new MBPartner(ctx,partnerID,null); //instancio modelo del cliente
			Discount = partner.getDiscount();	//obtengo descuento al pie del cliente	
			
		}
					
		mTab.setValue("Discount", Discount);				
				
		return "";
				
	}
	
	/**
	 * Se setean datos provenientes de la orden de compra directa en la factura proveedor.
	 * OpenUp Ltda. #1588
	 * @author Nicolas Sarlabos - 12/12/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromPO (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		Integer C_Order_ID = (Integer)value;
		
		if (C_Order_ID == null || C_Order_ID.intValue() == 0)
			return "";
		
		MOrder order = new MOrder(ctx,C_Order_ID,null);
		
		int partnerID = order.getC_BPartner_ID();
		int payTermID = order.getC_PaymentTerm_ID();
		int priceListID = order.getM_PriceList_ID();
		int currencyID = order.getC_Currency_ID();
		
		mTab.setValue("C_BPartner_ID", partnerID);			
		mTab.setValue("C_PaymentTerm_ID", payTermID);
		mTab.setValue("M_PriceList_ID", priceListID);
		mTab.setValue("C_Currency_ID", currencyID);
				
		return "";
				
	}
	
//	/**Order Header - C_BPartner.
//	 * 		- sets PriceList, PriceListVersion 
//	 * Se setean lista de precios y versión vigente.
//	 * OpenUp Ltda. #4484
//	 * @author INes Fernandez - 02/07/2015
//	 * @see
//	 * @param ctx
//	 * @param WindowNo
//	 * @param mTab
//	 * @param mField
//	 * @param value C_BPartner_ID
//	 * @return
//	 */
//	public String setPriceListAndVersion(Properties ctx, int WindowNo,
//			GridTab mTab, GridField mField, Object value) {
//
//		if (isCalloutActive() || value == null)
//			return "";
//
//		int partnerID = Env.getContextAsInt(ctx, WindowNo, 0, "C_BPartner_ID"); 
//		Timestamp dateOrdered = Env.getContextAsDate(ctx, WindowNo,
//				"dateOrdered");
//
//		String sql1 = "SELECT M_PriceList_ID "
//				+ "FROM M_PriceList "
//				+ "WHERE M_PriceList_ID = "
//				+ "(SELECT PO_PriceList_ID FROM C_BPartner WHERE C_BPartner_ID = "
//				+ partnerID + ")";
//
//		int M_PriceList_ID = DB.getSQLValueEx(null, sql1);
//
//		if (M_PriceList_ID > 0) {
//			mTab.setValue("M_PriceList_ID", M_PriceList_ID);
//
//			String sql2 = "SELECT MAX (m_pricelist_version_id) FROM m_pricelist_version v INNER JOIN m_pricelist pl "
//					+ "ON v.m_pricelist_id = pl.m_pricelist_id "
//					+ "WHERE pl.isactive = 'Y' "
//					+ "AND v.isactive = 'Y' "
//					+ "AND pl.m_pricelist_id = "
//					+ M_PriceList_ID
//					+ " AND v.validFrom <= '" + dateOrdered + "'";
//
//			int M_PriceList_Version = DB.getSQLValueEx(null, sql2);
//			if (M_PriceList_Version > 0) {
//				mTab.setValue("M_PriceList_Version_ID", M_PriceList_Version);
//				return "";
//			} else
//				throw new AdempiereException(
//						"La lista de precios no tiene ninguna versión válida");
//
//		} else
//			throw new AdempiereException(
//					"El proveedor seleccionado no tiene lista de precios de compra asignada");
//
//	}
	
	
	/**Order Line - M_PriceList_ID.
	 * 		- sets PriceListVersion 
	 * Setea versión vigente.
	 * OpenUp Ltda. #4616
	 * @author INes Fernandez - 28/07/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value M_PriceList_ID
	 * @return
	 */
	public String setPriceListVersion(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (isCalloutActive() || value == null)
			return "";

		int id = (Integer)value;

		if (id <= 0) return "";
		
		Timestamp dateOrdered = Env.getContextAsDate(ctx, WindowNo, "DateOrdered");
		
		MPriceList pl = new MPriceList(ctx, id, null);
		MPriceListVersion versionVig = pl.getVersionVigente(dateOrdered);
		
		if (versionVig != null && versionVig.get_ID() > 0) { //encontro version vigente
			mTab.setValue("M_PriceList_Version_ID", versionVig.get_ID());
		} 
		else{
			throw new AdempiereException("La lista de precios seleccionada no tiene ninguna versión vigente");
		}

		return "";
	}
	
	
	
	/*************************************************************************
	 *	Order Line - Product.
	 *		- reset C_Charge_ID / M_AttributeSetInstance_ID
	 *		- PriceList, PriceStd, PriceLimit, C_Currency_ID, EnforcePriceLimit
	 *		- UOM
	 * Sets Prices, C_Tax_ID and C_UOM_ID from Product.
	 * OpenUp Ltda. #4616
	 *  @author INes Fernandez - 28/07/2015
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String product_PriceListOnOrderLine (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		if (value == null) return "";
		
		int mProductID = (Integer)value;

		if (mProductID <= 0) return "";
		
		int pricelist_id = 0, pricelist_version_id = 0;
		MProduct product = new MProduct(ctx, mProductID, null);
		int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, WindowNo, "C_BPartner_ID");
		
		mTab.setValue("C_UOM_ID", product.getC_UOM_ID());
		
		MTaxCategory taxCat = new MTaxCategory(ctx, product.getC_TaxCategory_ID(), null);
		int tax_ID = taxCat.getDefaultTax().get_ID();
		mTab.setValue("C_Tax_ID", tax_ID);

		//E. Bentancor - Se cambia la logica por Issue #5701  
//		if(mTab.getValue("M_PriceList_Version_ID") != null){
//			
//			int plv_ID = (Integer)mTab.getValue("M_PriceList_Version_ID");
//			if (plv_ID > 0){
				int vendorProductNo = 0;
				BigDecimal priceList = new BigDecimal(0);
				PreparedStatement pstmt = null;
				ResultSet rs = null;
//				String sql1 = "SELECT PricePO, vendorProductNo FROM C_BPartner_Product WHERE M_Product_ID = " + product.get_ID() + 
//						      " AND M_PriceList_Version_ID = "+ plv_ID;
				
				String sql1 = "SELECT bpp.PricePO, bpp.vendorProductNo, pl.m_pricelist_id, plv.m_pricelist_version_id " +
										"FROM C_BPartner_Product bpp join M_ProductPrice pp " +
											"on bpp.m_pricelist_version_id = pp.m_pricelist_version_id " +
										"join m_pricelist_version plv " +
											"ON pp.m_pricelist_version_id = plv.m_pricelist_version_id " +
										"join m_pricelist pl " +
											"ON plv.m_pricelist_id = pl.m_pricelist_id " +
										"join uy_vendor_pricelist vpl " +
											"on vpl.m_pricelist_id = pl.m_pricelist_id " + 
										"WHERE plv.isactive = 'Y' " +
										"and pl.issopricelist = 'N' " +
										" and vpl.c_bpartner_id = " + C_BPartner_ID +
										" and bpp.M_Product_ID = "+ product.get_ID();
				
				pstmt = DB.prepareStatement(sql1, null);

				try {
					rs = pstmt.executeQuery();
					if(rs.next()){
						priceList = rs.getBigDecimal(1);
						vendorProductNo = rs.getInt(2);
						pricelist_id = rs.getInt(3);
						pricelist_version_id = rs.getInt(4);
					}
				} catch (SQLException e) {
					return "";
				}finally{
					DB.close(rs, pstmt);
				}
				
				//BigDecimal priceList = DB.getSQLValueBDEx(null, sql1);
				
				if(priceList.compareTo(new BigDecimal(0)) == 0) throw new AdempiereException("No se encontró el precio vigente del producto seleccionado");		
				
				mTab.setValue("PriceList", priceList);
				mTab.setValue("PriceLimit", priceList);
				mTab.setValue("PriceActual", priceList);
				mTab.setValue("PriceEntered", priceList);
				mTab.setValue("m_pricelist_id", pricelist_id);
				mTab.setValue("m_pricelist_version_id", pricelist_version_id);
				if(vendorProductNo != 0){
					mTab.setValue("vendorProductNo", vendorProductNo);
				}else{
					mTab.setValue("vendorProductNo", null);
				}
			
//			}
//		}
		
		return amt_PriceListOnOrderLine(ctx, WindowNo, mTab, mField, mTab.getValue("PriceEntered") );
		
	}
	
	/**
 	 *  OpenUp Emiliano Bentancor 09/03/2016  
 	 *  Set Product ID when the Prov. Id is filled for OC
 	 *  #5408
 	 *  @param ctx context
 	 *  @param WindowNo current Window No
 	 *  @param mTab Grid Tab
 	 *  @param mField Grid Field
 	 *  @param value New Value
 	 *  @return null or error message
 	 */	
  	 public String setProdIDforProvId(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

  		if (value == null || value.equals("") || mTab.getValue("M_Product_ID") != null) return "";
  		

// 		if((Integer)mTab.getValue("M_PriceList_Version_ID") == null){
// 			throw new AdempiereException("Por favor seleccione una lista de precios!");
// 		}
  		
		int provId = Integer.valueOf((String)mTab.getValue("vendorProductNo"));
			if (provId > 0){ 
				
 			String sql1 = "select max(m_product_id) from C_BPartner_Product where vendorProductNo = '"+ provId +"'";
 			
 			int UY_Product_ID = DB.getSQLValueEx(null, sql1);
 			
 			if(UY_Product_ID <= 0) throw new AdempiereException("No se encontró ningun producto para el codigo insertado");
 			
 			mTab.setValue("M_Product_ID", UY_Product_ID);
 			
 			
 		}else{
 			mTab.setValue("UPC", "");
 		}
 		
 		return product_PriceListOnOrderLine(ctx, WindowNo, mTab, mField, mTab.getValue("M_Product_ID") );
 	} 
	
	
	/**
	 *  OpenUp Emiliano Bentancor 10/11/2015  
	 *  Set InternalCode when the Product ID is filled for OC
	 *  
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
	  public String setUPCnProdInternalCode(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
	
	 	if (value == null) return "";
	 		 
			// Get the shipment type ID from the value
	 	Integer UY_Product_ID=(Integer) value;
			
			if ((UY_Product_ID!=null)&&(!(UY_Product_ID.equals(0)))) {
				MProduct product = MProduct.get(ctx, UY_Product_ID);
				if(null!=product){
					mTab.setValue("InternalCode", product.getValue());
					
					// Obtengo ultimo codigo de barra para este producto
					String sqlUPC = " select max(uy_productupc_id) from uy_productupc where m_product_id =" + UY_Product_ID;
					int productUpcID = DB.getSQLValueEx(null, sqlUPC);
					if (productUpcID > 0){
						MProductUpc prodUPC = new MProductUpc(ctx, productUpcID, null);
						mTab.setValue("UPC", prodUPC.getUPC());
						}
				}
			}
			return("");
		}
	
	
	/**
	 *	Order Line - amt_PriceListOnOrderLine.
	 *		- called from QtyEntered, ManualDiscount and PriceEntered
	 *		- calculates Discount or Actual Amount
	 *		- calculates LineNetAmt
	 *  OpenUp Ltda. #4616
	 *  @author INes Fernandez - 31/07/2015
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String amt_PriceListOnOrderLine (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null) return "";
		
		BigDecimal priceEntered = Env.ZERO, qtyEntered = Env.ZERO, manualDisc = Env.ZERO;
		BigDecimal lineNetAmt = BigDecimal.ZERO;
		BigDecimal k; //factor multiplicador

		priceEntered = (BigDecimal)mTab.getValue("PriceEntered");
		qtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
		
		if (mTab.getValue("Discount2") != null){
			manualDisc = (BigDecimal)mTab.getValue("Discount2");	
		}
		
		if (manualDisc == null){
			manualDisc = Env.ZERO;
			mTab.setValue("Discount2", manualDisc);
		}
		if (priceEntered == null) return "";
		if (qtyEntered == null) return "";
		
		k = BigDecimal.ONE.subtract(manualDisc.divide(BigDecimal.valueOf(100)));

		lineNetAmt = priceEntered.multiply(qtyEntered).multiply(k).setScale(2, RoundingMode.HALF_UP);
		mTab.setValue("LineNetAmt", lineNetAmt);

		return "";
				
	}	//	amt_PriceListOnOrderLine
	
	
	
	
	/**
	 *	Order Line - bonusTypeSelection.
	 *		- called from UY_EsBonifCruzada and UY_BonificaSimple
	 *		sets one or the other		
	 *  OpenUp Ltda. #4616
	 *  @author INes Fernandez - 03/08/2015
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String bonusTypeSelection (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null) return "";
		
		if(mTab.getValueAsBoolean(mField.getColumnName())){
			if (mField.getColumnName().equals("UY_EsBonificCruzada")){
				 mTab.setValue("UY_BonificaSimple", false);
			 }
			else if (mField.getColumnName().equals("UY_BonificaSimple")){
				 mTab.setValue("UY_EsBonificCruzada", false);
			 }
		}
		return "";
				
	}	//	bonusTypeSelection
	
	
	/**
	 *  OpenUp Emiliano Bentancor 10/11/2015  
	 *  Set Product ID when the UPC is filled for OC
	 *  #5701
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
      public String setProdIDforOC(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

	   		if (value == null || value.equals("") || mTab.getValue("M_Product_ID") != null) return "";
	  			
	   		PreparedStatement pstmt = null;
			ResultSet rs = null;
			int UY_Product_ID = 0, pricelist_id = 0, pricelist_version_id = 0;
			int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, WindowNo, "C_BPartner_ID");
			
			String sql1 = "SELECT pp.m_product_id, pl.m_pricelist_id, plv.m_pricelist_version_id "
								+ " FROM M_ProductPrice pp JOIN UY_ProductUPC pu"
								+ " ON pp.m_product_id = pu.m_product_id"
								+ " join m_pricelist_version plv"
								+ " ON pp.m_pricelist_version_id = plv.m_pricelist_version_id"
								+ " join m_pricelist pl"
								+ " ON plv.m_pricelist_id = pl.m_pricelist_id"
								+ " join uy_vendor_pricelist vpl"
								+ " on vpl.m_pricelist_id = pl.m_pricelist_id" 
								+ " WHERE plv.isactive = 'Y'"
								+ " and pl.issopricelist = 'N'"
								+ " and pu.UPC = '"+ value + "'"
								+ " and vpl.c_bpartner_id = " + C_BPartner_ID;
			
			pstmt = DB.prepareStatement(sql1, null);
  			
  			try {
				rs = pstmt.executeQuery();
				if(rs.next()){
					UY_Product_ID = rs.getInt(1);
					pricelist_id = rs.getInt(2);
					pricelist_version_id = rs.getInt(3);
				}else{
					mTab.setValue("UPC", "");
					throw new AdempiereException("No se encontró el precio vigente del producto seleccionado");
				}
			} catch (SQLException e) {
				return "";
			}finally{
				DB.close(rs, pstmt);
			}
  			
  			mTab.setValue("m_pricelist_id", pricelist_id);
  			mTab.setValue("m_pricelist_version_id", pricelist_version_id);
  			
  			//E. Bentancor #5408
   			String sqlProvCod = " select vendorProductNo from C_BPartner_Product"+
   					 			" where c_bpartner_id = "+ C_BPartner_ID + " and m_product_id = " + UY_Product_ID;
  			int vendorProductNo = DB.getSQLValueEx(null, sqlProvCod);
  			mTab.setValue("vendorProductNo", String.valueOf(vendorProductNo));
  			// Fin #5408
  			
  			mTab.setValue("M_Product_ID", UY_Product_ID);

  			return("");
  	}
	

	
}	//	CalloutOrder

