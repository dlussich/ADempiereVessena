/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.Query;

/**
 * @author Nicolas
 * 
 */
public class MFduCajaRef extends X_UY_Fdu_Caja_Ref {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8065719452695589231L;
	/**
	 * @param ctx
	 * @param UY_Fdu_Caja_Ref_ID
	 * @param trxName
	 */
	public MFduCajaRef(Properties ctx, int UY_Fdu_Caja_Ref_ID, String trxName) {
		super(ctx, UY_Fdu_Caja_Ref_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCajaRef(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Obtiene y retorna objeto de referencia de movimiento de caja segun value
	 * recibido OpenUp Ltda. Issue #1932
	 * 
	 * @author Leonardo Boccone - 07/03/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param moneda 
	 * @param salida
	 * @param entrada
	 * @param tc
	 * @param trxName
	 * @return
	 */
	
	public static MFduCajaRef forValue(Properties ctx, String value,
			int moneda, BigDecimal entrada, BigDecimal salida, String trxName) {

		String whereClause = "strpos('" + value + "' ," + X_UY_Fdu_Caja_Ref.COLUMNNAME_codigo + ")=1"+" AND " + X_UY_Fdu_Caja_Ref.COLUMNNAME_C_Currency_ID+ "=" +  moneda;
		MFduCajaRef ref = new Query(ctx, I_UY_Fdu_Caja_Ref.Table_Name,
				whereClause, trxName).first();

	

		return ref;
	}
	// Recibe el nombre de la Clasificacion y devuelve su Id
	public static int IdTypeCaja(Properties ctx,String pName, String trxName){
		
		MFduCajaType cajaType = MFduCajaType.forName(ctx, pName, trxName);
		
		if(cajaType !=null){
			int id= cajaType.getUY_Fdu_Caja_Type_ID();
			return id;
		}
		return 0;
		
	}


}
