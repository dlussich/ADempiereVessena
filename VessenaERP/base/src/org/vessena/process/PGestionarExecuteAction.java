/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 29/05/2013
 */
package org.openup.process;


import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MRGestion;

/**
 * org.openup.process - PGestionarExecuteAction
 * OpenUp Ltda. Issue #285 
 * Description: Ejecuta Acction de Gestion de Incidencia.
 * @author Gabriel Vila - 29/05/2013
 * @see
 */
public class PGestionarExecuteAction extends SvrProcess {

	MRGestion gestion = null;

	/**
	 * Constructor.
	 */
	public PGestionarExecuteAction() {
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
			this.gestion = new MRGestion(getCtx(), this.getRecord_ID(), get_TrxName());
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

		// Segun accion de la gestion procedo
		String result = gestion.processAction();
		
		// Si hubo validacion falsa en procesar accion, aviso y salgo.
		if (result != null){
			throw new AdempiereException(result);
		}
		
		return "OK";
		
	}

}
