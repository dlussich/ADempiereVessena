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

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.acct.FactLine;
import org.compiere.apps.ADialog;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.ValueNamePair;
import org.eevolution.model.MHRConcept;
import org.openup.model.I_C_PaymentPayOrder;
import org.openup.model.I_UY_Payment_Resguardo;
import org.openup.model.I_UY_Receipt_Resguardo;
import org.openup.model.MAllocDetailPayment;
import org.openup.model.MAllocDirectPayment;
import org.openup.model.MAllocation;
import org.openup.model.MAllocationInvoice;
import org.openup.model.MAllocationPayment;
import org.openup.model.MFFReplenish;
import org.openup.model.MFFReplenishLine;
import org.openup.model.MHRConceptoLine;
import org.openup.model.MHRNovedades;
import org.openup.model.MHRParametros;
import org.openup.model.MHRProcesoNomina;
import org.openup.model.MLinePayment;
import org.openup.model.MMediosPago;
import org.openup.model.MMovBancariosHdr;
import org.openup.model.MPayOrder;
import org.openup.model.MPaymentPayOrder;
import org.openup.model.MPaymentResguardo;
import org.openup.model.MPaymentRule;
import org.openup.model.MReceiptResguardo;
import org.openup.model.MSUMAccountStatus;
import org.openup.model.MTRDriver;
import org.openup.model.MTRDriverCash;
import org.openup.model.X_C_PaymentPayOrder;
import org.openup.model.X_UY_AllocDetailPayment;
import org.openup.model.X_UY_AllocDirectPayment;
import org.openup.model.X_UY_LinePayment;
import org.openup.model.X_UY_MediosPago;
import org.openup.model.X_UY_Payment_Resguardo;
import org.openup.model.X_UY_Receipt_Resguardo;
import org.openup.util.Converter;
import org.openup.util.OpenUpUtils;

/**
 *  Payment Model.
 *  - retrieve and create payments for invoice
 *  <pre>
 *  Event chain
 *  - Payment inserted
 *      C_Payment_Trg fires
 *          update DocumentNo with payment summary
 *  - Payment posted (C_Payment_Post)
 *      create allocation line
 *          C_Allocation_Trg fires
 *              Update C_BPartner Open Item Amount
 *      update invoice (IsPaid)
 *      link invoice-payment if batch
 *
 *  Lifeline:
 *  -   Created by VPayment or directly
 *  -   When changed in VPayment
 *      - old payment is reversed
 *      - new payment created
 *
 *  When Payment is posed, the Allocation is made
 *  </pre>
 *  @author 	Jorg Janke
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li>FR [ 1948157  ]  Is necessary the reference for document reverse
 *  		@see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962 
 *			<li> FR [ 1866214 ]  
 *			@sse http://sourceforge.net/tracker/index.php?func=detail&aid=1866214&group_id=176962&atid=879335 
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org 
 *			@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962 
 *
 *  @author Carlos Ruiz - globalqss [ 2141475 ] Payment <> allocations must not be completed - implement lots of validations on prepareIt
 *  @version 	$Id: MPayment.java,v 1.4 2006/10/02 05:18:39 jjanke Exp $
 */
public final class MPayment extends X_C_Payment 
	implements DocAction, ProcessCall
{


    /**
     * 
     */
    private static final long serialVersionUID = 6200327948230438741L;

	/**
	 * 	Get Payments Of BPartner
	 *	@param ctx context
	 *	@param C_BPartner_ID id
	 *	@param trxName transaction
	 *	@return array
	 */
	public static MPayment[] getOfBPartner (Properties ctx, int C_BPartner_ID, String trxName)
	{
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		final String whereClause = "C_BPartner_ID=?";
		List <MPayment> list = new Query(ctx, I_C_Payment.Table_Name, whereClause, trxName)
		.setParameters(C_BPartner_ID)
		.list();

		//
		MPayment[] retValue = new MPayment[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getOfBPartner
	
	
	/**************************************************************************
	 *  Default Constructor
	 *  @param ctx context
	 *  @param  C_Payment_ID    payment to load, (0 create new payment)
	 *  @param trxName trx name
	 */
	public MPayment (Properties ctx, int C_Payment_ID, String trxName)
	{
		super (ctx, C_Payment_ID, trxName);
		//  New
		if (C_Payment_ID == 0)
		{
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_Drafted);
			setTrxType(TRXTYPE_Sales);
			//
			setR_AvsAddr (R_AVSZIP_Unavailable);
			setR_AvsZip (R_AVSZIP_Unavailable);
			//
			setIsReceipt (true);
			setIsApproved (false);
			setIsReconciled (false);
			setIsAllocated(false);
			setIsOnline (false);
			setIsSelfService(false);
			setIsDelayedCapture (false);
			setIsPrepayment(false);
			setProcessed(false);
			setProcessing(false);
			setPosted (false);
			//
			setPayAmt(Env.ZERO);
			setDiscountAmt(Env.ZERO);
			setTaxAmt(Env.ZERO);
			setWriteOffAmt(Env.ZERO);
			setIsOverUnderPayment (true);
			setOverUnderAmt(Env.ZERO);
			//
			setDateTrx (new Timestamp(System.currentTimeMillis()));
			setDateAcct (getDateTrx());
			setTenderType(TENDERTYPE_Check);
			
			// OpenUp. Gabriel Vila. 28/10/2011. Issue #896.
			setamtallocated(Env.ZERO);
			setamtopen(Env.ZERO);
			setAmtToAllocate(Env.ZERO);
			// Fin OpenUp
			
		}
		
	}   //  MPayment
	
	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *	@param trxName transaction
	 */
	public MPayment (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPayment

	/**	Temporary	Payment Processors		*/
	private MPaymentProcessor[]	m_mPaymentProcessors = null;
	/**	Temporary	Payment Processor		*/
	private MPaymentProcessor	m_mPaymentProcessor = null;
	/** Logger								*/
	private static CLogger		s_log = CLogger.getCLogger (MPayment.class);
	/** Error Message						*/
	private String				m_errorMessage = null;
	
	/** Reversal Indicator			*/
	public static String	REVERSE_INDICATOR = "^";
	
	/**
	 *  Reset Payment to new status
	 */
	public void resetNew()
	{
		setC_Payment_ID(0);		//	forces new Record
		set_ValueNoCheck ("DocumentNo", null);
		setDocAction(DOCACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
		setProcessed(false);
		setPosted (false);
		setIsReconciled (false);
		setIsAllocated(false);
		setIsOnline(false);
		setIsDelayedCapture (false);
	//	setC_BPartner_ID(0);
		setC_Invoice_ID(0);
		setC_Order_ID(0);
		setC_Charge_ID(0);
		setC_Project_ID(0);
		setIsPrepayment(false);
	}	//	resetNew

	/**
	 * 	Is Cashbook Transfer Trx
	 *	@return true if Cash Trx
	 */
	public boolean isCashTrx()
	{
		return "X".equals(getTenderType());
	}	//	isCashTrx
	
	/**************************************************************************
	 *  Set Credit Card.
	 *  Need to set PatmentProcessor after Amount/Currency Set
	 *
	 *  @param TrxType Transaction Type see TRX_
	 *  @param creditCardType CC type
	 *  @param creditCardNumber CC number
	 *  @param creditCardVV CC verification
	 *  @param creditCardExpMM CC Exp MM
	 *  @param creditCardExpYY CC Exp YY
	 *  @return true if valid
	 */
	public boolean setCreditCard (String TrxType, String creditCardType, String creditCardNumber,
		String creditCardVV, int creditCardExpMM, int creditCardExpYY)
	{
		setTenderType(TENDERTYPE_CreditCard);
		setTrxType(TrxType);
		//
		setCreditCardType (creditCardType);
		setCreditCardNumber (creditCardNumber);
		setCreditCardVV (creditCardVV);
		setCreditCardExpMM (creditCardExpMM);
		setCreditCardExpYY (creditCardExpYY);
		//
		int check = MPaymentValidate.validateCreditCardNumber(creditCardNumber, creditCardType).length()
			+ MPaymentValidate.validateCreditCardExp(creditCardExpMM, creditCardExpYY).length();
		if (creditCardVV.length() > 0)
			check += MPaymentValidate.validateCreditCardVV(creditCardVV, creditCardType).length();
		return check == 0;
	}   //  setCreditCard

	/**
	 *  Set Credit Card - Exp.
	 *  Need to set PatmentProcessor after Amount/Currency Set
	 *
	 *  @param TrxType Transaction Type see TRX_
	 *  @param creditCardType CC type
	 *  @param creditCardNumber CC number
	 *  @param creditCardVV CC verification
	 *  @param creditCardExp CC Exp
	 *  @return true if valid
	 */
	public boolean setCreditCard (String TrxType, String creditCardType, String creditCardNumber,
		String creditCardVV, String creditCardExp)
	{
		return setCreditCard(TrxType, creditCardType, creditCardNumber,
			creditCardVV, MPaymentValidate.getCreditCardExpMM(creditCardExp), 
			MPaymentValidate.getCreditCardExpYY(creditCardExp));
	}   //  setCreditCard

	/**
	 *  Set ACH BankAccount Info
	 *
	 *  @param C_BankAccount_ID bank account
	 *  @param isReceipt true if receipt
	 *  @return true if valid
	 */
	public boolean setBankACH (MPaySelectionCheck preparedPayment)
	{
		//	Our Bank
		setC_BankAccount_ID(preparedPayment.getParent().getC_BankAccount_ID());
		//	Target Bank
		int C_BP_BankAccount_ID = preparedPayment.getC_BP_BankAccount_ID();
		MBPBankAccount ba = new MBPBankAccount (preparedPayment.getCtx(), C_BP_BankAccount_ID, null);
		setRoutingNo(ba.getRoutingNo());
		setAccountNo(ba.getAccountNo());
		setDescription(preparedPayment.getC_PaySelection().getName());
		setIsReceipt (X_C_Order.PAYMENTRULE_DirectDebit.equals	//	AR only
				(preparedPayment.getPaymentRule()));
		if ( MPaySelectionCheck.PAYMENTRULE_DirectDebit.equals(preparedPayment.getPaymentRule()) )
			setTenderType(MPayment.TENDERTYPE_DirectDebit);
		else if ( MPaySelectionCheck.PAYMENTRULE_DirectDeposit.equals(preparedPayment.getPaymentRule()))
			setTenderType(MPayment.TENDERTYPE_DirectDeposit);
		//
		int check = MPaymentValidate.validateRoutingNo(getRoutingNo()).length()
			+ MPaymentValidate.validateAccountNo(getAccountNo()).length();
		return check == 0;
	}	//	setBankACH

	/**
	 *  Set ACH BankAccount Info
	 *
	 *  @param C_BankAccount_ID bank account
	 *  @param isReceipt true if receipt
	 * 	@param tenderType - Direct Debit or Direct Deposit
	 *  @param routingNo routing
	 *  @param accountNo account
	 *  @return true if valid
	 */
	public boolean setBankACH (int C_BankAccount_ID, boolean isReceipt, String tenderType, 
		String routingNo, String accountNo)
	{
		setTenderType (tenderType);
		setIsReceipt (isReceipt);
		//
		if (C_BankAccount_ID > 0
			&& (routingNo == null || routingNo.length() == 0 || accountNo == null || accountNo.length() == 0))
			setBankAccountDetails(C_BankAccount_ID);
		else
		{
			setC_BankAccount_ID(C_BankAccount_ID);
			setRoutingNo (routingNo);
			setAccountNo (accountNo);
		}
		setCheckNo ("");
		//
		int check = MPaymentValidate.validateRoutingNo(routingNo).length()
			+ MPaymentValidate.validateAccountNo(accountNo).length();
		return check == 0;
	}   //  setBankACH
	/**
	 *  Set Cash BankAccount Info
	 *
	 *  @param C_BankAccount_ID bank account
	 *  @param isReceipt true if receipt
	 * 	@param tenderType - Cash (Payment)
	 *  @return true if valid
	 */
	public boolean setBankCash (int C_BankAccount_ID, boolean isReceipt, String tenderType)
	{
		setTenderType (tenderType);
		setIsReceipt (isReceipt);
		//
		if (C_BankAccount_ID > 0)
			setBankAccountDetails(C_BankAccount_ID);
		else
		{
			setC_BankAccount_ID(C_BankAccount_ID);
		}
		//
		return true;
	}   //  setBankCash

	/**
	 *  Set Check BankAccount Info
	 *
	 *  @param C_BankAccount_ID bank account
	 *  @param isReceipt true if receipt
	 *  @param checkNo check no
	 *  @return true if valid
	 */
	public boolean setBankCheck (int C_BankAccount_ID, boolean isReceipt, String checkNo)
	{
		return setBankCheck (C_BankAccount_ID, isReceipt, null, null, checkNo);
	}	//	setBankCheck

	/**
	 *  Set Check BankAccount Info
	 *
	 *  @param C_BankAccount_ID bank account
	 *  @param isReceipt true if receipt
	 *  @param routingNo routing no
	 *  @param accountNo account no
	 *  @param checkNo chack no
	 *  @return true if valid
	 */
	public boolean setBankCheck (int C_BankAccount_ID, boolean isReceipt, 
		String routingNo, String accountNo, String checkNo)
	{
		setTenderType (TENDERTYPE_Check);
		setIsReceipt (isReceipt);
		//
		if (C_BankAccount_ID > 0
			&& (routingNo == null || routingNo.length() == 0 
				|| accountNo == null || accountNo.length() == 0))
			setBankAccountDetails(C_BankAccount_ID);
		else
		{
			setC_BankAccount_ID(C_BankAccount_ID);
			setRoutingNo (routingNo);
			setAccountNo (accountNo);
		}
		setCheckNo (checkNo);
		//
		int check = MPaymentValidate.validateRoutingNo(routingNo).length()
			+ MPaymentValidate.validateAccountNo(accountNo).length()
			+ MPaymentValidate.validateCheckNo(checkNo).length();
		return check == 0;       //  no error message
	}   //  setBankCheck

	/**
	 * 	Set Bank Account Details.
	 * 	Look up Routing No & Bank Acct No
	 * 	@param C_BankAccount_ID bank account
	 */
	public void setBankAccountDetails (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID == 0)
			return;
		setC_BankAccount_ID(C_BankAccount_ID);
		//
		String sql = "SELECT b.RoutingNo, ba.AccountNo "
			+ "FROM C_BankAccount ba"
			+ " INNER JOIN C_Bank b ON (ba.C_Bank_ID=b.C_Bank_ID) "
			+ "WHERE C_BankAccount_ID=?";
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, C_BankAccount_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				setRoutingNo (rs.getString(1));
				setAccountNo (rs.getString(2));
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}	//	setBankAccountDetails

	/**
	 *  Set Account Address
	 *
	 *  @param name name
	 *  @param street street
	 *  @param city city
	 *  @param state state
	 *  @param zip zip
	 * 	@param country country
	 */
	public void setAccountAddress (String name, String street,
		String city, String state, String zip, String country)
	{
		setA_Name (name);
		setA_Street (street);
		setA_City (city);
		setA_State (state);
		setA_Zip (zip);
		setA_Country(country);
	}   //  setAccountAddress

	
	/**************************************************************************
	 *  Process Payment
	 *  @return true if approved
	 */
	public boolean processOnline()
	{
		log.info ("Amt=" + getPayAmt());
		//
		setIsOnline(true);
		setErrorMessage(null);
		//	prevent charging twice
		if (isApproved())
		{
			log.info("Already processed - " + getR_Result() + " - " + getR_RespMsg());
			setErrorMessage("Payment already Processed");
			return true;
		}

		if (m_mPaymentProcessor == null)
			setPaymentProcessor();
		if (m_mPaymentProcessor == null)
		{
			log.log(Level.WARNING, "No Payment Processor Model");
			setErrorMessage("No Payment Processor Model");
			return false;
		}

		boolean approved = false;

		try
		{
			PaymentProcessor pp = PaymentProcessor.create(m_mPaymentProcessor, this);
			if (pp == null)
				setErrorMessage("No Payment Processor");
			else
			{
				// Validate before trying to process
				String msg = pp.validate();
				if (msg!=null && msg.trim().length()>0) {
					setErrorMessage(Msg.getMsg(getCtx(), msg));
				} else {
					// Process if validation succeeds
					approved = pp.processCC ();
					if (approved)
						setErrorMessage(null);
					else
						setErrorMessage("From " +  getCreditCardName() + ": " + getR_RespMsg());
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "processOnline", e);
			setErrorMessage("Payment Processor Error: " + e.getMessage());
		}
		setIsApproved(approved);
		return approved;
	}   //  processOnline

	/**
	 *  Process Online Payment.
	 *  implements ProcessCall after standard constructor
	 *  Called when pressing the Process_Online button in C_Payment
	 *
	 *  @param ctx Context
	 *  @param pi Process Info
	 *  @param trx transaction
	 *  @return true if the next process should be performed
	 */
	public boolean startProcess (Properties ctx, ProcessInfo pi, Trx trx)
	{
		log.info("startProcess - " + pi.getRecord_ID());
		boolean retValue = false;
		//
		if (pi.getRecord_ID() != get_ID())
		{
			log.log(Level.SEVERE, "startProcess - Not same Payment - " + pi.getRecord_ID());
			return false;
		}
		//  Process it
		retValue = processOnline();
		save();
		return retValue;    //  Payment processed
	}   //  startProcess

	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		// OpenUp. Gabriel Vila. 27/10/2011. Issue #896.
		// Para no estar tocando tanto el before save original de adempiere,
		// me hago uno para OpenUp y comento el codigo original.
		return this.beforeSaveOpenUp(newRecord);
		
		/*
		
		// @Trifon - CashPayments
		//if ( getTenderType().equals("X") ) {
		if ( isCashTrx() && !MSysConfig.getBooleanValue("CASH_AS_PAYMENT", true , getAD_Client_ID())) {
			// Cash Book Is mandatory
			if ( getC_CashBook_ID() <= 0 ) {
				log.saveError("Error", Msg.parseTranslation(getCtx(), "@Mandatory@: @C_CashBook_ID@"));
				return false;
			}
		} else {
			// Bank Account Is mandatory
			if ( getC_BankAccount_ID() <= 0 ) {
				log.saveError("Error", Msg.parseTranslation(getCtx(), "@Mandatory@: @C_BankAccount_ID@"));
				return false;
			}
		}
		// end @Trifon - CashPayments
		
		//	We have a charge
		if (getC_Charge_ID() != 0) 
		{
			if (newRecord || is_ValueChanged("C_Charge_ID"))
			{
				setC_Order_ID(0);
				setC_Invoice_ID(0);
				setWriteOffAmt(Env.ZERO);
				setDiscountAmt(Env.ZERO);
				setIsOverUnderPayment(false);
				setOverUnderAmt(Env.ZERO);
				setIsPrepayment(false);
			}
		}
		//	We need a BPartner
		else if (getC_BPartner_ID() == 0 && !isCashTrx())
		{
			if (getC_Invoice_ID() != 0)
				;
			else if (getC_Order_ID() != 0)
				;
			else
			{
				log.saveError("Error", Msg.parseTranslation(getCtx(), "@NotFound@: @C_BPartner_ID@"));
				return false;
			}
		}
		//	Prepayment: No charge and order or project (not as acct dimension)
		if (newRecord 
			|| is_ValueChanged("C_Charge_ID") || is_ValueChanged("C_Invoice_ID")
			|| is_ValueChanged("C_Order_ID") || is_ValueChanged("C_Project_ID"))
			setIsPrepayment (getC_Charge_ID() == 0 
				&& getC_BPartner_ID() != 0
				&& (getC_Order_ID() != 0 
					|| (getC_Project_ID() != 0 && getC_Invoice_ID() == 0)));
		if (isPrepayment())
		{
			if (newRecord 
				|| is_ValueChanged("C_Order_ID") || is_ValueChanged("C_Project_ID"))
			{
				setWriteOffAmt(Env.ZERO);
				setDiscountAmt(Env.ZERO);
				setIsOverUnderPayment(false);
				setOverUnderAmt(Env.ZERO);
			}
		}
		
		//	Document Type/Receipt
		if (getC_DocType_ID() == 0)
			setC_DocType_ID();
		else
		{
			MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
			setIsReceipt(dt.isSOTrx());
		}
		setDocumentNo();
		//
		if (getDateAcct() == null)
			setDateAcct(getDateTrx());
		//
		if (!isOverUnderPayment())
			setOverUnderAmt(Env.ZERO);
		
		//	Organization
		if ((newRecord || is_ValueChanged("C_BankAccount_ID"))
			&& getC_Charge_ID() == 0)	//	allow different org for charge
		{
			MBankAccount ba = MBankAccount.get(getCtx(), getC_BankAccount_ID());
			if (ba.getAD_Org_ID() != 0)
				setAD_Org_ID(ba.getAD_Org_ID());
		}
		
		// [ adempiere-Bugs-1885417 ] Validate BP on Payment Prepare or BeforeSave
		// there is bp and (invoice or order)
		if (getC_BPartner_ID() != 0 && (getC_Invoice_ID() != 0 || getC_Order_ID() != 0)) {
			if (getC_Invoice_ID() != 0) {
				MInvoice inv = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
				if (inv.getC_BPartner_ID() != getC_BPartner_ID()) {
					log.saveError("Error", Msg.parseTranslation(getCtx(), "BP different from BP Invoice"));
					return false;
				}
			}
			if (getC_Order_ID() != 0) {
				MOrder ord = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
				if (ord.getC_BPartner_ID() != getC_BPartner_ID()) {
					log.saveError("Error", Msg.parseTranslation(getCtx(), "BP different from BP Order"));
					return false;
				}
			}
		}

		return true;
		
		*/
		// Fin OpenUp
		
	}	//	beforeSave
	
	/**
	 * OpenUp. 09/09/2011. Mario Reyes. Issue #753.
	 * Validacion de numero de documentos en recibos.
	 */
	private void validateDocumentNo() {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "", whereReceipt = "";


		if (isReceipt()) whereReceipt = " AND isreceipt = 'Y'";
		else whereReceipt =  " AND isreceipt = 'N' AND c_bpartner_id = " + this.getC_BPartner_ID(); 

		sql = " SELECT c_payment_id " +
				  " FROM c_payment " +
				  " WHERE c_payment_id != " + this.getC_Payment_ID() +
				  " AND docstatus='CO' " + 
				  " AND documentno = '" + this.getDocumentNo() + "' " + whereReceipt;
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				throw new AdempiereException("Ya existe un recibo con este numero de documento.");
			}

		} catch (Exception e) {
			throw new AdempiereException(e.toString());
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	/**
	 * 	Get Allocated Amt in Payment Currency
	 *	@return amount or null
	 */
	public BigDecimal getAllocatedAmt ()
	{
		BigDecimal retValue = null;
		if (getC_Charge_ID() != 0)
			return getPayAmt();
		//
		String sql = "SELECT SUM(currencyConvert(al.Amount,"
				+ "ah.C_Currency_ID, p.C_Currency_ID,ah.DateTrx,p.C_ConversionType_ID, al.AD_Client_ID,al.AD_Org_ID)) "
			+ "FROM C_AllocationLine al"
			+ " INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID=ah.C_AllocationHdr_ID) "
			+ " INNER JOIN C_Payment p ON (al.C_Payment_ID=p.C_Payment_ID) "
			+ "WHERE al.C_Payment_ID=?"
			+ " AND ah.IsActive='Y' AND al.IsActive='Y'";
		//	+ " AND al.C_Invoice_ID IS NOT NULL";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_Payment_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getBigDecimal(1);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "getAllocatedAmt", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	//	log.fine("getAllocatedAmt - " + retValue);
		//	? ROUND(NVL(v_AllocatedAmt,0), 2);
		return retValue;
	}	//	getAllocatedAmt

	/**
	 * 	Test Allocation (and set allocated flag)
	 *	@return true if updated
	 */
	public boolean testAllocation()
	{
		//	Cash Trx always allocated!!! WHY???
/* @Trifon - CashPayments
		if (isCashTrx())
		{
			if (!isAllocated())
			{
				setIsAllocated(true);
				return true;
			}
			return false;
		}
*/
		//
		BigDecimal alloc = getAllocatedAmt();
		if (alloc == null)
			alloc = Env.ZERO;
		BigDecimal total = getPayAmt();
		if (!isReceipt())
			total = total.negate();
		boolean test = total.compareTo(alloc) == 0;
		boolean change = test != isAllocated();
		if (change)
			setIsAllocated(test);
		log.fine("Allocated=" + test 
			+ " (" + alloc + "=" + total + ")");
		return change;
	}	//	testAllocation
	
	/**
	 * 	Set Allocated Flag for payments
	 * 	@param ctx context
	 *	@param C_BPartner_ID if 0 all
	 *	@param trxName trx
	 */
	public static void setIsAllocated (Properties ctx, int C_BPartner_ID, String trxName)
	{
		int counter = 0;
		String sql = "SELECT * FROM C_Payment "
			+ "WHERE IsAllocated='N' AND DocStatus IN ('CO','CL')";
		if (C_BPartner_ID > 1)
			sql += " AND C_BPartner_ID=?";
		else
			sql += " AND AD_Client_ID=" + Env.getAD_Client_ID(ctx);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			if (C_BPartner_ID > 1)
				pstmt.setInt (1, C_BPartner_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MPayment pay = new MPayment (ctx, rs, trxName);
				if (pay.testAllocation())
					if (pay.save())
						counter++;
			}
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		s_log.config("#" + counter);
	}	//	setIsAllocated

	/**************************************************************************
	 * 	Set Error Message
	 *	@param errorMessage error message
	 */
	public void setErrorMessage(String errorMessage)
	{
		m_errorMessage = errorMessage;
	}	//	setErrorMessage

	/**
	 * 	Get Error Message
	 *	@return error message
	 */
	public String getErrorMessage()
	{
		return m_errorMessage;
	}	//	getErrorMessage


	/**
	 *  Set Bank Account for Payment.
	 *  @param C_BankAccount_ID C_BankAccount_ID
	 */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID == 0)
		{
			setPaymentProcessor();
			if (getC_BankAccount_ID() == 0)
				throw new IllegalArgumentException("Can't find Bank Account");
		}
		else
			super.setC_BankAccount_ID(C_BankAccount_ID);
	}	//	setC_BankAccount_ID

	/**
	 *  Set BankAccount and PaymentProcessor
	 *  @return true if found
	 */
	public boolean setPaymentProcessor ()
	{
		return setPaymentProcessor (getTenderType(), getCreditCardType());
	}	//	setPaymentProcessor

	/**
	 *  Set BankAccount and PaymentProcessor
	 *  @param tender TenderType see TENDER_
	 *  @param CCType CC Type see CC_
	 *  @return true if found
	 */
	public boolean setPaymentProcessor (String tender, String CCType)
	{
		m_mPaymentProcessor = null;
		//	Get Processor List
		if (m_mPaymentProcessors == null || m_mPaymentProcessors.length == 0)
			m_mPaymentProcessors = MPaymentProcessor.find (getCtx(), tender, CCType, getAD_Client_ID(),
				getC_Currency_ID(), getPayAmt(), get_TrxName());
		//	Relax Amount
		if (m_mPaymentProcessors == null || m_mPaymentProcessors.length == 0)
			m_mPaymentProcessors = MPaymentProcessor.find (getCtx(), tender, CCType, getAD_Client_ID(),
				getC_Currency_ID(), Env.ZERO, get_TrxName());
		if (m_mPaymentProcessors == null || m_mPaymentProcessors.length == 0)
			return false;

		//	Find the first right one
		for (int i = 0; i < m_mPaymentProcessors.length; i++)
		{
			if (m_mPaymentProcessors[i].accepts (tender, CCType))
			{
				m_mPaymentProcessor = m_mPaymentProcessors[i];
			}
		}
		if (m_mPaymentProcessor != null)
			setC_BankAccount_ID (m_mPaymentProcessor.getC_BankAccount_ID());
		//
		return m_mPaymentProcessor != null;
	}   //  setPaymentProcessor


	/**
	 * 	Get Accepted Credit Cards for PayAmt (default 0)
	 *	@return credit cards
	 */
	public ValueNamePair[] getCreditCards ()
	{
		return getCreditCards(getPayAmt());
	}	//	getCreditCards


	/**
	 * 	Get Accepted Credit Cards for amount
	 *	@param amt trx amount
	 *	@return credit cards
	 */
	public ValueNamePair[] getCreditCards (BigDecimal amt)
	{
		try
		{
			if (m_mPaymentProcessors == null || m_mPaymentProcessors.length == 0)
				m_mPaymentProcessors = MPaymentProcessor.find (getCtx (), null, null, 
					getAD_Client_ID (), getC_Currency_ID (), amt, get_TrxName());
			//
			HashMap<String,ValueNamePair> map = new HashMap<String,ValueNamePair>(); //	to eliminate duplicates
			for (int i = 0; i < m_mPaymentProcessors.length; i++)
			{
				if (m_mPaymentProcessors[i].isAcceptAMEX ())
					map.put (CREDITCARDTYPE_Amex, getCreditCardPair (CREDITCARDTYPE_Amex));
				if (m_mPaymentProcessors[i].isAcceptDiners ())
					map.put (CREDITCARDTYPE_Diners, getCreditCardPair (CREDITCARDTYPE_Diners));
				if (m_mPaymentProcessors[i].isAcceptDiscover ())
					map.put (CREDITCARDTYPE_Discover, getCreditCardPair (CREDITCARDTYPE_Discover));
				if (m_mPaymentProcessors[i].isAcceptMC ())
					map.put (CREDITCARDTYPE_MasterCard, getCreditCardPair (CREDITCARDTYPE_MasterCard));
				if (m_mPaymentProcessors[i].isAcceptCorporate ())
					map.put (CREDITCARDTYPE_PurchaseCard, getCreditCardPair (CREDITCARDTYPE_PurchaseCard));
				if (m_mPaymentProcessors[i].isAcceptVisa ())
					map.put (CREDITCARDTYPE_Visa, getCreditCardPair (CREDITCARDTYPE_Visa));
			} //	for all payment processors
			//
			ValueNamePair[] retValue = new ValueNamePair[map.size ()];
			map.values ().toArray (retValue);
			log.fine("getCreditCards - #" + retValue.length + " - Processors=" + m_mPaymentProcessors.length);
			return retValue;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}	//	getCreditCards

	/**
	 * 	Get Type and name pair
	 *	@param CreditCardType credit card Type
	 *	@return pair
	 */
	private ValueNamePair getCreditCardPair (String CreditCardType)
	{
		return new ValueNamePair (CreditCardType, getCreditCardName(CreditCardType));
	}	//	getCreditCardPair

	
	/**************************************************************************
	 *  Credit Card Number
	 *  @param CreditCardNumber CreditCard Number
	 */
	public void setCreditCardNumber (String CreditCardNumber)
	{
		super.setCreditCardNumber (MPaymentValidate.checkNumeric(CreditCardNumber));
	}	//	setCreditCardNumber
	
	/**
	 *  Verification Code
	 *  @param newCreditCardVV CC verification
	 */
	public void setCreditCardVV(String newCreditCardVV)
	{
		super.setCreditCardVV (MPaymentValidate.checkNumeric(newCreditCardVV));
	}	//	setCreditCardVV

	/**
	 *  Two Digit CreditCard MM
	 *  @param CreditCardExpMM Exp month
	 */
	public void setCreditCardExpMM (int CreditCardExpMM)
	{
		if (CreditCardExpMM < 1 || CreditCardExpMM > 12)
			;
		else
			super.setCreditCardExpMM (CreditCardExpMM);
	}	//	setCreditCardExpMM

	/**
	 *  Two digit CreditCard YY (til 2020)
	 *  @param newCreditCardExpYY 2 or 4 digit year
	 */
	public void setCreditCardExpYY (int newCreditCardExpYY)
	{
		int CreditCardExpYY = newCreditCardExpYY;
		if (newCreditCardExpYY > 1999)
			CreditCardExpYY = newCreditCardExpYY-2000;
		super.setCreditCardExpYY(CreditCardExpYY);
	}	//	setCreditCardExpYY

	/**
	 *  CreditCard Exp  MMYY
	 *  @param mmyy Exp in form of mmyy
	 *  @return true if valid
	 */
	public boolean setCreditCardExp (String mmyy)
	{
		if (MPaymentValidate.validateCreditCardExp(mmyy).length() != 0)
			return false;
		//
		String exp = MPaymentValidate.checkNumeric(mmyy);
		String mmStr = exp.substring(0,2);
		String yyStr = exp.substring(2,4);
		setCreditCardExpMM (Integer.parseInt(mmStr));
		setCreditCardExpYY (Integer.parseInt(yyStr));
		return true;
	}   //  setCreditCardExp


	/**
	 *  CreditCard Exp  MMYY
	 *  @param delimiter / - or null
	 *  @return Exp
	 */
	public String getCreditCardExp(String delimiter)
	{
		String mm = String.valueOf(getCreditCardExpMM());
		String yy = String.valueOf(getCreditCardExpYY());

		StringBuffer retValue = new StringBuffer();
		if (mm.length() == 1)
			retValue.append("0");
		retValue.append(mm);
		//
		if (delimiter != null)
			retValue.append(delimiter);
		//
		if (yy.length() == 1)
			retValue.append("0");
		retValue.append(yy);
		//
		return (retValue.toString());
	}   //  getCreditCardExp

	/**
	 *  MICR
	 *  @param MICR MICR
	 */
	public void setMicr (String MICR)
	{
		super.setMicr (MPaymentValidate.checkNumeric(MICR));
	}	//	setBankMICR

	/**
	 *  Routing No
	 *  @param RoutingNo Routing No
	 */
	public void setRoutingNo(String RoutingNo)
	{
		// super.setRoutingNo (MPaymentValidate.checkNumeric(RoutingNo));
		super.setRoutingNo (RoutingNo);
	}	//	setBankRoutingNo


	/**
	 *  Bank Account No
	 *  @param AccountNo AccountNo
	 */
	public void setAccountNo (String AccountNo)
	{
		super.setAccountNo (MPaymentValidate.checkNumeric(AccountNo));
	}	//	setBankAccountNo


	/**
	 *  Check No
	 *  @param CheckNo Check No
	 */
	public void setCheckNo(String CheckNo)
	{
		super.setCheckNo(MPaymentValidate.checkNumeric(CheckNo));
	}	//	setBankCheckNo


	/**
	 *  Set DocumentNo to Payment info.
	 * 	If there is a R_PnRef that is set automatically 
	 */
	@SuppressWarnings("unused")
	private void setDocumentNo()
	{
		//	Cash Transfer
		if ("X".equals(getTenderType()))
			return;
		//	Current Document No
		String documentNo = getDocumentNo();
		//	Existing reversal
		if (documentNo != null 
			&& documentNo.indexOf(REVERSE_INDICATOR) >= 0)
			return;
		
		//	If external number exists - enforce it 
		if (getR_PnRef() != null && getR_PnRef().length() > 0)
		{
			if (!getR_PnRef().equals(documentNo))
				setDocumentNo(getR_PnRef());
			return;
		}
		
		documentNo = "";
		// globalqss - read configuration to assign credit card or check number number for Payments
		//	Credit Card
		if (TENDERTYPE_CreditCard.equals(getTenderType()))
		{
			if (MSysConfig.getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CREDIT_CARD", true, getAD_Client_ID())) {
				documentNo = getCreditCardType()
					+ " " + Obscure.obscure(getCreditCardNumber())
					+ " " + getCreditCardExpMM() 
					+ "/" + getCreditCardExpYY();
			}
		}
		//	Own Check No
		else if (TENDERTYPE_Check.equals(getTenderType())
			&& !isReceipt()
			&& getCheckNo() != null && getCheckNo().length() > 0)
		{
			if (MSysConfig.getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CHECK_ON_PAYMENT", true, getAD_Client_ID())) {
				documentNo = getCheckNo();
			}
		}
		//	Customer Check: Routing: Account #Check 
		else if (TENDERTYPE_Check.equals(getTenderType())
			&& isReceipt())
		{
			if (MSysConfig.getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CHECK_ON_RECEIPT", true, getAD_Client_ID())) {
				if (getRoutingNo() != null)
					documentNo = getRoutingNo() + ": ";
				if (getAccountNo() != null)
					documentNo += getAccountNo();
				if (getCheckNo() != null)
				{
					if (documentNo.length() > 0)
						documentNo += " ";
					documentNo += "#" + getCheckNo();
				}
			}
		}

		//	Set Document No
		documentNo = documentNo.trim();
		if (documentNo.length() > 0)
			setDocumentNo(documentNo);
	}	//	setDocumentNo

	/**
	 * 	Set Refernce No (and Document No)
	 *	@param R_PnRef reference
	 */
	public void setR_PnRef (String R_PnRef)
	{
		super.setR_PnRef (R_PnRef);
		if (R_PnRef != null)
			setDocumentNo (R_PnRef);
	}	//	setR_PnRef
	
	//	---------------

	/**
	 *  Set Payment Amount
	 *  @param PayAmt Pay Amt
	 */
	public void setPayAmt (BigDecimal PayAmt)
	{
		super.setPayAmt(PayAmt == null ? Env.ZERO : PayAmt);
	}	//	setPayAmt

	/**
	 *  Set Payment Amount
	 *
	 * @param C_Currency_ID currency
	 * @param payAmt amount
	 */
	public void setAmount (int C_Currency_ID, BigDecimal payAmt)
	{
		if (C_Currency_ID == 0)
			C_Currency_ID = MClient.get(getCtx()).getC_Currency_ID();
		setC_Currency_ID(C_Currency_ID);
		setPayAmt(payAmt);
	}   //  setAmount

	/**
	 *  Discount Amt
	 *  @param DiscountAmt Discount
	 */
	public void setDiscountAmt (BigDecimal DiscountAmt)
	{
		super.setDiscountAmt (DiscountAmt == null ? Env.ZERO : DiscountAmt);
	}	//	setDiscountAmt

	/**
	 *  WriteOff Amt
	 *  @param WriteOffAmt WriteOff
	 */
	public void setWriteOffAmt (BigDecimal WriteOffAmt)
	{
		super.setWriteOffAmt (WriteOffAmt == null ? Env.ZERO : WriteOffAmt);
	}	//	setWriteOffAmt

	/**
	 *  OverUnder Amt
	 *  @param OverUnderAmt OverUnder
	 */
	public void setOverUnderAmt (BigDecimal OverUnderAmt)
	{
		super.setOverUnderAmt (OverUnderAmt == null ? Env.ZERO : OverUnderAmt);
		setIsOverUnderPayment(getOverUnderAmt().compareTo(Env.ZERO) != 0);
	}	//	setOverUnderAmt

	/**
	 *  Tax Amt
	 *  @param TaxAmt Tax
	 */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		super.setTaxAmt (TaxAmt == null ? Env.ZERO : TaxAmt);
	}	//	setTaxAmt

	/**
	 * 	Set Info from BP Bank Account
	 *	@param ba BP bank account
	 */
	public void setBP_BankAccount (MBPBankAccount ba)
	{
		log.fine("" + ba);
		if (ba == null)
			return;
		setC_BPartner_ID(ba.getC_BPartner_ID());
		setAccountAddress(ba.getA_Name(), ba.getA_Street(), ba.getA_City(),
			ba.getA_State(), ba.getA_Zip(), ba.getA_Country());
		setA_EMail(ba.getA_EMail());
		setA_Ident_DL(ba.getA_Ident_DL());
		setA_Ident_SSN(ba.getA_Ident_SSN());
		//	CC
		if (ba.getCreditCardType() != null)
			setCreditCardType(ba.getCreditCardType());
		if (ba.getCreditCardNumber() != null)
			setCreditCardNumber(ba.getCreditCardNumber());
		if (ba.getCreditCardExpMM() != 0)
			setCreditCardExpMM(ba.getCreditCardExpMM());
		if (ba.getCreditCardExpYY() != 0)
			setCreditCardExpYY(ba.getCreditCardExpYY());
		if (ba.getCreditCardVV() != null)
			setCreditCardVV(ba.getCreditCardVV());
		//	Bank
		if (ba.getAccountNo() != null)
			setAccountNo(ba.getAccountNo());
		if (ba.getRoutingNo() != null)
			setRoutingNo(ba.getRoutingNo());
	}	//	setBP_BankAccount

	/**
	 * 	Save Info from BP Bank Account
	 *	@param ba BP bank account
	 * 	@return true if saved
	 */
	public boolean saveToBP_BankAccount (MBPBankAccount ba)
	{
		if (ba == null)
			return false;
		ba.setA_Name(getA_Name());
		ba.setA_Street(getA_Street());
		ba.setA_City(getA_City());
		ba.setA_State(getA_State());
		ba.setA_Zip(getA_Zip());
		ba.setA_Country(getA_Country());
		ba.setA_EMail(getA_EMail());
		ba.setA_Ident_DL(getA_Ident_DL());
		ba.setA_Ident_SSN(getA_Ident_SSN());
		//	CC
		ba.setCreditCardType(getCreditCardType());
		ba.setCreditCardNumber(getCreditCardNumber());
		ba.setCreditCardExpMM(getCreditCardExpMM());
		ba.setCreditCardExpYY(getCreditCardExpYY());
		ba.setCreditCardVV(getCreditCardVV());
		//	Bank
		if (getAccountNo() != null)
			ba.setAccountNo(getAccountNo());
		if (getRoutingNo() != null)
			ba.setRoutingNo(getRoutingNo());
		//	Trx
		ba.setR_AvsAddr(getR_AvsAddr());
		ba.setR_AvsZip(getR_AvsZip());
		//
		boolean ok = ba.save(get_TrxName());
		log.fine("saveToBP_BankAccount - " + ba);
		return ok;
	}	//	setBP_BankAccount

	/**
	 * 	Set Doc Type bases on IsReceipt
	 */
	@SuppressWarnings("unused")
	private void setC_DocType_ID ()
	{
		setC_DocType_ID(isReceipt());
	}	//	setC_DocType_ID

	/**
	 * 	Set Doc Type
	 * 	@param isReceipt is receipt
	 */
	public void setC_DocType_ID (boolean isReceipt)
	{
		setIsReceipt(isReceipt);
		String sql = "SELECT C_DocType_ID FROM C_DocType WHERE IsActive='Y' AND AD_Client_ID=? AND DocBaseType=? ORDER BY IsDefault DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getAD_Client_ID());
			if (isReceipt)
				pstmt.setString(2, X_C_DocType.DOCBASETYPE_ARReceipt);
			else
				pstmt.setString(2, X_C_DocType.DOCBASETYPE_APPayment);
			rs = pstmt.executeQuery();
			if (rs.next())
				setC_DocType_ID(rs.getInt(1));
			else
				log.warning ("setDocType - NOT found - isReceipt=" + isReceipt);
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}	//	setC_DocType_ID

	
	/**
	 * 	Set Document Type
	 *	@param C_DocType_ID doc type
	 */
	public void setC_DocType_ID (int C_DocType_ID)
	{
	//	if (getDocumentNo() != null && getC_DocType_ID() != C_DocType_ID)
	//		setDocumentNo(null);
		super.setC_DocType_ID(C_DocType_ID);
	}	//	setC_DocType_ID
	
	/**
	 * 	Verify Document Type with Invoice
	 * @param pAllocs 
	 *	@return true if ok
	 */
	@SuppressWarnings("unused")
	private boolean verifyDocType(MPaymentAllocate[] pAllocs)
	{
		if (getC_DocType_ID() == 0)
			return false;
		//
		Boolean documentSO = null;
		//	Check Invoice First
		if (getC_Invoice_ID() > 0)
		{
			String sql = "SELECT idt.IsSOTrx "
				+ "FROM C_Invoice i"
				+ " INNER JOIN C_DocType idt ON (i.C_DocType_ID=idt.C_DocType_ID) "
				+ "WHERE i.C_Invoice_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getC_Invoice_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
					documentSO = new Boolean ("Y".equals(rs.getString(1)));
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}	//	now Order - in Adempiere is allowed to pay PO or receive SO
		else if (getC_Order_ID() > 0)
		{
			String sql = "SELECT odt.IsSOTrx "
				+ "FROM C_Order o"
				+ " INNER JOIN C_DocType odt ON (o.C_DocType_ID=odt.C_DocType_ID) "
				+ "WHERE o.C_Order_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getC_Order_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
					documentSO = new Boolean ("Y".equals(rs.getString(1)));
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}	//	now Charge
		else if (getC_Charge_ID() > 0) 
		{
			// do nothing about charge
		} // now payment allocate
		else
		{
			if (pAllocs.length > 0) {
				for (MPaymentAllocate pAlloc : pAllocs) {
					String sql = "SELECT idt.IsSOTrx "
						+ "FROM C_Invoice i"
						+ " INNER JOIN C_DocType idt ON (i.C_DocType_ID=idt.C_DocType_ID) "
						+ "WHERE i.C_Invoice_ID=?";
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try
					{
						pstmt = DB.prepareStatement(sql, get_TrxName());
						pstmt.setInt(1, pAlloc.getC_Invoice_ID());
						rs = pstmt.executeQuery();
						if (rs.next()) {
							if (documentSO != null) { // already set, compare with current
								if (documentSO.booleanValue() != ("Y".equals(rs.getString(1)))) {
									return false;
								}
							} else {
								documentSO = new Boolean ("Y".equals(rs.getString(1)));
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
						rs = null;
						pstmt = null;
					}
				}
			}
		}
		
		//	DocumentType
		Boolean paymentSO = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT IsSOTrx "
			+ "FROM C_DocType "
			+ "WHERE C_DocType_ID=?";
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_DocType_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
				paymentSO = new Boolean ("Y".equals(rs.getString(1)));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//	No Payment info
		if (paymentSO == null)
			return false;
		setIsReceipt(paymentSO.booleanValue());
			
		//	We have an Invoice .. and it does not match
		if (documentSO != null 
				&& documentSO.booleanValue() != paymentSO.booleanValue())
			return false;
		//	OK
		return true;
	}	//	verifyDocType

	/**
	 * 	Verify Payment Allocate is ignored (must not exists) if the payment header has charge/invoice/order
	 * @param pAllocs 
	 *	@return true if ok
	 */
	@SuppressWarnings("unused")
	private boolean verifyPaymentAllocateVsHeader(MPaymentAllocate[] pAllocs) {
		if (pAllocs.length > 0) {
			if (getC_Charge_ID() > 0 || getC_Invoice_ID() > 0 || getC_Order_ID() > 0)
				return false;
		}
		return true;
	}

	/**
	 * 	Verify Payment Allocate Sum must be equal to the Payment Amount
	 * @param pAllocs 
	 *	@return true if ok
	 */
	@SuppressWarnings("unused")
	private boolean verifyPaymentAllocateSum(MPaymentAllocate[] pAllocs) {
		BigDecimal sumPaymentAllocates = Env.ZERO;
		if (pAllocs.length > 0) {
			for (MPaymentAllocate pAlloc : pAllocs)
				sumPaymentAllocates = sumPaymentAllocates.add(pAlloc.getAmount());
			if (getPayAmt().compareTo(sumPaymentAllocates) != 0)
				return false;
		}
		return true;
	}

	/**
	 *	Get ISO Code of Currency
	 *	@return Currency ISO
	 */
	public String getCurrencyISO()
	{
		return MCurrency.getISO_Code (getCtx(), getC_Currency_ID());
	}	//	getCurrencyISO

	/**
	 * 	Get Document Status
	 *	@return Document Status Clear Text
	 */
	public String getDocStatusName()
	{
		return MRefList.getListName(getCtx(), 131, getDocStatus());
	}	//	getDocStatusName

	/**
	 *	Get Name of Credit Card
	 *	@return Name
	 */
	public String getCreditCardName()
	{
		return getCreditCardName(getCreditCardType());
	}	//	getCreditCardName

	/**
	 *	Get Name of Credit Card
	 * 	@param CreditCardType credit card type
	 *	@return Name
	 */
	public String getCreditCardName(String CreditCardType)
	{
		if (CreditCardType == null)
			return "--";
		else if (CREDITCARDTYPE_MasterCard.equals(CreditCardType))
			return "MasterCard";
		else if (CREDITCARDTYPE_Visa.equals(CreditCardType))
			return "Visa";
		else if (CREDITCARDTYPE_Amex.equals(CreditCardType))
			return "Amex";
		else if (CREDITCARDTYPE_ATM.equals(CreditCardType))
			return "ATM";
		else if (CREDITCARDTYPE_Diners.equals(CreditCardType))
			return "Diners";
		else if (CREDITCARDTYPE_Discover.equals(CreditCardType))
			return "Discover";
		else if (CREDITCARDTYPE_PurchaseCard.equals(CreditCardType))
			return "PurchaseCard";
		return "?" + CreditCardType + "?";
	}	//	getCreditCardName

	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription
	
	
	/**
	 * 	Get Pay Amt
	 * 	@param absolute if true the absolute amount (i.e. negative if payment)
	 *	@return amount
	 */
	public BigDecimal getPayAmt (boolean absolute)
	{
		if (isReceipt())
			return super.getPayAmt();
		return super.getPayAmt().negate();
	}	//	getPayAmt
	
	/**
	 * 	Get Pay Amt in cents
	 *	@return amount in cents
	 */
	public int getPayAmtInCents ()
	{
		BigDecimal bd = super.getPayAmt().multiply(Env.ONEHUNDRED);
		return bd.intValue();
	}	//	getPayAmtInCents
	
	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	process
	
	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success 
	 */
	public boolean unlockIt()
	{
		log.info(toString());
		setProcessing(false);
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success 
	 */
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt

	
	/**************************************************************************
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{

		// OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
		// Comento metodo original y llamo a la localizacion de OpenUp.
		return this.prepareItOpenUp();
		// Fin Issue #896.
		
		/*
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (! MPaySelectionCheck.deleteGeneratedDraft(getCtx(), getC_Payment_ID(), get_TrxName())) {
			m_processMsg = "Could not delete draft generated payment selection lines";
			return DocAction.STATUS_Invalid;
		}

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), 
			isReceipt() ? X_C_DocType.DOCBASETYPE_ARReceipt : X_C_DocType.DOCBASETYPE_APPayment, getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		
		//	Unsuccessful Online Payment
		if (isOnline() && !isApproved())
		{
			if (getR_Result() != null)
				m_processMsg = "@OnlinePaymentFailed@";
			else
				m_processMsg = "@PaymentNotProcessed@";
			return DocAction.STATUS_Invalid;
		}
		
		//	Waiting Payment - Need to create Invoice & Shipment
		if (getC_Order_ID() != 0 && getC_Invoice_ID() == 0)
		{	//	see WebOrder.process
			MOrder order = new MOrder (getCtx(), getC_Order_ID(), get_TrxName());
			if (DOCSTATUS_WaitingPayment.equals(order.getDocStatus()))
			{
				order.setC_Payment_ID(getC_Payment_ID());
				order.setDocAction(X_C_Order.DOCACTION_WaitComplete);
				order.set_TrxName(get_TrxName());
			//	boolean ok = 
				order.processIt (X_C_Order.DOCACTION_WaitComplete);
				m_processMsg = order.getProcessMsg();
				order.saveEx(get_TrxName());
				//	Set Invoice
				MInvoice[] invoices = order.getInvoices();
				int length = invoices.length;
				if (length > 0)		//	get last invoice
					setC_Invoice_ID (invoices[length-1].getC_Invoice_ID());
				//
				if (getC_Invoice_ID() == 0)
				{
					m_processMsg = "@NotFound@ @C_Invoice_ID@";
					return DocAction.STATUS_Invalid;
				}
			}	//	WaitingPayment
		}
		
		MPaymentAllocate[] pAllocs = MPaymentAllocate.get(this);
		
		//	Consistency of Invoice / Document Type and IsReceipt
		if (!verifyDocType(pAllocs))
		{
			m_processMsg = "@PaymentDocTypeInvoiceInconsistent@";
			return DocAction.STATUS_Invalid;
		}

		//	Payment Allocate is ignored if charge/invoice/order exists in header
		if (!verifyPaymentAllocateVsHeader(pAllocs))
		{
			m_processMsg = "@PaymentAllocateIgnored@";
			return DocAction.STATUS_Invalid;
		}

		//	Payment Amount must be equal to sum of Allocate amounts
		if (!verifyPaymentAllocateSum(pAllocs))
		{
			m_processMsg = "@PaymentAllocateSumInconsistent@";
			return DocAction.STATUS_Invalid;
		}

		//	Do not pay when Credit Stop/Hold
		if (!isReceipt())
		{
			MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
			if (X_C_BPartner.SOCREDITSTATUS_CreditStop.equals(bp.getSOCreditStatus()))
			{
				m_processMsg = "@BPartnerCreditStop@ - @TotalOpenBalance@=" 
					+ bp.getTotalOpenBalance()
					+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
				return DocAction.STATUS_Invalid;
			}
			if (X_C_BPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus()))
			{
				m_processMsg = "@BPartnerCreditHold@ - @TotalOpenBalance@=" 
					+ bp.getTotalOpenBalance()
					+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
				return DocAction.STATUS_Invalid;
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
		
		*/
		// Fin OpenUp
		
	}	//	prepareIt
	
	/**
	 * 	Approve Document
	 * 	@return true if success 
	 */
	public boolean  approveIt()
	{
		log.info(toString());
		setIsApproved(true);
		return true;
	}	//	approveIt
	
	/**
	 * 	Reject Approval
	 * 	@return true if success 
	 */
	public boolean rejectIt()
	{
		log.info(toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	
	/**************************************************************************
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		// OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
		// Comento metodo original y llamo a la localizacion de OpenUp.
		return this.completeItOpenUp();
		// Fin Issue #896.

		/*		
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());

		//	Charge Handling
		if (getC_Charge_ID() != 0)
		{
			setIsAllocated(true);
		}
		else
		{
			allocateIt();	//	Create Allocation Records
			testAllocation();
		}

		//	Project update
		if (getC_Project_ID() != 0)
		{
		//	MProject project = new MProject(getCtx(), getC_Project_ID());
		}
		//	Update BP for Prepayments
		if (getC_BPartner_ID() != 0 && getC_Invoice_ID() == 0 && getC_Charge_ID() == 0 && MPaymentAllocate.get(this).length == 0)
		{
			MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
			//	Update total balance to include this payment 
			BigDecimal payAmt = MConversionRate.convertBase(getCtx(), getPayAmt(), 
				getC_Currency_ID(), getDateAcct(), getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
			if (payAmt == null)
			{
				m_processMsg = "Could not convert C_Currency_ID=" + getC_Currency_ID()
					+ " to base C_Currency_ID=" + MClient.get(Env.getCtx()).getC_Currency_ID();
				return DocAction.STATUS_Invalid;
			}
			//	Total Balance
			BigDecimal newBalance = bp.getTotalOpenBalance(false);
			if (newBalance == null)
				newBalance = Env.ZERO;
			if (isReceipt())
				newBalance = newBalance.subtract(payAmt);
			else
				newBalance = newBalance.add(payAmt);
				
			bp.setTotalOpenBalance(newBalance);
			bp.setSOCreditStatus();
			bp.saveEx();
		}		

		//	Counter Doc
		MPayment counter = createCounterDoc();
		if (counter != null)
			m_processMsg += " @CounterDoc@: @C_Payment_ID@=" + counter.getDocumentNo();

		// @Trifon - CashPayments
		//if ( getTenderType().equals("X") ) {
		if ( isCashTrx() && !MSysConfig.getBooleanValue("CASH_AS_PAYMENT", true , getAD_Client_ID())) {
			// Create Cash Book entry
			if ( getC_CashBook_ID() <= 0 ) {
				log.saveError("Error", Msg.parseTranslation(getCtx(), "@Mandatory@: @C_CashBook_ID@"));
				m_processMsg = "@NoCashBook@";
				return DocAction.STATUS_Invalid;
			}
			MCash cash = MCash.get (getCtx(), getAD_Org_ID(), getDateAcct(), getC_Currency_ID(), get_TrxName());
			if (cash == null || cash.get_ID() == 0)
			{
				m_processMsg = "@NoCashBook@";
				return DocAction.STATUS_Invalid;
			}
			MCashLine cl = new MCashLine( cash );
			cl.setCashType( X_C_CashLine.CASHTYPE_GeneralReceipts );
			cl.setDescription("Generated From Payment #" + getDocumentNo());
			cl.setC_Currency_ID( this.getC_Currency_ID() );
			cl.setC_Payment_ID( getC_Payment_ID() ); // Set Reference to payment.
			StringBuffer info=new StringBuffer();
			info.append("Cash journal ( ")
				.append(cash.getDocumentNo()).append(" )");				
			m_processMsg = info.toString();
			//	Amount
			BigDecimal amt = this.getPayAmt();

			//MDocType dt = MDocType.get(getCtx(), invoice.getC_DocType_ID());			
			//if (MDocType.DOCBASETYPE_APInvoice.equals( dt.getDocBaseType() )
				//|| MDocType.DOCBASETYPE_ARCreditMemo.equals( dt.getDocBaseType() ) 
			//) {
				//amt = amt.negate();
			//}

			cl.setAmount( amt );
			//
			cl.setDiscountAmt( Env.ZERO );
			cl.setWriteOffAmt( Env.ZERO );
			cl.setIsGenerated( true );
			
			if (!cl.save(get_TrxName()))
			{
				m_processMsg = "Could not save Cash Journal Line";
				return DocAction.STATUS_Invalid;
			}
		}
		// End Trifon - CashPayments
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		
		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
		
		*/
		// Fin OpenUp.
		
	}	//	completeIt
	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateTrx(new Timestamp (System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}

	/**
	 * 	Create Counter Document
	 * 	@return payment
	 */
	@SuppressWarnings("unused")
	private MPayment createCounterDoc()
	{
		//	Is this a counter doc ?
		if (getRef_Payment_ID() != 0)
			return null;

		//	Org Must be linked to BPartner
		MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName()); 
		if (counterC_BPartner_ID == 0)
			return null;
		//	Business Partner needs to be linked to Org
		MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
		int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int(); 
		if (counterAD_Org_ID == 0)
			return null;
		
		MBPartner counterBP = new MBPartner (getCtx(), counterC_BPartner_ID, get_TrxName());
	//	MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID);
		log.info("Counter BP=" + counterBP.getName());

		//	Document Type
		int C_DocTypeTarget_ID = 0;
		MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			log.fine(counterDT.toString());
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
				return null;
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else	//	indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			log.fine("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
				return null;
		}

		//	Deep Copy
		MPayment counter = new MPayment (getCtx(), 0, get_TrxName());
		counter.setAD_Org_ID(counterAD_Org_ID);
		counter.setC_BPartner_ID(counterBP.getC_BPartner_ID());
		counter.setIsReceipt(!isReceipt());
		counter.setC_DocType_ID(C_DocTypeTarget_ID);
		counter.setTrxType(getTrxType());
		counter.setTenderType(getTenderType());
		//
		counter.setPayAmt(getPayAmt());
		counter.setDiscountAmt(getDiscountAmt());
		counter.setTaxAmt(getTaxAmt());
		counter.setWriteOffAmt(getWriteOffAmt());
		counter.setIsOverUnderPayment (isOverUnderPayment());
		counter.setOverUnderAmt(getOverUnderAmt());
		counter.setC_Currency_ID(getC_Currency_ID());
		counter.setC_ConversionType_ID(getC_ConversionType_ID());
		//
		counter.setDateTrx (getDateTrx());
		counter.setDateAcct (getDateAcct());
		counter.setRef_Payment_ID(getC_Payment_ID());
		//
		String sql = "SELECT C_BankAccount_ID FROM C_BankAccount "
			+ "WHERE C_Currency_ID=? AND AD_Org_ID IN (0,?) AND IsActive='Y' "
			+ "ORDER BY IsDefault DESC";
		int C_BankAccount_ID = DB.getSQLValue(get_TrxName(), sql, getC_Currency_ID(), counterAD_Org_ID);
		counter.setC_BankAccount_ID(C_BankAccount_ID);

		//	References
		counter.setC_Activity_ID(getC_Activity_ID());
		counter.setC_Campaign_ID(getC_Campaign_ID());
		counter.setC_Project_ID(getC_Project_ID());
		counter.setUser1_ID(getUser1_ID());
		counter.setUser2_ID(getUser2_ID());
		counter.saveEx(get_TrxName());
		log.fine(counter.toString());
		setRef_Payment_ID(counter.getC_Payment_ID());
		
		//	Document Action
		if (counterDT != null)
		{
			if (counterDT.getDocAction() != null)
			{
				counter.setDocAction(counterDT.getDocAction());
				counter.processIt(counterDT.getDocAction());
				counter.saveEx(get_TrxName());
			}
		}
		return counter;
	}	//	createCounterDoc
	
	/**
	 * 	Allocate It.
	 * 	Only call when there is NO allocation as it will create duplicates.
	 * 	If an invoice exists, it allocates that 
	 * 	otherwise it allocates Payment Selection.
	 *	@return true if allocated
	 */
	public boolean allocateIt()
	{
		//	Create invoice Allocation -	See also MCash.completeIt
		if (getC_Invoice_ID() != 0)
		{	
				return allocateInvoice();
		}	
		//	Invoices of a AP Payment Selection
		if (allocatePaySelection())
			return true;
		
		if (getC_Order_ID() != 0)
			return false;
			
		//	Allocate to multiple Payments based on entry
		MPaymentAllocate[] pAllocs = MPaymentAllocate.get(this);
		if (pAllocs.length == 0)
			return false;
		
		MAllocationHdr alloc = new MAllocationHdr(getCtx(), false, 
			getDateTrx(), getC_Currency_ID(), 
				Msg.translate(getCtx(), "C_Payment_ID")	+ ": " + getDocumentNo(), 
				get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		if (!alloc.save())
		{
			log.severe("P.Allocations not created");
			return false;
		}
		//	Lines
		for (int i = 0; i < pAllocs.length; i++)
		{
			MPaymentAllocate pa = pAllocs[i];
			MAllocationLine aLine = null;
			if (isReceipt())
				aLine = new MAllocationLine (alloc, pa.getAmount(), 
					pa.getDiscountAmt(), pa.getWriteOffAmt(), pa.getOverUnderAmt());
			else
				aLine = new MAllocationLine (alloc, pa.getAmount().negate(), 
					pa.getDiscountAmt().negate(), pa.getWriteOffAmt().negate(), pa.getOverUnderAmt().negate());
			aLine.setDocInfo(pa.getC_BPartner_ID(), 0, pa.getC_Invoice_ID());
			aLine.setPaymentInfo(getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
				log.warning("P.Allocations - line not saved");
			else
			{
				pa.setC_AllocationLine_ID(aLine.getC_AllocationLine_ID());
				pa.saveEx();
			}
		}
		//	Should start WF
		alloc.processIt(DocAction.ACTION_Complete);
		m_processMsg = "@C_AllocationHdr_ID@: " + alloc.getDocumentNo();
		return alloc.save(get_TrxName());
	}	//	allocateIt

	/**
	 * 	Allocate single AP/AR Invoice
	 * 	@return true if allocated
	 */
	private boolean allocateInvoice()
	{
		//	calculate actual allocation
		BigDecimal allocationAmt = getPayAmt();			//	underpayment
		if (getOverUnderAmt().signum() < 0 && getPayAmt().signum() > 0)
			allocationAmt = allocationAmt.add(getOverUnderAmt());	//	overpayment (negative)

		MAllocationHdr alloc = new MAllocationHdr(getCtx(), false, 
			getDateTrx(), getC_Currency_ID(),
			Msg.translate(getCtx(), "C_Payment_ID") + ": " + getDocumentNo() + " [1]", get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		alloc.setDateAcct(getDateAcct()); // in case date acct is different from datetrx in payment
		alloc.saveEx();
		MAllocationLine aLine = null;
		if (isReceipt())
			aLine = new MAllocationLine (alloc, allocationAmt, 
				getDiscountAmt(), getWriteOffAmt(), getOverUnderAmt());
		else
			aLine = new MAllocationLine (alloc, allocationAmt.negate(), 
				getDiscountAmt().negate(), getWriteOffAmt().negate(), getOverUnderAmt().negate());
		aLine.setDocInfo(getC_BPartner_ID(), 0, getC_Invoice_ID());
		aLine.setC_Payment_ID(getC_Payment_ID());
		aLine.saveEx(get_TrxName());
		//	Should start WF
		alloc.processIt(DocAction.ACTION_Complete);
		alloc.saveEx(get_TrxName());
		m_processMsg = "@C_AllocationHdr_ID@: " + alloc.getDocumentNo();
			
		//	Get Project from Invoice
		int C_Project_ID = DB.getSQLValue(get_TrxName(), 
			"SELECT MAX(C_Project_ID) FROM C_Invoice WHERE C_Invoice_ID=?", getC_Invoice_ID());
		if (C_Project_ID > 0 && getC_Project_ID() == 0)
			setC_Project_ID(C_Project_ID);
		else if (C_Project_ID > 0 && getC_Project_ID() > 0 && C_Project_ID != getC_Project_ID())
			log.warning("Invoice C_Project_ID=" + C_Project_ID 
				+ " <> Payment C_Project_ID=" + getC_Project_ID());
		return true;
	}	//	allocateInvoice
	
	/**
	 * 	Allocate Payment Selection
	 * 	@return true if allocated
	 */
	private boolean allocatePaySelection()
	{
		MAllocationHdr alloc = new MAllocationHdr(getCtx(), false, 
			getDateTrx(), getC_Currency_ID(),
			Msg.translate(getCtx(), "C_Payment_ID")	+ ": " + getDocumentNo() + " [n]", get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		alloc.setDateAcct(getDateAcct()); // in case date acct is different from datetrx in payment
		
		String sql = "SELECT psc.C_BPartner_ID, psl.C_Invoice_ID, psl.IsSOTrx, "	//	1..3
			+ " psl.PayAmt, psl.DiscountAmt, psl.DifferenceAmt, psl.OpenAmt "
			+ "FROM C_PaySelectionLine psl"
			+ " INNER JOIN C_PaySelectionCheck psc ON (psl.C_PaySelectionCheck_ID=psc.C_PaySelectionCheck_ID) "
			+ "WHERE psc.C_Payment_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_Payment_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int C_BPartner_ID = rs.getInt(1);
				int C_Invoice_ID = rs.getInt(2);
				if (C_BPartner_ID == 0 && C_Invoice_ID == 0)
					continue;
				boolean isSOTrx = "Y".equals(rs.getString(3));
				BigDecimal PayAmt = rs.getBigDecimal(4);
				BigDecimal DiscountAmt = rs.getBigDecimal(5);
				BigDecimal WriteOffAmt = Env.ZERO;
				BigDecimal OpenAmt = rs.getBigDecimal(7);
				BigDecimal OverUnderAmt = OpenAmt.subtract(PayAmt)
					.subtract(DiscountAmt).subtract(WriteOffAmt);
				//
				if (alloc.get_ID() == 0 && !alloc.save(get_TrxName()))
				{
					log.log(Level.SEVERE, "Could not create Allocation Hdr");
					rs.close();
					pstmt.close();
					return false;
				}
				MAllocationLine aLine = null;
				if (isSOTrx)
					aLine = new MAllocationLine (alloc, PayAmt, 
						DiscountAmt, WriteOffAmt, OverUnderAmt);
				else
					aLine = new MAllocationLine (alloc, PayAmt.negate(), 
						DiscountAmt.negate(), WriteOffAmt.negate(), OverUnderAmt.negate());
				aLine.setDocInfo(C_BPartner_ID, 0, C_Invoice_ID);
				aLine.setC_Payment_ID(getC_Payment_ID());
				if (!aLine.save(get_TrxName()))
					log.log(Level.SEVERE, "Could not create Allocation Line");
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "allocatePaySelection", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		//	Should start WF
		boolean ok = true;
		if (alloc.get_ID() == 0)
		{
			log.fine("No Allocation created - C_Payment_ID=" 
				+ getC_Payment_ID());
			ok = false;
		}
		else
		{
			alloc.processIt(DocAction.ACTION_Complete);
			ok = alloc.save(get_TrxName());
			m_processMsg = "@C_AllocationHdr_ID@: " + alloc.getDocumentNo();
		}
		return ok;
	}	//	allocatePaySelection
	
	/**
	 * 	De-allocate Payment.
	 * 	Unkink Invoices and Orders and delete Allocations
	 */
	@SuppressWarnings("unused")
	private void deAllocate()
	{
		if (getC_Order_ID() != 0)
			setC_Order_ID(0);
	//	if (getC_Invoice_ID() == 0)
	//		return;
		//	De-Allocate all 
		MAllocationHdr[] allocations = MAllocationHdr.getOfPayment(getCtx(), 
			getC_Payment_ID(), get_TrxName());
		log.fine("#" + allocations.length);
		for (int i = 0; i < allocations.length; i++)
		{
			allocations[i].set_TrxName(get_TrxName());
			allocations[i].setDocAction(DocAction.ACTION_Reverse_Correct);
			if (!allocations[i].processIt(DocAction.ACTION_Reverse_Correct))
				throw new AdempiereException(allocations[i].getProcessMsg());
			allocations[i].saveEx();
		}
		
		// 	Unlink (in case allocation did not get it)
		if (getC_Invoice_ID() != 0)
		{
			//	Invoice					
			String sql = "UPDATE C_Invoice "
				+ "SET C_Payment_ID = NULL, IsPaid='N' "
				+ "WHERE C_Invoice_ID=" + getC_Invoice_ID()
				+ " AND C_Payment_ID=" + getC_Payment_ID();
			int no = DB.executeUpdate(sql, get_TrxName());
			if (no != 0)
				log.fine("Unlink Invoice #" + no);
			//	Order
			sql = "UPDATE C_Order o "
				+ "SET C_Payment_ID = NULL "
				+ "WHERE EXISTS (SELECT * FROM C_Invoice i "
					+ "WHERE o.C_Order_ID=i.C_Order_ID AND i.C_Invoice_ID=" + getC_Invoice_ID() + ")"
				+ " AND C_Payment_ID=" + getC_Payment_ID();
			no = DB.executeUpdate(sql, get_TrxName());
			if (no != 0)
				log.fine("Unlink Order #" + no);
		}
		//
		setC_Invoice_ID(0);
		setIsAllocated(false);
	}	//	deallocate

	/**
	 * 	Void Document.
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		
		// OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
		// Comento metodo original y llamo a la localizacion de OpenUp.
		return this.voidItOpenUp();
		// Fin Issue #896.

		/*
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			setDocAction(DOCACTION_None);
			return false;
		}
		//	If on Bank Statement, don't void it - reverse it
		if (getC_BankStatementLine_ID() > 0)
			return reverseCorrectIt();
		
		//	Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
			|| DOCSTATUS_Invalid.equals(getDocStatus())
			|| DOCSTATUS_InProgress.equals(getDocStatus())
			|| DOCSTATUS_Approved.equals(getDocStatus())
			|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
		{
			addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + getPayAmt() + ")");
			setPayAmt(Env.ZERO);
			setDiscountAmt(Env.ZERO);
			setWriteOffAmt(Env.ZERO);
			setOverUnderAmt(Env.ZERO);
			setIsAllocated(false);
			//	Unlink & De-Allocate
			deAllocate();
		}
		else
			return reverseCorrectIt();
		
		//
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
		
		*/
		// Fin OpenUp.
		
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_None);
		
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;		
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		// OpenUp. Gabriel Vila. 25/10/2010.
		// Cambios radicales en la reversion. Redirecciono proceso a uno propio de OpenUp.
		
		return this.reversionOpenUp();
		
		// Fin OpenUp.
		
		
		/*log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		//	Std Period open?
		Timestamp dateAcct = getDateAcct();
		if (!MPeriod.isOpen(getCtx(), dateAcct, 
			isReceipt() ? X_C_DocType.DOCBASETYPE_ARReceipt : X_C_DocType.DOCBASETYPE_APPayment, getAD_Org_ID()))
			dateAcct = new Timestamp(System.currentTimeMillis());
		
		//	Auto Reconcile if not on Bank Statement				
		boolean reconciled = getC_BankStatementLine_ID() == 0; //AZ Goodwill

		//	Create Reversal
		MPayment reversal = new MPayment (getCtx(), 0, get_TrxName());
		copyValues(this, reversal);
		reversal.setClientOrg(this);
		reversal.setC_Order_ID(0);
		reversal.setC_Invoice_ID(0);
		reversal.setDateAcct(dateAcct);
		//
		reversal.setDocumentNo(getDocumentNo() + REVERSE_INDICATOR);	//	indicate reversals
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		//
		reversal.setPayAmt(getPayAmt().negate());
		reversal.setDiscountAmt(getDiscountAmt().negate());
		reversal.setWriteOffAmt(getWriteOffAmt().negate());
		reversal.setOverUnderAmt(getOverUnderAmt().negate());
		//
		reversal.setIsAllocated(true);
		reversal.setIsReconciled(reconciled);	//	to put on bank statement
		reversal.setIsOnline(false);
		reversal.setIsApproved(true); 
		reversal.setR_PnRef(null);
		reversal.setR_Result(null);
		reversal.setR_RespMsg(null);
		reversal.setR_AuthCode(null);
		reversal.setR_Info(null);
		reversal.setProcessing(false);
		reversal.setOProcessing("N");
		reversal.setProcessed(false);
		reversal.setPosted(false);
		reversal.setDescription(getDescription());
		reversal.addDescription("{->" + getDocumentNo() + ")");
		//FR [ 1948157  ] 
		reversal.setReversal_ID(getC_Payment_ID());
		reversal.saveEx(get_TrxName());
		//	Post Reversal
		if (!reversal.processIt(DocAction.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return false;
		}
		reversal.closeIt();
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.save(get_TrxName());

		//	Unlink & De-Allocate
		deAllocate();
		setIsReconciled (reconciled);
		setIsAllocated (true);	//	the allocation below is overwritten
		//	Set Status 
		addDescription("(" + reversal.getDocumentNo() + "<-)");
		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);
		setProcessed(true);
		//FR [ 1948157  ] 
		setReversal_ID(reversal.getC_Payment_ID());
		
		//	Create automatic Allocation
		MAllocationHdr alloc = new MAllocationHdr (getCtx(), false, 
			getDateTrx(), getC_Currency_ID(),
			Msg.translate(getCtx(), "C_Payment_ID")	+ ": " + reversal.getDocumentNo(), get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		if (!alloc.save())
			log.warning("Automatic allocation - hdr not saved");
		else
		{
			//	Original Allocation
			MAllocationLine aLine = new MAllocationLine (alloc, getPayAmt(true), 
				Env.ZERO, Env.ZERO, Env.ZERO);
			aLine.setDocInfo(getC_BPartner_ID(), 0, 0);
			aLine.setPaymentInfo(getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
				log.warning("Automatic allocation - line not saved");
			//	Reversal Allocation
			aLine = new MAllocationLine (alloc, reversal.getPayAmt(true), 
				Env.ZERO, Env.ZERO, Env.ZERO);
			aLine.setDocInfo(reversal.getC_BPartner_ID(), 0, 0);
			aLine.setPaymentInfo(reversal.getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
				log.warning("Automatic allocation - reversal line not saved");
		}
		alloc.processIt(DocAction.ACTION_Complete);
		alloc.save(get_TrxName());
		//
		StringBuffer info = new StringBuffer (reversal.getDocumentNo());
		info.append(" - @C_AllocationHdr_ID@: ").append(alloc.getDocumentNo());
		
		//	Update BPartner
		if (getC_BPartner_ID() != 0)
		{
			MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
			bp.setTotalOpenBalance();
			bp.save(get_TrxName());
		}		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		m_processMsg = info.toString();
		return true;
		
		*/
		// Fin OpenUp.
		
	}	//	reverseCorrectionIt

	/**
	 * OpenUp. 	
	 * Descripcion : Proceso de reversion de recibos de pago y cobranza  :
	 * 	- Cambia estado del header a void.
	 * 	- Elimina lineas
	 * 	- Para pagos, pasa cheques de ENTregado a EMItido.
	 * 	- Para cobranzas, elimina cheques en cartera.
	 * 	- Validaciones de que los cheques no tengan movimientos posteriores.
	 * 	- Eliminacion de afectaciones con estos recibos.
	 * 	- Eliminacion de asientos contables de los recibos y de sus afectaciones.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 25/10/2010
	 */
	private boolean reversionOpenUp() {
	
		log.info(toString());

		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		

		// OpenUp. Gabriel Vila. 13/09/2011. Issue #723.
		// Cambio la forma de verificar el periodo.
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		//	Std Period open?
		/*Timestamp dateAcct = getDateAcct();
		  if (!MPeriod.isOpen(getCtx(), getDateAcct(), isReceipt() ? X_C_DocType.DOCBASETYPE_ARReceipt : X_C_DocType.DOCBASETYPE_APPayment, getAD_Org_ID())) {
			dateAcct = new Timestamp(System.currentTimeMillis());
		}*/
		
		// Verifico si los cheques tienen movimientos posteriores segun tipo de recibo
		if (!this.validacionCheques()) return false;
	
		// Elimino contabilizacion del recibo y de sus afectaciones
		this.deleteContabilidad();
		
		// Elimino afectaciones
		this.deleteAfectaciones();

		// Actualizo cheques asociados a este recibo
		this.updateChequesAsociados();
		
		// Elimino lineas del recibo
		this.deleteLineas();
		
		// Actualizo estado del recibo
		this.setDocAction(DOCACTION_None);
		this.setDocStatus(DOCSTATUS_Voided);
		this.setProcessed(true);
		this.setPosted(true);
		this.saveEx();
		
		//	Update BPartner
		if (getC_BPartner_ID() != 0)
		{
			MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
			bp.setTotalOpenBalance();
			bp.save(get_TrxName());
		}		

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return true;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Elimina lineas de este recibo.
	 * @author  Gabriel Vila 
	 * Fecha : 25/10/2010
	 */
	private void deleteLineas() {

		String sql="";
		
		try{
			// Delete de cheques en cartera
			sql = " delete from uy_linepayment " +
			      " where c_payment_id=" + this.getC_Payment_ID();
			DB.executeUpdate(sql, get_TrxName());
  			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		
	}

	/**
	 * OpenUp.	
	 * Descripcion : Actualiza estado de cheques entregados y elimina cheques en cartera para este recibo.
	 * @author  Gabriel Vila 
	 * Fecha : 25/10/2010
	 */
	private void updateChequesAsociados() {
						
		String action = "";
			
		try{
			// Si es recibo de cobranza
			if (this.isReceipt()){
				action = " delete from uy_mediospago " +
						 " where uy_mediospago_id in " +
						" (select uy_mediospago_id from uy_linepayment " +
						" where c_payment_id=" + this.getC_Payment_ID() + ")";
				DB.executeUpdateEx(action, get_TrxName());
			}
			if (!this.isReceipt()){	// Recibo de pago a proveedores				
				// Actualizo cheques propios
				action = " update uy_mediospago set estado='" + X_UY_MediosPago.ESTADO_Emitido + "'" +
						", oldstatus='" + X_UY_MediosPago.ESTADO_Emitido + "'" +
						", uy_linepayment_id = null " +	  
						 " from uy_linepayment lp " +
						 " where uy_mediospago.uy_linepayment_id = lp.uy_linepayment_id " +
						 " and lp.c_payment_id = " + this.getC_Payment_ID() +
						 " and uy_mediospago.tipomp='" + X_UY_MediosPago.TIPOMP_Propio + "'";
						    
				DB.executeUpdateEx(action, get_TrxName());
				
				// Actualizo cheques de terceros utilizados en este recibo a proveedor
				action = " update uy_mediospago set estado='" + X_UY_MediosPago.ESTADO_Cartera + "'" +
						", oldstatus='" + X_UY_MediosPago.ESTADO_Cartera + "'" +
						", uy_linepayment_id = null " +	  
						 " from uy_linepayment lp " +
						 " where uy_mediospago.uy_linepayment_id = lp.uy_linepayment_id " +
						 " and lp.c_payment_id = " + this.getC_Payment_ID() +
						 " and uy_mediospago.tipomp='" + X_UY_MediosPago.TIPOMP_Terceros + "'";						 
						    
				DB.executeUpdateEx(action, get_TrxName());
			}
  			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
	}

	/**
	 * OpenUp.	
	 * Descripcion : Elimina afectaciones de este recibo
	 * @author  Gabriel Vila 
	 * Fecha : 25/10/2010
	 */
	private void deleteAfectaciones() {

		String sql="";
		
		try{
			
			// Obtengo ids de afectaciones asociadas a este recibo
			ArrayList<Integer> idsAfecta = this.getAllocationHdrIDs();
			
			// Para cada afectacion asociada a este recibo
			for (int i=0; i<idsAfecta.size(); i++){
			
				// Elimino lineas de afectacion
				sql = " delete from c_allocationline " +
					  " where c_allocationhdr_id =" + idsAfecta.get(i).toString(); 
				DB.executeUpdate(sql, get_TrxName());
				
				// Elimino cabezal de afectacion
				sql = " delete from c_allocationhdr " +
				  " where c_allocationhdr_id =" + idsAfecta.get(i).toString(); 
				DB.executeUpdate(sql, get_TrxName());
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}

	}


	/**
	 * OpenUp.	
	 * Descripcion : Elimina contabilizacion del recibo y tambien de sus afectaciones.
	 * @author  Gabriel Vila 
	 * Fecha : 25/10/2010
	 */
	private void deleteContabilidad() {
		
		String sql="";
		
		try{
			
			// Elimino primero la contabilizacion de las afectaciones de este recibo
			sql = " delete from fact_acct " +
				  " where ad_table_id = 735 " +
				  " and record_id in (select c_allocationhdr_id from c_allocationhdr " +
				  " where c_allocationhdr_id in (select c_allocationhdr_id from c_allocationline " +
				  " where c_payment_id=" + this.getC_Payment_ID() + "))";
			DB.executeUpdate(sql, get_TrxName());
			
			// Elimino la contabilizacion de este recibo
			sql = " delete from fact_acct " +
				  " where ad_table_id = 335 " +
				  " and record_id = " + this.getC_Payment_ID();

			DB.executeUpdate(sql, get_TrxName());
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}

	}

	/**
	 * OpenUp.	
	 * Descripcion : Valida que los cheques no tengan movimientos posteriores para un 
	 * determinado recibo (cobranza o pago).
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 25/10/2010
	 */
	private boolean validacionCheques() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean value = true;
		
		try{
			
			// Obtengo medios de pago asociados a este recibo
			sql = " SELECT mp.uy_mediospago_id, mp.tipomp, mp.estado " +
 		  	      " FROM UY_MediosPago mp " +
 		  	      " INNER JOIN uy_linepayment line ON mp.uy_mediospago_id = line.uy_mediospago_id " + 
 		  	      " WHERE line.c_payment_id =? " +
 		  	      " AND mp.docstatus <> 'VO'";//OpenUp. Nicolas Sarlabos. 07/07/2015. #4531.
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getC_Payment_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){

				String tipoMP = rs.getString("tipomp").trim();
				boolean medioValido = true;
				
				// Si es recibo de pago
				if(!this.isReceipt()){
					if (!rs.getString("estado").equalsIgnoreCase(X_UY_MediosPago.ESTADO_Entregado)){
						medioValido = false;
					}
				}
				else{
					if (!rs.getString("estado").equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cartera)){
						medioValido = false;
					}
				}
				
				if (!medioValido){

					MMediosPago mp = new MMediosPago(getCtx(), rs.getInt("uy_mediospago_id"), get_TrxName());
					this.m_processMsg += " Medio de Pago con movimientos posteriores. " + "\n" +
										" Tipo : " + ((tipoMP.equalsIgnoreCase("PRO")) ? "Propio" : "Terceros") +
										" Numero Documento : " + mp.getDocumentNo() + "\n" +
										" Numero de Cheque : " + mp.getCheckNo() + "\n";
									
					if (mp.getUY_MovBancariosHdr_ID() > 0){
						MMovBancariosHdr movhdr = new MMovBancariosHdr(getCtx(), mp.getUY_MovBancariosHdr_ID(), get_TrxName());
						MDocType doc = new MDocType(getCtx(), movhdr.getC_DocType_ID(), null);
						this.m_processMsg += " Documento Bancario : " + doc.getPrintName() + "\n" +
											 " Numero : " + movhdr.getDocumentNo() + "\n";
					}
					
					value = false;
				}
				
			}

		}
		catch (Exception e)
		{
			this.m_processMsg = e.getMessage();
			value = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna IDS de cabezales de afectaciones asociadas a este recibo.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/03/2012
	 */
	private ArrayList<Integer> getAllocationHdrIDs(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		ArrayList<Integer> values = new ArrayList<Integer>();
		
		try{
			sql =" SELECT DISTINCT hdr.uy_allocation_id " +
				 " FROM uy_allocationpayment ap " +
				 " INNER JOIN uy_allocation hdr on ap.uy_allocation_id = hdr.uy_allocation_id " +
				 " WHERE ap.c_payment_id =? " +
				 " AND hdr.docstatus='CO'"; 
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getC_Payment_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				values.add((Integer)rs.getInt(1));
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
		
		return values;

	}
		
	
	/**
	 * 	Get Bank Statement Line of payment or 0
	 *	@return id or 0
	 */
	@SuppressWarnings("unused")
	private int getC_BankStatementLine_ID()
	{
		String sql = "SELECT C_BankStatementLine_ID FROM C_BankStatementLine WHERE C_Payment_ID=?";
		int id = DB.getSQLValue(get_TrxName(), sql, getC_Payment_ID());
		if (id < 0)
			return 0;
		return id;
	}	//	getC_BankStatementLine_ID
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success 
	 */
	public boolean reverseAccrualIt()
	{
		log.info(toString());
		
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
				
		return false;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		
		// OpenUp. Gabriel Vila. 15/01/2013. Issue #237.
		// Accion Reactivar de OpenUp. Comento codigo original.
		
		return this.reActivateItOpenUp();
		
		/*
		
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;	
		
		if (! reverseCorrectIt())
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;				
		
		return true;
		
		*/
		
		// Fin OpenUp
		
	}	//	reActivateIt

	
	/***
	 * Accion Reactivar de OpenUp.
	 * OpenUp Ltda. Issue #237 
	 * @author Gabriel Vila - 15/01/2013
	 * @see
	 * @return
	 */
	private boolean reActivateItOpenUp() {
		
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		//OpenUp. Nicolas Sarlabos. 09/01/2014. #1421.
		MDocType doc = new MDocType(getCtx(),this.getC_DocType_ID(),get_TrxName());
		
		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("pagofondofijo")){
				this.validateReactivateFF(doc);
			//OpenUp. Nicolas Sarlabos. 28/03/2016. #5174	
			} else if(doc.getValue().equalsIgnoreCase("adelanto")){
				this.updateNews();				
			}
			//Fin OpenUp.
		}
		//Fin OpenUp.
		
		// Before Action
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		// Valido que este recibo NO participe en afectaciones manuales en estado completo.
		// Si es asi, no puedo reactivar el recibo sin antes anular las afectaciones manuales en las que participa
		// Obtengo ids de afectaciones asociadas a este recibo
		ArrayList<Integer> idsAfecta = this.getAllocationHdrIDs();
		for (int i=0; i<idsAfecta.size(); i++){
			if (idsAfecta.get(i).intValue() != this.getUY_Allocation_ID()){
				m_processMsg = "No es posible ReActivar este Recibo ya que participa en Afectaciones Manuales.";
				return false;
			}
		}
		
		//Ini #5164
		this.unsetIsRetentioned();
		//Fin #5164
		
		// Valido estado de cheques con respecto a movimientos bancarios que pudiera tener
		if (!this.validacionCheques()) return false;

		// Actualizo cheques asociados a este recibo
		this.updateChequesAsociados();
		
		// En caso que este recibo tenga una afectacion directa, la anulo.
		if (this.getUY_Allocation_ID() > 0){
			MAllocation allocation = new MAllocation(getCtx(), this.getUY_Allocation_ID(), get_TrxName());
			if (!allocation.voidIt()){
				m_processMsg = allocation.getProcessMsg();
				return false;
			}
			allocation.saveEx();
			allocation.deleteEx(true); //OpenUp. Nicolas Sarlabos. 07/05/2015. #4061. Elimino afectacion.
		}
		
		// Elimino asientos contables de este recibo
		FactLine.deleteFact(X_C_Payment.Table_ID, this.getC_Payment_ID(), get_TrxName());
		
		//OpenUp. Nicolas Sarlabos. 07/05/2015. #4061.
		//Marco lineas de cobro/pago como NO procesadas, y borro lineas de detalle de afectaciones
		DB.executeUpdateEx("update uy_linepayment set processed = 'N' where c_payment_id = " + this.get_ID(), get_TrxName());
		DB.executeUpdateEx("delete from uy_allocdetailpayment where c_payment_id = " + this.get_ID(), get_TrxName());
		//Fin OpenUp.
		
		//OpenUp. Nicolas Sarlabos. 15/06/2015. #4340.
		// Si estoy en modulo de transporte
		if (MSysConfig.getBooleanValue("UY_IS_MODULO_TRANSPORTE", false, this.getAD_Client_ID())) {
			
			MTRDriver driver = MTRDriver.forPartner(getCtx(), this.getC_BPartner_ID(), get_TrxName());//obtengo chofer asociado al proveedor, si existe
			
			if(driver!=null && driver.get_ID()>0){		
				
				DB.executeUpdateEx("delete from uy_sum_accountstatus where ad_table_id = " + I_C_Payment.Table_ID +
						" and record_id = " + this.get_ID(), get_TrxName());			
				
			}		
			
		}
		//Fin #4340.
		
		
		// After Action
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setPosted(false);
		setProcessed(false);
		setDocStatus(DocAction.STATUS_InProgress);
		setDocAction(DOCACTION_Complete);
		
		return true;
	}

	/***
	 * Metodo que valida la reactivacion para recibo de fondo fijo.
	 * Solo se puede reactivar siempre y cuando el documento no este presente en una reposicion en estado completo. 
	 * OpenUp Ltda. Issue #1421.
	 * @author Nicolas Sarlabos - 09/01/2014
	 * @param doc 
	 * @see
	 * @return
	 */	
	private void validateReactivateFF(MDocType doc) {
		
		String sql = "";
		int lineID = 0;		
		
		sql = "select uy_ff_replenishline_id" +
		      " from uy_ff_replenishline" +
			  " where ad_table_id = " + X_C_Payment.Table_ID +
			  " and record_id = " + this.get_ID() +
			  " and c_doctype_id = " + this.getC_DocType_ID();
		
		lineID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(lineID > 0){
			
			MFFReplenishLine rline = new MFFReplenishLine(getCtx(),lineID,get_TrxName()); //instancio linea de reposicion 
			MFFReplenish hdr = new MFFReplenish(getCtx(),rline.getUY_FF_Replenish_ID(),get_TrxName()); //instancio cabezal de reposicion
			
			if(hdr.getDocStatus().equalsIgnoreCase("CO")) throw new AdempiereException("Imposible reactivar. El recibo pertenece al documento de reposicion N° " 
					+ hdr.getDocumentNo() + " en estado completo"); 		
			
		}		
		
	}


	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MPayment[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
			.append(",Receipt=").append(isReceipt())
			.append(",PayAmt=").append(getPayAmt())
			.append(",Discount=").append(getDiscountAmt())
			.append(",WriteOff=").append(getWriteOffAmt())
			.append(",OverUnder=").append(getOverUnderAmt());
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	//	getDocumentInfo

	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.PAYMENT, getC_Payment_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF

	
	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(": ")
			.append(Msg.translate(getCtx(),"PayAmt")).append("=").append(getPayAmt())
			.append(",").append(Msg.translate(getCtx(),"WriteOffAmt")).append("=").append(getWriteOffAmt());
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary
	
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg
	
	/**
	 * 	Get Document Owner (Responsible)
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}	//	getDoc_User_ID

	/**
	 * 	Get Document Approval Amount
	 *	@return amount payment(AP) or write-off(AR)
	 */
	public BigDecimal getApprovalAmt()
	{
		if (isReceipt())
			return getWriteOffAmt();
		return getPayAmt();
	}	//	getApprovalAmt

	
	public void deleteAcct(){
		
		String mySql="delete from fact_acct where record_id="+getC_Payment_ID()
		+ " and ad_table_id=335";
		
		DB.executeUpdate(mySql, get_TrxName());
		
	}
	
	
	/* OpenUp. Gabriel Vila. 17/07/2010.
	 * Obtengo array de lineas de un pago como objetos MLinePayment.
	 */
	public ArrayList<MLinePayment> getPaymentLines(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<MLinePayment> lines = new ArrayList<MLinePayment>();		
		
		try{			
			// Obtengo suma de totales de lineas de pago
			sql = "SELECT UY_LinePayment_ID " +
				  " FROM UY_LinePayment "  +
				  " WHERE C_Payment_ID =?"; 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getC_Payment_ID());
			rs = pstmt.executeQuery ();
			while (rs.next()) {
				MLinePayment line = new MLinePayment(getCtx(), rs.getInt("UY_LinePayment_ID"), get_TrxName());
				lines.add(line);
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
		return lines;
	}
	
	/* OpenUp. Gabriel Vila. 29/07/2010.
	 * Obtengo saldo afectado del pago. 
	 */
	public BigDecimal getSaldoAfectacionesPayment(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql ="SELECT c_paymentallocate_id, paymentallocated(?,?) as saldo " + 
 		  	" FROM c_paymentallocate b " + 
		  	" WHERE c_payment_id =?"; 
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getC_Payment_ID());
			pstmt.setInt(2, this.getC_Currency_ID());
			pstmt.setInt(3, this.getC_Payment_ID());
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal("saldo");
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
		return value;
	}

	
	/* OpenUp FL 21/12/2010.
	 * Get the open amount of the payment, ( payment amount - allocated amount ) 
	 * There is a new column in allocation lines, with the amount of the payment that is allocated to the invoice, due to multicurrency allocation 
	 */
	public BigDecimal getOpenAmt(int currentLineID) {

		// Set the open amount with the payment amount and then substract the allocations
		BigDecimal openAmt=this.getPayAmt();
		
		// Defensive: payment open amount must be positive and non 0
		if (openAmt.compareTo(BigDecimal.ZERO)>0) {
			MAllocationLine[] allocationLines=this.getAllocationLines();	
			BigDecimal payAmt=BigDecimal.ZERO;															// Defensive: used to check null	
			for (MAllocationLine allocationLine : allocationLines) {
				
				// Donï¿½t consider the current line
				if (allocationLine.getC_AllocationLine_ID()!=currentLineID) {
					
					payAmt=(BigDecimal) allocationLine.get_Value(MAllocationLine.COLUMNNAME_Amount);	// Retrive the amount. TODO: should be payAmt due to multicurrency, by now is the amount of the invoice and the currency must be the same
					if (payAmt!=null) { 																// Defensive: check null, can be avoided with a column getter  
						openAmt=openAmt.subtract(payAmt);														
					}
				}
			}
		}
		
		return(openAmt);
	}
	
	
	/* OpenUp FL 21/12/2010.
	 * Get allocation lines of the payment 
	 */
	public MAllocationLine[] getAllocationLines() {

		Query query=new Query(getCtx(),MAllocationLine.Table_Name,COLUMNNAME_C_Payment_ID+"=?",get_TrxName());
		query.setParameters(new Object[]{getC_Payment_ID()});
				
		List<MAllocationLine> list = query.list();

		return list.toArray(new MAllocationLine[list.size()]);		
	}
	
	/* OpenUp FL 09/02/2011
	 * Get allocation headers of the payment 
	 */
	public MAllocationHdr[] getAllocationHdr() {

		Query query=new Query(getCtx(),MAllocationHdr.Table_Name,COLUMNNAME_C_Payment_ID+"=?",get_TrxName());
		query.setParameters(new Object[]{getC_Payment_ID()});
				
		List<MAllocationHdr> list = query.list();

		return list.toArray(new MAllocationHdr[list.size()]);		
	}
	
	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Obtengo y retorno saldo pendiente de este recibo segun nuevas estructuras.
	 * @return
	 */
	public BigDecimal getAmtOpen(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql ="SELECT amtopen " + 
 		  	" FROM alloc_paymentamtopen " + 
		  	" WHERE c_payment_id =?"; 
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getC_Payment_ID());
			rs = pstmt.executeQuery ();
		
			if (rs.next())
				value = rs.getBigDecimal(1);
			else
				value = this.getPayAmt();
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
		return value;
	}

	/**
	 * OpenUp. Gabriel Vila. 26/10/2011. Issue #896.
	 * Gestiono todo el beforesave de nuestra localizacion en este metodo.
	 * @param newRecord
	 * @return
	 */
	private boolean beforeSaveOpenUp(boolean newRecord){
		
		if (!newRecord){
			// Valido cambios en algunos campos del cabezal que me afectan a las lineas
			if ((!this.is_ValueChanged(COLUMNNAME_PayAmt)) 
					&& (!this.is_ValueChanged(COLUMNNAME_UY_SubTotal))
					&& (!this.is_ValueChanged(COLUMNNAME_DiscountAmt))
					&& (!this.is_ValueChanged(COLUMNNAME_AmtToAllocate))
					&& (!this.is_ValueChanged(COLUMNNAME_PayAmt))
					&& (!this.is_ValueChanged(COLUMNNAME_amtallocated))
					&& (!this.is_ValueChanged(COLUMNNAME_amtopen))
					&& (!this.is_ValueChanged(COLUMNNAME_UY_AsignaTransporteHdr_ID))
					&& (!this.is_ValueChanged(COLUMNNAME_DocumentNo))
					&& (!this.is_ValueChanged(COLUMNNAME_DocStatus))
					&& (!this.is_ValueChanged(COLUMNNAME_DocAction))
					&& (!this.is_ValueChanged(COLUMNNAME_Processed))
					&& (!this.is_ValueChanged(COLUMNNAME_Posted))
					&& (!this.is_ValueChanged(COLUMNNAME_DateTrx))
					&& (!this.is_ValueChanged("TotalDiscounts"))
					&& (!this.is_ValueChanged("Discount"))
					&& (!this.is_ValueChanged("amtRetention"))
					){
				
				
				if (!this.isReceipt()){

					MLinePayment[] lines = this.getLines(get_TrxName());
					if (lines.length > 0){
						throw new AdempiereException("No puede modificar datos del Recibo ya que tiene lineas de medios de cobro." + 
								 	" Elimine primero estas lineas antes de modificar los datos del Recibo.");
					}					
				}

				MAllocDirectPayment[] lines = this.getAllocDirectLines(get_TrxName());
				if (lines.length > 0){
					throw new AdempiereException("No puede modificar datos del Recibo ya que tiene lineas de afectacion directa." + 
							 	" Elimine primero estas lineas antes de modificar los datos del Recibo.");
				}
				
			}

			// Actualizo el Total del recibo = Subtotal + Descuentos
			//this.setPayAmt(this.getUY_SubTotal().add(this.getDiscountAmt())); //ORIGINAL
			
			//SBT 03/02/2016 Issue #5063 Hay que contemplar las empresas que tienen descuentos en cascada
			if(!isReceipt()){
				BigDecimal totalDisc = null;
				MDocType doc = (MDocType) this.getC_DocType();
				if(doc.getValue().equalsIgnoreCase("payment") || doc.getValue().equalsIgnoreCase("paymentpo") || 
						doc.getValue().equalsIgnoreCase("paymentmulticur") ){//SBT solicitado por NL en petición #5428
					if(this.get_Value("TotalDiscounts")!=null)
						totalDisc =	new BigDecimal(this.get_ValueAsString("TotalDiscounts"));
					//this.setPayAmt(this.getUY_SubTotal().add(this.getDiscountAmt()).add(totalDisc));
					if(totalDisc!=null){
						this.setPayAmt(this.getUY_SubTotal().add(totalDisc));
					}else{
						this.setPayAmt(this.getUY_SubTotal().add(this.getDiscountAmt())); //ORIGINAL
					}	
				}else{
					this.setPayAmt(this.getUY_SubTotal());
				}
				
			}else{
				this.setTotalRecibo();
			}
			
			
			
			// Actualizo saldo afectacion directa
			this.updateAllocDirect(false);
			
		}
		
		// No permito numero de documento en blanco y para recibos el usuario debe ingresarlo manualmente.
		MDocType doc = (MDocType)this.getC_DocType();
		if (!doc.isOverwriteSeqOnComplete()){

			if (this.getDocumentNo() == null){
				throw new AdempiereException("Debe ingresar manualmente un Numero para este Recibo.");	
			}
			
			// Trim del numero ingresado por las dudas que metan blancos	
			this.setDocumentNo(this.getDocumentNo().trim());
			
			if (this.getDocumentNo().equalsIgnoreCase("")){
				throw new AdempiereException("Debe ingresar manualmente un Numero para este Recibo.");
			}
			
			// Valido que no se repita el numero de documento cuando doy de alta o cuando lo modifico
			if (newRecord){
				this.validateDocumentNo();
			}
			else{
				if (is_ValueChanged(COLUMNNAME_DocumentNo)){
					this.validateDocumentNo();
				}
			}	
		}
		
		// Igualo fecha de contabilizacion con la fecha de transaccion
		this.setDateAcct(this.getDateTrx());
	
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 20/10/2011. #Issue 896.
	 * Obtengo y retorno array de lineas de este recibo.
	 * @param trxName
	 * @return
	 */
	public MLinePayment[] getLines(String trxName) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MLinePayment> list = new ArrayList<MLinePayment>();
		
		try{
			sql =" SELECT " + X_UY_LinePayment.COLUMNNAME_UY_LinePayment_ID + 
 		  	     " FROM " + X_UY_LinePayment.Table_Name + 
		  	     " WHERE " + X_UY_LinePayment.COLUMNNAME_C_Payment_ID + "=?";
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, this.getC_Payment_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MLinePayment value = new MLinePayment(Env.getCtx(), rs.getInt(1), trxName);
				list.add(value);
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
		
		return list.toArray(new MLinePayment[list.size()]);		
	}
	
	/**
	 * OpenUp. Gabriel Vila. 28/10/2011. #Issue 896.
	 * Obtengo y retorno array de lineas de afectacion directa de este recibo.
	 * @param trxName
	 * @return
	 */
	public MAllocDirectPayment[] getAllocDirectLines(String trxName) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MAllocDirectPayment> list = new ArrayList<MAllocDirectPayment>();
		
		try{
			sql =" SELECT " + X_UY_AllocDirectPayment.COLUMNNAME_UY_AllocDirectPayment_ID + 
 		  	     " FROM " + X_UY_AllocDirectPayment.Table_Name + 
		  	     " WHERE " + X_UY_AllocDirectPayment.COLUMNNAME_C_Payment_ID + "=?";
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, this.getC_Payment_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MAllocDirectPayment value = new MAllocDirectPayment(Env.getCtx(), rs.getInt(1), trxName);
				list.add(value);
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
		
		return list.toArray(new MAllocDirectPayment[list.size()]);		
	}

	
	/**
	 * OpenUp. Gabriel Vila. 20/10/2011.
	 * Actualizo campo SubTotal del recibo. 
	 */
	public void updateSubTotal(){
		
		BigDecimal total = Env.ZERO;
		int monedaNac = monedaNacional(getCtx(), this.getAD_Client_ID());

		// Obtengo y recorro lineas de recibos de esta afectacion
		MLinePayment[] lines = this.getLines(get_TrxName());
		for (int i=0; i < lines.length; i++){
			//SBT 11/02/2016 #5413 Se agrega consulta para contemplar recibo pago multimoneda 
			if((this.getC_Currency_ID() == lines[i].get_ValueAsInt("C_Currency_ID")) || 
					((lines[i].get_Value("C_Currency_ID") == null) && // Si la linea no tiene moneda y no son documentos multimoneda continuo sumando(para clientes anteriores)
							!(this.getC_DocType().getValue().equalsIgnoreCase("paymentmulticur")) &&
								!(this.getC_DocType().getValue().equalsIgnoreCase("receiptmulticur")))){
				total = total.add(lines[i].getPayAmt());
			}else{
				if(!(this.getC_DocType().getValue().equalsIgnoreCase("paymentmulticur")) && !(this.getC_DocType().getValue().equalsIgnoreCase("receiptmulticur"))) throw new AdempiereException("Verifique tipo de documento");
				if(this.get_Value("C_Currency2_ID")==null || this.get_ValueAsInt("C_Currency2_ID")==0) 
					throw new AdempiereException("Debe indicar segunda moneda de afectación");
				if(this.getCurrencyRate().compareTo(Env.ZERO)<=0) throw new AdempiereException("Debe indicar Tasa de cambio");
				if(monedaNac == this.get_ValueAsInt("C_Currency2_ID")){//si la segunda moneda es la nacional tengo que dividir
					total = total.add(lines[i].getPayAmt().divide(this.getCurrencyRate(),2,RoundingMode.HALF_UP));
							//.divide(Env.ONEHUNDRED));
				}else{
					total = total.add(lines[i].getPayAmt().multiply(this.getCurrencyRate()));
				}
			}
		}
		
		// Si se manejan retenciones debo considerarlas tambien para actualizar el total
		if (MSysConfig.getBooleanValue("UY_HANDLE_RETENTION", false, this.getAD_Client_ID())){
			
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			// Obtengo y recorro lineas de resguardos, sumo importe de resguardo segun moneda del recibo
			
			// Si es recibo de pago
			if (!this.isReceipt()){
				List<MPaymentResguardo> resgs = this.getResguardos();
				for (MPaymentResguardo resg: resgs){
					if (this.getC_Currency_ID() == schema.getC_Currency_ID()){
						total = total.add(resg.getAmt());
					}
					else{
						total = total.add(resg.getAmtSource());
					}
				}
			}
			else{
				// Es recibo de cobro. // OpenUp. Gabriel Vila. 13/03/2013. Issue #243.
				List<MReceiptResguardo> resgs = this.getResguardosReceipt();
				for (MReceiptResguardo resg: resgs){
					if (this.getC_Currency_ID() == resg.getC_Currency_ID()){
						total = total.add(resg.getAmt());
					}
				}				
			}
		}
		
		this.setUY_SubTotal(total.setScale(2, RoundingMode.HALF_UP));
		this.saveEx(get_TrxName());
	}
	
	
	/**OJO !!! Se actualiza el monto de afectacion directa y adicionalmente se calcula el monto de retencion (SBT)
	 * OpenUp. Gabriel Vila. 28/10/2011.
	 * Actualizo campos de total modificados por la afectacion. 
	 */
	public void updateAllocDirect(boolean save){
		
		BigDecimal totalFacturas = Env.ZERO, totalNotasCredito = Env.ZERO;
		BigDecimal totalRetenciones = Env.ZERO;
		int monedaNac = monedaNacional(getCtx(), this.getAD_Client_ID());
		// Obtengo y recorro lineas de afectacion directa de este recibo
		MAllocDirectPayment[] lines = this.getAllocDirectLines(get_TrxName());
		for (int i=0; i < lines.length; i++){
			
			if (lines[i].getsign().compareTo(Env.ONE) == 0)
				//Si la moneda de la línea es igual a la moneda del recibo, sumo (#5413 SBT 11/02/16)
				if(lines[i].getC_Currency_ID() == this.getC_Currency_ID()){
					totalNotasCredito = totalNotasCredito.add(lines[i].getamtallocated());
				//Sino la moneda tiene que ser igual a la "segunda" moneda y es necesario calcular con el tipo de cambio del recibo	
				}else{
					if(!(this.getC_DocType().getValue().equalsIgnoreCase("paymentmulticur")) 
							&& !(this.getC_DocType().getValue().equalsIgnoreCase("receiptmulticur"))) throw new AdempiereException("Verifique tipo de documento");
					if(this.get_Value("C_Currency2_ID")==null) throw new AdempiereException("Debe indicar segunda moneda de afectación");
					if(this.getCurrencyRate().compareTo(Env.ZERO)<=0) throw new AdempiereException("Debe indicar Tasa de cambio");
					if(monedaNac == this.get_ValueAsInt("C_Currency2_ID")){//si la segunda moneda es la nacional tengo que dividir
						totalNotasCredito = totalNotasCredito.add(lines[i].getamtallocated().divide(
								this.getCurrencyRate(),2,RoundingMode.HALF_UP));
					}else{
						totalNotasCredito = totalNotasCredito.add(lines[i].getamtallocated().multiply(
								this.getCurrencyRate()));
					}
				}	
			else
				if(lines[i].getC_Currency_ID() == this.getC_Currency_ID()){
					totalFacturas = totalFacturas.add(lines[i].getamtallocated());
				}else{
					if(!(this.getC_DocType().getValue().equalsIgnoreCase("paymentmulticur")) 
							&& !(this.getC_DocType().getValue().equalsIgnoreCase("receiptmulticur"))) throw new AdempiereException("Verifique tipo de documento");
					if(this.get_Value("C_Currency2_ID")==null) throw new AdempiereException("Debe indicar segunda moneda de afectación");
					if(this.getCurrencyRate().compareTo(Env.ZERO)<=0) throw new AdempiereException("Debe indicar Tasa de cambio");
					if(monedaNac == this.get_ValueAsInt("C_Currency2_ID")){//si la segunda moneda es la nacional tengo que dividir
						totalFacturas = totalFacturas.add(lines[i].getamtallocated().divide(
								this.getCurrencyRate(),2,RoundingMode.HALF_UP));

						//totalFacturas = totalFacturas.add(lines[i].getamtallocated().multiply(this.getCurrencyRate()).divide(Env.ONEHUNDRED));
					}else{
						totalFacturas = totalFacturas.add(lines[i].getamtallocated().multiply(this.getCurrencyRate()));
					}
				}	
				
			if(lines[i].get_Value("amtRetention") != null){
				if(lines[i].getC_Currency_ID() == this.getC_Currency_ID()){
					totalRetenciones = totalRetenciones.add((BigDecimal) lines[i].get_Value("amtRetention"));
				}else{
					if(!(this.getC_DocType().getValue().equalsIgnoreCase("paymentmulticur")) 
							&& !(this.getC_DocType().getValue().equalsIgnoreCase("receiptmulticur"))) throw new AdempiereException("Verifique tipo de documento");
					if(this.get_Value("C_Currency2_ID")==null) throw new AdempiereException("Debe indicar segunda moneda de afectación");
					if(this.getCurrencyRate().compareTo(Env.ZERO)<=0) throw new AdempiereException("Debe indicar Tasa de cambio");
					if(monedaNac == this.get_ValueAsInt("C_Currency2_ID")){//si la segunda moneda es la nacional tengo que dividir
						totalRetenciones = totalRetenciones.add(((BigDecimal) lines[i].get_Value("amtRetention"))
								.divide(this.getCurrencyRate(),2,RoundingMode.HALF_UP));
					}else{
						totalRetenciones = totalRetenciones.add(((BigDecimal) lines[i].get_Value("amtRetention")).multiply(this.getCurrencyRate()));
					}
				}
			}
		}
		BigDecimal mtToAllocate = (this.getPayAmt().add(totalNotasCredito)).subtract(totalFacturas);
		//this.setAmtToAllocate((this.getPayAmt().add(totalNotasCredito)).subtract(totalFacturas));
		this.setAmtToAllocate(mtToAllocate.setScale(2, RoundingMode.HALF_UP));//Se agrega línea para redondear
		MDocType doc = (MDocType) this.getC_DocType();
		//Ini OpenUp SBT 16-12-2015 Issue #5413
		//Si el tipo de documento es multimoneda, se calcula el saldo de afect. directa en la moneda 2
		if(doc.getValue().equalsIgnoreCase("receiptmulticur")||doc.getValue().equalsIgnoreCase("paymentmulticur")){
			BigDecimal amtToCur2 = Env.ZERO;
			if(this.getC_Currency_ID() != this.monedaNacional(getCtx(), this.getAD_Client_ID())){
				//if(this.getCurrencyRate().compareTo(Env.ONEHUNDRED)<0){//TC menor a 100
					amtToCur2 = mtToAllocate.multiply(this.getCurrencyRate());
				//}else if (this.getCurrencyRate().compareTo(Env.ONEHUNDRED)>0){//TC mayor a 100
					
				//}
			}else{
				amtToCur2 = mtToAllocate.divide(this.getCurrencyRate(),2,  RoundingMode.HALF_UP); 
			}
//			BigDecimal amtToCur2= mtToAllocate.multiply(OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateTrx(),
//					this.get_ValueAsInt("C_Currency2_ID"),this.getC_Currency_ID(),this.getAD_Client_ID(), this.getAD_Org_ID()));
			this.set_Value("AmtToAllocateCur2", amtToCur2.setScale(2,  RoundingMode.HALF_UP));
		}//Fin OpenUp SBT 16-12-2015 Issue #5413
			
		this.set_ValueOfColumn("amtRetention", totalRetenciones);
		
		if (save) this.saveEx(get_TrxName());
	}
	
	/**
	 * Retorna el id de la moneda nacional
	 * OpenUp SBT 06-04-2015
	 */
	private int monedaNacional(Properties ctx, int ad_Client_ID) {
		return OpenUpUtils.getSchemaCurrencyID(ctx,ad_Client_ID,null);
	}


	/**
	 * OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
	 * Acciones al completar un recibo.
	 */
	private String completeItOpenUp(){
		
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());

		// Timinig Before Complete
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Verifico que el saldo a afectar sea menor al recibo y mayor a cero.
		if (this.getAmtToAllocate().compareTo(this.getPayAmt()) > 0){
			this.m_processMsg = "La Afectacion directa del Recibo no esta afectando el Total del Recibo."; 
			return DocAction.STATUS_Invalid;
		}

		if (this.getAmtToAllocate().compareTo(Env.ZERO) < 0){

			BigDecimal bordeInferior = new BigDecimal(-0.99);

			if (this.getAmtToAllocate().compareTo(bordeInferior) < 0){
				this.m_processMsg = "La Afectacion directa NO puede ser mayor al Total del Recibo + Notas de Credito (se considera tolerancia de 0.99)."; 
				return DocAction.STATUS_Invalid;
			}
		}
		
		// Proceso lineas del recibo
		if (!this.processPaymentLines()) return DocAction.STATUS_Invalid;
		
		// Elimino documentos de afectacion con monto a afectar en cero.
		this.deleteDocsAmtAllocatedZero();
		
		// Afectacion directa del recibo 
		try{
			if (!this.allocateItOpenUp()) return DocAction.STATUS_Invalid;
		}
		catch (Exception ex){
			this.m_processMsg = ex.getMessage();
			return DocAction.STATUS_Invalid;
		}

		// Marca documentos de afectacion directa y lineas del recibo como procesados
		this.setAllocDocsProcessed();
		this.setLinesProcessed();

		//OpenUp. Nicolas Sarlabos. 06/01/2014. #1421.
		MDocType doc = new MDocType(getCtx(), this.getC_DocType_ID(), null);

		// Para pagos, si tengo ordenes de pago, las marco como afectadas a este recibo
		if (!this.isReceipt()){
			if ((doc.getValue() != null) && (doc.getValue().equalsIgnoreCase("paymentpo"))){
				this.setPayOrderAllocated(true);	
			}
		}
		
		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("pagofondofijo")){
				
				MBPartner partnerFF = MBPartner.forValue(getCtx(), "fondofijo", get_TrxName()); //obtengo proveedor Fondo Fijo				
				
				if(partnerFF!=null){
					
					if(this.getC_BPartner_ID() == partnerFF.get_ID()) throw new AdempiereException("El proveedor no puede ser 'Fondo Fijo', cambie el proveedor en este documento");
					
				}
				
				this.updateReplenish();		
				
			}  else if(doc.getValue().equalsIgnoreCase("adelanto")) this.loadNews(); //OpenUp. Nicolas Sarlabos. 28/03/2016. #5174.
		}
		//Fin openUp.
		
		//OpenUp. Nicolas Sarlabos. 11/06/2015. #4340.
		// Si estoy en modulo de transporte
		if (MSysConfig.getBooleanValue("UY_IS_MODULO_TRANSPORTE", false, this.getAD_Client_ID())) {
			
			MTRDriver driver = MTRDriver.forPartner(getCtx(), this.getC_BPartner_ID(), get_TrxName());//obtengo chofer asociado al proveedor, si existe
			
			if(driver!=null && driver.get_ID()>0){

				if(driver.isEmployee()){ //si el chofer es empleado

					MTRDriverCash cash = null;
					MSUMAccountStatus sumba = null;
					MCurrency curr = (MCurrency)this.getC_Currency();

					DB.executeUpdateEx("delete from uy_sum_accountstatus where ad_table_id = " + I_C_Payment.Table_ID +
							" and record_id = " + this.get_ID(), get_TrxName());

					List<MLinePayment> payLines = this.getPaymentLines();

					for(MLinePayment line : payLines){

						MPaymentRule rule = (MPaymentRule)line.getUY_PaymentRule();

						if(rule.getpaymentruletype().equalsIgnoreCase("CO")){

							cash = MTRDriverCash.forDriverCurrency(getCtx(), driver.get_ID(), this.getC_Currency_ID(), get_TrxName());//obtengo caja del chofer para esta moneda

							if(cash!=null && cash.get_ID()>0){

								sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
								sumba.setC_BankAccount_ID(cash.getC_BankAccount_ID());
								sumba.setDateTrx(this.getDateTrx());
								sumba.setDueDate(this.getDateTrx());
								sumba.setC_DocType_ID(this.getC_DocType_ID());
								sumba.setDocumentNo(this.getDocumentNo());
								sumba.setAD_Table_ID(I_C_Payment.Table_ID);
								sumba.setRecord_ID(this.get_ID());
								sumba.setC_BPartner_ID(this.getC_BPartner_ID());
								sumba.setDescription(this.getDescription());
								sumba.setC_Currency_ID(this.getC_Currency_ID());
								sumba.setamtdocument(line.getPayAmt());
								sumba.setAmtSourceCr(line.getPayAmt());
								sumba.setAmtSourceDr(Env.ZERO);
								sumba.setAmtAcctDr(Env.ZERO);
								sumba.setCurrencyRate(line.getCurrencyRate());
								sumba.saveEx();					

							} else throw new AdempiereException("No se obtuvo caja asociada al chofer actual para la moneda " + curr.getCurSymbol());						

						}		

					}			
				}
			}		

		}
		//Fin #4340.

		//OpenUp. Raul Capecce. 29/10/2015. #4942.
		// Establezco el valor de los campos checkletter y checkletter2 que contendrán el literal del Total Recibo
		BigDecimal payAmt = getPayAmt();
		
		String CHECKLETER = "CheckLetter";
		String CHECKLETER2 = "CheckLetter2";
		
		if (payAmt != null) {
			Converter conv = new Converter();

			int largoLiteral =  MSysConfig.getIntValue("UY_CHECK_LITERAL_LENGTH_C_PAYMENT", 70, getAD_Client_ID());;
			String literalPayAmt = conv.getStringOfBigDecimal(payAmt);
			
			if (literalPayAmt.length() <= largoLiteral) {
				set_Value(CHECKLETER, literalPayAmt);
				set_Value(CHECKLETER2, "");
			} else {
				set_Value(CHECKLETER, literalPayAmt.substring(0, largoLiteral));
				if (literalPayAmt.length() < (largoLiteral * 2)) {
					set_Value(CHECKLETER2, literalPayAmt.substring(largoLiteral, literalPayAmt.length()));
				} else {
					set_Value(CHECKLETER2, literalPayAmt.substring(largoLiteral, (largoLiteral * 2)));
				}
			}
			
			
		}
		//Fin #4942
		
		//Ini #5164
		this.setIsRetentioned();
		//Fin #5164
		
		// Timinig After Complete
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setDefiniteDocumentNo();
		setIsApproved(true);
		setProcessed(true);
		setProcessing(false);
		setDocStatus(DOCSTATUS_Completed);
		setDocAction(DOCACTION_None);
		
		return DocAction.STATUS_Completed;
		
	}

	/***
	 * Si este recibo tiene ordenes de pago, las marca como afectadas en este pago.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 19, 2015
	 */
	private void setPayOrderAllocated(boolean allocate) {

		try {
			List<MPaymentPayOrder> lines = this.getPayOrders();
			
			for(MPaymentPayOrder line: lines){
				MPayOrder payOrder = (MPayOrder)line.getUY_PayOrder();
				payOrder.setC_Payment_ID(this.get_ID());
				payOrder.saveEx();
			}
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}


	/**
	 * OpenUp. Gabriel Vila. 16/12/2011. 
	 * Proceso lineas del recibo actualizando ademas los medios de pago necesarios.
	 * @return
	 * @throws Exception 
	 */
	private boolean processPaymentLines() {

		try{
		
			MLinePayment[] lines = this.getLines(get_TrxName());
			for (int i=0; i < lines.length; i++){

				MLinePayment linea = lines[i];
				
				// Si el recibo es de cobranza
				if (this.isReceipt()){
					
					// OpenUp. Gabriel Vila. 14/06/2013. Issue #1006.
					// Comento IF y cambio condiciones a cumplir para generar un medio de pago de tercero.
					//if (!linea.getTenderType().equalsIgnoreCase(MPayment.TENDERTYPE_Cash)){

					MBankAccount ba = (MBankAccount)linea.getC_BankAccount();
					MBank bank = (MBank)ba.getC_Bank();
					
					// Si este banco se maneja como banco (o sea maneja medios de pago como cheques)
					if (bank.isBankHandler()){
						// Si esta cuenta bancaria NO es propia
						if (!ba.isPublic()){

					// Fin OpenUp. Issue #1006							
							
							MMediosPago mediopago = new MMediosPago(getCtx(), 0, get_TrxName());
							mediopago.setDateTrx(this.getDateTrx());
							mediopago.setDateAcct(this.getDateTrx());
							mediopago.setDueDate(linea.getDueDate());
							mediopago.setC_DocType_ID(MDocType.getDocType(Doc.DOCTYPE_Cheque));
							mediopago.setC_BankAccount_ID(linea.getC_BankAccount_ID());
							mediopago.setC_BPartner_ID(this.getC_BPartner_ID());
							mediopago.setCheckNo(linea.getDocumentNo());
							mediopago.setC_Currency_ID(this.getC_Currency_ID());
							mediopago.setPayAmt(linea.getPayAmt());
							mediopago.settipomp(X_UY_MediosPago.TIPOMP_Terceros);
							mediopago.setestado(X_UY_MediosPago.ESTADO_Cartera);
							mediopago.setoldstatus(X_UY_MediosPago.ESTADO_Cartera);
							mediopago.setPosted(true);
							mediopago.setProcessed(true);
							mediopago.setProcessing(false);
							mediopago.setDocAction(DocumentEngine.ACTION_None);
							mediopago.setDocStatus(DocumentEngine.STATUS_Completed);
							mediopago.set_ValueOfColumn("UY_PaymentRule_ID", linea.getUY_PaymentRule_ID());
							//mediopago.setUY_LinePayment_ID(linea.get_ID());
							mediopago.setUY_MovBancariosHdr_ID(0);
							mediopago.setUY_MovBancariosLine_ID(0);
							mediopago.saveEx();
							
							linea.setUY_MediosPago_ID(mediopago.getUY_MediosPago_ID());
							
						}
					}
					
					// OpenUp. Gabriel Vila. 14/06/2013. Issue #1006.
					// Me aseguro que si no se creo medio de pago para esta linea de recibo, 
					// la misma quede marcada como CASH
					if (linea.getUY_MediosPago_ID() <= 0){
						linea.setTenderType(MPayment.TENDERTYPE_Cash);
					}
					
					linea.saveEx();

					// Fin OpenUp. Issue #1006.
				}
				else{
					// Estoy en un recibo de pago.
					if (!linea.getTenderType().equalsIgnoreCase(MPayment.TENDERTYPE_Cash)){
						
						MMediosPago mediopago = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
						
						if (mediopago.get_ID() > 0){
							
							// Si este medio de pago ya fue emitido 
							if (!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Emitido) && 
								!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cartera)){
								this.m_processMsg = " El medio de pago asociado a la linea con numero de documento : " + linea.getDocumentNo() + 
										            " ya fue entregado en otro comprobante.";
								return false;
							}
							
							mediopago.setUY_LinePayment_ID(linea.get_ID());
							mediopago.setUY_MovBancariosHdr_ID(0);
							mediopago.setUY_MovBancariosLine_ID(0);
							mediopago.setoldstatus(mediopago.getestado());
							mediopago.setestado(X_UY_MediosPago.ESTADO_Entregado);
							mediopago.saveEx();
						}
						else{
							this.m_processMsg = "El medio de pago asociado a la linea con numero de documento : " + linea.getDocumentNo() + 
												" fue eliminado.";
							return false;
						}
					}
				}
				
			}
		}
		catch (Exception ex){			
			this.m_processMsg = ex.getMessage();
			return false;
		}
		
		return true;
	}


	/**
	 * OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
	 * En caso que este recibo tenga afectacion directa, debo generar esta 
	 * afectacion en modelo final y completarla.
	 * @return
	 * @throws Exception 
	 */
	private boolean allocateItOpenUp() throws Exception {
		
		// Si no tengo lineas de afectacion salgo sin hacer nada
		MAllocDirectPayment[] allocDirLines = this.getAllocDirectLines(get_TrxName());
		if (allocDirLines.length <= 0) return true;
		
		// Creo cabezal de afectacion
		MAllocation allocation = new MAllocation(getCtx(), 0, get_TrxName());
		allocation.setDocTypeFromPayment(this.isReceipt());
		allocation.setC_BPartner_ID(this.getC_BPartner_ID());	
		allocation.setDateTrx(this.getDateTrx());
		allocation.setDateAcct(this.getDateAcct());
		allocation.setIsSOTrx(this.isReceipt());
		allocation.setIsManual(false);
		allocation.setC_Currency_ID(this.getC_Currency_ID());
		//SBT 17-02-2016 Issue #5413
		if(this.get_Value("C_Currency2_ID")!=null){
			allocation.setC_Currency2_ID(this.get_ValueAsInt("C_Currency2_ID"));
//			allocation.setDivideRate(this.getCurrencyRate());
		}
//		else{
//			allocation.setDivideRate(Env.ONE);
//		}
		allocation.setDivideRate(this.getCurrencyRate());
		allocation.setC_Payment_ID(this.getC_Payment_ID());
		allocation.saveEx();

		// Agrego lines de afectacion de recibo por lo afectado de este recibo
		MAllocationPayment paymentHdr = new MAllocationPayment(getCtx(), 0, get_TrxName());
		paymentHdr.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
		paymentHdr.setC_DocType_ID(this.getC_DocType_ID());
		paymentHdr.setDocumentNo(this.getDocumentNo());
		paymentHdr.setC_Currency_ID(this.getC_Currency_ID());
		paymentHdr.setC_Payment_ID(this.getC_Payment_ID());
		paymentHdr.setamtdocument(this.getPayAmt());
		paymentHdr.setamtallocated(this.getPayAmt().subtract(this.getAmtToAllocate()));
		paymentHdr.setamtopen(this.getPayAmt());
		paymentHdr.setdatedocument(this.getDateTrx());
		paymentHdr.saveEx();
		
		// Creo lineas de recibo y lineas de factura
		// Las notas de credito se agregan como recibos 
		for (int i = 0; i < allocDirLines.length; i++){
			
			// Si esta linea de afectacion directa es una nota de credito (suma al recibo)
			if (allocDirLines[i].getsign().compareTo(Env.ONE) == 0){
				MAllocationPayment payment = new MAllocationPayment(getCtx(), 0, get_TrxName());
				payment.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
				payment.setC_DocType_ID(allocDirLines[i].getC_DocType_ID());
				payment.setDocumentNo(allocDirLines[i].getDocumentNo());
				payment.setC_Currency_ID(allocDirLines[i].getC_Currency_ID());
				payment.setC_Invoice_ID(allocDirLines[i].getC_Invoice_ID());
				payment.setamtdocument(allocDirLines[i].getamtdocument());
				payment.setamtallocated(allocDirLines[i].getamtallocated());
				payment.setamtopen(allocDirLines[i].getamtopen());
				payment.setdatedocument(allocDirLines[i].getdatedocument());
				
				payment.saveEx();
				
			}
			else{
				// Es una factura (resta del recibo)
				MAllocationInvoice invoice = new MAllocationInvoice(getCtx(), 0, get_TrxName());
				invoice.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
				invoice.setC_DocType_ID(allocDirLines[i].getC_DocType_ID());
				invoice.setDocumentNo(allocDirLines[i].getDocumentNo());
				invoice.setC_Currency_ID(allocDirLines[i].getC_Currency_ID());
				invoice.setC_Invoice_ID(allocDirLines[i].getC_Invoice_ID());
				invoice.setamtdocument(allocDirLines[i].getamtdocument());
				invoice.setamtallocated(allocDirLines[i].getamtallocated());
				invoice.setamtopen(allocDirLines[i].getamtopen());
				invoice.setdatedocument(allocDirLines[i].getdatedocument());
				
				//OpenUp SBT 04-03-2016 Issue #5587 - Se debe contemplar el caso de que sea cuota 
				if(allocDirLines[i].getC_Invoice().getpaymentruletype().equalsIgnoreCase("CR")){
					MInvoice inv = new MInvoice(getCtx(), allocDirLines[i].getC_Invoice_ID(), null);
					if(inv.getC_PaymentTerm_ID()>0){						
						MPaymentTerm pTerm = new MPaymentTerm(getCtx(),inv.getC_PaymentTerm_ID(),null);
						//if(!pTerm.get_ValueAsString("Value").equalsIgnoreCase("credito")){
							//Si el pago es credito y tiene vencimiento se debe colocar
							invoice.set_Value("C_InvoicePaySchedule_ID", allocDirLines[i].getC_InvoicePaySchedule_ID());
							invoice.set_Value("DueDate", allocDirLines[i].getDueDate());
						//}
					}
				}
				invoice.saveEx();
			}
		}

		// Actualizo totales de la afectacion
		allocation.updateTotalPayments();
		allocation.updateTotalInvoices();
		
		// Si no puedo salgo con el texto del error.
		if(!allocation.processIt(ACTION_Complete)){
			m_processMsg = allocation.getProcessMsg();
			return false;
		}
		
		allocation.saveEx();
		
		// Posteo Inmediato de la afectacion directa
		if (!MFactAcct.alreadyPosted(allocation.get_Table_ID(), allocation.get_ID(), get_TrxName())){
			DocumentEngine.postImmediate(getCtx(),	allocation.getAD_Client_ID(), allocation.get_Table_ID(), allocation.get_ID(), true, get_TrxName());
		}
		
		// Me guardo el numero de afectacion asociado a la afectacion directa
		this.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
		
		return true;
	}


	/**
	 * OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
	 * Elimina documentos con monto a afectar cero.
	 */
	private void deleteDocsAmtAllocatedZero(){
		String action = " DELETE FROM " + X_UY_AllocDirectPayment.Table_Name +
						" WHERE " + X_UY_AllocDirectPayment.COLUMNNAME_C_Payment_ID + "=" + this.getC_Payment_ID() +
						" AND " + X_UY_AllocDirectPayment.COLUMNNAME_amtallocated + "= 0";
		DB.executeUpdate(action, get_TrxName());
	}
	
	/**
	 * OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
	 * Marca documentos de afectacion como procesados.
	 */
	private void setAllocDocsProcessed(){
		String action = " UPDATE " + X_UY_AllocDirectPayment.Table_Name +
						" SET " + X_UY_AllocDirectPayment.COLUMNNAME_Processed + "='Y' " +
						" WHERE " + X_UY_AllocDirectPayment.COLUMNNAME_C_Payment_ID + "=" + this.getC_Payment_ID();
		DB.executeUpdate(action, get_TrxName());
	}

	
	/**
	 * OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
	 * Marca lineas del recibo como procesadas.
	 */
	private void setLinesProcessed(){
		String action = " UPDATE " + X_UY_LinePayment.Table_Name +
						" SET " + X_UY_LinePayment.COLUMNNAME_Processed + "='Y' " +
						" WHERE " + X_UY_LinePayment.COLUMNNAME_C_Payment_ID + "=" + this.getC_Payment_ID();
		DB.executeUpdate(action, get_TrxName());
	}

	
	/**
	 * OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
	 * PrepareIt de Recibos de OpenUp.
	 * @return
	 */
	private String prepareItOpenUp(){
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), 
			isReceipt() ? X_C_DocType.DOCBASETYPE_ARReceipt : X_C_DocType.DOCBASETYPE_APPayment, getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}

		//	Do not pay when Credit Stop/Hold
		if (!isReceipt())
		{
			MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
			if (X_C_BPartner.SOCREDITSTATUS_CreditStop.equals(bp.getSOCreditStatus()))
			{
				m_processMsg = "@BPartnerCreditStop@ - @TotalOpenBalance@=" 
					+ bp.getTotalOpenBalance()
					+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
				return DocAction.STATUS_Invalid;
			}
			if (X_C_BPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus()))
			{
				m_processMsg = "@BPartnerCreditHold@ - @TotalOpenBalance@=" 
					+ bp.getTotalOpenBalance()
					+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
				return DocAction.STATUS_Invalid;
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/**
	 * OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
	 * voidIt de Recibos de OpenUp. 
	 * @return
	 */
	public boolean voidItOpenUp(){

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		//OpenUp. Nicolas Sarlabos. 09/01/2014. #1421
		MDocType doc = new MDocType(getCtx(),this.getC_DocType_ID(),get_TrxName());
		
		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("pagofondofijo")){
				this.validateVoidFFPayment();
			//OpenUp. Nicolas Sarlabos. 28/03/2016. #5174
			} else if(doc.getValue().equalsIgnoreCase("adelanto")){
				this.updateNews();				
			}
			//Fin OpenUp.
		}
		
		//Fin #1421.
		
		// Valido que este recibo NO participe en afectaciones manuales en estado completo.
		// Si es asi, no puedo anular el recibo sin antes anular las afectaciones manuales en las que participa
		// Obtengo ids de afectaciones asociadas a este recibo
		ArrayList<Integer> idsAfecta = this.getAllocationHdrIDs();
		for (int i=0; i<idsAfecta.size(); i++){
			if (idsAfecta.get(i).intValue() != this.getUY_Allocation_ID()){
				m_processMsg = "No es posible anular este Recibo ya que participa en Afectaciones Manuales.";
				return false;
			}
		}
		
		// Valido estado de cheques con respecto a movimientos bancarios que pudiera tener
		if (!this.validacionCheques()) return false;
		
		//Ini #5164
		this.unsetIsRetentioned();
		//Fin #5164

		// Actualizo cheques asociados a este recibo
		this.updateChequesAsociados();
		
		// En caso que este recibo tenga una afectacion directa, la anulo.
		if (this.getUY_Allocation_ID() > 0){
			MAllocation allocation = new MAllocation(getCtx(), this.getUY_Allocation_ID(), get_TrxName());
			if (!allocation.voidIt()){
				m_processMsg = allocation.getProcessMsg();
				return false;
			}
			allocation.saveEx();
		}
		
		// Elimino asientos contables de este recibo
		FactLine.deleteFact(X_C_Payment.Table_ID, this.getC_Payment_ID(), get_TrxName());
		
		//OpenUp. Nicolas Sarlabos. 15/06/2015. #4340.
		// Si estoy en modulo de transporte
		if (MSysConfig.getBooleanValue("UY_IS_MODULO_TRANSPORTE", false, this.getAD_Client_ID())) {
			
			MTRDriver driver = MTRDriver.forPartner(getCtx(), this.getC_BPartner_ID(), get_TrxName());//obtengo chofer asociado al proveedor, si existe
			
			if(driver!=null && driver.get_ID()>0){		
				
				DB.executeUpdateEx("delete from uy_sum_accountstatus where ad_table_id = " + I_C_Payment.Table_ID +
						" and record_id = " + this.get_ID(), get_TrxName());			
				
			}		
			
		}
		//Fin #4340.
		
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setPosted(true);
		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DOCACTION_None);
		
		return true;
		
	}

	/***
	 * Metodo que verifica que el recibo de FF a anular no se encuentre en un documento de reposicion en curso.
	 * OpenUp Ltda. Issue #1421
	 * @author Nicolas Sarlabos - 09/01/2014
	 * @see
	 * @return
	 */	
	private void validateVoidFFPayment() {
		
		//obtengo linea de reposicion 
		MFFReplenishLine rline = MFFReplenishLine.forTableReplenishLine(getCtx(), this.getC_DocType_ID(), X_C_Payment.Table_ID, this.get_ID(), 0, get_TrxName());
		
		if(rline!=null){
			
			MFFReplenish replenish = new MFFReplenish(getCtx(),rline.getUY_FF_Replenish_ID(),get_TrxName()); //instancio cabezal de doc de reposicion
			
			if(!replenish.getDocStatus().equalsIgnoreCase("CO")) { //solo si la reposicion no esta completa puedo anular y actualizar
				
				rline.deleteEx(true);
				
			} else throw new AdempiereException("Imposible anular por estar el recibo en el documento de reposicion N° " + 					
					replenish.getDocumentNo() + " en estado completo");						
		}
		
	}

	/**
	 * OpenUp. Gabriel Vila. 31/10/2011. Issue #896.
	 * Eliminacion de afectacion directa de este recibo. 
	 * @return
	 */
	public boolean deleteDirectAllocation(){
		
		String action= "";
		
		try{
			// Elimino lineas de afectacion directa 
			action = " DELETE FROM " + X_UY_AllocDirectPayment.Table_Name +
					 " WHERE " + X_UY_AllocDirectPayment.COLUMNNAME_C_Payment_ID + "=" + this.getC_Payment_ID();
			DB.executeUpdate(action, get_TrxName());
			
			// Actualizo recibo para desligarlo de la afectacion
			action = " UPDATE " + X_C_Payment.Table_Name +
					 " SET " + X_C_Payment.COLUMNNAME_UY_Allocation_ID + "=null " +
					 " WHERE " + X_C_Payment.COLUMNNAME_C_Payment_ID + "=" + this.getC_Payment_ID();
			DB.executeUpdate(action, get_TrxName());		
		}
		catch (Exception ex){
			this.m_processMsg = ex.getMessage();
			return false;
		}

		return true;
	}

	
	/**
	 * OpenUp. Gabriel Vila. 01/11/2011.
	 * Actualizo total afectado y saldo pendiente del Recibo. 
	 */
	public void updateAllocationInfo(){

		BigDecimal total = Env.ZERO;

		// Obtengo y recorro lineas de detalle de afectaciones de este recibo
		MAllocDetailPayment[] lines = this.getAllocDetailLines(get_TrxName());
		for (int i=0; i < lines.length; i++){
			total = total.add(lines[i].getamtallocated());				
		}
		this.setamtallocated(total.setScale(2, RoundingMode.HALF_UP));
		this.setamtopen(this.getPayAmt().subtract(this.getamtallocated()));
		this.saveEx(get_TrxName());
	}

	
	/**
	 * OpenUp. Gabriel Vila. 01/11/2011. #Issue 896.
	 * Obtengo y retorno array de lineas de detalle de afectacion de este recibo.
	 * @param trxName
	 * @return
	 */
	public MAllocDetailPayment[] getAllocDetailLines(String trxName) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MAllocDetailPayment> list = new ArrayList<MAllocDetailPayment>();
		
		try{
			sql =" SELECT " + X_UY_AllocDetailPayment.COLUMNNAME_UY_AllocDetailPayment_ID + 
 		  	     " FROM " + X_UY_AllocDetailPayment.Table_Name + 
 		  	     " WHERE " + X_UY_AllocDetailPayment.COLUMNNAME_C_Payment_ID + "=?";
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, this.getC_Payment_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MAllocDetailPayment value = new MAllocDetailPayment(Env.getCtx(), rs.getInt(1), trxName);
				list.add(value);
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
		
		return list.toArray(new MAllocDetailPayment[list.size()]);		
	}


	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/***
	 * Setea tipo de documento segun tipo de recibo.
	 * OpenUp Ltda. Issue #76 
	 * @author Gabriel Vila - 18/10/2012
	 * @see
	 */
	public void setDefaultDocTypeID(){
		
		MDocType doc = null;
		if (this.isReceipt()){
			doc = MDocType.forValue(getCtx(), "receipt", get_TrxName());
		}
		else{
			doc = MDocType.forValue(getCtx(), "payment", get_TrxName());
		}
		
		this.setC_DocType_ID(doc.get_ID());
		
	}

	/***
	 * Obtiene y retorna lineas de resguardos de este recibo.
	 * OpenUp Ltda. Issue #100 
	 * @author Gabriel Vila - 23/11/2012
	 * @see
	 * @return
	 */
	public List<MPaymentResguardo> getResguardos(){
		
		String whereClause = X_UY_Payment_Resguardo.COLUMNNAME_C_Payment_ID + "=" + this.get_ID();
		
		List<MPaymentResguardo> lines = new Query(getCtx(), I_UY_Payment_Resguardo.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}

	/***
	 * Obtiene y retorna lineas de resguardos de clientes de este recibo.
	 * OpenUp Ltda. Issue #243
	 * @author Gabriel Vila - 13/03/2013
	 * @see
	 * @return
	 */
	public List<MReceiptResguardo> getResguardosReceipt(){
		
		String whereClause = X_UY_Receipt_Resguardo.COLUMNNAME_C_Payment_ID + "=" + this.get_ID();
		
		List<MReceiptResguardo> lines = new Query(getCtx(), I_UY_Receipt_Resguardo.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
	/***
	 * Metodo que actualiza el documento de reposicion de FF insertando o actualizando una linea para este documento.
	 * Recibe como parametro un boolean que indica si el documento es una salida de FF.
	 * OpenUp Ltda. Issue #1426
	 * @author Nicolas Sarlabos - 27/12/2013
	 * @see
	 * @return
	 */	
	private void updateReplenish() {

		MFFReplenishLine rline = null;
		
		//obtengo documento de reposicion en curso para la sucursal y moneda actual
		MFFReplenish replenish = MFFReplenish.forBranchCurrency(getCtx(),this.getUY_FF_Branch_ID(),this.getC_Currency_ID(), get_TrxName());

		if(replenish==null) throw new AdempiereException("No se encontro documento de reposicion de fondo fijo en curso, por favor verifique");
		
		// obtengo la linea de reposicion, si existe, para poder actualizarla
		rline = MFFReplenishLine.forTableReplenishLine(getCtx(),this.getC_DocType_ID(),X_C_Payment.Table_ID,this.get_ID(),replenish.get_ID(),get_TrxName());

		if (rline != null) { // actualizo linea existente
			
			rline.setUY_FF_Replenish_ID(replenish.get_ID());
			rline.setDateTrx(this.getDateTrx());
			rline.setC_BPartner_ID(this.getC_BPartner_ID());
			rline.setRecord_ID(this.get_ID());
			rline.setAD_Table_ID(I_C_Payment.Table_ID);
			rline.setC_DocType_ID(this.getC_DocType_ID());
			rline.setDocumentNo(this.getDocumentNo());
			rline.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, 0, "AD_User_ID"));
			rline.setAmount(this.getPayAmt());
			
			if(this.getDescription()!=null && !this.getDescription().equalsIgnoreCase("")){
				rline.setDescription(this.getDescription());				
			} else rline.setDescription("--");
			
			rline.setChargeName("--");
			rline.setApprovedBy("--");
			rline.saveEx();
					
		} else {
			
			// inserto nueva linea
			rline = new MFFReplenishLine(getCtx(), 0, get_TrxName());
			rline.setUY_FF_Replenish_ID(replenish.get_ID());
			rline.setDateTrx(this.getDateTrx());
			rline.setC_BPartner_ID(this.getC_BPartner_ID());
			rline.setRecord_ID(this.get_ID());
			rline.setAD_Table_ID(I_C_Payment.Table_ID);
			rline.setC_DocType_ID(this.getC_DocType_ID());
			rline.setDocumentNo(this.getDocumentNo());
			rline.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, 0, "AD_User_ID"));
			rline.setAmount(this.getPayAmt());
			
			if(this.getDescription()!=null && !this.getDescription().equalsIgnoreCase("")){
				rline.setDescription(this.getDescription());				
			} else rline.setDescription("--");
			
			rline.setChargeName("--");
			rline.setApprovedBy("--");
			rline.saveEx();
			
		}	
	}	

	/***
	 * Verifica que el total de recibos y facturas sean iguales, o entren dentro de la tolerancia permitida.
	 * OpenUp Ltda. Issue #1303
	 * @author Nicolas Sarlabos - 11/11/2013
	 * @see
	 * @return
	 */	
	@SuppressWarnings("unused")
	private boolean verifyAmounts() {
		
		BigDecimal diff = this.getAmtToAllocate();
		BigDecimal bordeInferior = new BigDecimal(-0.99);
		BigDecimal bordeSuperior = new BigDecimal(0.99);
		
		if (diff.compareTo(bordeInferior)<0 || diff.compareTo(bordeSuperior)>0) return false;		
		
		return true;
	}	

	/***
	 * Obtiene y retorna lineas de ordenes de pago asociados a este recibo.
	 * OpenUp Ltda. Issue #5009
	 * @author gabriel - Nov 19, 2015
	 * @return
	 */
	private List<MPaymentPayOrder> getPayOrders(){
		
		String whereClause = X_C_PaymentPayOrder.COLUMNNAME_C_Payment_ID + "=" + this.get_ID();
		
		List<MPaymentPayOrder> lines = new Query(getCtx(), I_C_PaymentPayOrder.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}
	
	/**
	 * OpenUp. Emiliano Bentancor. 4/2/2016.
	 * Se setea al cabezal el monto Total Recibo
	 * Issue #5164
	 * @return
	 */
	private void setTotalRecibo(){
		BigDecimal tot = new BigDecimal(0);
		BigDecimal totCur2 = new BigDecimal(0);
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null, rs2 = null; String qryCur2 = "";
		
		String qry = "select coalesce(sum(PayAmt),0) "
				+ "from UY_LinePayment where C_Payment_ID = " + this.getC_Payment_ID() 
				+ " AND C_Currency_ID = "+this.getC_Currency_ID();
		//OpenUp SBT 07/03/2016 Issue #5588 
		//Si el documento es multimoneda entonces tengo que tener en cuenta los medios de pago en diferente monedas 
		if(this.get_Value("C_Currency2_ID")!=null && this.get_ValueAsInt("C_Currency2_ID")>0){
			
			qryCur2 = "SELECT COALESCE(SUM(PayAmt),0) "
					+ " FROM UY_LinePayment WHERE C_Payment_ID = " + this.getC_Payment_ID() 
					+ " AND C_Currency_ID = "+this.get_ValueAsInt("C_Currency2_ID");
			
		}
		
		try {
			pstmt = DB.prepareStatement (qry, get_TrxName());
			rs = pstmt.executeQuery();
			
			
			if(rs.next()){
				tot = tot.add(rs.getBigDecimal(1));
			}
			//OpenUp SBT 07/03/2016 Issue #5588
			//Si el docuemnto es mulitmoneda y tengo MP en la segunda moneda convierto la misma a la moneda del docuemnto
			//y sumo al total.
			if(qryCur2.length()>0){
				pstmt = DB.prepareStatement(qryCur2, get_TrxName());
				rs = pstmt.executeQuery();
				if(rs.next()){
					totCur2 = totCur2.add(rs.getBigDecimal(1));
				}
				BigDecimal curRate = null;
				if(this.getCurrencyRate()==null){
					curRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateTrx(), 
					this.getC_Currency_ID(), this.get_ValueAsInt("C_Currency2_ID"), this.getAD_Client_ID(),this.getAD_Org_ID());		
				}else{
					curRate = this.getCurrencyRate();
				}
				//Luego de calcular sumo al total
				tot = tot.add(totCur2.multiply(this.getCurrencyRate()));
			}
			
			if (this.isReceipt()){
				String qry2 = "select coalesce(sum(amtRetention),0) "
						+ "from UY_AllocDirectPayment where C_Payment_ID = " + this.getC_Payment_ID();
				pstmt2 = DB.prepareStatement (qry2, get_TrxName());
				rs2 = pstmt2.executeQuery();
				if(rs2.next()){
					tot = tot.add(rs2.getBigDecimal(1));
				}
				if(this.get_Value("TotalDiscounts")!=null){
					tot = tot.add((BigDecimal)this.get_Value("TotalDiscounts"));	
				}else if (this.get_Value("discountAmt")!=null){
					tot = tot.add((BigDecimal)this.get_Value("discountAmt"));
				}
				this.setPayAmt(tot);
			}
			
		} catch (SQLException e) {
			throw new AdempiereException(e);
		}finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
			DB.close(rs2, pstmt2);
			rs2 = null;
			pstmt2 = null;
		}
	}
	
	
	/**
	 * OpenUp. Emiliano Bentancor. 4/2/2016.
	 * Se setea el flag de retencion asociada a una Invoice
	 * Issue #5164
	 * @return
	 */
	private void setIsRetentioned(){
		MAllocDirectPayment[] lines = this.getAllocDirectLines(get_TrxName());
		int tam = lines.length;
		
		for(int i = 0; i < tam; i++){
			if(lines[i].get_Value("amtRetention") != null){
				BigDecimal amrRet = (BigDecimal) lines[i].get_Value("amtRetention");
				if(amrRet.compareTo(Env.ZERO) == 1){
					MInvoice inv = new MInvoice(getCtx(), lines[i].getC_Invoice_ID(), get_TrxName());
					inv.set_Value("IsRetentioned", "Y");
					inv.saveEx();
				}
			}
		}
	}
	
	/**
	 * OpenUp. Emiliano Bentancor. 4/2/2016.
	 * Se dessetea el flag de retencion asociada a una Invoice
	 * Issue #5164
	 * @return
	 * 
	 */
	private void unsetIsRetentioned(){
		MAllocDirectPayment[] lines = this.getAllocDirectLines(get_TrxName());
		int tam = lines.length;
		
		for(int i = 0; i < tam; i++){
			if(lines[i].get_Value("amtRetention") != null){
				BigDecimal amrRet = (BigDecimal) lines[i].get_Value("amtRetention");
				if(amrRet.compareTo(Env.ZERO) == 1){
					MInvoice inv = new MInvoice(getCtx(), lines[i].getC_Invoice_ID(), get_TrxName());
					inv.set_Value("IsRetentioned", "N");
					inv.saveEx();
				}
			}
		}
	}
	
	/**
	 * Obtengo el monto total de descuentos de las órdenes asociadas al recibo
	 * El valor de entrada es para descontarlo, para contemplar los delete de las orden de pago
	 * @author OpenUp SBT Issue #5428  4/2/2016 18:12:19
	 * @param valueIn --> Valor a descontar sino rec. 0
	 */
	public void updateTotalDiscounts(BigDecimal valueIn) {
						
		BigDecimal totalDisc = Env.ZERO;
		String sql = "";ResultSet rs = null;PreparedStatement pstmt = null;
		try{
			sql = "select sum(TotalDiscounts) from C_PaymentPayOrder where C_Payment_ID = ? ";
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				totalDisc = rs.getBigDecimal(1);
			}
			//Seteo el valor al objeto actual
			this.set_Value("TotalDiscounts", totalDisc.subtract(valueIn));

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
		
	}


	/**
	 * Obtengo el monto total de resguardo de las órdenes asociadas al recibo
	 * El valor de entrada es para descontarlo, por si se trata de un delete de una orden de pago
	 * @author OpenUp SBT Issue#  5/2/2016 9:09:46
	 * @param valueIn --> Valor a descontar sino rec. 0
	 */
	public void updateTotalResguardo(BigDecimal valueIn) {		
		
		BigDecimal totalDiscActual = Env.ZERO;
		
		String sql = "";ResultSet rs = null;PreparedStatement pstmt = null;
		try{
			sql = "select sum(AmtResguardo) from C_PaymentPayOrder where C_Payment_ID = ? ";
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				totalDiscActual = rs.getBigDecimal(1);
			}
			//Seteo el valor al objeto actual
			this.set_Value("AmtResguardo", totalDiscActual.subtract(valueIn));

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
			
	}
	
	/***
	 * Metodo que crea la novedad de nomina al completar el documento de adelanto de sueldo.
	 * OpenUp Ltda. Issue #5174
	 * @author Nicolas Sarlabos - 28/03/2016
	 * @see
	 * @return
	 */
	public void loadNews(){

		MHRNovedades hdr = null;
		MHRConceptoLine line = null;

		try {
			
			MHRParametros param = MHRParametros.forClient(this.getAD_Client_ID(), get_TrxName());
			
			if(param==null) throw new AdempiereException("Error al obtener parametros de nomina");
			
			int conceptID = DB.getSQLValueEx(get_TrxName(), "select hr_concept_id from uy_hrparametros where ad_client_id = " + param.getAD_Client_ID());
			
			if(conceptID <= 0) throw new AdempiereException("No se obtuvo concepto de adelanto de sueldo en parametros de nomina");
			
			int processID = this.get_ValueAsInt("UY_HRProcess_ID");
			
			MHRProcesoNomina pn = MHRProcesoNomina.forProcess(getCtx(), processID, get_TrxName());
			
			if(pn!=null){
				if(pn.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed))
					throw new AdempiereException("Imposible completar, existe el documento de liquidacion de nomina Nº " + pn.getDocumentNo() + " en estado COMPLETO");			
				
			}			
			
			//obtengo cabezal de novedades si existe
			hdr = MHRNovedades.forProcessEmployee(this.getC_BPartner_ID(), processID, get_TrxName());

			//si no existe cabezal de novedades creo uno nuevo
			if(hdr == null){

				MDocType doc = MDocType.forValue(getCtx(), "novedadnom", get_TrxName());

				if(doc==null) throw new AdempiereException("Error al obtener documento Novedad Nomina");

				hdr = new MHRNovedades(getCtx(),0,get_TrxName());
				hdr.setC_BPartner_ID(this.getC_BPartner_ID());
				hdr.setUY_HRProcess_ID(processID);
				hdr.setC_DocType_ID(doc.get_ID());
				hdr.saveEx();

			}
			
			//obtengo linea de novedad si existe
			line = MHRConceptoLine.forProcessConcept(this.getC_BPartner_ID(), conceptID, processID, get_TrxName());
			
			if(line==null){
				
				line = new MHRConceptoLine(getCtx(),0,get_TrxName());
				line.setUY_HRNovedades_ID(hdr.get_ID());
				line.setHR_Concept_ID(conceptID);
				line.setAmount(this.getPayAmt());
				line.saveEx();			
				
			} else {
				
				line.setAmount(line.getAmount().add(this.getPayAmt()));
				line.saveEx();				
				
			}

		} catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}

	}
	
	/***
	 * Metodo que actualiza la novedad de nomina al reactivar/anular el documento de adelanto de sueldo.
	 * OpenUp Ltda. Issue #5174
	 * @author Nicolas Sarlabos - 28/03/2016
	 * @see
	 * @return
	 */
	public void updateNews(){

		MHRNovedades hdr = null;
		MHRConceptoLine line = null;
		String message = null;

		try {
			
			MHRParametros param = MHRParametros.forClient(this.getAD_Client_ID(), get_TrxName());
			
			if(param==null) throw new AdempiereException("Error al obtener parametros de nomina");
			
			int conceptID = DB.getSQLValueEx(get_TrxName(), "select hr_concept_id from uy_hrparametros where ad_client_id = " + param.getAD_Client_ID());
			
			if(conceptID <= 0) throw new AdempiereException("No se obtuvo concepto de adelanto de sueldo en parametros de nomina");
			
			int processID = this.get_ValueAsInt("UY_HRProcess_ID");
			
			MHRProcesoNomina pn = MHRProcesoNomina.forProcess(getCtx(), processID, get_TrxName());
			
			if(pn!=null){
				
				if(pn.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
					
					throw new AdempiereException("Imposible reactivar/anular por estar la liquidacion de nomina Nº " + pn.getDocumentNo() + " en estado COMPLETO");					
					
				} else message = "Debe reprocesar la liquidacion de nomina Nº " + pn.getDocumentNo();					
								
			}
			
			//obtengo cabezal de novedades si existe
			hdr = MHRNovedades.forProcessEmployee(this.getC_BPartner_ID(), processID, get_TrxName());

			//si no existe cabezal de novedades creo uno nuevo
			if(hdr != null){

				//obtengo linea de novedad si existe
				line = MHRConceptoLine.forProcessConcept(this.getC_BPartner_ID(), conceptID, processID, get_TrxName());

				if(line!=null){

					BigDecimal difAmt = line.getAmount().subtract(this.getPayAmt());

					if(difAmt.compareTo(Env.ZERO)>0){

						line.setAmount(line.getAmount().subtract(this.getPayAmt()));
						line.saveEx();						

					} else line.deleteEx(true, get_TrxName());

				}

				//elimino cabezal si no tiene lineas
				List<MHRConceptoLine> conLines = hdr.getLines("");

				if(conLines.size()<=0) hdr.deleteEx(true, get_TrxName());				

			}	

			//muestro mensaje de aviso si lo hay
			if(message!=null) ADialog.warn(0,null,message);		
			
		} catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}

	}
	
}   //  MPayment
