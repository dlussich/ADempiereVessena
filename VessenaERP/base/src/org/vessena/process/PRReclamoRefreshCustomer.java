/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/04/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MRReclamo;

/**
 * org.openup.process - PRReclamoRefreshCustomer
 * OpenUp Ltda. Issue #737 
 * Description: Proceso que dispara el refresque de informacion de un cliente al momento
 * del ingreso de un incidente.
 * @author Gabriel Vila - 18/04/2013
 * @see
 */
public class PRReclamoRefreshCustomer extends SvrProcess {

	MRReclamo reclamo = null;
	
	/**
	 * Constructor.
	 */
	public PRReclamoRefreshCustomer() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 18/04/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.reclamo = new MRReclamo(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 18/04/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		this.reclamo.refreshCustomerInfo();
		
		return "OK";
	}

}
