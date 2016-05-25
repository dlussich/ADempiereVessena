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
import org.openup.model.MYearEndAcumulate;
import org.openup.model.MYearEndAcumulateLine;

public class Doc_UYYearEndAcumulate extends Doc{

	private MYearEndAcumulate yearEndAcumulate = null;
	
	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYYearEndAcumulate(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYYearEndAcumulate(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MYearEndAcumulate.class, rs, null, trxName);
	}

	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MYearEndAcumulate) getPO()).getDateAcct());
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	@Override
	public int getC_Currency_ID() {

		this.yearEndAcumulate = (MYearEndAcumulate)this.getPO();
		
		MClient client = new MClient(getCtx(), this.yearEndAcumulate.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();

		return schema.getC_Currency_ID();		
		
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		String description = "Resultados Acumulados";
		
		// Modelo desde PO
		this.yearEndAcumulate = (MYearEndAcumulate)this.getPO();	
		
		// Eschema contable
		MClient client = new MClient(getCtx(), this.yearEndAcumulate.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		Timestamp fechaTC = TimeUtil.addDays(this.yearEndAcumulate.getDateAcct(), 1); //para los calculos se toma la tasa de cambio del dia siguiente
		
		FactLine fl = null;

		MAccount acctResult = new MAccount(this.yearEndAcumulate.getCtx(), schema.getUY_Account_Result_ID(), this.yearEndAcumulate.get_TrxName());
		MYearEndAcumulateLine [] aLines = this.getLines();
		for (int i = 0; i < aLines.length; i++){
			
			MYearEndAcumulateLine aLine = aLines[i]; //instancio linea de resultado
			MAccount account = MAccount.forElementValue(getCtx(), aLine.getC_ElementValue_ID(),getTrxName());
			MElementValue value = (MElementValue)aLine.getC_ElementValue();
			
			// Obtengo diferencia entre suma de debitos y creditos de esta cuenta
			BigDecimal difference = aLine.getAmtSourceDr().subtract(aLine.getAmtSourceCr());
			
			// Si la diferencia es positiva entonces tengo mas debitos
			// y por lo tanto hago un asiento al credito por esta diferencia
			if (difference.compareTo(Env.ZERO) > 0) {
			
				// CR - Cuenta Balanceo Perdidas y Ganancias
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), null, difference);
				fl.setDescription(description);
				if (fl != null && this.yearEndAcumulate.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndAcumulate.getAD_Org_ID());
				
				if (value.getC_Currency_ID() != 0 && value.getC_Currency_ID() != schema.getC_Currency_ID()){
					
					BigDecimal divideRate = MConversionRate.getDivideRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					BigDecimal multiplyRate = MConversionRate.getRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					fl.setDivideRate(divideRate);
					fl.setUY_AmtNativeDr((fl.getAmtSourceDr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));
					fl.setUY_AmtNativeCr((fl.getAmtSourceCr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));					
					
				}

				// DR - Cuenta Resultado
				fl = fact.createLine(null, acctResult, schema.getC_Currency_ID(), difference, null);
				fl.setDescription(description);
				if (fl != null && this.yearEndAcumulate.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndAcumulate.getAD_Org_ID());
				
				if (value.getC_Currency_ID() != 0 && value.getC_Currency_ID() != schema.getC_Currency_ID()){
					
					BigDecimal divideRate = MConversionRate.getDivideRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					BigDecimal multiplyRate = MConversionRate.getRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					fl.setDivideRate(divideRate);
					fl.setUY_AmtNativeDr((fl.getAmtSourceDr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));
					fl.setUY_AmtNativeCr((fl.getAmtSourceCr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));					
					
				}
				
			}
			// Si la diferencia es negativa entonces tengo mas creditos
			// y por lo tanto hago un asiento al debito por esta diferencia
			else if (difference.compareTo(Env.ZERO) < 0){

				// DR  - Cuenta Balanceo Perdidas y Ganancias
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), difference.negate(), null);
				fl.setDescription(description);
				if (fl != null && this.yearEndAcumulate.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndAcumulate.getAD_Org_ID());
				
				if (value.getC_Currency_ID() != 0 && value.getC_Currency_ID() != schema.getC_Currency_ID()){
					
					BigDecimal divideRate = MConversionRate.getDivideRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					BigDecimal multiplyRate = MConversionRate.getRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					fl.setDivideRate(divideRate);
					fl.setUY_AmtNativeDr((fl.getAmtSourceDr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));
					fl.setUY_AmtNativeCr((fl.getAmtSourceCr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));					
					
				}

				// CR - Cuenta Resultado
				fl = fact.createLine(null, acctResult, schema.getC_Currency_ID(), null, difference.negate());
				fl.setDescription(description);
				if (fl != null && this.yearEndAcumulate.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndAcumulate.getAD_Org_ID());
				
				if (value.getC_Currency_ID() != 0 && value.getC_Currency_ID() != schema.getC_Currency_ID()){
					
					BigDecimal divideRate = MConversionRate.getDivideRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					BigDecimal multiplyRate = MConversionRate.getRate(schema.getC_Currency_ID(), value.getC_Currency_ID(), fechaTC, 0, this.getAD_Client_ID(), this
							.getAD_Org_ID());
					
					fl.setDivideRate(divideRate);
					fl.setUY_AmtNativeDr((fl.getAmtSourceDr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));
					fl.setUY_AmtNativeCr((fl.getAmtSourceCr().multiply(multiplyRate)).setScale(2, RoundingMode.HALF_UP));				
					
				}
			}			
		}
		
		// Redondeo.
		fact.createRounding(yearEndAcumulate.getC_DocType_ID(), yearEndAcumulate.getDocumentNo(), 0, 0, 0, 0);
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;	
	}
	
	/***
	 * Obtiene y retorna lineas de resultados acumulados
	 * OpenUp Ltda. Issue #863
	 * @author Nicolas Sarlabos - 31/05/2013
	 * @see
	 * @return
	 */
	private MYearEndAcumulateLine[] getLines(){
		
		List <MYearEndAcumulateLine> list = new ArrayList <MYearEndAcumulateLine>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = " select uy_yearendacumulateline_id " +
				  " from uy_yearendacumulateline " +
			      " where uy_yearendacumulate_id = " + this.yearEndAcumulate.get_ID();
			
			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MYearEndAcumulateLine l = new MYearEndAcumulateLine(getCtx(), rs.getInt("uy_yearendacumulateline_id"), getTrxName());
				list.add(l);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MYearEndAcumulateLine[list.size()]);
	}

}
