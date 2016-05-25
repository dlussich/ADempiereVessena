/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 06/11/2012
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.X_M_Product_Acct;
import org.compiere.util.Env;
import org.openup.model.MDevengamiento;
import org.openup.model.MDevengamientoLine;


/**
 * org.compiere.acct - Doc_UYDevengamiento
 * OpenUp Ltda. Issue #82 
 * Description: Gestion de devengamientos
 * @author Gabriel Vila - 06/11/2012
 * @see
 */
public class Doc_UYDevengamiento extends Doc {

	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYDevengamiento(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(ass, clazz, rs, defaultDocumentType, trxName);
	}

	/**
	 * Constructor
	 * @param ass
	 *            accounting schemata
	 * @param rs
	 *            record
	 * @param trxName
	 *            trx
	 */
	public Doc_UYDevengamiento(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MDevengamiento.class, rs, null, trxName);
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/11/2012
	 * @see
	 * @return
	 */
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MDevengamiento) getPO()).getDateAcct());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/11/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/11/2012
	 * @see
	 * @param as
	 * @return
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		// Creacion de Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		MDevengamiento devenga = (MDevengamiento)this.p_po;
		
		// Si el tipo de documento provision
		if (getDocumentType().equals(DOCTYPE_Ctb_Devengamiento))
		{
			MDevengamiento header = (MDevengamiento)getPO();
			List<MDevengamientoLine> lines = header.getLines();

			// Fecha asiento : ultimo dia del periodo del cabezal de devengamiento
			setDateAcct(new MPeriod(getCtx(), header.getC_Period_ID(), null).getEndDate());
			setDateDoc(header.getDateTrx());
			
			// Si no tengo lineas para procesar, salgo sin hacer nada
			if (lines.size() <= 0) return null;

			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
						
			// Cuentas de devengamiento para moneda nacional y moneda extranjera
			MAccount cuentaDevengamientoMN = MAccount.get(getCtx(), schema.getAcctSchemaDefault().getRealizedGain_Acct());
			MAccount cuentaDevengamientoME = MAccount.get(getCtx(), schema.getAcctSchemaDefault().getRealizedLoss_Acct());
			
			// Debito - Lineas con la cuenta de la linea
			// Credito - Lineas pero utilizando la cuenta de provision definida en el esquema contable (cuentas default)
			
			// Para cada cuenta-moneda a procesar
			
			BigDecimal totalAmtMN = Env.ZERO, totalAmtME = Env.ZERO;
			FactLine fl = null;
			
			for (MDevengamientoLine line: lines){

				if (line.isApproved()){
					

					MInvoice invoice = new MInvoice(getCtx(), line.getC_Invoice_ID(), null);
					
					// DR - Importe sin iva de cada producto correspondiente a una cuota
					MInvoiceLine invline = new MInvoiceLine(getCtx(), line.getC_InvoiceLine_ID(), null);
					MProduct prod = new MProduct(getCtx(), invline.getM_Product_ID(), null);
					X_M_Product_Acct prodacct = prod.getProductAccounting();
					
					if (prodacct == null){
						p_Error = "Falta definir cuentas para el Producto : " + prod.getValue() + " - " + prod.getName();
						log.log(Level.SEVERE, p_Error);
						return null;
					}
					
					MAccount ctaprod = MAccount.get(getCtx(), prodacct.getP_Expense_Acct());
					if (prod.isItem())
						ctaprod = MAccount.get(getCtx(), prodacct.getP_InventoryClearing_Acct());
					
					BigDecimal amtLine = invline.getLineNetAmt().divide(new BigDecimal(invoice.getQtyQuote()), 2, RoundingMode.HALF_UP);
					fl = fact.createLine (null, ctaprod, line.getC_Currency_ID(), amtLine, null,
							devenga.getC_DocType_ID(), devenga.getDocumentNo());

					if (fl != null){
						if (line.getC_BPartner_ID() > 0)
							fl.setC_BPartner_ID(line.getC_BPartner_ID());
						if (line.getM_Product_ID() > 0)
							fl.setM_Product_ID(line.getM_Product_ID());
						if (invline.getC_Activity_ID() > 0)
							fl.setC_Activity_ID(invline.getC_Activity_ID());
						if (invline.getC_Activity_ID_1() > 0)
							fl.setC_Activity_ID_1(invline.getC_Activity_ID_1());
						if (invline.getC_Activity_ID_2() > 0)
							fl.setC_Activity_ID_2(invline.getC_Activity_ID_2());
						if (invline.getC_Activity_ID_3() > 0)
							fl.setC_Activity_ID_3(invline.getC_Activity_ID_3());
						//fl.saveEx();
					}

					
					if (line.getC_Currency_ID() == schema.getC_Currency_ID()){
						totalAmtMN = totalAmtMN.add(line.getQuoteAmt());
					}
					else{
						totalAmtME = totalAmtME.add(line.getQuoteAmt());
						
						// Debo considerar tipo de cambio segun fecha de factura
						BigDecimal currencyRate = MConversionRate.getRate(line.getC_Currency_ID(), schema.getC_Currency_ID(), invoice.getDateAcct(), 0, invoice.getAD_Client_ID(), 0);
						if (currencyRate == null){
							throw new AdempiereException("No se pudo obtener Tasa de Cambio para la fecha : " + invoice.getDateAcct());							
						}
						if (fl != null){
							fl.setDivideRate(currencyRate);
							fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()).setScale(2, RoundingMode.HALF_UP));
							fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()).setScale(2, RoundingMode.HALF_UP));
						}
					}
				}
			}
			
			// CR - Suma Total de lineas de devengamiento a cuenta devengamiento segun moneda
			if (totalAmtMN.compareTo(Env.ZERO) != 0){
				fl = fact.createLine (null, cuentaDevengamientoMN, schema.getC_Currency_ID(), null, totalAmtMN,
						devenga.getC_DocType_ID(), devenga.getDocumentNo());
			}
			
			if (totalAmtME.compareTo(Env.ZERO) != 0){
				fl = fact.createLine (null, cuentaDevengamientoME, 100, null, totalAmtME,
						devenga.getC_DocType_ID(), devenga.getDocumentNo());
			}
		}
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}

		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}

}
