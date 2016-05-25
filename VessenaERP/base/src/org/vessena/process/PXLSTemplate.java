package org.openup.process;

import org.openup.model.MXLSIssue;
import org.compiere.model.MColumn;
import org.compiere.model.MField;
import org.compiere.model.MInOut;
import org.compiere.model.MProduct;
import org.compiere.model.MTab;
import org.compiere.model.MWindow;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import jxl.*; 
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 *  Generate a XLS template for a window to be use as an interface   
 *
 *	@author OpenUP FL 03/02/2011 issue #149
 */
//org.openup.process.PXLSTemplate 
public class PXLSTemplate extends SvrProcess {
	
	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_TITLE=0; 
	
	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_START=1; 
	
	// The maximun acepted errors at rows before stop, when many consecutive errors are detected means that this is the end 
	public static final int ROW_MAX_ERRORS=3;

	private static final int COLUMN_START = 0;

	private static final int ROW_DEFAULT = 10;
	
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
		
		// Get the file name and test for not null
		String fileName="c:\\Devoluciones.xls";						// FIXME: get this value from the windows name, by now it's just "Devoluciones.xls" 
		
		// The file name cannot be empty o null
		if ((fileName==null)||(fileName.equals(""))) {
			MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","El nombre de la planilla excel esta vacio",get_TrxName());	// TODO: translate
			return("El nombre de la planilla excel esta vacio");																				// TODO: translate
		}
		
		// Create the file object
		File file=new File(fileName);
		
		// The workbook
		WritableWorkbook workbook=null;
		
		try{
			// Create de workbook
		    workbook=Workbook.createWorkbook(file);
			
			// Get the window 
			MWindow window=new MWindow(getCtx(),1000071,null);		// FIXME: get this value from the context, by now it's "Devoluciones Directas de Clientes"
			
			// Get window tabs
			MTab[] tabs=window.getTabs(true,null);
			
			// Index of the sincronized sheet with tabs
			int indexOfSheet=0;
			
			// Loop over tabs first level tabs. TODO: non first level tabs could be reached recursively
			for (MTab tab : tabs) {
				
				// Skip non first level tabs
				//if (tab.getTabLevel()==0) {

					// Skip readonly tabs
					if (!tab.isReadOnly()) {
						
						// Tab ReadOnlyLogic should not be considered, all records will be inserted
	
						// Skip tabs where the user cannot insert records, all records will be inserted
						if (tab.isInsertRecord()) {
	
							// The index should not be greater than the total number of sheets 
							if (indexOfSheet<=workbook.getNumberOfSheets()) {
								
								// TODO: evaluate to control the name of the sheet againts the tab name, there is a limit of 34 characters in Excel and transaltions should be considered
						
								// Get the sincronized sheet
							    WritableSheet sheet=workbook.createSheet(tab.getName(),indexOfSheet);								// FIXME: use translation of tab name for sheet name
						
								// Load data from the sheet to the tab 
								WriteHeader(sheet,tab);
								
								// Load data from the sheet to the tab 
								WriteData(sheet,tab);
								
								// Increment the index to mantain the sincronization
								indexOfSheet++;
							}
						}
					}
				//}
			}

			// Write the workbook 
		    workbook.write();
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
	
	
	/**
	 *  Write the header to a sheet.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	private void WriteHeader(WritableSheet sheet,MTab tab) throws RowsExceededException, WriteException {

		// Get the fields of the tab
		MField[] fields=tab.getFields(true,null);

		// Index of the sincronized sheet with tabs
		int column=0;
		
		// TODO: evaluating this strategy
		String SQLFields="";
		
		// Add columns headers
		for (MField field : fields) {
			
			// Consider only displayed fields
			if (field.isDisplayed()) {

				// Get the corresponding table column object
				MColumn fieldColumn=(MColumn) field.getAD_Column();

				// TODO: include column filters, use field.getAD_Column(). Column ReadOnlyLogic should not be considered, all fields will be inserted
				
				// Skip readonly fields. Field ReadOnlyLogic should not be considered, all records will be inserted
				if (!(field.isReadOnly())) {
				
					// TODO: evaluate to control the field name againts the first excel cell in this column
					
					// Add column header
					sheet.addCell(new Label(column,ROW_TITLE,field.get_Translation(MField.COLUMNNAME_Name)));
					SQLFields+=fieldColumn.getColumnName()+", ";
				}
				else {
					// Add a readonly column header
					sheet.addCell(new Label(column,ROW_TITLE,field.get_Translation(MField.COLUMNNAME_Name)));
					SQLFields+=fieldColumn.getColumnName()+", ";
				}
				
				// Increment the index to mantain the sincronization
				column++;
			}
		}
		
		sheet.addCell(new Label(0,ROW_TITLE+1,SQLFields));
	}

	/**
	 *  Write data to a sheet.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	private void WriteData(WritableSheet sheet,MTab tab) throws RowsExceededException, WriteException {
		// TODO: complete this
	}

}
