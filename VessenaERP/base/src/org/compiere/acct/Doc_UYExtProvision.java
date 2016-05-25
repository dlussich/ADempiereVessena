/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 22/05/2014
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MElementValue;
import org.compiere.util.Env;
import org.openup.model.MExtProvision;
import org.openup.model.MExtProvisionLine;


/**
 * org.compiere.acct - Doc_UYExtProvision
 * OpenUp Ltda. Issue #49 
 * Description: Contabilizacion de extorno de provisiones de gastos pendientes.
 * @author Gabriel Vila - 22/05/2014
 * @see
 */
public class Doc_UYExtProvision extends Doc {

	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYExtProvision(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYExtProvision(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MExtProvision.class, rs, null, trxName);
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 22/05/2014
	 * @see
	 * @return
	 */
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MExtProvision) getPO()).getDateAcct());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 22/05/2014
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
	 * @author Gabriel Vila - 22/05/2014
	 * @see
	 * @param as
	 * @return
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		// Creacion de Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		MExtProvision header = (MExtProvision)getPO();
		List<MExtProvisionLine> lines = header.getSelectedLines();

		// Si no tengo lineas para procesar, salgo sin hacer nada
		if (lines.size() <= 0) return null;

		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
					
		// Cuentas de provision para moneda nacional y moneda extranjera
		MAccount cuentaProvisionMN = MAccount.get(getCtx(), schema.getAcctSchemaDefault().getUnrealizedGain_Acct());
		MAccount cuentaProvisionME = MAccount.get(getCtx(), schema.getAcctSchemaDefault().getUnrealizedLoss_Acct());
		
		// Credito - Lineas con la cuenta de la linea
		// Debito - Lineas pero utilizando la cuenta de provision definida en el esquema contable (cuentas default)
		
		// Para cada cuenta-moneda a procesar
		for (MExtProvisionLine line: lines){

			if (line.isSelected()){

				if (line.getamtopen().compareTo(Env.ZERO) != 0){

					// Modelo de la cuenta
					MElementValue elementValue = new MElementValue(getCtx(), line.getC_ElementValue_ID(), null);
					
					// Cuenta de la linea
					int cValidCombinationID = elementValue.getValidCombinationID();
					MAccount acctCuentaLinea = MAccount.get(getCtx(), cValidCombinationID);

					FactLine fl = null;
					
					// CR 
					fl = fact.createLine(null, acctCuentaLinea, line.getC_Currency_ID(), null, line.getamtopen(), 
							header.getC_DocType_ID(), header.getDocumentNo());
					if (fl != null && this.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.getAD_Org_ID());
					
					if (fl != null){
						if (line.getC_BPartner_ID() > 0)
							fl.setC_BPartner_ID(line.getC_BPartner_ID());
						if (line.getM_Product_ID() > 0)
							fl.setM_Product_ID(line.getM_Product_ID());
						if (line.getC_Activity_ID_1() > 0)
							fl.setC_Activity_ID_1(line.getC_Activity_ID_1());
						fl.saveEx();
					}
					
					// DR - Cuenta de provision segun moneda
					MAccount cuentaProvision = (line.getC_Currency_ID() == schema.getC_Currency_ID()) ? cuentaProvisionMN : cuentaProvisionME;
					
					fl = fact.createLine(null, cuentaProvision, line.getC_Currency_ID(), line.getamtopen(), null, 
							header.getC_DocType_ID(), header.getDocumentNo());
					if (fl != null && this.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.getAD_Org_ID());

					if (fl != null){
						if (line.getC_BPartner_ID() > 0)
							fl.setC_BPartner_ID(line.getC_BPartner_ID());
						if (line.getM_Product_ID() > 0)
							fl.setM_Product_ID(line.getM_Product_ID());
						if (line.getC_Activity_ID_1() > 0)
							fl.setC_Activity_ID_1(line.getC_Activity_ID_1());
						fl.saveEx();
					}
					
				}
				
			}
			
			
		}


		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}

}
