/**
 * 
 */
package org.openup.beans;

import java.util.ArrayList;
import java.util.HashMap;

import org.compiere.model.X_AD_Column;
import org.compiere.model.X_AD_Element;
import org.compiere.model.X_AD_FieldGroup;
import org.compiere.model.X_AD_Process;
import org.compiere.model.X_AD_Process_Para;
import org.compiere.model.X_AD_Ref_List;
import org.compiere.model.X_AD_Ref_Table;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.X_AD_Table;
import org.compiere.model.X_AD_Val_Rule;
import org.compiere.model.X_AD_Window;

/**
 * @author root
 *
 */
public class MigrationObject {

	private int adWindowID = 0;
	private X_AD_Window adWindow = null;
	
	private int adProcessID = 0;
	private X_AD_Process adProcess = null;

	private int adTableID = 0;
	private X_AD_Table adTable = null;
	
	private ArrayList<MigrationTab> tabs = new ArrayList<MigrationTab>();
	private MigrationTrlHdr trl = new MigrationTrlHdr();
	
	private HashMap<Integer, X_AD_Table> tables = new HashMap<Integer, X_AD_Table>();
	private HashMap<Integer, X_AD_Process> processes = new HashMap<Integer, X_AD_Process>();
	private HashMap<Integer, X_AD_Column> columns = new HashMap<Integer, X_AD_Column>();
	private HashMap<Integer, X_AD_Element> elements = new HashMap<Integer, X_AD_Element>();
	private HashMap<Integer, X_AD_FieldGroup> fieldGroups = new HashMap<Integer, X_AD_FieldGroup>();
	private HashMap<Integer, X_AD_Reference> references = new HashMap<Integer, X_AD_Reference>();
	private HashMap<Integer, X_AD_Ref_List> refLists = new HashMap<Integer, X_AD_Ref_List>();
	private HashMap<Integer, X_AD_Ref_Table> refTables = new HashMap<Integer, X_AD_Ref_Table>();
	private HashMap<Integer, X_AD_Val_Rule> valRules = new HashMap<Integer, X_AD_Val_Rule>();
	private HashMap<Integer, X_AD_Process_Para> params = new HashMap<Integer, X_AD_Process_Para>();
	
	private HashMap<Integer, MigrationTrlHdr> tablesTrls = new HashMap<Integer, MigrationTrlHdr>();  
	private HashMap<Integer, MigrationTrlHdr> processesTrls = new HashMap<Integer, MigrationTrlHdr>();
	private HashMap<Integer, MigrationTrlHdr> columnsTrls = new HashMap<Integer, MigrationTrlHdr>();
	private HashMap<Integer, MigrationTrlHdr> elementsTrls = new HashMap<Integer, MigrationTrlHdr>();
	private HashMap<Integer, MigrationTrlHdr> fieldGroupsTrls = new HashMap<Integer, MigrationTrlHdr>();
	private HashMap<Integer, MigrationTrlHdr> referencesTrls = new HashMap<Integer, MigrationTrlHdr>();
	private HashMap<Integer, MigrationTrlHdr> refListsTrls = new HashMap<Integer, MigrationTrlHdr>();
	private HashMap<Integer, MigrationTrlHdr> paramTrls = new HashMap<Integer, MigrationTrlHdr>();
	
	private HashMap<Integer, MigrationTableScript> tableScripts = new HashMap<Integer, MigrationTableScript>();
	
	/**
	 * @param adWindow the adWindow to set
	 */
	public void setAdWindow(X_AD_Window adWindow) {
		this.adWindow = adWindow;
	}
	/**
	 * @return the adWindow
	 */
	public X_AD_Window getAdWindow() {
		return adWindow;
	}
	/**
	 * @param adWindowID the adWindowID to set
	 */
	public void setAdWindowID(int adWindowID) {
		this.adWindowID = adWindowID;
	}
	/**
	 * @return the adWindowID
	 */
	public int getAdWindowID() {
		return adWindowID;
	}
	/**
	 * @param tabs the tabs to set
	 */
	public void setTabs(ArrayList<MigrationTab> tabs) {
		this.tabs = tabs;
	}
	/**
	 * @return the tabs
	 */
	public ArrayList<MigrationTab> getTabs() {
		return tabs;
	}
	/**
	 * @param tables the tables to set
	 */
	public void setTables(HashMap<Integer, X_AD_Table> tables) {
		this.tables = tables;
	}
	/**
	 * @return the tables
	 */
	public HashMap<Integer, X_AD_Table> getTables() {
		return tables;
	}
	/**
	 * @param tables the tables to set
	 */
	public void setProcesses(HashMap<Integer, X_AD_Process> processes) {
		this.processes = processes;
	}
	/**
	 * @return the tables
	 */
	public HashMap<Integer, X_AD_Process> getProcesses() {
		return this.processes;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(HashMap<Integer, X_AD_Column> columns) {
		this.columns = columns;
	}
	/**
	 * @return the columns
	 */
	public HashMap<Integer, X_AD_Column> getColumns() {
		return columns;
	}
	/**
	 * @param elements the elements to set
	 */
	public void setElements(HashMap<Integer, X_AD_Element> elements) {
		this.elements = elements;
	}
	/**
	 * @return the elements
	 */
	public HashMap<Integer, X_AD_Element> getElements() {
		return elements;
	}
	/**
	 * @param fieldGroups the fieldGroups to set
	 */
	public void setFieldGroups(HashMap<Integer, X_AD_FieldGroup> fieldGroups) {
		this.fieldGroups = fieldGroups;
	}
	/**
	 * @return the fieldGroups
	 */
	public HashMap<Integer, X_AD_FieldGroup> getFieldGroups() {
		return fieldGroups;
	}
	/**
	 * @param references the references to set
	 */
	public void setReferences(HashMap<Integer, X_AD_Reference> references) {
		this.references = references;
	}
	/**
	 * @return the references
	 */
	public HashMap<Integer, X_AD_Reference> getReferences() {
		return references;
	}
	/**
	 * @param refLists the refLists to set
	 */
	public void setRefLists(HashMap<Integer, X_AD_Ref_List> refLists) {
		this.refLists = refLists;
	}
	/**
	 * @return the refLists
	 */
	public HashMap<Integer, X_AD_Ref_List> getRefLists() {
		return refLists;
	}
	/**
	 * @param refTables the refTables to set
	 */
	public void setRefTables(HashMap<Integer, X_AD_Ref_Table> refTables) {
		this.refTables = refTables;
	}
	/**
	 * @return the refTables
	 */
	public HashMap<Integer, X_AD_Ref_Table> getRefTables() {
		return refTables;
	}
	/**
	 * @param valRules the valRules to set
	 */
	public void setValRules(HashMap<Integer, X_AD_Val_Rule> valRules) {
		this.valRules = valRules;
	}
	/**
	 * @return the valRules
	 */
	public HashMap<Integer, X_AD_Val_Rule> getValRules() {
		return valRules;
	}
	/**
	 * @param tablesTrls the tablesTrls to set
	 */
	public void setTablesTrls(HashMap<Integer, MigrationTrlHdr> tablesTrls) {
		this.tablesTrls = tablesTrls;
	}
	/**
	 * @return the tablesTrls
	 */
	public HashMap<Integer, MigrationTrlHdr> getTablesTrls() {
		return tablesTrls;
	}
	/**
	 * @param tablesTrls the tablesTrls to set
	 */
	public void setProcessesTrls(HashMap<Integer, MigrationTrlHdr> processesTrls) {
		this.processesTrls = processesTrls;
	}
	/**
	 * @return the tablesTrls
	 */
	public HashMap<Integer, MigrationTrlHdr> getProcessesTrls() {
		return this.processesTrls;
	}
	/**
	 * @param columnsTrls the columnsTrls to set
	 */
	public void setColumnsTrls(HashMap<Integer, MigrationTrlHdr> columnsTrls) {
		this.columnsTrls = columnsTrls;
	}
	/**
	 * @return the columnsTrls
	 */
	public HashMap<Integer, MigrationTrlHdr> getColumnsTrls() {
		return columnsTrls;
	}
	/**
	 * @param elementsTrls the elementsTrls to set
	 */
	public void setElementsTrls(HashMap<Integer, MigrationTrlHdr> elementsTrls) {
		this.elementsTrls = elementsTrls;
	}
	/**
	 * @return the elementsTrls
	 */
	public HashMap<Integer, MigrationTrlHdr> getElementsTrls() {
		return elementsTrls;
	}
	/**
	 * @param fieldGroupsTrls the fieldGroupsTrls to set
	 */
	public void setFieldGroupsTrls(HashMap<Integer, MigrationTrlHdr> fieldGroupsTrls) {
		this.fieldGroupsTrls = fieldGroupsTrls;
	}
	/**
	 * @return the fieldGroupsTrls
	 */
	public HashMap<Integer, MigrationTrlHdr> getFieldGroupsTrls() {
		return fieldGroupsTrls;
	}
	/**
	 * @param referencesTrls the referencesTrls to set
	 */
	public void setReferencesTrls(HashMap<Integer, MigrationTrlHdr> referencesTrls) {
		this.referencesTrls = referencesTrls;
	}
	/**
	 * @return the referencesTrls
	 */
	public HashMap<Integer, MigrationTrlHdr> getReferencesTrls() {
		return referencesTrls;
	}
	/**
	 * @param refListsTrls the refListsTrls to set
	 */
	public void setRefListsTrls(HashMap<Integer, MigrationTrlHdr> refListsTrls) {
		this.refListsTrls = refListsTrls;
	}
	/**
	 * @return the refListsTrls
	 */
	public HashMap<Integer, MigrationTrlHdr> getRefListsTrls() {
		return refListsTrls;
	}
	/**
	 * @param trl the trl to set
	 */
	public void setTrl(MigrationTrlHdr trl) {
		this.trl = trl;
	}
	/**
	 * @return the trl
	 */
	public MigrationTrlHdr getTrl() {
		return trl;
	}
	/**
	 * @param tableScripts the tableScripts to set
	 */
	public void setTableScripts(HashMap<Integer, MigrationTableScript> tableScripts) {
		this.tableScripts = tableScripts;
	}
	/**
	 * @return the tableScripts
	 */
	public HashMap<Integer, MigrationTableScript> getTableScripts() {
		return tableScripts;
	}
	/**
	 * @param adProcessID the adProcessID to set
	 */
	public void setAdProcessID(int adProcessID) {
		this.adProcessID = adProcessID;
	}
	/**
	 * @return the adProcessID
	 */
	public int getAdProcessID() {
		return adProcessID;
	}
	/**
	 * @param adProcess the adProcess to set
	 */
	public void setAdProcess(X_AD_Process adProcess) {
		this.adProcess = adProcess;
	}
	/**
	 * @return the adProcess
	 */
	public X_AD_Process getAdProcess() {
		return adProcess;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(HashMap<Integer, X_AD_Process_Para> params) {
		this.params = params;
	}
	/**
	 * @return the params
	 */
	public HashMap<Integer, X_AD_Process_Para> getParams() {
		return params;
	}
	/**
	 * @param paramTrls the paramTrls to set
	 */
	public void setParamTrls(HashMap<Integer, MigrationTrlHdr> paramTrls) {
		this.paramTrls = paramTrls;
	}
	/**
	 * @return the paramTrls
	 */
	public HashMap<Integer, MigrationTrlHdr> getParamTrls() {
		return paramTrls;
	}
	/**
	 * @param adTableID the adTableID to set
	 */
	public void setAdTableID(int adTableID) {
		this.adTableID = adTableID;
	}
	/**
	 * @return the adTableID
	 */
	public int getAdTableID() {
		return adTableID;
	}
	/**
	 * @param adTable the adTable to set
	 */
	public void setAdTable(X_AD_Table adTable) {
		this.adTable = adTable;
	}
	/**
	 * @return the adTable
	 */
	public X_AD_Table getAdTable() {
		return adTable;
	}
	
	
	
}
