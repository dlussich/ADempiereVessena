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

import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.X_UY_Allocation;

/**
 * Payment Callouts. org.compiere.model.CalloutPayment.*
 * @author Jorg Janke
 * @version $Id: CalloutPayment.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1803316 ] CalloutPayment: use C_Order.Bill_BPartner_ID
 * @author j2garcia - GlobalQSS
 *         <li>BF [ 2021745 ] Cannot assign project to payment with charge
 * @author Carlos Ruiz - GlobalQSS
 *         <li>BF [ 1933948 ] CalloutPayment working just with Draft Status
 */
public class CalloutPayment extends CalloutEngine
{

	/**
	 * Payment_Invoice. when Invoice selected - set C_Currency_ID -
	 * C_BPartner_ID - DiscountAmt = C_Invoice_Discount (ID, DateTrx) - PayAmt =
	 * invoiceOpen (ID) - Discount - WriteOffAmt = 0
	 * @param ctx context
	 * @param WindowNo current Window No
	 * @param mTab Grid Tab
	 * @param mField Grid Field
	 * @param value New Value
	 * @return null or error message
	 */
	public String invoice(Properties ctx, int WindowNo, GridTab mTab,
		GridField mField, Object value)
	{
		Integer C_Invoice_ID = (Integer)value;
		if (isCalloutActive () // assuming it is resetting value
			|| C_Invoice_ID == null || C_Invoice_ID.intValue () == 0)
			return "";
		mTab.setValue ("C_Order_ID", null);
		mTab.setValue ("C_Charge_ID", null);
		mTab.setValue ("IsPrepayment", Boolean.FALSE);
		//
		mTab.setValue ("DiscountAmt", Env.ZERO);
		mTab.setValue ("WriteOffAmt", Env.ZERO);
		// mTab.setValue ("IsOverUnderPayment", Boolean.FALSE);
		mTab.setValue ("OverUnderAmt", Env.ZERO);
		int C_InvoicePaySchedule_ID = 0;
		if (Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID.intValue ()
			&& Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
		{
			C_InvoicePaySchedule_ID = Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID");
		}
		// Payment Date
		Timestamp ts = (Timestamp)mTab.getValue ("DateTrx");
		if (ts == null)
			ts = new Timestamp (System.currentTimeMillis ());
		//
		String sql = "SELECT C_BPartner_ID,C_Currency_ID," // 1..2
			+ " invoiceOpen(C_Invoice_ID, ?)," // 3 #1
			+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx " // 4..5 #2/3
			+ "FROM C_Invoice WHERE C_Invoice_ID=?"; // #4
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, C_InvoicePaySchedule_ID);
			pstmt.setTimestamp (2, ts);
			pstmt.setInt (3, C_InvoicePaySchedule_ID);
			pstmt.setInt (4, C_Invoice_ID.intValue ());
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				mTab.setValue ("C_BPartner_ID", new Integer (rs.getInt (1)));
				int C_Currency_ID = rs.getInt (2); // Set Invoice Currency
				mTab.setValue ("C_Currency_ID", new Integer (C_Currency_ID));
				//
				BigDecimal InvoiceOpen = rs.getBigDecimal (3); // Set Invoice
				// OPen Amount
				if (InvoiceOpen == null)
					InvoiceOpen = Env.ZERO;
				BigDecimal DiscountAmt = rs.getBigDecimal (4); // Set Discount
				// Amt
				if (DiscountAmt == null)
					DiscountAmt = Env.ZERO;
				mTab.setValue ("PayAmt", InvoiceOpen.subtract (DiscountAmt));
				mTab.setValue ("DiscountAmt", DiscountAmt);
				// reset as dependent fields get reset
				Env.setContext (ctx, WindowNo, "C_Invoice_ID", C_Invoice_ID
					.toString ());
				mTab.setValue ("C_Invoice_ID", C_Invoice_ID);
			}
		}
		catch (SQLException e)
		{
			log.log (Level.SEVERE, sql, e);
			return e.getLocalizedMessage ();
		}
		finally
		{
			DB.close (rs, pstmt);
		}
		return docType (ctx, WindowNo, mTab, mField, value);
	} // invoice

	/**
	 * Payment_Order. when Waiting Payment Order selected - set C_Currency_ID -
	 * C_BPartner_ID - DiscountAmt = C_Invoice_Discount (ID, DateTrx) - PayAmt =
	 * invoiceOpen (ID) - Discount - WriteOffAmt = 0
	 * @param ctx context
	 * @param WindowNo current Window No
	 * @param mTab Grid Tab
	 * @param mField Grid Field
	 * @param value New Value
	 * @return null or error message
	 */
	public String order(Properties ctx, int WindowNo, GridTab mTab,
		GridField mField, Object value)
	{
		Integer C_Order_ID = (Integer)value;
		if (isCalloutActive () // assuming it is resetting value
			|| C_Order_ID == null || C_Order_ID.intValue () == 0)
			return "";
		mTab.setValue ("C_Invoice_ID", null);
		mTab.setValue ("C_Charge_ID", null);
		mTab.setValue ("IsPrepayment", Boolean.TRUE);
		//
		mTab.setValue ("DiscountAmt", Env.ZERO);
		mTab.setValue ("WriteOffAmt", Env.ZERO);
		mTab.setValue ("IsOverUnderPayment", Boolean.FALSE);
		mTab.setValue ("OverUnderAmt", Env.ZERO);
		// Payment Date
		Timestamp ts = (Timestamp)mTab.getValue ("DateTrx");
		if (ts == null)
			ts = new Timestamp (System.currentTimeMillis ());
		//
		String sql = "SELECT COALESCE(Bill_BPartner_ID, C_BPartner_ID) as C_BPartner_ID "
			+ ", C_Currency_ID "
			+ ", GrandTotal "
			+ "FROM C_Order WHERE C_Order_ID=?"; // #1
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, C_Order_ID.intValue ());
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				mTab.setValue ("C_BPartner_ID", new Integer (rs.getInt (1)));
				int C_Currency_ID = rs.getInt (2); // Set Order Currency
				mTab.setValue ("C_Currency_ID", new Integer (C_Currency_ID));
				//
				BigDecimal GrandTotal = rs.getBigDecimal (3); // Set Pay
				// Amount
				if (GrandTotal == null)
					GrandTotal = Env.ZERO;
				mTab.setValue ("PayAmt", GrandTotal);
			}
		}
		catch (SQLException e)
		{
			log.log (Level.SEVERE, sql, e);
			return e.getLocalizedMessage ();
		}
		finally
		{
			DB.close (rs, pstmt);
		}
		return docType (ctx, WindowNo, mTab, mField, value);
	} // order

	// 2008/07/18 Globalqss [ 2021745 ]
	// Deleted project method

	/**
	 * Payment_Charge. - reset - C_BPartner_ID, Invoice, Order, Project,
	 * Discount, WriteOff
	 * @param ctx context
	 * @param WindowNo current Window No
	 * @param mTab Grid Tab
	 * @param mField Grid Field
	 * @param value New Value
	 * @return null or error message
	 */
	public String charge(Properties ctx, int WindowNo, GridTab mTab,
		GridField mField, Object value)
	{
		Integer C_Charge_ID = (Integer)value;
		if (isCalloutActive () // assuming it is resetting value
			|| C_Charge_ID == null || C_Charge_ID.intValue () == 0)
			return "";
		mTab.setValue ("C_Invoice_ID", null);
		mTab.setValue ("C_Order_ID", null);
		// 2008/07/18 Globalqss [ 2021745 ]
		// mTab.setValue ("C_Project_ID", null);
		mTab.setValue ("IsPrepayment", Boolean.FALSE);
		//
		mTab.setValue ("DiscountAmt", Env.ZERO);
		mTab.setValue ("WriteOffAmt", Env.ZERO);
		mTab.setValue ("IsOverUnderPayment", Boolean.FALSE);
		mTab.setValue ("OverUnderAmt", Env.ZERO);
		return "";
	} // charge

	/**
	 * Payment_Document Type. Verify that Document Type (AP/AR) and Invoice
	 * (SO/PO) are in sync
	 * @param ctx context
	 * @param WindowNo current Window No
	 * @param mTab Grid Tab
	 * @param mField Grid Field
	 * @param value New Value
	 * @return null or error message
	 */
	public String docType(Properties ctx, int WindowNo, GridTab mTab,
		GridField mField, Object value)
	{
		int C_Invoice_ID = Env.getContextAsInt (ctx, WindowNo, "C_Invoice_ID");
		int C_Order_ID = Env.getContextAsInt (ctx, WindowNo, "C_Order_ID");
		int C_DocType_ID = Env.getContextAsInt (ctx, WindowNo, "C_DocType_ID");
		log.fine ("Payment_DocType - C_Invoice_ID=" + C_Invoice_ID
			+ ", C_DocType_ID=" + C_DocType_ID);
		MDocType dt = null;
		if (C_DocType_ID != 0)
		{
			dt = MDocType.get (ctx, C_DocType_ID);
			Env
				.setContext (ctx, WindowNo, "IsSOTrx", dt.isSOTrx () ? "Y"
					: "N");
		}
		// Invoice
		if (C_Invoice_ID != 0)
		{
			MInvoice inv = new MInvoice (ctx, C_Invoice_ID, null);
			if (dt != null)
			{
				if (inv.isSOTrx () != dt.isSOTrx ())
					return "PaymentDocTypeInvoiceInconsistent";
			}
		}
		// globalqss - Allow prepayment to Purchase Orders
		// Order Waiting Payment (can only be SO)
		// if (C_Order_ID != 0 && dt != null && !dt.isSOTrx())
		// return "PaymentDocTypeInvoiceInconsistent";
		// Order
		if (C_Order_ID != 0)
		{
			MOrder ord = new MOrder (ctx, C_Order_ID, null);
			if (dt != null)
			{
				if (ord.isSOTrx () != dt.isSOTrx ())
					return "PaymentDocTypeInvoiceInconsistent";
			}
		}
		return "";
	} // docType

	/**
	 * Payment_Amounts. Change of: - IsOverUnderPayment -> set OverUnderAmt to 0 -
	 * C_Currency_ID, C_ConvesionRate_ID -> convert all - PayAmt, DiscountAmt,
	 * WriteOffAmt, OverUnderAmt -> PayAmt make sure that add up to
	 * InvoiceOpenAmt
	 * @param ctx context
	 * @param WindowNo current Window No
	 * @param mTab Grid Tab
	 * @param mField Grid Field
	 * @param value New Value
	 * @param oldValue Old Value
	 * @return null or error message
	 */
	public String amounts(Properties ctx, int WindowNo, GridTab mTab,
		GridField mField, Object value, Object oldValue)
	{
		if (isCalloutActive ()) // assuming it is resetting value
			return "";
		int C_Invoice_ID = Env.getContextAsInt (ctx, WindowNo, "C_Invoice_ID");
		// New Payment
		if (Env.getContextAsInt (ctx, WindowNo, "C_Payment_ID") == 0
			&& Env.getContextAsInt (ctx, WindowNo, "C_BPartner_ID") == 0
			&& C_Invoice_ID == 0)
			return "";
		// Changed Column
		String colName = mField.getColumnName ();
		if (colName.equals ("IsOverUnderPayment") // Set Over/Under Amt to
			// Zero
			|| !"Y".equals (Env
				.getContext (ctx, WindowNo, "IsOverUnderPayment")))
			mTab.setValue ("OverUnderAmt", Env.ZERO);
		int C_InvoicePaySchedule_ID = 0;
		if (Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID
			&& Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
		{
			C_InvoicePaySchedule_ID = Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID");
		}
		// Get Open Amount & Invoice Currency
		BigDecimal InvoiceOpenAmt = Env.ZERO;
		int C_Currency_Invoice_ID = 0;
		if (C_Invoice_ID != 0)
		{
			Timestamp ts = (Timestamp)mTab.getValue ("DateTrx");
			if (ts == null)
				ts = new Timestamp (System.currentTimeMillis ());
			String sql = "SELECT C_BPartner_ID,C_Currency_ID," // 1..2
				+ " invoiceOpen(C_Invoice_ID,?)," // 3 #1
				+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx " // 4..5 #2/3
				+ "FROM C_Invoice WHERE C_Invoice_ID=?"; // #4
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement (sql, null);
				pstmt.setInt (1, C_InvoicePaySchedule_ID);
				pstmt.setTimestamp (2, ts);
				pstmt.setInt (3, C_InvoicePaySchedule_ID);
				pstmt.setInt (4, C_Invoice_ID);
				rs = pstmt.executeQuery ();
				if (rs.next ())
				{
					C_Currency_Invoice_ID = rs.getInt (2);
					InvoiceOpenAmt = rs.getBigDecimal (3); // Set Invoice Open
					// Amount
					if (InvoiceOpenAmt == null)
						InvoiceOpenAmt = Env.ZERO;
				}
			}
			catch (SQLException e)
			{
				log.log (Level.SEVERE, sql, e);
				return e.getLocalizedMessage ();
			}
			finally
			{
				DB.close (rs, pstmt);
				rs = null;
				pstmt = null;
			}
		} // get Invoice Info
		log.fine ("Open=" + InvoiceOpenAmt + ", C_Invoice_ID=" + C_Invoice_ID
			+ ", C_Currency_ID=" + C_Currency_Invoice_ID);
		// Get Info from Tab
		BigDecimal PayAmt = (BigDecimal)mTab.getValue ("PayAmt");
		BigDecimal DiscountAmt = (BigDecimal)mTab.getValue ("DiscountAmt");
		BigDecimal WriteOffAmt = (BigDecimal)mTab.getValue ("WriteOffAmt");
		BigDecimal OverUnderAmt = (BigDecimal)mTab.getValue ("OverUnderAmt");
		log.fine ("Pay=" + PayAmt + ", Discount=" + DiscountAmt + ", WriteOff="
			+ WriteOffAmt + ", OverUnderAmt=" + OverUnderAmt);
		// Get Currency Info
		int C_Currency_ID = ((Integer)mTab.getValue ("C_Currency_ID"))
			.intValue ();
		MCurrency currency = MCurrency.get (ctx, C_Currency_ID);
		Timestamp ConvDate = (Timestamp)mTab.getValue ("DateTrx");
		int C_ConversionType_ID = 0;
		Integer ii = (Integer)mTab.getValue ("C_ConversionType_ID");
		if (ii != null)
			C_ConversionType_ID = ii.intValue ();
		int AD_Client_ID = Env.getContextAsInt (ctx, WindowNo, "AD_Client_ID");
		int AD_Org_ID = Env.getContextAsInt (ctx, WindowNo, "AD_Org_ID");
		// Get Currency Rate
		BigDecimal CurrencyRate = Env.ONE;
		if ((C_Currency_ID > 0 && C_Currency_Invoice_ID > 0 && C_Currency_ID != C_Currency_Invoice_ID)
			|| colName.equals ("C_Currency_ID")
			|| colName.equals ("C_ConversionType_ID"))
		{
			log.fine ("InvCurrency=" + C_Currency_Invoice_ID + ", PayCurrency="
				+ C_Currency_ID + ", Date=" + ConvDate + ", Type="
				+ C_ConversionType_ID);
			CurrencyRate = MConversionRate.getRate (C_Currency_Invoice_ID,
				C_Currency_ID, ConvDate, C_ConversionType_ID, AD_Client_ID,
				AD_Org_ID);
			if (CurrencyRate == null || CurrencyRate.compareTo (Env.ZERO) == 0)
			{
				// mTab.setValue("C_Currency_ID", new
				// Integer(C_Currency_Invoice_ID)); // does not work
				if (C_Currency_Invoice_ID == 0)
					return ""; // no error message when no invoice is selected
				return "NoCurrencyConversion";
			}
			//
			InvoiceOpenAmt = InvoiceOpenAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			log.fine ("Rate=" + CurrencyRate + ", InvoiceOpenAmt="
				+ InvoiceOpenAmt);
		}
		// Currency Changed - convert all
		if (colName.equals ("C_Currency_ID")
			|| colName.equals ("C_ConversionType_ID"))
		{
			PayAmt = PayAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			mTab.setValue ("PayAmt", PayAmt);
			DiscountAmt = DiscountAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			mTab.setValue ("DiscountAmt", DiscountAmt);
			WriteOffAmt = WriteOffAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			mTab.setValue ("WriteOffAmt", WriteOffAmt);
			OverUnderAmt = OverUnderAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			mTab.setValue ("OverUnderAmt", OverUnderAmt);
		}
		// No Invoice - Set Discount, Witeoff, Under/Over to 0
		else if (C_Invoice_ID == 0)
		{
			// OpenUp. Gabriel Vila. 26/07/2010.
			// Comento lineas para que no me deje los descuentos en cero.
			/*if (Env.ZERO.compareTo (DiscountAmt) != 0)
				mTab.setValue ("DiscountAmt", Env.ZERO);*/
			// Actualizo total del recibo (PayAmt) para que sea igual al SubTotal + Descuentos
			BigDecimal total = ((BigDecimal)mTab.getValue("UY_SubTotal")).add((BigDecimal)mTab.getValue("DiscountAmt"));
			mTab.setValue("PayAmt", total);
			// Fin OpenUp.
			
			if (Env.ZERO.compareTo (WriteOffAmt) != 0)
				mTab.setValue ("WriteOffAmt", Env.ZERO);
			if (Env.ZERO.compareTo (OverUnderAmt) != 0)
				mTab.setValue ("OverUnderAmt", Env.ZERO);
		} else {
			boolean processed = mTab.getValueAsBoolean(MPayment.COLUMNNAME_Processed);
			if (colName.equals ("PayAmt")
				&& (!processed)
				&& "Y".equals (Env.getContext (ctx, WindowNo, "IsOverUnderPayment")))
			{
				OverUnderAmt = InvoiceOpenAmt.subtract (PayAmt).subtract (
					DiscountAmt).subtract (WriteOffAmt);
				mTab.setValue ("OverUnderAmt", OverUnderAmt);
			}
			else if (colName.equals ("PayAmt")
				&& (!processed))
			{
				WriteOffAmt = InvoiceOpenAmt.subtract (PayAmt).subtract (
					DiscountAmt).subtract (OverUnderAmt);
				mTab.setValue ("WriteOffAmt", WriteOffAmt);
			}
			else if (colName.equals ("IsOverUnderPayment")
				&& (!processed))
			{
				boolean overUnderPaymentActive = "Y".equals (Env.getContext (ctx,
					WindowNo, "IsOverUnderPayment"));
				if (overUnderPaymentActive)
				{
					OverUnderAmt = InvoiceOpenAmt.subtract (PayAmt).subtract (
						DiscountAmt);
					mTab.setValue ("WriteOffAmt", Env.ZERO);
					mTab.setValue ("OverUnderAmt", OverUnderAmt);
				}else{
					WriteOffAmt = InvoiceOpenAmt.subtract (PayAmt).subtract (
						DiscountAmt);
					mTab.setValue ("WriteOffAmt", WriteOffAmt);
					mTab.setValue ("OverUnderAmt", Env.ZERO);
				}
			}
			// Added Lines By Goodwill (02-03-2006)
			// Reason: we must make the callout is called just when docstatus is
			// draft
			// Old Code : else // calculate PayAmt
			// New Code :
			else if ((!processed)) // calculate
			// PayAmt
			// End By Goodwill
			{
				PayAmt = InvoiceOpenAmt.subtract (DiscountAmt).subtract (
					WriteOffAmt).subtract (OverUnderAmt);
				mTab.setValue ("PayAmt", PayAmt);
			}
		}
		return "";
	} // amounts
	

	/**
	 * OpenUp. Gabriel Vila. 23/07/2010.
	 */
	public String bankAccountCurrency(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
	
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MDocType doc = null;		

		try{
			
			int docID = Env.getContextAsInt (ctx, WindowNo, "C_DocType_ID");
			
			if(docID > 0) doc = new MDocType(ctx,docID,null);
			
			sql = " SELECT C_Currency_ID " + 
				  " FROM C_BankAccount " +
				  " WHERE C_BankAccount_ID =?";
			int bankAccountID = 0; 
			
			if (mTab.getValue("C_BankAccount_ID")==null) return "";
				
			bankAccountID =	(Integer)mTab.getValue("C_BankAccount_ID");
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, bankAccountID);
			rs = pstmt.executeQuery ();
			
			if (rs.next()) {
				// Seteo moneda
				mTab.setValue("C_Currency_ID", rs.getInt("C_Currency_ID"));

				// Si es documento de transferencia directa
				if(doc.getValue()!=null && doc.getValue().equalsIgnoreCase("transfdir")){
					
					BigDecimal currencyRate = Env.ZERO;

					// Si tengo moneda origen
					if (mTab.getValue("UY_C_Currency_From_ID") != null){
						
						int currencyFrom = ((Integer)mTab.getValue("UY_C_Currency_From_ID")).intValue();
						int currencyTo = rs.getInt("C_Currency_ID");						
						int adClientID = ((Integer)mTab.getValue("AD_Client_ID")).intValue();
						Timestamp dateTrx = (Timestamp)mTab.getValue("DateTrx");
						
						MConversionRate rate = MConversionRate.getLastRate(ctx, currencyFrom, currencyTo, adClientID, 0, MConversionType.TYPE_SPOT, dateTrx, null);
						if (rate != null){
							if (rate.getMultiplyRate() != null){
								if (rate.getMultiplyRate().compareTo(Env.ONE) > 0){
									currencyRate = rate.getMultiplyRate();
									mTab.setValue("IsDivideRate", false);
								}
								else{
									if (rate.getDivideRate() != null){
										currencyRate = rate.getDivideRate();
										mTab.setValue("IsDivideRate", true);
									}
								}
							}
						}
					}

					mTab.setValue("DivideRate", currencyRate);
					
				} 
				else {

					// Seteo tasa cambiaria divisora en caso de existir el campo
					mTab.setValue("DivideRate",MConversionRate.getDivideRate(142, rs.getInt("C_Currency_ID"), 
							(Timestamp)mTab.getValue("DateTrx"), 0, (Integer)mTab.getValue("AD_Client_ID"), (Integer)mTab.getValue("AD_Org_ID"))); 

				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}
	
	// Fin OpenUp.
	
	/**
	 * OpenUp. Nicolas Sarlabos. 17/08/2015.
	 */
	public String bankAccountCurrencyFrom(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
	
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MDocType doc = null;

		try{

			if (value == null) return "";

			int docID = Env.getContextAsInt (ctx, WindowNo, "C_DocType_ID");
			
			if(docID > 0) doc = new MDocType(ctx,docID,null);
			
			sql = " SELECT C_Currency_ID " + 
				  " FROM C_BankAccount " +
				  " WHERE C_BankAccount_ID =?";

			int bankAccountID = 0; 
			
			if (mTab.getValue("UY_C_BankAccount_From_ID")==null) return "";
				
			bankAccountID =	(Integer)mTab.getValue("UY_C_BankAccount_From_ID");
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, bankAccountID);
			rs = pstmt.executeQuery ();
			
			if (rs.next()) {
				// Seteo moneda
				mTab.setValue("UY_C_Currency_From_ID", rs.getInt("C_Currency_ID"));
				
				if(doc.getValue()!=null && doc.getValue().equalsIgnoreCase("transfdir")){

					BigDecimal currencyRate = Env.ZERO;

					// Si tengo moneda destino
					if (mTab.getValue("C_Currency_ID") != null){
						
						int currencyFrom = rs.getInt("C_Currency_ID");
						int currencyTo = ((Integer)mTab.getValue("C_Currency_ID")).intValue();
						int adClientID = ((Integer)mTab.getValue("AD_Client_ID")).intValue();
						Timestamp dateTrx = (Timestamp)mTab.getValue("DateTrx");
						
						MConversionRate rate = MConversionRate.getLastRate(ctx, currencyFrom, currencyTo, adClientID, 0, MConversionType.TYPE_SPOT, dateTrx, null);
						if (rate != null){
							if (rate.getMultiplyRate() != null){
								if (rate.getMultiplyRate().compareTo(Env.ONE) > 0){
									currencyRate = rate.getMultiplyRate();
									mTab.setValue("IsDivideRate", false);
								}
								else{
									if (rate.getDivideRate() != null){
										currencyRate = rate.getDivideRate();
										mTab.setValue("IsDivideRate", true);
									}
								}
							}
						}
					}

					mTab.setValue("DivideRate", currencyRate);
				}

			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}
	
	// Fin OpenUp.
	
	/**
	 * OpenUp. Gabriel Vila. 27/10/2011. Issue #896.
	 * Cuando se cambia el medio de pago de un recibo, intento setearle la
	 * cuenta bancaria siempre y cuando tenga una sola cuenta bancaria posible.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeTenderType(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		if (value == null) return "";
		if (mTab.getValue("C_Payment_ID") == null) return "";
		
		String tenderType = (String)value;
		if (tenderType.equalsIgnoreCase("")) return "";
		
		int cPaymentID = ((Integer)mTab.getValue("C_Payment_ID")).intValue();
		MPayment payment = new MPayment(Env.getCtx(), cPaymentID, null);
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
			sql = " SELECT C_BankAccount_ID " + 
				  " FROM C_BankAccount " +
				  " WHERE IsDefault = 'Y' " +
				  " AND C_Currency_ID =? " +
				  " AND TenderType =?";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, payment.getC_Currency_ID());
			pstmt.setString(2, tenderType);
			rs = pstmt.executeQuery ();

			int cont = 0, cBankAccountID = 0;
			while (rs.next()) {
				cBankAccountID = rs.getInt(1);
				cont++;
			}

			// Solo tengo una cuenta bancaria, la sugiero
			if (cont == 1) mTab.setValue("C_BankAccount_ID", cBankAccountID);
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}
	

	/***
	 * Al cambiar o indicar una forma de pago, debo actualizar las cuentas bancarias segun la misma.
	 * OpenUp Ltda. Issue #76 
	 * @author Gabriel Vila - 18/10/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changePaymentRule(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		if (value == null) return "";
		if (mTab.getValue("C_Payment_ID") == null) return "";
		
		int uyPaymentRuleID = (Integer)value;

		if (uyPaymentRuleID <= 0) return "";
		
		int cPaymentID = ((Integer)mTab.getValue("C_Payment_ID")).intValue();
		MPayment payment = new MPayment(Env.getCtx(), cPaymentID, null);
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
			sql = " SELECT C_BankAccount_ID " + 
				  " FROM C_BankAccount " +
				  " WHERE IsDefault = 'Y' " +
				  " AND C_Currency_ID =? " +
				  " AND uy_paymentrule_id =?";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, payment.getC_Currency_ID());
			pstmt.setInt(2, uyPaymentRuleID);
			rs = pstmt.executeQuery ();

			int cont = 0, cBankAccountID = 0;
			while (rs.next()) {
				cBankAccountID = rs.getInt(1);
				cont++;
			}

			// Solo tengo una cuenta bancaria, la sugiero
			if (cont == 1) mTab.setValue("C_BankAccount_ID", cBankAccountID);
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}

	
	/***
	 * Al cambiar la moneda, en caso que sea extranjera, tengo que cargar tasa de cambio
	 * para fecha de documento.
	 * OpenUp Ltda. Issue #539 
	 * @author Gabriel Vila - 15/03/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCurrency(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		MDocType docR = MDocType.forValue(ctx, "receiptmulticur", null);
		MDocType docP = MDocType.forValue(ctx, "paymentmulticur", null);

		if((null==docR || ((Integer)mTab.getValue("C_DocType_ID")).intValue() != docR.get_ID())
				||(null==docP || ((Integer)mTab.getValue("C_DocType_ID")).intValue() != docP.get_ID())){
			if (value == null) return "";
			
			if (mTab.getField(X_C_Payment.COLUMNNAME_CurrencyRate) == null){
				return "";
			}
			
			int cCurrency = ((Integer)value).intValue();
			if (cCurrency <= 0){
				mTab.setValue(X_C_Payment.COLUMNNAME_CurrencyRate, Env.ONE);
				return "";
			}
				
			if (cCurrency == 142){
				mTab.setValue(X_C_Payment.COLUMNNAME_CurrencyRate, Env.ONE);
				return "";
			}

			Timestamp fecha = (Timestamp)mTab.getValue(X_C_Payment.COLUMNNAME_DateTrx);
			BigDecimal dividerate = MConversionRate.getDivideRate(142, cCurrency, fecha, 0, 
									((Integer)mTab.getValue(X_C_Payment.COLUMNNAME_AD_Client_ID)).intValue(), 
									((Integer)mTab.getValue(X_C_Payment.COLUMNNAME_AD_Org_ID)).intValue());
				
			if (dividerate == null) dividerate = Env.ZERO;
				
			mTab.setValue(X_C_Payment.COLUMNNAME_CurrencyRate, dividerate);
				
			return "";
		}else{//Si es recibo multimoneda cuando se modifica moneda de recibo seteo tipo de cambio = 1
			mTab.setValue(X_C_Payment.COLUMNNAME_CurrencyRate, Env.ONE);
			mTab.setValue("C_Currency2_ID", "0");
			return "";	
		}

	}	
	
	
	/**
	 * OpenUp. Nicolas Sarlabos. 17/08/2015.
	 */
	public String setTotalMeTransf(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		
		if (value == null) return "";
		
		int docID = Env.getContextAsInt (ctx, WindowNo, "C_DocType_ID");
		
		if(docID > 0){
			
			MDocType doc = new MDocType(ctx,docID,null);
			
			if(doc.getValue()!=null && doc.getValue().equalsIgnoreCase("transfdir")){
				
				BigDecimal amtFrom = (BigDecimal)value;
				
				if(mTab.getValue("UY_C_Currency_From_ID")==mTab.getValue("C_Currency_ID")){
					
					mTab.setValue("uy_totalme", amtFrom);			
					
				} else {
					
					BigDecimal rate = (BigDecimal)mTab.getValue("DivideRate");
					
					if(rate!=null && rate.compareTo(Env.ZERO)>=0){
						
						mTab.setValue("uy_totalme", (((BigDecimal)mTab.getValue("uy_totalmn")).divide(rate, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));				
						
					}
				
				}				
				
			}		
			
		}		
		
		return "";
	}
	
	// Fin OpenUp.

	//Ini OpenUp 03/02/2016 Issue #5063 --> Descuentos en cascada para ventana Recibo de Pago Proveedor 
	/**
	 * Calcula el porcentaje sobre el monto de afectación directa al modificar el primer % de descuento
	 * @author OpenUp SBT Issue #5063 3/2/2016 10:51:35
	 */
	public String calculateDiscount1(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		if (value == null) return "";

		BigDecimal ptje = (BigDecimal) value;
		
		int payID = Integer.valueOf(mTab.get_ValueAsString("C_Payment_ID"));
		
		BigDecimal afectDirecta = this.getAfectacionDirectaInicial(mTab,payID); //AFectacion directa Inicial
		
		if(ptje.compareTo(Env.ZERO)>0){
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));
		
		}else if(ptje.compareTo(Env.ZERO)==0){//Si el primer descuento es cero el resto de los descuentos tiene que ser cero
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));
			mTab.setValue("Discount1",Env.ZERO);
			
		}else{
			mTab.setValue("Discount",Env.ZERO);
		}
		
		if (afectDirecta == null) {
			mTab.setValue("Discount",Env.ZERO);
		
		}

		return "";
	}
	

	/**
	 *Calcula el porcentaje sobre el monto de afectación directa al modificar el segundo % de descuento
	 * @author OpenUp SBT Issue#5413  3/2/2016 10:51:35
	 */
	public String calculateDiscount2(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		if (value == null) return "";
		
		BigDecimal ptje = (BigDecimal) value;
		int payID = Integer.valueOf(mTab.get_ValueAsString("C_Payment_ID"));
		
		BigDecimal pmerDto = (BigDecimal) mTab.getValue("Discount");
		BigDecimal afectDirecta = this.getAfectacionDirectaInicial(mTab,payID); //AFectacion directa Inicial
		
		if(ptje.compareTo(Env.ZERO)>0 && pmerDto.compareTo(Env.ZERO)>0){
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));
			
		}else if(ptje.compareTo(Env.ZERO)==0){//Si el descuento es cero tengo que recalcular descuento total
			
			mTab.setValue("Discount2",Env.ZERO);
			mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));
			
		}else{
			mTab.setValue("Discount1",Env.ZERO);
		}

		return "";
	}
	
	/**
	 * Calcula el porcentaje sobre el monto de afectación directa al modificar el tercer % de descuento
	 * @author OpenUp SBT Issue#5413 3/2/2016 10:51:35
	 */
	public String calculateDiscount3(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		if (value == null) return "";
		BigDecimal ptje = (BigDecimal) value;
		
		int payID = Integer.valueOf(mTab.get_ValueAsString("C_Payment_ID"));
		BigDecimal segDto = (BigDecimal) mTab.getValue("Discount1");
		BigDecimal afectDirecta = this.getAfectacionDirectaInicial(mTab,payID); //AFectacion directa Inicial

		if(ptje.compareTo(Env.ZERO)>0 && segDto.compareTo(Env.ZERO)>0){
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));
			
		}else if(ptje.compareTo(Env.ZERO)==0){//Si el descuento es cero tengo que recalcular descuento total

			mTab.setValue("Discount3",Env.ZERO);
			mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));
			
		}else{
			mTab.setValue("Discount2",Env.ZERO);
		}

		return "";
	}
	
	/**
	 * CCalcula el porcentaje sobre el monto de afectación directa al modificar el cuarto % de descuento
	 * Recibo de Pago de Proveedor 
	 * @author OpenUp SBT Issue#5413  3/2/2016 10:51:35
	 */
	public String calculateDiscount4(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		if (value == null) return "";
		BigDecimal ptje = (BigDecimal) value;
		int payID = Integer.valueOf(mTab.get_ValueAsString("C_Payment_ID"));
		
		BigDecimal afectDirecta = this.getAfectacionDirectaInicial(mTab,payID); //AFectacion directa Inicial
		BigDecimal tercerDto = (BigDecimal) mTab.getValue("Discount2");
	
		if(ptje.compareTo(Env.ZERO)>0 && tercerDto.compareTo(Env.ZERO)>0){
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));

		}else if(ptje.compareTo(Env.ZERO)==0 ){
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));
		}else{
			mTab.setValue("Discount3",Env.ZERO);
		}
	
		return "";
	}
	 
	/**
	 * Calcula el monto de descuento total al modificar el valor de descuento manual en 
	 * Recibo de Pago de Proveedor 
	 * @author OpenUp SBT Issue#5413  3/2/2016 10:51:35
	 */
	public String calculateDiscountManual(Properties ctx, int WindowNo, GridTab mTab,
				GridField mField, Object value){
			if (value == null) return "";
			BigDecimal descManual = (BigDecimal) value;
			int payID = Integer.valueOf(mTab.get_ValueAsString("C_Payment_ID"));
			
			if(descManual.compareTo(Env.ZERO)>=0){
				BigDecimal afectDirecta = this.getAfectacionDirectaInicial(mTab, payID);
				mTab.setValue("TotalDiscounts", this.recalcularDescuento(afectDirecta,mTab));
			}
		
			return "";
		}
	/**
	 * Obtener el monto incial de afectación directa del recibo en
	 * Recibo de Pago de Proveedor 
	 * @author OpenUp SBT Issue#5413  3/2/2016 10:51:35
	 */
	private BigDecimal getAfectacionDirectaInicial(GridTab mTab, int payID) {
		BigDecimal afectDirectaInicial = Env.ZERO;
		
		String sql = "";ResultSet rs = null;PreparedStatement pstmt = null;
		try{
//			sql = "select sum(CASE WHEN sign = -1 THEN amtallocated ELSE ((-1)*amtallocated) END) from UY_AllocDirectPayment where C_Payment_Id = ? ";
			sql = "select sum(CASE WHEN sign = -1 THEN amtallocated ELSE 0 END) "
					+ "FROM UY_AllocDirectPayment where C_Payment_Id = ? "; // PAra realizar el descuento solo se toma en cuenta los montos de facturas o de notas de debito
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, payID);
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				afectDirectaInicial = rs.getBigDecimal(1);
			}
			//SBT cambia el calculo los desc en cascada son sobre el monto a afectar
//			BigDecimal descManulActual = (BigDecimal) mTab.getValue("DiscountAmt");
//			BigDecimal subTotalActual = (BigDecimal) mTab.getValue("UY_SubTotal");
//			BigDecimal afectDctaActual = afectDirectaInicial.subtract(descManulActual.add(subTotalActual));
			BigDecimal afectDctaActual = afectDirectaInicial;
			return afectDctaActual;

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return afectDirectaInicial;
	}
	
	/**
	 * Calcular Descuentos en cascada para ventana
	 * Recibo de Pago de Proveedor 
	 * @author OpenUp SBT Issue#5413  3/2/2016 10:51:35
	 */
	private BigDecimal recalcularDescuento(BigDecimal afectDirecta,
			GridTab mTab) {
		BigDecimal primDto = (BigDecimal) mTab.getValue("Discount");
		BigDecimal sgdoDto = (BigDecimal) mTab.getValue("Discount1");
		BigDecimal tecrDto = (BigDecimal) mTab.getValue("Discount2");
		BigDecimal ctoDto = (BigDecimal) mTab.getValue("Discount3");
		BigDecimal afectDirectaInicial = afectDirecta;
		BigDecimal totalDescuento =  Env.ZERO;
		//Obtengo la afectación directa original
		
		if(primDto.compareTo(Env.ZERO)>0 && afectDirecta!=null){
			totalDescuento  =  afectDirectaInicial.multiply(primDto).divide(new BigDecimal("100"));
			if(sgdoDto.compareTo(Env.ZERO)>0){
				afectDirecta = afectDirectaInicial.subtract(totalDescuento);
				totalDescuento  =  totalDescuento.add(afectDirecta.multiply(sgdoDto).divide(new BigDecimal("100")));
				if(tecrDto.compareTo(Env.ZERO)>0){
					afectDirecta = afectDirectaInicial.subtract(totalDescuento);
					totalDescuento  =  totalDescuento.add(afectDirecta.multiply(tecrDto).divide(new BigDecimal("100")));
					if(ctoDto.compareTo(Env.ZERO)>0){
						afectDirecta = afectDirectaInicial.subtract(totalDescuento);
						totalDescuento  =  totalDescuento.add(afectDirecta.multiply(ctoDto).divide(new BigDecimal("100")));
					}
				}
			}
		}
		BigDecimal descManual = (BigDecimal) mTab.getValue("DiscountAmt");
		totalDescuento = totalDescuento.add(descManual);
		return totalDescuento.setScale(2, RoundingMode.HALF_UP);
	}//Fin OpenUp Descuentos en cascada para ventana Recibo de Pago Proveedor
	
	//Ini OpenUp #5413
	/**
	 * Cambio de moneda 2 en afectacion de ctacte.
	 * @author OpenUp SBT Issue #5413  11/2/2016 11:43:36
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @returnMDocType docR = MDocType.forValue(ctx, "receiptmulticur", null);
		MDocType docP = MDocType.forValue(ctx, "paymentmulticur", null);

		if((null==docR || ((Integer)mTab.getValue("C_DocType_ID")).intValue() != docR.get_ID())
				||(null==docP || ((Integer)mTab.getValue("C_DocType_ID")).intValue() != docP.get_ID())){
	 */
	public String changeCurrency2(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		MDocType doc = MDocType.forValue(ctx, "receiptmulticur", null);
		MDocType docP = MDocType.forValue(ctx, "paymentmulticur", null);
		if( (null!= doc && doc.get_ID()>0) || (null!= docP && docP.get_ID()>0)){
			if(((Integer)mTab.getValue("C_DocType_ID")).intValue() == doc.get_ID() || 
					((Integer)mTab.getValue("C_DocType_ID")).intValue() == docP.get_ID()){
				BigDecimal tcActual = (BigDecimal)mTab.getValue(X_C_Payment.COLUMNNAME_CurrencyRate);
				
				if (value == null) return "";
				
				if(tcActual.compareTo(Env.ONE)==0){	
					int cCurrency2 = ((Integer)value).intValue();
					if (cCurrency2 <= 0){
						mTab.setValue(X_C_Payment.COLUMNNAME_CurrencyRate, Env.ZERO);
						return "";
					}

					if (mTab.getValue("C_Currency2_ID") == null){
						mTab.setValue(X_C_Payment.COLUMNNAME_CurrencyRate, Env.ZERO);
						return "";
					}

					int cCurrency1 = ((Integer)mTab.getValue(X_C_Payment.COLUMNNAME_C_Currency_ID)).intValue();
					
					if (cCurrency2 == cCurrency1){
						MDocType doct = MDocType.forValue(ctx, "receiptmulticur", null);
						if( null!= doct && doc.get_ID()>0){
							if(((Integer)mTab.getValue("C_DocType_ID")).intValue() == doct.get_ID()){
								mTab.setValue("C_Currency2_ID", 0);
								return "";
							}
						}
						mTab.setValue(X_C_Payment.COLUMNNAME_CurrencyRate, Env.ZERO);
						return "";
					}

					Timestamp fecha = (Timestamp)mTab.getValue(X_C_Payment.COLUMNNAME_DateTrx);
//					BigDecimal dividerate = MConversionRate.getDivideRate(cCurrency1, cCurrency2, fecha, 0, 
//											((Integer)mTab.getValue(X_C_Payment.COLUMNNAME_AD_Client_ID)).intValue(), 
//											((Integer)mTab.getValue(X_C_Payment.COLUMNNAME_AD_Org_ID)).intValue());
					//Currency 2 >> currencyFrom ; Currency 1 >> currencyTo (Ejemplo moneda 1 pesos, moneda 2 dolares, convierto de dolare a pesos)
					BigDecimal dividerate = MConversionRate.getCurrencyRate(ctx,((Integer)mTab.getValue(X_C_Payment.COLUMNNAME_AD_Client_ID)).intValue(),
							cCurrency2, cCurrency1, fecha);
					if (dividerate == null) dividerate = Env.ZERO;
					
					mTab.setValue(X_C_Payment.COLUMNNAME_CurrencyRate, dividerate);
					
					return "";	
				}
				
			}
			 return "";
		}
		return "";
		
	}
	//Fin OpenUp #5413
	
	//Ini Openup SBT 15-02-2016 Issue #5413
    public String changeMonedaLinea(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		int moneda = ((Integer)value).intValue();
		if (moneda <= 0){
			return "";
		}else{
			mTab.setValue("C_Currency_ID", moneda);
		}
		return "";
	}
	//Fin Openup Issue #5413

	
} // CalloutPayment
