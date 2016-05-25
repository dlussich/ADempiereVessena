/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTTWebCourierLine extends X_UY_TT_WebCourierLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4157577243573413697L;

	/**
	 * @param ctx
	 * @param UY_TT_WebCourierLine_ID
	 * @param trxName
	 */
	public MTTWebCourierLine(Properties ctx, int UY_TT_WebCourierLine_ID,
			String trxName) {
		super(ctx, UY_TT_WebCourierLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTWebCourierLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * OpenUp. Guillermo Brust. 10/09/2013
	 * Metodo que devuelve si una cuenta esta en manos de un courier para un determinado levante
	 * 
	 * */
	public static boolean getIsInCourierForIncidencia(Properties ctx, int uyRReclamoID, String levante, String trxName){

		boolean retorno = false;
		
		String whereClause = X_UY_TT_WebCourierLine.COLUMNNAME_UY_R_Reclamo_ID + "=" + uyRReclamoID +
				" AND " + X_UY_TT_WebCourierLine.COLUMNNAME_Levante + "='" + levante + "'";

		//SBT Creo que no es suficiente el control 
		MTTWebCourierLine line = new Query(ctx, I_UY_TT_WebCourierLine.Table_Name, whereClause, trxName).setOrderBy(X_UY_TT_WebCourierLine.COLUMNNAME_UY_TT_WebCourierLine_ID + " desc").first();
		
		if(line != null){
			
			//Acá debemos ver si el estado del courier obtenido esta parametrizado como que todavia se encuentra en manos de el o no
			if(line.getUY_TT_DeliveryPointStatus_ID() > 0) retorno = ((X_UY_TT_DeliveryPointStatus) line.getUY_TT_DeliveryPointStatus()).isInCourier();			
		}	
		
		return retorno;
	}

}
