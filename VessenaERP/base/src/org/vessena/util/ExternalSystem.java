package org.openup.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.compiere.util.Env;
import org.openup.model.MFduConnectionData;

public abstract class ExternalSystem {
	
	private int connectionToSqlID = 1000011;
	
	
	
	public Connection getConnectionToSqlServer() throws Exception{
		
		Connection retorno = null;

		String connectString = ""; 

		try{

			MFduConnectionData conn = new MFduConnectionData(Env.getCtx(), this.connectionToSqlID, null);
			
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
	
	
	public Connection getConnectionToSqlServer(int connectionToSqlID) throws Exception{

		 Connection retorno = null;

		 String connectString = ""; 

		 try{

			 MFduConnectionData conn = new MFduConnectionData(Env.getCtx(), connectionToSqlID, null);

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
