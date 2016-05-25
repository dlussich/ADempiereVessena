/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/01/2013
 */
package org.openup.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;

/**
 * org.openup.model - MFDUControl
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 15/01/2013
 * @see
 */
public class MFDUControl extends X_UY_FDU_Control {

	private static final long serialVersionUID = 7041748285229700508L;
	
	private String formulaClean = "";

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FDU_Control_ID
	 * @param trxName
	 */
	public MFDUControl(Properties ctx, int UY_FDU_Control_ID, String trxName) {
		super(ctx, UY_FDU_Control_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFDUControl(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Proceso que ejecuta calculo de validacion de este control.
	 * OpenUp Ltda. Issue #133 
	 * @author Gabriel Vila - 15/01/2013
	 * @see
	 * @param globalVars
	 * @return boolean. True si es valido, False sino.
	 */
	public boolean evaluate(HashMap<String, Object> globalVars, MFduConnectionData fduData) {

		Connection con = null;
		CallableStatement cstmt = null;
		boolean result = true;
		
		try{
			// Limpio formula de calculo
			this.cleanFormula(globalVars);

			con = this.getConnection();
			
			cstmt = con.prepareCall("{? = call " + this.formulaClean + "}");
		    cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		    cstmt.execute();

		    int errorCounter = cstmt.getInt(1);
		    
		    if (errorCounter != 0)
		    	result = false;
		    
		    cstmt.close();
			con.close();		
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			
			if (con != null){
				try {
					if (cstmt != null){
						if (!cstmt.isClosed()) cstmt.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
		return result;
	}


	public String getFormulaClean(){
		return this.formulaClean;
	}
	
	/***
	 * Limpia formula de calculo de este control, sustituyendo variables por sus valores actuales.
	 * OpenUp Ltda. Issue #133 
	 * @author Gabriel Vila - 15/01/2013
	 * @see
	 * @param globalVars
	 */
	private void cleanFormula(HashMap<String, Object> globalVars) {

		String value = this.getcalculo();

		// Seteo valores de variables globales que hayan en esta formula
		Map<String, Object> map = globalVars; 
		for (Map.Entry<String, Object> entry : map.entrySet()) { 
			value = value.replaceAll("#" + entry.getKey().trim() + "#", entry.getValue().toString());
		}		

		this.formulaClean = value;
	}

	/***
	 * Obtiene conexion a base sql server.
	 * OpenUp Ltda. Issue #133 
	 * @author Guillermo Brust - 15/01/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		
		Connection retorno = null;

		String connectString = ""; 

		try {

			MFduConnectionData conn = new MFduConnectionData(getCtx(), 1000010, null);
			
			if(conn != null){
				
				connectString = "jdbc:sqlserver://" + conn.getserver_ip() + "\\" + conn.getServer() + 
								";databaseName=" + conn.getdatabase_name() + ";user=" + conn.getuser_db() + 
								";password=" + conn.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, conn.getuser_db(), conn.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}
}
