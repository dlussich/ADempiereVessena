package org.openup.process;

import java.util.logging.Level;
import org.compiere.process.SvrProcess;
import org.openup.model.MCierreTransporteHdr;

//org.openup.process.PCompleteCierreATR
public class PCierreATR extends SvrProcess {

	private int recordID = 0;
	
	@Override
	protected String doIt() throws Exception {
		
		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MCierreTransporteHdr model = new MCierreTransporteHdr(getCtx(), this.recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para cierre de transporte.";
			
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
