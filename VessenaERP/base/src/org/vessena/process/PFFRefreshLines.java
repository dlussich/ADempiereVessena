/**
 * 
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MFFCashCount;

/**
 * @author Nicolas
 *
 */
public class PFFRefreshLines extends SvrProcess{

	/**
	 * 
	 */
	public PFFRefreshLines() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
		String mensajeRetorno = "Refresh realizado con exito.";

		try{
			// Instancio modelo
			MFFCashCount model = new MFFCashCount(getCtx(), this.getRecord_ID(), get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";
				
			model.refresh();

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
