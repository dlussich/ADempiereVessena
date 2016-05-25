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
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MClientInfo;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCostDetail;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLandedCostAllocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTax;
import org.compiere.model.ProductCost;
import org.compiere.model.X_M_Product_Acct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.InvoiceLineAmtByProdAct;
import org.openup.model.MCFEInboxFileType;
import org.openup.model.MInvoiceCashPayment;
import org.openup.model.MInvoiceProvision;
import org.openup.model.MTRConfig;
import org.openup.model.MTRConfigAcct;
import org.openup.model.MTRConfigVFlete;
import org.openup.model.MTRCrt;
import org.openup.model.MTRTransOrder;
import org.openup.model.MTRTransOrderLine;
import org.openup.model.MTRTrip;
import org.openup.model.X_UY_TR_TransOrder;
import org.openup.model.X_UY_TR_Trip;

/**
 *  Post Invoice Documents.	
 *  <pre>
 *  Table:              C_Invoice (318)
 *  Document Types:     ARI, ARC, ARF, API, APC
 *  </pre>
 *  @author Jorg Janke
 *  @author Armen Rizal, Goodwill Consulting
 *  	<li>BF: 2797257	Landed Cost Detail is not using allocation qty
 *  
 *  @version  $Id: Doc_Invoice.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Invoice extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_Invoice(MAcctSchema[] ass, ResultSet rs, String trxName)
	{
		super (ass, MInvoice.class, rs, null, trxName);
	}	//	Doc_Invoice

	/** Contained Optional Tax Lines    */
	private DocTax[]        m_taxes = null;
	/** Currency Precision				*/
	private int				m_precision = -1;
	/** All lines are Service			*/
	private boolean			m_allLinesService = true;
	/** All lines are product item		*/
	private boolean			m_allLinesItem = true;

	// OpenUp. Gabriel Vila. 30/07/2010.
	// Declaracion de atributos
	private int currencyID = 0;
	private int partnerID = 0;
	private boolean checkInitialBalance = true;
	private MInvoice invoice = null;
	// Fin OpenUp
	
	/**
	 *  Load Specific Document Details
	 *  @return error message or null
	 */
	protected String loadDocumentDetails ()
	{
		this.invoice = (MInvoice)getPO();
		setDateDoc(invoice.getDateInvoiced());
		setIsTaxIncluded(invoice.isTaxIncluded());
		//	Amounts
		setAmount(Doc.AMTTYPE_Gross, invoice.getGrandTotal());
		setAmount(Doc.AMTTYPE_Net, invoice.getTotalLines());
		setAmount(Doc.AMTTYPE_Charge, invoice.getChargeAmt());
				
		//	Contained Objects
		m_taxes = loadTaxes();
		p_lines = loadLines(invoice);
		log.fine("Lines=" + p_lines.length + ", Taxes=" + m_taxes.length);
		
		// OpenUp. Gabriel Vila. 30/07/2010.
		// Seteo de atributos
		this.currencyID = invoice.getC_Currency_ID();
		this.partnerID = invoice.getC_BPartner_ID();
		// Fin OpenUp
		
		// OpenUp. Gabriel Vila. 18/11/2014. Issue #3205
		// Hay documentos cuya contabilzacion contempla otras lineas ademas de productos y por lo tanto el chequeo inicial de balanceo no debe hacerse.
		MDocType doc = (MDocType)invoice.getC_DocTypeTarget();
		if (doc != null){
			if (doc.getValue() != null){
				if (doc.getValue().equalsIgnoreCase("inv_valeflete")){
					this.checkInitialBalance = false;
				}
			}
		}
		// Fin OpenUp. Issue #3205
		
		return null;
	}   //  loadDocumentDetails

	/**
	 *	Load Invoice Taxes
	 *  @return DocTax Array
	 */
	private DocTax[] loadTaxes()
	{
		ArrayList<DocTax> list = new ArrayList<DocTax>();
		String sql = "SELECT it.C_Tax_ID, t.Name, t.Rate, it.TaxBaseAmt, it.TaxAmt, t.IsSalesTax "
			+ "FROM C_Tax t, C_InvoiceTax it "
			+ "WHERE t.C_Tax_ID=it.C_Tax_ID AND it.C_Invoice_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			pstmt.setInt(1, get_ID());
			rs = pstmt.executeQuery();
			//
			while (rs.next())
			{
				int C_Tax_ID = rs.getInt(1);
				String name = rs.getString(2);
				BigDecimal rate = rs.getBigDecimal(3);
				BigDecimal taxBaseAmt = rs.getBigDecimal(4);
				BigDecimal amount = rs.getBigDecimal(5);
				boolean salesTax = "Y".equals(rs.getString(6));
				//
				DocTax taxLine = new DocTax(C_Tax_ID, name, rate, 
					taxBaseAmt, amount, salesTax);
				log.fine(taxLine.toString());
				list.add(taxLine);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return null;
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//	Return Array
		DocTax[] tl = new DocTax[list.size()];
		list.toArray(tl);
		return tl;
	}	//	loadTaxes

	/**
	 *	Load Invoice Line
	 *	@param invoice invoice
	 *  @return DocLine Array
	 */
	private DocLine[] loadLines (MInvoice invoice)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		//
		MInvoiceLine[] lines = invoice.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MInvoiceLine line = lines[i];
			if (line.isDescription())
				continue;
			
			// OpenUp. Gabriel Vila. 14/09/2011. Issue #326.
			// No considero lineas sin importe para contabilizacion
			if (line.getLineNetAmt().compareTo(Env.ZERO) == 0)
				continue;
			// Fin Issue #326
			
			DocLine docLine = new DocLine(line, this);
			//	Qty
			BigDecimal Qty = line.getQtyInvoiced();
			boolean cm = getDocumentType().equals(DOCTYPE_ARCredit) 
				|| getDocumentType().equals(DOCTYPE_APCredit);
			docLine.setQty(cm ? Qty.negate() : Qty, invoice.isSOTrx());
			//OpenUp. Nicolas Sarlabos. 09/01/2013. Aplico porcentaje de descuento al pie de factura para evitar errores de posteo
			BigDecimal invoiceDiscount = invoice.getDiscount();
			if (invoiceDiscount == null) invoiceDiscount = Env.ZERO;
			BigDecimal discount = invoiceDiscount.divide(Env.ONEHUNDRED);
			discount = line.getLineNetAmt().multiply(discount);
			BigDecimal LineNetAmt = (line.getLineNetAmt().subtract(discount)).setScale(getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			//Fin OpenUp.
			BigDecimal PriceList = line.getPriceList();
			int C_Tax_ID = docLine.getC_Tax_ID();
			//	Correct included Tax
			if (isTaxIncluded() && C_Tax_ID != 0)
			{
				MTax tax = MTax.get(getCtx(), C_Tax_ID);
				if (!tax.isZeroTax())
				{
					BigDecimal LineNetAmtTax = tax.calculateTax(LineNetAmt, true, getStdPrecision());
					log.fine("LineNetAmt=" + LineNetAmt + " - Tax=" + LineNetAmtTax);
					LineNetAmt = LineNetAmt.subtract(LineNetAmtTax);
					for (int t = 0; t < m_taxes.length; t++)
					{
						if (m_taxes[t].getC_Tax_ID() == C_Tax_ID)
						{
							m_taxes[t].addIncludedTax(LineNetAmtTax);
							break;
						}
					}
					BigDecimal PriceListTax = tax.calculateTax(PriceList, true, getStdPrecision());
					PriceList = PriceList.subtract(PriceListTax);
				}
			}	//	correct included Tax
			
			docLine.setAmount (LineNetAmt, PriceList, Qty);	//	qty for discount calc
			if (docLine.isItem())
				m_allLinesService = false;
			else
				m_allLinesItem = false;
			//
			log.fine(docLine.toString());
			list.add(docLine);
		}
		
		//	Convert to Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);

		//	Included Tax - make sure that no difference
		if (isTaxIncluded())
		{
			for (int i = 0; i < m_taxes.length; i++)
			{
				if (m_taxes[i].isIncludedTaxDifference())
				{
					BigDecimal diff = m_taxes[i].getIncludedTaxDifference(); 
					for (int j = 0; j < dls.length; j++)
					{
						if (dls[j].getC_Tax_ID() == m_taxes[i].getC_Tax_ID())
						{
							dls[j].setLineNetAmtDifference(diff);
							break;
						}
					}	//	for all lines
				}	//	tax difference
			}	//	for all taxes
		}	//	Included Tax difference
		
		//	Return Array
		return dls;
	}	//	loadLines

	/**
	 * 	Get Currency Precision
	 *	@return precision
	 */
	private int getStdPrecision()
	{
		if (m_precision == -1)
			m_precision = MCurrency.getStdPrecision(getCtx(), getC_Currency_ID());
		return m_precision;
	}	//	getPrecision

	
	/**************************************************************************
	 *  Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *  @return positive amount, if total invoice is bigger than lines
	 */
	public BigDecimal getBalance()
	{
		
		// OpenUp. Gabriel Vila. 18/11/2014. Issue #3205
		// En documentos que no se debe hacer chequeo inicial de balanceo, salgo sin problemas
		if (!this.checkInitialBalance) return Env.ZERO;
		// Fin OpenUp. Issue #3205
		
		BigDecimal retValue = Env.ZERO;
		StringBuffer sb = new StringBuffer (" [");
		//  Total
		retValue = retValue.add(getAmount(Doc.AMTTYPE_Gross));
		sb.append(getAmount(Doc.AMTTYPE_Gross));
		//  - Header Charge
		retValue = retValue.subtract(getAmount(Doc.AMTTYPE_Charge));
		sb.append("-").append(getAmount(Doc.AMTTYPE_Charge));
		//  - Tax
		for (int i = 0; i < m_taxes.length; i++)
		{
			retValue = retValue.subtract(m_taxes[i].getAmount());
			sb.append("-").append(m_taxes[i].getAmount());
		}
		//  - Lines
		for (int i = 0; i < p_lines.length; i++)
		{
			retValue = retValue.subtract(p_lines[i].getAmtSource());
			sb.append("-").append(p_lines[i].getAmtSource());
		}
		sb.append("]");
		//
		log.fine(toString() + " Balance=" + retValue + sb.toString());
		
		// OpenUp. Gabriel Vila. 15/03/2013. Issue #556
		// Si no cierra por centavos lo marco como balanceado ya que despues el sistema por esta diferencia hace ajuste automatico
		BigDecimal bordeInferior = new BigDecimal(-0.9);
		BigDecimal bordeSuperior = new BigDecimal(0.9);
		if (retValue.compareTo(bordeInferior)>=0 && retValue.compareTo(bordeSuperior)<=0){
			retValue = Env.ZERO;
		}
		// Fin Issue #556
		
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  ARI, ARC, ARF, API, APC.
	 *  <pre>
	 *  ARI, ARF
	 *      Receivables     DR
	 *      Charge                  CR
	 *      TaxDue                  CR
	 *      Revenue                 CR
	 *
	 *  ARC
	 *      Receivables             CR
	 *      Charge          DR
	 *      TaxDue          DR
	 *      Revenue         RR
	 *
	 *  API
	 *      Payables                CR
	 *      Charge          DR
	 *      TaxCredit       DR
	 *      Expense         DR
	 *
	 *  APC
	 *      Payables        DR
	 *      Charge                  CR
	 *      TaxCredit               CR
	 *      Expense                 CR
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
		
		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		//  Cash based accounting
		if (!as.isAccrual())
			return facts;

		//  ** ARI, ARF
		if (getDocumentType().equals(DOCTYPE_ARInvoice) 
			|| getDocumentType().equals(DOCTYPE_ARProForma))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			
			//  Header Charge           CR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0)
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), null, amt);
			//  TaxDue                  CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getAmount();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
						getC_Currency_ID(), null, amt);
					if (tl != null)
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
				}
			}
			//  Revenue                 CR
			for (int i = 0; i < p_lines.length; i++)
			{
				amt = p_lines[i].getAmtSource();
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amt = amt.add(discount);
						dAmt = discount;
						fact.createLine (p_lines[i],
								p_lines[i].getAccount(ProductCost.ACCTTYPE_P_TDiscountGrant, as),
								getC_Currency_ID(), dAmt, null);
					}
				}
				fact.createLine (p_lines[i],
					p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as),
					getC_Currency_ID(), null, amt);
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      //  from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				}
			}
			
			//  Receivables     DR
			int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable, as);
			int receivablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_C_Receivable_Services, as);
			if (m_allLinesItem || !as.isPostServices() 
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), grossAmt, null);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), serviceAmt, null);
		}
		//  ARC
		else if (getDocumentType().equals(DOCTYPE_ARCredit))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			//  Header Charge   DR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0)
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), amt, null);
			//  TaxDue          DR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getAmount();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
						getC_Currency_ID(), amt, null);
					if (tl != null)
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
				}
			}
			//  Revenue         CR
			for (int i = 0; i < p_lines.length; i++)
			{
				amt = p_lines[i].getAmtSource();
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amt = amt.add(discount);
						dAmt = discount;
						fact.createLine (p_lines[i],
								p_lines[i].getAccount (ProductCost.ACCTTYPE_P_TDiscountGrant, as),
								getC_Currency_ID(), null, dAmt);
					}
				}
				fact.createLine (p_lines[i],
					p_lines[i].getAccount (ProductCost.ACCTTYPE_P_Revenue, as),
					getC_Currency_ID(), amt, null);
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      //  from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				}
			}
			//  Receivables             CR
			int receivables_ID = getValidCombination_ID (Doc.ACCTTYPE_C_Receivable, as);
			int receivablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_C_Receivable_Services, as);
			if (m_allLinesItem || !as.isPostServices() 
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), null, grossAmt);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), null, serviceAmt);
		}
		
		//  ** API
		else if (getDocumentType().equals(DOCTYPE_APInvoice))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			//  Charge          DR
			fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
				getC_Currency_ID(), getAmount(Doc.AMTTYPE_Charge), null);
			//  TaxCredit       DR
			for (int i = 0; i < m_taxes.length; i++)
			{
				FactLine tl = fact.createLine(null, m_taxes[i].getAccount(m_taxes[i].getAPTaxType(), as),
					getC_Currency_ID(), m_taxes[i].getAmount(), null);
				if (tl != null)
					tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
			}
			//  Expense         DR
			for (int i = 0; i < p_lines.length; i++)
			{
				DocLine line = p_lines[i];
				boolean landedCost = landedCost(as, fact, line, true);
				if (landedCost && as.isExplicitCostAdjustment())
				{
					fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), line.getAmtSource(), null);
					//
					FactLine fl = fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), null, line.getAmtSource());
					String desc = line.getDescription();
					if (desc == null)
						desc = "100%";
					else
						desc += " 100%";
					fl.setDescription(desc);
				}
				if (!landedCost)
				{
					MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
					if (line.isItem())
						expense = line.getAccount (ProductCost.ACCTTYPE_P_InventoryClearing, as);
					BigDecimal amt = line.getAmtSource();
					BigDecimal dAmt = null;
					if (as.isTradeDiscountPosted() && !line.isItem())
					{
						BigDecimal discount = line.getDiscount();
						if (discount != null && discount.signum() != 0)
						{
							amt = amt.add(discount);
							dAmt = discount;
							MAccount tradeDiscountReceived = line.getAccount(ProductCost.ACCTTYPE_P_TDiscountRec, as);
							fact.createLine (line, tradeDiscountReceived,
									getC_Currency_ID(), null, dAmt);
						}
					}
					fact.createLine (line, expense,
						getC_Currency_ID(), amt, null);
					if (!line.isItem())
					{
						grossAmt = grossAmt.subtract(amt);
						serviceAmt = serviceAmt.add(amt);
					}
					//
					if (line.getM_Product_ID() != 0
						&& line.getProduct().isService())	//	otherwise Inv Matching
						MCostDetail.createInvoice(as, line.getAD_Org_ID(), 
							line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
							line.get_ID(), 0,		//	No Cost Element
							line.getAmtSource(), line.getQty(),
							line.getDescription(), getTrxName());
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  //  from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    //  to Loc
				}
			}

			//  Liability               CR
			int payables_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Liability, as);
			int payablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Liability_Services, as);
			if (m_allLinesItem || !as.isPostServices() 
				|| payables_ID == payablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payables_ID),
					getC_Currency_ID(), null, grossAmt);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID),
					getC_Currency_ID(), null, serviceAmt);
			//
			updateProductPO(as);	//	Only API
			updateProductInfo (as.getC_AcctSchema_ID());    //  only API
		}
		//  APC
		else if (getDocumentType().equals(DOCTYPE_APCredit))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			//  Charge                  CR
			fact.createLine (null, getAccount(Doc.ACCTTYPE_Charge, as),
				getC_Currency_ID(), null, getAmount(Doc.AMTTYPE_Charge));
			//  TaxCredit               CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				FactLine tl = fact.createLine (null, m_taxes[i].getAccount(m_taxes[i].getAPTaxType(), as),
					getC_Currency_ID(), null, m_taxes[i].getAmount());
				if (tl != null)
					tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
			}
			//  Expense                 CR
			for (int i = 0; i < p_lines.length; i++)
			{
				DocLine line = p_lines[i];
				boolean landedCost = landedCost(as, fact, line, false);
				if (landedCost && as.isExplicitCostAdjustment())
				{
					fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), null, line.getAmtSource());
					//
					FactLine fl = fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), line.getAmtSource(), null);
					String desc = line.getDescription();
					if (desc == null)
						desc = "100%";
					else
						desc += " 100%";
					fl.setDescription(desc);
				}
				if (!landedCost)
				{
					MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
					if (line.isItem())
						expense = line.getAccount (ProductCost.ACCTTYPE_P_InventoryClearing, as);
					BigDecimal amt = line.getAmtSource();
					BigDecimal dAmt = null;
					if (as.isTradeDiscountPosted() && !line.isItem())
					{
						BigDecimal discount = line.getDiscount();
						if (discount != null && discount.signum() != 0)
						{
							amt = amt.add(discount);
							dAmt = discount;
							MAccount tradeDiscountReceived = line.getAccount(ProductCost.ACCTTYPE_P_TDiscountRec, as);
							fact.createLine (line, tradeDiscountReceived,
									getC_Currency_ID(), dAmt, null);
						}
					}
					fact.createLine (line, expense,
						getC_Currency_ID(), null, amt);
					if (!line.isItem())
					{
						grossAmt = grossAmt.subtract(amt);
						serviceAmt = serviceAmt.add(amt);
					}
					//
					if (line.getM_Product_ID() != 0
						&& line.getProduct().isService())	//	otherwise Inv Matching
						MCostDetail.createInvoice(as, line.getAD_Org_ID(), 
							line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
							line.get_ID(), 0,		//	No Cost Element
							line.getAmtSource().negate(), line.getQty(),
							line.getDescription(), getTrxName());
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  //  from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    //  to Loc
				}
			}
			//  Liability       DR
			int payables_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Liability, as);
			int payablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Liability_Services, as);
			if (m_allLinesItem || !as.isPostServices() 
				|| payables_ID == payablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payables_ID),
					getC_Currency_ID(), grossAmt, null);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID),
					getC_Currency_ID(), serviceAmt, null);
		}
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}
		//
		facts.add(fact);
		return facts;
		
		*/
		
	}   //  createFact
	
	/**
	 * 	Create Fact Cash Based (i.e. only revenue/expense)
	 *	@param as accounting schema
	 *	@param fact fact to add lines to
	 *	@param multiplier source amount multiplier
	 *	@return accounted amount
	 */
	public BigDecimal createFactCash (MAcctSchema as, Fact fact, BigDecimal multiplier)
	{
		boolean creditMemo = getDocumentType().equals(DOCTYPE_ARCredit)
			|| getDocumentType().equals(DOCTYPE_APCredit);
		boolean payables = getDocumentType().equals(DOCTYPE_APInvoice)
			|| getDocumentType().equals(DOCTYPE_APCredit);
		BigDecimal acctAmt = Env.ZERO;
		FactLine fl = null;
		//	Revenue/Cost
		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine line = p_lines[i];
			boolean landedCost = false;
			if  (payables)
				landedCost = landedCost(as, fact, line, false);
			if (landedCost && as.isExplicitCostAdjustment())
			{
				fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
					getC_Currency_ID(), null, line.getAmtSource());
				//
				fl = fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
					getC_Currency_ID(), line.getAmtSource(), null);
				String desc = line.getDescription();
				if (desc == null)
					desc = "100%";
				else
					desc += " 100%";
				fl.setDescription(desc);
			}
			if (!landedCost)
			{
				MAccount acct = line.getAccount(
					payables ? ProductCost.ACCTTYPE_P_Expense : ProductCost.ACCTTYPE_P_Revenue, as);
				if (payables)
				{
					//	if Fixed Asset
					if (line.isItem())
						acct = line.getAccount (ProductCost.ACCTTYPE_P_InventoryClearing, as);
				}
				BigDecimal amt = line.getAmtSource().multiply(multiplier);
				BigDecimal amt2 = null;
				if (creditMemo)
				{
					amt2 = amt;
					amt = null;
				}
				if (payables)	//	Vendor = DR
					fl = fact.createLine (line, acct,
						getC_Currency_ID(), amt, amt2);
				else			//	Customer = CR
					fl = fact.createLine (line, acct,
						getC_Currency_ID(), amt2, amt);
				if (fl != null)
					acctAmt = acctAmt.add(fl.getAcctBalance());
			}
		}
		//  Tax
		for (int i = 0; i < m_taxes.length; i++)
		{
			BigDecimal amt = m_taxes[i].getAmount();
			BigDecimal amt2 = null;
			if (creditMemo)
			{
				amt2 = amt;
				amt = null;
			}
			FactLine tl = null;
			if (payables)
				tl = fact.createLine (null, m_taxes[i].getAccount(m_taxes[i].getAPTaxType(), as),
					getC_Currency_ID(), amt, amt2);
			else
				tl = fact.createLine (null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
					getC_Currency_ID(), amt2, amt);
			if (tl != null)
				tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
		}
		//  Set Locations
		FactLine[] fLines = fact.getLines();
		for (int i = 0; i < fLines.length; i++)
		{
			if (fLines[i] != null)
			{
				if (payables)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  //  from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    //  to Loc
				}
				else
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);    //  from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				}
			}
		}
		return acctAmt;
	}	//	createFactCash
	
	
	/**
	 * 	Create Landed Cost accounting & Cost lines
	 *	@param as accounting schema
	 *	@param fact fact
	 *	@param line document line
	 *	@param dr DR entry (normal api)
	 *	@return true if landed costs were created
	 */
	private boolean landedCost (MAcctSchema as, Fact fact, DocLine line, boolean dr) 
	{
		int C_InvoiceLine_ID = line.get_ID();
		MLandedCostAllocation[] lcas = MLandedCostAllocation.getOfInvoiceLine(
			getCtx(), C_InvoiceLine_ID, getTrxName());
		if (lcas.length == 0)
			return false;
		
		//	Calculate Total Base
		double totalBase = 0;
		for (int i = 0; i < lcas.length; i++)
			totalBase += lcas[i].getBase().doubleValue();
		
		//	Create New
		MInvoiceLine il = new MInvoiceLine (getCtx(), C_InvoiceLine_ID, getTrxName());
		for (int i = 0; i < lcas.length; i++)
		{
			MLandedCostAllocation lca = lcas[i];
			if (lca.getBase().signum() == 0)
				continue;
			double percent = lca.getBase().doubleValue() / totalBase;
			String desc = il.getDescription();
			if (desc == null)
				desc = percent + "%";
			else
				desc += " - " + percent + "%";
			if (line.getDescription() != null)
				desc += " - " + line.getDescription(); 
			
			//	Accounting
			ProductCost pc = new ProductCost (Env.getCtx(), 
				lca.getM_Product_ID(), lca.getM_AttributeSetInstance_ID(), getTrxName());
			BigDecimal drAmt = null;
			BigDecimal crAmt = null;
			if (dr)
				drAmt = lca.getAmt();
			else
				crAmt = lca.getAmt();
			FactLine fl = fact.createLine (line, pc.getAccount(ProductCost.ACCTTYPE_P_CostAdjustment, as),
				getC_Currency_ID(), drAmt, crAmt);
			fl.setDescription(desc);
			fl.setM_Product_ID(lca.getM_Product_ID());
			
			//	Cost Detail - Convert to AcctCurrency
			BigDecimal allocationAmt =  lca.getAmt();
			if (getC_Currency_ID() != as.getC_Currency_ID())
				allocationAmt = MConversionRate.convert(getCtx(), allocationAmt, 
					getC_Currency_ID(), as.getC_Currency_ID(),
					getDateAcct(), getC_ConversionType_ID(), 
					getAD_Client_ID(), getAD_Org_ID());
			if (allocationAmt.scale() > as.getCostingPrecision())
				allocationAmt = allocationAmt.setScale(as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
			if (!dr)
				allocationAmt = allocationAmt.negate();
			// AZ Goodwill
			// use createInvoice to create/update non Material Cost Detail
			MCostDetail.createInvoice(as, lca.getAD_Org_ID(), 
					lca.getM_Product_ID(), lca.getM_AttributeSetInstance_ID(),
					C_InvoiceLine_ID, lca.getM_CostElement_ID(),
					allocationAmt, lca.getQty(),
					desc, getTrxName());
			// end AZ
		}
		
		log.config("Created #" + lcas.length);
		return true;
	}	//	landedCosts

	/**
	 * 	Update ProductPO PriceLastInv
	 *	@param as accounting schema
	 */
	private void updateProductPO (MAcctSchema as)
	{
		MClientInfo ci = MClientInfo.get(getCtx(), as.getAD_Client_ID());
		if (ci.getC_AcctSchema1_ID() != as.getC_AcctSchema_ID())
			return;
		
		StringBuffer sql = new StringBuffer (
			"UPDATE M_Product_PO po "
			+ "SET PriceLastInv = "
			//	select
			+ "(SELECT currencyConvert(il.PriceActual,i.C_Currency_ID,po.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) "
			+ "FROM C_Invoice i, C_InvoiceLine il "
			+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
			+ " AND po.M_Product_ID=il.M_Product_ID AND po.C_BPartner_ID=i.C_BPartner_ID");
			//jz + " AND ROWNUM=1 AND i.C_Invoice_ID=").append(get_ID()).append(") ")
			if (DB.isOracle()) //jz
			{
				sql.append(" AND ROWNUM=1 ");
			}
			else 
			{
				sql.append(" AND il.C_InvoiceLine_ID = (SELECT MIN(il1.C_InvoiceLine_ID) "
						+ "FROM C_Invoice i1, C_InvoiceLine il1 "
						+ "WHERE i1.C_Invoice_ID=il1.C_Invoice_ID"
						+ " AND po.M_Product_ID=il1.M_Product_ID AND po.C_BPartner_ID=i1.C_BPartner_ID")
						.append("  AND i1.C_Invoice_ID=").append(get_ID()).append(") ");
			}
			sql.append("  AND i.C_Invoice_ID=").append(get_ID()).append(") ")
			//	update
			.append("WHERE EXISTS (SELECT * "
			+ "FROM C_Invoice i, C_InvoiceLine il "
			+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
			+ " AND po.M_Product_ID=il.M_Product_ID AND po.C_BPartner_ID=i.C_BPartner_ID"
			+ " AND i.C_Invoice_ID=").append(get_ID()).append(")");
		int no = DB.executeUpdate(sql.toString(), getTrxName());
		log.fine("Updated=" + no);
	}	//	updateProductPO
	
	/**
	 *  Update Product Info (old).
	 *  - Costing (PriceLastInv)
	 *  - PO (PriceLastInv)
	 *  @param C_AcctSchema_ID accounting schema
	 *  @deprecated old costing
	 */
	private void updateProductInfo (int C_AcctSchema_ID)
	{
		log.fine("C_Invoice_ID=" + get_ID());

		/** @todo Last.. would need to compare document/last updated date
		 *  would need to maintain LastPriceUpdateDate on _PO and _Costing */

		//  update Product Costing
		//  requires existence of currency conversion !!
		//  if there are multiple lines of the same product last price uses first
		//	-> TotalInvAmt is sometimes NULL !! -> error
		// begin globalqss 2005-10-19
		// postgresql doesn't support LIMIT on UPDATE or DELETE statements
		/*
		StringBuffer sql = new StringBuffer (
			"UPDATE M_Product_Costing pc "
			+ "SET (PriceLastInv, TotalInvAmt,TotalInvQty) = "
			//	select
			+ "(SELECT currencyConvert(il.PriceActual,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID),"
			+ " currencyConvert(il.LineNetAmt,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID),il.QtyInvoiced "
			+ "FROM C_Invoice i, C_InvoiceLine il, C_AcctSchema a "
			+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
			+ " AND pc.M_Product_ID=il.M_Product_ID AND pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
			+ " AND ROWNUM=1"
			+ " AND pc.C_AcctSchema_ID=").append(C_AcctSchema_ID).append(" AND i.C_Invoice_ID=")
			.append(get_ID()).append(") ")
			//	update
			.append("WHERE EXISTS (SELECT * "
			+ "FROM C_Invoice i, C_InvoiceLine il, C_AcctSchema a "
			+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
			+ " AND pc.M_Product_ID=il.M_Product_ID AND pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
			+ " AND pc.C_AcctSchema_ID=").append(C_AcctSchema_ID).append(" AND i.C_Invoice_ID=")
				.append(get_ID()).append(")");
		*/
		// the next command is equivalent and works in postgresql and oracle
		StringBuffer sql = new StringBuffer (
				"UPDATE M_Product_Costing pc "
				+ "SET (PriceLastInv, TotalInvAmt,TotalInvQty) = "
				//	select
				+ "(SELECT currencyConvert(il.PriceActual,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID),"
				+ " currencyConvert(il.LineNetAmt,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID),il.QtyInvoiced "
				+ "FROM C_Invoice i, C_InvoiceLine il, C_AcctSchema a "
				+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
				+ " AND il.c_invoiceline_id = (SELECT MIN(C_InvoiceLine_ID) FROM C_InvoiceLine il2" +
						" WHERE  il2.M_PRODUCT_ID=il.M_PRODUCT_ID AND C_Invoice_ID=")
				.append(get_ID()).append(")"
				+ " AND pc.M_Product_ID=il.M_Product_ID AND pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
				+ " AND pc.C_AcctSchema_ID=").append(C_AcctSchema_ID).append(" AND i.C_Invoice_ID=")
				.append(get_ID()).append(") ")
				//	update
				.append("WHERE EXISTS (SELECT * "
				+ "FROM C_Invoice i, C_InvoiceLine il, C_AcctSchema a "
				+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
				+ " AND pc.M_Product_ID=il.M_Product_ID AND pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
				+ " AND pc.C_AcctSchema_ID=").append(C_AcctSchema_ID).append(" AND i.C_Invoice_ID=")
					.append(get_ID()).append(")");
		// end globalqss 2005-10-19
		int no = DB.executeUpdate(sql.toString(), getTrxName());
		log.fine("M_Product_Costing - Updated=" + no);
	}   //  updateProductInfo


	/**
	 * OpenUp.	
	 * Descripcion : Creacion de asientos contables para invoices.
	 * @param as
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 04/11/2010
	 */
	private ArrayList<Fact> crearAsientosContables(MAcctSchema as) {
		
		ArrayList<Fact> facts = new ArrayList<Fact>();

		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		int headervalidcombinationfact = 0;

		//  Cash based accounting
		if (!as.isAccrual())
			return facts;
		
		MInvoice invoiceHdr = (MInvoice)this.p_po;
		
		// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
		// Obtengo esquema para saber cual es la moneda nacional.
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		// Fin Issue #539

		// OpenUp. Gabriel Vila. 20/05/2014. Issue #1633
		// Contabilizacion de facturas flete en modulo de transporte.
		boolean isTransporte = MSysConfig.getBooleanValue("UY_IS_MODULO_TRANSPORTE", false, getAD_Client_ID());
		boolean isProductoFlete = false;
		// Fin OpenUp. Issue #1633.

		
		//  ** ARI, ARF
		if (getDocumentType().equals(DOCTYPE_ARInvoice) 
			|| getDocumentType().equals(DOCTYPE_ARProForma))
		{
			// OpenUp. Gabriel Vila. 10/09/2014. Issue #1405.
			// Contabilizacion de factura de flete en transporte para un expediente en representacion.
			// Cuando esto sucede el asiento cambia totalmente
			if (isTransporte){
				if (invoiceHdr.get_ValueAsInt("UY_TR_Trip_ID") > 0){
					MTRTrip expediente = new MTRTrip(getCtx(), invoiceHdr.get_ValueAsInt("UY_TR_Trip_ID"), null);
					if (expediente.isRepresentation()){
						return this.crearAsientosTransRepresentacion(as, client, invoiceHdr, expediente);
					}
				}
				else{
					// Para facturas de vale flete en modulo de transporte hay una contabilizacion especial
					MDocType doc = new MDocType(getCtx(), invoiceHdr.getC_DocTypeTarget_ID(), null);
					if (doc.getValue() != null){
						if (doc.getValue().equalsIgnoreCase("inv_valeflete")){
							return this.crearAsientosValeFlete(as, client, invoiceHdr);
						}
					}
				}
			}
			// Fin OpenUp. Issue #1405
			
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			
			//  Header Charge           CR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0){
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),	
					getC_Currency_ID(), null, amt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			}
				
			//  TaxDue                  CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getAmount();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
						getC_Currency_ID(), null, amt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
					if (tl != null){
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
					}
				}
			}

			
			// OpenUp Nicolas Sarlabos 05/10/2012. Issue #
			//En algunas empresas necesito tomar la cuenta de ventas desde el cliente y no desde el producto
			boolean cuentaVentaFromPartner = MSysConfig.getBooleanValue("UY_INVACCT_CTAVENTA_PARTNER", false, getAD_Client_ID());
			//Fin OpenUp Nicolas Sarlabos 05/10/2012
			
			// OpenUp. Gabriel Vila. 02/05/2012. Issue #728.
			// Para bajar cantidad de registros que se hace al contabilizar una invoice,
			// sumarizo importes de lineas de invoices segun cuenta contable de sus productos.
			HashMap<Integer, BigDecimal> hashLineAccts = new HashMap<Integer, BigDecimal>();
			HashMap<Integer, BigDecimal> hashLineDiscountAccts = new HashMap<Integer, BigDecimal>();
			for (int i = 0; i < p_lines.length; i++){
				
				// OpenUp Nicolas Sarlabos 05/10/2012. Issue #
				// Se realiza cambio para obtener la cuenta contable de ventas desde el cliente y no desde el producto
				MAccount acct = null, acct2 = null;
				int validCombID = 0;
				if (cuentaVentaFromPartner){
					validCombID = this.getInvoiceValidCombination(this.partnerID, this.currencyID, true, Doc.ACCTTYPE_C_Receivable_Services, as);
					acct = MAccount.get(getCtx(), validCombID);					
				}
				else{
					// OpenUp. Gabriel Vila. 20/05/2014. Issue #1633.
					// Si no es modulo de transporte proceso como siempre, si es modulo de transporte tengo que obtener cuentas para venta de flete.
					if (!isTransporte){
						acct = p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as);	
					}
					else{
						MProduct prod = new MProduct(getCtx(),  p_lines[i].getM_Product_ID(), null);
						if (prod.get_ValueAsBoolean("IsFleteTR")){
							// Es un producto flete, obtengo dos cuentas una para nacional y otro para extranjero, son opcionales y por lo tanto
							// me alcanza con tener una sola cuenta.
							isProductoFlete = true;
							acct = prod.getAccount("P_TR_FleteNacional_Acct", as);
							acct2 = prod.getAccount("P_TR_FleteInter_Acct", as);
						}
						else{
							// Si no es un producto flete, proceso igual que siempre
							acct = p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as);
						}
					}
					// Fin OpenUp. Issue #1633.
					
				}
				//Fin OpenUp Nicolas Sarlabos 05/10/2012
				
				
				BigDecimal amtLine = p_lines[i].getAmtSource();
				BigDecimal amt2Line = Env.ZERO;
				
				// OpenUp. Gabriel Vila. 20/05/2014. Issue #1633.
				// Si estoy en modulo de transporte y es un producto flete
				if (isTransporte && isProductoFlete){
					// Los importes del flete se dividen en dos cuentas: una para territorio nacional y otro para extranjero 
					MInvoiceLine line = (MInvoiceLine)p_lines[i].p_po;
					if (line.get_Value("NationalAmt") != null){
						amtLine = (BigDecimal)line.get_Value("NationalAmt");	
					}
					if (line.get_Value("InternationalAmt") != null){
						amt2Line = (BigDecimal)line.get_Value("InternationalAmt");	
					}
					
					if ((amtLine.compareTo(Env.ZERO) == 0) && (amt2Line.compareTo(Env.ZERO) == 0)){
						amtLine = p_lines[i].getAmtSource();
					}
				}
				
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amtLine = amtLine.add(discount);
						dAmt = discount;
						MAccount acctDiscount = null;
						//OpenUp Nicolas Sarlabos 5/10/2012 Issue #
						if (cuentaVentaFromPartner){
							acctDiscount = MAccount.get(getCtx(), validCombID);
						}else {
							acctDiscount = p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as); 
						}
						//Fin OpenUp Nicolas Sarlabos 5/10/2012 						
						if (hashLineDiscountAccts.containsKey((Integer)acctDiscount.get_ID())){
							hashLineDiscountAccts.put((Integer)acctDiscount.get_ID(), hashLineDiscountAccts.get((Integer)acctDiscount.get_ID()).add(dAmt));
						}
						else{
							hashLineDiscountAccts.put((Integer)acctDiscount.get_ID(), dAmt);
						}
					}
				}
				
				if (hashLineAccts.containsKey((Integer)acct.get_ID())){
					hashLineAccts.put((Integer)acct.get_ID(), hashLineAccts.get((Integer)acct.get_ID()).add(amtLine));
				}
				else{
					hashLineAccts.put((Integer)acct.get_ID(), amtLine);
				}
				
				// OpenUp. Gabriel Vila. 20/05/2014. Issue #1633.
				// Por temas de transporte ahora puedo tener dos cuentas por linea de factura.
				// Me sirve por si necesito dos lineas en cualquier momento.
				if (acct2 != null){
					if (hashLineAccts.containsKey((Integer)acct2.get_ID())){
						hashLineAccts.put((Integer)acct2.get_ID(), hashLineAccts.get((Integer)acct2.get_ID()).add(amt2Line));
					}
					else{
						hashLineAccts.put((Integer)acct2.get_ID(), amt2Line);
					}
				}
				// Fin OpenUp. Issue #1633.

				
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amtLine);
					serviceAmt = serviceAmt.add(amtLine);
				}
			}		
			
			// Contabilizo cuentas de productos
			Map<Integer, BigDecimal> map = hashLineAccts; 
			for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) { 
				MAccount acct = new MAccount(getCtx(), entry.getKey().intValue(), getTrxName());
				fact.createLine (null, acct, getC_Currency_ID(), null, entry.getValue(), 
						invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			}		

			// Contabilizo cuentas de descuentos de productos
			Map<Integer, BigDecimal> mapDisc = hashLineDiscountAccts; 
			for (Map.Entry<Integer, BigDecimal> entry : mapDisc.entrySet()) { 
				MAccount acct = new MAccount(getCtx(), entry.getKey().intValue(), getTrxName());
				fact.createLine (null, acct, getC_Currency_ID(), entry.getValue(), null,
						invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			}		
			// Fin OpenUp.
			
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      //  from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				}
			}
			
			//  Receivables     DR
			int receivables_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, true, Doc.ACCTTYPE_C_Receivable, as);
			int receivablesServices_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, true, Doc.ACCTTYPE_C_Receivable_Services, as);
			
			
			// OpenUp. Gabriel Vila. Si viene por facturacion batch, tomo cuenta de otro lado
			if (this.invoice != null){
				if (this.invoice.get_ValueAsInt("UY_CFE_InboxFileType_ID") > 0){
					MCFEInboxFileType fileType = new MCFEInboxFileType(getCtx(), this.invoice.get_ValueAsInt("UY_CFE_InboxFileType_ID"), null);
					receivables_ID = fileType.getAccount_Acct();
				}
			}
			// Fin OpenUp.
			
			if (m_allLinesItem || !as.isPostServices() 
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			
			if (grossAmt.signum() != 0){
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID), getC_Currency_ID(), grossAmt, null,
					invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			}

			if (serviceAmt.signum() != 0){
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID), getC_Currency_ID(), serviceAmt, null,
					invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());						
			}

			// OpenUp. Gabriel Vila. 15/03/2013. Issue #556
			// Para pequeños desfasajes aplico ajuste automatico.
			fact.createRounding(invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo(), 0, 0, 0, 0);
			// Fin Issue #556
		}
		//  ARC
		else if (getDocumentType().equals(DOCTYPE_ARCredit))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			//  Header Charge   DR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0)
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), amt, null,
					invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			//  TaxDue          DR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getAmount();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
						getC_Currency_ID(), amt, null,
						invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());

					if (tl != null)
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
				}
			}
			
			// OpenUp Nicolas Sarlabos 05/10/2012. Issue #
			//En algunas empresas necesito tomar la cuenta de ventas desde el cliente y no desde el producto
			boolean cuentaVentaFromPartner = MSysConfig.getBooleanValue("UY_INVACCT_CTAVENTA_PARTNER", false, getAD_Client_ID());
			//Fin OpenUp Nicolas Sarlabos 05/10/2012
						
			// OpenUp. Gabriel Vila. 02/05/2012. Issue #728.
			// Para bajar cantidad de registros que se hace al contabilizar una invoice,
			// sumarizo importes de lineas de invoices segun cuenta contable de sus productos.
			HashMap<Integer, BigDecimal> hashLineAccts = new HashMap<Integer, BigDecimal>();
			HashMap<Integer, BigDecimal> hashLineDiscountAccts = new HashMap<Integer, BigDecimal>();
			for (int i = 0; i < p_lines.length; i++){
				
				// OpenUp Nicolas Sarlabos 05/10/2012. Issue #
				// Se realiza cambio para obtener la cuenta contable de ventas desde el cliente y no desde el producto
				MAccount acct = null;
				int validCombID = 0;
				if (cuentaVentaFromPartner){
					validCombID = this.getInvoiceValidCombination(this.partnerID, this.currencyID, true, Doc.ACCTTYPE_C_Receivable_Services, as);
					acct = MAccount.get(getCtx(), validCombID);					
				}
				else{
					acct = p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as);
				}
				//Fin OpenUp Nicolas Sarlabos 05/10/2012
				BigDecimal amtLine = p_lines[i].getAmtSource();
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amtLine = amtLine.add(discount);
						dAmt = discount;
						// OpenUp Nicolas Sarlabos 05/10/2012. Issue #
						MAccount acctDiscount = null;
						if (cuentaVentaFromPartner){
							acctDiscount = MAccount.get(getCtx(), validCombID);
						}else {
							acctDiscount = p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as); 
						}
						// Fin OpenUp Nicolas Sarlabos 05/10/2012
						if (hashLineDiscountAccts.containsKey((Integer)acctDiscount.get_ID())){
							hashLineDiscountAccts.put((Integer)acctDiscount.get_ID(), hashLineDiscountAccts.get((Integer)acctDiscount.get_ID()).add(dAmt));
						}
						else{
							hashLineDiscountAccts.put((Integer)acctDiscount.get_ID(), dAmt);
						}
					}
				}
				if (hashLineAccts.containsKey((Integer)acct.get_ID())){
					hashLineAccts.put((Integer)acct.get_ID(), hashLineAccts.get((Integer)acct.get_ID()).add(amtLine));
				}
				else{
					hashLineAccts.put((Integer)acct.get_ID(), amtLine);
				}
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amtLine);
					serviceAmt = serviceAmt.add(amtLine);
				}
			}		
			
			// Contabilizo cuentas de productos
			Map<Integer, BigDecimal> map = hashLineAccts; 
			for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) { 
				MAccount acct = new MAccount(getCtx(), entry.getKey().intValue(), getTrxName());
				fact.createLine(null, acct, getC_Currency_ID(), entry.getValue(), null,
					invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());				
			}		

			// Contabilizo cuentas de descuentos de productos
			Map<Integer, BigDecimal> mapDisc = hashLineDiscountAccts; 
			for (Map.Entry<Integer, BigDecimal> entry : mapDisc.entrySet()) { 
				MAccount acct = new MAccount(getCtx(), entry.getKey().intValue(), getTrxName());
				fact.createLine (null, acct, getC_Currency_ID(), null, entry.getValue(),
					invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());						
			}		
			// Fin OpenUp.

			
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      //  from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				}
			}
			//  Receivables             CR

			int receivables_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, true, Doc.ACCTTYPE_C_Receivable, as);
			int receivablesServices_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, true, Doc.ACCTTYPE_C_Receivable_Services, as);
			
			if (m_allLinesItem || !as.isPostServices() 
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), null, grossAmt, invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), null, serviceAmt, invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());

			// OpenUp. Gabriel Vila. 15/03/2013. Issue #556
			// Para pequeños desfasajes aplico ajuste automatico.
			fact.createRounding(invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo(), 0, 0, 0, 0);
			// Fin Issue #556
		
		}
		
		//  ** API
		else if (getDocumentType().equals(DOCTYPE_APInvoice))
		{
			MDocType doc = new MDocType(getCtx(), invoiceHdr.getC_DocTypeTarget_ID(), null);
			
			// OpenUp. Gabriel Vila. 10/09/2014. Issue #3205
			// Contabilizacion de factura vale flete en transporte
			// Cuando esto sucede el asiento cambia totalmente
			if (isTransporte){
				// Para facturas de vale flete en modulo de transporte hay una contabilizacion especial
				//MDocType doc = new MDocType(getCtx(), invoiceHdr.getC_DocTypeTarget_ID(), null);
				if (doc.getValue() != null){
					if (doc.getValue().equalsIgnoreCase("inv_valeflete")){
						return this.crearAsientosValeFlete(as, client, invoiceHdr);
					}
					else if (doc.getValue().equalsIgnoreCase("valeflete")){
						return this.crearAsientosValeFlete(as, client, invoiceHdr);
					}
				}
			}
			// Fin OpenUp. Issue #1405
			
			
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			//  Charge          DR
			FactLine flcharge = fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
				getC_Currency_ID(), getAmount(Doc.AMTTYPE_Charge), null, invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());
			
			// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
			// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
			if (flcharge != null) {
				if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
					if (invoiceHdr.getCurrencyRate() != null){
						if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
							flcharge.setDivideRate(invoiceHdr.getCurrencyRate());
							flcharge.setAmtAcctDr(flcharge.getAmtSourceDr().multiply(flcharge.getDivideRate()));
							flcharge.setAmtAcctCr(flcharge.getAmtSourceCr().multiply(flcharge.getDivideRate()));
							flcharge.saveEx();
						}
					}
				}
			}
			// Fin Issue #539.
			
			//  TaxCredit       DR
			for (int i = 0; i < m_taxes.length; i++)
			{
				FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxCredit , as),
						getC_Currency_ID(), m_taxes[i].getAmount(), null, invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());
				
				// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
				// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
				if (tl != null) {
					if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
						if (invoiceHdr.getCurrencyRate() != null){
							if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
								tl.setDivideRate(invoiceHdr.getCurrencyRate());
								tl.setAmtAcctDr(tl.getAmtSourceDr().multiply(tl.getDivideRate()));
								tl.setAmtAcctCr(tl.getAmtSourceCr().multiply(tl.getDivideRate()));
							}
						}
					}
				}
				// Fin Issue #539.
				
				if (tl != null)
					tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
			}
			//  Expense         DR
			
			// OpenUp. Gabriel Vila. 12/10/2012. Issue #49.
			// Gestion de Provision de gastos. Antes de recorre las lineas
			// obtengo las cuentas de provision en moneda nacional y extranjera.
			MAccount cuentaProvisionMN = MAccount.get(getCtx(), as.getAcctSchemaDefault().getUnrealizedGain_Acct());
			MAccount cuentaProvisionME = MAccount.get(getCtx(), as.getAcctSchemaDefault().getUnrealizedLoss_Acct());
			// Fin OpenUp.
			
			
			// OpenUp. Gabriel Vila. 24/10/2012. Issue #82
			// Gestion de Devengamientos
			MAccount cuentaDevengamientoMN = MAccount.get(getCtx(), as.getAcctSchemaDefault().getRealizedGain_Acct());
			MAccount cuentaDevengamientoME = MAccount.get(getCtx(), as.getAcctSchemaDefault().getRealizedLoss_Acct());

			BigDecimal amtDevengar = Env.ZERO;
			// Fin OpenUp. Issue #82.
			
			FactLine fl = null, flprov = null;
			
			// Recorre Lineas del Comprobante
			for (int i = 0; i < p_lines.length; i++)
			{
				DocLine line = p_lines[i];
				boolean landedCost = landedCost(as, fact, line, true);
				if (landedCost && as.isExplicitCostAdjustment())
				{
					fl = fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), line.getAmtSource(), null, invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());
					// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
					// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
					if (fl != null) {
						if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
							if (invoiceHdr.getCurrencyRate() != null){
								if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
									fl.setDivideRate(invoiceHdr.getCurrencyRate());
									fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
									fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
								}
							}
						}
					}
					// Fin Issue #539.
					
					fl = fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), null, line.getAmtSource(), invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());
					// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
					// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
					if (fl != null) {
						if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
							if (invoiceHdr.getCurrencyRate() != null){
								if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
									fl.setDivideRate(invoiceHdr.getCurrencyRate());
									fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
									fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
								}
							}
						}
					}
					// Fin Issue #539.

					
					String desc = line.getDescription();
					if (desc == null)
						desc = "100%";
					else
						desc += " 100%";
					fl.setDescription(desc);					
					
				}
				if (!landedCost)
				{
					MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
					if (line.isItem())
						expense = line.getAccount (ProductCost.ACCTTYPE_P_InventoryClearing, as);

					// OpenUp. Nicolas Sarlabos. 29/02/2016. Issue #3503.
					if(MSysConfig.getBooleanValue("UY_PO_INVOICE_TAX_ACCT", false, getAD_Client_ID())){

						MTax tax = new MTax(getCtx(), line.getC_Tax_ID(), getTrxName());

						//obtengo cuenta contable desde contabilidad del impuesto
						int acctID = DB.getSQLValueEx(getTrxName(), "select t_expense_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

						if (acctID > 0){

							expense = MAccount.get(getCtx(), acctID);

						} else throw new AdempiereException ("No se obtuvo cuenta contable 'Producto Impuesto Comprado' para tasa de impuesto '" + tax.getName() + "'");						
					}				
					//Fin OpenUp.					
					
					// OpenUp. Gabriel Vila. 25/09/2014. Issue #1405. 
					// Contabilizacion de gastos en modulo de transporte
					// Tengo que tomar cuenta del producto para gastos del modulo de transporte					
					if (isTransporte){
						MProduct prod = new MProduct(getCtx(),  p_lines[i].getM_Product_ID(), null);
						if (prod.get_ValueAsBoolean("IsFleteTR")) {
							// Si tengo orden de transporte asociada a este documento de gasto
							if (invoiceHdr.get_ValueAsInt(X_UY_TR_TransOrder.COLUMNNAME_UY_TR_TransOrder_ID) > 0){
								
								// Obtengo modelo de orden de transporte
								MTRTransOrder ordtrans = new MTRTransOrder(getCtx(), invoiceHdr.get_ValueAsInt(X_UY_TR_TransOrder.COLUMNNAME_UY_TR_TransOrder_ID), null);
								
								// Obtengo lineas de orden de transporte
								List<MTRTransOrderLine> otlines = ordtrans.getLines();
								
								// En una orden todos los expedientes son del mismo tipo : importacion o exportacion o en transito.
								// Por lo tanto con tomar el primer expediente me alcanza para saber que tipo considerar para obtener la cuenta contable
								MTRTrip expediente = (MTRTrip)otlines.get(0).getUY_TR_Trip();

								// Cuenta item para compra si es expediente en transito
								if (expediente.isInTransit()){
									expense = line.getAccount (ProductCost.ACCTTYPE_P_TR_FleteTransito, as);
								}
								else{
									// Cuenta item para compra depende si es exportacion o importacion
									if (expediente.getTripType().equalsIgnoreCase(X_UY_TR_Trip.TRIPTYPE_IMPORTACION)){
										expense = line.getAccount (ProductCost.ACCTTYPE_P_TR_FleteImpNacional, as);									
									}
									else if (expediente.getTripType().equalsIgnoreCase(X_UY_TR_Trip.TRIPTYPE_EXPORTACION)){
										expense = line.getAccount (ProductCost.ACCTTYPE_P_TR_FleteExpNacional, as);
									}
								}
							}
							else{
								// Factura compra que no tiene orden de transporte y estoy en un producto flete para compra
								// Verifico si esta linea esta asociada a una linea de una orden de compra flete
								if (line.getC_OrderLine_ID() > 0){
									MOrderLine oLine = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), null);
									MOrder order = (MOrder)oLine.getC_Order();
									MDocType docOrder = (MDocType)order.getC_DocTypeTarget();
									if (docOrder.getValue() != null){
										if (docOrder.getValue().equalsIgnoreCase("poflete")){

											int accountID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_NotInvoicedReceipts, as);
											
											if (accountID <= 0){
												p_Error = "No se pudo obtener parametrizacion contable en Parametros Generales de Transporte";
												fact = null;
												facts.add(fact);
												return facts;
											}

											expense = MAccount.get(getCtx(), accountID);
										}
									}
								}
								
							}
						}
					}
					// Fin OpenUp. Issue #1405
					
					BigDecimal amt = line.getAmtSource();
					BigDecimal dAmt = null;
					if (as.isTradeDiscountPosted() && !line.isItem())
					{
						BigDecimal discount = line.getDiscount();
						if (discount != null && discount.signum() != 0)
						{
							amt = amt.add(discount);
							dAmt = discount;
							MAccount tradeDiscountReceived = line.getAccount(ProductCost.ACCTTYPE_P_TDiscountRec, as);
							FactLine fldis = fact.createLine (line, tradeDiscountReceived,
									getC_Currency_ID(), null, dAmt, invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());
							// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
							// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
							if (fldis != null) {
								if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
									if (invoiceHdr.getCurrencyRate() != null){
										if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
											fldis.setDivideRate(invoiceHdr.getCurrencyRate());
											fldis.setAmtAcctDr(fldis.getAmtSourceDr().multiply(fldis.getDivideRate()));
											fldis.setAmtAcctCr(fldis.getAmtSourceCr().multiply(fldis.getDivideRate()));
										}
									}
								}
							}
							// Fin Issue #539.
							
						}
					}
					
					MInvoiceLine invLine = (MInvoiceLine)p_lines[i].p_po;
					
					// OpenUp. Gabriel Vila. 26/12/2012. Issue #49.
					// Cambio toda la contabilizacion cuando hay manejo provisiones
					if (MSysConfig.getBooleanValue("UY_HANDLE_PROVISION", false, getAD_Client_ID())){
						// Con la primer linea de factura proceso todas las provisiones. En las demas lineas de factura no hago nada.
						if (i == 0){
							
							MAccount acctProvision = (invLine.getParent().getC_Currency_ID() == as.getC_Currency_ID()) ? cuentaProvisionMN : cuentaProvisionME;
											
							// Obtengo suma de lineas de factura agrupadas por producto y centro de costo
							List<InvoiceLineAmtByProdAct> prodactlines = invLine.getParent().getLinesSumByProdAct();
							for (InvoiceLineAmtByProdAct prodactline: prodactlines){
								// Para este producto - centro de costo, obtengo lineas de provision
								List<MInvoiceProvision> invprovs = invLine.getParent().getProvisionLinesByProdAct(prodactline.mProductID,prodactline.cActivityID);
								BigDecimal sumAmtAllocated = Env.ZERO;
								boolean tienelinea = false;
								for (MInvoiceProvision invprov: invprovs){
									tienelinea = true;
									if (invprov.getamtallocated().compareTo(Env.ZERO) != 0){
										
										flprov = fact.createLine (null, acctProvision, getC_Currency_ID(), invprov.getamtallocated(), null,
												invoiceHdr.getC_DocTypeTarget_ID(),invoiceHdr.getDocumentNo());
										// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
										// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
										if (flprov != null) {
											if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
												if (invoiceHdr.getCurrencyRate() != null){
													if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
														flprov.setDivideRate(invoiceHdr.getCurrencyRate());
														flprov.setAmtAcctDr(flprov.getAmtSourceDr().multiply(flprov.getDivideRate()));
														flprov.setAmtAcctCr(flprov.getAmtSourceCr().multiply(flprov.getDivideRate()));
													}
												}
											}
										}
										// Fin Issue #539.
								
										
										if (prodactline.cActivityID > 0){
											flprov.setC_Activity_ID_1(prodactline.cActivityID);
										}										
										if (prodactline.mProductID > 0){
											flprov.setM_Product_ID(prodactline.mProductID);
											flprov.setQty(prodactline.sumQty);
										}										
										flprov.setC_BPartner_ID(invLine.getParent().getC_BPartner_ID());
										flprov.saveEx();
										
										sumAmtAllocated = sumAmtAllocated.add(invprov.getamtallocated());
									}
								}
								
								MProduct prod = new MProduct(getCtx(), prodactline.mProductID, null);
								X_M_Product_Acct prodAcct = prod.getProductAccounting();
								
								if (prodAcct == null){
									p_Error = "No se pudo obtener cuenta compras para el producto : " + prod.getName();
									fact = null;
									facts.add(fact);
									return facts;
								}
								
								MAccount acctProd = MAccount.get(getCtx(), prodAcct.getP_InventoryClearing_Acct());
								
								// Finalmente al debito la diferencia que me pueda quedar a favor de la cuenta del articulo
								if (tienelinea){
									if (prodactline.sumAmt.compareTo(sumAmtAllocated) != 0){
										
										//OpenUp. Nicolas Sarlabos. 16/09/2013. #460.
										BigDecimal qtyQuotes = new BigDecimal(invLine.getParent().getQtyQuote()); //obtengo cantidad de cuotas
										//si el periodo de la fecha de transaccion es mayor al de inicio del devengamiento y cantidad de cutoas es mayor a cero 
										if ((MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() > invLine.getParent().getC_Period_ID_From()) && qtyQuotes.compareTo(Env.ZERO)>0){
											//obtengo cantidad de periodos desde el inicial hasta el de la fecha de transaccion inclusive
											int qtyPeriods = (MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() - invLine.getParent().getC_Period_ID_From()) + 1;
																																
											BigDecimal amount = prodactline.sumAmt.divide(qtyQuotes,2,RoundingMode.HALF_UP);
											amount = amount.multiply(new BigDecimal(qtyPeriods)).setScale(2, RoundingMode.HALF_UP);
											amount = amount.subtract(sumAmtAllocated);
											
											flprov = fact.createLine (null, acctProd, getC_Currency_ID(), amount, null,
													invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
											
										} else flprov = fact.createLine (null, acctProd, getC_Currency_ID(), prodactline.sumAmt.subtract(sumAmtAllocated), null,
												invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
										//Fin OpenUp. #460.
										// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
										// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
										if (flprov != null) {
											if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
												if (invoiceHdr.getCurrencyRate() != null){
													if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
														flprov.setDivideRate(invoiceHdr.getCurrencyRate());
														flprov.setAmtAcctDr(flprov.getAmtSourceDr().multiply(flprov.getDivideRate()));
														flprov.setAmtAcctCr(flprov.getAmtSourceCr().multiply(flprov.getDivideRate()));
													}
												}
											}
										}
										// Fin Issue #539.
										
										if (prodactline.cActivityID > 0){
											flprov.setC_Activity_ID_1(prodactline.cActivityID);
										}										
										if (prodactline.mProductID > 0){
											flprov.setM_Product_ID(prodactline.mProductID);
											flprov.setQty(prodactline.sumQty);
										}										
										flprov.setC_BPartner_ID(invLine.getParent().getC_BPartner_ID());
										flprov.saveEx();
									}
								}
								else{
									
									if (!invLine.getParent().isDevengable()){
										flprov = fact.createLine (null, acctProd, getC_Currency_ID(), prodactline.sumAmt, null,
												invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
										// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
										// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
										if (flprov != null) {
											if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
												if (invoiceHdr.getCurrencyRate() != null){
													if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
														flprov.setDivideRate(invoiceHdr.getCurrencyRate());
														flprov.setAmtAcctDr(flprov.getAmtSourceDr().multiply(flprov.getDivideRate()));
														flprov.setAmtAcctCr(flprov.getAmtSourceCr().multiply(flprov.getDivideRate()));
													}
												}
											}
										}
										// Fin Issue #539.

										if (prodactline.cActivityID > 0){
											flprov.setC_Activity_ID_1(prodactline.cActivityID);
										}										
										if (prodactline.mProductID > 0){
											flprov.setM_Product_ID(prodactline.mProductID);
											flprov.setQty(prodactline.sumQty);
										}										
										flprov.setC_BPartner_ID(invLine.getParent().getC_BPartner_ID());
										flprov.saveEx();
									}
								}
							}
						}
					}

					// En devengamientos debo dividir la diferencia por la cantidad de cuotas
					if (invLine.getParent().isDevengable()){
						// Si la fecha de contabilizacion de este comprobante pertenece a un periodo igual o mayor al periodo inicial del devengamiento
						if (MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() >= invLine.getParent().getC_Period_ID_From()){
							// Si tengo cantidad de cuotas
							if (invLine.getParent().getQtyQuote() > 1){
								amt = amt.divide(new BigDecimal(invLine.getParent().getQtyQuote()), 2, RoundingMode.HALF_UP);
								// Acumulo importe que se va devengando
								amtDevengar = amtDevengar.add(amt);
							}
							//OpenUp. Nicolas Sarlabos. 16/09/2013. #460.
							//si el periodo de la fecha de transaccion es menor o igual al periodo inicial de devengamiento creo la linea
							if (MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() <= invLine.getParent().getC_Period_ID_From()){
								
								fl = fact.createLine (line, expense, getC_Currency_ID(), amt, null,
										invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
							}
							//Fin OpenUp. #460.
	
							// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
							// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
							if (fl != null) {
								if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
									if (invoiceHdr.getCurrencyRate() != null){
										if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
											fl.setDivideRate(invoiceHdr.getCurrencyRate());
											fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
											fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
										}
									}
								}
							}
							// Fin Issue #539.
							
						}
						else{
							// DR
							MAccount acctDevengamiento = (getC_Currency_ID() == as.getC_Currency_ID()) ? cuentaDevengamientoMN : cuentaDevengamientoME;
							fl = fact.createLine (line, acctDevengamiento, getC_Currency_ID(), amt, null,
								invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
							// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
							// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
							if (fl != null) {
								if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
									if (invoiceHdr.getCurrencyRate() != null){
										if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
											fl.setDivideRate(invoiceHdr.getCurrencyRate());
											fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
											fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
										}
									}
								}
							}
							// Fin Issue #539.
							
						}
					}
					else{
						// No es devengable y no paso por provision
						if (flprov == null){
							fl = fact.createLine (line, expense, getC_Currency_ID(), amt, null,
								invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
							// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
							// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
							if (fl != null) {
								if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
									if (invoiceHdr.getCurrencyRate() != null){
										if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
											fl.setDivideRate(invoiceHdr.getCurrencyRate());
											fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
											fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
										}
									}
								}
							}
							// Fin Issue #539.							
						}
					}

					
					if (!line.isItem())
					{
						grossAmt = grossAmt.subtract(amt);
						serviceAmt = serviceAmt.add(amt);
					}
					
					// OpenUp. Gabriel Vila. 02/10/2012. Issue #32.
					// Contabilizar asiento con 3 categorias de centros de costo.
					if ((invLine != null) && (invLine.get_ID() > 0)){
						if (fl != null){
							if (invLine.getC_Activity_ID() > 0)
								fl.setC_Activity_ID(invLine.getC_Activity_ID());
							if (invLine.getC_Activity_ID_1() > 0)
								fl.setC_Activity_ID_1(invLine.getC_Activity_ID_1());
							if (invLine.getC_Activity_ID_2() > 0)
								fl.setC_Activity_ID_2(invLine.getC_Activity_ID_2());
							if (invLine.getC_Activity_ID_3() > 0)
								fl.setC_Activity_ID_3(invLine.getC_Activity_ID_3());
							fl.saveEx();
						}
					}
					// Fin OpenUp Issue #32.

					
					//
					if (line.getM_Product_ID() != 0
						&& line.getProduct().isService())	//	otherwise Inv Matching
						MCostDetail.createInvoice(as, line.getAD_Org_ID(), 
							line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
							line.get_ID(), 0,		//	No Cost Element
							line.getAmtSource(), line.getQty(),
							line.getDescription(), getTrxName());
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  //  from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    //  to Loc
				}
			}

			//  Liability               CR
			int payables_ID = 0;
			int payablesServices_ID = 0;		
			boolean isCash = false;

			if(doc.getValue()!=null){
				if(doc.getValue().equalsIgnoreCase("factprovsinoc")){//si es factura proveedor sin OC

					if(invoiceHdr.getpaymentruletype().equalsIgnoreCase("CO")){//si se pago CONTADO
						
						isCash = true;

						//obtengo linea de pago contado para la factura
						List<MInvoiceCashPayment> cashLines = MInvoiceCashPayment.getLines(getCtx(), invoiceHdr.get_ID(), getTrxName());

						if(cashLines.size()<=0) throw new AdempiereException ("No se obtuvieron lineas de pago contado para este documento.");
						
						for(MInvoiceCashPayment cashLine : cashLines){
							
							MBankAccount account = (MBankAccount)cashLine.getC_BankAccount();
							
							//obtengo cuenta contable desde la cuenta bancaria
							payables_ID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from c_bankaccount_acct where c_bankaccount_id = " + account.get_ID());

							if(payables_ID <= 0) throw new AdempiereException ("No se obtuvo Cuenta de Bancos para la cuenta bancaria '" + account.getDescription() + "'");	
							
							if (m_allLinesItem || !as.isPostServices() 
									|| payables_ID == payablesServices_ID)
								{
									grossAmt = getAmount(Doc.AMTTYPE_Gross);
									serviceAmt = Env.ZERO;
								}
								else if (m_allLinesService)
								{
									serviceAmt = getAmount(Doc.AMTTYPE_Gross);
									grossAmt = Env.ZERO;
								}
								
								if (grossAmt.signum() != 0){
									if (amtDevengar.compareTo(Env.ZERO) > 0){
										// DR
										MAccount acctDevengamiento = (getC_Currency_ID() == as.getC_Currency_ID()) ? cuentaDevengamientoMN : cuentaDevengamientoME;
										BigDecimal totalLines = ((MInvoice)this.p_po).getTotalLines();
										//OpenUp. Nicolas Sarlabos. 16/09/2013. #460.
										FactLine fldev = null;
										MInvoiceLine invLine = (MInvoiceLine)p_lines[0].p_po;
										//si el preiodo de la fecha de transaccion es mayor al periodo inicial de devengamiento
										if (MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() > invLine.getParent().getC_Period_ID_From()){
											//obtengo cantidad de periodos entre el periodo final de devengamiento y el de la transaccion (no incluido)
											int qtyPeriods = (invLine.getParent().getC_Period_ID_To() - MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID());

											BigDecimal amount = amtDevengar.multiply(new BigDecimal(qtyPeriods)).setScale(2, RoundingMode.HALF_UP); //obtengo importe		

											fldev = fact.createLine(null, acctDevengamiento, getC_Currency_ID(), amount, null,
													invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());

										} else fldev = fact.createLine(null, acctDevengamiento, getC_Currency_ID(), totalLines.subtract(amtDevengar), null,
												invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
										//Fin OpenUp.
										// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
										// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
										if (fldev != null) {
											if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
												if (invoiceHdr.getCurrencyRate() != null){
													if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
														fldev.setDivideRate(invoiceHdr.getCurrencyRate());
														fldev.setAmtAcctDr(fldev.getAmtSourceDr().multiply(fldev.getDivideRate()));
														fldev.setAmtAcctCr(fldev.getAmtSourceCr().multiply(fldev.getDivideRate()));
													}
												}
											}
										}
										// Fin Issue #539.
										
									}
									// CR
									//OpenUp. Nicolas Sarlabos. 20/08/2013. #1232
									if(payables_ID <= 0) {
										FactLine.deleteFact(I_C_Invoice.Table_ID, invoiceHdr.get_ID(), getTrxName());
										throw new AdempiereException ("No es posible Contabilizar Documento ya que no se encuentra cuenta para socio de negocio y moneda.");
									}
									//Fin OpenUp. #1232
									FactLine flcred = fact.createLine(null, MAccount.get(getCtx(), payables_ID), invoiceHdr.getC_Currency_ID(), null, cashLine.getPayAmt(),
											invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
									// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
									// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
									if (flcred != null) {
										if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
											if (invoiceHdr.getCurrencyRate() != null){
												if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
													flcred.setDivideRate(invoiceHdr.getCurrencyRate());
													flcred.setAmtAcctDr(flcred.getAmtSourceDr().multiply(flcred.getDivideRate()));
													flcred.setAmtAcctCr(flcred.getAmtSourceCr().multiply(flcred.getDivideRate()));
												}
											}
										}
									}
									// Fin Issue #539.
									
								}	
								
								if (serviceAmt.signum() != 0){
									FactLine flserv = fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID),
											getC_Currency_ID(), null, serviceAmt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
									
									// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
									// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
									if (flserv != null) {
										if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
											if (invoiceHdr.getCurrencyRate() != null){
												if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
													flserv.setDivideRate(invoiceHdr.getCurrencyRate());
													flserv.setAmtAcctDr(flserv.getAmtSourceDr().multiply(flserv.getDivideRate()));
													flserv.setAmtAcctCr(flserv.getAmtSourceCr().multiply(flserv.getDivideRate()));
												}
											}
										}
									}
									// Fin Issue #539.

								}						
						}
						
					} else {

						payables_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability, as);
						payablesServices_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability_Services, as);

					}

				} else if (doc.getValue().equalsIgnoreCase("factgastoviaje")){//si es un documento de gasto de viaje
					
					//obtengo cuenta contable del esquema
					payables_ID = DB.getSQLValueEx(getTrxName(), "select p_expense_acct from c_acctschema_default");
					
					if(payables_ID <= 0) throw new AdempiereException ("No se obtuvo cuenta de Gasto de Viaje");

				} else {

					payables_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability, as);
					payablesServices_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability_Services, as);

				}

			} else {

				payables_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability, as);
				payablesServices_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability_Services, as);
			}			
			
			// OpenUp. Gabriel Vila. 29/09/2014. Issue #1584.
			// Si la cuenta COMPRAS del proveedor para la moneda del comprobante, no tiene datos EL SISTEMA DEBE IR A BUSCARLA EN EL GRUPO DE SOCIO DE NEGOCIO
					
			if(isCash == false){
				
				if (m_allLinesItem || !as.isPostServices() 
						|| payables_ID == payablesServices_ID)
					{
						grossAmt = getAmount(Doc.AMTTYPE_Gross);
						serviceAmt = Env.ZERO;
					}
					else if (m_allLinesService)
					{
						serviceAmt = getAmount(Doc.AMTTYPE_Gross);
						grossAmt = Env.ZERO;
					}
					
					if (grossAmt.signum() != 0){
						if (amtDevengar.compareTo(Env.ZERO) > 0){
							// DR
							MAccount acctDevengamiento = (getC_Currency_ID() == as.getC_Currency_ID()) ? cuentaDevengamientoMN : cuentaDevengamientoME;
							BigDecimal totalLines = ((MInvoice)this.p_po).getTotalLines();
							//OpenUp. Nicolas Sarlabos. 16/09/2013. #460.
							FactLine fldev = null;
							MInvoiceLine invLine = (MInvoiceLine)p_lines[0].p_po;
							//si el preiodo de la fecha de transaccion es mayor al periodo inicial de devengamiento
							if (MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() > invLine.getParent().getC_Period_ID_From()){
								//obtengo cantidad de periodos entre el periodo final de devengamiento y el de la transaccion (no incluido)
								int qtyPeriods = (invLine.getParent().getC_Period_ID_To() - MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID());

								BigDecimal amount = amtDevengar.multiply(new BigDecimal(qtyPeriods)).setScale(2, RoundingMode.HALF_UP); //obtengo importe		

								fldev = fact.createLine(null, acctDevengamiento, getC_Currency_ID(), amount, null,
										invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());

							} else fldev = fact.createLine(null, acctDevengamiento, getC_Currency_ID(), totalLines.subtract(amtDevengar), null,
									invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
							//Fin OpenUp.
							// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
							// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
							if (fldev != null) {
								if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
									if (invoiceHdr.getCurrencyRate() != null){
										if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
											fldev.setDivideRate(invoiceHdr.getCurrencyRate());
											fldev.setAmtAcctDr(fldev.getAmtSourceDr().multiply(fldev.getDivideRate()));
											fldev.setAmtAcctCr(fldev.getAmtSourceCr().multiply(fldev.getDivideRate()));
										}
									}
								}
							}
							// Fin Issue #539.
							
						}
						// CR
						//OpenUp. Nicolas Sarlabos. 20/08/2013. #1232
						if(payables_ID <= 0) {
							FactLine.deleteFact(I_C_Invoice.Table_ID, invoiceHdr.get_ID(), getTrxName());
							throw new AdempiereException ("No es posible Contabilizar Documento ya que no se encuentra cuenta para socio de negocio y moneda.");
						}
						//Fin OpenUp. #1232
						FactLine flcred = fact.createLine(null, MAccount.get(getCtx(), payables_ID), getC_Currency_ID(), null, grossAmt,
								invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
						// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
						// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
						if (flcred != null) {
							if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
								if (invoiceHdr.getCurrencyRate() != null){
									if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
										flcred.setDivideRate(invoiceHdr.getCurrencyRate());
										flcred.setAmtAcctDr(flcred.getAmtSourceDr().multiply(flcred.getDivideRate()));
										flcred.setAmtAcctCr(flcred.getAmtSourceCr().multiply(flcred.getDivideRate()));
									}
								}
							}
						}
						// Fin Issue #539.
						
					}	
					
					if (serviceAmt.signum() != 0){
						FactLine flserv = fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID),
								getC_Currency_ID(), null, serviceAmt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
						
						// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
						// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
						if (flserv != null) {
							if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
								if (invoiceHdr.getCurrencyRate() != null){
									if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
										flserv.setDivideRate(invoiceHdr.getCurrencyRate());
										flserv.setAmtAcctDr(flserv.getAmtSourceDr().multiply(flserv.getDivideRate()));
										flserv.setAmtAcctCr(flserv.getAmtSourceCr().multiply(flserv.getDivideRate()));
									}
								}
							}
						}
						// Fin Issue #539.

					}	
				
			}
			
			//
			updateProductPO(as);	//	Only API
			updateProductInfo (as.getC_AcctSchema_ID());    //  only API
			
			// OpenUp. Gabriel Vila. 15/03/2013. Issue #556
			// Para pequeños desfasajes aplico ajuste automatico.
			fact.createRounding(invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo(), 0, 0, 0, 0);
			// Fin Issue #556
			
		}
		//  APC
		else if (getDocumentType().equals(DOCTYPE_APCredit))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			//  Charge                  CR
			fact.createLine (null, getAccount(Doc.ACCTTYPE_Charge, as),
				getC_Currency_ID(), null, getAmount(Doc.AMTTYPE_Charge), invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			//  TaxCredit               CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				FactLine tl = fact.createLine (null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxCredit, as),
						getC_Currency_ID(), null, m_taxes[i].getAmount(), invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());

				// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
				// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
				if (tl != null) {
					if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
						if (invoiceHdr.getCurrencyRate() != null){
							if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
								tl.setDivideRate(invoiceHdr.getCurrencyRate());
								tl.setAmtAcctDr(tl.getAmtSourceDr().multiply(tl.getDivideRate()));
								tl.setAmtAcctCr(tl.getAmtSourceCr().multiply(tl.getDivideRate()));
							}
						}
					}
				}
				// Fin Issue #539.
				
				// Fin OpenUp.	
				if (tl != null)
					tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
			}
			
			// OpenUp. Gabriel Vila. 12/10/2012. Issue #49.
			// Gestion de Provision de gastos. Antes de recorre las lineas
			// obtengo las cuentas de provision en moneda nacional y extranjera.
			MAccount cuentaProvisionMN = MAccount.get(getCtx(), as.getAcctSchemaDefault().getUnrealizedGain_Acct());
			MAccount cuentaProvisionME = MAccount.get(getCtx(), as.getAcctSchemaDefault().getUnrealizedLoss_Acct());
			// Fin OpenUp.

			
			// OpenUp. Matias Carbajal. 30/12/2014. Issue #2066
			// Gestion de Devengamientos para notas de credito
			MAccount cuentaDevengamientoMN = MAccount.get(getCtx(), as.getAcctSchemaDefault().getRealizedGain_Acct());
			MAccount cuentaDevengamientoME = MAccount.get(getCtx(), as.getAcctSchemaDefault().getRealizedLoss_Acct());

			BigDecimal amtDevengar = Env.ZERO;
			// Fin OpenUp. Issue #2066.

			
			//  Expense                 CR
			for (int i = 0; i < p_lines.length; i++)
			{
				DocLine line = p_lines[i];
				boolean landedCost = landedCost(as, fact, line, false);
				if (landedCost && as.isExplicitCostAdjustment())
				{
					fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), null, line.getAmtSource(), invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());					
					//
					FactLine fl = fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), line.getAmtSource(), null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
					String desc = line.getDescription();
					if (desc == null)
						desc = "100%";
					else
						desc += " 100%";
					fl.setDescription(desc);
				}
				if (!landedCost)
				{
					MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
					if (line.isItem())
						expense = line.getAccount (ProductCost.ACCTTYPE_P_InventoryClearing, as);
					BigDecimal amt = line.getAmtSource();
					BigDecimal dAmt = null;
					if (as.isTradeDiscountPosted() && !line.isItem())
					{
						BigDecimal discount = line.getDiscount();
						if (discount != null && discount.signum() != 0)
						{
							amt = amt.add(discount);
							dAmt = discount;
							MAccount tradeDiscountReceived = line.getAccount(ProductCost.ACCTTYPE_P_TDiscountRec, as);
							FactLine fl = fact.createLine (line, tradeDiscountReceived,
									getC_Currency_ID(), dAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());

							// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
							// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
							if (fl != null){
								if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
									if (invoiceHdr.getCurrencyRate() != null){
										if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
											fl.setDivideRate(invoiceHdr.getCurrencyRate());
											fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
											fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
										}
									}
								}
							}
							// Fin Issue #539.
						}
					}
					
					// OpenUp. Gabriel Vila. 02/10/2012. Issue #32.
					// Obtengo linea de invoice que necesito.
					MInvoiceLine invLine = (MInvoiceLine)p_lines[i].p_po;
					// Fin OpenUp
					
					// OpenUp. Gabriel Vila. 12/10/2012. Issue #49.
					// Gestion de provision de gastos. Comento linea original.
					
					//fact.createLine (line, expense, getC_Currency_ID(), null, amt);
					
					FactLine fl = null, flprov = null;
					if (invLine.isProvisioned()){	// Si esta linea de factura esta provisionada
						MAccount acctProvision = (invLine.getParent().getC_Currency_ID() == as.getC_Currency_ID()) ? cuentaProvisionMN : cuentaProvisionME; 
						// Para cada linea de provision asociada a esta linea de factura
						List<MInvoiceProvision> invprovs = invLine.getInvoiceLineProvisions();
						BigDecimal sumAmtAllocated = Env.ZERO;
						boolean tienelinea = false;
						for (MInvoiceProvision invprov: invprovs){
							tienelinea = true;
							if (invprov.getamtallocated().compareTo(Env.ZERO) != 0){
								flprov = fact.createLine (line, acctProvision, getC_Currency_ID(), null, invprov.getamtallocated());
								if (invLine.getC_Activity_ID_1() > 0){
									flprov.setC_Activity_ID_1(invLine.getC_Activity_ID_1());
									flprov.saveEx();
								}
								// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
								// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
								if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
									if (invoiceHdr.getCurrencyRate() != null){
										if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
											flprov.setDivideRate(invoiceHdr.getCurrencyRate());
											flprov.setAmtAcctDr(flprov.getAmtSourceDr().multiply(flprov.getDivideRate()));
											flprov.setAmtAcctCr(flprov.getAmtSourceCr().multiply(flprov.getDivideRate()));
										}
									}
								}
								// Fin Issue #539.
								
								sumAmtAllocated = sumAmtAllocated.add(invprov.getamtallocated());
							}
						}
						
						// Finalmente al credito la diferencia que me pueda quedar a favor de la cuenta del articulo
						if (tienelinea){
							if (amt.compareTo(sumAmtAllocated) > 0){
								fl = fact.createLine (line, expense, getC_Currency_ID(), null, amt.subtract(sumAmtAllocated),
										invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
								// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
								// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
								if (fl != null){
									if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
										if (invoiceHdr.getCurrencyRate() != null){
											if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
												fl.setDivideRate(invoiceHdr.getCurrencyRate());
												fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
												fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
											}
										}
									}
								}
								// Fin Issue #539.
							}
						}
					}
					else{
						
/////////////////////////////////////////////////////////////////////////////////
						// OpenUp. Matias Carbajal. 30/12/2014. Issue #2066.
						// Devengamiento de nota de credito)
						if (invLine.getParent().isDevengable()){
						// Si la fecha de contabilizacion de este comprobante pertenece a un periodo igual o mayor al periodo inicial del devengamiento
							if (MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() >= invLine.getParent().getC_Period_ID_From()){
								// Si tengo cantidad de cuotas
								if (invLine.getParent().getQtyQuote() > 1){
									amt = amt.divide(new BigDecimal(invLine.getParent().getQtyQuote()), 2, RoundingMode.HALF_UP);
									// Acumulo importe que se va devengando
									amtDevengar = amtDevengar.add(amt);
								}
								//OpenUp. Nicolas Sarlabos. 16/09/2013. #460.
								//si el periodo de la fecha de transaccion es menor o igual al periodo inicial de devengamiento creo la linea
								if (MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() <= invLine.getParent().getC_Period_ID_From()){
									
									fl = fact.createLine (line, expense, getC_Currency_ID(), null, amt, 
											invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
								}
								//Fin OpenUp. #460.

								// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
								// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
								if (fl != null) {
									if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
										if (invoiceHdr.getCurrencyRate() != null){
											if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
												fl.setDivideRate(invoiceHdr.getCurrencyRate());
												fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
												fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
											}
										}
									}
								}
								// Fin Issue #539.

							}
							else{
								// DR
								MAccount acctDevengamiento = (getC_Currency_ID() == as.getC_Currency_ID()) ? cuentaDevengamientoMN : cuentaDevengamientoME;
								fl = fact.createLine ( line, acctDevengamiento, getC_Currency_ID(), null, amt, 
										invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
								// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
								// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
								if (fl != null) {
									if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
										if (invoiceHdr.getCurrencyRate() != null){
											if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
												fl.setDivideRate(invoiceHdr.getCurrencyRate());
												fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
												fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
											}
										}
									}
								}
								// Fin Issue #539.

							}
						}
						else{
							// No es devengable y no paso por provision
							if (flprov == null){
								fl = fact.createLine (line, expense, getC_Currency_ID(), null, amt,
										invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
								// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
								// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
								if (fl != null) {
									if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
										if (invoiceHdr.getCurrencyRate() != null){
											if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
												fl.setDivideRate(invoiceHdr.getCurrencyRate());
												fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
												fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
											}
										}
									}
								}
								// Fin Issue #539.							
							}
						}
						//Fin Issue #2066				
/////////////////////////////////////////////////////////////////////////////////				

					}
					
					if (!line.isItem())
					{
						grossAmt = grossAmt.subtract(amt);
						serviceAmt = serviceAmt.add(amt);
					}
					
				
					
					
					// OpenUp. Gabriel Vila. 02/10/2012. Issue #32.
					// Contabilizar asiento con 3 categorias de centros de costo.
					if ((invLine != null) && (invLine.get_ID() > 0)){
						if (fl != null){
							if (invLine.getC_Activity_ID() > 0)
								fl.setC_Activity_ID(invLine.getC_Activity_ID());
							if (invLine.getC_Activity_ID_1() > 0)
								fl.setC_Activity_ID_1(invLine.getC_Activity_ID_1());
							if (invLine.getC_Activity_ID_2() > 0)
								fl.setC_Activity_ID_2(invLine.getC_Activity_ID_2());
							if (invLine.getC_Activity_ID_3() > 0)
								fl.setC_Activity_ID_3(invLine.getC_Activity_ID_3());
							fl.saveEx();
						}
					}
					// Fin OpenUp Issue #32.
					
					//
					if (line.getM_Product_ID() != 0
						&& line.getProduct().isService())	//	otherwise Inv Matching
						MCostDetail.createInvoice(as, line.getAD_Org_ID(), 
							line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
							line.get_ID(), 0,		//	No Cost Element
							line.getAmtSource().negate(), line.getQty(),
							line.getDescription(), getTrxName());
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  //  from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    //  to Loc
				}
			}
			//  Liability       DR

			int payables_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability, as);
			int payablesServices_ID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability_Services, as);
			
			if (m_allLinesItem || !as.isPostServices() 
				|| payables_ID == payablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}			
			
			if (grossAmt.signum() != 0){
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// OpenUp. Matias Carbajal. 07/01/2015. Issue #2066.
				// Gestion de Devengamientos para notas de credito
				if (amtDevengar.compareTo(Env.ZERO) > 0){
					// DR
					MAccount acctDevengamiento = (getC_Currency_ID() == as.getC_Currency_ID()) ? cuentaDevengamientoMN : cuentaDevengamientoME;
					BigDecimal totalLines = ((MInvoice)this.p_po).getTotalLines();
					//OpenUp. Nicolas Sarlabos. 16/09/2013. #460.
					FactLine fldev = null;
					MInvoiceLine invLine = (MInvoiceLine)p_lines[0].p_po;
					//si el preiodo de la fecha de transaccion es mayor al periodo inicial de devengamiento
					if (MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID() > invLine.getParent().getC_Period_ID_From()){
						//obtengo cantidad de periodos entre el periodo final de devengamiento y el de la transaccion (no incluido)
						int qtyPeriods = (invLine.getParent().getC_Period_ID_To() - MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).get_ID());

						BigDecimal amount = amtDevengar.multiply(new BigDecimal(qtyPeriods)).setScale(2, RoundingMode.HALF_UP); //obtengo importe		

						fldev = fact.createLine(null, acctDevengamiento, getC_Currency_ID(), null, amount,
								invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());

					} else fldev = fact.createLine(null, acctDevengamiento, getC_Currency_ID(), totalLines.subtract(amtDevengar), null,
							invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
					//Fin OpenUp.
					// OpenUp. Gabriel Vila. 15/03/2013. Issue #539.
					// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
					if (fldev != null) {
						if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
							if (invoiceHdr.getCurrencyRate() != null){
								if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
									fldev.setDivideRate(invoiceHdr.getCurrencyRate());
									fldev.setAmtAcctDr(fldev.getAmtSourceDr().multiply(fldev.getDivideRate()));
									fldev.setAmtAcctCr(fldev.getAmtSourceCr().multiply(fldev.getDivideRate()));
								}
							}
						}
					}
					// Fin Issue #539.
					
				}
				// CR
				//OpenUp. Nicolas Sarlabos. 20/08/2013. #1232
				
				//fin #2066
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//OpenUp. Nicolas Sarlabos. 16/09/2013. #1232
				if(payables_ID <= 0) {
					FactLine.deleteFact(I_C_Invoice.Table_ID, invoiceHdr.get_ID(), getTrxName());
					throw new AdempiereException ("No es posible Contabilizar Documento ya que no se encuentra cuenta para socio de negocio y moneda.");
				}
				//Fin OpenUp. #1232
				FactLine flgross = fact.createLine(null, MAccount.get(getCtx(), payables_ID),
						getC_Currency_ID(), grossAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());		
				// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
				if (flgross != null) {
					if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
						if (invoiceHdr.getCurrencyRate() != null){
							if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
								flgross.setDivideRate(invoiceHdr.getCurrencyRate());
								flgross.setAmtAcctDr(flgross.getAmtSourceDr().multiply(flgross.getDivideRate()));
								flgross.setAmtAcctCr(flgross.getAmtSourceCr().multiply(flgross.getDivideRate()));
							}
						}
					}
				}
				// Fin Issue #539.		
			}
			
			if (serviceAmt.signum() != 0){
				//OpenUp. Nicolas Sarlabos. 16/09/2013. #1232
				if(payablesServices_ID <= 0) {
					FactLine.deleteFact(I_C_Invoice.Table_ID, invoiceHdr.get_ID(), getTrxName());
					throw new AdempiereException ("No es posible Contabilizar Documento ya que no se encuentra cuenta para socio de negocio y moneda.");
				}
				//Fin OpenUp. #1232
				FactLine flgross = fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID),
						getC_Currency_ID(), serviceAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
				
				// Si tengo tasa de cambio (el usuario la puede modificar en la transaccion)
				if (flgross != null) {
					if (invoiceHdr.getC_Currency_ID() != schema.getC_Currency_ID()){
						if (invoiceHdr.getCurrencyRate() != null){
							if (invoiceHdr.getCurrencyRate().compareTo(Env.ZERO) > 0){
								flgross.setDivideRate(invoiceHdr.getCurrencyRate());
								flgross.setAmtAcctDr(flgross.getAmtSourceDr().multiply(flgross.getDivideRate()));
								flgross.setAmtAcctCr(flgross.getAmtSourceCr().multiply(flgross.getDivideRate()));
							}
						}
					}
				}
				// Fin Issue #539.		
			}
			
			// OpenUp. Gabriel Vila. 15/03/2013. Issue #556
			// Para pequeños desfasajes aplico ajuste automatico.
			fact.createRounding(invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo(), 0, 0, 0, 0);
			// Fin Issue #556

		}
		// OpenUp. Gabriel Vila. 14/06/2010.
		//factoring begin
		else if (getDocumentType().equals(DOCTYPE_Factoring))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			
			//  Header Charge           CR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0)
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), null, amt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			//  TaxDue                  CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getAmount();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
						getC_Currency_ID(), null, amt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
					if (tl != null)
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
				}
			}
			//  Revenue                 CR
			for (int i = 0; i < p_lines.length; i++)
			{
				amt = p_lines[i].getAmtSource();
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amt = amt.add(discount);
						dAmt = discount;
						fact.createLine (p_lines[i],
								p_lines[i].getAccount(ProductCost.ACCTTYPE_P_TDiscountGrant, as),
								getC_Currency_ID(), dAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
					}
				}
				fact.createLine (p_lines[i],
					p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as),
					getC_Currency_ID(), null, amt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      //  from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				}
			}
			
			//  Receivables     DR
			int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Factoring, as);
			int receivablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Factoring, as);
			if (m_allLinesItem || !as.isPostServices() 
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), grossAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), serviceAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
		}
		//factoring end
		//Documento de canje para el nuevo CDC
		else if (getDocumentType().equals(DOCTYPE_Canje))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			
			//  Header Charge           CR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0)
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), null, amt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			//  TaxDue                  CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getAmount();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
						getC_Currency_ID(), null, amt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
					if (tl != null)
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
				}
			}
			//  Revenue                 CR
			for (int i = 0; i < p_lines.length; i++)
			{
				amt = p_lines[i].getAmtSource();
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amt = amt.add(discount);
						dAmt = discount;
						fact.createLine (p_lines[i],
								p_lines[i].getAccount(ProductCost.ACCTTYPE_P_TDiscountGrant, as),
								getC_Currency_ID(), dAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
					}
				}
				fact.createLine (p_lines[i],
					p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as),
					getC_Currency_ID(), null, amt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      //  from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				}
			}
			
			//  Receivables     DR
			int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Canje, as);
			int receivablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Canje, as);

			if (m_allLinesItem || !as.isPostServices() 
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), grossAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), serviceAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
		}

		else if (getDocumentType().equals(DOCTYPE_FacturaImportaciones))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			
			//  Header Charge           CR
			MAccount acct = null;
			headervalidcombinationfact = this.getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability, as);
			acct = MAccount.get (as.getCtx(), headervalidcombinationfact);
			BigDecimal amt = getAmount(Doc.AMTTYPE_Gross);
			if (amt != null && amt.signum() != 0);
			{
				fact.createLine(null, acct, this.currencyID, null, getAmount(), invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			}
			
			//  Receivables     DR
			int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Facturas_Importaciones, as);
			int receivablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Facturas_Importaciones, as);

			if (m_allLinesItem || !as.isPostServices() 
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), grossAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), serviceAmt, null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
		}
		
		//OpenUp Nicolas Sarlabos 1/12/2011 issue#754 Pregunto si es Nota Credito Importaciones
		else if (getDocumentType().equals(DOCTYPE_NotaCreditoImportaciones))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			
			//  Header Charge           CR
			MAccount acct = null;
			headervalidcombinationfact = this.getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_V_Liability, as);
			acct = MAccount.get (as.getCtx(), headervalidcombinationfact);
			BigDecimal amt = getAmount(Doc.AMTTYPE_Gross);
			if (amt != null && amt.signum() != 0);
			{
				fact.createLine(null, acct, this.currencyID, getAmount(), null, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			
			}
			
			//  Receivables     DR
			int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Nota_Credito_Importaciones, as);
			int receivablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Nota_Credito_Importaciones, as);
			if (m_allLinesItem || !as.isPostServices() 
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0){
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID), getC_Currency_ID(), null, grossAmt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
			}
			
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), null, serviceAmt, invoiceHdr.getC_DocTypeTarget_ID(), invoiceHdr.getDocumentNo());
		}
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}

		facts.add(fact);
		return facts;
		
	}
	
	
	/**
	 *  OpenUp. Gabriel Vila. 26/07/2010
	 *  Obtengo validcombinationID para Invoices.
	 */
	private int getInvoiceValidCombination(int bPartnerID, int currencyID, boolean isSales, int acctType, MAcctSchema acctSchema){

		String sql = "", sql2 = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = -1;		
		boolean aplicaFiltroMoneda = false;
		
		try{
			
			// Obtengo grupo del socio de negocio
			MBPartner bp = new MBPartner(getCtx(), bPartnerID, null);
			MBPGroup grupoBP = (MBPGroup)bp.getC_BP_Group();
			
			// Si es invoice de venta
			if (isSales){
				// Si la moneda del recibo es moneda nacional
				if (currencyID==acctSchema.getC_Currency_ID()){
					if (acctType==Doc.ACCTTYPE_C_Receivable){
					
						sql = "SELECT C_Receivable_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";

						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT C_Receivable_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";	
						}

					}
					if (acctType==Doc.ACCTTYPE_C_Receivable_Services){
						sql = "SELECT C_Receivable_Services_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT C_Receivable_Services_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}
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
					if (acctType==Doc.ACCTTYPE_C_Receivable_Services){
						sql = "SELECT C_Receivable_Services_Acct FROM UY_ClientesME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT C_Receivable_Services_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}

					}
				}
			}
			else{
				// Invoice de compra
				// Si la moneda del recibo es moneda nacional
				if (currencyID==acctSchema.getC_Currency_ID()){
					if (acctType==Doc.ACCTTYPE_V_Liability){
						sql = "SELECT V_Liability_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Liability_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}
						
					}
					else if (acctType==Doc.ACCTTYPE_V_Liability_Services){
						sql = "SELECT V_Liability_Services_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Liability_Services_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}

					}
					else if (acctType==Doc.ACCTTYPE_NotInvoicedReceipts){
						sql = "SELECT NotInvoicedReceipts_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT NotInvoicedReceipts_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}

					}
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
					else if (acctType==Doc.ACCTTYPE_V_Liability_Services){
						sql = "SELECT V_Liability_Services_Acct FROM UY_ProveedoresME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Liability_Services_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}

					}
					else if (acctType==Doc.ACCTTYPE_ValeFlete){
						//sevans 04/03/2016 issue #4618
						//Se modifica el campo donde se consulta la cuenta
						sql = "SELECT NotInvoicedReceipts_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT NotInvoicedReceipts_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}

					}					
					else if (acctType==Doc.ACCTTYPE_NotInvoicedReceipts){
						sql = "SELECT NotInvoicedReceipts_Acct FROM UY_ProveedoresME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT NotInvoicedReceipts_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}


					}
					
				}
			}
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, bPartnerID);
			pstmt.setInt(2, acctSchema.getC_AcctSchema_ID());
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

	/***
	 * Metodo para crear asientos en modulo de transporte para factura de flete de un expediente en representacion.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 10/09/2014
	 * @see
	 * @param as
	 * @param client
	 * @param invoice
	 * @param expediente
	 * @return
	 */
	private ArrayList<Fact> crearAsientosTransRepresentacion(MAcctSchema as, MClient client, MInvoice invoice, MTRTrip expediente){
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// Obtengo modelo de paramatrizacion contable desde la parametrizacion general del modulo de transporte
		MTRConfig config = MTRConfig.forValue(getCtx(), "general", null);
		MTRConfigAcct confAcct = MTRConfigAcct.forConfigID(getCtx(), config.get_ID(), null);
		
		// Si no tengo parametrizacion contable aviso y salgo
		if (confAcct == null){
			p_Error = "No se pudo obtener parametrizacion contable en Parametros Generales de Transporte";
			fact = null;
			facts.add(fact);
			return facts;
		}

		// DR - Al debito va la cuenta general de transporte para clientes deudores del representado
		MAccount acct = new MAccount(getCtx(), confAcct.getP_TR_PagaRepresentado_Acct(), getTrxName());
		fact.createLine (null, acct, getC_Currency_ID(), invoice.getTotalLines(), null, invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());
		
		// CR - Al credito va la cuenta de derechos flete del representado
		MAccount acctCR = new MAccount(getCtx(), confAcct.getP_TR_Representado_Acct(), getTrxName());
		fact.createLine (null, acctCR, getC_Currency_ID(), null, invoice.getTotalLines(), invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());
		
		//  Impuestos
		BigDecimal amtTax = Env.ZERO;
		for (int i = 0; i < m_taxes.length; i++)
		{
			amtTax = m_taxes[i].getAmount();
			if (amtTax != null && amtTax.signum() != 0)
			{

				// DR - IVA : Deudores por retencion de IVA al representado 
				MAccount acctIVA1 = new MAccount(getCtx(), confAcct.getP_TR_IVA_Representado_Acct(), getTrxName());
				FactLine tl = fact.createLine (null, acctIVA1, getC_Currency_ID(), amtTax, null, invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());
				if (tl != null) tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
					
				// CR - IVA : Retencion de IVA a no residentes 
				MAccount acctIVA2 = new MAccount(getCtx(), confAcct.getP_TR_IVA_NoResidente_Acct(), getTrxName());
				FactLine t2 = fact.createLine (null, acctIVA2, getC_Currency_ID(), null, amtTax, invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());
				if (t2 != null) t2.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
			}
			
		}

		// Redondeo.
		fact.createRounding(invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo(), 0, 0, 0, 0);

		facts.add(fact);
		return facts;
		
	}

	/***
	 * Contabilizacion de documento: Factura Vale Flete, para clientes de transporte.
	 * OpenUp Ltda. Issue #3205 
	 * @author Gabriel Vila - 12/11/2014
	 * @see
	 * @param as
	 * @param client
	 * @param invoice
	 * @return
	 */
	private ArrayList<Fact> crearAsientosValeFlete(MAcctSchema as, MClient client, MInvoice invoice){
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// Seteo documento multimoneda para que no de problemas de balanceo
		//this.setIsMultiCurrency(true);
		
		BigDecimal amtClient = Env.ZERO, amtOtherClients = Env.ZERO;
		
		// Subtotal segun productos marcados para contabilizar en vale flete
		String sql = " select coalesce(sum(l.linenetamt),0) from c_invoiceline l inner join m_product prod on l.m_product_id = prod.m_product_id " +
				     " where l.c_invoice_id =" + invoice.get_ID() +
				     " and prod.IsFleteTR='Y'";
		BigDecimal amtValeFlete = DB.getSQLValueBDEx(null, sql);
		
		amtClient = amtValeFlete;
		
		// Obtengo parametros generales de transporte
		MTRConfig config = MTRConfig.forClientID(getCtx(), this.getAD_Client_ID(), null);
		MTRConfigVFlete configVF = MTRConfigVFlete.forConfig(getCtx(), config.get_ID(), null);
		
		// Si segun parametros de transporte, manejo dos empresas en vale flete, debo saber el detalle de a que empresa se facturan los CRTS
		// de la orden de transporte asociada a este vale flete
		if (configVF.isSeparated()){
			
			// Obtengo orden de transporte asociada al vale flete
			MTRTransOrder torder = new MTRTransOrder(getCtx(), invoice.get_ValueAsInt("UY_TR_TransOrder_ID"), null);
		
			// Recorro CRTS de la orden de transporte de este vale flete para contar cuantos son facturados por una empresa y cuantos por otra
			List<MTRTransOrderLine> lines = torder.getLines();
			
			int contClient = 0, contOthers = 0, contTotal = 0;
			for (MTRTransOrderLine line: lines){
				MTRCrt crt = (MTRCrt)line.getUY_TR_Crt();
				int invSaleFleteID = DB.getSQLValue(null, " select c_invoice_id from c_invoice where uy_tr_crt_id =" + crt.get_ID() +
														  " and ad_client_id !=" + invoice.getAD_Client_ID());
				
				if (invSaleFleteID <= 0){
					contClient++;
				}
				else{
					contOthers++;
				}
			}
			
			contTotal = contClient + contOthers;
			
			if (contTotal > 0){
				BigDecimal montoParte = amtClient.divide(new BigDecimal(contTotal), 2, RoundingMode.HALF_UP);
				if (montoParte.compareTo(Env.ZERO) != 0){
					BigDecimal montoClient = montoParte.multiply(new BigDecimal(contClient)).setScale(2, RoundingMode.HALF_UP);
					amtOtherClients = amtClient.subtract(montoClient);
					amtClient =  montoClient;
				}
			}
			
		}
		
		
		// CR - Al credito va la cuenta Proveedores del socio de negocio segun moneda del documento
		//OpenUp. Nicolas Sarlabos. 31/07/2015. #4618.
		int acctBPId = getInvoiceValidCombination(invoice.getC_BPartner_ID(), invoice.getC_Currency_ID(), false, Doc.ACCTTYPE_ValeFlete, as);
		//Fin OpenUp.
		if (acctBPId <= 0){
			p_Error = "No se pudo obtener cuenta ORDEN COMPRA FLETE para este socio de negocio - moneda.";
			fact = null;
			facts.add(fact);
			return facts;
		}
		//fact.createLine(null, MAccount.get(getCtx(), acctBPId), invoice.getC_Currency_ID(), null, invoice.getGrandTotal(), invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());
		fact.createLine(null, MAccount.get(getCtx(), acctBPId), invoice.getC_Currency_ID(), null, amtValeFlete, invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());

		
		/*
		// CR - IVA Basico en moneda del documento
		MTax tax = MTax.forValue(getCtx(), "basico", null);
		BigDecimal taxBaseAmount = invoice.getTotalLines();
		BigDecimal taxAmount = invoice.getGrandTotal().subtract(invoice.getTotalLines());
		DocTax taxLine = new DocTax(tax.get_ID(), tax.getName(), tax.getRate(), taxBaseAmount, taxAmount, false);
		
		
		fact.createLine(null, taxLine.getAccount(DocTax.ACCTTYPE_TaxCredit , as),
				invoice.getC_Currency_ID(), taxLine.getAmount(), null, invoice.getC_DocTypeTarget_ID(),invoice.getDocumentNo());
		*/

		/*
		// DR - Al debito va la cuenta de compra flete asociada el proveedor por cada vale flete y por cada producto (gasto extra)
		int accountID = getInvoiceValidCombination(invoice.getC_BPartner_ID(), invoice.getC_Currency_ID(), false, Doc.ACCTTYPE_NotInvoicedReceipts, as);
		if (accountID <= 0){
			p_Error = "No se pudo obtener parametrizacion contable en Parametros Generales de Transporte";
			fact = null;
			facts.add(fact);
			return facts;
		}
		MAccount acctPOFlete = MAccount.get(getCtx(), accountID);

		// Obtengo ordenes de compra flete afectados en esta factura
		List<MOrderFlete> ofletes = invoice.getLinesInvOrdFlete();
		for (MOrderFlete oflete: ofletes){
			fact.createLine (null, acctPOFlete, oflete.getC_Currency_ID(), oflete.getamtallocated(), null, invoice.getC_DocTypeTarget_ID(), 
					 invoice.getDocumentNo());
		}
		
		// Obtengo vales flete afectados en esta factura
		List<MInvoiceFlete> vfletes = invoice.getLinesInvFlete();
		for (MInvoiceFlete vflete: vfletes){
			fact.createLine (null, acctPOFlete, vflete.getC_Currency_ID(), vflete.getamtallocated(), null, invoice.getC_DocTypeTarget_ID(), 
					 invoice.getDocumentNo());
		}
		*/
		
		MAccount expense = null, expense2 = null;
		
		// Ahora lineas de gastos extra (productos)
		for (int i = 0; i < p_lines.length; i++){

			
			/*
			DocLine line = p_lines[i];
			fact.createLine (line, acctPOFlete, line.getC_Currency_ID(), line.getAmtSource(), null, invoice.getC_DocTypeTarget_ID(), 
							 invoice.getDocumentNo());
			*/
			
			DocLine line = p_lines[i];
			
			MProduct prod =  new MProduct(getCtx(), line.getM_Product_ID(), null);

			if (prod.get_ValueAsBoolean("IsFleteTR")){
				expense2 = line.getAccount (ProductCost.ACCTTYPE_P_InventoryClearing, as);
				
				if ( i == 0){
					expense = line.getAccount (ProductCost.ACCTTYPE_P_InventoryClearing, as);
					
					// Si moneda del vale flete es distinto a la moneda de la empresa (ej: vale fletes de empresa uruguaya hecho en moneda reales)
					if (invoice.getC_Currency_ID() != as.getC_Currency_ID()){
						// Tomo otra cuenta
						MAccount expenseAux = line.getAccount (ProductCost.ACCTTYPE_P_TR_FleteImpNacional, as);
						if (expenseAux != null){
							if (expenseAux.get_ID() > 0){
								expense = expenseAux;
							}
						}
					}
				}
				
				if ((expense2 == null) || (expense2.get_ID() <= 0)){
					expense2 = expense;
				}
				
				
				//fact.createLine (line, expense2, line.getC_Currency_ID(), line.getAmtSource(), null, invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());
				
			}
			
			/*
			// Si tengo orden de transporte asociada a este documento de gasto
			if (invoic.get_ValueAsInt(X_UY_TR_TransOrder.COLUMNNAME_UY_TR_TransOrder_ID) > 0){
				
				// Obtengo modelo de orden de transporte
				MTRTransOrder ordtrans = new MTRTransOrder(getCtx(), invoice.get_ValueAsInt(X_UY_TR_TransOrder.COLUMNNAME_UY_TR_TransOrder_ID), null);
				
				// Obtengo lineas de orden de transporte
				List<MTRTransOrderLine> otlines = ordtrans.getLines();

				for (MTRTransOrderLine )
				
				
				// En una orden todos los expedientes son del mismo tipo : importacion o exportacion o en transito.
				// Por lo tanto con tomar el primer expediente me alcanza para saber que tipo considerar para obtener la cuenta contable
				MTRTrip expediente = (MTRTrip)otlines.get(0).getUY_TR_Trip();

				// Cuenta item para compra si es expediente en transito
				if (expediente.isInTransit()){
					expense = line.getAccount (ProductCost.ACCTTYPE_P_TR_FleteTransito, as);
				}
				else{
					// Cuenta item para compra depende si es exportacion o importacion
					if (expediente.getTripType().equalsIgnoreCase(X_UY_TR_Trip.TRIPTYPE_IMPORTACION)){
						expense = line.getAccount (ProductCost.ACCTTYPE_P_TR_FleteImpNacional, as);									
					}
					else if (expediente.getTripType().equalsIgnoreCase(X_UY_TR_Trip.TRIPTYPE_EXPORTACION)){
						expense = line.getAccount (ProductCost.ACCTTYPE_P_TR_FleteExpNacional, as);
					}
				}
			}
			else{
				// Factura compra que no tiene orden de transporte y estoy en un producto flete para compra
				// Verifico si esta linea esta asociada a una linea de una orden de compra flete
				if (line.getC_OrderLine_ID() > 0){
					MOrderLine oLine = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), null);
					MOrder order = (MOrder)oLine.getC_Order();
					MDocType docOrder = (MDocType)order.getC_DocTypeTarget();
					if (docOrder.getValue() != null){
						if (docOrder.getValue().equalsIgnoreCase("poflete")){

							int accountID = getInvoiceValidCombination(this.partnerID, this.currencyID, false, Doc.ACCTTYPE_NotInvoicedReceipts, as);
							
							if (accountID <= 0){
								p_Error = "No se pudo obtener parametrizacion contable en Parametros Generales de Transporte";
								fact = null;
								facts.add(fact);
								return facts;
							}

							expense = MAccount.get(getCtx(), accountID);
						}
					}
				}
				
			}
			*/
			
		}

		if (amtClient.compareTo(Env.ZERO) != 0){			
			fact.createLine(null, expense, invoice.getC_Currency_ID(), amtClient, null, invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());
		}
		
		if (amtOtherClients.compareTo(Env.ZERO) != 0){
			if (configVF.getP_Asset_Acct() <= 0){
				p_Error = "No se pudo obtener cuenta para vale flete otras empresas.";
				fact = null;
				facts.add(fact);
				return facts;
			}
			MAccount acctOthers = MAccount.get(getCtx(), configVF.getP_Asset_Acct());
			fact.createLine(null, acctOthers, invoice.getC_Currency_ID(), amtOtherClients, null, invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo());
		}

		
		// Redondeo.
		fact.createRounding(invoice.getC_DocTypeTarget_ID(), invoice.getDocumentNo(), 0, 0, 0, 0);

		facts.add(fact);
		return facts;
		
	}

	
}   //  Doc_Invoice
