/**
 * 
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * @author gbrust
 *
 */
public class MBcuCapitalFinanciado extends X_UY_Bcu_CapitalFinanciado {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852528173409177943L;

	/**
	 * @param ctx
	 * @param UY_Bcu_CapitalFinanciado_ID
	 * @param trxName
	 */
	public MBcuCapitalFinanciado(Properties ctx,
			int UY_Bcu_CapitalFinanciado_ID, String trxName) {
		super(ctx, UY_Bcu_CapitalFinanciado_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBcuCapitalFinanciado(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 12/08/2013.
	 * Obteniene y devuleve si una compra es incosistente por estar procesando una cuota n, la cual no fue procesada su cuota n-1 
	 * ISSUE #1114
	 * */
	public static boolean getIsIncosistente(Properties ctx, String trxName, String cuenta, String cupon, int cantCuotas,
			Timestamp fechaCierre, Timestamp fechaPresentacion, int cuotaActual) {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean retorno = true;
		
		try{		
					
			String sql = " SELECT cf.cuotaactual as cuotaanterior" +
						 " FROM UY_Bcu_CapitalFinanciado cf" +
						 " WHERE cf.accountno = '" + cuenta + "'" +
						 " AND cf.cuotas = " + cantCuotas +
						 " AND cf.cupon = '" + cupon + "'" +
						 " AND cf.fechacierre < '" + fechaCierre + "'" +
						 " AND cf.fechaoperacion = '" + fechaPresentacion + "'" +
						 " ORDER BY cf.fechacierre desc";					    
			
			pstmt = DB.prepareStatement (sql, trxName);
		    rs = pstmt.executeQuery();
		    
		   	if (rs.next()){
				if(rs.getInt("cuotaanterior") - cuotaActual == 1) retorno = false;
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}

}
