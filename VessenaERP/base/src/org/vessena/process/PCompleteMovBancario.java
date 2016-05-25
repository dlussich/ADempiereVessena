package org.openup.process;

import java.util.logging.Level;
import org.compiere.process.SvrProcess;
import org.openup.model.MMovBancariosHdr;


public class PCompleteMovBancario extends SvrProcess {

	private int recordID = 0;
	
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MMovBancariosHdr model = new MMovBancariosHdr(getCtx(), this.recordID, get_TrxName());
			
			if (model.get_ID() <= 0) return "No se pudo obtener modelo.";
			
			// Si no puedo salgo con el texto del error.
			if(!model.processIt(model.getDocAction())){
				mensajeRetorno = model.getProcessMsg();
				throw new Exception(model.getProcessMsg());
			}

			// Confirmo cambios en DB
			commit();

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			rollback();
			throw e;  // OpenUp M.R. 18-08-2011 Issue#	723 lanzo excepcion para comprobacion del periodo por tipo de documento
		}
		
		return mensajeRetorno;
	}

	@Override
	protected void prepare() {
		// Guardo ID del registro a considerar
		recordID = getRecord_ID();
	}
	
}
