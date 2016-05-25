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
import org.openup.model.MDiffProcess;
import org.openup.model.MDiffTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *  Application Dictionary Difference Process 
 *
 *	@author OpenUP FL 29/03/2011 issue #586, Registros iguales en tablas del diccionario de Alianzur y Vessena
 */
public class PDiffProcess extends SvrProcess {
	
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
		MDiffProcess diffProcess= new MDiffProcess(getCtx(),getRecord_ID(),get_TrxName());
		
		// Nothing to do if no object
		if (diffProcess==null) {
			return("Identificador no valido");							// TODO: translate
		}
		
		// Delete tables and compare them again
		if (diffProcess.DeleteTables(get_TrxName())) {
			
			// Compare
			compareTables();
		}
		
		return("Done");		// TODO: Translate
	}	//	doIt
	
	
	private void compareTables() throws Exception  {
		
		// Get all records from product having a value
		String sql = "SELECT * FROM information_schema.tables WHERE table_schema='adempiere' AND table_type='BASE TABLE' AND table_name LIKE 'ad_%'";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Get table names an compare it
		try{
			// Prepare and excecute
			pstmt = DB.prepareStatement (sql,null);
			rs = pstmt.executeQuery();
		
			// loop over tables 
			while (rs.next()) {
				// Get or create the object
				MDiffTable diffTable=MDiffTable.getByTableName(getCtx(),rs.getString("table_name"),get_TrxName());

				// Set process id and count
				diffTable.setAD_DiffProcess_ID(this.getRecord_ID());
				diffTable.setrowcount(); 
				diffTable.setCreateCount(); 
				diffTable.setUpdateCount(); 
				diffTable.saveEx();
				
				// Compare records
				// compareRecords(diffTable.getAD_DiffTable_ID(),diffTable.getTableName());
			};
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
}
