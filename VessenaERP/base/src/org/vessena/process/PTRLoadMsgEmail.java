/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 24/02/2015
 */
package org.openup.process;

import org.compiere.process.SvrProcess;
import org.openup.model.MTRLoadMsg;

/**
 * org.openup.process - PTRLoadMsgEmail
 * OpenUp Ltda. Issue #1405 
 * Description: Proceso que envia mensajes de carga.
 * @author Gabriel Vila - 24/02/2015
 * @see
 */
public class PTRLoadMsgEmail extends SvrProcess {

	
	MTRLoadMsg model = null;
	
	/**
	 * Constructor.
	 */
	public PTRLoadMsgEmail() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 24/02/2015
	 * @see
	 */
	@Override
	protected void prepare() {

		this.model = new MTRLoadMsg(getCtx(), this.getRecord_ID(), null);
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 24/02/2015
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		this.model.sendEmails();

		return "OK";
		
	}

}
