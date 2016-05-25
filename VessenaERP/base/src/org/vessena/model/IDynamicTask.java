/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/06/2013
 */
package org.openup.model;

import org.compiere.model.MAttachment;
import org.compiere.model.MQuery;

/**
 * org.openup.model - IDynamicTask
 * OpenUp Ltda. Issue #1034 
 * Description: Interface para acciones diversas.
 * @author Gabriel Vila - 18/06/2013
 * @see
 */
public interface IDynamicTask {

	public void setComment(String comment);
	public void setAttachment(MAttachment attachment);
	public void changeMQuery(MQuery query);
	
}
