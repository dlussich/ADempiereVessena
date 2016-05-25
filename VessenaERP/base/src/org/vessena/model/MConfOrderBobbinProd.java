/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MConfOrderBobbinProd extends X_UY_ConfOrderBobbinProd {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6542849676657508196L;

	/**
	 * @param ctx
	 * @param UY_ConfOrderBobbinProd_ID
	 * @param trxName
	 */
	public MConfOrderBobbinProd(Properties ctx, int UY_ConfOrderBobbinProd_ID, String trxName) {
		super(ctx, UY_ConfOrderBobbinProd_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MConfOrderBobbinProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getQtyEntered().compareTo(Env.ZERO)<=0) throw new AdempiereException("La cantidad debe ser mayor a cero");
		if(this.getWeight().compareTo(Env.ZERO)<=0) throw new AdempiereException("El peso debe ser mayor a cero");
		
		return true;
	}
	
	public static BigDecimal totalWeightProd (Properties ctx, int confHdrID, String trxName) {
		
		BigDecimal value = Env.ZERO;
		
		String sql = "select coalesce(sum(qtyentered * weight),0)" +
                     " from uy_conforderbobbinprod" +
                     " where uy_confirmorderhdr_id = " + confHdrID;
		
		value = DB.getSQLValueBDEx(trxName, sql);		
		
		return value;
		
	}

}
