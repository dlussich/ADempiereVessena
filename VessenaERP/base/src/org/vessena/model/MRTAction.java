/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

import com.sun.enterprise.tools.verifier.tests.ejb.ejb30.InitMethodReturnType;

/**
 * Tabla con los codigos SISTECO para definir acciones sobre la tabla de interfaz
 *
 */
public class MRTAction extends X_UY_RT_Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UY_RT_Action_ID
	 * @param trxName
	 */
	public MRTAction(Properties ctx, int UY_RT_Action_ID, String trxName) {
		super(ctx, UY_RT_Action_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTAction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtengo modelo segun value recibido.
	 * OpenUp Ltda. Issue #4404 
	 * @author INes Fernandez Vila - 19/6/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRTAction forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_DeliveryPoint.COLUMNNAME_Value + "='" + value + "'";
		
		MRTAction model = new Query(ctx,I_UY_RT_Action.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;
	}
	
	

}
