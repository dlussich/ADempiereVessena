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
package org.compiere.acct;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankAccount;
import org.compiere.model.MCharge;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MPayment;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MLinePayment;
import org.openup.model.MPaymentResguardo;
import org.openup.model.MRetention;
import org.openup.util.OpenUpUtils;

/**
 *  Post Invoice Documents.
 *  <pre>
 *  Table:              C_Payment (335)
 *  Document Types      ARP, APP
 *  </pre>
 *  @author Jorg Janke
 *  @version  $Id: Doc_Payment.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Payment extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_Payment (MAcctSchema[] ass, ResultSet rs, String trxName)
	{
		super (ass, MPayment.class, rs, null, trxName);
	}	//	Doc_Payment
	
	/**	Tender Type				*/
	//private String		m_TenderType = null;
	/** Prepayment				*/
	//private boolean		m_Prepayment = false;
	/** Bank Account			*/
	private int			m_C_BankAccount_ID = 0;

	// OpenUp. Gabriel Vila. 26/07/2010.
	// Array con Lineas del pago en objetos del tipo MLinePayment.
	private ArrayList<MLinePayment> paymentLines = new ArrayList<MLinePayment>();
	// Importe de descuentos del cabezal del recibo
	private BigDecimal importeDescuentos = new BigDecimal(0);
	// ID del BPartner del recibo
	private int	partnerID = 0;
	// Moneda del cabezal del recibo
	private int	currencyID = 0;
	
	// Importe de retenciones del cabezal del recibo
	private BigDecimal importeRetenciones = new BigDecimal(0);
		
	//Documento del cabezal
	private MPayment pay = null;
	// Fin OpenUp.
	//private boolean isPrepayment = false; //OpenUp. Nicolas Sarlabos. 09/08/2013. #1204
	
	/**
	 *  Load Specific Document Details
	 *  @return error message or null
	 */
	protected String loadDocumentDetails ()
	{
		pay = (MPayment)getPO();
		setDateDoc(pay.getDateTrx());
		//m_TenderType = pay.getTenderType();
		//m_Prepayment = pay.isPrepayment();
		m_C_BankAccount_ID = pay.getC_BankAccount_ID();
		
		// OpenUp. Gabriel Vila. 26/07/2010.
		this.paymentLines = pay.getPaymentLines();
		this.importeDescuentos = pay.getDiscountAmt();
		
//		//OpenUp sevans 22/02/2016 Issue #5456
//		//Se obtiene el total de los descuentos		
		if(pay.get_Value("totaldiscounts") != null)
			this.importeDescuentos = (BigDecimal)pay.get_Value("totaldiscounts");
		this.partnerID = pay.getC_BPartner_ID();
		this.currencyID = pay.getC_Currency_ID();
		//E.Bentancor #5164
		if(pay.get_Value("amtRetention") != null){
			this.importeRetenciones = (BigDecimal) pay.get_Value("amtRetention");
		}
		// Fin
		//this.isPrepayment = pay.isPrepayment(); //OpenUp. Nicolas Sarlabos. 09/08/2013. #1204
		
		//	Amount
		setAmount(Doc.AMTTYPE_Gross, pay.getPayAmt());
		return null;
	}   //  loadDocumentDetails

	
	/**************************************************************************
	 *  Get Source Currency Balance - always zero
	 *  @return Zero (always balanced)
	 */
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
	//	log.config( toString() + " Balance=" + retValue);
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  ARP, APP.
	 *  <pre>
	 *  ARP
	 *      BankInTransit   DR
	 *      UnallocatedCash         CR
	 *      or Charge/C_Prepayment
	 *  APP
	 *      PaymentSelect   DR
	 *      or Charge/V_Prepayment
	 *      BankInTransit           CR
	 *  CashBankTransfer
	 *      -
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		
		// OpenUp. Gabriel Vila. 04/11/2010.
		// Redirecciono a un proceso nuevo de OpenUp.
		return this.crearAsientosContables(as);
		// Fin OpenUp.

		/*
		
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		//	Cash Transfer
		if ("X".equals(m_TenderType))
		{
			ArrayList<Fact> facts = new ArrayList<Fact>();
			facts.add(fact);
			return facts;
		}

		int AD_Org_ID = getBank_Org_ID();		//	Bank Account Org	
		if (getDocumentType().equals(DOCTYPE_ARReceipt))
		{
			//	Asset
			FactLine fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankInTransit, as),
				getC_Currency_ID(), getAmount(), null);
			if (fl != null && AD_Org_ID != 0)
				fl.setAD_Org_ID(AD_Org_ID);
			//	
			MAccount acct = null;
			if (getC_Charge_ID() != 0)
				acct = MCharge.getAccount(getC_Charge_ID(), as, getAmount());
			else if (m_Prepayment)
				acct = getAccount(Doc.ACCTTYPE_C_Prepayment, as);
			else
				acct = getAccount(Doc.ACCTTYPE_UnallocatedCash, as);
			fl = fact.createLine(null, acct,
				getC_Currency_ID(), null, getAmount());
			if (fl != null && AD_Org_ID != 0
				&& getC_Charge_ID() == 0)		//	don't overwrite charge
				fl.setAD_Org_ID(AD_Org_ID);
		}
		//  APP
		else if (getDocumentType().equals(DOCTYPE_APPayment))
		{
			MAccount acct = null;
			if (getC_Charge_ID() != 0)
				acct = MCharge.getAccount(getC_Charge_ID(), as, getAmount());
			else if (m_Prepayment)
				acct = getAccount(Doc.ACCTTYPE_V_Prepayment, as);
			else
				acct = getAccount(Doc.ACCTTYPE_PaymentSelect, as);
			FactLine fl = fact.createLine(null, acct,
				getC_Currency_ID(), getAmount(), null);
			if (fl != null && AD_Org_ID != 0
				&& getC_Charge_ID() == 0)		//	don't overwrite charge
				fl.setAD_Org_ID(AD_Org_ID);
			
			//	Asset
			fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankInTransit, as),
				getC_Currency_ID(), null, getAmount());
			if (fl != null && AD_Org_ID != 0)
				fl.setAD_Org_ID(AD_Org_ID);
		}
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}
		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
		
		*/
		
	}   //  createFact

	/**
	 * 	Get AD_Org_ID from Bank Account
	 * 	@return AD_Org_ID or 0
	 */
	private int getBank_Org_ID ()
	{
		if (m_C_BankAccount_ID == 0)
			return 0;
		//
		MBankAccount ba = MBankAccount.get(getCtx(), m_C_BankAccount_ID);
		return ba.getAD_Org_ID();
	}	//	getBank_Org_ID

	
	/**
	 * OpenUp.	
	 * Descripcion : Creacion de asientos contables para invoices.
	 * @param as
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 04/11/2010
	 */
	private ArrayList<Fact> crearAsientosContables(MAcctSchema as) {
		
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// OpenUp. Gabriel Vila. 27/07/2010.
		// Modificaciones varias y radicales a la contabilidad de un recibo.
		// El mismo tiene lineas para multiples medios de pago.

		int AD_Org_ID = getBank_Org_ID();		//	Bank Account Org	
		int headerValidCombinationID = 0;
		int discountValidCombinationID = 0;
		int ivaValidCombinationID = 0;
		int ivaBasicoID = MTax.forValue(getCtx(), "basico", null).get_ID();
		
		BigDecimal currRate; 
		
		MPayment payment = (MPayment)getPO();
		
		// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
		// Obtengo esquema para saber cual es la moneda nacional.
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		// Fin Issue #539
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		
		// Recibo de cobranza
		if (getDocumentType().equals(DOCTYPE_ARReceipt))
		{
			// Importe total del recibo de cobranza al credito.
			MAccount acct = null;
			headerValidCombinationID = this.getReciboValidCombination(this.partnerID, this.currencyID, true, Doc.ACCTTYPE_C_Receivable, as);
			if (getC_Charge_ID() != 0)
				acct = MCharge.getAccount(getC_Charge_ID(), as, getAmount());
			else
				acct = MAccount.get (as.getCtx(), headerValidCombinationID);

			FactLine fl = fact.createLine(null, acct, this.currencyID, null, getAmount(), payment.getC_DocType_ID(), payment.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);
			// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
			// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
			if (fl != null) {
				if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
					this.setIsMultiCurrency(true);
					//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
					if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

						currRate = payment.getCurrencyRate();

					} else {

						currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),schema.getC_Currency_ID(),payment.getC_Currency_ID(),
								this.getAD_Client_ID(), this.getAD_Org_ID());					
					}	

					fl.setDivideRate(currRate);
					fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
					fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));

				}
				//Fin #5696.
			}
		
			// Fin Issue #539.

			
			// Descuentos al debito con dos asientos : el del descuento y el del impuesto que genera el descuento.
			
			// Importe neto, bruto e IVA Venta
			MTax iva = new MTax(getCtx(), ivaBasicoID, getTrxName()); // IVA
			BigDecimal importeNetoDescuentos = (this.importeDescuentos.divide((iva.getRate().divide(new BigDecimal(100),3, RoundingMode.HALF_UP)).add(new BigDecimal(1)),3, RoundingMode.HALF_UP)); 
			BigDecimal importeIVA = this.importeDescuentos.subtract(importeNetoDescuentos);
//			if(this.get_va)
//			BigDecimal importeRetentions = 

			// Asiento neto descuentos (si tengo importe)
			if (importeNetoDescuentos.compareTo(ZERO) !=  0){
				MAccount acctDiscount = null;
				discountValidCombinationID = this.getReciboValidCombination(this.partnerID, this.currencyID, true, Doc.ACCTTYPE_DiscountRev, as);
				acctDiscount = MAccount.get (as.getCtx(), discountValidCombinationID);
				fl = fact.createLine(null, acctDiscount, this.currencyID, importeNetoDescuentos, null,
						payment.getC_DocType_ID(), payment.getDocumentNo());
				// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
				// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
				if (fl != null) {
					if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
						this.setIsMultiCurrency(true);
						//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
						if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

							currRate = payment.getCurrencyRate();

						} else {

							currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),schema.getC_Currency_ID(),payment.getC_Currency_ID(),
									this.getAD_Client_ID(), this.getAD_Org_ID());							
						}					

						fl.setDivideRate(currRate);
						fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
						fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));

					}
					//Fin #5696.
				}
			}
				// Fin Issue #539.
						

			//E.Bentancor 10/2/2016 Issue #5164
			if(this.importeRetenciones.compareTo(ZERO) !=  0){
				// Cuentas de retenciones de clientes para moneda nacional y moneda extranjera
				MAccount cuentaRetCliMN = MAccount.get(getCtx(), schema.getAcctSchemaDefault().get_ValueAsInt("P_RetencionCobroMN_Acct"));
				MAccount cuentaRetCliME = MAccount.get(getCtx(), schema.getAcctSchemaDefault().get_ValueAsInt("P_RetencionCobroME_Acct"));
				
				if (fl != null) {
					if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
						this.setIsMultiCurrency(true);
						if (payment.getCurrencyRate() != null){
							if (payment.getCurrencyRate().compareTo(Env.ZERO) > 0){
								fl = fact.createLine(null, cuentaRetCliME, this.currencyID, this.importeRetenciones, null,
										payment.getC_DocType_ID(), payment.getDocumentNo());
								
								fl.setDivideRate(payment.getCurrencyRate());
								fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
							}
						}
					}else{
						fl = fact.createLine(null, cuentaRetCliMN, this.currencyID, this.importeRetenciones, null,
								payment.getC_DocType_ID(), payment.getDocumentNo());
					}
				}
			}// Fin Issue #5164
			
			
			// Asiento iva ventas
			if (importeIVA.compareTo(ZERO) !=  0){
				MAccount acctIVA = null;
				ivaValidCombinationID = this.getReciboValidCombination(ivaBasicoID, this.currencyID, true, Doc.ACCTTYPE_IVA_VENTAS, as);
				acctIVA = MAccount.get (as.getCtx(), ivaValidCombinationID);
				fl = fact.createLine(null, acctIVA, this.currencyID, importeIVA, null,
						payment.getC_DocType_ID(), payment.getDocumentNo());
				// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
				// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
				if (fl != null) {
					if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
						this.setIsMultiCurrency(true);
						//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
						if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

							currRate = payment.getCurrencyRate();

						} else {

							currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),schema.getC_Currency_ID(),payment.getC_Currency_ID(),
									this.getAD_Client_ID(), this.getAD_Org_ID());								
						}	

						fl.setDivideRate(currRate);
						fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
						fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));

					}
					//Fin #5696.
				}
			}
				// Fin Issue #539.			
			
			// Al debito van las lineas del recibo de cobranza
			for (int i=0;i<this.paymentLines.size();i++){
				MLinePayment line = this.paymentLines.get(i);
				// Si no es en efectivo
				if (!line.getTenderType().equalsIgnoreCase(MPayment.TENDERTYPE_Cash)){
					int validCombinationID = this.getReciboValidCombination(line.getC_BankAccount_ID(), this.currencyID, true, Doc.ACCTTYPE_BankEmited, as);
					MAccount account = MAccount.get (as.getCtx(), validCombinationID);
					fl = fact.createLine(null, account, getC_Currency_ID(), line.getPayAmt(), null,
							payment.getC_DocType_ID(), payment.getDocumentNo());
					if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
					// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
					// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
					if (fl != null) {
						if (payment.getC_Currency_ID() != line.get_ValueAsInt("c_currency_id")){
							this.setIsMultiCurrency(true);
							//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
							if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

								currRate = payment.getCurrencyRate();

							} else {

								currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),line.get_ValueAsInt("c_currency_id"),payment.getC_Currency_ID(),
										this.getAD_Client_ID(), this.getAD_Org_ID());									
							}

							fl.setDivideRate(currRate);
							fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
							fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));

						}
						//Fin #5696.
					}
					// Fin Issue #539.			
					
				}
				else{
					// Es en efectivo
					int validCombinationID = this.getReciboValidCombination(line.getC_BankAccount_ID(), this.currencyID, true, Doc.ACCTTYPE_BankAsset, as);
					MAccount account = MAccount.get (as.getCtx(), validCombinationID);
					fl = fact.createLine(null, account, getC_Currency_ID(), line.getPayAmt(), null,
							payment.getC_DocType_ID(), payment.getDocumentNo());
					if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
					// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
					// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
					if (fl != null) {
						if (payment.getC_Currency_ID() != line.get_ValueAsInt("c_currency_id")){
							this.setIsMultiCurrency(true);
							//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
							if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

								currRate = payment.getCurrencyRate();

							} else {

								currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),line.get_ValueAsInt("c_currency_id"),payment.getC_Currency_ID(),
										this.getAD_Client_ID(), this.getAD_Org_ID());								
							}

							fl.setDivideRate(currRate);
							fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
							fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));

						}
						//Fin #5696.
					}
					// Fin Issue #539.

				}
			}
			
			// OpenUp. Gabriel Vila. 22/02/2011. Issue #390.
			// Redondeo.
			FactLine roundLine = fact.createRounding(payment.getC_DocType_ID(), payment.getDocumentNo(), 0, 0, 0, 0);
			// Fin OpenUp.
			
			// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
			// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
			if (roundLine != null) {
				if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
					this.setIsMultiCurrency(true);
					if (payment.getCurrencyRate() != null){
						if (payment.getCurrencyRate().compareTo(Env.ZERO) > 0){
							roundLine.setDivideRate(payment.getCurrencyRate());
							roundLine.setAmtAcctDr(roundLine.getAmtSourceDr().multiply(roundLine.getDivideRate()));
							roundLine.setAmtAcctCr(roundLine.getAmtSourceCr().multiply(roundLine.getDivideRate()));
						}
					}
				}
			}
			// Fin Issue #539.

			
		}
		
		//  Si es Recibo de Pago
		else if (getDocumentType().equals(DOCTYPE_APPayment))
		{
			// Importe total del recibo de pago al debito.
			MAccount acct = null;
			
			//OpenUp. Nicolas Sarlabos. 28/03/2016. #5174.
			MDocType doc = (MDocType)payment.getC_DocType();
			//Fin OpenUp.			
			
			//OpenUp. Nicolas Sarlabos. 09/08/2013. #1204
			if(payment.isPrepayment()){			
				headerValidCombinationID = this.getReciboValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Prepayment, as);
				if (headerValidCombinationID <= 0){
					p_Error = "Falta parametrizar la cuenta Anticipos para este Socio de Negocio y Moneda";
					log.log(Level.SEVERE, p_Error);
					fact = null;
					facts.add(fact);
					return facts;
				}
			} 
			else{
				//OpenUp. Nicolas Sarlabos. 28/03/2016. #5174.
				if(doc.getValue()!=null && doc.getValue().equalsIgnoreCase("adelanto")){
					
					headerValidCombinationID = DB.getSQLValueEx(getTrxName(), "select e_prepayment_acct from c_acctschema_default where ad_client_id = " + this.getAD_Client_ID());
					if (headerValidCombinationID <= 0){
						p_Error = "Falta parametrizar la cuenta Pago Anticipado a Empleados en cuentas por defecto del esquema contable";
						log.log(Level.SEVERE, p_Error);
						fact = null;
						facts.add(fact);
						return facts;
					}				
				//Fin OpenUp.	
				} else {
					
					headerValidCombinationID = this.getReciboValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability, as);
					if (headerValidCombinationID <= 0){
						p_Error = "Falta parametrizar la cuenta Proveedor para este Socio de Negocio y Moneda";
						log.log(Level.SEVERE, p_Error);
						fact = null;
						facts.add(fact);
						return facts;
					}					
					
				}
				
			}
			//Fin OpenUp #1204
			
			if (getC_Charge_ID() != 0)
				acct = MCharge.getAccount(getC_Charge_ID(), as, getAmount());
			else
				acct = MAccount.get (as.getCtx(), headerValidCombinationID);
			
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), getAmount(), null,
					payment.getC_DocType_ID(), payment.getDocumentNo());
			
			// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
			// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
			if (fl != null) {
				if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
					this.setIsMultiCurrency(true);
					//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
					if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

						currRate = payment.getCurrencyRate();

					}	else {

						currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),schema.getC_Currency_ID(),payment.getC_Currency_ID(),
								this.getAD_Client_ID(), this.getAD_Org_ID());

					}	

					fl.setDivideRate(currRate);
					fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
					fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));	
					
				}
				//Fin #5696.
			}
			// Fin Issue #539.
			
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Descuentos al debito con dos asientos : el del descuento y el del impuesto que genera el descuento.
			
			// Importe neto, bruto e IVA compra
			MTax iva = new MTax(getCtx(), ivaBasicoID, getTrxName()); // IVA
			//BigDecimal importeIVA = (this.importeDescuentos.multiply(iva.getRate()).divide(new BigDecimal(100),3, RoundingMode.HALF_UP)); 
			//BigDecimal importeNetoDescuentos = this.importeDescuentos.subtract(importeIVA);
			BigDecimal importeNetoDescuentos = (this.importeDescuentos.divide((iva.getRate().divide(new BigDecimal(100),3, RoundingMode.HALF_UP)).add(new BigDecimal(1)),3, RoundingMode.HALF_UP)); 
			BigDecimal importeIVA = this.importeDescuentos.subtract(importeNetoDescuentos);


			importeNetoDescuentos = this.importeDescuentos; 
			importeIVA = Env.ZERO;

			
			// Asiento neto descuentos 
			if (importeNetoDescuentos.compareTo(ZERO) !=  0){
				MAccount acctDiscount = null;
				discountValidCombinationID = this.getReciboValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_DiscountExp, as);
				acctDiscount = MAccount.get (as.getCtx(), discountValidCombinationID);
				fl = fact.createLine(null, acctDiscount, this.currencyID, null, importeNetoDescuentos,
						payment.getC_DocType_ID(), payment.getDocumentNo());
				// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
				// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
				if (fl != null) {
					if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
						this.setIsMultiCurrency(true);
						//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
						if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

							currRate = payment.getCurrencyRate();

						} else {

							currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),schema.getC_Currency_ID(),payment.getC_Currency_ID(),
									this.getAD_Client_ID(), this.getAD_Org_ID());	
						}
						
						fl.setDivideRate(currRate);
						fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
						fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
						
					}
					//Fin #5696.
				}
				// Fin Issue #539.				
			}

			// Asiento iva compra
			if (importeIVA.compareTo(ZERO) !=  0){
				MAccount acctIVA = null;
				ivaValidCombinationID = this.getReciboValidCombination(ivaBasicoID, this.currencyID, false, Doc.ACCTTYPE_IVA_COMPRAS, as);
				acctIVA = MAccount.get (as.getCtx(), ivaValidCombinationID);
				fl = fact.createLine(null, acctIVA, this.currencyID, null, importeIVA,
						payment.getC_DocType_ID(), payment.getDocumentNo());
				// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
				// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
				if (fl != null) {
					if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
						this.setIsMultiCurrency(true);
						//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
						if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

							currRate = payment.getCurrencyRate();

						} else {				

							currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),schema.getC_Currency_ID(),payment.getC_Currency_ID(),
									this.getAD_Client_ID(), this.getAD_Org_ID());							
						}
						
						fl.setDivideRate(currRate);
						fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
						fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
					}//Fin #5696.
				}
				// Fin Issue #539.		
			}
			
			// Al credito van las lineas del recibo de pago
			for (int i=0;i<this.paymentLines.size();i++){
				MLinePayment line = this.paymentLines.get(i);
				// Si no es en efectivo
				if (!line.getTenderType().equalsIgnoreCase(MPayment.TENDERTYPE_Cash)){
					int validCombinationID = this.getReciboValidCombination(line.getC_BankAccount_ID(), this.currencyID, false, Doc.ACCTTYPE_PaymentSelect, as);
					MAccount account = MAccount.get (as.getCtx(), validCombinationID);
					fl = fact.createLine(null, account, getC_Currency_ID(), null, line.getPayAmt(),
							payment.getC_DocType_ID(), payment.getDocumentNo());
					//if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
					// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
					// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
					if (fl != null) {
						if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
							this.setIsMultiCurrency(true);
							//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
							if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

								currRate = payment.getCurrencyRate();

							} else {						

								currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),schema.getC_Currency_ID(),payment.getC_Currency_ID(),
										this.getAD_Client_ID(), this.getAD_Org_ID());	
							}

							fl.setDivideRate(currRate);
							fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
							fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
						}
						//Fin #5696.
					}
					// Fin Issue #539.				
					
				}
				else{
					// Es en efectivo
					int validCombinationID = this.getReciboValidCombination(line.getC_BankAccount_ID(), this.currencyID, false, Doc.ACCTTYPE_BankAsset, as);
					MAccount account = MAccount.get (as.getCtx(), validCombinationID);
					fl = fact.createLine(null, account, getC_Currency_ID(), null, line.getPayAmt(),
							payment.getC_DocType_ID(), payment.getDocumentNo());
					//if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
					// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
					// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
					if (fl != null) {
						if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
							this.setIsMultiCurrency(true);
							//OpenUp. Nicolas Sarlabos. 06/04/2016. Issue #5696.
							if (payment.getCurrencyRate() != null && payment.getCurrencyRate().compareTo(Env.ZERO) > 0){

								currRate = payment.getCurrencyRate();

							} else {							

								currRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateDoc(),schema.getC_Currency_ID(),payment.getC_Currency_ID(),
										this.getAD_Client_ID(), this.getAD_Org_ID());	
							}

							fl.setDivideRate(currRate);
							fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
							fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate().setScale(2, RoundingMode.CEILING)));
						}
						//Fin #5696.
					}
					// Fin Issue #539.		
					
				}
			}
			
			// OpenUp. Gabriel Vila. 23/11/2012. Issue #100
			// Si manejo retenciones, debo considerarlas para el asiento del recibo de pago
			if (MSysConfig.getBooleanValue("UY_HANDLE_RETENTION", false, this.getAD_Client_ID())){

				
				// Obtengo y recorro lineas de resguardos, sumo importe de resguardo segun moneda del recibo
				List<MPaymentResguardo> resgs = payment.getResguardos();

				// CR - Lineas de Resguardo
				for (MPaymentResguardo resg: resgs){
					
					MRetention retention = (MRetention)resg.getUY_Retention();
					MAccount acctRet = MAccount.get(getCtx(), retention.getR_Transit_Acct());

					BigDecimal amtRet = resg.getAmt();
					if (payment.getC_Currency_ID() != as.getC_Currency_ID()){
						amtRet = resg.getAmtSource();
					}
					
					fl = fact.createLine(null, acctRet, payment.getC_Currency_ID(), null, amtRet,
							payment.getC_DocType_ID(), payment.getDocumentNo());
					if (fl != null && payment.getAD_Org_ID() != 0) fl.setAD_Org_ID(payment.getAD_Org_ID());
					
					// Si la moneda no es la nacional tengo que usar la misma tasa de cambio que se uso
					// al momento de generarse el resguardo, como no se uso una unica tasa, lo que
					// hago es obtenerlo de la division de los importes en distinta moneda.			
					if (payment.getC_Currency_ID() != as.getC_Currency_ID()){
						if (resg.getAmtSource().compareTo(Env.ZERO) != 0){
							BigDecimal rate = resg.getAmt().divide(resg.getAmtSource(), 2, RoundingMode.HALF_UP);
							fl.setDivideRate(rate);
							fl.setAmtAcctCr(resg.getAmtSource().multiply(rate).setScale(2, RoundingMode.HALF_UP));
						}
					}
				}
			}
			// Fin OpenUp
			
			// OpenUp. Gabriel Vila. 22/02/2011. Issue #390.
			// Redondeo.
			FactLine roundLine = fact.createRounding(payment.getC_DocType_ID(), payment.getDocumentNo(), 0, 0, 0, 0);
			// Fin OpenUp.

			// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
			// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
			if (roundLine != null) {
				if (payment.getC_Currency_ID() != schema.getC_Currency_ID()){
					this.setIsMultiCurrency(true);
					if (payment.getCurrencyRate() != null){
						if (payment.getCurrencyRate().compareTo(Env.ZERO) > 0){
							roundLine.setDivideRate(payment.getCurrencyRate());
							roundLine.setAmtAcctDr(roundLine.getAmtSourceDr().multiply(roundLine.getDivideRate()));
							roundLine.setAmtAcctCr(roundLine.getAmtSourceCr().multiply(roundLine.getDivideRate()));
						}
					}
				}
			}
			// Fin Issue #539.

		}
		else
		{
			p_Error = "Tipo de Documento desconocido: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}
		
		
		facts.add(fact);
		return facts;
	}
	
	/**
	 * Obtengo validcombinationID para una linea de recibo.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila. - 17/02/2012
	 * @see
	 * @param keyID
	 * @param currencyID
	 * @param isReceipt
	 * @param acctType
	 * @param acctSchema
	 * @return
	 */
	private int getReciboValidCombination(int keyID, int currencyID, boolean isReceipt, 
					 				      int acctType, MAcctSchema acctSchema){

		String sql = "", sql2 = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = -1;		
		boolean aplicaFiltroMoneda = false;
		boolean usoDefault = false;
		
		MBPartner bp = null;
		MBPGroup grupoBP = null;

		
		try{			

			// Cuentas en comun para pagos y cobranzas
			if (acctType==Doc.ACCTTYPE_BankInTransit) {
				sql = "SELECT B_InTransit_Acct FROM C_BankAccount_Acct WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
			}

			if (acctType==Doc.ACCTTYPE_BankAsset){
				sql = "SELECT B_Asset_Acct FROM C_BankAccount_Acct WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
			}

			if (acctType==Doc.ACCTTYPE_PaymentSelect){
				sql = "SELECT B_PaymentSelect_Acct FROM C_BankAccount_Acct WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
			}
			
			if (acctType==Doc.ACCTTYPE_BankEmited){
				sql = "SELECT B_Emited_Acct FROM C_BankAccount_Acct WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
			}
		
			if (acctType==Doc.ACCTTYPE_IVA_COMPRAS){
				sql = "SELECT t_credit_acct FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
			}

			if (acctType==Doc.ACCTTYPE_IVA_VENTAS){
				sql = "SELECT t_due_acct FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
			}
			
			// Cuentas particulares de un recibo de cobranza
			if (isReceipt){
				
				// Obtengo grupo del socio de negocio
				bp = new MBPartner(getCtx(), keyID, null);
				grupoBP = (MBPGroup)bp.getC_BP_Group();
				
				// Si la moneda del recibo es moneda nacional
				if (currencyID==acctSchema.getC_Currency_ID()){
					if (acctType==Doc.ACCTTYPE_C_Receivable){
						sql = "SELECT C_Receivable_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT C_Receivable_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";	
						}

						
					}
					else if (acctType==Doc.ACCTTYPE_DiscountRev){
						sql = "SELECT PayDiscount_Rev_Acct FROM C_BP_Group_Acct a, C_BPartner bp "
							+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=?";
						
					}
				}
				else{
					// Moneda extranjera
					if (acctType==Doc.ACCTTYPE_C_Receivable){
						sql = "SELECT C_Receivable_Acct FROM UY_ClientesME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT C_Receivable_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}
						
					}
					else if (acctType==Doc.ACCTTYPE_DiscountRev){
						sql = "SELECT PayDiscount_Rev_Acct FROM UY_BP_Group_Acct a, C_BPartner bp "
							+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
					}
				}
			}
			else{
				// PAGO
				
				// Obtengo grupo del socio de negocio
				bp = new MBPartner(getCtx(), keyID, null);
				grupoBP = (MBPGroup)bp.getC_BP_Group();
				
				// Si la moneda del recibo es moneda nacional
				if (currencyID==acctSchema.getC_Currency_ID()){
					if (acctType==Doc.ACCTTYPE_V_Liability){

						sql = "SELECT V_Liability_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Liability_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}
						
					}

					else if (acctType==Doc.ACCTTYPE_DiscountExp){
						//OpenUp. Nicolas Sarlabos. 09/08/2013. #1205
						/*
						sql = "SELECT PayDiscount_Exp_Acct FROM C_BP_Group_Acct a, C_BPartner bp "
							+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=?";
						*/	
						sql = "SELECT PayDiscount_Exp_Acct FROM c_acctschema_default where c_acctschema_id =?";
						usoDefault = true;
						//Fin OpenUp #1205
					}//OpenUp. Nicolas Sarlabos. 09/08/2013. #1204

					
					else if (acctType==Doc.ACCTTYPE_V_Prepayment){
						
						sql = "SELECT V_Prepayment_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Prepayment_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}
						

						
					}//Fin OpenUp #1204
				}
				else{
					// Moneda extranjera
					if (acctType==Doc.ACCTTYPE_V_Liability){
						sql = "SELECT V_Liability_Acct FROM UY_ProveedoresME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Liability_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}
						
					}

					else if (acctType==Doc.ACCTTYPE_DiscountExp){
						//OpenUp. Nicolas Sarlabos. 09/08/2013. #1205
						/*
						sql = "SELECT PayDiscount_Exp_Acct FROM UY_BP_Group_Acct a, C_BPartner bp "
							+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=? AND C_Currency_ID=?";
						*/
						sql = " SELECT PayDiscount_Exp_ME_Acct FROM c_acctschema_default where c_acctschema_id =?";
						aplicaFiltroMoneda = false;
						usoDefault = true;
						//Fin OpenUp #1205
					}//OpenUp. Nicolas Sarlabos. 09/08/2013. #1204
					
					else if (acctType==Doc.ACCTTYPE_V_Prepayment){
						sql = "SELECT V_Prepayment_Acct FROM UY_ProveedoresME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Prepayment_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}
						
					}//Fin OpenUp #1204
				}
			}
			pstmt = DB.prepareStatement (sql, null);

			if (!usoDefault){
				pstmt.setInt(1, keyID);
				pstmt.setInt(2, acctSchema.getC_AcctSchema_ID());
			}
			else{
				pstmt.setInt(1, acctSchema.getC_AcctSchema_ID());
			}
			if (aplicaFiltroMoneda) pstmt.setInt(3, currencyID);
			
			rs = pstmt.executeQuery ();

			if (rs.next()) value = rs.getInt(1);
			
			// Si no obtuve desde cuentas del socio de negocio, busco cuentas en grupo del socio de negocio
			if ((value <= 0) && !sql2.equalsIgnoreCase("")) {

				DB.close(rs, pstmt);
				rs = null; pstmt = null;

				pstmt = DB.prepareStatement (sql2, null);
				pstmt.setInt(1, grupoBP.get_ID());
				pstmt.setInt(2, acctSchema.getC_AcctSchema_ID());
				if (aplicaFiltroMoneda) pstmt.setInt(3, currencyID);
				
				rs = pstmt.executeQuery ();

				if (rs.next()) value = rs.getInt(1);

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

	
}   //  Doc_Payment
