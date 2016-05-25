/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 03/09/2014
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;

/**
 * org.openup.process - PPRTriggerTasks
 * OpenUp Ltda. Issue #2167 
 * Description: Proceso que dispara tareas de cronogramas de manera automatica.
 * @author Gabriel Vila - 03/09/2014
 * @see
 */
public class PPRTriggerTasks extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PPRTriggerTasks() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/09/2014
	 * @see
	 */
	@Override
	protected void prepare() {

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/09/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try {
			
			// Obtengo tareas recurrentes y activas de cronogramas activos y vigentes para el dia de hoy, que inicien con fecha-hora menor o igual a hoy
			// ( y no hayan sido disparadas anteriomente).
			
			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK - Disparador de Tareas.";
	}

}
