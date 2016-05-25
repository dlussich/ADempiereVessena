/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 09/10/2012
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MElementValue;
import org.compiere.util.Env;
import org.openup.model.MProvision;
import org.openup.model.MProvisionLine;

/**
 * org.compiere.acct - Doc_UYProvision
 * OpenUp Ltda. Issue #49 
 * Description: Contabilizacion de Provisiones
 * @author Gabriel Vila - 09/10/2012
 * @see
 */
public class Doc_UYProvision extends Doc {

	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYProvision(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYProvision(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MProvision.class, rs, null, trxName);
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/10/2012
	 * @see
	 * @return
	 */
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MProvision) getPO()).getDateAcct());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/10/2012
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
	 * @author Hp - 09/10/2012
	 * @see
	 * @param as
	 * @return
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		// Creacion de Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		// Si el tipo de documento provision
		if (getDocumentType().equals(DOCTYPE_Ctb_Provision))
		{
			MProvision header = (MProvision)getPO();
			List<MProvisionLine> lines = header.getLines();

			// Si no tengo lineas para procesar, salgo sin hacer nada
			if (lines.size() <= 0) return null;

			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
						
			// Cuentas de provision para moneda nacional y moneda extranjera
			MAccount cuentaProvisionMN = MAccount.get(getCtx(), schema.getAcctSchemaDefault().getUnrealizedGain_Acct());
			MAccount cuentaProvisionME = MAccount.get(getCtx(), schema.getAcctSchemaDefault().getUnrealizedLoss_Acct());
			
			// Debito - Lineas con la cuenta de la linea
			// Credito - Lineas pero utilizando la cuenta de provision definida en el esquema contable (cuentas default)
			
			// Para cada cuenta-moneda a procesar
			for (MProvisionLine line: lines){

				if (line.getAmtSourceAverage().compareTo(Env.ZERO) != 0){

					// Modelo de la cuenta
					MElementValue elementValue = new MElementValue(getCtx(), line.getC_ElementValue_ID(), null);
					
					// Cuenta de la linea
					int cValidCombinationID = elementValue.getValidCombinationID();
					MAccount acctCuentaLinea = MAccount.get(getCtx(), cValidCombinationID);

					FactLine fl = null;
					
					// DR 
					fl = fact.createLine(null, acctCuentaLinea, line.getC_Currency_ID(), line.getAmtSourceAverage(), null,
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
					
					// CR - Cuenta de provision segun moneda
					MAccount cuentaProvision = (line.getC_Currency_ID() == schema.getC_Currency_ID()) ? cuentaProvisionMN : cuentaProvisionME;
					
					fl = fact.createLine(null, cuentaProvision, line.getC_Currency_ID(), null, line.getAmtSourceAverage(),
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
