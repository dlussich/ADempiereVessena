/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/08/2013
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.model.MConversionRate;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.process - PFixInvoiceCurrencyRate
 * OpenUp Ltda. Issue #1237 
 * Description: Setea tasa de cambio a invoices completas que no la tienen definida.
 * @author Gabriel Vila - 21/08/2013
 * @see
 */
public class PFixInvoiceCurrencyRate extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PFixInvoiceCurrencyRate() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/08/2013
	 * @see
	 */
	@Override
	protected void prepare() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/08/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			sql = " select ad_client_id, c_invoice_id, currencyrate, c_currency_id, dateinvoiced " +
				  " from c_invoice " +
				  " where docstatus='CO' " +
				  " order by dateinvoiced "; 
				  
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
		
			rs = pstmt.executeQuery ();
			
			while (rs.next()){

				Timestamp fechaRate = TimeUtil.trunc(rs.getTimestamp("dateinvoiced"), TimeUtil.TRUNC_DAY);
				
				if (rs.getBigDecimal("currencyrate") == null){
					BigDecimal dividerate = Env.ONE;
					if(rs.getInt("c_currency_id") != 142){
						dividerate = MConversionRate.getDivideRate(142, rs.getInt("c_currency_id"), fechaRate, 0, rs.getInt("ad_client_id"), 0);
						if (dividerate == null){
							dividerate = Env.ONE;
							/*
							throw new AdempiereException("No hay Tasa de Cambio cargada en el Sistema para Moneda : " + rs.getInt("c_currency_id") + 
														 " y Fecha de Documento : " + fechaRate);
							*/
						}
					}
					
					String action = " update c_invoice set currencyrate =" + dividerate + 
							" where c_invoice_id =" + rs.getInt("c_invoice_id");
					DB.executeUpdateEx(action, null);
				}
			}
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return "OK";

	}

}
