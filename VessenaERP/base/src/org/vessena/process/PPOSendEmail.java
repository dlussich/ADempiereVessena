/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 10/06/2014
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.process.SvrProcess;

/**
 * org.openup.process - PPOSendEmail
 * OpenUp Ltda. Issue #2017 
 * Description: Proceso que envia un email de orden de compra a proveedor.
 * @author Gabriel Vila - 10/06/2014
 * @see
 */
public class PPOSendEmail extends SvrProcess {

	MOrder order = null;
	
	/**
	 * Constructor.
	 */
	public PPOSendEmail() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 10/06/2014
	 * @see
	 */
	@Override
	protected void prepare() {
		
		this.order = new MOrder(getCtx(), this.getRecord_ID(), get_TrxName());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 10/06/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try {
		
			this.order.sendEmailToVendor(false);

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
				
		return "OK";
		
	}

}
