/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 17/05/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MRFQGenFilter;

/**
 * org.openup.process - PQuoteSendMail
 * OpenUp Ltda. Issue #583 
 * Description: Envia emails con informacion de cotizaciones a proveedores.
 * @author Gabriel Vila - 17/05/2013
 * @see
 */
public class PQuoteSendMail extends SvrProcess {

	MRFQGenFilter genfilter = null;
	
	/**
	 * Constructor.
	 */
	public PQuoteSendMail() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 17/05/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.genfilter = new MRFQGenFilter(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}


	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 17/05/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		this.genfilter.sendMailToVendors();
		
		return "OK";
	}

}
