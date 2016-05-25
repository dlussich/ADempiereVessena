/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 29/05/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MRNotification;

/**
 * org.openup.process - PNotificarExecuteAction
 * OpenUp Ltda. Issue #285 
 * Description: Proceso de ejecucion de notificacion de incidencia.
 * @author Gabriel Vila - 29/05/2013
 * @see
 */
public class PNotificarExecuteAction extends SvrProcess {

	MRNotification notificacion = null;
	
	/**
	 * Constructor.
	 */
	public PNotificarExecuteAction() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 29/05/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.notificacion = new MRNotification(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 29/05/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String result = notificacion.processAction();
		
		// Si hubo validacion falsa en procesar accion, aviso y salgo.
		if (result != null){
			throw new AdempiereException(result);
		}
		
		return "OK";
		
	}

}
