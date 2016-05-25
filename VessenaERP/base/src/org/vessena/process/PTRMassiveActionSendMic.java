/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 20/04/2015
 */
package org.openup.process;

import org.compiere.process.SvrProcess;
import org.openup.model.MTRMassiveAction;

/**
 * org.openup.process - PTRMassiveActionSendMic
 * OpenUp Ltda. Issue #3996 
 * Description: Proceso para envio masivo de MICS a aduana.
 * @author Gabriel Vila - 20/04/2015
 * @see
 */
public class PTRMassiveActionSendMic extends SvrProcess {

	MTRMassiveAction model = null;
	
	/**
	 * Constructor.
	 */
	public PTRMassiveActionSendMic() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/04/2015
	 * @see
	 */
	@Override
	protected void prepare() {
		
		this.model = new MTRMassiveAction(getCtx(), this.getRecord_ID(), null);
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/04/2015
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		this.model.loadData();

		return "OK";

	}

}
