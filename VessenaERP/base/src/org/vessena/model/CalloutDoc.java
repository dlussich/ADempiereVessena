/**
 * 
 */
package org.openup.model;

import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

/**
 * @author gbrust
 *
 */
public class CalloutDoc extends CalloutEngine {

	/**
	 * 
	 */
	public CalloutDoc() {
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * 
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 08/07/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String refreshTab(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		mTab.dataRefreshAll();
		
		return "";
	}	

}
