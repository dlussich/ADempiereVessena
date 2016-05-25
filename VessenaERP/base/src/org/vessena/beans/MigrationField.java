/**
 * 
 */
package org.openup.beans;

import org.compiere.model.X_AD_Field;

/**
 * @author root
 *
 */
public class MigrationField {

	private int adFieldID = 0;
	private X_AD_Field adField = null;
	private MigrationTrlHdr trl = new MigrationTrlHdr();
	
	/**
	 * @param adFieldID the adFieldID to set
	 */
	public void setAdFieldID(int adFieldID) {
		this.adFieldID = adFieldID;
	}
	/**
	 * @return the adFieldID
	 */
	public int getAdFieldID() {
		return adFieldID;
	}
	
	/**
	 * @param adField the adField to set
	 */
	public void setAdField(X_AD_Field adField) {
		this.adField = adField;
	}
	/**
	 * @return the adField
	 */
	public X_AD_Field getAdField() {
		return adField;
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
