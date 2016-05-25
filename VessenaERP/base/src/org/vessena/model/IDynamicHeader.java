/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 20/04/2014
 */
package org.openup.model;

import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTab;

/**
 * org.openup.model - IDynamicHeader
 * OpenUp Ltda. Issue #2082. 
 * Description: Interface para cambios dinamicos de textos de columnas de campos en ventanas.
 * @author Gabriel Vila - 20/04/2014
 * @see
 */
public interface IDynamicHeader {

	public void setFielsdHeaderText(GridTab gTab);
	public void setFieldHeaderText(GridFieldVO fieldVO);
	
	
}
