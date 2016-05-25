package org.openup.process;


import java.util.logging.Level;
import org.compiere.process.SvrProcess;
import org.openup.model.MUpdDevo_Filter;

//org.openup.process.PDevFromFilters
public class  PDevFromFilters extends SvrProcess {

	MUpdDevo_Filter filtros;

	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Proceso finalizado con exito.";

		try {
			// Instancio modelo
			MUpdDevo_Filter model = new MUpdDevo_Filter(getCtx(), this
					.getRecord_ID(), null);
			;
			if (model.get_ID() <= 0)
				return "No se pudo obtener modelo para este proceso.";

			// Si no puedo salgo con el texto del error.
			if (!model.processIt(model.getDocAction())) {
				mensajeRetorno = model.getProcessMsg();
				throw new Exception(model.getProcessMsg());
			}

			
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			rollback();
			throw e;
		}

		return mensajeRetorno;
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

}
