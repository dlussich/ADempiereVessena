package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MMediosPago;

public class PCompleteMedioPago extends SvrProcess {

	private int recordID = 0;
	
	@Override
	protected String doIt() throws Exception {
		
		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MMediosPago model = new MMediosPago(getCtx(), recordID ,get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para proceso de factura masivo.";
			
			// Completo registro de este modelo. 
			// Si no puedo salgo con el texto del error.
			if(!model.processIt(model.getDocAction())){
				mensajeRetorno = model.getProcessMsg();
				throw new Exception(model.getProcessMsg());
			}

			// Salvo 
			model.saveEx();
			// Confirmo cambios en DB
			commit();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			rollback();
			throw e;
		}
		
		return mensajeRetorno;
	}

	@Override
	protected void prepare() {
		// Guardo ID del registro a considerar
		recordID = getRecord_ID();
	}

}
