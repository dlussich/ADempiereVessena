/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 04/11/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConversionRate;
import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * org.openup.model - MPOPolicy
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 04/11/2012
 * @see
 */
public class MPOPolicy extends X_UY_POPolicy {

	/**
	 * 
	 */
	private static final long serialVersionUID = -956746811345489323L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_POPolicy_ID
	 * @param trxName
	 */
	public MPOPolicy(Properties ctx, int UY_POPolicy_ID, String trxName) {
		super(ctx, UY_POPolicy_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPOPolicy(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Obtiene y retorna rango de politica segun monto y moneda recibido.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 04/11/2012
	 * @see
	 * @param dateTrx
	 * @param amt
	 * @param cCurrencyID
	 * @param currencyRate
	 * @return
	 */
	public MPOPolicyRange getRangeForAmount(Timestamp dateTrx, BigDecimal amt, int cCurrencyID, BigDecimal currencyRate){
		
		BigDecimal amtRange = amt;
		
		MPOPolicyRange value = null;
		
		try{
			
			if (this.getC_Currency_ID() != cCurrencyID){
				if (currencyRate == null){
					BigDecimal rate = MConversionRate.getDivideRate(cCurrencyID, this.getC_Currency_ID(), dateTrx, 0, this.getAD_Client_ID(), this.getAD_Org_ID());
					if ((rate == null) || (rate.compareTo(Env.ZERO) <= 0)){
						throw new AdempiereException("No se pudo obtener tipo de cambio segun fecha transaccion y moneda de politica de compra.");
					}
					amtRange = amt.divide(rate, 2, RoundingMode.HALF_UP);
				}
			}
			
			List<MPOPolicyRange> rangos = this.getRanges();
			if (rangos.size() <= 0) {
				throw new AdempiereException("No se han definido rangos de montos para la politica de compra: " + this.getName());
			}
			
			for (MPOPolicyRange rango: rangos){
				if (amtRange.compareTo(new BigDecimal(rango.getdesde())) >= 0){
					if (rango.gethasta() > 0){
						if (amtRange.compareTo(new BigDecimal(rango.gethasta())) <= 0){
							return rango;
						}
					}
					else{
						return rango;
					}
				}
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return value;
	}

	/***
	 * Obtiene y retorna lista de rangos asociados a esta politica de compra.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 04/11/2012
	 * @see
	 * @return
	 */
	private List<MPOPolicyRange> getRanges() {

		String whereClause = X_UY_POPolicyRange.COLUMNNAME_UY_POPolicy_ID + "=" + this.get_ID();
		
		List<MPOPolicyRange> lines = new Query(getCtx(), I_UY_POPolicyRange.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.list();
		
		return lines;
	}
	
	
	/***
	 * Obtiene y retorna lista de categorias especiales asociadas a esta politica de compra.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 04/11/2012
	 * @see
	 * @return
	 */
	public HashMap<MPOPolicyCategory, Integer> getCategories() {

		String whereClause = X_UY_POPolicyCategory.COLUMNNAME_UY_POPolicy_ID + "=" + this.get_ID();
		
		List<MPOPolicyCategory> lines = new Query(getCtx(), I_UY_POPolicyCategory.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.list();
		
		if (lines == null) return null;

		HashMap<MPOPolicyCategory, Integer> cats = new HashMap<MPOPolicyCategory, Integer>();
		for (MPOPolicyCategory cat: lines){
			cats.put(cat, new Integer(cat.get_ID()));
		}
		
		return cats;
	}	
	
	/***
	 * Obtiene y retorna modelo segun value recibido
	 * OpenUp Ltda. Issue #92
	 * @author Gabriel Vila - 04/11/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MPOPolicy forValue(Properties ctx, String value, String trxName){
		
		MPOPolicy model = null;
		
		if(value != null){
			
			value = value.toLowerCase().trim();
			
			String whereClause = " lower(" + X_UY_POPolicy.COLUMNNAME_Value + ")='" + value + "'";
			
			model = new Query(ctx, I_UY_POPolicy.Table_Name, whereClause, trxName)
			.setClient_ID()
			.first();
			
		}					
		return model;
	}
	
}
