/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/06/2012
 */
 
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MRFQGenReq
 * OpenUp Ltda. Issue #93 
 * Description: 
 * @author Hp - 18/06/2012
 * @see
 */
public class MRFQGenReq extends X_UY_RFQGen_Req {

	private static final long serialVersionUID = -6452450114008115908L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQGen_Req_ID
	 * @param trxName
	 */
	public MRFQGenReq(Properties ctx, int UY_RFQGen_Req_ID, String trxName) {
		super(ctx, UY_RFQGen_Req_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQGenReq(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;
		
		if (!newRecord)
			this.updateRFQProduct();
		
		return true;
	}

	/***
	 * Actualiza datos del producto de esta linea de requisicion en la generacion
	 * de solicitud de cotizacion.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 07/11/2012
	 * @see
	 */
	private void updateRFQProduct() {
		
		try{
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			String sql = " select coalesce(sum(qtyrequired),0) as total " +
					     " from uy_rfqgen_req " +
					     " where uy_rfqgen_prod_id =? " +
					     " and isselected='Y' ";
			BigDecimal sumQty = DB.getSQLValueBD(get_TrxName(), sql, this.getUY_RFQGen_Prod_ID());
			
			// Total de cotizaciones del producto segun nuevo total
			MRFQGenProd genprod = new MRFQGenProd(getCtx(), this.getUY_RFQGen_Prod_ID(), null);
			MRFQGenFilter genfilter = new MRFQGenFilter(getCtx(), genprod.getUY_RFQGen_Filter_ID(), null);

			BigDecimal totalAmt = genprod.getPriceActual().multiply(sumQty).setScale(2, RoundingMode.HALF_UP);
			
			int qtyQuote = 0;
			
			// Si tengo monto tiene sentido que busque cantidad de cotizaciones segun politica
			if (totalAmt.compareTo(Env.ONE) >= 0){
				MPOPolicy policy = new MPOPolicy(getCtx(), genfilter.getUY_POPolicy_ID(), null);
				qtyQuote = policy.getRangeForAmount(today, totalAmt, genprod.getC_Currency_ID(), null).getQty();
			}
			
			String action = " update uy_rfqgen_prod set qtyrequired =" + sumQty + "," +
					        " totalamt =" + totalAmt + "," +
					        " qtyquote =" + qtyQuote +
					        " where uy_rfqgen_prod_id =" + genprod.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		return super.afterDelete(success);
	}

	
	
}
