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

import org.openup.model.MXLSIssue;
import org.adempiere.model.GenericPO;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.MColumn;
import org.compiere.model.MField;
import org.compiere.model.MInOut;
import org.compiere.model.MProduct;
import org.compiere.model.MQuery;
import org.compiere.model.MTab;
import org.compiere.model.MTable;
import org.compiere.model.MWindow;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.AccessSqlParser.TableInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import jxl.*; 

/**
 *  Load XLS interfases from one window  
 *
 *	@author OpenUP FL 03/02/2011 issue #149
 */
//org.openup.process.PXLSLoad
public class PXLSLoad extends SvrProcess {
	
	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_TITLE=0; 
	
	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_START=1; 
	
	// The maximun acepted errors at rows before stop, when many consecutive errors are detected means that this is the end 
	public static final int ROW_MAX_ERRORS=3;

	private static final int COLUMN_START = 0;
	
	private String fileName="e:\\Devoluciones.xls";
	private Integer AD_Window_ID=1000071;	
	
	private HashMap<Integer,Integer> ids;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare() {
		
		// Get parameters
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			
			String name = para[i].getParameterName();
			if (name.equals("fileName")) {
				this.fileName=(String) para[i].getParameter();
			}
//			else if (name.equals("AD_Window_ID")) {
//				this.AD_Window_ID=(Integer) para[i].getParameter();
//			}
		}
		
		// TODO: evaluate to get the AD_Window_ID from the context if it's not 	set from parameters

	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception {
		
		// The file name cannot be empty o null
		if ((this.fileName==null)||(this.fileName.equals(""))) {
			MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),this.fileName,"","","","El nombre de la planilla excel esta vacio",get_TrxName());	// TODO: translate
			return("El nombre de la planilla excel esta vacio");																					// TODO: translate
		}
		
		// Create the file object
		File file=new File(this.fileName);
		
		// Nothing to process if the file dont exist
		if (!file.exists()) {
			MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),this.fileName,"","","","No se encontro la planilla Excel",get_TrxName());	// TODO: translate
			return("No se encontro la planilla Excel");																						// TODO: translate
		}
		
		// The workbook
		Workbook workbook=null;
		
		try{
			// Open de workbook
			workbook = Workbook.getWorkbook(file);
			
			// Get the window 
			MWindow window=new MWindow(getCtx(),this.AD_Window_ID,null);		// FIXME: get this value from the context, by now it's "Devoluciones Directas de Clientes"
			
			// Get window tabs
			MTab[] tabs=window.getTabs(true,null);
			
			// Index of the sincronized sheet with tabs
			int indexOfSheet=0;
			
			// Loop over tabs first level tabs. TODO: non first level tabs could be reached recursively or use HashMap
			for (MTab tab : tabs) {
				
				// Reset ids hashmap
				if (tab.getTabLevel()==0) {
					ids=new HashMap<Integer,Integer>();
				}
				
				// Skip non first level tabs
				if (tab.getTabLevel()==0) {

					// Skip readonly tabs
					if (!tab.isReadOnly()) {
						
						// Tab ReadOnlyLogic should not be considered, all records will be inserted
	
						// Skip tabs where the user cannot insert records, all records will be inserted
						if (tab.isInsertRecord()) {
	
							// The index should not be greater than the total number of sheets 
							if (indexOfSheet<=workbook.getNumberOfSheets()) {
								
								// TODO: evaluate to control the name of the sheet againts the tab name, there is a limit of 34 characters in Excel and transaltions should be considered
						
								// Get the sincronized sheet
								Sheet sheet=workbook.getSheet(indexOfSheet);
						
								// Load data from the sheet to the tab 
								Load(sheet,tab,window);
								
								// Increment the index to mantain the sincronization
								indexOfSheet++;
							}
						}
					}
				}
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
	
	
	private void Load(Sheet sheet,MTab tab,MWindow window) {

		// Get the fields of the tab
		MField[] fields=tab.getFields(true,null);
		
		for (int row=ROW_START;row<sheet.getRows();row++) {
			
			// Create a generic object to manage the table
			GenericPO table=new GenericPO(tab.getAD_Table().getTableName(),getCtx(),0,get_TrxName());
			POInfo tableInfo=POInfo.getPOInfo(getCtx(),table.get_Table_ID(),null);
			Boolean saveTable=true;
			
			// Index of the sincronized column
			int indexOfColumn=0;
			
			for (MField field : fields) {
				
				// Get the corresponding table column object
				MColumn column=(MColumn) field.getAD_Column();

				// To manage value casting an intermediate value is used to get the cell content or default value
				Object value=null;
				
				// Consider only displayed fields
				if (field.isDisplayed()) {

					// Get the cell and the corresponding field 
					Cell cell=sheet.getCell(indexOfColumn,row);
					
					// Skip readonly fields
					if (!(field.isReadOnly())) {
					
						// field ReadOnlyLogic should not be considered, all records will be inserted
						
						// TODO: evaluate to control the field name againts the first excel cell in this column
						
						// TODO: include column filters, column ReadOnlyLogic should not be considered, all fields will be inserted						
							
						// Special tratement of date
						if ((cell.getType().equals(CellType.DATE))||(cell.getType().equals(CellType.DATE_FORMULA))) {

							// Date, Timestamp and Caledar have usage problems in Java. Could be replaced with external Joda, aparently the facto DateTime API, but the JSR-310 could replace all, so depreciated messages are not bad in this case
							java.util.Date date;
							if (cell.getType().equals(CellType.DATE)) {
								DateCell dateCell=(DateCell) cell;
								date=dateCell.getDate();
							}
							else {
								DateFormulaCell dateFormulaCell=(DateFormulaCell) cell;
								date=dateFormulaCell.getDate();
							}
							
							// Avoid null or invalid dates
							if (date!=null) {
								value=new Timestamp(date.getYear(),date.getMonth(),date.getDate()+1,date.getHours(),date.getMinutes(),date.getSeconds(),0);
							}
						}
						else {
							// TODO: cell formulas shuld be addresed, (cell.getType().equals(CellType.NUMBER))||(cell.getType().equals(CellType.NUMBER_FORMULA))) and (cell.getType().equals(CellType.STRING_FORMULA)) otherwise cell.getContents() will continue to be fine  
							
							// Avoid null and empty contents
							if ((cell.getContents()!=null)&&(!(cell.getContents().isEmpty()))) {
								value=cell.getContents();
							}
						}
					}
					
					// Increment the index to mantain the sincronization 
					indexOfColumn++;
				}

				// Set field default value for not displayed, readonly or invalid contents
				if (value==null) {

					// Get default value
					String defaultValue=null;
					if ((field.getDefaultValue()!=null)&&(!(field.getDefaultValue().isEmpty()))) {
						defaultValue=field.getDefaultValue();													
					}
					else {
						// Consider column default value
						if ((column.getDefaultValue()!=null)&&(!(column.getDefaultValue().isEmpty()))) {
							defaultValue=column.getDefaultValue();												
						}
					}
					
					// Evaluate default value only if it has a value
					if (defaultValue!=null) {
						
						// Evaluate first for table
						String parseValue=Env.parseVariable(defaultValue,table,get_TrxName(),false);
						if ((parseValue==null)||(parseValue.isEmpty())) {
							// Evaluate otherwise for window
							parseValue=Env.parseVariable(defaultValue,window,get_TrxName(),false);
						}
						
						// Date casting. FIXME: generalize this control
						Class<?> columnClass=tableInfo.getColumnClass(tableInfo.getColumnIndex(column.getColumnName()));
						if (columnClass.equals(Timestamp.class)) {
							value=Timestamp.valueOf(parseValue);
						}
						else {
							// Set devault value to the parse value
							if ((parseValue!=null)&&(!(parseValue.isEmpty()))) {
								value=parseValue;
							}
						}
					}
				}
				
				// Set the column value using the column id 
				if (value!=null) {
					
					// Make validations 
					if (this.Validate(column,field,value)) {
						table.set_ValueOfColumn(field.getAD_Column_ID(),value);
					}
					else {
						// TODO: write a error message
			
						// If one filed is invalid, the record cannot be save
						saveTable=false;
					}
				}
			}
	
			// Save the table
			if (saveTable) {
				
				if (tab.getTabLevel()!=0) {
					// table.set_ValueOfColumn(this.idColumnName;value)
				}
				
				table.saveEx();
				
				// Add
				if (tab.getTabLevel()==0) {
					// this.idColumnName="M_InOut_ID";
					// this.ids.put(key, value) // TODO: Replace the key with the value of column 0, the first one, ad value, whith the new created ide.
				}
			}
		}		
	}
	
	private boolean Validate(MColumn column,MField field, Object value) {
		
		// Get the reference object 
		X_AD_Reference reference=(X_AD_Reference) column.getAD_Reference();
		
		// Validacion directo por tabla (TableDir)
		if (reference.getAD_Reference_ID()==DisplayType.TableDir) {

			String columnName=column.getColumnName();
			if (columnName.endsWith("_ID")) {

				// For TableDir, value should value should be always Integer
				Integer integerValue=new Integer((String) value);
				
				// Defencive: don't validate when the id is null
				if (integerValue==null) {
					return(false);
				}
				
				String keyColumn = MQuery.getZoomColumnName(columnName);
				String tableName = MQuery.getZoomTableName(columnName);
				String sql="SELECT "+tableName+"."+keyColumn+" FROM "+tableName+" WHERE "+tableName+"."+keyColumn+"=?";

				// The return value should be equal 
				return(integerValue.equals(DB.getSQLValue(null,sql,integerValue)));
			}
		}
		else if (reference.getValidationType().equals(X_AD_Reference.VALIDATIONTYPE_ListValidation)) {
			
			// For list validation, value should be always String  
			String stringValue=(String) value;
			
			// The return value should be equal 
			return(stringValue.equals(DB.getSQLValueString(null, "SELECT ad_ref_list.value FROM ad_ref_list WHERE ad_ref_list.ad_reference_id=? and ad_ref_list.value=?",reference.getAD_Reference_ID(),stringValue)));
		}
		else if (reference.getValidationType().equals(X_AD_Reference.VALIDATIONTYPE_TableValidation)) {
			// FIXME: Build the SQL with the next code extracted from MLookupFactory
						
			//			String sql0 = "SELECT t.TableName,ck.ColumnName AS KeyColumn,"				//	1..2
			//				+ "cd.ColumnName AS DisplayColumn,rt.IsValueDisplayed,cd.IsTranslated,"	//	3..5
			//				+ "rt.WhereClause,rt.OrderByClause,t.AD_Window_ID,t.PO_Window_ID, "		//	6..9
			//				+ "t.AD_Table_ID, cd.ColumnSQL as DisplayColumnSQL, "					//	10..11
			//				+ "rt.AD_Window_ID as RT_AD_Window_ID " // 12
			//				+ "FROM AD_Ref_Table rt"
			//				+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID)"
			//				+ " INNER JOIN AD_Column ck ON (rt.AD_Key=ck.AD_Column_ID)"
			//				+ " INNER JOIN AD_Column cd ON (rt.AD_Display=cd.AD_Column_ID) "
			//				+ "WHERE rt.AD_Reference_ID=?"
			//				+ " AND rt.IsActive='Y' AND t.IsActive='Y'";
						
			//				TableName = rs.getString(1);
			//				KeyColumn = rs.getString(2);
			//				DisplayColumn = rs.getString(3);
			//				isValueDisplayed = "Y".equals(rs.getString(4));
			//				IsTranslated = "Y".equals(rs.getString(5));
			//				WhereClause = rs.getString(6);
			//				OrderByClause = rs.getString(7);
			//				ZoomWindow = rs.getInt(8);
			//				ZoomWindowPO = rs.getInt(9);
			//				//AD_Table_ID = rs.getInt(10);
			//				displayColumnSQL = rs.getString(11);
			//				overrideZoomWindow = rs.getInt(12);
			//				loaded = true;

		}
		
		return(true);
	}
}
