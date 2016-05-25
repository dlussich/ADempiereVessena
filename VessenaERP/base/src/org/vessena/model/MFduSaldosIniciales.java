package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MFduSaldosIniciales extends X_UY_Fdu_SaldosIniciales{

	private static final long serialVersionUID = 441641047117798554L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Fdu_SaldosIniciales_ID
	 * @param trxName
	 */
	public MFduSaldosIniciales(Properties ctx, int UY_Fdu_SaldosIniciales_ID,
			String trxName) {
		super(ctx, UY_Fdu_SaldosIniciales_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduSaldosIniciales(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static BigDecimal getValue(int c_Activity_ID, int c_Currency_ID,Timestamp getfecha) {
		BigDecimal valorinicial= Env.ZERO;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
	try{
		sql = " select saldo" +
				  " from uy_fdu_saldosiniciales " +
				  " where fecha <=? " +
				  " and c_activity_id =? " +
				  " and isActive ='Y' " +
				  " and c_currency_id =? ";
				 
				  
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, getfecha);
			pstmt.setInt(2, c_Activity_ID);
			pstmt.setInt(3, c_Currency_ID);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				valorinicial= rs.getBigDecimal("saldo");
			}
		
		return valorinicial;
	} catch (Exception e) {
		throw new AdempiereException(e);
	}
	finally 
	{
		DB.close(rs, pstmt);
		rs = null;
		pstmt = null;
	}
	}
	public static MFduSaldosIniciales getDate(Properties ctx, int c_Activity_ID, int c_Currency_ID,Timestamp fecha, String trx) {
		
		
		String whereClause = X_UY_Fdu_SaldosIniciales.COLUMNNAME_C_Activity_ID + "=" + c_Activity_ID  + 
				" AND  " +  X_UY_Fdu_SaldosIniciales.COLUMNNAME_C_Currency_ID + "=" + c_Currency_ID +
				" AND  " +  X_UY_Fdu_SaldosIniciales.COLUMNNAME_IsActive +"='Y'"+
				" AND  " + X_UY_Fdu_SaldosIniciales.COLUMNNAME_fecha + " <= '" + fecha + "' " ;
		
		MFduSaldosIniciales saldos =  new Query(ctx, I_UY_Fdu_SaldosIniciales.Table_Name, whereClause, trx).first();

		
		return saldos;
	}
	
	

}
