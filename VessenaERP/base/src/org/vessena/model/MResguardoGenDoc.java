/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author emiliano
 *
 */
public class MResguardoGenDoc extends X_UY_ResguardoGenDoc {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4054089212846497521L;

	/**
	 * @param ctx
	 * @param UY_ResguardoGenDoc_ID
	 * @param trxName
	 */
	public MResguardoGenDoc(Properties ctx, int UY_ResguardoGenDoc_ID, String trxName) {
		super(ctx, UY_ResguardoGenDoc_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MResguardoGenDoc(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * Elimina lineas de este resguardo.
	 * OpenUp Ltda. Issue #5144
	 * @author Emiliano Bentancor - 8/12/2015
	 * @see
	 */
	public void deleteLines(){

		try{
			
			String action = " DELETE FROM uy_resguardogenline " +
					        " where uy_resguardoGenDoc_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}
	
	

}
