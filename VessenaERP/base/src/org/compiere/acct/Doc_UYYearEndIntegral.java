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
import org.openup.model.MYearEndIntegral;
import org.openup.model.MYearEndIntegralLine;

public class Doc_UYYearEndIntegral extends Doc {

	private MYearEndIntegral yearEndIntegral = null;
	
	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYYearEndIntegral(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYYearEndIntegral(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MYearEndIntegral.class, rs, null, trxName);
	}

	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MYearEndIntegral) getPO()).getDateAcct());
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		Fact fact = new Fact(this, as, Fact.POST_Actual);
		String description = "Cierre de Cuentas Integrales";

		// Modelo desde PO
		this.yearEndIntegral = (MYearEndIntegral)this.getPO();	

		// Eschema contable
		MClient client = new MClient(getCtx(), this.yearEndIntegral.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		Timestamp fechaTC = TimeUtil.addDays(this.yearEndIntegral.getDateAcct(), 1); //para los calculos se toma la tasa de cambio del dia siguiente

		FactLine fl = null;

		/*******************************************************************************/
		// Proceso cuentas 1,2 y 3
		MYearEndIntegralLine [] intLines = this.getLines();
		for (int i = 0; i < intLines.length; i++){

			MYearEndIntegralLine iLine = intLines[i]; //instancio linea de cuenta

			MAccount account = MAccount.forElementValue(getCtx(), iLine.getC_ElementValue_ID(),getTrxName());
			
			MElementValue value = (MElementValue)iLine.getC_ElementValue();

			// Obtengo diferencia entre suma de debitos y creditos de esta cuenta
			BigDecimal difference = iLine.getAmtSourceDr().subtract(iLine.getAmtSourceCr());

			// Si la diferencia es positiva entonces tengo mas debitos
			// y por lo tanto hago un asiento al credito por esta diferencia
			if (difference.compareTo(Env.ZERO) > 0) {

				// CR
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), null, difference);
				fl.setDescription(description);
				if (fl != null && this.yearEndIntegral.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndIntegral.getAD_Org_ID());				
				
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

				// DR
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), difference.negate(), null);
				fl.setDescription(description);
				if (fl != null && this.yearEndIntegral.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndIntegral.getAD_Org_ID());
				
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
		fact.createRounding(yearEndIntegral.getC_DocType_ID(), yearEndIntegral.getDocumentNo(), 0, 0, 0, 0);

		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;		
	}

	@Override
	public int getC_Currency_ID() {
				
		this.yearEndIntegral = (MYearEndIntegral)this.getPO();
		
		MClient client = new MClient(getCtx(), this.yearEndIntegral.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();

		return schema.getC_Currency_ID();	
	}

	/***
	 * Obtiene y retorna lineas de cierre de cuentas integrales
	 * OpenUp Ltda. Issue #862
	 * @author Nicolas Sarlabos - 30/05/2013
	 * @see
	 * @return
	 */
	private MYearEndIntegralLine[] getLines(){
		
		List <MYearEndIntegralLine> list = new ArrayList <MYearEndIntegralLine>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = " select uy_yearendintegralline_id " +
				  " from uy_yearendintegralline " +
			      " where uy_yearendintegral_id = " + this.yearEndIntegral.get_ID();
			
			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MYearEndIntegralLine l = new MYearEndIntegralLine(getCtx(), rs.getInt("uy_yearendintegralline_id"), getTrxName());
				list.add(l);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MYearEndIntegralLine[list.size()]);
	}

}
