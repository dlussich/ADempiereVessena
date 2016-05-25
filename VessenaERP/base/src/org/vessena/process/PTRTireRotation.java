/**
 * 
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MTRTireMove;

/**
 * @author Nicolas
 *
 */
public class PTRTireRotation extends SvrProcess {

	/**
	 * 
	 */
	public PTRTireRotation() {
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

		String mensajeRetorno = "OK";

		try{
			// Instancio modelo
			MTRTireMove model = new MTRTireMove(getCtx(), this.getRecord_ID(), get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";
				
			model.rotate();

			// Salvo 
			model.saveEx();
		
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
