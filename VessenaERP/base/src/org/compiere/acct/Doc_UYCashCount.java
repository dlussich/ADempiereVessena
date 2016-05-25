/**
 * 
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MCashConfig;
import org.openup.model.MCashCount;
import org.openup.model.MPaymentRule;

/**
 * @author Nicolás
 *
 */
public class Doc_UYCashCount extends Doc {
	
	private MCashCount header = null;
	private String description = "Arqueo de Cajas";

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYCashCount(MAcctSchema[] ass, Class<?> clazz, ResultSet rs, String defaultDocumentType, String trxName) {
		super(ass, clazz, rs, defaultDocumentType, trxName);
		// TODO Auto-generated constructor stub
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
	public Doc_UYCashCount(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MCashCount.class, rs, null, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MCashCount) getPO()).getDateAction());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		
		String sql = "";
		BigDecimal amt = Env.ZERO;
	
		MCashConfig config = MCashConfig.forClient(getCtx(), null);
		
		if(config==null) throw new AdempiereException("No se pudo obtener parametros de caja");
		
		Fact fact = new Fact(this, as, Fact.POST_Actual);	
		int acctID = 0;
		
		// Modelo desde PO
		this.header = (MCashCount)this.getPO();	

		// Eschema contable
		MClient client = new MClient(getCtx(), this.header.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		FactLine fl = null;
		
		//1-Proceso cuentas bancarias de depositos efectivo
		this.procesarDepositos(fact, schema);
		
		//2-Proceso efectivo final de cajas
		if(header.getAmtCajas2().compareTo(Env.ZERO)!=0){
			
			//obtengo cuenta contable desde parametros de caja
			acctID = config.getCashCountCashier_Acct();
			
			if(acctID > 0){
				
				// DR
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), header.getAmtCajas2(), null);
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());		
				
			}			
		}	
		
		//3-Proceso creditos dados - creditos cobrados
		//obtengo cuenta contable en $ desde parametros de caja
		acctID = config.getCashCountCredit_Acct();
		
		if(acctID > 0){
			
			fl = null;
			
			BigDecimal diff = header.getamtcredito().subtract(header.getamtcobrocredito());
			
			if(diff.compareTo(Env.ZERO) > 0){
				
				// DR
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), diff, null);
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());			
				
			} else if (diff.compareTo(Env.ZERO) < 0) {
				
				// CR
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, diff.negate());
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());				
			}		
		}
		
		//obtengo cuenta contable en U$ desde parametros de caja
		acctID = config.getCashCountCredit_Acct_1();
		
		if(acctID > 0){
			
			fl = null;
			
			BigDecimal diff = header.getamtcredito2().subtract(header.getamtcobrocredito2());
			
			if(diff.compareTo(Env.ZERO) > 0){
				
				// DR
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), diff, null);
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());			
				
			} else if (diff.compareTo(Env.ZERO) < 0) {
				
				// CR
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, diff.negate());
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());				
			}	
			
			if (fl != null) {
				if (100 != schema.getC_Currency_ID()){
					if (header.getCurrencyRate() != null){
						if (header.getCurrencyRate().compareTo(Env.ZERO) > 0){
							fl.setDivideRate(header.getCurrencyRate());
							fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
							fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
						}
					}
				}
			}			
		}
		
		//4-Proceso tarjeta de credito
		MPaymentRule rule = MPaymentRule.forValue(getCtx(), "tarjeta", getTrxName());
		
		if(rule!=null && rule.get_ID()>0){
			
			fl = null;

			//tarjeta $
			if(header.getamtvtatarjeta().compareTo(Env.ZERO)!=0){

				acctID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from uy_paymentrule_acct where uy_paymentrule_id = " + rule.get_ID());

				if(acctID > 0){

					// DR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), header.getamtvtatarjeta(), null);
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());				
				}				
			}

			//tarjeta U$
			if(header.getamtvtatarjeta2().compareTo(Env.ZERO)!=0){

				acctID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from uy_paymentruleme_acct where c_currency_id = 100 and uy_paymentrule_id = " + rule.get_ID());

				if(acctID > 0){

					// DR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), header.getamtvtatarjeta2(), null);
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());				
				}	
				
				if (fl != null) {
					if (100 != schema.getC_Currency_ID()){
						if (header.getCurrencyRate() != null){
							if (header.getCurrencyRate().compareTo(Env.ZERO) > 0){
								fl.setDivideRate(header.getCurrencyRate());
								fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
								fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
							}
						}
					}
				}			
			}			
			
		}
		
		//5-Proceso Luncheon Ticket
		sql = "select coalesce(sum(r.amount2),0)" +
				" from uy_cashcountpayrule r" +
				" inner join uy_paymentrule pr on r.uy_paymentrule_id = pr.uy_paymentrule_id" +
				" where r.uy_cashcount_id = " + header.get_ID() +
				" and pr.value = 'luncheon' and r.c_currency_id = 142";
		
		amt = DB.getSQLValueBDEx(getTrxName(), sql);
		
		if(amt.compareTo(Env.ZERO)!=0){
			
			fl = null;
			
			rule = MPaymentRule.forValue(getCtx(), "luncheon", getTrxName());
			
			if(rule!=null && rule.get_ID()>0){
			
				acctID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from uy_paymentrule_acct where uy_paymentrule_id = " + rule.get_ID());

				if(acctID > 0){

					// DR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), amt, null);
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());				
				}
			
			}
			
		}
		
		//6-Proceso Sodexo
		sql = "select coalesce(sum(r.amount2),0)" +
				" from uy_cashcountpayrule r" +
				" inner join uy_paymentrule pr on r.uy_paymentrule_id = pr.uy_paymentrule_id" +
				" where r.uy_cashcount_id = " + header.get_ID() +
				" and pr.value = 'sodexo' and r.c_currency_id = 142";
		
		amt = DB.getSQLValueBDEx(getTrxName(), sql);
		
		if(amt.compareTo(Env.ZERO)!=0){
			
			fl = null;
			
			rule = MPaymentRule.forValue(getCtx(), "sodexo", getTrxName());
			
			if(rule!=null && rule.get_ID()>0){
			
				acctID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from uy_paymentrule_acct where uy_paymentrule_id = " + rule.get_ID());

				if(acctID > 0){

					// DR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), amt, null);
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());				
				}
			
			}
			
		}
		
		//7-Proceso devolucion de envases
		BigDecimal amtEnvases = header.getamtvtadevenvases().add(header.getQuebranto());
		
		if(amtEnvases.compareTo(Env.ZERO)!=0){
			
			fl = null;
			
			//obtengo cuenta contable desde parametros de caja
			acctID = config.getCashCountEnvase_Acct();
			
			if(acctID > 0){
				
				// DR
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), amtEnvases, null);
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());			
			}		
			
		}
		
		//8-Proceso Totales por Tipo de Impuesto
		this.procesarTotalesImpuesto(fact, schema);

		//9-Proceso servicios cobrados
		if(header.getamtvtapagodeservicio().compareTo(Env.ZERO)!=0){
			
			fl = null;

			//obtengo cuenta contable desde parametros de caja
			acctID = config.getCashCountService_Acct();

			if(acctID > 0){

				// CR
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getamtvtapagodeservicio());
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());			
			}		

		}
		
		//10-Proceso cuenta contable de Caja Administracion
		this.procesarCobranzas(fact, schema);		
		
		//11-Proceso lineas de diferencia
		//para $ y U$
		this.procesarDiferencia(fact, 142, config);
		this.procesarDiferencia(fact, 100, config);
		
		// Redondeo.
		fact.createRounding(header.getC_DocType_ID(), header.getDocumentNo(), 0, 0, 0, 0);

		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}

	/***
	 * Procesa lineas de depositos en efectivo de remesas
	 * OpenUp Ltda. Issue #5258
	 * @author Nicolas Sarlabos - 13/01/2016
	 * @see
	 * @return
	 */
	private void procesarDepositos(Fact fact, MAcctSchema schema) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = "select b.c_bankaccount_id, coalesce(sum(bl.amount),0) as amount" +
					" from uy_cashremitbankline bl" +
					" inner join uy_cashremitbank b on bl.uy_cashremitbank_id = b.uy_cashremitbank_id" +
					" inner join uy_cashremittance r on b.uy_cashremittance_id = r.uy_cashremittance_id" +
					" inner join c_bankaccount ba on b.c_bankaccount_id = ba.c_bankaccount_id" +
					" where r.docstatus = 'CO' and date_trunc ('day', r.datetrx) = '" + header.getDateAction() + "'" +
					" group by b.c_bankaccount_id" +
					" order by b.c_bankaccount_id";

			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();

			while(rs.next()){

				FactLine fl = null;

				MBankAccount account = new MBankAccount(getCtx(),rs.getInt("c_bankaccount_id"),getTrxName());
				BigDecimal amount = rs.getBigDecimal("amount");

				//obtengo cuenta contable desde la cuenta bancaria
				int acctID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from c_bankaccount_acct where c_bankaccount_id = " + account.get_ID());

				if(acctID <= 0) throw new AdempiereException ("No se obtuvo Cuenta de Bancos para la cuenta bancaria '" + account.getDescription() + "'");	
				
				
				// Santiago Evans 02/03/2016 Issue #5564
				//No se tiene que convertir a dolares el source amount
				//
				
				// DR
				if(account.getC_Currency_ID()!=schema.getC_Currency_ID()){
					
					this.setIsMultiCurrency(true);					
					
				}
				
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), amount, null);
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());			

				if (fl != null) {
					if (account.getC_Currency_ID() != schema.getC_Currency_ID()){
						if (header.getCurrencyRate() != null){
							if (header.getCurrencyRate().compareTo(Env.ZERO) > 0){
								fl.setDivideRate(header.getCurrencyRate());
								fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
								fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
							}
						}
					}
				}
				
			}

		} catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	
	/***
	 * Procesa totales por tipo de impuesto
	 * OpenUp Ltda. Issue #5258
	 * @author Nicolas Sarlabos - 13/01/2016
	 * @see
	 * @return
	 */
	private void procesarTotalesImpuesto(Fact fact, MAcctSchema schema) {
		
		FactLine fl = null;
		MTax tax = null;
		int acctID = 0;
		
		//Exento
		tax = MTax.forValue(getCtx(), "exento", getTrxName());
		
		if(tax!=null && tax.get_ID()>0){
			
			if(header.getAmtVentasExento().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_receivables_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getAmtVentasExento());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}

			if(header.getIvaVentasExento().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_due_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getIvaVentasExento());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}			
		}
		
		//Basico
		tax = MTax.forValue(getCtx(), "basico", getTrxName());

		if(tax!=null && tax.get_ID()>0){

			if(header.getAmtVentasBasico().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_receivables_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getAmtVentasBasico());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}

			if(header.getIvaVentasBasico().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_due_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getIvaVentasBasico());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}			
		}

		//Minimo
		tax = MTax.forValue(getCtx(), "minimo", getTrxName());

		if(tax!=null && tax.get_ID()>0){

			if(header.getAmtVentasMinimo().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_receivables_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getAmtVentasMinimo());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}

			if(header.getIvaVentasMinimo().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_due_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getIvaVentasMinimo());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}			
		}

		//Percibido Carniceria
		tax = MTax.forValue(getCtx(), "percibidocarni", getTrxName());

		if(tax!=null && tax.get_ID()>0){

			if(header.getAmtVentasPercibido().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_receivables_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getAmtVentasPercibido());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}

			if(header.getIvaVentasPercibido().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_due_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getIvaVentasPercibido());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}			
		}
		
		//Vegetales Minimo
		tax = MTax.forValue(getCtx(), "vegetalminimo", getTrxName());

		if(tax!=null && tax.get_ID()>0){

			if(header.getAmtVentasVegetales().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_receivables_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getAmtVentasVegetales());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}

			if(header.getIvaVentasVegetales().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_due_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getIvaVentasVegetales());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}			
		}
		
		//Carniceria 22
		tax = MTax.forValue(getCtx(), "carniceria22", getTrxName());

		if(tax!=null && tax.get_ID()>0){

			if(header.getAmtVentasCarnes2().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_receivables_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getAmtVentasCarnes2());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}

			if(header.getIvaVentasCarnes2().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_due_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getIvaVentasCarnes2());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}			
		}

		//Carniceria 10
		tax = MTax.forValue(getCtx(), "carniceria10", getTrxName());

		if(tax!=null && tax.get_ID()>0){

			if(header.getAmtVentasCarnes().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_receivables_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getAmtVentasCarnes());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}

			if(header.getIvaVentasCarnes().compareTo(Env.ZERO)!=0){
				
				fl = null;

				//obtengo cuenta contable desde contabilidad del impuesto
				acctID = DB.getSQLValueEx(getTrxName(), "select t_due_acct from c_tax_acct where c_tax_id = " + tax.get_ID());

				if(acctID > 0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), schema.getC_Currency_ID(), null, header.getIvaVentasCarnes());
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());						
				}				
			}			
		}

	}
	
	/***
	 * Procesa lineas de cobranzas en efectivo de remesas
	 * OpenUp Ltda. Issue #5258
	 * @author Nicolas Sarlabos - 14/01/2016
	 * @see
	 * @return
	 */
	private void procesarCobranzas(Fact fact, MAcctSchema schema) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = "select rc.c_bankaccount_id, coalesce(sum(rc.amount),0) as amount, rc.c_bankaccount_id_1" +
					" from uy_cashremittancecharge rc" +
					" inner join uy_cashremittance r on rc.uy_cashremittance_id = r.uy_cashremittance_id" +
					" where r.docstatus = 'CO' and date_trunc ('day', r.datetrx) = '" + header.getDateAction() + "'" +
					" group by rc.c_bankaccount_id, rc.c_bankaccount_id_1";

			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();

			while(rs.next()){

				FactLine fl = null;

				int cuentaDestinoID = rs.getInt("c_bankaccount_id_1");
				BigDecimal amount = rs.getBigDecimal("amount");
				MBankAccount account = null;

				if(cuentaDestinoID > 0){

					account = new MBankAccount(getCtx(),cuentaDestinoID,getTrxName());

				} else account = new MBankAccount(getCtx(),rs.getInt("c_bankaccount_id"),getTrxName());
				
				//obtengo cuenta contable desde la cuenta bancaria
				int acctID = DB.getSQLValueEx(getTrxName(), "select b_asset_acct from c_bankaccount_acct where c_bankaccount_id = " + account.get_ID());

				if(acctID <= 0) throw new AdempiereException ("No se obtuvo Cuenta de Bancos para la cuenta bancaria '" + account.getDescription() + "'");	

				// CR
				fl = fact.createLine(null, MAccount.get(getCtx(), acctID), account.getC_Currency_ID(), null, amount);
				fl.setDescription(description);
				if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());			

				if (fl != null) {
					if (account.getC_Currency_ID() != schema.getC_Currency_ID()){
						if (header.getCurrencyRate() != null){
							if (header.getCurrencyRate().compareTo(Env.ZERO) > 0){
								fl.setDivideRate(header.getCurrencyRate());
								fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
								fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
							}
						}
					}
				}							

			}

		} catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	
	/***
	 * Procesa lineas de diferencia.
	 * OpenUp Ltda. Issue #5258
	 * @author Nicolas Sarlabos - 14/01/2016
	 * @see
	 * @return
	 */
	private void procesarDiferencia(Fact fact, int currID, MCashConfig config ) {

		String sql = "";
		FactLine fl = null;
		BigDecimal total = Env.ZERO;
		int acctID = 0;

		try{

			sql = "select coalesce(sum(l.amount2),0)" +
					" from uy_cashcountline l" +
					" inner join uy_cashcountpayrule r on l.uy_cashcountpayrule_id = r.uy_cashcountpayrule_id" +
					" where r.c_currency_id = " + currID + " and r.uy_cashcount_id = " + this.header.get_ID();
			
			BigDecimal amt = DB.getSQLValueBDEx(null, sql);
			
			if(currID==142){
				
				total = (amt.subtract(this.header.getamtventas())).add(this.header.getamtvtadevenvases().add(this.header.getQuebranto()));
				acctID = config.getCashCountDiff_Acct();	
				
			} else {
				
				total = amt.subtract(this.header.getamtventas2());
				acctID = config.getCashCountDiff_Acct_1();				
			}			

			if(acctID>0){

				if(total.compareTo(Env.ZERO)<0){

					// DR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), currID, total, null);
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());

				} else if(total.compareTo(Env.ZERO)>0){

					// CR
					fl = fact.createLine(null, MAccount.get(getCtx(), acctID), currID, null, total);
					fl.setDescription(description);
					if (fl != null && this.header.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.header.getAD_Org_ID());

				}

			}	

		} catch(Exception e) {
			throw new AdempiereException(e);

		} 
	}


}
