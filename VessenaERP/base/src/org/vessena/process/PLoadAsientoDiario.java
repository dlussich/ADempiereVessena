/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 13/09/2012
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MLoadCierreCtaCte;


/**
 * org.openup.process - PLoadCierreCtaCte
 * OpenUp Ltda. Issue #29 
 * Description: Carga masiva desde excel de Cierre de Cuenta Corriente y generacion de Asiento diario.
 * @author Gabriel Vila - 13/09/2012
 * @see
 */
public class PLoadAsientoDiario extends SvrProcess {

	MLoadCierreCtaCte cierreHdr = null;
	
	/**
	 * Constructor.
	 */
	public PLoadAsientoDiario() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue #29 
	 * @author Hp - 13/09/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.cierreHdr = new MLoadCierreCtaCte(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue #29 
	 * @author Hp - 13/09/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		this.cierreHdr.setWaiting(this.getProcessInfo().getWaiting());
		this.cierreHdr.executeProcess();
		
		return "Proceso Finalizado.";
	}

}
