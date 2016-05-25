/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 15, 2015
*/
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MCFEInbox;
import org.openup.model.MCFEInboxLoad;
import org.openup.model.X_UY_CFE_InboxLoad;

/**
 * org.openup.process - PCFEInboxLoadXLS
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Dec 15, 2015
*/
public class PCFEInboxLoadData extends SvrProcess {

	
	private MCFEInboxLoad model = null;
	
	
	/***
	 * Constructor.
	*/
	public PCFEInboxLoadData() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		this.model = new MCFEInboxLoad(getCtx(), this.getRecord_ID(), get_TrxName());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String message = null;
		
		try {
			
			MCFEInbox cfeInbox = (MCFEInbox)this.model.getUY_CFE_Inbox();
			
			if (this.model.getCFELoadType().equalsIgnoreCase(X_UY_CFE_InboxLoad.CFELOADTYPE_EXCEL)){
				message = cfeInbox.processExcelFile(this.model);	
			}
			else if (this.model.getCFELoadType().equalsIgnoreCase(X_UY_CFE_InboxLoad.CFELOADTYPE_VISTA)){
				message = cfeInbox.processView(this.model);
			}
			else if (this.model.getCFELoadType().equalsIgnoreCase(X_UY_CFE_InboxLoad.CFELOADTYPE_ERP)){
				message = cfeInbox.processManualDocs(this.model);
			}
			
			if (message != null){
				throw new AdempiereException(message);
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK";
	}

}
