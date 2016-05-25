/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 16/12/2013
 */
package org.openup.beans;

import java.util.Comparator;

import org.compiere.model.MElementValue;

/**
 * org.openup.beans - SortTree
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 16/12/2013
 * @see
 */
public class SortTree implements Comparator<MElementValue> {

	/**
	 * Constructor.
	 */
	public SortTree() {
	}

	@Override
	public int compare(MElementValue o1, MElementValue o2) {
		return o1.getValue().compareTo(o2.getValue());
	}

}
