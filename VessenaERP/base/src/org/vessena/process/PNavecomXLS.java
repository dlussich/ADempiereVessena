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

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import jxl.*; 
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *  Export Navecom to a excel file 
 *
 *	@author OpenUP FL 11/03/2011 issue #498
 */
public class PNavecomXLS extends SvrProcess {
	
	// The start row to scan for data, start at line 1, the line 1 should have titles
	public static final int ROW_LABEL=0; 
	
	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_START=1; 
	
	// The maximun acepted errors at rows before stop, when many consecutive errors are detected means that this is the end 
	public static final int ROW_LIMIT=60000;

	// File to create
	public String fileName="Navecom.xls";
	public String SQL="SELECT uy_navecom_filter_id as Navegador, ad_client as Empresa, ad_org as Organizacion,  documentno as Factura, dateinvoiced FechaFactura, codprod as Codigo, nomprod as Descripcion,  m_product_category as Categoria,  uy_familia as Familia, uy_subfamilia as SubFamilia, uy_linea_negocio as LineaNegocio,  codbp as Cliente, nombp as Nombre, name as Vendedor, uy_canalventas as CanaVentas,  moneda as Moneda, linenetamt, qtyinvoiced, linenetamtbase FROM uy_navecom_invoices WHERE uy_navecom_filter_id=?"; // OpenUp M.R. 14-06-2011 Issue #651 Se modifica linea para traer solo los datos finales y no solo IDs como lo hacia antes
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare() {
		
		// Get parameters
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			
			String name = para[i].getParameterName();
			if (name.equals("ExportTo")) {
				this.fileName=(String) para[i].getParameter();
			} 
			else if (name.equals("SQLStatement")) {
				this.SQL=(String) para[i].getParameter();
			}
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception {
		
		// Get the current id
		int UY_Navecom_Filter_ID = getRecord_ID();
		
		// Nothing to process if no  ID
		if (UY_Navecom_Filter_ID==0) {
			return("Identificador no valido");							// TODO: translate
		}
		
		// TODO: evaluate this code only if required to update the base model
		// Get the object
		// MNavecomFilter navecomFilter= new MNavecomFilter(getCtx(),UY_Navecom_Filter_ID,get_TrxName());
		//		
		// // Nothing to process if no object
		// if (navecomFilter==null) {
		//	 return("No pudo leer el filtro");							// TODO: translate
		// }
		
		// If the file name is empty, then set a default value
		if ((this.fileName==null)||(this.fileName.equals(""))) {
			return("Nombre de archivo no ingresado");					// TODO: add user and timestamp to the default value, path also should be considered.
		}
		
		// If the file name is empty, then set a default value
		if ((this.SQL==null)||(this.SQL.equals(""))) {
			return("SQL no ingresado");									// TODO: add user and timestamp to the default value, path also should be considered.
		}
		
		// Define the result set, the statement and workbook objects 
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		WritableWorkbook workbook=null;
		
		// Write the query to excel data file
		try {
			// Prepare and excecute
			pstmt = DB.prepareStatement (this.SQL, get_TrxName());
			pstmt.setInt(1,UY_Navecom_Filter_ID);
			rs = pstmt.executeQuery();
			
			// Continue only if the firest record can be readed 
			if (rs.next()) {
				// Get the metadata
				ResultSetMetaData rsmd=rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				// Create the file, workbook and sheet objects
				File file=new File(this.fileName);
			    workbook = Workbook.createWorkbook(file);
			    WritableSheet sheet = workbook.createSheet("Data",0);								// TODO: translate "Data"

				// Write the header
				for (int column=1;column<=columnCount;column++) {				
					sheet.addCell(new Label(column-1,ROW_LABEL,rsmd.getColumnLabel(column)));		// Header column content.
				}				

				// Loop over resultset rows
				int row=ROW_START;
				do {
				
					// Loop over resultset columns
					for (int column=1;column<=columnCount;column++) {
						sheet.addCell(new Label(column-1,row,rs.getString(column)));					// TODO: consider rsmd.getColumnClassName(column); to change the cell class from Label to number or date 
					}
								
				} while ((rs.next())&&((row++)<ROW_LIMIT));

				// Define the data area
			    workbook.addNameArea("Data",sheet,0,0,columnCount,row);									// TODO: translate "Data"
			    workbook.write();
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			// Close and reset the resultset and statement
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;

			// Close the workbook
			if (workbook!=null) {		// Defernsive
				workbook.close();
			}
		}		

		// TODO: Set the filename to the navecomFilter object. To generalize this method first must test for the existance of filename
		// navecomFilter.setExport(this.fileName);
		// navecomFilter.saveEx();
		
		// Show the file to the user
		Env.startBrowser(this.fileName);
		
		return("Exportado");
	}	//	doIt
}
