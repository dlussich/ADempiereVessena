/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 24/11/2014
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.Env;
import org.openup.model.MTypeFact;
import org.openup.model.MTypeFactLine;

/**
 * org.compiere.acct - Doc_UYTypeFact
 * OpenUp Ltda. Issue #3315 
 * Description: Contabilizacion de documento de Registracion de Asientos Tipo.
 * @author Gabriel Vila - 24/11/2014
 * @see
 */
public class Doc_UYTypeFact extends Doc {

	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYTypeFact(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYTypeFact(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MTypeFact.class, rs, null, trxName);
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 24/11/2014
	 * @see
	 * @return
	 */
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MTypeFact) getPO()).getDateTrx());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 24/11/2014
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
	 * @author Gabriel Vila - 24/11/2014
	 * @see
	 * @param as
	 * @return
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		// Creacion de Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		MTypeFact header = (MTypeFact)getPO();
		List<MTypeFactLine> lines = header.getLines();

		MClient client = new MClient(getCtx(), header.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		FactLine fl = null;
		
		// DR - Lineas
		// CR - Lineas
		for (MTypeFactLine line: lines){

			// Si linea es al DEBE
			if(line.isDebit()){ 

				//DR
				MAccount acctDr = MAccount.forElementValue(getCtx(), line.getC_ElementValue_ID(), null);
				fl = fact.createLine(null, acctDr, header.getC_Currency_ID(), line.getAmt(), null,
						header.getC_DocType_ID(), header.getDocumentNo());

				if (fl != null){
					fl.setC_BPartner_ID(line.getC_BPartner_ID());
					fl.setLine_ID(line.get_ID());
					
					if (header.getC_Currency_ID() != schema.getC_Currency_ID()){
						if (header.getCurrencyRate() != null){
							if (header.getCurrencyRate().compareTo(Env.ZERO) > 0){
								fl.setDivideRate(header.getCurrencyRate());
								fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()).setScale(2, RoundingMode.HALF_UP));
								fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()).setScale(2, RoundingMode.HALF_UP));
								fl.saveEx();
							}
						}
					}
				}
				
			} 
			else { //si la linea del registro es al HABER
				
				//CR
				MAccount acctCR = MAccount.forElementValue(getCtx(), line.getC_ElementValue_ID(), null);
				fl = fact.createLine(null, acctCR, header.getC_Currency_ID(), null, line.getAmt(),
						header.getC_DocType_ID(), header.getDocumentNo());

				if (fl != null){
					fl.setC_BPartner_ID(line.getC_BPartner_ID());
					fl.setLine_ID(line.get_ID());
					
					if (header.getC_Currency_ID() != schema.getC_Currency_ID()){
						if (header.getCurrencyRate() != null){
							if (header.getCurrencyRate().compareTo(Env.ZERO) > 0){
								fl.setDivideRate(header.getCurrencyRate());
								fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()).setScale(2, RoundingMode.HALF_UP));
								fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()).setScale(2, RoundingMode.HALF_UP));
								fl.saveEx();
							}
						}
					}

				}					
			}

		} 

		// Redondeo.
		fact.createRounding(header.getC_DocType_ID(), header.getDocumentNo(), 0, 0, 0, 0);

		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
		
	}

}
