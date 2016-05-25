/**
 * MReserveOrders.java
 * 11/01/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MLocator;
import org.compiere.model.MWarehouse;

/**
 * OpenUp.
 * MReserveOrders
 * Descripcion :
 * @author FL
 * Fecha : 11/01/2011
 */
public class MInOutType extends X_UY_InOutType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3969046073743462959L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InOutType
	 * @param trxName
	 */
	public MInOutType(Properties ctx, int UY_InOutType_ID,String trxName) {
		super(ctx, UY_InOutType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInOutType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public int getDefaultM_Locator_ID() {
		// Get the warehouse
		MWarehouse warehouse=new MWarehouse(getCtx(), this.getM_Warehouse_ID(), null);
		
		// Get or create the default locator for the warehouse
		MLocator locator=warehouse.getDefaultLocator();
		
		// Just return the default locator id  
		return(locator.getM_Locator_ID());
	}

}
