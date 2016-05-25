package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MCargo;
import org.openup.model.MCargoLine;
import org.openup.model.MCargoRegister;
import org.openup.model.MCargoRegisterLine;

public class Doc_UYCargoRegister extends Doc {
	
	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYCargoRegister(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYCargoRegister(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MCargoRegister.class, rs, null, trxName);
	}

	//@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MCargoRegister) getPO()).getDateAcct());
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		// Creacion de Fact Header
		String sql = "";
		MCargo cargo = null;
		MCargoLine carLine = null;
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		MCargoRegister header = (MCargoRegister)getPO();
		List<MCargoRegisterLine> lines = header.getLines();
		
		FactLine fl = null;
		
		//obtengo el c_elementvalue_id a partir de la cuenta bancaria ingresada en el cabezal
		sql = "SELECT cv.account_id" +
              " FROM c_validcombination cv" +
              " INNER JOIN c_bankaccount_acct b ON cv.c_validcombination_id=b.b_asset_acct" +
              " WHERE c_bankaccount_id=" + header.getC_BankAccount_ID();
		int elementValueID = DB.getSQLValueEx(null, sql);
			
		// DR - Lineas
		// CR - Lineas
		for (MCargoRegisterLine line: lines){

			cargo = (MCargo)line.getUY_Cargo();

			sql = "SELECT uy_cargoline_id" +
					" FROM uy_cargoline" +
					" WHERE uy_cargo_id=" + cargo.get_ID();
			int carLineID = DB.getSQLValueEx(null, sql);

			carLine = new MCargoLine(getCtx(),carLineID,getTrxName()); //instancio linea de cargo

			if(carLine.get_ID()>0){
				
				if(line.isDebit()){ //si la linea del registro es al DEBE
					//DR
					MAccount acctDr = MAccount.forElementValue(getCtx(), carLine.getC_ElementValue_ID(), null);
					fl = fact.createLine(null, acctDr, as.getC_Currency_ID(), line.getAmount(), null,
							header.getC_DocType_ID(), header.getDocumentNo());

					if (fl != null){
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_1());
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_2());
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_3());				
					}
					
					//CR
					MAccount acctCR = MAccount.forElementValue(getCtx(), elementValueID, null);
					fl = fact.createLine(null, acctCR, as.getC_Currency_ID(), null, line.getAmount(),
							header.getC_DocType_ID(), header.getDocumentNo());

					if (fl != null){
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_1());
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_2());
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_3());				
					}					
					
				} else { //si la linea del registro es al HABER
					
					//DR
					MAccount acctDr = MAccount.forElementValue(getCtx(), elementValueID, null);
					fl = fact.createLine(null, acctDr, as.getC_Currency_ID(), line.getAmount(), null,
							header.getC_DocType_ID(), header.getDocumentNo());

					if (fl != null){
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_1());
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_2());
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_3());				
					}
					
					//CR
					MAccount acctCR = MAccount.forElementValue(getCtx(), carLine.getC_ElementValue_ID(), null);
					fl = fact.createLine(null, acctCR, as.getC_Currency_ID(), null, line.getAmount(),
							header.getC_DocType_ID(), header.getDocumentNo());

					if (fl != null){
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_1());
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_2());
						fl.setC_Activity_ID_1(carLine.getC_Activity_ID_3());				
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
