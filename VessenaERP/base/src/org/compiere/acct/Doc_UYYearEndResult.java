package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MElementValue;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MYearEndResult;
import org.openup.model.MYearEndResultLine;

public class Doc_UYYearEndResult extends Doc{

	private MYearEndResult yearEndResult = null;
	
	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYYearEndResult(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYYearEndResult(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MYearEndResult.class, rs, null, trxName);
	}

	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MYearEndResult) getPO()).getDateAcct());
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	//@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		String description = "Resultados del Ejercicio";
		
		// Modelo desde PO
		this.yearEndResult = (MYearEndResult)this.getPO();	
		
		// Eschema contable
		MClient client = new MClient(getCtx(), this.yearEndResult.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		Timestamp fechaTC = TimeUtil.addDays(this.yearEndResult.getDateAcct(), 1); //para los calculos se toma la tasa de cambio del dia siguiente
		
		FactLine fl = null;

		/*******************************************************************************/
		// Proceso cuentas 4 y 5
		BigDecimal sumCredits = Env.ZERO;
		BigDecimal sumDebits = Env.ZERO;
		MYearEndResultLine [] resLines = this.getLines();
		for (int i = 0; i < resLines.length; i++){
			
			MYearEndResultLine rLine = resLines[i]; //instancio linea de resultado
			
			MAccount account = MAccount.forElementValue(getCtx(), rLine.getC_ElementValue_ID(),getTrxName());
			
			MElementValue value = (MElementValue)rLine.getC_ElementValue();
			
			// Obtengo diferencia entre suma de debitos y creditos de esta cuenta
			BigDecimal difference = rLine.getAmtSourceDr().subtract(rLine.getAmtSourceCr());
			
			// Si la diferencia es positiva entonces tengo mas debitos
			// y por lo tanto hago un asiento al credito por esta diferencia
			if (difference.compareTo(Env.ZERO) > 0) {
			
				// CR - Cuenta 4 y 5
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), null, difference);
				fl.setDescription(description);
				if (fl != null && this.yearEndResult.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndResult.getAD_Org_ID());
				
				if (value.getC_Currency_ID() != 0 && value.getC_Currency_ID() != schema.getC_Currency_ID()){
					
					BigDecimal divideRate = MConversionRate.getDivideRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					BigDecimal multiplyRate = MConversionRate.getRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					fl.setDivideRate(divideRate);
					fl.setUY_AmtNativeDr((fl.getAmtSourceDr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));
					fl.setUY_AmtNativeCr((fl.getAmtSourceCr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));					
					
				}
								
				// Acumulo creditos
				sumCredits = sumCredits.add(difference);
			}
			// Si la diferencia es negativa entonces tengo mas creditos
			// y por lo tanto hago un asiento al debito por esta diferencia
			else if (difference.compareTo(Env.ZERO) < 0){

				// DR  - Cuenta 4 y 5
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), difference.negate(), null);
				fl.setDescription(description);
				if (fl != null && this.yearEndResult.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndResult.getAD_Org_ID());
				
				if (value.getC_Currency_ID() != 0 && value.getC_Currency_ID() != schema.getC_Currency_ID()){
					
					BigDecimal divideRate = MConversionRate.getDivideRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					BigDecimal multiplyRate = MConversionRate.getRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					fl.setDivideRate(divideRate);
					fl.setUY_AmtNativeDr((fl.getAmtSourceDr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));
					fl.setUY_AmtNativeCr((fl.getAmtSourceCr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));					
					
				}
				
				// Acumulo debitos
				sumDebits = sumDebits.add(difference.negate());
			}
			
		}
		
		// Obtengo diferencia entre debitos y creditos sumarizados para cuentas 4 y 5
		BigDecimal differenceGainLoss = sumDebits.subtract(sumCredits);
		MAccount acctGainLoss = new MAccount(this.yearEndResult.getCtx(), schema.getUY_Account_LossGain_ID(), this.yearEndResult.get_TrxName());
		// Si esta diferencia es positiva entonces tengo mas debitos
		// y por lo tanto hago un asiento al credito con la cuenta de balanceo de perdidas y ganancias
		if (differenceGainLoss.compareTo(Env.ZERO) > 0) {
			// CR - Cuenta Balanceo Perdidas y Ganancias
			fl = fact.createLine(null, acctGainLoss, schema.getC_Currency_ID(), null, differenceGainLoss);
			fl.setDescription(description);
			if (fl != null && this.yearEndResult.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndResult.getAD_Org_ID());
		}
		// Si esta diferencia es negativa entonces tengo mas creditos
		// y por lo tanto hago un asiento al debito con la cuenta de balanceo de perdidas y ganancias
		else if (differenceGainLoss.compareTo(Env.ZERO) < 0) {
			// DR  - Cuenta Balanceo Perdidas y Ganancias
			fl = fact.createLine(null, acctGainLoss, schema.getC_Currency_ID(), differenceGainLoss.negate(), null);
			fl.setDescription(description);
			if (fl != null && this.yearEndResult.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndResult.getAD_Org_ID());
		}
		
		// Redondeo.
		fact.createRounding(yearEndResult.getC_DocType_ID(), yearEndResult.getDocumentNo(), 0, 0, 0, 0);
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;		
	
	}
	
	
	@Override
	public int getC_Currency_ID() {
		
		this.yearEndResult = (MYearEndResult)this.getPO();
		
		MClient client = new MClient(getCtx(), this.yearEndResult.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();

		return schema.getC_Currency_ID();	
	}

	/***
	 * Obtiene y retorna lineas de resultados del ejercicio
	 * OpenUp Ltda. Issue #861
	 * @author Nicolas Sarlabos - 28/05/2013
	 * @see
	 * @return
	 */
	private MYearEndResultLine[] getLines(){
		
		List <MYearEndResultLine> list = new ArrayList <MYearEndResultLine>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = " select uy_yearendresultline_id " +
				  " from uy_yearendresultline " +
			      " where uy_yearendresult_id = " + this.yearEndResult.get_ID();
			
			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MYearEndResultLine l = new MYearEndResultLine(getCtx(), rs.getInt("uy_yearendresultline_id"), getTrxName());
				list.add(l);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MYearEndResultLine[list.size()]);
	}
	
	

}
