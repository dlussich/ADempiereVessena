/**
 * PCompleteFactMasiva.java
 * 28/12/2010
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MProcesoFactmasHdr;

/**
 * OpenUp.
 * PCompleteFactMasiva
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 28/12/2010
 */
public class PCompleteFactMasiva extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PCompleteFactMasiva() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Guardo ID del registro a considerar
		recordID = getRecord_ID();
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MProcesoFactmasHdr model = new MProcesoFactmasHdr(getCtx(), this.recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para proceso de factura masivo.";
			
			// OpenUp. Gabriel Vila. 05/10/2011. Issue #894.
			// Asocio ventana con modelo.
			model.setWindow(this.getProcessInfo().getWindow());
			// Fin Issue #894.

			
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

}
