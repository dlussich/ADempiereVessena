package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.acct.FactLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_Fact_Acct;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class FactAcctValidator implements ModelValidator {

	private int m_AD_Client_ID = 0;
	
	@Override
	public String docValidate(PO po, int timing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();
		engine.addModelChange(X_Fact_Acct.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception {

		if (po.get_TableName().equals("Fact_Acct")) {
			if (type == TYPE_BEFORE_NEW) {

				FactLine facct = (FactLine)po;
				
				// Moneda de la cuenta de la linea contable
				facct.setUY_AccNat_Currency_ID(new BigDecimal(this.getAccountCurrency(facct)));

				// Moneda nacional 
				int idMonedaNacional = this.getIDMonedaNacional(facct.getAD_Client_ID());
				
				// Si la moneda desde y hasta son distintas
				if (facct.getC_Currency_ID() != facct.getUY_AccNat_Currency_ID().intValueExact()){
					
					// Si la moneda nativa de la cuenta es moneda nacional
					if (facct.getUY_AccNat_Currency_ID().intValueExact()==idMonedaNacional){
						// Tasa de cambio muliplicadora	
						facct.setDivideRate(MConversionRate.getRate(facct.getC_Currency_ID(), facct.getUY_AccNat_Currency_ID().intValueExact(), facct.getDateAcct(), 0, facct.getAD_Client_ID(), facct.getAD_Org_ID()));
						facct.setUY_AmtNativeCr(facct.getAmtAcctCr());
						facct.setUY_AmtNativeDr(facct.getAmtAcctDr());					
					}
					else{
						// Si no vengo con tasa de cambio manual
						if (facct.getDivideRate()==null){
							// Tasa de cambio divisora
							facct.setDivideRate(MConversionRate.getDivideRate(facct.getC_Currency_ID(), facct.getUY_AccNat_Currency_ID().intValueExact(), facct.getDateAcct(), 0, facct.getAD_Client_ID(), facct.getAD_Org_ID()));
						}
						if (facct.getDivideRate().compareTo(Env.ZERO)>0){
							// Montos en moneda nativa de la cuenta segun tasa de cambio
							facct.setUY_AmtNativeCr(facct.getAmtSourceCr().divide(facct.getDivideRate(),2, RoundingMode.HALF_UP));
							facct.setUY_AmtNativeDr(facct.getAmtSourceDr().divide(facct.getDivideRate(),2, RoundingMode.HALF_UP));					
						}
					}
				}
				else{
					// Mismas monedas, entonces tasa de cambio = 1
					facct.setDivideRate(new BigDecimal(1));
					facct.setUY_AmtNativeCr(facct.getAmtSourceCr());
					facct.setUY_AmtNativeDr(facct.getAmtSourceDr());					
				}
			}			
		}
		return null;
	}

	private int getAccountCurrency(FactLine facct){

		String mysql=" SELECT isforeigncurrency,c_currency_id "  +
					 " FROM c_elementvalue " +
					 " WHERE c_elementvalue_id=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value=142;
		try
		{
			pstmt = DB.prepareStatement (mysql, facct.get_TrxName());
			
			pstmt.setInt(1, facct.getAccount_ID());
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				if (rs.getString("isforeigncurrency").equalsIgnoreCase("Y")){
					value = rs.getInt("c_currency_id");
				}
			}
		
 		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}

	
	/* Obtiene id de moneda nacional para la empresa actual*/
	private Integer getIDMonedaNacional(Integer idEmpresa){

		MClient client = new MClient(Env.getCtx(), idEmpresa, null);
		MAcctSchema schema = client.getAcctSchema();

		return schema.getC_Currency_ID();
	}

}
