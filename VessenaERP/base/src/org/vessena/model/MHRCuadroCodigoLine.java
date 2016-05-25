/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MHRCuadroCodigoLine extends X_UY_HRCuadroCodigoLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5640213365400411841L;

	/**
	 * @param ctx
	 * @param UY_HRCuadroCodigoLine_ID
	 * @param trxName
	 */
	public MHRCuadroCodigoLine(Properties ctx, int UY_HRCuadroCodigoLine_ID,
			String trxName) {
		super(ctx, UY_HRCuadroCodigoLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRCuadroCodigoLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
			
		if(this.getAmount().compareTo(Env.ZERO) <= 0) throw new AdempiereException("El importe debe ser mayor a cero");
		if(this.getFactor().compareTo(Env.ZERO) <= 0) throw new AdempiereException("El factor multiplicador debe ser mayor a cero");
		
		this.setTotalAmt(this.getAmount().multiply(this.getFactor())); //seteo importe total
				
		return true;
	}

}
