/**
 * 
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MCashCount;

/**
 * @author Nicolás
 *
 */
public class PCashCount extends SvrProcess {

	/**
	 * 
	 */
	public PCashCount() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Datos procesados con exito.";

		try{
			// Instancio modelo
			MCashCount model = new MCashCount(getCtx(), this.getRecord_ID(), get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";
				
			model.loadData();
		
			// Confirmo cambios en DB
			commitEx();

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			rollback();
			throw e;
		}
		
		return mensajeRetorno;
	}

}
