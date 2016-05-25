/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 21/04/2015
 */
package org.openup.process;

import org.compiere.process.SvrProcess;
import org.openup.model.MTRMassiveAction;

/**
 * org.openup.process - PTRMassiveActionSendCrt2
 * OpenUp Ltda. Issue #3996 
 * Description: Proceso para envio masivo de crts.
 * @author Gabriel Vila - 21/04/2015
 * @see
 */
public class PTRMassiveActionSendCrt2 extends SvrProcess {

	MTRMassiveAction model = null;
	
	/**
	 * Constructor.
	 */
	public PTRMassiveActionSendCrt2() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/04/2015
	 * @see
	 */
	@Override
	protected void prepare() {
		
		this.model = new MTRMassiveAction(getCtx(), this.getRecord_ID(), null);

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/04/2015
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		this.model.sendCrts();

		return "OK";
		
	}

}
