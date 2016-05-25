/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/09/2012
 */
package org.openup.process;

import org.compiere.process.SvrProcess;
import org.openup.model.MLoadCierreCtaCte;

/**
 * org.openup.process - PLoadSimpleJournal
 * OpenUp Ltda. Issue #29 
 * Description: Carga masiva de asientos diarios.
 * @author Gabriel Vila - 18/09/2012
 * @see
 */
public class PLoadSimpleJournal extends SvrProcess {

	MLoadCierreCtaCte carga = null;
	
	/**
	 * Constructor.
	 */
	public PLoadSimpleJournal() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 18/09/2012
	 * @see
	 */
	@Override
	protected void prepare() {
		this.carga = new MLoadCierreCtaCte(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 18/09/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		this.carga.setWaiting(this.getProcessInfo().getWaiting());
		
		try{
			this.carga.executeProcess();	
		}
		catch(Exception e){
			throw e;
		}
		
		
		return "Proceso Finalizado.";
		
	}

}
