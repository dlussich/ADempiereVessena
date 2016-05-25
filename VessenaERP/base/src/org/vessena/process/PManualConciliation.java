package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MConciliation;

public class PManualConciliation extends SvrProcess {
		
	@Override
	protected void prepare() {
				
	}

	@Override
	protected String doIt() throws Exception {
		
		String mensajeRetorno = "Conciliacion realizada con exito.";

		try{
			// Instancio modelo
			MConciliation model = new MConciliation(getCtx(), this.getRecord_ID(), get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";
				
			model.createManualConciliation();

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
