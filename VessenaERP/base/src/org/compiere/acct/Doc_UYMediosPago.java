package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankAccount;
import org.compiere.model.MCharge;
import org.compiere.model.MClient;
import org.compiere.model.MSysConfig;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MMediosPago;
import org.openup.model.MPaymentRule;

public class Doc_UYMediosPago extends Doc {
	
	private MMediosPago header = (MMediosPago)getPO(); //OpenUp Nicolas Sarlabos issue #895 19/10/2011
	
	/**
	 * 
	 * OpenUp. issue #895	
	 * Descripcion : 
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 19/10/2011
	 */
	@Override
	public boolean isConvertible(MAcctSchema acctSchema) {
		if(this.header.isInitialLoad()) return true;

		if (this.header.getDivideRate() != null){
			if (this.header.getDivideRate().compareTo(Env.ONE) > 0){
				return true;
			}
		}

		return super.isConvertible(acctSchema);
	}

	/**
	 * 
	 * OpenUp. issue #895	
	 * Descripcion : 
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 19/10/2011
	 */
	@Override
	public boolean isPeriodOpen() {
		if(this.header.isInitialLoad()) return true;
		else return super.isPeriodOpen();
	}

	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_UYMediosPago (MAcctSchema[] ass, ResultSet rs, String trxName)
	{
		super (ass, MMediosPago.class, rs, null, trxName);
	}
	
	private int C_BankAccount_ID = 0;

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		// Creacion de Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// Bank Account Org
		int AD_Org_ID = getBank_Org_ID();	
		//OpenUp Nicolas Sarlabos issue #895 19/10/2011
		// Verifico si no es una carga inicial de saldo bancario
		if (header.isInitialLoad()){
			return new ArrayList<Fact>();
		}
		//fin OpenUp Nicolas Sarlabos issue #895 19/10/2011
		
		if (header.gettipomp().equalsIgnoreCase("TER")) return new ArrayList<Fact>();
		
		MMediosPago mpago = (MMediosPago)this.p_po;
		
		// Si el documento es Cheque
		if (getDocumentType().equals(DOCTYPE_Cheque))
		{
			//MMediosPago header = (MMediosPago)getPO(); ////OpenUp Nicolas Sarlabos issue #895 19/10/2011,el header se declara afuera

			// Si la moneda de la transaccion no es moneda nacional, debo contabilizar en moneda nacional con la tasa de cambio ingresada
			// manualmente por el usuario.
			boolean esMonedaExtranjera = false;
			
			MClient client = new MClient(getCtx(), getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();

			if (getC_Currency_ID()!=schema.getC_Currency_ID()){
				esMonedaExtranjera = true;
			}
			
			// Debitos
			MAccount acct = null;
			if (getC_Charge_ID() != 0)
				acct = MCharge.getAccount(getC_Charge_ID(), as, getAmount());
			else
				acct = getAccount(Doc.ACCTTYPE_PaymentSelect, as);

			FactLine fl = fact.createLine(null, acct,getC_Currency_ID(), getAmount(), null,
					mpago.getC_DocType_ID(), mpago.getCheckNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);		

			// Si es moneda extranjera tengo que actualizar el monto en pesos del asiento y la tasa de cambio
			if (esMonedaExtranjera){
				fl.setDivideRate((BigDecimal)header.get_Value("DivideRate"));

				fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
				fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));

				//fl.setAmtAcctDr(fl.getAmtSourceDr().divide(fl.getDivideRate(), 2, RoundingMode.HALF_UP));
				//fl.setAmtAcctCr(fl.getAmtSourceCr().divide(fl.getDivideRate(), 2, RoundingMode.HALF_UP));
			}
			
			if (MSysConfig.getBooleanValue("UY_CHECK_EMITTED_ACCT_BANK", true, getAD_Client_ID())){
				fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), getC_Currency_ID(), null, getAmount(),
						mpago.getC_DocType_ID(), mpago.getCheckNo());
			}
			else{
				fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankEmited, as), getC_Currency_ID(), null, getAmount(),
						mpago.getC_DocType_ID(), mpago.getCheckNo());
			}
			
			if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);

			// Si es moneda extranjera tengo que actualizar el monto en pesos del asiento y la tasa de cambio
			if (esMonedaExtranjera){
				fl.setDivideRate((BigDecimal)header.get_Value("DivideRate"));
				
				fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
				fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));

				//fl.setAmtAcctDr(fl.getAmtSourceDr().divide(fl.getDivideRate(), 2, RoundingMode.HALF_UP));
				//fl.setAmtAcctCr(fl.getAmtSourceCr().divide(fl.getDivideRate(), 2, RoundingMode.HALF_UP));
			}
		//OpenUp. Nicolas Sarlabos. 29/01/2016. #5389.	
		} else if (getDocumentType().equals(DOCTYPE_Banco_Transferencias)){
			
			boolean esMonedaExtranjera = false;
			
			MClient client = new MClient(getCtx(), getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();

			if (getC_Currency_ID()!=schema.getC_Currency_ID()){
				esMonedaExtranjera = true;
			}
			
			//DR
			MPaymentRule rule = MPaymentRule.forValue(getCtx(), "transferencia", getTrxName());
			int acctID = 0;
			
			if(rule!=null && rule.get_ID()>0){
				
				if(rule.isBankHandler()){
					
					acctID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from uy_paymentrule_acct where uy_paymentrule_id = " + rule.get_ID());					
					
				} else {
					
					MBankAccount account = (MBankAccount)this.header.getC_BankAccount();
					
					acctID = DB.getSQLValueEx(getTrxName(),"select b_asset_acct from c_bankaccount_acct where c_bankaccount_id = " + account.get_ID());					
					
				}
				
				FactLine fl = fact.createLine(null, MAccount.get(getCtx(), acctID),getC_Currency_ID(), getAmount(), null,
						mpago.getC_DocType_ID(), mpago.getCheckNo());
				if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);	
				
				// Si es moneda extranjera tengo que actualizar el monto en pesos del asiento y la tasa de cambio
				if (esMonedaExtranjera){
					fl.setDivideRate((BigDecimal)header.get_Value("DivideRate"));

					fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
					fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));

				}		
				
			}			
			
			//CR
			FactLine fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), getC_Currency_ID(), null, getAmount(),
					mpago.getC_DocType_ID(), mpago.getCheckNo());
			
			// Si es moneda extranjera tengo que actualizar el monto en pesos del asiento y la tasa de cambio
			if (esMonedaExtranjera){
				fl.setDivideRate((BigDecimal)header.get_Value("DivideRate"));

				fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
				fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));

			}		
			
		}
		//Fin OpenUp.
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

	@Override
	/**************************************************************************
	 *  Get Source Currency Balance - always zero
	 *  @return Zero (always balanced)
	 */
	public BigDecimal getBalance() {
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}

	@Override
	protected String loadDocumentDetails() {

		// Guardo y seteo informacion del modelo
		MMediosPago model = (MMediosPago)getPO();
		setDateDoc(model.getDateTrx());
		this.C_BankAccount_ID = model.getC_BankAccount_ID();
		setAmount(Doc.AMTTYPE_Gross, model.getPayAmt());
		return null;
	}

	/**
	 * 	Get AD_Org_ID from Bank Account
	 * 	@return AD_Org_ID or 0
	 */
	private int getBank_Org_ID ()
	{
		if (this.C_BankAccount_ID == 0)
			return 0;
		//
		MBankAccount ba = MBankAccount.get(getCtx(), this.C_BankAccount_ID);
		return ba.getAD_Org_ID();
	}	//	getBank_Org_ID

}
