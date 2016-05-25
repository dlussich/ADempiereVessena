/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MCashConfig extends X_UY_CashConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2922995991864892928L;

	/**
	 * @param ctx
	 * @param UY_CashConfig_ID
	 * @param trxName
	 */
	public MCashConfig(Properties ctx, int UY_CashConfig_ID, String trxName) {
		super(ctx, UY_CashConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo para la compania actual.
	 * OpenUp Ltda. Issue #4449 
	 * @author Nicolas Sarlabos - 23/07/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashConfig forClient(Properties ctx, String trxName){
		
		MCashConfig model = new Query(ctx, I_UY_CashConfig.Table_Name, null, trxName)
		.setClient_ID()
		.first();
		
		return model;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord || is_ValueChanged("CantBilletes")){
			
			if(this.getCantBilletes()<=0) throw new AdempiereException("Cantidad de billetes debe ser mayor a cero");			
			
		}
		
		if(newRecord || is_ValueChanged("CantTickets")){
			
			if(this.getCantTickets()<=0) throw new AdempiereException("Cantidad de tickets debe ser mayor a cero");			
			
		}
		
		if(newRecord || is_ValueChanged("ToleranceAmount")){
			
			if(this.getToleranceAmount().compareTo(Env.ZERO)<0) throw new AdempiereException("Monto de tolerancia no puede ser menor a cero");			
			
		}
		
		return true;
	}

}
