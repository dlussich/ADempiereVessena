/**
 * 
 */
package org.openup.process;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MField;
import org.compiere.model.MProcess;
import org.compiere.model.MProcessPara;
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
import org.compiere.model.X_AD_Process;
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
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.openup.beans.MigrationField;
import org.openup.beans.MigrationTab;
import org.openup.beans.MigrationTableScript;
import org.openup.beans.MigrationTrlHdr;
import org.openup.beans.MigrationObject;

/**
 * @author root
 *
 */
public class PMigration extends SvrProcess {

	private MigrationObject migObject = null;
	
	// Ventana
	private int adWindowID = 0;
	private MWindow window = null;

	// Informe y Proceso
	private int adProcessID = 0;
	private MProcess process = null;
	
	// Tabla
	private int adTableID = 0;
	private MTable table = null;
	
	private boolean isExportAction = true;
	private boolean isWindowMigration = false;
	private boolean isProcessMigration = false;
	private boolean isTableMigration = false;
	private String exportPath = "";
	private String importFileName = "";
	
	private static final String ACTION_EXPORT = "EXPORT";
	private static final String MIGRATE_WINDOW = "WINDOW";
	private static final String MIGRATE_PROCESS = "PROCESS";
	private static final String MIGRATE_TABLE = "TABLE";
	
	/**
	 * 
	 */
	public PMigration() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
	
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("DDMigrationType")){
					if (((String)para[i].getParameter()).toUpperCase().equalsIgnoreCase(ACTION_EXPORT))
						isExportAction = true; 
					else 
						isExportAction = false;
				}
				if (name.equalsIgnoreCase("DDObjectType")){
					if (((String)para[i].getParameter()).toUpperCase().equalsIgnoreCase(MIGRATE_WINDOW))
						isWindowMigration = true; 
					else if (((String)para[i].getParameter()).toUpperCase().equalsIgnoreCase(MIGRATE_PROCESS))
						isProcessMigration = true;
					else if (((String)para[i].getParameter()).toUpperCase().equalsIgnoreCase(MIGRATE_TABLE))						
						isTableMigration = true;
				}
				if (name.equalsIgnoreCase("DDFileOut")){
					if (para[i].getParameter()!=null)
						this.exportPath = ((String)para[i].getParameter()).trim();
				}
				if (name.equalsIgnoreCase("DDFileIn")){
					if (para[i].getParameter()!=null)
						this.importFileName = ((String)para[i].getParameter()).trim();
				}
				if (name.equalsIgnoreCase("AD_Window_ID")){
					if (para[i].getParameter()!=null)
						this.adWindowID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Process_ID")){
					if (para[i].getParameter()!=null)
						this.adProcessID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Table_ID")){
					if (para[i].getParameter()!=null)
						this.adTableID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}	
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() {

		String mensaje = "OK"; 
		
		try{
			if (this.isExportAction && this.isWindowMigration)
				this.exportWindow();

			if (!this.isExportAction && this.isWindowMigration)
				this.importWindow();
		
			if (this.isExportAction && this.isProcessMigration)
				this.exportProcess();

			if (!this.isExportAction && this.isProcessMigration)
				this.importProcess();

			if (this.isExportAction && this.isTableMigration)
				this.exportTable();

			if (!this.isExportAction && this.isTableMigration)
				this.importTable();
			
		}
		catch (Exception e)
		{
			mensaje = e.getMessage();
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new AdempiereException();
		}

		return mensaje;
	}

	
	/**
	 * OpenUp. Set Fields.
	 * @param tab
	 * @param migTab
	 * @throws Exception 
	 */
	private void setFields(MTab tab, MigrationTab migTab) throws Exception{

		if (tab == null) return;
		
		MField[] fields = tab.getFields(true, null);
		for (int j=0; j<fields.length; j++){
			MigrationField migField = new MigrationField();
			migField.setAdFieldID(fields[j].getAD_Field_ID());
			migField.setAdField((X_AD_Field)fields[j]);
			
			// Set Field Trls
			migField.setTrl(new MigrationTrlHdr(fields[j]));
			
			migTab.getFields().add(migField);
			
			// Set fieldGroup asociado al Field
			this.setFieldGropup(fields[j].getAD_FieldGroup_ID());
			// Set Reference asociado al Field
			this.setReference(fields[j].getAD_Reference_Value_ID());
			// Set ValRule asociada al Field
			this.setValRule(fields[j].getAD_Val_Rule_ID());
		}

	}
	
	/**
	 * OpenUp. Set Table.
	 * @param adTableID
	 * @throws Exception 
	 */
	private void setTable(int adTableID, boolean importTable) throws Exception{
		
		if (adTableID > 0){
		
			MTable table = new MTable(getCtx(), adTableID, null);
			
			if (table != null) {
				if ((!table.getTableName().toLowerCase().startsWith("ad_") && (!table.getTableName().toLowerCase().startsWith("a_")))) {
					if (!this.migObject.getTables().containsKey(Integer.valueOf(table.getAD_Table_ID()))){
						
						this.migObject.getTables().put(Integer.valueOf(table.getAD_Table_ID()), table);

						// Set de columns de la table
						this.setTableColumns(table, importTable);
						
						if (importTable){

							// Esto es para marcar la tabla para que cuando se importe se pasen tambien las
							// columnas, etc.
							table.setImportTable("Y");  
							
							
							// Set Table Trls
							this.migObject.getTablesTrls().put(Integer.valueOf(table.getAD_Table_ID()), new MigrationTrlHdr(table));
						

							// Set Table Script
							if (!table.isView())	
								this.migObject.getTableScripts().put(Integer.valueOf(table.getAD_Table_ID()), new MigrationTableScript(table));
							else
								this.migObject.getTableScripts().put(Integer.valueOf(table.getAD_Table_ID()), null);
							
						}
						else{
							// Solo importe la definicion de la tabla para referencias de tablas.
							table.setImportTable("N");
						}
					}
				}
			}
		}
	}

	/***
	 * Setea info de Proceso en XML
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/01/2015
	 * @see
	 * @param adTableID
	 * @param importTable
	 * @throws Exception
	 */
	private void setProcess(int adProcessID) throws Exception{
		
		if (adProcessID > 0){
		
			MProcess process = new MProcess(getCtx(), adProcessID, null);
			
			if (process != null) {
				
				if (!this.migObject.getProcesses().containsKey(Integer.valueOf(process.get_ID()))){
					
					this.migObject.getProcesses().put(Integer.valueOf(process.get_ID()), process);

					// Set Process Trls
					this.migObject.getProcessesTrls().put(Integer.valueOf(process.get_ID()), new MigrationTrlHdr(process));

					// Set Process parameters
					MProcessPara[] params = process.getParameters();
					for (int i=0; i<params.length; i++){
						System.out.println(params[i].getColumnName());
						this.setParameter(params[i].getAD_Process_Para_ID());
					}
					
					
				}
			}
		}
	}

	
	/**
	 * OpenUp. Set Columns.
	 * @param table
	 */
	private void setTableColumns(MTable table, boolean importColumns) throws Exception{
		if (table != null){
			// Obtengo Columns de la Table y las agrego 
			MColumn[] columnsTable = table.getColumns(true);
			for (int k=0; k<columnsTable.length; k++){
				// Set Column
				this.setColumn(columnsTable[k].getAD_Column_ID(), importColumns);
			}
		}
	}

	/**
	 * OpenUp. Set FieldGroup.
	 * @param adFieldGroupID
	 */
	private void setFieldGropup(int adFieldGroupID){
		if (adFieldGroupID > 0){
			X_AD_FieldGroup fieldGroup = new X_AD_FieldGroup(getCtx(), adFieldGroupID, null);
			if (fieldGroup != null){
				if (!this.migObject.getFieldGroups().containsKey(Integer.valueOf(fieldGroup.getAD_FieldGroup_ID()))){
					this.migObject.getFieldGroups().put(Integer.valueOf(fieldGroup.getAD_FieldGroup_ID()), fieldGroup);
					
					// Set FieldGroup Trls
					this.migObject.getFieldGroupsTrls().put(Integer.valueOf(fieldGroup.getAD_FieldGroup_ID()), new MigrationTrlHdr(fieldGroup));
				}
					
			}
		}
	}

	/**
	 * OpenUp. Set Reference.
	 * @param adReferenceValueID
	 * @throws Exception 
	 */
	private void setReference(int adReferenceValueID) throws Exception{
		if (adReferenceValueID > 0){
			X_AD_Reference reference = new X_AD_Reference(getCtx(), adReferenceValueID, null);
			if (reference != null){
				if (!this.migObject.getReferences().containsKey(Integer.valueOf(reference.getAD_Reference_ID()))){
					this.migObject.getReferences().put(Integer.valueOf(reference.getAD_Reference_ID()), reference);

					// Set Reference Trls
					this.migObject.getReferencesTrls().put(Integer.valueOf(reference.getAD_Reference_ID()), new MigrationTrlHdr(reference));
					
					if (reference.getValidationType().equalsIgnoreCase(X_AD_Reference.VALIDATIONTYPE_ListValidation))
						this.setReferenceList(reference);	// Set Reference List	
					else if (reference.getValidationType().equalsIgnoreCase(X_AD_Reference.VALIDATIONTYPE_TableValidation))
						this.setReferenceTable(reference);  // Set Reference Table		
				}
			}
		}
	}
	
	/**
	 * OpenUp. Set Reference List.
	 * @param reference
	 */
	private void setReferenceList(X_AD_Reference reference){
		if (reference.getAD_Reference_ID() > 0){
			for (ValueNamePair vp : MRefList.getList(getCtx(), reference.getAD_Reference_ID(), false)){
				X_AD_Ref_List refList = MRefList.get(getCtx(), reference.getAD_Reference_ID(), vp.getValue(), null);
				if (refList != null){
					if (!this.migObject.getRefLists().containsKey(Integer.valueOf(refList.getAD_Ref_List_ID()))){
						this.migObject.getRefLists().put(Integer.valueOf(refList.getAD_Ref_List_ID()), refList);						

						// Set ReferenceList Trls
						this.migObject.getRefListsTrls().put(Integer.valueOf(refList.getAD_Ref_List_ID()), new MigrationTrlHdr(refList));
					}
				}
			}
		}
	}
	

	/**
	 * OpenUp. Set Reference Table.
	 * @param reference
	 * @throws Exception 
	 */
	private void setReferenceTable(X_AD_Reference reference) throws Exception{

		if (reference.getAD_Reference_ID() > 0){
			
			MRefTable refTable = MRefTable.getRefTable(getCtx(), reference.getAD_Reference_ID(), null); 
			
			if (refTable != null){

				if (!this.migObject.getRefTables().containsKey(Integer.valueOf(refTable.getAD_Reference_ID()))){
					
					this.migObject.getRefTables().put(Integer.valueOf(refTable.getAD_Reference_ID()), refTable);

					// Set table de la Reference Table
					this.setTable(refTable.getAD_Table_ID(), true);
				}
			}
		}
	}

	/**
	 * OpenUp. Set Column. 
	 * @param adColumnID
	 */
	private void setColumn(int adColumnID, boolean importColumn) throws Exception{
		if (adColumnID > 0){
			X_AD_Column column = new X_AD_Column(getCtx(), adColumnID, null);
			if (column != null){
				if (!this.migObject.getColumns().containsKey(Integer.valueOf(column.getAD_Column_ID()))){
					this.migObject.getColumns().put(Integer.valueOf(column.getAD_Column_ID()), column);

					// Set Element
					this.setElement(column.getAD_Element_ID());
					
					if (importColumn){
						
						column.setMigrateColumn(true);
						
						// Set Column Trls
						this.migObject.getColumnsTrls().put(Integer.valueOf(column.getAD_Column_ID()), new MigrationTrlHdr(column));
						
						// Set Reference
						this.setReference(column.getAD_Reference_Value_ID());
						
						// Set ValRule
						this.setValRule(column.getAD_Val_Rule_ID());
						
						// Set Process
						if (column.getAD_Process_ID() > 0){
							this.setProcess(column.getAD_Process_ID());	
						}
					}
					else{
						column.setMigrateColumn(false);
					}
				}
			}
		}
	}
	
	
	/**
	 * OpenUp. Set Element.
	 * @param adElementID
	 */
	private void setElement(int adElementID){
		if (adElementID > 0){
			X_AD_Element element = new X_AD_Element(getCtx(), adElementID, null);
			if (element != null){
				if (!this.migObject.getElements().containsKey(Integer.valueOf(element.getAD_Element_ID()))){
					this.migObject.getElements().put(Integer.valueOf(element.getAD_Element_ID()), element);
					
					// Set Element Trls
					this.migObject.getElementsTrls().put(Integer.valueOf(element.getAD_Element_ID()), new MigrationTrlHdr(element));
				}
			}					
		}
	}

	/**
	 * OpenUp. Set Element.
	 * @param adElementID
	 */
	private void setValRule(int adValRuleID){
		if (adValRuleID > 0){
			X_AD_Val_Rule valRule = new X_AD_Val_Rule(getCtx(), adValRuleID, null);
			if (valRule != null){
				if (!this.migObject.getValRules().containsKey(Integer.valueOf(valRule.getAD_Val_Rule_ID())))
					this.migObject.getValRules().put(Integer.valueOf(valRule.getAD_Val_Rule_ID()), valRule);
			}					
		}
	}
	
	/**
	 * OpenUp. Serialize to XML.
	 * @throws FileNotFoundException
	 */
	private void serializeToXML(Object value, String fileName) throws FileNotFoundException{
		FileOutputStream os = new FileOutputStream("/tmp/" + fileName);
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(value);
		encoder.close(); 
	}

	/**
	 * OpenUp- Deserialize an Object from an XML File.
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	private Object deserializeFromXML(String fileName) throws FileNotFoundException{

		Object value = null;
		FileInputStream os1 = new FileInputStream(fileName);
		XMLDecoder decoder = new XMLDecoder(os1);
		value = decoder.readObject();
		//MigrationWindow w1 = (MigrationWindow)decoder.readObject();
		decoder.close();
		
		return value;
	}
	
	/**
	 * OpenUp. Export a Window Object to an XML File.
	 * @throws Exception
	 */
	private void exportWindow() throws Exception{

		// Si no tengo id de ventana no hago nada
		if (this.adWindowID <= 0) return;

		// Obtengo modelo de ventana seleccionada
		this.window = new MWindow(getCtx(), this.adWindowID, null); 
		if (this.window == null) return;
		
		// Instancio y seteo modelo de migracion de ventana
		migObject = new MigrationObject();
		migObject.setAdWindowID(adWindowID);
		migObject.setAdWindow(this.window);
		
		// Set Window Trls
		this.migObject.setTrl(new MigrationTrlHdr(this.window));  
		
		// Set Tabs
		MTab[] tabs = this.window.getTabs(true, null);
		
		for (int i=0; i<tabs.length; i++){

			if (!tabs[i].isTranslationTab()){
				MigrationTab migTab = new MigrationTab();
				migTab.setAdTabID(tabs[i].getAD_Tab_ID());
				migTab.setAdTab((X_AD_Tab)tabs[i]);
				
				// Set Tab Trls
				migTab.setTrl(new MigrationTrlHdr(tabs[i]));
			
				// Set fields
				this.setFields(tabs[i], migTab);
				
				this.migObject.getTabs().add(migTab);
				
				// Set table asociada a Tab
				this.setTable(tabs[i].getAD_Table_ID(), true);				
			}
		}

		// Get XML File Name for Export
		
		MClient client = new MClient(getCtx(), Env.getAD_Client_ID(getCtx()), null);
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    String fileName = this.exportPath + "/" + client.getValue() + "_window_" + this.window.getName() + "_" + sdf.format(cal.getTime()) + ".xml";		
	    
		// Serialize to XML
		this.serializeToXML(this.migObject, fileName);
	}

	/**
	 * OpenUp. Import a Window Object from an XML File.
	 * @throws Exception 
	 */
	private void importWindow() throws Exception{
		
		// Si no tengo fileName del archivo a importar no hago nada
		if (this.importFileName == null) return;
		if (this.importFileName.equalsIgnoreCase("")) return;
		
		System.out.println("Inicia proceso....");
		
		// Deserialize from XML
		try {
			System.out.println("Des-serializa XML : " + this.importFileName);
			Object value = this.deserializeFromXML(this.importFileName);
			this.migObject = (MigrationObject)value;
		} catch (Exception e) {
			throw e;
		}
		
		try{
			
			// Comando para que pueda hacer ALTER-UPDATE-ALTER cuando se agregan campos nuevos que no aceptan nulos
			DB.executeUpdateEx("SET CONSTRAINTS ALL IMMEDIATE", null);
			
			System.out.println("************* BEGIN - GET WINDOW ***************");
			
			// Get window
			this.adWindowID = this.migObject.getAdWindowID();
			this.window = (MWindow)this.migObject.getAdWindow();
			this.getFromWindow(this.window);
			
			System.out.println("************* END - GET WINDOW ***************");
			System.out.println("************* BEGIN - GET VALRULES ***************");
			
			// Get ValRules
			for (X_AD_Val_Rule valRule : this.migObject.getValRules().values())
				this.getFromValRule(valRule);
			
			System.out.println("************* END - GET VALRULES ***************");
			System.out.println("************* BEGIN - GET ELEMENTS ***************");
			
			// Get Elements
			for (X_AD_Element element : this.migObject.getElements().values())
				this.getFromElement(element);

			System.out.println("************* END - GET ELEMENTS ***************");
			System.out.println("************* BEGIN - GET REFERENCES ***************");
			
			// Get References
			for (X_AD_Reference reference : this.migObject.getReferences().values())
				this.getFromReference(reference);

			System.out.println("************* END - GET REFERENCES ***************");
			System.out.println("************* BEGIN - GET REFLIST ***************");
			
			// Get Reference_Lists
			for (X_AD_Ref_List refList : this.migObject.getRefLists().values())
				this.getFromRefList(refList);

			System.out.println("************* END - GET REFLIST ***************");
			System.out.println("************* BEGIN - GET FIELDGROUP ***************");
			
			// Get Field Groups
			for (X_AD_FieldGroup fGroup : this.migObject.getFieldGroups().values())
				this.getFromFieldGroup(fGroup);		
			
			System.out.println("************* END - GET FIELDGROUP ***************");
			System.out.println("************* BEGIN - GET TABLE ***************");
			
			// Get Tables
			for (X_AD_Table table : this.migObject.getTables().values())
				this.getFromTable((MTable)table);		

			System.out.println("************* END - GET TABLE ***************");
			System.out.println("************* BEGIN - GET PROCESS ***************");


			// Get Processes
			for (X_AD_Process process : this.migObject.getProcesses().values())
				this.getFromProcess((MProcess)process);		

			// Get Params
			for (X_AD_Process_Para param : this.migObject.getParams().values())
				this.getFromParam(param);

			
			System.out.println("************* END - GET PROCESS ***************");
			System.out.println("************* BEGIN - GET COLUMN ***************");
			
			
			// Get Columns
			for (X_AD_Column column : this.migObject.getColumns().values())
				this.getFromColumn(column);
			

			System.out.println("************* END - GET COLUMN ***************");
			System.out.println("************* BEGIN - GET REFTABLE ***************");
			
			// Get Reference_Tables
			for (X_AD_Ref_Table refTable : this.migObject.getRefTables().values())
				this.getFromRefTable(refTable);

			System.out.println("************* END - GET REFTABLE ***************");
			System.out.println("************* BEGIN - GET TABS AND FIELDS ***************");
			
			// Get Tabs (get fields inside)
			for (MigrationTab migTab : this.migObject.getTabs())
				this.getFromTab(migTab.getAdTab(), migTab);
			
			System.out.println("************* END - GET TABS AND FIELDS ***************");
			System.out.println("************* BEGIN - GET TRANSLATIONS ***************");
			
			// Get Translations
			this.getTranslations();
			
			System.out.println("************* END - GET TRANSLATIONS ***************");
			System.out.println("************* BEGIN - GET CONSTRAINTS ***************");
			
			// Get Foregin Constraints
			this.getForeignConstraints();			
			
			System.out.println("************* END - GET CONSTRAINTS ***************");
			
			
		} catch (Exception e){
			throw new Exception(e.getMessage());
		}
	}

	
	/**
	 * OpenUp. Get Window (new or existing) from a given window.
	 * @param valRuleFrom
	 * @throws Exception 
	 */
	private void getFromWindow(MWindow winFrom) throws Exception{
		
		System.out.println("IN - getFromWindow");
		
		if (winFrom == null) return;
		if (winFrom.getAD_Window_ID() <= 0) return;
		
		MWindow winTo = null;
		
		// Si existe ventana con mismo nombre, la instancio y asumo que es esa ventana la que se va a actualizar.
		String sql = " SELECT ad_window_id FROM ad_window WHERE lower(name) ='" + winFrom.getName().trim().toLowerCase() + "'";
		int adWindowIDTo = DB.getSQLValueEx(null, sql);

		if (adWindowIDTo > 0){
			winTo = new MWindow(getCtx(), adWindowIDTo, null);
		}
		else{
			winTo = new MWindow(getCtx(), 0, null);
			winTo.setName(winFrom.getName());
		}
		
		// Seteo atributos de ventana destino
		winTo.setDescription(winFrom.getDescription());
		winTo.setHelp( (winFrom.getHelp() != null) ? winFrom.getHelp() : "");
		winTo.setWindowType(winFrom.getWindowType());
		winTo.setIsSOTrx(winFrom.isSOTrx());
		winTo.setEntityType(winFrom.getEntityType());
		winTo.setProcessing(false);
		winTo.setAD_Image_ID(winFrom.getAD_Image_ID());
		winTo.setAD_Color_ID(winFrom.getAD_Color_ID());
		winTo.setIsDefault(winFrom.isDefault());
		winTo.setWinHeight(winFrom.getWinHeight());
		winTo.setWinWidth(winFrom.getWinWidth());
		winTo.setIsBetaFunctionality(winFrom.isBetaFunctionality());
		winTo.setOpenAsNewRecord(winFrom.isOpenAsNewRecord());
		winTo.setUseTimer(winFrom.isUseTimer());
		
		// Save
		winTo.saveEx();
		
		// Acceso de ventana a Roles del usuario actual
		/*MUser user = new MUser(getCtx(), getAD_User_ID(), get_TrxName());
		MRole[] roles = user.getRoles(0);
		for (int i=0; i<roles.length; i++){
			MWindowAccess winAccess = new MWindowAccess(winTo, roles[i].getAD_Role_ID());
			winAccess.saveEx(get_TrxName());				
		}*/
		
		// Window Trls
		this.migObject.getTrl().setId(winTo.getAD_Window_ID());
		this.migObject.getTrl().deleteTransalations(null);
		this.migObject.getTrl().insertTranslations(winFrom, null);
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.window.setAD_Window_ID(winTo.getAD_Window_ID());
		
		System.out.println("OUT - getFromWindow");
	}

	/**
	 * OpenUp. Get Tab (new or existing) from a given tab. 
	 * @param tabFrom
	 * @throws Exception 
	 */
	private void getFromTab(X_AD_Tab tabFrom, MigrationTab migTab) throws Exception{
				
		if (tabFrom == null) return;
		if (tabFrom.getAD_Tab_ID() <= 0) return;

		System.out.println("IN - getFromTab : " + tabFrom.getName() + ", " + tabFrom.toString());
		
		// Obtengo table asociada a la tab desde el objeto migracion
		X_AD_Table table = this.migObject.getTables().get(new Integer(tabFrom.getAD_Table_ID()));
		if (table == null) return;
		
		X_AD_Tab tabTo = null;

		String sql = "SELECT ad_tab_id FROM ad_tab WHERE lower(name) ='" + tabFrom.getName().trim().toLowerCase() + "' " +
				 	 " AND ad_window_id =" + this.window.getAD_Window_ID();
		int adTabIDTo = DB.getSQLValueEx(null, sql);
		if (adTabIDTo > 0){
			tabTo = new X_AD_Tab(getCtx(), adTabIDTo, null);
		}
		else{
			tabTo = new X_AD_Tab(getCtx(), 0, null);
			tabTo.setName(tabFrom.getName());
			tabTo.setAD_Window_ID(this.window.getAD_Window_ID());
		}

		// Set attributes
		tabTo.setDescription(tabFrom.getDescription());
		tabTo.setHelp( (tabFrom.getHelp() != null) ? tabFrom.getHelp() : "");
		tabTo.setAD_Table_ID(table.getAD_Table_ID());
		tabTo.setSeqNo(tabFrom.getSeqNo());
		tabTo.setTabLevel(tabFrom.getTabLevel());
		tabTo.setIsSingleRow(tabFrom.isSingleRow());
		tabTo.setIsInfoTab(tabFrom.isInfoTab());
		tabTo.setIsTranslationTab(tabFrom.isTranslationTab());
		tabTo.setIsReadOnly(tabFrom.isReadOnly());
		tabTo.setHasTree(tabFrom.getIsHasTree());
		tabTo.setWhereClause(tabFrom.getWhereClause());
		tabTo.setOrderByClause(tabFrom.getOrderByClause());
		tabTo.setCommitWarning(tabFrom.getCommitWarning());
		tabTo.setProcessing(false);
		tabTo.setAD_Image_ID(tabFrom.getAD_Image_ID());
		tabTo.setImportFields(tabFrom.getImportFields());
		tabTo.setIsSortTab(tabFrom.isSortTab());
		tabTo.setEntityType(tabFrom.getEntityType());
		tabTo.setReadOnlyLogic(tabFrom.getReadOnlyLogic());
		tabTo.setDisplayLogic(tabFrom.getDisplayLogic());
		tabTo.setIsInsertRecord(tabFrom.isInsertRecord());
		tabTo.setIsAdvancedTab(tabFrom.isAdvancedTab());
		tabTo.setIsShowToolbar(tabFrom.isShowToolbar());

		// AD_Column_ID
		if (tabFrom.getAD_Column_ID() > 0){
			if (this.migObject.getColumns().get(new Integer(tabFrom.getAD_Column_ID())) != null)
				tabTo.setAD_Column_ID(this.migObject.getColumns().get(new Integer(tabFrom.getAD_Column_ID())).getAD_Column_ID());	
		}

		// Parent Column
		if (tabFrom.getParent_Column_ID() > 0){
			if (this.migObject.getColumns().get(new Integer(tabFrom.getParent_Column_ID())) != null)
				tabTo.setParent_Column_ID(this.migObject.getColumns().get(new Integer(tabFrom.getParent_Column_ID())).getAD_Column_ID());
		}
		
		tabTo.saveEx();
		
		// Tab Translations
		migTab.getTrl().setId(tabTo.getAD_Tab_ID());
		migTab.getTrl().deleteTransalations(null);
		migTab.getTrl().insertTranslations(this.window, null);
		
		// Get Tab Fields
		for (MigrationField migField : migTab.getFields())
			this.getFromField(migField.getAdField(), migField, tabTo.getAD_Tab_ID());

		System.out.println("OUT - getFromTab : " + tabFrom.getName() + ", " + tabFrom.toString());
	}


	/**
	 * OpenUp. Get Field (new or existing) from a given field. 
	 * @param tabField
	 * @throws Exception 
	 */
	private void getFromField(X_AD_Field fieldFrom, MigrationField migField, int adTabID){
		
		try {
			if (fieldFrom == null) return;
			if (fieldFrom.getAD_Field_ID() <= 0) return;

			System.out.println("IN - getFromField : " + fieldFrom.getName() + ", " + fieldFrom.toString());
			
			// Obtengo columna asociada al field
			X_AD_Column column = this.migObject.getColumns().get(new Integer(fieldFrom.getAD_Column_ID()));
			if (column == null) return;
			
			X_AD_Field fieldTo = null;
			
			String sql = "SELECT ad_field_id FROM ad_field WHERE ad_tab_id =" + adTabID + 
					     " AND ad_column_id=" + column.getAD_Column_ID(); 
			int adFieldIDTo = DB.getSQLValueEx(null, sql);
			if (adFieldIDTo > 0){
				fieldTo = new X_AD_Field(getCtx(), adFieldIDTo, null);
			}
			else{
				fieldTo = new X_AD_Field(getCtx(), 0, null);
				fieldTo.setAD_Column_ID(column.getAD_Column_ID());
				fieldTo.setAD_Tab_ID(adTabID);	
			}

			// Set attributes
			fieldTo.setName(fieldFrom.getName());
			fieldTo.setDescription(fieldFrom.getDescription());
			fieldTo.setHelp( (fieldFrom.getHelp() != null) ? fieldFrom.getHelp() : "");
			fieldTo.setIsCentrallyMaintained(fieldFrom.isCentrallyMaintained());
			fieldTo.setIsDisplayed(fieldFrom.isDisplayed());
			fieldTo.setDisplayLogic(fieldFrom.getDisplayLogic());
			fieldTo.setDisplayLength(fieldFrom.getDisplayLength());
			fieldTo.setIsReadOnly(fieldFrom.isReadOnly());
			fieldTo.setSeqNo(fieldFrom.getSeqNo());
			fieldTo.setSortNo(fieldFrom.getSortNo());
			fieldTo.setIsSameLine(fieldFrom.isSameLine());
			fieldTo.setIsHeading(fieldFrom.isHeading());
			fieldTo.setIsFieldOnly(fieldFrom.isFieldOnly());
			fieldTo.setIsEncrypted(fieldFrom.isEncrypted());
			fieldTo.setEntityType(fieldFrom.getEntityType());
			fieldTo.setAD_Reference_ID(fieldFrom.getAD_Reference_ID());
			fieldTo.setIsMandatory(fieldFrom.getIsMandatory());
			//fieldTo.setIncluded_Tab_ID(fieldFrom.getIncluded_Tab_ID());
			fieldTo.setDefaultValue(fieldFrom.getDefaultValue());
			fieldTo.setInfoFactoryClass(fieldFrom.getInfoFactoryClass());
			
			// FieldGroup
			if (fieldFrom.getAD_FieldGroup_ID() > 0)
				fieldTo.setAD_FieldGroup_ID(this.migObject.getFieldGroups().get(new Integer(fieldFrom.getAD_FieldGroup_ID())).getAD_FieldGroup_ID());
			
			// VAL_RULE
			if (fieldFrom.getAD_Val_Rule_ID() > 0)
				fieldTo.setAD_Val_Rule_ID(this.migObject.getValRules().get(new Integer(fieldFrom.getAD_Val_Rule_ID())).getAD_Val_Rule_ID());
			
			// REFERENCE VALUE
			if (fieldFrom.getAD_Reference_Value_ID() > 0)
				fieldTo.setAD_Reference_Value_ID(this.migObject.getReferences().get(new Integer(fieldFrom.getAD_Reference_Value_ID())).getAD_Reference_ID());

			fieldTo.saveEx();

			// Field translations
			migField.getTrl().setId(fieldTo.getAD_Field_ID());
			migField.getTrl().deleteTransalations(null);
			migField.getTrl().insertTranslations(this.window, null);

			System.out.println("OUT - getFromField : " + fieldFrom.getName() + ", " + fieldFrom.toString());
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/**
	 * OpenUp. Get ValRule (new or existing) from ValRule source.
	 * @param valRuleFrom
	 */
	private void getFromValRule(X_AD_Val_Rule valRuleFrom){
		
		if (valRuleFrom == null) return;
		if (valRuleFrom.getAD_Val_Rule_ID() <= 0) return;
		
		System.out.println("IN - getFromValRule : " + valRuleFrom.getName() + ", " + valRuleFrom.toString());
		
		X_AD_Val_Rule valRuleTo = null;
		
		// Busco por nombre si ya tengo esta ValRule en el destino.
		// Si lo tengo lo actualizo, sino lo tengo lo genero nuevo.
		String sql = "SELECT ad_val_rule_id FROM ad_val_rule WHERE lower(name) ='" + valRuleFrom.getName().trim().toLowerCase() + "'";
		int adValRuleIDTo = DB.getSQLValueEx(null, sql);
		if (adValRuleIDTo > 0){
			valRuleTo = new X_AD_Val_Rule(getCtx(), adValRuleIDTo, null);
		}
		else{
			valRuleTo = new X_AD_Val_Rule(getCtx(), 0, null);
			valRuleTo.setName(valRuleFrom.getName());
		}
		
		// Seteo datos de ValRule destino
		valRuleTo.setDescription(valRuleFrom.getDescription());
		valRuleTo.setType(valRuleFrom.getType());
		valRuleTo.setCode(valRuleFrom.getCode());
		valRuleTo.setEntityType(valRuleFrom.getEntityType());

		valRuleTo.saveEx();
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getValRules().get(new Integer(valRuleFrom.getAD_Val_Rule_ID())).setAD_Val_Rule_ID(valRuleTo.getAD_Val_Rule_ID());
		
		System.out.println("OUT - getFromValRule : " + valRuleFrom.getName() + ", " + valRuleFrom.toString());
	}

	/**
	 * OpenUp. Get Element (new or existing) from a given element.
	 * @param elementFrom
	 */
	private void getFromElement(X_AD_Element elementFrom){
		
		if (elementFrom == null) return;
		if (elementFrom.getAD_Element_ID() <= 0) return;

		System.out.println("IN - getFromElement : " + elementFrom.getName());
		
		X_AD_Element elementTo = null;
		
		// Busco por nombre si ya tengo este objeto en el destino.
		// Si lo tengo lo actualizo, sino lo tengo lo genero nuevo.
		String sql = "SELECT ad_element_id FROM ad_element WHERE lower(columnname) ='" + elementFrom.getColumnName().trim().toLowerCase() + "'";
		int adElementIDTo = DB.getSQLValueEx(null, sql);
		if (adElementIDTo > 0){
			elementTo = new X_AD_Element(getCtx(), adElementIDTo, null);
		}
		else{
			elementTo = new X_AD_Element(getCtx(), 0, null);
			elementTo.setColumnName(elementFrom.getColumnName().trim());
		}

		// Set attributes
		elementTo.setName(elementFrom.getName());
		elementTo.setDescription(elementFrom.getDescription());
		elementTo.setEntityType(elementFrom.getEntityType());
		elementTo.setPrintName(elementFrom.getPrintName());
		elementTo.setHelp( (elementFrom.getHelp() != null) ? elementFrom.getHelp() : "");
		elementTo.setPO_Name(elementFrom.getPO_Name());
		elementTo.setPO_PrintName(elementFrom.getPO_PrintName());
		elementTo.setPO_Description(elementFrom.getPO_Description());
		elementTo.setPO_Help(elementFrom.getPO_Help());

		elementTo.saveEx();
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getElements().get(new Integer(elementFrom.getAD_Element_ID())).setAD_Element_ID(elementTo.getAD_Element_ID());
		
		System.out.println("OUT - getFromElement : " + elementFrom.getName());
	}

	
	/**
	 * OpenUp. Get Reference (new or existing) from a given reference. 
	 * @param referenceFrom
	 */
	private void getFromReference(X_AD_Reference referenceFrom){
		
		if (referenceFrom == null) return;
		if (referenceFrom.getAD_Reference_ID() <= 0) return;

		System.out.println("IN - getFromReference : " + referenceFrom.getName() + ", " + referenceFrom.toString());
		
		X_AD_Reference refTo = null;
		
		
		// Busco por nombre si ya tengo este objeto en el destino.
		// Si lo tengo lo actualizo, sino lo tengo lo genero nuevo.
		String sql = "SELECT ad_reference_id FROM ad_reference WHERE lower(name) ='" + referenceFrom.getName().trim().toLowerCase() + "'";
		int adReferenceIDTo = DB.getSQLValueEx(null, sql);
		if (adReferenceIDTo > 0){
			refTo = new X_AD_Reference(getCtx(), adReferenceIDTo, null);
		}
		else{
			refTo = new X_AD_Reference(getCtx(), 0, null);
			refTo.setName(referenceFrom.getName().trim());
		}

		// Set attributes
		refTo.setDescription(referenceFrom.getDescription());
		refTo.setHelp( (referenceFrom.getHelp() != null) ? referenceFrom.getHelp() : "");
		refTo.setValidationType(referenceFrom.getValidationType());
		refTo.setVFormat(referenceFrom.getVFormat());
		refTo.setEntityType(referenceFrom.getEntityType());
		refTo.setIsOrderByValue(referenceFrom.isOrderByValue());

		refTo.saveEx();
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getReferences().get(new Integer(referenceFrom.getAD_Reference_ID())).setAD_Reference_ID(refTo.getAD_Reference_ID());
		
		System.out.println("OUT - getFromReference : " + referenceFrom.getName() + ", " + referenceFrom.toString());
	}


	/**
	 * OpenUp. Get Field Group (new or existing) from a given one. 
	 * @param referenceFrom
	 */
	private void getFromFieldGroup(X_AD_FieldGroup fgroupFrom){
				
		if (fgroupFrom == null) return;
		if (fgroupFrom.getAD_FieldGroup_ID() <= 0) return;

		System.out.println("IN - getFromFieldGroup : " + fgroupFrom.getName());
		
		X_AD_FieldGroup fgroupTo = null;
		
		// Busco por nombre si ya tengo este objeto en el destino.
		// Si lo tengo lo actualizo, sino lo tengo lo genero nuevo.
		String sql = "SELECT ad_fieldgroup_id FROM ad_fieldgroup WHERE lower(name) ='" + fgroupFrom.getName().trim().toLowerCase() + "'";
		int adFGroupIDTo = DB.getSQLValueEx(null, sql);
		if (adFGroupIDTo > 0){
			fgroupTo = new X_AD_FieldGroup(getCtx(), adFGroupIDTo, null);
		}
		else{
			fgroupTo = new X_AD_FieldGroup(getCtx(), 0, null);
			fgroupTo.setName(fgroupFrom.getName());

		}

		// Set attributes
		fgroupTo.setEntityType(fgroupFrom.getEntityType());
		fgroupTo.setFieldGroupType(fgroupFrom.getFieldGroupType());
		fgroupTo.setIsCollapsedByDefault(fgroupFrom.isCollapsedByDefault());

		fgroupTo.saveEx();
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getFieldGroups().get(new Integer(fgroupFrom.getAD_FieldGroup_ID())).setAD_FieldGroup_ID(fgroupTo.getAD_FieldGroup_ID());
		
		System.out.println("OUT - getFromFieldGroup : " + fgroupFrom.getName());
	}

	/**
	 * OpenUp. Get Reference List (new or existing) from a given reference list. 
	 * @param referenceFrom
	 */
	private void getFromRefList(X_AD_Ref_List refListFrom){
		
		if (refListFrom == null) return;
		if (refListFrom.getAD_Ref_List_ID() <= 0) return;
		
		System.out.println("IN - getFromRefList : " + refListFrom.getName() + ", " + refListFrom.toString());
		
		// Obtengo reference asociado a la lista desde el objeto migracion
		X_AD_Reference reference = this.migObject.getReferences().get(new Integer(refListFrom.getAD_Reference_ID()));
		if (reference == null) return;
		
		X_AD_Ref_List refTo = null;
		String sql = "SELECT ad_ref_list_id FROM ad_ref_list WHERE lower(value) ='" + refListFrom.getValue().trim().toLowerCase() + "'" +
	 			 " AND ad_reference_id = " + reference.getAD_Reference_ID();
		int adRefListIDTo = DB.getSQLValueEx(null, sql);
		if (adRefListIDTo > 0){
			refTo = new X_AD_Ref_List(getCtx(), adRefListIDTo, null);
		}
		else{
			refTo = new X_AD_Ref_List(getCtx(), 0, null);
			refTo.setValue(refListFrom.getValue().trim());
			refTo.setAD_Reference_ID(reference.getAD_Reference_ID());
		}

		// Set attributes
		refTo.setName(refListFrom.getName());
		refTo.setDescription(refListFrom.getDescription());
		refTo.setValidFrom(refListFrom.getValidFrom());
		refTo.setValidTo(refListFrom.getValidTo());
		refTo.setEntityType(refListFrom.getEntityType());

		refTo.saveEx(null);
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getRefLists().get(new Integer(refListFrom.getAD_Ref_List_ID())).setAD_Ref_List_ID(refTo.getAD_Ref_List_ID());
		
		System.out.println("OUT - getFromRefList : " + refListFrom.getName() + ", " + refListFrom.toString());
	}

	/**
	 * OpenUp. Get Reference Table (new or existing) from a given reference table. 
	 * @param referenceFrom
	 */
	private void getFromRefTable(X_AD_Ref_Table refTableFrom){
		
		if (refTableFrom == null) return;
		if (refTableFrom.getAD_Reference_ID() <= 0) return;
		
		System.out.println("IN - getFromRefTable : " + refTableFrom.toString());
		
		// Obtengo reference asociado a la refTable desde el objeto migracion
		X_AD_Reference reference = this.migObject.getReferences().get(new Integer(refTableFrom.getAD_Reference_ID()));
		if (reference == null) return;
		
		// Obtengo table asociada a la refTable desde el objeto migracion
		X_AD_Table table = this.migObject.getTables().get(new Integer(refTableFrom.getAD_Table_ID()));
		if (table == null) return;
		
		X_AD_Ref_Table refTo = null;
		
		// Verifico si ya existe uno con el mismo ID y tabla
		refTo = MRefTable.getRefTable(getCtx(), reference.getAD_Reference_ID(), table.get_ID(), null);

		// Si no existe, asumo uno nuevo
		if (refTo == null){
			refTo = new X_AD_Ref_Table(getCtx(), 0, null);
			refTo.setAD_Reference_ID(reference.getAD_Reference_ID());
			refTo.setAD_Table_ID(table.getAD_Table_ID());
		}

		// Set attributes
		refTo.setIsValueDisplayed(refTableFrom.isValueDisplayed());
		refTo.setWhereClause(refTableFrom.getWhereClause());
		refTo.setOrderByClause(refTableFrom.getOrderByClause());
		refTo.setEntityType(refTableFrom.getEntityType());
		
		// AD_Key (column)
		if (refTableFrom.getAD_Key() > 0){
			if (refTo.getAD_Key() != refTableFrom.getAD_Key()){
				if (this.migObject.getColumns().get(new Integer(refTableFrom.getAD_Key())) != null){
					refTo.setAD_Key(this.migObject.getColumns().get(new Integer(refTableFrom.getAD_Key())).getAD_Column_ID());	
				}
			}
		}
			
		// AD_Display (column)
		if (refTableFrom.getAD_Display() > 0){
			if (refTo.getAD_Display() != refTableFrom.getAD_Display()){
				if (this.migObject.getColumns().get(new Integer(refTableFrom.getAD_Display())) != null){
					refTo.setAD_Display(this.migObject.getColumns().get(new Integer(refTableFrom.getAD_Display())).getAD_Column_ID());	
				}
			}
		}
		
		refTo.saveEx();
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getRefTables().get(new Integer(refTableFrom.getAD_Reference_ID())).setAD_Reference_ID(refTo.getAD_Reference_ID());
		
		System.out.println("OUT - getFromRefTable : " + refTableFrom.toString());
	}

	/**
	 * Get Table (new or existing) from source table.
	 * @param table
	 * @throws Exception 
	 */
	private void getFromTable(MTable tableFrom) throws Exception{

		if (tableFrom == null) return;
		if (tableFrom.getAD_Table_ID() <= 0) return;
		
		if (tableFrom.getImportTable() != null){
			if (tableFrom.getImportTable().equalsIgnoreCase("N")){
				return;
			}
		}
		
		System.out.println("IN - getFromTable : " + tableFrom.getName() + ", " + tableFrom.toString());
		
		MTable tableTo = null;
		
		// Busco por nombre si ya tengo este objeto en el destino.
		// Si lo tengo lo actualizo, sino lo tengo lo genero nuevo.
		String sql = "SELECT ad_table_id FROM ad_table WHERE lower(tablename) ='" + tableFrom.getTableName().trim().toLowerCase() + "'";
		int adTableIDTo = DB.getSQLValueEx(null, sql);
		if (adTableIDTo > 0){
			tableTo = new MTable(getCtx(), adTableIDTo, null);
		}
		else{
			// Creo nueva table en DD
			tableTo = new MTable(getCtx(), 0, null);
			tableTo.setTableName(tableFrom.getTableName());	
			
			// Si la tabla No es una vista
			if (!tableFrom.isView()){
				// Ejecuto script de creacion de table en DB
				MigrationTableScript tableScript = this.migObject.getTableScripts().get(new Integer(tableFrom.getAD_Table_ID()));
				DB.executeUpdateEx(tableScript.getTableScript(), null);
			}
		}
		
		// Set table attributtes
		tableTo.setName(tableFrom.getName().trim());
		tableTo.setAccessLevel(tableFrom.getAccessLevel());
		tableTo.setDescription(tableFrom.getDescription());
		tableTo.setHelp( (tableFrom.getHelp() != null) ? tableFrom.getHelp() : "");
		tableTo.setIsView(tableFrom.isView());
		tableTo.setIsSecurityEnabled(tableFrom.isSecurityEnabled());
		tableTo.setIsDeleteable(tableFrom.isDeleteable());
		tableTo.setIsHighVolume(tableFrom.isHighVolume());
		tableTo.setImportTable(tableFrom.getImportTable());
		tableTo.setIsChangeLog(tableFrom.isChangeLog());
		tableTo.setReplicationType(tableFrom.getReplicationType());
		
		tableTo.saveEx();
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getTables().get(new Integer(tableFrom.getAD_Table_ID())).setAD_Table_ID(tableTo.getAD_Table_ID());
		
		System.out.println("OUT - getFromTable : " + tableFrom.getName() + ", " + tableFrom.toString());
	}

	/**
	 * Get column (new or existing) from source column.
	 * @param column
	 * @throws Exception 
	 */
	private void getFromColumn(X_AD_Column columnFrom) throws Exception{
		
		if (columnFrom == null) return;
		if (columnFrom.getAD_Column_ID() <= 0) return;
		
		// Obtengo table asociada a la columna desde el objeto migracion
		X_AD_Table table = this.migObject.getTables().get(new Integer(columnFrom.getAD_Table_ID()));
		if (table == null) return;
		
		System.out.println("IN - getFromColumn : " + columnFrom.getColumnName() + ", " + columnFrom.toString() + ", " +
						   "Table : " + table.getTableName());
		
		X_AD_Column columnTo = null;
		
		// Busco por nombre si ya tengo este objeto en el destino.
		// Si lo tengo lo actualizo, sino lo tengo lo genero nuevo.
		String sql = " SELECT ad_column_id FROM ad_column WHERE lower(columnname) ='" + columnFrom.getColumnName().trim().toLowerCase() + "'" +
				     " AND ad_table_id = " + table.getAD_Table_ID();
		int adColumnIDTo = DB.getSQLValueEx(null, sql);
		if (adColumnIDTo > 0){
			columnTo = new MColumn(getCtx(), adColumnIDTo, null);
		}
		else{
			columnTo = new MColumn(getCtx(), 0, null);
			columnTo.setColumnName(columnFrom.getColumnName());
			columnTo.setAD_Table_ID(table.getAD_Table_ID());
		}

		// Set column attributtes
		columnTo.setName(columnFrom.getName());
		columnTo.setDescription(columnFrom.getDescription());
		columnTo.setHelp( (columnFrom.getHelp() != null) ? columnFrom.getHelp() : "");
		columnTo.setVersion(columnFrom.getVersion());
		columnTo.setEntityType(columnFrom.getEntityType());
		columnTo.setFieldLength(columnFrom.getFieldLength());
		columnTo.setDefaultValue(columnFrom.getDefaultValue());
		columnTo.setIsKey(columnFrom.isKey());
		columnTo.setIsParent(columnFrom.isParent());
		columnTo.setIsMandatory(columnFrom.isMandatory());
		columnTo.setIsUpdateable(columnFrom.isUpdateable());
		columnTo.setReadOnlyLogic(columnFrom.getReadOnlyLogic());
		columnTo.setIsIdentifier(columnFrom.isIdentifier());
		columnTo.setSeqNo(columnFrom.getSeqNo());
		columnTo.setIsTranslated(columnFrom.isTranslated());
		columnTo.setCallout(columnFrom.getCallout());
		columnTo.setVFormat(columnFrom.getVFormat());
		columnTo.setValueMin(columnFrom.getValueMin());
		columnTo.setValueMax(columnFrom.getValueMax());
		columnTo.setIsSelectionColumn(columnFrom.isSelectionColumn());
		columnTo.setIsAlwaysUpdateable(columnFrom.isAlwaysUpdateable());
		columnTo.setColumnSQL(columnFrom.getColumnSQL());
		columnTo.setMandatoryLogic(columnFrom.getMandatoryLogic());
		columnTo.setInfoFactoryClass(columnFrom.getInfoFactoryClass());
		columnTo.setIsAutocomplete(columnFrom.isAutocomplete());
		columnTo.setIsAllowLogging(columnFrom.isAllowLogging());
		columnTo.setFormatPattern(columnFrom.getFormatPattern());
		columnTo.setAD_Reference_ID(columnFrom.getAD_Reference_ID());
		
		// VAL_RULE
		if (columnFrom.getAD_Val_Rule_ID() > 0){
			if (this.migObject.getValRules().get(new Integer(columnFrom.getAD_Val_Rule_ID())) != null){
				columnTo.setAD_Val_Rule_ID(this.migObject.getValRules().get(new Integer(columnFrom.getAD_Val_Rule_ID())).getAD_Val_Rule_ID());	
			}
		}
	
		// REFERENCE VALUE
		if (columnFrom.getAD_Reference_Value_ID() > 0){
			if (this.migObject.getReferences().get(new Integer(columnFrom.getAD_Reference_Value_ID())) != null){
				columnTo.setAD_Reference_Value_ID(this.migObject.getReferences().get(new Integer(columnFrom.getAD_Reference_Value_ID())).getAD_Reference_ID());	
			}
		}
		
		// ELEMENT
		columnTo.setAD_Element_ID(this.migObject.getElements().get(new Integer(columnFrom.getAD_Element_ID())).getAD_Element_ID());
		
		// AD_PROCESS
		if (columnFrom.getAD_Process_ID() > 0){
			if (this.migObject.getProcesses().get(new Integer(columnFrom.getAD_Process_ID())) != null){
				columnTo.setAD_Process_ID(this.migObject.getProcesses().get(new Integer(columnFrom.getAD_Process_ID())).getAD_Process_ID());	
			}
		}

		columnTo.saveEx();

		if (!table.isView()){
			if (columnTo.getColumnSQL() != null){
				if (columnTo.isMigrateColumn()){
					this.executeLOCALSyncColumnProcess(columnTo.getAD_Column_ID());	
				}
			}
		}
		
		/*
		// Ejecuto proceso de sincronizacion de columna en DB a partir del DD		
		if ((!table.isView()) && (!columnTo.isKey()) && (columnTo.getColumnSQL()==null) && (!this.columnHasDependencies(columnTo)))
			this.executeLOCALSyncColumnProcess(columnTo.getAD_Column_ID());
		*/

		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getColumns().get(new Integer(columnFrom.getAD_Column_ID())).setAD_Column_ID(columnTo.getAD_Column_ID());
		
		System.out.println("OUT - getFromColumn : " + columnFrom.getColumnName() + ", " + columnFrom.toString() + ", " +
				   "Table : " + table.getTableName());

	}
	
	/*private void executeTableColumnCreateProcess(int adTableID){
		
		int adProcessID = MProcess.getProcess_ID("AD_Table_CreateColumns", null);
		MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, adTableID);
		if (!instance.save())
		{
			log.log(Level.SEVERE,"No fue posible guardar informacion de instancia para Proceso de Creacion de Columnas de Tabla.");
			return;
		}
		ProcessInfo pi = new ProcessInfo ("TableCreateColumns", adProcessID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());

		//	Add Parameter - DocumentNo - Numero de documento de la orden de proceso a reportear
		MPInstancePara ip = new MPInstancePara(instance, 10);
		ip.setParameter("PP_Order_ID1",this.getPP_Order_ID());
		if (!ip.save())
		{
			log.log(Level.WARNING, "No fue posible agregar parametro para ID1 de Orden en el Proceso de Reporte de Orden de Proceso.");
			return;
		}
		
		MPInstancePara ip2 = new MPInstancePara(instance, 20);
		ip2.setParameter("PP_Order_ID2",this.getPP_Order_ID());
		if (!ip2.save())
		{
			log.log(Level.WARNING, "No fue posible agregar parametro para ID2 de Orden en el Proceso de Reporte de Orden de Proceso.");
			return;
		}
		
		//	Execute Process
		ProcessCtl worker = new ProcessCtl(null, Env.getWindowNo(null), pi, Trx.get(get_TrxName(), false));
		worker.start();     
	}*/

	/**
	 * Execute process for Columns syncronizing.
	 * @param adColumnID
	 */
	/*private void executeSyncColumnProcess(int adColumnID){
		
		int adProcessID = MProcess.getProcess_ID("AD_Column Sync", null);
		MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, adColumnID);
		if (!instance.save())
		{
			log.log(Level.SEVERE,"Error en save de instancia para Sincronizacion de Columna.");
			return;
		}
		ProcessInfo pi = new ProcessInfo ("ColumnSync", adProcessID, X_AD_Column.Table_ID, adColumnID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
		
		//	Execute Process
		ProcessCtl worker = new ProcessCtl(null, Env.getWindowNo(null), pi, Trx.get(get_TrxName(), false));
		worker.start();     
	}*/

	/**
	 * OpenUp. Get all objects Translations.
	 * @throws Exception 
	 */
	private void getTranslations() throws Exception{
		
		try{

			PO model = null;
			if (this.window != null) model = this.window;
			if (this.process != null) model = this.process;
			if (this.table != null) model = this.table;
			
			// Table Trls
			for (MigrationTrlHdr trl : this.migObject.getTablesTrls().values()){
				int id = this.migObject.getTables().get(new Integer(trl.getId())).getAD_Table_ID();
				trl.setId(id);
				trl.deleteTransalations(null);
				trl.insertTranslations(model, null);
			}
			
			// Column Trls
			for (MigrationTrlHdr trl : this.migObject.getColumnsTrls().values()){
				int id = this.migObject.getColumns().get(new Integer(trl.getId())).getAD_Column_ID();
				trl.setId(id);
				trl.deleteTransalations(null);
				trl.insertTranslations(model, null);
			}
			
			// Element Trls
			for (MigrationTrlHdr trl : this.migObject.getElementsTrls().values()){
				int id = this.migObject.getElements().get(new Integer(trl.getId())).getAD_Element_ID();
				trl.setId(id);
				trl.deleteTransalations(null);
				trl.insertTranslations(model, null);
			}
			
			// FieldGroup Trls
			for (MigrationTrlHdr trl : this.migObject.getFieldGroupsTrls().values()){
				int id = this.migObject.getFieldGroups().get(new Integer(trl.getId())).getAD_FieldGroup_ID();
				trl.setId(id);
				trl.deleteTransalations(null);
				trl.insertTranslations(model, null);
			}
			
			// Reference Trls
			for (MigrationTrlHdr trl : this.migObject.getReferencesTrls().values()){
				int id = this.migObject.getReferences().get(new Integer(trl.getId())).getAD_Reference_ID();
				trl.setId(id);
				trl.deleteTransalations(null);
				trl.insertTranslations(model, null);
			}
			
			// RefList Trls
			for (MigrationTrlHdr trl : this.migObject.getRefListsTrls().values()){
				int id = this.migObject.getRefLists().get(new Integer(trl.getId())).getAD_Ref_List_ID();
				trl.setId(id);
				trl.deleteTransalations(null);
				trl.insertTranslations(model, null);
			}
			
			// Params Trls
			for (MigrationTrlHdr trl : this.migObject.getParamTrls().values()){
				int id = this.migObject.getParams().get(new Integer(trl.getId())).getAD_Process_Para_ID();
				trl.setId(id);
				trl.deleteTransalations(null);
				trl.insertTranslations(model, null);
			}
			
		} catch (Exception e){
			throw e;
		}

	}

	/**
	 * Update tables foregin constraints.
	 * @throws Exception
	 */
	private void getForeignConstraints(){

		try{
			// Para cada table de migracion, elimino la foreign constraint actuales y 
			// doy de alta las que vienen en la migracion
			for (MigrationTableScript tableScript : this.migObject.getTableScripts().values()){
				
				if (tableScript == null) continue;
				
				X_AD_Table table = this.migObject.getTables().get(new Integer(tableScript.getTableId()));

				if (table == null) continue;
				if (table.isView()) continue;
				
				// Delete constraint
				tableScript.setTableId(table.getAD_Table_ID());
				tableScript.deleteForeignKeys(null);
				
				// Insert new ones
				for (String constraint : tableScript.getForeignConstraints()){
					System.out.println(constraint);
					
					try {
						DB.executeUpdateEx(constraint, null);	
					} catch (Exception e) {
						// Nada en caso de error
					}
					
				}
					
			}			
		} catch (Exception e){
			throw new AdempiereException();
		}

	}

	private void executeLOCALSyncColumnProcess(int adColumnID) throws Exception{

		if (adColumnID <= 0) return ;

		MColumn column = new MColumn (getCtx(), adColumnID, null);
		if (column.get_ID() <= 0) return;
			
		MTable table = new MTable(getCtx(), column.getAD_Table_ID(), null); 
		if (table.get_ID() <= 0) return;
		
		//	Find Column in Database
		Connection conn = null;
		try {
			conn = DB.getConnectionRO();
			DatabaseMetaData md = conn.getMetaData();
			String catalog = DB.getDatabase().getCatalog();
			String schema = DB.getDatabase().getSchema();
			String tableName = table.getTableName();
			if (md.storesUpperCaseIdentifiers())
			{
				tableName = tableName.toUpperCase();
			}
			else if (md.storesLowerCaseIdentifiers())
			{
				tableName = tableName.toLowerCase();
			}
			int noColumns = 0;
			String sql = null;
			//
			ResultSet rs = md.getColumns(catalog, schema, tableName, null);
			while (rs.next())
			{
				noColumns++;
				String columnName = rs.getString ("COLUMN_NAME");
				if (!columnName.equalsIgnoreCase(column.getColumnName()))
					continue;
				
				//	update existing column
				boolean notNull = DatabaseMetaData.columnNoNulls == rs.getInt("NULLABLE");
				sql = column.getSQLModify(table, column.isMandatory() != notNull);
				break;
			}
			rs.close();
			rs = null;
		
			//	No Table
			if (noColumns == 0)
				sql = table.getSQLCreate ();
			//	No existing column
			else if (sql == null)
				sql = column.getSQLAdd(table);
			
			int no = 0;
			
			if (sql != null){
				try{
					if (sql.indexOf(DB.SQLSTATEMENT_SEPARATOR) == -1)
					{
						System.out.println(sql);
						no = DB.executeUpdateEx(sql, null);
						addLog (0, null, new BigDecimal(no), sql);
					}
					else
					{
						String statements[] = sql.split(DB.SQLSTATEMENT_SEPARATOR);
						for (int i = 0; i < statements.length; i++)
						{   
							System.out.println(statements[i]);
							int count = DB.executeUpdateEx(statements[i], null);
							addLog (0, null, new BigDecimal(count), statements[i]);
							no += count;
						}
					}
				} catch (Exception e){
					throw new AdempiereException(e);
				}
				
			}
	
			if (no == -1)
			{
				String msg = "@Error@ ";
				ValueNamePair pp = CLogger.retrieveError();
				if (pp != null)
					msg = pp.getName() + " - ";
				msg += sql;
				throw new AdempiereUserError (msg);
			}
			return;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {}
			}
		}
	}	//	doIt

	/**
	 * OpenUp. Verifica si un campo de una tabla tiene o no vistas que dependan de el.
	 * @param column
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private boolean columnHasDependencies(X_AD_Column column) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean value = false;
		
		try{

			MTable table = new MTable(getCtx(), column.getAD_Table_ID(), null);
			
			sql = " select a.relname, a.oid " +
				  " from pg_class a, pg_depend b, pg_depend c, pg_class d, pg_attribute e " +
				  " where a.oid = b.refobjid " +
				  " and b.objid = c.objid " +
				  " and b.refobjid <> c.refobjid " +
				  " and b.deptype = 'n' " +
				  " and c.refobjid = d.oid " +
				  " and d.relname = lower(?) " +
				  " and d.relkind = 'r' " +
				  " and d.oid = e.attrelid " +
				  " and e.attname = lower(?) " +
				  " and c.refobjsubid = e.attnum " +
				  " and a.relkind = 'v'";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, table.getTableName().toLowerCase());
			pstmt.setString(2, column.getColumnName().toLowerCase());
			rs = pstmt.executeQuery ();
			if (rs.next()) 
				value = true;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;	
	}

	/**
	 * OpenUp. Export a Process Object to an XML File.
	 * @throws Exception
	 */
	private void exportProcess() throws Exception{

		// Si no tengo id de process no hago nada
		if (this.adProcessID <= 0) return;

		// Obtengo modelo de proceso seleccionado
		this.process = new MProcess(getCtx(), this.adProcessID, null); 
		if (this.process == null) return;
		
		// Instancio y seteo modelo de migracion
		migObject = new MigrationObject();
		migObject.setAdProcessID(adProcessID);
		migObject.setAdProcess(this.process);
		
		// Set Process Trls
		this.migObject.setTrl(new MigrationTrlHdr(this.process));  

		// Get and process parameters
		MProcessPara[] params = this.process.getParameters();
		for (int i=0; i<params.length; i++){
			System.out.println(params[i].getColumnName());
			this.setParameter(params[i].getAD_Process_Para_ID());
		}
		
		// Get XML File Name for Export
		MClient client = new MClient(getCtx(), Env.getAD_Client_ID(getCtx()), null);
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    String fileName = this.exportPath + "/" + client.getValue() + "_process_" + this.process.getName() + "_" + sdf.format(cal.getTime()) + ".xml";		
	    
		// Serialize to XML
		this.serializeToXML(this.migObject, fileName);		
	}
	
	/**
	 * OpenUp. Set a process param.
	 * @param adProcessParaID
	 * @throws Exception 
	 */
	private void setParameter(int adProcessParaID) throws Exception{
		if (adProcessParaID > 0){
			X_AD_Process_Para param = new X_AD_Process_Para(getCtx(), adProcessParaID, null);
			if (param != null){
				if (!this.migObject.getParams().containsKey(Integer.valueOf(param.getAD_Process_Para_ID()))){

					this.migObject.getParams().put(Integer.valueOf(param.getAD_Process_Para_ID()), param);
					
					// Set Param Trls
					this.migObject.getParamTrls().put(Integer.valueOf(param.getAD_Process_Para_ID()), new MigrationTrlHdr(param));
					// Set Element
					this.setElement(param.getAD_Element_ID());
					// Set Reference
					this.setReference(param.getAD_Reference_Value_ID());
					// Set ValRule
					this.setValRule(param.getAD_Val_Rule_ID());
				}

			}					
		}
	}
	
	/**
	 * OpenUp. Import a Process Object from an XML File.
	 * @throws Exception 
	 */
	private void importProcess() throws Exception{
		
		// Si no tengo fileName del archivo a importar no hago nada
		if (this.importFileName == null) return;
		if (this.importFileName.equalsIgnoreCase("")) return;
		
		System.out.println("Inicia proceso....");
		
		// Deserialize from XML
		try {
			System.out.println("Des-serializa XML : " + this.importFileName);
			Object value = this.deserializeFromXML(this.importFileName);
			this.migObject = (MigrationObject)value;
		} catch (Exception e) {
			throw e;
		}
		
		try{
			
			// Comando para que pueda hacer ALTER-UPDATE-ALTER cuando se agregan campos nuevos que no aceptan nulos
			DB.executeUpdateEx("SET CONSTRAINTS ALL IMMEDIATE", null);
			
			System.out.println("************* BEGIN - GET WINDOW ***************");
			
			// Get Process
			this.adProcessID = this.migObject.getAdProcessID();
			this.process = (MProcess)this.migObject.getAdProcess();
			this.getFromProcess(this.process);
			
			System.out.println("************* END - GET WINDOW ***************");
			System.out.println("************* BEGIN - GET VALRULES ***************");
			
			// Get ValRules
			for (X_AD_Val_Rule valRule : this.migObject.getValRules().values())
				this.getFromValRule(valRule);
			
			System.out.println("************* END - GET VALRULES ***************");
			System.out.println("************* BEGIN - GET ELEMENTS ***************");
			
			// Get Elements
			for (X_AD_Element element : this.migObject.getElements().values())
				this.getFromElement(element);

			System.out.println("************* END - GET ELEMENTS ***************");
			System.out.println("************* BEGIN - GET REFERENCES ***************");
			
			// Get References
			for (X_AD_Reference reference : this.migObject.getReferences().values())
				this.getFromReference(reference);

			System.out.println("************* END - GET REFERENCES ***************");
			System.out.println("************* BEGIN - GET REFLIST ***************");
			
			// Get Reference_Lists
			for (X_AD_Ref_List refList : this.migObject.getRefLists().values())
				this.getFromRefList(refList);

			System.out.println("************* END - GET REFLIST ***************");
			System.out.println("************* BEGIN - GET FIELDGROUP ***************");
			
			// Get Field Groups
			for (X_AD_FieldGroup fGroup : this.migObject.getFieldGroups().values())
				this.getFromFieldGroup(fGroup);		
			
			System.out.println("************* END - GET FIELDGROUP ***************");
			System.out.println("************* BEGIN - GET TABLE ***************");
			
			// Get Tables
			for (X_AD_Table table : this.migObject.getTables().values())
				this.getFromTable((MTable)table);		
			
			System.out.println("************* END - GET TABLE ***************");
			System.out.println("************* BEGIN - GET COLUMN ***************");
			
			// Get Columns
			for (X_AD_Column column : this.migObject.getColumns().values())
				this.getFromColumn(column);

			System.out.println("************* END - GET COLUMN ***************");
			System.out.println("************* BEGIN - GET REFTABLE ***************");
			
			// Get Reference_Tables
			for (X_AD_Ref_Table refTable : this.migObject.getRefTables().values())
				this.getFromRefTable(refTable);

			System.out.println("************* END - GET REFTABLE ***************");
			System.out.println("************* BEGIN - GET PARAMS ***************");
			
			// Get Params
			for (X_AD_Process_Para param : this.migObject.getParams().values())
				this.getFromParam(param);

			System.out.println("************* END - GET PARAMS ***************");
			System.out.println("************* BEGIN - GET TRANSLATIONS ***************");
			
			// Get Translations
			this.getTranslations();
			
			System.out.println("************* END - GET TRANSLATIONS ***************");
			System.out.println("************* BEGIN - GET CONSTRAINTS ***************");
			
			// Get Foregin Constraints
			this.getForeignConstraints();			
			
			System.out.println("************* END - GET CONSTRAINTS ***************");
			
			
		} catch (Exception e){
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * OpenUp. Get Process (new or existing) from a given process. 
	 * @param winFrom
	 * @throws Exception
	 */
	private void getFromProcess(MProcess processFrom) throws Exception{
		
		System.out.println("IN - getFromProcess");
		
		if (processFrom == null) return;
		if (processFrom.getAD_Process_ID() <= 0) return;
		
		MProcess processTo = null;

		// Intento ver si existe una con el mismo nombre
		String sql = "SELECT ad_process_id FROM ad_process WHERE lower(value) ='" + processFrom.getValue().trim().toLowerCase() + "'";
		int adProcessIDTo= DB.getSQLValueEx(null, sql);
		
		if (adProcessIDTo > 0){
			processTo = new MProcess(getCtx(), adProcessIDTo, null);
		}
		else{
			processTo = new MProcess(getCtx(), 0, null);
			processTo.setValue(processFrom.getValue());			
		}


		// Set attributes
		processTo.setName(processFrom.getName());
		processTo.setDescription(processFrom.getDescription());
		processTo.setHelp( (processFrom.getHelp() != null) ? processFrom.getHelp() : "");
		processTo.setAccessLevel(processFrom.getAccessLevel());
		processTo.setEntityType(processFrom.getEntityType());
		processTo.setIsReport(processFrom.getIsReport());
		processTo.setIsDirectPrint(processFrom.getIsDirectPrint());
		//processTo.setAD_ReportView_ID(processFrom.getAD_ReportView_ID());
		processTo.setClassname(processFrom.getClassname());
		//processTo.setAD_PrintFormat_ID(processFrom.getAD_PrintFormat_ID());
		processTo.setWorkflowValue(processFrom.getWorkflowValue());
		//processTo.setAD_Workflow_ID(processFrom.getAD_Workflow_ID());
		processTo.setIsBetaFunctionality(processFrom.getIsBetaFunctionality());
		processTo.setIsServerProcess(processFrom.getIsServerProcess());
		processTo.setShowHelp(processFrom.getShowHelp());
		processTo.setJasperReport(processFrom.getJasperReport());
		processTo.setAD_Form_ID(processFrom.getAD_Form_ID());
		
		processTo.saveEx();
		
		// Trls
		//this.migObject.getTrl().setId(processTo.getAD_Process_ID());
		//this.migObject.getTrl().deleteTransalations(null);
		//this.migObject.getTrl().insertTranslations(processTo, null);
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		if (this.process != null){
			this.process.setAD_Process_ID(processTo.getAD_Process_ID());	
		}
		
		if (this.migObject.getProcesses() != null){
			this.migObject.getProcesses().get(new Integer(processFrom.get_ID())).setAD_Process_ID(processTo.get_ID());	
		}
		
		System.out.println("OUT - getFromProcess");
	}

	/**
	 * OpenUp. Get Reference (new or existing) from a given process param. 
	 * @param valRuleFrom
	 */
	private void getFromParam(X_AD_Process_Para paramFrom){
		
		if (paramFrom == null) return;
		if (paramFrom.getAD_Process_Para_ID() <= 0) return;
		
		System.out.println("IN - getFromParam : " + paramFrom.getName() + ", " + paramFrom.toString());

		X_AD_Process_Para paramTo = null;
		
		// Obtengo proceso asociado al parametro recibido
		int adProcessID = 0;
		if (this.process != null){
			adProcessID = this.process.get_ID();
		}
		else{
			if (this.migObject.getProcesses() != null){
				adProcessID = ((X_AD_Process)this.migObject.getProcesses().get(new Integer(paramFrom.getAD_Process_ID()))).get_ID();
			}
		}
		
		String sql = "SELECT ad_process_para_id FROM ad_process_para WHERE lower(name) ='" + paramFrom.getName().trim().toLowerCase() + "'" +
				     " AND ad_process_id = " + adProcessID;
		
		int adProcessParaID = DB.getSQLValueEx(null, sql);
		if (adProcessParaID > 0){
			paramTo = new X_AD_Process_Para(getCtx(), adProcessParaID, null);
		}
		else{
			paramTo = new X_AD_Process_Para(getCtx(), 0, null);
			paramTo.setAD_Process_ID(adProcessID);
			paramTo.setName(paramFrom.getName());
		}
		
		// Set attributes
		paramTo.setDescription(paramFrom.getDescription());
		paramTo.setHelp( (paramFrom.getHelp() != null) ? paramFrom.getHelp() : "");
		paramTo.setSeqNo(paramFrom.getSeqNo());
		paramTo.setColumnName(paramFrom.getColumnName());
		paramTo.setIsCentrallyMaintained(paramFrom.getIsCentrallyMaintained());
		paramTo.setFieldLength(paramFrom.getFieldLength());
		paramTo.setIsMandatory(paramFrom.getIsMandatory());
		paramTo.setIsRange(paramFrom.getIsRange());
		paramTo.setDefaultValue(paramFrom.getDefaultValue());
		paramTo.setDefaultValue2(paramFrom.getDefaultValue2());
		paramTo.setVFormat(paramFrom.getVFormat());
		paramTo.setValueMin(paramFrom.getValueMin());
		paramTo.setValueMax(paramFrom.getValueMax());
		paramTo.setEntityType(paramFrom.getEntityType());
		paramTo.setReadOnlyLogic(paramFrom.getReadOnlyLogic());
		paramTo.setDisplayLogic(paramFrom.getDisplayLogic());
		paramTo.setAD_Reference_ID(paramFrom.getAD_Reference_ID());
		
		// VAL_RULE
		if (paramFrom.getAD_Val_Rule_ID() > 0)
			paramTo.setAD_Val_Rule_ID(this.migObject.getValRules().get(new Integer(paramFrom.getAD_Val_Rule_ID())).getAD_Val_Rule_ID());
	
		// REFERENCE VALUE
		if (paramFrom.getAD_Reference_Value_ID() > 0)
			paramTo.setAD_Reference_Value_ID(this.migObject.getReferences().get(new Integer(paramFrom.getAD_Reference_Value_ID())).getAD_Reference_ID());
		
		// ELEMENT
		paramTo.setAD_Element_ID(this.migObject.getElements().get(new Integer(paramFrom.getAD_Element_ID())).getAD_Element_ID());
		
		// Save
		paramTo.saveEx();
		
		// Guardo el ID del objeto destino en el objeto origen para guardar la asociacion idOrigen-idDestino
		this.migObject.getParams().get(new Integer(paramFrom.getAD_Process_Para_ID())).setAD_Process_Para_ID(paramTo.getAD_Process_Para_ID());
		
		System.out.println("OUT - getFromParam : " + paramFrom.getName() + ", " + paramFrom.toString());
	}


	/**
	 * OpenUp. Export a Table Object to an XML File.
	 * @throws Exception
	 */
	private void exportTable() throws Exception{

		// Si no tengo id de objecto no hago nada
		if (this.adTableID <= 0) return;

		// Obtengo modelo asociado
		this.table = new MTable(getCtx(), this.adTableID, null); 
		if (this.table == null) return;
		
		// Instancio y seteo modelo de migracion
		migObject = new MigrationObject();
		migObject.setAdTableID(this.adTableID);
		migObject.setAdTable(this.table);
		
		// Guardo la table en hash para que se pueda procesar facilmente en la importacion
		this.setTable(this.adTableID, true);
		
		// Get XML File Name for Export
		MClient client = new MClient(getCtx(), Env.getAD_Client_ID(getCtx()), null);
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    String fileName = this.exportPath + "/" + client.getValue() + "_table_" + this.table.getTableName() + "_" + sdf.format(cal.getTime()) + ".xml";		
	    
		// Serialize to XML
		this.serializeToXML(this.migObject, fileName);
	}

	
	/**
	 * OpenUp. Import a Table Object from an XML File.
	 * @throws Exception 
	 */
	private void importTable() throws Exception{
		
		// Si no tengo fileName del archivo a importar no hago nada
		if (this.importFileName == null) return;
		if (this.importFileName.equalsIgnoreCase("")) return;
		
		System.out.println("Inicia proceso....");
		
		// Deserialize from XML
		try {
			System.out.println("Des-serializa XML : " + this.importFileName);
			Object value = this.deserializeFromXML(this.importFileName);
			this.migObject = (MigrationObject)value;
		} catch (Exception e) {
			throw e;
		}
		
		try{
			
			// Comando para que pueda hacer ALTER-UPDATE-ALTER cuando se agregan campos nuevos que no aceptan nulos
			DB.executeUpdateEx("SET CONSTRAINTS ALL IMMEDIATE", null);
			
			System.out.println("************* BEGIN - GET TABLE ***************");
			
			// Get Table
			this.adTableID = this.migObject.getAdTableID();
			this.table = (MTable)this.migObject.getAdTable();
			
			System.out.println("************* END - GET TABLE ***************");
			System.out.println("************* BEGIN - GET VALRULES ***************");
			
			// Get ValRules
			for (X_AD_Val_Rule valRule : this.migObject.getValRules().values())
				this.getFromValRule(valRule);
			
			System.out.println("************* END - GET VALRULES ***************");
			System.out.println("************* BEGIN - GET ELEMENTS ***************");
			
			// Get Elements
			for (X_AD_Element element : this.migObject.getElements().values())
				this.getFromElement(element);

			System.out.println("************* END - GET ELEMENTS ***************");
			System.out.println("************* BEGIN - GET REFERENCES ***************");
			
			// Get References
			for (X_AD_Reference reference : this.migObject.getReferences().values())
				this.getFromReference(reference);

			System.out.println("************* END - GET REFERENCES ***************");
			System.out.println("************* BEGIN - GET REFLIST ***************");
			
			// Get Reference_Lists
			for (X_AD_Ref_List refList : this.migObject.getRefLists().values())
				this.getFromRefList(refList);

			System.out.println("************* END - GET REFLIST ***************");
			System.out.println("************* BEGIN - GET TABLES ***************");
			
			// Get Tables
			for (X_AD_Table table : this.migObject.getTables().values())
				this.getFromTable((MTable)table);		
			
			System.out.println("************* END - GET TABLE ***************");
			System.out.println("************* BEGIN - GET COLUMN ***************");
			
			// Get Columns
			for (X_AD_Column column : this.migObject.getColumns().values())
				this.getFromColumn(column);

			System.out.println("************* END - GET COLUMN ***************");
			System.out.println("************* BEGIN - GET REFTABLE ***************");
			
			// Get Reference_Tables
			for (X_AD_Ref_Table refTable : this.migObject.getRefTables().values())
				this.getFromRefTable(refTable);

			System.out.println("************* END - GET REFTABLE ***************");
			System.out.println("************* BEGIN - GET TRANSLATIONS ***************");
			
			// Get Translations
			this.getTranslations();
			
			System.out.println("************* END - GET TRANSLATIONS ***************");
			System.out.println("************* BEGIN - GET CONSTRAINTS ***************");
			
			// Get Foregin Constraints
			this.getForeignConstraints();			
			
			System.out.println("************* END - GET CONSTRAINTS ***************");
			
			
		} catch (Exception e){
			throw new Exception(e.getMessage());
		}
	}
}
