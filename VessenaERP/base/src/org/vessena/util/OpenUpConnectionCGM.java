package org.openup.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;

public class OpenUpConnectionCGM {
	/***
	 * Obtiene conexion a base PostgreSQL.
	 * OpenUp Ltda. Issue 
	 * @author Raul Capecce - 07/07/2015
	 * @see https://jdbc.postgresql.org/documentation/head/connect.html
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnectionToSqlServer() {
		
		String server_ip   = MSysConfig.getValue("UY_CGM_IF__IP_SERVER", "192.168.102.16", Env.getAD_Client_ID(Env.getCtx()));
		String server_port = MSysConfig.getValue("UY_CGM_IF__PORT_SERVER", "5432", Env.getAD_Client_ID(Env.getCtx()));
		String db_name     = MSysConfig.getValue("UY_CGM_IF__DB_NAME", "cgmatest", Env.getAD_Client_ID(Env.getCtx()));
		String db_user     = MSysConfig.getValue("UY_CGM_IF__DB_USER", "open_up", Env.getAD_Client_ID(Env.getCtx()));
		String db_pass     = MSysConfig.getValue("UY_CGM_IF__DB_PASS", "Yx1y99Yx", Env.getAD_Client_ID(Env.getCtx()));
		
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			
			connectString = "jdbc:postgresql://" + server_ip + ":" + server_port + "/" + db_name;
			
			Properties props = new Properties();
			props.setProperty("user", db_user);
			props.setProperty("password", db_pass);
			
			retorno = DriverManager.getConnection(connectString, props);

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return retorno;
	}
}
