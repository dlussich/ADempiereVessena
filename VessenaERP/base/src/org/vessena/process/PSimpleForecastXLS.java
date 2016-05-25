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

import org.openup.model.MSimpleForecast;
import org.openup.model.MSimpleForecastLine;
import org.openup.model.MXLSIssue;
import org.compiere.model.MProduct;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import jxl.*; 

/**
 *  BOM a simple forecast 
 *
 *	@author OpenUP FL 03/02/2011 issue #149
 */
public class PSimpleForecastXLS extends SvrProcess {
	
	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_TITLE=0; 
	
	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_START=1; 
	
	// The maximun acepted errors at rows before stop, when many consecutive errors are detected means that this is the end 
	public static final int ROW_MAX_ERRORS=3;
	
	// The product column
	public static final int PRODUCT_COLUMN=0;
	
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
		
		// Get the current id
		int UY_SimpleForecast_ID = getRecord_ID();
		
		// Nothing to process if no  ID
		if (UY_SimpleForecast_ID==0) {
			return("Identificador no valido");							// TODO: translate
		}
		
		// Get the object
		MSimpleForecast simpleForecast= new MSimpleForecast(getCtx(),UY_SimpleForecast_ID,get_TrxName());
		
		// Nothing to process if no object
		if (simpleForecast==null) {
			return("No pudo leer el pronostico simple");														// TODO: translate
		}
		
		// Delete previous Excel issues
		if (!MXLSIssue.Delete(getCtx(),getTable_ID(),getRecord_ID(),get_TrxName())) {
			return("No pudo borrar los problemas de Excel previos");	// TODO: translate
		}
		
		// Get the file name and test for not null
		String fileName=simpleForecast.getFileName();
		
		// The file name cannot be empty o null
		if ((fileName==null)||(fileName.equals(""))) {
			MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","El nombre de la planilla excel esta vacio",get_TrxName());
			return("El nombre de la planilla excel esta vacio");
		}
		
		// Create the file object
		File file=new File(fileName);
		
		// Nothing to process if the file dont exist
		if (!file.exists()) {
			MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","No se encontro la planilla Excel",get_TrxName());
			return("No se encontro la planilla Excel");																		// TODO: translate
		}
		
		// Define de workbook
		Workbook workbook=null;
		
		try{
			// Open de workbook
			workbook = Workbook.getWorkbook(file);
			
			// Open the first sheet
			Sheet sheet = workbook.getSheet(0);
			
			if (sheet.getColumns()<1) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",get_TrxName());
				return("La primer hoja de la planilla Excel no tiene columnas");								// TODO: translate
			}
			
			if (sheet.getRows()<1) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",get_TrxName());
				return("La primer hoja de la planilla Excel no tiene columnas");								// TODO: translate
			}
			
			// Create a hash to store date columns
			HashMap<Integer,Timestamp> dateColumns=new HashMap<Integer,Timestamp>();
			for (int j=0;j<sheet.getColumns();j++) {
				
				if (j!=PRODUCT_COLUMN) {
					Cell cell=sheet.getCell(j,ROW_TITLE);
					if ((cell.getType()==CellType.DATE)||(cell.getType()==CellType.DATE_FORMULA)) {
						DateCell dateCell = (DateCell) cell;
						//OpenUp Nicolas Garcia 27/06/2011 Issue #740 
						//TODO pongo mas 1 porque no se encontro otra manera rapidamente, deja la fecha un dia atrazada	
						Timestamp timestamp=new java.sql.Timestamp (dateCell.getDate().getTime()+86400000);
						// FIN OpenUp Nicolas Garcia 27/06/2011 
						if (timestamp!=null) {
							dateColumns.put(j,timestamp);
						}
					}
				}
			}
			
			// Date columns should be identified
			if (dateColumns.size()<1) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","No se identificaron en la primer hoja columnas con titulo con fecha o poseen un formato incorrecto",get_TrxName());
				return("No se identificaron en la primer hoja columnas con titulo con fecha o poseen un formato incorrecto");		// TODO: translate
			}
			
			// Delete all previous forecast lines
			simpleForecast.DeleteLines();
			simpleForecast.setIsBOM(false);
			simpleForecast.saveEx();
			
			
			// Create indexes for the loop
			int i=ROW_START;				// Line conuter 
			int errors=0;					// Error conuter
			
			// Loop until 3 consecutive errors
			while ((errors<ROW_MAX_ERRORS)&&(i<sheet.getRows())) {
	
				// value from the first cell
				Cell cell=sheet.getCell(PRODUCT_COLUMN,i);
				String value=cell.getContents();
				
				// Should not be null or empty
				if ((value==null)||(value.equals(""))) {
					
					// Add an Excel error
					MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, sheet.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"El codigo del producto esta vacio o posee un formato incorrecto",get_TrxName());
					
					errors++;
				} 
				else {
					// Get the product object
					MProduct product=this.getProductByValue(value);
					
					// The product must be get by value
					if (product==null) {
						// Add an Excel error, product not defined
						MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, sheet.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), sheet.getCell(PRODUCT_COLUMN,i).getContents(),"El codigo del producto no esta definido o posee un formato incorrecto",get_TrxName());
					}
					else {
						
						for (Map.Entry<Integer, Timestamp> dateColumn : dateColumns.entrySet()) {
						    int j=dateColumn.getKey().intValue();
						    
							cell=sheet.getCell(j,i);
							if ((cell.getType()==CellType.NUMBER)||(cell.getType()==CellType.NUMBER_FORMULA)) {
	
								// Get the number using casting
								NumberCell numberCell=(NumberCell) cell;
								BigDecimal qty=new BigDecimal(numberCell.getValue());
								
								// Only not null quantities, else, its an error 
								if (qty!=null) {
										
									// Avoid quantities with 0, this is not an error
									if (!qty.equals(BigDecimal.ZERO)) {
	
										// Reset error, this is a healthy line
										errors=0;								
										
										// Create the line and set the properties
										MSimpleForecastLine line=new MSimpleForecastLine(getCtx(),0,get_TrxName());
										line.setUY_SimpleForecast_ID(UY_SimpleForecast_ID);
										line.setM_Product_ID(product.getM_Product_ID());
										line.setQty(qty);
										line.setDateTo(dateColumn.getValue());
										
										// Save the line
										line.saveEx();
									}
								}
								else {
									// Add an Excel error, empty or zero
									MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, sheet.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"El contenido de la celda es vacio, cero o posee un formato incorrecto",get_TrxName());			// TODO: translate
								}
							}
							else {
								// Add an Excel error, empty or zero
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, sheet.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"El contenido de la celda no es un numero o posee un formato incorrecto",get_TrxName());			// TODO: translate
							}
						}
					}
				}
				
				i++;
			}

			// Add a message for error stops
			if (!(errors<ROW_MAX_ERRORS)) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","Se identificaron varios errores consecutivos, intencionalmente se detuvo el proceso",get_TrxName());		// TODO: Translate
			}

		}
		catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally {
			// Close the workbook
			if (workbook!=null) {					// Defernsive
				workbook.close();
			}
		}
		
		return("XLS");
	}	//	doIt
	
	
	private MProduct getProductByValue(String value) throws Exception  {
		
		MProduct product=null;		// When no product its found, null its returned
		
		if (value==null) return null;
		
		// Get all records from product having a value
		String sql = "SELECT * FROM m_product WHERE trim(value)=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1,value.trim());
			rs = pstmt.executeQuery();
		
			// Just get the first record, else a null product will be returned
			if (rs.next()){				
				product = new MProduct(Env.getCtx(),rs,null);
			}
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
		
		return(product);
	}


}
