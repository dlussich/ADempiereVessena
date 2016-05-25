/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 31/01/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MAcctNavCC;

/**
 * org.openup.process - PAcctNavCC
 * OpenUp Ltda. Issue #273 
 * Description: Proceso que dispara la consulta por centro de costo en el 
 * Navegador Contable.
 * @author Gabriel Vila - 31/01/2013
 * @see
 */
public class PAcctNavCC extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PAcctNavCC() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 31/01/2013
	 * @see
	 */
	@Override
	protected void prepare() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 31/01/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		// Obtengo modelo de navegador contable para ccosto.
		MAcctNavCC navCC = new MAcctNavCC(getCtx(), this.getRecord_ID(), null);
		
		if (navCC.get_ID() <= 0)
			throw new AdempiereException("No se pudo obtener modelo para la consulta.");

		navCC.setWaiting(this.getProcessInfo().getWaiting());
		navCC.execute();
		
		return "OK";
	}

}
