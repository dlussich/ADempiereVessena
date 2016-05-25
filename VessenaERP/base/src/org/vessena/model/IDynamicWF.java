/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 04/11/2012
 */
package org.openup.model;

import org.compiere.wf.MWFNode;

/**
 * org.openup.model - IDynamicWF
 * OpenUp Ltda. Issue #92 
 * Description: Interface para comportamiento dinamico de WorkFlow.
 * @author Gabriel Vila - 04/11/2012
 * @see
 * 
 * @version Gabriel Vila - 29/01/2014 : Se agrega metodo que puede procesar aprobaciones automaticas hechas por el sistema.
 */
public interface IDynamicWF {

	public int getDynamicWFResponsibleID(MWFNode node);

	public void setApprovalInfo(int AD_WF_Responsible_ID, String textMsg);

	public String getWFActivityDescription();

	public String getWFActivityHelp();

	public boolean IsParcialApproved();
	
	public void processAutomaticApproval();
	
	public void processAutomaticComplete();
	
}
