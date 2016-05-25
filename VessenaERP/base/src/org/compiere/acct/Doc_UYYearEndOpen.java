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
import org.openup.model.MYearEndOpen;
import org.openup.model.MYearEndOpenLine;

public class Doc_UYYearEndOpen extends Doc{

private MYearEndOpen yearEndOpen = null;
	
	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYYearEndOpen(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYYearEndOpen(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MYearEndOpen.class, rs, null, trxName);
	}

	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MYearEndOpen) getPO()).getDateAcct());
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
	
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		String description = "Apertura de Cuentas Integrales";
		
		// Modelo desde PO
		this.yearEndOpen = (MYearEndOpen)this.getPO();	
		
		// Eschema contable
		MClient client = new MClient(getCtx(), this.yearEndOpen.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		Timestamp fechaTC = TimeUtil.addDays(this.yearEndOpen.getDateAcct(), 1); //para los calculos se toma la tasa de cambio del dia siguiente
		
		FactLine fl = null;

		/*******************************************************************************/
		// Proceso cuentas 4 y 5
		MYearEndOpenLine [] oLines = this.getLines();
		for (int i = 0; i < oLines.length; i++){
			
			MYearEndOpenLine oLine = oLines[i]; //instancio linea de apertura
			
			MAccount account = MAccount.forElementValue(getCtx(), oLine.getC_ElementValue_ID(),getTrxName());
			
			MElementValue value = (MElementValue)oLine.getC_ElementValue();
			
			// Obtengo diferencia entre suma de debitos y creditos de esta cuenta
			BigDecimal difference = oLine.getAmtSourceDr().subtract(oLine.getAmtSourceCr());
			
			// Si la diferencia es positiva entonces tengo mas debitos
			// y por lo tanto hago un asiento al credito por esta diferencia
			if (difference.compareTo(Env.ZERO) > 0) {
			
				// CR - Cuenta 4 y 5
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), null, difference);
				fl.setDescription(description);
				if (fl != null && this.yearEndOpen.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndOpen.getAD_Org_ID());
				
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

				// DR  - Cuenta 4 y 5
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), difference.negate(), null);
				fl.setDescription(description);
				if (fl != null && this.yearEndOpen.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.yearEndOpen.getAD_Org_ID());	
				
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
		fact.createRounding(yearEndOpen.getC_DocType_ID(), yearEndOpen.getDocumentNo(), 0, 0, 0, 0);
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;		
	}
	
	@Override
	public int getC_Currency_ID() {
		
		this.yearEndOpen = (MYearEndOpen)this.getPO();
		
		MClient client = new MClient(getCtx(), this.yearEndOpen.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();

		return schema.getC_Currency_ID();	
	}

	/***
	 * Obtiene y retorna lineas de apertura de cuentas integrales
	 * OpenUp Ltda. Issue #861
	 * @author Nicolas Sarlabos - 30/05/2013
	 * @see
	 * @return
	 */
	private MYearEndOpenLine[] getLines(){
		
		List <MYearEndOpenLine> list = new ArrayList <MYearEndOpenLine>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = " select uy_yearendopenline_id " +
				  " from uy_yearendopenline " +
			      " where uy_yearendopen_id = " + this.yearEndOpen.get_ID();
			
			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MYearEndOpenLine l = new MYearEndOpenLine(getCtx(), rs.getInt("uy_yearendopenline_id"), getTrxName());
				list.add(l);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MYearEndOpenLine[list.size()]);
	}

}
