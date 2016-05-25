/**
 * 
 */
package org.openup.beans;

import java.util.ArrayList;

import org.compiere.model.X_AD_Tab;

/**
 * @author root
 *
 */
public class MigrationTab {

	private int adTabID = 0;
	private X_AD_Tab adTab = null;
	private MigrationTrlHdr trl = new MigrationTrlHdr();
	
	private ArrayList<MigrationField> fields = new ArrayList<MigrationField>();
	
	
	/**
	 * @param adTabID the adTabID to set
	 */
	public void setAdTabID(int adTabID) {
		this.adTabID = adTabID;
	}
	/**
	 * @return the adTabID
	 */
	public int getAdTabID() {
		return adTabID;
	}
	/**
	 * @param adTab the adTab to set
	 */
	public void setAdTab(X_AD_Tab adTab) {
		this.adTab = adTab;
	}
	/**
	 * @return the adTab
	 */
	public X_AD_Tab getAdTab() {
		return adTab;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<MigrationField> fields) {
		this.fields = fields;
	}
	/**
	 * @return the fields
	 */
	public ArrayList<MigrationField> getFields() {
		return fields;
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
	
	
	
}
