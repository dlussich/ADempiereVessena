package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MTRTrip;

public class PTRTripRefreshDoc extends SvrProcess {
	
	private int tripID = 0;

	public PTRTripRefreshDoc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		tripID = getRecord_ID();

	}

	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Operacion realizada con exito.";

		try{
			// Instancio modelo
			MTRTrip model = new MTRTrip(getCtx(), tripID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";

			model.refreshFromTrip();

			// Confirmo cambios en DB
			//commitEx();

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
