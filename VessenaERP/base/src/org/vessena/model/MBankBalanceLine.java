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
public class MBankBalanceLine extends X_UY_BankBalanceLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2449687794181086516L;

	/**
	 * @param ctx
	 * @param UY_BankBalanceLine_ID
	 * @param trxName
	 */
	public MBankBalanceLine(Properties ctx, int UY_BankBalanceLine_ID,
			String trxName) {
		super(ctx, UY_BankBalanceLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBankBalanceLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getAmtSourceCr().compareTo(Env.ZERO) < 0 || this.getAmtSourceDr().compareTo(Env.ZERO) < 0) throw new AdempiereException("El importe no puede ser negativo");
		if(this.getAmtSourceCr().compareTo(Env.ZERO) != 0 && this.getAmtSourceDr().compareTo(Env.ZERO) != 0) throw new AdempiereException("Debe ingresar un solo importe, al credito o debito");
				
		return true;
	}

}
