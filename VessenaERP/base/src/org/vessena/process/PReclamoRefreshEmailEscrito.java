/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 26/08/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MRGestion;

/**
 * org.openup.process - PReclamoRefreshEmailEscrito
 * OpenUp Ltda. Issue #281 
 * Description: Proceso trampa para que se refresque el texto del email en notificacion
 * escrita. De esta manera el usuario puede verlo antes de confirmarlo.
 * @author Gabriel Vila - 26/08/2013
 * @see
 */
public class PReclamoRefreshEmailEscrito extends SvrProcess {

	MRGestion gestion = null;
	
	/**
	 * Constructor.
	 */
	public PReclamoRefreshEmailEscrito() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 26/08/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		
		try{
			this.gestion = new MRGestion(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 26/08/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		// Trampa para llamar al before save de la MGestion donde se hace la visualizacion 
		// del email de confirmacion de motivos de notificacion escrita
		this.gestion.setJustificationApproved(this.gestion.getJustificationApproved() + " ");
		this.gestion.saveEx();
		
		return "OK";
	}

}
