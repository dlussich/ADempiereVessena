package org.openup.beans;

import java.sql.Connection;
import java.sql.DriverManager;

// org.openup.process.PDriveDataBase
public class LogicLoadPODB {

	public static Connection getConnection() throws Exception {

		String connectString = "jdbc:postgresql://1.1.20.123:5432/vessena";
		String user = "adempiere";
		String password = "Op3nuP029";

		try {

			return DriverManager.getConnection(connectString, user, password);

		} catch (Exception e) {
			throw e;

		}

	}


}
