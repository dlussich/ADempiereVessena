/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. usuario - 04/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTPostalLiq
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo para Modelo de Liquidacion por Codigo Postal en Tracking de Tarjetas.
 * @author Gabriel Vila - 04/10/2013
 * @see
 */
public class MTTPostalLiq extends X_UY_TT_PostalLiq {

	private static final long serialVersionUID = 1573034269895453082L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_PostalLiq_ID
	 * @param trxName
	 */
	public MTTPostalLiq(Properties ctx, int UY_TT_PostalLiq_ID, String trxName) {
		super(ctx, UY_TT_PostalLiq_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTPostalLiq(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
