/**
 * 
 */
package org.openup.process;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MField;
import org.compiere.model.MProcess;
import org.compiere.model.MProcessPara;
import org.compiere.model.MQuery;
import org.compiere.model.MRefList;
import org.compiere.model.MRefTable;
import org.compiere.model.MTab;
import org.compiere.model.MTable;
import org.compiere.model.MWindow;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Column;
import org.compiere.model.X_AD_Element;
import org.compiere.model.X_AD_Field;
import org.compiere.model.X_AD_FieldGroup;
import org.compiere.model.X_AD_Process_Para;
import org.compiere.model.X_AD_Ref_List;
import org.compiere.model.X_AD_Ref_Table;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.X_AD_Tab;
import org.compiere.model.X_AD_Table;
import org.compiere.model.X_AD_Val_Rule;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.openup.beans.MigrationField;
import org.openup.beans.MigrationTab;
import org.openup.beans.MigrationTableScript;
import org.openup.beans.MigrationTrlHdr;
import org.openup.beans.MigrationObject;
import org.openup.model.MXLSIssue;



/**
 * @author root
 *
 */
//org.openup.process.PWindowToXLS
public class PWindowToXLS extends SvrProcess {

	public static final int adWindowID = 1000000;//Instrucciones: Id de AD_Windows
	private String path = ""; //OpenUp. Nicolas Sarlabos. 28/02/2013. Declaro variable para guardar ruta de destino del archivo xls.
	private MWindow window=null;
	private String dirGuardado="";
	private boolean soloMandatory=false;
	private MTab[] tablePadre= new MTab[10];
	private ArrayList<String> tablasRecorridas =new ArrayList<String>();
	private HashMap<Integer, MField>hashMapFields = new HashMap<Integer, MField>();
	//private HashMap<String, String>hashMapReferencias = new HashMap<Integer, MField>();
	
	private StringBuffer SQLnombres=new StringBuffer("");
	private StringBuffer SQLSelect=new StringBuffer("");
	private StringBuffer SQLjoin=new StringBuffer("");
	// Index of the sincronized sheet with tabs
	private int column=0;
	

	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_TITLE=0; 
	
	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_START=1; 
	
	// The maximun acepted errors at rows before stop, when many consecutive errors are detected means that this is the end 
	public static final int ROW_MAX_ERRORS=3;

	private static final int COLUMN_START = 0;

	private static final int ROW_DEFAULT = 10;
	
	
	@Override
	protected void prepare() {
		//OpenUp. Nicolas Sarlabos. 28/02/2013. Obtengo parametro de ruta de destino
		// Obtengo parametros y los recorro
				ProcessInfoParameter[] para = getParameter();
				for (int i = 0; i < para.length; i++) {
					String name = para[i].getParameterName().trim();
					if (name != null) {

						if (name.equalsIgnoreCase("File_Directory")) {
							if (para[i].getParameter() != null) {
								this.path = ((String) para[i].getParameter());
							}
						}
												
					}

				}
		//Fin OpenUp.
	}
	@Override
	protected String doIt() throws Exception {
		window=new MWindow(getCtx(),adWindowID, null);
		return this.explosion();
	}
	
	
	private void joinFrom(MTab tab,String tableActual){

			//De esta manera controlo no repetir nombre de tablas, si existe entonces
		// pongo un aleas a la tabla del tipo C_BPartner C_BPartner110 Donde 110 es la sequencia
		String apodo="";
		if(tablasRecorridas.contains(tableActual)){
			apodo=tableActual+tab.getSeqNo();
			
		}else{
			apodo=tableActual;
		}
			tablasRecorridas.add(apodo);
		
		if(tab.getAD_Column_ID()>0){//Tiene Referencia
			MColumn refColPadre = new MColumn(getCtx(),tab.getAD_Column_ID(),null);
			//MTable refTabPadre = new MTable(getCtx(),refColPadre.getAD_Table_ID(),null);
			
			//Seteo siempre la ultima tab de nivel como padre
			tablePadre[tab.getTabLevel()]=tab;
			
			//Agrego un join
			String tablaPadre=refColPadre.getColumnName().toLowerCase().replace("_id", "");
			SQLjoin.append(" LEFT JOIN "+tableActual+" "+apodo+" ON "+apodo+"."+tablaPadre+"_ID="+tablaPadre+"."+tablaPadre+"_ID");
		
			
		}else{
		
		//Si el nivel de la pestaña es 0
			if(tab.getTabLevel()==0){			
				//reseteo
				tablePadre[0]=tab;
				SQLjoin=new StringBuffer("");
				
				SQLjoin.append(" FROM "+this.getNameTableFather(1));
				
			}else{		
				
				//Seteo siempre la ultima tab de nivel como padre
				tablePadre[tab.getTabLevel()]=tab;
				
				//Agrego un join
				String tablaPadre=this.getNameTableFather(tab.getTabLevel());
				SQLjoin.append(" LEFT JOIN "+tableActual+" ON "+tableActual+"."+tablaPadre+"_ID="+tablaPadre+"."+tablaPadre+"_ID");
			}
		}
	}

	
	
	private String explosion() throws Exception{
		// Get the file name and test for not null
		//OpenUp. Nicolas Sarlabos. 28/02/2013. Concateno la ruta de destino del archivo si obtuve parametro o salgo con exception.
		String fileName= "";
		
		if(!this.path.equalsIgnoreCase("")){

			fileName= path + "\\Clientes.xls";//Instrucciones: Direccion destino						// FIXME: get this value from the windows name, by now it's just "Devoluciones.xls"
			
		} else throw new AdempiereException ("No se econtro ruta de destino");
		//Fin OpenUp.
		
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
			MWindow window=new MWindow(getCtx(),adWindowID,null);		// FIXME: get this value from the context, by now it's "Devoluciones Directas de Clientes"
			
			// Get window tabs
			MTab[] tabs=window.getTabs(true,null);
			
			// Index of the sincronized sheet with tabs
			int indexOfSheet=0;
			WritableSheet sheet=null;
			
			//int row=ROW_START;
			// Loop over tabs first level tabs. TODO: non first level tabs could be reached recursively
			for (MTab tab : tabs) {
				
				//Si no es pestaña de traduccion ni de contabilidad
				if(!tab.isTranslationTab()&& !tab.isInfoTab()&& tab.get_ID()!=183){	//Instrucciones: Id de pestañas a no contemplar a la N			
					
					
					// Skip non first level tabs
					//if (tab.getTabLevel()==0) {
					//genero tabla para usar su informacion
					 MTable tablaActual=new MTable(getCtx(),tab.getAD_Table_ID(),null);
					
					 //Generate From Join SQL
					joinFrom(tab,tablaActual.getTableName());
				
					// Skip readonly tabs
					if (!tab.isReadOnly()) {
						
						// Tab ReadOnlyLogic should not be considered, all records will be inserted
	
						// Skip tabs where the user cannot insert records, all records will be inserted
						if (tab.isInsertRecord()) {
	
							// The index should not be greater than the total number of sheets 
							if (indexOfSheet<=workbook.getNumberOfSheets()) {
								
								// TODO: evaluate to control the name of the sheet againts the tab name, there is a limit of 34 characters in Excel and transaltions should be considered
						
								// Solo creo nueva hoja cuando el nivel es cero.
								if(tab.getTabLevel()==0){
									// Load data from the sheet to the tab 
									WriteData(sheet,column);
									
									sheet=workbook.createSheet(tab.getName(),indexOfSheet);								// FIXME: use translation of tab name for sheet name	
									//Reseteo
									// Increment the index to mantain the sincronization
									indexOfSheet++;
									hashMapFields.clear();
									column=0;				
									
								}
							   
								// Load data from the sheet to the tab 
								WriteHeader(sheet,tab,tablaActual);	
								
							}
						}
					}
				}
			}
			// Load data from the sheet to the tab 
			WriteData(sheet,column);
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
		
		
		return "ll";
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Este metodo genera los titulos en el excel y tambien genera el texto de la consulta que se encuentra entre
	 * "SELECT _ _ _ FROM" para luego ser usado para generar la consulta.
	 * @param sheet
	 * @param tab
	 * @param tableActual
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @author  NicoLas Garcia 
	 * Fecha : 08/07/2011
	 */
	private void WriteHeader(WritableSheet sheet,MTab tab,MTable tableActual) throws RowsExceededException, WriteException {

		// Get the fields of the tab
		MField[] fields=tab.getFields(true,null);
		
		// Add columns headers
		for (MField field : fields) {
			
			// Get the corresponding table column object
			MColumn fieldColumn=(MColumn) field.getAD_Column();
			// Consider only displayed fields
			if (field.isDisplayed()&& (((field.getIsMandatory()!=null && field.getIsMandatory().equals("Y"))||fieldColumn.isMandatory())||((field.getIsMandatory()!=null && field.getIsMandatory().equals("Y"))&&fieldColumn.isMandatory()))) {//FIXME caso Mandatory, parametrisar

				

				String tituloColumna=tab.get_Translation(MTab.COLUMNNAME_Name)+"-"+field.get_Translation(MField.COLUMNNAME_Name);
				if(tituloColumna==null||tituloColumna.equals("") ){
					tituloColumna=field.getName();
				}
				
				// Skip readonly fields. Field ReadOnlyLogic should not be considered, all records will be inserted
				if (!(field.isReadOnly())) {
			
					// Add column header
					sheet.addCell(new Label(column,ROW_TITLE,tituloColumna));
					
					//Genero los campos a seleccionar luego en la select
					SQLSelect.append(", "+tableActual.getTableName()+"."+fieldColumn.getColumnName());
				}
				else {
					// Add a readonly column header
					sheet.addCell(new Label(column,ROW_TITLE,tituloColumna));
					
					//Genero los campos a seleccionar luego en la select
					SQLSelect.append(", "+tableActual.getTableName()+"."+fieldColumn.getColumnName());
				}
				
				// Increment the index to mantain the sincronization
				hashMapFields.put(column,field);
				column++;
			}
		}
	}

	/**
	 *  Write data to a sheet.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	private void WriteData(WritableSheet sheet,int tope) throws RowsExceededException, WriteException {
		// TODO: complete this

		//Si los SQL tienen algo
		if((!SQLSelect.toString().equals(""))&&(!SQLjoin.toString().equals(""))){
			
			StringBuffer SQL=new StringBuffer("SELECT ");
			SQL.append(SQLSelect.substring(1));
			SQL.append(SQLjoin.toString());
			SQL.append(" GROUP BY "+SQLSelect.substring(1));
			
			
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try {
				pstmt = DB.prepareStatement (SQL.toString(), null);
				//pstmt.setInt(1, model.get_ID());
				rs = pstmt.executeQuery ();
				
				//Todo para saber el numero de columnas
				 //ResultSetMetaData rsmd = rs.getMetaData();
			     //int numberOfColumns = rsmd.getColumnCount();
				int row=ROW_START;
				
				while (rs.next()){ 	//FIXME Cambiar if por while , esto es solo para probar
					
					//Recorro las columnas del RS
					for (int i=0;i<tope;i++){
						
						if(rs.getObject(i+1)!=null){
							//Guardo en la planilla el dato que devuevle la funcion GetStringForReference la cual devuelve la info en formato texto
							sheet.addCell(new Label(i,row,this.GetStringForReference(i, rs.getObject(i+1))));
							//System.out.println(row +" "+(i+1));
						}
						
					}
					
					if(new Integer(row).toString().endsWith("00")){
					System.out.println(row);
					}
					row++;
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}finally{
				DB.close(rs, pstmt);
				rs=null;pstmt=null;
			}
			
		}
	}

	private String getNameTableFather(int level){
		return new MTable(getCtx(),tablePadre[level-1].getAD_Table_ID(),null).getTableName();		
	}
	
	
	private String GetStringForReference(int columnInt, Object value) throws Exception {
			
		try{
			MField field=hashMapFields.get(columnInt);
			MColumn column=(MColumn)field.getAD_Column();
			// Get the reference object 
			X_AD_Reference reference=(X_AD_Reference) column.getAD_Reference();
			X_AD_Reference referenceWindows=(X_AD_Reference) field.getAD_Reference();
			
				//Validacion Por Tabla
			if (reference.getAD_Reference_ID()==DisplayType.Table ||referenceWindows.getAD_Reference_ID()==DisplayType.Table) {
				
				String columnName=column.getColumnName();
				if (columnName.endsWith("_ID")) {

					// For Table, value should value should be always Integer
					Integer integerValue=new Integer(value.toString());
					
					// Defencive: don't validate when the id is null
					if (integerValue==null) {
						return(value.toString());
					}
					
					int adRefValue=0;
					
					
					
					//Se crean id desde la ventana
					if(field.getAD_Reference_Value_ID()>0){
						adRefValue=field.getAD_Reference_Value_ID();
					}else if(column.getAD_Reference_Value_ID()>0){//Pregunto por la tabla
						adRefValue=column.getAD_Reference_Value_ID();
					}
					
					
					//int table=DB.getSQLValueEx(null, "select ad_table_id from ad_ref_table WHERE ad_reference_id=? and ad_ref_list.value=?",adRefValue);
					int keyID=DB.getSQLValueEx(null, "select ad_key from ad_ref_table WHERE ad_reference_id=?",adRefValue);
					int columnDisplay=DB.getSQLValueEx(null, "select ad_display from ad_ref_table WHERE ad_reference_id=?",adRefValue);
						
					if(keyID>0 && columnDisplay>0){
						MColumn llave = new MColumn(getCtx(),keyID,null);
						MTable tabla= new MTable(getCtx(),llave.getAD_Table_ID(),null);
						MColumn campoMostrar = new MColumn(getCtx(),columnDisplay,null);
						
						return DB.getSQLValueStringEx(null,"SELECT "+campoMostrar.getColumnName()+" FROM "+tabla.getTableName()+" WHERE "+ llave.getColumnName()+"=?",integerValue);
						
					}			
				}
				
			}	// Validacion directo por tabla (TableDir)
			else if (reference.getAD_Reference_ID()==DisplayType.TableDir||reference.getAD_Reference_ID()==DisplayType.Search) {

				String columnName=column.getColumnName();
				if (columnName.endsWith("_ID")) {

					// For TableDir, value should value should be always Integer
					Integer integerValue=new Integer(value.toString());
					
					// Defencive: don't validate when the id is null
					if (integerValue==null) {
						return(value.toString());
					}
					
					//Error en nombre de tabla M_ProductBOM_ID tendria que ser M_Product_BOM_ID 
					//FIXME en las busquedas hay que estudiar mejor si se toma idem como table dir por el ejemplo de este error
					if (columnName.equals("M_ProductBOM_ID")){
						columnName="M_Product_BOM_ID";
					}
					
					String keyColumn = MQuery.getZoomColumnName(columnName);
					String tableName = MQuery.getZoomTableName(columnName);
					String sql="SELECT COALESCE(("+caseTableDir(tableName)+"),'"+integerValue+"') as p FROM "+tableName+" WHERE "+tableName+"."+keyColumn+"=?";
					
					return DB.getSQLValueStringEx(null,sql,integerValue);					
				}
			}
			
			else if (reference.getAD_Reference_ID()==DisplayType.List||referenceWindows.getAD_Reference_ID()==DisplayType.List) {
				// For list validation, value should be always String  
				String stringValue=(String) value;
				
				
				//Uso referencia de la ventana.
				int refLista=DB.getSQLValueEx(null, "SELECT ad_ref_list_ID FROM ad_ref_list WHERE ad_ref_list.ad_reference_id=? and ad_ref_list.value=?",field.getAD_Reference_Value_ID(),stringValue);
				
				if(refLista<0){ //Si no hay referencia en la ventana
					refLista=DB.getSQLValueEx(null, "SELECT ad_ref_list_ID FROM ad_ref_list WHERE ad_ref_list.ad_reference_id=? and ad_ref_list.value=?",column.getAD_Reference_Value_ID(),stringValue);
				}
				
				if(refLista>0){
					MRefList refList = new MRefList(getCtx(), refLista, null);
					return refList.get_Translation(MRefList.COLUMNNAME_Name);
				}
				// The return value should be equal 
				return value.toString();//Defensivo
			}
			
			
			return(value.toString());
		}catch (Exception e) {
			System.out.println(e.toString());
			return e.toString();
		}
	}
	private String caseTableDir(String tableName) throws Exception{
		StringBuffer salida= new StringBuffer("");
		

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String SQL="select columnname from ad_column where  isidentifier  ='Y' AND ad_table_id =" +
				"(select ad_table_id from ad_table WHERE tablename='"+tableName+"') ORDER BY seqno";
		try {
			
			pstmt = DB.prepareStatement (SQL.toString(), null);
			rs = pstmt.executeQuery ();
			
			while (rs.next()){
				salida.append("||'-'||"+rs.getString("columnname"));
			}
			
			
		} catch (Exception e) {
			throw e;
		}finally{
			DB.close(rs, pstmt);
			rs=null;pstmt=null;
		}
		
		return salida.substring(7);
	}
//	private void GenerarSQL(){
//		
//		//Obrengo las pestanias de la ventana.
//		MTab[] tabs=window.getTabs(true,null);
//		
//		String tablePadre="";
//		
//		//Recorro las pestanias
//		for (MTab tab:tabs){
//			
//			//Si el nivel de la pestaña es 0 y no es la primera vez
//			if(tab.getTabLevel()==0 &&(SQLnombres!=null &&!SQLnombres.toString().equals(""))){			
//					//Se guarda lo acumulado
//					//TODO GUARDAR
//					recorrerArbol();
//					//Se borran sql
//					SQLnombres = new StringBuffer("");
//					SQLSelect =new StringBuffer("");
//					SQLjoin=new StringBuffer("");					
//				}	
//
//			//Obtengo la tabla
//			MTable table = new MTable(getCtx(),tab.getAD_Table_ID(),null);
//				
//			//this.joinFrom(tab, table.getTableName(), SQLjoin);
//			
//			//Obtengo los campos de la pestaña
//			MField[] fields =tab.getFields(true, null);
//			
//			//Recorro los Campos
//			for (MField field:fields){
//							
//				//Pregunto si esta el flag SOLO MANDATORY
//				if(soloMandatory){
//					
//					//Solo hago si es mandatory
//					if(field.getIsMandatory().equals("Y")){
//						GenerarSQL( tab, field, table.getTableName());
//					}
//				}else{
//					GenerarSQL(tab, field, table.getTableName());
//					
//				}
//			}			
//			
//		}
//		
//		//Cubro el caso del ultimo arbol
//		
//		
//	}
	
}
