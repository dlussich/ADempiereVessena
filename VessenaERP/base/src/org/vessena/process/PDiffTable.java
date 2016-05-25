/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.openup.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MDiff;
import org.openup.model.MDiffColumn;
import org.openup.model.MDiffTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *  Application Dictionary Difference Process 
 *
 *	@author OpenUP FL 29/03/2011 issue #586, Registros iguales en tablas del diccionario de Alianzur y Vessena
 */
public class PDiffTable extends SvrProcess {
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare() {

	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception {
		
		// Get the process object
		MDiffTable diffTable= new MDiffTable(getCtx(),getRecord_ID(),get_TrxName());
		
		// Nothing to do if no object
		if (diffTable==null) {
			return("Identificador no valido");							// TODO: translate
		}
		
		// Delete tables and compare them again
		if (diffTable.DeleteDiff(get_TrxName())) {
			
			// Compare
			compareRecords(diffTable.getAD_DiffTable_ID(),diffTable.getTableName());
		}
		
		return("Done");		// TODO: Translate
	}	//	doIt
	
	
	private void compareRecords(int AD_DiffTable_ID,String tableName) throws Exception {
		// TODO Auto-generated method stub

		// Get all records from product having a value
		String sql = "SELECT * FROM "+tableName;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		// Get table names an compare it
		try{
			// Prepare and excecute
			pstmt = DB.prepareStatement (sql,null);
			rs = pstmt.executeQuery();
			
			// target connection, prepare and execute
			Connection targetConnection=getTargetConnection();
			Statement targetStatement=targetConnection.createStatement();
			ResultSet targetRecord;
			

			// Continue only if the firest record can be readed 
			if (rs.next()) {
				// Get the metadata
				ResultSetMetaData rsmd=rs.getMetaData();
				
				// Compare values
				do {
					// Read target record
					targetRecord=targetStatement.executeQuery(sql+" WHERE "+tableName+"_ID="+rs.getString(tableName+"_ID"));
					
					// Differences in the current record
					int diffCount=0;
					String diffType=null;
					int AD_Window_ID=0;
					int AD_Tab_ID=0;

					// Get or create the difference object
					MDiff diff=MDiff.getByRecordID(getCtx(), AD_DiffTable_ID, rs.getInt(tableName+"_ID"),get_TrxName());
					
					// For matched id. TODO: evaluate the inclusion of name
					if (targetRecord.next()) {
					
						// Loop over resultset columns
						for (int column=1;column<=rsmd.getColumnCount();column++) {

							try {
								
								// Try to find a related window or tab
								if (rsmd.getColumnName(column).equalsIgnoreCase("AD_Window_ID")) {
									AD_Window_ID=rs.getInt("AD_Window_ID");
								}
								else {
									if (rsmd.getColumnName(column).equalsIgnoreCase("AD_Tab_ID")) {
										AD_Tab_ID=rs.getInt("AD_Tab_ID");
									}
								}
								
								
								String target=targetRecord.getString(rsmd.getColumnName(column));
								String source=rs.getString(column);
								
								if (source!=null) {
									if (target==null) {
										
										// Save the diff object to get the id
										diff.saveEx();
										
										// Get or create the difference object
										MDiffColumn diffColumn=MDiffColumn.getByColumnName(getCtx(), diff.getAD_Diff_ID(),rsmd.getColumnName(column),get_TrxName());
										diffColumn.setSource(source);
										diffColumn.saveEx();
										
										diffCount++;
										if (diffType==null) {
											diffType="n";
										}
									}
									else {
										if (!(source.trim().equalsIgnoreCase(target.trim()))) {

											// Save the diff object to get the id
											diff.saveEx();
											
											// Get or create the difference object
											MDiffColumn diffColumn=MDiffColumn.getByColumnName(getCtx(), diff.getAD_Diff_ID(),rsmd.getColumnName(column),get_TrxName());
											diffColumn.setTarget(target);
											diffColumn.setSource(source);
											diffColumn.saveEx();
											  
											diffCount++;
											if (diffType==null) {
												diffType="diferentes";
											}
										}	
									}
								}
							}
							catch  (Exception e) {
								diffCount++;
								if (diffType==null) {
									diffType="error";
								}
							}
						}
					}
					else {
						// This is a new record
						diffType="nuevo";
						
						// Loop over resultset columns to get window and tab fields
						for (int column=1;column<=rsmd.getColumnCount();column++) {
							// Try to find a related window or tab
							if (rsmd.getColumnName(column).equalsIgnoreCase("AD_Window_ID")) {
								AD_Window_ID=rs.getInt("AD_Window_ID");
							}
							else {
								if (rsmd.getColumnName(column).equalsIgnoreCase("AD_Tab_ID")) {
									AD_Tab_ID=rs.getInt("AD_Tab_ID");
								}
							}
						}
					}
					
					// Update and save the diff object if a difference was found
					if (diffType!=null) {
						diff.setDiffCount(diffCount);
						diff.setAD_DiffType(diffType);
						diff.setAD_Window_ID(AD_Window_ID);
						diff.setAD_Tab_ID(AD_Tab_ID);
						diff.saveEx();
					}
					
				} while (rs.next());
				
				// Close target record
				targetRecord.close();
			}


			// Close target connection
			targetStatement.close();
			targetConnection.close();
		
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	private static Connection getTargetConnection() throws ClassNotFoundException, SQLException {
		
		// FIXME: include this in a try
		Class.forName("org.postgresql.Driver");
		
		
		// FIXME: include this in a try
		Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/vessena4","adempiere","postgres");
		
		return(connection);
		
	}

}
