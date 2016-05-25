/**
 * 
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaDefault;
import org.compiere.model.MActivity;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MHREmployee;
import org.openup.model.MCategoriaCCostos;
import org.openup.model.MTRClearing;
import org.openup.model.MTRClearingLine;
import org.openup.model.MTRDriverCash;

/**
 * @author Nicolas
 *
 */
public class Doc_UYTRClearing extends Doc{

	private MTRClearing clearing = null;
	
	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYTRClearing(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYTRClearing(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MTRClearing.class, rs, null, trxName);
	}

	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MTRClearing) getPO()).getDateAcct());
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
	
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		FactLine fl = null;
		MBankAccount bAccount = null;
		MAccount account = null;
		int accountID = 0;
		
		// Modelo desde PO
		this.clearing = (MTRClearing)this.getPO();	

		// Eschema contable
		MClient client = new MClient(getCtx(), this.clearing.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		//obtengo linea de liquidacion(siempre 1 sola)
		MTRClearingLine line = MTRClearingLine.forHdrAndCurrency(getCtx(), this.clearing.get_ID(), this.clearing.getC_Currency_ID(), getTrxName());		
		
		//se procesa linea de devolucion a caja
		if(line.getAmount2().compareTo(Env.ZERO)>0){
			if(line.getC_BankAccount_ID()>0){
				
				bAccount = (MBankAccount)line.getC_BankAccount();
				
				//obtengo cuenta contable desde la cuenta bancaria
				accountID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from c_bankaccount_acct where c_bankaccount_id = " + line.getC_BankAccount_ID());
				
				if(accountID <= 0) throw new AdempiereException ("No se obtuvo Cuenta de Bancos para la cuenta bancaria '" + bAccount.getDescription() + "'");
				
				account = MAccount.get(getCtx(), accountID);	
				
				fl = fact.createLine(null, account, line.getC_Currency_ID(), line.getAmount2(), null);		
					
			}			
		}
		
		//se procesa linea de adelanto de sueldo
		if(line.getAmount3().compareTo(Env.ZERO)>0){
			
			MAcctSchemaDefault schemaDef = schema.getAcctSchemaDefault();
			account = new MAccount(getCtx(), schemaDef.getE_Prepayment_Acct(), getTrxName()); //obtengo cuenta de adelantos a empleados
			
			if(account!=null && account.get_ID()>0){
				
				//obtengo empleado desde el chofer
				MBPartner partner = MBPartner.forDriver(getCtx(), this.clearing.getUY_TR_Driver_ID(), getTrxName());
				
				if(partner!=null && partner.get_ID()>0){
					
					if(partner.isEmployee()){
						
						MHREmployee emp = MHREmployee.getActiveEmployee(getCtx(), partner.get_ID(), getTrxName());
						
						if(emp!=null && emp.get_ID()>0){
							
							if(emp.getC_Activity_ID()>0){
								
								fl = fact.createLine(null, account, line.getC_Currency_ID(), line.getAmount3(), null);
								fl.setC_BPartner_ID(partner.get_ID());
								
								//seteo centro de costos
								MActivity cc = (MActivity)emp.getC_Activity();
								
								MCategoriaCCostos catCC = new MCategoriaCCostos(getCtx(), cc.getUY_Categoria_CCostos_ID(), getTrxName());
								
								String catValue = catCC.getValue();
								
								if(catValue.equalsIgnoreCase("1")){
									
									fl.setC_Activity_ID_1(cc.get_ID());

								} else if(catValue.equalsIgnoreCase("2")){
									
									fl.setC_Activity_ID_2(cc.get_ID());

								} else if(catValue.equalsIgnoreCase("3")){
									
									fl.setC_Activity_ID_3(cc.get_ID());
								}								
										
								if (fl != null && this.clearing.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.clearing.getAD_Org_ID());	
								
							} else throw new AdempiereException ("No se obtuvo Centro de Costos del empleado");							
							
							
						} else throw new AdempiereException ("No se obtuvieron Datos Laborales del empleado");						
						
						
					} else throw new AdempiereException ("El socio de negocio asociado al conductor no es un Empleado");		
					
					
				} else throw new AdempiereException ("No se obtuvo el empleado asociado al conductor de esta liquidacion");			
				
				
			} else throw new AdempiereException ("No se obtuvo Cuenta de Sueldos a Pagar");
			
			
		}
		
		//se procesa importe de gastos de viaje
		if(line.getExpenseAmt().compareTo(Env.ZERO)>0){
			
			//obtengo cuenta contable del esquema
			accountID = DB.getSQLValueEx(getTrxName(), "select p_expense_acct from c_acctschema_default");
			
			if(accountID>0){
				
				account = MAccount.get(getCtx(), accountID);
				
				fl = fact.createLine(null, account, line.getC_Currency_ID(), line.getExpenseAmt(), null);				
				
			} else throw new AdempiereException ("No se obtuvo cuenta de Gasto de Viaje");			
			
		}
		
		//se procesa importe de anticipos al chofer
		if(line.getAmount().compareTo(Env.ZERO)>0){
			
			//obtengo caja del empleado para empleado y moneda actuales
			MTRDriverCash cash = MTRDriverCash.forDriverCurrency(getCtx(), this.clearing.getUY_TR_Driver_ID(), line.getC_Currency_ID(), getTrxName());
			
			if(cash != null){
				
				bAccount = (MBankAccount)cash.getC_BankAccount();
				
				//obtengo cuenta contable desde la cuenta bancaria
				accountID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from c_bankaccount_acct where c_bankaccount_id = " + bAccount.get_ID());
				
				if(accountID <= 0) throw new AdempiereException ("No se obtuvo Cuenta de Bancos para la cuenta bancaria '" + bAccount.getDescription() + "'");
				
				account = MAccount.get(getCtx(), accountID);
				
				//obtengo empleado desde el chofer
				MBPartner partner = MBPartner.forDriver(getCtx(), this.clearing.getUY_TR_Driver_ID(), getTrxName());
				
				if(partner!=null && partner.get_ID()>0){
					
					if(partner.isEmployee()){
						
						MHREmployee emp = MHREmployee.getActiveEmployee(getCtx(), partner.get_ID(), getTrxName());
						
						if(emp!=null && emp.get_ID()>0){
							
							if(emp.getC_Activity_ID()>0){
								
								fl = fact.createLine(null, account, line.getC_Currency_ID(), null, line.getAmount());
								fl.setC_BPartner_ID(partner.get_ID());
								
								//seteo centro de costos
								MActivity cc = (MActivity)emp.getC_Activity();
								
								MCategoriaCCostos catCC = new MCategoriaCCostos(getCtx(), cc.getUY_Categoria_CCostos_ID(), getTrxName());
								
								String catValue = catCC.getValue();
								
								if(catValue.equalsIgnoreCase("1")){
									
									fl.setC_Activity_ID_1(cc.get_ID());

								} else if(catValue.equalsIgnoreCase("2")){
									
									fl.setC_Activity_ID_2(cc.get_ID());

								} else if(catValue.equalsIgnoreCase("3")){
									
									fl.setC_Activity_ID_3(cc.get_ID());
								}									
									
								if (fl != null && this.clearing.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.clearing.getAD_Org_ID());
								
							} else throw new AdempiereException ("No se obtuvo Centro de Costos del empleado");											
							
						} else throw new AdempiereException ("No se obtuvieron Datos Laborales del empleado");										
						
					} else throw new AdempiereException ("El socio de negocio asociado al conductor no es un Empleado");						
					
				} else throw new AdempiereException ("No se obtuvo el empleado asociado al conductor de esta liquidacion");					
				
			} else throw new AdempiereException ("No se obtuvo la caja para chofer y moneda actuales");		
			
		}
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}

}
