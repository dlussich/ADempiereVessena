/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Jan 14, 2016
*/
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MCFEInbox;

/**
 * org.openup.process - PCFEInboxSendData
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Jan 14, 2016
*/
public class PCFEInboxSendData extends SvrProcess {

	private MCFEInbox model = null;
	
	/***
	 * Constructor.
	*/
	public PCFEInboxSendData() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		this.model = new MCFEInbox(getCtx(), this.getRecord_ID(), get_TrxName());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		try {

			this.model.sendData();
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK";

	}

}
