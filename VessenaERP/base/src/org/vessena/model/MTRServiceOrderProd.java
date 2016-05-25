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
public class MTRServiceOrderProd extends X_UY_TR_ServiceOrderProd {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2812802296926287800L;

	/**
	 * @param ctx
	 * @param UY_TR_ServiceOrderProd_ID
	 * @param trxName
	 */
	public MTRServiceOrderProd(Properties ctx, int UY_TR_ServiceOrderProd_ID,
			String trxName) {
		super(ctx, UY_TR_ServiceOrderProd_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRServiceOrderProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getQtyRequired().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Debe indicar Cantidad Requerida");
		
		if(this.getQtyRequired().compareTo(this.getQtyAvailable()) > 0) throw new AdempiereException("Cantidad requerida no debe superar la disponible");
		
		//if(this.getUY_TR_ServiceOrderMaintain_ID() > 0 && this.getUY_TR_ServiceOrderFailure_ID() > 0) throw new AdempiereException("Debe seleccionar una falla o una tarea de mantenimiento, pero no ambas");
		
		//if(this.getUY_TR_ServiceOrderMaintain_ID() <= 0 && this.getUY_TR_ServiceOrderFailure_ID() <= 0) throw new AdempiereException("Debe seleccionar una falla o una tarea de mantenimiento");
						
		return true;
	}

}
