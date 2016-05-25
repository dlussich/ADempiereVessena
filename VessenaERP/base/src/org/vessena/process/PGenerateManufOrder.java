package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MBudget;

public class PGenerateManufOrder extends SvrProcess {
	
	private int recordID = 0;

	@Override
	protected void prepare() {
		
		recordID = getRecord_ID();
		
	}

	@Override
	protected String doIt() throws Exception {
	
		String mensajeRetorno = "Orden generada con exito.";

		try{
			// Instancio modelo
			MBudget model = new MBudget(getCtx(), recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";
				
			model.generateManufOrder();
		
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
