/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolas
 *
 */
public class MCashRemitBank extends X_UY_CashRemitBank {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8965146333571768839L;

	/**
	 * @param ctx
	 * @param UY_CashRemitBank_ID
	 * @param trxName
	 */
	public MCashRemitBank(Properties ctx, int UY_CashRemitBank_ID,
			String trxName) {
		super(ctx, UY_CashRemitBank_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashRemitBank(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna lineas de deposito para este registro.
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 27/07/2015
	 * @see
	 * @return
	 */
	public List<MCashRemitBankLine> getBankLines(){

		String whereClause = X_UY_CashRemitBankLine.COLUMNNAME_UY_CashRemitBank_ID + "=" + this.get_ID();

		List<MCashRemitBankLine> lines = new Query(getCtx(), I_UY_CashRemitBankLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

}
