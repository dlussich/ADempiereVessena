/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MHRLoadDriverLine extends X_UY_HRLoadDriverLine {

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		this.setTotalLines(this.getAmtTotal());		
		
		return true;
	}

	/***
	 * Obtiene y retorna importe total de los campos de importe de esta linea.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 14/01/2014
	 * @see
	 * @return
	 */
	private BigDecimal getAmtTotal() {
		
		String sql = "select coalesce(amtlicencia + amtjornal + amtferiadobrasil + amthoraextra + amtviaticonac + amtviaticoext,0)" +
	 	             "from uy_hrloaddriverline where uy_hrloaddriverline_id = " + this.get_ID();
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;	
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1035522018534434160L;

	/**
	 * @param ctx
	 * @param UY_HRLoadDriverLine_ID
	 * @param trxName
	 */
	public MHRLoadDriverLine(Properties ctx, int UY_HRLoadDriverLine_ID,
			String trxName) {
		super(ctx, UY_HRLoadDriverLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRLoadDriverLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
